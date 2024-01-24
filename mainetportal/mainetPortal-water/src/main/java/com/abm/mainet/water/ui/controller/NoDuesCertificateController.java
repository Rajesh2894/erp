package com.abm.mainet.water.ui.controller;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ApplicationPortalMaster;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.dto.ChargeDetailDTO;
import com.abm.mainet.common.dto.CheckListModel;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.dto.FinYearDTORespDTO;
import com.abm.mainet.common.dto.RequestDTO;
import com.abm.mainet.common.service.ICommonBRMSService;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.view.JsonViewObject;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.common.util.UtilityService;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.dms.utility.FileUploadUtility;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.abm.mainet.water.dto.NoDueCerticateDTO;
import com.abm.mainet.water.dto.NoDuesCertificateReqDTO;
import com.abm.mainet.water.dto.NoDuesCertificateRespDTO;
import com.abm.mainet.water.dto.WaterRateMaster;
import com.abm.mainet.water.service.IChangeOfOwnershipService;
import com.abm.mainet.water.service.INoDuesCertificateService;
import com.abm.mainet.water.service.IWaterBRMSService;
import com.abm.mainet.water.ui.model.NoDuesCertificateModel;

@Controller
@RequestMapping("NoDuesCertificateController.html")
public class NoDuesCertificateController extends AbstractFormController<NoDuesCertificateModel> {

	private static final Logger LOGGER = LoggerFactory.getLogger(NoDuesCertificateController.class);

	@Autowired
	private IPortalServiceMasterService iPortalService;

	@Resource
	private INoDuesCertificateService noDuesCertificateService;

	@Resource
	private IChangeOfOwnershipService changeOfOwnerShipService;

	@Autowired
	private IWaterBRMSService checklistAndChargeService;

	@Autowired
	private ICommonBRMSService iCommonBRMSService;

	@Autowired
	ISMSAndEmailService iSMSAndEmailService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {

		sessionCleanup(httpServletRequest);
		FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
		getModel().bind(httpServletRequest);
		ModelAndView mv = null;
		try {
			final NoDuesCertificateModel model = getModel();

			final Employee emp = UserSession.getCurrent().getEmployee();
			final NoDuesCertificateReqDTO noDuesDto = model.getReqDTO();
			if (emp != null) {
				model.getApplicantDetailDto().setApplicantFirstName(emp.getEmpname());
				model.getApplicantDetailDto().setApplicantMiddleName(emp.getEmpMName());
				model.getApplicantDetailDto().setApplicantLastName(emp.getEmpLName());
				model.getApplicantDetailDto().setMobileNo((emp.getEmpmobno()));
				model.getApplicantDetailDto().setEmailId(emp.getEmpemail());
				model.getApplicantDetailDto().setApplicantTitle(emp.getTitle());
				if (emp.getPincode() != null) {
					model.getApplicantDetailDto().setPinCode(emp.getPincode());
				}
			}
			noDuesDto.setApplicantDTO(model.getApplicantDetailDto());
			noDuesCertificateService.setCommonField(UserSession.getCurrent().getOrganisation(), model);
			final RequestDTO dto = new RequestDTO();
			dto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			final FinYearDTORespDTO finYearDTORespDTO = noDuesCertificateService.getFinancialYear(dto);
			if (finYearDTORespDTO != null) {
				model.setFinYear(finYearDTORespDTO.getFinList());
			}
			mv = new ModelAndView("NoDuesCertificateForm", MainetConstants.FORM_NAME, getModel());
			mv.addObject(MainetConstants.REQUIRED_PG_PARAM.COMMAND, getModel());
		} catch (final Exception e) {
			LOGGER.error("Exception occur in index()", e);
			mv = new ModelAndView("defaultExceptionFormView");
		}
		return mv;

	}

	@RequestMapping(params = "getConnectionDetail", method = RequestMethod.POST)
	@ResponseBody
	public NoDuesCertificateReqDTO getConnectionDetail(final HttpServletRequest request) {

		LOGGER.info("Start the getConnectionDetail()");

		getModel().bind(request);
		final NoDuesCertificateModel model = getModel();
		model.getReqDTO().setConsumerName(null);
		model.getReqDTO().setConsumerAddress(null);
		model.getReqDTO().setWaterDues(null);
		model.getReqDTO().setDuesAmount(0d);
		model.getReqDTO().setError(false);
		/*
		 * requestDTO.setUserId(usersession.getEmployee().getEmpId());
		 * requestDTO.setEmpId(usersession.getEmployee().getEmpId());
		 */
		final String connectionNo = model.getReqDTO().getConsumerNo();

		try {

			if ((connectionNo != null) && !connectionNo.isEmpty() && (UserSession.getCurrent() != null)) {
				noDuesCertificateService.getConnectionDetail(UserSession.getCurrent(), connectionNo, model.getReqDTO(),
						model);
			}
		} catch (final Exception exception) {
			LOGGER.error("Exception occur in getConnectionDetail()", exception);

		}
		return model.getReqDTO();
	}

