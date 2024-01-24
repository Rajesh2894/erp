package com.abm.mainet.common.util;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

import com.abm.mainet.common.constant.MainetConstants;

public class MainetTenantIdentifierResolver implements CurrentTenantIdentifierResolver {

    @Override
    public String resolveCurrentTenantIdentifier() {
        String tenant = MainetTenantThreadLocalUtility.tenantThreadLocal.get();
        if (tenant == null) {
            tenant = MainetConstants.PATNA;
        }
        return tenant;
    }

    @Override
    public boolean validateExistingCurrentSessions() {

        return true;
    }

}
