package com.fishqq.meta.services.impl;

import java.util.List;

import javax.annotation.Resource;

import com.alibaba.dt.dataphin.meta.client.common.PaginatedResult;
import com.alibaba.dt.dataphin.meta.client.common.TableQueryFilter;
import com.alibaba.dt.dataphin.meta.core.bo.TableBO;
import com.alibaba.dt.dataphin.meta.core.bo.datasource.DataSourceBO;
import com.alibaba.dt.dataphin.meta.core.bo.datasource.JdbcDataSourceBO;
import com.alibaba.dt.dataphin.meta.core.bo.datasource.MaxComputeDataSourceBO;
import com.alibaba.dt.dataphin.meta.core.service.metastore.MetastoreTableService;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author 白路 bailu.zjj@alibaba-inc.com
 * @date 2018/10/9
 */
@Service
@Qualifier("merge")
public class MetastoreTableServiceImpl<T extends DataSourceBO> implements MetastoreTableService<T> {
    @Resource
    @Qualifier("jdbc")
    private MetastoreTableService<JdbcDataSourceBO> jdbcTableService;

    @Resource
    @Qualifier("maxCompute")
    private MetastoreTableService<MaxComputeDataSourceBO> maxComputeTableService;

    @Override
    public List<TableBO> listByGuid(Multimap<T, String> tableGuidByDsBO, TableQueryFilter filter) {
        Multimap<JdbcDataSourceBO, String> jdbcDs = ArrayListMultimap.create();
        Multimap<MaxComputeDataSourceBO, String> maxComputeDs = ArrayListMultimap.create();

        tableGuidByDsBO.entries().forEach(entry -> {
            if (entry.getKey() instanceof JdbcDataSourceBO) {
                jdbcDs.put((JdbcDataSourceBO)entry.getKey(), entry.getValue());
            } else if (entry.getKey() instanceof MaxComputeDataSourceBO) {
                maxComputeDs.put((MaxComputeDataSourceBO)entry.getKey(), entry.getValue());
            }
        });

        List<TableBO> result = Lists.newArrayList();

        if (jdbcDs.size() > 0) {
            result.addAll(this.jdbcTableService.listByGuid(jdbcDs, filter));
        }
        if (maxComputeDs.size() > 0) {
            result.addAll(this.maxComputeTableService.listByGuid(maxComputeDs, filter));
        }

        return result;
    }

    @Override
    public PaginatedResult<TableBO> search(DataSourceBO dataSourceBO, String keyword,
                                           TableQueryFilter filter) {
        if (dataSourceBO instanceof JdbcDataSourceBO) {
            return this.jdbcTableService.search((JdbcDataSourceBO)dataSourceBO, keyword, filter);
        } else if (dataSourceBO instanceof MaxComputeDataSourceBO) {
            return this.maxComputeTableService.search((MaxComputeDataSourceBO)dataSourceBO, keyword, filter);
        } else {
            return new PaginatedResult<>(0, Lists.newArrayList());
        }
    }

    @Override
    public Long countSearchedTables(List dataSourceBOs, String keyword, TableQueryFilter filter) {
        List<JdbcDataSourceBO> jdbcDs = Lists.newArrayList();
        List<MaxComputeDataSourceBO> maxComputeDs = Lists.newArrayList();

        dataSourceBOs.stream().forEach(dataSourceBO -> {
            if (dataSourceBO instanceof JdbcDataSourceBO) {
                jdbcDs.add((JdbcDataSourceBO)dataSourceBO);
            } else if (dataSourceBO instanceof MaxComputeDataSourceBO) {
                maxComputeDs.add((MaxComputeDataSourceBO)dataSourceBO);
            }
        });

        long total = 0L;

        if (jdbcDs.size() > 0) {
            total += this.jdbcTableService.countSearchedTables(jdbcDs, keyword, filter);
        }
        if (maxComputeDs.size() > 0) {
            total += this.maxComputeTableService.countSearchedTables(maxComputeDs, keyword, filter);
        }

        return total;
    }

    @Override
    public PaginatedResult<String> searchName(DataSourceBO dataSourceBO, String keyword, TableQueryFilter filter) {
        if (dataSourceBO instanceof JdbcDataSourceBO) {
            return this.jdbcTableService.searchName((JdbcDataSourceBO)dataSourceBO, keyword, filter);
        } else if (dataSourceBO instanceof MaxComputeDataSourceBO) {
            return this.maxComputeTableService.searchName((MaxComputeDataSourceBO)dataSourceBO, keyword, filter);
        } else {
            return new PaginatedResult<>(0, Lists.newArrayList());
        }
    }
}
