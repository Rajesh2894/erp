package com.abm.mainet.buildingplan.service;

import java.util.List;
import java.util.Map;

import com.abm.mainet.buildingplan.domain.LicenseGrantedEntity;
import com.abm.mainet.buildingplan.domain.TbLicPerfSiteAffecEntity;
import com.abm.mainet.buildingplan.dto.LicenseApplicationLandAcquisitionDetailDTO;
import com.abm.mainet.buildingplan.dto.LicenseGrantedDTO;
import com.abm.mainet.buildingplan.dto.SiteAffectedDTO;

public interface ISiteAffecService {

	void saveSiteList(List<TbLicPerfSiteAffecEntity> listSiteAffDto);

	List<SiteAffectedDTO> getSiteDetailsByApplicationId(Long applicationId, String flag);

	void saveLicenseData(List<LicenseGrantedEntity> listSiteAffDto);

	List<LicenseGrantedDTO> getLicenseDetailsByApplicationId(Long applicationId);

	Map<String, List<LicenseApplicationLandAcquisitionDetailDTO>> getLandDetailsByAppId(Long applicationId, Long orgId);

	List<SiteAffectedDTO> getApplicationNotingDetail(Long applicationId, Long orgId, Long level);

	List<LicenseGrantedDTO> getApplicationNotingDetailLicense(Long applicationId, Long orgId, Long level);

}
