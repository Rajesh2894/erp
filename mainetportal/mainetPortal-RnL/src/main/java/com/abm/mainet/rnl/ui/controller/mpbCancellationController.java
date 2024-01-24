/**
 * 
 */

package com.abm.mainet.rnl.ui.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.ChargeDetailDTO;
import com.abm.mainet.common.dto.CheckListModel;
import com.abm.mainet.common.service.ICommonBRMSService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.view.JsonViewObject;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;
import com.abm.mainet.rnl.datamodel.RNLRateMaster;
import com.abm.mainet.rnl.dto.BookingReqDTO;
import com.abm.mainet.rnl.dto.EstateBookingDTO;
import com.abm.mainet.rnl.dto.EstatePropReqestDTO;
import com.abm.mainet.rnl.dto.EstatePropResponseDTO;
import com.abm.mainet.rnl.dto.PropInfoDTO;
import com.abm.mainet.rnl.service.IRNLChecklistAndChargeService;
import com.abm.mainet.rnl.service.MPBCancellationService;
import com.abm.mainet.rnl.ui.model.mpbCancelModel;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author priti.singh
 *
 */
@Controller

@RequestMapping("mpbCancellation.html")
public class mpbCancellationController extends AbstractFormController<mpbCancelModel> {

	private static final Logger LOGGER = Logger.getLogger(mpbCancellationController.class);

	@Autowired
	private MPBCancellationService mPBCancellationService;

	@Autowired
	private ICommonBRMSService commonBRMSService;

