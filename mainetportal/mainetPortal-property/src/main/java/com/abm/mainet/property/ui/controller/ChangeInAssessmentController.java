package com.abm.mainet.property.ui.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.UtilityService;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.property.dto.ArrayOfDiversionPlotDetails;
import com.abm.mainet.property.dto.ArrayOfKhasraDetails;
import com.abm.mainet.property.dto.ArrayOfPlotDetails;
import com.abm.mainet.property.dto.AssessmentChargeCalDTO;
import com.abm.mainet.property.dto.BillDisplayDto;
import com.abm.mainet.property.dto.LandTypeApiDetailRequestDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentDetailDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentFactorDtlDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentOwnerDtlDto;
import com.abm.mainet.property.dto.SelfAssessmentDeaultValueDTO;
import com.abm.mainet.property.service.SelfAssessmentService;
import com.abm.mainet.property.ui.model.SelfAssesmentNewModel;
import com.abm.mainet.property.ui.validator.RuleErrorValidator;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

/**
 * @author Rahul.Yadav
 *
 */
@Controller
@RequestMapping("/ChangeInAssessmentForm.html")
public class ChangeInAssessmentController extends AbstractFormController<SelfAssesmentNewModel> {

    @Autowired
    private SelfAssessmentService selfAssessmentService;

    @Autowired
    private IPortalServiceMasterService serviceMaster;

    @Autowired
    private ISMSAndEmailService ismsAndEmailService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(HttpServletRequest request) throws Exception {
        this.sessionCleanup(request);
        return new ModelAndView("ChangeAssessmentTermCondition", MainetConstants.FORM_NAME, this.getModel());
    }

