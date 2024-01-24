package com.abm.mainet.property.ui.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.BillReceiptPostingDTO;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.IReceiptEntryService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.property.dto.BillDisplayDto;
import com.abm.mainet.property.dto.BillPresentAndCalculationDto;
import com.abm.mainet.property.dto.LandTypeApiDetailRequestDto;
import com.abm.mainet.property.dto.PropertyTransferMasterDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentDetailDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentFactorDtlDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.service.AmalgamationService;
import com.abm.mainet.property.service.AsExecssAmtService;
import com.abm.mainet.property.service.AssesmentMastService;
import com.abm.mainet.property.service.DataEntrySuiteService;
import com.abm.mainet.property.service.IProvisionalAssesmentMstService;
import com.abm.mainet.property.service.IProvisionalBillService;
import com.abm.mainet.property.service.MutationService;
import com.abm.mainet.property.service.PrimaryPropertyService;
import com.abm.mainet.property.service.PropertyBRMSService;
import com.abm.mainet.property.service.PropertyMainBillService;
import com.abm.mainet.property.service.PropertyService;
import com.abm.mainet.property.service.SelfAssessmentService;
import com.abm.mainet.property.ui.model.SelfAssesmentNewModel;
import com.abm.mainet.property.ui.validator.RuleErrorValidator;
import com.abm.mainet.property.ui.validator.SelfAssessmentValidator;
import com.google.common.util.concurrent.AtomicDouble;
import com.ibm.icu.text.SimpleDateFormat;

@Controller
@RequestMapping({ "/BifurcationForm.html", "/BifurcationAuthorization.html" })
public class BifurcationController extends AbstractFormController<SelfAssesmentNewModel> {

    @Autowired
    private ServiceMasterService serviceMaster;

    @Autowired
    private IFileUploadService fileUpload;

    @Autowired
    private AssesmentMastService assesmentMastService;

    @Autowired
    private ILocationMasService iLocationMasService;

    @Autowired
    private IFinancialYearService iFinancialYear;

    @Resource
    private SelfAssessmentService selfAssessmentService;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private PropertyBRMSService propertyBRMSService;

    @Autowired
    private IChecklistVerificationService iChecklistVerificationService;

    @Resource
    private IProvisionalAssesmentMstService provisionalAssesmentMstService;

    @Autowired
    private IWorkFlowTypeService iWorkFlowTypeService;

    @Autowired
    private DataEntrySuiteService dataEntrySuiteService;

    @Autowired
    private PropertyMainBillService propertyMainBillService;

    @Autowired
    private IReceiptEntryService iReceiptEntryService;

    @Autowired
    private PrimaryPropertyService primaryPropertyService;
    
    @Autowired
    private IProvisionalBillService iProvisionalBillService;
    
    @Autowired
    private MutationService mutationService;
    
    @Autowired
    private AmalgamationService amalgamationService;
    
    @Autowired
    private AsExecssAmtService asExecssAmtService;

    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(HttpServletRequest request) throws Exception {
        this.sessionCleanup(request);
        fileUpload.sessionCleanUpForFileUpload();
        SelfAssesmentNewModel model = this.getModel();
        model.setProvisionalAssesmentMstDto(null);
        setCommonFields(model);
        model.setCommonHelpDocs("BifurcationForm.html");
        return defaultResult();
    }

    @RequestMapping(method = { RequestMethod.POST }, params = "searchProperty")
    public ModelAndView serachProperty(HttpServletRequest request) throws Exception {
        SelfAssesmentNewModel model = this.getModel();
        model.bind(request);
        model.setLastChecker(true);
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        model.setAssType(MainetConstants.Property.Bifurcation);
        model.setSelfAss(MainetConstants.Property.Bifurcation);
        model.setConstructFlag(MainetConstants.Y_FLAG);
        model.getProvisionalAssesmentMstDto().setAssActive(null);
        model.setProvAssMstDtoList(new ArrayList<>());
        ProvisionalAssesmentMstDto assesmentMstDto = model.getProvisionalAssesmentMstDto();
        List<String> checkActiveFlagList = null;
        int count = 0;
        boolean skdclDuesCheck = Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "DPC");
        // if (StringUtils.isNotBlank(model.getProvisionalAssesmentMstDto().getAssNo())) {
        // checkActiveFlagList = assesmentMastService.checkActiveFlag(model.getProvisionalAssesmentMstDto().getAssNo(), orgId);
        // }
        if (StringUtils.isBlank(model.getProvisionalAssesmentMstDto().getAssNo())) {
            if (StringUtils.isNotBlank(assesmentMstDto.getFlatNo())) {
                checkActiveFlagList = assesmentMastService.checkActiveFlagByOldPropNoNFlatNo(assesmentMstDto.getAssOldpropno(),
                        assesmentMstDto.getFlatNo(), orgId);
            } else {
                checkActiveFlagList = assesmentMastService.checkActiveFlagByOldPropNo(assesmentMstDto.getAssOldpropno(), orgId);
            }
        } else {
            if (StringUtils.isNotBlank(assesmentMstDto.getFlatNo())) {
                checkActiveFlagList = assesmentMastService.checkActiveFlagByPropnonFlatNo(assesmentMstDto.getAssNo(),
                        assesmentMstDto.getFlatNo(), orgId);
            } else {
                checkActiveFlagList = assesmentMastService.checkActiveFlag(assesmentMstDto.getAssNo(), orgId);
            }
        }
        if (CollectionUtils.isNotEmpty(checkActiveFlagList)) {
            String checkActiveFlag = checkActiveFlagList.get(checkActiveFlagList.size() - 1);
            if (StringUtils.equals(checkActiveFlag, MainetConstants.STATUS.INACTIVE)) {
                model.addValidationError(getApplicationSession().getMessage("property.inactive"));
            }
        }
        if ((model.getProvisionalAssesmentMstDto().getAssNo() == null
                || model.getProvisionalAssesmentMstDto().getAssNo().isEmpty())
                && (model.getProvisionalAssesmentMstDto().getAssOldpropno() == null
                        || model.getProvisionalAssesmentMstDto().getAssOldpropno().isEmpty())) {
            model.addValidationError(getApplicationSession().getMessage("bif.enter.Prop"));
        }

