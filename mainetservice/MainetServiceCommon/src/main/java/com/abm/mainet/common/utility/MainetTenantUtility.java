package com.abm.mainet.common.utility;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.abm.mainet.common.constant.MainetConstants;

public final class MainetTenantUtility {

    private static Logger log = LoggerFactory.getLogger(MainetTenantUtility.class);
    private static final Properties properties = new Properties();
    public static final ThreadLocal<String> tenantThreadLocal = new ThreadLocal<>();

    public static boolean setThreadLocal(final Long orgId) {
        log.info("Start to set DataSource Connection with DB based on ULb Selection in setThreadLocal() ");
        try {

            if ((orgId != null) && (orgId != 0)) {
                tenantThreadLocal.set(ApplicationSession.getInstance().getMessage(String.valueOf(orgId)));
            } else {
                tenantThreadLocal.set(MainetConstants.DEFAULT_DATA_SOURCE);
            }

            return true;

        } catch (final Exception exception) {
            log.error("Exception occur in in setThreadLocal() in MainetTenantUtility class ::::::" + exception.getMessage());
            return false;
        }

    }

    /**
     * this method being used to set KDMC marriage database
     *
     * @param key
     * @return
     */
    public static boolean setThreadLocal(final String key) {
        log.info("Start to set DataSource Connection with DB based on ULb Selection in setThreadLocal() ");
        boolean status = false;
        try {

            if ((key != null) && !key.isEmpty()) {
                tenantThreadLocal.set(ApplicationSession.getInstance().getMessage(key));
                status = true;
            }
        } catch (final Exception exception) {
            log.error("Exception occur in in setThreadLocal() in MainetTenantUtility class ::::::"
                    + exception.getMessage());

        }
        return status;
    }

}
