package com.abm.mainet.adh.service;

import java.util.LinkedHashMap;
import java.util.List;

import com.abm.mainet.adh.dto.ADHRequestDto;
import com.abm.mainet.adh.dto.ADHResponseDTO;
import com.abm.mainet.adh.dto.AdvertiserMasterDto;
import com.abm.mainet.adh.dto.NewAdvertisementApplicationDto;
import com.abm.mainet.adh.dto.NewAdvertisementReqDto;
import com.abm.mainet.common.util.LookUp;

/**
 * @author vishwajeet.kumar
 * @since 14 October 2019
 */
public interface INewAdvertisementApplicationService {

    /**
     * This Method is used for get advertiser details from Advertiser Master on the basis of these parameter
     * @param advertiserCategoryId
     * @param orgId
     * @return List of advertiser details
     */
    List<AdvertiserMasterDto> getAdvertiserDetails(Long advertiserCategoryId, Long orgId);

    /**
     * This Method is used for get Location from Location Master
     * @param orgid
     * @return List Of Location
     */
    List<LookUp> geLocationByOrgId(Long orgid);

    /**
     * This Method is used for Save New Advertisement Application
     * @param advertisementReqDto
     * @return Advertisement Data
     */
    NewAdvertisementReqDto saveNewAdvertisementApplication(NewAdvertisementReqDto advertisementReqDto);

    /**
     * This Method is Used For get CheckList Charge Flag And LicMaxDay from License Validity Master
     * @param orgId
     * @param agencyRegShortCode
     * @return LinkedHashMap
     */
    LinkedHashMap<String, Object> getCheckListChargeFlagAndLicMaxDay(Long orgId, String agencyRegShortCode);

    /**
     * This Method is Used For get License number From New Advertisement Application On the Basis of orgId
     * @param orgid
     * @return NewAdvertisementApplicationDto
     */
    List<NewAdvertisementApplicationDto> getLicenseNoByOrgId(Long orgid);

    /**
     * This Method is Used For get Advertisement Application By LicenseNo
     * @param licenseNo
     * @param orgId
     * @return
     */

    NewAdvertisementReqDto getAdvertisementApplicationByLicenseNo(String licenseNo, Long orgId);
    
    //D#79968
    NewAdvertisementReqDto getAdvertisementApplicationByApp(Long applicationId, Long orgId);
    //D#79968
    ADHResponseDTO getADHDataByApplicationId(ADHRequestDto adhRequestDto);
    
    NewAdvertisementApplicationDto getPropertyDetailsByPropertyNumber(NewAdvertisementApplicationDto reqDto);
    
    //Defect #129856
    String getLicMaxTenureDays(Long orgId, String serviceShortCode,Long licType);

}
