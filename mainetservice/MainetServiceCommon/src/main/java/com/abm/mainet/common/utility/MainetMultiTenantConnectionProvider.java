package com.abm.mainet.common.utility;

import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;

import com.abm.mainet.common.constant.MainetConstants;

public class MainetMultiTenantConnectionProvider extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

    private Map<String, DataSource> dsMap;

    public void setDsMap(final Map<String, DataSource> dsMap) {
        this.dsMap = dsMap;
    }

    private static final long serialVersionUID = -6346329866948428974L;

    @Override
    protected DataSource selectAnyDataSource() {

        return dsMap.get(MainetConstants.DEFAULT_DATA_SOURCE);
    }

    @Override
    protected DataSource selectDataSource(final String tenantId) {

        return dsMap.get(tenantId);
    }

}
