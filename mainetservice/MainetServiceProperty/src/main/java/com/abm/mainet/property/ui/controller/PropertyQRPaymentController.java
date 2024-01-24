package com.abm.mainet.property.ui.controller;

import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.payment.dao.PaymentDAO;
import com.abm.mainet.common.integration.payment.dto.PaymentRequestDTO;
import com.abm.mainet.common.integration.payment.entity.PaymentTransactionMas;
import com.abm.mainet.common.integration.payment.service.PaymentService;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.IReceiptEntryService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.property.dao.IAssessmentMastDao;
import com.abm.mainet.property.dto.BillDisplayDto;
import com.abm.mainet.property.dto.BillPaymentDetailDto;
import com.abm.mainet.property.dto.PropertyBillPaymentDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.service.AssesmentMastService;
import com.abm.mainet.property.service.IProvisionalAssesmentMstService;
import com.abm.mainet.property.service.PrimaryPropertyService;
import com.abm.mainet.property.service.PropertyBillPaymentService;
import com.abm.mainet.property.service.PropertyServiceImpl;
import com.abm.mainet.property.ui.model.PropertyBillPaymentModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paytm.pg.merchant.PaytmChecksum;

@Controller
@RequestMapping("/PropertyQRPayment.html")
public class PropertyQRPaymentController extends AbstractFormController<PropertyBillPaymentModel> {
	private static final Logger LOGGER = Logger.getLogger(PropertyServiceImpl.class);

	@Autowired
	private PropertyBillPaymentService propertyBillPaymentService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private AssesmentMastService assesmentMastService;

	@Autowired
	private PrimaryPropertyService primaryPropertyService;

	@Autowired
	private IReceiptEntryService receiptEntryService;

	@Autowired
	private IAssessmentMastDao assessmentMastDao;

	@Autowired
	private IFinancialYearService iFinancialYearService;

	@Resource
	private IProvisionalAssesmentMstService provisionalAssesmentMstService;

	@Autowired
	private IWorkflowRequestService workflowReqService;

	@Autowired
	private PaymentService paymentService;

