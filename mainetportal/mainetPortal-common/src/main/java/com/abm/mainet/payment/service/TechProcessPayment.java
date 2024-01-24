package com.abm.mainet.payment.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.payment.dto.PaymentRequestDTO;
import com.tp.pg.util.TransactionRequestBean;
import com.tp.pg.util.TransactionResponseBean;

public class TechProcessPayment implements PaymentStrategy {

    protected Logger LOG = Logger.getLogger(TechProcessPayment.class);

    @Override
    public PaymentRequestDTO generatePaymentRequest(final PaymentRequestDTO paymentRequestVO) throws FrameworkException {

        LOG.info("Start the genreatePaymentRequest() in TechProcessPayment class ");
        try {

            if ((paymentRequestVO.getMerchantId() != null) && (paymentRequestVO.getKey() != null)
                    && (paymentRequestVO.getSalt() != null) && (paymentRequestVO.getSchemeCode() != null)
                    && (paymentRequestVO.getPgUrl() != null)) {
                final UUID customerId = UUID.randomUUID();
                String ShoppingCartDetails = null;
                final TransactionRequestBean transactionRequestBean = new TransactionRequestBean();
                transactionRequestBean.setStrRequestType(paymentRequestVO.getRequestType());
                transactionRequestBean.setStrMerchantCode(paymentRequestVO.getMerchantId());
                transactionRequestBean.setMerchantTxnRefNumber(
                        MainetConstants.PG_REQUEST_PROPERTY.TXN + paymentRequestVO.getTxnid().toString());

                if ((paymentRequestVO.getProduction() != null)
                        && paymentRequestVO.getProduction().equalsIgnoreCase(MainetConstants.PAYMENT.OFFLINE)) {
                    transactionRequestBean.setStrAmount(MainetConstants.GrievanceConstants.PROCESS_VERSION);
                    transactionRequestBean.setStrBankCode(MainetConstants.NUMB470);
                    ShoppingCartDetails = paymentRequestVO.getSchemeCode().concat(MainetConstants.operator.UNDER_SCORE)
                            .concat(MainetConstants.GrievanceConstants.PROCESS_VERSION).concat(MainetConstants.ZERO_0);
                } else {
                    transactionRequestBean.setStrAmount(paymentRequestVO.getDueAmt().toString());
                    ShoppingCartDetails = paymentRequestVO.getSchemeCode().concat(MainetConstants.operator.UNDER_SCORE)
                            .concat(paymentRequestVO.getDueAmt().toString()).concat(MainetConstants.ZERO_0);
                    transactionRequestBean.setStrBankCode(MainetConstants.BLANK);
                }
                transactionRequestBean.setStrCurrencyCode(MainetConstants.PG_REQUEST_PROPERTY.CRN);
                transactionRequestBean.setStrITC(paymentRequestVO.getTxnid() + MainetConstants.operator.TILDE
                        + paymentRequestVO.getServiceId() + MainetConstants.operator.TILDE
                        + paymentRequestVO.getApplicationId() + MainetConstants.operator.TILDE + paymentRequestVO.getUdf3()
                        + MainetConstants.operator.TILDE
                        + paymentRequestVO.getUdf5() + MainetConstants.operator.TILDE + paymentRequestVO.getUdf6()
                        + MainetConstants.operator.TILDE
                        + paymentRequestVO.getUdf7());
                transactionRequestBean.setStrReturnURL(paymentRequestVO.getSuccessUrl());
                transactionRequestBean.setStrShoppingCartDetails(ShoppingCartDetails);
                transactionRequestBean.setTxnDate(getCurrentDate());
                transactionRequestBean.setWebServiceLocator(paymentRequestVO.getPgUrl());
                transactionRequestBean.setCustID(customerId.toString());
                transactionRequestBean.setStrTPSLTxnID(MainetConstants.BLANK);
                transactionRequestBean.setStrMobileNumber(paymentRequestVO.getMobNo());
                transactionRequestBean.setKey(paymentRequestVO.getKey().getBytes());
                transactionRequestBean.setIv(paymentRequestVO.getSalt().getBytes());
                transactionRequestBean.setStrCustomerName(paymentRequestVO.getApplicantName());
                if ((paymentRequestVO.getEmail() != null) && !paymentRequestVO.getEmail().isEmpty()) {
                    transactionRequestBean.setStrEmail(paymentRequestVO.getEmail());
                }
                transactionRequestBean.setStrTimeOut(MainetConstants.PG_REQUEST_PROPERTY.SERVERTIMEOUT);
                final String tokenNo = transactionRequestBean.getTransactionToken();
                paymentRequestVO.setPayRequestMsg(tokenNo);
                LOG.info("Single Token request String" + tokenNo);
                LOG.info("End  the genreatePaymentRequest() in TechProcessPayment class ");
            } else {
                LOG.error("Payment Required parameter are not fetch from tables");
                throw new FrameworkException("Payment Required parameter are not fetch from tables");
            }
        } catch (final Exception exception) {
            LOG.error(" Exception occur in genreatePaymentRequest in TechProcessPayment", exception);
            throw new FrameworkException("Exception occur in genreatePaymentRequest ...", exception);
        }
        return paymentRequestVO;
    }

