package com.abm.mainet.tradeLicense.service;


import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.tradeLicense.dto.LicenseValidityDto;
import com.abm.mainet.tradeLicense.dto.RenewalHistroyDetails;
import com.abm.mainet.tradeLicense.dto.RenewalMasterDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;



@WebService
public interface IRenewalLicenseApplicationService {
	
	/**
	 * used to Save And Update Renewal License Application Form
	 * @param renewalMasterDetailDTO
	 * @return
	 */
	TradeMasterDetailDTO saveAndUpdateApplication(TradeMasterDetailDTO dto);
	
	
	/**
	 * get Renewal License details by application id
	 * @param applicationId
	 * @return
	 */
	RenewalMasterDetailDTO getRenewalLicenseDetailsByApplicationId(Long applicationId);
	
	/**
     * used to get Word Zone Block By Application Id
     * @param applicationId
     * @param serviceId
     * @param orgId
     * @return
     */
    WardZoneBlockDTO getWordZoneBlockByApplicationId(Long applicationId, Long serviceId, Long orgId);
    
   int validateRenewalLicenseCount(Long trdId, Long orgId);

    
	TradeMasterDetailDTO getTradeLicenceChargesFromBrmsRule(TradeMasterDetailDTO masDto) throws FrameworkException;


	Map<Long, Double> getLoiCharges(Long applicationId, Long serviceId, Long orgId) throws CloneNotSupportedException;


	void updateRenewalFormData(TradeMasterDetailDTO masDto) throws FrameworkException;


	TradeMasterDetailDTO getTradeLicenceChargesAtApplicationFromBrmsRule(TradeMasterDetailDTO masDto) throws FrameworkException;
	
    Long calculateLicMaxTenureDays(Long deptId, Long serviceId, Date licToDate, Long orgId, TradeMasterDetailDTO tradeMasterDto, long renewalPeriod);


	CommonChallanDTO getDepartmentWiseLoiData(Long applicationNo, Long orgId);


	List<RenewalHistroyDetails> getRenewalHistoryDetails(String licNo,Long orgId);


	void sendSmsEmail(TradeMasterDetailDTO masDto);


	Boolean checkLicenseNoExist(String refNo, Long orgId);

	Boolean updateStatusAfterDishonurEntry(Long appId, Long orgId);

	CommonChallanDTO getRenewalLicenseDates(TradeMasterDetailDTO dto);


	String  geneateDemandForLicense(List<TradeMasterDetailDTO> dtoList);


	Map<String,String> getLicenseMaxTenureDays(LicenseValidityDto dto);	
}
