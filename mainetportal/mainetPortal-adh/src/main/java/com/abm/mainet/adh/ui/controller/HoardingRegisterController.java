package com.abm.mainet.adh.ui.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.maven.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.adh.datamodel.ADHRateMaster;
import com.abm.mainet.adh.dto.ADHRequestDto;
import com.abm.mainet.adh.dto.ADHResponseDTO;
import com.abm.mainet.adh.dto.AdvertiserMasterDto;
import com.abm.mainet.adh.dto.HoardingBookingDto;
import com.abm.mainet.adh.dto.HoardingMasterDto;
import com.abm.mainet.adh.dto.NewAdvertisementApplicationDetDto;
import com.abm.mainet.adh.dto.NewAdvertisementReqDto;
import com.abm.mainet.adh.service.HoardingRegistrationService;
import com.abm.mainet.adh.service.IAgencyRegistrationService;
import com.abm.mainet.adh.service.IBRMSADHService;
import com.abm.mainet.adh.service.INewAdvertisementApplicationService;
import com.abm.mainet.adh.ui.model.HoardingRegistrationModel;
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
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;

@Controller
@RequestMapping("/NewHoardingApplication.html")
public class HoardingRegisterController extends AbstractFormController<HoardingRegistrationModel>{

    private static final Logger LOGGER = Logger.getLogger(HoardingRegisterController.class);
   
    
    @Autowired
    private IBRMSADHService brmsadhService;
    
    @Autowired
	private IPortalServiceMasterService PortalServiceMaster;
    
    @Autowired
    private INewAdvertisementApplicationService advertisementApplicationService;
    
    
    
    @Autowired
    private HoardingRegistrationService hoardingRegistrationService;
   
    
    @Autowired
    private ICommonBRMSService brmsCommonService;
    
    
    
    @Autowired
	IBRMSADHService BRMSADHService;
    
