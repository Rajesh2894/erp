package com.abm.mainet.payment.service;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.jfree.util.Log;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.DefaultUriTemplateHandler;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.ProperySearchDto;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.EncryptionAndDecryption;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.payment.dao.PaymentDAO;
import com.abm.mainet.payment.domain.PGBankDetail;
import com.abm.mainet.payment.domain.PGBankParameter;
import com.abm.mainet.payment.domain.PaymentTransactionMas;
import com.abm.mainet.payment.dto.PaymentRequestDTO;
import com.abm.mainet.payment.dto.PaymentTransactionMasDTO;
import com.abm.mainet.payment.dto.ProvisionalCertificateDTO;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PaymentServiceImpl implements IPaymentService {

	private static final String PROBLEM_DUE_TO_SECURITY_REASONS_TRY_AGAIN = "Some problem due to security reasons try again...";

	private static final String EXCEPTION_FOR_FETCHING_BANK_PARAMETERS = "Some problem facing for fetching Bank Parameters";

	protected Logger LOG = Logger.getLogger(PaymentServiceImpl.class);

	@Resource
	private PaymentDAO paymentDAO;

	@Autowired
	private ISMSAndEmailService ismsAndEmailService;

	/**
	 * This function performs validation save {@link PaymentTransactionMas} When we
	 * send request to the respective merchants.
	 * 
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */

	@Override
	@Transactional
	public void proceesPaymentTransactionOnApplicationSubmit(final HttpServletRequest httpServletRequest,
			final PaymentRequestDTO payURequestDTO) throws FrameworkException {
		final String serviceName = paymentDAO.findServiceNameForServiceId(payURequestDTO.getServiceId());
		payURequestDTO.setServiceName(serviceName);
		saveOnlineTransactionMasterOnApplicationSubmit(payURequestDTO);
	}

	@Transactional
	public void saveOnlineTransactionMasterOnApplicationSubmit(final PaymentRequestDTO payURequestDTO)
			throws FrameworkException {
		final PaymentTransactionMas paymentTransactionMas = new PaymentTransactionMas();
		paymentTransactionMas.setLmodDate(new Date());
		paymentTransactionMas.setLgIpMac(Utility.getMacAddress());
		paymentTransactionMas.setOrgId(payURequestDTO.getOrgId());
		paymentTransactionMas.setUserId(payURequestDTO.getEmpId());
		paymentTransactionMas.setLangId(MainetConstants.DEFAULT_LANGUAGE_ID);
		paymentTransactionMas.setApmApplicationDate(new Date());
		paymentTransactionMas.setSmServiceId(payURequestDTO.getServiceId());
		paymentTransactionMas.setSendKey(MainetConstants.BankParam.KEY);
		paymentTransactionMas.setSendAmount(payURequestDTO.getDueAmt());
		paymentTransactionMas.setSendProductinfo(payURequestDTO.getServiceName());
		paymentTransactionMas.setSendFirstname(payURequestDTO.getApplicantName());
		paymentTransactionMas.setSendEmail(payURequestDTO.getEmail());
		paymentTransactionMas.setSendPhone(payURequestDTO.getMobNo());
		paymentTransactionMas.setSendSurl(payURequestDTO.getSuccessUrl());
		paymentTransactionMas.setSendFurl(payURequestDTO.getFailUrl());
		paymentTransactionMas.setSendSalt(MainetConstants.BankParam.SALT);
		paymentTransactionMas.setSendHash(MainetConstants.BankParam.HASH);
		paymentTransactionMas.setPgType(MainetConstants.BankParam.PG);
		paymentTransactionMas.setApmApplicationId(payURequestDTO.getUdf2());
		paymentTransactionMas.setSendUrl(MainetConstants.BankParam.SENDURL);
		paymentTransactionMas.setRecvStatus(MainetConstants.PAYMENT_STATUS.PENDING);
		paymentTransactionMas.setFeeIds(payURequestDTO.getFeeIds());
		paymentTransactionMas.setChallanServiceType(payURequestDTO.getChallanServiceType());
		paymentTransactionMas.setDocumentUploaded(payURequestDTO.getDocumentUploaded());
		paymentTransactionMas.setLangId(1);
		paymentDAO.savePaymentTransaction(paymentTransactionMas);
		payURequestDTO.setTxnid(paymentTransactionMas.getTranCmId());

	}

	@Override
	@Transactional
	public void proceesPaymentTransactionBeforePayment(final HttpServletRequest httpServletRequest,
			final PaymentRequestDTO payURequestDTO) throws FrameworkException {
		final List<PGBankParameter> pgBankParamDets = paymentDAO
				.getMerchantMasterParamByBankId(payURequestDTO.getBankId(), payURequestDTO.getOrgId());

		if ((pgBankParamDets != null) && !pgBankParamDets.isEmpty()) {
			for (final PGBankParameter pgBankParamDet : pgBankParamDets) {
				if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.KEY)) {
					payURequestDTO.setKey(pgBankParamDet.getParaValue());
				}
				if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.SALT)) {
					payURequestDTO.setSalt(pgBankParamDet.getParaValue());
				}
				if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.SCHEME_CODE)
						&& (pgBankParamDet.getParaValue() != null)) {
					payURequestDTO.setSchemeCode(pgBankParamDet.getParaValue());
				}
				if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.REQUESTTYPE)
						&& (pgBankParamDet.getParaValue() != null)) {
					payURequestDTO.setRequestType(pgBankParamDet.getParaValue());
				}

				if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.PRODUCTION)
						&& (pgBankParamDet.getParaValue() != null)) {
					payURequestDTO.setProduction(pgBankParamDet.getParaValue());
				}
				if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.PG)
						&& (pgBankParamDet.getParaValue() != null)) {
					payURequestDTO.setPgName(pgBankParamDet.getParaValue());
				}
				if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.SURL)
						&& (pgBankParamDet.getParaValue() != null)) {
					payURequestDTO.setSuccessUrl(pgBankParamDet.getParaValue());
				}
				if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.FURL)
						&& (pgBankParamDet.getParaValue() != null)) {
					payURequestDTO.setFailUrl(pgBankParamDet.getParaValue());
				}
				if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.CURL)
						&& (pgBankParamDet.getParaValue() != null)) {
					payURequestDTO.setCancelUrl(pgBankParamDet.getParaValue());
				}

				if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.ORDER_ID)) {
					payURequestDTO.setOrderId(pgBankParamDet.getParaValue());
				}

				if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.TRA_CIN)) {
					payURequestDTO.setTrnCurrency(pgBankParamDet.getParaValue());
				}
				if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.TRA_DEC)) {
					payURequestDTO.setTrnRemarks(pgBankParamDet.getParaValue());
				}
				if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.TRANS_TYE)) {
					payURequestDTO.setRequestType(pgBankParamDet.getParaValue());
				}
				if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.RESP_URL)) {
					payURequestDTO.setResponseUrl(pgBankParamDet.getParaValue());
				}
				if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.ADD_F1)) {
					payURequestDTO.setAddField1(pgBankParamDet.getParaValue());
				}
				if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.ADD_F2)) {
					payURequestDTO.setAddField2(pgBankParamDet.getParaValue());
				}
				if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.ADD_F3)) {
					payURequestDTO.setAddField3(pgBankParamDet.getParaValue());
				}
				if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.ADD_F4)) {
					payURequestDTO.setAddField4(pgBankParamDet.getParaValue());
				}
				if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.ADD_F5)) {
					payURequestDTO.setAddField5(pgBankParamDet.getParaValue());
				}
				if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.ADD_F6)) {
					payURequestDTO.setAddField6(pgBankParamDet.getParaValue());
				}
				if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.ADD_F7)) {
					payURequestDTO.setAddField7(pgBankParamDet.getParaValue());
				}
				if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.ADD_F8)) {
					payURequestDTO.setAddField8(pgBankParamDet.getParaValue());
				}
				if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.ADD_F9)) {
					payURequestDTO.setRecurrPeriod(pgBankParamDet.getParaValue());
				}
				if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.ADD_F10)) {
					payURequestDTO.setRecurrDay(pgBankParamDet.getParaValue());
				}
				if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.ADD_F11)) {
					payURequestDTO.setNoOfRecurring(pgBankParamDet.getParaValue());
				}
			}
		} else {
			payURequestDTO.setErrorCause(EXCEPTION_FOR_FETCHING_BANK_PARAMETERS);

			throw new FrameworkException(EXCEPTION_FOR_FETCHING_BANK_PARAMETERS);
		}
		final String url = httpServletRequest.getRequestURL().toString();

		final PGBankDetail bankMaster = paymentDAO.getBankDetailByBankId(payURequestDTO.getBankId(),
				payURequestDTO.getOrgId());
		if (bankMaster != null) {
			payURequestDTO.setPgUrl(bankMaster.getPgUrl());
			payURequestDTO.setMerchantId(bankMaster.getMerchantId());
			payURequestDTO.setBankId(bankMaster.getBankId());
			payURequestDTO.setPgName(bankMaster.getPgName());
		} else {
			throw new FrameworkException(PROBLEM_DUE_TO_SECURITY_REASONS_TRY_AGAIN);
		}

		try {
			String amt = EncryptionAndDecryption.decrypt(payURequestDTO.getFinalAmount());
			if (amt != null && !amt.isEmpty()) {
				payURequestDTO.setDueAmt(new BigDecimal(amt));
			}

		} catch (final Exception e) {
			throw new FrameworkException(PROBLEM_DUE_TO_SECURITY_REASONS_TRY_AGAIN);
		}
		if (payURequestDTO.getPgName().equalsIgnoreCase(MainetConstants.PG_SHORTNAME.PAYU)) {
			payURequestDTO.setSuccessUrl(url + MainetConstants.REQUIRED_PG_PARAM.SUCCESS_PAYU);
			payURequestDTO.setFailUrl(url + MainetConstants.REQUIRED_PG_PARAM.FAIL_PAYU);
			payURequestDTO.setSuccessUrl(url + MainetConstants.REQUIRED_PG_PARAM.CANCEL_PAYU);
			getRequestDTO(payURequestDTO, payURequestDTO.getPgName());
		} else if (payURequestDTO.getPgName().equalsIgnoreCase(MainetConstants.PG_SHORTNAME.TECH_PROCESS)) {
			payURequestDTO.setHash(MainetConstants.REQUIRED_PG_PARAM.NA);
			payURequestDTO.setSuccessUrl(url + MainetConstants.REQUIRED_PG_PARAM.SUCCESS_TP);
			payURequestDTO.setFailUrl(url + MainetConstants.REQUIRED_PG_PARAM.FAIL_TP);
			payURequestDTO.setSuccessUrl(url + MainetConstants.REQUIRED_PG_PARAM.CANCEL_TP);
			getRequestDTO(payURequestDTO, payURequestDTO.getPgName());
		} else if (payURequestDTO.getPgName().equalsIgnoreCase(MainetConstants.PG_SHORTNAME.MAHA_ONLINE)) {

			payURequestDTO.setSuccessUrl(url + MainetConstants.REQUIRED_PG_PARAM.SUCCESS_MH);
			payURequestDTO.setFailUrl(url + MainetConstants.REQUIRED_PG_PARAM.FAIL_MH);
			payURequestDTO.setSuccessUrl(url + MainetConstants.REQUIRED_PG_PARAM.CANCEL_MH);
			getRequestDTO(payURequestDTO, payURequestDTO.getPgName());
		} else if (payURequestDTO.getPgName().equalsIgnoreCase(MainetConstants.PG_SHORTNAME.HDFC)) {
			payURequestDTO.setHash(MainetConstants.REQUIRED_PG_PARAM.NA);
			payURequestDTO.setSuccessUrl(url + MainetConstants.REQUIRED_PG_PARAM.SUCCESS_HDFC);
			payURequestDTO.setFailUrl(url + MainetConstants.REQUIRED_PG_PARAM.FAIL_HDFC);
			payURequestDTO.setSuccessUrl(url + MainetConstants.REQUIRED_PG_PARAM.CANCEL_HDFC);
			getRequestDTO(payURequestDTO, payURequestDTO.getPgName());
		} else if (payURequestDTO.getPgName().equalsIgnoreCase(MainetConstants.PG_SHORTNAME.CCA)) {
			payURequestDTO.setHash(MainetConstants.REQUIRED_PG_PARAM.NA);
			if (payURequestDTO.getSuccessUrl() == null || payURequestDTO.getSuccessUrl().isEmpty()) {
				payURequestDTO.setSuccessUrl(url + MainetConstants.REQUIRED_PG_PARAM.SUCCESS_HDFC_CCA);
			}
			if (payURequestDTO.getCancelUrl() == null || payURequestDTO.getCancelUrl().isEmpty()) {
				payURequestDTO.setCancelUrl(url + MainetConstants.REQUIRED_PG_PARAM.CANCEL_HDFC_CCA);
			}
			if (payURequestDTO.getFailUrl() == null || payURequestDTO.getFailUrl().isEmpty()) {
				payURequestDTO.setFailUrl(url + MainetConstants.REQUIRED_PG_PARAM.FAIL_HDFC_CCA);
			}
		} else if (payURequestDTO.getPgName().equalsIgnoreCase(MainetConstants.PG_SHORTNAME.AWL)) {
			payURequestDTO.setHash(MainetConstants.REQUIRED_PG_PARAM.NA);

			payURequestDTO.setSuccessUrl(url + MainetConstants.REQUIRED_PG_PARAM.SUCCESS_AWL);

			payURequestDTO.setCancelUrl(url + MainetConstants.REQUIRED_PG_PARAM.CANCEL_AWL);

			payURequestDTO.setFailUrl(url + MainetConstants.REQUIRED_PG_PARAM.FAIL_AWL);

			getRequestDTO(payURequestDTO, payURequestDTO.getPgName());
		} else if (payURequestDTO.getPgName().equalsIgnoreCase(MainetConstants.PG_SHORTNAME.EASYPAY)) {
			payURequestDTO.setHash(MainetConstants.REQUIRED_PG_PARAM.NA);
			LOG.info(
					"in proceesTransactionBeforePayment method of PaymentServiceImpl class at portal side in Easypay condition");
			payURequestDTO.setSuccessUrl(url + MainetConstants.REQUIRED_PG_PARAM.SUCCESS_EASYPAY);

			payURequestDTO.setCancelUrl(url + MainetConstants.REQUIRED_PG_PARAM.CANCEL_EASYPAY);

			payURequestDTO.setFailUrl(url + MainetConstants.REQUIRED_PG_PARAM.FAIL_EASYPAY);

			getRequestDTO(payURequestDTO, payURequestDTO.getPgName());
		} else if (payURequestDTO.getPgName().equalsIgnoreCase(MainetConstants.PG_SHORTNAME.ICICI)) {
			payURequestDTO.setHash(MainetConstants.REQUIRED_PG_PARAM.NA);
			if (payURequestDTO.getSuccessUrl() == null || payURequestDTO.getSuccessUrl().isEmpty()) {
				payURequestDTO.setSuccessUrl(url + MainetConstants.REQUIRED_PG_PARAM.SUCCESS_ICICI_CCA);
			}
			if (payURequestDTO.getCancelUrl() == null || payURequestDTO.getCancelUrl().isEmpty()) {
				payURequestDTO.setCancelUrl(url + MainetConstants.REQUIRED_PG_PARAM.CANCEL_ICICI_CCA);
			}
			if (payURequestDTO.getFailUrl() == null || payURequestDTO.getFailUrl().isEmpty()) {
				payURequestDTO.setFailUrl(url + MainetConstants.REQUIRED_PG_PARAM.FAIL_ICICI_CCA);
			}
		} else if (payURequestDTO.getPgName().equalsIgnoreCase(MainetConstants.PG_SHORTNAME.BILLCLOUD)) {
			payURequestDTO.setHash(MainetConstants.REQUIRED_PG_PARAM.NA);
			payURequestDTO.setSalt(MainetConstants.REQUIRED_PG_PARAM.NA);
			if (payURequestDTO.getSuccessUrl() == null || payURequestDTO.getSuccessUrl().isEmpty()) {
				payURequestDTO.setSuccessUrl(url + MainetConstants.REQUIRED_PG_PARAM.SUCCESS_BILLCLOUD);
			}
			if (payURequestDTO.getCancelUrl() == null || payURequestDTO.getCancelUrl().isEmpty()) {
				payURequestDTO.setCancelUrl(url + MainetConstants.REQUIRED_PG_PARAM.CANCEL_BILLCLOUD);
			}
			if (payURequestDTO.getFailUrl() == null || payURequestDTO.getFailUrl().isEmpty()) {
				payURequestDTO.setFailUrl(url + MainetConstants.REQUIRED_PG_PARAM.FAIL_BILLCLOUD);
			}
		}

		saveOnlineTransactionMaster(payURequestDTO);

	}

	private void getRequestDTO(PaymentRequestDTO paymentRequestDTO, final String pgName) {
		LOG.info("Start getRequestDTO of paymentServiceImp class of portal");
		try {

			final PaymentStrategy paymentStrategy = PaymentBeanFactory.getInstance(pgName);
			paymentRequestDTO = paymentStrategy.generatePaymentRequest(paymentRequestDTO);

		} catch (final Exception e) {
			throw new FrameworkException(" Exeception  occur in getRequestDTO() ...", e);
		}
		LOG.info("End getRequestDTO of paymentServiceImp class of portal");
	}

	public void saveOnlineTransactionMaster(final PaymentRequestDTO payURequestDTO) throws FrameworkException {
		LOG.info("Start saveOnlineTransactionMaster of paymentServiceImp class of portal");
		final PaymentTransactionMas paymentTransactionMas = paymentDAO
				.getOnlineTransactionMasByTrackId(payURequestDTO.getTxnid());
		paymentTransactionMas.setSendFirstname(payURequestDTO.getApplicantName());
		paymentTransactionMas.setSendPhone(payURequestDTO.getMobNo());
		paymentTransactionMas.setSendEmail(payURequestDTO.getEmail());
		paymentTransactionMas.setLmodDate(new Date());
		paymentTransactionMas.setLgIpMac(Utility.getMacAddress());
		paymentTransactionMas.setSendUrl(payURequestDTO.getPgUrl());
		paymentTransactionMas.setSendKey(payURequestDTO.getKey());
		paymentTransactionMas.setSendSalt(payURequestDTO.getSalt());
		paymentTransactionMas.setSendHash(payURequestDTO.getHash());
		paymentTransactionMas.setSendSurl(payURequestDTO.getSuccessUrl());
		paymentTransactionMas.setPgType(String.valueOf(payURequestDTO.getBankId()));
		paymentTransactionMas.setPgSource(payURequestDTO.getPgName());
		paymentTransactionMas.setRecvStatus(MainetConstants.PAYMENT_STATUS.PENDING);
		paymentDAO.updateOnlineTransactionMas(paymentTransactionMas);
		LOG.info("End saveOnlineTransactionMaster of paymentServiceImp class of portal");
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void proceesPaymentTransactionAfterPayment(final HttpServletRequest httpServletRequest,
			final Map<String, String> responseMap) throws FrameworkException {

		final Long transId = Long.parseLong(responseMap.get(MainetConstants.BankParam.TXN_ID));

		final PaymentTransactionMas paymentTransactionMas = paymentDAO.getOnlineTransactionMasByTrackId(transId);

		if (paymentTransactionMas == null) {
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.EXCEPTION,
					"Could not find current transaction ! Please try again!");

			throw new FrameworkException("Could not find current transaction ! Please try again!");
		}

		paymentTransactionMas.setRecvStatus(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.STATUS));
		paymentTransactionMas.setRecvErrm(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.ERR_STR));
		paymentTransactionMas.setRecvMode(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.MODE));
		paymentTransactionMas
				.setRecvNetAmountDebit(Double.parseDouble(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.NET_AMT)));
		paymentTransactionMas.setRecvHash(responseMap.get(MainetConstants.BankParam.HASH));
		paymentTransactionMas.setUpdatedDate(new Date());
		paymentTransactionMas.setRecvBankRefNum(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.BNK_REF_NO));
		paymentTransactionMas.setRecvMihpayid(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.TRANS_ID));
		paymentTransactionMas.setPgSource(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.PAY_SRC));
		paymentDAO.updateOnlineTransactionMas(paymentTransactionMas);

	}

	@Override
	@Transactional(readOnly = true)
	public Map<Long, String> getAllPgBank(final long orgid) throws FrameworkException {
		return paymentDAO.getAllPgBank(orgid);
	}

	@Transactional(readOnly = true)
	public String getServiceShortName(final Long smServiceId, final long orgid) {

		return paymentDAO.getServiceShortName(smServiceId, orgid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainetsource.payment.service.PaymentService#
	 * getOnlineTransactionMasByTrackId(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = true)
	public PaymentTransactionMas getOnlineTransactionMasByTrackId(final Long trackId) {

		return paymentDAO.getOnlineTransactionMasByTrackId(trackId);
	}

	@Override
	public Map<String, String> genrateResponse(Map<String, String> responseMap, final String pgFlag,
			final String sessionAmount, final Long pgId, final Long orgId, final int LangId) {

		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.LANG, String.valueOf(LangId));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.PG_ID, pgId.toString());
		setPgbankDeatilInMap(pgId, responseMap, orgId);

		if (responseMap.containsKey(MainetConstants.REQUIRED_PG_PARAM.MSG)
				&& pgFlag.equalsIgnoreCase(MainetConstants.PG_SHORTNAME.TECH_PROCESS)) {

			getPaymentResponseDTO(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.MSG), responseMap, pgFlag);
		}

		if (responseMap.containsKey(MainetConstants.REQUIRED_PG_PARAM.MSG)
				&& pgFlag.equalsIgnoreCase(MainetConstants.PG_SHORTNAME.MAHA_ONLINE)) {

			getPaymentResponseDTO(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.MSG), responseMap, pgFlag);

		}

		else if (responseMap.containsKey(MainetConstants.REQUIRED_PG_PARAM.MSG)
				&& pgFlag.equalsIgnoreCase(MainetConstants.PG_SHORTNAME.HDFC)) {
			getPaymentResponseDTO(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.MSG), responseMap, pgFlag);

		}

		else if (pgFlag.equalsIgnoreCase(MainetConstants.PG_SHORTNAME.PAYU)) {

			final String responseAmount = responseMap.get(MainetConstants.REQUIRED_PG_PARAM.AMT);
			final String[] amount = responseAmount.split("\\.");
			final String mnt = amount[0];
			final String[] checkamount = sessionAmount.split("\\.");
			final String sesAmount = checkamount[0];
			if ((responseAmount != null) && !responseAmount.isEmpty() && mnt.equals(sesAmount)) {
				getPaymentResponseDTO(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.MSG), responseMap, pgFlag);
			} else {
				throw new FrameworkException("Some Problem Due To Security Reason...Amount Are Not Match");
			}

		} else if (pgFlag.equalsIgnoreCase(MainetConstants.PG_SHORTNAME.CCA)) {

			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.SESSION_AMT, sessionAmount);
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.WORKING_KEY,
					responseMap.get(MainetConstants.REQUIRED_PG_PARAM.KEY));
			getPaymentResponseDTO(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.ENCRYPTED_RESPONSE), responseMap,
					pgFlag);
		} else if (pgFlag.equalsIgnoreCase(MainetConstants.PG_SHORTNAME.EASYPAY)) {

			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.SESSION_AMT, sessionAmount);
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.WORKING_KEY,
					responseMap.get(MainetConstants.REQUIRED_PG_PARAM.KEY));
			getPaymentResponseDTO(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.ENCRYPTED_RESPONSE), responseMap,
					pgFlag);
		} else if (pgFlag.equalsIgnoreCase(MainetConstants.PG_SHORTNAME.ICICI)) {

			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.SESSION_AMT, sessionAmount);
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.WORKING_KEY,
					responseMap.get(MainetConstants.REQUIRED_PG_PARAM.KEY));
			getPaymentResponseDTO(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.ENCRYPTED_RESPONSE), responseMap,
					pgFlag);
		} else if (pgFlag.equalsIgnoreCase(MainetConstants.PG_SHORTNAME.BILLCLOUD)) {

			getPaymentResponseDTO(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.MSG), responseMap, pgFlag);
		} else if (pgFlag.equalsIgnoreCase(MainetConstants.PG_SHORTNAME.BILLDESK)) {

			getPaymentResponseDTO(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.MSG), responseMap, pgFlag);
		} else if (pgFlag.equalsIgnoreCase(MainetConstants.PG_SHORTNAME.ATOMPAY)) {

			getPaymentResponseDTO(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.MSG), responseMap, pgFlag);
		}
		return responseMap;
	}

	private void getPaymentResponseDTO(final String responseString, Map<String, String> responseMap,
			final String pgName) {
		try {

			final PaymentStrategy paymentStrategy = PaymentBeanFactory.getInstance(pgName);
			responseMap = paymentStrategy.generatePaymentResponse(responseString, responseMap);
		} catch (final Exception e) {
			throw new FrameworkException("Exeception occur in getPaymentResponseDTO...", e);
		}

	}

	@Transactional(readOnly = true)
	private void setPgbankDeatilInMap(final Long bankId, final Map<String, String> responseMap, final Long orgId) {

		final List<PGBankParameter> pgBankParamDets = paymentDAO.getMerchantMasterParamByBankId(bankId, orgId);
		if ((pgBankParamDets != null) && !pgBankParamDets.isEmpty()) {
			for (final PGBankParameter pgBankParamDet : pgBankParamDets) {
				if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.KEY)) {
					responseMap.put(MainetConstants.REQUIRED_PG_PARAM.KEY, pgBankParamDet.getParaValue());

				}
				if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.SALT)) {
					responseMap.put(MainetConstants.REQUIRED_PG_PARAM.IV, pgBankParamDet.getParaValue());
				}

				if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.SCHEME_CODE)) {
					if (pgBankParamDet.getParaValue() != null) {
						responseMap.put(MainetConstants.BankParam.SCHEME_CODE, pgBankParamDet.getParaValue());
					}
				}

				if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.PRODUCTION)) {
					if (pgBankParamDet.getParaValue() != null) {
						responseMap.put(MainetConstants.BankParam.PRODUCTION, pgBankParamDet.getParaValue());
					}
				}
				if (pgBankParamDet.getParName().equals(MainetConstants.BankParam.REQUESTTYPE)) {
					if (pgBankParamDet.getParaValue() != null) {
						responseMap.put(MainetConstants.BankParam.REQUESTTYPE, pgBankParamDet.getParaValue());
					}
				}
			}
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.PG_ID, bankId.toString());
		} else {
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.ERR_STR,
					"Some problem facing for fatching Bank Parameters");
			throw new FrameworkException("Some problem facing for fatching Bank Parameters");
		}
	}

	/*
	 * @Override
	 * 
	 * @Transactional public List<LookUp> getBankList(final String bankId, final int
	 * langId) { List<LookUp> list = new ArrayList<>(); try { final
	 * List<PGPortalBankDetail> bankList = paymentDAO.getPGPortalBankList(bankId);
	 * LookUp lookup = null; if ((bankList != null) && (bankList.size() != 0)) {
	 * list = new ArrayList<>(); for (final PGPortalBankDetail pg : bankList) {
	 * lookup = new LookUp(); lookup.setLookUpId(pg.getBankCode()); if (langId == 1)
	 * { lookup.setLookUpCode(pg.getBankName()); } else {
	 * lookup.setLookUpCode(pg.getBankNameMar()); } list.add(lookup); } } } catch
	 * (final Exception exception) { throw new
	 * FrameworkException("Exception occur in getBankList ...", exception); } return
	 * list; }
	 */

	@Override
	public void updateReiceptData(final CommonChallanDTO offlineDTO) {
		try {
			@SuppressWarnings("unchecked")
			final LinkedHashMap<Long, Object> responseChallan = (LinkedHashMap<Long, Object>) JersyCall
					.callRestTemplateClient(offlineDTO, ServiceEndpoints.PAYMENT_URL.PAYMENT_RECEIPT_ENTRY);

			if (responseChallan != null) {
				Log.info(" update Payment Reciept Data");
			}
		} catch (final Exception exception) {
			throw new FrameworkException("Exception occur in updateReiceptData ...", exception);
		}

	}

	@Override
	@Transactional
	public void sendSMSandEMail(final Map<String, String> responseMap) {

		final SMSAndEmailDTO dto = new SMSAndEmailDTO();
		dto.setEmail(responseMap.get(MainetConstants.BankParam.EMAIL));
		dto.setAppNo(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.APPLICATION_NO));
		dto.setMobnumber(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.PHONE_NO));
		dto.setAppName(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.OWNER_NAME));
		dto.setAppAmount(responseMap.get(MainetConstants.BankParam.AMT));
		dto.setDeptShortCode(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.UDF5));
		dto.setServiceUrl(responseMap.get(MainetConstants.BankParam.UDF3));
		dto.setLangId(Integer.valueOf(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.LANG)));
		dto.setV_muncipality_name(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.ORGNAME));
		dto.setTemplateType(MainetConstants.PAYMENT.OFFLINE);
		dto.setTransactionType(MainetConstants.PAYMENT.ONLINE);
		ismsAndEmailService.sendEmailAndSMS(dto,
				Long.valueOf(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.ORGID)));
	}

	@Override
	@Transactional
	public void canceTransactionBeforePayment(HttpServletRequest httpServletRequest, PaymentRequestDTO payURequestDTO)
			throws FrameworkException {
		final PaymentTransactionMas paymentTransactionMas = paymentDAO
				.getOnlineTransactionMasByTrackId(payURequestDTO.getTxnid());
		paymentTransactionMas.setRecvStatus(MainetConstants.PAYU_STATUS.CANCEL);
		paymentDAO.updateOnlineTransactionMas(paymentTransactionMas);
	}

	@Override
	@Transactional(readOnly = true)
	public List<PaymentTransactionMas> getPaymentMasterListById(Long tranCmId) {
		return paymentDAO.getPaymentMasterListById(tranCmId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PaymentTransactionMasDTO getStatusByReferenceNo(String referenceNo) {
		PaymentTransactionMasDTO dto = new PaymentTransactionMasDTO();
		dto.setReferenceId(referenceNo);
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(dto, ApplicationSession.getInstance().getMessage("GET_STATUS_BY_REFERENCENO"));
		if (responseVo != null) {
			final String d = new JSONObject(responseVo).toString();

			try {
				return new ObjectMapper().readValue(d, PaymentTransactionMasDTO.class);
			} catch (Exception e) {
				throw new FrameworkException(e);
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ChallanReceiptPrintDTO getDuplicateReceipt(ProperySearchDto searchDto) {
		ChallanReceiptPrintDTO dto = new ChallanReceiptPrintDTO();
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(
				searchDto, ApplicationSession.getInstance().getMessage("PROPERTY_DUPLICATE_RECEIPT"));
		if (responseVo != null) {
			final String d = new JSONObject(responseVo).toString();

			try {
				return new ObjectMapper().readValue(d, ChallanReceiptPrintDTO.class);
			} catch (Exception e) {
				throw new FrameworkException(e);
			}
		}
		return null;
	}

	@Override
	public ProvisionalCertificateDTO getProvisinalCertData(Long applicationId, Long orgId) {

		Map<String, String> requestParam = new HashMap<>();
		DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
		dd.setParsePath(true);
		requestParam.put("orgId", String.valueOf(orgId));
		requestParam.put("applicationNo", String.valueOf(applicationId));
		URI uri = dd.expand(ServiceEndpoints.WebServiceUrl.GET_PROV_CERT_DETAILS, requestParam);

		final LinkedHashMap<Long, Object> requestList = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(orgId, uri.toString());

		ProvisionalCertificateDTO dtos = new ProvisionalCertificateDTO();
		if (requestList != null) {
			String d = new JSONObject(requestList).toString();
			try {
				dtos = new ObjectMapper().readValue(d, ProvisionalCertificateDTO.class);

			} catch (Exception e) {
			}
		}
		return dtos;

	}
}
