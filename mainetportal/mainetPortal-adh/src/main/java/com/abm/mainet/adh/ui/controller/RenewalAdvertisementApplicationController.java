package com.abm.mainet.adh.ui.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
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
import com.abm.mainet.adh.dto.NewAdvertisementApplicationDto;
import com.abm.mainet.adh.dto.NewAdvertisementReqDto;
import com.abm.mainet.adh.service.IAgencyRegistrationService;
import com.abm.mainet.adh.service.IBRMSADHService;
import com.abm.mainet.adh.service.INewAdvertisementApplicationService;
import com.abm.mainet.adh.ui.model.RenewalAdvertisementApplicationModel;
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

/**
 * @author vishwajeet.kumar
 * @since 11 Dec 2019
 */
@Controller
@RequestMapping("/RenewalAdvertisementApplication.html")
public class RenewalAdvertisementApplicationController extends AbstractFormController<RenewalAdvertisementApplicationModel> {

    @Autowired
    private INewAdvertisementApplicationService advertisementApplicationService;

    @Autowired
    private IBRMSADHService brmsadhService;

    @Autowired
    private ICommonBRMSService brmsCommonService;
    
    @Autowired
	private IPortalServiceMasterService PortalServiceMaster;
    
    @Autowired
	IAgencyRegistrationService agencyRegistrationService;

