/**
 * 
 */
package com.abm.mainet.property.ui.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.bill.service.BillMasterCommonService;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.cfc.objection.service.NoticeMasterService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.BillReceiptPostingDTO;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.TbBillDet;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.repository.ReceiptRepository;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.IReceiptEntryService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.UserTaskDTO;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionWithDocs;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.common.workflow.service.IWorkflowActionService;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.common.workflow.service.IWorkflowTaskService;
import com.abm.mainet.property.domain.AsExcessAmtEntity;
import com.abm.mainet.property.dto.ArrayOfDiversionPlotDetails;
import com.abm.mainet.property.dto.ArrayOfKhasraDetails;
import com.abm.mainet.property.dto.ArrayOfPlotDetails;
import com.abm.mainet.property.dto.BillDisplayDto;
import com.abm.mainet.property.dto.BillPresentAndCalculationDto;
import com.abm.mainet.property.dto.LandTypeApiDetailRequestDto;
import com.abm.mainet.property.dto.PropertyRoomDetailsDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentDetailDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentFactorDtlDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.service.AsExecssAmtService;
import com.abm.mainet.property.service.DataEntrySuiteService;
import com.abm.mainet.property.service.IProvisionalAssesmentMstService;
import com.abm.mainet.property.service.IProvisionalBillService;
import com.abm.mainet.property.service.PropertyAuthorizationService;
import com.abm.mainet.property.service.PropertyBRMSService;
import com.abm.mainet.property.service.PropertyBillPaymentService;
import com.abm.mainet.property.service.PropertyService;
import com.abm.mainet.property.service.SelfAssessmentService;
import com.abm.mainet.property.ui.model.SelfAssesmentNewModel;
import com.google.common.util.concurrent.AtomicDouble;

@Controller
@RequestMapping("/SelfAssessmentAuthorization.html")
public class SelfAssessmentAuthorizationController extends AbstractFormController<SelfAssesmentNewModel> {

    private static final Logger LOGGER = Logger.getLogger(SelfAssessmentAuthorizationController.class);
    @Resource
    private IChallanService iChallanService;

    @Resource
    private IProvisionalAssesmentMstService provisionalAssesmentMstService;

    @Autowired
    private ILocationMasService iLocationMasService;

    @Autowired
    private IProvisionalBillService iProvisionalBillService;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private ServiceMasterService serviceMaster;

    @Resource
    private SelfAssessmentService selfAssessmentService;

    @Resource
    private PropertyBillPaymentService propertyBillPaymentService;

    @Autowired
    private IChecklistVerificationService iChecklistVerificationService;

    @Autowired
    private IFileUploadService fileUpload;

    @Autowired
    private IFinancialYearService iFinancialYear;

    @Autowired
    private PropertyAuthorizationService propertyAuthorizationService;

    @Autowired
    private PropertyBRMSService propertyBRMSService;

    @Autowired
    private IProvisionalAssesmentMstService iProvisionalAssesmentMstService;

    @Autowired
    private IReceiptEntryService iReceiptEntryService;

    @Autowired
    private IWorkFlowTypeService iWorkFlowTypeService;

    @Autowired
    private DataEntrySuiteService dataEntrySuiteService;
    
    @Autowired
    private IWorkflowRequestService workflowReqService;
    
    @Autowired
    private IWorkflowActionService workflowActionService;
    
    @Autowired
    private NoticeMasterService noticeMasterService;

