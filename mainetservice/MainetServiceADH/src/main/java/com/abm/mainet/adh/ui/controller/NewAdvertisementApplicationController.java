package com.abm.mainet.adh.ui.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.abm.mainet.adh.datamodel.ADHRateMaster;
import com.abm.mainet.adh.dto.AdvertiserMasterDto;
import com.abm.mainet.adh.dto.NewAdvertisementApplicationDetDto;
import com.abm.mainet.adh.dto.NewAdvertisementApplicationDto;
import com.abm.mainet.adh.dto.NewAdvertisementReqDto;
import com.abm.mainet.adh.service.ADHCommonService;
import com.abm.mainet.adh.service.IAdvertiserMasterService;
import com.abm.mainet.adh.service.IBRMSADHService;
import com.abm.mainet.adh.ui.model.NewAdvertisementApplicationModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.LocationOperationWZMapping;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.brms.datamodel.CheckListModel;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.mapper.FileUploadValidator;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.validitymaster.dto.LicenseValidityMasterDto;
import com.abm.mainet.validitymaster.service.ILicenseValidityMasterService;

/**
 * @author vishwajeet.kumar
 * @since 23 Sept 2019
 */

@Controller
@RequestMapping(MainetConstants.AdvertisingAndHoarding.NEW_ADV_APP_HTML)
public class NewAdvertisementApplicationController extends AbstractFormController<NewAdvertisementApplicationModel> {

    @Autowired
    private BRMSCommonService brmsCommonService;

    @Autowired
    private TbServicesMstService servicesMstService;

    @Autowired
    private IAdvertiserMasterService advertiserMasterService;

    @Autowired
    private IBRMSADHService brmsadhService;

    @Autowired
    private ILocationMasService locationMasService;
    
    @Autowired
	ILicenseValidityMasterService licenseValidityMasterService;
    
    private static final Logger LOGGER = Logger.getLogger(NewAdvertisementApplicationController.class);

    /**
     * this method is used for return first page of new Advertisement application
     * @param request
     * @return
     */
    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(HttpServletRequest request) {
        sessionCleanup(request);
        ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class).sessionCleanUpForFileUpload();
        getModel().bind(request);
        ServiceMaster master = servicesMstService.findShortCodeByOrgId(
                MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE,
                UserSession.getCurrent().getOrganisation().getOrgid());
        this.getModel().setDeptId(master.getTbDepartment().getDpDeptid());
        this.getModel()
                .setLocationMasList(
                        ApplicationContextProvider.getApplicationContext().getBean(ILocationMasService.class).getlocationByDeptId(
                                master.getTbDepartment().getDpDeptid(), UserSession.getCurrent().getOrganisation().getOrgid()));

        this.getModel()
                .setLicMaxTenureDays(ApplicationContextProvider.getApplicationContext().getBean(ADHCommonService.class)
                        .calculateLicMaxTenureDays(master.getTbDepartment().getDpDeptid(), master.getSmServiceId(),
                                null, UserSession.getCurrent().getOrganisation().getOrgid(),MainetConstants.ZERO_LONG));
        
