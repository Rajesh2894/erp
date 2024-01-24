package com.abm.mainet.adh.ui.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

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
import com.abm.mainet.adh.dto.AgencyRegistrationRequestDto;
import com.abm.mainet.adh.dto.AgencyRegistrationResponseDto;
import com.abm.mainet.adh.service.IAgencyRegistrationService;
import com.abm.mainet.adh.service.IBRMSADHService;
import com.abm.mainet.adh.service.INewAdvertisementApplicationService;
import com.abm.mainet.adh.ui.model.AdvertiserCancellationFormModel;
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
import com.abm.mainet.common.ui.view.JsonViewObject;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.dms.domain.CFCAttachment;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * @author Sharvan kumar Mandal
 * @since 24 June 2021
 */

@Controller
@RequestMapping("/AdvertisercancellationForm.html")
public class AdvertiserCancellationFormController extends AbstractFormController<AdvertiserCancellationFormModel> {

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

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(HttpServletRequest request) {
		sessionCleanup(request);
		getModel().bind(request);
		// Added new controller class for User Story 112154
		// FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setFormDisplayFlag(MainetConstants.FlagN);
		PortalService service = PortalServiceMaster.getService(
				PortalServiceMaster.getServiceId(MainetConstants.AdvertisingAndHoarding.AGENCY_CAN_SHORT_CODE, orgId),
				orgId);
		this.getModel().setService(service);
		List<AdvertiserMasterDto> masterDtoList = agencyRegistrationService
				.getLicNoAndAgenNameAndStatusByorgId(UserSession.getCurrent().getOrganisation().getOrgid());
		this.getModel().getAgencyRequestDto().getMasterDto().setApplicationTypeFlag(MainetConstants.FlagN);
		// this.getModel().setChecklistCheck(MainetConstants.FlagY);

		this.getModel()
				.setMasterDtoList(masterDtoList.stream().filter(master -> StringUtils
						.equals(master.getApplicantMobileNo(), UserSession.getCurrent().getEmployee().getEmpmobno())
						&& !StringUtils.equals(master.getAgencyStatus(), MainetConstants.AdvertisingAndHoarding.FLAGC)
						&& !StringUtils.equals(master.getAgencyStatus(), MainetConstants.AdvertisingAndHoarding.FLAGT)
						&& !StringUtils.equals(master.getAgencyStatus(), MainetConstants.Common_Constant.INACTIVE_FLAG)
						&& StringUtils.isNotBlank(master.getAgencyLicNo())).collect(Collectors.toList()));
		LinkedHashMap<String, Object> checkListChargeFlagAndLicMaxDay = agencyRegistrationService
				.getCheckListChargeFlagAndLicMaxDay(UserSession.getCurrent().getOrganisation().getOrgid(),
						MainetConstants.AdvertisingAndHoarding.AGENCY_CAN_SHORT_CODE);
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
		if (StringUtils.equals(this.getModel().getApplicationchargeApplFlag(), MainetConstants.FlagY))
			this.getModel().getAgencyRequestDto().setFree(false);
		if (StringUtils.equals(this.getModel().getApplicationchargeApplFlag(), MainetConstants.FlagN))
			this.getModel().getAgencyRequestDto().setFree(true);
		// return new ModelAndView("AdvertisercancellationForm",
		// MainetConstants.FORM_NAME, getModel());
		return index();
	}

	// @ResponseBody
	@RequestMapping(params = "searchAdvertiserNameByLicNo", method = { RequestMethod.POST })
	public ModelAndView searchAdvertiserNameByLicNo(@RequestParam(value = "agencyLicNo") String agencyLicNo,
			HttpServletRequest request) throws Exception {
		this.getModel().bind(request);
		AdvertiserCancellationFormModel model = getModel();
		FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
		ModelAndView mv = new ModelAndView(MainetConstants.AdvertisingAndHoarding.AGENCY_REG_Cancel_VALIDN,
				MainetConstants.FORM_NAME, this.getModel());
		// Long orgId =6L;
		AgencyRegistrationResponseDto agencyRegDto = agencyRegistrationService
				.getAgencyDetailByLicnoAndOrgId(agencyLicNo, UserSession.getCurrent().getOrganisation().getOrgid());
		if (agencyRegDto.getMasterDto().getAgencyLicNo() != null ) {
			if (StringUtils.equals(agencyRegDto.getMasterDto().getAgencyStatus(), MainetConstants.FlagA)) {
				// agencyName = agencyRegDto.getMasterDto().getAgencyName();
				this.getModel().getAgencyRequestDto().setMasterDto(agencyRegDto.getMasterDto());
				this.getModel().setApplicantDetailDto(agencyRegDto.getApplicantDetailDTO());
				// this.getModel().setAdvertiserDto(agencyRegDto.getMasterDto());
			} else {
				// agencyName = MainetConstants.FlagN;
				//model.addValidationError(getApplicationSession().getMessage("advertiser.cancellation.validate.licNo"));
				model.addValidationError(getApplicationSession().getMessage("adh.validate.search"));
			}
			// this.getModel().getAgencyRequestDto().setMasterDto(agencyRegDto.getMasterDto());
			// this.getModel().setApplicantDetailDto(agencyRegDto.getApplicantDetailDTO());
			this.getModel().setFormDisplayFlag(MainetConstants.FlagY);

		} else {
			//model.addValidationError(getApplicationSession().getMessage("adh.validate.search"));
			model.addValidationError(getApplicationSession().getMessage("advertiser.cancellation.validate.licNo"));

		}
		// return agencyRegDto;
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
	}