	@Autowired
	private IRNLChecklistAndChargeService iRNLChecklistAndChargeService;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final Model uiModel, final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		mpbCancelModel model = getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long userId = UserSession.getCurrent().getEmployee().getEmpId();
		List<EstateBookingDTO> masterDtosList = mPBCancellationService.fetchAllBookingsByOrg(userId, orgId);
		model.setEstateBookings(masterDtosList);
		return new ModelAndView("mpbcancellation", MainetConstants.FORM_NAME, model);
	}

	/* to get details on enter of booking no and orgId */

	@RequestMapping(method = RequestMethod.POST, params = "getBookedPropertyDetails")
	public ModelAndView getBookedPropertyDetails(final HttpServletRequest request) {

		this.getModel().bind(request);
		mpbCancelModel model = getModel();

		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();


		List<PropInfoDTO> propInfoDtoList = mPBCancellationService
				.fetchAllBookedPropertyDetails(model.getEstateBookingDTO().getBookingNo(), orgId);

		model.setPropInfoDtoList(propInfoDtoList);
		ModelAndView modelAndView = null;

		// validation for property which payment is not complete (start)

		propInfoDtoList.forEach(list -> {
			model.setReceiptNo(list.getReceiptNo());
			model.setPropId(list.getPropId());

		});

		if (model.getReceiptNo() == null) {
			model.addValidationError(("Payment not done for Booking No.") + model.getEstateBookingDTO().getBookingNo());
			modelAndView = new ModelAndView("MPBCancelValidn", MainetConstants.FORM_NAME, this.getModel());
			modelAndView.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
					getModel().getBindingResult());
			return modelAndView;
		}

		/* validation for property which payment is not complete (end) */

		/* fetch cancellation charges */

		WSRequestDTO initRequestDto = new WSRequestDTO();
		final BookingReqDTO bookingReqDTO = getModel().getBookingReqDTO();
		EstatePropResponseDTO estatePropResponseDTO = bookingReqDTO.getEstatePropResponseDTO();

		initRequestDto.setModelName(MainetConstants.RNL_Common.CHECKLIST_RNL_MODEL_NAME);
		WSResponseDTO response = commonBRMSService.initializeModel(initRequestDto);
		final List<Object> checklistModel = JersyCall.castResponse(response, CheckListModel.class, 0);
		final CheckListModel checkListModel = (CheckListModel) checklistModel.get(0);

		if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {

			Organisation organisation1 = new Organisation();
			organisation1.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
			final List<Object> rNLRateMasterList = JersyCall.castResponse(response, RNLRateMaster.class, 1);
			RNLRateMaster rNLRateMaster = (RNLRateMaster) rNLRateMasterList.get(0);
			WSRequestDTO taxReqDTO = new WSRequestDTO();
			rNLRateMaster.setOrgId(orgId);
			rNLRateMaster.setServiceCode("EBC");
			LookUp chargeApplicableAt = CommonMasterUtility
					.getValueFromPrefixLookUp(MainetConstants.AdvertisingAndHoarding.APL, "CAA", organisation1);

			rNLRateMaster.setChargeApplicableAt(Long.toString(chargeApplicableAt.getLookUpId()));

			taxReqDTO.setDataModel(rNLRateMaster);
			String serviceShortcode = "EBC";

			final WSResponseDTO res = iRNLChecklistAndChargeService.getApplicableTaxes(rNLRateMaster,
					UserSession.getCurrent().getOrganisation().getOrgid(), serviceShortcode);

			/* Setting values to fetch charges */

			final EstatePropReqestDTO requestObj = new EstatePropReqestDTO();
			requestObj.setOrgId(orgId);
			requestObj.setPropId(Long.valueOf(model.getPropId()));
			final ResponseEntity<?> responseEntity = JersyCall.callAnyRestTemplateClient(requestObj,
					ServiceEndpoints.WebServiceUrl.GET_PROPERTY);
			if (responseEntity.getStatusCode() == HttpStatus.OK) {
				final String jsonStr = getJsonString(responseEntity);
				try {
					estatePropResponseDTO = new ObjectMapper().readValue(jsonStr, EstatePropResponseDTO.class);
					model.getBookingReqDTO().setEstatePropResponseDTO(estatePropResponseDTO);
				} catch (final Exception e) {
					LOGGER.error("Error while reading value from response: " + e.getMessage(), e);
					modelAndView = new ModelAndView("defaultExceptionView");
				}

			} else {
				LOGGER.error("Fetch all rented properties details failed due to :" + responseEntity.getBody());
				modelAndView = new ModelAndView("defaultExceptionView");
			}

			if (res.getWsStatus() != null
					&& MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(res.getWsStatus())) {

				if (!res.isFree()) {
					final List<?> rates = JersyCall.castResponse(res, RNLRateMaster.class);
					final List<RNLRateMaster> requiredCHarges = new ArrayList<>();
					for (final Object rate : rates) {
						RNLRateMaster master1 = (RNLRateMaster) rate;
						master1.setOrgId(orgId);
						master1.setServiceCode("EBC");
						master1.setDeptCode("RNL");
						master1.setRateStartDate(new Date().getTime());
						master1.setTaxSubCategory(getSubCategoryDesc(master1.getTaxSubCategory(),
								UserSession.getCurrent().getOrganisation()));

						master1.setUsageSubtype1(model.getBookingReqDTO().getEstatePropResponseDTO().getUsage());
						master1.setUsageSubtype2(model.getBookingReqDTO().getEstatePropResponseDTO().getType());
						master1.setFloorLevel(model.getBookingReqDTO().getEstatePropResponseDTO().getFloor());
						master1.setUsageSubtype3(model.getBookingReqDTO().getEstatePropResponseDTO().getSubType());
						master1.setFactor4(model.getBookingReqDTO().getEstatePropResponseDTO().getPropName());

						requiredCHarges.add(master1);
					}
					WSRequestDTO chargeReqDTO = new WSRequestDTO();
					chargeReqDTO.setDataModel(requiredCHarges);
					final List<ChargeDetailDTO> output = iRNLChecklistAndChargeService
							.getApplicableCharges(requiredCHarges);

					/* Setting values to fetch charges */

					if (output == null) {
						model.addValidationError(getApplicationSession().getMessage("rnl.validate.charges.not.found.brms.sheet"));
						LOGGER.error("Charges not Found in brms Sheet");
					} else {
						model.setChargesInfo(newChargesToPay(output));
						model.setAmountToPay(chargesToPay(model.getChargesInfo()));
						if (model.getAmountToPay() == 0.0d) {
							model.addValidationError(getApplicationSession()
									.getMessage("rnl.validate.service.charge.amountToPay.cannot.be") + model.getAmountToPay()
									+ getApplicationSession().getMessage("rnl.if.service.configured.as.chargeable"));
							LOGGER.error("Service charge amountToPay cannot be " + model.getAmountToPay()
									+ " if service configured as Chargeable");
						}

					}

				}
			} else {
				model.addValidationError(getApplicationSession()
						.getMessage("rnl.exception.occured.depends.on.factor.for.RNL.ratemaster"));
				LOGGER.error("Exception occured while fecthing Depends on factor for RNL Rate Mster ");
			}

		}

		/* fetch cancellation charges */
		return new ModelAndView("mpbcancellationdetails", MainetConstants.FORM_NAME, this.getModel());

	}

	private List<ChargeDetailDTO> newChargesToPay(final List<ChargeDetailDTO> charges) {
		List<ChargeDetailDTO> chargeList = new ArrayList<>(0);
		mpbCancelModel mPBCancelModel = getModel();

		for (final ChargeDetailDTO charge : charges) {
			BigDecimal amount = new BigDecimal(charge.getChargeAmount());

			charge.setChargeAmount(amount.doubleValue());
			chargeList.add(charge);
		}
		return chargeList;
	}

	private double chargesToPay(final List<ChargeDetailDTO> charges) {
		double amountSum = 0.0;

		for (final ChargeDetailDTO charge : charges) {
			amountSum = amountSum + charge.getChargeAmount();
		}
		return amountSum;
	}

	private String getSubCategoryDesc(final String taxsubCategory, final Organisation org) {
		String subCategoryDesc = "";
		final List<LookUp> subCategryLookup = CommonMasterUtility.getLevelData("TAC",
				MainetConstants.EstateBooking.LEVEL, org);
		for (final LookUp lookup : subCategryLookup) {
			if (lookup.getLookUpCode().equals(taxsubCategory)) {
				subCategoryDesc = lookup.getDescLangFirst();
				break;
			}
		}
		return subCategoryDesc;
	}

	// @ResponseBody
	@RequestMapping(method = RequestMethod.POST, params = "saveMPBCancellation")
	public ModelAndView saveMPBCancellation(final HttpServletRequest request) throws IOException {
		this.getModel().bind(request);
		mpbCancelModel model = this.getModel();
		Map<String, Object> object = new LinkedHashMap<String, Object>();
		JsonViewObject respObj;
		EstateBookingDTO estateBookingDTO = new EstateBookingDTO();
		estateBookingDTO.setBookingNo(model.getEstateBookingDTO().getBookingNo());
		estateBookingDTO.setOrgId(
				UserSession.getCurrent().getOrganisation().getOrgid());/*
																		 * estateBookingDTO.setApplicationId(model.
																		 * getEstateBookingDTO().getApplicationId());
																		 */
		if (this.getModel().saveMPBCancellation()) {
			return jsonResult(JsonViewObject.successResult(this.getModel().getSuccessMessage()));

		} else {
			return jsonResult(JsonViewObject.successResult(getApplicationSession().getMessage("continue.forpayment")));
		}

		/*
		 * if (this.getModel().saveMPBCancellation()) { object.put("bookingNo",
		 * this.getModel().getEstateBookingDTO().getBookingNo()); } else {
		 * object.put("ERROR", this.getModel().getBindingResult().getAllErrors()); }
		 */
		/*
		 * return object;
		 */

	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, params = "saveMPBCancellationForFreeCharge")
	public Map<String, Object> saveMPBCancellationForFreeCharge(final HttpServletRequest request) throws IOException {
		this.getModel().bind(request);
		mpbCancelModel model = this.getModel();
		Map<String, Object> object = new LinkedHashMap<String, Object>();
		JsonViewObject respObj;
		EstateBookingDTO estateBookingDTO = new EstateBookingDTO();
		estateBookingDTO.setBookingNo(model.getEstateBookingDTO().getBookingNo());
		estateBookingDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());

		if (this.getModel().saveMPBCancellation()) {
			object.put("bookingNo", this.getModel().getEstateBookingDTO().getBookingNo());
		} else {
			object.put("ERROR", this.getModel().getBindingResult().getAllErrors());
		}

		return object;

	}

	@RequestMapping(params = "getMPBCancellationReport", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String getMPBCancellationReport(final HttpServletRequest request) {
		mpbCancelModel mPBCancelModel = getModel();
		// String bookingNo = mPBCancelModel.getPropInfoDTO().getBookingNo();
		// D#39643
		String bookingNo = mPBCancelModel.getEstateBookingDTO().getBookingNo();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		LOGGER.info("birt pa report " + ServiceEndpoints.BRMS_RNL_URL.RNL_BIRT_REPORT_URL
				+ "=RefundNote.rptdesign&OrgId=" + orgId + "&BookingId=" + bookingNo);
		return ServiceEndpoints.BRMS_RNL_URL.RNL_BIRT_REPORT_URL + "=RefundNote.rptdesign&OrgId=" + orgId
				+ "&BookingId=" + bookingNo;

	}

	private String getJsonString(final ResponseEntity<?> responseEntity) {

		@SuppressWarnings("unchecked")
		final LinkedHashMap<Long, Object> outPutObject = (LinkedHashMap<Long, Object>) responseEntity.getBody();
		final String jsonString = new JSONObject(outPutObject).toString();
		return jsonString;

	}

}
