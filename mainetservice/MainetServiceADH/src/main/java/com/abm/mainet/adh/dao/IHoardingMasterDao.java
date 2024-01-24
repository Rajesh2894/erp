/**
 * 
 */
package com.abm.mainet.adh.dao;

import java.util.List;

import com.abm.mainet.adh.domain.HoardingMasterEntity;

/**
 * @author Anwarul.Hassan
 *
 * @since 22-Aug-2019
 */
public interface IHoardingMasterDao {
    /**
     * This method will get all HoardingMaster by orgId or hoardingNumber or hoardingType or hoardingStatus or hoardingLocation
     * @param orgId
     * @param hoardingNumber
     * @param hoardingStatus
     * @param hoardingType
     * @param hoardingSubType
     * @param hoardingLocation
     * @return HoardingMasterDto list by passing any of the above parameters
     */
    List<HoardingMasterEntity> searchHoardingMasterData(Long orgId, String hoardingNumber,
            Long hoardingStatus, Long hoardingType, Long hoardingSubType, Long hoardingSubType3, Long hoardingSubType4,
            Long hoardingSubType5, Long hoardingLocation);
}
