package com.abm.mainet.property.service;

import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.objection.dto.ObjectionDetailsDto;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.CommonBillResponseDto;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.PaymentDetailForMPOSDto;
import com.abm.mainet.common.dto.ReversalPaymentForMPOSDto;
import com.abm.mainet.common.integration.dto.CommonAppResponseDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.property.dto.PropertyDetailDto;
import com.abm.mainet.common.integration.property.dto.PropertyInputDto;
import com.abm.mainet.property.dto.AssessmentChargeCalDTO;
import com.abm.mainet.property.dto.BillPaymentDetailDto;
import com.abm.mainet.property.dto.ExcelDownloadDTO;
import com.abm.mainet.property.dto.PropertyBillPaymentDto;
import com.abm.mainet.property.dto.PropertyDetailRequestDTO;
import com.abm.mainet.property.dto.PropertyDetailResponseDTO;
import com.abm.mainet.property.dto.ProperySearchDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentFactorDtlDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.SelfAssessmentDeaultValueDTO;
import com.abm.mainet.property.dto.SelfAssessmentFinancialDTO;
import com.abm.mainet.property.dto.SelfAssessmentSaveDTO;

@WebService
public interface IPropertyTaxSelfAssessmentService {

    Object setDefaultFeildsOnLoad(SelfAssessmentDeaultValueDTO dto) throws Exception;

    AssessmentChargeCalDTO fetchChecklistAndCharges(SelfAssessmentDeaultValueDTO assessmentDto);

    SelfAssessmentSaveDTO saveSelfAssessment(SelfAssessmentSaveDTO saveData);

    SelfAssessmentFinancialDTO getFinYearId(PropertyInputDto request);

    SelfAssessmentFinancialDTO fetchFromGivenDate(PropertyInputDto request);

    SelfAssessmentFinancialDTO fetchNextSchedule(PropertyInputDto request);

    SelfAssessmentFinancialDTO displayYearList(PropertyInputDto request) throws Exception;

    SelfAssessmentDeaultValueDTO checkForValidProperty(SelfAssessmentDeaultValueDTO request) throws Exception;

    AssessmentChargeCalDTO calculateArrearsAndTaxForward(AssessmentChargeCalDTO request) throws Exception;

    SelfAssessmentDeaultValueDTO fetchAllLastPaymentDetails(SelfAssessmentDeaultValueDTO request) throws Exception;

    ExcelDownloadDTO billScheduleLookup(ExcelDownloadDTO request) throws Exception;

    BillPaymentDetailDto getPropertyPaymentDetails(PropertyBillPaymentDto request) throws Exception;
    
    BillPaymentDetailDto fetchPropertyPaymentDetails(PropertyBillPaymentDto request) throws Exception;

    AssessmentChargeCalDTO checklistAndChargesNoChnage(SelfAssessmentDeaultValueDTO request) throws Exception;

    SelfAssessmentDeaultValueDTO fetchPropertyByApplicationId(SelfAssessmentDeaultValueDTO request) throws Exception;

    List<DocumentDetailsVO> fetchAppDocuments(ProperySearchDto searchDto);

    AssessmentChargeCalDTO fetchTaxListForDisplay(AssessmentChargeCalDTO dto) throws Exception;

    SelfAssessmentSaveDTO saveAssessmentWithEdit(SelfAssessmentSaveDTO saveData) throws Exception;

    List<DocumentDetailsVO> getCheckList(ProvisionalAssesmentMstDto assDto, List<ProvisionalAssesmentFactorDtlDto> factDto);

    AssessmentChargeCalDTO fetchCharges(SelfAssessmentDeaultValueDTO assessmentDto);

    AssessmentChargeCalDTO fetchChecklistAndChargesForChangeAndNoChange(SelfAssessmentDeaultValueDTO assessmentDto)
            throws Exception;

    Long getServiceIdByShortName(Long orgId, String DeptCode);

    void callWorkflowInCaseOfZeroBillPayment(CommonChallanDTO offline, ProvisionalAssesmentMstDto asseMstDt);

    void callWorkflowInCaseOfZeroBillPaymentByRequest(SelfAssessmentDeaultValueDTO assDefaultDto);

    SelfAssessmentSaveDTO saveAndUpdateDraftApplicationAfterEdit(SelfAssessmentSaveDTO assDefaultDto);

    PropertyDetailDto updateOTPServiceForAssessment(String mobileNo);

    PropertyDetailDto getOTPServiceForAssessment(String mobileNo);

    CommonAppResponseDTO updateMobileAndEmailForMobile(ProperySearchDto searchDto);

    List<SelfAssessmentFinancialDTO> displayYearListForMobile(PropertyInputDto request) throws Exception;
    
    ChallanReceiptPrintDTO knockOffAmountToConfirmAmount(BillPaymentDetailDto request);
    
    void setDecriptionValues(Organisation org, ProvisionalAssesmentMstDto assMst) throws Exception;
    
    Map<String, Double> getBillPaidStatusOfProperty(PropertyInputDto inputDto);
    
    Boolean saveAssessmentAfterObjection(ObjectionDetailsDto inputDto);

	

	List<String> getFlatList(String propNo, Long orgId);
	
	CommonBillResponseDto getBillOutstandingForMpos(String propertyNo);
	
	Map<String, String> updateBillDataForMPOS(PaymentDetailForMPOSDto mposDto);
	
	Map<String, String> reversePaymentForMPOS(ReversalPaymentForMPOSDto mposDto);

	CommonChallanDTO getPropertyDetails(CommonChallanDTO request) throws Exception;

	List<PropertyDetailResponseDTO> getPropertyDeatilForMpos(PropertyDetailRequestDTO searchDTO);
    
}
