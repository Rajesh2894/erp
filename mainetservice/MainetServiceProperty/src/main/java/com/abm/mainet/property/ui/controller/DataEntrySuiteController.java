package com.abm.mainet.property.ui.controller;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.TbBillDet;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.model.JQGridResponse;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.property.domain.BillingScheduleDetailEntity;
import com.abm.mainet.property.domain.PropertyMastEntity;
import com.abm.mainet.property.dto.ArrayOfDiversionPlotDetails;
import com.abm.mainet.property.dto.ArrayOfKhasraDetails;
import com.abm.mainet.property.dto.ArrayOfPlotDetails;
import com.abm.mainet.property.dto.LandTypeApiDetailRequestDto;
import com.abm.mainet.property.dto.PropertyRoomDetailsDto;
import com.abm.mainet.property.dto.ProperySearchDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentDetailDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentFactorDtlDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentOwnerDtlDto;
import com.abm.mainet.property.repository.PropertyMainBillRepository;
import com.abm.mainet.property.service.AssesmentMastService;
import com.abm.mainet.property.service.BillingScheduleService;
import com.abm.mainet.property.service.DataEntrySuiteService;
import com.abm.mainet.property.service.IProvisionalAssesmentMstService;
import com.abm.mainet.property.service.IProvisionalBillService;
import com.abm.mainet.property.service.PrimaryPropertyService;
import com.abm.mainet.property.service.PropertyAuthorizationService;
import com.abm.mainet.property.service.PropertyBillPaymentService;
import com.abm.mainet.property.service.PropertyMainBillService;
import com.abm.mainet.property.service.PropertyService;
import com.abm.mainet.property.service.SelfAssessmentService;
import com.abm.mainet.property.service.ViewPropertyDetailsService;
import com.abm.mainet.property.ui.model.NewPropertyRegistrationModel;
import com.abm.mainet.property.ui.validator.DataEntrySuiteValidator;

@Controller
@RequestMapping({ "/DataEntrySuite.html", "/DataEntryAuthorisation.html" })
public class DataEntrySuiteController extends AbstractFormController<NewPropertyRegistrationModel> {

    private static final Logger LOGGER = Logger.getLogger(DataEntrySuiteController.class);

    @Resource
    private SelfAssessmentService selfAssessmentService;

    @Autowired
    private IFinancialYearService iFinancialYear;

    @Autowired
    private BillingScheduleService billingScheduleService;

    @Autowired
    private ServiceMasterService serviceMaster;

    @Autowired
    private IFileUploadService fileUpload;

    @Resource
    private PropertyBillPaymentService propertyBillPaymentService;

    @Resource
    private ILocationMasService iLocationMasService;

    @Autowired
    private IProvisionalBillService iProvisionalBillService;

    @Autowired
    private IWorkFlowTypeService iWorkFlowTypeService;

    @Autowired
    private IChecklistVerificationService iChecklistVerificationService;

    @Autowired
    private IProvisionalAssesmentMstService iProvisionalAssesmentMstService;

    @Autowired
    private DataEntrySuiteService dataEntrySuiteService;

    @Autowired
    private PropertyAuthorizationService propertyAuthorizationService;

    @Autowired
    private IAttachDocsService iAttachDocsService;

    @Autowired
    private IEmployeeService iEmployeeService;

    @Autowired
    private ViewPropertyDetailsService viewPropertyDetailsService;

    @Autowired
    private AssesmentMastService assesmentMastService;

    @Autowired
    private PropertyMainBillService propertyMainBillService;

    @Autowired
    private PrimaryPropertyService primaryPropertyService;
    
    @Autowired
    private PropertyService propertyService;
    
    @Autowired
    private PropertyMainBillRepository propertyMainBillRepository;
    
    @Resource
    private SeqGenFunctionUtility seqGenFunctionUtility;

    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(HttpServletRequest request) throws Exception {
        this.sessionCleanup(request);
        fileUpload.sessionCleanUpForFileUpload();
        getModel().setCommonHelpDocs("DataEntrySuite.html");
        setCommonFields(getModel());
        getModel().setLocation(dataEntrySuiteService.getLocationList(getModel().getOrgId(), getModel().getDeptId()));
        return defaultResult();
    }

    private void setCommonFields(NewPropertyRegistrationModel model) {
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        ServiceMaster service = serviceMaster.getServiceByShortName(MainetConstants.Property.DES, orgId);
        model.setOrgId(orgId);
        model.setServiceMaster(service);
        model.setDeptId(service.getTbDepartment().getDpDeptid());
        model.setServiceId(service.getSmServiceId());
        model.setAssType(MainetConstants.Property.DATA_ENTRY_SUITE);
        model.setServiceShortCode(MainetConstants.Property.DES);

    }

    @RequestMapping(params = "proceed", method = RequestMethod.POST)
    public ModelAndView confirmToProceed(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        NewPropertyRegistrationModel model = this.getModel();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        if (model.getAssType().equals(MainetConstants.Property.DATA_ENTRY_SUITE) || model.getSchduleId() == null) {
            List<LookUp> list = dataEntrySuiteService.setScheduleListForArrEntry(
                    getModel().getProvisionalAssesmentMstDto().getAssAcqDate(),
                    orgId,
                    getModel().getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().get(0).getFaYearId());
            model.setScheduleForArrEntry(list);
        }
        // #119956 - set flat wise arrears
        if (model.getFlatNoOfRow() != null) {
            if (CollectionUtils
                    .isNotEmpty(model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList())) {
                ProvisionalAssesmentDetailDto provisionalAssesmentDetailDto = getModel().getProvisionalAssesmentMstDto()
                        .getProvisionalAssesmentDetailDtoList().get(model.getFlatNoOfRow().intValue());
                List<TbBillMas> tempBillMasList = model.getBillMasList().stream()
                        .filter(bill -> bill.getFlatNo().equals(provisionalAssesmentDetailDto.getFlatNo()))
                        .collect(Collectors.toList());
                if (CollectionUtils.isEmpty(tempBillMasList)) {
                    model.setFinYearFlag(MainetConstants.FlagY); // to reset financial year on next row
                } else {
                    BillingScheduleDetailEntity billSchDet = billingScheduleService.getSchedulebySchFromDate(orgId,
                            tempBillMasList.get(0).getBmFromdt());
                    model.setSchduleId(billSchDet.getSchDetId()); // to set financial year bill wise on UI
                }
                model.setTempBillMasList(tempBillMasList);
            }
            return new ModelAndView("DataEntryOutStandingForFlat", MainetConstants.FORM_NAME, model);
        } else {
            return new ModelAndView("DataEntryOutStanding", MainetConstants.FORM_NAME, model);
        }
    }

