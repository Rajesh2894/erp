package com.abm.mainet.common.integration.payment.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import javax.crypto.IllegalBlockSizeException;

import org.apache.log4j.Logger;
import org.jfree.util.Log;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.payment.dto.PaymentRequestDTO;
import com.abm.mainet.common.integration.payment.entity.PaymentTransactionMas;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.EncryptionAndDecryption;
import com.ccavenue.security.AesCryptUtil;
import com.ibm.icu.math.BigDecimal;

public class ICICIPayment implements PaymentStrategy {

    protected Logger LOG = Logger.getLogger(ICICIPayment.class);

    @Override
    public PaymentRequestDTO generatePaymentRequest(PaymentRequestDTO paymentRequestVO) throws FrameworkException {
        LOG.info("Start the genreatePaymentRequest() in ICICI payment class ");
        try {
            if ((paymentRequestVO.getPgUrl() != null && !paymentRequestVO.getPgUrl().isEmpty())
                    && (paymentRequestVO.getPgName().equalsIgnoreCase(MainetConstants.PG_SHORTNAME.ICICI))) {
                String workingKey = paymentRequestVO.getKey();
                LinkedHashMap<String, String> map = new LinkedHashMap<>();
                map.put(MainetConstants.REQUIRED_PG_PARAM.MERCHANT_ID, paymentRequestVO.getMerchantId());
                map.put(MainetConstants.REQUIRED_PG_PARAM.ORDER_ID, paymentRequestVO.getTxnid().toString());
                map.put(MainetConstants.REQUIRED_PG_PARAM.CURRENCY, MainetConstants.PG_REQUEST_PROPERTY.CRN);
                map.put(MainetConstants.REQUIRED_PG_PARAM.AMT, paymentRequestVO.getValidateAmount().toString());
                map.put(MainetConstants.REQUIRED_PG_PARAM.REDIRECT_URL, paymentRequestVO.getSuccessUrl());
                map.put(MainetConstants.REQUIRED_PG_PARAM.CANCEL_URL, paymentRequestVO.getCancelUrl());
                map.put(MainetConstants.REQUIRED_PG_PARAM.LANGUAGE, MainetConstants.REQUIRED_PG_PARAM.DEFAULT_LANG);
                map.put(MainetConstants.REQUIRED_PG_PARAM.BILL_NAME, paymentRequestVO.getApplicantName());
                map.put(MainetConstants.REQUIRED_PG_PARAM.MERCHANT_PER1, paymentRequestVO.getUdf1());
                map.put(MainetConstants.REQUIRED_PG_PARAM.MERCHANT_PER2, paymentRequestVO.getUdf2());
                map.put(MainetConstants.REQUIRED_PG_PARAM.MERCHANT_PER3, paymentRequestVO.getUdf3());
                map.put(MainetConstants.REQUIRED_PG_PARAM.MERCHANT_PER5, paymentRequestVO.getUdf5());
                map.put(MainetConstants.REQUIRED_PG_PARAM.MERCHANT_PER4, encryptedParameter(map));

                String encryptedValues = generateEncryptedValue(workingKey, map);

                StringBuilder url = new StringBuilder(paymentRequestVO.getPgUrl());
                url.append(MainetConstants.operator.AMPERSAND + MainetConstants.REQUIRED_PG_PARAM.ENCRYPTED_REQUEST
                        + MainetConstants.REQUIRED_PG_PARAM.SEARCH_SPECIAL_CHAR_REPLACE + encryptedValues);
                url.append(MainetConstants.operator.AMPERSAND + MainetConstants.REQUIRED_PG_PARAM.ACCESS_CODE
                        + MainetConstants.REQUIRED_PG_PARAM.SEARCH_SPECIAL_CHAR_REPLACE
                        + paymentRequestVO.getSchemeCode());
                paymentRequestVO.setPayRequestMsg(url.toString());
                LOG.info("Single Token request String " + url.toString());
            } else {
                LOG.error("Payment Required parameter are not fetch from tables");
                throw new FrameworkException("Payment Required parameter are not fetch from tables");
            }
        } catch (final Exception exception) {
            LOG.error(" Exception occur in genreatePaymentRequest for ICICI Payment", exception);
            throw new FrameworkException("Exception occur in genreatePaymentRequest in ICICI Payment ...",
                    exception);
        }
        return paymentRequestVO;
    }