        Long finYearId = iFinancialYear.getFinanceYearId(new Date());
        // To get previous year Id
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);
        Date prevYear = cal.getTime();
        Long prevYearId = iFinancialYear.getFinanceYearId(prevYear);

        // To check bill and dues is present for previous year or not
        if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.SKDCL)) {
            if (StringUtils.isNotBlank(assesmentMstDto.getAssNo())) {
                List<TbBillMas> billMasList = null;
                if (StringUtils.isNotBlank(assesmentMstDto.getFlatNo())
                        && StringUtils.equals(model.getBillingMethod(), MainetConstants.FlagI)) {
                    billMasList = propertyMainBillService.fetchAllBillByPropNoAndFlatNo(assesmentMstDto.getAssNo(),
                            assesmentMstDto.getFlatNo(), orgId);
                    if (skdclDuesCheck) {
                        count = propertyMainBillService.getPaidBillCountByPropNoAndFlatNo(assesmentMstDto.getAssNo(), orgId,
                                prevYearId, assesmentMstDto.getFlatNo());
                    }

                } else {
                    billMasList = propertyMainBillService
                            .fetchAllBillByPropNo(model.getProvisionalAssesmentMstDto().getAssNo(), orgId);
                    if (skdclDuesCheck) {
                        count = propertyMainBillService.getPaidBillCountByPropNo(model.getProvisionalAssesmentMstDto().getAssNo(),
                                orgId, prevYearId);
                    }
                }
                if (CollectionUtils.isNotEmpty(billMasList)) {
                    TbBillMas billMas = billMasList.get(billMasList.size() - 1);
                    if (!prevYearId.equals(billMas.getBmYear()) && !finYearId.equals(billMas.getBmYear())) {
                        model.addValidationError(
                                getApplicationSession().getMessage("property.bif.bill.notgenerated.prevYear"));
                    }
                }
            }

            if (count > 0) {
                model.addValidationError(getApplicationSession().getMessage("property.validationDues"));
            }
        }
        if (!model.hasValidationErrors()) {

            model.getProvisionalAssesmentMstDto().setOrgId(orgId);
            List<ProvisionalAssesmentMstDto> assMstList = null;
            String propNo = model.getProvisionalAssesmentMstDto().getAssNo();

            if (StringUtils.isNotBlank(assesmentMstDto.getFlatNo())
                    && StringUtils.equals(model.getBillingMethod(), MainetConstants.FlagI)) {
                assMstList = assesmentMastService.getPropDetailFromAssByPropNoFlatNo(orgId, propNo, assesmentMstDto.getFlatNo());
            } else {
                assMstList = assesmentMastService.fetchAssessmentMasterForAmalgamation(
                        orgId, model.getProvisionalAssesmentMstDto().getAssNo(),
                        new ArrayList<>(), model.getProvisionalAssesmentMstDto().getAssOldpropno(), new ArrayList<>());
            }

            if (assMstList != null && !assMstList.isEmpty()) {
            	ProvisionalAssesmentMstDto assMstDto = assMstList.get(assMstList.size() - 1);
            	try {
                    PropertyTransferMasterDto transferMasDto = mutationService.getMutationByPropNo(assMstDto.getAssNo(),
                            UserSession.getCurrent().getOrganisation().getOrgid());
                    if (transferMasDto.getApmApplicationId() != null) {
                        String status = mutationService.getWorkflowRequestByAppId(transferMasDto.getApmApplicationId(),
                                UserSession.getCurrent().getOrganisation().getOrgid());
                        if (MainetConstants.TASK_STATUS_PENDING.equalsIgnoreCase(status)) {
                            getModel().addValidationError(
                                    ApplicationSession.getInstance().getMessage("property.validation.mutaion.process"));
                            return defaultMyResult();
                        }
                    }
                } catch (Exception e) {
                    //LOGGER.error("Exception occured" + e);
                }
                if (!assMstDto.getAssNo().isEmpty()) {
                    if (assMstDto != null && assMstDto.getSmServiceId() > 0) {
                        String bifService = serviceMaster.fetchServiceShortCode(assMstDto.getSmServiceId(), orgId);
                        if (StringUtils.equals(MainetConstants.Property.Bifurcation, bifService)) {
                            model.addValidationError(getApplicationSession().getMessage("property.bif.already.bifurcated"));
                            return defaultMyResult();
                        }
                    }
                    if ((Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.ASCL)
                    		||Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.PSCL))
                    		&& CollectionUtils.isNotEmpty(assMstDto.getProvisionalAssesmentDetailDtoList())) {
                    	 if(assMstDto.getProvisionalAssesmentDetailDtoList().size() == 1) {
                    		 model.addValidationError(getApplicationSession().getMessage("property.bif.proceed.valid"));
                             return defaultMyResult();
                    	 }
                    }
                    // current year dues pending validation
                    if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),
                            MainetConstants.APP_NAME.SKDCL)) {
                        if (StringUtils.isNotBlank(assesmentMstDto.getFlatNo())
                                && StringUtils.equals(model.getBillingMethod(), MainetConstants.FlagI) && skdclDuesCheck) {
                            count = propertyMainBillService.getPaidBillCountByPropNoAndFlatNo(assesmentMstDto.getAssNo(), orgId,
                                    finYearId, assesmentMstDto.getFlatNo());
                        } else if (skdclDuesCheck) {
                            count = propertyMainBillService.getPaidBillCountByPropNo(propNo, orgId, finYearId);
                        }
                    } else {
                        count = propertyMainBillService.getPaidBillCountByPropNo(assMstDto.getAssNo(), orgId, finYearId);
                    }
                }
                if (count > 0) {
                	List<String> propNoList = new ArrayList<String>();
                	propNoList.add(assMstDto.getAssNo());
                	List<TbBillMas> paidFlagList = amalgamationService.fetchNotPaidBillsByPropNo(propNoList, orgId);
                	TbBillMas billMas = paidFlagList.get(paidFlagList.size() - 1);
                    boolean paidFlag = false;
                    if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)) {
                    	AtomicDouble pendingAmount = new AtomicDouble(0);
                        billMas.getTbWtBillDet().forEach(billDet ->{
                        	pendingAmount.addAndGet(billDet.getBdCurBalTaxamt() + billDet.getBdPrvArramt());
                        });
                        BillDisplayDto advanceAmnt = asExecssAmtService.getBillDisplayDtoWithAdvanceAmt(propNo, orgId, null);
                        if(advanceAmnt != null && advanceAmnt.getCurrentYearTaxAmt() != null && advanceAmnt.getCurrentYearTaxAmt().doubleValue() >= pendingAmount.doubleValue()) {
                        	paidFlag = true;
                        }
                    }
                    if( !paidFlag) {
                    	model.addValidationError(getApplicationSession().getMessage("property.validationDues"));
                    }
                } else {
                    setViewData(model, orgId, assMstList);
                }
            } else {
                model.addValidationError(getApplicationSession().getMessage("bif.enter.oldProp"));
            }
        }
        return defaultMyResult();

    }

    private void setViewData(SelfAssesmentNewModel model, long orgId, final List<ProvisionalAssesmentMstDto> assMstList)
            throws Exception {
        model.setProvisionalAssesmentMstDto(assMstList.get(assMstList.size() - 1));
        ProvisionalAssesmentMstDto searchDto = model.getProvisionalAssesmentMstDto();
        List<Double> totalBuildup = new ArrayList<Double>();
        List<ProvisionalAssesmentDetailDto> detailDtoList = searchDto.getProvisionalAssesmentDetailDtoList();
        for (ProvisionalAssesmentDetailDto dto : detailDtoList) {
            if (dto.getAssdBuildupArea() != null) {
                totalBuildup.add(dto.getAssdBuildupArea());
            }
        }
        if (CollectionUtils.isNotEmpty(totalBuildup)) {
            Double buildup = totalBuildup.stream().mapToDouble(Double::doubleValue).sum();
            model.setTotalBuildUpArea(buildup);
        } else {
            model.setTotalBuildUpArea(0.0);
        }
        searchDto.setParentProp(assMstList.get(0).getAssNo());
        List<ProvisionalAssesmentFactorDtlDto> factorMapDtl = factorMappingForView(searchDto);
        model.setProvAsseFactDtlDto(factorMapDtl);
        searchDto.setSmServiceId(model.getServiceId());
        searchDto.setOrgId(orgId);
        searchDto.setLocationName(
                iLocationMasService.getLocationNameById(searchDto.getLocId(), orgId));
        Long finYearId = iFinancialYear.getFinanceYearId(new Date());
        if (searchDto.getAssCorrAddress() == null) {
            searchDto.setProAsscheck(MainetConstants.FlagY);
        } else {
            searchDto.setProAsscheck(MainetConstants.FlagN);
        }
        if (searchDto.getAssLpYear() == null) {
            searchDto.setProAssBillPayment(MainetConstants.Property.NOT_APP);
        } else {
            searchDto.setProAssBillPayment(MainetConstants.Property.MANUAL);
        }
        searchDto.setProAssOwnerTypeName(CommonMasterUtility
                .getNonHierarchicalLookUpObject(searchDto.getAssOwnerType(), UserSession.getCurrent().getOrganisation())
                .getDescLangFirst());
        LookUp ownerType = CommonMasterUtility.getNonHierarchicalLookUpObject(
                searchDto.getAssOwnerType(), UserSession.getCurrent().getOrganisation());
        getModel().setOwnershipPrefix(ownerType.getLookUpCode());
        LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp(ownerType.getLookUpCode(),
                MainetConstants.Property.propPref.OWT,
                UserSession.getCurrent().getOrganisation());
        model.setOwnershipTypeValue(lookup.getDescLangFirst());
        final List<FinancialYear> finYearList = iFinancialYear.getAllFinincialYear();
        String financialYear = null;
        for (final FinancialYear finYearTemp : finYearList) {
            financialYear = Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate());
            getModel().getFinancialYearMap().put(finYearTemp.getFaYear(), financialYear);
        }
        List<FinancialYear> financialYearList = iFinancialYear
                .getFinanceYearListFromGivenDate(UserSession.getCurrent().getOrganisation().getOrgid(),
                        finYearId, new Date());
        if (!financialYearList.isEmpty()) {
            List<Long> finYearListDAta = new ArrayList<>();
            for (FinancialYear financialYearEach : financialYearList) {
                Long finYear = iFinancialYear.getFinanceYearId(financialYearEach.getFaFromDate());
                finYearListDAta.add(finYear);
            }
            this.getModel().setFinYearList(finYearListDAta);
        }
        if (model.getProvisionalAssesmentMstDto().getAssLandType() != null) {
            LookUp landTypePrefix = CommonMasterUtility.getNonHierarchicalLookUpObject(
                    model.getProvisionalAssesmentMstDto().getAssLandType(), UserSession.getCurrent().getOrganisation());
            getModel().setLandTypePrefix(landTypePrefix.getLookUpCode());
        }
        if (getModel().getLandTypePrefix() != null) {
            setlandTypeDetails(getModel());
        }
        if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.SKDCL)
        		|| Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.ASCL)
        		|| Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.PSCL)) {
            searchDto.setFaYearId(finYearId);
            List<ProvisionalAssesmentDetailDto> detailList = new ArrayList<>();
            for (ProvisionalAssesmentDetailDto detailDto : detailDtoList) {
                detailDto.setFaYearId(finYearId);
                detailList.add(detailDto);
            }
            searchDto.setProvisionalAssesmentDetailDtoList(detailList);
        }
        model.setDropDownValues(UserSession.getCurrent().getOrganisation());

        /*
         * AtomicInteger count = new AtomicInteger(0); Map<String, List<ProvisionalAssesmentFactorDtlDto>> factorMap = new
         * HashMap<>(); searchDto.getProvisionalAssesmentDetailDtoList().forEach(propDet -> { count.getAndIncrement();
         * this.getModel().getUnitNoList().add(count.toString()); propDet.getProvisionalAssesmentFactorDtlDtoList().forEach(fact
         * -> { List<ProvisionalAssesmentFactorDtlDto> factList1 = factorMap.get(fact.getAssfFactorId().toString()); if (factList1
         * != null && !factList1.isEmpty()) { fact.setUnitNoFact(count.toString()); factList1.add(fact); } else { factList1 = new
         * ArrayList<>(); fact.setUnitNoFact(count.toString()); factList1.add(fact); }
         * factorMap.put(fact.getAssfFactorId().toString(), factList1); }); }); List<LookUp> lookupList =
         * CommonMasterUtility.lookUpListByPrefix("FCT", UserSession.getCurrent().getOrganisation().getOrgid());
         * lookupList.forEach(fact -> { List<ProvisionalAssesmentFactorDtlDto> factList =
         * factorMap.get(Long.toString(fact.getLookUpId())); if (factList == null || factList.isEmpty()) {
         * factorMap.put(Long.toString(fact.getLookUpId()), null); } }); this.getModel().setDisplayfactorMap(factorMap);
         */
        setFactorMap(searchDto);
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

    private void setCommonFields(SelfAssesmentNewModel model) {
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        model.setOrgId(orgId);
        ServiceMaster service = serviceMaster.getServiceByShortName(MainetConstants.Property.Bifurcation, orgId);
        model.setOrgId(orgId);
        model.setDeptId(service.getTbDepartment().getDpDeptid());
        model.setServiceId(service.getSmServiceId());
        model.setIntgrtionWithBP(CommonMasterUtility
                .getDefaultValueByOrg(MainetConstants.Property.IBP, UserSession.getCurrent().getOrganisation()).getLookUpCode());
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

    @RequestMapping(params = "showDetails", method = RequestMethod.POST)
    public ModelAndView defaultLoad(@RequestParam("appNo") final long applicationId, @RequestParam("taskId") final long serviceId,
            @RequestParam("actualTaskId") final long taskId, final HttpServletRequest httpServletRequest) throws Exception {
        sessionCleanup(httpServletRequest);
        fileUpload.sessionCleanUpForFileUpload();
        getModel().setLastChecker(iWorkFlowTypeService.isLastTaskInCheckerTaskList(taskId));
        getModel().getWorkflowActionDto().setApplicationId(applicationId);
        getModel().getWorkflowActionDto().setTaskId(taskId);
        SelfAssesmentNewModel model = this.getModel();
        setCommonFields(model);
        model.setCommonHelpDocs("BifurcationAuthorization.html");
        if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.SKDCL)
        		|| Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.ASCL)
        		|| Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.PSCL)) {
            getModel().setSelfAss(MainetConstants.Property.Bifurcation);
        }
        // getModel().setSelfAss(MainetConstants.Property.Bifurcation);
        model.setAssType(MainetConstants.Property.ASESS_AUT);// to identify call of model from Authorization
        model.setNoOfDaysAuthEditFlag(MainetConstants.N_FLAG);
        final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        ServiceMaster service = serviceMaster.getServiceMaster(serviceId, orgId);
        Long deptId = service.getTbDepartment().getDpDeptid();
        getModel().setDocumentList(iChecklistVerificationService.getDocumentUploaded(applicationId, orgId));
        final List<ProvisionalAssesmentMstDto> provAssDtoList = provisionalAssesmentMstService
                .getAssesmentMstDtoListByAppId(applicationId, orgId);
        model.setProvisionalAssesmentMstDto(provAssDtoList.get(provAssDtoList.size() - 1));
        ProvisionalAssesmentMstDto assMst = model.getProvisionalAssesmentMstDto();
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

        List<FinancialYear> financialYearList = iFinancialYear
                .getFinanceYearListFromGivenDate(UserSession.getCurrent().getOrganisation().getOrgid(), assMst.getFaYearId(),
                        new Date());
        String financialYear = null;
        for (final FinancialYear finYearTemp : financialYearList) {
            financialYear = Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate());
            getModel().getFinancialYearMap().put(finYearTemp.getFaYear(), financialYear);
        }
        List<Long> finYearList = financialYearList.stream().map(FinancialYear::getFaYear).collect(Collectors.toList());
        List<ProvisionalAssesmentFactorDtlDto> factorMap = factorMappingForView(assMst);
        List<ProvisionalAssesmentFactorDtlDto> factorMapBefore = new ArrayList<>();
        factorMap.forEach(fact -> {
            ProvisionalAssesmentFactorDtlDto factBefore = new ProvisionalAssesmentFactorDtlDto();
            BeanUtils.copyProperties(fact, factBefore);
            factorMapBefore.add(factBefore);
        });
        if (!factorMap.isEmpty()) {
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
        }
        getModel().setProvAsseFactDtlDto(factorMap);
        getModel().setAuthCompFactDto(factorMapBefore);
        getModel().setServiceId(serviceId);
        getModel().setDeptId(model.getDeptId());
        getModel().setProvAssMstDtoList(provAssDtoList);
        assMst.setLocationName(iLocationMasService.getLocationNameById(assMst.getLocId(), orgId));
        model.setFinYearList(finYearList);

        if (service != null && service.getSmFeesSchedule() != null && service.getSmFeesSchedule() != 0) {
        	List<TbBillMas> billMasList = new ArrayList<>();
        	billMasList = iProvisionalBillService.getProvisionalBillMasByPropertyNo(assMst.getAssNo(), orgId);
            getModel().setAuthComBillList(billMasList);
        }
        if (assMst.getAssLandType() != null && assMst.getAssLandType() > 0) {
            LookUp landTypePrefix = CommonMasterUtility.getNonHierarchicalLookUpObject(
                    assMst.getAssLandType(), UserSession.getCurrent().getOrganisation());
            getModel().setLandTypePrefix(landTypePrefix.getLookUpCode());
        }
        if (getModel().getLandTypePrefix() != null) {
            setlandTypeDetails(getModel());
        }
        getModel().setDropDownValues(UserSession.getCurrent().getOrganisation());
        getModel().setAuthCompBeforeDto(
                provisionalAssesmentMstService.copyProvDtoDataToOtherDto(assMst));// Setting before change property detail
        if (getModel().getAuthCompBeforeDto().getAssLandType() != null) {
            LookUp landTypePrefix = CommonMasterUtility.getNonHierarchicalLookUpObject(
                    getModel().getAuthCompBeforeDto().getAssLandType(), UserSession.getCurrent().getOrganisation());
            getModel().setOldLandTypePrefix(landTypePrefix.getLookUpCode());
        }
        BigDecimal billPaidAmt = iReceiptEntryService.getPaidAmountByAppNo(orgId, applicationId, deptId);
        if (billPaidAmt != null) {
            assMst.setBillPaidAmt(billPaidAmt.doubleValue());
        } else {
            billPaidAmt = new BigDecimal(0);
        }
        return new ModelAndView("SelfAssessmentView", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "getOwnershipTypeInfoDiv", method = RequestMethod.POST)
    public ModelAndView getOwnershipTypeDiv(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @RequestParam(value = "ownershipType") String ownershipType) {

        this.getModel().bind(httpServletRequest);
        SelfAssesmentNewModel model = this.getModel();

        model.setOwnershipPrefix(ownershipType);
        LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp(ownershipType,
                MainetConstants.Property.propPref.OWT, UserSession.getCurrent().getOrganisation());

        model.setOwnershipTypeValue(lookup.getDescLangFirst());
        model.getProvisionalAssesmentMstDto().setAssOwnerType(lookup.getLookUpId());
        return new ModelAndView("SelfAssessmentOwnershipDetailForm", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "proceed", method = RequestMethod.POST)
    public ModelAndView confirmToProceed(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        this.bindModel(httpServletRequest);
        SelfAssesmentNewModel model = this.getModel();
        ProvisionalAssesmentMstDto assMstDto = model.getProvisionalAssesmentMstDto();
        List<ProvisionalAssesmentDetailDto> detailDtoList2 = new ArrayList<>();
        List<ProvisionalAssesmentDetailDto> detailDtoList = assMstDto.getProvisionalAssesmentDetailDtoList();
        if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.SKDCL)) {
            for (ProvisionalAssesmentDetailDto detailDto : detailDtoList) {
                if (detailDto.getAssdBifurcateNo() == null) {
                    detailDtoList2.add(detailDto);
                }
                assMstDto.setProvisionalAssesmentDetailDtoList(detailDtoList2);
            }
            if (detailDtoList2.size() > 0) {
                assMstDto.setProvisionalAssesmentDetailDtoList(detailDtoList);
                getModel().addValidationError("property.bif.select.checkbox");
                ModelAndView mv = new ModelAndView("BifurcationValidn", MainetConstants.FORM_NAME, this.getModel());
                mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
                return mv;
            }else {
            	assMstDto.setProvisionalAssesmentDetailDtoList(detailDtoList);
            }
        } else {
            for (ProvisionalAssesmentDetailDto detailDto : detailDtoList) {
                if (detailDto.getAssdBifurcateNo() != null) {
                    detailDtoList2.add(detailDto);
                }
                assMstDto.setProvisionalAssesmentDetailDtoList(detailDtoList2);
            }
            if (CollectionUtils.isEmpty(detailDtoList2)) {
                assMstDto.setProvisionalAssesmentDetailDtoList(detailDtoList);
                getModel().addValidationError("Please select atleast one unit to bifurcate");
                ModelAndView mv = new ModelAndView("BifurcationValidn", MainetConstants.FORM_NAME, this.getModel());
                mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
                return mv;
            }
        }

        model.setProvisionalAssesmentMstDto(assMstDto);
        List<DocumentDetailsVO> docs = model.getCheckList();
        docs = fileUpload.prepareFileUpload(docs);
        assMstDto.setDocs(docs);
        model.setDocumentList(new ArrayList<>());
        List<CFCAttachment> documentList = selfAssessmentService.preparePreviewOfFileUpload(model.getDocumentList(),
                model.getCheckList());
        model.setDocumentList(documentList);
        model.validateBean(assMstDto, SelfAssessmentValidator.class);
        if (model.hasValidationErrors()) {
            return defaultMyResult();
        }
        this.getModel().setDropDownValues(UserSession.getCurrent().getOrganisation());
        ServiceMaster serviceMas = serviceMaster.getServiceByShortName(MainetConstants.Property.Bifurcation, model.getOrgId());
        if (serviceMas != null && serviceMas.getSmFeesSchedule() != null && serviceMas.getSmFeesSchedule() != 0) {
            if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.SKDCL)) {
                getchargesForIndividualProperty(model);
            } else {
                getCheckListAndcharges(model);
            }
        }
        // D#18545 Error MSG for Rule found or not
        model.validateBean(model.getProvisionalAssesmentMstDto(), RuleErrorValidator.class);
        if (model.hasValidationErrors()) {
            return defaultMyResult();
        }
        return new ModelAndView("SelfAssessmentView", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "getCheckList", method = RequestMethod.POST)
    public ModelAndView getCheckList(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        SelfAssesmentNewModel model = this.getModel();
        final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        // model.setAssType(MainetConstants.N_FLAG);
        ProvisionalAssesmentMstDto dto = model.getProvisionalAssesmentMstDto();
        List<ProvisionalAssesmentDetailDto> detailDtoList2 = new ArrayList<>();
        List<ProvisionalAssesmentDetailDto> detailDtoList = dto.getProvisionalAssesmentDetailDtoList();
        if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.SKDCL)) {
            for (ProvisionalAssesmentDetailDto detailDto : detailDtoList) {
                if (detailDto.getAssdBifurcateNo() == null) {
                    detailDtoList2.add(detailDto);
                }
                dto.setProvisionalAssesmentDetailDtoList(detailDtoList2);
            }
            if (detailDtoList2.size() > 0) {
                dto.setProvisionalAssesmentDetailDtoList(detailDtoList);
                getModel().addValidationError("property.bif.select.checkbox");
                ModelAndView mv = new ModelAndView("BifurcationValidn", MainetConstants.FORM_NAME, this.getModel());
                mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
                return mv;
            } else {
                dto.setProvisionalAssesmentDetailDtoList(detailDtoList);
            }
        } else {
            for (ProvisionalAssesmentDetailDto detailDto : detailDtoList) {
                if (detailDto.getAssdBifurcateNo() != null) {
                    detailDtoList2.add(detailDto);
                }
                dto.setProvisionalAssesmentDetailDtoList(detailDtoList2);
            }
            if (CollectionUtils.isEmpty(detailDtoList2)) {
                dto.setProvisionalAssesmentDetailDtoList(detailDtoList);
                getModel().addValidationError("Please select atleast one unit to bifurcate");
                ModelAndView mv = new ModelAndView("BifurcationValidn", MainetConstants.FORM_NAME, this.getModel());
                mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
                return mv;
            }
        }

        model.setProvisionalAssesmentMstDto(dto);
        List<ProvisionalAssesmentFactorDtlDto> factDto = model.getProvAsseFactDtlDto();
        dto.setOrgId(model.getOrgId());
        dto.setSmServiceId(model.getServiceId());
        LookUp checkListApplLookUp = null;
        List<DocumentDetailsVO> docs = new ArrayList<>();
        ServiceMaster serviceMas = serviceMaster.getServiceByShortName(MainetConstants.Property.Bifurcation,
                model.getOrgId());
        if (serviceMas != null) {
            checkListApplLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(serviceMas.getSmChklstVerify(),
                    ApplicationContextProvider.getApplicationContext().getBean(IOrganisationService.class)
                            .getOrganisationById(model.getOrgId()));

            if (org.apache.commons.lang.StringUtils.equalsIgnoreCase(checkListApplLookUp.getLookUpCode(),
                    MainetConstants.FlagA)) {
                docs = selfAssessmentService.fetchCheckList(dto, factDto);
                model.setCheckList(docs);
            }
        }
        if (docs.isEmpty()) {
            this.getModel().setDropDownValues(UserSession.getCurrent().getOrganisation());
            if (serviceMas != null && serviceMas.getSmFeesSchedule() != null && serviceMas.getSmFeesSchedule() != 0) {
                if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.SKDCL)) {
                    getchargesForIndividualProperty(model);
                } else {
                    getCheckListAndcharges(model);
                }
            }
            // D#18545 Error MSG for Rule found or not
            model.validateBean(model.getProvisionalAssesmentMstDto(), RuleErrorValidator.class);
            if (model.hasValidationErrors()) {
                return defaultMyResult();
            }
            return new ModelAndView("SelfAssessmentView", MainetConstants.FORM_NAME, model);
        }
        Long max = 0l;
        List<ProvisionalAssesmentDetailDto> detailDto = model.getProvisionalAssesmentMstDto()
                .getProvisionalAssesmentDetailDtoList();
        if (detailDto != null && !detailDto.isEmpty()) {
            for (ProvisionalAssesmentDetailDto det : detailDto) {
                if (det.getAssdUnitNo() > max) {
                    max = det.getAssdUnitNo();
                }
            }
        }
        model.setMaxUnit(max);
        model.setSuccessMessage(MainetConstants.N_FLAG);
        return new ModelAndView("BifurcationValidn", MainetConstants.FORM_NAME, model);
    }

    private List<TbBillMas> getCheckListAndcharges(SelfAssesmentNewModel model) {
        ProvisionalAssesmentMstDto dto = model.getProvisionalAssesmentMstDto();
        if (!"A".equals(model.getAssType())) {
            //dto.setAssNo(null);
           // dto.setAssOldpropno(null);
            dto.setApmApplicationId(null);
        }
        dto.getProvisionalAssesmentDetailDtoList().forEach(detailList -> {
            if (detailList.getFirstAssesmentDate() != null) {
                detailList.setFirstAssesmentStringDate(
                        new SimpleDateFormat(MainetConstants.DATE_FRMAT).format(detailList.getFirstAssesmentDate()));
            }
            detailList.setLastAssesmentDate(detailList.getFirstAssesmentDate());

        });
        // propertyService.setWardZoneDetailByLocId(dto, model.getDeptId(), dto.getLocId());
        selfAssessmentService.setFactorMappingToAssDto(model.getProvAsseFactDtlDto(), dto);
        setFactorMap(dto);
        Map<Long, BillDisplayDto> taxWiseRebate = new TreeMap<>();// map<TxaId,BillDisplayDto>
        Map<Date, List<BillPresentAndCalculationDto>> schWiseChargeMap = propertyBRMSService.fetchCharges(dto, model.getDeptId(),
                taxWiseRebate, model.getFinYearList(), null, model.getAssType(), model.getAssesmentManualDate(), null);
        List<TbBillMas> billMasList = propertyService.generateNewBill(schWiseChargeMap,
                UserSession.getCurrent().getOrganisation(), model.getDeptId(), null, null);
        List<BillReceiptPostingDTO> reabteRecDtoList = selfAssessmentService.knowkOffDemandLevelRebateAndExemption(billMasList,
                schWiseChargeMap,
                UserSession.getCurrent().getOrganisation(), taxWiseRebate);
        model.setReabteRecDtoList(reabteRecDtoList);
        propertyService.interestCalculationWithoutBRMSCall(UserSession.getCurrent().getOrganisation(), model.getDeptId(),
                billMasList,
                MainetConstants.Property.INT_RECAL_NO);
        Long finYearId = iFinancialYear.getFinanceYearId(new Date());
        propertyService.taxCarryForward(billMasList, model.getOrgId());
        BillDisplayDto surCharge = propertyService.calculateSurcharge(UserSession.getCurrent().getOrganisation(),
                model.getDeptId(), billMasList, null,
                MainetConstants.Property.SURCHARGE, finYearId, null);
        if (surCharge != null && surCharge.getTotalTaxAmt() != null) {
            model.getProvisionalAssesmentMstDto().setTotalSubcharge(surCharge.getTotalTaxAmt().doubleValue());
        }
        List<BillDisplayDto> earlyPayRebate = propertyBRMSService.fetchEarlyPayRebateRate(billMasList,
                UserSession.getCurrent().getOrganisation(), model.getDeptId(), null, null);
        Map<String, List<BillDisplayDto>> presentMap = selfAssessmentService.getDisplayMapOfTaxes(billMasList,
                UserSession.getCurrent().getOrganisation(), model.getDeptId(), earlyPayRebate, taxWiseRebate, surCharge);
        dto.setBillTotalAmt(propertyService.getTotalActualAmount(billMasList, earlyPayRebate, taxWiseRebate, surCharge));
        model.setBillMasList(billMasList);
        if ("A".equals(model.getAssType())) {
            model.setDisplayMapAuthComp(presentMap);
        }
        if (model.getDisplayMap() == null || model.getDisplayMap().isEmpty() || !"A".equals(model.getAssType())) {
            model.setDisplayMap(presentMap);
        }
        updateBifurcationBill(billMasList, dto.getAssNo(), dto.getFlatNo(), UserSession.getCurrent().getOrganisation().getOrgid(),finYearId);
        model.setBillMasList(billMasList);
        propertyService.updateARVRVInBill(dto, billMasList);
        return billMasList;
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

        List<LookUp> lookupList = CommonMasterUtility.lookUpListByPrefix("FCT",
                UserSession.getCurrent().getOrganisation().getOrgid());
        lookupList.forEach(fact -> {
            List<ProvisionalAssesmentFactorDtlDto> factList = factorMap.get(Long.toString(fact.getLookUpId()));
            if (factList == null || factList.isEmpty()) {
                factorMap.put(Long.toString(fact.getLookUpId()), null);
            }
        });
        this.getModel().setDisplayfactorMap(factorMap);
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

    @RequestMapping(params = "editBifurcationForm", method = RequestMethod.POST)
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
        // model.getProvAsseFactDtlDto().clear();
        model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().forEach(det -> {
            det.getProvisionalAssesmentFactorDtlDtoList().clear();
        });
        return new ModelAndView("editBifurcationForm", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "backToSearchPage", method = RequestMethod.POST)
    public ModelAndView backToSearchPage(HttpServletRequest httpServletRequest) {
        this.sessionCleanup(httpServletRequest);
        return new ModelAndView("BifurcationValidn", MainetConstants.FORM_NAME, this.getModel());
    }

    private void getchargesForIndividualProperty(SelfAssesmentNewModel model) {
        ProvisionalAssesmentMstDto dto = model.getProvisionalAssesmentMstDto();
        ProvisionalAssesmentDetailDto provisionalAssesmentDetailDto = dto.getProvisionalAssesmentDetailDtoList()
                .get(dto.getProvisionalAssesmentDetailDtoList().size() - 1);

        List<String> listOfFlats = new ArrayList<>();
        dto.getProvisionalAssesmentDetailDtoList().forEach(detailList -> {
            if (detailList.getFirstAssesmentDate() != null) {
                detailList.setFirstAssesmentStringDate(
                        new SimpleDateFormat(MainetConstants.DATE_FRMAT).format(detailList.getFirstAssesmentDate()));
            }
            detailList.setLastAssesmentDate(detailList.getFirstAssesmentDate());

            if (detailList.getFlatNo() != null && !listOfFlats.contains(detailList.getFlatNo())) {
                listOfFlats.add(detailList.getFlatNo());
            }
        });
        dto.getProvisionalAssesmentDetailDtoList().get(dto.getProvisionalAssesmentDetailDtoList().size() - 1)
                .setLastAssesmentDate(provisionalAssesmentDetailDto.getFirstAssesmentDate());
        selfAssessmentService.setFactorMappingToAssDto(model.getProvAsseFactDtlDto(), dto);

        // Map<Long, BillDisplayDto> taxWiseRebate = new TreeMap<>();
        // propertyBRMSService.fetchCharges(dto, model.getDeptId(), taxWiseRebate, model.getFinYearList(), null,
        // model.getAssType(), model.getAssesmentManualDate(), null);

        List<TbBillMas> billMasList = new ArrayList<>();
        List<BillReceiptPostingDTO> reabteRecDtoList = new ArrayList<>();
        for (String flat : listOfFlats) {
            // selfAssessmentService.setFactorMappingToAssDto(model.getProvAsseFactDtlDto(), dto);
            ProvisionalAssesmentMstDto tempDto = new ProvisionalAssesmentMstDto();
            BeanUtils.copyProperties(dto, tempDto);
            List<ProvisionalAssesmentDetailDto> tempDetailList = dto.getProvisionalAssesmentDetailDtoList().stream()
                    .filter(detail -> detail.getFlatNo().equals(flat)).collect(Collectors.toList());

            tempDetailList.get(tempDetailList.size() - 1)
                    .setLastAssesmentDate(provisionalAssesmentDetailDto.getFirstAssesmentDate());

            tempDto.setProvisionalAssesmentDetailDtoList(tempDetailList);

            Map<Long, BillDisplayDto> tempTaxWiseRebate = new TreeMap<>();
            Map<Date, List<BillPresentAndCalculationDto>> schWiseChargeMap = propertyBRMSService.fetchCharges(tempDto,
                    model.getDeptId(), tempTaxWiseRebate, model.getFinYearList(), null, model.getAssType(),
                    model.getAssesmentManualDate(), null);

            List<TbBillMas> billMasterList = new ArrayList<>();
            billMasterList = propertyService.generateNewBill(schWiseChargeMap,
                    UserSession.getCurrent().getOrganisation(), model.getDeptId(), null, flat);
            billMasList.addAll(billMasterList);
            List<BillReceiptPostingDTO> tempReabteRecDtoList = selfAssessmentService
                    .knowkOffDemandLevelRebateAndExemption(billMasList, schWiseChargeMap,
                            UserSession.getCurrent().getOrganisation(), tempTaxWiseRebate);
            reabteRecDtoList.addAll(tempReabteRecDtoList);
            propertyService.taxCarryForward(billMasList, model.getOrgId());
        }
        model.setBillMasList(billMasList);
        model.setReabteRecDtoList(reabteRecDtoList);
    }

    @ResponseBody
    @RequestMapping(params = "getBillingMethod", method = { RequestMethod.POST })
    public List<String> getBillingMethodAndFlatList(@RequestParam("propNo") String propNo, HttpServletRequest request) {
        this.getModel().bind(request);
        SelfAssesmentNewModel model = this.getModel();
        List<String> flatNoList = null;
        String billingMethod = null;
        Long billingMethodId = primaryPropertyService.getBillMethodIdByPropNo(propNo,
                UserSession.getCurrent().getOrganisation().getOrgid());
        try {
            billingMethod = CommonMasterUtility
                    .getNonHierarchicalLookUpObject(billingMethodId, UserSession.getCurrent().getOrganisation())
                    .getLookUpCode();
        } catch (Exception e) {

        }
        this.getModel().setBillingMethod(billingMethod);
        if (StringUtils.isNotBlank(billingMethod) && StringUtils.equals(billingMethod, MainetConstants.FlagI)) {
            flatNoList = new ArrayList<String>();
            flatNoList = primaryPropertyService.getFlatNoIdByPropNo(propNo,
                    UserSession.getCurrent().getOrganisation().getOrgid());
        }
        model.setFlatNoList(flatNoList);
        return flatNoList;
    }
    
    private void updateBifurcationBill(List<TbBillMas> billList, String propNo, String flatNo, Long orgId,Long finId) {
    	List<TbBillMas> existBillList = null;
    	Long lastYear = propertyMainBillService.getMaxFinYearIdByPropNo(propNo, orgId);
    	if(lastYear != null) {
    		if(StringUtils.isNotBlank(flatNo)) {
        		
        	}else {
        	    existBillList = propertyMainBillService.fetchAllBillByPropNoAndCurrentFinId(propNo, orgId, lastYear);
        	}
        	TbBillMas latestBill = existBillList.get(0);
        	
        	latestBill.getTbWtBillDet().forEach(billDet ->{
        		billList.get(0).getTbWtBillDet().forEach(det ->{
        			if(billDet.getTaxId().equals(det.getTaxId())) {
        				double paidAmount = billDet.getBdCurTaxamt() - billDet.getBdCurBalTaxamt();
        				double oldBillAmount = billDet.getBdCurTaxamt() - det.getBdCurTaxamt();
        				double balanceAmount = oldBillAmount - paidAmount;
        				balanceAmount = Math.abs(balanceAmount);
        				double currentBillPaidAmount = det.getBdCurTaxamt() - balanceAmount;
        				if(currentBillPaidAmount >= 0) {
        					det.setBdCurBalTaxamt(Math.floor(currentBillPaidAmount));
        				}else {
        					det.setBdCurBalTaxamt(0.0);
        				}
        				billList.get(0).setBmTotalOutstanding(Math.abs(billList.get(0).getBmTotalOutstanding() - balanceAmount));
        				billList.get(0).setBmTotalBalAmount(Math.abs(billList.get(0).getBmTotalBalAmount() - balanceAmount));
        			}
        			
        		});
        	});
        	
        	if(billList.get(0).getBmTotalOutstanding() <= 0) {
        		billList.get(0).setBmPaidFlag(MainetConstants.FlagY);
        	}
    	}
    	
    }
}
