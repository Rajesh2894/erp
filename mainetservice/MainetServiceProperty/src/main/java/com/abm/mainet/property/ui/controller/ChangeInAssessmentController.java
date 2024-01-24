package com.abm.mainet.property.ui.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.record.UseSelFSRecord;
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
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.BillReceiptPostingDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.TbBillDet;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.repository.ReceiptRepository;
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
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.property.datamodel.PropertyRateMasterModel;
import com.abm.mainet.property.domain.AsExcessAmtEntity;
import com.abm.mainet.property.domain.ProvisionalBillMasEntity;
import com.abm.mainet.property.dto.ArrayOfDiversionPlotDetails;
import com.abm.mainet.property.dto.ArrayOfKhasraDetails;
import com.abm.mainet.property.dto.ArrayOfPlotDetails;
import com.abm.mainet.property.dto.BillDisplayDto;
import com.abm.mainet.property.dto.BillPaymentDetailDto;
import com.abm.mainet.property.dto.BillPresentAndCalculationDto;
import com.abm.mainet.property.dto.LandTypeApiDetailRequestDto;
import com.abm.mainet.property.dto.PropertyPenltyDto;
import com.abm.mainet.property.dto.PropertyTransferMasterDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentDetailDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentFactorDtlDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.repository.PropertyMainBillRepository;
import com.abm.mainet.property.repository.ProvisionalBillRepository;
import com.abm.mainet.property.service.AsExecssAmtService;
import com.abm.mainet.property.service.AssesmentMastService;
import com.abm.mainet.property.service.DataEntrySuiteService;
import com.abm.mainet.property.service.IProvisionalAssesmentMstService;
import com.abm.mainet.property.service.IProvisionalBillService;
import com.abm.mainet.property.service.MutationService;
import com.abm.mainet.property.service.PrimaryPropertyService;
import com.abm.mainet.property.service.PropertyBRMSService;
import com.abm.mainet.property.service.PropertyBillPaymentService;
import com.abm.mainet.property.service.PropertyMainBillService;
import com.abm.mainet.property.service.PropertyPenltyService;
import com.abm.mainet.property.service.PropertyService;
import com.abm.mainet.property.service.SelfAssessmentService;
import com.abm.mainet.property.ui.model.SelfAssesmentNewModel;
import com.abm.mainet.property.ui.validator.RuleErrorValidator;
import com.abm.mainet.property.ui.validator.SelfAssessmentValidator;
import com.google.common.util.concurrent.AtomicDouble;
import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;

/**
 * @author apeksha.kirdat
 * @since 14 March 2018
 */
@Controller
@RequestMapping("/ChangeInAssessmentForm.html")
public class ChangeInAssessmentController extends AbstractFormController<SelfAssesmentNewModel> {

    private static final Logger LOGGER = Logger.getLogger(ChangeInAssessmentController.class);
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
    private PropertyMainBillService propertyMainBillService;

    @Autowired
    private PropertyBRMSService propertyBRMSService;

    @Autowired
    private DataEntrySuiteService dataEntrySuiteService;

    @Autowired
    private AsExecssAmtService asExecssAmtService;

    @Autowired
    private MutationService mutationService;
    
    @Autowired
    private IWorkflowRequestService workflowReqService;
    
    @Autowired
   	private DepartmentService departmentService;
    
    @Autowired
    private PrimaryPropertyService primaryPropertyService;
    
    @Autowired
    private IProvisionalBillService iProvisionalBillService;
    
    @Autowired
    private BillMasterCommonService billMasterCommonService;
    
    @Autowired
    private TbTaxMasService tbTaxMasService;
    
