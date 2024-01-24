package com.abm.mainet.property.ui.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.BillReceiptPostingDTO;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.repository.ReceiptRepository;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.property.domain.AsExcessAmtEntity;
import com.abm.mainet.property.dto.BillDisplayDto;
import com.abm.mainet.property.dto.BillPresentAndCalculationDto;
import com.abm.mainet.property.dto.LandTypeApiDetailRequestDto;
import com.abm.mainet.property.dto.PropertyTransferMasterDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentFactorDtlDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.service.AsExecssAmtService;
import com.abm.mainet.property.service.AssesmentMastService;
import com.abm.mainet.property.service.DataEntrySuiteService;
import com.abm.mainet.property.service.IProvisionalAssesmentMstService;
import com.abm.mainet.property.service.MutationService;
import com.abm.mainet.property.service.PropertyBRMSService;
import com.abm.mainet.property.service.PropertyMainBillService;
import com.abm.mainet.property.service.PropertyService;
import com.abm.mainet.property.service.SelfAssessmentService;
import com.abm.mainet.property.ui.model.SelfAssesmentNewModel;
import com.abm.mainet.property.ui.validator.RuleErrorValidator;

@Controller
@RequestMapping("NoChangeInAssessment.html")
public class NoChangeInAssessmentController extends AbstractFormController<SelfAssesmentNewModel> {

    private static final Logger LOGGER = Logger.getLogger(NoChangeInAssessmentController.class);
    @Autowired
    private IFileUploadService fileUpload;

    @Autowired
    private ILocationMasService iLocationMasService;

    @Autowired
    private IFinancialYearService iFinancialYear;

    @Resource
    private IProvisionalAssesmentMstService provisionalAssesmentMstService;

    @Autowired
    private AssesmentMastService assesmentMastService;

    @Autowired
    private PropertyService propertyService;

    @Resource
    private SelfAssessmentService selfAssessmentService;

    @Autowired
    private PropertyBRMSService propertyBRMSService;

    @Autowired
    private ServiceMasterService serviceMaster;

    @Autowired
    private PropertyMainBillService propertyMainBillService;

    @Autowired
    private DataEntrySuiteService dataEntrySuiteService;

    @Autowired
    private AsExecssAmtService asExecssAmtService;

    @Autowired
    private MutationService mutationService;
    
    @Autowired
    private IWorkflowRequestService workflowReqService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(HttpServletRequest request) {
        this.sessionCleanup(request);
        SelfAssesmentNewModel model = this.getModel();
        fileUpload.sessionCleanUpForFileUpload();
        model.setCommonHelpDocs("NoChangeInAssessment.html");
        return new ModelAndView("NoChangeInAssessmentSearch", MainetConstants.FORM_NAME, this.getModel());
    }

