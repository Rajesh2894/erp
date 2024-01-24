package com.abm.mainet.mobile.dao;

import com.abm.mainet.mobile.domain.DeviceRegistrationEntity;

/**
 * @author umashanker.kanaujiya
 *
 */
public interface DeviceRegistrationDAO {

    Boolean doDevRegistrationService(DeviceRegistrationEntity entity);

    /**
     * @param UserId
     * @param OrgId
     * @return
     */
    DeviceRegistrationEntity getDevRegistrationService(Long UserId, Long OrgId);

}