    @Autowired
    private PropertyMainBillRepository propertyMainBillRepository;
    
    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(HttpServletRequest request) throws Exception {
        this.sessionCleanup(request);
        getModel().bind(request);
        SelfAssesmentNewModel model = this.getModel();
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_ASCL)) {
        	 Long finYearId = iFinancialYear.getFinanceYearId(new Date());
             FinancialYear financialYear = iFinancialYear.getFinincialYearsById(finYearId,
                     UserSession.getCurrent().getOrganisation().getOrgid());
             model.setCurrentFinStartDate(financialYear.getFaFromDate());
             model.setCurrentFinEndDate(financialYear.getFaToDate());
        }
        model.setCommonHelpDocs("ChangeInAssessmentForm.html");
        return new ModelAndView("ChangeInAssessmentSearch", MainetConstants.FORM_NAME, model);

    }

    @RequestMapping(params = "displayChangeInAssessmentForm", method = RequestMethod.POST)
    public ModelAndView formChangeAssessment(HttpServletRequest request) throws Exception {
        this.bindModel(request);
        fileUpload.sessionCleanUpForFileUpload();
        SelfAssesmentNewModel model = this.getModel();
        model.setNoOfDaysEditableFlag(MainetConstants.FlagN);
        model.setNoOfDaysAuthEditFlag(MainetConstants.FlagN);
        ModelAndView mv = null;
        ProvisionalAssesmentMstDto assMst = new ProvisionalAssesmentMstDto();
        LookUp apartmentLevelCalculation = null;
        AtomicBoolean apartmentLevelCalculationAppl = new AtomicBoolean(false);
		try {
			apartmentLevelCalculation = CommonMasterUtility.getValueFromPrefixLookUp("APL", "ALC", UserSession.getCurrent().getOrganisation());
		}catch (Exception exception) {
			
		}
		if(apartmentLevelCalculation != null && org.apache.commons.lang.StringUtils.equals(MainetConstants.FlagY, apartmentLevelCalculation.getOtherField())) {
			apartmentLevelCalculationAppl.set(true);
        }
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
        
        if (StringUtils.isEmpty(proAssMas.getAssOldpropno()) && StringUtils.isEmpty(proAssMas.getAssNo())) {
            model.addValidationError("Property number or Old PID number must not be empty.");
            mv = new ModelAndView("ChangeInAssessmentSearchValidn", MainetConstants.FORM_NAME, this.getModel());
            mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
            return mv;
        } 

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
                    mv = new ModelAndView("ChangeInAssessmentSearchValidn", MainetConstants.FORM_NAME,
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
     
		final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		List<ProvisionalAssesmentMstDto> assList = assesmentMastService
				.getPropDetailFromMainAssByPropNoOrOldPropNo(orgId, proAssMas.getAssNo(), proAssMas.getAssOldpropno());
		if (CollectionUtils.isNotEmpty(assList)) {
			ProvisionalAssesmentMstDto assesMast = assList.get(assList.size() - 1);
			if (assesMast != null
					&& org.apache.commons.lang.StringUtils.equals(assesMast.getIsGroup(), MainetConstants.FlagY) && !apartmentLevelCalculationAppl.get()) {
				getModel().addValidationError(
						ApplicationSession.getInstance().getMessage("property.changeInAssessGrpValidn"));
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
        else {            
            Long finYearId = iFinancialYear.getFinanceYearId(new Date());
            ServiceMaster service = serviceMaster.getServiceByShortName(MainetConstants.Property.serviceShortCode.CIA, orgId);
            Long serviceId = null;
            if (service != null) {
                serviceId = service.getSmServiceId();
            }
			List<ProvisionalAssesmentMstDto> provAssesMastList = new ArrayList<>();
			try {
				if (!StringUtils.isEmpty(proAssMas.getAssNo())) {
					provAssesMastList = provisionalAssesmentMstService.getPropDetailByPropNoOnly(proAssMas.getAssNo());
				} else {
					provAssesMastList = provisionalAssesmentMstService
							.getPropDetailByOldPropNoAndOrgId(proAssMas.getAssOldpropno(), orgId);
				}
			}catch (Exception e) {
				// TODO: handle exception
			}
			

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
                
                //122894 - To check application already in progress
				if (provAssesMstDto!=null && provAssesMstDto.getApmApplicationId() != null) {
					WorkflowRequest workflowRequest = workflowReqService
							.getWorkflowRequestByAppIdOrRefId(provAssesMstDto.getApmApplicationId(), null, orgId);
					if (workflowRequest != null && MainetConstants.WorkFlow.Status.PENDING.equalsIgnoreCase(workflowRequest.getStatus())) {
						getModel().addValidationError("Assessment against property number " + proAssMas.getAssNo()
								+ " is already in progress ");
						ModelAndView modelview = new ModelAndView("ChangeInAssessmentSearchValidn",
								MainetConstants.FORM_NAME, this.getModel());
						modelview.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
								getModel().getBindingResult());
						return modelview;
					}
				}
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
            boolean assessment = false;
            if(assMst!=null) {
            		assessment = selfAssessmentService.CheckForAssesmentFiledForCurrYear(orgId, proAssMas.getAssNo(),
            					 proAssMas.getAssOldpropno(), MainetConstants.STATUS.ACTIVE, finYearId);
            		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)){
                    	if(assMst.getAssPropType1() != null) {
                       	 assMst.setAssPropType1Desc(CommonMasterUtility
                                   .getHierarchicalLookUp(assMst.getAssPropType1(), UserSession.getCurrent().getOrganisation()).getDescLangFirst());
                       }
                       if(assMst.getAssPropType2() != null) {
                      	 assMst.setAssPropType2Desc(CommonMasterUtility
                                  .getHierarchicalLookUp(assMst.getAssPropType2(), UserSession.getCurrent().getOrganisation()).getDescLangFirst());
                      }
                       if(assMst.getAssPropType3() != null) {
                      	 assMst.setAssPropType3Desc(CommonMasterUtility
                                  .getHierarchicalLookUp(assMst.getAssPropType3(), UserSession.getCurrent().getOrganisation()).getDescLangFirst());
                      }
                       if(assMst.getAssPropType4() != null) {
                      	 assMst.setAssPropType4Desc(CommonMasterUtility
                                  .getHierarchicalLookUp(assMst.getAssPropType4(), UserSession.getCurrent().getOrganisation()).getDescLangFirst());
                      }
                       if(assMst.getAssPropType5() != null) {
                      	 assMst.setAssPropType5Desc(CommonMasterUtility
                                  .getHierarchicalLookUp(assMst.getAssPropType5(), UserSession.getCurrent().getOrganisation()).getDescLangFirst());
                      }
                    }
            }
            
            if ((Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "ASCL")) || (org.apache.commons.lang.StringUtils.equals(MainetConstants.FlagY, model.getNoOfDaysEditableFlag())
                    || !assessment)) {
            	 if (assMst != null) {
            	if(checkCurrentYearBillPaymentDone(finYearId, assMst.getAssNo())) {
            		if(checkBillPending(assMst.getAssNo(),assMst.getFlatNo())) {
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

                        /*
                         * model.getDistrict().forEach(dis -> { if (Long.toString(dis.getLookUpId()).equals(assMst.getAssDistrict()))
                         * { assMst.setAssDistrictDesc(dis.getDescLangFirst()); } });
                         */

                        /*
                         * model.getTehsil().forEach(teh -> { if (teh.getLookUpCode().equals(assMst.getAssTahasil())) {
                         * assMst.setAssTahasilDesc(teh.getDescLangFirst()); } });
                         */

                        /*
                         * model.getVillage().forEach(vil -> { if (vil.getLookUpCode().equals(assMst.getTppVillageMauja())) {
                         * assMst.setTppVillageMaujaDesc(vil.getDescLangFirst()); } });
                         */

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
                                            MainetConstants.FlagY))) {
                                financialYearList = iFinancialYear.getFinanceYearListFromGivenDate(orgId,
                                        assMst.getFaYearId(), new Date());
                            } else {
                                financialYearList = iFinancialYear.getFinanceYearListAfterGivenDate(
                                        UserSession.getCurrent().getOrganisation().getOrgid(), assMst.getFaYearId(),
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
                       	 if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "DBA")) {
                       		 financialYearList = new ArrayList<FinancialYear>();
                       		 List<Long> finIdList = new ArrayList<Long>();
                       		 finIdList.add(finYearId);
                       		 financialYearList = iFinancialYear.getFinYearByFinIdList(finIdList);
                       		 
                       	 }
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
                        assMst.setReviseAssessmentDate(null);
                        model.setProvisionalAssesmentMstDto(assMst);
                        model.setProvAssMstDtoList(provAssesMastList);
                        model.setAuthComBillList(billMasList);
                        model.setOldProAssMstDtoList(provAssesMastList);
                        model.setIntgrtionWithBP(CommonMasterUtility
                                .getDefaultValueByOrg(MainetConstants.Property.IBP, UserSession.getCurrent().getOrganisation())
                                .getLookUpCode());
                        String lookUp = CommonMasterUtility.getDefaultValueByOrg(MainetConstants.Property.propPref.APP,
                                UserSession.getCurrent().getOrganisation()).getLookUpCode();
                        model.getProvisionalAssesmentMstDto().setPartialAdvancePayStatus(lookUp);
                        return new ModelAndView("ChangeInAssessmentForm", MainetConstants.FORM_NAME, model);
            		}else {
            			model.addValidationError("Please clear the dues.");
                        mv = new ModelAndView("ChangeInAssessmentSearchValidn", MainetConstants.FORM_NAME, this.getModel());
                        mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
                        return mv;
            		}
            		
                 
                     } else {
                    	 model.addValidationError("Please generate current year bill first");
                         mv = new ModelAndView("ChangeInAssessmentSearchValidn", MainetConstants.FORM_NAME, this.getModel());
                         mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
                         return mv;
                     }
            	}else {
            		  model.addValidationError("Please enter valid property number or Old PID number.");
                      mv = new ModelAndView("ChangeInAssessmentSearchValidn", MainetConstants.FORM_NAME, this.getModel());
                      mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
                      return mv;
            	}
               
            } else {
                model.addValidationError(
                        "Assessment already done for entered property no or Old PID for current financial year.");
                mv = new ModelAndView("ChangeInAssessmentSearchValidn", MainetConstants.FORM_NAME, this.getModel());
                mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
                return mv;
            }

        }
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

    private void setFactorMapOnEdit(List<ProvisionalAssesmentFactorDtlDto> provAsseFactDtlDto) {
        Map<String, List<ProvisionalAssesmentFactorDtlDto>> factorMap = new HashMap<>();
        provAsseFactDtlDto.forEach(fact -> {
            List<ProvisionalAssesmentFactorDtlDto> factList1 = factorMap.get(fact.getAssfFactorId().toString());
            if (factList1 != null && !factList1.isEmpty()) {
                factList1.add(fact);
            } else {
                factList1 = new ArrayList<>();
                factList1.add(fact);
            }
            boolean result = this.getModel().getUnitNoList().contains(fact.getUnitNoFact());
            if (!result) {
                this.getModel().getUnitNoList().add(fact.getUnitNoFact());
            }
            factorMap.put(fact.getAssfFactorId().toString(), factList1);
        });

        List<LookUp> lookupList = CommonMasterUtility.lookUpListByPrefix(MainetConstants.Property.propPref.FCT,
                UserSession.getCurrent().getOrganisation().getOrgid());
        lookupList.forEach(fact -> {
            List<ProvisionalAssesmentFactorDtlDto> factList = factorMap.get(Long.toString(fact.getLookUpId()));
            if (factList == null || factList.isEmpty()) {
                factorMap.put(Long.toString(fact.getLookUpId()), null);
            }
        });
        this.getModel().setDisplayfactorMap(factorMap);
    }

    @RequestMapping(params = "displayAdvanceSearchForm", method = RequestMethod.POST)
    public ModelAndView displayAdvanceSearchForm(HttpServletRequest request) {
        getModel().bind(request);
        SelfAssesmentNewModel model = this.getModel();
        return new ModelAndView("AdvanceSearchForm", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "proceed", method = RequestMethod.POST)
    public ModelAndView confirmToProceed(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        this.bindModel(httpServletRequest);
        SelfAssesmentNewModel model = this.getModel();
        ProvisionalAssesmentMstDto assMstDto = model.getProvisionalAssesmentMstDto();
        Date firstAssesmentDate = assMstDto.getProvisionalAssesmentDetailDtoList().get(0).getFirstAssesmentDate();
        Date assesmentDate = assMstDto.getProvisionalAssesmentDetailDtoList().get(0).getAssdAssesmentDate();
        assMstDto.getProvisionalAssesmentDetailDtoList().forEach(unitDetails -> {
            unitDetails.setFirstAssesmentDate(firstAssesmentDate);
            unitDetails.setLastAssesmentDate(assesmentDate);
            if (unitDetails.getLastAssesmentDate() != null) {
                unitDetails.setLastAssesmentStringDate(new SimpleDateFormat(MainetConstants.DATE_FRMAT)
                        .format(unitDetails.getLastAssesmentDate()));
            }

        });
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
		ServiceMaster serviceMas = serviceMaster.getServiceByShortName(MainetConstants.Property.serviceShortCode.CIA,
				model.getOrgId());
		model.setServiceShortCode(serviceMas.getSmShortdesc());
		if (serviceMas != null && serviceMas.getSmFeesSchedule() != null && serviceMas.getSmFeesSchedule() != 0) {
			getCharges(model);
		}
        // D#18545 Error MSG for Rule found or not
        model.validateBean(model.getProvisionalAssesmentMstDto(), RuleErrorValidator.class);
        if (model.hasValidationErrors()) {
            return defaultMyResult();
        }
        return new ModelAndView("SelfAssessmentView", MainetConstants.FORM_NAME, model);
    }

    private void getCharges(SelfAssesmentNewModel model) {
        Date manualDate = null;
        Date advanceManualDate = null;
        ProvisionalAssesmentMstDto dto = model.getProvisionalAssesmentMstDto();
        TbServiceReceiptMasEntity manualReceiptMas = null;
        if (dto.getAssNo() != null && !dto.getAssNo().isEmpty()) {
            List<AsExcessAmtEntity> excessAmtEntByPropNoList = ApplicationContextProvider.getApplicationContext()
                    .getBean(AsExecssAmtService.class)
                    .getExcessAmtEntByPropNo(dto.getAssNo(), UserSession.getCurrent().getOrganisation().getOrgid());
            if (CollectionUtils.isNotEmpty(excessAmtEntByPropNoList)) {
                AsExcessAmtEntity asExcessAmtEntity = excessAmtEntByPropNoList.get(excessAmtEntByPropNoList.size() - 1);
                manualReceiptMas = ApplicationContextProvider.getApplicationContext().getBean(ReceiptRepository.class)
                        .getmanualReceiptAdvanceAmountByAddRefNo(asExcessAmtEntity.getRmRcptid(), dto.getAssNo(),
                                UserSession.getCurrent().getOrganisation().getOrgid());
                if (manualReceiptMas != null) {
                    advanceManualDate = manualReceiptMas.getRmDate();
                }
            }
        }
        model.setManualReeiptDate(advanceManualDate);
        model.setObjectionFlag(MainetConstants.FlagY);
        if (model.getAssesmentManualDate() != null) {
            manualDate = model.getAssesmentManualDate();
        } else {
            manualDate = advanceManualDate;
        }
        // propertyService.setWardZoneDetailByLocId(dto, model.getDeptId(), dto.getLocId());
        selfAssessmentService.setFactorMappingToAssDto(model.getProvAsseFactDtlDto(), dto);
        BillDisplayDto advanceAmt = asExecssAmtService.getBillDisplayDtoWithAdvanceAmt(dto.getAssNo(), dto.getOrgId(),null);
        // setFactorMap(dto);
        Map<Long, Double> finAlvMap = new HashMap<>();
		Map<Long, Double> finRvMap = new HashMap<>();
		Map<Long, Double> finCvmap = new HashMap<>();
		Map<Long, Double> finStdRate = new HashMap<>();
        Map<Long, BillDisplayDto> taxWiseRebate = new TreeMap<>();// map<TxaId,BillDisplayDto>
        Long finYearId = iFinancialYear.getFinanceYearId(new Date());
        List<TbBillMas> currentCalculatedBillList = new ArrayList<TbBillMas>();
        List<Long> existingfinYearList = new ArrayList<>(0);
        
        List<BillReceiptPostingDTO> reabteRecDtoListForPreviousYearChange = null;
        List<ProvisionalAssesmentDetailDto> nonProvDetailList = new ArrayList<ProvisionalAssesmentDetailDto>();
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "DBA")) {
        	Map<Date, List<BillPresentAndCalculationDto>> schWiseChargeMap = propertyBRMSService.fetchCharges(dto, model.getDeptId(),
                    taxWiseRebate, model.getFinYearList(), manualReceiptMas, model.getAssType(), model.getAssesmentManualDate(),null);
           currentCalculatedBillList = propertyService.generateNewBill(schWiseChargeMap,
                    UserSession.getCurrent().getOrganisation(), model.getDeptId(), null,null);
          
			createMapForALV(dto, finAlvMap, finRvMap, finCvmap, finStdRate);
			reabteRecDtoListForPreviousYearChange = selfAssessmentService.knowkOffDemandLevelRebateAndExemption(currentCalculatedBillList,
                    schWiseChargeMap,
                    UserSession.getCurrent().getOrganisation(), taxWiseRebate);
            propertyService.interestCalculationWithoutBRMSCall(UserSession.getCurrent().getOrganisation(), model.getDeptId(),
            		currentCalculatedBillList,
                    MainetConstants.Property.INT_RECAL_NO);
            existingfinYearList.addAll(model.getFinYearList());
            model.getFinYearList().clear();
            model.getFinYearList().add(finYearId);
            List<ProvisionalAssesmentDetailDto> provDetailList = dto.getProvisionalAssesmentDetailDtoList().stream().filter(det -> det.getFaYearId().equals(finYearId)).collect(Collectors.toList());
            List<ProvisionalAssesmentDetailDto> nonCurrentYearDetails = dto.getProvisionalAssesmentDetailDtoList().stream().filter(det -> !det.getFaYearId().equals(finYearId)).collect(Collectors.toList());
            nonProvDetailList.addAll(nonCurrentYearDetails);
            dto.getProvisionalAssesmentDetailDtoList().clear();
            dto.getProvisionalAssesmentDetailDtoList().addAll(provDetailList);
        }
        taxWiseRebate = new TreeMap<>();// map<TxaId,BillDisplayDto>
        Map<Date, List<BillPresentAndCalculationDto>> schWiseChargeMap = propertyBRMSService.fetchCharges(dto, model.getDeptId(),
                taxWiseRebate, model.getFinYearList(), manualReceiptMas, model.getAssType(), model.getAssesmentManualDate(),null);
        List<TbBillMas> billMasList = propertyService.generateNewBill(schWiseChargeMap,
                UserSession.getCurrent().getOrganisation(), model.getDeptId(), null,null);
        List<BillReceiptPostingDTO> reabteRecDtoList = selfAssessmentService.knowkOffDemandLevelRebateAndExemption(billMasList,
                schWiseChargeMap,
                UserSession.getCurrent().getOrganisation(), taxWiseRebate);
        model.setReabteRecDtoList(reabteRecDtoList);
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "DBA")) {
        	model.setReabteRecDtoList(reabteRecDtoListForPreviousYearChange);
        }
        List<BillDisplayDto> rebateDtoList = new ArrayList<BillDisplayDto>();
        // if(org.apache.commons.lang.StringUtils.isNotBlank(model.getNoOfDaysEditableFlag()) &&
        // !org.apache.commons.lang.StringUtils.equals(model.getNoOfDaysEditableFlag(), MainetConstants.FlagY)) {
        rebateDtoList = propertyBRMSService.fetchEarlyPayRebateRate(billMasList,
                UserSession.getCurrent().getOrganisation(), model.getDeptId(), manualDate,null);
        // }
        List<TbBillMas> billMasArrears = propertyMainBillService.fetchNotPaidBillForAssessment(dto.getAssNo(),
                dto.getOrgId());
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "DBA")) {
        	Optional<TbBillMas> findFirst = billMasArrears.stream().filter(billMas -> billMas.getBmYear().equals(finYearId)).findFirst();
        	if(!findFirst.isPresent()) {
        		List<TbBillMas> currBillList = propertyMainBillService.fetchAllBillByPropNoAndCurrentFinId(dto.getAssNo(), dto.getOrgId(), finYearId);
        		billMasArrears.addAll(currBillList);
        	}
        }
       
        List<TbBillMas> billList = billMasList;
        if (billMasArrears != null && !billMasArrears.isEmpty()) {
        	if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)) {
        		propertyBRMSService.fetchInterstRate(billMasArrears, UserSession.getCurrent().getOrganisation(), model.getDeptId());
        		billMasterCommonService.calculateInterestForPrayagRaj(billMasArrears, UserSession.getCurrent().getOrganisation(), model.getDeptId(), MainetConstants.FlagY, null, UserSession.getCurrent().getEmployee().getEmpId(), billMasArrears, MainetConstants.FlagY);
        	}else {
        		propertyService.interestCalculation(UserSession.getCurrent().getOrganisation(), model.getDeptId(), billMasArrears,
                        MainetConstants.Property.INT_RECAL_YES);
        	}
            billList = propertyService.getBillListWithMergingOfOldAndNewBill(billMasArrears, billMasList);
            billList.sort(Comparator.comparing(TbBillMas::getBmFromdt));
            if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "DBA")) {
            	billMasterCommonService.updateArrearInCurrentBillsForNewBillGenertaionForChangInAss(billList);
            }else {
            	propertyService.updateArrearInCurrentBills(billList);
            }
        }
        double ajustedAmt = 0;
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "DBA")) {
        	if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "PSCL")) {
        		updateBillMas(billList, billMasArrears, finYearId, dto.getAssNo(),currentCalculatedBillList, taxWiseRebate);
        	}else {
        		ajustedAmt = updateBillMasForChangeInAss(billList, billMasArrears, finYearId, dto.getAssNo(),currentCalculatedBillList, taxWiseRebate, finAlvMap, finRvMap, finCvmap, finStdRate,advanceAmt,model.getDeptId());
        	}
        	model.setAdjustedAdvanceAmnt(ajustedAmt);
        	dto.getProvisionalAssesmentDetailDtoList().addAll(nonProvDetailList);
        	dto.getProvisionalAssesmentDetailDtoList().sort(Comparator.comparing(ProvisionalAssesmentDetailDto::getFaYearId));
        	model.getFinYearList().clear();
            model.getFinYearList().addAll(existingfinYearList);
        	//asExecssAmtService.updateExecssAmtByAdjustedAmt(dto.getAssNo(), UserSession.getCurrent().getOrganisation().getOrgid(), ajustedAmt, UserSession.getCurrent().getEmployee().getEmpId(), model.getClientIpAddress(), null);
        }
        propertyService.updateARVRVInBill(dto, billMasList);
        if(!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "DBA")) {
        	 propertyService.interestCalculationWithoutBRMSCall(UserSession.getCurrent().getOrganisation(), model.getDeptId(),
                     billList,
                     MainetConstants.Property.INT_RECAL_NO);

        }
        propertyService.taxCarryForward(billList, model.getOrgId());
        BillDisplayDto surCharge = null;
        if (org.apache.commons.lang.StringUtils.isNotBlank(model.getNoOfDaysEditableFlag())
                && org.apache.commons.lang.StringUtils.equals(model.getNoOfDaysEditableFlag(), MainetConstants.FlagY)) {
            surCharge = propertyService.calculateSurchargeAtAuthorizationEdit(UserSession.getCurrent().getOrganisation(),
                    model.getDeptId(), billList, dto.getAssNo(),
                    MainetConstants.Property.SURCHARGE, finYearId);
        } else {
            surCharge = propertyService.calculateSurcharge(UserSession.getCurrent().getOrganisation(),
                    model.getDeptId(), billList, dto.getAssNo(),
                    MainetConstants.Property.SURCHARGE, finYearId, manualDate);
        }

        
        double previousAdvanceAmount = 0;
        if (advanceAmt != null) {
            previousAdvanceAmount = advanceAmt.getTotalTaxAmt().doubleValue();
        }
        LookUp billDeletionInactive = null;
        try {
            billDeletionInactive = CommonMasterUtility.getValueFromPrefixLookUp("BDI", "ENV",
                    UserSession.getCurrent().getOrganisation());
        } catch (Exception e) {

        }

        BigDecimal billAmountPaid = new BigDecimal(0);
        if (org.apache.commons.lang.StringUtils.isNotBlank(model.getNoOfDaysEditableFlag())
                && org.apache.commons.lang.StringUtils.equals(model.getNoOfDaysEditableFlag(), MainetConstants.FlagY)) {
            rebateDtoList = propertyBRMSService.fetchEarlyPayRebateRate(billList,
                    UserSession.getCurrent().getOrganisation(), model.getDeptId(), manualDate,null);
            billAmountPaid = ApplicationContextProvider.getApplicationContext().getBean(IReceiptEntryService.class)
                    .getActicePaidAmountByAppNo(UserSession.getCurrent().getOrganisation().getOrgid(),
                            dto.getApmApplicationId(), model.getDeptId());
            if ((billDeletionInactive != null
                    && org.apache.commons.lang.StringUtils.isNotBlank(billDeletionInactive.getOtherField())
                    && org.apache.commons.lang.StringUtils.equals(billDeletionInactive.getOtherField(), MainetConstants.FlagY))) {
                billAmountPaid = ApplicationContextProvider.getApplicationContext().getBean(IReceiptEntryService.class)
                        .getPaidAmountByAdditionalRefNoIncRebate(UserSession.getCurrent().getOrganisation().getOrgid(),
                                String.valueOf(dto.getAssNo()), model.getDeptId());
            }
            /* Surcharge / penalty under editable form code starts */
            PropertyPenltyDto currFinYearPenaltyDto = ApplicationContextProvider.getApplicationContext()
                    .getBean(PropertyPenltyService.class)
                    .calculateExistingSurcharge(dto.getAssNo(), finYearId, model.getOrgId());
            if (currFinYearPenaltyDto != null) {
                model.setPreviousSurcharge(currFinYearPenaltyDto.getActualAmount());
            }
            if (currFinYearPenaltyDto != null && surCharge != null) {
                double pendingAmount = surCharge.getTotalTaxAmt().doubleValue() - currFinYearPenaltyDto.getActualAmount();
                currFinYearPenaltyDto.setActualAmount(surCharge.getTotalTaxAmt().doubleValue());
                if (pendingAmount > 0) {
                    currFinYearPenaltyDto.setPendingAmount(pendingAmount);
                }
                model.setPropPenaltyDto(currFinYearPenaltyDto);
            } else if (currFinYearPenaltyDto != null && surCharge == null) {
                currFinYearPenaltyDto.setActualAmount(0.0);
                currFinYearPenaltyDto.setPendingAmount(0.0);
                model.setPropPenaltyDto(currFinYearPenaltyDto);
            }
            model.setSurCharge(surCharge);
            model.setAdvanceAmount(0);
            model.setAdvanceAmt(null);

            /* Surcharge / penalty under editable form code ends */

            /* Advance payment under editable form code starts */
            if (advanceAmt != null && advanceAmt.getCurrentYearTaxAmt().compareTo(BigDecimal.ZERO) != 0) {
                billAmountPaid = billAmountPaid.subtract(advanceAmt.getCurrentYearTaxAmt());
            }
            double excessAmount = 0;
            if (billAmountPaid != null) {
                excessAmount = billAmountPaid.doubleValue() - billList.get(billList.size() - 1).getBmTotalOutstanding();
            }

            if (excessAmount > 0) {
                double totalRebateAmount = 0.0;
                if (CollectionUtils.isNotEmpty(rebateDtoList)) {
                    for (BillDisplayDto rebateDto : rebateDtoList) {
                        totalRebateAmount = totalRebateAmount + rebateDto.getTotalTaxAmt().doubleValue();
                    }
                }
                if (advanceAmt == null) {
                    advanceAmt = new BillDisplayDto();
                    Long advanceTaxId = ApplicationContextProvider.getApplicationContext()
                            .getBean(BillMasterCommonService.class)
                            .getAdvanceTaxId(UserSession.getCurrent().getOrganisation().getOrgid(),
                                    MainetConstants.Property.PROP_DEPT_SHORT_CODE, null);

                    if (surCharge != null) {
                        BigDecimal advanceAmount = new BigDecimal(excessAmount - surCharge.getTotalTaxAmt().doubleValue());
                        if (advanceAmount.doubleValue() > 0) {
                            advanceAmt.setCurrentYearTaxAmt(
                                    new BigDecimal(excessAmount - surCharge.getTotalTaxAmt().doubleValue()));
                            advanceAmt.setTotalTaxAmt(new BigDecimal(excessAmount - surCharge.getTotalTaxAmt().doubleValue()));
                        }
                        if (CollectionUtils.isNotEmpty(rebateDtoList)) {
                            advanceAmt.setCurrentYearTaxAmt(
                                    new BigDecimal(advanceAmt.getCurrentYearTaxAmt().doubleValue() + totalRebateAmount));
                            advanceAmt.setTotalTaxAmt(
                                    new BigDecimal(advanceAmt.getTotalTaxAmt().doubleValue() + totalRebateAmount));
                        }
                    } else {
                        advanceAmt.setCurrentYearTaxAmt(new BigDecimal(excessAmount));
                        advanceAmt.setTotalTaxAmt(new BigDecimal(excessAmount));
                        if (CollectionUtils.isNotEmpty(rebateDtoList)) {
                            advanceAmt.setCurrentYearTaxAmt(
                                    new BigDecimal(advanceAmt.getCurrentYearTaxAmt().doubleValue() + totalRebateAmount));
                            advanceAmt.setTotalTaxAmt(
                                    new BigDecimal(advanceAmt.getTotalTaxAmt().doubleValue() + totalRebateAmount));
                        }

                    }
                    advanceAmt.setTaxAmt(excessAmount);
                    advanceAmt.setTaxId(advanceTaxId);
                    TbTaxMas taxMas = ApplicationContextProvider.getApplicationContext().getBean(TbTaxMasService.class)
                            .findTaxByTaxIdAndOrgId(advanceTaxId,
                                    UserSession.getCurrent().getOrganisation().getOrgid());
                    advanceAmt.setTaxCategoryId(taxMas.getTaxCategory1());
                    advanceAmt.setDisplaySeq(taxMas.getTaxDisplaySeq());
                    advanceAmt.setTaxDesc(taxMas.getTaxDesc());
                    if (advanceAmt != null && advanceAmt.getTotalTaxAmt() != null) {
                        model.setAdvanceAmount(advanceAmt.getTotalTaxAmt().doubleValue());
                    } else {
                        advanceAmt = null;
                    }

                } else {
                    double advanceAmtBeforeKnockOff = advanceAmt.getCurrentYearTaxAmt().doubleValue();
                    if (surCharge != null) {
                        advanceAmt.setCurrentYearTaxAmt(
                                new BigDecimal((advanceAmt.getCurrentYearTaxAmt().doubleValue() + excessAmount)
                                        - surCharge.getTotalTaxAmt().doubleValue()));
                        advanceAmt.setTotalTaxAmt(
                                new BigDecimal((advanceAmt.getTotalTaxAmt().doubleValue() + excessAmount)
                                        - surCharge.getTotalTaxAmt().doubleValue()));
                    } else {
                        advanceAmt.setCurrentYearTaxAmt(
                                new BigDecimal(advanceAmt.getCurrentYearTaxAmt().doubleValue() + excessAmount));
                        advanceAmt.setTotalTaxAmt(
                                new BigDecimal(advanceAmt.getTotalTaxAmt().doubleValue() + excessAmount));
                    }
                    if (CollectionUtils.isNotEmpty(rebateDtoList)) {
                        advanceAmt.setTotalTaxAmt(new BigDecimal(advanceAmt.getTotalTaxAmt().doubleValue() + totalRebateAmount));
                        advanceAmt.setCurrentYearTaxAmt(
                                new BigDecimal(advanceAmt.getCurrentYearTaxAmt().doubleValue() + totalRebateAmount));
                    }
                    model.setAdvanceAmount(advanceAmt.getTotalTaxAmt().doubleValue() - advanceAmtBeforeKnockOff);

                }
                /* Advance payment under editable form code ends */
            }

        }
        if (surCharge != null && surCharge.getTotalTaxAmt() != null) {
            model.getProvisionalAssesmentMstDto().setTotalSubcharge(surCharge.getTotalTaxAmt().doubleValue());
        }

        if (billDeletionInactive != null
                && org.apache.commons.lang.StringUtils.isNotBlank(billDeletionInactive.getOtherField())
                && org.apache.commons.lang.StringUtils.equals(billDeletionInactive.getOtherField(),
                        MainetConstants.FlagY)) {

            if (billAmountPaid != null && billAmountPaid.doubleValue() > 0) {
                rebateDtoList = null;
            }
        }
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

        if (advanceManualDate != null && model.getEarlyPayRebate() != null
                && model.getEarlyPayRebate().getCurrentYearTaxAmt() != null
                && advanceAmt.getCurrentYearTaxAmt()
                        .doubleValue() < (billList.get(billList.size() - 1).getBmTotalOutstanding()
                                - model.getEarlyPayRebate().getCurrentYearTaxAmt().doubleValue())) {
            rebateDtoList = null;
        }

        List<BillDisplayDto> otherTaxesDisDtoList = new ArrayList<>();
        if (rebateDtoList != null && !rebateDtoList.isEmpty()) {
            otherTaxesDisDtoList.addAll(rebateDtoList);
        }
        if (surCharge != null) {
            otherTaxesDisDtoList.add(surCharge);
        }
        if (advanceAmt != null && advanceAmt.getCurrentYearTaxAmt().doubleValue() <= 0) {
            advanceAmt = null;
        }
        if (advanceAmt != null) {
        	advanceAmt.setCurrentYearTaxAmt(new BigDecimal(advanceAmt.getCurrentYearTaxAmt().doubleValue()-ajustedAmt));
        	advanceAmt.setTotalTaxAmt(new BigDecimal(advanceAmt.getTotalTaxAmt().doubleValue()-ajustedAmt));
            model.setAdvanceAmt(advanceAmt);
            otherTaxesDisDtoList.add(advanceAmt);
        }

        Map<String, List<BillDisplayDto>> presentMap = selfAssessmentService.getDisplayMapOfTaxes(billList,
                UserSession.getCurrent().getOrganisation(), model.getDeptId(),
                taxWiseRebate, otherTaxesDisDtoList);
        dto.setBillTotalAmt(propertyService.getTotalActualAmount(billList, rebateDtoList, taxWiseRebate, surCharge, advanceAmt));
        double lastBillPaidAmnt = 0;
        if (org.apache.commons.lang.StringUtils.equals(model.getNoOfDaysEditableFlag(), "Y")) {
            if (billAmountPaid != null) {
                lastBillPaidAmnt = billAmountPaid.doubleValue();
            }
            double totalAmntPaid = dto.getBillTotalAmt() - lastBillPaidAmnt;
            model.setLastBillAmountPaid(lastBillPaidAmnt + previousAdvanceAmount);
            if (totalAmntPaid > 0) {
                dto.setBillTotalAmt(totalAmntPaid);

            } else {
                dto.setBillTotalAmt(0.00);
                final Map<Long, Double> details = new HashMap<>(0);
                final Map<Long, Long> billDetails = new HashMap<>(0);
                ApplicationContextProvider.getApplicationContext().getBean(BillMasterCommonService.class).updateBillData(
                        billList, lastBillPaidAmnt, details, billDetails,
                        UserSession.getCurrent().getOrganisation(), null, null);
                if (totalAmntPaid < 0) {
                    totalAmntPaid = -totalAmntPaid;
                    if (model.getAdvanceAmount() == 0) {
                        if (advanceAmt != null) {
                            model.setAdvanceAmount(totalAmntPaid - advanceAmt.getCurrentYearTaxAmt().doubleValue());
                            advanceAmt.setCurrentYearTaxAmt(new BigDecimal(totalAmntPaid));
                            advanceAmt.setTotalTaxAmt(new BigDecimal(totalAmntPaid));
                        } else {
                            advanceAmt = new BillDisplayDto();
                            List<BillDisplayDto> displayList = new ArrayList<BillDisplayDto>();
                            Long advanceTaxId = ApplicationContextProvider.getApplicationContext()
                                    .getBean(BillMasterCommonService.class)
                                    .getAdvanceTaxId(UserSession.getCurrent().getOrganisation().getOrgid(),
                                            MainetConstants.Property.PROP_DEPT_SHORT_CODE, null);
                            advanceAmt.setCurrentYearTaxAmt(new BigDecimal(totalAmntPaid));
                            advanceAmt.setTotalTaxAmt(new BigDecimal(totalAmntPaid));
                            advanceAmt.setTaxAmt(totalAmntPaid);
                            advanceAmt.setTaxId(advanceTaxId);
                            TbTaxMas taxMas = ApplicationContextProvider.getApplicationContext().getBean(TbTaxMasService.class)
                                    .findTaxByTaxIdAndOrgId(advanceTaxId,
                                            UserSession.getCurrent().getOrganisation().getOrgid());
                            advanceAmt.setTaxCategoryId(taxMas.getTaxCategory1());
                            advanceAmt.setDisplaySeq(taxMas.getTaxDisplaySeq());
                            advanceAmt.setTaxDesc(taxMas.getTaxDesc());
                            model.setAdvanceAmount(advanceAmt.getTotalTaxAmt().doubleValue());
                            displayList.add(advanceAmt);
                            presentMap.put(taxMas.getTaxDesc(), displayList);
                        }

                    }

                }
            }
        }
        if (surCharge != null && model.getProvisionalAssesmentMstDto().getTotalSubcharge() > dto.getBillTotalAmt()) {
            model.getProvisionalAssesmentMstDto().setTotalSubcharge(0.0);
        }
        ListIterator<TbBillMas> listIterator = billList.listIterator();
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)) {
        	while(listIterator.hasNext()) {
            	TbBillMas next = listIterator.next();
            	next.setBmGenDes(MainetConstants.N_FLAG);
            }
        }
        model.getProvisionalAssesmentMstDto().setBillPaidAmt(lastBillPaidAmnt);
        model.setBillMasList(billList);
        model.setDisplayMap(presentMap);
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "ASCL")) {
        	model.setAdvanceAmt(null);
        }
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "DBA")) {
        	model.getFinYearList().clear();
            model.getFinYearList().addAll(existingfinYearList);
        }
    }

	private void createMapForALV(ProvisionalAssesmentMstDto dto, Map<Long, Double> finAlvMap,
			Map<Long, Double> finRvMap, Map<Long, Double> finCvmap, Map<Long, Double> finStdRate) {
		dto.getProvisionalAssesmentDetailDtoList().forEach(detail -> {
			if (!finAlvMap.containsKey(detail.getFaYearId())) {
				finAlvMap.put(detail.getFaYearId(), detail.getAssdAlv());
				finRvMap.put(detail.getFaYearId(), detail.getAssdRv());
				finCvmap.put(detail.getFaYearId(), detail.getAssdCv());
				finStdRate.put(detail.getFaYearId(), detail.getAssdStdRate());
			} else {
				finAlvMap.put(detail.getFaYearId(),
						finAlvMap.get(detail.getFaYearId()) + detail.getAssdAlv());
				finRvMap.put(detail.getFaYearId(), finRvMap.get(detail.getFaYearId()) + detail.getAssdRv());
				finCvmap.put(detail.getFaYearId(), finCvmap.get(detail.getFaYearId()) + detail.getAssdCv());
			}
		});
	}

    @RequestMapping(params = "getCheckList", method = RequestMethod.POST)
    public ModelAndView getCheckList(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        SelfAssesmentNewModel model = this.getModel();
        ProvisionalAssesmentMstDto dto = model.getProvisionalAssesmentMstDto();
        Date firstAssesmentDate = dto.getProvisionalAssesmentDetailDtoList().get(0).getFirstAssesmentDate();
        Date assesmentDate = dto.getProvisionalAssesmentDetailDtoList().get(0).getAssdAssesmentDate();
        dto.getProvisionalAssesmentDetailDtoList().forEach(unitDetails -> {
            unitDetails.setFirstAssesmentDate(firstAssesmentDate);
            unitDetails.setLastAssesmentDate(assesmentDate);
            if (unitDetails.getLastAssesmentDate() != null) {
                unitDetails.setLastAssesmentStringDate(new SimpleDateFormat(MainetConstants.DATE_FRMAT)
                        .format(unitDetails.getLastAssesmentDate()));
            }
        });
        
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_ASCL)) {
        	//Long yearAcquisition = getyearOfAcquisition(model.getProvisionalAssesmentMstDto().getReviseAssessmentDate());
        	
        }

        if (model.hasValidationErrors()) {
			return defaultMyResult();
		}
        List<ProvisionalAssesmentFactorDtlDto> factDto = model.getProvAsseFactDtlDto();
        // Defect_no 70835 Selections are not visible after proceeding for change in assessments
        setFactorMapOnEdit(model.getProvAsseFactDtlDto());
        dto.setOrgId(model.getOrgId());
        dto.setSmServiceId(model.getServiceId());
        
        LookUp checkListApplLookUp = null;
		List<DocumentDetailsVO> docs = null;
        ServiceMaster serviceMas = serviceMaster.getServiceByShortName(MainetConstants.Property.serviceShortCode.CIA, model.getOrgId());
        model.setServiceShortCode(serviceMas.getSmShortdesc());
		if (serviceMas != null) {
			checkListApplLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(serviceMas.getSmChklstVerify(),
					ApplicationContextProvider.getApplicationContext().getBean(IOrganisationService.class)
							.getOrganisationById(model.getOrgId()));
			if (org.apache.commons.lang.StringUtils.equalsIgnoreCase(checkListApplLookUp.getLookUpCode(),
					MainetConstants.FlagA)) {
				docs = selfAssessmentService.fetchCheckList(dto, factDto);
				if (docs == null || docs.isEmpty()) {
					model.addValidationError(
							ApplicationSession.getInstance().getMessage("property.changeChecklistVerify"));
					return defaultMyResult();
				}
			}			
		}
        docs = fileUpload.prepareFileUpload(docs);
        dto.setDocs(docs);
        model.setCheckList(docs);
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
        this.getModel().setDropDownValues(UserSession.getCurrent().getOrganisation());
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
             
		if (CollectionUtils.isNotEmpty(docs)) {
			getModel().setSelfAss(MainetConstants.Property.CHN_IN_ASESS);
			return defaultMyResult();
		} else if ((docs == null || docs.isEmpty()) && serviceMas != null && serviceMas.getSmFeesSchedule() != null
				&& serviceMas.getSmFeesSchedule() != 0) {
			getCharges(model);
		}
		
		// D#18545 Error MSG for Rule found or not
		model.validateBean(model.getProvisionalAssesmentMstDto(), RuleErrorValidator.class);
		if (model.hasValidationErrors()) {
			return defaultMyResult();
		}       
        // return defaultMyResult();
        return new ModelAndView("SelfAssessmentView", MainetConstants.FORM_NAME, model);
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

    private void setCommonFields(SelfAssesmentNewModel model) {
        getModel().setAssType(MainetConstants.Property.CHN_IN_ASESS);
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        ServiceMaster service = serviceMaster.getServiceByShortName(MainetConstants.Property.serviceShortCode.CIA, orgId);
        model.setOrgId(orgId);
        model.setServiceMaster(service);
        model.setDeptId(service.getTbDepartment().getDpDeptid());
        model.setServiceId(service.getSmServiceId());

    }

    @RequestMapping(params = "editChangeAssForm", method = RequestMethod.POST)
    public ModelAndView editForm(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        getModel().setSelfAss(null);
        SelfAssesmentNewModel model = this.getModel();
        model.setFormType(MainetConstants.FlagE);
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
        /*
         * model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().forEach(det -> {
         * det.getProvisionalAssesmentFactorDtlDtoList().clear(); });
         */
        setFactorMapOnEdit(model.getProvAsseFactDtlDto());
        return new ModelAndView("ChangeInAssessmentForm", MainetConstants.FORM_NAME, model);
    }

    @ResponseBody
    @RequestMapping(params = "saveNoOfDaysEditForm", method = RequestMethod.POST)
    public Map<String, Object> saveAgencyRegistration(HttpServletRequest httpServletRequest) {
        getModel().bind(httpServletRequest);
        Map<String, Object> object = new LinkedHashMap<String, Object>();
        if (this.getModel().saveNoOfDaysEditForm()) {
            object.put("successFlag", MainetConstants.FlagY);
            object.put("applicationId", this.getModel().getProvisionalAssesmentMstDto().getApmApplicationId());
        } else {
            object.put("successFlag", MainetConstants.FlagN);
            object.put(MainetConstants.ERROR, this.getModel().getBindingResult().getAllErrors());
        }

        return object;

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
            getModel().setArrayOfKhasraDetails(response);
        }

        else if (model.getLandTypePrefix().equals(MainetConstants.Property.LandType.NZL)) {
            ArrayOfPlotDetails response = dataEntrySuiteService.getNajoolDetails(dto);
            model.setArrayOfPlotDetails(response);
        } else if (model.getLandTypePrefix().equals(MainetConstants.Property.LandType.DIV)) {
            ArrayOfDiversionPlotDetails response = dataEntrySuiteService.getDiversionDetails(dto);
            model.setArrayOfDiversionPlotDetails(response);
        }

        return new ModelAndView("showPropertyKhasaraDetails", MainetConstants.FORM_NAME, model);
    }

    // commentted : functionality is under developement
    /*
     * @RequestMapping(params = "advanceSearch", method = RequestMethod.POST) public ModelAndView advanceSearch(HttpServletRequest
     * httpServletRequest, HttpServletResponse httpServletResponse) { getModel().bind(httpServletRequest); SelfAssesmentNewModel
     * model = this.getModel(); return new ModelAndView("AdvanceSearchForm", MainetConstants.FORM_NAME, model); }
     * @RequestMapping(params = "searchPropDetail", method = RequestMethod.POST) public ModelAndView search(HttpServletRequest
     * request) { SelfAssesmentNewModel model = this.getModel(); model.bind(request); model.setSearchDtoResult(new
     * ArrayList<>(0)); ProperySearchDto dto = model.getSearchDto();
     * dto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid()); if ((dto.getGuardianName() == null ||
     * dto.getGuardianName().isEmpty()) && (dto.getLocId() == null) && (dto.getMobileno() == null || dto.getMobileno().isEmpty())
     * && (dto.getOwnerName() == null || dto.getOwnerName().isEmpty())) {
     * model.addValidationError("Please enter search criteria."); } else { model.setSearchDtoResult(
     * propertyService.searchPropertyDetails( model.getSearchDto())); } return defaultMyResult(); }
     * @RequestMapping(params = "SEARCH_GRID_RESULTS", produces = "application/json", method = RequestMethod.POST)
     * public @ResponseBody JQGridResponse<? extends Serializable> getSearchResults( HttpServletRequest
     * httpServletRequest, @RequestParam String page,
     * @RequestParam String rows, @RequestParam String sidx, @RequestParam String sord) { SelfAssesmentNewModel model =
     * this.getModel(); return this.getModel().paginate(httpServletRequest, page, rows, model.getSearchDtoResult().size(),
     * model.getSearchDtoResult()); }
     */

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
    
    @RequestMapping(params = "backToSearch", method = RequestMethod.POST)
	public ModelAndView backToSearch(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {		
		this.sessionCleanup(httpServletRequest);
		return new ModelAndView("ChangeInAssessmentSearchForm", MainetConstants.FORM_NAME,  this.getModel());
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
    
    
    private void updateBillMas(List<TbBillMas> newBillMas, List<TbBillMas> oldBillMas, Long currFinId, String propNo, List<TbBillMas> currentlyCalculatedBills,Map<Long, BillDisplayDto> taxWiseRebate) {
    	AtomicDouble advanceAmount = new AtomicDouble(0);
    	if(CollectionUtils.isNotEmpty(oldBillMas)) {
    		Long firstBmIdNo = propertyMainBillRepository.getFirstBmIdNoByPropNo(oldBillMas.get(0).getPropNo());
    		
    		newBillMas.forEach(newBill ->{
    			
    			List<TbBillMas> currentBillList = oldBillMas.stream().filter(billMas -> billMas.getBmYear().equals(currFinId)).collect(Collectors.toList());
        		TbBillMas oldCurrentBillList = currentBillList.get(0);
    			if(newBill.getBmYear().equals(oldCurrentBillList.getBmYear())) {
    				newBill.getTbWtBillDet().forEach(newBillDet ->{
    					List<TbBillDet> oldBillList = oldCurrentBillList.getTbWtBillDet().stream()
    							.filter(oldBillDet -> oldBillDet.getTaxId().equals(newBillDet.getTaxId()))
    							.collect(Collectors.toList());
    					if(CollectionUtils.isNotEmpty(oldBillList)) {
    						TbBillDet oldBillTaxWise = oldBillList.get(0);
    						
    						if(firstBmIdNo == newBill.getBmIdno()) {
    							newBillDet.setBdPrvArramt(oldBillTaxWise.getBdPrvArramt());
    							newBillDet.setBdPrvBalArramt(oldBillTaxWise.getBdPrvBalArramt());
    						}
        					if(newBillDet.getBdCurTaxamt() > 0) {
        						double paidAmount = oldBillTaxWise.getBdCurTaxamt() - oldBillTaxWise.getBdCurBalTaxamt();
        						double newBillbalanceAmount = newBillDet.getBdCurTaxamt() - paidAmount;
        						if(newBillbalanceAmount >= 0) {
        							newBillDet.setBdCurBalTaxamt(newBillbalanceAmount);
        							newBillDet.setBdCurTaxamt(newBillbalanceAmount);
        							
									if (currFinId.equals(newBill.getBmYear()) && MapUtils.isNotEmpty(taxWiseRebate)) {
										
										 taxWiseRebate.entrySet().forEach(entry -> {
							                    BillDisplayDto value = entry.getValue();
							                    if(value.getParentTaxId().equals(newBillDet.getTaxId())) {
							                    	newBillDet.setBdCurBalTaxamt(newBillbalanceAmount - (value.getCurrentYearTaxAmt().doubleValue()));
							                    }
							                });
									} 
        						}else {
        							newBillDet.setBdCurBalTaxamt(0.0);
        							advanceAmount.addAndGet(Math.abs(newBillbalanceAmount));
        						}
        					}else {
        						newBillDet.setBdCurTaxamt(oldBillTaxWise.getBdCurTaxamt());
        						newBillDet.setBdCurBalTaxamt(oldBillTaxWise.getBdCurBalTaxamt());
        					}
    					}
    					
    				});
    			}
    		});
    	}
    	
    	List<TbBillMas> existingBills = propertyMainBillService.fetchBillSForViewProperty(propNo);
		HashMap<Long, Double> amountWithTaxes = new HashMap<Long, Double>();
		AtomicDouble payAmount = new AtomicDouble();
		for (TbBillMas newBill : currentlyCalculatedBills) {
			if(!newBill.getBmYear().equals(currFinId)) {
				List<TbBillMas> billMas = existingBills.stream().filter(bill -> bill.getBmYear().equals(newBill.getBmYear())).collect(Collectors.toList());
				if(CollectionUtils.isNotEmpty(billMas)) {
					for (TbBillDet newTbBillDet : newBill.getTbWtBillDet()) {
						List<TbBillDet> oldBillDetTax = billMas.get(0).getTbWtBillDet().stream().filter(billDet -> billDet.getTaxId().equals(newTbBillDet.getTaxId())).collect(Collectors.toList());
						if(CollectionUtils.isNotEmpty(oldBillDetTax)) {
							TbBillDet billDet = oldBillDetTax.get(0);
							double incrOrDecrAmount = newTbBillDet.getBdCurTaxamt() - billDet.getBdCurTaxamt();
							if(incrOrDecrAmount >= 0) {
								if(MapUtils.isNotEmpty(amountWithTaxes) && amountWithTaxes.get(billDet.getTaxId()) != null && amountWithTaxes.get(billDet.getTaxId()) > 0) {
									Double alreadyAddedAmount = amountWithTaxes.get(billDet.getTaxId());
									alreadyAddedAmount = alreadyAddedAmount + incrOrDecrAmount;
									amountWithTaxes.put(billDet.getTaxId(), alreadyAddedAmount);
								}else {
									amountWithTaxes.put(billDet.getTaxId(), incrOrDecrAmount);
								}
							}else {
								payAmount.addAndGet(Math.abs(incrOrDecrAmount));
							}
						}else {
							if(MapUtils.isNotEmpty(amountWithTaxes) && amountWithTaxes.get(newTbBillDet.getTaxId()) != null && amountWithTaxes.get(newTbBillDet.getTaxId()) > 0) {
								Double alreadyAddedAmount = amountWithTaxes.get(newTbBillDet.getTaxId());
								alreadyAddedAmount = alreadyAddedAmount + newTbBillDet.getBdCurTaxamt();
								amountWithTaxes.put(newTbBillDet.getTaxId(), alreadyAddedAmount);
							}else {
								amountWithTaxes.put(newTbBillDet.getTaxId(), newTbBillDet.getBdCurTaxamt());
							}
						}
						
					}
				}else {
					for (TbBillDet newTbBillDet : newBill.getTbWtBillDet()) {
						double incrOrDecrAmount = newTbBillDet.getBdCurTaxamt();
						if(incrOrDecrAmount >= 0) {
							if(MapUtils.isNotEmpty(amountWithTaxes) && amountWithTaxes.get(newTbBillDet.getTaxId()) != null && amountWithTaxes.get(newTbBillDet.getTaxId()) > 0) {
								Double alreadyAddedAmount = amountWithTaxes.get(newTbBillDet.getTaxId());
								alreadyAddedAmount = alreadyAddedAmount + incrOrDecrAmount;
								amountWithTaxes.put(newTbBillDet.getTaxId(), alreadyAddedAmount);
							}else {
								amountWithTaxes.put(newTbBillDet.getTaxId(), incrOrDecrAmount);
							}
						}else {
							payAmount.addAndGet(Math.abs(incrOrDecrAmount));
						}
					}
				}
			}
		}
		
		
		if(MapUtils.isNotEmpty(amountWithTaxes)) {
			for (TbBillMas newBill : newBillMas) {
				if(newBill.getBmYear().equals(currFinId)) {
					newBill.getTbWtBillDet().forEach(billDet ->{
						Double amountToAdd = amountWithTaxes.get(billDet.getTaxId());
						if(amountToAdd!= null && amountToAdd > 0) {
							billDet.setBdCurTaxamt(billDet.getBdCurTaxamt() + amountToAdd);
							billDet.setBdCurBalTaxamt(billDet.getBdCurBalTaxamt() + amountToAdd);
						}
						
					});
				}
			}
		}
		
		
		AtomicDouble demandBalanceAmount = new AtomicDouble(0);
    	AtomicDouble balanceAmount = new AtomicDouble(0);
    	newBillMas.get(newBillMas.size() - 1).getTbWtBillDet().forEach(billDet ->{
    		balanceAmount.addAndGet(billDet.getBdCurBalTaxamt() + billDet.getBdPrvBalArramt());
    		final String taxCode = CommonMasterUtility
					.getHierarchicalLookUp(billDet.getTaxCategory(), UserSession.getCurrent().getOrganisation())
					.getLookUpCode();
    		if(!org.apache.commons.lang.StringUtils.equals("I", taxCode)) {
    			demandBalanceAmount.addAndGet(billDet.getBdCurBalTaxamt());	
    		}
    	});
    	
    	
    	if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "KIF")) {
    		final Map<Long, Double> details = new HashMap<>(0);
     		final Map<Long, Long> billDetails = new HashMap<>(0);
     		final List<Map<Long, List<Double>>> rebateDetails = new ArrayList<>();
        	
        	billMasterCommonService.updateBillDataForInterest(newBillMas,
        			payAmount.doubleValue(), details, billDetails,
    				UserSession.getCurrent().getOrganisation(), rebateDetails, null);
    		billMasterCommonService.updateBillData(newBillMas,
    				newBillMas.get(newBillMas.size() - 1).getExcessAmount(), details,
    				billDetails, UserSession.getCurrent().getOrganisation(), rebateDetails, null);
    		billMasterCommonService.updateArrearInCurrentBills(newBillMas);
    	}
    	newBillMas.forEach(newBill ->{
    		newBill.setRevisedBillDate(new Date());
    		newBill.setRevisedBillType(MainetConstants.FlagC);
    		newBill.getTbWtBillDet().forEach(detDto ->{
    	  		  detDto.setRevisedBillDate(new Date());
    	  		  detDto.setRevisedBillType(MainetConstants.FlagC);
    	  	  });
    	});
    	newBillMas.get(newBillMas.size() - 1).setBmTotalOutstanding(balanceAmount.doubleValue());
    	newBillMas.get(newBillMas.size() - 1).setBmTotalBalAmount(demandBalanceAmount.doubleValue());
		if(balanceAmount.doubleValue() <= 0) {
			newBillMas.get(newBillMas.size() - 1).setBmPaidFlag(MainetConstants.FlagY);
    	}
		
		if(newBillMas.get(newBillMas.size() - 1).getExcessAmount() > 0) {
			advanceAmount.addAndGet(newBillMas.get(newBillMas.size() - 1).getExcessAmount());
		}
    	  if(advanceAmount.doubleValue() > 0) {
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
 	        
 	         this.getModel().setDemandBAsedAdvanceAmt(advanceAmtDto);
         }
    }
    
    private boolean checkBillPending(String propNo, String flatNo) {
    	AtomicBoolean payStatus = new AtomicBoolean(true);
    	if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "SKDCL")) {
    		Long billingMethodId = primaryPropertyService.getBillMethodIdByPropNo(propNo, UserSession.getCurrent().getOrganisation().getOrgid());
    		LookUp billingMethodLookUp  = null;
    		String billingMethod = null;
        	try {
        		 billingMethodLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(billingMethodId, UserSession.getCurrent().getOrganisation());
        	}catch (Exception e) {
    			// TODO: handle exception
    		}
        	if(billingMethodLookUp != null) {
        		billingMethod = billingMethodLookUp.getLookUpCode();
        	}
        	
    		List<TbBillMas> billMasData = null;
    		
    		if(org.apache.commons.lang.StringUtils.isNotBlank(billingMethod) && org.apache.commons.lang.StringUtils.equals(billingMethod, MainetConstants.FlagI)) {
    			billMasData = propertyMainBillService.fetchNotPaidBillForAssessmentByFlatNo(propNo, UserSession.getCurrent().getOrganisation().getOrgid(), flatNo);
    		}else {
    			billMasData = propertyMainBillService.fetchNotPaidBillForAssessment(propNo, UserSession.getCurrent().getOrganisation().getOrgid());
    		}
    		
    		if(CollectionUtils.isNotEmpty(billMasData)) {
    			payStatus.set(false);
    		}
    	}
    	
    	return payStatus.get();
    	
    }

    @RequestMapping(params = "saveAuthorizationWithEditForObjection", method = RequestMethod.POST)
    public ModelAndView saveAuthorizationWithEdit(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        SelfAssesmentNewModel model = this.getModel();
        model.getBillMasList().forEach(bill ->{
        	bill.setPropNo(model.getProvisionalAssesmentMstDto().getAssNo());
        });
        propertyMainBillService.saveAndUpdateMainBill(model.getBillMasList(),
				UserSession.getCurrent().getOrganisation().getOrgid(),
				UserSession.getCurrent().getEmployee().getEmpId(), MainetConstants.Property.AuthStatus.AUTH,
				model.getClientIpAddress());
        List<ProvisionalBillMasEntity> provBillList = new ArrayList<>();
		iProvisionalBillService.saveAndUpdateProvisionalBillForReviseBill(model.getBillMasList(), model.getBillMasList().get(0).getOrgid(), UserSession.getCurrent().getEmployee().getEmpId(),
				model.getBillMasList().get(0).getPropNo(), null, provBillList, model.getClientIpAddress());
        selfAssessmentService.saveDemandLevelRebate(model.getProvisionalAssesmentMstDto(), UserSession.getCurrent().getEmployee().getEmpId(), model.getDeptId(), model.getBillMasList(), model.getReabteRecDtoList(),
				UserSession.getCurrent().getOrganisation(), provBillList);
        assesmentMastService.saveAndUpdateAssessmentMastForOnlyForDES(model.getProvisionalAssesmentMstDto(),
				UserSession.getCurrent().getOrganisation().getOrgid(),
				UserSession.getCurrent().getEmployee().getEmpId(), model.getClientIpAddress());
        if (model.saveAuthorizationForObjection()) {
            return jsonResult(JsonViewObject.successResult(
                    ApplicationSession.getInstance().getMessage("property.Auth.save")));
        }
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)
        		&& MainetConstants.FlagE.equals(model.getSelfAss())){
        	return customDefaultMyResult("SelfAssessmentView");
        }
        return customDefaultMyResult("PropertyAuthorizationBeforeAfter");
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
    
    @RequestMapping(params = "getUnitCount", method = RequestMethod.POST)
    public @ResponseBody Long getUnitCount(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            String yearOfAcq) {
        this.getModel().bind(httpServletRequest);
        SelfAssesmentNewModel model = this.getModel();
       // model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().clear();
        
        Long financeYearId = iFinancialYear.getFinanceYearId(Utility.stringToDate(yearOfAcq));
        List<ProvisionalAssesmentDetailDto> provisionalAssesmentDetailDtoList = model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList();
        List<ProvisionalAssesmentDetailDto> detList = new ArrayList<ProvisionalAssesmentDetailDto>();
        for (ProvisionalAssesmentDetailDto det : provisionalAssesmentDetailDtoList) {
        	if(financeYearId < det.getFaYearId()) {
        		detList.add(det);
        	}
		}
        model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().clear();
        model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().addAll(detList);
        Long faYearId = model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().get(0).getFaYearId();
        List<ProvisionalAssesmentDetailDto> oneYearList = model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().stream().filter(provDetail -> provDetail.getFaYearId().equals(faYearId)).collect(Collectors.toList());
        
        Long unitCount = (long) (oneYearList.size() + oneYearList.size());
         return unitCount;

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
    
	public Long getyearOfAcquisition(Date currentDate) {
		SelfAssesmentNewModel model = this.getModel();
		if (!(currentDate.compareTo(model.getCurrentFinStartDate()) >= 0
				&& currentDate.compareTo(model.getCurrentFinEndDate()) <= 0)) {
			return 0L;
		}
		return 1L;

	}
	
	
	private double updateBillMasForChangeInAss(List<TbBillMas> newBillMas, List<TbBillMas> oldBillMas, Long currFinId, String propNo,
			List<TbBillMas> currentlyCalculatedBills, Map<Long, BillDisplayDto> taxWiseRebateMap, Map<Long, Double> finAlvMap,Map<Long, Double> finRvMap
			,Map<Long, Double> finCvmap,Map<Long, Double> finStdRate,BillDisplayDto advanceAmt,Long deptId) {
		
		List<TbBillMas> billsToSaveList = new ArrayList<TbBillMas>();
		newBillMas.clear();
		HashMap<Long, Double> amountWithTaxes = new HashMap<Long, Double>();
		
		List<TbBillMas> existingBillList = propertyMainBillService.fetchBillSForViewProperty(propNo);
		
		existingBillList.forEach(previousBill -> {
			previousBill.setRevisedBillDate(new Date());
			previousBill.setRevisedBillType(MainetConstants.FlagC);
			previousBill.getTbWtBillDet().forEach(detDto -> {
				detDto.setRevisedBillDate(new Date());
				detDto.setRevisedBillType(MainetConstants.FlagC);
			});
		});
		propertyMainBillService.copyDataFromMainBillDetailToHistory(existingBillList, MainetConstants.FlagC,UserSession.getCurrent().getEmployee().getEmpId(),this.getModel().getClientIpAddress());
		
		AtomicDouble payAmount = new AtomicDouble(0.00);
		AtomicDouble advanceAmount = new AtomicDouble(0);
		
		Long startedYearChanInAss = currentlyCalculatedBills.get(0).getBmYear();
		Long systemFirstBillYear = existingBillList.get(0).getBmYear();
		Map<Long, Map<Long, Double>> YearWiseArrearsAddMap = new HashMap<>();
		Map<Long, Map<Long, Double>> YearWiseArrearsSubMap = new HashMap<>();
		Map<Long, Map<Long, Double>> YearWiseExemptionAddMap = new HashMap<>();
		int systemExistBillSize = existingBillList.size();
		int userCalNewBillSize = currentlyCalculatedBills.size();
		
		for (TbBillMas existingBill : existingBillList) {
			
			List<TbBillMas> toOverriseBill = currentlyCalculatedBills.stream()
					.filter(currentBill -> currentBill.getBmYear().equals(existingBill.getBmYear()))
					.collect(Collectors.toList());
			if(CollectionUtils.isNotEmpty(toOverriseBill)) {
				FinancialYear finincialYearsById = iFinancialYear.getFinincialYearsById(existingBill.getBmYear(), UserSession.getCurrent().getOrganisation().getOrgid());
				Long previousFinYear = getPreviousFinYear(finincialYearsById);
				HashMap<Long, Double> additionMap = new HashMap<Long, Double>();
				HashMap<Long, Double> subMap = new HashMap<Long, Double>();
				HashMap<Long, Double> exemptionMap = new HashMap<Long, Double>();
				
				for (TbBillDet existBillDet : existingBill.getTbWtBillDet()) {
					for (TbBillDet overrideDet : toOverriseBill.get(0).getTbWtBillDet()) {

						final String taxCode = CommonMasterUtility.getHierarchicalLookUp(existBillDet.getTaxCategory(), UserSession.getCurrent().getOrganisation())
								.getLookUpCode();
						if(existBillDet.getTaxId().equals(overrideDet.getTaxId())) {
							if (!taxCode.equals(PrefixConstants.TAX_CATEGORY.INTERST)) {
								double paidAmnt = existBillDet.getBdCurTaxamt() - existBillDet.getBdCurBalTaxamt();
								setBillAmountDetail(additionMap, subMap, existBillDet, overrideDet,YearWiseArrearsAddMap.get(previousFinYear),YearWiseArrearsSubMap.get(previousFinYear));
								
								if(previousFinYear != null) {
									setArrearDiffAmount(YearWiseArrearsAddMap, YearWiseArrearsSubMap, previousFinYear,
											existBillDet);
								}
								if (paidAmnt > 0) {
									knocffTaxWiseAmnt(payAmount, existBillDet, paidAmnt);
								}
							}
							
						}else {
							if (!taxCode.equals(PrefixConstants.TAX_CATEGORY.INTERST)) {
								Optional<TbBillDet> findAny = toOverriseBill.get(0).getTbWtBillDet().stream().filter(overDet -> overDet.getTaxId().equals(existBillDet.getTaxId())).findAny();
								if(!findAny.isPresent() && existBillDet.getBdCurTaxamt() > 0) {
									double advnceAmnt = existBillDet.getBdCurTaxamt() - existBillDet.getBdCurBalTaxamt();
									payAmount.addAndGet(advnceAmnt);
									Map<Long, Double> PreviousSubstractMap = YearWiseArrearsSubMap.get(previousFinYear);
									if(MapUtils.isNotEmpty(PreviousSubstractMap) && PreviousSubstractMap.get(existBillDet.getTaxId()) != null) {
										subMap.put(existBillDet.getTaxId(), existBillDet.getBdCurTaxamt() + PreviousSubstractMap.get(existBillDet.getTaxId()));
									}else {
										subMap.put(existBillDet.getTaxId(), existBillDet.getBdCurTaxamt());
									}
									existBillDet.setBdCurTaxamt(0.00);
									existBillDet.setBdCurBalTaxamt(0.00);
									if(previousFinYear != null) {
										setArrearDiffAmount(YearWiseArrearsAddMap, YearWiseArrearsSubMap, previousFinYear,
												existBillDet);
									}
								}
							}
						}
					
					}
				
				}
				
				YearWiseArrearsAddMap.put(existingBill.getBmYear(), additionMap);
				YearWiseArrearsSubMap.put(existingBill.getBmYear(), subMap);
				//YearWiseExemptionAddMap.put(existingBill.getBmYear(), exemptionMap);
				billsToSaveList.add(existingBill);
			}else {
				if(existingBill.getBmYear().equals(currFinId)) {
					billsToSaveList.add(existingBill);
				}else if(org.apache.commons.lang.StringUtils.equals(existingBill.getBmPaidFlag(), MainetConstants.FlagN)) {
					billsToSaveList.add(existingBill);
				}
			}
			
		
		}
		
		if(billsToSaveList.get(0).getBmYear().equals(startedYearChanInAss)) {
			billsToSaveList.forEach(existMas ->{
				if(startedYearChanInAss.equals(existMas.getBmYear())) {
					Map<Long, Double> PreviousSubstractMap = YearWiseArrearsSubMap.get(existMas.getBmYear());
					existMas.getTbWtBillDet().forEach(existDet ->{
						final String taxCode = CommonMasterUtility.getHierarchicalLookUp(existDet.getTaxCategory(), UserSession.getCurrent().getOrganisation())
								.getLookUpCode();
						if (taxCode.equals(PrefixConstants.TAX_CATEGORY.INTERST)) {
							PreviousSubstractMap.put(existDet.getTaxId(), existDet.getBdCurTaxamt());
							existDet.setBdCurTaxamt(0.00);
							existDet.setBdCurBalTaxamt(0.00);
						}
					});
				}
			});
		}
		for (TbBillMas currentBill : currentlyCalculatedBills) {
			for (TbBillMas existingBill : existingBillList) {
				if(currentBill.getBmYear().equals(existingBill.getBmYear())) {
					for (TbBillDet currentBillDet : currentBill.getTbWtBillDet()) {
						Optional<TbBillDet> findAny = existingBill.getTbWtBillDet().stream().filter(existBillDet -> existBillDet.getTaxId().equals(currentBillDet.getTaxId())).findAny();
						if(!findAny.isPresent()) {
							existingBill.getTbWtBillDet().add(currentBillDet);
						}
					}
				}
			}
		}
		
		if(userCalNewBillSize > systemExistBillSize) {
			createMapToAddArrearsInFirstBill(currentlyCalculatedBills, amountWithTaxes, billsToSaveList);
		}
		
		if(MapUtils.isNotEmpty(amountWithTaxes)) {
			setArrearsInSystemFirstBill(amountWithTaxes, billsToSaveList, systemFirstBillYear);
		}
		double ajustedAmount = 0;
		
		AtomicDouble totalBalAmnt = new AtomicDouble(0.00);
		billsToSaveList.forEach(billMaster ->{
			billMaster.getTbWtBillDet().forEach(detail ->{
				totalBalAmnt.addAndGet(detail.getBdCurBalTaxamt() + detail.getBdPrvBalArramt());
			});
			
		});
		if(advanceAmt != null && advanceAmt.getCurrentYearTaxAmt().doubleValue() > 0 && totalBalAmnt.doubleValue() > 0) {
			billMasterCommonService.updateArrearInCurrentBills(billsToSaveList);
			List<BillReceiptPostingDTO> result = new ArrayList<BillReceiptPostingDTO>();
			
			final Map<Long, Double> details = new HashMap<>(0);
			final Map<Long, Long> billDetails = new HashMap<>(0);
			final List<Map<Long, List<Double>>> rebateDetails = new ArrayList<>();

			List<BillReceiptPostingDTO> interestResult = billMasterCommonService.updateBillDataForInterest(billsToSaveList, advanceAmt.getCurrentYearTaxAmt().doubleValue(), details, billDetails,
					UserSession.getCurrent().getOrganisation(), rebateDetails, null);
			result = billMasterCommonService.updateBillData(billsToSaveList, billsToSaveList.get(billsToSaveList.size() - 1).getExcessAmount(),
					details, billDetails, UserSession.getCurrent().getOrganisation(), rebateDetails, null);
			result.addAll(interestResult);
			billMasterCommonService.updateArrearInCurrentBills(billsToSaveList);
			billsToSaveList.get(billsToSaveList.size() -1).getTbWtBillDet().forEach(billSaveDet ->{
				billSaveDet.setBdPrvArramt(billSaveDet.getBdPrvBalArramt());
			});
			
			if (billsToSaveList.get(billsToSaveList.size() - 1).getExcessAmount() > 0) {
				ajustedAmount = advanceAmt.getCurrentYearTaxAmt().doubleValue()
                        - billsToSaveList.get(billsToSaveList.size() - 1).getExcessAmount();
            } else {
            	ajustedAmount = advanceAmt.getCurrentYearTaxAmt().doubleValue();
            }
			
			
			BillPaymentDetailDto billPayDto = ApplicationContextProvider.getApplicationContext().getBean(PropertyBillPaymentService.class)
	 				.getBillPaymentDetail(null, propNo,
	 						UserSession.getCurrent().getOrganisation().getOrgid(),
	 						UserSession.getCurrent().getEmployee().getEmpId(), null, null, null);
			
			CommonChallanDTO createDtoForReceiptInsertion = createDtoForReceiptInsertion(null, deptId, details, billDetails, billPayDto, advanceAmt.getCurrentYearTaxAmt().doubleValue(), billsToSaveList.get(billsToSaveList.size() - 1).getBmFromdt(), null);
			this.getModel().getReceiptInsertionList().put(createDtoForReceiptInsertion, result);
			
		
		}
		billsToSaveList.forEach(billSave -> {
			if (billSave.getBmYear() > startedYearChanInAss) {
				interestCalculation(payAmount, YearWiseArrearsAddMap, YearWiseArrearsSubMap, billSave);
			}
		});
		
		
		
		billMasterCommonService.updateArrearInCurrentBills(billsToSaveList);
		
		
		billsToSaveList.forEach(billMaster ->{
			AtomicDouble totalAmnt = new AtomicDouble(0.00);
			billMaster.getTbWtBillDet().forEach(detail ->{
				totalAmnt.addAndGet(detail.getBdCurBalTaxamt() + detail.getBdPrvBalArramt());
			});
			if(totalAmnt.get() > 0) {
				billMaster.setBmPaidFlag(MainetConstants.FlagN);
				billMaster.setBmTotalOutstanding(totalAmnt.get());
			}else {
				billMaster.setBmPaidFlag(MainetConstants.FlagY);
				billMaster.setBmTotalOutstanding(0.00);
			}
		});
		
		setALVIntiBills(finAlvMap, finRvMap, finCvmap, finStdRate, billsToSaveList);


		AtomicDouble demandBalanceAmount = new AtomicDouble(0);
		AtomicDouble balanceAmount = new AtomicDouble(0);
		billsToSaveList.get(billsToSaveList.size() - 1).getTbWtBillDet().forEach(billDet -> {
			balanceAmount.addAndGet(billDet.getBdCurBalTaxamt() + billDet.getBdPrvBalArramt());
			final String taxCode = CommonMasterUtility
					.getHierarchicalLookUp(billDet.getTaxCategory(), UserSession.getCurrent().getOrganisation())
					.getLookUpCode();
			if (!org.apache.commons.lang.StringUtils.equals("I", taxCode)) {
				demandBalanceAmount.addAndGet(billDet.getBdCurBalTaxamt());
			}
		});

		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "KIF")) {
			final Map<Long, Double> details = new HashMap<>(0);
			final Map<Long, Long> billDetails = new HashMap<>(0);
			final List<Map<Long, List<Double>>> rebateDetails = new ArrayList<>();

			billMasterCommonService.updateBillDataForInterest(billsToSaveList, payAmount.doubleValue(), details, billDetails,
					UserSession.getCurrent().getOrganisation(), rebateDetails, null);
			billMasterCommonService.updateBillData(billsToSaveList, billsToSaveList.get(billsToSaveList.size() - 1).getExcessAmount(),
					details, billDetails, UserSession.getCurrent().getOrganisation(), rebateDetails, null);
			billMasterCommonService.updateArrearInCurrentBills(billsToSaveList);
		}
		billsToSaveList.forEach(newBill -> {
			newBill.setRevisedBillDate(new Date());
			newBill.setRevisedBillType(MainetConstants.FlagC);
			newBill.getTbWtBillDet().forEach(detDto -> {
				detDto.setRevisedBillDate(new Date());
				detDto.setRevisedBillType(MainetConstants.FlagC);
			});
		});
		billsToSaveList.get(billsToSaveList.size() - 1).setBmTotalOutstanding(balanceAmount.doubleValue());
		billsToSaveList.get(billsToSaveList.size() - 1).setBmTotalBalAmount(demandBalanceAmount.doubleValue());
		if (balanceAmount.doubleValue() <= 0) {
			billsToSaveList.get(billsToSaveList.size() - 1).setBmPaidFlag(MainetConstants.FlagY);
		}

		if (billsToSaveList.get(billsToSaveList.size() - 1).getExcessAmount() > 0) {
			advanceAmount.addAndGet(billsToSaveList.get(billsToSaveList.size() - 1).getExcessAmount());
		}
		if (advanceAmount.doubleValue() > 0) {
			Long advanceTaxId = ApplicationContextProvider.getApplicationContext()
					.getBean(BillMasterCommonService.class)
					.getAdvanceTaxId(UserSession.getCurrent().getOrganisation().getOrgid(),
							MainetConstants.Property.PROP_DEPT_SHORT_CODE, null);
			TbTaxMas taxMas = ApplicationContextProvider.getApplicationContext().getBean(TbTaxMasService.class)
					.findTaxByTaxIdAndOrgId(advanceTaxId, UserSession.getCurrent().getOrganisation().getOrgid());
			BillDisplayDto advanceAmtDto = new BillDisplayDto();
			advanceAmtDto.setCurrentYearTaxAmt(BigDecimal.valueOf(advanceAmount.doubleValue()));
			advanceAmtDto.setTotalTaxAmt(BigDecimal.valueOf(advanceAmount.doubleValue()));
			advanceAmtDto.setTaxId(taxMas.getTaxId());
			advanceAmtDto.setTaxCategoryId(taxMas.getTaxCategory1());
			advanceAmtDto.setDisplaySeq(taxMas.getTaxDisplaySeq());
			advanceAmtDto.setTaxDesc(taxMas.getTaxDesc());
			List<BillDisplayDto> advanceList = new ArrayList<BillDisplayDto>();
			advanceList.add(advanceAmtDto);

			this.getModel().setDemandBAsedAdvanceAmt(advanceAmtDto);
		}
		newBillMas.addAll(billsToSaveList);
		return ajustedAmount;

	}

	private void setALVIntiBills(Map<Long, Double> finAlvMap, Map<Long, Double> finRvMap, Map<Long, Double> finCvmap,
			Map<Long, Double> finStdRate, List<TbBillMas> billsToSaveList) {
		for (TbBillMas bill : billsToSaveList) {
			if(finStdRate.get(bill.getBmYear()) != null && finStdRate.get(bill.getBmYear()) > 0) {
				bill.setAssdStdRate(finStdRate.get(bill.getBmYear()));
			}
			if(finAlvMap.get(bill.getBmYear()) != null && finAlvMap.get(bill.getBmYear()) > 0) {
				bill.setAssdAlv(finAlvMap.get(bill.getBmYear()));
			}
			if(finRvMap.get(bill.getBmYear()) != null && finRvMap.get(bill.getBmYear()) > 0) {
				bill.setAssdRv(finRvMap.get(bill.getBmYear()));
			}
			if(finCvmap.get(bill.getBmYear()) != null && finCvmap.get(bill.getBmYear()) > 0) {
				bill.setAssdCv(finCvmap.get(bill.getBmYear()));
			}
			
		}
	}

	private void interestCalculation(AtomicDouble payAmount, Map<Long, Map<Long, Double>> YearWiseArrearsAddMap,
			Map<Long, Map<Long, Double>> YearWiseArrearsSubMap, TbBillMas billSave) {
		FinancialYear finincialYearsById = iFinancialYear.getFinincialYearsById(billSave.getBmYear(), UserSession.getCurrent().getOrganisation().getOrgid());
		Long previousFinYear = getPreviousFinYear(finincialYearsById);
		Map<Long, Double> PreviousAdditionMap = YearWiseArrearsAddMap.get(previousFinYear);
		Map<Long, Double> PreviousSubstractMap = YearWiseArrearsSubMap.get(previousFinYear);
		//Map<Long, Double> exemptionMap = exemptionYearWiseMap.get(previousFinYear);
		
		billSave.getTbWtBillDet().forEach(billDet -> {
			double paidAmnt = billDet.getBdCurTaxamt() - billDet.getBdCurBalTaxamt();
			final String taxCode = CommonMasterUtility
					.getHierarchicalLookUp(billDet.getTaxCategory(), UserSession.getCurrent().getOrganisation())
					.getLookUpCode();
			if (taxCode.equals(PrefixConstants.TAX_CATEGORY.INTERST)) {
				Map<Long, Double> addMap = YearWiseArrearsAddMap.get(billSave.getBmYear());
				Map<Long, Double> subMap = YearWiseArrearsSubMap.get(billSave.getBmYear());
				TbTaxMas intersetTaxMas = tbTaxMasService.findById(billDet.getTaxId(), UserSession.getCurrent().getOrganisation().getOrgid());
		 	   
		 	   List<TbBillDet> interestDependentTax = billSave.getTbWtBillDet().stream().filter(detail -> detail.getTaxId().equals(intersetTaxMas.getParentCode())).collect(Collectors.toList());
		 	   if(CollectionUtils.isNotEmpty(interestDependentTax)) {
		 		  double balanceAmnt = interestDependentTax.get(0).getBdPrvArramt();
					/*
					 * if(MapUtils.isNotEmpty(exemptionMap)) { Double exemAmnt =
					 * exemptionMap.get(intersetTaxMas.getParentCode()); if(exemAmnt != null &&
					 * exemAmnt > 0) { balanceAmnt = balanceAmnt - exemAmnt; } }
					 */
		 		  double interest = Math.round(balanceAmnt * 0.12);
		 			if(billDet.getBdCurTaxamt() > interest) {
		 				double howMuchAmntLesser = billDet.getBdCurTaxamt() - interest;
		 				if(MapUtils.isNotEmpty(PreviousSubstractMap) && PreviousSubstractMap.get(billDet.getTaxId()) != null) {
		 					howMuchAmntLesser = howMuchAmntLesser + PreviousSubstractMap.get(billDet.getTaxId());
		 				}
		 				billDet.setBdCurTaxamt(interest);
		 				billDet.setBdCurBalTaxamt(interest);
		 				subMap.put(billDet.getTaxId(), howMuchAmntLesser);
		 			}else {
		 				double howMuchAmntGreater = interest - billDet.getBdCurTaxamt();
		 				if(MapUtils.isNotEmpty(PreviousAdditionMap) && PreviousAdditionMap.get(billDet.getTaxId()) != null) {
		 					howMuchAmntGreater = howMuchAmntGreater + PreviousAdditionMap.get(billDet.getTaxId());
		 				}
		 				billDet.setBdCurTaxamt(interest);
		 				billDet.setBdCurBalTaxamt(interest);
		 				addMap.put(billDet.getTaxId(), howMuchAmntGreater);
		 			}
		 		 
		 	   }
		 	  if(previousFinYear != null) {
					setArrearDiffAmount(YearWiseArrearsAddMap, YearWiseArrearsSubMap, previousFinYear,
							billDet);
				}
		 	  if (paidAmnt > 0) {
					knocffTaxWiseAmnt(payAmount, billDet, paidAmnt);
				}
			}
		});
	}

	private void createMapToAddArrearsInFirstBill(List<TbBillMas> currentlyCalculatedBills,
			HashMap<Long, Double> amountWithTaxes, List<TbBillMas> existingBillList) {
		currentlyCalculatedBills.forEach(newBill ->{
			Optional<TbBillMas> findAny = existingBillList.stream().filter(bill -> bill.getBmYear().equals(newBill.getBmYear())).findAny();
			if(!findAny.isPresent()) {
				newBill.getTbWtBillDet().forEach(det ->{
					if(MapUtils.isNotEmpty(amountWithTaxes) && amountWithTaxes.get(det.getTaxId()) != null && amountWithTaxes.get(det.getTaxId()) > 0) {
						Double alreadyAddedAmount = amountWithTaxes.get(det.getTaxId());
						alreadyAddedAmount = alreadyAddedAmount + det.getBdCurTaxamt();
						amountWithTaxes.put(det.getTaxId(), alreadyAddedAmount);
					}else {
						amountWithTaxes.put(det.getTaxId(), det.getBdCurTaxamt());
					}
				});
			}
		});
	}

	private void setArrearsInSystemFirstBill(HashMap<Long, Double> amountWithTaxes, List<TbBillMas> existingBillList,
			Long systemFirstBillYear) {
		for (TbBillMas newBill : existingBillList) {
			if(newBill.getBmYear().equals(systemFirstBillYear)) {
				newBill.getTbWtBillDet().forEach(billDet ->{
					Double amountToAdd = amountWithTaxes.get(billDet.getTaxId());
					if(amountToAdd!= null && amountToAdd > 0) {
						billDet.setBdCurTaxamt(billDet.getBdCurTaxamt() + amountToAdd);
						billDet.setBdCurBalTaxamt(billDet.getBdCurBalTaxamt() + amountToAdd);
					}
					
				});
			}else {
				newBill.getTbWtBillDet().forEach(billDet ->{
					Double amountToAdd = amountWithTaxes.get(billDet.getTaxId());
					if(amountToAdd!= null && amountToAdd > 0) {
						billDet.setBdPrvArramt(billDet.getBdPrvArramt() + amountToAdd);
						billDet.setBdPrvBalArramt(billDet.getBdPrvBalArramt() + amountToAdd);
					}
					
				});
			}
		}
	}

	private void knocffTaxWiseAmnt(AtomicDouble payAmount, TbBillDet existBillDet, double paidAmnt) {
		if (existBillDet.getBdCurBalTaxamt() > paidAmnt) {
			existBillDet
					.setBdCurBalTaxamt(existBillDet.getBdCurBalTaxamt() - paidAmnt);
		} else {
			double advnceAmnt = paidAmnt - existBillDet.getBdCurBalTaxamt();
			existBillDet.setBdCurBalTaxamt(0.00);
			payAmount.addAndGet(advnceAmnt);
		}
	}

	private void setArrearDiffAmount(Map<Long, Map<Long, Double>> YearWiseArrearsAddMap,
			Map<Long, Map<Long, Double>> YearWiseArrearsSubMap, Long previousFinYear, TbBillDet existBillDet) {
		Map<Long, Double> addArrearMap = YearWiseArrearsAddMap.get(previousFinYear);
		if(MapUtils.isNotEmpty(addArrearMap)) {
			if(addArrearMap.get(existBillDet.getTaxId()) != null && addArrearMap.get(existBillDet.getTaxId()) > 0) {
				existBillDet.setBdPrvArramt(existBillDet.getBdPrvArramt() + addArrearMap.get(existBillDet.getTaxId()));
			}
		}
		
		Map<Long, Double> substractMap = YearWiseArrearsSubMap.get(previousFinYear);
		if(MapUtils.isNotEmpty(substractMap)) {
			if(substractMap.get(existBillDet.getTaxId()) != null && substractMap.get(existBillDet.getTaxId()) > 0) {
				double amount =  existBillDet.getBdPrvArramt() - substractMap.get(existBillDet.getTaxId());
				if(amount > 0) {
					existBillDet.setBdPrvArramt(existBillDet.getBdPrvArramt() - substractMap.get(existBillDet.getTaxId()));
				}else {
					existBillDet.setBdPrvArramt(0.00);
				}
			}
		}
	}

	private void setBillAmountDetail(HashMap<Long, Double> additionMap, HashMap<Long, Double> subMap,
			TbBillDet existBillDet, TbBillDet overrideDet,Map<Long, Double> PreviousAdditionMap,Map<Long, Double> PreviousSubstractMap) {
		if(existBillDet.getBdCurTaxamt() > overrideDet.getBdCurTaxamt()) {
			double howMuchAmntLesser = existBillDet.getBdCurTaxamt() - overrideDet.getBdCurTaxamt();
			double exemptionAmnt = overrideDet.getBdCurTaxamt() - overrideDet.getBdCurBalTaxamt();
			howMuchAmntLesser = howMuchAmntLesser + exemptionAmnt;
			if(MapUtils.isNotEmpty(PreviousSubstractMap) && PreviousSubstractMap.get(overrideDet.getTaxId()) != null) {
				howMuchAmntLesser = howMuchAmntLesser + PreviousSubstractMap.get(overrideDet.getTaxId());
			}
			existBillDet.setBdCurTaxamt(overrideDet.getBdCurTaxamt());
			existBillDet.setBdCurBalTaxamt(overrideDet.getBdCurBalTaxamt());
			subMap.put(existBillDet.getTaxId(), howMuchAmntLesser);
			
			if(MapUtils.isNotEmpty(PreviousAdditionMap) && PreviousAdditionMap.get(overrideDet.getTaxId()) != null) {
				additionMap.put(existBillDet.getTaxId(), PreviousAdditionMap.get(overrideDet.getTaxId()));
			}
			
		}else {
			double exemption = overrideDet.getBdCurTaxamt() - overrideDet.getBdCurBalTaxamt();
			double howMuchAmntGreater = overrideDet.getBdCurTaxamt() - existBillDet.getBdCurTaxamt();
			if(MapUtils.isNotEmpty(PreviousAdditionMap) && PreviousAdditionMap.get(overrideDet.getTaxId()) != null) {
				howMuchAmntGreater = howMuchAmntGreater + PreviousAdditionMap.get(overrideDet.getTaxId());
			}
			existBillDet.setBdCurTaxamt(overrideDet.getBdCurTaxamt());
			existBillDet.setBdCurBalTaxamt(overrideDet.getBdCurBalTaxamt());
			additionMap.put(existBillDet.getTaxId(), howMuchAmntGreater);
			
			if(exemption > 0) {
				if(MapUtils.isNotEmpty(PreviousSubstractMap) && PreviousSubstractMap.get(overrideDet.getTaxId()) != null) {
					exemption = exemption + PreviousSubstractMap.get(overrideDet.getTaxId());
				}
				subMap.put(existBillDet.getTaxId(), exemption);
			}else {
				if(MapUtils.isNotEmpty(PreviousSubstractMap) && PreviousSubstractMap.get(overrideDet.getTaxId()) != null) {
					subMap.put(existBillDet.getTaxId(), PreviousSubstractMap.get(overrideDet.getTaxId()));
				}
			}
			
		}
	}
	
	 private Long getPreviousFinYear(FinancialYear currentFinYear) {
	        FinancialYear previousFinYear = null;
	        Timestamp currFinYearTimeStamp = new Timestamp(currentFinYear.getFaFromDate().getTime());
	        Date currentFinYearDate = new Date(currFinYearTimeStamp.getTime());
	        LocalDate convertFinFromDateToLocalDate = currentFinYearDate.toInstant().atZone(ZoneId.systemDefault())
	                .toLocalDate();
	        currentFinYear.getFaToDate().getTime();
	        Date afterSubMonths = Date
	                .from(convertFinFromDateToLocalDate.minusMonths(5).atStartOfDay(ZoneId.systemDefault()).toInstant());
	        previousFinYear = ApplicationContextProvider.getApplicationContext().getBean(TbFinancialyearService.class)
	                .getFinanciaYearByDate(afterSubMonths);
	        if(previousFinYear != null) {
	        	return previousFinYear.getFaYear();
	        }else {
	        	return null;
	        }
	        
	    }
	
	 private CommonChallanDTO createDtoForReceiptInsertion(TbServiceReceiptMasEntity receiptDetail, Long deptId,
				final Map<Long, Double> details, final Map<Long, Long> billDetails, BillPaymentDetailDto billPayDto,Double Amnt,Date manualRecDate,String manualReceiptNo) {
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
	        	offline.setNarration("Internal adjustment of advance amount");
	            offline.setPayModeIn(CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.PaymentMode.CASH,
	                    PrefixConstants.LookUpPrefix.PAY_AT_COUNTER, UserSession.getCurrent().getOrganisation()).getLookUpId());
	        }
			offline.setDeptCode(MainetConstants.Property.PROP_DEPT_SHORT_CODE);
			return offline;
		}
}