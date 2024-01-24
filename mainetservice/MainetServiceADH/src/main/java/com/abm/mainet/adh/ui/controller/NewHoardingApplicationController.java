
package com.abm.mainet.adh.ui.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.adh.datamodel.ADHRateMaster;
import com.abm.mainet.adh.dto.AdvertiserMasterDto;
import com.abm.mainet.adh.dto.HoardingBookingDto;
import com.abm.mainet.adh.dto.HoardingMasterDto;
import com.abm.mainet.adh.dto.NewAdvertisementApplicationDetDto;
import com.abm.mainet.adh.service.ADHCommonService;
import com.abm.mainet.adh.service.IAdvertiserMasterService;
import com.abm.mainet.adh.service.IBRMSADHService;
import com.abm.mainet.adh.service.IHoardingMasterService;
import com.abm.mainet.adh.ui.model.NewHoardingApplicationModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.LocationOperationWZMapping;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.brms.datamodel.CheckListModel;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
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
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.validitymaster.dto.LicenseValidityMasterDto;
import com.abm.mainet.validitymaster.service.ILicenseValidityMasterService;

/**
 * @author anwarul.hassan
 * @since 17-Oct-2019
 */
@Controller
@RequestMapping(MainetConstants.AdvertisingAndHoarding.NEW_HRD_APP_HTML)
public class NewHoardingApplicationController extends AbstractFormController<NewHoardingApplicationModel> {

    private static final Logger LOGGER = Logger.getLogger(NewHoardingApplicationController.class);
    @Autowired
    private ILocationMasService locationMasService;
    @Autowired
    private TbServicesMstService servicesMstService;
    @Autowired
    private BRMSCommonService brmsCommonService;
    @Autowired
    private IAdvertiserMasterService advertiserMasterService;
    @Autowired
    private IHoardingMasterService hoardingMasterService;

    @Autowired
    private IFileUploadService fileUpload;
    @Autowired
    private IBRMSADHService brmsAdhService;
    