	@ResponseBody
	@RequestMapping(params = "saveAdvertiserCancellation", method = RequestMethod.POST)
	public Map<String, Object> saveAdvertiserCancellation(HttpServletRequest request) {
		getModel().bind(request);

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

	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.AdvertisingAndHoarding.GET_CHECKLIST_CHARGES)
	public ModelAndView getChecklistCharges(final HttpServletRequest request) {
		this.getModel().bind(request);
		AdvertiserCancellationFormModel model = getModel();
		AdvertiserMasterDto masDto = this.getModel().getAdvertiserDto();
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().getAdvertiserDto().setOrgId(orgid);
		model.validateBean(model.getAgencyRequestDto().getMasterDto(), AgencyRegistrationFormValidator.class);
		ModelAndView mv = new ModelAndView(MainetConstants.AdvertisingAndHoarding.AGENCY_REG_Cancel_VALIDN,
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
						model.addValidationError(
								getApplicationSession().getMessage("adh.validate.application.charges.notfound"));
					}
				}
			} else {

				model.addValidationError(
						getApplicationSession().getMessage("adh.problem.occured.initializing.checklist"));
			}

		}
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;

	}

	private void populateChecklistModel(CheckListModel checklistModel) {

		checklistModel.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		checklistModel.setServiceCode(MainetConstants.AdvertisingAndHoarding.AGENCY_CAN_SHORT_CODE);
		checklistModel.setDeptCode(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE);
	}

	private Double callBrmsForApplicationCharges(AdvertiserCancellationFormModel model, ADHRateMaster ADHRateMaster) {
		Double amoutToPay = 0.0;
		final WSRequestDTO adhRateRequestDto = new WSRequestDTO();
		ADHRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		ADHRateMaster.setServiceCode(MainetConstants.AdvertisingAndHoarding.AGENCY_CAN_SHORT_CODE);
		ADHRateMaster.setChargeApplicableAt(
				Long.toString(CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.Prefix.APL,
						PrefixConstants.Common.CAA, UserSession.getCurrent().getOrganisation()).getLookUpId()));
		adhRateRequestDto.setDataModel(ADHRateMaster);

		ADHRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		ADHRateMaster.setServiceCode(MainetConstants.AdvertisingAndHoarding.AGENCY_CAN_SHORT_CODE);

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

	private double chargesToPay(final List<ChargeDetailDTO> charges) {
		double amountSum = 0.0;
		for (final ChargeDetailDTO charge : charges) {
			amountSum = amountSum + charge.getChargeAmount();
		}
		return amountSum;
	}

	private ADHRateMaster populateChargeModel(final AdvertiserCancellationFormModel model,
			final ADHRateMaster chargeModel) {
		chargeModel.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		chargeModel.setServiceCode(MainetConstants.AdvertisingAndHoarding.AGENCY_CAN_SHORT_CODE);
		chargeModel.setRateStartDate(new Date().getTime());
		chargeModel.setDeptCode(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE);
		return chargeModel;
	}

	@RequestMapping(params = "showDetails", method = RequestMethod.POST)
	public ModelAndView showDetails(@RequestParam("appId") final long appId,
			final HttpServletRequest httpServletRequest) throws Exception {
		getModel().bind(httpServletRequest);
		try {

			ADHRequestDto adhRequestDto = new ADHRequestDto();
			adhRequestDto.setApplicationId(appId);
			adhRequestDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			adhRequestDto.setServiceCode(MainetConstants.AdvertisingAndHoarding.AGENCY_CAN_SHORT_CODE);
			ADHResponseDTO adhResponseDTO = advertisementApplicationService.getADHDataByApplicationId(adhRequestDto);
			// getModel().setAgencyRequestDto(adhResponseDTO.getAgencyRegistrationRequestDto());
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
		return new ModelAndView(MainetConstants.AdvertisingAndHoarding.AGENCY_REG_Cancel_VALIDN,
				MainetConstants.FORM_NAME, getModel());
	}
}
