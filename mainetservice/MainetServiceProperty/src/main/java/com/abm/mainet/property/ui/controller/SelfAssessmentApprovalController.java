package com.abm.mainet.property.ui.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.cfc.objection.dto.HearingInspectionDto;
import com.abm.mainet.cfc.objection.dto.ObjectionDetailsDto;
import com.abm.mainet.cfc.objection.service.HearingAndInspectionService;
import com.abm.mainet.cfc.objection.service.IObjectionDetailsService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.IReceiptEntryService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.UserTaskDTO;
import com.abm.mainet.common.workflow.service.IWorkflowTaskService;
import com.abm.mainet.property.dto.BillDisplayDto;
import com.abm.mainet.property.dto.LandTypeApiDetailRequestDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentFactorDtlDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.service.AssesmentMastService;
import com.abm.mainet.property.service.DataEntrySuiteService;
import com.abm.mainet.property.service.IProvisionalAssesmentMstService;
import com.abm.mainet.property.service.PropertyAuthorizationService;
import com.abm.mainet.property.service.PropertyBillPaymentService;
import com.abm.mainet.property.service.PropertyMainBillService;
import com.abm.mainet.property.service.PropertyService;
import com.abm.mainet.property.service.SelfAssessmentService;
import com.abm.mainet.property.ui.model.SelfAssesmentNewModel;

@Controller
@RequestMapping("/SelfAssessmentApproval.html")
public class SelfAssessmentApprovalController extends AbstractFormController<SelfAssesmentNewModel> {

    @Resource
    private IChallanService iChallanService;

    @Autowired
    private ILocationMasService iLocationMasService;

    @Autowired
    private IObjectionDetailsService iObjectionDetailsService;

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
    private IProvisionalAssesmentMstService iProvisionalAssesmentMstService;

    @Autowired
    private IReceiptEntryService iReceiptEntryService;

    @Autowired
    private PropertyMainBillService propertyMainBillService;

    @Autowired
    private DataEntrySuiteService dataEntrySuiteService;

    @Autowired
    private AssesmentMastService assesmentMastService;

    @Autowired
    private IWorkflowTaskService iWorkflowTaskService;
      
    @Autowired
	private HearingAndInspectionService hearingAndInspectionService;

