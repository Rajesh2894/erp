package com.abm.mainet.common.util;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.abm.mainet.common.constant.MainetConstants;

public class MainetTenantThreadLocalUtility {

    private static final String EXCEPTION_OCCUR_SET_THREAD_LOCAL_IN_MAINET_TENANT_UTILITY_CLASS = "Exception occur in in setThreadLocal() in MainetTenantUtility class ::::::";
    private static final String EXCEPTION_IN_LOADING_THE_PROPERTY_FILE_IN_MAINET = "Exception occur in loading the property file  in MainetTenantUtility class :::::: ";
    private static Logger logger = Logger.getLogger(MainetTenantThreadLocalUtility.class);
    private static final Properties properties = new Properties();
    public static final ThreadLocal<String> tenantThreadLocal = new ThreadLocal<>();
    static {
        try {
            properties.load(MainetTenantThreadLocalUtility.class.getClassLoader()
                    .getResourceAsStream("/properties/DataSourceConfiguration.properties"));

        } catch (final IOException ioException) {
            logger.error(EXCEPTION_IN_LOADING_THE_PROPERTY_FILE_IN_MAINET, ioException);
        }
    }

    public static boolean setThreadLocal(final Long orgId) {
        try {

            if ((orgId != null) && (orgId != 0)) {
                tenantThreadLocal.set(properties.getProperty(String.valueOf(orgId)));
            } else {
                tenantThreadLocal.set(MainetConstants.PATNA);
            }

            return true;

        } catch (final Exception exception) {
            logger.error(EXCEPTION_OCCUR_SET_THREAD_LOCAL_IN_MAINET_TENANT_UTILITY_CLASS, exception);
            return false;
        }

    }
}
