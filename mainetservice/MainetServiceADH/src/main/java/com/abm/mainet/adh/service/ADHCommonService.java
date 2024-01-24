package com.abm.mainet.adh.service;

import java.util.Date;

/**
 * @author cherupelli.srikanth
 * @since 30 september 2019
 */
public interface ADHCommonService {
    Long calculateLicMaxTenureDays(Long deptId, Long serviceId, Date licToDate, Long orgId, Long licType);
}
