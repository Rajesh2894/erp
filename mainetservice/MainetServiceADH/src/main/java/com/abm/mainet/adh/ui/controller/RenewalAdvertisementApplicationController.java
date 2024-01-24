package com.abm.mainet.adh.ui.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
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
import com.abm.mainet.adh.service.ADHCommonService;
import com.abm.mainet.adh.service.IAdvertiserMasterService;
import com.abm.mainet.adh.service.IBRMSADHService;
import com.abm.mainet.adh.service.INewAdvertisementApplicationService;
import com.abm.mainet.adh.ui.model.RenewalAdvertisementApplicationModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
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
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.validitymaster.dto.LicenseValidityMasterDto;
import com.abm.mainet.validitymaster.service.ILicenseValidityMasterService;

/**
 * @author vishwajeet.kumar
 * @since 28 Nov 2019
 */
@Controller
@RequestMapping("/RenewalAdvertisementApplication.html")
public class RenewalAdvertisementApplicationController extends AbstractFormController<RenewalAdvertisementApplicationModel> {

    @Autowired
    private INewAdvertisementApplicationService newAdvApplicationService;

    @Autowired
    private TbServicesMstService servicesMstService;

    @Autowired
    private IAdvertiserMasterService advertiserMasterService;

    @Autowired
    private IBRMSADHService brmsadhService;

    @Autowired
    private BRMSCommonService brmsCommonService;

    @Autowired
    private ILicenseValidityMasterService licenseValidityMasterService;
    
