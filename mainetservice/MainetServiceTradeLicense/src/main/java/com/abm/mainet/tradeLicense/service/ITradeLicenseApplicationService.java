package com.abm.mainet.tradeLicense.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.springframework.beans.BeansException;

import com.abm.mainet.cfc.loi.dto.LoiPaymentSearchDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.tradeLicense.domain.TbMlTradeMast;
import com.abm.mainet.tradeLicense.dto.TradeDataEntyDto;
import com.abm.mainet.tradeLicense.dto.TradeLicenseItemDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeLicenseOwnerDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;

@WebService
public interface ITradeLicenseApplicationService {

	/**
	 * used to Save And Update Trade Master Application Form
	 * 
	 * @param tradeMasterDto
	 * @return
	 */
	TradeMasterDetailDTO saveAndUpdateApplication(TradeMasterDetailDTO tradeMasterDto);

	/**
	 * used to get Trade License By ApplicationId(all child details)
	 * 
	 * @param applicationId
	 * @return
	 */
	TradeMasterDetailDTO getTradeLicenseWithAllDetailsByApplicationId(Long applicationId);

	/**
	 * used to get Trade License By id
	 * 
	 * @param id
	 * @return
	 */
	TradeMasterDetailDTO getTradeLicenseById(Long id);

	/**
	 * used to save Trade Application Data Suite (without application id)
	 * 
	 * @param tradeMasterDto
	 * @return
	 */
	TradeMasterDetailDTO saveTradeApplicationDataSuite(TradeMasterDetailDTO tradeMasterDto);

	/**
	 * used to get trade market charges( from BRMS Rule)
	 * 
	 * @param masDtoQ
	 * @return
	 */
	TradeMasterDetailDTO getTradeLicenceChargesFromBrmsRule(TradeMasterDetailDTO masDto) throws FrameworkException;

	/**
	 * used to search License list
	 * 
	 * @param oldLicenseNo
	 * @param businessName
	 * @param ward1
	 * @param ward2
	 * @param ward3
	 * @param ward4
	 * @param ward5
	 * @param orgId
	 * @return
	 */
	List<TradeDataEntyDto> getFilteredNewTradeLicenceList(Long licenseType, String oldLicenseNo, String newLicenseNo,
			Long ward1, Long ward2, Long ward3, Long ward4, Long ward5, Long orgId, String busName, String ownerName);

	/**
	 * used to get property details by property no
	 * 
	 * @param tradeMasterDTO
	 * @return
	 */
	TradeMasterDetailDTO getPropertyDetailsByPropertyNumber(TradeMasterDetailDTO tradeMasterDTO);

	/**
	 * To Get Trade License By Old Liscense No
	 * 
	 * @param oldLicenseNo
	 * @param orgId
	 * @return TradeMasterDetailDTO
	 */
	TradeMasterDetailDTO getTradeLicenseByOldLiscenseNo(String oldLicenseNo, Long orgId);

	/**
	 * used to get Word Zone Block By Application Id
	 * 
	 * @param applicationId
	 * @param serviceId
	 * @param orgId
	 * @return
	 */
	WardZoneBlockDTO getWordZoneBlockByApplicationId(Long applicationId, Long serviceId, Long orgId);

	/**
	 * get Loi Charges
	 * 
	 * @param applicationId
	 * @param serviceId
	 * @param orgId
	 * @return
	 * @throws CloneNotSupportedException
	 */
	Map<Long, Double> getLoiCharges(Long applicationId, Long serviceId, Long orgId) throws CloneNotSupportedException;

	/**
	 * used to get Active ApplicationId by orgid
	 * 
	 * @param orgid
	 * @return
	 */

	List<TradeMasterDetailDTO> getActiveApplicationIdByOrgId(Long orgid);

	/**
	 * get Payment Mode from LOI Number
	 * 
	 * @param orgId
	 * @param loiNo
	 * @return
	 */

	List<TradeMasterDetailDTO> getpaymentMode(Long orgId, String loiNo);

	/**
	 * Update Trade Status Flag,License From and To date
	 * 
	 * @param apmApplicationId
	 * @param orgId
	 * @param flag
	 * @param toDate
	 * @param lgIpMacUpd
	 */

	void updateTradeLicenseStatusFlag(TradeMasterDetailDTO tradeMasterDto, Long orgId, Long flag, Date toDate,
			String lgIpMacUpd);