    @RequestMapping(params = "accept", method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView search(HttpServletRequest request) {
        this.sessionCleanup(request);
        SelfAssesmentNewModel model = this.getModel();
        model.setMobdisabled(true);
        model.setOtpdisabled(true);
        getModel().setAssType(MainetConstants.Property.CHN_IN_ASESS);
        model.setRedirectURL("ChangeInAssessmentForm.html");
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        return new ModelAndView("NoChangeInAssessmentSearch", MainetConstants.FORM_NAME, this.getModel());
    }

    @ResponseBody
    @RequestMapping(params = "SearchPropNo", method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView searchPropNo(HttpServletRequest request) throws Exception {
        this.bindModel(request);
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        SelfAssesmentNewModel model = this.getModel();
        ModelAndView mv = null;
        model.setMobdisabled(true);
        model.setOtpdisabled(true);
        model.setOtp(null);
        mv = new ModelAndView("NoChangeInAssessmentSearchValidn", MainetConstants.FORM_NAME, this.getModel());
        mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
        ProvisionalAssesmentMstDto proAssMas = model.getProvisionalAssesmentMstDto();
        if (StringUtils.isEmpty(proAssMas.getAssOldpropno()) && StringUtils.isEmpty(proAssMas.getAssNo())) {
            model.addValidationError("Property number or Old PID number must not be empty.");          
        } else {
            final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
            SelfAssessmentDeaultValueDTO requestData = new SelfAssessmentDeaultValueDTO();
            requestData.setOrgId(orgId);
            requestData.setProvisionalMas(proAssMas);
            SelfAssessmentDeaultValueDTO response = selfAssessmentService.checkForValidProperty(requestData);
			if (response == null || response.getProvisionalMas() == null) {
				if (!StringUtils.isEmpty(response.getDisplayValidMsg()))
					model.addValidationError(response.getDisplayValidMsg());
				else
					model.addValidationError(getApplicationSession().getMessage("property.ChangeInAss.validPropNo"));
			} else {
				Boolean propertyActiveOrNot = selfAssessmentService.checkWhetherPropertyIsActive(proAssMas.getAssNo(),
						proAssMas.getAssOldpropno(), orgId);
				if (!propertyActiveOrNot) {
					model.addValidationError(ApplicationSession.getInstance().getMessage("property.inactiveProperty"));
				} else {
                model.setDefaultData(response);
                model.setProvisionalAssesmentMstDto(response.getProvisionalMas());
                // model.setMobdisabled(false);
                final String otp = UtilityService.generateRandomNumericCode(MainetConstants.OTP_PRASSWORD_LENGTH);
                model.setOtp(otp);
                sendOTPEmailAndSMS(model, response.getProvisionalMas().getProvisionalAssesmentOwnerDtlDtoList().get(0), otp);
                model.setOtpdisabled(false);
				}
            }
        }
        return mv;
    }

    @ResponseBody
    @RequestMapping(params = "sendOTP", method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView sendOTP(HttpServletRequest request) throws Exception {
        this.bindModel(request);
        ModelAndView mv = new ModelAndView("NoChangeInAssessmentSearchValidn", MainetConstants.FORM_NAME, this.getModel());
        SelfAssesmentNewModel model = this.getModel();
        model.setOtpdisabled(true);
        ProvisionalAssesmentOwnerDtlDto ownDtlDto = model.getProvisionalAssesmentMstDto().getProvisionalAssesmentOwnerDtlDtoList()
                .get(0);
        /* if (ownDtlDto.getAssoMobileno().equals(model.getMobNumber())) { */
        final String otp = UtilityService.generateRandomNumericCode(MainetConstants.OTP_PRASSWORD_LENGTH);
        model.setOtp(otp);
        sendOTPEmailAndSMS(model, ownDtlDto, otp);
        model.setOtpdisabled(false);

        /*
         * } else { model.addValidationError(getApplicationSession().getMessage("property.ChangeInAss.validMobNo"));
         * mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult()); }
         */
        return mv;
    }

    private void sendOTPEmailAndSMS(SelfAssesmentNewModel model, ProvisionalAssesmentOwnerDtlDto ownDtlDto, final String otp) {
        final SMSAndEmailDTO dto = new SMSAndEmailDTO();
        dto.setEmail(model.getProvisionalAssesmentMstDto().getAssEmail());
        dto.setMobnumber(ownDtlDto.getAssoMobileno());
        dto.setAppName(ownDtlDto.getAssoOwnerName());
        dto.setOneTimePassword(otp);
        if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
            dto.setV_muncipality_name(UserSession.getCurrent().getOrganisation().getONlsOrgname());
        } else {
            dto.setV_muncipality_name(UserSession.getCurrent().getOrganisation().getONlsOrgnameMar());
        }
        ismsAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.ONLINE_SERVICE,
                "ChangeInAssessmentForm.html",
                MainetConstants.SMS_EMAIL.OTP_MSG, dto, UserSession.getCurrent().getOrganisation(),
                UserSession.getCurrent().getLanguageId());
    }

    @ResponseBody
    @RequestMapping(params = "ValidatePropNo", method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView validatePropNo(HttpServletRequest request) throws Exception {
        this.bindModel(request);
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        SelfAssesmentNewModel model = this.getModel();
        ModelAndView mv = null;
        mv = new ModelAndView("NoChangeInAssessmentSearchValidn", MainetConstants.FORM_NAME, this.getModel());
        ProvisionalAssesmentMstDto proAssMas = model.getProvisionalAssesmentMstDto();
        if (StringUtils.isEmpty(proAssMas.getAssOldpropno()) && StringUtils.isEmpty(proAssMas.getAssNo())) {
            model.addValidationError("Property number or Old PID number must not be empty.");
            mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
        } else {
            final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

            if (model.getOtp().equals(model.getUserOtp())) {
                ProvisionalAssesmentMstDto assMst = model.getProvisionalAssesmentMstDto();
                setCommonFields(model);
                assMst.setOrgId(orgId);
                assMst.setSmServiceId(model.getServiceId());
                if (assMst.getAssCorrAddress() == null) {
                    assMst.setProAsscheck(MainetConstants.YES);
                } else {
                    assMst.setProAsscheck(MainetConstants.NO);
                }
                if (assMst.getAssLpYear() == null) {
                    assMst.setProAssBillPayment(MainetConstants.Property.NOT_APP);
                } else {
                    assMst.setProAssBillPayment(MainetConstants.Property.MANUAL);
                }
                model.setFinancialYearMap(model.getDefaultData().getFinancialYearMap());
                List<ProvisionalAssesmentFactorDtlDto> factorMapList = factorMappingForView(assMst);
                model.setProvAsseFactDtlDto(factorMapList);

                LookUp ownerType = CommonMasterUtility.getNonHierarchicalLookUpObject(
                        assMst.getAssOwnerType(), UserSession.getCurrent().getOrganisation());
                model.setOwnershipPrefix(ownerType.getLookUpCode());

                if (assMst.getAssLandType() != null) {
                    LookUp landTypePrefix = CommonMasterUtility.getNonHierarchicalLookUpObject(
                            assMst.getAssLandType(), UserSession.getCurrent().getOrganisation());
                    getModel().setLandTypePrefix(landTypePrefix.getLookUpCode());
                }
                LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp(ownerType.getLookUpCode(),
                        MainetConstants.Property.propPref.OWT);
                model.setOwnershipTypeValue(lookup.getDescLangFirst());
                assMst.setProAssOwnerTypeName(CommonMasterUtility
                        .getNonHierarchicalLookUpObject(assMst.getAssOwnerType()).getDescLangFirst());

                assMst.setProAssdRoadfactorDesc(
                        CommonMasterUtility.getNonHierarchicalLookUpObject(assMst.getPropLvlRoadType(),
                                UserSession.getCurrent().getOrganisation()).getDescLangFirst());
                this.getModel().setProvisionalAssesmentMstDto(assMst);
                if (getModel().getLandTypePrefix() != null) {
                    setlandTypeDetails(getModel());
                }

                this.getModel().setFinYearList(assMst.getFinancialYearList());
                model.setDropDownValues();
                model.getDefaultData().setProvisionalMas(assMst);
                model.getDefaultData().setDeptId(model.getDeptId());
                SelfAssessmentDeaultValueDTO rsponse = selfAssessmentService.fetchAllLastPaymentDetails(model.getDefaultData());
                assMst = rsponse.getProvisionalMas();
                setFactorMap(assMst);
                model.setProvisionalAssesmentMstDto(assMst);
                return new ModelAndView("ChangeInAssessmentForm", MainetConstants.FORM_NAME, getModel());
            } else {
                model.addValidationError(
                        getApplicationSession().getMessage("property.ChangeInAss.validOtp"));
                mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
            }
        }
        return mv;
    }

    private void setlandTypeDetails(SelfAssesmentNewModel model) {
        LandTypeApiDetailRequestDto dto = new LandTypeApiDetailRequestDto();
        dto.setLandType(model.getLandTypePrefix());
        dto.setDistrictId(model.getProvisionalAssesmentMstDto().getAssDistrict());
        dto.setTehsilId(model.getProvisionalAssesmentMstDto().getAssTahasil());
        dto.setVillageId(model.getProvisionalAssesmentMstDto().getTppVillageMauja());
        dto.setMohallaId(model.getProvisionalAssesmentMstDto().getMohalla());
        model.setDistrict(selfAssessmentService.findDistrictByLandType(dto));
        model.setTehsil(selfAssessmentService.getTehsilListByDistrict(dto));
        model.setVillage(selfAssessmentService.getVillageListByTehsil(dto));
        if (model.getLandTypePrefix().equals(MainetConstants.Property.NZL)
                || model.getLandTypePrefix().equals(MainetConstants.Property.DIV)) {
            getModel().setMohalla(selfAssessmentService.getMohallaListByVillageId(dto));
            getModel().setBlockStreet(selfAssessmentService.getStreetListByMohallaId(dto));
        }
    }

    private void setFactorMap(final ProvisionalAssesmentMstDto assMst) {
        AtomicInteger count = new AtomicInteger(0);
        Map<String, List<ProvisionalAssesmentFactorDtlDto>> factorMap = new HashMap<>();
        this.getModel().getUnitNoList().clear();
        assMst.getProvisionalAssesmentDetailDtoList().forEach(propDet -> {
            count.getAndIncrement();
            this.getModel().getUnitNoList().add(count.toString());
            propDet.getProvisionalAssesmentFactorDtlDtoList().forEach(fact -> {
                List<ProvisionalAssesmentFactorDtlDto> factList1 = factorMap.get(fact.getAssfFactorId().toString());
                if (factList1 != null && !factList1.isEmpty()) {
                    fact.setUnitNoFact(count.toString());
                    factList1.add(fact);
                } else {
                    factList1 = new ArrayList<>();
                    fact.setUnitNoFact(count.toString());
                    factList1.add(fact);
                }
                factorMap.put(fact.getAssfFactorId().toString(), factList1);
            });
        });

        List<LookUp> lookupList = CommonMasterUtility.getListLookup("FCT",
                UserSession.getCurrent().getOrganisation());
        lookupList.forEach(fact -> {
            List<ProvisionalAssesmentFactorDtlDto> factList = factorMap.get(Long.toString(fact.getLookUpId()));
            if (factList == null || factList.isEmpty()) {
                factorMap.put(Long.toString(fact.getLookUpId()), null);
            }
        });
        this.getModel().setDisplayfactorMap(factorMap);
    }

    private List<ProvisionalAssesmentFactorDtlDto> factorMappingForView(final ProvisionalAssesmentMstDto assMst) {
        List<ProvisionalAssesmentFactorDtlDto> factorMap = new ArrayList<>();
        assMst.getProvisionalAssesmentDetailDtoList().forEach(propDet -> {
            propDet.getProvisionalAssesmentFactorDtlDtoList().forEach(fact -> {
                fact.setUnitNoFact(propDet.getAssdUnitNo().toString());
                fact.setProAssfFactorIdDesc(CommonMasterUtility
                        .getNonHierarchicalLookUpObject(fact.getAssfFactorId(), UserSession.getCurrent().getOrganisation())
                        .getDescLangFirst());
                fact.setProAssfFactorValueDesc(CommonMasterUtility
                        .getNonHierarchicalLookUpObject(fact.getAssfFactorValueId(),
                                UserSession.getCurrent().getOrganisation())
                        .getDescLangFirst());
                fact.setFactorValueCode(CommonMasterUtility
                        .getNonHierarchicalLookUpObject(fact.getAssfFactorId(), UserSession.getCurrent().getOrganisation())
                        .getLookUpCode());
                factorMap.add(fact);
                assMst.getProAssfactor().add(MainetConstants.YES);
            });

        });
        return factorMap;
    }

    private void setCommonFields(SelfAssesmentNewModel model) {
        getModel().setAssType(MainetConstants.Property.CHN_IN_ASESS);
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final Long serviceId = serviceMaster.getServiceId(MainetConstants.Property.serviceShortCode.CIA, orgId);
        final Long deptId = serviceMaster.getServiceDeptIdId(serviceId);
        model.setOrgId(orgId);
        model.setDeptId(deptId);
        model.setServiceId(serviceId);
        model.setIntgrtionWithBP(CommonMasterUtility
                .getDefaultValueByOrg(MainetConstants.Property.IBP, UserSession.getCurrent().getOrganisation()).getLookUpCode());
        String lookUpCode = CommonMasterUtility
                .getDefaultValueByOrg(MainetConstants.Property.APP, UserSession.getCurrent().getOrganisation()).getLookUpCode();
        model.getProvisionalAssesmentMstDto().setPartialAdvancePayStatus(lookUpCode);
    }

    @RequestMapping(params = "proceed", method = RequestMethod.POST)
    public ModelAndView confirmToProceed(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        SelfAssesmentNewModel model = this.getModel();
        this.getModel().setDropDownValues();
        getCheckListAndcharges(model);
        // D#18545 Error MSG for Rule found or not
		model.validateBean(model.getProvisionalAssesmentMstDto(), RuleErrorValidator.class);
		ModelAndView mv = null;
		if (model.hasValidationErrors()) {
			//return defaultMyResult();
			mv = new ModelAndView("ChangeInAssessmentForm", MainetConstants.FORM_NAME, this.getModel());
			mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
            return mv;
		}else {
			return new ModelAndView("SelfAssessmentView", MainetConstants.FORM_NAME, model);	
		}
        
    }

    private void getCheckListAndcharges(SelfAssesmentNewModel model) {
        ProvisionalAssesmentMstDto dto = model.getProvisionalAssesmentMstDto();
        setFactorMappingToAssDto(model.getProvAsseFactDtlDto(), dto);
        setFactorMap(dto);
        dto.setOrgId(model.getOrgId());
        dto.setSmServiceId(model.getServiceId());
        model.getDefaultData().setProvisionalMas(dto);
        model.getDefaultData().setDeptId(model.getDeptId());
        model.getDefaultData().setProvAsseFactDtlDto(model.getProvAsseFactDtlDto());
        model.getDefaultData().setFinYearList(model.getFinYearList());
        AssessmentChargeCalDTO checklistCharge = selfAssessmentService
                .fetchChecklistAndChargesForChangeAndNoChange(model.getDefaultData());
        model.setDemandLevelRebateList(checklistCharge.getDemandLevelRebateList());
        model.setCheckList(checklistCharge.getCheckList());
        
        Date assesmentDate = checklistCharge.getProvisionalMas().getProvisionalAssesmentDetailDtoList().get(0).getAssdAssesmentDate();
        checklistCharge.getProvisionalMas().getProvisionalAssesmentDetailDtoList().forEach(unitDetails ->{
        	unitDetails.setLastAssesmentDate(assesmentDate);
        	if(unitDetails.getLastAssesmentDate() != null) {
        		unitDetails.setLastAssesmentStringDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT)
                        .format(unitDetails.getLastAssesmentDate()));
        	}
        });
        /*
         * AssessmentChargeCalDTO arrearsRequest = new AssessmentChargeCalDTO(); arrearsRequest.setProvisionalMas(dto);
         * arrearsRequest.setBillMasList(checklistCharge.getBillMasList());
         */
        // checklistCharge = selfAssessmentService.calculateArrearsAndTaxForward(checklistCharge);
        this.getModel().setProvisionalAssesmentMstDto(checklistCharge.getProvisionalMas());
        
        
        model.setBillMasList(checklistCharge.getBillMasList());
        Map<String, List<BillDisplayDto>> displayMap = selfAssessmentService.getTaxMapForDisplayCategoryWise(
                checklistCharge.getDisplayDto(),
                UserSession.getCurrent().getOrganisation(), model.getDeptId(), checklistCharge.getTaxCatList());
        model.setDisplayMap(displayMap);
    }

