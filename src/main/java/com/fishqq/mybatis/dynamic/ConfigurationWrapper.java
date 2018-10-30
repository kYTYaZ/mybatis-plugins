package com.fishqq.mybatis.dynamic;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.builder.CacheRefResolver;
import org.apache.ibatis.builder.ResultMapResolver;
import org.apache.ibatis.builder.annotation.MethodResolver;
import org.apache.ibatis.builder.xml.XMLStatementBuilder;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.loader.ProxyFactory;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.io.VFS;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMap;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.scripting.LanguageDriverRegistry;
import org.apache.ibatis.session.AutoMappingBehavior;
import org.apache.ibatis.session.AutoMappingUnknownColumnBehavior;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.LocalCacheScope;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeAliasRegistry;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

/**
 * @author 白路 bailu.zjj@alibaba-inc.com
 * @date 2018/9/4
 */
public class ConfigurationWrapper extends Configuration {
    private Environment environment;
    private Configuration configuration;

    public ConfigurationWrapper(Configuration configuration, Environment environment) {
        this.environment = environment;
        this.configuration = configuration;
    }

    @Override
    public Environment getEnvironment() {
        return this.environment;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public String getLogPrefix() {
        return this.configuration.getLogPrefix();
    }

    @Override
    public void setLogPrefix(String logPrefix) {
        this.configuration.setLogPrefix(logPrefix);
    }

    @Override
    public Class<? extends Log> getLogImpl() {
        return this.configuration.getLogImpl();
    }

    @Override
    public void setLogImpl(Class<? extends Log> logImpl) {
        this.configuration.setLogImpl(logImpl);
    }

    @Override
    public Class<? extends VFS> getVfsImpl() {
        return this.configuration.getVfsImpl();
    }

    @Override
    public void setVfsImpl(Class<? extends VFS> vfsImpl) {
        this.configuration.setVfsImpl(vfsImpl);
    }

    @Override
    public boolean isCallSettersOnNulls() {
        return this.configuration.isCallSettersOnNulls();
    }

    @Override
    public void setCallSettersOnNulls(boolean callSettersOnNulls) {
        this.configuration.setCallSettersOnNulls(callSettersOnNulls);
    }

    @Override
    public boolean isUseActualParamName() {
        return this.configuration.isUseActualParamName();
    }

    @Override
    public void setUseActualParamName(boolean useActualParamName) {
        this.configuration.setUseActualParamName(useActualParamName);
    }

    @Override
    public boolean isReturnInstanceForEmptyRow() {
        return this.configuration.isReturnInstanceForEmptyRow();
    }

    @Override
    public void setReturnInstanceForEmptyRow(boolean returnEmptyInstance) {
        this.configuration.setReturnInstanceForEmptyRow(returnInstanceForEmptyRow);
    }

    @Override
    public String getDatabaseId() {
        return this.configuration.getDatabaseId();
    }

    @Override
    public void setDatabaseId(String databaseId) {
        this.configuration.setDatabaseId(databaseId);
    }

    @Override
    public Class<?> getConfigurationFactory() {
        return this.configuration.getConfigurationFactory();
    }

    @Override
    public void setConfigurationFactory(Class<?> configurationFactory) {
        this.configuration.setConfigurationFactory(configurationFactory);
    }

    @Override
    public boolean isSafeResultHandlerEnabled() {
        return this.configuration.isSafeResultHandlerEnabled();
    }

    @Override
    public void setSafeResultHandlerEnabled(boolean safeResultHandlerEnabled) {
        this.configuration.setSafeResultHandlerEnabled(safeResultHandlerEnabled);
    }

    @Override
    public boolean isSafeRowBoundsEnabled() {
        return this.configuration.isSafeRowBoundsEnabled();
    }

    @Override
    public void setSafeRowBoundsEnabled(boolean safeRowBoundsEnabled) {
        this.configuration.setSafeRowBoundsEnabled(safeRowBoundsEnabled);
    }

    @Override
    public boolean isMapUnderscoreToCamelCase() {
        return this.configuration.isMapUnderscoreToCamelCase();
    }

    @Override
    public void setMapUnderscoreToCamelCase(boolean mapUnderscoreToCamelCase) {
        this.configuration.setMapUnderscoreToCamelCase(mapUnderscoreToCamelCase);
    }

    @Override
    public void addLoadedResource(String resource) {
        this.configuration.addLoadedResource(resource);
    }

    @Override
    public boolean isResourceLoaded(String resource) {
        return this.configuration.isResourceLoaded(resource);
    }

    @Override
    public AutoMappingBehavior getAutoMappingBehavior() {
        return this.configuration.getAutoMappingBehavior();
    }

    @Override
    public void setAutoMappingBehavior(AutoMappingBehavior autoMappingBehavior) {
        this.configuration.setAutoMappingBehavior(autoMappingBehavior);
    }

    /**
     * @since 3.4.0
     */
    @Override
    public AutoMappingUnknownColumnBehavior getAutoMappingUnknownColumnBehavior() {
        return this.getAutoMappingUnknownColumnBehavior();
    }

    /**
     * @since 3.4.0
     */
    @Override
    public void setAutoMappingUnknownColumnBehavior(AutoMappingUnknownColumnBehavior autoMappingUnknownColumnBehavior) {
        this.configuration.setAutoMappingUnknownColumnBehavior(autoMappingUnknownColumnBehavior);
    }

    @Override
    public boolean isLazyLoadingEnabled() {
        return this.configuration.isLazyLoadingEnabled();
    }

    @Override
    public void setLazyLoadingEnabled(boolean lazyLoadingEnabled) {
        this.configuration.setLazyLoadingEnabled(lazyLoadingEnabled);
    }

    @Override
    public ProxyFactory getProxyFactory() {
        return this.configuration.getProxyFactory();
    }

    @Override
    public void setProxyFactory(ProxyFactory proxyFactory) {
        this.configuration.setProxyFactory(proxyFactory);
    }

    @Override
    public boolean isAggressiveLazyLoading() {
        return this.configuration.isAggressiveLazyLoading();
    }

    @Override
    public void setAggressiveLazyLoading(boolean aggressiveLazyLoading) {
        this.configuration.setAggressiveLazyLoading(aggressiveLazyLoading);
    }

    @Override
    public boolean isMultipleResultSetsEnabled() {
        return this.configuration.isMultipleResultSetsEnabled();
    }

    @Override
    public void setMultipleResultSetsEnabled(boolean multipleResultSetsEnabled) {
        this.configuration.setMultipleResultSetsEnabled(multipleResultSetsEnabled);
    }

    @Override
    public Set<String> getLazyLoadTriggerMethods() {
        return this.configuration.getLazyLoadTriggerMethods();
    }

    @Override
    public void setLazyLoadTriggerMethods(Set<String> lazyLoadTriggerMethods) {
        this.configuration.setLazyLoadTriggerMethods(lazyLoadTriggerMethods);
    }

    @Override
    public boolean isUseGeneratedKeys() {
        return this.configuration.isUseGeneratedKeys();
    }

    @Override
    public void setUseGeneratedKeys(boolean useGeneratedKeys) {
        this.configuration.setUseGeneratedKeys(useGeneratedKeys);
    }

    @Override
    public ExecutorType getDefaultExecutorType() {
        return this.configuration.getDefaultExecutorType();
    }

    @Override
    public void setDefaultExecutorType(ExecutorType defaultExecutorType) {
        this.configuration.setDefaultExecutorType(defaultExecutorType);
    }

    @Override
    public boolean isCacheEnabled() {
        return this.configuration.isCacheEnabled();
    }

    @Override
    public void setCacheEnabled(boolean cacheEnabled) {
        this.configuration.setCacheEnabled(cacheEnabled);
    }

    @Override
    public Integer getDefaultStatementTimeout() {
        return this.configuration.getDefaultStatementTimeout();
    }

    @Override
    public void setDefaultStatementTimeout(Integer defaultStatementTimeout) {
        this.configuration.setDefaultStatementTimeout(defaultStatementTimeout);
    }

    /**
     * @since 3.3.0
     */
    @Override
    public Integer getDefaultFetchSize() {
        return this.configuration.getDefaultFetchSize();
    }

    /**
     * @since 3.3.0
     */
    @Override
    public void setDefaultFetchSize(Integer defaultFetchSize) {
        this.configuration.setDefaultFetchSize(defaultFetchSize);
    }

    @Override
    public boolean isUseColumnLabel() {
        return this.configuration.isUseColumnLabel();
    }

    @Override
    public void setUseColumnLabel(boolean useColumnLabel) {
        this.configuration.setUseColumnLabel(useColumnLabel);
    }

    @Override
    public LocalCacheScope getLocalCacheScope() {
        return this.configuration.getLocalCacheScope();
    }

    @Override
    public void setLocalCacheScope(LocalCacheScope localCacheScope) {
        this.configuration.setLocalCacheScope(localCacheScope);
    }

    @Override
    public JdbcType getJdbcTypeForNull() {
        return this.configuration.getJdbcTypeForNull();
    }

    @Override
    public void setJdbcTypeForNull(JdbcType jdbcTypeForNull) {
        this.configuration.setJdbcTypeForNull(jdbcTypeForNull);
    }

    @Override
    public Properties getVariables() {
        return this.configuration.getVariables();
    }

    @Override
    public void setVariables(Properties variables) {
        this.configuration.setVariables(variables);
    }

    @Override
    public TypeHandlerRegistry getTypeHandlerRegistry() {
        return this.configuration.getTypeHandlerRegistry();
    }

    /**
     * Set a default {@link TypeHandler} class for {@link Enum}.
     * A default {@link TypeHandler} is {@link org.apache.ibatis.type.EnumTypeHandler}.
     *
     * @param typeHandler a type handler class for {@link Enum}
     * @since 3.4.5
     */
    @Override
    public void setDefaultEnumTypeHandler(Class<? extends TypeHandler> typeHandler) {
        this.configuration.setDefaultEnumTypeHandler(typeHandler);
    }

    @Override
    public TypeAliasRegistry getTypeAliasRegistry() {
        return this.configuration.getTypeAliasRegistry();
    }

    /**
     * @since 3.2.2
     */
    @Override
    public MapperRegistry getMapperRegistry() {
        return this.configuration.getMapperRegistry();
    }

    @Override
    public ReflectorFactory getReflectorFactory() {
        return this.configuration.getReflectorFactory();
    }

    @Override
    public void setReflectorFactory(ReflectorFactory reflectorFactory) {
        this.configuration.setReflectorFactory(reflectorFactory);
    }

    @Override
    public ObjectFactory getObjectFactory() {
        return this.configuration.getObjectFactory();
    }

    @Override
    public void setObjectFactory(ObjectFactory objectFactory) {
        this.configuration.setObjectFactory(objectFactory);
    }

    @Override
    public ObjectWrapperFactory getObjectWrapperFactory() {
        return this.configuration.getObjectWrapperFactory();
    }

    @Override
    public void setObjectWrapperFactory(ObjectWrapperFactory objectWrapperFactory) {
        this.configuration.setObjectWrapperFactory(objectWrapperFactory);
    }

    /**
     * @since 3.2.2
     */
    @Override
    public List<Interceptor> getInterceptors() {
        return this.configuration.getInterceptors();
    }

    @Override
    public LanguageDriverRegistry getLanguageRegistry() {
        return this.configuration.getLanguageRegistry();
    }

    @Override
    public void setDefaultScriptingLanguage(Class<?> driver) {
        this.configuration.setDefaultScriptingLanguage(driver);
    }

    @Override
    public LanguageDriver getDefaultScriptingLanguageInstance() {
        return this.configuration.getDefaultScriptingLanguageInstance();
    }

    /**
     * @deprecated Use {@link #getDefaultScriptingLanguageInstance()}
     */
    @Deprecated
    @Override
    public LanguageDriver getDefaultScriptingLanuageInstance() {
        return this.configuration.getDefaultScriptingLanuageInstance();
    }

    @Override
    public MetaObject newMetaObject(Object object) {
        return this.configuration.newMetaObject(object);
    }

    @Override
    public ParameterHandler newParameterHandler(MappedStatement mappedStatement, Object parameterObject,
                                                BoundSql boundSql) {
        return this.configuration.newParameterHandler(mappedStatement, parameterObject, boundSql);
    }

    @Override
    public ResultSetHandler newResultSetHandler(Executor executor, MappedStatement mappedStatement, RowBounds rowBounds,
                                                ParameterHandler parameterHandler,
                                                ResultHandler resultHandler, BoundSql boundSql) {
        return this.configuration.newResultSetHandler(executor, mappedStatement, rowBounds, parameterHandler,
            resultHandler, boundSql);
    }

    @Override
    public StatementHandler newStatementHandler(Executor executor, MappedStatement mappedStatement,
                                                Object parameterObject, RowBounds rowBounds,
                                                ResultHandler resultHandler, BoundSql boundSql) {
        return this.configuration.newStatementHandler(executor, mappedStatement, parameterObject, rowBounds,
            resultHandler, boundSql);
    }

    @Override
    public Executor newExecutor(Transaction transaction) {
        return this.configuration.newExecutor(transaction);
    }

    @Override
    public Executor newExecutor(Transaction transaction, ExecutorType executorType) {
        return this.configuration.newExecutor(transaction, executorType);
    }

    @Override
    public void addKeyGenerator(String id, KeyGenerator keyGenerator) {
        this.configuration.addKeyGenerator(id, keyGenerator);
    }

    @Override
    public Collection<String> getKeyGeneratorNames() {
        return this.configuration.getKeyGeneratorNames();
    }

    @Override
    public Collection<KeyGenerator> getKeyGenerators() {
        return this.configuration.getKeyGenerators();
    }

    @Override
    public KeyGenerator getKeyGenerator(String id) {
        return this.configuration.getKeyGenerator(id);
    }

    @Override
    public boolean hasKeyGenerator(String id) {
        return this.configuration.hasKeyGenerator(id);
    }

    @Override
    public void addCache(Cache cache) {
        this.configuration.addCache(cache);
    }

    @Override
    public Collection<String> getCacheNames() {
        return this.configuration.getCacheNames();
    }

    @Override
    public Collection<Cache> getCaches() {
        return this.configuration.getCaches();
    }

    @Override
    public Cache getCache(String id) {
        return this.configuration.getCache(id);
    }

    @Override
    public boolean hasCache(String id) {
        return this.configuration.hasCache(id);
    }

    @Override
    public void addResultMap(ResultMap rm) {
        this.configuration.addResultMap(rm);
    }

    @Override
    public Collection<String> getResultMapNames() {
        return this.configuration.getResultMapNames();
    }

    @Override
    public Collection<ResultMap> getResultMaps() {
        return this.configuration.getResultMaps();
    }

    @Override
    public ResultMap getResultMap(String id) {
        return this.configuration.getResultMap(id);
    }

    @Override
    public boolean hasResultMap(String id) {
        return this.configuration.hasResultMap(id);
    }

    @Override
    public void addParameterMap(ParameterMap pm) {
        this.configuration.addParameterMap(pm);
    }

    @Override
    public Collection<String> getParameterMapNames() {
        return this.configuration.getParameterMapNames();
    }

    @Override
    public Collection<ParameterMap> getParameterMaps() {
        return this.configuration.getParameterMaps();
    }

    @Override
    public ParameterMap getParameterMap(String id) {
        return this.configuration.getParameterMap(id);
    }

    @Override
    public boolean hasParameterMap(String id) {
        return this.configuration.hasParameterMap(id);
    }

    @Override
    public void addMappedStatement(MappedStatement ms) {
        this.configuration.addMappedStatement(ms);
    }

    @Override
    public Collection<String> getMappedStatementNames() {
        return this.configuration.getMappedStatementNames();
    }

    @Override
    public Collection<MappedStatement> getMappedStatements() {
        return this.configuration.getMappedStatements();
    }

    @Override
    public Collection<XMLStatementBuilder> getIncompleteStatements() {
        return this.configuration.getIncompleteStatements();
    }

    @Override
    public void addIncompleteStatement(XMLStatementBuilder incompleteStatement) {
        this.configuration.addIncompleteStatement(incompleteStatement);
    }

    @Override
    public Collection<CacheRefResolver> getIncompleteCacheRefs() {
        return this.configuration.getIncompleteCacheRefs();
    }

    @Override
    public void addIncompleteCacheRef(CacheRefResolver incompleteCacheRef) {
        this.configuration.addIncompleteCacheRef(incompleteCacheRef);
    }

    @Override
    public Collection<ResultMapResolver> getIncompleteResultMaps() {
        return this.configuration.getIncompleteResultMaps();
    }

    @Override
    public void addIncompleteResultMap(ResultMapResolver resultMapResolver) {
        this.configuration.addIncompleteResultMap(resultMapResolver);
    }

    @Override
    public void addIncompleteMethod(MethodResolver builder) {
        this.configuration.addIncompleteMethod(builder);
    }

    @Override
    public Collection<MethodResolver> getIncompleteMethods() {
        return this.configuration.getIncompleteMethods();
    }

    @Override
    public MappedStatement getMappedStatement(String id) {
        return this.configuration.getMappedStatement(id);
    }

    @Override
    public MappedStatement getMappedStatement(String id, boolean validateIncompleteStatements) {
        return this.configuration.getMappedStatement(id, validateIncompleteStatements);
    }

    @Override
    public Map<String, XNode> getSqlFragments() {
        return this.configuration.getSqlFragments();
    }

    @Override
    public void addInterceptor(Interceptor interceptor) {
        this.configuration.addInterceptor(interceptor);
    }

    @Override
    public void addMappers(String packageName, Class<?> superType) {
        this.configuration.addMappers(packageName, superType);
    }

    @Override
    public void addMappers(String packageName) {
        this.configuration.addMappers(packageName);
    }

    @Override
    public <T> void addMapper(Class<T> type) {
        this.configuration.addMapper(type);
    }

    @Override
    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        return this.configuration.getMapper(type, sqlSession);
    }

    @Override
    public boolean hasMapper(Class<?> type) {
        return this.configuration.hasMapper(type);
    }

    @Override
    public boolean hasStatement(String statementName) {
        return this.configuration.hasStatement(statementName);
    }

    @Override
    public boolean hasStatement(String statementName, boolean validateIncompleteStatements) {
        return this.configuration.hasStatement(statementName, validateIncompleteStatements);
    }

    @Override
    public void addCacheRef(String namespace, String referencedNamespace) {
        this.configuration.addCacheRef(namespace, referencedNamespace);
    }
}
