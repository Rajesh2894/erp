package com.abm.mainet.adh.service;

import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import com.abm.mainet.adh.dto.NewAdvertisementApplicationDto;
import com.abm.mainet.adh.dto.NewAdvertisementReqDto;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.utility.LookUp;

/**
 * Object: this web services is used for new Advertisement application data.
 * @author vishwajeet.kumar
 * @since 25 Sept 2019
 * 
 */
@WebService
public interface INewAdvertisementApplicationService {

    /**
     * This method is used for save New Advertisement Application (Both Services And WebServices)
     * @param advertisementReqDto
     * @return
     */
    NewAdvertisementReqDto saveNewAdvertisementApplication(NewAdvertisementReqDto advertisementReqDto);

    /**
     * This method is used for get all data from Advertisement Application By Application Id
     * @param applicationId
     * @param orgId
     * @return
     */
    NewAdvertisementReqDto getNewAdvertisementApplicationByAppId(long applicationId, Long orgId);

    /**
     * This method is used for generate License Number
     * @param date
     * @param orgId
     * @param deptId
     * @return license No
     */
    String generateNewAdvertisementLicenseNumber(Organisation organisation, Long dpDeptid);

    /**
     * get Location By Dept Id And OrgId
     * @param orgId
     * @return
     */
    List<LookUp> getLocationByDeptIdAndOrgId(Long orgId);

    /**
     * get Loi Charges
     * @param applicationId
     * @param serviceId
     * @param orgId
     * @return
     * @throws CloneNotSupportedException
     */
    Map<Long, Double> getLoiCharges(Long applicationId, Long serviceId, Long orgId) throws CloneNotSupportedException;

    /**
     * update Advertisement License No
     * @param applicationId
     * @param advertisementLicenseNo
     * @param orgId
     */
    void updateAdvertisementLicenseNo(Long applicationId, String advertisementLicenseNo, Long orgId);

    /**
     * fetch License Number By Organisation Id
     * @param orgid
     * @return
     */
    List<NewAdvertisementApplicationDto> getLicenseNoByOrgId(Long orgId);

    /**
     * get Advertisement Application By License Number
     * @param licenseNo
     * @param orgId
     * @return NewAdvertisementReqDto
     */
    NewAdvertisementReqDto getAdvertisementApplicationByLicenseNo(String licenseNo, Long orgId);

}