    @RequestMapping(params = "nextToViewAfterArrear", method = RequestMethod.POST)
    public ModelAndView nextToViewAfterArrear(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        NewPropertyRegistrationModel model = this.getModel();
        ModelAndView mv = new ModelAndView("DataEntryOutStanding", MainetConstants.FORM_NAME, model);
        ProvisionalAssesmentMstDto dto = model.getProvisionalAssesmentMstDto();
        //Defect #168978
//        if(StringUtils.isNotBlank(dto.getAssOldpropno())) {
//			boolean checkOldPropNoExist = propertyService.checkOldPropNoExist(dto.getAssOldpropno(),
//					UserSession.getCurrent().getOrganisation().getOrgid());
//			if(checkOldPropNoExist) {
//				model.addValidationError("Already PTIN exist with "+ dto.getAssOldpropno());
//			}
//         }
        model.validateBean(model, DataEntrySuiteValidator.class);
        if (model.hasValidationErrors()) {
            mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
            return mv;
        }
        this.getModel().setDropDownValues();

        dataEntrySuiteService.setFactorMappingToAssDto(model.getProvAsseFactDtlDto(), dto);
        return new ModelAndView("DataEntrySuiteView", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "nextToView", method = RequestMethod.POST)
    public ModelAndView nextToView(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        NewPropertyRegistrationModel model = this.getModel();
        ProvisionalAssesmentMstDto dto = model.getProvisionalAssesmentMstDto();
        if(StringUtils.isNotBlank(dto.getAssOldpropno())) {
			boolean checkOldPropNoExist = propertyService.checkOldPropNoExist(dto.getAssOldpropno(),
					UserSession.getCurrent().getOrganisation().getOrgid());
			if(checkOldPropNoExist) {
				model.addValidationError("Already PTIN exist with "+ dto.getAssOldpropno());
			}
         }
        if (model.hasValidationErrors()) {
            return defaultMyResult();
        }
        this.getModel().setDropDownValues();
        Organisation org = UserSession.getCurrent().getOrganisation();
        dataEntrySuiteService.setFactorMappingToAssDto(model.getProvAsseFactDtlDto(), dto);
        if (StringUtils.isBlank(dto.getIsGroup())) {
            dto.setParentGrp1(null);
            dto.setParentGrp2(null);
            dto.setParentPropName(null);
            dto.setGroupPropName(null);
        }
        if (dto.getParentGrp1() != null && dto.getParentGrp2() != null) {
            LookUp parentLookUp = CommonMasterUtility.getHierarchicalLookUp(dto.getParentGrp1(), org.getOrgid());
            dto.setParentPropName(parentLookUp.getLookUpDesc());
            LookUp grpLookUp = CommonMasterUtility.getHierarchicalLookUp(dto.getParentGrp2(), org.getOrgid());
            dto.setGroupPropName(grpLookUp.getLookUpDesc());
        }
        model.setProvisionalAssesmentMstDto(dto);
        return new ModelAndView("DataEntrySuiteView", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "financialYear", method = RequestMethod.POST)
    public ModelAndView yearFrequancy(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws Exception {
        getModel().bind(httpServletRequest);
        NewPropertyRegistrationModel model = this.getModel();
        this.getModel().setBillMasList(null);
        Organisation org = UserSession.getCurrent().getOrganisation();
        List<TbBillMas> billMasList = dataEntrySuiteService.createProvisionalBillForDataEntry(model.getDeptId(),
                model.getSchduleId(), org.getOrgid(),
                model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().get(0).getFaYearId(), null);
        this.getModel().setBillMasList(billMasList);
        List<FinancialYear> financialYearList = dataEntrySuiteService
                .getFinYearListForDataEntry(model.getProvisionalAssesmentMstDto().getAssAcqDate(), org.getOrgid());
        List<Long> finYearList = financialYearList.stream()
                .map(FinancialYear::getFaYear)
                .collect(Collectors.toList());
        String financialYear = null;
        for (final FinancialYear finYearTemp : financialYearList) {
            financialYear = Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate());
            getModel().getFinancialYearMapForTax().put(finYearTemp.getFaYear(), financialYear);
        }
        this.getModel().setFinYearList(finYearList);

        return new ModelAndView("DataEntryOutStanding", MainetConstants.FORM_NAME, model);

    }

    @RequestMapping(params = "getFactorValueDiv", method = RequestMethod.POST)
    public ModelAndView getFactorValueDiv(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @RequestParam(value = "factorCode") String factorCode) {
        this.getModel().bind(httpServletRequest);
        NewPropertyRegistrationModel model = this.getModel();
        LookUp lookup = model.getFactorLookup(factorCode);
        model.setFactorPrefix(lookup.getLookUpCode());
        model.setFactorId(lookup.getLookUpId());
        return new ModelAndView("NewPropRegUnitSpec", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "getMappingFactor")

    public @ResponseBody Map<Long, String> getMappingFactor(@RequestParam("factorType") String factorType) {
        Organisation org = UserSession.getCurrent().getOrganisation();
        Map<Long, String> factorMap = new HashMap<>(0);
        List<LookUp> factorLookup = CommonMasterUtility.lookUpListByPrefix(factorType, org.getOrgid());
        for (LookUp lookUp : factorLookup) {
            factorMap.put(lookUp.getLookUpId(), lookUp.getDescLangFirst());
        }
        return factorMap;
    }

    @RequestMapping(params = "getOwnershipTypeDiv", method = RequestMethod.POST)
    public ModelAndView getOwnershipTypeDiv(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @RequestParam(value = "ownershipType") String ownershipType) {
        this.getModel().bind(httpServletRequest);
        NewPropertyRegistrationModel model = this.getModel();
        model.setOwnershipPrefix(ownershipType);
        LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp(ownershipType, MainetConstants.Property.propPref.OWT,
                UserSession.getCurrent().getOrganisation());
        model.setOwnershipTypeValue(lookup.getDescLangFirst());
        model.getProvisionalAssesmentMstDto().setAssOwnerType(lookup.getLookUpId());
        return new ModelAndView("NewPropRegOwner", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "getOwnershipTypesDiv", method = RequestMethod.POST)
    public ModelAndView getOwnershipTypeDet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @RequestParam(value = "ownershipType") String ownershipType) {
        this.getModel().bind(httpServletRequest);
        NewPropertyRegistrationModel model = this.getModel();
        model.setOwnershipPrefix(ownershipType);
        LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp(ownershipType, MainetConstants.Property.propPref.OWT,
                UserSession.getCurrent().getOrganisation());
        model.setOwnershipTypeValue(lookup.getDescLangFirst());
        model.getProvisionalAssesmentMstDto().setAssOwnerType(lookup.getLookUpId());
        return new ModelAndView("NewPropRegOwner", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "getFinancialYear", method = RequestMethod.POST)
    public @ResponseBody Map<Long, String> getyearOfAcquisition(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            String yearOfAcq) throws Exception {
        this.getModel().bind(httpServletRequest);
        NewPropertyRegistrationModel model = this.getModel();
        Map<Long, String> financialYearMap = new LinkedHashMap<>();
        model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().clear();
        model.getProvAsseFactDtlDto().clear();
        Date selectedDate = Utility.stringToDate(yearOfAcq);
        Long selectedYear = iFinancialYear.getFinanceYearId(selectedDate);
        Long currentYear = iFinancialYear.getFinanceYearId(new Date());
        if (selectedYear.equals(currentYear)) {
            String currentYearStr = Utility.getFinancialYearFromDate(new Date());
            financialYearMap.put(currentYear, currentYearStr);
            return financialYearMap;
        } else {
            return getModel().getFinancialYearMap();
        }
    }

    @RequestMapping(params = "getBillDate", method = RequestMethod.POST)
    public @ResponseBody Long getyearOfAcquisitions(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            String yearOfAcq) {
        this.getModel().bind(httpServletRequest);
        NewPropertyRegistrationModel model = this.getModel();
        model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().clear();
        Date currentDate = Utility.stringToDate(yearOfAcq);
        model.getProvAsseFactDtlDto().clear();  // clear dto on year change
        return iFinancialYear.getFinanceYearId(currentDate);

    }

    @RequestMapping(params = "deleteUnitTableRow", method = RequestMethod.POST)
    public @ResponseBody void deleteUnitTableRow(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse, @RequestParam(value = "deletedRowCount") int deletedRowCount) {
        this.getModel().bind(httpServletRequest);
        NewPropertyRegistrationModel model = this.getModel();
        ProvisionalAssesmentDetailDto detDto = model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList()
                .get(deletedRowCount);
        if (CollectionUtils.isNotEmpty(model.getBillMasList())) {
            List<TbBillMas> newList = model
                    .getBillMasList().stream().filter(mainBill -> (mainBill.getFlatNo() != null
                            && detDto.getFlatNo() != null && !mainBill.getFlatNo().equals(detDto.getFlatNo())))
                    .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(newList)) {
                model.setBillMasList(newList);
            }
        }
        model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().remove(detDto);
    }

    @RequestMapping(params = "deleteUnitSpecificInfo", method = RequestMethod.POST)
    public @ResponseBody void deleteUnitSpecificInfo(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse, @RequestParam(value = "deletedUnitRow") Integer deletedUnitRow) {
        this.getModel().bind(httpServletRequest);
        NewPropertyRegistrationModel model = this.getModel();
        model.setUnitStatusCount(deletedUnitRow);
        for (ProvisionalAssesmentFactorDtlDto FactorDtlDto : model.getProvAsseFactDtlDto()) {
            if (model.getUnitStatusCount() == deletedUnitRow) {
                model.getProvAsseFactDtlDto().remove(FactorDtlDto);
            }
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

    @RequestMapping(params = "showDetails", method = RequestMethod.POST)
    public ModelAndView defaultLoad(@RequestParam("appNo") final String propertyNo, @RequestParam("taskId") final long serviceId,
            @RequestParam("actualTaskId") final long taskId, final HttpServletRequest httpServletRequest) throws Exception {
        sessionCleanup(httpServletRequest);
        fileUpload.sessionCleanUpForFileUpload();
        getModel().setCommonHelpDocs("DataEntrySuite.html");
        getModel().setLastChecker(iWorkFlowTypeService.isLastTaskInCheckerTaskList(taskId));
        Organisation org = UserSession.getCurrent().getOrganisation();
        final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        ServiceMaster service = serviceMaster.getServiceMaster(serviceId, orgId);
        Long deptId = service.getTbDepartment().getDpDeptid();
        getModel().setAssType(MainetConstants.FlagA);
        getModel().setAuthEditFlag(MainetConstants.FlagN);
        getModel().setOrgId(orgId);
        getModel().setModeType(MainetConstants.FlagV);

        final List<ProvisionalAssesmentMstDto> provAssDtoList = iProvisionalAssesmentMstService
                .getDataEntryPropDetailFromProvAssByPropNo(orgId, propertyNo, "A");
        final ProvisionalAssesmentMstDto provisionalAssesmentMstDto = propertyAuthorizationService
                .getAssesmentMstDtoForDisplay(provAssDtoList);

        getModel().setAssType(MainetConstants.Property.ASESS_AUT);// to identify call of model from Authorization

        LookUp ownerType = CommonMasterUtility.getNonHierarchicalLookUpObject(
                provisionalAssesmentMstDto.getAssOwnerType(), UserSession.getCurrent().getOrganisation());
        getModel().setOwnershipPrefix(ownerType.getLookUpCode());

        if (provisionalAssesmentMstDto.getAssLandType() != null) {

            LookUp landTypePrefix = CommonMasterUtility.getNonHierarchicalLookUpObject(
                    provisionalAssesmentMstDto.getAssLandType(), UserSession.getCurrent().getOrganisation());
            getModel().setLandTypePrefix(landTypePrefix.getLookUpCode());
        }
        List<TbBillMas> billMasList = iProvisionalBillService
                .getProvisionalBillMasByPropertyNo(provisionalAssesmentMstDto.getAssNo(), orgId);

        this.getModel().setBillMasList(billMasList);

        LookUp loadFinYearlookupForDes = null;
        try {
            loadFinYearlookupForDes = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.ENV.FYD,
                    MainetConstants.ENV, UserSession.getCurrent().getOrganisation());
        } catch (Exception exception) {
            LOGGER.error("No Prefix found for ENV(FYD)");
        }
        if (loadFinYearlookupForDes != null && StringUtils.isNotBlank(loadFinYearlookupForDes.getOtherField())) {
            getModel().setFinancialYearMap(
                    dataEntrySuiteService.getConfiguredYearListForDES(loadFinYearlookupForDes.getOtherField(),
                            getModel().getOrgId()));
        } else {
            getModel().setFinancialYearMap(dataEntrySuiteService.getYearListForDES());
        }
        final List<FinancialYear> compFinYearList = iFinancialYear.getAllFinincialYear();
        checkForMissingYear(provisionalAssesmentMstDto, compFinYearList); // if year is not current and previous year
        String financialYear = null;
        for (final FinancialYear finYearTemp : compFinYearList) {
            financialYear = Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate());
            getModel().getFinancialYearMapForTax().put(finYearTemp.getFaYear(), financialYear);
        }

        List<ProvisionalAssesmentFactorDtlDto> factorMap = factorMappingForView(provisionalAssesmentMstDto);
        List<ProvisionalAssesmentFactorDtlDto> factorMapBefore = new ArrayList<>();
        factorMap.forEach(fact -> {
            ProvisionalAssesmentFactorDtlDto factBefore = new ProvisionalAssesmentFactorDtlDto();
            BeanUtils.copyProperties(fact, factBefore);
            factorMapBefore.add(factBefore);
        });
        getModel().setProvAsseFactDtlDto(factorMap);
        getModel().setAuthCompFactDto(factorMapBefore);
        getModel().setServiceId(serviceId);
        String serviceShortCode = service.getSmShortdesc();
        getModel().setServiceShortCode(serviceShortCode);
        getModel().setDeptId(deptId);
        // getModel().setSchedule(selfAssessmentService.getAllBillscheduleWithoutCurrentScheduleByOrgid(org));
        getModel().setProvAssMstDtoList(provAssDtoList);
        setDataIntoProvAssessDto(orgId, provisionalAssesmentMstDto, factorMapBefore);
        getModel().setProvisionalAssesmentMstDto(provisionalAssesmentMstDto);
        if (getModel().getLandTypePrefix() != null) {
            setlandTypeDetails(getModel());
        }
        getModel().setDropDownValues();
        getModel().setAuthComBillList(billMasList);
        List<LookUp> taxDescription = dataEntrySuiteService.getTaxDescription(UserSession.getCurrent().getOrganisation(),
                deptId);
        getModel().setTaxMasterList(taxDescription);
        getModel().setDocumentList(iChecklistVerificationService.getDocumentUploadedByRefNo(propertyNo, orgId));
        getModel().getWorkflowActionDto().setTaskId(taskId);
        getModel().getWorkflowActionDto().setReferenceId(propertyNo);
        List<LookUp> list = dataEntrySuiteService
                .setScheduleListForArrEntry(getModel().getProvisionalAssesmentMstDto().getAssAcqDate(), orgId,
                        provisionalAssesmentMstDto.getProvisionalAssesmentDetailDtoList().get(0).getFaYearId());
        if (billMasList != null && !billMasList.isEmpty()) {
            BillingScheduleDetailEntity billSchDet = billingScheduleService
                    .getSchedulebySchFromDate(orgId, billMasList.get(0).getBmFromdt());
            getModel().setScheduleForArrEntry(list);
            getModel().setSchduleId(billSchDet.getSchDetId());
        }
        getModel().setAuthCompBeforeDto(
                iProvisionalAssesmentMstService.copyProvDtoDataToOtherDto(provisionalAssesmentMstDto));// Setting before change
        getModel().setAttachDocsList(iAttachDocsService.findAllDocLikeReferenceId(orgId, provisionalAssesmentMstDto.getAssNo()));                                                                    // property
        getModel().setIntgrtionWithBP(CommonMasterUtility
                .getDefaultValueByOrg(MainetConstants.Property.IBP, UserSession.getCurrent().getOrganisation()).getLookUpCode());

        return new ModelAndView("DataEntrySuiteView", MainetConstants.FORM_NAME, getModel());

    }

    private void setlandTypeDetails(NewPropertyRegistrationModel model) {
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

    @RequestMapping(params = "edit", method = RequestMethod.POST)
    public ModelAndView editForm(@RequestParam String rowId,
            final HttpServletRequest httpServletRequest) throws Exception {
        NewPropertyRegistrationModel model = this.getModel();
        String flatNo = null;
        if (rowId.contains(MainetConstants.operator.UNDER_SCORE)) {
            String[] splitStr = rowId.split(MainetConstants.operator.UNDER_SCORE);
            rowId = splitStr[0];
            flatNo = splitStr[1];
        }
        ServiceMaster serviceDES = serviceMaster.getServiceByShortName(MainetConstants.Property.DES,
                UserSession.getCurrent().getOrganisation().getOrgid());
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        // If it is property that belongs to parent then to edit it
        PropertyMastEntity propMas = null;
        if (flatNo == null) {
            propMas = primaryPropertyService.getPropertyDetailsByPropNo(rowId, orgId);
        }
        boolean validate = true;
        boolean isGrpPropFlag = false;
        if (flatNo != null) {
            validate = dataEntrySuiteService.validateDataEntrySuiteWithFlatNo(rowId, flatNo,
                    UserSession.getCurrent().getOrganisation().getOrgid(), serviceDES.getSmServiceId());
        } else {
            validate = dataEntrySuiteService.validateDataEntrySuite(rowId,
                    UserSession.getCurrent().getOrganisation().getOrgid(), serviceDES.getSmServiceId());
        }
        if (!validate && propMas != null && StringUtils.equals(propMas.getIsGroup(), MainetConstants.FlagY)) {
            isGrpPropFlag = true;
        }
        if (propMas != null && StringUtils.equals(propMas.getIsGroup(), MainetConstants.FlagY)) {
            validate = true;
        }
        String receiptDelFlag = null;
        if (flatNo != null) {
            receiptDelFlag = "Y";// need to change
        } else {
            receiptDelFlag = assesmentMastService.getReceiptDelFlag(rowId,
                    UserSession.getCurrent().getOrganisation().getOrgid());
        }
        if (validate || StringUtils.equals(receiptDelFlag, MainetConstants.FlagY)) {
            sessionCleanup(httpServletRequest);
            fileUpload.sessionCleanUpForFileUpload();
            getModel().setCommonHelpDocs("DataEntrySuite.html");
            Organisation org = UserSession.getCurrent().getOrganisation();
            ServiceMaster service = serviceMaster.getServiceByShortName(MainetConstants.Property.DES, orgId);
            // For map or view property on GIS map
            try {
                LookUp gisFlag = CommonMasterUtility.getValueFromPrefixLookUp("GIS",
                        "MLI", org);
                getModel().setGisValue(gisFlag.getOtherField());
                if (gisFlag != null) {
                    getModel().setGisUri(
                            ServiceEndpoints.GisItegration.GIS_URI + ServiceEndpoints.GisItegration.PROPERTY_GIS_LAYER_NAME);
                }
            } catch (Exception e) {
                throw new FrameworkException("No Prefix Configure in Prefix Master for Prifix =MLI", e);
            }
            getModel().setAssType(MainetConstants.FlagE);
            getModel().setModeType(MainetConstants.FlagV);
            getModel().setOrgId(orgId);
            getModel().setDataFrom(MainetConstants.FlagP);
            List<ProvisionalAssesmentMstDto> provAssDtoList = null;
            if (flatNo != null) {
                provAssDtoList = iProvisionalAssesmentMstService
                        .getDataEntryPropDetailFromProvAssByPropNoAndFlatNo(orgId, rowId, flatNo, MainetConstants.FlagA);
            } else {
                provAssDtoList = iProvisionalAssesmentMstService.getDataEntryPropDetailFromProvAssByPropNo(orgId, rowId,
                        MainetConstants.FlagA);
            }
            if (provAssDtoList == null || provAssDtoList.isEmpty()) {
                getModel().setDataFrom(MainetConstants.FlagM);
                if (flatNo != null) {
                    provAssDtoList = assesmentMastService.getPropDetailFromAssByPropNoFlatNo(orgId, rowId, flatNo);
                } else {
                    provAssDtoList = assesmentMastService.getPropDetailFromAssByPropNo(orgId, rowId);
                }
            }
            final ProvisionalAssesmentMstDto provisionalAssesmentMstDto = propertyAuthorizationService
                    .getAssesmentMstDtoForDisplay(provAssDtoList);
            if (provisionalAssesmentMstDto != null && provisionalAssesmentMstDto.getBillMethod() != null) {
                String billMethod = CommonMasterUtility.getNonHierarchicalLookUpObject(
                        provisionalAssesmentMstDto.getBillMethod(), UserSession.getCurrent().getOrganisation()).getLookUpCode();
                getModel().setBillMethodDesc(billMethod);
            }
            Long deptId = service.getTbDepartment().getDpDeptid();
            getModel().setDeptId(deptId);
            LookUp ownerType = CommonMasterUtility.getNonHierarchicalLookUpObject(
                    provisionalAssesmentMstDto.getAssOwnerType(), UserSession.getCurrent().getOrganisation());
            getModel().setOwnershipPrefix(ownerType.getLookUpCode());

            List<TbBillMas> billMasList = null;
            if (flatNo != null) {
                billMasList = iProvisionalBillService
                        .getProvisionalBillMasByPropertyNoAndFlatNo(provisionalAssesmentMstDto.getAssNo(), flatNo, orgId);
            } else {
                billMasList = iProvisionalBillService
                        .getProvisionalBillMasByPropertyNo(provisionalAssesmentMstDto.getAssNo(), orgId);
            }

            if (billMasList == null || billMasList.isEmpty()) {
                if (flatNo != null) {
                    billMasList = propertyMainBillService.fetchAllBillByPropNoAndFlatNo(provisionalAssesmentMstDto.getAssNo(),
                            flatNo,
                            orgId);
                } else {
                    billMasList = propertyMainBillService.fetchAllBillByPropNo(provisionalAssesmentMstDto.getAssNo(),
                            orgId);
                }
            }
            this.getModel().setBillMasList(billMasList);
            final List<FinancialYear> compFinYearList = iFinancialYear.getAllFinincialYear();
            LookUp loadFinYearlookupForDes = null;
            try {
                loadFinYearlookupForDes = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.ENV.FYD,
                        MainetConstants.ENV, UserSession.getCurrent().getOrganisation());
            } catch (Exception exception) {
                LOGGER.error("No Prefix found for ENV(FYD)");
            }
            if (loadFinYearlookupForDes != null && StringUtils.isNotBlank(loadFinYearlookupForDes.getOtherField())) {
                getModel().setFinancialYearMap(
                        dataEntrySuiteService.getConfiguredYearListForDES(loadFinYearlookupForDes.getOtherField(),
                                getModel().getOrgId()));
            } else {
                getModel().setFinancialYearMap(dataEntrySuiteService.getYearListForDES());
            }
            checkForMissingYear(provisionalAssesmentMstDto, compFinYearList); // if year is not current and previous year
            String financialYear = null;
            for (final FinancialYear finYearTemp : compFinYearList) {
                financialYear = Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate());
                getModel().getFinancialYearMapForTax().put(finYearTemp.getFaYear(), financialYear);
            }
            List<ProvisionalAssesmentFactorDtlDto> factorMap = factorMappingForView(provisionalAssesmentMstDto);
            List<ProvisionalAssesmentFactorDtlDto> factorMapBefore = new ArrayList<>();
            factorMap.forEach(fact -> {
                ProvisionalAssesmentFactorDtlDto factBefore = new ProvisionalAssesmentFactorDtlDto();
                BeanUtils.copyProperties(fact, factBefore);
                factorMapBefore.add(factBefore);
            });

            getModel().setProvAsseFactDtlDto(factorMap);
            getModel().setAuthCompFactDto(factorMapBefore);
            getModel().setServiceId(model.getServiceId());
            getModel().setDeptId(model.getDeptId());
            // getModel().setSchedule(selfAssessmentService.getAllBillscheduleWithoutCurrentScheduleByOrgid(org));
            getModel().setProvAssMstDtoList(provAssDtoList);
            if (provisionalAssesmentMstDto.getAssLandType() != null) {

                LookUp landTypePrefix = CommonMasterUtility.getNonHierarchicalLookUpObject(
                        provisionalAssesmentMstDto.getAssLandType(), UserSession.getCurrent().getOrganisation());
                getModel().setLandTypePrefix(landTypePrefix.getLookUpCode());
            }
            setDataIntoProvAssessDto(orgId, provisionalAssesmentMstDto, factorMapBefore);
            provisionalAssesmentMstDto.getProvisionalAssesmentDetailDtoList().forEach(det -> {
                det.getProvisionalAssesmentFactorDtlDtoList().clear();
            });

            if (provisionalAssesmentMstDto.getParentPropNo() != null
                    && provisionalAssesmentMstDto.getGroupPropNo() != null) {
                provisionalAssesmentMstDto.setParentGrp1(Long.valueOf(provisionalAssesmentMstDto.getParentPropNo()));
                provisionalAssesmentMstDto.setParentGrp2(Long.valueOf(provisionalAssesmentMstDto.getGroupPropNo()));
            }
            getModel().setProvisionalAssesmentMstDto(provisionalAssesmentMstDto);

            if (getModel().getLandTypePrefix() != null) {
                setlandTypeDetails(getModel());
            }
            getModel().setDropDownValues();
            getModel().setAuthComBillList(billMasList);
            List<LookUp> taxDescription = dataEntrySuiteService.getTaxDescription(UserSession.getCurrent().getOrganisation(),
                    deptId);
            getModel().setTaxMasterList(taxDescription);
            getModel().setDocumentList(iChecklistVerificationService.getDocumentUploadedByRefNo(rowId, orgId));
            getModel().getWorkflowActionDto().setReferenceId(rowId);
            List<LookUp> list = dataEntrySuiteService.setScheduleListForArrEntry(
                    getModel().getProvisionalAssesmentMstDto().getAssAcqDate(),
                    UserSession.getCurrent().getOrganisation().getOrgid(),
                    provisionalAssesmentMstDto.getProvisionalAssesmentDetailDtoList().get(0).getFaYearId());
            if (billMasList != null && !billMasList.isEmpty()) {
                BillingScheduleDetailEntity billSchDet = billingScheduleService
                        .getSchedulebySchFromDate(orgId, billMasList.get(0).getBmFromdt());
                getModel().setScheduleForArrEntry(list);
                getModel().setSchduleId(billSchDet.getSchDetId());
            }
            getModel()
                    .setAttachDocsList(
                            iAttachDocsService.findAllDocLikeReferenceId(orgId, provisionalAssesmentMstDto.getAssNo()));                                                                    // property
            getModel().setIntgrtionWithBP(CommonMasterUtility
                    .getDefaultValueByOrg(MainetConstants.Property.IBP, UserSession.getCurrent().getOrganisation())
                    .getLookUpCode());
            getModel().setSelfAss(null);
            Long max = 0l;
            List<ProvisionalAssesmentDetailDto> detailDto = model.getProvisionalAssesmentMstDto()
                    .getProvisionalAssesmentDetailDtoList();
            if (detailDto != null && !detailDto.isEmpty()) {
                for (ProvisionalAssesmentDetailDto dto : detailDto) {
                    if (dto.getAssdUnitNo() != null && dto.getAssdUnitNo() > max) {
                        max = dto.getAssdUnitNo();
                    }
                }
            }
            model.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            model.setMaxUnit(max);
            getModel().setLocation(dataEntrySuiteService.getLocationList(org.getOrgid(),
                    deptId));
            getModel().setTaxCollList(dataEntrySuiteService.getTaxCollectorList(deptId, org.getOrgid()));
            if (isGrpPropFlag)
                getModel().setIsGrpPropertyFlag(MainetConstants.FlagY);
            return new ModelAndView("DataEntrySuiteView", MainetConstants.FORM_NAME, getModel());
        }
        return null;
    }

    private void checkForMissingYear(final ProvisionalAssesmentMstDto provisionalAssesmentMstDto,
            final List<FinancialYear> compFinYearList) {
        boolean yearcontain = getModel().getFinancialYearMapForTax().containsKey(provisionalAssesmentMstDto.getFaYearId());
        if (!yearcontain) {
            compFinYearList.stream().filter(year -> year.getFaYear() == provisionalAssesmentMstDto.getFaYearId())
                    .forEach(year -> {
                        String missYear;
                        try {
                            missYear = Utility.getFinancialYearFromDate(year.getFaFromDate());
                            getModel().getFinancialYearMap().put(provisionalAssesmentMstDto.getFaYearId(), missYear);
                        } catch (Exception e) {
                            throw new FrameworkException(e);
                        }
                    });
        }
    }

    private void setDataIntoProvAssessDto(final long orgId, final ProvisionalAssesmentMstDto provisionalAssesmentMstDto,
            List<ProvisionalAssesmentFactorDtlDto> factorMapBefore) {
        provisionalAssesmentMstDto.setOrgId(orgId);
        if (provisionalAssesmentMstDto.getAssCorrAddress() == null) {
            provisionalAssesmentMstDto.setProAsscheck(MainetConstants.FlagY);
        } else {
            provisionalAssesmentMstDto.setProAsscheck(MainetConstants.FlagN);
        }
        if (provisionalAssesmentMstDto.getAssLpYear() == null) {
            provisionalAssesmentMstDto.setProAssBillPayment(MainetConstants.Property.NOT_APP);
        } else {
            provisionalAssesmentMstDto.setProAssBillPayment(MainetConstants.Property.MANUAL);
        }
        provisionalAssesmentMstDto
                .setLocationName(iLocationMasService.getLocationNameById(provisionalAssesmentMstDto.getLocId(), orgId));

        if (provisionalAssesmentMstDto.getTaxCollEmp() != null) {
            Employee emp = iEmployeeService.findEmployeeByIdAndOrgId(provisionalAssesmentMstDto.getTaxCollEmp(), orgId);
            if (emp != null) {
                provisionalAssesmentMstDto
                        .setTaxCollEmpDesc(emp.getEmpname() + " " + emp.getEmplname() + "-" + emp.getDesignation().getDsgname());
            }
        }
        List<LookUp> lookupList = CommonMasterUtility.lookUpListByPrefix("FCT",
                UserSession.getCurrent().getOrganisation().getOrgid());
        lookupList.forEach(factQuest -> {
            boolean result = factorMapBefore.stream()
                    .anyMatch(fact -> factQuest.getLookUpId() == fact.getAssfFactorId().longValue());
            if (result) {
                provisionalAssesmentMstDto.getProAssfactor().add(MainetConstants.FlagY);
            } else {
                provisionalAssesmentMstDto.getProAssfactor().add(MainetConstants.FlagN);
            }
        });

    }

    @RequestMapping(params = "deleteFactorStatus", method = RequestMethod.POST)
    public @ResponseBody void deleteFactorStatus(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse, @RequestParam(value = "factorCode") String factorCode) {
        this.getModel().bind(httpServletRequest);
        NewPropertyRegistrationModel model = this.getModel();
        int size = model.getProvAsseFactDtlDto().size();
        for (int i = size; i > 0; i--) {
            int j = i - 1;
            ProvisionalAssesmentFactorDtlDto factorDto = model.getProvAsseFactDtlDto().get(j);
            if (factorDto.getFactorValueCode() != null && factorDto.getFactorValueCode().equals(factorCode)) {
                model.getProvAsseFactDtlDto().remove(factorDto);
            }
        }
        // while deleting factor :-required to delete from detail list where factor already mapped
        model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().forEach(det -> {
            det.getProvisionalAssesmentFactorDtlDtoList().clear();
        });
    }

    @RequestMapping(params = "deleteOwnerTable", method = RequestMethod.POST)
    public @ResponseBody void deleteOwnerTable(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse, @RequestParam(value = "deletedOwnerRowId") Integer deletedOwnerRowId) {
        this.getModel().bind(httpServletRequest);
        NewPropertyRegistrationModel model = this.getModel();
        model.setOwnerDetailTableCount(deletedOwnerRowId);
        for (ProvisionalAssesmentOwnerDtlDto dto : model.getProvisionalAssesmentMstDto()
                .getProvisionalAssesmentOwnerDtlDtoList()) {
            if (model.getOwnerDetailTableCount() == deletedOwnerRowId) {
                model.getProvisionalAssesmentMstDto().getProvisionalAssesmentOwnerDtlDtoList().remove(dto);
            }
        }
    }

    @RequestMapping(params = "getFinanceYearListFromGivenDate", method = RequestMethod.POST)
    public @ResponseBody List<Long> getFinanceYearListFromGivenDate(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse, @RequestParam(value = "finYearId") Long finYearId) {
        List<Long> finYearList = null;
        List<FinancialYear> financialYearList = iFinancialYear
                .getFinanceYearListFromGivenDate(UserSession.getCurrent().getOrganisation().getOrgid(), finYearId, new Date());
        if (!financialYearList.isEmpty()) {
            finYearList = new ArrayList<>();
            for (FinancialYear financialYearEach : financialYearList) {
                Long finYear = iFinancialYear.getFinanceYearId(financialYearEach.getFaFromDate());
                finYearList.add(finYear);
            }
        }
        this.getModel().setFinYearList(finYearList);
        return finYearList;
    }

    @RequestMapping(params = "getNextScheduleFromLastPayDet", method = RequestMethod.POST)
    public @ResponseBody List<Object> getNextScheduleFromLastPayDet(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse, @RequestParam(value = "schDetId") Long schDetId) {
        NewPropertyRegistrationModel model = this.getModel();
        List<Object> acqDateDetail = new ArrayList<>();
        List<BillingScheduleDetailEntity> nextSchedule = billingScheduleService
                .getNextScheduleFromLastPayDet(UserSession.getCurrent().getOrganisation().getOrgid(), schDetId);
        BillingScheduleDetailEntity billingScheduleDetailEntity = nextSchedule.get(0);
        BillingScheduleDetailEntity selectedSch = billingScheduleService.getSchDetailByScheduleId(schDetId,
                UserSession.getCurrent().getOrganisation().getOrgid());
        acqDateDetail.add(selectedSch.getBillToDate());
        acqDateDetail.add(iFinancialYear.getFinanceYearId(billingScheduleDetailEntity.getBillFromDate()));
        model.getProvAsseFactDtlDto().clear();  // clear dto on year change
        return acqDateDetail;
    }

    @RequestMapping(params = "editDataEntryForm", method = RequestMethod.POST)
    public ModelAndView editForm(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        NewPropertyRegistrationModel model = this.getModel();
        getModel().setSelfAss(null);
        model.setAuthEditFlag("Y");
        getModel().setModeType(MainetConstants.FlagA);
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
        model.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        model.setMaxUnit(max);
        if (model.getAssType() != null && model.getAssType().equals(MainetConstants.Property.ASESS_AUT)) {
            loadDateForAuthorization(model);
        }
        if (model.getLandTypePrefix() != null && (model.getLandTypePrefix().equals(MainetConstants.Property.LandType.KPK))
                && model.getProvisionalAssesmentMstDto().getTppPlotNoCs() != null) {
            model.setEnteredKhasraNo(model.getProvisionalAssesmentMstDto().getTppPlotNoCs());
        }
        if (model.getLandTypePrefix() != null
                && (model.getLandTypePrefix().equals(MainetConstants.Property.LandType.NZL)
                        || model.getLandTypePrefix().equals(MainetConstants.Property.LandType.DIV))
                && model.getProvisionalAssesmentMstDto().getTppPlotNo() != null) {
            model.setEnteredPlotNo(model.getProvisionalAssesmentMstDto().getTppPlotNo());
        }
        model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().forEach(det -> {
            det.getProvisionalAssesmentFactorDtlDtoList().clear();
        });
        return new ModelAndView("DataEntrySuiteEdit", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "compareDate", method = RequestMethod.POST)
    public @ResponseBody Date compareDate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @RequestParam(value = "finId") Long finId) {
        return iFinancialYear.getFinincialYearsById(finId, UserSession.getCurrent().getOrganisation().getOrgid()).getFaFromDate();
    }

    @RequestMapping(params = "displayYearListBasedOnDate", method = RequestMethod.POST)
    public @ResponseBody Map<Long, String> displayYearListBasedOnDate(HttpServletRequest httpServletRequest,
            @RequestParam(value = "finYearId") Long finYearId) throws Exception {

        List<FinancialYear> financialYearList = iFinancialYear
                .getFinanceYearListFromGivenDate(UserSession.getCurrent().getOrganisation().getOrgid(), finYearId, new Date());
        String financialYear = null;
        Map<Long, String> yearMap = new HashMap<>(0);
        for (final FinancialYear finYearTemp : financialYearList) {
            financialYear = Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate());
            yearMap.put(finYearTemp.getFaYear(), financialYear);

        }
        this.getModel().setFinancialYearMap(yearMap);
        return yearMap;
    }

    private void loadDateForAuthorization(NewPropertyRegistrationModel model) {
        List<LookUp> locList = getModel().getLocation();
        List<LocationMasEntity> location = iLocationMasService.findWZMappedLocationByOrgIdAndDeptId(
                UserSession.getCurrent().getOrganisation().getOrgid(),
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
        List<Employee> empList = iEmployeeService.findAllEmployeeByDept(UserSession.getCurrent().getOrganisation().getOrgid(),
                getModel().getDeptId());
        if (empList != null && !empList.isEmpty()) {
            empList.forEach(emp -> {
                LookUp lookUp = new LookUp();
                lookUp.setLookUpId(emp.getEmpId());
                String empDesc = emp.getEmpname() + " " + emp.getEmplname();
                String empDescReg = emp.getEmpname() + " " + emp.getEmplname();
                if (emp.getDesignation() != null && emp.getDesignation().getDsgname() != null) {
                    empDesc = empDesc + "-" + emp.getDesignation().getDsgname();
                    empDescReg = empDescReg + "-" + emp.getDesignation().getDsgname();
                }
                lookUp.setDescLangFirst(empDesc);
                lookUp.setDescLangSecond(empDescReg);
                getModel().getTaxCollList().add(lookUp);
            });
        }
    }

    @RequestMapping(params = "saveDataEntryForm", method = RequestMethod.POST)
    public ModelAndView saveForm(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        getModel().setSelfAss(null);
        NewPropertyRegistrationModel model = this.getModel();

        model.getTbBillMas().setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());

        try {
        	if (model.saveDataEntryForm()) {
                return jsonResult(JsonViewObject.successResult(model
                        .getSuccessMessage()));
            }
        }catch(Exception e) {
        	String [] msg =e.getMessage().split(";");
        	model.addValidationError(msg[0]);
        }
        
        return (model.getCustomViewName() == null)
                || model.getCustomViewName().isEmpty() ? defaultMyResult()
                        : customDefaultMyResult(model.getCustomViewName());
    }

    @RequestMapping(params = "addDataEntryDetails", method = RequestMethod.POST)
    public ModelAndView addDetails(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws Exception {
        this.sessionCleanup(httpServletRequest);
        getModel().bind(httpServletRequest);
        fileUpload.sessionCleanUpForFileUpload();
        getModel().setCommonHelpDocs("DataEntrySuite.html");
        setCommonFields(getModel());
        getModel().setLeastFinYear(iFinancialYear.getMinFinanceYear(getModel().getOrgId()));
        getModel().setSelfAss(MainetConstants.Y_FLAG);
        Organisation org = UserSession.getCurrent().getOrganisation();

        LookUp loadFinYearlookupForDes = null;
        try {
            loadFinYearlookupForDes = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.ENV.FYD,
                    MainetConstants.ENV, UserSession.getCurrent().getOrganisation());
        } catch (Exception exception) {
            LOGGER.error("No Prefix found for ENV(FYD)");
        }
        if (loadFinYearlookupForDes != null && StringUtils.isNotBlank(loadFinYearlookupForDes.getOtherField())) {
            getModel().setFinancialYearMap(dataEntrySuiteService
                    .getConfiguredYearListForDES(loadFinYearlookupForDes.getOtherField(), getModel().getOrgId()));
        } else {
            getModel().setFinancialYearMap(dataEntrySuiteService.getYearListForDES());
        }
        getModel().setLocation(dataEntrySuiteService.getLocationList(getModel().getOrgId(), getModel().getDeptId()));

        getModel().setTaxCollList(dataEntrySuiteService.getTaxCollectorList(getModel().getDeptId(), org.getOrgid()));
        // getModel().setSchedule(selfAssessmentService.getAllBillscheduleWithoutCurrentScheduleByOrgid(org));
        List<LookUp> taxDescription = dataEntrySuiteService.getTaxDescription(UserSession.getCurrent().getOrganisation(),
                getModel().getDeptId());
        getModel().setTaxMasterList(taxDescription);
        List<LookUp> lookupList = CommonMasterUtility.lookUpListByPrefix("FCT",
                UserSession.getCurrent().getOrganisation().getOrgid());
        if (lookupList.size() > 1) {

        } else if (lookupList.size() == 1 && lookupList.get(0).getLookUpCode().equals(MainetConstants.CommonConstants.NA)) {
            getModel().setFactorNotApplicable(lookupList.get(0).getLookUpCode());
        }
        return new ModelAndView("addDataEntryDetails", MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(params = "getLandTypeDetails", method = RequestMethod.POST)
    public ModelAndView getLandTypeDetailsDiv(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @RequestParam(value = "landType") String landType) {
        this.getModel().bind(httpServletRequest);
        NewPropertyRegistrationModel model = this.getModel();
        model.setLandTypePrefix(landType);
        LandTypeApiDetailRequestDto dto = new LandTypeApiDetailRequestDto();
        dto.setLandType(landType);
        List<LookUp> districtList = dataEntrySuiteService.findDistrictByLandType(dto);
        getModel().setDistrict(districtList);
        return new ModelAndView("PropertyLandDetails", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "getTehsilListByDistrict", method = RequestMethod.POST)
    public @ResponseBody List<LookUp> getObjectionServiceByDept(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse, @RequestParam("districtId") String districtId,
            @RequestParam(value = "landType") String landType) {
        this.getModel().getTehsil().clear();
        LandTypeApiDetailRequestDto dto = new LandTypeApiDetailRequestDto();
        dto.setLandType(landType);
        dto.setDistrictId(districtId);
        List<LookUp> tehsilList = dataEntrySuiteService.getTehsilListByDistrict(dto);
        getModel().setTehsil(tehsilList);
        return tehsilList;

    }

    @RequestMapping(params = "getVillageListByTehsil", method = RequestMethod.POST)
    public @ResponseBody List<LookUp> getVillageListByTehsil(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse, @RequestParam("tehsilId") String tehsilId,
            @RequestParam("districtId") String districtId, @RequestParam(value = "landType") String landType) {
        this.getModel().getVillage().clear();
        LandTypeApiDetailRequestDto dto = new LandTypeApiDetailRequestDto();
        dto.setLandType(landType);
        dto.setDistrictId(districtId);
        dto.setTehsilId(tehsilId);
        List<LookUp> villageList = dataEntrySuiteService.getVillageListByTehsil(dto);
        getModel().setVillage(villageList);
        return villageList;
    }

    @RequestMapping(params = "getMohallaListByVillageId", method = RequestMethod.POST)
    public @ResponseBody List<LookUp> getMohallaListByVillageId(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse, @RequestParam("villageId") String villageId,
            @RequestParam("tehsilId") String tehsilId, @RequestParam("districtId") String districtId,
            @RequestParam(value = "landType") String landType) {
        this.getModel().getMohalla().clear();
        LandTypeApiDetailRequestDto dto = new LandTypeApiDetailRequestDto();
        dto.setLandType(landType);
        dto.setDistrictId(districtId);
        dto.setTehsilId(tehsilId);
        dto.setVillageId(villageId);
        List<LookUp> mohallaList = dataEntrySuiteService.getMohallaListByVillageId(dto);
        getModel().setMohalla(mohallaList);
        return mohallaList;
    }

    @RequestMapping(params = "getStreetListByMohallaId", method = RequestMethod.POST)
    public @ResponseBody List<LookUp> getStreetListByMohallaId(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse, @RequestParam("mohallaId") String mohallaId,
            @RequestParam("villageId") String villageId, @RequestParam("tehsilId") String tehsilId,
            @RequestParam("districtId") String districtId, @RequestParam(value = "landType") String landType) {
        this.getModel().getBlockStreet().clear();
        LandTypeApiDetailRequestDto dto = new LandTypeApiDetailRequestDto();
        dto.setLandType(landType);
        dto.setDistrictId(districtId);
        dto.setTehsilId(tehsilId);
        dto.setVillageId(villageId);
        dto.setMohallaId(mohallaId);
        List<LookUp> streetList = dataEntrySuiteService.getStreetListByMohallaId(dto);
        getModel().setBlockStreet(streetList);
        return streetList;
    }

    @RequestMapping(params = "getKhasaraDetails", method = RequestMethod.POST)
    public ModelAndView getKhasaraDetails(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse, @RequestParam("landType") String landType,
            @RequestParam("khasara") String khasara, @RequestParam("districtId") String districtId,
            @RequestParam("TehsilId") String TehsilId, @RequestParam("villageId") String villageId) {
        LandTypeApiDetailRequestDto dto = new LandTypeApiDetailRequestDto();
        dto.setLandType(landType);
        dto.setDistrictId(districtId);
        dto.setTehsilId(TehsilId);
        dto.setVillageId(villageId);
        dto.setKhasraNo(khasara);
        ArrayOfKhasraDetails response = dataEntrySuiteService.getKhasraDetails(dto);
        NewPropertyRegistrationModel model = this.getModel();
        model.setArrayOfKhasraDetails(response);
        return new ModelAndView("showPropertyKhasaraDetails", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "getNajoolAndDiversionDetails", method = RequestMethod.POST)
    public ModelAndView getNajoolAndDiversionDetails(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse, @RequestParam("landType") String landType,
            @RequestParam("plotNo") String plotNo, @RequestParam("districtId") String districtId,
            @RequestParam("TehsilId") String TehsilId, @RequestParam("villageId") String villageId,
            @RequestParam("mohallaId") String mohallaId, @RequestParam("streetNo") String streetNo) {
        NewPropertyRegistrationModel model = this.getModel();
        LandTypeApiDetailRequestDto dto = new LandTypeApiDetailRequestDto();
        dto.setLandType(landType);
        dto.setDistrictId(districtId);
        dto.setTehsilId(TehsilId);
        dto.setVillageId(villageId);
        dto.setMohallaId(mohallaId);
        dto.setStreetNo(streetNo);
        dto.setPlotNo(plotNo);
        dto.setLandTypePrefix(landType);
        if (model.getLandTypePrefix().equals(MainetConstants.Property.LandType.NZL)) {
            ArrayOfPlotDetails response = dataEntrySuiteService.getNajoolDetails(dto);
            model.setArrayOfPlotDetails(response);
        } else if (model.getLandTypePrefix().equals(MainetConstants.Property.LandType.DIV)) {
            ArrayOfDiversionPlotDetails response = dataEntrySuiteService.getDiversionDetails(dto);
            model.setArrayOfDiversionPlotDetails(response);
        }
        return new ModelAndView("showPropertyKhasaraDetails", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "getLandTypeApiDetails", method = RequestMethod.POST)
    public ModelAndView getLandTypeApiDetails(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse) {
        NewPropertyRegistrationModel model = this.getModel();
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

    @RequestMapping(params = "getKhasraNoList", method = { RequestMethod.POST, RequestMethod.GET })
    public @ResponseBody List<LookUp> getKhasraNoList(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse, @RequestParam("villageId") String villageId,
            @RequestParam("tehsilId") String tehsilId, @RequestParam("districtId") String districtId,
            @RequestParam(value = "landType") String landType, @RequestParam("khasara") String khasara) {
        ProvisionalAssesmentMstDto assDto = getModel().getProvisionalAssesmentMstDto();
        LandTypeApiDetailRequestDto dto = new LandTypeApiDetailRequestDto();
        dto.setLandType(landType);
        dto.setDistrictId(districtId);
        dto.setTehsilId(tehsilId);
        dto.setVillageId(villageId);
        dto.setKhasraNo(khasara);
        List<LookUp> khasraNoList = dataEntrySuiteService.getKhasraNoList(dto);
        assDto.setAssVsrNo(dto.getVillageCode());
        getModel().setKhasraNo(khasraNoList);
        return khasraNoList;
    }

    @RequestMapping(params = "getNajoolPlotList", method = { RequestMethod.POST, RequestMethod.GET })
    public @ResponseBody List<LookUp> getNajoolPlotList(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse, @RequestParam("landType") String landType,
            @RequestParam("districtId") String districtId,
            @RequestParam("TehsilId") String TehsilId, @RequestParam("villageId") String villageId,
            @RequestParam("mohallaId") String mohallaId, @RequestParam("streetNo") String streetNo,
            @RequestParam("plotNo") String plotNo) {
        LandTypeApiDetailRequestDto dto = new LandTypeApiDetailRequestDto();
        dto.setLandType(landType);
        dto.setDistrictId(districtId);
        dto.setTehsilId(TehsilId);
        dto.setVillageId(villageId);
        dto.setMohallaId(mohallaId);
        dto.setStreetNo(streetNo);
        dto.setPlotNo(plotNo);
        List<LookUp> najoolPlotList = dataEntrySuiteService.getNajoolPlotList(dto);
        getModel().setPlotNo(najoolPlotList);
        return najoolPlotList;
    }

    @RequestMapping(params = "getDiversionPlotList", method = { RequestMethod.POST, RequestMethod.GET })
    public @ResponseBody List<LookUp> getDiversionPlotList(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse, @RequestParam("landType") String landType,
            @RequestParam("districtId") String districtId,
            @RequestParam("TehsilId") String tehsilId, @RequestParam("villageId") String villageId,
            @RequestParam("mohallaId") String mohallaId, @RequestParam("streetNo") String streetNo,
            @RequestParam("plotNo") String plotNo) {
        LandTypeApiDetailRequestDto dto = new LandTypeApiDetailRequestDto();
        dto.setLandType(landType);
        dto.setDistrictId(districtId);
        dto.setTehsilId(tehsilId);
        dto.setVillageId(villageId);
        dto.setMohallaId(mohallaId);
        dto.setStreetNo(streetNo);
        dto.setPlotNo(plotNo);
        List<LookUp> diversionPlotList = dataEntrySuiteService.getDiversionPlotList(dto);
        getModel().setPlotNo(diversionPlotList);
        return diversionPlotList;
    }

    @RequestMapping(params = "SEARCH_GRID_RESULTS", produces = "application/json", method = RequestMethod.POST)
    public @ResponseBody JQGridResponse<? extends Serializable> getSearchResults(
            HttpServletRequest httpServletRequest) {
        List<ProperySearchDto> result = null;
        int count = 0;
        NewPropertyRegistrationModel model = this.getModel();
        ProperySearchDto dto = model.getSearchDto();
        if ((dto.getProertyNo() == null || dto.getProertyNo().isEmpty()) && (dto.getOldPid() == null || dto.getOldPid().isEmpty())
                && (dto.getMobileno() == null || dto.getMobileno().isEmpty())
                && (dto.getOwnerName() == null || dto.getOwnerName().isEmpty())
                && (dto.getAssWard1() == null || dto.getAssWard1() <= 0)
                && (dto.getLocId() == null || dto.getLocId() <= 0)
                && (dto.getPropLvlRoadType() == null || dto.getPropLvlRoadType() <= 0)
                && (dto.getAssdUsagetype1() == null || dto.getAssdUsagetype1() <= 0)
                && (dto.getAssdConstruType() == null || dto.getAssdConstruType() <= 0)
                && (dto.getFlatNo() == null || dto.getFlatNo().isEmpty())) {
            model.addValidationError("In order to search it is mandatory to enter any one of the below search detail");
        }
        if ((dto.getOwnerName() != null && !dto.getOwnerName().isEmpty()) && dto.getOwnerName().length() < 3) {
            model.addValidationError("Please enter more than three character in owner name feild");
        }
        final String page = httpServletRequest.getParameter(MainetConstants.CommonConstants.PAGE);
        final String rows = httpServletRequest.getParameter(MainetConstants.CommonConstants.ROWS);

        if (!model.hasValidationErrors()) {
            result = viewPropertyDetailsService.searchPropertyDetailsForAll(model.getSearchDto(),
                    getModel().createPagingDTO(httpServletRequest), getModel().createGridSearchDTO(httpServletRequest),
                    null, UserSession.getCurrent().getLoggedLocId());

            if (result != null && !result.isEmpty()) {
                count = viewPropertyDetailsService.getTotalSearchCountForAll(model.getSearchDto(),
                        getModel().createPagingDTO(httpServletRequest),
                        getModel().createGridSearchDTO(httpServletRequest), null);
            }
        }

        return this.getModel().paginate(httpServletRequest, page, rows, count,
                result);
    }

    @RequestMapping(params = "searchData", method = RequestMethod.POST)
    public ModelAndView search(HttpServletRequest request) {
        NewPropertyRegistrationModel model = this.getModel();
        model.bind(request);
        model.setSearchDtoResult(new ArrayList<>(0));
        ProperySearchDto dto = model.getSearchDto();
        model.getSearchDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        model.getSearchDto().setDeptId(model.getDeptId());

        List<String> checkActiveFlagList = assesmentMastService.checkActiveFlag(dto.getProertyNo(),
                UserSession.getCurrent().getOrganisation().getOrgid());
        if (!checkActiveFlagList.isEmpty()) {
            String checkActiveFlag = checkActiveFlagList.get(checkActiveFlagList.size() - 1);
            if (StringUtils.equals(checkActiveFlag, MainetConstants.STATUS.INACTIVE)) {
                model.addValidationError(getApplicationSession()
                        .getMessage("property.inactive"));
            }
        }
        if ((dto.getProertyNo() == null || dto.getProertyNo().isEmpty()) && (dto.getOldPid() == null || dto.getOldPid().isEmpty())
                && (dto.getMobileno() == null || dto.getMobileno().isEmpty())
                && (dto.getOwnerName() == null || dto.getOwnerName().isEmpty())
                && (dto.getAssWard1() == null || dto.getAssWard1() <= 0)
                && (dto.getLocId() == null || dto.getLocId() <= 0)
                && (dto.getPropLvlRoadType() == null || dto.getPropLvlRoadType() <= 0)
                && (dto.getAssdUsagetype1() == null || dto.getAssdUsagetype1() <= 0)
                && (dto.getAssdConstruType() == null || dto.getAssdConstruType() <= 0)
                && (dto.getFlatNo() == null || dto.getFlatNo().isEmpty())) {
            model.addValidationError("In order to search it is mandatory to enter any one of the below search detail");
        }
        if ((dto.getOwnerName() != null && !dto.getOwnerName().isEmpty()) && dto.getOwnerName().length() < 3) {
            model.addValidationError("Please enter more than three character in owner name feild");
        }
        return defaultMyResult();
    }

    @RequestMapping(params = "backToSearch", method = RequestMethod.POST)
    public ModelAndView backToSearch(HttpServletRequest request) {
        NewPropertyRegistrationModel model = this.getModel();
        model.bind(request);
        return defaultMyResult();
    }

    @RequestMapping(method = { RequestMethod.POST }, params = "resetDESSeachGrid")
    public ModelAndView reset(HttpServletRequest request) {
        sessionCleanup(request);
        getModel().setCommonHelpDocs("DataEntrySuite.html");
        getModel().bind(request);
        NewPropertyRegistrationModel model = this.getModel();
        long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        model.setCommonHelpDocs("DataEntrySuite.html");
        ServiceMaster service = serviceMaster.getServiceByShortName(MainetConstants.Property.DES, orgId);
        model.getSearchDto().setOrgId(orgId);
        model.getSearchDto().setDeptId(service.getTbDepartment().getDpDeptid());
        getModel().setLocation(dataEntrySuiteService.getLocationList(orgId,
                service.getTbDepartment().getDpDeptid()));
        model.setOrgId(orgId);
        model.setDeptId(service.getTbDepartment().getDpDeptid());
        return defaultMyResult();
    }

    @RequestMapping(params = "deleteLandEntry", method = RequestMethod.POST)
    public @ResponseBody void deleteLandEntry(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        this.getModel().bind(httpServletRequest);
        NewPropertyRegistrationModel model = this.getModel();
        model.getProvisionalAssesmentMstDto().setAssLandType(null);
        model.getProvisionalAssesmentMstDto().setAssLandTypeDesc(null);
        model.setLandTypePrefix(null);
        model.getProvisionalAssesmentMstDto().setAssDistrict(null);
        model.getProvisionalAssesmentMstDto().setAssDistrictDesc(null);
        model.getProvisionalAssesmentMstDto().setAssTahasil(null);
        model.getProvisionalAssesmentMstDto().setAssTahasilDesc(null);
        model.getProvisionalAssesmentMstDto().setTppVillageMauja(null);
        model.getProvisionalAssesmentMstDto().setTppVillageMaujaDesc(null);
        model.getProvisionalAssesmentMstDto().setMohalla(null);
        model.getProvisionalAssesmentMstDto().setMohallaDesc(null);
        model.getProvisionalAssesmentMstDto().setAssStreetNo(null);
        model.getProvisionalAssesmentMstDto().setAssStreetNoDesc(null);
        model.getProvisionalAssesmentMstDto().setTppPlotNo(null);
        model.getProvisionalAssesmentMstDto().setTppPlotNoCs(null);
        model.setArrayOfKhasraDetails(null);
        model.setArrayOfDiversionPlotDetails(null);
        model.setArrayOfPlotDetails(null);

    }

    @RequestMapping(params = "deleteBillMasOfNextYear", method = { RequestMethod.POST, RequestMethod.GET })
    public @ResponseBody void deleteBillMasOfNextYear(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse, @RequestParam("finYearId") Long finYearId) {
        List<TbBillMas> billMastList = getModel().getBillMasList();
        if (billMastList != null && !billMastList.isEmpty() && billMastList.size() > 2) {
            if (billMastList.get(billMastList.size() - 2).getBmYear().equals(finYearId)
                    && getModel().getAssType().equals(MainetConstants.Property.DATA_ENTRY_SUITE)) {
                TbBillMas billMas = billMastList.get(billMastList.size() - 1);
                billMastList.remove(billMas);
            }
        }

    }

    @RequestMapping(params = "yearRestriction", method = RequestMethod.POST)
    public @ResponseBody String yearRestriction(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse, @RequestParam("selYearId") Long finYearId) {
        NewPropertyRegistrationModel model = this.getModel();
        String validMsg = null;
        this.getModel().bind(httpServletRequest);
        if (model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList() != null
                && !model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().isEmpty()) {
            Long faYearId = getModel().getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().get(0)
                    .getFaYearId();
            if ((model.getAssType().equals("E") || model.getAssType().equals("A")) && faYearId != null
                    && model.getBillMasList() != null && !model.getBillMasList().isEmpty() && !faYearId.equals(finYearId)) {
            	if(!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)) {
            		validMsg = ApplicationSession.getInstance().getMessage("property.dataEntry.floorDetails.yearValid");
            	}
            }
        }
        return validMsg;
    }

    // #108848 by Arun
    @SuppressWarnings("unused")
    @RequestMapping(params = "checkFileUpload", method = RequestMethod.POST)
    public @ResponseBody Boolean checkFileUpload(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        this.getModel().bind(httpServletRequest);
        boolean flag = false;
        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
            flag = true;
            break;
        }
        return flag;
    }

    @RequestMapping(params = "addRoomDetails", method = RequestMethod.POST)
    public ModelAndView addRoomDetails(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        return new ModelAndView("AddRoomDetails", MainetConstants.FORM_NAME, this.getModel());
    }

    @RequestMapping(params = "removeRoomDetails", method = RequestMethod.POST)
    public @ResponseBody Boolean removeRoomDetails(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse, @RequestParam("countOfRow") Long countOfRow,
            @RequestParam("index") Long index) {
        this.getModel().bind(httpServletRequest);
        NewPropertyRegistrationModel model = this.getModel();
        List<ProvisionalAssesmentDetailDto> detailDtoList = model.getProvisionalAssesmentMstDto()
                .getProvisionalAssesmentDetailDtoList();
        if ((detailDtoList != null && !detailDtoList.isEmpty()) && detailDtoList.get(countOfRow.intValue()) != null
                && detailDtoList.get(countOfRow.intValue()).getRoomDetailsDtoList().size() >= (index.intValue())
                && detailDtoList.get(countOfRow.intValue()).getRoomDetailsDtoList().get(index.intValue() - 1) != null) {
            PropertyRoomDetailsDto roomDetDto = model.getProvisionalAssesmentMstDto()
                    .getProvisionalAssesmentDetailDtoList().get(countOfRow.intValue()).getRoomDetailsDtoList()
                    .get(index.intValue() - 1);
            model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().get(countOfRow.intValue())
                    .getRoomDetailsDtoList().remove(roomDetDto);
        }
        return true;
    }

    @RequestMapping(params = "saveRoomDetails", method = RequestMethod.POST)
    public ModelAndView saveRoomDetails(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        return new ModelAndView("addDataEntryDetails", MainetConstants.FORM_NAME, this.getModel());
    }

    @RequestMapping(params = "financialYearWiseFlatArrears", method = RequestMethod.POST)
    public ModelAndView setFinYearWiseFlatArrears(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws Exception {
        getModel().bind(httpServletRequest);
        NewPropertyRegistrationModel model = this.getModel();
        this.getModel().setTempBillMasList(null);
        Organisation org = UserSession.getCurrent().getOrganisation();
        if (model.getProvisionalAssesmentMstDto().getAssOwnerType() != null) {
            LookUp ownerType = CommonMasterUtility.getNonHierarchicalLookUpObject(
                    model.getProvisionalAssesmentMstDto().getAssOwnerType(),
                    UserSession.getCurrent().getOrganisation());
            getModel().setOwnershipPrefix(ownerType.getLookUpCode());
        }

        List<TbBillMas> billMasList;
        if (CollectionUtils.isNotEmpty(this.getModel().getBillMasList())) {
            billMasList = this.getModel().getBillMasList();
        } else {
            billMasList = new LinkedList<>();
        }
        if (null != model.getFlatNoOfRow() && CollectionUtils
                .isNotEmpty(model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList())) {
            ProvisionalAssesmentDetailDto provisionalAssesmentDetailDto = model.getProvisionalAssesmentMstDto()
                    .getProvisionalAssesmentDetailDtoList().get(model.getFlatNoOfRow().intValue());
            List<TbBillMas> billMasNewList = dataEntrySuiteService.createProvisionalBillForDataEntry(model.getDeptId(),
                    model.getSchduleId(), org.getOrgid(), provisionalAssesmentDetailDto.getFaYearId(),
                    provisionalAssesmentDetailDto.getFlatNo());
            model.setFinYearFlag(MainetConstants.FlagN);
            model.setTempBillMasList(billMasNewList);
            billMasList.addAll(billMasNewList);
        }
        this.getModel().setBillMasList(billMasList);
        List<FinancialYear> financialYearList = dataEntrySuiteService
                .getFinYearListForDataEntry(model.getProvisionalAssesmentMstDto().getAssAcqDate(), org.getOrgid());
        List<Long> finYearList = financialYearList.stream()
                .map(FinancialYear::getFaYear)
                .collect(Collectors.toList());
        String financialYear = null;
        for (final FinancialYear finYearTemp : financialYearList) {
            financialYear = Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate());
            getModel().getFinancialYearMapForTax().put(finYearTemp.getFaYear(), financialYear);
        }
        this.getModel().setFinYearList(finYearList);
        return new ModelAndView("DataEntryOutStandingForFlat", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "nextToViewAfterArrearForFlat", method = RequestMethod.POST)
    public ModelAndView nextToViewAfterArrearForFlat(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        NewPropertyRegistrationModel model = this.getModel();
        ModelAndView mv = new ModelAndView("DataEntryOutStandingForFlat", MainetConstants.FORM_NAME, model);
        model.validateBean(model, DataEntrySuiteValidator.class);
        if (model.hasValidationErrors()) {
            mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
            return mv;
        }
        // In case modification made in existing arrears to reset bill master list
        if (CollectionUtils.isNotEmpty(model.getBillMasList())
                && CollectionUtils.isNotEmpty(model.getTempBillMasList())) {
            List<TbBillMas> newList = model.getBillMasList().stream()
                    .filter(mainBill -> !mainBill.getFlatNo().equals(model.getTempBillMasList().get(0).getFlatNo()))
                    .collect(Collectors.toList());
            newList.addAll(model.getTempBillMasList());
            model.setBillMasList(newList);
        }
        return new ModelAndView("addDataEntryDetails", MainetConstants.FORM_NAME, model);
    }
    
    @RequestMapping(params = "editForPrayag", method = RequestMethod.POST)
    public ModelAndView editForPrayag(@RequestParam String rowId,
            @RequestParam(value = MainetConstants.AdvertisingAndHoarding.SAVE_MODE, required = true) String saveMode,
            final HttpServletRequest httpServletRequest) throws Exception {
        NewPropertyRegistrationModel model = this.getModel();
        String flatNo = null;
        if (rowId.contains(MainetConstants.operator.UNDER_SCORE)) {
            String[] splitStr = rowId.split(MainetConstants.operator.UNDER_SCORE);
            rowId = splitStr[0];
            flatNo = splitStr[1];
        }
        ServiceMaster serviceDES = serviceMaster.getServiceByShortName(MainetConstants.Property.DES,
                UserSession.getCurrent().getOrganisation().getOrgid());
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        // If it is property that belongs to parent then to edit it
        PropertyMastEntity propMas = null;
        if (flatNo == null) {
            propMas = primaryPropertyService.getPropertyDetailsByPropNo(rowId, orgId);
        }
        boolean validate = true;
        boolean isGrpPropFlag = false;
        if (flatNo != null) {
            validate = dataEntrySuiteService.validateDataEntrySuiteWithFlatNo(rowId, flatNo,
                    UserSession.getCurrent().getOrganisation().getOrgid(), serviceDES.getSmServiceId());
        } else {
            validate = dataEntrySuiteService.validateDataEntrySuite(rowId,
                    UserSession.getCurrent().getOrganisation().getOrgid(), serviceDES.getSmServiceId());
        }
        if (!validate && propMas != null && StringUtils.equals(propMas.getIsGroup(), MainetConstants.FlagY)) {
            isGrpPropFlag = true;
        }
        if (propMas != null && StringUtils.equals(propMas.getIsGroup(), MainetConstants.FlagY)) {
            validate = true;
        }
        String receiptDelFlag = null;
        if (flatNo != null) {
            receiptDelFlag = "Y";// need to change
        } else {
            receiptDelFlag = assesmentMastService.getReceiptDelFlag(rowId,
                    UserSession.getCurrent().getOrganisation().getOrgid());
        }
        
        int billGenCount = propertyMainBillService.getCountOfBillWithoutDESByPropNo(rowId, UserSession.getCurrent().getOrganisation().getOrgid());
        validate = true;
        if(billGenCount > 0) {
        	validate = false;
        }
        if (validate || StringUtils.equals(receiptDelFlag, MainetConstants.FlagY) || StringUtils.equals(saveMode, MainetConstants.FlagV)) {
            sessionCleanup(httpServletRequest);
            fileUpload.sessionCleanUpForFileUpload();
            getModel().setCommonHelpDocs("DataEntrySuite.html");
            Organisation org = UserSession.getCurrent().getOrganisation();
            ServiceMaster service = serviceMaster.getServiceByShortName(MainetConstants.Property.DES, orgId);
            // For map or view property on GIS map
            try {
                LookUp gisFlag = CommonMasterUtility.getValueFromPrefixLookUp("GIS",
                        "MLI", org);
                getModel().setGisValue(gisFlag.getOtherField());
                if (gisFlag != null) {
                    getModel().setGisUri(
                            ServiceEndpoints.GisItegration.GIS_URI + ServiceEndpoints.GisItegration.PROPERTY_GIS_LAYER_NAME);
                }
            } catch (Exception e) {
                throw new FrameworkException("No Prefix Configure in Prefix Master for Prifix =MLI", e);
            }
            getModel().setAssType(MainetConstants.FlagE);
            getModel().setModeType(MainetConstants.FlagV);
            getModel().setOrgId(orgId);
            getModel().setDataFrom(MainetConstants.FlagP);
            List<ProvisionalAssesmentMstDto> provAssDtoList = null;
            if (flatNo != null) {
                provAssDtoList = iProvisionalAssesmentMstService
                        .getDataEntryPropDetailFromProvAssByPropNoAndFlatNo(orgId, rowId, flatNo, MainetConstants.FlagA);
            } else {
                provAssDtoList = iProvisionalAssesmentMstService.getDataEntryPropDetailFromProvAssByPropNo(orgId, rowId,
                        MainetConstants.FlagA);
            }
            if (provAssDtoList == null || provAssDtoList.isEmpty()) {
                getModel().setDataFrom(MainetConstants.FlagM);
                if (flatNo != null) {
                    provAssDtoList = assesmentMastService.getPropDetailFromAssByPropNoFlatNo(orgId, rowId, flatNo);
                } else {
                    provAssDtoList = assesmentMastService.getPropDetailFromAssByPropNo(orgId, rowId);
                }
            }
            final ProvisionalAssesmentMstDto provisionalAssesmentMstDto = propertyAuthorizationService
                    .getAssesmentMstDtoForDisplay(provAssDtoList);
            if (provisionalAssesmentMstDto != null && provisionalAssesmentMstDto.getBillMethod() != null) {
                String billMethod = CommonMasterUtility.getNonHierarchicalLookUpObject(
                        provisionalAssesmentMstDto.getBillMethod(), UserSession.getCurrent().getOrganisation()).getLookUpCode();
                getModel().setBillMethodDesc(billMethod);
            }
            Long deptId = service.getTbDepartment().getDpDeptid();
            getModel().setDeptId(deptId);
            LookUp ownerType = CommonMasterUtility.getNonHierarchicalLookUpObject(
                    provisionalAssesmentMstDto.getAssOwnerType(), UserSession.getCurrent().getOrganisation());
            getModel().setOwnershipPrefix(ownerType.getLookUpCode());
            List<LookUp> taxDescription = dataEntrySuiteService.getTaxDescription(UserSession.getCurrent().getOrganisation(),
                    deptId);
            getModel().setTaxMasterList(taxDescription);
            List<TbBillMas> billMasList = null;
			/*
			 * if (flatNo != null) { billMasList = iProvisionalBillService
			 * .getProvisionalBillMasByPropertyNoAndFlatNo(provisionalAssesmentMstDto.
			 * getAssNo(), flatNo, orgId); } else { billMasList = iProvisionalBillService
			 * .getProvisionalBillMasByPropertyNo(provisionalAssesmentMstDto.getAssNo(),
			 * orgId); }
			 */

            if (billMasList == null || billMasList.isEmpty()) {
                if (flatNo != null) {
                    billMasList = propertyMainBillService.fetchAllBillByPropNoAndFlatNo(provisionalAssesmentMstDto.getAssNo(),
                            flatNo,
                            orgId);
                } else {
                    billMasList = propertyMainBillService.fetchAllBillByPropNo(provisionalAssesmentMstDto.getAssNo(),
                            orgId);
                }
            }
            
            for (LookUp taxMas : taxDescription) {
				if (StringUtils.equals(MainetConstants.FlagN, taxMas.getExtraStringField1())
						|| StringUtils.equals(MainetConstants.FlagI, taxMas.getExtraStringField1())) {
					for (TbBillMas billMas : billMasList) {
						List<TbBillDet> billDetList = billMas.getTbWtBillDet().stream()
								.filter(det -> det.getTaxId().equals(taxMas.getLookUpId()))
								.collect(Collectors.toList());
						if(CollectionUtils.isEmpty(billDetList)) {
							TbBillDet intTax = new TbBillDet();
							intTax.setBmIdno(billMas.getBmIdno());
							intTax.setTaxId(taxMas.getLookUpId());
							intTax.setBdCurTaxamt(0.00);
							intTax.setBdCurBalTaxamt(0.00);
							intTax.setTaxCategory(taxMas.getLookUpParentId());
							intTax.setCollSeq(Long.valueOf(taxMas.getExtraStringField2()));
							intTax.setLmoddate(new Date());
							intTax.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
							billMas.getTbWtBillDet().add(intTax);
						}

					}
				}
			}
            Long firstBillBmIdNo = propertyMainBillRepository.getFirstBmIdNoByPropNo(billMasList.get(0).getPropNo());
            addFirstBillArrearTax(billMasList, firstBillBmIdNo);
            
            this.getModel().setBillMasList(billMasList);
            
            
            final List<FinancialYear> compFinYearList = iFinancialYear.getAllFinincialYear();
            LookUp loadFinYearlookupForDes = null;
            try {
                loadFinYearlookupForDes = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.ENV.FYD,
                        MainetConstants.ENV, UserSession.getCurrent().getOrganisation());
            } catch (Exception exception) {
                LOGGER.error("No Prefix found for ENV(FYD)");
            }
            if (loadFinYearlookupForDes != null && StringUtils.isNotBlank(loadFinYearlookupForDes.getOtherField())) {
                getModel().setFinancialYearMap(
                        dataEntrySuiteService.getConfiguredYearListForDES(loadFinYearlookupForDes.getOtherField(),
                                getModel().getOrgId()));
            } else {
                getModel().setFinancialYearMap(dataEntrySuiteService.getYearListForDES());
            }
            checkForMissingYear(provisionalAssesmentMstDto, compFinYearList); // if year is not current and previous year
            String financialYear = null;
            for (final FinancialYear finYearTemp : compFinYearList) {
                financialYear = Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate());
                getModel().getFinancialYearMapForTax().put(finYearTemp.getFaYear(), financialYear);
            }
            List<ProvisionalAssesmentFactorDtlDto> factorMap = factorMappingForView(provisionalAssesmentMstDto);
            List<ProvisionalAssesmentFactorDtlDto> factorMapBefore = new ArrayList<>();
            factorMap.forEach(fact -> {
                ProvisionalAssesmentFactorDtlDto factBefore = new ProvisionalAssesmentFactorDtlDto();
                BeanUtils.copyProperties(fact, factBefore);
                factorMapBefore.add(factBefore);
            });

            getModel().setProvAsseFactDtlDto(factorMap);
            getModel().setAuthCompFactDto(factorMapBefore);
            getModel().setServiceId(model.getServiceId());
            getModel().setDeptId(model.getDeptId());
            // getModel().setSchedule(selfAssessmentService.getAllBillscheduleWithoutCurrentScheduleByOrgid(org));
            getModel().setProvAssMstDtoList(provAssDtoList);
            if (provisionalAssesmentMstDto.getAssLandType() != null) {

                LookUp landTypePrefix = CommonMasterUtility.getNonHierarchicalLookUpObject(
                        provisionalAssesmentMstDto.getAssLandType(), UserSession.getCurrent().getOrganisation());
                getModel().setLandTypePrefix(landTypePrefix.getLookUpCode());
            }
            setDataIntoProvAssessDto(orgId, provisionalAssesmentMstDto, factorMapBefore);
            provisionalAssesmentMstDto.getProvisionalAssesmentDetailDtoList().forEach(det -> {
                det.getProvisionalAssesmentFactorDtlDtoList().clear();
            });

            if (provisionalAssesmentMstDto.getParentPropNo() != null
                    && provisionalAssesmentMstDto.getGroupPropNo() != null) {
                provisionalAssesmentMstDto.setParentGrp1(Long.valueOf(provisionalAssesmentMstDto.getParentPropNo()));
                provisionalAssesmentMstDto.setParentGrp2(Long.valueOf(provisionalAssesmentMstDto.getGroupPropNo()));
            }
            
            getModel().setOldProDetFinYaerId(provisionalAssesmentMstDto.getProvisionalAssesmentDetailDtoList().get(0).getFaYearId());
            if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL) && CollectionUtils.isNotEmpty(billMasList)) {
            	for (ProvisionalAssesmentDetailDto proDet : provisionalAssesmentMstDto.getProvisionalAssesmentDetailDtoList()) {
            		proDet.setFaYearId(billMasList.get(0).getBmYear());
				}
            }
            getModel().setProvisionalAssesmentMstDto(provisionalAssesmentMstDto);

            if (getModel().getLandTypePrefix() != null) {
                setlandTypeDetails(getModel());
            }
            getModel().setDropDownValues();
            getModel().setAuthComBillList(billMasList);
            getModel().setDocumentList(iChecklistVerificationService.getDocumentUploadedByRefNo(rowId, orgId));
            getModel().getWorkflowActionDto().setReferenceId(rowId);
            List<LookUp> list = null;
            if(CollectionUtils.isNotEmpty(billMasList)) {
            	list = dataEntrySuiteService.setScheduleListForArrEntry(
                        getModel().getProvisionalAssesmentMstDto().getAssAcqDate(),
                        UserSession.getCurrent().getOrganisation().getOrgid(),
                        billMasList.get(billMasList.size() -1).getBmYear());
            }else {
            	list = dataEntrySuiteService.setScheduleListForArrEntry(
                        getModel().getProvisionalAssesmentMstDto().getAssAcqDate(),
                        UserSession.getCurrent().getOrganisation().getOrgid(),
                        provisionalAssesmentMstDto.getProvisionalAssesmentDetailDtoList().get(0).getFaYearId());
            }
            if (billMasList != null && !billMasList.isEmpty()) {
                BillingScheduleDetailEntity billSchDet = billingScheduleService
                        .getSchedulebySchFromDate(orgId, billMasList.get(0).getBmFromdt());
                getModel().setScheduleForArrEntry(list);
                getModel().setSchduleId(billSchDet.getSchDetId());
            }
            getModel()
                    .setAttachDocsList(
                            iAttachDocsService.findAllDocLikeReferenceId(orgId, provisionalAssesmentMstDto.getAssNo()));                                                                    // property
            getModel().setIntgrtionWithBP(CommonMasterUtility
                    .getDefaultValueByOrg(MainetConstants.Property.IBP, UserSession.getCurrent().getOrganisation())
                    .getLookUpCode());
            getModel().setSelfAss(null);
            Long max = 0l;
            List<ProvisionalAssesmentDetailDto> detailDto = model.getProvisionalAssesmentMstDto()
                    .getProvisionalAssesmentDetailDtoList();
            if (detailDto != null && !detailDto.isEmpty()) {
                for (ProvisionalAssesmentDetailDto dto : detailDto) {
                    if (dto.getAssdUnitNo() != null && dto.getAssdUnitNo() > max) {
                        max = dto.getAssdUnitNo();
                    }
                }
            }
            getModel().setModeType(saveMode);
            model.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            model.setMaxUnit(max);
            getModel().setLocation(dataEntrySuiteService.getLocationList(org.getOrgid(),
                    deptId));
            getModel().setTaxCollList(dataEntrySuiteService.getTaxCollectorList(deptId, org.getOrgid()));
            if (isGrpPropFlag)
                getModel().setIsGrpPropertyFlag(MainetConstants.FlagY);
            return new ModelAndView("DataEntrySuiteView", MainetConstants.FORM_NAME, getModel());
        }else {
        	 getModel().addValidationError("This property is not editable -> Bill or Receipt generated generated through system");
             ModelAndView mv = new ModelAndView("DataEntrySuiteValidn", MainetConstants.FORM_NAME, this.getModel());
             mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
             return mv;
        }
    }

	private void addFirstBillArrearTax(List<TbBillMas> billMasList, Long firstBillBmIdNo) {
		billMasList.forEach(billMas ->{
			if(billMas != null) {
				if(billMas.getBmIdno() == firstBillBmIdNo) {
					 List<TbBillDet> tbWtBillDetList = new ArrayList<>(0);
					if(CollectionUtils.isNotEmpty(billMas.getTbWtBillDet())) {
						billMas.getTbWtBillDet().forEach(det ->{
		        				TbBillDet billDet = new TbBillDet();
		        				StringBuilder taxId = createTaxId(det);
		        				billDet.setTaxId(Long.valueOf(taxId.toString()));
		        				billDet.setBdCurTaxamt(det.getBdPrvArramt());
		        				billDet.setBdCurBalTaxamt(det.getBdPrvBalArramt());
		        				billDet.setBdCsmp(BigDecimal.valueOf(det.getBdPrvBalArramt()));
		        				tbWtBillDetList.add(billDet);
		        				List<LookUp> taxList = getModel().getTaxMasterList().stream().filter(tax ->tax.getLookUpId() == det.getTaxId()).collect(Collectors.toList());
		        				if(CollectionUtils.isNotEmpty(taxList)) {
		        					addTaxDetails(taxId, taxList);
		        				}
		        		});
					}
					billMas.getTbWtBillDet().addAll(tbWtBillDetList);
		    	}
			}
		});
	}

	private void addTaxDetails(StringBuilder taxId, List<LookUp> taxList) {
		LookUp taxMas = new LookUp();
		taxMas.setLookUpId(Long.valueOf(taxId.toString()));
		StringBuilder taxDesc = new StringBuilder();
		taxDesc.append("Previous arrears of ");
		taxDesc.append(taxList.get(0).getOtherField());
		taxMas.setOtherField(taxDesc.toString());
		getModel().getTaxMasterList().add(taxMas);
	}

	private StringBuilder createTaxId(TbBillDet det) {
		StringBuilder taxId = new StringBuilder();
		taxId.append("1111");
		taxId.append(det.getTaxId().toString());
		return taxId;
	}

}
