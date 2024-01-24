package com.abm.mainet.adh.ui.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
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
import com.abm.mainet.adh.dto.AgencyRegistrationResponseDto;
import com.abm.mainet.adh.service.IAgencyRegistrationService;
import com.abm.mainet.adh.service.IBRMSADHService;
import com.abm.mainet.adh.service.INewAdvertisementApplicationService;
import com.abm.mainet.adh.ui.model.AgencyRegistrationFormModel;
import com.abm.mainet.adh.ui.validator.AgencyRegistrationFormValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
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
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.dms.domain.CFCAttachment;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.dms.utility.FileUploadUtility;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * @author cherupelli.srikanth
 * @since 23 October 2019
 */
@Controller
@RequestMapping("/AgencyRegistrationRenewalForm.html")
public class AgencyRegistrationRenewalFormController extends AbstractFormController<AgencyRegistrationFormModel> {

	@Autowired
	IAgencyRegistrationService agencyRegistrationService;

	@Autowired
	private ICommonBRMSService iCommonBRMSService;

	@Autowired
	IBRMSADHService BRMSADHService;

	@Autowired
	private IPortalServiceMasterService PortalServiceMaster;
	
	@Autowired
    private INewAdvertisementApplicationService advertisementApplicationService;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(HttpServletRequest request) {
		sessionCleanup(request);
		this.getModel().setFormDisplayFlag(MainetConstants.FlagN);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		PortalService service = PortalServiceMaster.getService(PortalServiceMaster
				.getServiceId(MainetConstants.AdvertisingAndHoarding.AGENCY_REG_REN_SHORT_CODE, orgId), orgId);
		this.getModel().setService(service);

		List<AdvertiserMasterDto> masterDtoList = agencyRegistrationService
				.getLicNoAndAgenNameAndStatusByorgId(UserSession.getCurrent().getOrganisation().getOrgid());
		//D#79136
		/*if (CollectionUtils.isNotEmpty(masterDtoList)) {
			masterDtoList.forEach(masterDto -> {
				if (!StringUtils.equals(masterDto.getAgencyStatus(), MainetConstants.AdvertisingAndHoarding.FLAGC)
						&& !StringUtils.equals(masterDto.getAgencyStatus(),
								MainetConstants.Common_Constant.INACTIVE_FLAG)) {
					this.getModel().getMasterDtoList().add(masterDto);
				}
			});
		}*/
		this.getModel().setMasterDtoList(masterDtoList.stream()
                .filter(master -> StringUtils.equals(master.getApplicantMobileNo(),UserSession.getCurrent().getEmployee().getEmpmobno())
                		&& !StringUtils.equals(master.getAgencyStatus(),MainetConstants.AdvertisingAndHoarding.FLAGC)
                        && !StringUtils.equals(master.getAgencyStatus(),MainetConstants.Common_Constant.INACTIVE_FLAG)
                        && StringUtils.isNotBlank(master.getAgencyLicNo()))
                .collect(Collectors.toList()));

		LinkedHashMap<String, Object> checkListChargeFlagAndLicMaxDay = agencyRegistrationService
				.getCheckListChargeFlagAndLicMaxDay(UserSession.getCurrent().getOrganisation().getOrgid(),
						MainetConstants.AdvertisingAndHoarding.AGENCY_REG_REN_SHORT_CODE);
		if (MapUtils.isNotEmpty(checkListChargeFlagAndLicMaxDay)) {
			for (Map.Entry<String, Object> map : checkListChargeFlagAndLicMaxDay.entrySet()) {
				if (StringUtils.equals(map.getKey(), MainetConstants.AdvertisingAndHoarding.CHECKLIST_APPL_FLAG)) {
					this.getModel().setCheckListApplFlag(map.getValue().toString());
				}
				if (StringUtils.equals(map.getKey(), MainetConstants.AdvertisingAndHoarding.APPL_CHARGE_FLAG)) {
					this.getModel().setApplicationchargeApplFlag(map.getValue().toString());
				}
				if (StringUtils.equals(map.getKey(), MainetConstants.AdvertisingAndHoarding.LIC_MAX_TENURE_DAYS)) {
					Long valueOf = Long.valueOf(map.getValue().toString());
					this.getModel().setLicMaxTenureDays(valueOf);
				}

			}
		}
		if (StringUtils.equals(this.getModel().getApplicationchargeApplFlag(), MainetConstants.FlagY))
			this.getModel().getAgencyRequestDto().setFree(false);
		if (StringUtils.equals(this.getModel().getApplicationchargeApplFlag(), MainetConstants.FlagN))
			this.getModel().getAgencyRequestDto().setFree(true);
		return index();

	}