    private static final Logger LOGGER = Logger.getLogger(NewAdvertisementApplicationController.class);

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class).sessionCleanUpForFileUpload();
        List<NewAdvertisementApplicationDto> advertisementApplDtoList = newAdvApplicationService
                .getLicenseNoByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        this.getModel().setLicenseDataList(advertisementApplDtoList);
        return index();
    }

    @RequestMapping(params = MainetConstants.AdvertisingAndHoarding.GET_ADVERTISEMENT_APPLICATION_FORM, method = RequestMethod.POST)
    public ModelAndView getAdvertisementApplicationData(
            @RequestParam(MainetConstants.AdvertisingAndHoarding.LICENSE_NO) String licenseNo,
            final HttpServletRequest request) {
        this.getModel().bind(request);
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        this.getModel().setSaveMode(MainetConstants.FlagR);
        NewAdvertisementReqDto advertisementReqDto = newAdvApplicationService.getAdvertisementApplicationByLicenseNo(licenseNo,
                orgId);
        if (advertisementReqDto.getAdvertisementDto().getApmApplicationId() != null) {
            advertisementReqDto.getAdvertisementDto().setApmApplicationId(null);
        }
        ServiceMaster master = ApplicationContextProvider.getApplicationContext().getBean(TbServicesMstService.class)
                .findShortCodeByOrgId(MainetConstants.AdvertisingAndHoarding.RENEWAL_ADV_SHORTCODE, orgId);
        this.getModel().setLocationMasList(ApplicationContextProvider.getApplicationContext().getBean(ILocationMasService.class)
                .getlocationByDeptId(master.getTbDepartment().getDpDeptid(), orgId));
        
		if (Utility.compareDate(advertisementReqDto.getAdvertisementDto().getLicenseToDate(), new Date())) {
			this.getModel().setLicMaxTenureDays(ApplicationContextProvider.getApplicationContext()
					.getBean(ADHCommonService.class).calculateLicMaxTenureDays(master.getTbDepartment().getDpDeptid(),
							master.getSmServiceId(), null, UserSession.getCurrent().getOrganisation().getOrgid(),MainetConstants.ZERO_LONG)
					+ 1);
			this.getModel().setLicMinTenureDays(0l);
		} else {
			this.getModel().setLicMaxTenureDays(ApplicationContextProvider.getApplicationContext()
					.getBean(ADHCommonService.class).calculateLicMaxTenureDays(master.getTbDepartment().getDpDeptid(),
							master.getSmServiceId(), advertisementReqDto.getAdvertisementDto().getLicenseToDate(),
							UserSession.getCurrent().getOrganisation().getOrgid(),MainetConstants.ZERO_LONG)
					- 1);
			this.getModel().setLicMinTenureDays(Long.valueOf(Math.abs(Utility.getDaysBetweenDates(new Date(),
					advertisementReqDto.getAdvertisementDto().getLicenseToDate())) + 1));
		}
        
        
        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate licenseFromDate = advertisementReqDto.getAdvertisementDto().getLicenseToDate().toInstant()
                .atZone(defaultZoneId)
                .toLocalDate().plusDays(1);
        Date date = Date.from(licenseFromDate.atStartOfDay(defaultZoneId).toInstant());

        this.getModel().setAdvertisementReqDto(advertisementReqDto);

        this.getModel().getAdvertisementReqDto().getAdvertisementDto().setLicenseFromDate(date);
        return new ModelAndView(MainetConstants.AdvertisingAndHoarding.RENEWAL_ADVERTISEMENT_FORM, MainetConstants.FORM_NAME,
                this.getModel());
    }

    @RequestMapping(params = MainetConstants.AdvertisingAndHoarding.GET_AGENCY_NAME, method = RequestMethod.POST)
    public @ResponseBody List<AdvertiserMasterDto> getAgencyNameByOrgId(
            @RequestParam(MainetConstants.AdvertisingAndHoarding.ADVERTSIER_CAT) Long advertiserCategoryId) {

        List<AdvertiserMasterDto> advertiserMasterList = advertiserMasterService
                .getAgencyDetailsByAgencyCategoryAndOrgId(advertiserCategoryId,
                        UserSession.getCurrent().getOrganisation().getOrgid());
        return advertiserMasterList;
    }

    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.AdvertisingAndHoarding.APPLICABLE_CHECKLIST_CHARGE)
    public ModelAndView getApplicableCheckListFromBRMS(final HttpServletRequest request) {
        this.getModel().bind(request);
        ModelAndView modelAndView = null;
        if (this.getModel().validateInputs()) {
            findApplicableCheckList(request);
            this.getModel().setEnableSubmit(MainetConstants.FlagY);
            modelAndView = new ModelAndView(MainetConstants.AdvertisingAndHoarding.RENEWAL_ADV_DATA,
                    MainetConstants.FORM_NAME, this.getModel());
        }
        if (this.getModel().getBindingResult() != null && this.getModel().getBindingResult().hasErrors()) {
            modelAndView = new ModelAndView(MainetConstants.AdvertisingAndHoarding.RENEWAL_ADV_APPLICATION_VALIDN,
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
        RenewalAdvertisementApplicationModel applicationModel = this.getModel();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        ServiceMaster master = servicesMstService
                .findShortCodeByOrgId(MainetConstants.AdvertisingAndHoarding.RENEWAL_ADV_SHORTCODE, orgId);
        this.getModel().getAdvertisementReqDto().getAdvertisementDto()
                .setAgencyId(this.getModel().getAdvertisementReqDto().getAdvertisementDto().getAgencyId());
        WSRequestDTO initRequestDto = new WSRequestDTO();
        String chkApplicableOrNot = CommonMasterUtility.getCPDDescription(master.getSmChklstVerify(),
                MainetConstants.FlagV, UserSession.getCurrent().getOrganisation().getOrgid());
        initRequestDto.setModelName(MainetConstants.AdvertisingAndHoarding.CHECKLIST_ADHRATEMASTER);
        WSResponseDTO response = brmsCommonService.initializeModel(initRequestDto);
        if (response.getWsStatus() != null
                && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
            if (chkApplicableOrNot.equalsIgnoreCase(MainetConstants.FlagA)) {
                List<Object> checklist = RestClient.castResponse(response, CheckListModel.class, 0);
                CheckListModel checkListModel = (CheckListModel) checklist.get(0);
                checkListModel.setOrgId(orgId);
                checkListModel.setServiceCode(MainetConstants.AdvertisingAndHoarding.RENEWAL_ADV_SHORTCODE);
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
                    applicationModel
                            .addValidationError(getApplicationSession().getMessage("adh.problem.while.getting.checklist"));
                    LOGGER.error("Problem while getting CheckList");
                }

            }
            Organisation organisation = new Organisation();
            organisation.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
            List<Object> adhRateMasterList = RestClient.castResponse(response, ADHRateMaster.class, 1);
            ADHRateMaster adhRateMaster = (ADHRateMaster) adhRateMasterList.get(0);
            WSRequestDTO taxReqDTO = new WSRequestDTO();
            adhRateMaster.setOrgId(orgId);
            adhRateMaster.setServiceCode(MainetConstants.AdvertisingAndHoarding.RENEWAL_ADV_SHORTCODE);
            LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
                    MainetConstants.AdvertisingAndHoarding.APL, PrefixConstants.NewWaterServiceConstants.CAA,
                    organisation);
            adhRateMaster.setChargeApplicableAt(Long.toString(chargeApplicableAt.getLookUpId()));
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
                        master1.setServiceCode(MainetConstants.AdvertisingAndHoarding.RENEWAL_ADV_SHORTCODE);
                        master1.setDeptCode(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE);
                        master1.setRateStartDate(new Date().getTime());
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
    
//Defect #123725
    
    @RequestMapping(params = MainetConstants.AdvertisingAndHoarding.GET_LICENCE_TYPE, method = RequestMethod.POST)
	public @ResponseBody Long getLicMaxTenureDays(@RequestParam("licType") Long licType,
			final HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);
		ServiceMaster serviceMaster = ApplicationContextProvider.getApplicationContext()
				.getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode(MainetConstants.AdvertisingAndHoarding.RENEWAL_ADV_SHORTCODE,
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
				.getServiceMasterByShortCode(MainetConstants.AdvertisingAndHoarding.RENEWAL_ADV_SHORTCODE,
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
