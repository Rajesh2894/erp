/**
 * 
 */
package com.abm.mainet.adh.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.adh.dto.NewAdvertisementApplicationDto;

/**
 * @author Anwarul.Hassan
 * @since 11-Oct-2019
 */
public interface IAdvertisementDataEntryService {
    /**
     * This method is used to get Advertisement data
     * @param orgId
     * @return
     */
    List<NewAdvertisementApplicationDto> getAdvertisementDetailsByOrgId(Long orgId);

    /**
     * This method is used to save advertisement data
     * @param applicationDto
     * @return
     */
    NewAdvertisementApplicationDto saveAdvertisementData(NewAdvertisementApplicationDto applicationDto);

    NewAdvertisementApplicationDto findAgencyCategoryByOrgId(Long orgId);

    List<NewAdvertisementApplicationDto> searchAdvtDataEntry(Long orgId, Long agencyId, Long licenseType, String adhStatus,
            Long locId,Date licenFromDate,Date licenToDate);

    NewAdvertisementApplicationDto getAdvertisementDetailsByOrgIdAndAdhId(Long adhId, Long orgId);

    Long getLocationByOrgIdAndLocId(Long orgId, Long locationId);

    void deleteAdvertisementId(List<Long> removeAdverId );
    
    Long calculateLicMaxTenureDays(Long deptId, Long serviceId, Date licToDate, Long orgId, Long licType);
}