    @Override
    public Map<String, String> generatePaymentResponse(String responseString, Map<String, String> responseMap)
            throws FrameworkException {

        String workingKey = "", response = "";
        Map<String, String> paymentResponseMap = new HashMap<>(0);
        final DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        final Date date = new Date();
        final String currentDate = dateFormat.format(date);

        if (responseMap != null && !responseMap.isEmpty()) {
            String sessionAmount = responseMap.get(MainetConstants.REQUIRED_PG_PARAM.SESSION_AMT);
            if (responseMap.containsKey(MainetConstants.REQUIRED_PG_PARAM.KEY)) {
                workingKey = responseMap.get(MainetConstants.REQUIRED_PG_PARAM.KEY);

                if (responseMap.containsKey(MainetConstants.REQUIRED_PG_PARAM.ENCRYPTED_RESPONSE)) {

                    response = responseMap.get(MainetConstants.REQUIRED_PG_PARAM.ENCRYPTED_RESPONSE);
                    Map<String, String> decreptedMap = generateDecryptedValue(workingKey, response);
                    int failureCount = 0;
                    StringBuilder errorString = new StringBuilder();
                    if (decreptedMap.containsKey(MainetConstants.REQUIRED_PG_PARAM.ORDER_STATUS)) {

                        try {
                            if (decreptedMap.get(MainetConstants.REQUIRED_PG_PARAM.MERCHANT_PER4) != null) {
                                Map<String, String> decreptedParameter = decrypteParameter(
                                        decreptedMap.get(MainetConstants.REQUIRED_PG_PARAM.MERCHANT_PER4));

                                /*
                                 * if (!decreptedParameter.get(MainetConstants.REQUIRED_PG_PARAM.CURRENCY)
                                 * .equals(decreptedMap.get(MainetConstants.REQUIRED_PG_PARAM.CURRENCY))) { failureCount =
                                 * failureCount + 1; errorString.append("Currency Type in system is " +
                                 * decreptedParameter.get(MainetConstants.REQUIRED_PG_PARAM.CURRENCY) +
                                 * " while received from PG is " + decreptedMap.get(MainetConstants.REQUIRED_PG_PARAM.CURRENCY));
                                 * }
                                 */

                                boolean orderEncryptedStatus = !decreptedParameter
                                        .get(MainetConstants.REQUIRED_PG_PARAM.ORDER_ID)
                                        .equals(decreptedMap.get(MainetConstants.REQUIRED_PG_PARAM.ORDER_ID));
                                boolean orderNumberStatus = Boolean.FALSE;
                                /*
                                 * !decreptedParameter .get(MainetConstants.REQUIRED_PG_PARAM.ORDER_ID)
                                 * .equals(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.ORDER_NO));
                                 */

                                if (orderEncryptedStatus || orderNumberStatus) {
                                    failureCount = failureCount + 1;
                                    String orderString = "";
                                    if (orderEncryptedStatus) {
                                        orderString = orderString
                                                + decreptedMap.get(MainetConstants.REQUIRED_PG_PARAM.ORDER_ID) + " ";
                                    }
                                    if (orderNumberStatus) {
                                        orderString = orderString
                                                + responseMap.get(MainetConstants.REQUIRED_PG_PARAM.ORDER_NO);
                                    }
                                    errorString.append("Order Id in system is "
                                            + decreptedParameter.get(MainetConstants.REQUIRED_PG_PARAM.ORDER_ID)
                                            + " while received from PG is " + orderString);
                                } else {
                                    Long tranCmId = Long
                                            .valueOf(decreptedMap.get(MainetConstants.REQUIRED_PG_PARAM.ORDER_ID));
                                    List<PaymentTransactionMas> paymentList = ApplicationContextProvider
                                            .getApplicationContext().getBean(PaymentService.class)
                                            .getPaymentMasterListById(tranCmId);
                                    if (paymentList != null && !paymentList.isEmpty()) {
                                        failureCount = failureCount + 1;
                                        errorString.append("For order number "
                                                + decreptedMap.get(MainetConstants.REQUIRED_PG_PARAM.ORDER_ID)
                                                + ", response is already their in the system");
                                    }
                                }

                                if (decreptedMap.containsKey(MainetConstants.REQUIRED_PG_PARAM.AMT)) {

                                    String originalAmount = decreptedParameter
                                            .get(MainetConstants.REQUIRED_PG_PARAM.AMT);
                                    String receivedAmount = decreptedMap.get(MainetConstants.REQUIRED_PG_PARAM.AMT);

                                    BigDecimal original = new BigDecimal(originalAmount).setScale(2,
                                            BigDecimal.ROUND_HALF_UP);
                                    BigDecimal received = new BigDecimal(receivedAmount).setScale(2,
                                            BigDecimal.ROUND_HALF_UP);
                                    BigDecimal sessionAmt = new BigDecimal(sessionAmount).setScale(2,
                                            BigDecimal.ROUND_HALF_UP);

                                    boolean originalAmountstatus = (original.compareTo(received)) != 0;
                                    boolean sessionAmountStatus = (sessionAmt.compareTo(received)) != 0;

                                    if (originalAmountstatus || sessionAmountStatus) {
                                        failureCount = failureCount + 1;
                                        errorString.append(
                                                "Amount in system is " + sessionAmt + " while received from PG is "
                                                        + decreptedMap.get(MainetConstants.REQUIRED_PG_PARAM.AMT));
                                    }
                                }

                                /*
                                 * if (!decreptedParameter.get(MainetConstants.REQUIRED_PG_PARAM.BILL_NAME)
                                 * .equals(decreptedMap.get(MainetConstants.REQUIRED_PG_PARAM.BILL_NAME))) { failureCount =
                                 * failureCount + 1; errorString.append("Bill Name in system is " +
                                 * decreptedParameter.get(MainetConstants.REQUIRED_PG_PARAM.BILL_NAME) +
                                 * " while received from PG is " + decreptedMap.get(MainetConstants.REQUIRED_PG_PARAM.BILL_NAME));
                                 * } if (!decreptedParameter.get(MainetConstants.REQUIRED_PG_PARAM.MERCHANT_PER1)
                                 * .equals(decreptedMap.get(MainetConstants.REQUIRED_PG_PARAM.MERCHANT_PER1))) { failureCount =
                                 * failureCount + 1; errorString.append("Merchant Param 1 in system is " +
                                 * decreptedParameter.get(MainetConstants.REQUIRED_PG_PARAM.MERCHANT_PER1) +
                                 * " while received from PG is " +
                                 * decreptedMap.get(MainetConstants.REQUIRED_PG_PARAM.MERCHANT_PER1)); } if
                                 * (!decreptedParameter.get(MainetConstants.REQUIRED_PG_PARAM.MERCHANT_PER2)
                                 * .equals(decreptedMap.get(MainetConstants.REQUIRED_PG_PARAM.MERCHANT_PER2))) { failureCount =
                                 * failureCount + 1; errorString.append("Merchant Param 2 in system is " +
                                 * decreptedParameter.get(MainetConstants.REQUIRED_PG_PARAM.MERCHANT_PER2) +
                                 * " while received from PG is " +
                                 * decreptedMap.get(MainetConstants.REQUIRED_PG_PARAM.MERCHANT_PER2)); } if
                                 * (!decreptedParameter.get(MainetConstants.REQUIRED_PG_PARAM.MERCHANT_PER3)
                                 * .equals(decreptedMap.get(MainetConstants.REQUIRED_PG_PARAM.MERCHANT_PER3))) { failureCount =
                                 * failureCount + 1; errorString.append("Merchant Param 3 in system is " +
                                 * decreptedParameter.get(MainetConstants.REQUIRED_PG_PARAM.MERCHANT_PER3) +
                                 * " while received from PG is " +
                                 * decreptedMap.get(MainetConstants.REQUIRED_PG_PARAM.MERCHANT_PER3)); } if
                                 * (!decreptedParameter.get(MainetConstants.REQUIRED_PG_PARAM.MERCHANT_PER5)
                                 * .equals(decreptedMap.get(MainetConstants.REQUIRED_PG_PARAM.MERCHANT_PER5))) { failureCount =
                                 * failureCount + 1; errorString.append("Merchant Param 5 in system is " +
                                 * decreptedParameter.get(MainetConstants.REQUIRED_PG_PARAM.MERCHANT_PER5) +
                                 * " while received from PG is " +
                                 * decreptedMap.get(MainetConstants.REQUIRED_PG_PARAM.MERCHANT_PER5)); }
                                 */

                                if (failureCount > 0) {
                                    paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS,
                                            MainetConstants.PAYU_STATUS.FAIL);
                                    paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.ERR_STR,
                                            errorString.toString());
                                } else {
                                    if (decreptedMap.get(MainetConstants.REQUIRED_PG_PARAM.ORDER_STATUS)
                                            .equalsIgnoreCase(MainetConstants.PAYU_STATUS.SUCCESS)) {
                                        paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS,
                                                MainetConstants.PAYU_STATUS.SUCCESS);
                                        /*
                                         * errorString.append(decreptedMap .get(MainetConstants.REQUIRED_PG_PARAM.RESPONSE_CODE) +
                                         * " : " + decreptedMap.get(MainetConstants.REQUIRED_PG_PARAM.FAILURE_MESSAGE));
                                         */

                                    } else if (decreptedMap.get(MainetConstants.REQUIRED_PG_PARAM.ORDER_STATUS)
                                            .equalsIgnoreCase(MainetConstants.PAYU_STATUS.ABORTED)) {
                                        paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS,
                                                MainetConstants.PAYU_STATUS.ABORTED);
                                        errorString.append(
                                                "Payment is cancelled by user at ICICI Payment Gateway Page");
                                    } else {
                                        paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS,
                                                MainetConstants.PAYU_STATUS.FAIL);
                                        errorString.append(decreptedMap
                                                .get(MainetConstants.REQUIRED_PG_PARAM.RESPONSE_CODE) + " : "
                                                + decreptedMap.get(MainetConstants.REQUIRED_PG_PARAM.FAILURE_MESSAGE));
                                    }
                                }
                                paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.ERR_STR,
                                        errorString.toString());
                                paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.TRACK_ID,
                                        decreptedMap.get(MainetConstants.REQUIRED_PG_PARAM.TRACKING_ID));