    @Autowired
	ILicenseValidityMasterService licenseValidityMasterService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final Model model, final HttpServletRequest request) {
        sessionCleanup(request);
        fileUpload.sessionCleanUpForFileUpload();
        ServiceMaster master = servicesMstService.findShortCodeByOrgId(
                MainetConstants.AdvertisingAndHoarding.HOARDING_REGISTRATION_SHORT_CODE,
                UserSession.getCurrent().getOrganisation().getOrgid());
        this.getModel().setLocationMasList(
                locationMasService.getlocationByDeptId(master.getTbDepartment().getDpDeptid(),
                        UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setHoardingNumberList(hoardingMasterService
                .getHoardingNumberAndIdListByOrgId(UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel()
        .setLicMaxTenureDays(ApplicationContextProvider.getApplicationContext().getBean(ADHCommonService.class)
                .calculateLicMaxTenureDays(master.getTbDepartment().getDpDeptid(), master.getSmServiceId(),
                        null, UserSession.getCurrent().getOrganisation().getOrgid(),MainetConstants.ZERO_LONG));
          return index();
    }

    @RequestMapping(params = MainetConstants.AdvertisingAndHoarding.GET_AGENCY_NAME, method = RequestMethod.POST)
    public @ResponseBody List<AdvertiserMasterDto> getAgencyNameByOrgId(
            @RequestParam(MainetConstants.AdvertisingAndHoarding.ADVERTSIER_CAT) Long advertiserCategoryId) {

        List<AdvertiserMasterDto> advertiserMasterList = advertiserMasterService
                .getAgencyDetailsByAgencyCategoryAndOrgId(advertiserCategoryId,
                        UserSession.getCurrent().getOrganisation().getOrgid());
        return advertiserMasterList;
    }

    @ResponseBody
    @RequestMapping(params = MainetConstants.AdvertisingAndHoarding.GET_HRD_DET_BY_NUM, method = { RequestMethod.POST })
    public Map<String, Object> searchHoardingDetailsByHoardingNumber(
            @RequestParam(MainetConstants.AdvertisingAndHoarding.HOARDING_ID) Long hoardingId,
            HttpServletRequest request) {
        Map<String, Object> object = new LinkedHashMap<String, Object>();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        HoardingMasterDto hoardingMasterDto = hoardingMasterService
                .getByOrgIdAndHoardingId(UserSession.getCurrent().getOrganisation().getOrgid(), hoardingId);    
  
		List<HoardingBookingDto> bookingDto = hoardingMasterService.getHoardingDetailsByOrgId(orgId);
		for (HoardingBookingDto hoardingBookingDto : bookingDto) {
			if(hoardingBookingDto.getHoardingId() != null) {
			if (hoardingBookingDto.getHoardingId().equals(hoardingId)) {
				hoardingMasterDto.setStatusFlag(MainetConstants.FlagY);
			}
			}
		}
        // this.getModel().setHoardingMasterDto(hoardingMasterDto);
        hoardingMasterDto.setDisplayIdDesc(
                CommonMasterUtility.getNonHierarchicalLookUpObject(hoardingMasterDto.getDisplayTypeId(),
                        UserSession.getCurrent().getOrganisation()).getDescLangFirst());
        object.put("hoardingDto", hoardingMasterDto);
        object.put("status", hoardingMasterDto.getStatusFlag());
        return object;
    }

    @ResponseBody
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
                    MainetConstants.CommonConstants.COMMAND,
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
        NewHoardingApplicationModel applicationModel = this.getModel();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        ServiceMaster master = servicesMstService.findShortCodeByOrgId(
                MainetConstants.AdvertisingAndHoarding.HOARDING_REGISTRATION_SHORT_CODE,
                orgId);
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
        this.getModel().getAdvertisementReqDto().getAdvertisementDto()
                .setAgencyId(this.getModel().getAdvertisementReqDto().getAdvertisementDto().getAgencyId());
        WSRequestDTO initRequestDto = new WSRequestDTO();
        String chkApplicableOrNot = CommonMasterUtility.getCPDDescription(master.getSmChklstVerify(), MainetConstants.FlagV,
                UserSession.getCurrent().getOrganisation().getOrgid());
        initRequestDto.setModelName(MainetConstants.AdvertisingAndHoarding.CHECKLIST_ADHRATEMASTER);
        WSResponseDTO response = brmsCommonService.initializeModel(initRequestDto);
        if (response.getWsStatus() != null
                && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
            if (chkApplicableOrNot.equalsIgnoreCase(MainetConstants.FlagA)) {
                List<Object> checklist = RestClient.castResponse(response, CheckListModel.class, 0);
                CheckListModel checkListModel = (CheckListModel) checklist.get(0);
                checkListModel.setOrgId(orgId);
                checkListModel.setServiceCode(MainetConstants.AdvertisingAndHoarding.HOARDING_REGISTRATION_SHORT_CODE);
                final WSRequestDTO checkRequestDto = new WSRequestDTO();
                checkRequestDto.setDataModel(checkListModel);
                WSResponseDTO checkListResponse = brmsCommonService.getChecklist(checkRequestDto);
                List<DocumentDetailsVO> checklistDoc = Collections.emptyList();
                if (checkListResponse.getWsStatus() != null
                        && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(checkListResponse.getWsStatus())) {
                    checklistDoc = (List<DocumentDetailsVO>) checkListResponse.getResponseObj();
                    if (checklistDoc != null && !checklistDoc.isEmpty()) {
                        long cnt = 1;
                        for (final DocumentDetailsVO doc : checklistDoc) {
                            doc.setDocumentSerialNo(cnt);
                            cnt++;
                        }
                    }
                    this.getModel().setCheckList(checklistDoc);
                } else {
                    applicationModel.addValidationError(getApplicationSession().getMessage("adh.problem.while.getting.checklist"));
                    LOGGER.error("Problem while getting CheckList");
                }
            }
            Organisation organisation = new Organisation();
            organisation.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
            List<Object> adhRateMasterList = RestClient.castResponse(response, ADHRateMaster.class, 1);
            ADHRateMaster adhRateMaster = (ADHRateMaster) adhRateMasterList.get(0);
            WSRequestDTO taxReqDto = new WSRequestDTO();
            adhRateMaster.setOrgId(orgId);
            adhRateMaster.setServiceCode(MainetConstants.AdvertisingAndHoarding.HOARDING_REGISTRATION_SHORT_CODE);
            LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.LookUpPrefix.APL,
                    PrefixConstants.LookUpPrefix.CAA, organisation);
            adhRateMaster.setChargeApplicableAt(Long.toString(chargeApplicableAt.getLookUpId()));
            taxReqDto.setDataModel(adhRateMaster);
            final WSResponseDTO taxResponseDto = brmsAdhService.getApplicableTaxes(taxReqDto);
            if (taxResponseDto != null
                    && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(taxResponseDto.getWsStatus())) {
                if (!taxResponseDto.isFree()) {
                    final List<Object> rates = (List<Object>) taxResponseDto.getResponseObj();
                    final List<ADHRateMaster> requiredCharges = new ArrayList<ADHRateMaster>();
                    for (Object rate : rates) {
                        ADHRateMaster adhMaster = (ADHRateMaster) rate;
                        adhMaster.setOrgId(orgId);
                        adhMaster.setServiceCode(MainetConstants.AdvertisingAndHoarding.HOARDING_REGISTRATION_SHORT_CODE);
                        adhMaster.setDeptCode(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE);
                        adhMaster.setRateStartDate(new Date().getTime());
                        requiredCharges.add(adhMaster);
                    }
                    WSRequestDTO chargeReqDto = new WSRequestDTO();
                    chargeReqDto.setDataModel(requiredCharges);
                    WSResponseDTO applicableCharges = brmsAdhService.getApplicableCharges(chargeReqDto);
                    final List<ChargeDetailDTO> chargeDetailDTOs = (List<ChargeDetailDTO>) applicableCharges.getResponseObj();
                    if (chargeDetailDTOs == null) {
                        applicationModel
                                .addValidationError(getApplicationSession().getMessage("adh.charges.not.found.brms.sheet"));
                        LOGGER.error("Charges not found in brms sheet");
                    } else {
                        applicationModel.setChargesInfo(newChargesToPay(chargeDetailDTOs));
                        applicationModel.setAmountToPay(chargesToPay(applicationModel.getChargesInfo()));
                        if (applicationModel.getAmountToPay() == 0.0d) {
                            applicationModel.addValidationError(getApplicationSession().getMessage(
                                    "adh.service.charge.amountToPay.cannot.be") + applicationModel.getAmountToPay()
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
                LOGGER.error("Exception occured while fecthing Depends on factor for ADH Rate Mster");
            }
        } else {
            applicationModel.addValidationError(
                    getApplicationSession().getMessage("adh.problem.while.initializing.checklist.and.ADHratemaster.model"));
            LOGGER.error("Problem while initializing CheckList and ADHRateMaster Model");
        }
        }
       else {
        this.getModel().getAdvertisementReqDto().setFree(true);
        applicationModel.setPayable(false);
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
    
    
//Defect #123725
    
    @RequestMapping(params = MainetConstants.AdvertisingAndHoarding.GET_LICENCE_TYPE, method = RequestMethod.POST)
	public @ResponseBody Long getLicMaxTenureDays(@RequestParam("licType") Long licType,
			final HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);
		ServiceMaster serviceMaster = ApplicationContextProvider.getApplicationContext()
				.getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode(MainetConstants.AdvertisingAndHoarding.HOARDING_REGISTRATION_SHORT_CODE,
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
				.getServiceMasterByShortCode(MainetConstants.AdvertisingAndHoarding.HOARDING_REGISTRATION_SHORT_CODE,
						UserSession.getCurrent().getOrganisation().getOrgid());
		List<LicenseValidityMasterDto> licValMasterDtoList = licenseValidityMasterService.searchLicenseValidityData(
				UserSession.getCurrent().getOrganisation().getOrgid(), serviceMaster.getTbDepartment().getDpDeptid(),
				serviceMaster.getSmServiceId(), MainetConstants.ZERO_LONG, licType);
		LookUp dependsOnLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(
				licValMasterDtoList.get(0).getLicDependsOn(), UserSession.getCurrent().getOrganisation());
		String YearType = dependsOnLookUp.getLookUpCode();
		return YearType;
	}
	
}