    public void setFactorMappingToAssDto(List<ProvisionalAssesmentFactorDtlDto> provFactList, ProvisionalAssesmentMstDto dto) {
        // Factor and Unit Binding
        provFactList.forEach(factDtlDto -> {
            if (factDtlDto.getAssfFactorValueId() != null) {
                if (factDtlDto.getUnitNoFact().equals(MainetConstants.Property.ALL)) {
                    dto.getProvisionalAssesmentDetailDtoList().forEach(detDto -> {
                        addFactorToDto(factDtlDto, detDto);
                    });
                } else {
                    dto.getProvisionalAssesmentDetailDtoList().forEach(detDto -> {
                        if (detDto.getAssdUnitNo().toString().equals(factDtlDto.getUnitNoFact())) {
                            addFactorToDto(factDtlDto, detDto);
                        }
                    });
                }
            }
        });
    }

    private void addFactorToDto(ProvisionalAssesmentFactorDtlDto factDtlDto, ProvisionalAssesmentDetailDto detDto) {
        if (factDtlDto.getProAssfId() == 0) {
            // New factor added
            boolean result = detDto.getProvisionalAssesmentFactorDtlDtoList().stream()
                    .anyMatch(fact -> fact.getAssfFactorValueId().equals(factDtlDto.getAssfFactorValueId()));
            if (!result) {
                detDto.getProvisionalAssesmentFactorDtlDtoList().add(factDtlDto);
            } else {
                detDto.getProvisionalAssesmentFactorDtlDtoList().stream()
                        .filter(fact -> fact.getAssfFactorValueId().equals(factDtlDto.getAssfFactorValueId()))
                        .forEach(fact -> {
                            fact.setAssfFactorId(factDtlDto.getAssfFactorId());
                            fact.setAssfFactorValueId(factDtlDto.getAssfFactorValueId());
                        });
            }
        } else {
            // Existing factor if value change
            detDto.getProvisionalAssesmentFactorDtlDtoList().stream()
                    .filter(fact -> fact.getProAssfId() == factDtlDto.getProAssfId())
                    .forEach(fact -> {
                        fact.setAssfFactorId(factDtlDto.getAssfFactorId());
                        fact.setAssfFactorValueId(factDtlDto.getAssfFactorValueId());
                    });
        }
    }

