/**
 * 
 */
package com.abm.mainet.adh.service;

import java.util.List;

import com.abm.mainet.adh.dto.AdvertiserMasterDto;
import com.abm.mainet.adh.dto.InspectionEntryDto;
import com.abm.mainet.adh.dto.NewAdvertisementApplicationDto;

/**
 * @author Anwarul.Hassan
 * @since 23-Oct-2019
 */
public interface IInspectionEntryService {

    List<String> getLicenseNoByOrgId(Long orgId);

    NewAdvertisementApplicationDto getAdvertisementDetails(String licenseNo, Long orgId);

    AdvertiserMasterDto getAgencyByAgencyLicNoAndOrgId(String agencyLicNo, Long orgId);

    InspectionEntryDto saveInspectionEntryData(InspectionEntryDto inspectionEntryDto);
}