                                paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.TRANS_ID,
                                        decreptedMap.get(MainetConstants.REQUIRED_PG_PARAM.TRACKING_ID));

                                paymentResponseMap.put(MainetConstants.BankParam.TXN_ID,
                                        decreptedMap.get(MainetConstants.REQUIRED_PG_PARAM.ORDER_ID));

                                paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.PAYDATE, currentDate);

                                paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.SUCEESS_MSG,
                                        decreptedMap.get(MainetConstants.REQUIRED_PG_PARAM.STATUS_MESSAGE));

                                paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.BNK_REF_NO,
                                        decreptedMap.get(MainetConstants.REQUIRED_PG_PARAM.BANK_REF_NO));

                                paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.AMT, sessionAmount);

                                paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.PAY_SRC,
                                        MainetConstants.PG_SHORTNAME.ICICI);

                                paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.MODE,
                                        decreptedMap.get(MainetConstants.REQUIRED_PG_PARAM.PAYMENT_MODE));

                                paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.NET_AMT, sessionAmount);

                                paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.UDF1,
                                        decreptedMap.get(MainetConstants.REQUIRED_PG_PARAM.MERCHANT_PER1));

                                paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.UDF2,
                                        decreptedMap.get(MainetConstants.REQUIRED_PG_PARAM.MERCHANT_PER2));

                                paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.UDF3,
                                        decreptedMap.get(MainetConstants.REQUIRED_PG_PARAM.MERCHANT_PER3));

                                paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.UDF5,
                                        decreptedMap.get(MainetConstants.REQUIRED_PG_PARAM.MERCHANT_PER5));

                                paymentResponseMap.put(MainetConstants.BankParam.HASH,
                                        MainetConstants.REQUIRED_PG_PARAM.NA);

                                paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.LANG,
                                        responseMap.get(MainetConstants.REQUIRED_PG_PARAM.LANG));

                                paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.PG_ID,
                                        responseMap.get(MainetConstants.REQUIRED_PG_PARAM.PG_ID));

                                paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.ORDER_ID,
                                        responseMap.get(MainetConstants.REQUIRED_PG_PARAM.ORDER_ID));
                            } else {
                                paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS,
                                        MainetConstants.PAYU_STATUS.FAIL);
                                LOG.error("Session Parameters mismatched with Payment Gatway Parameters");
                                throw new FrameworkException(
                                        "Session Paratemeters mismatched with Payment Gatway Parameters");
                            }

                        } catch (Exception exp) {
                            paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS,
                                    MainetConstants.PAYU_STATUS.FAIL);
                            paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.ERR_STR,
                                    "Erro While decrypting the encrypted response");
                            paymentResponseMap.put(MainetConstants.BankParam.HASH,
                                    MainetConstants.REQUIRED_PG_PARAM.NA);
                            paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.MODE,
                                    MainetConstants.REQUIRED_PG_PARAM.NA);
                        }

                    } else {
                        paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS,
                                MainetConstants.PAYU_STATUS.FAIL);
                        LOG.error("ICICI response does not contains status field");
                        throw new FrameworkException("ICICI response does not contains status field");
                    }
                } else {
                    LOG.error("ICICI does not contains specified response key-value pair");
                    throw new FrameworkException("ICICI does not contains specified response key-value pair");
                }
            } else {
                LOG.error("Working key is not available for ICICI response");
                throw new FrameworkException("Working key is not available for ICICI response");
            }
        } else {
            LOG.error("Response message are not convert into string");
            paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.ERR_STR, "CheckSum not Match");
            throw new FrameworkException("Response message are not correct send by ICICI");
        }
        responseMap.clear();
        responseMap.putAll(paymentResponseMap);
        return responseMap;
    }

    private String generateEncryptedValue(final String workingKey, final LinkedHashMap<String, String> map) {
        String pname = "", pvalue = "";
        StringBuilder ccaRequest = new StringBuilder();
        for (Entry<String, String> requestParameters : map.entrySet()) {
            pname = requestParameters.getKey();
            ccaRequest.append(pname + MainetConstants.REQUIRED_PG_PARAM.SEARCH_SPECIAL_CHAR_REPLACE);
            pvalue = requestParameters.getValue();
            ccaRequest.append(pvalue + MainetConstants.operator.AMPERSAND);
        }

        AesCryptUtil aesUtil = new AesCryptUtil(workingKey);
        return aesUtil.encrypt(ccaRequest.toString());
    }

    private String encryptedParameter(final LinkedHashMap<String, String> map) {
        String pname = "", pvalue = "";
        StringBuilder ccaRequest = new StringBuilder();
        for (Entry<String, String> requestParameters : map.entrySet()) {
            pname = requestParameters.getKey();
            if (MainetConstants.REQUIRED_PG_PARAM.ORDER_ID.equals(pname) || MainetConstants.REQUIRED_PG_PARAM.AMT.equals(pname)) {
                ccaRequest.append(pname + MainetConstants.REQUIRED_PG_PARAM.SEARCH_SPECIAL_CHAR_REPLACE);
                pvalue = requestParameters.getValue();
                ccaRequest.append(pvalue + MainetConstants.operator.AMPERSAND);
            }
        }
        String encryptedString = null;
        try {
            encryptedString = EncryptionAndDecryption.encrypt(ccaRequest.toString());
            Log.info("map form before sending data is " + map.toString() + " while encrypted request is "
                    + encryptedString);
        } catch (IllegalBlockSizeException exp) {
            LOG.error("Error while creating the encrypted request for Payment Request DTO");
        }
        return encryptedString;
    }

    private Map<String, String> decrypteParameter(final String response) {
        String decResp = null;
        try {
            decResp = EncryptionAndDecryption.decrypt(response);
            Log.info(" decrypted request is " + decResp);
        } catch (IllegalBlockSizeException exp) {
            LOG.error("Error while creating the decrypted response from ICICI PG");
            throw new FrameworkException("Error while creating the decrypted response from ICICI PG", exp);
        }

        StringTokenizer tokenizer = new StringTokenizer(decResp, MainetConstants.operator.AMPERSAND);
        final Map<String, String> map = new HashMap<>(0);
        String pair = "", pname = "", pvalue = "";

        while (tokenizer.hasMoreTokens()) {
            pair = tokenizer.nextToken();
            if (pair != null) {
                StringTokenizer strTok = new StringTokenizer(pair,
                        MainetConstants.REQUIRED_PG_PARAM.SEARCH_SPECIAL_CHAR_REPLACE);
                if (strTok.hasMoreTokens()) {
                    pname = strTok.nextToken();
                    if (strTok.hasMoreTokens())
                        pvalue = strTok.nextToken();
                    map.put(pname, pvalue);
                }
            }
        }
        Log.info(" decrypted map is " + map.toString());
        return map;
    }

    private Map<String, String> generateDecryptedValue(final String workingKey, final String response) {

        AesCryptUtil aesUtil = new AesCryptUtil(workingKey);
        String decResp = aesUtil.decrypt(response);

        StringTokenizer tokenizer = new StringTokenizer(decResp, MainetConstants.operator.AMPERSAND);
        final Map<String, String> map = new HashMap<>(0);
        String pair = "", pname = "", pvalue = "";

        while (tokenizer.hasMoreTokens()) {
            pair = tokenizer.nextToken();
            if (pair != null) {
                StringTokenizer strTok = new StringTokenizer(pair,
                        MainetConstants.REQUIRED_PG_PARAM.SEARCH_SPECIAL_CHAR_REPLACE);
                if (strTok.hasMoreTokens()) {
                    pname = strTok.nextToken();
                    if (strTok.hasMoreTokens())
                        pvalue = strTok.nextToken();
                    map.put(pname, pvalue);
                }
            }
        }
        LOG.info("generateDecryptedValue is " + map.toString());
        return map;
    }

}
