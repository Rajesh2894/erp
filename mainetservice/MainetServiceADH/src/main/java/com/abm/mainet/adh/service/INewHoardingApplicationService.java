/**
 * 
 */
package com.abm.mainet.adh.service;

import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import com.abm.mainet.adh.dto.HoardingBookingDto;
import com.abm.mainet.adh.dto.HoardingMasterDto;
import com.abm.mainet.adh.dto.NewAdvertisementReqDto;

/**
 * @author anwarul.hassan
 * @since 17-Oct-2019
 */
@WebService
public interface INewHoardingApplicationService {
    /**
     * This method is used to get the list of hoarding numbers by ogId
     * @param orgId
     * @return List of hoarding numbers
     */
    List<String> getHoardingNumberListByOrgId(Long orgId);

    /**
     * This method is used to get hoarding master details by hoarding number and orgId
     * 
     * @param agencyLicNo
     * @param orgId
     * @return Harding Master Dto
     */
    HoardingMasterDto getHoardingDetailsByHoardingNumberAndOrgId(String hoardingNumber, Long orgId);

    Map<Long, Double> getLoiCharges(Long applicationId, Long serviceId, Long orgId) throws CloneNotSupportedException;

    NewAdvertisementReqDto saveNewHoardingApplication(NewAdvertisementReqDto advertisementReqDto);

    List<HoardingMasterDto> getHoardingMasterListByOrgId(Long orgId);

    List<Object[]> getHoardingNumberAndIdListByOrgId(Long orgId);

    HoardingMasterDto getByOrgIdAndHoardingId(Long orgId, Long hoardingId);

    NewAdvertisementReqDto getDataForHoardingApplication(Long applicationId, Long orgId);
    
    List<HoardingBookingDto> getHoardingDetailsByOrgId(Long orgId);
}
