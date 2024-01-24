package com.abm.mainet.property.ui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.BillReceiptPostingDTO;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.TbBillDet;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.master.repository.TbComparentDetJpaRepository;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.property.domain.BillingScheduleDetailEntity;
import com.abm.mainet.property.dto.ArrayOfDiversionPlotDetails;
import com.abm.mainet.property.dto.ArrayOfKhasraDetails;
import com.abm.mainet.property.dto.ArrayOfPlotDetails;
import com.abm.mainet.property.dto.BillDisplayDto;
import com.abm.mainet.property.dto.BillPresentAndCalculationDto;
import com.abm.mainet.property.dto.ExcelSheetDto;
import com.abm.mainet.property.dto.LandTypeApiDetailRequestDto;
import com.abm.mainet.property.dto.PropertyCommonInfoDto;
import com.abm.mainet.property.dto.PropertyRoomDetailsDto;
import com.abm.mainet.property.dto.ProperySearchDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentDetailDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentFactorDtlDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentOwnerDtlDto;
import com.abm.mainet.property.dto.WriteExcelData;
import com.abm.mainet.property.service.BillingScheduleService;
import com.abm.mainet.property.service.DataEntrySuiteService;
import com.abm.mainet.property.service.PropertyBRMSService;
import com.abm.mainet.property.service.PropertyService;
import com.abm.mainet.property.service.SelfAssessmentService;
import com.abm.mainet.property.ui.model.SelfAssesmentNewModel;
import com.abm.mainet.property.ui.validator.RuleErrorValidator;
import com.abm.mainet.property.ui.validator.SelfAssessmentValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.icu.text.SimpleDateFormat;

@Controller
@RequestMapping("/SelfAssessmentForm.html")
public class SelfAssessmentFormController extends AbstractFormController<SelfAssesmentNewModel> {

    private static final Logger LOGGER = Logger.getLogger(SelfAssessmentFormController.class);
    @Resource
    private SelfAssessmentService selfAssessmentService;

    @Autowired
    private IFinancialYearService iFinancialYear;

    @Autowired
    private BillingScheduleService billingScheduleService;

    @Autowired
    private ServiceMasterService serviceMaster;

    @Autowired
    private PropertyBRMSService propertyBRMSService;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private IFileUploadService fileUpload;

    @Resource
    private ILocationMasService iLocationMasService;

    @Autowired
    private DataEntrySuiteService dataEntrySuiteService;
    
