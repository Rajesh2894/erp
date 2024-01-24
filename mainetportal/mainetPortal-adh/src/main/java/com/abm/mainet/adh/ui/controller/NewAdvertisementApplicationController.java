package com.abm.mainet.adh.ui.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.adh.datamodel.ADHRateMaster;
import com.abm.mainet.adh.dto.AdvertiserMasterDto;
import com.abm.mainet.adh.dto.NewAdvertisementApplicationDetDto;
import com.abm.mainet.adh.dto.NewAdvertisementApplicationDto;
import com.abm.mainet.adh.dto.NewAdvertisementReqDto;
import com.abm.mainet.adh.service.IAgencyRegistrationService;
import com.abm.mainet.adh.service.IBRMSADHService;
import com.abm.mainet.adh.service.INewAdvertisementApplicationService;
import com.abm.mainet.adh.ui.model.NewAdvertisementApplicationModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.dto.ChargeDetailDTO;
import com.abm.mainet.common.dto.CheckListModel;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.ICommonBRMSService;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;

/**
 * @author vishwajeet.kumar
 * @since 14 october 2019
 */
@Controller
@RequestMapping("/NewAdvertisementApplication.html")
public class NewAdvertisementApplicationController extends AbstractFormController<NewAdvertisementApplicationModel> {

    @Autowired
    private INewAdvertisementApplicationService advertisementApplicationService;

    @Autowired
    private ICommonBRMSService brmsCommonService;

    @Autowired
    private IBRMSADHService brmsadhService;
    
    @Autowired
	private IPortalServiceMasterService PortalServiceMaster;
    
    @Autowired
	IAgencyRegistrationService agencyRegistrationService;