	@SuppressWarnings("unused")
	@RequestMapping(params = MainetConstants.AdvertisingAndHoarding.SEARCH_AGENCY_BY_LICNO, method = {
			RequestMethod.POST })
	public ModelAndView searchAgencyByLicNo(
			@RequestParam(MainetConstants.AdvertisingAndHoarding.AGENCY_LIC_NO) String agencyLicNo,
			HttpServletRequest request) throws Exception {

		this.getModel().bind(request);
		FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
		AgencyRegistrationFormModel model = this.getModel();

		ModelAndView mv = new ModelAndView(MainetConstants.AdvertisingAndHoarding.AGENCY_REG_RENEW_VALIDN,
				MainetConstants.FORM_NAME, this.getModel());

		AgencyRegistrationResponseDto agencyRegrDto = agencyRegistrationService.getAgencyDetailByLicnoAndOrgId(agencyLicNo,
				UserSession.getCurrent().getOrganisation().getOrgid());

		if (agencyRegrDto.getMasterDto() != null) {
			LocalDate date = agencyRegrDto.getMasterDto().getAgencyLicToDate().toInstant().atZone(ZoneId.systemDefault())
                    .toLocalDate();
            LocalDate licEligibleLocalDate = date.minusMonths(3);
            Date licEligibleDate = Date.from(licEligibleLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
			if (StringUtils.equalsIgnoreCase(agencyRegrDto.getMasterDto().getAgencyStatus(), "T")) {
				model.addValidationError(
						getApplicationSession().getMessage("agency.registration.validate.already.applied.service"));
			} else {
				 if (Utility.compareDate(licEligibleDate, new Date())
	                    || (Utility.comapreDates(licEligibleDate, new Date())
	                    && !Utility.comapreDates(agencyRegrDto.getMasterDto().getAgencyLicFromDate(), new Date()))) {
					 
				 
				if (agencyRegrDto.getMasterDto().getAgencyLicFromDate().equals(new Date())) {
					model.addValidationError(getApplicationSession().getMessage(
							"agency.registration.validate.noteligible.toapply"));
				} else {
					if (Utility.compareDate(agencyRegrDto.getMasterDto().getAgencyLicToDate(), new Date())) {
						this.getModel().setLicMaxTenureDays(model.getLicMaxTenureDays() + 1);
						this.getModel().setLicMinTenureDays(0l);
					} else {
						int daysBetLicToAndCurrDate = Utility.getDaysBetweenDates(new Date(),
								agencyRegrDto.getMasterDto().getAgencyLicToDate());
						this.getModel()
								.setLicMaxTenureDays(model.getLicMaxTenureDays() - Math.abs(daysBetLicToAndCurrDate));
						this.getModel().setLicMinTenureDays(Long.valueOf(Math.abs(daysBetLicToAndCurrDate) + 1));
					}
					ZoneId defaultZoneId = ZoneId.systemDefault();
	                LocalDate licenseFromDate = agencyRegrDto.getMasterDto().getAgencyLicToDate().toInstant().atZone(ZoneId.systemDefault())
	                        .toLocalDate().plusDays(1);
	                Date FromDate = Date.from(licenseFromDate.atStartOfDay(defaultZoneId).toInstant());
	                agencyRegrDto.getMasterDto().setAgencyLicFromDate(FromDate);
					this.getModel().getAgencyRequestDto().setMasterDto(agencyRegrDto.getMasterDto());
					this.getModel().getAgencyRequestDto().setMasterDtolist(agencyRegrDto.getMasterDtolist());
					this.getModel().setFormDisplayFlag(MainetConstants.FlagY);
					this.getModel().setApplicantDetailDto(agencyRegrDto.getApplicantDetailDTO());
				}
				
				 } else {
		                String licToDate = new SimpleDateFormat(MainetConstants.DATE_FORMAT)
		                        .format(agencyRegrDto.getMasterDto().getAgencyLicToDate());
		                String licFromDate = new SimpleDateFormat(MainetConstants.DATE_FORMAT)
		                        .format(agencyRegrDto.getMasterDto().getAgencyLicFromDate());

		                model.addValidationError(getApplicationSession().getMessage("Your license period is " + " "
		                        + licFromDate + " " + "to " + licToDate + " "
		                        + "You can apply to this service before three months of expiry date. / After completion of one day from license from date"));
		            }

			}

		} else {
			model.addValidationError(getApplicationSession().getMessage("adh.validate.search"));

		}
		this.getModel().setViewMode(MainetConstants.FlagV);
    	this.getModel().setOpenMode(MainetConstants.FlagD);
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
	}

	@RequestMapping(params = MainetConstants.AdvertisingAndHoarding.GET_CHECKLIST_CHARGES, method = {
			RequestMethod.POST })
	public ModelAndView getCheckListAndCharges(HttpServletRequest request) {
		this.getModel().bind(request);
		AgencyRegistrationFormModel model = this.getModel();
		model.validateBean(model.getAgencyRequestDto().getMasterDto(), AgencyRegistrationFormValidator.class);
		ModelAndView mv = new ModelAndView(MainetConstants.AdvertisingAndHoarding.AGENCY_REG_RENEW_VALIDN,
				MainetConstants.FORM_NAME, this.getModel());

		if (!model.hasValidationErrors()) {
			final WSRequestDTO checkListRateRequestModel = new WSRequestDTO();
			checkListRateRequestModel.setModelName(MainetConstants.AdvertisingAndHoarding.CHECKLIST_ADHRATEMASTER);
			WSResponseDTO checkListRateResponseModel = iCommonBRMSService.initializeModel(checkListRateRequestModel);

			// checking the checkList initializing response is success or not(if not
			// displaying the message on Screen)
			if (StringUtils.equalsIgnoreCase(MainetConstants.WebServiceStatus.SUCCESS,
					checkListRateResponseModel.getWsStatus())) {
				List<DocumentDetailsVO> checkListList = new ArrayList<>();
				final List<Object> castCheckListModel = JersyCall.castResponse(checkListRateResponseModel,
						CheckListModel.class, 0);
				final List<Object> ADHRateMasterList = JersyCall.castResponse(checkListRateResponseModel,
						ADHRateMaster.class, 1);

				final CheckListModel checkListModel = (CheckListModel) castCheckListModel.get(0);
				final ADHRateMaster ADHRateMaster = (ADHRateMaster) ADHRateMasterList.get(0);

				populateChecklistModel(checkListModel);
				WSRequestDTO checklistReqDto = new WSRequestDTO();
				checklistReqDto.setModelName(MainetConstants.AdvertisingAndHoarding.CHECK_LIST);
				checklistReqDto.setDataModel(checkListModel);

				checkListList = iCommonBRMSService.getChecklist(checkListModel);

				// checking the response after getting applicable checklist from BRMS is
				// success.(If not then, displaying the validation message on Screen)
				if (checkListList != null && !checkListList.isEmpty()) {
					this.getModel().setCheckListApplFlag(MainetConstants.FlagN);
					if (CollectionUtils.isNotEmpty(checkListList)) {
						long count = 1;
						for (DocumentDetailsVO doc : checkListList) {
							doc.setDocumentSerialNo(count);
							count++;
						}
						model.setCheckList(checkListList);
					}
				}

				if (StringUtils.equals(model.getApplicationchargeApplFlag(), MainetConstants.FlagY)) {
					Double applicationCharge = callBrmsForApplicationCharges(model, ADHRateMaster);
					if (applicationCharge <= 0) {
						model.addValidationError(getApplicationSession().getMessage("adh.validate.application.charges.notfound"));
					}
				}
			} else {
				// This message if error occured in initializing the checkList
				model.addValidationError(
						getApplicationSession().getMessage("adh.problem.occured.initializing.checklist"));
			}

		}
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
	}

	private void populateChecklistModel(CheckListModel checklistModel) {

		checklistModel.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		checklistModel.setServiceCode(MainetConstants.AdvertisingAndHoarding.AGENCY_REG_REN_SHORT_CODE);
		checklistModel.setDeptCode(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE);
	}

	@ResponseBody
	@RequestMapping(params = MainetConstants.AdvertisingAndHoarding.SAVE_AGENCY_REGISTRATION, method = RequestMethod.POST)
	public Map<String, Object> saveAgencyRegistration(HttpServletRequest httpServletRequest)
			throws JsonParseException, JsonMappingException, IOException {
		getModel().bind(httpServletRequest);
		Map<String, Object> object = new LinkedHashMap<String, Object>();
		if (this.getModel().saveForm()) {
			object.put(MainetConstants.AdvertisingAndHoarding.APPLICATION_ID,
					this.getModel().getAgencyRequestDto().getApplicationId());
			this.getModel().getAgencyRequestDto().getMasterDto()
					.setApmApplicationId(this.getModel().getAgencyRequestDto().getApplicationId());

		} else {
			object.put(MainetConstants.AdvertisingAndHoarding.ERROR, this.getModel().getBindingResult().getAllErrors());
		}

		return object;

	}

	/**
	 * This method is used to print Agency Registraton Acknowledgement
	 * 
	 * @param request
	 * @return Agency Registraton Acknowledgement
	 */
	@RequestMapping(params = MainetConstants.AdvertisingAndHoarding.PRINT_AGENCY_REG_ACKW, method = {
			RequestMethod.POST })
	public ModelAndView printAgencyRegAcknowledgement(HttpServletRequest request) {
		AgencyRegistrationFormModel model = this.getModel();
		List<CFCAttachment> downloadDocs = new ArrayList<>();
		List<CFCAttachment> preparePreviewOfFileUpload = model.preparePreviewOfFileUpload(downloadDocs,
				this.getModel().getCheckList());

		if (CollectionUtils.isNotEmpty(preparePreviewOfFileUpload)) {
			this.getModel().setDocumentList(preparePreviewOfFileUpload);
		}

		String applicantName = model.getApplicantDetailDto().getApplicantFirstName() + MainetConstants.WHITE_SPACE;
		applicantName += model.getApplicantDetailDto().getApplicantMiddleName() == null ? MainetConstants.BLANK
				: model.getApplicantDetailDto().getApplicantMiddleName() + MainetConstants.WHITE_SPACE;
		applicantName += model.getApplicantDetailDto().getApplicantLastName();
		this.getModel().setApplicationId(model.getAgencyRequestDto().getMasterDto().getApmApplicationId());
		this.getModel().setApplicantName(applicantName);
		return new ModelAndView(MainetConstants.AdvertisingAndHoarding.AGENCY_REGISTRATION_ACKNOWLEDGEMENT,
				MainetConstants.FORM_NAME, this.getModel());

	}

	private double chargesToPay(final List<ChargeDetailDTO> charges) {
		double amountSum = 0.0;
		for (final ChargeDetailDTO charge : charges) {
			amountSum = amountSum + charge.getChargeAmount();
		}
		return amountSum;
	}

	private Double callBrmsForApplicationCharges(AgencyRegistrationFormModel model, ADHRateMaster ADHRateMaster) {
		Double amoutToPay = 0.0;
		final WSRequestDTO adhRateRequestDto = new WSRequestDTO();
		ADHRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		ADHRateMaster.setServiceCode(MainetConstants.AdvertisingAndHoarding.AGENCY_REG_REN_SHORT_CODE);
		ADHRateMaster.setChargeApplicableAt(
				Long.toString(CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.Prefix.APL,
						PrefixConstants.Common.CAA, UserSession.getCurrent().getOrganisation()).getLookUpId()));
		adhRateRequestDto.setDataModel(ADHRateMaster);

		ADHRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		ADHRateMaster.setServiceCode(MainetConstants.AdvertisingAndHoarding.AGENCY_REG_REN_SHORT_CODE);

		WSResponseDTO adhRateResponseDto = BRMSADHService.getApplicableTaxes(ADHRateMaster);
		if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(adhRateResponseDto.getWsStatus())) {
			if (!adhRateResponseDto.isFree()) {

				final List<?> rates = JersyCall.castResponse(adhRateResponseDto, ADHRateMaster.class);
				final List<ADHRateMaster> requiredCHarges = new ArrayList<>();
				for (final Object rate : rates) {
					ADHRateMaster master1 = (com.abm.mainet.adh.datamodel.ADHRateMaster) rate;
					master1 = populateChargeModel(model, master1);
					requiredCHarges.add(master1);
				}
				WSRequestDTO chargeReqDto = new WSRequestDTO();
				chargeReqDto.setModelName(MainetConstants.AdvertisingAndHoarding.ADH_Rate_Master);
				chargeReqDto.setDataModel(requiredCHarges);
				List<ChargeDetailDTO> detailDTOs = BRMSADHService.getApplicableCharges(requiredCHarges);

				model.getAgencyRequestDto().setFree(false);
				model.setPayableFlag(MainetConstants.FlagY);
				model.setChargesInfo(detailDTOs);
				model.setAmountToPay((chargesToPay(detailDTOs)));
				model.getOfflineDTO().setAmountToShow(model.getAmountToPay());
				model.setApplicationchargeApplFlag(MainetConstants.FlagN);
			} else {
				model.setPayableFlag(MainetConstants.FlagN);
				model.getAgencyRequestDto().setFree(true);
				model.setAmountToPay(0.0d);
			}
		}
		amoutToPay = model.getAmountToPay();
		return amoutToPay;
	}

	private ADHRateMaster populateChargeModel(final AgencyRegistrationFormModel model,
			final ADHRateMaster chargeModel) {
		chargeModel.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		chargeModel.setServiceCode(MainetConstants.AdvertisingAndHoarding.AGENCY_REG_REN_SHORT_CODE);
		chargeModel.setRateStartDate(new Date().getTime());
		chargeModel.setDeptCode(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE);
		return chargeModel;
	}

	@RequestMapping(params = "showDetails", method = RequestMethod.POST)
	public ModelAndView showDetails(@RequestParam("appId") final long appId,
			final HttpServletRequest httpServletRequest) throws Exception {
		getModel().bind(httpServletRequest);
		try {
			//call to GET_ADH_DATA_BY_APPLICATION_ID
			ADHRequestDto adhRequestDto = new ADHRequestDto();
			adhRequestDto.setApplicationId(appId);
			adhRequestDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			adhRequestDto.setServiceCode(MainetConstants.AdvertisingAndHoarding.AGENCY_REG_REN_SHORT_CODE);
			ADHResponseDTO  adhResponseDTO=advertisementApplicationService.getADHDataByApplicationId(adhRequestDto);
			//getModel().setAgencyRequestDto(adhResponseDTO.getAgencyRegistrationRequestDto());
			getModel().setMasterDtoList(adhResponseDTO.getAgencyRegistrationRequestDto().getMasterDtolist());
			getModel().setApplicantDetailDto(adhResponseDTO.getAgencyRegistrationRequestDto().getApplicantDetailDto());
			
		} catch (Exception exception) {
			throw new FrameworkException(exception);
		}
		LookUp agencyTypeLookUp = CommonMasterUtility.getValueFromPrefixLookUp(
				PrefixConstants.ADVERTISEMENT_AND_HOARDING_PREFIX.AGN,
				PrefixConstants.ADVERTISEMENT_AND_HOARDING_PREFIX.ADC, UserSession.getCurrent().getOrganisation());

		if (agencyTypeLookUp != null) {
			getModel().setAgenctCategoryLookUp(agencyTypeLookUp);
		}
		getModel().setSaveMode(MainetConstants.AdvertisingAndHoarding.VIEW);
		getModel().setFormDisplayFlag(MainetConstants.FlagY);
		getModel().setCheckListApplFlag(MainetConstants.AdvertisingAndHoarding.VIEW);
		getModel().setApplicationchargeApplFlag(MainetConstants.AdvertisingAndHoarding.VIEW);
		return new  ModelAndView(MainetConstants.AdvertisingAndHoarding.AGENCY_REG_RENEW_VALIDN,
				MainetConstants.FORM_NAME, getModel());
	}
	
	  //Defect  126626
	/**
	 * To get Ownership table
	 * 
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @param ownershipType
	 * @return
	 */
	@RequestMapping(params = MainetConstants.AdvertisingAndHoarding.GET_RENEWAL_OWNERSHIP_TYPE_DIV, method = RequestMethod.POST)
	public ModelAndView getRenewalOwnershipTypeDiv(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			@RequestParam(value = MainetConstants.AdvertisingAndHoarding.RENEWAL_OWNERSHIP_TYPE) String RenewalownershipType) {
		this.getModel().bind(httpServletRequest);

		AgencyRegistrationFormModel model = this.getModel();
		model.setOwnershipPrefix(RenewalownershipType);
		
		if( model.getAgencyRequestDto().getMasterDtolist().isEmpty() || (model.getAgencyRequestDto().getMasterDtolist() !=null 
				&& model.getAgencyRequestDto().getMasterDtolist().size() <= 2 && model.getAgencyRequestDto().getMasterDtolist().get(0).getAgencyOwner() == null  )) {
			model.getAgencyRequestDto().getMasterDtolist().clear();
		if(model.getOwnershipPrefix().equals("A")) {
			AdvertiserMasterDto dto=new AdvertiserMasterDto();
			AdvertiserMasterDto dto1=new AdvertiserMasterDto();
			model.getAgencyRequestDto().getMasterDtolist().add(dto);
			model.getAgencyRequestDto().getMasterDtolist().add(dto1);
		}
		}
	   
		
		return new ModelAndView(MainetConstants.AdvertisingAndHoarding.RENEWAL_OWNERSHIP_DETAIL_TABLE, MainetConstants.FORM_NAME, model);
	}

	@RequestMapping(params = MainetConstants.AdvertisingAndHoarding.GET_LICENCE_TYPE, method = RequestMethod.POST)
	public @ResponseBody String getLicMaxTenureDays(@RequestParam("licType") Long licType,
			final HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		PortalService service = PortalServiceMaster.getService(
				PortalServiceMaster.getServiceId(MainetConstants.AdvertisingAndHoarding.AGENCY_REG_REN_SHORT_CODE, orgId), orgId);
		this.getModel().setService(service);
		String checkListChargeFlagAndLicMaxDay = advertisementApplicationService.getLicMaxTenureDays(
				UserSession.getCurrent().getOrganisation().getOrgid(),
				MainetConstants.AdvertisingAndHoarding.AGENCY_REG_REN_SHORT_CODE, licType);
		return checkListChargeFlagAndLicMaxDay;

	}

	@RequestMapping(params = MainetConstants.AdvertisingAndHoarding.GET_CALCULATE_YEAR_TYPE, method = RequestMethod.POST)
	public @ResponseBody String gtCalculateYearTpe(@RequestParam("licType") Long licType,
			final HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);
	
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		PortalService service = PortalServiceMaster.getService(
				PortalServiceMaster.getServiceId(MainetConstants.AdvertisingAndHoarding.AGENCY_REG_REN_SHORT_CODE, orgId),
				orgId);
		this.getModel().setService(service);
		String YearType  = agencyRegistrationService
				.getCalculateYearTypeBylicType(orgId,
						MainetConstants.AdvertisingAndHoarding.AGENCY_REG_REN_SHORT_CODE,licType );
				return YearType;
	}	
}