    @RequestMapping(params = "showDetails", method = RequestMethod.POST)
    public ModelAndView defaultLoad(@RequestParam("appNo") final String refNo, @RequestParam("taskId") final long serviceId,
            @RequestParam("actualTaskId") final long taskId, final HttpServletRequest httpServletRequest) throws Exception {
        sessionCleanup(httpServletRequest);
        fileUpload.sessionCleanUpForFileUpload();
        getModel().setLastChecker(true);
        final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        ServiceMaster service = serviceMaster.getServiceMaster(serviceId, orgId);
        getModel().setServiceType(service.getSmShortdesc());
        Long deptId = service.getTbDepartment().getDpDeptid();
        UserTaskDTO userDto = iWorkflowTaskService.findByTaskId(taskId);
        ProvisionalAssesmentMstDto assMst = null;
        List<TbBillMas> billMasList = null;
        List<ProvisionalAssesmentMstDto> provAssDtoList = null;
        ObjectionDetailsDto objDto = null;
        ObjectionDetailsDto objectionDto = null;
        if (userDto.getReferenceId() != null) {
            // ref id(obj no) is null when zero Objection file
            objDto = iObjectionDetailsService.getObjectionDetailByObjNo(orgId, userDto.getReferenceId());
            objectionDto = objDto;
        }
        /// 118973    
        if(userDto.getApplicationId() != null && userDto.getApplicationId() > 0) {
        	List<ObjectionDetailsDto> objDtoList = iObjectionDetailsService.getObjectionDetailListByAppId(orgId, serviceId, userDto.getApplicationId());
    		 objectionDto = objDtoList.get(objDtoList.size()-1);
        }
        
		HearingInspectionDto hearingInsDto =null;
		if (objectionDto != null) {
			hearingInsDto = hearingAndInspectionService.getHearingDetailByObjId(orgId, objectionDto.getObjectionId());
			if (null != hearingInsDto && null != hearingInsDto.getDecisionInFavorOf()) {
				this.getModel().setHearingDecisionFlag(
						CommonMasterUtility.getNonHierarchicalLookUpObject(hearingInsDto.getDecisionInFavorOf(),
								UserSession.getCurrent().getOrganisation()).getLookUpCode());
			}
		}
        //
		if(objectionDto!=null && objectionDto.getObjectionOn()!=null) {
			String objectionOn = CommonMasterUtility.getNonHierarchicalLookUpObject(objectionDto.getObjectionOn(),
					UserSession.getCurrent().getOrganisation()).getLookUpCode();
			if(StringUtils.isNotBlank(objectionOn) && objectionOn.equals(MainetConstants.Objection.ObjectionOn.NOTICE)) {
				provAssDtoList = assesmentMastService
	                    .getAssesmentMstDtoListByAppId(Long.valueOf(refNo), orgId);

	            //List<Long> assIds = provAssDtoList.stream().map(ProvisionalAssesmentMstDto::getProAssId).collect(Collectors.toList());
	            assMst = propertyAuthorizationService.getAssesmentMstDtoForDisplay(provAssDtoList);
	            if (assMst.getBillMethod() != null && MainetConstants.FlagI.equals(CommonMasterUtility
	    				.getNonHierarchicalLookUpObject(assMst.getBillMethod(), UserSession.getCurrent().getOrganisation())
	    				.getLookUpCode())) {
	            	billMasList = propertyMainBillService.fetchAllBillByPropNoAndFlatNo(assMst.getAssNo(),assMst.getFlatNo(),
							orgId);	    			
	    		} else {
	    			billMasList = propertyMainBillService.fetchAllBillByPropNo(assMst.getAssNo(),
							orgId);	    			
	    		}
	            //billMasList = propertyMainBillService.fetchAllBillByPropNoAndAssIds(assMst.getAssNo(), assIds, orgId);
			}else if(StringUtils.isNotBlank(objectionOn) && objectionOn.equals(MainetConstants.Objection.ObjectionOn.BILL)) {
				// Objection on bill case
	            billMasList = propertyMainBillService.fetchBillByBillNoAndPropertyNo(objDto.getObjectionReferenceNumber(),
	                    objDto.getBillNo().toString(), orgId);
	            List<Long> assIds = billMasList.stream().map(TbBillMas::getAssId).collect(Collectors.toList());
				/*
				 * if(assIds != null) { provAssDtoList =
				 * assesmentMastService.getAssessmentMstListByAssIds(assIds, orgId); }else {
				 */
	            	provAssDtoList = assesmentMastService.getPropDetailFromAssByPropNo(orgId, billMasList.get(0).getPropNo());
	            //}
	            assMst = provAssDtoList.get(0);
			}
		}
		else if (userDto.getReferenceId() == null || (objDto != null && objDto.getApmApplicationId() != null)) {
            // Objection on notice case(with and without objection file)
            provAssDtoList = assesmentMastService
                    .getAssesmentMstDtoListByAppId(Long.valueOf(refNo), orgId);

            List<Long> assIds = provAssDtoList.stream().map(ProvisionalAssesmentMstDto::getProAssId).collect(Collectors.toList());
            assMst = propertyAuthorizationService.getAssesmentMstDtoForDisplay(provAssDtoList);
            billMasList = propertyMainBillService.fetchAllBillByPropNoAndAssIds(assMst.getAssNo(), assIds, orgId);

        } else {
            // Objection on bill case
            billMasList = propertyMainBillService.fetchBillByBillNoAndPropertyNo(objDto.getObjectionReferenceNumber(),
                    objDto.getBillNo().toString(), orgId);
            List<Long> assIds = billMasList.stream().map(TbBillMas::getAssId).collect(Collectors.toList());
            provAssDtoList = assesmentMastService.getAssessmentMstListByAssIds(assIds, orgId);
            assMst = provAssDtoList.get(0);
        }
        LookUp billDeletionInactive = null;
        try {
        	billDeletionInactive = CommonMasterUtility.getValueFromPrefixLookUp("BDI", "ENV", UserSession.getCurrent().getOrganisation());
        }catch (Exception e) {
        	
		}
        final Long applicationId = assMst.getApmApplicationId();

        BigDecimal billPaidAmt = iReceiptEntryService.getPaidAmountByAppNo(orgId, applicationId, deptId);
        if((billDeletionInactive != null && org.apache.commons.lang.StringUtils.isNotBlank(billDeletionInactive.getOtherField())
				&& org.apache.commons.lang.StringUtils.equals(billDeletionInactive.getOtherField(), MainetConstants.FlagY))) {
        	billPaidAmt = ApplicationContextProvider.getApplicationContext().getBean(IReceiptEntryService.class)
					.getPaidAmountByAdditionalRefNoIncRebate(UserSession.getCurrent().getOrganisation().getOrgid(),
							String.valueOf(assMst.getAssNo()), deptId);
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
        LookUp ownerType = CommonMasterUtility.getNonHierarchicalLookUpObject(
                assMst.getAssOwnerType(), UserSession.getCurrent().getOrganisation());
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
        getModel().setOwnershipTypeValue(lookup.getDescLangFirst());

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
            Long finYearId = iFinancialYear.getFinanceYearId(new Date());
            BillDisplayDto surCharge = propertyService.calculateSurchargeAtAuthorizationEdit(
                    UserSession.getCurrent().getOrganisation(),
                    getModel().getDeptId(), billMasList, null,
                    MainetConstants.Property.SURCHARGE, finYearId);
            if (surCharge != null && surCharge.getTotalTaxAmt() != null) {
                billDisDtoList.add(surCharge);
            }
            assMst.setBillTotalAmt(propertyService.getTotalActualAmount(billMasList, null, rebateMap, surCharge));
            getModel().setDisplayMap(propertyService.getTaxMapForDisplayCategoryWise(billDisDtoList,
                    UserSession.getCurrent().getOrganisation(), deptId));// Setting map for tax Display on UI
        }
        Long finYearId = iFinancialYear.getFinanceYearId(assMst.getAssAcqDate());
        List<FinancialYear> financialYearList = iFinancialYear
                .getFinanceYearListFromGivenDate(UserSession.getCurrent().getOrganisation().getOrgid(), finYearId, new Date());
        String financialYear = null;
        for (final FinancialYear finYearTemp : financialYearList) {
            financialYear = Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate());
            getModel().getFinancialYearMap().put(finYearTemp.getFaYear(), financialYear);
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
        for (LookUp factQuest : lookupList) {
            boolean result = factorMapBefore.stream()
                    .anyMatch(fact -> factQuest.getLookUpId() == fact.getAssfFactorId().longValue());
            if (result) {
                assMst.getProAssfactor().add(MainetConstants.FlagY);
            } else {
                assMst.getProAssfactor().add(MainetConstants.FlagN);
            }
        }

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
        getModel().setReferenceid(userDto.getReferenceId());
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
        getModel().setApprovalFlag(MainetConstants.Property.APPROVAL);
        if (getModel().getAuthCompBeforeDto().getAssLandType() != null) {
            LookUp landTypePrefix = CommonMasterUtility.getNonHierarchicalLookUpObject(
                    getModel().getAuthCompBeforeDto().getAssLandType(), UserSession.getCurrent().getOrganisation());
            getModel().setOldLandTypePrefix(landTypePrefix.getLookUpCode());
        }

        getModel().setIntgrtionWithBP(CommonMasterUtility.getDefaultValueByOrg(MainetConstants.Property.IBP,
                UserSession.getCurrent().getOrganisation()).getLookUpCode());
        return new ModelAndView("SelfAssessmentView", MainetConstants.FORM_NAME, getModel());

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

}
