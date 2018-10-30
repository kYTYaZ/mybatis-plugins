package com.fishqq.meta.services.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;

import com.alibaba.dt.dataphin.common.mybatis.interceptor.pagination.PaginationResult;
import com.alibaba.dt.dataphin.meta.client.common.PaginatedResult;
import com.alibaba.dt.dataphin.meta.client.common.TableQueryFilter;
import com.alibaba.dt.dataphin.meta.core.bo.TableBO;
import com.alibaba.dt.dataphin.meta.core.bo.datasource.JdbcDataSourceBO;
import com.alibaba.dt.dataphin.meta.core.converter.ColumnConverter;
import com.alibaba.dt.dataphin.meta.core.converter.TableConverter;
import com.alibaba.dt.dataphin.meta.core.dal.dataobject.ColumnDO;
import com.alibaba.dt.dataphin.meta.core.dal.dataobject.TableDO;
import com.alibaba.dt.dataphin.meta.core.dal.repository.MetastoreTableRepository;
import com.alibaba.dt.dataphin.meta.core.service.metastore.MetastoreTableService;
import com.alibaba.dt.onedata.metaguid.GuidUtil;

import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author 白路 bailu.zjj@alibaba-inc.com
 * @date 2018/9/7
 */
@Service
@Qualifier("jdbc")
public class JdbcMetastoreTableServiceImpl implements MetastoreTableService<JdbcDataSourceBO> {
    @Resource
    private MetastoreTableRepository tableRepository;

    @Override
    public List<TableBO> listByGuid(Multimap<JdbcDataSourceBO, String> tableGuidByDsBO, TableQueryFilter filter) {
        List<TableBO> bos = Lists.newArrayList();

        // no ext and stat data in meta store
        for (JdbcDataSourceBO dsBO : tableGuidByDsBO.keySet()) {
            List<String> tableNamesInDs = tableGuidByDsBO.get(dsBO).stream()
                .map(GuidUtil::parseTableNameFromTableGuid)
                .collect(Collectors.toList());

            List<TableDO> tableDOS = this.tableRepository.listTable(dsBO, tableNamesInDs);

            List<TableBO> tableBOS = toTableBO(tableDOS.stream(), dsBO, filter);

            bos.addAll(tableBOS);
        }

        return bos;
    }

    @Override
    public PaginatedResult<TableBO> search(JdbcDataSourceBO dataSourceBO,
                                           String keyword,
                                           TableQueryFilter filter) {
        PaginationResult result = this.tableRepository.searchTable(
            dataSourceBO, keyword, filter.isSearchByPrefix(), filter.isNeedBbox(), filter.getPaginationCriteria());

        List<TableBO> bos = toTableBO(result.stream().map(r -> (TableDO) r), dataSourceBO, filter);

        return new PaginatedResult<>(result.getTotalCount(), bos);
    }

    @Override
    public Long countSearchedTables(List<JdbcDataSourceBO> dataSourceBOs,
                                    String keyword,
                                    TableQueryFilter filter) {
        return this.tableRepository.countTable(dataSourceBOs, keyword, filter.isSearchByPrefix(), filter.isNeedBbox());
    }

    @Override
    public PaginatedResult<String> searchName(JdbcDataSourceBO dataSourceBO,
                                              String keyword,
                                              TableQueryFilter filter) {
        PaginationResult result = this.tableRepository.searchTableName(
            dataSourceBO, keyword, filter.isSearchByPrefix(), filter.isNeedBbox(), filter.getPaginationCriteria());

        List<String> names = result.stream().map(a -> (String)a).collect(Collectors.toList());

        return new PaginatedResult<>(result.getTotalCount(), names);
    }

    private List<TableBO> toTableBO(Stream<TableDO> dos, JdbcDataSourceBO dataSourceBO, TableQueryFilter filter) {
        List<TableBO> bos = dos.map(tableDO -> toTableBO(tableDO, dataSourceBO, filter)).collect(Collectors.toList());

        return bos;
    }

    private TableBO toTableBO(TableDO tableDO, JdbcDataSourceBO dataSourceBO, TableQueryFilter filter) {
        TableBO tableBO = TableConverter.toBO(tableDO);

        if (filter.isNeedColumns()) {
            List<ColumnDO> columnDOS = this.tableRepository.listColumn(dataSourceBO, tableBO.getName());
            tableBO.setColumns(ColumnConverter.toBOs(columnDOS.stream(), tableBO.getGuid()));
        }

        return tableBO;
    }
}
