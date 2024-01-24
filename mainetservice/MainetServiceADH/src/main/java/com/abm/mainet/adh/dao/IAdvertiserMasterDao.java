package com.abm.mainet.adh.dao;

import java.util.List;

import com.abm.mainet.adh.domain.AdvertiserMasterEntity;
import com.abm.mainet.adh.domain.AdvertiserMasterHistoryEntity;

/**
 * @author cherupelli.srikanth
 * @since 03 August 2019
 */

public interface IAdvertiserMasterDao {

    /**
     * 
     * @param orgId
     * @param advertiserNumber
     * @param advertiserOldNumber
     * @param advertiserName
     * @param advertiserStatus
     * @return AdvertiserMasterEntity list based on the above input parameters
     */
    List<AdvertiserMasterEntity> searchAdvertiserData(Long orgId, String advertiserNumber, String advertiserOldNumber,
            String advertiserName, String advertiserStatus);

    // save in TB_ADH_AGENCYMASTER_HIST
    void saveInAdvertiserMasterHistoryEntity(AdvertiserMasterHistoryEntity entity);
}
