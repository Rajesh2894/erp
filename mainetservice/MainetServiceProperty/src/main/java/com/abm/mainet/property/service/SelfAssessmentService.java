package com.abm.mainet.property.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.BillReceiptPostingDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.GridSearchDTO;
import com.abm.mainet.common.dto.PagingDTO;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.property.domain.MainBillMasEntity;
import com.abm.mainet.property.domain.ProvisionalBillMasEntity;
import com.abm.mainet.property.dto.BillDisplayDto;
import com.abm.mainet.property.dto.BillPresentAndCalculationDto;
import com.abm.mainet.property.dto.PropertyCommonInfoDto;
import com.abm.mainet.property.dto.ProperySearchDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentFactorDtlDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.SelfAssessmentSaveDTO;

public interface SelfAssessmentService {

    List<DocumentDetailsVO> fetchCheckList(ProvisionalAssesmentMstDto dto, List<ProvisionalAssesmentFactorDtlDto> factDto);

    List<LookUp> getAllBillscheduleWithoutCurrentScheduleByOrgid(Organisation orgid);

    List<LookUp> getAllBillschedulByOrgid(Organisation orgid);

    void setFactorMappingToAssDto(List<ProvisionalAssesmentFactorDtlDto> provFactList, ProvisionalAssesmentMstDto dto);

    void saveDemandLevelRebate(ProvisionalAssesmentMstDto provAsseMstDto, Long empId, Long deptId, List<TbBillMas> billMasList,
            List<BillReceiptPostingDTO> rebateRecDto, Organisation organisation, List<ProvisionalBillMasEntity> provBillList);

    /*
     * Map<String, List<BillDisplayDto>> getDisplayMapOfTaxes(List<TbBillMas> billMasList, Organisation organisation, Long deptId,
     * BillDisplayDto rebateDto, Map<Long, BillDisplayDto> taxWiseRebate, String propNo);
     */

    List<CFCAttachment> preparePreviewOfFileUpload(List<CFCAttachment> downloadDocs, List<DocumentDetailsVO> docs);

    List<LookUp> getAllBillscheduleByOrgid(Organisation orgid);

    boolean CheckForAssesmentFieldForCurrYear(Long orgId, String assNo, String assOldpropno, String status, Long finYearId,
            Long serviceId);

    List<LookUp> findDistrictByLandType(String landType);

    List<LookUp> getTehsilListByDistrict(String districtId, String landType);

    List<LookUp> getVillageListByTehsil(String tehsilId, String districtId, String landType);

    List<LookUp> getMohallaListByVillageId(String villageId, String tehsilId, String districtId, String landType);

    List<LookUp> getStreetListByMohallaId(String mohallaId, String villageId, String tehsilId, String districtId,
            String landType);

    String getRiDetailsByDistrictTehsilVillageID(String landType, String districtId, String tehsilId, String villageId);

    String getRecordDetailsByDistrictTehsilVillageMohallaStreetID(String districtId, String tehsilId, String villageId,
            String mohallaId, String streetNo);

    List<AttachDocs> preparePreviewOfFileUpload(List<DocumentDetailsVO> docs);

    List<PropertyCommonInfoDto> searchPropertyDetails(ProperySearchDto searchDto, PagingDTO pagingDTO,
            GridSearchDTO gridSearchDTO, Long serviceId, List<LookUp> locationList);

    Map<String, List<BillDisplayDto>> getDisplayMapOfTaxes(List<TbBillMas> billMasList, Organisation organisation, Long deptId,
    		List<BillDisplayDto> rebateDtoList, Map<Long, BillDisplayDto> taxWiseRebate, BillDisplayDto surCharge);

	List<BillReceiptPostingDTO> knowkOffAdvanceAmt(List<TbBillMas> billMasList, BillDisplayDto billDisplayDto,
			Organisation org, Date manualReceiptDate, String propNo);

    Map<String, List<BillDisplayDto>> getDisplayMapOfTaxes(List<TbBillMas> billMasList, Organisation organisation, Long deptId,
            Map<Long, BillDisplayDto> taxWiseRebate, List<BillDisplayDto> otherTaxesDisDtoList);

    List<Long> saveSelfAssessmentWithEdit(ProvisionalAssesmentMstDto provAsseMstDto, Long orgId, Long empId, Long deptId,
            int langId, List<Long> finYearList, List<TbBillMas> billMasList, List<BillReceiptPostingDTO> rebateRecDto);

    List<Long> saveSelfAssessment(ProvisionalAssesmentMstDto provAsseMstDto, CommonChallanDTO offline, List<Long> finYearList,
            List<TbBillMas> billMasList, List<BillReceiptPostingDTO> rebateRecDto, BillDisplayDto advanceAmt);

    void saveAdvanceAmt(ProvisionalAssesmentMstDto provAsseMstDto, Long empId, Long deptId, BillDisplayDto advanceAmt,
            Organisation organisation, List<TbBillMas> billMasList, String ipAddress, List<ProvisionalBillMasEntity> provBillList,
            List<BillReceiptPostingDTO> billRecePstingDto, double ajustedAmt,String flatNo,int langId);

    BillDisplayDto getAjustedAdvanceAmt(Organisation org, Long applicationNo, Long deptId);

    void savePureAdvancePayment(ProvisionalAssesmentMstDto provAsseMstDto, Long empId, Long deptId, Organisation org,
            String ipAddress, Double advAmt);

    List<BillReceiptPostingDTO> knowkOffDemandLevelRebateAndExemption(List<TbBillMas> billMasList,
            Map<Date, List<BillPresentAndCalculationDto>> DemandLevelRebateMap, Organisation org,
            Map<Long, BillDisplayDto> taxWiseRebate);

    void sendMail(ProvisionalAssesmentMstDto provAsseMstDto, Organisation organisation, int langId, Long userId);

    List<Long> saveAndUpdateDraftAssessmentAfterEdit(SelfAssessmentSaveDTO saveDto, Organisation org);

    boolean CheckForAssesmentFiledForCurrYear(Long orgId, String assNo, String assOldpropno, String status, Long finYearId);

    Long findTaxDescIdByDeptIdAndOrgId(Long orgId, Long deptId, Long taxDescId);

	String uniquePropertyId(ProvisionalAssesmentMstDto dto, Organisation org);

	List<String> fetchAssessmentByGroupPropName(Long orgId);

	List<String> fetchAssessmentByParentPropName(Long orgId);
	
	Map<String, List<BillDisplayDto>> getDisplayMapOfTaxesForRevision(List<TbBillMas> billMasList, final Organisation organisation,
            Long deptId, List<BillDisplayDto> rebateDtoList, Map<Long, BillDisplayDto> taxWiseRebate, BillDisplayDto surCharge, String interstWaiveOff);

	void saveChangedBillingMethod(ProvisionalAssesmentMstDto provisionalAssesmentMstDto, Long applicationNo, Long empId,
			String ipAddress, Long orgId);

	void saveBillMethodChangeAuthorization(ProvisionalAssesmentMstDto provisionalAssesmentMstDto, Employee employee,
			Long orgId);

	void saveBillMethodChangeData(String appId, Long serviceId, String workflowDecision, Long orgId, Long empId,
			String lgIpMac, Long level);
	
	void mappingDummyKeyToActualKey(List<ProvisionalBillMasEntity> billMasList,
            List<BillReceiptPostingDTO> rebateRecDto);
}