    /*
     * load property data for Authorization
     */
    @RequestMapping(params = "showDetails", method = RequestMethod.POST)
    public ModelAndView defaultLoad(@RequestParam("appNo") final long applicationId, @RequestParam("taskId") final long serviceId,
            @RequestParam("actualTaskId") final long taskId, final HttpServletRequest httpServletRequest) throws Exception {
        sessionCleanup(httpServletRequest);
        fileUpload.sessionCleanUpForFileUpload();
        getModel().setLastChecker(iWorkFlowTypeService.isLastTaskInCheckerTaskList(taskId));
        UserTaskDTO userTaskDTO = ApplicationContextProvider.getApplicationContext().getBean(IWorkflowTaskService.class).findByTaskId(taskId);
        getModel().setCurrentApprovalLevel(userTaskDTO.getCurentCheckerLevel());
        getModel().setNoOfDaysEditableFlag(MainetConstants.FlagN);
        getModel().setNoOfDaysAuthEditFlag(MainetConstants.FlagN);
        final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        ServiceMaster service = serviceMaster.getServiceMaster(serviceId, orgId);
        Long deptId = service.getTbDepartment().getDpDeptid();
        final List<ProvisionalAssesmentMstDto> provAssDtoList = provisionalAssesmentMstService
                .getAssesmentMstDtoListByAppId(applicationId, orgId);
        final ProvisionalAssesmentMstDto assMst = propertyAuthorizationService.getAssesmentMstDtoForDisplay(provAssDtoList);
        Date afterAddingDaysToCreatedDate = null;
        try {
            LookUp propertyNoOfDaysLookUp = CommonMasterUtility.getValueFromPrefixLookUp("D", "NOD",
                    UserSession.getCurrent().getOrganisation());
            LocalDate convertCreateDateToLocalDate = assMst.getCreatedDate().toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDate();
            afterAddingDaysToCreatedDate = Date.from(
                    convertCreateDateToLocalDate.plusDays(Long.valueOf(propertyNoOfDaysLookUp.getOtherField()))
                            .atStartOfDay(ZoneId.systemDefault()).toInstant());
        } catch (Exception e) {
            // TODO: handle exception
        }

        if (afterAddingDaysToCreatedDate != null && Utility.compareDate(new Date(), afterAddingDaysToCreatedDate)) {
            getModel().setNoOfDaysAuthEditFlag(MainetConstants.FlagY);
        }
        LookUp billDeletionInactive = null;
        try {
            billDeletionInactive = CommonMasterUtility.getValueFromPrefixLookUp("BDI", "ENV",
                    UserSession.getCurrent().getOrganisation());
        } catch (Exception e) {

        }
        if (billDeletionInactive != null && StringUtils.isNotBlank(billDeletionInactive.getOtherField())
                && StringUtils.equals(billDeletionInactive.getOtherField(), MainetConstants.FlagY)) {
            getModel().setNoOfDaysAuthEditFlag(MainetConstants.FlagN);
        }

        LookUp constructioncomplitiondate = null;
        try {
            constructioncomplitiondate = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.Property.CD.toString(),
                    MainetConstants.Property.propPref.PAR.toString(),
                    UserSession.getCurrent().getOrganisation());

        } catch (Exception e) {

        }
        if (constructioncomplitiondate != null) {
            getModel().setConstructFlag(constructioncomplitiondate.getOtherField());
        } else {
            getModel().setConstructFlag(MainetConstants.Y_FLAG);
        }
        BigDecimal billPaidAmt = iReceiptEntryService.getPaidAmountByAppNo(orgId, applicationId, deptId);
        // if applicationId not available in TB_RECEIPT_MAS
        if (!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "PSCL") && billPaidAmt == null) {
            billPaidAmt = iReceiptEntryService.getPaidAmountByAdditionalRefNo(orgId, assMst.getAssNo(), deptId);
        }
        if ((!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "PSCL") && !Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "ASCL")) && (billDeletionInactive != null && org.apache.commons.lang.StringUtils.isNotBlank(billDeletionInactive.getOtherField())
                && org.apache.commons.lang.StringUtils.equals(billDeletionInactive.getOtherField(), MainetConstants.FlagY))) {
            billPaidAmt = ApplicationContextProvider.getApplicationContext().getBean(IReceiptEntryService.class)
                    .getPaidAmountByAdditionalRefNoIncRebate(UserSession.getCurrent().getOrganisation().getOrgid(),
                            String.valueOf(assMst.getAssNo()), deptId);
        }
        TbServiceReceiptMasEntity manualReceiptMas = null;
        if (assMst.getAssNo() != null && !assMst.getAssNo().isEmpty()) {
            List<AsExcessAmtEntity> excessAmtEntByPropNoList = ApplicationContextProvider.getApplicationContext()
                    .getBean(AsExecssAmtService.class)
                    .getExcessAmtEntByActivePropNo(assMst.getAssNo(), UserSession.getCurrent().getOrganisation().getOrgid());
            if (CollectionUtils.isNotEmpty(excessAmtEntByPropNoList)) {
                AsExcessAmtEntity asExcessAmtEntity = excessAmtEntByPropNoList.get(excessAmtEntByPropNoList.size() - 1);
                manualReceiptMas = ApplicationContextProvider.getApplicationContext().getBean(ReceiptRepository.class)
                        .getmanualReceiptAdvanceAmountByAddRefNo(asExcessAmtEntity.getRmRcptid(), assMst.getAssNo(),
                                UserSession.getCurrent().getOrganisation().getOrgid());

            }
        }
        if (manualReceiptMas != null) {
            AtomicDouble manualReceiptArrearAmnt = new AtomicDouble(0);
            if (CollectionUtils.isNotEmpty(manualReceiptMas.getReceiptFeeDetail())) {
                manualReceiptMas.getReceiptFeeDetail().forEach(receiptDet -> {
                    TbTaxMas taxMAs = ApplicationContextProvider.getApplicationContext().getBean(TbTaxMasService.class)
                            .findById(receiptDet.getTaxId(), orgId);
                    String taxCatCode = CommonMasterUtility
                            .getHierarchicalLookUp(taxMAs.getTaxCategory1(), UserSession.getCurrent().getOrganisation())
                            .getLookUpCode();

                    if (!StringUtils.equals(taxCatCode, MainetConstants.FlagA)) {
                        manualReceiptArrearAmnt.addAndGet(receiptDet.getRfFeeamount().doubleValue());
                    }
                });
            }
            getModel().setManualRecptArrearPaidAmnt(manualReceiptArrearAmnt.doubleValue());
            if (billPaidAmt == null) {
                billPaidAmt = new BigDecimal(0);
            }
            double lastBillAmount = billPaidAmt.doubleValue();
            lastBillAmount = lastBillAmount + manualReceiptMas.getRmAmount().doubleValue();
            billPaidAmt = new BigDecimal(lastBillAmount);
        } else {
            getModel().setManualRecptArrearPaidAmnt(0);
        }
        if (billPaidAmt != null) {
            assMst.setBillPaidAmt(billPaidAmt.doubleValue());
        }
        getModel().setAssType(MainetConstants.Property.ASESS_AUT);// to identify call of model from Authorization
        assMst.setOrgId(orgId);
        if (assMst.getAssCorrAddress() == null) {
            assMst.setProAsscheck(MainetConstants.FlagY);
        } else {
            assMst.setProAsscheck(MainetConstants.FlagN);
        }
        if (assMst.getAssLpYear() == null) {
            assMst.setProAssBillPayment(MainetConstants.Property.NOT_APP);
        } else {
            assMst.setProAssBillPayment(MainetConstants.Property.MANUAL);
        }
        if(assMst.getAssOwnerType() != null && assMst.getAssOwnerType() > 0) {
        	LookUp ownerType = CommonMasterUtility.getNonHierarchicalLookUpObject(
        			assMst.getAssOwnerType(), UserSession.getCurrent().getOrganisation());
        	if(ownerType != null && StringUtils.isNotBlank(ownerType.getLookUpCode())) {
        		getModel().setOwnershipPrefix(ownerType.getLookUpCode());
        		getModel().setOwnershipPrefixBefore(ownerType.getLookUpCode());
        		if (assMst.getAssLandType() != null && assMst.getAssLandType() > 0) {
        			LookUp landTypePrefix = CommonMasterUtility.getNonHierarchicalLookUpObject(
        					assMst.getAssLandType(), UserSession.getCurrent().getOrganisation());
        			getModel().setLandTypePrefix(landTypePrefix.getLookUpCode());
        		}
        		
        		LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp(ownerType.getLookUpCode(),
        				MainetConstants.Property.propPref.OWT,
        				UserSession.getCurrent().getOrganisation());
        		if(lookup != null && StringUtils.isNotBlank(lookup.getDescLangFirst())) {
        			getModel().setOwnershipTypeValue(lookup.getDescLangFirst());        	        		        			
        		}
        	}
        }

        List<TbBillMas> billMasList = new ArrayList<>();
        if (assMst.getBillMethod() != null && MainetConstants.FlagI.equals(CommonMasterUtility
                .getNonHierarchicalLookUpObject(assMst.getBillMethod(), UserSession.getCurrent().getOrganisation())
                .getLookUpCode())) {
            billMasList = iProvisionalBillService.getProvisionalBillMasByPropertyNoAndFlatNo(assMst.getAssNo(),
                    assMst.getFlatNo(), orgId);
        } else {
            billMasList = iProvisionalBillService.getProvisionalBillMasByPropertyNo(assMst.getAssNo(), orgId);
        }

        if (!billMasList.isEmpty()) {
            getModel().setBillMasList(billMasList);
            TbBillMas billMas = billMasList.get(billMasList.size() - 1);
            propertyBillPaymentService.setTaxDetailInBillDetail(billMas, UserSession.getCurrent().getOrganisation(), deptId,null);
            List<BillDisplayDto> billDisDtoList = propertyService.getTaxListForDisplay(billMas,
                    UserSession.getCurrent().getOrganisation(), deptId);
            Map<Long, BillDisplayDto> rebateMap = propertyAuthorizationService.getRebete(applicationId, orgId, deptId);
            rebateMap.entrySet().forEach(rebate -> {
                billDisDtoList.add(rebate.getValue());
            });
            BillDisplayDto advanceDto = selfAssessmentService.getAjustedAdvanceAmt(UserSession.getCurrent().getOrganisation(),
                    applicationId, deptId);
            if (advanceDto != null) {
                billDisDtoList.add(advanceDto);
                getModel().setAdvanceAmt(advanceDto);
            }
            if(assMst.getCreatedDate() != null) {
            	Long assYearId = iFinancialYear.getFinanceYearId(assMst.getCreatedDate());
            	BillDisplayDto surCharge = propertyService.getSurChargetoView(
            			UserSession.getCurrent().getOrganisation(),
            			deptId, assMst.getAssNo(),
            			MainetConstants.Property.SURCHARGE, assYearId, applicationId, billPaidAmt);
            	if (surCharge != null && surCharge.getTotalTaxAmt() != null) {
            		billDisDtoList.add(surCharge);
            	}
            	assMst.setBillTotalAmt(propertyService.getTotalActualAmount(billMasList, null, rebateMap, surCharge));            	
            }

            getModel().setDisplayMap(propertyService.getTaxMapForDisplayCategoryWise(billDisDtoList,
                    UserSession.getCurrent().getOrganisation(), deptId));// Setting map for tax Display on UI
            getModel().setTotalTaxPayableWithArrears(assMst.getBillTotalAmt() + getModel().getManualRecptArrearPaidAmnt());
        }
        if(assMst.getAssAcqDate() != null) {
        	Long finYearId = iFinancialYear.getFinanceYearId(assMst.getAssAcqDate());
        	List<FinancialYear> financialYearList = iFinancialYear
        			.getFinanceYearListFromGivenDate(UserSession.getCurrent().getOrganisation().getOrgid(), finYearId, new Date());
        	String financialYear = null;
        	for (final FinancialYear finYearTemp : financialYearList) {
        		financialYear = Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate());
        		getModel().getFinancialYearMap().put(finYearTemp.getFaYear(), financialYear);
        	}
        }

        List<ProvisionalAssesmentFactorDtlDto> factorMap = factorMappingForView(assMst);
        List<ProvisionalAssesmentFactorDtlDto> factorMapBefore = new ArrayList<>();
        factorMap.forEach(fact -> {
            ProvisionalAssesmentFactorDtlDto factBefore = new ProvisionalAssesmentFactorDtlDto();
            BeanUtils.copyProperties(fact, factBefore);
            factorMapBefore.add(factBefore);
        });

        List<LookUp> lookupList = CommonMasterUtility.lookUpListByPrefix("FCT",
                UserSession.getCurrent().getOrganisation().getOrgid());
        lookupList.forEach(factQuest -> {
            boolean result = factorMapBefore.stream()
                    .anyMatch(fact -> factQuest.getLookUpId() == fact.getAssfFactorId().longValue());
            if (result) {
                assMst.getProAssfactor().add(MainetConstants.FlagY);
            } else {
                assMst.getProAssfactor().add(MainetConstants.FlagN);
            }
        });

        getModel().setProvAsseFactDtlDto(factorMap);
        getModel().setAuthCompFactDto(factorMapBefore);
        getModel().setServiceId(serviceId);
        String serviceShortCode = service.getSmShortdesc();
        getModel().setServiceShortCode(serviceShortCode);
        getModel().setOrgId(orgId);
        getModel().setDeptId(deptId);
        getModel().setAuthComBillList(billMasList);
        getModel().setProvAssMstDtoList(provAssDtoList);
        assMst.setLocationName(iLocationMasService.getLocationNameById(assMst.getLocId(), orgId));
        getModel().setProvisionalAssesmentMstDto(assMst);
        if (getModel().getLandTypePrefix() != null) {
            setlandTypeDetails(getModel());
        }
        getModel().setDropDownValues(UserSession.getCurrent().getOrganisation());
        getModel().setDocumentList(iChecklistVerificationService.getDocumentUploaded(applicationId, orgId));
        getModel().getWorkflowActionDto().setApplicationId(applicationId);
        getModel().getWorkflowActionDto().setTaskId(taskId);
        getModel().setAuthCompBeforeDto(
                iProvisionalAssesmentMstService.copyProvDtoDataToOtherDto(assMst));// Setting before change property detail

        if (getModel().getAuthCompBeforeDto().getAssLandType() != null) {
            LookUp landTypePrefix = CommonMasterUtility.getNonHierarchicalLookUpObject(
                    getModel().getAuthCompBeforeDto().getAssLandType(), UserSession.getCurrent().getOrganisation());
            getModel().setOldLandTypePrefix(landTypePrefix.getLookUpCode());
        }

        getModel().setIntgrtionWithBP(CommonMasterUtility.getDefaultValueByOrg(MainetConstants.Property.IBP,
                UserSession.getCurrent().getOrganisation()).getLookUpCode());
        if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
        	getModel().setAuthLevel(iWorkFlowTypeService.curentCheckerLevel(taskId));
        	WorkflowRequest workflowRequest = workflowReqService
					.getWorkflowRequestByAppIdOrRefId(applicationId, null, UserSession.getCurrent().getOrganisation().getOrgid());
        	getModel().setRoomTypeList(UserSession.getCurrent().getOrganisation());
			if (workflowRequest != null && MainetConstants.WorkFlow.Decision.SEND_BACK.equalsIgnoreCase(workflowRequest.getLastDecision())
					&& getModel().isAuthLevel()) {
				getModel().setHideUserAction(true);
				getModel().setLocation(setLocation());
				List<WorkflowTaskActionWithDocs> taskActionWithDocs = workflowActionService.getActionLogByUuidAndWorkflowId(String.valueOf(applicationId), 
						workflowRequest.getId(), (short)UserSession.getCurrent().getLanguageId());
				if(CollectionUtils.isNotEmpty(taskActionWithDocs)) {
					List<WorkflowTaskActionWithDocs> remarkList = taskActionWithDocs.stream().filter(remark -> 
					MainetConstants.WorkFlow.Decision.SEND_BACK_REMARK.equals(remark.getDecision()))
					.collect(Collectors.toList());
					if(CollectionUtils.isNotEmpty(remarkList)) {
						getModel().getWorkflowActionDto().setComments(remarkList.get(remarkList.size()-1).getComments());
					}
				}
				return new ModelAndView("SelfAssessmentFormValidn", MainetConstants.FORM_NAME, getModel());
			}
        }
        getModel().checkListDecentralized(applicationId, serviceId, orgId);
        return new ModelAndView("SelfAssessmentView", MainetConstants.FORM_NAME, getModel());

    }

    private List<LookUp> setLocation() {
		List<LookUp> locList = getModel().getLocation();
		    Organisation org = UserSession.getCurrent().getOrganisation();
		    List<LocationMasEntity> location = iLocationMasService.findWZMappedLocationByOrgIdAndDeptId(org.getOrgid(),
		            getModel().getDeptId());
		    if (location != null && !location.isEmpty()) {
		        location.forEach(loc -> {
		            LookUp lookUp = new LookUp();
		            lookUp.setLookUpId(loc.getLocId());
		            lookUp.setDescLangFirst(loc.getLocNameEng());
		            lookUp.setDescLangSecond(loc.getLocNameReg());
		            locList.add(lookUp);
		        });
		    }
			return locList;
	}
    
    private void setlandTypeDetails(SelfAssesmentNewModel model) {
        LandTypeApiDetailRequestDto dto = new LandTypeApiDetailRequestDto();
        dto.setLandType(model.getLandTypePrefix());
        dto.setDistrictId(model.getProvisionalAssesmentMstDto().getAssDistrict());
        dto.setTehsilId(model.getProvisionalAssesmentMstDto().getAssTahasil());
        dto.setVillageId(model.getProvisionalAssesmentMstDto().getTppVillageMauja());
        dto.setMohallaId(model.getProvisionalAssesmentMstDto().getMohalla());
        dto.setStreetNo(model.getProvisionalAssesmentMstDto().getAssStreetNo());
        dto.setKhasraNo(model.getProvisionalAssesmentMstDto().getTppPlotNoCs());
        dto.setPlotNo(model.getProvisionalAssesmentMstDto().getTppPlotNo());
        model.setDistrict(dataEntrySuiteService.findDistrictByLandType(dto));
        model.setTehsil(dataEntrySuiteService.getTehsilListByDistrict(dto));
        model.setVillage(dataEntrySuiteService.getVillageListByTehsil(dto));
        if (model.getLandTypePrefix().equals(MainetConstants.Property.LandType.KPK)) {
            model.setKhasraNo(dataEntrySuiteService.getKhasraNoList(dto));
        }
        if (model.getLandTypePrefix().equals(MainetConstants.Property.LandType.NZL)
                || model.getLandTypePrefix().equals(MainetConstants.Property.LandType.DIV)) {
            getModel().setMohalla(dataEntrySuiteService.getMohallaListByVillageId(dto));
            getModel().setBlockStreet(dataEntrySuiteService.getStreetListByMohallaId(dto));
        }
        if (model.getLandTypePrefix().equals(MainetConstants.Property.LandType.NZL)) {
            getModel().setPlotNo(dataEntrySuiteService.getNajoolPlotList(dto));
        }
        if (model.getLandTypePrefix().equals(MainetConstants.Property.LandType.DIV)) {
            getModel().setPlotNo(dataEntrySuiteService.getDiversionPlotList(dto));
        }
    }

    private List<ProvisionalAssesmentFactorDtlDto> factorMappingForView(final ProvisionalAssesmentMstDto assMst) {
        List<ProvisionalAssesmentFactorDtlDto> factorMap = new ArrayList<>();
        List<Long> unitNoList = new ArrayList<>();
        assMst.getProvisionalAssesmentDetailDtoList().forEach(propDet -> {
            if (!unitNoList.contains(propDet.getAssdUnitNo())) {
                propDet.getProvisionalAssesmentFactorDtlDtoList().forEach(fact -> {
                    ProvisionalAssesmentFactorDtlDto factDto = new ProvisionalAssesmentFactorDtlDto();
                    BeanUtils.copyProperties(fact, factDto);
                    factDto.setUnitNoFact(propDet.getAssdUnitNo().toString());
                    factDto.setProAssfFactorIdDesc(CommonMasterUtility
                            .getNonHierarchicalLookUpObject(fact.getAssfFactorId(), UserSession.getCurrent().getOrganisation())
                            .getDescLangFirst());
                    factDto.setProAssfFactorValueDesc(CommonMasterUtility
                            .getNonHierarchicalLookUpObject(fact.getAssfFactorValueId(),
                                    UserSession.getCurrent().getOrganisation())
                            .getDescLangFirst());
                    factDto.setFactorValueCode(CommonMasterUtility
                            .getNonHierarchicalLookUpObject(fact.getAssfFactorId(), UserSession.getCurrent().getOrganisation())
                            .getLookUpCode());
                    factorMap.add(factDto);
                });
            }
            unitNoList.add(propDet.getAssdUnitNo());
        });
        return factorMap;
    }

    @RequestMapping(params = "saveAuthorization", method = RequestMethod.POST)
    public ModelAndView saveAuthorization(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        SelfAssesmentNewModel model = this.getModel();
        model.setAuthEditFlag(MainetConstants.FlagN);
        WorkflowTaskAction workFlowActionDto = this.getModel().getWorkflowActionDto();
		if (model.saveAuthorization() ) {
			if (StringUtils.equals(workFlowActionDto.getDecision(), MainetConstants.WorkFlow.Decision.REJECTED)) {
				return jsonResult(
						JsonViewObject.successResult(ApplicationSession.getInstance().getMessage("property.reject")));
			} else if (StringUtils.equals(workFlowActionDto.getDecision(),
					MainetConstants.WorkFlow.Decision.SEND_BACK)) {
				return jsonResult(
						JsonViewObject.successResult(ApplicationSession.getInstance().getMessage("property.sendback")));
			} else if (StringUtils.equals(workFlowActionDto.getDecision(),
					MainetConstants.WorkFlow.Decision.FORWARD_TO_EMPLOYEE)) {
				return jsonResult(
						JsonViewObject.successResult(ApplicationSession.getInstance().getMessage("property.forward")));
			} else if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)
            		&& getModel().isAuthLevel() && getModel().isHideUserAction()) {
				return jsonResult(JsonViewObject
						.successResult(ApplicationSession.getInstance().getMessage("prop.save.selfNew") + model.getProvisionalAssesmentMstDto().getAssNo()));
			} else {
				if(model.isLastChecker()) {
					return jsonResult(JsonViewObject
							.successResult(ApplicationSession.getInstance().getMessage("property.Auth.save") +" Your property No is "+ model.getProvisionalAssesmentMstDto().getAssNo()));
					
				}else {
					return jsonResult(JsonViewObject
							.successResult(ApplicationSession.getInstance().getMessage("property.Auth.save")));
				}
			}
		}
        return customDefaultMyResult("SelfAssessmentView");
    }

    @RequestMapping(params = "saveAuthorizationWithEdit", method = RequestMethod.POST)
    public ModelAndView saveAuthorizationWithEdit(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        SelfAssesmentNewModel model = this.getModel();
        model.setAuthEditFlag("Y");
        if (model.saveAuthorization()) {
        	if(model.isLastChecker()) {
				return jsonResult(JsonViewObject
						.successResult(ApplicationSession.getInstance().getMessage("property.Auth.save") +" Your property No is "+ model.getProvisionalAssesmentMstDto().getAssNo()));
				
			}else {
				return jsonResult(JsonViewObject
						.successResult(ApplicationSession.getInstance().getMessage("property.Auth.save")));
			}
        }
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)
        		&& MainetConstants.FlagE.equals(model.getSelfAss())){
        	return customDefaultMyResult("SelfAssessmentView");
        }
        return customDefaultMyResult("PropertyAuthorizationBeforeAfter");
    }

    @RequestMapping(params = "backToFirstLevelAuth", method = RequestMethod.POST)
    public ModelAndView backToFirstLevelAuth(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        return new ModelAndView("SelfAssessmentView", MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(params = "compareBeforeAfterAuth", method = RequestMethod.POST)
    public ModelAndView compareBeforeAfterAuth(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        this.getModel().setDropDownValues(UserSession.getCurrent().getOrganisation());
        getCheckListAndcharges(getModel());

        if (getModel().getProvisionalAssesmentMstDto().getAssLandType() != null) {
            LookUp landTypePrefix = CommonMasterUtility.getNonHierarchicalLookUpObject(
                    getModel().getProvisionalAssesmentMstDto().getAssLandType(), UserSession.getCurrent().getOrganisation());
            getModel().setChangeLandTypePrefix(landTypePrefix.getLookUpCode());
        }
        return new ModelAndView("PropertyAuthorizationBeforeAfter", MainetConstants.FORM_NAME, getModel());
    }

    private void getCheckListAndcharges(SelfAssesmentNewModel model) {
        ProvisionalAssesmentMstDto dto = model.getProvisionalAssesmentMstDto();
        // propertyService.setWardZoneDetailByLocId(dto, model.getDeptId(), dto.getLocId());
        selfAssessmentService.setFactorMappingToAssDto(model.getProvAsseFactDtlDto(), dto);
        Long currentFinYearId = iFinancialYear.getFinanceYearId(new Date());
        String flatNo = null;
        if (dto.getBillMethod() != null
                && MainetConstants.FlagI.equals(CommonMasterUtility
                        .getNonHierarchicalLookUpObject(dto.getBillMethod(), UserSession.getCurrent().getOrganisation())
                        .getLookUpCode())
                && CollectionUtils.isNotEmpty(dto.getProvisionalAssesmentDetailDtoList())
                && (StringUtils.isNotBlank(dto.getProvisionalAssesmentDetailDtoList().get(0).getFlatNo()))) {
            flatNo = dto.getProvisionalAssesmentDetailDtoList().get(0).getFlatNo();
        }
        Map<Long, BillDisplayDto> taxWiseRebate = new TreeMap<>();// map<TxaId,BillDisplayDto>
        Map<Date, List<BillPresentAndCalculationDto>> schWiseChargeMap = propertyBRMSService.fetchCharges(dto, model.getDeptId(),
                taxWiseRebate, model.getFinYearList(), null, model.getAssType(), model.getAssesmentManualDate(), null);
        List<TbBillMas> billMasList = propertyService.generateNewBill(schWiseChargeMap,
                UserSession.getCurrent().getOrganisation(), model.getDeptId(), null, flatNo);
        List<BillReceiptPostingDTO> reabteRecDtoList = selfAssessmentService.knowkOffDemandLevelRebateAndExemption(billMasList,
                schWiseChargeMap,
                UserSession.getCurrent().getOrganisation(), taxWiseRebate);
        model.setReabteRecDtoList(reabteRecDtoList);
        
        AtomicDouble prvBalance = new AtomicDouble(0);
        AtomicDouble prvtotal = new AtomicDouble(0);
        AtomicDouble totalDemandWithoutRebate = new AtomicDouble(0);
        billMasList.forEach(authBill -> {
            totalDemandWithoutRebate.addAndGet(authBill.getBmTotalAmount());
            AtomicInteger found = new AtomicInteger(0);
            model.getAuthComBillList()
                    .forEach(oldBill -> {
                        if (authBill.getBmFromdt().equals(oldBill.getBmFromdt())) {
                            double amount = oldBill.getBmTotalAmount() * 10 / 100;
                            double diffAmount = authBill.getBmTotalAmount() - oldBill.getBmTotalAmount();
                            if (diffAmount > amount) {
                                propertyService.calculatePenaltyTax(UserSession.getCurrent().getOrganisation(), model.getDeptId(),
                                        authBill, diffAmount, prvBalance, prvtotal, totalDemandWithoutRebate);
                            }
                            found.incrementAndGet();
                        }
                    });
            if (found.get() <= 0) {
                double diffAmount = authBill.getBmTotalAmount();
                if (diffAmount > 0d) {
                    propertyService.calculatePenaltyTax(UserSession.getCurrent().getOrganisation(), model.getDeptId(),
                            authBill, diffAmount, prvBalance, prvtotal, totalDemandWithoutRebate);
                }
            }
        });
        List<TbBillMas> billMasArrears = model.getAuthComBillList();
        List<TbBillMas> billList = billMasList;
        if (billMasArrears != null && !billMasArrears.isEmpty()) {
        	if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "PSCL")) {
        		setInterestForCurrentYearBill(currentFinYearId, billMasArrears, billList);
        	}
            billList = propertyService.getBillListWithMergingOfOldAndNewBill(billMasArrears, billMasList);
            propertyService.updateArrearInCurrentBills(billList);
            billList.sort(Comparator.comparing(TbBillMas::getBmFromdt));
        }
      //Defect #137881, #137868
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_ASCL)) {
        	propertyService.interestCalculationWithoutBRMSCall(UserSession.getCurrent().getOrganisation(), model.getDeptId(),
                    billMasList, MainetConstants.Property.INT_RECAL_NO);
        }else if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "PSCL")) {
        	//propertyBRMSService.fetchInterstRate(billMasList, UserSession.getCurrent().getOrganisation(), model.getDeptId());// calculating interest rate through BRMS
        	//billMasterCommonService.calculateInterestForPrayagRaj(billMasList, UserSession.getCurrent().getOrganisation(), model.getDeptId(), MainetConstants.FlagY, null, UserSession.getCurrent().getEmployee().getEmpId(),billMasList,null);
        }else {
        	propertyService.interestCalculation(UserSession.getCurrent().getOrganisation(), model.getDeptId(), billMasList,
                    MainetConstants.Property.INT_RECAL_NO);
        }
        Long finYearId = iFinancialYear.getFinanceYearId(new Date());
        BillDisplayDto surCharge = propertyService.calculateSurchargeAtAuthorizationEdit(
                UserSession.getCurrent().getOrganisation(),
                model.getDeptId(), billMasList, dto.getAssNo(),
                MainetConstants.Property.SURCHARGE, finYearId);
        model.setSurCharge(surCharge);
        propertyService.taxCarryForward(billList, model.getOrgId());
        List<BillDisplayDto> earlyPayRebate = propertyBRMSService.fetchEarlyPayRebateRate(billList,
                UserSession.getCurrent().getOrganisation(), model.getDeptId(), null, null);
        final TbBillMas last = billList.get(billList.size() - 1);
        if (CollectionUtils.isNotEmpty(earlyPayRebate)) {
            double totalRebateAmount = 0.0;
            for (BillDisplayDto rebateDto : earlyPayRebate) {
                totalRebateAmount = totalRebateAmount + rebateDto.getTotalTaxAmt().doubleValue();
            }
            if (dto.getBillPaidAmt() < last.getBmTotalOutstanding() - totalRebateAmount) {
                earlyPayRebate = null;
                model.setEarlyPayRebate(null);
            }
            if (CollectionUtils.isNotEmpty(earlyPayRebate)) {
                model.setEarlyPayRebate(earlyPayRebate.get(0));
            }
        }
        Map<String, List<BillDisplayDto>> presentMap = selfAssessmentService.getDisplayMapOfTaxes(billList,
                UserSession.getCurrent().getOrganisation(), model.getDeptId(), earlyPayRebate, taxWiseRebate, surCharge);
        dto.setBillTotalAmt(propertyService.getTotalActualAmount(billList, earlyPayRebate, taxWiseRebate, surCharge));
        propertyService.updateARVRVInBill(dto, billList);
        ListIterator<TbBillMas> listIterator = billList.listIterator();
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)) {
        	while(listIterator.hasNext()) {
            	TbBillMas next = listIterator.next();
            	if(MainetConstants.Property.serviceShortCode.CIA.equalsIgnoreCase(model.getServiceShortCode())) {
            		next.setBmGenDes(MainetConstants.N_FLAG);
            	}else {
            		next.setBmGenDes(MainetConstants.Y_FLAG);
            	}
            	
            }
        }
        model.setBillMasList(billList);
        model.setDisplayMapAuthComp(presentMap);
    }

	private void setInterestForCurrentYearBill(Long currentFinYearId, List<TbBillMas> billMasArrears,
			List<TbBillMas> billList) {
		for (TbBillMas billMasArrear : billMasArrears) {
			for (TbBillMas bill : billList) {
				if(billMasArrear.getBmYear().equals(currentFinYearId) && bill.getBmYear().equals(currentFinYearId)) {
					for (TbBillDet det : bill.getTbWtBillDet()) {
						final String taxCode = CommonMasterUtility.getHierarchicalLookUp(det.getTaxCategory(), UserSession.getCurrent().getOrganisation())
								.getLookUpCode();
						if (taxCode.equals(PrefixConstants.TAX_CATEGORY.INTERST)) {
							List<TbBillDet> interestList = billMasArrear.getTbWtBillDet().stream().filter(arrearDet -> arrearDet.getTaxId().equals(det.getTaxId())).collect(Collectors.toList());
							if(CollectionUtils.isNotEmpty(interestList)) {
								det.setBdCurTaxamt(interestList.get(0).getBdCurTaxamt());
								det.setBdCurBalTaxamt(interestList.get(0).getBdCurBalTaxamt());
							}
						}
					}
				}
			}
		}
	}

    @RequestMapping(params = "getLandTypeApiDetails", method = RequestMethod.POST)
    public ModelAndView getLandTypeApiDetails(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse) {
        SelfAssesmentNewModel model = this.getModel();
        LandTypeApiDetailRequestDto dto = new LandTypeApiDetailRequestDto();
        dto.setLandType(model.getLandTypePrefix());
        dto.setDistrictId(model.getProvisionalAssesmentMstDto().getAssDistrict());
        dto.setTehsilId(model.getProvisionalAssesmentMstDto().getAssTahasil());
        dto.setVillageId(model.getProvisionalAssesmentMstDto().getTppVillageMauja());
        dto.setMohallaId(model.getProvisionalAssesmentMstDto().getMohalla());
        dto.setStreetNo(model.getProvisionalAssesmentMstDto().getAssStreetNo());
        dto.setKhasraNo(model.getProvisionalAssesmentMstDto().getTppPlotNoCs());
        dto.setPlotNo(model.getProvisionalAssesmentMstDto().getTppPlotNo());
        if (model.getLandTypePrefix().equals(MainetConstants.Property.LandType.KPK)) {
            ArrayOfKhasraDetails response = dataEntrySuiteService.getKhasraDetails(dto);
            model.setArrayOfKhasraDetails(response);
        } else if (model.getLandTypePrefix().equals(MainetConstants.Property.LandType.NZL)) {
            ArrayOfPlotDetails response = dataEntrySuiteService.getNajoolDetails(dto);
            model.setArrayOfPlotDetails(response);
        } else if (model.getLandTypePrefix().equals(MainetConstants.Property.LandType.DIV)) {
            ArrayOfDiversionPlotDetails response = dataEntrySuiteService.getDiversionDetails(dto);
            model.setArrayOfDiversionPlotDetails(response);
        }
        return new ModelAndView("showPropertyKhasaraDetails", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "getBeforeAuthLandTypeApiDetails", method = RequestMethod.POST)
    public ModelAndView getBeforeAuthLandTypeApiDetails(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse) {
        SelfAssesmentNewModel model = this.getModel();
        LandTypeApiDetailRequestDto dto = new LandTypeApiDetailRequestDto();
        dto.setLandType(model.getOldLandTypePrefix());
        dto.setDistrictId(model.getAuthCompBeforeDto().getAssDistrict());
        dto.setTehsilId(model.getAuthCompBeforeDto().getAssTahasil());
        dto.setVillageId(model.getAuthCompBeforeDto().getTppVillageMauja());
        dto.setMohallaId(model.getAuthCompBeforeDto().getMohalla());
        dto.setStreetNo(model.getAuthCompBeforeDto().getAssStreetNo());
        dto.setKhasraNo(model.getAuthCompBeforeDto().getTppPlotNoCs());
        dto.setPlotNo(model.getAuthCompBeforeDto().getTppPlotNo());
        if (model.getOldLandTypePrefix().equals(MainetConstants.Property.LandType.KPK)) {
            ArrayOfKhasraDetails response = dataEntrySuiteService.getKhasraDetails(dto);
            model.setArrayOfKhasraDetails(response);
        } else if (model.getOldLandTypePrefix().equals(MainetConstants.Property.LandType.NZL)) {
            ArrayOfPlotDetails response = dataEntrySuiteService.getNajoolDetails(dto);
            model.setArrayOfPlotDetails(response);
        } else if (model.getOldLandTypePrefix().equals(MainetConstants.Property.LandType.DIV)) {
            ArrayOfDiversionPlotDetails response = dataEntrySuiteService.getDiversionDetails(dto);
            model.setArrayOfDiversionPlotDetails(response);
        }
        return new ModelAndView("BeforeAuthPropertyApiDetails", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "getAfterAuthLandApiDetails", method = RequestMethod.POST)
    public ModelAndView getAfterAuthLandApiDetails(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse) {
        SelfAssesmentNewModel model = this.getModel();
        LandTypeApiDetailRequestDto dto = new LandTypeApiDetailRequestDto();
        dto.setLandType(model.getChangeLandTypePrefix());
        dto.setDistrictId(model.getProvisionalAssesmentMstDto().getAssDistrict());
        dto.setTehsilId(model.getProvisionalAssesmentMstDto().getAssTahasil());
        dto.setVillageId(model.getProvisionalAssesmentMstDto().getTppVillageMauja());
        dto.setMohallaId(model.getProvisionalAssesmentMstDto().getMohalla());
        dto.setStreetNo(model.getProvisionalAssesmentMstDto().getAssStreetNo());
        dto.setKhasraNo(model.getProvisionalAssesmentMstDto().getTppPlotNoCs());
        dto.setPlotNo(model.getProvisionalAssesmentMstDto().getTppPlotNo());
        if (model.getChangeLandTypePrefix().equals(MainetConstants.Property.LandType.KPK)) {
            ArrayOfKhasraDetails response = dataEntrySuiteService.getKhasraDetails(dto);
            model.setArrayOfKhasraDetails(response);
        } else if (model.getChangeLandTypePrefix().equals(MainetConstants.Property.LandType.NZL)) {
            ArrayOfPlotDetails response = dataEntrySuiteService.getNajoolDetails(dto);
            model.setArrayOfPlotDetails(response);
        } else if (model.getChangeLandTypePrefix().equals(MainetConstants.Property.LandType.DIV)) {
            ArrayOfDiversionPlotDetails response = dataEntrySuiteService.getDiversionDetails(dto);
            model.setArrayOfDiversionPlotDetails(response);
        }
        return new ModelAndView("showPropertyKhasaraDetails", MainetConstants.FORM_NAME, model);
    }
    @RequestMapping(params = "viewRoomDetails", method = RequestMethod.POST)
	public ModelAndView addRoomDetails(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			@RequestParam(value = "countOfRow") int countOfRow) {
		getModel().bind(httpServletRequest);
		ProvisionalAssesmentDetailDto detailDto = getModel().getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList()
				.get(countOfRow);
		List<PropertyRoomDetailsDto> roomList = new ArrayList<>();
		for(PropertyRoomDetailsDto room : detailDto.getRoomDetailsDtoList()) {
			room.setRoomTypeDesc(CommonMasterUtility.getNonHierarchicalLookUpObject(room.getRoomType(),
                    UserSession.getCurrent().getOrganisation()).getDescLangFirst());
			roomList.add(room);
		}
		detailDto.setRoomDetailsDtoList(roomList);
		getModel().getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().set(countOfRow, detailDto);
		return new ModelAndView("viewRoomDetails", MainetConstants.FORM_NAME, getModel());
	}
    
    @RequestMapping(params = "downloadSpecialNotice", method = RequestMethod.POST)
	public @ResponseBody String downloadSpecialNoticeBirt(HttpServletRequest request) {
		this.getModel().bind(request);
		Long  notId = noticeMasterService.getNoticeIdByApplicationId(this.getModel().getProvisionalAssesmentMstDto().getApmApplicationId());
		return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL + "=newProperty_registeration.rptdesign&P_NIT_NO=" + notId;
	}
}
