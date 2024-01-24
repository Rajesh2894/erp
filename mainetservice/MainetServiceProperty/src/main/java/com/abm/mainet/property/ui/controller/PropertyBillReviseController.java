
package com.abm.mainet.property.ui.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.bill.service.BillMasterCommonService;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.BillReceiptPostingDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.TbBillDet;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.repository.DuplicateReceiptRepository;
import com.abm.mainet.common.repository.ReceiptRepository;
import com.abm.mainet.common.service.IDuplicateReceiptService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.IReceiptEntryService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.property.domain.AssesmentMastEntity;
import com.abm.mainet.property.domain.MainBillMasEntity;
import com.abm.mainet.property.domain.ProvisionalBillMasEntity;
import com.abm.mainet.property.dto.BillDisplayDto;
import com.abm.mainet.property.dto.BillPaymentDetailDto;
import com.abm.mainet.property.dto.BillPresentAndCalculationDto;
import com.abm.mainet.property.dto.LandTypeApiDetailRequestDto;
import com.abm.mainet.property.dto.PropertyTransferMasterDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentDetailDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentFactorDtlDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.repository.PropertyMainBillDetRepository;
import com.abm.mainet.property.repository.PropertyMainBillRepository;
import com.abm.mainet.property.service.AsExecssAmtService;
import com.abm.mainet.property.service.AssesmentMastService;
import com.abm.mainet.property.service.DataEntrySuiteService;
import com.abm.mainet.property.service.IProvisionalAssesmentMstService;
import com.abm.mainet.property.service.IProvisionalBillService;
import com.abm.mainet.property.service.MutationService;
import com.abm.mainet.property.service.PropertyBRMSService;
import com.abm.mainet.property.service.PropertyBillPaymentService;
import com.abm.mainet.property.service.PropertyMainBillService;
import com.abm.mainet.property.service.PropertyService;
import com.abm.mainet.property.service.SelfAssessmentService;
import com.abm.mainet.property.ui.model.SelfAssesmentNewModel;
import com.abm.mainet.validitymaster.service.IEmployeeWardZoneMappingService;
import com.google.common.util.concurrent.AtomicDouble;
import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;

/**
 * @author cherupelli.srikanth
 * @since 01 July 2021
 */

@Controller
@RequestMapping("/PropertyBillRevise.html")
public class PropertyBillReviseController extends AbstractFormController<SelfAssesmentNewModel>{
	
	private static final Logger LOGGER = Logger.getLogger(PropertyBillReviseController.class);
	
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
    private ServiceMasterService serviceMaster;

    @Autowired
    private DataEntrySuiteService dataEntrySuiteService;

    @Autowired
    private MutationService mutationService;
    
    @Autowired
    private IWorkflowRequestService workflowReqService;
    
    @Autowired
    private PropertyBRMSService propertyBRMSService;
    
    @Autowired
    private PropertyMainBillService propertyMainBillService;
    
    @Autowired
    private BillMasterCommonService billMasterCommonService;
	
    @Resource
    private SeqGenFunctionUtility seqGenFunctionUtility;
    
    @Autowired
    private TbTaxMasService tbTaxMasService;
    
    @Autowired
    private IProvisionalBillService iProvisionalBillService;
    
    @Autowired 
    private ReceiptRepository receiptRepository;
    
	@Autowired
	private IDuplicateReceiptService iDuplicateReceiptService;
	
	@Autowired
	private IDuplicateReceiptService duplicateReceiptService;
	
	@Autowired
	private IReceiptEntryService receiptEntryService;
	
	@Autowired
	private DuplicateReceiptRepository  duplicateReceiptRepository;
	
	 @Autowired
	 private AsExecssAmtService asExecssAmtService;
	 