    @ResponseBody
    @RequestMapping(params = "SearchPropNo", method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView searchPropNo(HttpServletRequest request) throws Exception {
        this.bindModel(request);
        fileUpload.sessionCleanUpForFileUpload();
        SelfAssesmentNewModel model = this.getModel();
        ModelAndView mv = null;
        mv = new ModelAndView("NoChangeInAssessmentSearchValidn", MainetConstants.FORM_NAME, this.getModel());
        ProvisionalAssesmentMstDto proAssMas = model.getProvisionalAssesmentMstDto();
        // D#109397 - To check mutation is already in progress or not
        try {
            PropertyTransferMasterDto transferMasDto = mutationService.getMutationByPropNo(proAssMas.getAssNo(),
                    UserSession.getCurrent().getOrganisation().getOrgid());
            if (transferMasDto.getApmApplicationId() != null) {
                String status = mutationService.getWorkflowRequestByAppId(transferMasDto.getApmApplicationId(),
                        UserSession.getCurrent().getOrganisation().getOrgid());
                if (MainetConstants.TASK_STATUS_PENDING.equalsIgnoreCase(status)) {
                    getModel().addValidationError(
                            ApplicationSession.getInstance().getMessage("property.validation.mutaion.process"));
                    mv = new ModelAndView("NoChangeInAssessmentSearchValidn", MainetConstants.FORM_NAME,
                            this.getModel());
                    mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
                            getModel().getBindingResult());
                    return mv;
                }
            }
        } catch (Exception e) {
            LOGGER.error("Exception occured" + e);
        }
        // end
        // D#34185
        List<String> checkActiveFlagList = assesmentMastService.checkActiveFlag(proAssMas.getAssNo(),
                UserSession.getCurrent().getOrganisation().getOrgid());

        if (model.getAssesmentManualDate() != null) {
            Long finYearId = iFinancialYear.getFinanceYearId(new Date());
            FinancialYear financialYear = iFinancialYear.getFinincialYearsById(finYearId,
                    UserSession.getCurrent().getOrganisation().getOrgid());

            if (!(model.getAssesmentManualDate().compareTo(financialYear.getFaFromDate()) >= 0
                    && model.getAssesmentManualDate().compareTo(financialYear.getFaToDate()) <= 0)) {
                getModel().addValidationError("Manual receipt date should exist betweeen current financial year");
                mv = new ModelAndView("NoChangeInAssessmentSearchValidn", MainetConstants.FORM_NAME, this.getModel());
                mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
                return mv;
            } else if (Utility.compareDate(new Date(), model.getAssesmentManualDate())) {
                getModel().addValidationError("Manual receipt date cannot be greater than current date");
                mv = new ModelAndView("NoChangeInAssessmentSearchValidn", MainetConstants.FORM_NAME, this.getModel());
                mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
                return mv;
            }
        }
        
        List<ProvisionalAssesmentMstDto> assList = assesmentMastService.getPropDetailFromMainAssByPropNoOrOldPropNo(
				UserSession.getCurrent().getOrganisation().getOrgid(), proAssMas.getAssNo(),
				proAssMas.getAssOldpropno());
		if (CollectionUtils.isNotEmpty(assList)) {
			ProvisionalAssesmentMstDto assesMast = assList.get(assList.size() - 1);
			if (assesMast != null
					&& org.apache.commons.lang.StringUtils.equals(assesMast.getIsGroup(), MainetConstants.FlagY)) {
				getModel().addValidationError(
						ApplicationSession.getInstance().getMessage("property.changeInAssessGrpValidn"));
				mv = new ModelAndView("NoChangeInAssessmentSearchValidn", MainetConstants.FORM_NAME, this.getModel());
				mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
				return mv;
			}
		}
		if (CollectionUtils.isNotEmpty(checkActiveFlagList)) {
			String checkActiveFlag = checkActiveFlagList.get(checkActiveFlagList.size() - 1);
			if (org.apache.commons.lang.StringUtils.equals(checkActiveFlag, MainetConstants.STATUS.INACTIVE)) {
				getModel().addValidationError("This property is Inactive");
				mv = new ModelAndView("NoChangeInAssessmentSearchValidn", MainetConstants.FORM_NAME, this.getModel());
				mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
				return mv;
			}			
		}
        if (StringUtils.isEmpty(proAssMas.getAssOldpropno()) && StringUtils.isEmpty(proAssMas.getAssNo())) {
            model.addValidationError("Property number or Old PID number must not be empty.");
            mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
        }		
		else {
            final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
            Long finYearId = iFinancialYear.getFinanceYearId(new Date());
            ServiceMaster service = serviceMaster.getServiceByShortName(MainetConstants.Property.serviceShortCode.NCA, orgId);
            Long serviceId = null;
            if (service != null) {
                serviceId = service.getSmServiceId();
            }
            boolean assessment = selfAssessmentService.CheckForAssesmentFiledForCurrYear(orgId,
                    proAssMas.getAssNo(), proAssMas.getAssOldpropno(), MainetConstants.STATUS.ACTIVE, finYearId);
            if (!assessment) {
            	
            	//122894 - To check application already in progress
            	List<ProvisionalAssesmentMstDto> provAssesMastList = provisionalAssesmentMstService
                        .getPropDetailByPropNoOnly(proAssMas.getAssNo());
            	if (CollectionUtils.isNotEmpty(provAssesMastList)) {
            		ProvisionalAssesmentMstDto provAssesMstDto = provAssesMastList.get(provAssesMastList.size() - 1);
            		
    				if (provAssesMstDto!=null && provAssesMstDto.getApmApplicationId() != null) {
    					WorkflowRequest workflowRequest = workflowReqService
    							.getWorkflowRequestByAppIdOrRefId(provAssesMstDto.getApmApplicationId(), null, orgId);
    					if (workflowRequest !=null && MainetConstants.WorkFlow.Status.PENDING.equalsIgnoreCase(workflowRequest.getStatus())) {
    						getModel().addValidationError("Assessment against property number " + proAssMas.getAssNo()
    								+ " is already in progress ");
    						ModelAndView modelview = new ModelAndView("NoChangeInAssessmentSearchValidn",
    								MainetConstants.FORM_NAME, this.getModel());
    						modelview.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
    								getModel().getBindingResult());
    						return modelview;
    					}
    				}                   
            	}
            	//
                final ProvisionalAssesmentMstDto assMst = assesmentMastService.getPropDetailByAssNoOrOldPropNo(orgId,
                        proAssMas.getAssNo(), proAssMas.getAssOldpropno(),
                        MainetConstants.STATUS.ACTIVE,null);
                if (assMst != null) {
                    setCommonFields(model);
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
                    List<ProvisionalAssesmentFactorDtlDto> factorMap = factorMappingForView(assMst);
                    model.setProvAsseFactDtlDto(factorMap);
                    assMst.setLocationName(iLocationMasService.getLocationNameById(assMst.getLocId(), orgId));
                    List<Long> finYearList = new ArrayList<>();

                    List<FinancialYear> financialYearList = null;
                    /*if (serviceId != null && serviceId.equals(assMst.getSmServiceId())) {
                        financialYearList = iFinancialYear.getFinanceYearListFromGivenDate(orgId, assMst.getFaYearId(),
                                new Date());
                    } else {*/
                        financialYearList = iFinancialYear
                                .getFinanceYearListAfterGivenDate(UserSession.getCurrent().getOrganisation().getOrgid(),
                                        assMst.getFaYearId(),
                                        new Date());
                    //}
                    if (!financialYearList.isEmpty()) {
                        String financialYear = null;
                        for (final FinancialYear finYearTemp : financialYearList) {
                            financialYear = Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate());
                            getModel().getFinancialYearMap().put(finYearTemp.getFaYear(), financialYear);
                        }
                        propertyService.setUnitDetailsForNextYears(assMst, finYearList, financialYearList);
                    }
                    LookUp ownerType = CommonMasterUtility.getNonHierarchicalLookUpObject(
                            assMst.getAssOwnerType(), UserSession.getCurrent().getOrganisation());
                    model.setOwnershipPrefix(ownerType.getLookUpCode());
                    assMst.setProAssOwnerTypeName(CommonMasterUtility
                            .getNonHierarchicalLookUpObject(assMst.getAssOwnerType(), UserSession.getCurrent().getOrganisation())
                            .getDescLangFirst());
                    if (assMst.getAssLandType() != null && assMst.getAssLandType() > 0) {
                        LookUp landTypePrefix = CommonMasterUtility.getNonHierarchicalLookUpObject(
                                assMst.getAssLandType(), UserSession.getCurrent().getOrganisation());
                        getModel().setLandTypePrefix(landTypePrefix.getLookUpCode());
                    }
                    this.getModel().setProvisionalAssesmentMstDto(assMst);
                    if (getModel().getLandTypePrefix() != null) {
                        setlandTypeDetails(getModel());
                    }
                    model.getDistrict().forEach(dis -> {
                        if (Long.valueOf(dis.getLookUpId()).toString().equals(assMst.getAssDistrict())) {
                            assMst.setAssDistrictDesc(dis.getDescLangFirst());
                        }
                    });

                    model.getTehsil().forEach(teh -> {
                        if (teh.getLookUpCode().equals(assMst.getAssTahasil())) {
                            assMst.setAssTahasilDesc(teh.getDescLangFirst());
                        }
                    });

                    model.getVillage().forEach(vil -> {
                        if (vil.getLookUpCode().equals(assMst.getTppVillageMauja())) {
                            assMst.setTppVillageMaujaDesc(vil.getDescLangFirst());
                        }
                    });

                    for (LookUp moh : model.getMohalla()) {
                        if (moh.getLookUpCode().equals(assMst.getMohalla())) {
                            assMst.setMohallaDesc(moh.getDescLangFirst());
                            break;
                        }
                    }

                    for (LookUp sheet : model.getBlockStreet()) {
                        if (sheet.getLookUpCode().equals(assMst.getAssStreetNo())) {
                            assMst.setAssStreetNoDesc(sheet.getDescLangFirst());
                            break;
                        }
                    }
                    this.getModel().setFinYearList(finYearList);
                    String lookUpCode = CommonMasterUtility.getDefaultValueByOrg(MainetConstants.Property.propPref.APP,
                            UserSession.getCurrent().getOrganisation()).getLookUpCode();
                    model.getProvisionalAssesmentMstDto().setPartialAdvancePayStatus(lookUpCode);
                    model.setDropDownValues(UserSession.getCurrent().getOrganisation());
                    // propertyService.setWardZoneDetailByLocId(assMst, model.getDeptId(), assMst.getLocId());
                    assMst.setSmServiceId(model.getServiceId());
                    assMst.setOrgId(orgId);
                    List<DocumentDetailsVO> docs = selfAssessmentService.fetchCheckList(assMst, factorMap);

                    /* This code is to upload manual receipt when assesment is doing through manual receipt date */
                    if (model.getAssesmentManualDate() != null) {
                        DocumentDetailsVO manualCheckList = new DocumentDetailsVO();
                        manualCheckList.setCheckkMANDATORY("Y");
                        manualCheckList.setDoc_DESC_ENGL("Manual Receipt");
                        manualCheckList.setDoc_DESC_Mar("Manual Receipt");
                        if (CollectionUtils.isNotEmpty(docs)) {
                            manualCheckList.setDocumentSerialNo(docs.get(docs.size() - 1).getDocumentSerialNo() + 1);
                            docs.add(manualCheckList);
                        } else {
                            docs = new ArrayList<DocumentDetailsVO>();
                            manualCheckList.setDocumentSerialNo(1L);
                            docs.add(manualCheckList);
                        }

                    }
                    model.setCheckList(docs);
                    Map<Long, BillDisplayDto> taxWiseRebate = new TreeMap<>();// map<TxaId,BillDisplayDto>
                    Date manualDate = null;
                    Date advanceManualDate = null;
                    TbServiceReceiptMasEntity manualReceiptMas = null;
                    if (assMst.getAssNo() != null && !assMst.getAssNo().isEmpty()) {
                        List<AsExcessAmtEntity> excessAmtEntByPropNoList = ApplicationContextProvider.getApplicationContext()
                                .getBean(AsExecssAmtService.class).getExcessAmtEntByPropNo(assMst.getAssNo(),
                                        UserSession.getCurrent().getOrganisation().getOrgid());
                        if (CollectionUtils.isNotEmpty(excessAmtEntByPropNoList)) {
                            AsExcessAmtEntity asExcessAmtEntity = excessAmtEntByPropNoList
                                    .get(excessAmtEntByPropNoList.size() - 1);
                            manualReceiptMas = ApplicationContextProvider.getApplicationContext().getBean(ReceiptRepository.class)
                                    .getmanualReceiptAdvanceAmountByAddRefNo(asExcessAmtEntity.getRmRcptid(), assMst.getAssNo(),
                                            UserSession.getCurrent().getOrganisation().getOrgid());
                            if (manualReceiptMas != null) {
                                advanceManualDate = manualReceiptMas.getRmDate();
                            }
                        }
                    }
                    /*
                     * Actually rebate and surcharge calculating based on two type manual receipt date one from advance payment
                     * manual entry date and another from manual date through assesment form
                     */
                    if (model.getAssesmentManualDate() != null) {
                        manualDate = model.getAssesmentManualDate();
                    } else {
                        manualDate = advanceManualDate;
                    }
                    Map<Date, List<BillPresentAndCalculationDto>> schWiseChargeMap = propertyBRMSService.fetchCharges(assMst,
                            model.getDeptId(), taxWiseRebate, finYearList, manualReceiptMas, model.getAssType(),
                            model.getAssesmentManualDate(),null);
                    // D#18545 Error MSG for Rule found or not
                    model.validateBean(assMst, RuleErrorValidator.class);
                    if (model.hasValidationErrors()) {
                        // return defaultMyResult();
                        mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
                        return mv;
                    }
                    List<TbBillMas> orgBillMasList = propertyService.generateNewBill(schWiseChargeMap,
                            UserSession.getCurrent().getOrganisation(), model.getDeptId(), null,null);
                    propertyService.updateARVRVInBill(assMst, orgBillMasList);
                    List<BillReceiptPostingDTO> reabteRecDtoList = selfAssessmentService.knowkOffDemandLevelRebateAndExemption(
                            orgBillMasList,
                            schWiseChargeMap,
                            UserSession.getCurrent().getOrganisation(), taxWiseRebate);
                    model.setReabteRecDtoList(reabteRecDtoList);

                    model.setManualReeiptDate(advanceManualDate);

                    List<BillDisplayDto> rebateDtoList = propertyBRMSService.fetchEarlyPayRebateRate(orgBillMasList,
                            UserSession.getCurrent().getOrganisation(), model.getDeptId(), manualDate,null);

                    if (CollectionUtils.isNotEmpty(rebateDtoList)) {
                        double rebateAmount = 0.0;
                        for (BillDisplayDto billDisplayDto : rebateDtoList) {
                            rebateAmount = rebateAmount + billDisplayDto.getTotalTaxAmt().doubleValue();
                        }
                        BillDisplayDto totalRebate = new BillDisplayDto();
                        BeanUtils.copyProperties(rebateDtoList.get(0), totalRebate);
                        totalRebate.setCurrentYearTaxAmt(new BigDecimal(rebateAmount));
                        totalRebate.setTotalTaxAmt(new BigDecimal(rebateAmount));
                        model.setEarlyPayRebate(totalRebate);
                    }
                    List<TbBillMas> billMasArrears = propertyMainBillService.fetchNotPaidBillForAssessment(assMst.getAssNo(),
                            orgId);
                    List<TbBillMas> mergBillList = null;
                    if (billMasArrears != null && !billMasArrears.isEmpty()) {
                        propertyService.interestCalculation(UserSession.getCurrent().getOrganisation(), model.getDeptId(),
                                billMasArrears,
                                MainetConstants.Property.INT_RECAL_YES);
                        mergBillList = propertyService.getBillListWithMergingOfOldAndNewBill(billMasArrears, orgBillMasList);
                        propertyService.updateArrearInCurrentBills(mergBillList);
                    } else {
                        mergBillList = orgBillMasList;
                    }

                    propertyService.interestCalculationWithoutBRMSCall(UserSession.getCurrent().getOrganisation(),
                            model.getDeptId(), mergBillList, MainetConstants.Property.INT_RECAL_NO);
                    mergBillList.sort(Comparator.comparing(TbBillMas::getBmFromdt));
                    propertyService.taxCarryForward(mergBillList, model.getOrgId());
                    BillDisplayDto surCharge = propertyService.calculateSurcharge(UserSession.getCurrent().getOrganisation(),
                            model.getDeptId(), mergBillList, assMst.getAssNo(),
                            MainetConstants.Property.SURCHARGE, finYearId, manualDate);
                    if (surCharge != null && surCharge.getTotalTaxAmt() != null) {
                        model.getProvisionalAssesmentMstDto().setTotalSubcharge(surCharge.getTotalTaxAmt().doubleValue());
                    }
                    BillDisplayDto advanceAmt = asExecssAmtService.getBillDisplayDtoWithAdvanceAmt(assMst.getAssNo(),
                            assMst.getOrgId(),null);
                    if (advanceManualDate != null && model.getEarlyPayRebate() != null
                            && model.getEarlyPayRebate().getCurrentYearTaxAmt() != null
                            && advanceAmt.getCurrentYearTaxAmt()
                                    .doubleValue() < (mergBillList.get(mergBillList.size() - 1).getBmTotalOutstanding()
                                            - model.getEarlyPayRebate().getCurrentYearTaxAmt().doubleValue())) {
                        rebateDtoList = null;
                    }

                    List<BillDisplayDto> otherTaxesDisDtoList = new ArrayList<>();
                    if (CollectionUtils.isNotEmpty(rebateDtoList)) {
                        otherTaxesDisDtoList.addAll(rebateDtoList);
                    }
                    if (surCharge != null) {
                        otherTaxesDisDtoList.add(surCharge);
                    }
                    if (advanceAmt != null) {
                        model.setAdvanceAmt(advanceAmt);
                        otherTaxesDisDtoList.add(advanceAmt);
                    }

                    Map<String, List<BillDisplayDto>> presentMap = selfAssessmentService.getDisplayMapOfTaxes(mergBillList,
                            UserSession.getCurrent().getOrganisation(), model.getDeptId(), taxWiseRebate, otherTaxesDisDtoList);
                    assMst.setBillTotalAmt(
                            propertyService.getTotalActualAmount(mergBillList, rebateDtoList, taxWiseRebate, surCharge,
                                    advanceAmt));
                    model.setBillMasList(mergBillList);
                    model.setDisplayMap(presentMap);
                    model.setIntgrtionWithBP(CommonMasterUtility
                            .getDefaultValueByOrg(MainetConstants.Property.IBP, UserSession.getCurrent().getOrganisation())
                            .getLookUpCode());
                    return new ModelAndView("SelfAssessmentView", MainetConstants.FORM_NAME, getModel());
                } else {
                    model.addValidationError("Please enter valid property number or Old PID number.");

                    mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
                }
            } else {
                model.addValidationError(
                        "Assessment already done for entered property no or Old PID for current financial year.");
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
        model.setDistrict(dataEntrySuiteService.findDistrictByLandType(dto));
        model.setTehsil(dataEntrySuiteService.getTehsilListByDistrict(dto));
        model.setVillage(dataEntrySuiteService.getVillageListByTehsil(dto));
        if (model.getLandTypePrefix().equals(MainetConstants.Property.LandType.NZL)
                || model.getLandTypePrefix().equals(MainetConstants.Property.LandType.DIV)) {
            getModel().setMohalla(dataEntrySuiteService.getMohallaListByVillageId(dto));
            getModel().setBlockStreet(dataEntrySuiteService.getStreetListByMohallaId(dto));
        }
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
                assMst.getProAssfactor().add(MainetConstants.FlagY);
            });

        });
        return factorMap;
    }

    private void setCommonFields(SelfAssesmentNewModel model) {
        getModel().setAssType(MainetConstants.Property.NO_CHN_ASESS);
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        ServiceMaster service = serviceMaster.getServiceByShortName(MainetConstants.Property.serviceShortCode.NCA, orgId);
        model.setOrgId(orgId);
        model.setServiceMaster(service);
        model.setDeptId(service.getTbDepartment().getDpDeptid());
        model.setServiceId(service.getSmServiceId());
    }

    @RequestMapping(params = "editNoChangeAssForm", method = RequestMethod.POST)
    public ModelAndView editForm(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        getModel().setSelfAss(null);
        SelfAssesmentNewModel model = this.getModel();
        return new ModelAndView("ChangeInAssessmentForm", MainetConstants.FORM_NAME, model);
    }

}