    @RequestMapping(params = "editChangeAssForm", method = RequestMethod.POST)
    public ModelAndView editForm(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        getModel().setSelfAss(null);
        SelfAssesmentNewModel model = this.getModel();
        Long max = 0l;
        List<ProvisionalAssesmentDetailDto> detailDto = model.getProvisionalAssesmentMstDto()
                .getProvisionalAssesmentDetailDtoList();
        if (detailDto != null && !detailDto.isEmpty()) {
            for (ProvisionalAssesmentDetailDto dto : detailDto) {
                if (dto.getAssdUnitNo() > max) {
                    max = dto.getAssdUnitNo();
                }
            }
        }
        model.setMaxUnit(max);
        model.getProvAsseFactDtlDto().clear();
        return new ModelAndView("ChangeInAssessmentForm", MainetConstants.FORM_NAME, model);
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
        if (model.getLandTypePrefix().equals(MainetConstants.Property.KPK)) {
            ArrayOfKhasraDetails response = selfAssessmentService.getKhasraDetails(dto);
            getModel().setArrayOfKhasraDetails(response);
        }

        else if (model.getLandTypePrefix().equals(MainetConstants.Property.NZL)) {
            ArrayOfPlotDetails response = selfAssessmentService.getNajoolDetails(dto);
            model.setArrayOfPlotDetails(response);
        } else if (model.getLandTypePrefix().equals(MainetConstants.Property.DIV)) {
            ArrayOfDiversionPlotDetails response = selfAssessmentService.getDiversionDetails(dto);
            model.setArrayOfDiversionPlotDetails(response);
        }

        return new ModelAndView("showPropertyKhasaraDetails", MainetConstants.FORM_NAME, model);
    }

}