    @Autowired
   	IAgencyRegistrationService agencyRegistrationService;

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final Model model, final HttpServletRequest request) {
    	sessionCleanup(request);
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        PortalService service = PortalServiceMaster.getService(PortalServiceMaster
				.getServiceId(MainetConstants.AdvertisingAndHoarding.HOARDING_REG_SHORT_CODE, orgId), orgId);
        this.getModel().setService(service);
        List<LookUp> lookUps = advertisementApplicationService
                .geLocationByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
       
        this.getModel().setListOfLookUp(lookUps);
        this.getModel().setHoardingNumberList(hoardingRegistrationService.getHoardingNumberAndIdListByOrgId(orgId));
        LinkedHashMap<String, Object> checkListChargeFlagAndLicMaxDay = brmsadhService
				.getCheckListChargeFlagAndLicMaxDay(orgId,
						MainetConstants.AdvertisingAndHoarding.HOARDING_REG_SHORT_CODE);
        if (MapUtils.isNotEmpty(checkListChargeFlagAndLicMaxDay)) {
			for (Map.Entry<String, Object> map : checkListChargeFlagAndLicMaxDay.entrySet()) {
				if (StringUtils.equals(map.getKey(), MainetConstants.AdvertisingAndHoarding.CHECKLIST_APPL_FLAG)) {
					this.getModel().setCheckListApplFlag(map.getValue().toString());
				}
				if (StringUtils.equals(map.getKey(), MainetConstants.AdvertisingAndHoarding.APPL_CHARGE_FLAG)) {
					this.getModel().setApplicationchargeApplFlag(map.getValue().toString());
				}
				
			}
		}
        return new ModelAndView("NewHoardingApplication", MainetConstants.FORM_NAME, this.getModel());
    }
    
    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.AdvertisingAndHoarding.APPLICABLE_CHECKLIST_CHARGE)
    public ModelAndView getApplicableCheckListFromBRMS(final HttpServletRequest request) {
        this.getModel().bind(request);
        ModelAndView modelAndView = null;
        if (this.getModel().validateInputs()) {
            findApplicableCheckList(request);
            this.getModel().setEnableSubmit(MainetConstants.FlagY);
            modelAndView = new ModelAndView(MainetConstants.AdvertisingAndHoarding.NEW_HOARDING_DATA, MainetConstants.FORM_NAME,
                    this.getModel());
        }
        if (this.getModel().getBindingResult() != null && this.getModel().getBindingResult().hasErrors()) {
            modelAndView = new ModelAndView(MainetConstants.AdvertisingAndHoarding.NEW_HRD_APP_VALIDN,
                    MainetConstants.COMMAND,
                    this.getModel());
            modelAndView.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
            this.getModel().setEnableSubmit(MainetConstants.FlagN);
            if (!this.getModel().getCheckList().isEmpty()) {
                this.getModel().getCheckList().clear();
            }
        }
        return modelAndView;
    }

    @SuppressWarnings("unchecked")
    private void findApplicableCheckList(HttpServletRequest request) {
    	this.getModel().getAdvertisementReqDto().getAdvertisementDto().setNewAdvertDetDtos(new ArrayList<NewAdvertisementApplicationDetDto>());
        getModel().bind(request);
        HoardingRegistrationModel applicationModel = this.getModel();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
     
        this.getModel().getAdvertisementReqDto().getAdvertisementDto()
                .setAgencyId(this.getModel().getAdvertisementReqDto().getAdvertisementDto().getAgencyId());
        WSRequestDTO initRequestDto = new WSRequestDTO();
        Boolean hordingIdDuplicate=false;
        Set<Long> items = new HashSet<Long>();   
        List<NewAdvertisementApplicationDetDto> list = this.getModel().getAdvertisementReqDto().getAdvertisementDto().getNewAdvertDetDtos();
        list.stream()
                .filter(n -> !items.add(n.getHoardingId())) // Set.add() returns false if the element was already in the set.
                .collect(Collectors.toSet());  
        if(items.size() != list.size()) {
        	 hordingIdDuplicate=true;
        	 applicationModel.addValidationError("please don't select duplicate Hoarding Number");
             LOGGER.error("please don't select duplicate Hoarding Number");
        	}
        if(!hordingIdDuplicate) {
        initRequestDto.setModelName(MainetConstants.AdvertisingAndHoarding.CHECKLIST_ADHRATEMASTER);
        WSResponseDTO response = brmsCommonService.initializeModel(initRequestDto);
        if (response.getWsStatus() != null
                && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
        	if (this.getModel().getCheckListApplFlag().equals(MainetConstants.FlagY)) {                List<Object> checklist = JersyCall.castResponse(response, CheckListModel.class, 0);
                CheckListModel checkListModel = (CheckListModel) checklist.get(0);
                checkListModel.setOrgId(orgId);
                checkListModel.setServiceCode(MainetConstants.AdvertisingAndHoarding.HOARDING_REG_SHORT_CODE);
                final WSRequestDTO checkRequestDto = new WSRequestDTO();
                checkRequestDto.setDataModel(checkListModel);
                List<DocumentDetailsVO> checklistResponse = brmsCommonService.getChecklist(checkListModel);
                
                if (checklistResponse != null && !checklistResponse.isEmpty()) {
                    long cnt = 1;
                    for (final DocumentDetailsVO doc : checklistResponse) {
                        doc.setDocumentSerialNo(cnt);
                        cnt++;
                    }
                }
                this.getModel().setCheckList(checklistResponse);
                } 
            
            Organisation organisation = new Organisation();
            organisation.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
            List<Object> adhRateMasterList = JersyCall.castResponse(response, ADHRateMaster.class, 1);
            ADHRateMaster adhRateMaster = (ADHRateMaster) adhRateMasterList.get(0);
            WSRequestDTO taxReqDto = new WSRequestDTO();
            adhRateMaster.setOrgId(orgId);
            adhRateMaster.setServiceCode(MainetConstants.AdvertisingAndHoarding.HOARDING_REG_SHORT_CODE);
            LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
                    MainetConstants.AdvertisingAndHoarding.APL, PrefixConstants.ADVERTISEMENT_AND_HOARDING_PREFIX.CAA,
                    organisation);
            adhRateMaster.setChargeApplicableAt(Long.toString(chargeApplicableAt.getLookUpId()));
            taxReqDto.setDataModel(adhRateMaster);
            final WSResponseDTO taxResponseDto = BRMSADHService.getApplicableTaxes(taxReqDto);
            if (taxResponseDto != null
                    && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(taxResponseDto.getWsStatus())) {
                if (!taxResponseDto.isFree()) {
                	final List<?> rates = JersyCall.castResponse(taxResponseDto, ADHRateMaster.class);
                    // final List<Object> rates = (List<Object>) taxResponseDTO.getResponseObj();
                    final List<ADHRateMaster> requiredCHarges = new ArrayList<>();
                    for (final Object rate : rates) {
                        ADHRateMaster master1 = (ADHRateMaster) rate;
                        master1.setOrgId(orgId);
                        master1.setServiceCode(MainetConstants.AdvertisingAndHoarding.HOARDING_REG_SHORT_CODE);
                        master1.setDeptCode(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE);
                        master1.setRateStartDate(new Date().getTime());
                        requiredCHarges.add(master1);
                    }
                    List<ChargeDetailDTO> applicableCharges = brmsadhService.getApplicableCharges(requiredCHarges);
                    if (applicableCharges == null) {
                        applicationModel
                                .addValidationError(getApplicationSession().getMessage("adh.validate.charges.not.found.brms.sheet"));
                        LOGGER.error("Charges not found in brms sheet");
                    } else {
                        applicationModel.setChargesInfo(newChargesToPay(applicableCharges));
                        applicationModel.setAmountToPay(chargesToPay(applicationModel.getChargesInfo()));
                        if (applicationModel.getAmountToPay() == 0.0d) {
                            applicationModel.addValidationError(getApplicationSession().getMessage(
                                    "adh.validate.service.charge.amountToPay.cannot.be") + applicationModel.getAmountToPay()
                                    + getApplicationSession().getMessage("adh.if.service.configured.as.chargeable"));
                            LOGGER.error("Service charge amountToPay cannot be" + applicationModel.getAmountToPay()
                                    + "if service configured as Chargeable");
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
            }
            else {
            applicationModel.addValidationError(
                    getApplicationSession().getMessage("adh.problem.while.initializing.checklist.and.ADHratemaster.model"));
            LOGGER.error("Problem while initializing CheckList and ADHRateMaster Model");
        }
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

    /**
     * @param chargeDetailDTOs
     * @return
     */
    private List<ChargeDetailDTO> newChargesToPay(List<ChargeDetailDTO> charges) {
        List<ChargeDetailDTO> chargeList = new ArrayList<>(0);
        for (ChargeDetailDTO charge : charges) {
            BigDecimal chargeAmount = new BigDecimal(charge.getChargeAmount());
            charge.setChargeAmount(chargeAmount.doubleValue());
            chargeList.add(charge);
        }
        return chargeList;
    }
	
	@RequestMapping(params = MainetConstants.AdvertisingAndHoarding.GET_AGENCY_NAME, method = RequestMethod.POST)
    public @ResponseBody List<AdvertiserMasterDto> getAgencyNameByOrgId(
            @RequestParam(MainetConstants.AdvertisingAndHoarding.ADVERTSIER_CAT) Long advertiserCategoryId) {
        List<AdvertiserMasterDto> advertiserMasterList = advertisementApplicationService
                .getAdvertiserDetails(advertiserCategoryId,
                        UserSession.getCurrent().getOrganisation().getOrgid());
        return advertiserMasterList;
    }
	@ResponseBody
    @RequestMapping(params = "searchHoardingDetailsByHoardingNumber", method = { RequestMethod.POST })
    public Map<String, Object> searchHoardingDetailsByHoardingNumber(
            @RequestParam(MainetConstants.AdvertisingAndHoarding.HOARDING_ID) Long hoardingId,
            HttpServletRequest request) {
        Map<String, Object> object = new LinkedHashMap<String, Object>();
        HoardingMasterDto hoardingMasterDto = hoardingRegistrationService
                .getByOrgIdAndHoardingId(UserSession.getCurrent().getOrganisation().getOrgid(), hoardingId);
         //this.getModel().setHoardingMasterDto(hoardingMasterDto);
        List<HoardingBookingDto> bookingDto = hoardingRegistrationService.getHoardingDetailsByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        for (HoardingBookingDto hoardingBookingDto : bookingDto) {
			if(hoardingBookingDto.getHoardingId() != null) {
			if (hoardingBookingDto.getHoardingId().equals(hoardingId)) {
				hoardingMasterDto.setStatusFlag(MainetConstants.FlagY);
			}
			}
		}
        hoardingMasterDto.setDisplayIdDesc(
                CommonMasterUtility.getNonHierarchicalLookUpObject(hoardingMasterDto.getDisplayTypeId(),
                        UserSession.getCurrent().getOrganisation()).getDescLangFirst());
        object.put("hoardingDto", hoardingMasterDto);
        object.put("status", hoardingMasterDto.getStatusFlag());
        return object;
    }
	 /*@ResponseBody
	    @RequestMapping(params = MainetConstants.AdvertisingAndHoarding.GET_LOCATION_MAPPING, method = RequestMethod.POST)
	    public String getLocationMapping(
	            @RequestParam(value = MainetConstants.AdvertisingAndHoarding.LOCATION_ID, required = false) Long locationId) {
	        String response = MainetConstants.BLANK;
	        ServiceMaster serviceMaster = ApplicationContextProvider.getApplicationContext()
	                .getBean(ServiceMasterService.class)
	                .getServiceMasterByShortCode(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE,
	                        UserSession.getCurrent().getOrganisation().getOrgid());
	        LocationOperationWZMapping wzMapping = locationMasService.findbyLocationAndDepartment(
	                locationId, serviceMaster.getTbDepartment().getDpDeptid());
	        if (wzMapping != null) {
	            response = "Y";
	        } else {
	            response = "N";
	        }
	        return response;

	    }*/
	
	//D#79968
	 @RequestMapping(params = "showDetails", method = RequestMethod.POST)
		public ModelAndView showDetails(@RequestParam("appId") final long appId,
				final HttpServletRequest httpServletRequest) throws Exception {
			getModel().bind(httpServletRequest);

			try {
				//call to GET_ADH_DATA_BY_APPLICATION_ID
				ADHRequestDto adhRequestDto = new ADHRequestDto();
				adhRequestDto.setApplicationId(appId);
				adhRequestDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
				adhRequestDto.setServiceCode(MainetConstants.AdvertisingAndHoarding.HOARDING_REG_SHORT_CODE);
				ADHResponseDTO  adhResponseDTO=advertisementApplicationService.getADHDataByApplicationId(adhRequestDto);
				getModel().setSaveMode(MainetConstants.AdvertisingAndHoarding.VIEW);
				getModel().setAdvertisementReqDto(adhResponseDTO.getAdvertisementReqDto());
			} catch (Exception exception) {
				throw new FrameworkException(exception);
			}
			List<LookUp> lookUps = advertisementApplicationService
	                .geLocationByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			getModel().setListOfLookUp(lookUps);
			getModel().setHoardingNumberList(hoardingRegistrationService.getHoardingNumberAndIdListByOrgId(UserSession.getCurrent().getOrganisation().getOrgid()));
			
			return new ModelAndView("NewHoardingApplicationValidn", MainetConstants.FORM_NAME, getModel());
		}
	 
	// Defect #123725

	@RequestMapping(params = MainetConstants.AdvertisingAndHoarding.GET_LICENCE_TYPE, method = RequestMethod.POST)
	public @ResponseBody String getLicMaxTenureDays(@RequestParam("licType") Long licType,
			final HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		PortalService service = PortalServiceMaster.getService(
				PortalServiceMaster.getServiceId(MainetConstants.AdvertisingAndHoarding.HOARDING_REG_SHORT_CODE, orgId),
				orgId);
		this.getModel().setService(service);
		String checkListChargeFlagAndLicMaxDay = advertisementApplicationService.getLicMaxTenureDays(
				UserSession.getCurrent().getOrganisation().getOrgid(),
				MainetConstants.AdvertisingAndHoarding.HOARDING_REG_SHORT_CODE, licType);
		return checkListChargeFlagAndLicMaxDay;

	}

	@RequestMapping(params = MainetConstants.AdvertisingAndHoarding.GET_CALCULATE_YEAR_TYPE, method = RequestMethod.POST)
	public @ResponseBody String gtCalculateYearTpe(@RequestParam("licType") Long licType,
			final HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);

		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		PortalService service = PortalServiceMaster.getService(
				PortalServiceMaster.getServiceId(MainetConstants.AdvertisingAndHoarding.HOARDING_REG_SHORT_CODE, orgId),
				orgId);
		this.getModel().setService(service);
		String YearType = agencyRegistrationService.getCalculateYearTypeBylicType(orgId,
				MainetConstants.AdvertisingAndHoarding.HOARDING_REG_SHORT_CODE, licType);
		return YearType;
	}
	
	@ResponseBody
    @RequestMapping(params = "saveHoardingApplication", method = RequestMethod.POST)
    public Map<String, Object> saveAdvertisement(HttpServletRequest httpServletRequest) {
        getModel().bind(httpServletRequest);
        this.getModel().saveForm();
        Map<String, Object> object = new LinkedHashMap<String, Object>();
        object.put("error", this.getModel().getBindingResult().getAllErrors());
        object.put("applicationNo", this.getModel().getApmApplicationId());
        if (this.getModel().getAdvertisementReqDto().isFree()) {
            object.put("servicefree", "Y");
        }
        return object;
    }
}