    private static final Logger LOGGER = Logger.getLogger(RenewalAdvertisementApplicationController.class);

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        List<NewAdvertisementApplicationDto> advertisementApplDtoList = null;
        advertisementApplDtoList = advertisementApplicationService
                .getLicenseNoByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        this.getModel().setLicenseDataList(advertisementApplDtoList);
        return index();
    }

    @RequestMapping(params = MainetConstants.AdvertisingAndHoarding.GET_ADVERTISEMENT_APP_FORM, method = RequestMethod.POST)
    public ModelAndView getAdvertisementApplicationData(
            @RequestParam(MainetConstants.AdvertisingAndHoarding.LICENSE_NO) String licenseNo, final HttpServletRequest request) {

        this.getModel().bind(request);
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        List<LookUp> lookUps = advertisementApplicationService
                .geLocationByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        LinkedHashMap<String, Object> checkListChargeFlagAndLicMaxDay = advertisementApplicationService
                .getCheckListChargeFlagAndLicMaxDay(UserSession.getCurrent().getOrganisation().getOrgid(),
                        MainetConstants.AdvertisingAndHoarding.RENEWAL_SERVICE_CODE);

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
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        this.getModel().setSaveMode(MainetConstants.AdvertisingAndHoarding.FLAG_R);
        NewAdvertisementReqDto advertisementReqDto = advertisementApplicationService.getAdvertisementApplicationByLicenseNo(
                licenseNo, orgId);
        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate licenseFromDate = advertisementReqDto.getAdvertisementDto().getLicenseToDate().toInstant()
                .atZone(defaultZoneId)
                .toLocalDate().plusDays(1);
        Date date = Date.from(licenseFromDate.atStartOfDay(defaultZoneId).toInstant());
        if (advertisementReqDto.getAdvertisementDto().getApmApplicationId() != null) {
            advertisementReqDto.getAdvertisementDto().setApmApplicationId(null);
            advertisementReqDto.getAdvertisementDto().setLicenseFromDate(date);
        }
        this.getModel().setAdvertisementReqDto(advertisementReqDto);

        return new ModelAndView(MainetConstants.AdvertisingAndHoarding.RENEWAL_ADVERTISEMENT_APP_FORM, MainetConstants.FORM_NAME,
                this.getModel());
    }

    @RequestMapping(params = MainetConstants.AdvertisingAndHoarding.GET_AGENCY_NAME, method = RequestMethod.POST)
    public @ResponseBody List<AdvertiserMasterDto> getAgencyNameByOrgId(
            @RequestParam(MainetConstants.AdvertisingAndHoarding.ADVERTSIER_CAT) Long advertiserCategoryId) {
        List<AdvertiserMasterDto> advertiserMasterList = null;
        advertiserMasterList = advertisementApplicationService
                .getAdvertiserDetails(advertiserCategoryId, UserSession.getCurrent().getOrganisation().getOrgid());

        return advertiserMasterList;
    }

    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.AdvertisingAndHoarding.APPLICABLE_CHECKLIST_CHARGE)
    public ModelAndView getApplicableCheckListFromBRMS(final HttpServletRequest request) {
        ModelAndView modelAndView = null;
        if (this.getModel().validateInputs()) {
            findApplicableCheckList(request);
            this.getModel().setEnableSubmit(MainetConstants.FlagY);
            modelAndView = new ModelAndView(MainetConstants.AdvertisingAndHoarding.RENEWAL_ADVERTISEMENT_DATA,
                    MainetConstants.FORM_NAME, this.getModel());
        }
        if (this.getModel().getBindingResult() != null && this.getModel().getBindingResult().hasErrors()) {
            modelAndView = new ModelAndView(MainetConstants.AdvertisingAndHoarding.RENEWAL_ADVERTISEMENT_APP_VALID,
                    MainetConstants.FORM_NAME, this.getModel());
            modelAndView.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
                    getModel().getBindingResult());
            this.getModel().setEnableSubmit(MainetConstants.FlagN);
            if (!this.getModel().getCheckList().isEmpty())
                this.getModel().getCheckList().clear();
        }
        return modelAndView;
    }

    private void findApplicableCheckList(HttpServletRequest request) {
        getModel().bind(request);
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        RenewalAdvertisementApplicationModel applicationModel = this.getModel();
        this.getModel().getAdvertisementReqDto().getAdvertisementDto()
                .setAgencyId(this.getModel().getAdvertisementReqDto().getAdvertisementDto().getAgencyId());

        WSRequestDTO initRequestDto = new WSRequestDTO();
        initRequestDto.setModelName(MainetConstants.AdvertisingAndHoarding.CHECKLIST_ADHRATEMASTER);

        WSResponseDTO response = brmsCommonService.initializeModel(initRequestDto);
        if (response.getWsStatus() != null
                && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
            if (this.getModel().getCheckListApplFlag().equals(MainetConstants.FlagY)) {
                List<Object> checklist = JersyCall.castResponse(response, CheckListModel.class, 0);
                CheckListModel checkListModel = (CheckListModel) checklist.get(0);
                checkListModel.setOrgId(orgId);
                checkListModel.setServiceCode(MainetConstants.AdvertisingAndHoarding.RENEWAL_SERVICE_CODE);
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
            WSRequestDTO taxReqDTO = new WSRequestDTO();
            adhRateMaster.setOrgId(orgId);
            adhRateMaster.setServiceCode(MainetConstants.AdvertisingAndHoarding.RENEWAL_SERVICE_CODE);
            LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
                    MainetConstants.AdvertisingAndHoarding.APL, PrefixConstants.ADVERTISEMENT_AND_HOARDING_PREFIX.CAA,
                    organisation);
            adhRateMaster.setChargeApplicableAt(Long.toString(chargeApplicableAt.getLookUpId()));
            taxReqDTO.setDataModel(adhRateMaster);
            WSResponseDTO taxResponseDTO = brmsadhService.getApplicableTaxes(taxReqDTO);
            if (taxResponseDTO.getWsStatus() != null
                    && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(taxResponseDTO.getWsStatus())) {
                if (!taxResponseDTO.isFree()) {
                    final List<?> rates = JersyCall.castResponse(taxResponseDTO, ADHRateMaster.class);
                    final List<ADHRateMaster> requiredCHarges = new ArrayList<>();
                    for (final Object rate : rates) {
                        ADHRateMaster master1 = (ADHRateMaster) rate;
                        master1.setOrgId(orgId);
                        master1.setServiceCode(MainetConstants.AdvertisingAndHoarding.RENEWAL_SERVICE_CODE);
                        master1.setDeptCode(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE);
                        master1.setRateStartDate(new Date().getTime());
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
    @RequestMapping(params = MainetConstants.AdvertisingAndHoarding.SAVE_ADVERTISEMENT_APP, method = RequestMethod.POST)
    public Map<String, Object> saveAdvertisement(HttpServletRequest httpServletRequest) {
        getModel().bind(httpServletRequest);
        this.getModel().saveApplication();
        Map<String, Object> object = new LinkedHashMap<String, Object>();
        object.put(MainetConstants.AdvertisingAndHoarding.ERROR, this.getModel().getBindingResult().getAllErrors());
        object.put(MainetConstants.AdvertisingAndHoarding.APPLICATION_NO, this.getModel().getApmApplicationId());
        if (this.getModel().getAdvertisementReqDto().isFree()) {
            object.put(MainetConstants.AdvertisingAndHoarding.SERVICE_FREE, MainetConstants.FlagY);
        }
        return object;
    }
    
    //D#79968
    @RequestMapping(params = "showDetails", method = RequestMethod.POST)
	public ModelAndView showDetails(@RequestParam("appId") final long appId,
			final HttpServletRequest httpServletRequest) throws Exception {
		getModel().bind(httpServletRequest);

		try {
			NewAdvertisementReqDto advertisementReqDto=advertisementApplicationService.getAdvertisementApplicationByApp(appId,UserSession.getCurrent().getOrganisation().getOrgid());
			getModel().setSaveMode(MainetConstants.AdvertisingAndHoarding.VIEW);
			getModel().setAdvertisementReqDto(advertisementReqDto);
		} catch (Exception exception) {
			throw new FrameworkException(exception);
		}
		List<LookUp> lookUps = advertisementApplicationService
                .geLocationByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        this.getModel().setListOfLookUp(lookUps);
		
		return new ModelAndView("renewalAdvertisementApplicationForm", MainetConstants.FORM_NAME, getModel());
	}
    
     //Defect #123725
    

	@RequestMapping(params = MainetConstants.AdvertisingAndHoarding.GET_LICENCE_TYPE, method = RequestMethod.POST)
	public @ResponseBody String getLicMaxTenureDays(@RequestParam("licType") Long licType,
			final HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		PortalService service = PortalServiceMaster.getService(
				PortalServiceMaster.getServiceId(MainetConstants.AdvertisingAndHoarding.RENEWAL_SERVICE_CODE, orgId), orgId);
		this.getModel().setService(service);
		String checkListChargeFlagAndLicMaxDay = advertisementApplicationService.getLicMaxTenureDays(
				UserSession.getCurrent().getOrganisation().getOrgid(),
				MainetConstants.AdvertisingAndHoarding.RENEWAL_SERVICE_CODE, licType);
		return checkListChargeFlagAndLicMaxDay;

	}

	@RequestMapping(params = MainetConstants.AdvertisingAndHoarding.GET_CALCULATE_YEAR_TYPE, method = RequestMethod.POST)
	public @ResponseBody String gtCalculateYearTpe(@RequestParam("licType") Long licType,
			final HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);
	
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		PortalService service = PortalServiceMaster.getService(
				PortalServiceMaster.getServiceId(MainetConstants.AdvertisingAndHoarding.RENEWAL_SERVICE_CODE, orgId),
				orgId);
		this.getModel().setService(service);
		String YearType  = agencyRegistrationService
				.getCalculateYearTypeBylicType(orgId,
						MainetConstants.AdvertisingAndHoarding.RENEWAL_SERVICE_CODE,licType );
				return YearType;
	}
    
}
