package com.abm.mainet.adh.service;

import java.util.List;

import com.abm.mainet.adh.dto.HoardingBookingDto;
import com.abm.mainet.adh.dto.HoardingMasterDto;

/**
 * 
 * @author Anwarul.Hassan
 *
 * @since 16-Aug-2019
 */
public interface IHoardingMasterService {

    /**
     * This method will save form content in the database
     * 
     * @param HoardingMasterDto
     */
    HoardingMasterDto saveHoardingMasterData(HoardingMasterDto masterDto);

    /**
     * This method will update form content in the database
     * 
     * @param HoardingMasterDto
     */
    HoardingMasterDto updateHoardingMasterData(HoardingMasterDto masterDto);

    /**
     * This method will get all HoardingMaster by orgId or hoardingNumber or hoardingType or hoardingStatus or hoardingLocation
     * 
     * @param orgId
     * @param hoardingNumber
     * @param hoardingType
     * @param hoardingStatus
     * @param hoardingLocation
     * @return HoardingMasterDto list by passing any of the above parameters
     */
    List<HoardingMasterDto> searchHoardingMasterData(Long orgId, String hoardingNumber, Long hoardingStatus,
            Long hoardingType, Long hoardingSubType, Long hoardingSubType3, Long hoardingSubType4,
            Long hoardingSubType5, Long hoardingLocation);

    /**
     * @param orgId
     * @param hoardingId
     * @return
     */
    HoardingMasterDto getByOrgIdAndHoardingId(Long orgId, Long hoardingId);

    List<HoardingMasterDto> getByOrgIdAndHoardingIdList(Long orgId, Long hoardingId);

    List<HoardingMasterDto> getAllHoardingNumberByOrgId(Long orgId);

    HoardingMasterDto getHoardingDetailsByOrgIdAndHoardingNo(Long orgId, String hoardingNumber);

    /**
     * This method is used to get the list of hoarding numbers by ogId
     * 
     * @param orgId
     * @return List of hoarding numbers
     */
    List<String> getHoardingNumberListByOrgId(Long orgId);

    List<String[]> getHoardingNumberAndIdListByOrgId(Long orgId);
    
    List<HoardingBookingDto> getHoardingBookingDetails(Long adhId,Long orgId);

    List<HoardingBookingDto> getHoardingDetailsByOrgId(Long orgId);
}