	@RequestMapping(params = "getCharges", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView getCheckListAndCharges(final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse) {

		getModel().bind(httpServletRequest);
		
		ModelAndView mv = new ModelAndView("NoDuesCertificateFormValid", MainetConstants.FORM_NAME, getModel());
		final NoDuesCertificateModel model = getModel();
		model.setCheckListFlag("N");
		if (model.getOtp().equals(model.getUserOtp())) {
			// [START] BRMS call initialize model
			final WSRequestDTO dto = new WSRequestDTO();
			dto.setModelName("ChecklistModel|WaterRateMaster");
			final WSResponseDTO response = iCommonBRMSService.initializeModel(dto);

			if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
				final List<Object> checklistModel = JersyCall.castResponse(response, CheckListModel.class, 0);
				final List<Object> waterRateMasterList = JersyCall.castResponse(response, WaterRateMaster.class, 1);
				final CheckListModel checkListModel2 = (CheckListModel) checklistModel.get(0);
				final WaterRateMaster WaterRateMaster = (WaterRateMaster) waterRateMasterList.get(0);
				noDuesCertificateService.populateCheckListModel(model, checkListModel2);

				String checkListApplFlag = model.getCheckListApplFlag();
				if (checkListApplFlag.equals("A")) {
				final List<DocumentDetailsVO> checkListList = iCommonBRMSService.getChecklist(checkListModel2);

				
				if ((checkListList != null) && !checkListList.isEmpty()) {
					long cnt = 1;
					for (final DocumentDetailsVO doc : checkListList) {
						doc.setDocumentSerialNo(cnt);
						cnt++;
					}
					model.setCheckList(checkListList);
				} else {
					ModelAndView modelAndView;
					modelAndView = new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW,
							MainetConstants.FORM_NAME, getModel());

					return modelAndView;
				}
				}
				final WSResponseDTO res = checklistAndChargeService.getApplicableTaxes(WaterRateMaster,
						UserSession.getCurrent().getOrganisation().getOrgid(),
						MainetConstants.NewWaterServiceConstants.WND);
				if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(res.getWsStatus())) {
					if (!res.isFree()) {
						final List<?> rates = JersyCall.castResponse(res, WaterRateMaster.class);
						final List<WaterRateMaster> requiredCHarges = new ArrayList<>();
						for (final Object rate : rates) {
							WaterRateMaster master1 = (WaterRateMaster) rate;
							master1 = noDuesCertificateService.populateChargeModel(model, master1);
							requiredCHarges.add(master1);
						}

						final List<ChargeDetailDTO> detailDTOs = checklistAndChargeService
								.getApplicableCharges(requiredCHarges);
						model.setFree(MainetConstants.NewWaterServiceConstants.NO);
						model.setChargesInfo(detailDTOs);
						model.setCharges((chargesToPay(detailDTOs)));
						setChargeMap(model, detailDTOs);
						model.getOfflineDTO().setAmountToShow(model.getCharges());
						model.getReqDTO().setCharges(model.getCharges());
						//model.getReqDTO().setFree(false);
					} else {
						model.getReqDTO().setFree(true);
						model.setDocumentSubmitted(true);
						model.setFree(MainetConstants.PAYMENT.FREE);
						model.getReqDTO().setCharges(0.0d);
					}
				} else {
					ModelAndView modelAndView;
					modelAndView = new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW,
							MainetConstants.FORM_NAME, getModel());

					return modelAndView;
				}

			} else {
				ModelAndView modelAndView;
				modelAndView = new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW, MainetConstants.FORM_NAME,
						getModel());

				return modelAndView;
			}

		} else {
			model.setCheckListFlag("Y");
			model.addValidationError(getApplicationSession().getMessage("water.validation.otp"));
		}
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
	}

	private double chargesToPay(final List<ChargeDetailDTO> charges) {

		double amountSum = 0.0;
		if (charges != null) {
			for (final ChargeDetailDTO charge : charges) {
				amountSum = amountSum + charge.getChargeAmount();
			}
		}
		return amountSum;
	}

	private void setChargeMap(final NoDuesCertificateModel model, final List<ChargeDetailDTO> charges) {

		final Map<Long, Double> chargesMap = new HashMap<>();
		if (charges != null) {
			for (final ChargeDetailDTO dto : charges) {

				chargesMap.put(dto.getChargeCode(), dto.getChargeAmount());
			}
		}
		model.setChargesMap(chargesMap);
	}

	@RequestMapping(params = "save", method = RequestMethod.POST)
	public ModelAndView saveWaterForm(final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse) {

		getModel().bind(httpServletRequest);
		final NoDuesCertificateModel model = getModel();
		ModelAndView mv = null;
		List<DocumentDetailsVO> docs = model.getCheckList();
		docs = model.getFileUploadList(docs, FileUploadUtility.getCurrent().getFileMap());
		try {
			if (model.validateInputs()) {
				final NoDuesCertificateReqDTO reqDTO = model.getReqDTO();
				final UserSession session = UserSession.getCurrent();
				reqDTO.setUserId(session.getEmployee().getEmpId());
				reqDTO.setLangId((long) session.getLanguageId());
				reqDTO.setUpdatedBy(session.getEmployee().getEmpId());
				reqDTO.setOrgId(session.getOrganisation().getOrgid());
				reqDTO.setLgIpMac(session.getEmployee().getEmppiservername());
				reqDTO.setDocumentList(docs);
				reqDTO.setApplicantDTO(model.getApplicantDetailDto());
				reqDTO.setServiceId(model.getServiceId());
				reqDTO.setFree(true);
				if ((model.getFree() != null) && !model.getFree().equals(MainetConstants.PAYMENT.FREE)) {
					reqDTO.setPayMode(MainetConstants.PAYMENT.FREE);
				} else {
					reqDTO.setPayMode(model.getOfflineDTO().getOnlineOfflineCheck());
				}
				reqDTO.setCharges(model.getCharges());
				final NoDuesCertificateRespDTO response = noDuesCertificateService.saveForm(reqDTO);

				if (response != null) {
					if ((response.getStatus() != null)
							&& response.getStatus().equals(MainetConstants.WebServiceStatus.SUCCESS)) {
						model.getResponseDTO().setApplicationNo(response.getApplicationNo());
						final ApplicationPortalMaster applicationMaster = saveApplcationMaster(reqDTO.getServiceId(),
								response.getApplicationNo(),
								FileUploadUtility.getCurrent().getFileMap().entrySet().size());
						iPortalService.saveApplicationMaster(applicationMaster, model.getCharges(),
								FileUploadUtility.getCurrent().getFileMap().entrySet().size());
						if ((model.getFree() != null) && !model.getFree().equals(MainetConstants.PAYMENT.FREE)) {

							final CommonChallanDTO offline = model.getOfflineDTO();
							model.setPaymentDeatil(offline);

							if (FileUploadUtility.getCurrent().getFileMap().entrySet().size() != 0) {
								offline.setDocumentUploaded(true);
							} else {
								offline.setDocumentUploaded(false);
							}

							if ((offline.getOnlineOfflineCheck() != null)
									&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT.OFFLINE)) {
								return jsonResult(JsonViewObject
										.successResult(getApplicationSession().getMessage("water.noDues.paymentSuccess1")
												+ response.getApplicationNo()
												+ getApplicationSession().getMessage("water.noDues.paymentSuccess3")));	
							} else {
								return jsonResult(JsonViewObject
										.successResult(getApplicationSession().getMessage("water.noDues.paymentSuccess1")
												+ response.getApplicationNo()
												+ getApplicationSession().getMessage("water.noDues.paymentSuccess2")));	
							}

						} else {
							final String msg = MessageFormat.format(
									getApplicationSession().getMessage("water.noduescerti.success"),
									response.getApplicationNo().toString());
							return jsonResult(JsonViewObject.successResult(msg));
						}
					}

					else {
						if (!response.getErrorList().isEmpty()) {
							for (final String msg : response.getErrorList()) {
								model.addValidationError(msg);
							}
						} else {
							mv = new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);
						}
					}
				}
				mv = new ModelAndView("NoDuesCertificateFormValid", MainetConstants.FORM_NAME, getModel());
				mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
			}
		} catch (final Exception exception) {
			logger.error("Exception found in save method: ", exception);
			mv = new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);
		}
		return mv;
	}

	private ApplicationPortalMaster saveApplcationMaster(final Long serviceId, final Long applicationNo,
			final int documentListSize) throws Exception {

		final PortalService appealMaster = iPortalService.getService(serviceId,
				UserSession.getCurrent().getOrganisation().getOrgid());

		final ApplicationPortalMaster applicationMaster = new ApplicationPortalMaster();
		calculateDate(appealMaster, applicationMaster, documentListSize);
		applicationMaster.setPamApplicationId(applicationNo);
		applicationMaster.setSmServiceId(serviceId);
		applicationMaster.setPamApplicationDate(new Date());
		applicationMaster.updateAuditFields();
		return applicationMaster;
	}

	@RequestMapping(params = "generateOtp", method = RequestMethod.POST)
	@ResponseBody
	public NoDuesCertificateReqDTO generateOtp(final HttpServletRequest request) {

		getModel().bind(request);
		final NoDuesCertificateModel model = getModel();
		NoDuesCertificateReqDTO reqDTO = getModel().getReqDTO();
		final String connectionNo = model.getReqDTO().getConsumerNo();
		/*
		 * try { if ((connectionNo != null) && !connectionNo.isEmpty() &&
		 * (UserSession.getCurrent() != null)) { reqDTO =
		 * noDuesCertificateService.getConnectionDetail(UserSession.getCurrent(),
		 * connectionNo, model.getReqDTO(), model); } } catch (final Exception
		 * exception) { LOGGER.error("Exception occur in getConnectionDetail()",
		 * exception);
		 * 
		 * }
		 */
		final String otp = UtilityService.generateRandomNumericCode(MainetConstants.OTP_PRASSWORD_LENGTH);
		model.setOtp(otp);
		LOGGER.info("OTP for water no dues certificate is : "+otp);
		SMSAndEmailDTO emailDto = new SMSAndEmailDTO();
		if (reqDTO.getMobileNo() != null && !reqDTO.getMobileNo().isEmpty()) {
			emailDto.setMobnumber(reqDTO.getMobileNo());
		}
		if (reqDTO.getEmail() != null && !reqDTO.getEmail().isEmpty()) {
			emailDto.setEmail(reqDTO.getEmail());
		}
		emailDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		emailDto.setCurrentDate(Utility.dateToString(new Date()));
		emailDto.setAppNo(otp);

		iSMSAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.ONLINE_SERVICE, "CitizenRegistration.html",
				MainetConstants.SMS_EMAIL.OTP_MSG, emailDto, UserSession.getCurrent().getOrganisation(),
				UserSession.getCurrent().getLanguageId());
		model.setReqDTO(reqDTO);
		return model.getReqDTO();
	}

	/*@RequestMapping(method = RequestMethod.POST, params = "noDueCertificatePrint")
	public ModelAndView noDueCertificatePrint(final HttpServletRequest httpServletRequest) {
		LOGGER.info("Start the noDueCertificatePrint()");
		try {
			getModel().bind(httpServletRequest);
			final NoDuesCertificateModel model = getModel();
			NoDuesCertificateRespDTO response = getModel().getResponseDTO();
			NoDueCerticateDTO dto = null;
			NoDuesCertificateReqDTO requestDTO = getModel().getReqDTO();
			requestDTO.setApplicationId(response.getApplicationNo());
			requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			if (response.getStatus().equals(MainetConstants.SUCCESS)) {
				if (requestDTO != null) {
					dto = noDuesCertificateService.getNoDuesApplicationData(requestDTO);
				}

			}

			if (dto != null) {
				dto.setApproveBy(UserSession.getCurrent().getEmployee().getEmploginname());
				dto.setOrgName(UserSession.getCurrent().getOrganisation().getONlsOrgname());

				model.setNodueCertiDTO(dto);

				new ModelAndView("NoDuesCertiFormPrint", MainetConstants.FORM_NAME, getModel());

			}

		} catch (final Exception exception) {
			logger.error("Exception found in noDueCertificatePrint  method : ", exception);
			new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);
		}
		return new ModelAndView("NoDuesCertiFormPrint", MainetConstants.FORM_NAME, getModel());
	}*/
}
