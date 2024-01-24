package com.abm.mainet.common.service;

import com.abm.mainet.common.domain.CFCApplicationAddressEntity;

/**
 * @author Rahul.Yadav
 *
 */
public interface ICFCApplicationAddressService {

    /**
     * @param loiApplicationId
     * @param orgId
     * @return
     */
    CFCApplicationAddressEntity getApplicationAddressByAppId(
            Long loiApplicationId, Long orgId);

}