	 @Autowired
	 private PropertyMainBillRepository propertyMainBillRepository;
	
    
	@RequestMapping(method= {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView index(HttpServletRequest request) {
		sessionCleanup(request);
		this.getModel().bind(request);
		this.getModel().setShowTaxDetails(MainetConstants.FlagN);
		this.getModel().setAssType("PBR");
		this.getModel().setBillReviseFlag(MainetConstants.FlagY);
		 this.getModel().setAssMethod(
	                CommonMasterUtility.getDefaultValue(MainetConstants.Property.propPref.ASS, UserSession.getCurrent().getOrganisation()).getLookUpCode());	
		return index();
	}
	
	@RequestMapping(params = "displayChangeInAssessmentForm", method = RequestMethod.POST)
	public ModelAndView serachProperty(HttpServletRequest request) throws Exception {
        this.bindModel(request);
        fileUpload.sessionCleanUpForFileUpload();
        SelfAssesmentNewModel model = this.getModel();
        model.setNoOfDaysEditableFlag(MainetConstants.FlagN);
        model.setNoOfDaysAuthEditFlag(MainetConstants.FlagN);
        this.getModel().setShowTaxDetails(MainetConstants.FlagN);
        ModelAndView mv = null;
        ProvisionalAssesmentMstDto assMst = new ProvisionalAssesmentMstDto();
        List<TbBillMas> billMasList = null;
        LookUp billDeletionInactive = null;
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
        try {
            billDeletionInactive = CommonMasterUtility.getValueFromPrefixLookUp("BDI", "ENV",
                    UserSession.getCurrent().getOrganisation());
        } catch (Exception e) {

        }
        ProvisionalAssesmentMstDto proAssMas = model.getProvisionalAssesmentMstDto();

        // end
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

        // D#34185
		List<String> checkActiveFlagList = assesmentMastService.checkActiveFlag(proAssMas.getAssNo(),
				UserSession.getCurrent().getOrganisation().getOrgid());
		String checkActiveFlag = null;
		if(CollectionUtils.isNotEmpty(checkActiveFlagList)) {
			checkActiveFlag = checkActiveFlagList.get(checkActiveFlagList.size() - 1);
		}
		if (checkActiveFlag != null && org.apache.commons.lang.StringUtils.equals(checkActiveFlag, MainetConstants.STATUS.INACTIVE)) {
			getModel().addValidationError("This property is Inactive");
			mv = new ModelAndView("ChangeInAssessmentSearchValidn", MainetConstants.FORM_NAME, this.getModel());
			mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
			return mv;
		}
        if (StringUtils.isEmpty(proAssMas.getAssOldpropno()) && StringUtils.isEmpty(proAssMas.getAssNo())) {
            model.addValidationError("Property number or Old PID number must not be empty.");
            mv = new ModelAndView("ChangeInAssessmentSearchValidn", MainetConstants.FORM_NAME, this.getModel());
            mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
            return mv;
        } else {
            final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
            ServiceMaster service = serviceMaster.getServiceByShortName(MainetConstants.Property.NPR, orgId);
            Long serviceId = null;
            if (service != null) {
                serviceId = service.getSmServiceId();
            }
            List<ProvisionalAssesmentMstDto> provAssesMastList = provisionalAssesmentMstService
                    .getPropDetailByPropNoOnly(proAssMas.getAssNo());

            if (CollectionUtils.isNotEmpty(provAssesMastList)) {
                LookUp noOfDaysLookUp = null;
                try {
                    noOfDaysLookUp = CommonMasterUtility.getValueFromPrefixLookUp("D", "NOD",
                            UserSession.getCurrent().getOrganisation());
                } catch (Exception exception) {

                }
                if (noOfDaysLookUp == null) {
                    model.addValidationError("Please add prefix NOD and value Day");
                    mv = new ModelAndView("ChangeInAssessmentSearchValidn", MainetConstants.FORM_NAME, this.getModel());
                    mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
                            getModel().getBindingResult());
                    return mv;
                }
                ProvisionalAssesmentMstDto provAssesMstDto = provAssesMastList.get(provAssesMastList.size() - 1);
                
                //
                LocalDate convertCreateDateToLocalDate = provAssesMstDto.getCreatedDate().toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDate();
                Date afterAddingDaysToCreatedDate = Date.from(
                        convertCreateDateToLocalDate.plusDays(Long.valueOf(noOfDaysLookUp.getOtherField()))
                                .atStartOfDay(ZoneId.systemDefault()).toInstant());
                if (!Utility.compareDate(afterAddingDaysToCreatedDate, new Date())) {
                    model.setNoOfDaysEditableFlag(MainetConstants.FlagY);
                    assMst = provAssesMastList.get(provAssesMastList.size() - 1);
                    billMasList = ApplicationContextProvider.getApplicationContext().getBean(IProvisionalBillService.class)
                            .getProvisionalBillMasByPropertyNo(proAssMas.getAssNo(), orgId);
                }
            }

            if (org.apache.commons.lang.StringUtils.equals(model.getNoOfDaysEditableFlag(), MainetConstants.FlagN)) {
                assMst = assesmentMastService.getPropDetailByAssNoOrOldPropNo(orgId, proAssMas.getAssNo(),
                        proAssMas.getAssOldpropno(), MainetConstants.STATUS.ACTIVE,null);
            }
                if (assMst != null) {
                	if (assMst != null && assMst.getApmApplicationId() != null) {
            			WorkflowRequest workflowRequest = workflowReqService
            					.getWorkflowRequestByAppIdOrRefId(assMst.getApmApplicationId(), null, orgId);
            			if (MainetConstants.WorkFlow.Status.PENDING.equalsIgnoreCase(workflowRequest.getStatus())) {
            				getModel().addValidationError(
            						ApplicationSession.getInstance().getMessage("property.applicationInProgress"));
            				mv = new ModelAndView("PropertyBillReviseValidn", MainetConstants.FORM_NAME, this.getModel());
                            mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
                            return mv;
            			}
            		}
                	
                	 try {
                         PropertyTransferMasterDto transferMasDto = mutationService.getMutationByPropNo(proAssMas.getAssNo(),
                                 UserSession.getCurrent().getOrganisation().getOrgid());
                         if (transferMasDto.getApmApplicationId() != null) {
                             String status = mutationService.getWorkflowRequestByAppId(transferMasDto.getApmApplicationId(),
                                     UserSession.getCurrent().getOrganisation().getOrgid());
                             if (MainetConstants.TASK_STATUS_PENDING.equalsIgnoreCase(status)) {
                                 getModel().addValidationError(
                                         ApplicationSession.getInstance().getMessage("property.validation.mutaion.process"));
                                 mv = new ModelAndView("PropertyBillReviseValidn", MainetConstants.FORM_NAME, this.getModel());
                                 mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
                                 return mv;
                             }
                         }
                     } catch (Exception e) {
                         LOGGER.error("Exception occured" + e);
                     }
                	Long currrentFinYearId = iFinancialYear.getFinanceYearId(new Date());
                	if(checkCurrentYearBillPaymentDone(currrentFinYearId, assMst.getAssNo())) {
                    	if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "EWZ") && assMst != null
                				&& !(ApplicationContextProvider.getApplicationContext().getBean(IEmployeeWardZoneMappingService.class)
                						.checkWardZoneMappingFlag(UserSession.getCurrent().getEmployee().getEmpId(),
                								UserSession.getCurrent().getOrganisation().getOrgid(), assMst.getAssWard1(),
                								assMst.getAssWard2(), assMst.getAssWard3(),
                								assMst.getAssWard4(), assMst.getAssWard5()))) {
                    		  model.addValidationError("ward zone not mapped");
                              mv = new ModelAndView("ChangeInAssessmentSearchValidn", MainetConstants.FORM_NAME, this.getModel());
                              mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
                              return mv;
                		}
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
                        LookUp ownerType = CommonMasterUtility.getNonHierarchicalLookUpObject(
                                assMst.getAssOwnerType(), UserSession.getCurrent().getOrganisation());
                        model.setOwnershipPrefix(ownerType.getLookUpCode());
                        LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp(ownerType.getLookUpCode(),
                                MainetConstants.Property.propPref.OWT,
                                UserSession.getCurrent().getOrganisation());
                        model.setOwnershipTypeValue(lookup.getDescLangFirst());

                        if (assMst.getAssLandType() != null && assMst.getAssLandType() > 0) {
                            LookUp landTypePrefix = CommonMasterUtility.getNonHierarchicalLookUpObject(
                                    assMst.getAssLandType(), UserSession.getCurrent().getOrganisation());
                            getModel().setLandTypePrefix(landTypePrefix.getLookUpCode());
                            assMst.setAssLandTypeDesc(CommonMasterUtility
                                    .getNonHierarchicalLookUpObject(assMst.getAssLandType(),
                                            UserSession.getCurrent().getOrganisation())
                                    .getDescLangFirst());
                        }

                        assMst.setLocationName(iLocationMasService.getLocationNameById(assMst.getLocId(), orgId));
                        assMst.setProAssOwnerTypeName(CommonMasterUtility
                                .getNonHierarchicalLookUpObject(assMst.getAssOwnerType(), UserSession.getCurrent().getOrganisation())
                                .getDescLangFirst());
                        if(assMst.getPropLvlRoadType() != null)
                        assMst.setProAssdRoadfactorDesc(
                                CommonMasterUtility.getNonHierarchicalLookUpObject(assMst.getPropLvlRoadType(),
                                        UserSession.getCurrent().getOrganisation()).getDescLangFirst());
                        model.setProvisionalAssesmentMstDto(assMst);
                        if (getModel().getLandTypePrefix() != null) {
                            setlandTypeDetails(getModel());
                        }


                        for (LookUp dis : model.getDistrict()) {
                            if (Long.toString(dis.getLookUpId()).equals(assMst.getAssDistrict())) {
                                assMst.setAssDistrictDesc(dis.getDescLangFirst());
                            }
                        }

                        for (LookUp teh : model.getTehsil()) {
                            if (teh.getLookUpCode().equals(assMst.getAssTahasil())) {
                                assMst.setAssTahasilDesc(teh.getDescLangFirst());
                            }
                        }
                        for (LookUp vil : model.getVillage()) {
                            if (vil.getLookUpCode().equals(assMst.getTppVillageMauja())) {
                                assMst.setTppVillageMaujaDesc(vil.getDescLangFirst());
                            }
                        }

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

                        List<Long> finYearList = new ArrayList<>();
                        List<ProvisionalAssesmentDetailDto> unitList = new ArrayList<ProvisionalAssesmentDetailDto>();
                        List<FinancialYear> financialYearList = null;
                        if (org.apache.commons.lang.StringUtils.equals(model.getNoOfDaysEditableFlag(),
                                MainetConstants.FlagN)) {
                            if ((org.apache.commons.lang.StringUtils.equals(assMst.getAssActive(), MainetConstants.FlagI)
                                    && billDeletionInactive != null
                                    && org.apache.commons.lang.StringUtils.isNotBlank(billDeletionInactive.getOtherField())
                                    && org.apache.commons.lang.StringUtils.equals(billDeletionInactive.getOtherField(),
                                            MainetConstants.FlagY))
                                    || (serviceId != null && serviceId.equals(assMst.getSmServiceId()))) {
                                financialYearList = iFinancialYear.getFinanceYearListFromGivenDate(orgId,
                                        assMst.getFaYearId(), new Date());
                            } else {
                            	Long finYearId = iFinancialYear.getFinanceYearId(new Date());
                                financialYearList = iFinancialYear.getFinanceYearListAfterGivenDate(
                                        UserSession.getCurrent().getOrganisation().getOrgid(), finYearId,
                                        new Date());
                            }
                        } else {

                            for (ProvisionalAssesmentMstDto proMas : provAssesMastList) {

                                List<FinancialYear> propWisefinancialYearList = new ArrayList<FinancialYear>();
                                FinancialYear finYear = iFinancialYear.getFinincialYearsById(proMas.getFaYearId(),
                                        UserSession.getCurrent().getOrganisation().getOrgid());
                                if (billDeletionInactive != null
                                        && org.apache.commons.lang.StringUtils
                                                .isNotBlank(billDeletionInactive.getOtherField())
                                        && org.apache.commons.lang.StringUtils.equals(billDeletionInactive.getOtherField(),
                                                MainetConstants.FlagY)) {
                                    propWisefinancialYearList = iFinancialYear.getFinanceYearListFromGivenDate(
                                            UserSession.getCurrent().getOrganisation().getOrgid(), billMasList.get(0).getBmYear(),
                                            finYear.getFaToDate());
                                } else {
                                    propWisefinancialYearList = iFinancialYear.getFinanceYearListFromGivenDate(
                                            UserSession.getCurrent().getOrganisation().getOrgid(), proMas.getFaYearId(),
                                            finYear.getFaToDate());

                                }
                                if (!propWisefinancialYearList.isEmpty()) {

                                    List<ProvisionalAssesmentDetailDto> setUnitDetailsForNextYears = setUnitDetailsForNextYearsForNoOfDays(
                                            proMas, finYearList, propWisefinancialYearList);
                                    unitList.addAll(setUnitDetailsForNextYears);

                                    List<ProvisionalAssesmentFactorDtlDto> factorMap = factorMappingForView(proMas);
                                    model.setProvAsseFactDtlDto(factorMap);
                                    String financialYear = null;
                                    for (final FinancialYear finYearTemp : propWisefinancialYearList) {

                                        financialYear = Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate());

                                        getModel().getFinancialYearMap().put(finYearTemp.getFaYear(), financialYear);
                                    }
                                }
                            }

                        }

                        if (org.apache.commons.lang.StringUtils.equals(model.getNoOfDaysEditableFlag(),
                                MainetConstants.FlagN)) {
                            if (!financialYearList.isEmpty()) {
                                propertyService.setUnitDetailsForNextYears(assMst, finYearList, financialYearList);
                                List<ProvisionalAssesmentFactorDtlDto> factorMap = factorMappingForView(assMst);
                                model.setProvAsseFactDtlDto(factorMap);
                                String financialYear = null;
                                for (final FinancialYear finYearTemp : financialYearList) {
                                    financialYear = Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate());
                                    getModel().getFinancialYearMap().put(finYearTemp.getFaYear(), financialYear);
                                }
                            }
                        }

                        /*
                         * not requried for sudha List<TbBillMas> billMasList =
                         * propertyMainBillService.fetchAllBillByPropNo(assMst.getAssNo(), orgId);
                         * assMst.setBillAmount(propertyService.getTotalActualAmount(billMasList, null, null, null)); BigDecimal
                         * outstandingAmount = iReceiptEntryService.getPaidAmountByAdditionalRefNo(orgId, assMst.getAssNo(),
                         * model.getDeptId()); if (outstandingAmount != null) {
                         * assMst.setOutstandingAmount(outstandingAmount.doubleValue()); }
                         * changeInAssessmentService.setLastPaymentDetails(assMst, orgId);
                         */

                        setFactorMap(assMst);
                        this.getModel().setFinYearList(finYearList);
                        if (org.apache.commons.lang.StringUtils.equals(model.getNoOfDaysEditableFlag(), MainetConstants.FlagY)) {
                            assMst.setProvisionalAssesmentDetailDtoList(unitList);
                            assMst.getProvisionalAssesmentDetailDtoList()
                                    .sort(Comparator.comparing(ProvisionalAssesmentDetailDto::getFaYearId));
                        }
                        model.setProvisionalAssesmentMstDto(assMst);
                        String oldPropNo = assMst.getAssOldpropno();
                        String propNo = assMst.getAssNo();
                        model.setProvAssMstDtoList(provAssesMastList);
                        model.setAuthComBillList(billMasList);
                        model.setOldProAssMstDtoList(provAssesMastList);
                        model.setReviseProvisionalAssesmentMstDto(assMst);
                        if(CollectionUtils.isNotEmpty(assMst.getProvisionalAssesmentOwnerDtlDtoList())) {
                        	model.setOwnerName(assMst.getProvisionalAssesmentOwnerDtlDtoList().get(0).getAssoOwnerName());
                        }
                        model.setIntgrtionWithBP(CommonMasterUtility
                                .getDefaultValueByOrg(MainetConstants.Property.IBP, UserSession.getCurrent().getOrganisation())
                                .getLookUpCode());
                        String lookUp = CommonMasterUtility.getDefaultValueByOrg(MainetConstants.Property.propPref.APP,
                                UserSession.getCurrent().getOrganisation()).getLookUpCode();
                        model.getProvisionalAssesmentMstDto().setPartialAdvancePayStatus(lookUp);
                        
                        Organisation organisation = UserSession.getCurrent().getOrganisation();
                        final LookUp taxAppAtBill = CommonMasterUtility.getValueFromPrefixLookUp(
                                MainetConstants.Property.propPref.BILL, MainetConstants.Property.propPref.CAA,
                                organisation);
                        final LookUp chargeApplicableAtBillReceipt = CommonMasterUtility.getValueFromPrefixLookUp(
                                MainetConstants.Property.propPref.BILL_RECEIPT, PrefixConstants.NewWaterServiceConstants.CAA,
                                organisation);
                        Long deptId = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class).getDepartmentIdByDeptCode("AS");
                        model.setDeptId(deptId);
                        List<TbTaxMas> indepenTaxList = tbTaxMasService.fetchAllIndependentTaxes(organisation.getOrgid(), deptId,
                                taxAppAtBill.getLookUpId(), null, chargeApplicableAtBillReceipt.getLookUpId());
                        List<TbTaxMas> depenTaxList = tbTaxMasService.fetchAllDepenentTaxes(organisation.getOrgid(), deptId,
                                taxAppAtBill.getLookUpId(), null, chargeApplicableAtBillReceipt.getLookUpId());
                        indepenTaxList.addAll(depenTaxList);
                        indepenTaxList.forEach(tax ->{
                        	final String taxCode = CommonMasterUtility
    								.getHierarchicalLookUp(tax.getTaxCategory1(), UserSession.getCurrent().getOrganisation()).getLookUpCode();
                        	if(org.apache.commons.lang.StringUtils.equals(taxCode, "N") || org.apache.commons.lang.StringUtils.equals(taxCode, "I")) {
                        	 	BillDisplayDto billDto = new BillDisplayDto();
                            	billDto.setTaxId(tax.getTaxId());
                            	billDto.setTaxDesc(tax.getTaxDesc());
                            	model.getRevisedArrearList().add(billDto);
                        	}
                        });
                        this.getModel().setShowTaxDetails(MainetConstants.FlagN);
                        model.getProvisionalAssesmentMstDto().setAssNo(propNo);
                        model.setSelfAss(propNo);
                        displayYearListPaidAmount(request, null);
                        model.getProvisionalAssesmentMstDto().setAssOldpropno(oldPropNo);
                        return new ModelAndView("PropertyBillReviseForm", MainetConstants.FORM_NAME, model);
                	}else {
                   	 model.addValidationError("Please generate current year bill first");
                        mv = new ModelAndView("PropertyBillReviseValidn", MainetConstants.FORM_NAME, this.getModel());
                        mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
                        return mv;
                	}

                } else {
                    model.addValidationError("Please enter valid property number or Old PID number.");
                    mv = new ModelAndView("PropertyBillReviseValidn", MainetConstants.FORM_NAME, this.getModel());
                    mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
                    return mv;
                }
             

        }

    }
	
	 private void setCommonFields(SelfAssesmentNewModel model) {
	        getModel().setAssType(MainetConstants.Property.CHN_IN_ASESS);
	        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
	        ServiceMaster service = serviceMaster.getServiceByShortName(MainetConstants.Property.serviceShortCode.CIA, orgId);
	        model.setOrgId(orgId);
	        model.setServiceMaster(service);
	        model.setDeptId(service.getTbDepartment().getDpDeptid());
	        model.setServiceId(service.getSmServiceId());

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

	        List<LookUp> lookupList = CommonMasterUtility.lookUpListByPrefix(MainetConstants.Property.propPref.FCT,
	                UserSession.getCurrent().getOrganisation().getOrgid());
	        lookupList.forEach(fact -> {
	            List<ProvisionalAssesmentFactorDtlDto> factList = factorMap.get(Long.toString(fact.getLookUpId()));
	            if (factList == null || factList.isEmpty()) {
	                factorMap.put(Long.toString(fact.getLookUpId()), null);
	            }
	        });
	        if (lookupList.size() > 1) {

	        } else if (lookupList.size() == 1 && lookupList.get(0).getLookUpCode().equals(MainetConstants.CommonConstants.NA)) {
	            getModel().setFactorNotApplicable(lookupList.get(0).getLookUpCode());
	        }
	        this.getModel().setDisplayfactorMap(factorMap);
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
	            	if(propDet.getAssdUnitNo() != null)
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
	    
	    public List<ProvisionalAssesmentDetailDto> setUnitDetailsForNextYearsForNoOfDays(ProvisionalAssesmentMstDto assMst,
	            List<Long> finYearList, List<FinancialYear> financialYearList) {

	        List<ProvisionalAssesmentDetailDto> provisionalDetailDtoFinYearList = new ArrayList<>(0);
	        for (FinancialYear financialYearEach : financialYearList) {
	            ProvisionalAssesmentDetailDto dto = null;
	            List<ProvisionalAssesmentDetailDto> provisionalDetailDtoFinYear = new ArrayList<>(0);
	            finYearList.add(financialYearEach.getFaYear());
	            for (ProvisionalAssesmentDetailDto assDetailDto : assMst.getProvisionalAssesmentDetailDtoList()) {
	                dto = new ProvisionalAssesmentDetailDto();
	                BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);
	                BeanUtils.copyProperties(assDetailDto, dto);
	                dto.setFaYearId(financialYearEach.getFaYear());
	                dto.setProvisionalAssesmentFactorDtlDtoList(assDetailDto.getProvisionalAssesmentFactorDtlDtoList());
	                dto.setLastAssesmentStringDate(
	                        new SimpleDateFormat(MainetConstants.DATE_FRMAT).format(dto.getAssdAssesmentDate()));
	                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	                Date todayWithZeroTime = null;
	                try {
	                    todayWithZeroTime = formatter.parse(formatter.format(dto.getAssdAssesmentDate()));
	                } catch (ParseException e) {
	                    e.printStackTrace();
	                }
	                dto.setLastAssesmentDate(todayWithZeroTime);
	                provisionalDetailDtoFinYear.add(dto);
	            }
	            provisionalDetailDtoFinYearList.addAll(provisionalDetailDtoFinYear);
	        }
	        return provisionalDetailDtoFinYearList;

	    }
	    
	    @RequestMapping(params = "calculateReviseCharges", method = RequestMethod.POST)
	    public ModelAndView calclateReviseCharges(HttpServletRequest request) throws Exception {
	    	 this.bindModel(request);
	         SelfAssesmentNewModel model = this.getModel();
	         Long deptId = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class).getDepartmentIdByDeptCode("AS");
	         ProvisionalAssesmentMstDto dto = model.getProvisionalAssesmentMstDto();
	         List<Long> billPaidFinIds = new ArrayList<Long>();
	         List<TbServiceReceiptMasEntity> billPaymentDoneList = ApplicationContextProvider
	 				.getApplicationContext().getBean(ReceiptRepository.class)
	 				.getCollectionDetails(model.getPreviousBillMasList().get(0).getPropNo(), deptId,
	 						UserSession.getCurrent().getOrganisation().getOrgid());
	         model.setReceiptPaidDetails(billPaymentDoneList);
	         AtomicDouble paidReceiptAmount = new AtomicDouble(0);
	         BillPaymentDetailDto billPayDto = ApplicationContextProvider.getApplicationContext().getBean(PropertyBillPaymentService.class)
	 				.getBillPaymentDetail(null, dto.getAssNo(),
	 						UserSession.getCurrent().getOrganisation().getOrgid(),
	 						UserSession.getCurrent().getEmployee().getEmpId(), null, null, null);
	         
	         billPaymentDoneList.forEach(receiptPaid ->{
	 			if(receiptPaid.getRmAmount().doubleValue() > 0 && org.apache.commons.lang.StringUtils.equals(receiptPaid.getReceiptTypeFlag(), MainetConstants.FlagR)) {
	 				paidReceiptAmount.addAndGet(receiptPaid.getRmAmount().doubleValue());
	 			}
	 		});
	         model.setLastReceiptAmount(paidReceiptAmount.doubleValue());
	         
	         if(CollectionUtils.isNotEmpty(billPaymentDoneList)) {
	        	 model.getPreviousBillMasList().forEach(billMas ->{
	         		billPaymentDoneList.forEach(payDetail ->{
	         			if(org.apache.commons.lang.StringUtils.equals(payDetail.getReceiptTypeFlag(), MainetConstants.FlagR)) {
	         				payDetail.getReceiptFeeDetail().forEach(feeDetail ->{
	             				if(feeDetail.getBmIdNo() != null && feeDetail.getBmIdNo().equals(billMas.getBmIdno())) {
	             					List<Long> billIdList = billPaidFinIds.stream().filter(billPaid -> billPaid.equals(billMas.getBmYear()))
									.collect(Collectors.toList());
	             					if(CollectionUtils.isEmpty(billIdList)) {
	             						billPaidFinIds.add(billMas.getBmYear());
	             					}
	             				}
	             			});
	         			}
	         			
	         		});
	         	});
	         }
	
	         if(model.hasValidationErrors()) {
	        	 ModelAndView mv = new ModelAndView("PropertyBillReviseForm", MainetConstants.FORM_NAME, this.getModel());
                 mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
                 return mv;
	         }
	         model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().forEach(propDetail ->{
	        	 propDetail.setProFaYearIdDesc(model.getFinancialYearMap().get(propDetail.getFaYearId()));
	         });
	         
	         selfAssessmentService.setFactorMappingToAssDto(model.getProvAsseFactDtlDto(), dto);
	         model.getProvisionalAssesmentMstDto().setRemarks("REVISION");
	         model.setShowTaxDetails(MainetConstants.FlagY);
	         Map<Long, BillDisplayDto> taxWiseRebate = new TreeMap<>();
	         ProvisionalAssesmentMstDto assMst  = assesmentMastService.getPropDetailByAssNoOrOldPropNo(UserSession.getCurrent().getOrganisation().getOrgid(), dto.getAssNo(),
	        		 dto.getAssOldpropno(), MainetConstants.STATUS.ACTIVE,null);
	         model.getProvisionalAssesmentMstDto().setAssAcqDate(assMst.getAssAcqDate());
	         Map<Date, List<BillPresentAndCalculationDto>> schWiseChargeMap = propertyBRMSService.fetchCharges(model.getProvisionalAssesmentMstDto(), deptId, taxWiseRebate, model.getFinYearList(), null, model.getAssType(),model.getAssesmentManualDate(),null);
	         List<TbBillMas> billMasList = propertyService.generateNewBill(schWiseChargeMap,
	                 UserSession.getCurrent().getOrganisation(), model.getDeptId(), null,null);
	         
	         List<BillReceiptPostingDTO> reabteRecDtoList = selfAssessmentService.knowkOffDemandLevelRebateAndExemption(billMasList,
	                 schWiseChargeMap,
	                 UserSession.getCurrent().getOrganisation(), taxWiseRebate);
	         
	         for (TbBillMas billDto : billMasList) {
	        	 for (TbBillDet detDto : billDto.getTbWtBillDet()) {
	        		 TbTaxMas taxMaster = tbTaxMasService.findTaxByTaxIdAndOrgId(detDto.getTaxId(), UserSession.getCurrent().getOrganisation().getOrgid());
	        		 detDto.setCollSeq(taxMaster.getCollSeq());
				}
				
			}
	         model.setReabteRecDtoList(reabteRecDtoList);
	         AtomicDouble receiptAmount = new AtomicDouble(0);
	         dto.getProvisionalAssesmentDetailDtoList().forEach(det ->{
	        	 receiptAmount.addAndGet(det.getRevisedPaidAmount());
	         });
	         final Map<Long, Double> details = new HashMap<>(0);
	 		final Map<Long, Long> billDetails = new HashMap<>(0);
	 		final List<Map<Long, List<Double>>> rebateDetails = new ArrayList<>();

	 		
	 		for (BillDisplayDto reviseAmt : model.getRevisedArrearList()) {
				for (TbBillDet det : billMasList.get(0).getTbWtBillDet()) {
					if(reviseAmt.getTaxId().equals(det.getTaxId())) {
					if (reviseAmt.getArrearsTaxAmt() != null) {
						final String taxCode = CommonMasterUtility
								.getHierarchicalLookUp(det.getTaxCategory(), UserSession.getCurrent().getOrganisation())
								.getLookUpCode();
						if (!org.apache.commons.lang.StringUtils.equals(MainetConstants.FlagI, taxCode)) {
							det.setBdCurTaxamt(det.getBdCurTaxamt() + reviseAmt.getArrearsTaxAmt().doubleValue());
							det.setBdCurBalTaxamt(det.getBdCurBalTaxamt() + reviseAmt.getArrearsTaxAmt().doubleValue());
						}

					}
	 				}
				}
			}
	 		billMasterCommonService.updateArrearInCurrentBillsForNewBillGenertaion(billMasList);
	 		List<TbBillMas> afterKnockOff = new ArrayList<TbBillMas>();
	 		AtomicDouble advanceAmount = new AtomicDouble(0);
	 		Map<CommonChallanDTO,List<BillReceiptPostingDTO>> receiptInsertionList = new HashMap<CommonChallanDTO, List<BillReceiptPostingDTO>>();
	 		LinkedHashMap<CommonChallanDTO,List<BillReceiptPostingDTO>> manualReceiptInsertionList = new LinkedHashMap<CommonChallanDTO, List<BillReceiptPostingDTO>>();
	 		if(receiptAmount.doubleValue() > 0 || CollectionUtils.isNotEmpty(billPaymentDoneList)) {
	 			model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().forEach(propDetail ->{
	 				billMasList.forEach(billMas -> {
	 					
					List<TbBillMas> alreadyBillAdded = afterKnockOff.stream().filter(knockOff -> knockOff.getBmYear().equals(propDetail.getFaYearId()))
							.collect(Collectors.toList());
	 					if (propDetail.getFaYearId().equals(billMas.getBmYear()) && CollectionUtils.isEmpty(alreadyBillAdded)) {
	 						TbBillMas lastBillMas = null;
	 						if(CollectionUtils.isNotEmpty(afterKnockOff)) {
	 							lastBillMas = afterKnockOff.get(afterKnockOff.size() -1);
	 						}
	 						billMas.setWtLo1("Y");
	 						afterKnockOff.add(billMas);
	 						//billMasterCommonService.updateArrearInCurrentBillsForNewBillGenertaion(afterKnockOff);
	 						if(afterKnockOff.size() > 1) {
	 							propertyService.updateArrearInCurrentBillsForBillRevise(afterKnockOff, lastBillMas);
	 						}
	 						afterKnockOff.forEach(billMass ->{
	 							billMass.setWtLo1(null);
	 						});
	 						propertyService.interestCalculationWithoutBRMSCall(UserSession.getCurrent().getOrganisation(),
	 								model.getDeptId(), afterKnockOff, MainetConstants.Property.INT_RECAL_NO);
	 						afterKnockOff.forEach(billMasKnock -> {
 								billMasKnock.setWtLo3(MainetConstants.FlagY);
	 							billMasKnock.getTbWtBillDet().sort(Comparator.comparing(TbBillDet::getCollSeq));
	 						});
	 						
	 						String paymentCategory= null;
	 						if(propDetail.getRevisedPaidAmount() <= 0) {
	 							paymentCategory = "A";
	 						}
	 						propDetail.setRevisedPaidAmount(propDetail.getRevisedPaidAmount() + advanceAmount.doubleValue());
	 						if(propDetail.getRevisedPaidAmount() > 0) {
	 							List<BillReceiptPostingDTO> result = new ArrayList<>();
	 							advanceAmount.getAndSet(0);
	 							Organisation organisation = UserSession.getCurrent().getOrganisation();
	 							
	 							if (checkBifurcationMethodApplicable(organisation)) {
	 								result = billMasterCommonService.updateBifurcationMethodBillData(afterKnockOff, propDetail.getRevisedPaidAmount(), details,
			 								billDetails, UserSession.getCurrent().getOrganisation(), rebateDetails, null);
							} else if (Utility.isEnvPrefixAvailable(organisation, "KIF")) {

								if (org.apache.commons.lang.StringUtils.isNotBlank(model.getInterWaiveOffAppl())
										&& org.apache.commons.lang.StringUtils.equals(MainetConstants.FlagY,
												model.getInterWaiveOffAppl())) {
									afterKnockOff.forEach(afterBillMas -> {
										afterBillMas.getTbWtBillDet().forEach(det -> {
											final String taxCode = CommonMasterUtility
													.getHierarchicalLookUp(det.getTaxCategory(),
															UserSession.getCurrent().getOrganisation())
													.getLookUpCode();
											if (org.apache.commons.lang.StringUtils.equals(MainetConstants.FlagI,
													taxCode)) {
												det.setBdCurBalTaxamt(0.0);
												det.setBdPrvBalArramt(0.0);
											}
										});
									});
									result = billMasterCommonService.updateBillData(afterKnockOff,
											propDetail.getRevisedPaidAmount(), details,
											billDetails, UserSession.getCurrent().getOrganisation(), rebateDetails, null);
								} else {

									List<BillReceiptPostingDTO> interestResult = billMasterCommonService.updateBillDataForInterest(afterKnockOff,
											propDetail.getRevisedPaidAmount(), details, billDetails,
											UserSession.getCurrent().getOrganisation(), rebateDetails, null);
									result = billMasterCommonService.updateBillData(afterKnockOff,
											afterKnockOff.get(afterKnockOff.size() - 1).getExcessAmount(), details,
											billDetails, UserSession.getCurrent().getOrganisation(), rebateDetails, null);
									result.addAll(interestResult);

								}
								
								Date manualRecepDate = billMas.getBmFromdt();
								if(propDetail.getManualRecDate() != null) {
									manualRecepDate = propDetail.getManualRecDate();
								}
								CommonChallanDTO createDtoForReceiptInsertion = createDtoForReceiptInsertion(null, deptId, details, billDetails, billPayDto
										,propDetail.getRevisedPaidAmount(),manualRecepDate,propDetail.getManualReceiptNo(),paymentCategory);
								manualReceiptInsertionList.put(createDtoForReceiptInsertion, result);
							}
	 							else {
	 								billMasterCommonService.updateBillData(afterKnockOff, propDetail.getRevisedPaidAmount(), details,
			 								billDetails, UserSession.getCurrent().getOrganisation(), rebateDetails, null);
	 							}
	 							
	 						}
	 						if(afterKnockOff.get(afterKnockOff.size() - 1).getExcessAmount() > 0) {
	 							advanceAmount.getAndSet(afterKnockOff.get(afterKnockOff.size() - 1).getExcessAmount());
	 						}
						/*
						 * afterKnockOff.forEach(billMasDto ->{ billMasDto.getTbWtBillDet().forEach(det
						 * ->{ det.setBdPrvArramt(det.getBdPrvBalArramt()); }); });
						 */
	 						billPaymentDoneList.forEach(receiptDetail ->{
	 							if(org.apache.commons.lang.StringUtils.equals(receiptDetail.getReceiptTypeFlag(), MainetConstants.FlagR)) {
	 								Long financeYearId = iFinancialYear.getFinanceYearId(receiptDetail.getRmDate());
	 								if(financeYearId.equals(propDetail.getFaYearId())) {
	 									insertNewReceipt(afterKnockOff, receiptDetail.getRmAmount().doubleValue(), receiptDetail, deptId, receiptInsertionList,dto.getAssNo(),model.getInterWaiveOffAppl(),billPayDto);
	 									if(afterKnockOff.get(afterKnockOff.size() - 1).getExcessAmount() > 0) {
	 			 							advanceAmount.addAndGet(afterKnockOff.get(afterKnockOff.size() - 1).getExcessAmount());
	 			 						}
	 								}
		 						}
	 						});
	 						
	 						
	 					}
	 				});
	 		 		
	 		 		});
	 		}else {
	 			afterKnockOff.addAll(billMasList);
	 			propertyService.interestCalculationWithoutBRMSCall(UserSession.getCurrent().getOrganisation(),
							model.getDeptId(), afterKnockOff, MainetConstants.Property.INT_RECAL_NO);
	 			billMasterCommonService.updateArrearInCurrentBillsForNewBillGenertaion(billMasList);
	 		}
	 		
	 		if (org.apache.commons.lang.StringUtils.isNotBlank(model.getInterWaiveOffAppl())
					&& org.apache.commons.lang.StringUtils.equals(MainetConstants.FlagY,
							model.getInterWaiveOffAppl())) {
			afterKnockOff.forEach(afterBillMas -> {
				afterBillMas.getTbWtBillDet().forEach(det -> {
					final String taxCode = CommonMasterUtility
							.getHierarchicalLookUp(det.getTaxCategory(), UserSession.getCurrent().getOrganisation())
							.getLookUpCode();
					if (org.apache.commons.lang.StringUtils.equals(MainetConstants.FlagI, taxCode)) {
						det.setBdCurBalTaxamt(0.0);
						det.setBdPrvBalArramt(0.0);
					}
				});
			});

			if(CollectionUtils.isNotEmpty(model.getRevisedArrearList())) {
				for (BillDisplayDto reviseAmt : model.getRevisedArrearList()) {
					for (TbBillDet det : afterKnockOff.get(0).getTbWtBillDet()) {
						if(reviseAmt.getTaxId().equals(det.getTaxId())) {
						if (reviseAmt.getArrearsTaxAmt() != null) {
							final String taxCode = CommonMasterUtility
									.getHierarchicalLookUp(det.getTaxCategory(), UserSession.getCurrent().getOrganisation())
									.getLookUpCode();
							if (org.apache.commons.lang.StringUtils.equals(MainetConstants.FlagI, taxCode)) {
								det.setBdCurTaxamt(det.getBdCurTaxamt() + reviseAmt.getArrearsTaxAmt().doubleValue());
								det.setBdCurBalTaxamt(det.getBdCurBalTaxamt() + reviseAmt.getArrearsTaxAmt().doubleValue());
							}

						}
		 				}
					}
				}
			}
			
			billMasterCommonService.updateArrearInCurrentBillsForNewBillGenertaion(afterKnockOff);
		}
	 		
	 		model.getReceiptInsertionList().putAll(receiptInsertionList);
	 		model.getManualReceiptInsertionList().putAll(manualReceiptInsertionList);
	         Long finYearId = iFinancialYear.getFinanceYearId(new Date());
	         propertyService.taxCarryForward(afterKnockOff, model.getOrgId());
	         BillDisplayDto surCharge = propertyService.calculateSurcharge(UserSession.getCurrent().getOrganisation(),
	                 model.getDeptId(), billMasList, null,
	                 MainetConstants.Property.SURCHARGE, finYearId, null);
	         if (surCharge != null && surCharge.getTotalTaxAmt() != null) {
	             model.getProvisionalAssesmentMstDto().setTotalSubcharge(surCharge.getTotalTaxAmt().doubleValue());
	         }
	         List<BillDisplayDto> earlyPayRebate = null;
	         Map<String, List<BillDisplayDto>> presentMap = selfAssessmentService.getDisplayMapOfTaxesForRevision(afterKnockOff,
	                 UserSession.getCurrent().getOrganisation(), model.getDeptId(), earlyPayRebate, taxWiseRebate, surCharge,model.getInterWaiveOffAppl());
	         
	         if(advanceAmount != null && advanceAmount.doubleValue() > 0) {
	        	  Long advanceTaxId = ApplicationContextProvider.getApplicationContext()
	                      .getBean(BillMasterCommonService.class)
	                      .getAdvanceTaxId(UserSession.getCurrent().getOrganisation().getOrgid(),
	                              MainetConstants.Property.PROP_DEPT_SHORT_CODE, null);
	 	         TbTaxMas taxMas = ApplicationContextProvider.getApplicationContext().getBean(TbTaxMasService.class)
	                      .findTaxByTaxIdAndOrgId(advanceTaxId,
	                              UserSession.getCurrent().getOrganisation().getOrgid());
	 	        BillDisplayDto advanceAmtDto = new BillDisplayDto();
                advanceAmtDto.setCurrentYearTaxAmt(BigDecimal.valueOf(advanceAmount.doubleValue()));
                advanceAmtDto.setTotalTaxAmt(BigDecimal.valueOf(advanceAmount.doubleValue()));
                advanceAmtDto.setTaxId(taxMas.getTaxId());
                advanceAmtDto.setTaxCategoryId(taxMas.getTaxCategory1());
                advanceAmtDto.setDisplaySeq(taxMas.getTaxDisplaySeq());
                advanceAmtDto.setTaxDesc(taxMas.getTaxDesc());
                List<BillDisplayDto> advanceList = new ArrayList<BillDisplayDto>();
                advanceList.add(advanceAmtDto);
	 	         presentMap.put(taxMas.getTaxDesc(), advanceList); 
	 	         model.setAdvanceAmt(advanceAmtDto);
	         }
	         
	         dto.setBillTotalAmt(propertyService.getTotalActualAmountForRevision(afterKnockOff, earlyPayRebate, taxWiseRebate, surCharge));
	         model.setBillMasList(afterKnockOff);
	         model.setDisplayMap(presentMap);
	         model.setApprovalFlag(String.valueOf(model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().get(0).getAssdRv()));
	         model.setPreviousSurcharge(model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().get(0).getAssdStdRate());
	    	 return new ModelAndView("PropertyBillReviseForm", MainetConstants.FORM_NAME, model);
	    }
	    
	    @RequestMapping(params = "getFinancialYear", method = RequestMethod.POST)
	    public @ResponseBody Long getyearOfAcquisition(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
	            String yearOfAcq) {
	        this.getModel().bind(httpServletRequest);
	        SelfAssesmentNewModel model = this.getModel();
	       // model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().clear();
	        Date currentDate = Utility.stringToDate(yearOfAcq);
	        model.getProvAsseFactDtlDto().clear();  // clear dto on year change
	         return iFinancialYear.getFinanceYearId(currentDate);
	         

	    }
	    
	    @RequestMapping(params = "displayYearListBasedOnDate", method = RequestMethod.POST)
	    public @ResponseBody Map<Long, String> displayYearListBasedOnDate(HttpServletRequest httpServletRequest,
	            @RequestParam(value = "finYearId") Long finYearId) throws Exception {
	    	this.getModel().bind(httpServletRequest);
	        SelfAssesmentNewModel model = this.getModel();
	    	 List<TbBillMas> billMasList = propertyMainBillService.fetchAllBillByPropNo(model.getProvisionalAssesmentMstDto().getAssNo(), UserSession.getCurrent().getOrganisation().getOrgid());
	        List<FinancialYear> financialYearList = iFinancialYear
	                .getFinanceYearListFromGivenDate(UserSession.getCurrent().getOrganisation().getOrgid(), finYearId, billMasList.get(billMasList.size() - 1).getBmTodt());
	        String financialYear = null;
	        Map<Long, String> yearMap = new HashMap<>(0);
	        for (final FinancialYear finYearTemp : financialYearList) {
	            financialYear = Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate());
	            yearMap.put(finYearTemp.getFaYear(), financialYear);

	        }
	        this.getModel().setFinancialYearMap(yearMap);
	        List<Long> finYearList = new ArrayList<>();
	        List<ProvisionalAssesmentDetailDto> unitList = new ArrayList<ProvisionalAssesmentDetailDto>();
	        
	        return yearMap;
	    }
	    
	    @RequestMapping(params = "getFinanceYearListFromGivenDate", method = RequestMethod.POST)
	    public @ResponseBody List<Long> getFinanceYearListFromGivenDate(HttpServletRequest httpServletRequest,
	            HttpServletResponse httpServletResponse, @RequestParam(value = "finYearId") Long finYearId) {
	    	this.getModel().bind(httpServletRequest);
	        SelfAssesmentNewModel model = this.getModel();
	        List<Long> finYearList = null;
	        List<TbBillMas> billMasList = propertyMainBillService.fetchAllBillByPropNo(model.getProvisionalAssesmentMstDto().getAssNo(), UserSession.getCurrent().getOrganisation().getOrgid());
	        model.setPreviousBillMasList(billMasList);
	        List<FinancialYear> financialYearList = iFinancialYear
	                .getFinanceYearListFromGivenDate(UserSession.getCurrent().getOrganisation().getOrgid(), finYearId, billMasList.get(billMasList.size() - 1).getBmTodt());
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
	    
	@ResponseBody
	@RequestMapping(params = "updateRevisedBill", method = RequestMethod.POST)
	public Map<String, Object> updateRevisedBill(HttpServletRequest request) {
		this.getModel().bind(request);
		SelfAssesmentNewModel model = this.getModel();
		Map<String, Object> object = new LinkedHashMap<String, Object>();
		final Map<Long, Double> details = new HashMap<>(0);
		final Map<Long, Long> billDetails = new HashMap<>(0);
		final List<Map<Long, List<Double>>> rebateDetails = new ArrayList<>();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		
		List<DocumentDetailsVO> docs  = new ArrayList<>(0);
          if ((FileUploadUtility.getCurrent().getFileMap() != null)
                  && !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
              for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
                  final List<File> list = new ArrayList<>(entry.getValue());
                  for (final File file : list) {
                      try {
                          DocumentDetailsVO d = new DocumentDetailsVO();
                          final Base64 base64 = new Base64();
                          final String bytestring = base64.encodeToString(FileUtils.readFileToByteArray(file));
                          d.setDocumentByteCode(bytestring);
                          d.setDocumentName(file.getName());
                          docs.add(d);
                      } catch (final IOException e) {
                      }
                  }
              }
          } else {
        	  object.put(MainetConstants.ERROR, "Please upload document");
        	  return object;
          }
          model.getPreviousBillMasList().forEach(previousBill ->{
        	  previousBill.setRevisedBillDate(new Date());
        	  previousBill.setRevisedBillType(MainetConstants.FlagR);
        	  previousBill.getTbWtBillDet().forEach(detDto ->{
        		  detDto.setRevisedBillDate(new Date());
        		  detDto.setRevisedBillType(MainetConstants.FlagR);
        	  });
          });
		propertyMainBillService.copyDataFromMainBillDetailToHistory(model.getPreviousBillMasList(),
				MainetConstants.FlagR,UserSession.getCurrent().getEmployee().getEmpId(),model.getClientIpAddress());

		AtomicBoolean mergeArrearCurrFlag = new AtomicBoolean(true);
		model.getBillMasList().forEach(newBillMas -> {

			List<TbBillMas> oldBillMas = model.getPreviousBillMasList().stream()
					.filter(preBillMas -> preBillMas.getBmYear().equals(newBillMas.getBmYear()))
					.collect(Collectors.toList());
			if (CollectionUtils.isNotEmpty(oldBillMas)) {

				newBillMas.setBmIdno(oldBillMas.get(0).getBmIdno());
				newBillMas.setBmNo(oldBillMas.get(0).getBmNo());
				newBillMas.setLmoddate(oldBillMas.get(0).getLmoddate());
				newBillMas.setLgIpMac(oldBillMas.get(0).getLgIpMac());
				newBillMas.setUserId(oldBillMas.get(0).getUserId());
				newBillMas.setPropNo(oldBillMas.get(0).getPropNo());
				newBillMas.setBmBilldt(oldBillMas.get(0).getBmBilldt());
				newBillMas.setBmDuedate(oldBillMas.get(0).getBmDuedate());

				newBillMas.getTbWtBillDet().forEach(newBillDet -> {
					List<TbBillDet> oldBillDet = oldBillMas.get(0).getTbWtBillDet().stream()
							.filter(billDet -> billDet.getTaxId().equals(newBillDet.getTaxId()))
							.collect(Collectors.toList());
					
					if (mergeArrearCurrFlag.get()) {
						newBillDet.setBdCurTaxamt(newBillDet.getBdCurTaxamt() + newBillDet.getBdPrvArramt());
						newBillDet.setBdCurBalTaxamt(newBillDet.getBdCurBalTaxamt() + newBillDet.getBdPrvBalArramt());
					}
					if (CollectionUtils.isNotEmpty(oldBillDet)) {
						newBillDet.setBdBilldetid(oldBillDet.get(0).getBdBilldetid());
						newBillDet.setLmoddate(oldBillDet.get(0).getLmoddate());
						newBillDet.setLgIpMac(oldBillDet.get(0).getLgIpMac());
						newBillDet.setUserId(oldBillDet.get(0).getUserId());
						/*
						 * if(newBillDet.getBdCurTaxamt() <=0) {
						 * newBillDet.setBdCurTaxamt(oldBillDet.get(0).getBdCurTaxamt());
						 * newBillDet.setBdCurBalTaxamt(oldBillDet.get(0).getBdCurBalTaxamt()); }
						 */
						//newBillDet.setBdCurTaxamt(newBillDet.getBdCurTaxamt() - paidAmnt);
						//double balanceAmount = newBillDet.getBdCurBalTaxamt() - paidAmnt;
					}else {
						final long bdBilldetid = seqGenFunctionUtility.generateJavaSequenceNo(
			                    MainetConstants.Property.PROP_DEPT_SHORT_CODE, MainetConstants.Property.table.TB_AS_PRO_BILL_DET,
			                    MainetConstants.Property.primColumn.PRO_BILL_DET_ID, null,
			                    null);
						newBillDet.setBdBilldetid(bdBilldetid);
						newBillDet.setLmoddate(new Date());
						newBillDet.setLgIpMac(model.getClientIpAddress());
						newBillDet.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
					}
					newBillDet.setBmIdno(newBillMas.getBmIdno());
					if (mergeArrearCurrFlag.get()) {
						newBillDet.setBdPrvArramt(0.0);
						newBillDet.setBdPrvBalArramt(0.0);
					}
					//newBillDet.setBdPrvArramt(0.0);
					//newBillDet.setBdPrvBalArramt(0.0);
				});
				mergeArrearCurrFlag.getAndSet(false);

			}
		});
		
		List<TbBillMas> newBillListToSave = model.getBillMasList().stream()
		.filter(newBill -> newBill.getBmIdno() > 0)
		.collect(Collectors.toList());
		propertyService.taxCarryForward(newBillListToSave, UserSession.getCurrent().getOrganisation().getOrgid());
		billMasterCommonService.updateArrearInCurrentBills(newBillListToSave);
		
		for (Entry<CommonChallanDTO, List<BillReceiptPostingDTO>> printingDto : model.getReceiptInsertionList()
				.entrySet()) {			
			CommonChallanDTO offlineDto = printingDto.getKey();
			TbServiceReceiptMasEntity rcptMastEntity = receiptEntryService.insertInReceiptMaster(offlineDto,
					printingDto.getValue());
			ApplicationContextProvider.getApplicationContext().getBean(IChallanService.class).setReceiptDtoAndSaveDuplicateService(rcptMastEntity, offlineDto);
		}
		

        model.getReceiptPaidDetails().forEach(receiptDetailsDto ->{
        		List<TbServiceReceiptMasEntity> receiptList = new ArrayList<TbServiceReceiptMasEntity>();
        		receiptList.add(receiptDetailsDto);
        		receiptEntryService.inActiveReceiptByReceiptList(UserSession.getCurrent().getOrganisation().getOrgid(), receiptList, MainetConstants.Property.BILL_REV_INACTIVE_COMM, UserSession.getCurrent().getEmployee().getEmpId());
        	
        });
        
        newBillListToSave.forEach(billMasDto ->{
        	billMasDto.setRevisedBillDate(new Date());
        	billMasDto.setRevisedBillType(MainetConstants.FlagR);
				billMasDto.getTbWtBillDet().forEach(det ->{
					det.setRevisedBillDate(new Date());
					det.setRevisedBillType(MainetConstants.FlagR);
					//det.setBdPrvArramt(det.getBdPrvBalArramt());
				});
			});
        
        
        /**
         * Defect -> 152010 by srikanth
         */
        newBillListToSave.get(0).setBmActualArrearsAmt(0.0);
        newBillListToSave.get(0).setBmTotalArrears(0.0);
        newBillListToSave.get(0).setBmTotalArrearsWithoutInt(0.0);
 		if (org.apache.commons.lang.StringUtils.isNotBlank(model.getInterWaiveOffAppl())
				&& org.apache.commons.lang.StringUtils.equals(MainetConstants.FlagY,
						model.getInterWaiveOffAppl())) {
 			newBillListToSave.forEach(afterBillMas -> {
			afterBillMas.getTbWtBillDet().forEach(det -> {
				final String taxCode = CommonMasterUtility
						.getHierarchicalLookUp(det.getTaxCategory(), UserSession.getCurrent().getOrganisation())
						.getLookUpCode();
				if (org.apache.commons.lang.StringUtils.equals(MainetConstants.FlagI, taxCode)) {
					det.setBdCurBalTaxamt(0.0);
					det.setBdPrvBalArramt(0.0);
					det.setBdCurTaxamt(0.0);
					det.setBdPrvArramt(0.0);
				}
			});
		});

		if(CollectionUtils.isNotEmpty(model.getRevisedArrearList())) {
			for (BillDisplayDto reviseAmt : model.getRevisedArrearList()) {
				for (TbBillDet det : newBillListToSave.get(0).getTbWtBillDet()) {
					if(reviseAmt.getTaxId().equals(det.getTaxId())) {
					if (reviseAmt.getArrearsTaxAmt() != null) {
						final String taxCode = CommonMasterUtility
								.getHierarchicalLookUp(det.getTaxCategory(), UserSession.getCurrent().getOrganisation())
								.getLookUpCode();
						if (org.apache.commons.lang.StringUtils.equals(MainetConstants.FlagI, taxCode)) {
							det.setBdCurTaxamt(det.getBdCurTaxamt() + reviseAmt.getArrearsTaxAmt().doubleValue());
							det.setBdCurBalTaxamt(det.getBdCurBalTaxamt() + reviseAmt.getArrearsTaxAmt().doubleValue());
						}

					}
	 				}
				}
			}
		}
		
		billMasterCommonService.updateArrearInCurrentBills(newBillListToSave);
	}

 		propertyService.updateARVRVInBill(model.getProvisionalAssesmentMstDto(), newBillListToSave);
        
        billMasterCommonService.updateArrearInCurrentBills(newBillListToSave);
		List<MainBillMasEntity> saveAndUpdateMainBill = propertyMainBillService.saveAndUpdateMainBill(newBillListToSave,
				UserSession.getCurrent().getOrganisation().getOrgid(),
				UserSession.getCurrent().getEmployee().getEmpId(), MainetConstants.Property.AuthStatus.AUTH,
				model.getClientIpAddress());
		if(model.getAdvanceAmt() != null && model.getAdvanceAmt().getCurrentYearTaxAmt() != null &&  model.getAdvanceAmt().getCurrentYearTaxAmt().doubleValue() > 0) {
			saveAdvancePayment(orgId, model.getAdvanceAmt().getCurrentYearTaxAmt().doubleValue(),  model.getReviseProvisionalAssesmentMstDto().getAssNo(), UserSession.getCurrent().getEmployee().getEmpId(), null);
		}
		ProvisionalAssesmentMstDto propMasDto = assesmentMastService.getPropDetailByAssNoOrOldPropNoForBillRevise(orgId, model.getReviseProvisionalAssesmentMstDto().getAssNo(),
                 null, MainetConstants.STATUS.ACTIVE,null);
		List<ProvisionalAssesmentMstDto> allYearAssesment = assesmentMastService.getPropDetailByPropNoOnly(model.getReviseProvisionalAssesmentMstDto().getAssNo());
		List<ProvisionalBillMasEntity> provBillList = new ArrayList<>();
		
		List<Long> deleteBillDetIdList = new ArrayList<Long>();
		model.getPreviousBillMasList().forEach(previousBill ->{
			newBillListToSave.forEach(newBillMas ->{
				if(previousBill.getBmYear().equals(newBillMas.getBmYear())) {
					previousBill.getTbWtBillDet().forEach(previousBillDet ->{
						final String taxCode = CommonMasterUtility.getHierarchicalLookUp(previousBillDet.getTaxCategory(), UserSession.getCurrent().getOrganisation())
								.getLookUpCode();
						if(!taxCode.equals(PrefixConstants.TAX_CATEGORY.INTERST)) {
							Optional<TbBillDet> detExist = newBillMas.getTbWtBillDet().stream().filter(newBillDet -> newBillDet.getTaxId().equals(previousBillDet.getTaxId())).findAny();
							if(!detExist.isPresent()) {
								deleteBillDetIdList.add(previousBillDet.getBdBilldetid());
							}
						}
					});
				}
			});
		});
		
		iProvisionalBillService.saveAndUpdateProvisionalBillForReviseBill(newBillListToSave, orgId, UserSession.getCurrent().getEmployee().getEmpId(),
				propMasDto.getAssNo(), null, provBillList, model.getClientIpAddress());
		if(CollectionUtils.isNotEmpty(deleteBillDetIdList)) {
			propertyMainBillService.deleteBillDetList(deleteBillDetIdList);
		}
		
		if(MapUtils.isNotEmpty(model.getManualReceiptInsertionList())) {
			for (Entry<CommonChallanDTO, List<BillReceiptPostingDTO>> printingDto : model.getManualReceiptInsertionList()
					.entrySet()) {			
				CommonChallanDTO offlineDto = printingDto.getKey();
				selfAssessmentService.mappingDummyKeyToActualKey(provBillList, printingDto.getValue());
				TbServiceReceiptMasEntity rcptMastEntity = receiptEntryService.insertInReceiptMaster(offlineDto,
						printingDto.getValue());
				ApplicationContextProvider.getApplicationContext().getBean(IChallanService.class).setReceiptDtoAndSaveDuplicateService(rcptMastEntity, offlineDto);
			}
		}
		
		selfAssessmentService.saveDemandLevelRebate(propMasDto, UserSession.getCurrent().getEmployee().getEmpId(), model.getDeptId(), newBillListToSave, model.getReabteRecDtoList(),
				UserSession.getCurrent().getOrganisation(), provBillList);
		
		//Long lastFinId = model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().get(model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().size() - 1).getFaYearId();
		for (ProvisionalAssesmentMstDto assMst : allYearAssesment) {
			assMst.setPropLvlRoadType(model.getProvisionalAssesmentMstDto().getPropLvlRoadType());
			assMst.setReviseAssessmentDate(model.getProvisionalAssesmentMstDto().getReviseAssessmentDate());
			for (ProvisionalAssesmentDetailDto detailDto : assMst.getProvisionalAssesmentDetailDtoList()) {
				for (ProvisionalAssesmentDetailDto currentDetailDto : model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList()) {
					if(detailDto.getAssdUnitNo().equals(currentDetailDto.getAssdUnitNo()) && currentDetailDto.getFaYearId().equals(assMst.getFaYearId())) {
						detailDto.setAssdFloorNo(currentDetailDto.getAssdFloorNo());
						detailDto.setAssdYearConstruction(currentDetailDto.getAssdYearConstruction());
						detailDto.setFirstAssesmentDate(currentDetailDto.getFirstAssesmentDate());
						detailDto.setAssdConstruType(currentDetailDto.getAssdConstruType());
						detailDto.setAssdUsagetype1(currentDetailDto.getAssdUsagetype1());
						detailDto.setAssdUsagetype2(currentDetailDto.getAssdUsagetype2());
						detailDto.setAssdAlv(currentDetailDto.getAssdAlv());
						detailDto.setAssdRv(currentDetailDto.getAssdRv());
						detailDto.setAssdOccupancyType(currentDetailDto.getAssdOccupancyType());
						detailDto.setAssdBuildupArea(currentDetailDto.getAssdBuildupArea());
						detailDto.setOccupierName(currentDetailDto.getOccupierName());
						detailDto.setAssdNatureOfproperty1(currentDetailDto.getAssdNatureOfproperty1());
						detailDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
						detailDto.setUpdatedDate(new Date());
						detailDto.setLgIpMacUpd(model.getClientIpAddress());
						
					}
					
				}
			}
			assesmentMastService.saveAndUpdateAssessmentMastForOnlyForDES(assMst,
					UserSession.getCurrent().getOrganisation().getOrgid(),
					UserSession.getCurrent().getEmployee().getEmpId(), model.getClientIpAddress());
		}
		
		if(CollectionUtils.isNotEmpty(docs)) {
			 RequestDTO dto = new RequestDTO();
	         dto.setDeptId(model.getDeptId());
	         dto.setServiceId(propMasDto.getSmServiceId());
	         dto.setReferenceId(propMasDto.getAssNo());
	         dto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
	         dto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
	         dto.setLangId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
	         fileUpload.doFileUpload(docs, dto);
		}
		 
		object.put("successFlag", MainetConstants.FlagY);
		
		
		return object;
	}
	    
	 
	    @RequestMapping(params = "edit", method = RequestMethod.POST)
	    public ProvisionalAssesmentMstDto edit(HttpServletRequest request) {
	    	this.getModel().bind(request);
	    	SelfAssesmentNewModel model = this.getModel();
	    	return model.getProvisionalAssesmentMstDto();
	    	
	    }
	    
	public boolean checkBifurcationMethodApplicable(Organisation organisation) {
		boolean bifurcationApplicable = false;
		LookUp bifurcationMethod = null;
		try {
			bifurcationMethod = CommonMasterUtility.getValueFromPrefixLookUp("B", "RBA", organisation);
		} catch (Exception exception) {

		}

		if (bifurcationMethod != null && org.apache.commons.lang.StringUtils.equals(MainetConstants.FlagY, bifurcationMethod.getOtherField())) {
			bifurcationApplicable = true;
		}
		
		return bifurcationApplicable;

	}
	
	private void insertNewReceipt(List<TbBillMas> newBillListToSave, double paidAmount,
			TbServiceReceiptMasEntity receiptDetail, Long deptId,
			Map<CommonChallanDTO, List<BillReceiptPostingDTO>> receiptInsertionList, String propNo,
			String interstWaiveOff,BillPaymentDetailDto billPayDto) {
		final Map<Long, Double> details = new HashMap<>(0);
		final Map<Long, Long> billDetails = new HashMap<>(0);
		final List<Map<Long, List<Double>>> rebateDetails = new ArrayList<>();
		
		List<BillReceiptPostingDTO> result  = new ArrayList<BillReceiptPostingDTO>();
		
		if (checkBifurcationMethodApplicable(UserSession.getCurrent().getOrganisation())) {
			result = billMasterCommonService.updateBifurcationMethodBillData(newBillListToSave, paidAmount,
					details, billDetails, UserSession.getCurrent().getOrganisation(), rebateDetails, null);
		} else if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "KIF")) {
			List<BillReceiptPostingDTO> interestResult = new ArrayList<>();

			if (org.apache.commons.lang.StringUtils.isNotBlank(interstWaiveOff)
					&& org.apache.commons.lang.StringUtils.equals(MainetConstants.FlagY, interstWaiveOff)) {
				newBillListToSave.forEach(afterBillMas -> {
					afterBillMas.getTbWtBillDet().forEach(det -> {
						final String taxCode = CommonMasterUtility
								.getHierarchicalLookUp(det.getTaxCategory(), UserSession.getCurrent().getOrganisation())
								.getLookUpCode();
						if (org.apache.commons.lang.StringUtils.equals(MainetConstants.FlagI, taxCode)) {
							det.setBdCurBalTaxamt(0.0);
							det.setBdPrvBalArramt(0.0);
						}
					});
				});
				result = billMasterCommonService.updateBillData(newBillListToSave,
						paidAmount, details, billDetails,
						UserSession.getCurrent().getOrganisation(), rebateDetails, null);
			} else {
				interestResult = billMasterCommonService.updateBillDataForInterest(newBillListToSave, paidAmount,
						details, billDetails, UserSession.getCurrent().getOrganisation(), rebateDetails, null);
				result = billMasterCommonService.updateBillData(newBillListToSave,
						newBillListToSave.get(newBillListToSave.size() - 1).getExcessAmount(), details, billDetails,
						UserSession.getCurrent().getOrganisation(), rebateDetails, null);

			}

			result.addAll(interestResult);
		}
		else {
			result = billMasterCommonService.updateBillData(newBillListToSave, paidAmount, details,
					billDetails, UserSession.getCurrent().getOrganisation(), rebateDetails, null);
		}
		
		
		CommonChallanDTO offline = createDtoForReceiptInsertion(receiptDetail, deptId, details, billDetails,
				billPayDto,null,null,null,null);
        if(receiptDetail.getRmDemand() != null) {
        	result.get(0).setTotalDemand(receiptDetail.getRmDemand().doubleValue());
        }
      
         
        List<TbServiceReceiptMasEntity> receiptList = new ArrayList<TbServiceReceiptMasEntity>();
        receiptList.add(receiptDetail);
        receiptInsertionList.put(offline, result);
        
    //ApplicationContextProvider.getApplicationContext().getBean(IReceiptEntryService.class).inActiveReceiptByReceiptList(session.getOrganisation().getOrgid(), receiptList, "Inactive at the time of bill revision", session.getEmployee().getEmpId());
    //ApplicationContextProvider.getApplicationContext().getBean(IReceiptEntryService.class).insertInReceiptMaster(offline, result);
		
	}

	private CommonChallanDTO createDtoForReceiptInsertion(TbServiceReceiptMasEntity receiptDetail, Long deptId,
			final Map<Long, Double> details, final Map<Long, Long> billDetails, BillPaymentDetailDto billPayDto,Double Amnt,Date manualRecDate,String manualReceiptNo,String paymentCategory) {
		CommonChallanDTO offline = new CommonChallanDTO();
		

    	LOGGER.info("Begin--> " + this.getClass().getSimpleName() + " setChallanDToandSaveChallanData() method");
        final UserSession session = UserSession.getCurrent();
        
        LookUp printBillPaymentDescOnBill = null;
		try {
			printBillPaymentDescOnBill = CommonMasterUtility.getValueFromPrefixLookUp("PPB", "ENV",
					UserSession.getCurrent().getOrganisation());
		} catch (Exception e) {
			LOGGER.error("No prefix found for ENV(PPB)");
		}
		LookUp printManualReceiptYear = null;
		try {
			printManualReceiptYear = CommonMasterUtility.getValueFromPrefixLookUp("PMY", "ENV",
					UserSession.getCurrent().getOrganisation());
		} catch (Exception e) {
			LOGGER.error("No prefix found for ENV(PMY)");
		}
		
		if(receiptDetail != null && receiptDetail.getRmAmount() != null) {
			offline.setAmountToPay(Double.toString(receiptDetail.getRmAmount().doubleValue()));
		}else if(Amnt != null){
			offline.setAmountToPay(Double.toString(Amnt));
		}
        offline.setUserId(session.getEmployee().getEmpId());
        offline.setOrgId(session.getOrganisation()
                .getOrgid());
        offline.setLangId(session.getLanguageId());
        offline.setLgIpMac(session.getEmployee().getEmppiservername());
        if ((details != null) && !details.isEmpty()) {
            offline.setFeeIds(details);
        }
        if ((billDetails != null) && !billDetails.isEmpty()) {
            offline.setBillDetIds(billDetails);
        }
        offline.setPropNoConnNoEstateNoL("Property No.");
        offline.setPropNoConnNoEstateNoV(billPayDto.getPropNo());
        offline.setFaYearId(UserSession.getCurrent().getFinYearId());
        if(receiptDetail != null) {
        	Long manualYearId = ApplicationContextProvider.getApplicationContext().getBean(IFinancialYearService.class).getFinanceYearId(receiptDetail.getRmDate());
        	offline.setFaYearId(String.valueOf(manualYearId));
        }else if(manualRecDate != null){
        	Long manualYearId = ApplicationContextProvider.getApplicationContext().getBean(IFinancialYearService.class).getFinanceYearId(manualRecDate);
        	offline.setFaYearId(String.valueOf(manualYearId));
        }
        	LookUp billReceipt = CommonMasterUtility.getValueFromPrefixLookUp("R", "RV", UserSession.getCurrent().getOrganisation());
        	
        offline.setPaymentCategory(billReceipt.getLookUpCode());
        if(org.apache.commons.lang.StringUtils.isNotBlank(paymentCategory)) {
        	offline.setPaymentCategory(paymentCategory);
        }
        offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
        offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
        // offline.setEmailId(billPayDto.get);
        offline.setApplicantName(billPayDto.getOwnerFullName());
        offline.setApplicantFullName(billPayDto.getOwnerFullName());
        offline.setPayeeName(billPayDto.getOwnerFullName());
        offline.setApplNo(billPayDto.getApplNo());
        offline.setApplicantAddress(billPayDto.getAddress());
        offline.setUniquePrimaryId(billPayDto.getPropNo());
        offline.setMobileNumber(billPayDto.getPrimaryOwnerMobNo());
        offline.setServiceId(billPayDto.getServiceId());				
        if(printBillPaymentDescOnBill != null && org.apache.commons.lang.StringUtils.equals(MainetConstants.FlagY, printBillPaymentDescOnBill.getOtherField())) {
        	try {
        		final ServiceMaster service = serviceMaster.getServiceByShortName("PBP",
        				UserSession.getCurrent().getOrganisation().getOrgid());
        		if(service != null) {
        			  offline.setServiceId(service.getSmServiceId());
        		}
        	}catch (Exception exception) {
        		LOGGER.error("No service available for service short code PBP");
			}
        }
        offline.setDeptId(billPayDto.getDeptId());
        offline.setDeptId(deptId);
        offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.REVENUE_BASED);
        offline.setDocumentUploaded(false);
        offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
        offline.setPlotNo(billPayDto.getPlotNo());
        offline.setOfflinePaymentText(CommonMasterUtility
                .getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation())
                .getLookUpCode());
        offline.setManualReceiptNo(null);
        offline.setManualReeiptDate(new Date());
        if(receiptDetail != null) {
        	offline.setManualReeiptDate(receiptDetail.getRmDate());
        }else if(manualRecDate != null) {
        	offline.setManualReeiptDate(manualRecDate);
        }
        if(org.apache.commons.lang.StringUtils.isNotBlank(manualReceiptNo)) {
        	offline.setManualReceiptNo(manualReceiptNo);
        }
        offline.setUsageType(billPayDto.getUsageType1());
        if (billPayDto.getWard1() != null) {
            offline.getDwzDTO().setAreaDivision1(billPayDto.getWard1());
        }
        if (billPayDto.getWard2() != null) {
            offline.getDwzDTO().setAreaDivision2(billPayDto.getWard2());
        }
        if (billPayDto.getWard3() != null) {
            offline.getDwzDTO().setAreaDivision3(billPayDto.getWard3());
        }
        if (billPayDto.getWard4() != null) {
            offline.getDwzDTO().setAreaDivision4(billPayDto.getWard4());
        }
        if (billPayDto.getWard5() != null) {
            offline.getDwzDTO().setAreaDivision5(billPayDto.getWard5());
        }
        // offline.setBillDetails(billPayDto.getBillDetails());
        offline.setReferenceNo(billPayDto.getOldpropno());
        offline.setPdRv(billPayDto.getPdRv());
        if(receiptDetail != null) {
        	offline.setNarration(receiptDetail.getRmNarration());
            offline.setPayModeIn(receiptDetail.getReceiptModeDetail().get(0).getCpdFeemode());
        }else {
        	offline.setNarration("Inserted through property revise bill");
            offline.setPayModeIn(CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.PaymentMode.CASH,
                    PrefixConstants.LookUpPrefix.PAY_AT_COUNTER, UserSession.getCurrent().getOrganisation()).getLookUpId());
        }
		offline.setDeptCode(MainetConstants.Property.PROP_DEPT_SHORT_CODE);
		return offline;
	}
	 
	@RequestMapping(params = "displayYearListPaidAmount", method = RequestMethod.POST)
	public @ResponseBody Map<String, Double> displayYearListPaidAmount(HttpServletRequest httpServletRequest,
			@RequestParam(value = "finYearId") Long finYearId) throws Exception {
		this.getModel().bind(httpServletRequest);
		SelfAssesmentNewModel model = this.getModel();
		if(org.apache.commons.lang.StringUtils.isBlank(model.getProvisionalAssesmentMstDto().getAssNo())) {
			model.getProvisionalAssesmentMstDto().setAssNo(model.getSelfAss());
		}
		Long deptId = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
				.getDepartmentIdByDeptCode("AS");
		List<TbServiceReceiptMasEntity> billPaymentDoneList = receiptRepository.getCollectionDetails(
				model.getProvisionalAssesmentMstDto().getAssNo(), deptId,
				UserSession.getCurrent().getOrganisation().getOrgid());
		Long finanYearId = null;
		if (CollectionUtils.isNotEmpty(billPaymentDoneList)) {
			finanYearId = iFinancialYear.getFinanceYearId(billPaymentDoneList.get(0).getRmDate());
		}
		List<TbBillMas> billMasList = propertyMainBillService.fetchAllBillByPropNo(
				model.getProvisionalAssesmentMstDto().getAssNo(),
				UserSession.getCurrent().getOrganisation().getOrgid());
		List<FinancialYear> financialYearList = iFinancialYear.getFinanceYearListFromGivenDate(
				UserSession.getCurrent().getOrganisation().getOrgid(), finanYearId,
				billMasList.get(billMasList.size() - 1).getBmTodt());
		String financialYear = null;
		Map<String, Double> yearMapPaidBill = new HashMap<>(0);		
		for (final FinancialYear finYearTemp : financialYearList) {
			financialYear = Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate());
			for (TbServiceReceiptMasEntity receiptPaid : billPaymentDoneList) {
				if (org.apache.commons.lang.StringUtils.equals(receiptPaid.getReceiptTypeFlag(),
						MainetConstants.FlagR)) {
					Long financeYearId = iFinancialYear.getFinanceYearId(receiptPaid.getRmDate());
					if (financeYearId.equals(finYearTemp.getFaYear())) {
						Double sameYearAmount = yearMapPaidBill.get(financialYear);
						if (sameYearAmount != null) {
							yearMapPaidBill.put(financialYear,
									receiptPaid.getRmAmount().doubleValue() + sameYearAmount.doubleValue());
						} else {
							yearMapPaidBill.put(financialYear, receiptPaid.getRmAmount().doubleValue());
						}
					}
				}
			}
		}
		model.getFinYearWiseBillMap().putAll(yearMapPaidBill);
		return yearMapPaidBill;
	}
	
	   private boolean saveAdvancePayment(final Long orgId, final Double amount, final String propNo, final Long userId,
	            final Long receiptId) {
	        asExecssAmtService.addEntryInExcessAmt(orgId, propNo, amount, receiptId, userId);
	        return true;
	    }

	   private boolean checkCurrentYearBillPaymentDone(Long finId, String propNo) {
	    	AtomicBoolean payStatus = new AtomicBoolean(true);
	    	if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "BRV")) {
	    		List<TbBillMas> currentYearBillMas = propertyMainBillService.fetchAllBillByPropNoAndCurrentFinId(propNo, UserSession.getCurrent().getOrganisation().getOrgid(), finId);
	    		if(CollectionUtils.isEmpty(currentYearBillMas)) {
	    			payStatus.getAndSet(false);
	    		}
	    	}
	    	
	    	return payStatus.get();
	    	
	    }

}