        return new ModelAndView(MainetConstants.AdvertisingAndHoarding.NEW_ADVERTISEMENT_APPLICATION,
                MainetConstants.FORM_NAME, this.getModel());
    }

    /**
     * get Agency Name By OrgId
     * @param advertiserCategoryId
     * @return
     */

    @RequestMapping(params = MainetConstants.AdvertisingAndHoarding.GET_AGENCY_NAME, method = RequestMethod.POST)
    public @ResponseBody List<AdvertiserMasterDto> getAgencyNameByOrgId(
            @RequestParam(MainetConstants.AdvertisingAndHoarding.ADVERTSIER_CAT) Long advertiserCategoryId) {

        List<AdvertiserMasterDto> advertiserMasterList = advertiserMasterService
                .getAgencyDetailsByAgencyCategoryAndOrgId(advertiserCategoryId,
                        UserSession.getCurrent().getOrganisation().getOrgid());
        return advertiserMasterList;
    }

    @RequestMapping(params = "getPropertyDetails", method = RequestMethod.POST)
    public ModelAndView getPropertyDetails(HttpServletRequest request) {
        bindModel(request);
        ModelAndView modelAndView = null;
        NewAdvertisementApplicationModel model = this.getModel();
        NewAdvertisementReqDto advertisementReqDto = model.getAdvertisementReqDto();
        NewAdvertisementApplicationDto reqDto = model.getAdvertisementReqDto().getAdvertisementDto();
        reqDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        NewAdvertisementApplicationDto applicationDto = advertiserMasterService.getPropertyDetailsByPropertyNumber(reqDto);
        String respMsg = "";
        if (applicationDto != null) {
            reqDto.setPropOutstandingAmt(applicationDto.getPropOutstandingAmt());
            reqDto.setPropOwnerName(applicationDto.getPropOwnerName());
            reqDto.setPropAddress(applicationDto.getPropAddress());
            model.setAdvertisementReqDto(advertisementReqDto);
            if(applicationDto.getAssessmentCheckFlag().equals("N")) {
            	respMsg = "Assessment Not done";
            	model
                .addValidationError(respMsg);
            	modelAndView = new ModelAndView("NewAdvertisementApplicationValidn",
                        MainetConstants.CommonConstants.COMMAND, this.getModel());
                return  modelAndView.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
                        getModel().getBindingResult());	
            }
        } else {
            respMsg = ApplicationSession.getInstance().getMessage("adh.new.advertisement.validation.property.not.found");
            /*return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.ERR_MSG, respMsg);*/
            model
            .addValidationError(respMsg +" "+ reqDto.getPropNumber());
            modelAndView = new ModelAndView("NewAdvertisementApplicationValidn",
                    MainetConstants.CommonConstants.COMMAND, this.getModel());
            return  modelAndView.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
                    getModel().getBindingResult());
        }
        return defaultMyResult();
    }

    /**
     * get Applicable Check List From BRMS
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.AdvertisingAndHoarding.APPLICABLE_CHECKLIST_CHARGE)
    public ModelAndView getApplicableCheckListFromBRMS(final HttpServletRequest request) {
    	this.getModel().getAdvertisementReqDto().getAdvertisementDto().setNewAdvertDetDtos(new ArrayList<NewAdvertisementApplicationDetDto>());
        this.getModel().bind(request);
        ModelAndView modelAndView = null;
        if (this.getModel().validateInputs()) {
            Boolean isSameAdvertiser=true;
			// Defect #127337
			final long AdType = this.getModel().getAdvertisementReqDto().getAdvertisementDto().getNewAdvertDetDtos()
					.get(0).getAdhTypeId1();
			int count = 0;
			for (NewAdvertisementApplicationDetDto dto : this.getModel().getAdvertisementReqDto().getAdvertisementDto()
					.getNewAdvertDetDtos()) {

				if (count > 0) {
					if (String.valueOf(AdType).equals(String.valueOf(dto.getAdhTypeId1()))) {

					} else {
						isSameAdvertiser=false;
						this.getModel().addValidationError(getApplicationSession().getMessage("advertiser.master.validate.advertiser.same"));
					}
				}
				count++;
			}
			if (!(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)))
				getPropertyDetailsByPropertyNuber( this.getModel());
			if (this.getModel().getBindingResult() != null && this.getModel().getBindingResult().hasErrors()) {
	            modelAndView = new ModelAndView("NewAdvertisementApplicationValidn",
	                    MainetConstants.CommonConstants.COMMAND, this.getModel());
	            modelAndView.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
	                    getModel().getBindingResult());
	            this.getModel().setEnableSubmit(MainetConstants.FlagN);
	            if (!this.getModel().getCheckList().isEmpty())
	                this.getModel().getCheckList().clear();
	            return modelAndView;
	        }
			if(isSameAdvertiser)
			findApplicableCheckList(request);
            this.getModel().setEnableSubmit(MainetConstants.FlagY);
            modelAndView = new ModelAndView(MainetConstants.AdvertisingAndHoarding.NEW_ADVERTISEMENT_DATA,
                    MainetConstants.FORM_NAME, this.getModel());
        }
        if (this.getModel().getBindingResult() != null && this.getModel().getBindingResult().hasErrors()) {
            modelAndView = new ModelAndView("NewAdvertisementApplicationValidn",
                    MainetConstants.CommonConstants.COMMAND, this.getModel());
            modelAndView.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
                    getModel().getBindingResult());
            this.getModel().setEnableSubmit(MainetConstants.FlagN);
            if (!this.getModel().getCheckList().isEmpty())
                this.getModel().getCheckList().clear();
        }
        return modelAndView;
    }

    @SuppressWarnings("unchecked")
    private void findApplicableCheckList(HttpServletRequest request) {
        getModel().bind(request);
        NewAdvertisementApplicationModel applicationModel = this.getModel();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        ServiceMaster master = servicesMstService
                .findShortCodeByOrgId(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE, orgId);
        this.getModel().getAdvertisementReqDto().getAdvertisementDto()
                .setAgencyId(this.getModel().getAdvertisementReqDto().getAdvertisementDto().getAgencyId());
        WSRequestDTO initRequestDto = new WSRequestDTO();
        String chkApplicableOrNot = CommonMasterUtility.getCPDDescription(master.getSmChklstVerify(),
                MainetConstants.FlagV, UserSession.getCurrent().getOrganisation().getOrgid());
        initRequestDto.setModelName(MainetConstants.AdvertisingAndHoarding.CHECKLIST_ADHRATEMASTER);
        WSResponseDTO response = brmsCommonService.initializeModel(initRequestDto);
        if (response.getWsStatus() != null
                && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
        	long cnt = 1;
        	List<DocumentDetailsVO> checklistDoc = Collections.emptyList();
        	List<DocumentDetailsVO> checklistDocs=new ArrayList<>();
        	for(NewAdvertisementApplicationDetDto dto:applicationModel.getAdvertisementReqDto().getAdvertisementDto()
                    .getNewAdvertDetDtos()) {
            if (chkApplicableOrNot.equalsIgnoreCase(MainetConstants.FlagA)) {
                List<Object> checklist = RestClient.castResponse(response, CheckListModel.class, 0);
                CheckListModel checkListModel = (CheckListModel) checklist.get(0);
                checkListModel.setOrgId(orgId);
                checkListModel.setFactor3(CommonMasterUtility
                        .getHierarchicalLookUp(dto.getAdhTypeId1(), UserSession.getCurrent().getOrganisation())
                        .getDescLangFirst());
                checkListModel.setFactor4(CommonMasterUtility
                        .getHierarchicalLookUp(dto.getAdhTypeId2(), UserSession.getCurrent().getOrganisation())
                        .getDescLangFirst());
                checkListModel.setServiceCode(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE);
                final WSRequestDTO checkRequestDto = new WSRequestDTO();
                checkRequestDto.setDataModel(checkListModel);
                WSResponseDTO checkListResponse = brmsCommonService.getChecklist(checkRequestDto);
					if (checkListResponse.getWsStatus() != null && MainetConstants.WebServiceStatus.SUCCESS
							.equalsIgnoreCase(checkListResponse.getWsStatus())) {
						checklistDoc = (List<DocumentDetailsVO>) checkListResponse.getResponseObj();
						if (checklistDoc != null && !checklistDoc.isEmpty()) {
							// long cnt = 1;
							for (final DocumentDetailsVO doc : checklistDoc) {
								doc.setDocumentSerialNo(cnt);
								if (!checklistDocs.stream().anyMatch(docs -> docs.getDoc_DESC_ENGL() != null
										&& docs.getDoc_DESC_ENGL().equals(doc.getDoc_DESC_ENGL()))) {
									checklistDocs.add(doc);
									cnt++;
								}
								// checklistDocs.add(doc);
							}

						}
						// this.getModel().setCheckList(checklistDoc);
					} else {
						applicationModel.addValidationError(
								getApplicationSession().getMessage("adh.problem.while.getting.checklist"));
						LOGGER.error("Problem while getting CheckList");
					}
				}
			}
        	this.getModel().setCheckList(checklistDocs);
            Organisation organisation = new Organisation();
            organisation.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
            List<Object> adhRateMasterList = RestClient.castResponse(response, ADHRateMaster.class, 1);
            ADHRateMaster adhRateMaster = (ADHRateMaster) adhRateMasterList.get(0);
            WSRequestDTO taxReqDTO = new WSRequestDTO();
            adhRateMaster.setOrgId(orgId);
            adhRateMaster.setServiceCode(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE);
            LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
                    MainetConstants.AdvertisingAndHoarding.APL, PrefixConstants.ADVERTISEMENT_AND_HOARDING_PREFIX.CAA,
                    organisation);
            adhRateMaster.setChargeApplicableAt(Long.toString(chargeApplicableAt.getLookUpId()));
            //new code added to set lenght , height and ward,zone
            LocationOperationWZMapping wzMapping = locationMasService.findbyLocationAndDepartment(
        			this.getModel().getAdvertisementReqDto().getAdvertisementDto().getLocId(),
        			master.getTbDepartment().getDpDeptid());
            if(wzMapping != null && wzMapping.getCodIdOperLevel1() != null)
                adhRateMaster.setZone(CommonMasterUtility.getHierarchicalLookUp(wzMapping.getCodIdOperLevel1(), UserSession.getCurrent().getOrganisation()).getDescLangFirst());  
                if(wzMapping != null && wzMapping.getCodIdOperLevel2() != null)
                adhRateMaster.setWard(CommonMasterUtility.getHierarchicalLookUp(wzMapping.getCodIdOperLevel2(), UserSession.getCurrent().getOrganisation()).getDescLangFirst());
                //adhRateMaster.setLenght( this.getModel().getAdvertisementReqDto().getAdvertisementDto().getNewAdvertDetDtos().get(0).getAdvDetailsLength().longValue());
                //adhRateMaster.setHeight( this.getModel().getAdvertisementReqDto().getAdvertisementDto().getNewAdvertDetDtos().get(0).getAdvDetailsHeight().longValue());
                taxReqDTO.setDataModel(adhRateMaster);
            final WSResponseDTO taxResponseDTO = brmsadhService.getApplicableTaxes(taxReqDTO);
            if (taxResponseDTO.getWsStatus() != null
                    && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(taxResponseDTO.getWsStatus())) {

                if (!taxResponseDTO.isFree()) {
                    final List<Object> rates = (List<Object>) taxResponseDTO.getResponseObj();
                    final List<ADHRateMaster> requiredCHarges = new ArrayList<>();
                    for (final Object rate : rates) {
                        ADHRateMaster master1 = (ADHRateMaster) rate;
                        master1.setOrgId(orgId);
                        master1.setServiceCode(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE);
                        master1.setDeptCode(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE);
                        master1.setRateStartDate(new Date().getTime());
                        LookUp lookup1=CommonMasterUtility.getNonHierarchicalLookUpObject(applicationModel.getAdvertisementReqDto().getAdvertisementDto().getLicenseType(), UserSession.getCurrent().getOrganisation());
                        master1.setLicenseType(lookup1.getDescLangFirst());
                        
                        if ((Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL))
                 		   || (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_ASCL))) {
	                 	   //setting no0f days for temporary license 
	                 	   if(this.getModel().getAdvertisementReqDto().getAdvertisementDto().getLicenseFromDate() !=null
	                        		&& this.getModel().getAdvertisementReqDto().getAdvertisementDto().getLicenseFromDate() != null
	                        		&& lookup1.getDefaultVal() == MainetConstants.FlagT) {
		                 		Date licFromDate = this.getModel().getAdvertisementReqDto().getAdvertisementDto().getLicenseFromDate();
		               	        Date licToDate = this.getModel().getAdvertisementReqDto().getAdvertisementDto().getLicenseToDate();
		                 		long noOfDays = getNoOfDays(licFromDate, licToDate);
		            	        master1.setNoOfDays(noOfDays);
	                 	   }
                       }
                        double area=0;
                        if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SUDA)) {
                        	master1.setLocationCategory(locationMasService.getLocationNameById(this.getModel().getAdvertisementReqDto().getAdvertisementDto().getLocId(), orgId));
                        	if(this.getModel().getAdvertisementReqDto().getAdvertisementDto()!=null&&CollectionUtils.isNotEmpty(this.getModel().getAdvertisementReqDto().getAdvertisementDto().getNewAdvertDetDtos())){
                        		for(NewAdvertisementApplicationDetDto nDto:this.getModel().getAdvertisementReqDto().getAdvertisementDto().getNewAdvertDetDtos()) {
                        			if(nDto.getAdvDetailsArea()!=null && nDto.getUnit()!=null)
                        			area=area+(nDto.getAdvDetailsArea().multiply(new BigDecimal(nDto.getUnit()))).doubleValue();
                        		}
                        	}master1.setArea(area);
                        }
                        
                        	//setting subType
                       if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)) {
                    	   	if(applicationModel.getUlbOwned()!=null) {
                    	   		master1.setLocationCategory(applicationModel.getUlbOwned());
                    	   	}
		                    for (NewAdvertisementApplicationDetDto applicationDetDto : this.getModel().getAdvertisementReqDto().getAdvertisementDto().getNewAdvertDetDtos()) {
		                    	
		                        if (applicationDetDto.getAdhTypeId1() != null) {
		                        	master1.setUsageSubtype1(CommonMasterUtility
		                                    .getHierarchicalLookUp(applicationDetDto.getAdhTypeId1(), organisation)
		                                    .getDescLangFirst());
		                        }
		
		                        if (applicationDetDto.getAdhTypeId2() != null) {
		                        	master1.setUsageSubtype2(CommonMasterUtility
		                                    .getHierarchicalLookUp(applicationDetDto.getAdhTypeId2(), organisation)
		                                    .getDescLangFirst());
		                        }
		                        if (applicationDetDto.getAdhTypeId3() != null) {
		                        	master1.setUsageSubtype3(CommonMasterUtility
		                                    .getHierarchicalLookUp(applicationDetDto.getAdhTypeId3(), organisation)
		                                    .getDescLangFirst());
		                        }
		                        if (applicationDetDto.getAdhTypeId4() != null) {
		                        	master1.setUsageSubtype4(CommonMasterUtility
		                                    .getHierarchicalLookUp(applicationDetDto.getAdhTypeId4(), organisation)
		                                    .getDescLangFirst());
		                        }
		                        if (applicationDetDto.getAdhTypeId5() != null) {
		                        	master1.setUsageSubtype5(CommonMasterUtility
		                                    .getHierarchicalLookUp(applicationDetDto.getAdhTypeId5(), organisation)
		                                    .getDescLangFirst());
		                        }
		                        
		                    }
                       }
                        requiredCHarges.add(master1);
                    }
                    
                 
                    WSRequestDTO chargeReqDTO = new WSRequestDTO();
                    chargeReqDTO.setDataModel(requiredCHarges);
                    WSResponseDTO applicableCharges = brmsadhService.getApplicableCharges(chargeReqDTO);
                    final List<ChargeDetailDTO> output = (List<ChargeDetailDTO>) applicableCharges.getResponseObj();
                    if (output == null) {
                        applicationModel.addValidationError(
                                getApplicationSession().getMessage("adh.charges.not.found.brms.sheet"));
                        LOGGER.error("Charges not Found in brms Sheet");
                    } else {
                        applicationModel.setChargesInfo(newChargesToPay(output));
                        applicationModel.setAmountToPay(chargesToPay(applicationModel.getChargesInfo()));
                        if (applicationModel.getAmountToPay() == 0.0d) {
                            applicationModel.addValidationError(getApplicationSession().getMessage(
                                    "adh.service.charge.amountToPay.cannot.be") + applicationModel.getAmountToPay()
                                    + getApplicationSession().getMessage("adh.if.service.configured.as.chargeable"));
                            LOGGER.error("Service charge amountToPay cannot be " + applicationModel.getAmountToPay()
                                    + " if service configured as Chargeable");
                        }
                        Double lenght = this.getModel().getAdvertisementReqDto().getAdvertisementDto().getNewAdvertDetDtos().get(0).getAdvDetailsLength().doubleValue();
                        Double height = this.getModel().getAdvertisementReqDto().getAdvertisementDto().getNewAdvertDetDtos().get(0).getAdvDetailsHeight().doubleValue();
                       for (final ChargeDetailDTO charge : output) {
                    	   
                    	   if(charge.getLength() != null && charge.getLength() > 0.0) {
                    		   /*if(!(lenght.equals(charge.getLength()))) {
                    			   applicationModel.addValidationError("Entered length should be "+charge.getLength());
                    			   this.getModel().getAdvertisementReqDto().setFree(true);
                                   applicationModel.setPayable(false);
                    		   }*/
                    		   if(lenght >= charge.getLength()) {
                    			   applicationModel.addValidationError("Entered length should be less than "+charge.getLength());
                    			   this.getModel().getAdvertisementReqDto().setFree(true);
                                   applicationModel.setPayable(false);
                    		   }
                    	   }
                    	   if(charge.getHeight() != null && charge.getHeight() > 0.0) {
                    		   /*if(!(height.equals(charge.getHeight()))) {
                    			   applicationModel.addValidationError("Entered height  should be  "+charge.getHeight());
                    			   this.getModel().getAdvertisementReqDto().setFree(true);
                                   applicationModel.setPayable(false);
                    		   }*/
                    		   if(height >= charge.getHeight()) {
                    			   applicationModel.addValidationError("Entered height should be less than "+charge.getHeight());
                    			   this.getModel().getAdvertisementReqDto().setFree(true);
                                   applicationModel.setPayable(false);
                    		   }
                    	   }
                       }
                        this.getModel().getOfflineDTO().setAmountToShow(applicationModel.getAmountToPay());
                        this.getModel().setPayable(true);
                    }

                } else {
                    this.getModel().getAdvertisementReqDto().setFree(true);
                    applicationModel.setPayable(false);
                }
            } else {
                applicationModel.addValidationError(getApplicationSession()
                        .getMessage("adh.exception.occured.depends.on.factor.for.ADH.ratemaster"));
                LOGGER.error("Exception occured while fecthing Depends on factor for ADH Rate Mster ");
            }
         }else {
            applicationModel.addValidationError(
                    getApplicationSession().getMessage("adh.problem.while.initializing.checklist.and.ADHratemaster.model"));
            LOGGER.error("Problem while initializing CheckList and ADHRateMaster Model");
        }
    }

    public double chargesToPay(final List<ChargeDetailDTO> charges) {
        double amountSum = 0.0;
        if (!CollectionUtils.isEmpty(charges)) {
            for (final ChargeDetailDTO charge : charges) {
                amountSum = amountSum + charge.getChargeAmount();
            }
        }
        return amountSum;
    }

    private List<ChargeDetailDTO> newChargesToPay(final List<ChargeDetailDTO> charges) {
        List<ChargeDetailDTO> chargeList = new ArrayList<>(0);
        for (final ChargeDetailDTO charge : charges) {
            BigDecimal amount = new BigDecimal(charge.getChargeAmount());
            charge.setChargeAmount(amount.doubleValue());
            chargeList.add(charge);
        }
        return chargeList;
    }

    @ResponseBody
    @RequestMapping(params = "saveAdvertisementApplication", method = RequestMethod.POST)
    public Map<String, Object> saveAdvertisement(HttpServletRequest httpServletRequest) {
        getModel().bind(httpServletRequest);
        this.getModel().saveApplication();
        Map<String, Object> object = new LinkedHashMap<String, Object>();
        object.put("error", this.getModel().getBindingResult().getAllErrors());
        object.put("applicationNo", this.getModel().getApmApplicationId());
        if (this.getModel().getAdvertisementReqDto().isFree()) {
            object.put("servicefree", "Y");
        }
        return object;
    }
    
    
    //Defect #123725
    
    @RequestMapping(params = MainetConstants.AdvertisingAndHoarding.GET_LICENCE_TYPE, method = RequestMethod.POST)
	public @ResponseBody Long getLicMaxTenureDays(@RequestParam("licType") Long licType,
			final HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);
		ServiceMaster serviceMaster = ApplicationContextProvider.getApplicationContext()
				.getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE,
						UserSession.getCurrent().getOrganisation().getOrgid());

		Long calculateLicMaxTenureDays = ApplicationContextProvider.getApplicationContext()
				.getBean(ADHCommonService.class).calculateLicMaxTenureDays(
						serviceMaster.getTbDepartment().getDpDeptid(), serviceMaster.getSmServiceId(), null,
						UserSession.getCurrent().getOrganisation().getOrgid(), licType);
		this.getModel().setLicMaxTenureDays(calculateLicMaxTenureDays);
		return calculateLicMaxTenureDays;
	}

	@RequestMapping(params = MainetConstants.AdvertisingAndHoarding.GET_CALCULATE_YEAR_TYPE, method = RequestMethod.POST)
	public @ResponseBody String gtCalculateYearTpe(@RequestParam("licType") Long licType,
			final HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);
		ServiceMaster serviceMaster = ApplicationContextProvider.getApplicationContext()
				.getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE,
						UserSession.getCurrent().getOrganisation().getOrgid());
		List<LicenseValidityMasterDto> licValMasterDtoList = licenseValidityMasterService.searchLicenseValidityData(
				UserSession.getCurrent().getOrganisation().getOrgid(), serviceMaster.getTbDepartment().getDpDeptid(),
				serviceMaster.getSmServiceId(), MainetConstants.ZERO_LONG, licType);
		if(!(licValMasterDtoList).isEmpty()){
			LookUp dependsOnLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(
					licValMasterDtoList.get(0).getLicDependsOn(), UserSession.getCurrent().getOrganisation());
			String YearType = dependsOnLookUp.getLookUpCode();
			return YearType;
		} else {
			return MainetConstants.FALSE; 
		}
		
	}
	
	 
	    public void getPropertyDetailsByPropertyNuber(NewAdvertisementApplicationModel model) {
	        NewAdvertisementReqDto advertisementReqDto = model.getAdvertisementReqDto();
	        NewAdvertisementApplicationDto reqDto = model.getAdvertisementReqDto().getAdvertisementDto();
	        if(!(reqDto.getPropNumber().isEmpty())) {
	        reqDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
	        NewAdvertisementApplicationDto applicationDto = advertiserMasterService.getPropertyDetailsByPropertyNumber(reqDto);
	        String respMsg = "";
	        if (applicationDto != null) {
	            reqDto.setPropOutstandingAmt(applicationDto.getPropOutstandingAmt());
	            reqDto.setPropOwnerName(applicationDto.getPropOwnerName());
	            reqDto.setPropAddress(applicationDto.getPropAddress());
	            model.setAdvertisementReqDto(advertisementReqDto);
	            if(applicationDto.getAssessmentCheckFlag().equals("N")) {
	            	respMsg = "Assessment Not done";
	            	model
	                .addValidationError(respMsg);  	
	            }
	        } else {
	            respMsg = ApplicationSession.getInstance().getMessage("adh.new.advertisement.validation.property.not.found");
	            model
	            .addValidationError(respMsg +" "+ reqDto.getPropNumber());
	        }   
	        }
	    }
	    
    public long getNoOfDays(Date licToDate, Date licFromDate) {
    	long diffInMillis = licToDate.getTime() - licFromDate.getTime();
        long diffInDays = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
        long noOfDays = (int) (diffInDays + 1);
        
        return noOfDays;
    	
    }
}
