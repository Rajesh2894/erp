package com.abm.mainet.property.ui.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.property.dto.ProvisionalAssesmentFactorDtlDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.service.IProvisionalAssesmentMstService;
import com.abm.mainet.property.service.PropertyAuthorizationService;
import com.abm.mainet.property.service.PropertyBillPaymentService;
import com.abm.mainet.property.service.SelfAssessmentService;
import com.abm.mainet.property.ui.model.NewPropertyRegistrationModel;

@Controller
@RequestMapping("/NewPropertyRegAuthorization.html")
public class NewPropertyRegAuthorizationController extends AbstractFormController<NewPropertyRegistrationModel> {

    @Resource
    private IChallanService iChallanService;

    @Resource
    private IProvisionalAssesmentMstService provisionalAssesmentMstService;

    @Autowired
    private ILocationMasService iLocationMasService;

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
    private IWorkFlowTypeService iWorkFlowTypeService;

    /*
     * load property data for Authorization
     */
    @RequestMapping(params = "showDetails", method = RequestMethod.POST)
    public ModelAndView defaultLoad(@RequestParam("appNo") final long applicationId, @RequestParam("taskId") final long serviceId,
            @RequestParam("actualTaskId") final long taskId, final HttpServletRequest httpServletRequest) throws Exception {
        sessionCleanup(httpServletRequest);
        fileUpload.sessionCleanUpForFileUpload();
        getModel().setLastChecker(iWorkFlowTypeService.isLastTaskInCheckerTaskList(taskId));
        final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        ServiceMaster service = serviceMaster.getServiceMaster(serviceId, orgId);
        Long deptId = service.getTbDepartment().getDpDeptid();

        getModel().setOrgId(orgId);
        getModel().setDeptId(deptId);
        getModel().setNoOfDaysEditFlag(MainetConstants.FlagN);

        final List<ProvisionalAssesmentMstDto> provAssDtoList = provisionalAssesmentMstService
                .getAssesmentMstDtoListByAppId(applicationId, orgId);
        final ProvisionalAssesmentMstDto assMst = propertyAuthorizationService.getAssesmentMstDtoForDisplay(provAssDtoList);
        Date afterAddingDaysToCreatedDate = null;
        try {
        	LookUp propertyNoOfDaysLookUp = CommonMasterUtility.getValueFromPrefixLookUp("D", "NOD",UserSession.getCurrent().getOrganisation());
    		LocalDate convertCreateDateToLocalDate = assMst.getCreatedDate().toInstant()
    				.atZone(ZoneId.systemDefault()).toLocalDate();
    		 afterAddingDaysToCreatedDate = Date.from(
    				convertCreateDateToLocalDate.plusDays(Long.valueOf(propertyNoOfDaysLookUp.getOtherField())).atStartOfDay(ZoneId.systemDefault()).toInstant());
        }catch (Exception e) {
			// TODO: handle exception
		}
        
		if(afterAddingDaysToCreatedDate != null && Utility.compareDate(new Date(),afterAddingDaysToCreatedDate)) {
			 getModel().setNoOfDaysEditFlag(MainetConstants.FlagY);
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
        LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp(ownerType.getLookUpCode(),
                MainetConstants.Property.propPref.OWT,
                UserSession.getCurrent().getOrganisation());
        getModel().setOwnershipTypeValue(lookup.getDescLangFirst());

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
        getModel().setDeptId(deptId);
        getModel().setProvAssMstDtoList(provAssDtoList);
        assMst.setLocationName(iLocationMasService.getLocationNameById(assMst.getLocId(), orgId));
        getModel().setProvisionalAssesmentMstDto(assMst);
        getModel().setDropDownValues();
        getModel().setDocumentList(iChecklistVerificationService.getDocumentUploaded(applicationId, orgId));
        getModel().getWorkflowActionDto().setApplicationId(applicationId);
        getModel().getWorkflowActionDto().setTaskId(taskId);
        getModel().setAuthCompBeforeDto(
                iProvisionalAssesmentMstService.copyProvDtoDataToOtherDto(assMst));// Setting before change property detail
        getModel().setIntgrtionWithBP(CommonMasterUtility
                .getDefaultValueByOrg(MainetConstants.Property.IBP, UserSession.getCurrent().getOrganisation()).getLookUpCode());
        return new ModelAndView("NewPropertyRegistrationView", MainetConstants.FORM_NAME, getModel());

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

    @RequestMapping(params = "openEditPage", method = RequestMethod.POST)
    public ModelAndView openEditPage(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        NewPropertyRegistrationModel model = this.getModel();
        model.setEditFlag(MainetConstants.FlagY);
        return new ModelAndView("NewPropertyRegistrationView", MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(params = "saveAuthorization", method = RequestMethod.POST)
    public ModelAndView saveAuthorization(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        NewPropertyRegistrationModel model = this.getModel();
        model.setAuthEditFlag(MainetConstants.FlagN);
        if (model.saveAuthorization()) {
            return jsonResult(JsonViewObject.successResult(
                    ApplicationSession.getInstance().getMessage("property.Auth.save")));
        }
        return customDefaultMyResult("NewProRegAuthorizationBeforeAfter");
    }

    @RequestMapping(params = "saveAuthorizationWithEdit", method = RequestMethod.POST)
    public ModelAndView saveAuthorizationWithEdit(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        NewPropertyRegistrationModel model = this.getModel();
        model.setAuthEditFlag("Y");
        if (model.saveAuthorization()) {
            return jsonResult(JsonViewObject.successResult(
                    ApplicationSession.getInstance().getMessage("property.Auth.save")));
        }
        return customDefaultMyResult("NewProRegAuthorizationBeforeAfter");
    }

    @RequestMapping(params = "backToFirstLevelAuth", method = RequestMethod.POST)
    public ModelAndView backToFirstLevelAuth(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        return new ModelAndView("NewPropertyRegistrationView", MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(params = "compareBeforeAfterAuth", method = RequestMethod.POST)
    public ModelAndView compareBeforeAfterAuth(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        this.getModel().setDropDownValues();
        getCheckListAndcharges(getModel());
        return new ModelAndView("NewProRegAuthorizationBeforeAfter", MainetConstants.FORM_NAME, getModel());
    }

    private void getCheckListAndcharges(NewPropertyRegistrationModel model) {
        ProvisionalAssesmentMstDto dto = model.getProvisionalAssesmentMstDto();
        // propertyService.setWardZoneDetailByLocId(dto, model.getDeptId(), dto.getLocId());
        selfAssessmentService.setFactorMappingToAssDto(model.getProvAsseFactDtlDto(), dto);
    }
}
