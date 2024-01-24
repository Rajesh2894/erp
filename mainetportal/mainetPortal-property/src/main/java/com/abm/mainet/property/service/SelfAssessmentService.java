package com.abm.mainet.property.service;

import java.util.List;
import java.util.Map;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.property.dto.ArrayOfDiversionPlotDetails;
import com.abm.mainet.property.dto.ArrayOfKhasraDetails;
import com.abm.mainet.property.dto.ArrayOfPlotDetails;
import com.abm.mainet.property.dto.AssessmentChargeCalDTO;
import com.abm.mainet.property.dto.BillDisplayDto;
import com.abm.mainet.property.dto.BillPaymentDetailDto;
import com.abm.mainet.property.dto.ExcelDownloadDTO;
import com.abm.mainet.property.dto.LandTypeApiDetailRequestDto;
import com.abm.mainet.property.dto.PropertyBillPaymentDto;
import com.abm.mainet.property.dto.PropertyEditNameAddressDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.SelfAssessmentDeaultValueDTO;
import com.abm.mainet.property.dto.SelfAssessmentFinancialDTO;
import com.abm.mainet.property.dto.SelfAssessmentSaveDTO;

public interface SelfAssessmentService {

    SelfAssessmentDeaultValueDTO setAllDefaultFields(Long orgid, Long deptId);

    AssessmentChargeCalDTO fetchChecklistAndCharges(SelfAssessmentDeaultValueDTO defaultData);

    SelfAssessmentSaveDTO saveSelfAssessment(SelfAssessmentSaveDTO saveDto);

    Map<String, List<BillDisplayDto>> getTaxMapForDisplayCategoryWise(List<BillDisplayDto> displayDto, Organisation organisation,
            Long deptId, List<Long> taxCatList);

    SelfAssessmentFinancialDTO getFinyearDate(SelfAssessmentDeaultValueDTO selfAssessmentDeaultValueDTO);

    SelfAssessmentFinancialDTO fetchFromGivenDate(SelfAssessmentDeaultValueDTO selfAssessmentDeaultValueDTO);

    SelfAssessmentFinancialDTO fetchScheduleDate(SelfAssessmentDeaultValueDTO defaultData);

    SelfAssessmentFinancialDTO displayYearList(SelfAssessmentDeaultValueDTO defaultData);

    SelfAssessmentDeaultValueDTO checkForValidProperty(SelfAssessmentDeaultValueDTO requestData);

    SelfAssessmentDeaultValueDTO fetchAllLastPaymentDetails(SelfAssessmentDeaultValueDTO defaultData);

    ExcelDownloadDTO getAllBillschedulByOrgid(ExcelDownloadDTO data);

    BillPaymentDetailDto getPropertyPaymentDetails(PropertyBillPaymentDto data, Organisation organisation);

    SelfAssessmentDeaultValueDTO fetchPropertyByApplicationId(SelfAssessmentDeaultValueDTO defaultData);

    List<DocumentDetailsVO> fetchAppDocuments(Long applicationId, Long orgId);

    AssessmentChargeCalDTO fetchTaxListForDisplay(AssessmentChargeCalDTO request);

    List<LookUp> findDistrictByLandType(LandTypeApiDetailRequestDto dto);

    List<LookUp> getTehsilListByDistrict(LandTypeApiDetailRequestDto dto);

    List<LookUp> getVillageListByTehsil(LandTypeApiDetailRequestDto dto);

    List<LookUp> getMohallaListByVillageId(LandTypeApiDetailRequestDto dto);

    List<LookUp> getStreetListByMohallaId(LandTypeApiDetailRequestDto dto);

    ArrayOfKhasraDetails getKhasraDetails(LandTypeApiDetailRequestDto dto);

    ArrayOfPlotDetails getNajoolDetails(LandTypeApiDetailRequestDto dto);

    ArrayOfDiversionPlotDetails getDiversionDetails(LandTypeApiDetailRequestDto dto);

    List<LookUp> getKhasraNoList(LandTypeApiDetailRequestDto dto);

    List<LookUp> getNajoolPlotList(LandTypeApiDetailRequestDto dto);

    List<LookUp> getDiversionPlotList(LandTypeApiDetailRequestDto dto);

    AssessmentChargeCalDTO fetchCharges(SelfAssessmentDeaultValueDTO defaultData);

    AssessmentChargeCalDTO fetchChecklistAndChargesForChangeAndNoChange(SelfAssessmentDeaultValueDTO arrearsRequest);
    
    LandTypeApiDetailRequestDto getVsrNo(LandTypeApiDetailRequestDto dto);
    
    void callWorkflowInCaseOfZeroBillPayment(CommonChallanDTO offline, ProvisionalAssesmentMstDto asseMstDt);

	SelfAssessmentSaveDTO saveAndUpdateDraftApplicationAfterEdit(SelfAssessmentSaveDTO saveDto);

	Boolean checkWhetherPropertyIsActive(String propNo, String oldPropNo, Long orgId);

	List<String> fetchFlstList(String propNo, Long orgid);

	ProvisionalAssesmentMstDto getPropertyEdidNameAddress(ProvisionalAssesmentMstDto assesmentMstDto);
	
	PropertyEditNameAddressDto saveEditNameAndAddress(PropertyEditNameAddressDto editNameAddressDto);
	
	ProvisionalAssesmentMstDto savePropertyElectricRelatedData(ProvisionalAssesmentMstDto assesmentMstDto);
	
	

	boolean checkPropertyExistByPropNo(String propNo, Long orgId);
}
