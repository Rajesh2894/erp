package com.abm.mainet.common.utility;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

import com.abm.mainet.common.constant.MainetConstants;

public class MainetTenantIdentifierResolver implements CurrentTenantIdentifierResolver {

    @Override
    public String resolveCurrentTenantIdentifier() {
        String tenant = MainetTenantUtility.tenantThreadLocal.get();
        if (tenant == null) {
            tenant = MainetConstants.DEFAULT_DATA_SOURCE;
        }
        return tenant;
    }

    @Override
    public boolean validateExistingCurrentSessions() {

        return true;
    }

}
