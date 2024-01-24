package com.abm.mainet.tradeLicense.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.tradeLicense.dto.TradeLicenseOwnerDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;

public interface ITradeLicenseApplicationService {

	/**
	 * used to Save And Update Trade Master Application Form
	 * 
	 * @param tradeMasterDto
	 * @return
	 */
	TradeMasterDetailDTO saveAndUpdateApplication(TradeMasterDetailDTO tradeMasterDto);

	/**
	 * used to Get Property Details by Property Number
	 * 
	 * @param tradeMasterDTO
	 * @return
	 */

	TradeMasterDetailDTO getPropertyDetailsByPropertyNumber(TradeMasterDetailDTO tradeMasterDTO);

	/**
	 * Used to Get Charges from BRMS Rule
	 * 
	 * @param masDto
	 * @return
	 */

	TradeMasterDetailDTO getTradeLicenceChargesFromBrmsRule(TradeMasterDetailDTO masDto);

	/**
	 * Used to Get Application Charges from BRMS Rule
	 * 
	 * @param masDto
	 * @return
	 */

	TradeMasterDetailDTO getTradeLicenceApplicationChargesFromBrmsRule(TradeMasterDetailDTO masDto);

	/**
	 * getLicense details from license no
	 * 
	 * @param trdLicno
	 * @return
	 */
	TradeMasterDetailDTO getLicenseDetailsByLicenseNo(String trdLicno, Long orgId);

	/**
	 * fetch license details by application id
	 * 
	 * @param applicationId
	 * @return
	 */

	TradeMasterDetailDTO getTradeLicenseWithAllDetailsByApplicationId(Long applicationId);
	
	
	LinkedHashMap<String, Object> getCheckListChargeFlagAndLicMaxDay(Long orgId, String agencyRegShortCode);

	Boolean resolveServiceWorkflowType(TradeMasterDetailDTO tradeDTO);

	Boolean checkTaskRejectedOrNot(Long applicationId,Long orgId);

	List<TradeLicenseOwnerDetailDTO> getOwnerList(String licNo, Long orgId);

	CommonChallanDTO getFeesId(TradeMasterDetailDTO masDto);

	boolean isKDMCEnvPresent();

	List<String> getPropertyFlatNo(String propNo, Long orgId);


	List<TradeMasterDetailDTO> getLicenseDetails(TradeMasterDetailDTO dto);

	void aapaleSarakarPortalEntry(TradeMasterDetailDTO dto);

}