    @Autowired
    private TbComparentDetJpaRepository tbComparentDetJpaRepository;

    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(HttpServletRequest request) throws Exception {
        this.sessionCleanup(request);
        SelfAssesmentNewModel model = this.getModel();
        fileUpload.sessionCleanUpForFileUpload();
        model.setCommonHelpDocs("SelfAssessmentForm.html");
        setCommonFields(getModel());
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
        // getModel().setSchedule(selfAssessmentService.getAllBillscheduleWithoutCurrentScheduleByOrgid(org));
        getModel().setAssType(MainetConstants.Property.NEW_ASESS);
        getModel().setSelfAss(MainetConstants.Y_FLAG);
        getModel().setProAssPropAddCheck(MainetConstants.Y_FLAG);
        final List<FinancialYear> finYearList = iFinancialYear.getAllFinincialYear();
        String financialYear = null;
        for (final FinancialYear finYearTemp : finYearList) {
            financialYear = Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate());
            getModel().getFinancialYearMap().put(finYearTemp.getFaYear(), financialYear);
        }
        // Defect_40635 Construction date need to remove. Currently it is mandatory
        LookUp constructioncomplitiondate = null;
        try {
            constructioncomplitiondate = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.Property.CD.toString(),
                    MainetConstants.Property.propPref.PAR.toString(),
                    org);

        } catch (Exception e) {

        }
        if (constructioncomplitiondate != null) {
            getModel().setConstructFlag(constructioncomplitiondate.getOtherField());
        } else {
            getModel().setConstructFlag(MainetConstants.Y_FLAG);
        }
        List<LookUp> lookupList = CommonMasterUtility.lookUpListByPrefix("FCT",
                UserSession.getCurrent().getOrganisation().getOrgid());
        if (lookupList.size() > 1) {

        } else if (lookupList.size() == 1 && lookupList.get(0).getLookUpCode().equals(MainetConstants.CommonConstants.NA)) {
            getModel().setFactorNotApplicable(lookupList.get(0).getLookUpCode());
        }
        if(Utility.isEnvPrefixAvailable(org, "LRI")) {
        	request.setAttribute("integrationOfLr",MainetConstants.Y_FLAG );       	
        }
        if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL)) {
        	getModel().setRoomTypeList(org);
            getModel().setRoomTypeJson(new ObjectMapper().writeValueAsString(getModel().getRoomTypeMap()));
        }
        return defaultResult();
    }

    private void setCommonFields(SelfAssesmentNewModel model) {
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        ServiceMaster service = serviceMaster.getServiceByShortName(MainetConstants.Property.SAS, orgId);
        model.setOrgId(orgId);
        model.setLeastFinYear(iFinancialYear.getMinFinanceYear(orgId));
        model.setServiceMaster(service);
        model.setDeptId(service.getTbDepartment().getDpDeptid());
        model.setServiceId(service.getSmServiceId());
        model.setIntgrtionWithBP(CommonMasterUtility
                .getDefaultValueByOrg(MainetConstants.Property.IBP, UserSession.getCurrent().getOrganisation()).getLookUpCode());
        String lookUp = CommonMasterUtility.getDefaultValueByOrg(MainetConstants.Property.propPref.APP,
                UserSession.getCurrent().getOrganisation()).getLookUpCode();
        model.getProvisionalAssesmentMstDto().setPartialAdvancePayStatus(lookUp);

    }

    @RequestMapping(params = "getCheckList", method = RequestMethod.POST)
    public ModelAndView getCheckList(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        SelfAssesmentNewModel model = this.getModel();
        ProvisionalAssesmentMstDto dto = model.getProvisionalAssesmentMstDto();
        dto.setOrgId(model.getOrgId());
        dto.setSmServiceId(model.getServiceId());
        AtomicInteger flatCount = new AtomicInteger(0);
        if (dto.getBillMethod() != null && MainetConstants.FlagI.equals(CommonMasterUtility
 				.getNonHierarchicalLookUpObject(dto.getBillMethod(), UserSession.getCurrent().getOrganisation())
 				.getLookUpCode())) {
     	   for (ProvisionalAssesmentDetailDto detail : dto.getProvisionalAssesmentDetailDtoList()) {
         	   AtomicBoolean flatExists = new AtomicBoolean(false);
         	   for (ProvisionalAssesmentDetailDto detialDto : dto.getProvisionalAssesmentDetailDtoList()) {
         		   if ((detail.getFaYearId()
     						.equals(dto.getProvisionalAssesmentDetailDtoList()
     								.get(dto.getProvisionalAssesmentDetailDtoList().size() - 1).getFaYearId()))
     						|| (detialDto.getFaYearId() > detail.getFaYearId()
     								&& detialDto.getFlatNo().equals(detail.getFlatNo()))) {
            			flatExists.getAndSet(true);
            		}	
        	    }
         	   if(!flatExists.get()) {
            		flatCount.getAndIncrement();
            	}
     	    }
           
             if(flatCount.intValue() > 0) {
             	model.addValidationError("Make sure the flat number should be same for all years");
             }
             
             if (model.hasValidationErrors()) {
                 return defaultMyResult();
             }
        }
		// 119294
		LookUp checkListApplLookUp = null;
		List<DocumentDetailsVO> docs = null;
		  if(StringUtils.isNotBlank(dto.getAssOldpropno())) {
				boolean checkOldPropNoExist = propertyService.checkOldPropNoExist(dto.getAssOldpropno(),
						UserSession.getCurrent().getOrganisation().getOrgid());
				if(checkOldPropNoExist) {
					model.addValidationError("Already PTIN exist with "+ dto.getAssOldpropno());
				}
           }
		  
		  AtomicBoolean dupOwnerAadharNo = new AtomicBoolean(false);
		  
		  if(dto.getProvisionalAssesmentOwnerDtlDtoList().size() > 1) {
			  dto.getProvisionalAssesmentOwnerDtlDtoList().forEach(ownerDto ->{
				List<ProvisionalAssesmentOwnerDtlDto> ownerAadharList = dto.getProvisionalAssesmentOwnerDtlDtoList()
						.stream().filter(owner -> owner.getAssoAddharno() != null && ownerDto.getAssoAddharno() != null && owner.getAssoAddharno().equals(ownerDto.getAssoAddharno()))
						.collect(Collectors.toList());
				if(!dupOwnerAadharNo.get() && ownerAadharList.size() > 1) {
					model.addValidationError("Same aadhar no for multiple owner is not allowed");
					dupOwnerAadharNo.getAndSet(true);
				}
			  });
		  }
           if (model.hasValidationErrors()) {
               return defaultMyResult();
           }
		ServiceMaster serviceMas = serviceMaster.getServiceByShortName(MainetConstants.Property.SAS, model.getOrgId());
		if (serviceMas != null) {
			checkListApplLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(serviceMas.getSmChklstVerify(),
					ApplicationContextProvider.getApplicationContext().getBean(IOrganisationService.class)
							.getOrganisationById(model.getOrgId()));
			if (StringUtils.equalsIgnoreCase(checkListApplLookUp.getLookUpCode(), MainetConstants.FlagA)) {
				docs = selfAssessmentService.fetchCheckList(dto, model.getProvAsseFactDtlDto());
			}
		}		
		//
        model.setCheckList(docs);
        model.validateBean(dto, SelfAssessmentValidator.class);
        if (docs==null || docs.isEmpty()) {
            model.setDropDownValues(UserSession.getCurrent().getOrganisation());
			if (serviceMas != null && serviceMas.getSmFeesSchedule() != null && serviceMas.getSmFeesSchedule() != 0) {
				getcharges(model);// get charges from BRMS
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
                if (det.getFirstAssesmentDate() != null) {
                    det.setFirstAssesmentStringDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT)
                            .format(det.getFirstAssesmentDate()));
                }
                if (det.getAssdUnitNo() > max) {
                    max = det.getAssdUnitNo();
                }
            }
        }
        model.setMaxUnit(max);
        if (!model.hasValidationErrors()) {
        	model.setSuccessMessage(MainetConstants.Y_FLAG);
        }
        return defaultMyResult();
    }

    @RequestMapping(params = "proceed", method = RequestMethod.POST)
    public ModelAndView confirmToProceed(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        SelfAssesmentNewModel model = this.getModel();
        ProvisionalAssesmentMstDto assMstDto = model.getProvisionalAssesmentMstDto();
        List<DocumentDetailsVO> docs = model.getCheckList();
        docs = fileUpload.prepareFileUpload(docs);
        assMstDto.setDocs(docs);
        model.setDocumentList(new ArrayList<>());
       AtomicInteger flatCount = new AtomicInteger(0);
       if (assMstDto.getBillMethod() != null && MainetConstants.FlagI.equals(CommonMasterUtility
				.getNonHierarchicalLookUpObject(assMstDto.getBillMethod(), UserSession.getCurrent().getOrganisation())
				.getLookUpCode())) {
    	   for (ProvisionalAssesmentDetailDto detail : assMstDto.getProvisionalAssesmentDetailDtoList()) {
        	   AtomicBoolean flatExists = new AtomicBoolean(false);
        	   for (ProvisionalAssesmentDetailDto detialDto : assMstDto.getProvisionalAssesmentDetailDtoList()) {
        		   if ((detail.getFaYearId()
    						.equals(assMstDto.getProvisionalAssesmentDetailDtoList()
    								.get(assMstDto.getProvisionalAssesmentDetailDtoList().size() - 1).getFaYearId()))
    						|| (detialDto.getFaYearId() > detail.getFaYearId()
    								&& detialDto.getFlatNo().equals(detail.getFlatNo()))) {
           			flatExists.getAndSet(true);
           		}	
       	    }
        	   if(!flatExists.get()) {
           		flatCount.getAndIncrement();
           	}
    	    }
          
            if(flatCount.intValue() > 0) {
            	model.addValidationError("Make sure the flat number should be same for all years");
            }
            
            if(StringUtils.isNotBlank(assMstDto.getAssOldpropno())) {
				boolean checkOldPropNoExist = propertyService.checkOldPropNoExist(assMstDto.getAssOldpropno(),
						UserSession.getCurrent().getOrganisation().getOrgid());
				if(checkOldPropNoExist) {
					model.addValidationError("Already PTIN exist with "+ assMstDto.getAssOldpropno());
				}
           }
            if (model.hasValidationErrors()) {
                return defaultMyResult();
            }
       }
        List<CFCAttachment> documentList = selfAssessmentService.preparePreviewOfFileUpload(model.getDocumentList(),
                model.getCheckList());
        model.setDocumentList(documentList);
        model.validateBean(assMstDto, SelfAssessmentValidator.class);
        if (model.hasValidationErrors()) {
            return defaultMyResult();
        }
        model.setDropDownValues(UserSession.getCurrent().getOrganisation());
        
        // check whether charges are applicable or not
        ServiceMaster serviceMas = serviceMaster.getServiceByShortName(MainetConstants.Property.SAS, model.getOrgId());		
		if (serviceMas != null && serviceMas.getSmFeesSchedule() != null && serviceMas.getSmFeesSchedule() != 0) {
			getcharges(model);// get charges from BRMS
		}
        // D#18545 Error MSG for Rule found or not
        model.validateBean(model.getProvisionalAssesmentMstDto(), RuleErrorValidator.class);
        if (model.hasValidationErrors()) {
            return defaultMyResult();
        }
        return new ModelAndView("SelfAssessmentView", MainetConstants.FORM_NAME, model);

    }

    private void getcharges(SelfAssesmentNewModel model) {
        ProvisionalAssesmentMstDto dto = model.getProvisionalAssesmentMstDto();

		if (dto.getBillMethod() != null && MainetConstants.FlagI.equals(CommonMasterUtility
				.getNonHierarchicalLookUpObject(dto.getBillMethod(), UserSession.getCurrent().getOrganisation())
				.getLookUpCode())) {						
				getchargesForIndividualProperty(model);			
		} else {
        ProvisionalAssesmentDetailDto provisionalAssesmentDetailDto = dto.getProvisionalAssesmentDetailDtoList()
                .get(dto.getProvisionalAssesmentDetailDtoList().size() - 1);

        dto.getProvisionalAssesmentDetailDtoList().forEach(detailList -> {
            if (detailList.getFirstAssesmentDate() != null) {
                detailList.setFirstAssesmentStringDate(new SimpleDateFormat(MainetConstants.DATE_FRMAT)
                        .format(detailList.getFirstAssesmentDate()));
            }
            detailList.setLastAssesmentDate(detailList.getFirstAssesmentDate());
        });
        dto.getProvisionalAssesmentDetailDtoList().get(dto.getProvisionalAssesmentDetailDtoList().size() - 1)
                .setLastAssesmentDate(provisionalAssesmentDetailDto.getFirstAssesmentDate());
        selfAssessmentService.setFactorMappingToAssDto(model.getProvAsseFactDtlDto(), dto);
        model.removeMOSFactorForNoBuildingPermissionAndLandProperty(dto);
        Map<Long, BillDisplayDto> taxWiseRebate = new TreeMap<>();// map<TxaId,BillDisplayDto>

        Map<Date, List<BillPresentAndCalculationDto>> schWiseChargeMap = propertyBRMSService.fetchCharges(dto, model.getDeptId(),
                taxWiseRebate, model.getFinYearList(), null, model.getAssType(),model.getAssesmentManualDate(),null);
        List<TbBillMas> billMasList = propertyService.generateNewBill(schWiseChargeMap,
                UserSession.getCurrent().getOrganisation(), model.getDeptId(), null,null);
        List<BillReceiptPostingDTO> reabteRecDtoList = selfAssessmentService.knowkOffDemandLevelRebateAndExemption(billMasList,
                schWiseChargeMap,
                UserSession.getCurrent().getOrganisation(), taxWiseRebate);
        propertyService.updateARVRVInBill(dto, billMasList);
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "SKDCL")) {
        	billMasList.get(0).setBmFromdt(dto.getAssAcqDate());
        }
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
                UserSession.getCurrent().getOrganisation(), model.getDeptId(), null,null);
        Map<String, List<BillDisplayDto>> presentMap = selfAssessmentService.getDisplayMapOfTaxes(billMasList,
                UserSession.getCurrent().getOrganisation(), model.getDeptId(), earlyPayRebate, taxWiseRebate, surCharge);
        dto.setBillTotalAmt(propertyService.getTotalActualAmount(billMasList, earlyPayRebate, taxWiseRebate, surCharge));
        ListIterator<TbBillMas> listIterator = billMasList.listIterator();
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "PSCL")) {
        	while(listIterator.hasNext()) {
            	TbBillMas next = listIterator.next();
            	next.setBmGenDes(MainetConstants.Y_FLAG);
            }
        }
        
        model.setBillMasList(billMasList);
        model.setDisplayMap(presentMap);
		}
    }
    
    // US120052
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

		Map<Long, BillDisplayDto> taxWiseRebate = new TreeMap<>();
		propertyBRMSService.fetchCharges(dto, model.getDeptId(), taxWiseRebate, model.getFinYearList(), null,
				model.getAssType(), model.getAssesmentManualDate(),null);

		List<TbBillMas> billMasList = new ArrayList<>();
		List<BillReceiptPostingDTO> reabteRecDtoList = new ArrayList<>();
		for (String flat : listOfFlats) {
			//selfAssessmentService.setFactorMappingToAssDto(model.getProvAsseFactDtlDto(), dto);
			ProvisionalAssesmentMstDto tempDto = new ProvisionalAssesmentMstDto();
			BeanUtils.copyProperties(dto, tempDto);
			List<ProvisionalAssesmentDetailDto> tempDetailList = dto.getProvisionalAssesmentDetailDtoList().stream()
					.filter(detail -> detail.getFlatNo().equals(flat)).collect(Collectors.toList());

			tempDetailList.get(tempDetailList.size() - 1)
					.setLastAssesmentDate(provisionalAssesmentDetailDto.getFirstAssesmentDate());						
			tempDto.setProvisionalAssesmentDetailDtoList(tempDetailList);
			
			Set<Long> set = new LinkedHashSet<>();
			tempDetailList.stream().filter(p -> set.add(p.getFaYearId())).collect(Collectors.toList());
			List<Long> finYearList = set.stream().collect(Collectors.toList());
			Map<Long, BillDisplayDto> tempTaxWiseRebate = new TreeMap<>();
			Map<Date, List<BillPresentAndCalculationDto>> schWiseChargeMap = propertyBRMSService.fetchCharges(tempDto,
					model.getDeptId(), tempTaxWiseRebate, finYearList, null, model.getAssType(),
					model.getAssesmentManualDate(), null);

			List<TbBillMas> billMasterList = new ArrayList<>();
			billMasterList = propertyService.generateNewBill(schWiseChargeMap,
					UserSession.getCurrent().getOrganisation(), model.getDeptId(), null, flat);
			billMasList.addAll(billMasterList);
			List<BillReceiptPostingDTO> tempReabteRecDtoList = selfAssessmentService
					.knowkOffDemandLevelRebateAndExemption(billMasList, schWiseChargeMap,
							UserSession.getCurrent().getOrganisation(), tempTaxWiseRebate);
			reabteRecDtoList.addAll(tempReabteRecDtoList);
			//As in case of individual and new registration property, no need of tax carry forward.Defect : 148107
			//propertyService.taxCarryForward(billMasList, model.getOrgId());
		}
		model.setBillMasList(billMasList);
		model.setReabteRecDtoList(reabteRecDtoList);
	}
    
    @RequestMapping(params = "getFactorValueDiv", method = RequestMethod.POST)
    public ModelAndView getFactorValueDiv(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @RequestParam(value = "factorCode") String factorCode) {
        this.getModel().bind(httpServletRequest);
        SelfAssesmentNewModel model = this.getModel();
        LookUp lookup = model.getFactorLookup(factorCode);
        model.setFactorPrefix(lookup.getLookUpCode());
        model.setFactorId(lookup.getLookUpId());
        return new ModelAndView("SelfAssessmentUnitSpecificAdditionalInfo", MainetConstants.FORM_NAME, model);
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
        SelfAssesmentNewModel model = this.getModel();
        model.setOwnershipPrefix(ownershipType);
        LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp(ownershipType, MainetConstants.Property.propPref.OWT,
                UserSession.getCurrent().getOrganisation());
        model.setOwnershipTypeValue(lookup.getDescLangFirst());
        model.getProvisionalAssesmentMstDto().setAssOwnerType(lookup.getLookUpId());
        if (!model.getProvisionalAssesmentMstDto()
                .getProvisionalAssesmentOwnerDtlDtoList().isEmpty() && !ownershipType.equals("JO")) {
            ProvisionalAssesmentOwnerDtlDto ownerDto = model.getProvisionalAssesmentMstDto()
                    .getProvisionalAssesmentOwnerDtlDtoList().get(0);
            model.getProvisionalAssesmentMstDto().getProvisionalAssesmentOwnerDtlDtoList().clear();
            model.getProvisionalAssesmentMstDto().getProvisionalAssesmentOwnerDtlDtoList().add(ownerDto);
        }
        return new ModelAndView("SelfAssessmentOwnershipDetailForm", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "getFinancialYear", method = RequestMethod.POST)
    public @ResponseBody Long getyearOfAcquisition(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            String yearOfAcq) {
        this.getModel().bind(httpServletRequest);
        SelfAssesmentNewModel model = this.getModel();
        model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().clear();
        Date currentDate = Utility.stringToDate(yearOfAcq);
        model.getProvAsseFactDtlDto().clear();  // clear dto on year change
        return iFinancialYear.getFinanceYearId(currentDate);

    }

    @RequestMapping(params = "deleteUnitTableRow", method = RequestMethod.POST)
    public @ResponseBody void deleteUnitTableRow(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse, @RequestParam(value = "deletedRowCount") int deletedRowCount) {
        this.getModel().bind(httpServletRequest);
        SelfAssesmentNewModel model = this.getModel();
        if (!model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().isEmpty()
                && model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().size() > deletedRowCount) {
            ProvisionalAssesmentDetailDto detDto = model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList()
                    .get(deletedRowCount);
            model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().remove(detDto);
        }
    }

    @RequestMapping(params = "deleteUnitSpecificInfo", method = RequestMethod.POST)
    public @ResponseBody void deleteUnitSpecificInfo(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse, @RequestParam(value = "deletedUnitRow") Integer deletedUnitRow) {
        this.getModel().bind(httpServletRequest);
        SelfAssesmentNewModel model = this.getModel();
        model.setUnitStatusCount(deletedUnitRow);
        for (ProvisionalAssesmentFactorDtlDto FactorDtlDto : model.getProvAsseFactDtlDto()) {
            if (model.getUnitStatusCount() == deletedUnitRow) {
                model.getProvAsseFactDtlDto().remove(FactorDtlDto);
            }
        }
    }

    @RequestMapping(params = "deleteFactorStatus", method = RequestMethod.POST)
    public @ResponseBody void deleteFactorStatus(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse, @RequestParam(value = "factorCode") String factorCode) {
        this.getModel().bind(httpServletRequest);
        SelfAssesmentNewModel model = this.getModel();
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
        SelfAssesmentNewModel model = this.getModel();
        if (!model.getProvisionalAssesmentMstDto().getProvisionalAssesmentOwnerDtlDtoList().isEmpty()
                && model.getProvisionalAssesmentMstDto().getProvisionalAssesmentOwnerDtlDtoList().size() > deletedOwnerRowId) {
            ProvisionalAssesmentOwnerDtlDto detDto = model.getProvisionalAssesmentMstDto()
                    .getProvisionalAssesmentOwnerDtlDtoList()
                    .get(deletedOwnerRowId);
            model.getProvisionalAssesmentMstDto().getProvisionalAssesmentOwnerDtlDtoList().remove(detDto);
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
        SelfAssesmentNewModel model = this.getModel();
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

    @RequestMapping(params = "editSelfAssForm", method = RequestMethod.POST)
    public ModelAndView editForm(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        if (!MainetConstants.Property.Bifurcation.equals(getModel().getSelfAss()))
            getModel().setSelfAss(null);
        SelfAssesmentNewModel model = this.getModel();
        /*
         * getModel().setSchedule(selfAssessmentService
         * .getAllBillscheduleWithoutCurrentScheduleByOrgid(UserSession.getCurrent().getOrganisation()));
         */
        Long max = 0l;
        List<ProvisionalAssesmentDetailDto> detailDto = model.getProvisionalAssesmentMstDto()
                .getProvisionalAssesmentDetailDtoList();
        if (detailDto != null && !detailDto.isEmpty()) {
            for (ProvisionalAssesmentDetailDto dto : detailDto) {
                dto.getProvisionalAssesmentFactorDtlDtoList().clear();
                if (dto.getAssdUnitNo() > max) {
                    max = dto.getAssdUnitNo();
                }
            }
        }
        model.setMaxUnit(max);
        if (model.getAssType().equals(MainetConstants.Property.ASESS_AUT)) {
            loadDateForAuthorization(model);
        }
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)
        		&& MainetConstants.Property.ASESS_AUT.equals(model.getAssType())) {
        	model.setSelfAss(MainetConstants.FlagE);// to identify from Authorization Edit
        }
        if (MainetConstants.Property.Bifurcation.equals(model.getAssType())
                || MainetConstants.Property.Bifurcation.equals(model.getSelfAss())) {
            return new ModelAndView("BifurcationValidn", MainetConstants.FORM_NAME, model);
        } else {
            return new ModelAndView("SelfAssessmentFormEdit", MainetConstants.FORM_NAME, model);
        }
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

    private void loadDateForAuthorization(SelfAssesmentNewModel model) {
        List<LookUp> locList = getModel().getLocation();
        setFactorMap(model.getProvisionalAssesmentMstDto());
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

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(params = "downloadSheet", method = RequestMethod.GET)
    public void exportRateExcelData(final HttpServletResponse response, final HttpServletRequest request) {
        ProvisionalAssesmentMstDto dto = getModel().getProvisionalAssesmentMstDto();
        List<ExcelSheetDto> dtoList = new LinkedList<>();
        if (this.getModel().getBillMasList() != null && !this.getModel().getBillMasList().isEmpty()) {
            TbBillMas currBill = new TbBillMas();
            List<TbBillMas> billMasList = new ArrayList<>();
            List<TbBillDet> billList = new ArrayList<>();
            getModel().getDisplayMap().forEach((key, value) -> {
                value.forEach(tax -> {
                    TbBillDet det = new TbBillDet();
                    det.setBdCurTaxamt(tax.getCurrentYearTaxAmt().doubleValue());
                    det.setTaxId(tax.getTaxId());
                    det.setTaxDesc(tax.getTaxDesc());
                    billList.add(det);
                });
            });
            currBill.setTbWtBillDet(billList);
            this.getModel().getBillMasList().forEach(billMas -> {

                if (Utility.comapreDates(
                        this.getModel().getBillMasList().get(this.getModel().getBillMasList().size() - 1).getBmFromdt(),
                        billMas.getBmFromdt())) {
                    billMasList.add(currBill);
                } else {
                    billMasList.add(billMas);
                }
            });
            ExcelSheetDto excelHeadDto = new ExcelSheetDto();
            excelHeadDto.setTaxName(getApplicationSession().getMessage("propertyTax.TaxName"));
            excelHeadDto.setSrNo(getApplicationSession().getMessage("propertyTax.SrNo"));
            List<String> taxAmountList = new LinkedList<>();
            AtomicInteger count = new AtomicInteger(1);
            List<LookUp> billSchList = selfAssessmentService
                    .getAllBillschedulByOrgid(UserSession.getCurrent().getOrganisation());
            this.getModel().getBillMasList().forEach(mas -> {
                BillingScheduleDetailEntity billSchDet = billingScheduleService.getSchedulebySchFromDate(
                        UserSession.getCurrent().getOrganisation().getOrgid(), mas.getBmFromdt());
                billSchList.parallelStream().filter(lookup -> billSchDet.getSchDetId().equals(lookup.getLookUpId()))// enter
                                                                                                                    // in
                                                                                                                    // loop
                                                                                                                    // if
                                                                                                                    // tax
                                                                                                                    // cat
                                                                                                                    // is
                        .forEach(lookup -> {
                            taxAmountList.add(lookup.getLookUpCode());
                        });
            });
            excelHeadDto.setTaxAmount(taxAmountList);// Setting Schedule name as header
            dtoList.add(excelHeadDto);// Excel Sheet Header Row as Header is also Dynamic
            // Taking all taxes from current Bill as tax carry forward
            currBill.getTbWtBillDet().forEach(taxDet -> {// for each Tax row
                ExcelSheetDto excelDto = new ExcelSheetDto();
                List<String> taxAmount = new LinkedList<>();
                excelDto.setTaxName(taxDet.getTaxDesc());// tax name
                billMasList.forEach(mas -> {
                    boolean hasTax = mas.getTbWtBillDet().stream()
                            .anyMatch(billDetail -> billDetail.getTaxId().equals(taxDet.getTaxId()));
                    mas.getTbWtBillDet().parallelStream().filter(det -> taxDet.getTaxId().equals(det.getTaxId()))
                            .forEach(det -> {
                                taxAmount.add(Double.toString(det.getBdCurTaxamt()));
                            });
                    if (!hasTax) {
                        taxAmount.add("0.0");
                    }
                });
                excelDto.setSrNo(count.toString());
                count.incrementAndGet();
                excelDto.setTaxAmount(taxAmount);
                dtoList.add(excelDto);
            });

            /*
             * ExcelSheetDto excelTotalDto = new ExcelSheetDto(); excelTotalDto.setTaxName("Total"); List<String> totAmount = new
             * LinkedList<>(); this.getModel().getBillMasList().forEach(mas -> { totAmount.add(Double.toString(mas.getBmToatlInt()
             * + mas.getBmTotalAmount())); });
             */
            // excelTotalDto.setTaxAmount(totAmount);
            // excelTotalDto.setBillTotalAmt(dto.getBillTotalAmt());
            // dtoList.add(excelTotalDto);// Last row of Excel Sheet
        }
        try {
            WriteExcelData data = new WriteExcelData("Tax_Wise_Details_Sheet" + ".xlsx", request, response);
            data.getExpotedExcelSheet(dtoList, ExcelSheetDto.class, dto.getBillTotalAmt());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(params = "fetchPropertyDetilsByMobileNo", method = RequestMethod.POST)
    public @ResponseBody ModelAndView fetchPropertyDetilsByMobileNo(HttpServletRequest httpServletRequest,
            @RequestParam(value = "mobileNo") String mobileNo) {
        getModel().bind(httpServletRequest);
        SelfAssesmentNewModel model = this.getModel();
        ProperySearchDto searchDto = new ProperySearchDto();
        searchDto.setMobileno(mobileNo);
        searchDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        if(!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), PrefixConstants.ENV.MCS)) {
        	List<PropertyCommonInfoDto> propCommDtoList = selfAssessmentService.searchPropertyDetails(searchDto, null, null, null,
                model.getLocation());
        	if (propCommDtoList != null && !propCommDtoList.isEmpty()) {
            model.setPropCommonDtoList(propCommDtoList);
            return new ModelAndView("commonPropertyDetails", MainetConstants.FORM_NAME, model);
        	} 
        }       
		return null;
    }

    @RequestMapping(params = "getLandTypeDetails", method = RequestMethod.POST)
    public ModelAndView getLandTypeDetailsDiv(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @RequestParam(value = "landType") String landType) {
        this.getModel().bind(httpServletRequest);
        SelfAssesmentNewModel model = this.getModel();
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
        SelfAssesmentNewModel model = this.getModel();
        model.setArrayOfKhasraDetails(response);

        return new ModelAndView("showPropertyKhasaraDetails", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "getNajoolAndDiversionDetails", method = RequestMethod.POST)
    public ModelAndView getNajoolAndDiversionDetails(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse, @RequestParam("landType") String landType,
            @RequestParam("plotNo") String plotNo, @RequestParam("districtId") String districtId,
            @RequestParam("TehsilId") String TehsilId, @RequestParam("villageId") String villageId,
            @RequestParam("mohallaId") String mohallaId, @RequestParam("streetNo") String streetNo) {
        SelfAssesmentNewModel model = this.getModel();
        // model.setLandTypePrefix(landType);
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
        } else {
            ArrayOfDiversionPlotDetails response = dataEntrySuiteService.getDiversionDetails(dto);
            model.setArrayOfDiversionPlotDetails(response);
        }

        return new ModelAndView("showPropertyKhasaraDetails", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "getLandTypeApiDetails", method = RequestMethod.POST)
    public ModelAndView getLandTypeApiDetails(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse) {
        SelfAssesmentNewModel model = this.getModel();
        LandTypeApiDetailRequestDto dto = new LandTypeApiDetailRequestDto();
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

    @RequestMapping(params = "getKhasraNoList", method = { RequestMethod.POST, RequestMethod.GET })
    public @ResponseBody List<LookUp> getKhasraNoList(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse, @RequestParam("villageId") String villageId,
            @RequestParam("tehsilId") String tehsilId, @RequestParam("districtId") String districtId,
            @RequestParam(value = "landType") String landType, @RequestParam("khasara") String khasara) {
        ProvisionalAssesmentMstDto assDto = this.getModel().getProvisionalAssesmentMstDto();
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
        List<LookUp> diversionPlotList = dataEntrySuiteService.getDiversionPlotList(dto);
        getModel().setPlotNo(diversionPlotList);
        return diversionPlotList;
    }

    @RequestMapping(params = "deleteLandEntry", method = RequestMethod.POST)
    public @ResponseBody void deleteLandEntry(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        this.getModel().bind(httpServletRequest);
        SelfAssesmentNewModel model = this.getModel();
        model.getProvisionalAssesmentMstDto().setAssLandType(null);
        model.getProvisionalAssesmentMstDto().setAssLandTypeDesc(null);
        model.setLandTypePrefix(null);
        model.setChangeLandTypePrefix(null);
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
    
    //97207 By Arun
    @RequestMapping(params = "addRoomDetails", method = RequestMethod.POST)
	public ModelAndView addRoomDetails(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		List<ProvisionalAssesmentDetailDto> detailList = getModel().getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList();
		if(CollectionUtils.isNotEmpty(detailList)) {
			httpServletRequest.setAttribute("carpetArea",
					detailList.get(Integer.parseInt(getModel().getCountOfRow())).getCarpetArea());
		}	
		return new ModelAndView("SelfAssessRoomDetails", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(params = "removeRoomDetails", method = RequestMethod.POST)
	public @ResponseBody Boolean removeRoomDetails(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, @RequestParam("countOfRow") Long countOfRow,
			@RequestParam("index") Long index) {
		this.getModel().bind(httpServletRequest);
		SelfAssesmentNewModel model = this.getModel();
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
		ProvisionalAssesmentDetailDto detailDto=getModel().getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList()
				.get(Integer.parseInt(getModel().getCountOfRow()));
		List<Double> lengthList=new ArrayList<>();
		List<Double> widthList=new ArrayList<>();
		List<Double> areaList=new ArrayList<>();
		for(PropertyRoomDetailsDto room:detailDto.getRoomDetailsDtoList()) {
			if(room.getRoomLength() != null && room.getRoomWidth() != null) {
				lengthList.add(room.getRoomLength());
				widthList.add(room.getRoomWidth());
			}
		}
		if(CollectionUtils.isNotEmpty(lengthList) && CollectionUtils.isNotEmpty(widthList)) {
			List<Long> roomTypeList = detailDto.getRoomDetailsDtoList().stream().map(PropertyRoomDetailsDto::getRoomType).collect(Collectors.toList());
			for (int i = 0; i < lengthList.size(); i++) {
				Double area = lengthList.get(i) * widthList.get(i);
				Double calArea=0.0;
				if(MainetConstants.Property.ZERO.equals(getModel().getRoomTypeMap().get(roomTypeList.get(i)))){
					calArea = area * 0.0;
				}else if(MainetConstants.Property.HALF.equals(getModel().getRoomTypeMap().get(roomTypeList.get(i)))){
					calArea = area / 2;
				}else {
					calArea = area;
				}
				areaList.add(calArea);
			}
			detailDto.setAssdBuildupArea(areaList.stream().mapToDouble(Double::doubleValue).sum());
			detailDto.setCarpetArea(detailDto.getRoomDetailsDtoList().stream().mapToDouble(PropertyRoomDetailsDto::getRoomArea).sum());
			getModel().getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().set(Integer.parseInt(getModel().getCountOfRow()), detailDto);
		}
		return new ModelAndView("SelfAssessmentFormValidn", MainetConstants.FORM_NAME,  this.getModel());
	}
	
	@RequestMapping(params = "setRoomDetails", method = RequestMethod.POST)
	public @ResponseBody Boolean setRoomDetails(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);

		if (CollectionUtils
				.isNotEmpty(getModel().getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList())) {
			int size = getModel().getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().size();
			Long finYear = getModel().getFinYearList().get(0);
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (getModel().getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().get(i)
							.getFaYearId().equals(finYear)
							&& getModel().getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().get(i)
									.getAssdUnitNo().equals(getModel().getProvisionalAssesmentMstDto()
											.getProvisionalAssesmentDetailDtoList().get(j).getAssdUnitNo())) {
						ProvisionalAssesmentDetailDto floorDetailList = getModel().getProvisionalAssesmentMstDto()
								.getProvisionalAssesmentDetailDtoList().get(i);
						getModel().getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().get(j)
								.setRoomDetailsDtoList(floorDetailList.getRoomDetailsDtoList());
					}

				}

			}
		}

		return true;
	}
	// end

	 @RequestMapping(params = "getUnitCount", method = RequestMethod.POST)
	    public @ResponseBody Long getUnitCount(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
	            String yearOfAcq) {
	        this.getModel().bind(httpServletRequest);
	        SelfAssesmentNewModel model = this.getModel();
	        
	        if(model.getProvisionalAssesmentMstDto() != null && CollectionUtils.isNotEmpty(model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList())) {
	        	 Long faYearId = model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().get(0).getFaYearId();
	 	        List<ProvisionalAssesmentDetailDto> oneYearList = model.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList().stream().filter(provDetail -> provDetail.getFaYearId().equals(faYearId)).collect(Collectors.toList());
	 	        Long unitCount = (long) (oneYearList.size() + oneYearList.size());
	 	       return unitCount;
	        }else {
	        	return 2L;
	        }
	       
	         

	    }
	 
	 @RequestMapping(params = "getOtherFieldVal", method = RequestMethod.POST)
	public @ResponseBody String getOtherFieldVal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestParam("usagesubtype") String usagesubtype) {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		String zero = String.valueOf(MainetConstants.NUMBERS.ZERO);
		LookUp lookUp = CommonMasterUtility.getHieLookupByLookupCode(usagesubtype, MainetConstants.Property.propPref.USA,
				MainetConstants.NUMBERS.TWO, orgId);
		if(null != lookUp) {
			 String otherField = tbComparentDetJpaRepository.getOtherFieldVal(lookUp.getLookUpParentId(),
					lookUp.getLookUpId(), orgId);
			 if(StringUtils.isNotBlank(otherField))
			 return otherField;
		}
		return zero;

	}
}