    private static final Logger LOGGER = Logger.getLogger(NewAdvertisementApplicationController.class);

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        List<LookUp> lookUps = advertisementApplicationService
                .geLocationByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        LinkedHashMap<String, Object> checkListChargeFlagAndLicMaxDay = advertisementApplicationService
                .getCheckListChargeFlagAndLicMaxDay(UserSession.getCurrent().getOrganisation().getOrgid(),
                        MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE);
        if (MapUtils.isNotEmpty(checkListChargeFlagAndLicMaxDay)) {
            for (Map.Entry<String, Object> map : checkListChargeFlagAndLicMaxDay.entrySet()) {
                if (StringUtils.equals(map.getKey(), MainetConstants.AdvertisingAndHoarding.CHECKLIST_APPL_FLAG)) {
                    this.getModel().setCheckListApplFlag(map.getValue().toString());
                }
                if (StringUtils.equals(map.getKey(), MainetConstants.AdvertisingAndHoarding.LIC_MAX_TENURE_DAYS)) {
                    Long valueOf = Long.valueOf(map.getValue().toString());
                    this.getModel().setLicMaxTenureDays(valueOf);
                }
            }
        }
        this.getModel().setListOfLookUp(lookUps);
        return new ModelAndView("NewAdvertisementApplication", MainetConstants.FORM_NAME, this.getModel());
    }

    @RequestMapping(params = "getAgencyName", method = RequestMethod.POST)
    public @ResponseBody List<AdvertiserMasterDto> getAgencyNameByOrgId(
            @RequestParam("advertiserCategoryId") Long advertiserCategoryId) {
        List<AdvertiserMasterDto> advertiserMasterList = null;
        advertiserMasterList = advertisementApplicationService
                .getAdvertiserDetails(advertiserCategoryId, UserSession.getCurrent().getOrganisation().getOrgid());

        return advertiserMasterList;
    }

    @RequestMapping(method = RequestMethod.POST, params = "getApplicableCheckListAndCharges")
    public ModelAndView getApplicableCheckListFromBRMS(final HttpServletRequest request) {
    	this.getModel().getAdvertisementReqDto().getAdvertisementDto().setNewAdvertDetDtos(new ArrayList<NewAdvertisementApplicationDetDto>());
        this.getModel().bind(request);
        ModelAndView modelAndView = null;
        if (this.getModel().validateInputs()) {
        	Boolean isSameAdvertiser=true;
            //Defect #127337
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
            		MainetConstants.CommonConstants.COMMAND, this.getModel());
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
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        NewAdvertisementApplicationModel applicationModel = this.getModel();
        this.getModel().getAdvertisementReqDto().getAdvertisementDto()
                .setAgencyId(this.getModel().getAdvertisementReqDto().getAdvertisementDto().getAgencyId());

        WSRequestDTO initRequestDto = new WSRequestDTO();
        initRequestDto.setModelName(MainetConstants.AdvertisingAndHoarding.CHECKLIST_ADHRATEMASTER);

        WSResponseDTO response = brmsCommonService.initializeModel(initRequestDto);
        if (response.getWsStatus() != null
                && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
        	long cnt = 1;
        	List<DocumentDetailsVO> checklistResponse = Collections.emptyList();
        	List<DocumentDetailsVO> checklistDocs=new ArrayList<>();
        	for(NewAdvertisementApplicationDetDto dto:applicationModel.getAdvertisementReqDto().getAdvertisementDto()
                    .getNewAdvertDetDtos()) {
            if (this.getModel().getCheckListApplFlag().equals(MainetConstants.FlagY)) {
                List<Object> checklist = JersyCall.castResponse(response, CheckListModel.class, 0);
                CheckListModel checkListModel = (CheckListModel) checklist.get(0);
                checkListModel.setOrgId(orgId);
                checkListModel.setFactor3(CommonMasterUtility
                        .getHierarchicalLookUp(dto.getAdhTypeId1(), UserSession.getCurrent().getOrganisation())
                        .getDescLangFirst());
                checkListModel.setFactor4(CommonMasterUtility
                        .getHierarchicalLookUp(dto.getAdhTypeId2(), UserSession.getCurrent().getOrganisation())
                        .getDescLangFirst());
                checkListModel.setServiceCode("ADH");
                final WSRequestDTO checkRequestDto = new WSRequestDTO();
                checkRequestDto.setDataModel(checkListModel);
                checklistResponse = brmsCommonService.getChecklist(checkListModel);
                if (checklistResponse != null && !checklistResponse.isEmpty()) {
                    //long cnt = 1;
                    for (final DocumentDetailsVO doc : checklistResponse) {
                        doc.setDocumentSerialNo(cnt);
                        if (!checklistDocs.stream().anyMatch(
                                docs ->docs.getDoc_DESC_ENGL() != null
                                        && docs.getDoc_DESC_ENGL().equals(doc.getDoc_DESC_ENGL()))) {
                        	checklistDocs.add(doc);
                        	cnt++;
                        }
                        
                    }
                }
                //this.getModel().setCheckList(checklistResponse);
            }
        	}
        	this.getModel().setCheckList(checklistDocs);
            Organisation organisation = new Organisation();
            organisation.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
            List<Object> adhRateMasterList = JersyCall.castResponse(response, ADHRateMaster.class, 1);
            ADHRateMaster adhRateMaster = (ADHRateMaster) adhRateMasterList.get(0);
            WSRequestDTO taxReqDTO = new WSRequestDTO();
            adhRateMaster.setOrgId(orgId);
            adhRateMaster.setServiceCode(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE);
            LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
                    MainetConstants.AdvertisingAndHoarding.APL, PrefixConstants.ADVERTISEMENT_AND_HOARDING_PREFIX.CAA,
                    organisation);
            adhRateMaster.setChargeApplicableAt(Long.toString(chargeApplicableAt.getLookUpId()));
            // new code added to validate lenght and height and set ward and zone

            Map<String, String> wzMapping = brmsadhService.getWardAndZone(orgId,
                    this.getModel().getAdvertisementReqDto().getAdvertisementDto().getLocId());
            adhRateMaster.setZone(wzMapping.get("zone"));
            adhRateMaster.setWard(wzMapping.get("ward"));
            taxReqDTO.setDataModel(adhRateMaster);
            WSResponseDTO taxResponseDTO = brmsadhService.getApplicableTaxes(taxReqDTO);
            if (taxResponseDTO.getWsStatus() != null
                    && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(taxResponseDTO.getWsStatus())) {
                if (!taxResponseDTO.isFree()) {
                    final List<?> rates = JersyCall.castResponse(taxResponseDTO, ADHRateMaster.class);
                    // final List<Object> rates = (List<Object>) taxResponseDTO.getResponseObj();
                    final List<ADHRateMaster> requiredCHarges = new ArrayList<>();
                    for (final Object rate : rates) {
                        ADHRateMaster master1 = (ADHRateMaster) rate;
                        master1.setOrgId(orgId);
                        master1.setServiceCode(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE);
                        master1.setDeptCode(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE);
                        master1.setRateStartDate(new Date().getTime());
                        LookUp lookup1=CommonMasterUtility.getNonHierarchicalLookUpObject(applicationModel.getAdvertisementReqDto().getAdvertisementDto().getLicenseType(), UserSession.getCurrent().getOrganisation());
                        master1.setLicenseType(lookup1.getDescLangFirst());
                        requiredCHarges.add(master1);
                    }
                    List<ChargeDetailDTO> applicableCharges = brmsadhService.getApplicableCharges(requiredCHarges);
                    if (applicableCharges == null) {
                        applicationModel.addValidationError(
                                getApplicationSession().getMessage("adh.validate.charges.not.found.brms.sheet"));
                        LOGGER.error("Charges not Found in brms Sheet");
                    } else {
                        applicationModel.setChargesInfo(newChargesToPay(applicableCharges));
                        applicationModel.setAmountToPay(chargesToPay(applicationModel.getChargesInfo()));
                        if (applicationModel.getAmountToPay() == 0.0d) {
                            applicationModel.addValidationError(getApplicationSession().getMessage(
                                    "adh.validate.service.charge.amountToPay.cannot.be") + applicationModel.getAmountToPay()
                                    + getApplicationSession().getMessage("adh.if.service.configured.as.chargeable"));
                            LOGGER.error("Service charge amountToPay cannot be " + applicationModel.getAmountToPay()
                                    + " if service configured as Chargeable");
                        }
                        this.getModel().getOfflineDTO().setAmountToShow(applicationModel.getAmountToPay());
                        this.getModel().setPayable(true);
                        Double lenght = this.getModel().getAdvertisementReqDto().getAdvertisementDto().getNewAdvertDetDtos()
                                .get(0).getAdvDetailsLength().doubleValue();
                        Double height = this.getModel().getAdvertisementReqDto().getAdvertisementDto().getNewAdvertDetDtos()
                                .get(0).getAdvDetailsHeight().doubleValue();
                        for (final ChargeDetailDTO charge : applicableCharges) {

                            if (charge.getLength() != null && charge.getLength() > 0.0) {
                                if (lenght >= charge.getLength()) {
                                    applicationModel
                                            .addValidationError("Entered length should be less than " + charge.getLength());
                                    this.getModel().getAdvertisementReqDto().setFree(true);
                                    applicationModel.setPayable(false);
                                }
                            }
                            if (charge.getHeight() != null && charge.getHeight() > 0.0) {
                                if (height >= charge.getHeight()) {
                                    applicationModel
                                            .addValidationError("Entered height should be less than " + charge.getHeight());
                                    this.getModel().getAdvertisementReqDto().setFree(true);
                                    applicationModel.setPayable(false);
                                }
                            }
                        }
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
        } else {
            applicationModel.addValidationError(
                    getApplicationSession().getMessage("adh.problem.while.initializing.checklist.and.ADHratemaster.model"));
            LOGGER.error("Problem while initializing CheckList and ADHRateMaster Model");
        }

    }

    private double chargesToPay(List<ChargeDetailDTO> chargesInfo) {
        double amountSum = 0.0;
        if (!CollectionUtils.isEmpty(chargesInfo)) {
            for (final ChargeDetailDTO charge : chargesInfo) {
                amountSum = amountSum + charge.getChargeAmount();
            }
        }
        return amountSum;
    }

    private List<ChargeDetailDTO> newChargesToPay(List<ChargeDetailDTO> applicableCharges) {
        List<ChargeDetailDTO> chargeList = new ArrayList<>(0);
        for (final ChargeDetailDTO charge : applicableCharges) {
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

    // D#79968
    @RequestMapping(params = "showDetails", method = RequestMethod.POST)
    public ModelAndView showDetails(@RequestParam("appId") final long appId,
            final HttpServletRequest httpServletRequest) throws Exception {
        getModel().bind(httpServletRequest);

        try {
            NewAdvertisementReqDto advertisementReqDto = advertisementApplicationService.getAdvertisementApplicationByApp(appId,
                    UserSession.getCurrent().getOrganisation().getOrgid());
            getModel().setSaveMode(MainetConstants.AdvertisingAndHoarding.VIEW);
            advertisementReqDto.getAdvertisementDto()
                    .setLicenseFromDateStr(Utility.dateToString(advertisementReqDto.getAdvertisementDto().getLicenseFromDate()));
            advertisementReqDto.getAdvertisementDto()
                    .setLicenseToDateStr(Utility.dateToString(advertisementReqDto.getAdvertisementDto().getLicenseToDate()));
            getModel().setAdvertisementReqDto(advertisementReqDto);
        } catch (Exception exception) {
            throw new FrameworkException(exception);
        }
        List<LookUp> lookUps = advertisementApplicationService
                .geLocationByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        this.getModel().setListOfLookUp(lookUps);

        return new ModelAndView("NewAdvertisementApplicationValidn", MainetConstants.FORM_NAME, getModel());
    }
    
    
    @RequestMapping(params = "getPropertyDetails", method = RequestMethod.POST)
    public ModelAndView getPropertyDetails(HttpServletRequest request) {
        bindModel(request);
        ModelAndView modelAndView = null;
        NewAdvertisementApplicationModel model = this.getModel();
        NewAdvertisementReqDto advertisementReqDto = model.getAdvertisementReqDto();
        NewAdvertisementApplicationDto reqDto = model.getAdvertisementReqDto().getAdvertisementDto();
        reqDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        NewAdvertisementApplicationDto applicationDto = advertisementApplicationService.getPropertyDetailsByPropertyNumber(reqDto);
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
                        MainetConstants.COMMAND, this.getModel());
                return  modelAndView.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
                        getModel().getBindingResult());	
            }
        } else {
            respMsg = ApplicationSession.getInstance().getMessage("adh.new.advertisement.validation.property.not.found");
            model
            .addValidationError(respMsg +" "+ reqDto.getPropNumber());
            modelAndView = new ModelAndView("NewAdvertisementApplicationValidn",
                    MainetConstants.COMMAND, this.getModel());
            return  modelAndView.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
                    getModel().getBindingResult());
        }
        return defaultMyResult();
    }
    
    
    //Defect #129856
    
    
    @RequestMapping(params = MainetConstants.AdvertisingAndHoarding.GET_LICENCE_TYPE, method = RequestMethod.POST)
	public @ResponseBody String getLicMaxTenureDays(@RequestParam("licType") Long licType,
			final HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		PortalService service = PortalServiceMaster.getService(
				PortalServiceMaster.getServiceId(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE, orgId),
				orgId);
		this.getModel().setService(service);
		String checkListChargeFlagAndLicMaxDay  = advertisementApplicationService
                .getLicMaxTenureDays(UserSession.getCurrent().getOrganisation().getOrgid(),
                        MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE,licType);
				return checkListChargeFlagAndLicMaxDay;
		
	}

	@RequestMapping(params = MainetConstants.AdvertisingAndHoarding.GET_CALCULATE_YEAR_TYPE, method = RequestMethod.POST)
	public @ResponseBody String gtCalculateYearTpe(@RequestParam("licType") Long licType,
			final HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);
	
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		PortalService service = PortalServiceMaster.getService(
				PortalServiceMaster.getServiceId(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE, orgId),
				orgId);
		this.getModel().setService(service);
		String YearType  = agencyRegistrationService
				.getCalculateYearTypeBylicType(orgId,
						MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE,licType );
				return YearType;
	}
  
	public void getPropertyDetailsByPropertyNuber(NewAdvertisementApplicationModel model) {
        /*bindModel(request);
        
        NewAdvertisementApplicationModel model = this.getModel();*/
        NewAdvertisementReqDto advertisementReqDto = model.getAdvertisementReqDto();
        NewAdvertisementApplicationDto reqDto = model.getAdvertisementReqDto().getAdvertisementDto();
        if(!(reqDto.getPropNumber().isEmpty())) {
        reqDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        NewAdvertisementApplicationDto applicationDto = advertisementApplicationService.getPropertyDetailsByPropertyNumber(reqDto);
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
    
}
