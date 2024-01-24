package com.abm.mainet.water.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.abm.mainet.cfc.scrutiny.dto.ScrutinyLableValueDTO;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.GridSearchDTO;
import com.abm.mainet.common.dto.PagingDTO;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.integration.dto.PlumberHoleManDTO;
import com.abm.mainet.common.integration.dto.PlumberMasterDTO;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.integration.property.dto.PropertyDetailDto;
import com.abm.mainet.water.domain.CsmrInfoHistEntity;
import com.abm.mainet.water.domain.PlumberMaster;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.domain.TbWtCsmrRoadTypes;
import com.abm.mainet.water.dto.MeterDetailsEntryDTO;
import com.abm.mainet.water.dto.NewWaterConnectionReqDTO;
import com.abm.mainet.water.dto.NewWaterConnectionResponseDTO;
import com.abm.mainet.water.dto.PlumberDTO;
import com.abm.mainet.water.dto.ProvisionalCertificateDTO;
import com.abm.mainet.water.dto.RoadTypeDTO;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.dto.ViewWaterDetailRequestDto;
import com.abm.mainet.water.dto.WaterBillPrintingDTO;
import com.abm.mainet.water.dto.WaterDashboardDTO;
import com.abm.mainet.water.dto.WaterDataEntrySearchDTO;
import com.abm.mainet.water.rest.dto.KDMCWaterDetailsResponseDTO;
import com.abm.mainet.water.rest.dto.ViewCsmrConnectionDTO;

/**
 * @author deepika.pimpale
 *
 */
@WebService
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces(MediaType.APPLICATION_JSON)
public interface NewWaterConnectionService {

	/**
	 * @param requestDTO
	 * @return
	 */
	NewWaterConnectionResponseDTO saveWaterApplication(NewWaterConnectionReqDTO requestDTO);

	/**
	 * @param plumId
	 * @return
	 */
	PlumberMaster getPlumberByPlumberNo(String plumId);

	/**
	 * @param instId
	 * @param orgid
	 * @return
	 */
	Long getAvgConsumptionById(Long instId, long orgid);

	/**
	 * @param rcLength
	 * @param organisation
	 * @return
	 */
	Double getSlopeValueByRoadLength(Long rcLength, Organisation organisation);

	/**
	 * @param diameter
	 * @param organisation
	 * @return
	 */
	Double getDiameterSlab(Double diameter, Organisation organisation);

	/**
	 * @param applicationId
	 * @param serviceId
	 * @param orgId
	 * @return
	 */
	Map<Long, Double> getLoiCharges(Long applicationId, Long serviceId, Long orgId) throws CloneNotSupportedException;

	/**
	 * @param applicationId
	 * @param serviceId
	 * @param orgId
	 * @return
	 */
	WardZoneBlockDTO getWordZoneBlockByApplicationId(Long applicationId, Long serviceId, Long orgId);

	/**
	 * @param master
	 * @return
	 */
	List<TbWtCsmrRoadTypes> getRoadListById(TbKCsmrInfoMH master);

	List<RoadTypeDTO> getReconnectionRoadDiggingListByAppId(Long appId, Long orgId);

	void saveReconnectionRoadDiggingDetails(List<TbWtCsmrRoadTypes> csmrRoadTypesEntity,
			ScrutinyLableValueDTO lableValueDTO);

	/**
	 * @param entity
	 * @param billfrequency
	 * @param contype
	 * @param finYearId
	 * @return
	 */
	List<TbCsmrInfoDTO> getwaterRecordsForBill(TbCsmrInfoDTO entity, String contype, Long billfrequency,
			String finYearId);

	/**
	 * @param requestDTO
	 * @return
	 */
	NewWaterConnectionReqDTO getViewDetailsData(NewWaterConnectionReqDTO requestDTO);

	/**
	 * @param entity
	 * @param conType
	 * @param createGridSearchDTO
	 * @param createPagingDTO
	 * @return
	 */
	List<WaterBillPrintingDTO> getBillPrintingSearchDetail(TbCsmrInfoDTO entity, String conType);

	String setConnectionNoDetails(long applicationid, long orgId, long serviceId, long employee);

	List<TbBillMas> fetchBillsForDistributionEntry(WardZoneBlockDTO entity, String billCcnType, Long distriutionType,
			String connectionNo, long orgId);

	/**
	 * @param applicationId
	 * @param serviceId
	 * @param orgId
	 * @return
	 */
	/**
	 * method signature change by @Sadik.shaikh to generate the customize number
	 */
	String generateWaterConnectionNumber(Long applicationId, Long serviceId, Long orgId, TbKCsmrInfoMH master);

	void saveWaterDataEntry(NewWaterConnectionReqDTO newWaterConnectionReqDTO,
			MeterDetailsEntryDTO meterDetailsEntryDTO, List<TbBillMas> list);

	void updateWaterDataEntry(NewWaterConnectionReqDTO newWaterConnectionReqDTO,
			MeterDetailsEntryDTO meterDetailsEntryDTO, List<TbBillMas> list);

	NewWaterConnectionResponseDTO saveNewWaterApplication(NewWaterConnectionReqDTO requestDTO);

	void initiateWorkFlowForFreeService(NewWaterConnectionReqDTO requestDTO);

	List<TbCsmrInfoDTO> getAllConnectionMasterForBillScheduler(long orgid);