    @Override
    public Map<String, String> generatePaymentResponse(final String responseString, final Map<String, String> responseMap)
            throws FrameworkException {

        if ((responseMap.get(MainetConstants.REQUIRED_PG_PARAM.KEY) != null)
                && (responseMap.get(MainetConstants.REQUIRED_PG_PARAM.IV) != null) && (responseString != null)) {

            LOG.info("Response message from the techprocess Payment in HashCode  " + responseString);
            final String key = responseMap.get(MainetConstants.REQUIRED_PG_PARAM.KEY);
            final String iv = responseMap.get(MainetConstants.REQUIRED_PG_PARAM.IV);
            final TransactionResponseBean responseBean = new TransactionResponseBean();
            responseBean.setIv(iv.getBytes());
            responseBean.setKey(key.getBytes());
            responseBean.setResponsePayload(responseString);
            final String responseMsg = responseBean.getResponsePayload();
            final List<String> strings = Arrays.asList(responseMsg.split("\\|"));
            LOG.info("Response message after convert into string " + strings + " response String  Size" + strings.size());
            if (strings.size() > 0) {

                if (strings.get(0).substring(11).equals(MainetConstants.PG_REQUEST_PROPERTY.PAYMNETSTATUS)) {
                    responseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS, MainetConstants.PAYU_STATUS.SUCCESS);
                    responseMap.put(MainetConstants.REQUIRED_PG_PARAM.FIELD9, MainetConstants.PAYU_STATUS.SUCCESS.toUpperCase());
                } else {
                    responseMap.put(MainetConstants.REQUIRED_PG_PARAM.FIELD9, MainetConstants.PAYU_STATUS.FAIL);
                    responseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS, MainetConstants.PAYU_STATUS.FAIL);
                }
                responseMap.put(MainetConstants.REQUIRED_PG_PARAM.SUCEESS_MSG, strings.get(1).substring(8));
                responseMap.put(MainetConstants.REQUIRED_PG_PARAM.ERR_STR, strings.get(2).substring(12));
                responseMap.put(MainetConstants.REQUIRED_PG_PARAM.BNK_REF_NO, strings.get(3).substring(13));
                responseMap.put(MainetConstants.REQUIRED_PG_PARAM.BANKID, strings.get(4).substring(13));
                responseMap.put(MainetConstants.REQUIRED_PG_PARAM.TRANS_ID, strings.get(5).substring(12));
                responseMap.put(MainetConstants.REQUIRED_PG_PARAM.AMT, strings.get(6).substring(8));
                responseMap.put(MainetConstants.REQUIRED_PG_PARAM.DUE_AMT, strings.get(6).substring(8));
                responseMap.put(MainetConstants.REQUIRED_PG_PARAM.NET_AMT, strings.get(6).substring(8));

                if (!strings.get(7).substring(15).isEmpty() && !strings.get(7).substring(15).equalsIgnoreCase("NA")) {

                    final String[] param = strings.get(7).substring(15)
                            .split(MainetConstants.SQUARE_CURLY_BRACKET + MainetConstants.SQUARE_CURLY_BRACKET);

                    if (param.length == 4) {

                        final String itc = param[0].substring(5);
                        final String email = param[1].substring(6);
                        final String mob = param[2].substring(4);
                        final String name = param[3].substring(9).replace(MainetConstants.LEFT_CURLYBRACET,
                                MainetConstants.BLANK);
                        responseMap.put(MainetConstants.BankParam.EMAIL, email);
                        responseMap.put(MainetConstants.BankParam.FNAME, name);
                        responseMap.put(MainetConstants.BankParam.PHONE, mob);
                        final String strArray[] = itc.split(MainetConstants.REGEX);
                        responseMap.put(MainetConstants.REQUIRED_PG_PARAM.TRACK_ID, strArray[0]);
                        responseMap.put(MainetConstants.BankParam.UDF1, strArray[1]);
                        responseMap.put(MainetConstants.BankParam.UDF2, strArray[2]);
                        responseMap.put(MainetConstants.BankParam.UDF3, strArray[3]);
                        responseMap.put(MainetConstants.BankParam.UDF5, strArray[4]);
                        responseMap.put(MainetConstants.BankParam.UDF6, strArray[5]);
                        responseMap.put(MainetConstants.BankParam.UDF7, strArray[6]);
                    } else {
                        final String itc = param[0].substring(5);
                        final String mob = param[1].substring(4);
                        final String name = param[2].substring(9).replace(MainetConstants.LEFT_CURLYBRACET,
                                MainetConstants.BLANK);
                        responseMap.put(MainetConstants.BankParam.FNAME, name);
                        responseMap.put(MainetConstants.BankParam.PHONE, mob);
                        final String strArray[] = itc.split(MainetConstants.REGEX);
                        responseMap.put(MainetConstants.REQUIRED_PG_PARAM.TRACK_ID, strArray[0]);
                        responseMap.put(MainetConstants.BankParam.UDF1, strArray[1]);
                        responseMap.put(MainetConstants.BankParam.UDF2, strArray[2]);
                        responseMap.put(MainetConstants.BankParam.UDF3, strArray[3]);
                        responseMap.put(MainetConstants.BankParam.UDF5, strArray[4]);
                        responseMap.put(MainetConstants.BankParam.UDF6, strArray[5]);
                        responseMap.put(MainetConstants.BankParam.UDF7, strArray[6]);

                    }
                }

                else {
                    responseMap.put(MainetConstants.REQUIRED_PG_PARAM.ERR_STR, "Failed from fetch the data from payment gateway");
                }
                responseMap.put(MainetConstants.REQUIRED_PG_PARAM.PAY_SRC, MainetConstants.PG_SHORTNAME.TECH_PROCESS);
                responseMap.put(MainetConstants.REQUIRED_PG_PARAM.PAYDATE, strings.get(8).substring(14));
                responseMap.put(MainetConstants.REQUIRED_PG_PARAM.BNK_REF_NO, strings.get(9).substring(13));
                responseMap.put(MainetConstants.BankParam.HASH, strings.get(12).substring(5));
            } else {
                LOG.error("Payment Required parameter are not fetch from tables");
                responseMap.put(MainetConstants.REQUIRED_PG_PARAM.ERR_STR, "CheckSum not Match");
                throw new FrameworkException("additional  data are not fetch in response send by techprocess");
            }
        } else {
            LOG.error("Response message are not convert into string");
            responseMap.put(MainetConstants.REQUIRED_PG_PARAM.ERR_STR, "CheckSum not Match");
            throw new FrameworkException("Response message are not match send by techprocess");
        }

        return responseMap;
    }

    public String getCurrentDate() {

        final DateFormat inputformat = new SimpleDateFormat(MainetConstants.DATEFORMAT_DDMMYYYY);
        final String currentDate = inputformat.format(new Date());
        return currentDate;
    }

}
