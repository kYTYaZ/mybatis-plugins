package com.alibaba.dt.dataphin.meta.server.rpc;

import java.util.List;

import javax.annotation.Resource;

import com.alibaba.dt.dataphin.common.lang.result.ResultDTO;
import com.alibaba.dt.dataphin.meta.client.api.SyncApi;
import com.alibaba.dt.dataphin.meta.client.model.sync.SyncStatData;
import com.alibaba.dt.dataphin.meta.core.bo.SyncTypeEnum;
import com.alibaba.dt.dataphin.meta.core.service.SyncService;
import com.alibaba.dt.dataphin.meta.server.ApiLogger;
import com.alibaba.dt.onedata.rpc.annotation.ServiceProvider;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author 白路 bailu.zjj@alibaba-inc.com
 * @date 2018/10/9
 */
@Service
@Qualifier("rpc")
@ServiceProvider
public class RpcSyncApi extends ApiLogger implements SyncApi {
    @Resource
    private SyncService syncService;

    @Override
    public ResultDTO<SyncStatData> syncTablesByGuid(Long tenantId, List<String> tableGuids) {
        return this.log(() -> this.syncService.syncTables(tenantId, tableGuids),
            "syncTable", tableGuids);
    }

    @Override
    public ResultDTO<SyncStatData> syncTablesByName(Long tenantId, Long dataSourceId, List<String> tableNames) {
        return this.log(() -> this.syncService.syncTables(tenantId, dataSourceId, tableNames),
            "syncTable", tenantId, dataSourceId, tableNames);
    }

    @Override
    public ResultDTO<Boolean> syncTablesInDataSource(Long tenantId, Long dataSourceId) {
        return this.log(() -> this.syncService.syncTablesInDataSourceInBackground(
            tenantId, SyncTypeEnum.SYNC_TABLE_DETAIL, dataSourceId),
            "syncData", tenantId, dataSourceId);
    }

    @Override
    public ResultDTO<Boolean> syncTablesInTenant(Long tenantId) {
        return null;
    }
}