	/**
	 * getLicense details from license no
	 * 
	 * @param trdLicno
	 * @return
	 */
	TradeMasterDetailDTO getLicenseDetailsByLicenseNo(String trdLicno, Long orgId);

	/**
	 * get Trade Details By Trade Id
	 * 
	 * @param trdId
	 * @return
	 */
	TradeMasterDetailDTO getTradeDetailsByTrdId(Long trdId, Long orgId);

	boolean validateDataEntrySuite(Long trdId, Long orgId);

	/**
	 * get Item Details By tri_status
	 * 
	 * @param trdId
	 * @param orgId
	 * @return
	 */

	List<TradeLicenseItemDetailDTO> getItemDetailsByTriStatusAndTrdId(Long trdId, Long orgId);

	TradeMasterDetailDTO getTradeLicenceAppChargesFromBrmsRule(TradeMasterDetailDTO masDto) throws FrameworkException;

	void updateTradeLicenseStatus(TradeMasterDetailDTO tradeMasterDto, Long orgId, Long flag, String lgIpMacUpd);

	Long calculateLicMaxTenureDays(Long deptId, Long serviceId, Date licToDate, Long orgId,
			TradeMasterDetailDTO tradeMasterDto);

	Map<String, String> getServiceMasterByServiceCode(Long orgId, String serviceShortName);

	Boolean checkWoflowDefinedOrNot(TradeMasterDetailDTO mastDto);

	List<TradeLicenseItemDetailDTO> getTradeLicenseHistDetBuTrdId(Long triId);

	void saveTradeLicensePrintingData(TbMlTradeMast masEntity);

	String getApprovedBuisnessName(String licenseNo, long orgId, Long lookUpId);

	String checkEnviorement();

	Map<String, Long> getApplicationNumberByRefNo(String licenseNo, Long serviceId, Long orgId, Long empId);

	boolean checkLicensePresent(String referenceNo, Long orgId);


	Map<String, Long> getItemDetailsByApplicationId(Long applicationId);


	List<TradeMasterDetailDTO> getLicenseDetByCatAndDate(Long triCod1, Long triCod2, Date fromDate, Date toDate, Long orgId);

	Boolean checkRejectedInTaskflow(Long applicationId,Long orgId);
	public CommonChallanDTO getLicenseDetailsByAppIdAndOrgId(Long applicationId, Long orgId);

	String getLicenseDetailsByApplId(Long applicationId);

	String getOwnersName(TradeMasterDetailDTO tradeMasterDto, String flagy);

	CommonChallanDTO getLicenseDetails(Long applicationId, Long orgId);

	List<TradeLicenseOwnerDetailDTO> getOwnerListByLicNoAndOrgId(String LicNo, Long orgId);

	CommonChallanDTO getFeesId(TradeMasterDetailDTO tradeMaster);

	void sendSmsEmail(TradeMasterDetailDTO masDto);

	List<String> getPropertyFlatNo(String propNo, Long orgId);

	List<TradeMasterDetailDTO> getpaymentModeByRcptId(Long orgId, long loiNo);

	Boolean checkAccountActiveOrNot();
	
	 Boolean updateStatusAfterDishonurEntry(Long appId,Long orgId);

	void setApplTimeDishonurChargeAmt(TradeMasterDetailDTO trdDto, String servShortCode);

	boolean generateCertificate(String srcPath, Long applicationNo, String certificateNo, Long orgId,TradeMasterDetailDTO tradeMasterDetailDTO);

	List<TradeMasterDetailDTO> getLicenseDetailsBySourceAndOrgId(TradeMasterDetailDTO dto);

	Double getParentTaxValue(TradeMasterDetailDTO masDto);

	List<TradeMasterDetailDTO> fetchAllLicenseDemand(Long orgId, String licNo, Long long1, Long long2, Long long3, Long long4, Long long5);

	TradeMasterDetailDTO getLicenseDetailsByLicenseNoMobileApp(String trdLicno, Long orgId);

	CommonChallanDTO createPushToPayApiRequest(CommonChallanDTO offlineDTO, Long empId, Long orgId,
			String renewalServiceShortCode, String amount) throws BeansException, IOException, InterruptedException;

	List<TradeMasterDetailDTO> getpaymentModeByAppId(Long orgId, Long applicationId);

	List<TradeMasterDetailDTO> getLicenseDetailbyLicAndMobile(String trdLicno, String mobileNo, Long orgId);
	boolean updateStatusFlagByRefId(String refId, Long orgId, Long updateBy);

	Map<String, String> getApplicationDetail(Long applicationId);
}
