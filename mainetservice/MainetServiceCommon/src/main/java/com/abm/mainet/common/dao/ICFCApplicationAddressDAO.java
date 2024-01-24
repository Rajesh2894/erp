package com.abm.mainet.common.dao;

import com.abm.mainet.common.domain.CFCApplicationAddressEntity;

/**
 * @author Rahul.Yadav
 *
 */
public interface ICFCApplicationAddressDAO {

    /**
     * @param loiApplicationId
     * @param orgId
     * @return
     */
    CFCApplicationAddressEntity getApplicationAddressByAppId(
            Long loiApplicationId, Long orgId);

}
