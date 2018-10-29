package com.fishqq.mybatis.plugins.pagination;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import com.fishqq.mybatis.plugins.InvocationInfo;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

@Intercepts({@Signature(method = "query",
    type = Executor.class,
    args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class PaginationPlugin implements Interceptor {
    /**
     * SQL 方言类型
     */
    public enum SqlDialect {
        MYSQL,
        POSTGRESQL;

        public static SqlDialect fromName(String name) {
            for (SqlDialect dialect : SqlDialect.values()) {
                if (dialect.toString().equalsIgnoreCase(name)) {
                    return dialect;
                }
            }
            return null;
        }
    }

    private static final Boolean DEFAULT_USING_PHYSICAL_PAGINATION = true;

    private static final List<ResultMapping> EMPTY_RESULTMAPPING = new ArrayList<>();

    private static final String BOUND_SQL_SQL_FIELD = "sql";

    private static final String MANUAL_COUNT_MS_ID_SUFFIX = "_COUNT";

    private SqlDialect dialect;
    private Boolean usingPhysicalPagination;

    public PaginationPlugin() {
        this.dialect = SqlDialect.POSTGRESQL;
        this.usingPhysicalPagination = DEFAULT_USING_PHYSICAL_PAGINATION;
    }

    public PaginationPlugin(SqlDialect dialect, Boolean usingPhysicalPagination) {
        this.dialect = dialect;
        this.usingPhysicalPagination = usingPhysicalPagination;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object result;

        if (invocation.getTarget() instanceof Executor) {
            result = process(invocation);
        } else {
            result = invocation.proceed();
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    private Object process(Invocation invocation) throws Throwable {
        InvocationInfo invocationInfo = new InvocationInfo(invocation);

        PaginationResult result = new PaginationResult();

        Pagination paginationCriteria = getPaginationParam(invocationInfo);

        if (paginationCriteria == null) {
            Object data = invocation.proceed();
            result.setData((List)data);
            result.setTotalCount(((List<Object>)data).size());
            return result;
        }

        if (paginationCriteria.isNeedCount()) {
            Long count = executeCount(invocationInfo);
            result.setTotalCount(count.intValue());
        }

        MappedStatement ms = invocationInfo.getMappedStatement();
        BoundSql boundSql = invocationInfo.getBoundSql();
        MetaObject boundSqlMetaObject = ms.getConfiguration().newMetaObject(boundSql);

        if (usingPhysicalPagination) {
            //直接进行内存分页
            invocationInfo.setMappedStatement(cloneMappedStatement(ms, boundSql, null));
            invocationInfo.setRowBounds(new RowBounds(paginationCriteria.getOffset(),
                paginationCriteria.getPageSize()));

            Object data = invocation.proceed();
            result.setData((List<Object>)data);
        } else {
            // replace to limit sql
            String limitSql = getLimitSql(invocationInfo.getBoundSql().getSql(), paginationCriteria);
            replaceSql(boundSqlMetaObject, limitSql);

            //禁用内存分页
            invocationInfo.setRowBounds(RowBounds.DEFAULT);

            invocationInfo.setMappedStatement(cloneMappedStatement(ms, boundSql, null));

            Object data = invocation.proceed();
            result.setData((List<Object>)data);
        }

        return result;
    }

    private Long executeCount(InvocationInfo target)
        throws SQLException, IllegalAccessException, InvocationTargetException {

        MappedStatement ms = target.getMappedStatement();
        Configuration configuration = ms.getConfiguration();

        String countMsId = ms.getId() + MANUAL_COUNT_MS_ID_SUFFIX;
        MappedStatement manualCountMs = getExistedMappedStatement(configuration, countMsId);
        if (manualCountMs != null) {
            return executeManualCount(target, manualCountMs);
        } else {
            return executeAutoCount(target);
        }
    }

    /**
     * 执行手写count
     */
    private Long executeManualCount(InvocationInfo target, MappedStatement manCountMs)
        throws IllegalAccessException, SQLException {

        Executor executor = target.getTarget();
        CacheKey cacheKey = executor.createCacheKey(manCountMs, target.getParameter(), target.getRowBounds(),
            target.getBoundSql());
        BoundSql countBoundSql = manCountMs.getBoundSql(target.getParameter());

        List<?> countResults = executor.query(manCountMs, target.getParameter(), target.getRowBounds(),
            target.getResultHandler(), cacheKey, countBoundSql);

        return ((Number)countResults.get(0)).longValue();
    }

    /**
     * 执行自动count
     *
     * @param target
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private Long executeAutoCount(InvocationInfo target)
        throws InvocationTargetException, IllegalAccessException {

        MappedStatement ms = target.getMappedStatement();
        BoundSql boundSql = target.getBoundSql();
        Invocation invocation = target.invocation;
        MetaObject boundSqlMetaObject = ms.getConfiguration().newMetaObject(boundSql);

        String originSql = boundSql.getSql();
        String countSql = getCountSql(originSql);

        // replace to count sql
        replaceSql(boundSqlMetaObject, countSql);

        // replace ms
        target.setMappedStatement(cloneMappedStatement(ms,
            boundSql,
            Arrays.asList(new ResultMap.Builder(ms.getConfiguration(), ms.getId(),
                Integer.class, EMPTY_RESULTMAPPING).build())
        ));

        List<?> countData = (List<?>)invocation.proceed();

        Long count = ((Number)countData.get(0)).longValue();

        // reset origin sql
        replaceSql(boundSqlMetaObject, originSql);

        // reset ms
        target.setMappedStatement(ms);

        return count;
    }

    private MappedStatement getExistedMappedStatement(Configuration configuration, String msId) {
        MappedStatement mappedStatement = null;
        try {
            mappedStatement = configuration.getMappedStatement(msId, false);
        } catch (Throwable t) {
            //ignore
        }
        return mappedStatement;
    }

    private void replaceSql(MetaObject metaObject, String sql) {
        metaObject.setValue(BOUND_SQL_SQL_FIELD, sql);
    }

    private boolean needPaginate(InvocationInfo target) {
        Object parameter = target.getBoundSql().getParameterObject();
        if (parameter instanceof Pagination) {
            return true;
        } else if (parameter instanceof Map) {
            Map<?, ?> paramMap = (Map)parameter;
            return paramMap.values().stream()
                .anyMatch(v -> v instanceof RowBounds || v instanceof Pagination);
        } else {
            return false;
        }
    }

    /**
     * 对于已给定分页参数的方法，判断是否需要分页
     *
     * @param paginationCriteria 分页参数
     * @param rowBounds          mybatis 分页参数
     * @return true=分页，false=不分页
     */
    private boolean needProcessPaging(Pagination paginationCriteria, RowBounds rowBounds) {
        if (paginationCriteria == null
            && rowBounds.getLimit() == RowBounds.NO_ROW_LIMIT
            && rowBounds.getOffset() == RowBounds.NO_ROW_OFFSET) {
            return false;
        } else if (paginationCriteria != null) {
            return false;
        } else {
            return true;
        }
    }

    private Pagination getPaginationParam(InvocationInfo invocationTargetInfo) {
        Pagination paginationCriteria = null;

        BoundSql boundSql = invocationTargetInfo.getBoundSql();

        Object parameter = boundSql.getParameterObject();
        if (parameter == null) {
            paginationCriteria = null;
        } else if (parameter instanceof Pagination) {
            paginationCriteria = (Pagination)parameter;
        } else if (parameter instanceof Map) {
            Map<String, ?> paramMap = (Map<String, ?>)parameter;
            Optional<?> p = paramMap.values().stream()
                .filter(v -> v != null && Pagination.class.isAssignableFrom(v.getClass()))
                .findFirst();
            paginationCriteria = (Pagination)p.orElse(null);
        }

        if (paginationCriteria == null) {
            RowBounds rowBounds = invocationTargetInfo.getRowBounds();

            if (rowBounds != null
                && rowBounds.getLimit() != RowBounds.NO_ROW_LIMIT
                && rowBounds.getOffset() != RowBounds.NO_ROW_OFFSET) {

                paginationCriteria = new Pagination().setPageSize(rowBounds.getLimit())
                    .setPage((rowBounds.getOffset()) / rowBounds.getLimit());
            }
        }

        return paginationCriteria;
    }

    private MappedStatement cloneMappedStatement(MappedStatement ms, BoundSql boundSql, List<ResultMap> resultMaps) {
        return new Builder(ms.getConfiguration(), ms.getId(), new BoundSqlSource(boundSql), ms.getSqlCommandType())
            .resource(ms.getResource())
            .parameterMap(ms.getParameterMap())
            .resultMaps((resultMaps == null || resultMaps.isEmpty()) ? ms.getResultMaps() : resultMaps)
            .fetchSize(ms.getFetchSize())
            .timeout(ms.getTimeout())
            .statementType(ms.getStatementType())
            .resultSetType(ms.getResultSetType())
            .cache(ms.getCache())
            .flushCacheRequired(ms.isFlushCacheRequired())
            .useCache(ms.isUseCache())
            .resultOrdered(ms.isResultOrdered())
            .keyGenerator(ms.getKeyGenerator())
            .keyProperty(Arrays.stream(Optional.ofNullable(ms.getKeyProperties()).orElse(new String[0]))
                .collect(Collectors.joining(",")))
            .keyColumn(Arrays.stream(Optional.ofNullable(ms.getKeyColumns()).orElse(new String[0]))
                .collect(Collectors.joining(",")))
            .databaseId(ms.getDatabaseId())
            .lang(ms.getLang())
            .resultSets(Arrays.stream(Optional.ofNullable(ms.getResultSets()).orElse(new String[0]))
                .collect(Collectors.joining(",")))
            .build();
    }

    private static class BoundSqlSource implements SqlSource {
        private BoundSql boundSql;

        BoundSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        @Override
        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }

    /**
     * 获取总数sql - 如果要支持其他数据库，修改这里就可以
     *
     * @param sql 未解析参数的sql
     * @return 未解析参数的count sql
     */
    private String getCountSql(String sql) {
        return "select count(0) from (" + sql + ") tmp_count";
    }

    /**
     * 获取分页sql - 如果要支持其他数据库，修改这里就可以
     *
     * @param sql      未解析参数的sql
     * @param criteria 分页参数
     * @return 未解析参数的limit sql
     */
    private String getLimitSql(String sql, Pagination criteria) {
        StringBuilder pageSql = new StringBuilder();
        if (SqlDialect.MYSQL == dialect) {
            pageSql.append(sql).append(" LIMIT ").append(criteria.getOffset())
                .append(",").append(criteria.getPageSize());
        } else {
            pageSql.append(sql).append(" LIMIT ").append(criteria.getPageSize())
                .append(" OFFSET ").append(criteria.getOffset());
        }
        return pageSql.toString();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {
        String dialectConf = properties.getProperty("dialect");
        if (!(dialectConf == null || dialectConf.trim().isEmpty())) {
            dialect = SqlDialect.fromName(dialectConf);
            if (dialect == null) {
                throw new RuntimeException("Unsupported SQL dialect " + dialectConf);
            }
        }

        String usingPhysicalPaginationConf = properties.getProperty("usingPhysicalPagination");
        if (!(usingPhysicalPaginationConf == null || usingPhysicalPaginationConf.trim().isEmpty())
            && !Boolean.valueOf(usingPhysicalPaginationConf)) {
            usingPhysicalPagination = false;
        }
    }
}