	@Resource
	private PaymentDAO paymentDAO;

	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest request, Model uiModel) {
		sessionCleanup(request);
		getModel().bind(request);
		LOGGER.info("system dir " + System.getProperty("user.dir"));
		PropertyBillPaymentModel model = this.getModel();
		model.setCommonHelpDocs("PropertyQRPayment.html");
		Organisation org = UserSession.getCurrent().getOrganisation();
		if (Utility.isEnvPrefixAvailable(org, "BMT")) {
			model.getOfflineDTO().setOnlineOfflineCheck(MainetConstants.FlagP);
		}
		try {
			List<LookUp> parentPropLookupList = CommonMasterUtility.getLevelData(PrefixConstants.GPI,
					MainetConstants.NUMBER_ONE, org);
			this.getModel().setParentPropLookupList(parentPropLookupList);
		} catch (Exception e) {
			LOGGER.error("Prefix GPI is not configured in prefix master ");
		}
		String redirectURL = ApplicationSession.getInstance().getMessage("paytm.dqr.homescreen");
		uiModel.addAttribute("redirectURL", redirectURL);
		return new ModelAndView("PropertyQRPaymentSearch", MainetConstants.FORM_NAME, model);

	}

	

	@RequestMapping(params = "getBillPaymentDetail", method = RequestMethod.POST)
	public ModelAndView getBillPaymentDetail(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		PropertyBillPaymentModel model = this.getModel();
		PropertyBillPaymentDto dto = model.getPropBillPaymentDto();
		Organisation org = UserSession.getCurrent().getOrganisation();
		List<String> checkActiveFlagList = null;
		String billingMethod = null;
		LookUp billMethod = null;
		try {
			billMethod = CommonMasterUtility.getValueFromPrefixLookUp("BMT", "ENV", org);
		} catch (Exception e) {
		}
		getModel().setParentGrpFlag(MainetConstants.FlagN);
		ModelAndView mv = new ModelAndView("propertyBillPaymentSearchValidn", MainetConstants.FORM_NAME,
				this.getModel());
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		if (StringUtils.isNotBlank(dto.getAssNo())) {
			if (billMethod != null && StringUtils.isNotBlank(billMethod.getOtherField())
					&& StringUtils.equals(billMethod.getOtherField(), MainetConstants.FlagY)) {
				Long billingMethodId = primaryPropertyService.getBillMethodIdByPropNo(dto.getAssNo(), org.getOrgid());

				if (billingMethodId == null) {
					getModel().addValidationError("Invalid Property Number");
					return mv;
				}
				LookUp billingMethodLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(billingMethodId, org);

				if (billingMethodLookUp != null) {
					billingMethod = billingMethodLookUp.getLookUpCode();
					getModel().setBillingMethod(billingMethod);
					if (StringUtils.equals(billingMethodLookUp.getLookUpCode(), MainetConstants.FlagI)
							&& StringUtils.isBlank(dto.getFlatNo())) {
						getModel().addValidationError("Please enter flat no");
						return mv;
					}
					// D#145343
					if (dto.getFlatNo() != null) {
						String occpierName = primaryPropertyService.getPropertyDetailsByPropNoFlatNoAndOrgId(
								dto.getAssNo(), dto.getFlatNo(), org.getOrgid());
						if (occpierName != null) {
							model.setOccupierName(occpierName);
						}
					}
				}
			}
			checkActiveFlagList = assesmentMastService.checkActiveFlag(dto.getAssNo(),
					UserSession.getCurrent().getOrganisation().getOrgid());
		}
		if (CollectionUtils.isNotEmpty(checkActiveFlagList)) {
			String checkActiveFlag = checkActiveFlagList.get(checkActiveFlagList.size() - 1);
			if (StringUtils.equals(checkActiveFlag, MainetConstants.STATUS.INACTIVE)) {
				getModel().addValidationError("This property is Inactive");
				return mv;
			}
		}
		if ("M".equals(model.getReceiptType()) && model.getManualReeiptDate() == null) {
			getModel().addValidationError("Enter valid manual receipt date.");
			return mv;
		}

		if ("M".equals(model.getReceiptType()) && model.getManualReeiptDate() != null) {
			if (Utility.compareDate(new Date(), model.getManualReeiptDate())) {
				getModel().addValidationError("Manual receipt date cannot be greater than current date");
				return mv;
			}
		}

		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_ASCL)
				&& Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "BPA")) {
			List<ProvisionalAssesmentMstDto> provAssesMastList = new ArrayList<>();
			if (!StringUtils.isEmpty(dto.getAssNo())) {
				provAssesMastList = provisionalAssesmentMstService.getPropDetailByPropNoOnly(dto.getAssNo());
			} else {
				String propNo = provisionalAssesmentMstService.getPropNoByOldPropNo(dto.getAssOldpropno(),
						UserSession.getCurrent().getOrganisation().getOrgid());
				if (StringUtils.isNotBlank(propNo)) {
					provAssesMastList = provisionalAssesmentMstService.getPropDetailByPropNoOnly(propNo);
				}
			}
			if (CollectionUtils.isNotEmpty(provAssesMastList)) {
				ProvisionalAssesmentMstDto provAssesMstDto = provAssesMastList.get(provAssesMastList.size() - 1);
				if (provAssesMstDto != null && provAssesMstDto.getApmApplicationId() != null) {
					WorkflowRequest workflowRequest = workflowReqService.getWorkflowRequestByAppIdOrRefId(
							provAssesMstDto.getApmApplicationId(), null,
							UserSession.getCurrent().getOrganisation().getOrgid());
					if (workflowRequest != null
							&& MainetConstants.WorkFlow.Status.PENDING.equalsIgnoreCase(workflowRequest.getStatus())) {
						if (StringUtils.isNotBlank(dto.getAssNo())) {
							getModel().addValidationError("Change In Assessment against property number "
									+ dto.getAssNo() + " is already in progress ");
						} else {
							getModel().addValidationError("Change In Assessment against property number "
									+ dto.getAssOldpropno() + " is already in progress ");
						}
						return mv;
					}
				}
			}

		}

		BillPaymentDetailDto billPayDto = null;
		if (StringUtils.isNotBlank(dto.getParentPropNo())) {
			getModel().setParentGrpFlag(MainetConstants.FlagY);
			this.getModel().getPropBillPaymentDto().setSpecNotSearchType("GP");
			List<String> propertyNoList = assessmentMastDao.fetchAssessmentByGroupPropNo(org.getOrgid(),
					dto.getParentPropNo(), null, MainetConstants.FlagA);
			if (CollectionUtils.isNotEmpty(propertyNoList)) {
				// To check whether at least one bill is present for all properties present in
				// parent property
				List<Object[]> propNoWithoutBills = assessmentMastDao.fetchPropNoWhoseBillNotPresent(org.getOrgid(),
						null);
				if (CollectionUtils.isNotEmpty(propNoWithoutBills)) {
					List<String> propNos = new ArrayList<>();
					List<String> parentPropNos = new ArrayList<>(propertyNoList);
					propNoWithoutBills.forEach(prop -> {
						propNos.add(prop[0].toString());
					});
					parentPropNos.retainAll(propNos);
					if (CollectionUtils.isNotEmpty(parentPropNos)) {
						getModel().addValidationError(
								ApplicationSession.getInstance().getMessage("property.groupPropertyValidn"));
						return mv;
					}
				}
				// To check out parent properties whose bill is not generated in current year
				Long finId = iFinancialYearService.getFinanceYearId(new Date());
				List<Object[]> result = assessmentMastDao.getAllPropBillGeneByPropNoList(finId, org.getOrgid(),
						propertyNoList);
				if (CollectionUtils.isNotEmpty(result)) {
					getModel().addValidationError(
							ApplicationSession.getInstance().getMessage("property.groupPropertyValidn"));
					return mv;
				} else if (CollectionUtils.isNotEmpty(propertyNoList)) {
					billPayDto = propertyBillPaymentService.getBillPaymentDetailForGrp(dto, propertyNoList,
							model.getManualReeiptDate(), UserSession.getCurrent().getEmployee().getEmpId(),
							UserSession.getCurrent().getOrganisation());

				}
			} else {
				getModel().addValidationError(ApplicationSession.getInstance().getMessage("property.vaidParentPropNo"));
				return mv;
			}
		} else {
			List<ProvisionalAssesmentMstDto> assList = assesmentMastService.getPropDetailFromMainAssByPropNoOrOldPropNo(
					UserSession.getCurrent().getOrganisation().getOrgid(), dto.getAssNo(), dto.getAssOldpropno());
			if (CollectionUtils.isNotEmpty(assList)) {
				ProvisionalAssesmentMstDto assesMast = assList.get(assList.size() - 1);
				if (assesMast != null && StringUtils.equals(assesMast.getIsGroup(), MainetConstants.FlagY)) {
					getModel().addValidationError(
							ApplicationSession.getInstance().getMessage("property.propBillPayGrpValidn"));
					return mv;
				}
			}
			billPayDto = propertyBillPaymentService.getBillPaymentDetail(dto.getAssOldpropno(), dto.getAssNo(),
					UserSession.getCurrent().getOrganisation().getOrgid(),
					UserSession.getCurrent().getEmployee().getEmpId(), model.getManualReeiptDate(), billingMethod,
					dto.getFlatNo());
		}
		// Defect#114092 - After submitting the form sequence of Tax Description is
		// getting changed on generated Receipt
		if (billPayDto != null) {
			if (billPayDto.getBillDisList() != null && !billPayDto.getBillDisList().isEmpty()) {
				billPayDto.getBillDisList().sort(Comparator.comparing(BillDisplayDto::getDisplaySeq));
			}
			if (StringUtils.equals(MainetConstants.FlagI, billingMethod)) {
				billPayDto.getAssmtDto().getProvisionalAssesmentDetailDtoList().forEach(detail -> {
					if ("Illegal".equalsIgnoreCase(detail.getLegal())
							|| MainetConstants.FlagN.equals(detail.getLegal())) {
						model.setIllegal(MainetConstants.FlagY);
					}
				});
			}
			model.setBillPayDto(billPayDto);
			model.setOwnerDtlDto(billPayDto.getOwnerDtlDto());
			model.setHalfPaymentRebate(billPayDto.getHalfPaymentRebate());
			BigDecimal receivedAmount = receiptEntryService.getReceiptAmountPaidByPropNoOrFlatNo(dto.getAssNo(),
					dto.getFlatNo(), UserSession.getCurrent().getOrganisation(), billPayDto.getDeptId());
			if (receivedAmount != null) {
				model.setReceivedAmount(receivedAmount.doubleValue());
			} else {
				model.setReceivedAmount(0.0);
			}
			if (billPayDto.getTotalRebate().doubleValue() > 0) {
				model.setRebateApplFlag(MainetConstants.FlagY);
			} else {
				model.setRebateApplFlag(MainetConstants.FlagN);
			}
			if (billPayDto.getErrorMsg() != null) {
				getModel().addValidationError(billPayDto.getErrorMsg());
				return mv;
			}
		}

		if (billPayDto == null) {
			if (model.getManualReeiptDate() != null) {
				getModel().addValidationError(
						"Enter valid Property No or Old Property No/ Manual receipt date cannot be less than bill date and last manual receipt date");
			} else {
				getModel().addValidationError("Enter valid Property No or Old Property No");
			}
			return mv;
		}
		if (billPayDto.getRedirectCheck() != null && billPayDto.getRedirectCheck().equals(MainetConstants.Y_FLAG)) {
			return mv;
		}

		BigDecimal amount = new BigDecimal(MainetConstants.Property.BIG_DEC_ZERO);
		if (billPayDto != null && CollectionUtils.isNotEmpty(billPayDto.getBillDisList())) {
			for (BillDisplayDto bdDto : billPayDto.getBillDisList()) {
				if (bdDto.getTaxCategoryId() != null) {
					final String taxCode = CommonMasterUtility
							.getHierarchicalLookUp(bdDto.getTaxCategoryId(), billPayDto.getOrgId()).getLookUpCode();
					if (StringUtils.isNotBlank(taxCode) && taxCode.equals(PrefixConstants.TAX_CATEGORY.REBATE)) {
						amount = amount.add(bdDto.getCurrentYearTaxAmt());
					}
				}
			}
		}
		model.setTotalRebate(Double.valueOf(Math.round(Double.valueOf(amount.toString()))));
		model.setDeptCode(departmentService.getDeptCode(billPayDto.getDeptId()));
		if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL)) {
			model.setSkdclEnv(MainetConstants.FlagY);
		}
		return new ModelAndView("PropertyQRPayment", MainetConstants.FORM_NAME, model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(params = "showQRCode", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> showQRCode(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, @RequestParam("amount") final String amount) {
		PropertyBillPaymentModel model = this.getModel();
		Map<String, String> response = new HashMap<>();
		String redirectURL = null;
		try {
			PaymentRequestDTO paymentRequestDTO = convertToDTO(model, httpServletRequest, Long.valueOf(amount));
			paymentService.proceesTransactionOnApplication(httpServletRequest, paymentRequestDTO);
			JSONObject paytmParams = new JSONObject();
			String orderId = String.format("%08d", paymentRequestDTO.getTxnid()).toString();
			JSONObject body = new JSONObject();
			DecimalFormat decimalFormat = new DecimalFormat("0.00");
			String formattedAmount = decimalFormat.format(Double.valueOf(amount));
			body.put("mid", ApplicationSession.getInstance().getMessage("paytm.dqr.merchantId"));
			body.put("orderId", orderId);
			body.put("amount", formattedAmount);
			body.put("businessType", ApplicationSession.getInstance().getMessage("paytm.dqr.businessType"));
			body.put("posId", ApplicationSession.getInstance().getMessage("paytm.dqr.posId"));
			String checksum = PaytmChecksum.generateSignature(body.toString(),
					ApplicationSession.getInstance().getMessage("paytm.dqr.merchantKey"));
			JSONObject head = new JSONObject();
			head.put("clientId", ApplicationSession.getInstance().getMessage("paytm.dqr.clientId"));
			head.put("version", ApplicationSession.getInstance().getMessage("paytm.dqr.version"));
			head.put("signature", checksum);
			paytmParams.put("body", body);
			paytmParams.put("head", head);
			String post_data = paytmParams.toString();
			LOGGER.info("post_data: " + post_data);
			StringEntity entity = new StringEntity(post_data);
			CloseableHttpClient httpclient = getCloseableHttpClient();
			HttpPost httpPost = new HttpPost(ApplicationSession.getInstance().getMessage("paytm.dqr.URL"));
			httpPost.setEntity(entity);
			httpPost.setHeader("accept", "application/json");
			httpPost.setHeader("content-type", "application/json");
			LOGGER.info("httpPost: " + httpPost);
			HttpResponse httpresponse = httpclient.execute(httpPost);
			String content = EntityUtils.toString(httpresponse.getEntity());
			LOGGER.info("content: " + content);
			ObjectMapper obj = new ObjectMapper();
			Map<String, Map<String, String>> Object = (Map<String, Map<String, String>>) obj.readValue(content,
					Map.class);
			Map<String, String> bodyMap = (Map<String, String>) Object.get("body");
			String qrdata = bodyMap.get("qrData");
			redirectURL = "PaytmPayments:?requestId=123;method=displayTxnQr;mid=NAGARN91949950010751;portName=COM4;baudRate=115200;parity=0;dataBits=8;stopBits=1;order_id="
					+ orderId + ";order_amount=" + formattedAmount + ";qrcode_id=" + qrdata
					+ ";currencySign=INR;debugMode=1;posid=POS-1";
			response.put("redirectURL", redirectURL);
			response.put("orderId", paymentRequestDTO.getTxnid().toString());
		} catch (Exception e) {
			LOGGER.error("Exception: " + e);
			e.printStackTrace();
		}
		return response;

	}

	@SuppressWarnings("deprecation")
	public static CloseableHttpClient getCloseableHttpClient() {
		CloseableHttpClient httpClient = null;
		try {
			httpClient = HttpClients.custom().setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
					.setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
						public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
							return true;
						}
					}).build()).build();

		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
			LOGGER.error("Exception: " + e);
			e.printStackTrace();

		}
		return httpClient;
	}

	private PaymentRequestDTO convertToDTO(final PropertyBillPaymentModel model, final HttpServletRequest request,
			Long amount) {
		PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO();
		final ServiceMaster service = ApplicationContextProvider.getApplicationContext()
				.getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode("PBP", model.getBillPayDto().getOrgId());
		if (service != null) {
			paymentRequestDTO.setUdf1(service.getSmServiceId().toString());
			paymentRequestDTO.setServiceId(service.getSmServiceId());
			if (UserSession.getCurrent().getLanguageId() == 1) {
				paymentRequestDTO.setServiceName(service.getSmServiceName());
			} else {
				paymentRequestDTO.setServiceName(service.getSmServiceNameMar());
			}
		}
		final String url = request.getRequestURL().toString();
		paymentRequestDTO.setUdf2(model.getPropBillPaymentDto().getAssNo());
		paymentRequestDTO.setUdf5(service.getSmShortdesc());
		paymentRequestDTO.setUdf6(model.getBillPayDto().getOrgId().toString());
		paymentRequestDTO.setUdf7(String.valueOf(UserSession.getCurrent().getEmployee().getUserId()));
		paymentRequestDTO.setMobNo(model.getBillPayDto().getMobileNo());
		paymentRequestDTO.setValidateAmount(BigDecimal.valueOf(amount));
		paymentRequestDTO.setControlUrl(MainetConstants.CommonConstants.NA);
		paymentRequestDTO.setFailUrl(url + MainetConstants.REQUIRED_PG_PARAM.FAIL);
		paymentRequestDTO.setCancelUrl(url + MainetConstants.REQUIRED_PG_PARAM.CANCEL);
		paymentRequestDTO.setSuccessUrl(url + MainetConstants.REQUIRED_PG_PARAM.SUCCESS_PAYU);
		paymentRequestDTO.setChallanServiceType(MainetConstants.FlagY);
		paymentRequestDTO.setDocumentUploaded(MainetConstants.FlagN);
		paymentRequestDTO.setOrgId(model.getBillPayDto().getOrgId());
		paymentRequestDTO.setEmpId(UserSession.getCurrent().getEmployee().getUserId());
		paymentRequestDTO.setLangId(UserSession.getCurrent().getLanguageId());
		paymentRequestDTO.setApplicationId(model.getPropBillPaymentDto().getAssNo());
		paymentRequestDTO.setApplicantName(model.getBillPayDto().getOwnerFullName());
		paymentRequestDTO.setDueAmt(BigDecimal.valueOf(amount));
		if ((model.getBillPayDto().getEmailId() != null) && !model.getBillPayDto().getEmailId().isEmpty()) {
			paymentRequestDTO.setEmail(model.getBillPayDto().getEmailId());
		} else {
			paymentRequestDTO.setEmail(MainetConstants.REQUIRED_PG_PARAM.NA);
		}
		paymentRequestDTO.setPayModeorType(MainetConstants.PAYMODE.MOBILE);
		return paymentRequestDTO;
	}

	@RequestMapping(params = "backToMainPage", method = RequestMethod.POST)
	public ModelAndView back(@RequestParam(value = "orderId", required = false) Long orderId,HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Model uiModel) {
		this.sessionCleanup(httpServletRequest);
		String redirectURL = ApplicationSession.getInstance().getMessage("paytm.dqr.homescreen");
		if(orderId!=null) {	
			PaymentTransactionMas paymentTransactionMas = paymentDAO.getOnlineTransactionMasByTrackId(orderId);
			if (paymentTransactionMas != null && paymentTransactionMas.getRecvHash() != null) {
				redirectURL = paymentTransactionMas.getRecvHash();
			}
		}
		uiModel.addAttribute("redirectURL", redirectURL);
		getModel().setCommonHelpDocs("PropertyBillPayment.html");
		getModel().bind(httpServletRequest);
		PropertyBillPaymentModel model = this.getModel();
		try {
			List<LookUp> parentPropLookupList = CommonMasterUtility.getLevelData(PrefixConstants.GPI,
					MainetConstants.NUMBER_ONE, UserSession.getCurrent().getOrganisation());
			model.setParentPropLookupList(parentPropLookupList);
		} catch (Exception e) {
			LOGGER.error("Prefix GPI is not configured in prefix master ");
		}
		return new ModelAndView("BackToPropertyQRPayment", MainetConstants.FORM_NAME, model);
	}
	
}