	void findNoOfDaysCalculation(TbCsmrInfoDTO csmrDto, Organisation organisation);

	TbCsmrInfoDTO getPropertyDetailsByPropertyNumber(NewWaterConnectionReqDTO requestDTO);

	int getTotalSearchCount(WaterDataEntrySearchDTO searchDTO, PagingDTO pagingDTO, GridSearchDTO gridSearchDTO);

	List<WaterDataEntrySearchDTO> searchConnectionDetails(WaterDataEntrySearchDTO searchDTO, PagingDTO pagingDTO,
			GridSearchDTO gridSearchDTO, Long serviceId);

	TbCsmrInfoDTO getConnectionDetailsById(Long csIdn);

	List<PlumberDTO> listofplumber(Long orgid);

	List<WaterDashboardDTO> getAllConnectionByMobileNo(String mobileNo, Long orgid);

	TbCsmrInfoDTO fetchConnectionDetailsByConnNo(String csCcn, Long orgId);

	/**
	 * save Or Update Illegal Connection Notice
	 * 
	 * @param infoDto
	 */
	void saveIllegalConnectionNoticeGeneration(TbCsmrInfoDTO infoDto);

	/**
	 * generate Illegal Connection Notice Number
	 * 
	 * @param orgId
	 * @return String
	 */
	String generateIllegalConnectionNoticeNumber(Long orgId);

	/**
	 * get All Illegal Connection Notices
	 * 
	 * @param orgId
	 * @return
	 */
	List<TbCsmrInfoDTO> getAllIllegalConnectionNotice(WaterDataEntrySearchDTO searchDto);

	/**
	 * Fetch Connection By Illegal Notice No
	 * 
	 * @param csCcn
	 * @param orgId
	 * @return TbCsmrInfoDTO
	 */
	TbCsmrInfoDTO fetchConnectionByIllegalNoticeNo(TbCsmrInfoDTO infoDto);

	/**
	 * @param requestDTO
	 * @return
	 */
	NewWaterConnectionResponseDTO saveIllegalToLegalConnectionApplication(NewWaterConnectionReqDTO requestDTO);

	/**
	 * To get valid connection number
	 * 
	 * @param csCcn
	 * @param orgId
	 * @return
	 */
	Long checkValidConnectionNumber(String csCcn, Long orgId);

	/**
	 * Get entry flag 'D' or 'S'
	 * 
	 * @param csIdn
	 * @param orgId
	 * @return
	 */
	String checkEntryFlag(Long csIdn, Long orgId);
	
	public ViewCsmrConnectionDTO viewConnectionDetailsByconnNo(String cnnoNo,Long orgId);
	
	Long getPlumIdByApplicationId(Long applicationId, Long orgId);
	
	boolean saveServiceWiseAdvancePayment(Map<Long, Double> chargeDesc, Long applicationNo, Long receiptid,
			Long taxId, Long deptId,Long userId, Long orgId);
	
	List<WaterDataEntrySearchDTO> searchWaterDetailsByRequest(ViewWaterDetailRequestDto viewWaterDet);
	
	double getTotalOutstandingOfConnNosAssocWithPropNo(String propNo);
	
	CommonChallanDTO getDepartmentWiseLoiData(Long applicationNo,Long orgId);
	
	void updateMobileNumberOfConMaster(Long csIdn,String mobNo,Long orgId,String emailId);
	
	List<WaterDataEntrySearchDTO> searchConnectionDetailsForMobileOrPortal(WaterDataEntrySearchDTO searchDTO);	
	
	PropertyDetailDto getTotalOutStandingOfPropertyNumber(Long appNo,Long orgId);

	TbCsmrInfoDTO getPropertyDetailsByPropertyNumberNFlatNo(NewWaterConnectionReqDTO requestDTO);

	double getTotalOutstanding(String propNo);

	HashMap<String, Double> getDischargePrefixMap(String prefixStr, Long orgId);

	Double getDischargeRateForDomestic(TbCsmrInfoDTO master, HashMap<String, Double> dischargePrefixMap);

	Double getDischargeRateForCommercial(TbCsmrInfoDTO master, HashMap<String, Double> dischargePrefixMap);

	Double getSlopeValueByRoadLengthAndOrg(Long rcLength, Organisation org);

	Double getConnectionSizeByDFactor(Long d_Factor, Organisation org);

	CsmrInfoHistEntity getCsmrHistByCsIdAndOrgId(Long csIdn, Long orgId);

	public List<PlumberMasterDTO> getPlumberList(Long applicationId, Long orgId);

	public void updatePlumberHoleManInfoInCsmr(PlumberHoleManDTO plumberHoleManDTO, Long applicationId, Long orgId);

	public TbKCsmrInfoMH getActiveConnectionByCsCcnAndOrgId(String csCcn, Long orgId, String activeFlag);
	
	public KDMCWaterDetailsResponseDTO getTotalWaterOutstandingAmount(String connectionNo, Long orgId);

	TbKCsmrInfoMH getConnectionByCsCcnAndOrgId(String csCcn, Long orgId);

	ProvisionalCertificateDTO getProvisionalCertificateData(Long applicationNo, Long orgId);

	Map<String, Long> getMeterData(Long applicationId);

	Long getCsIdnByConnectionNo(String connectionNo, Long orgId);

}
