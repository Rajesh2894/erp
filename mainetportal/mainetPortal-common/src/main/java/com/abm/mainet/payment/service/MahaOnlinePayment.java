package com.abm.mainet.payment.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.payment.dto.PaymentRequestDTO;

public class MahaOnlinePayment implements PaymentStrategy {

    protected Logger LOG = Logger.getLogger(MahaOnlinePayment.class);

    @Override
    public PaymentRequestDTO generatePaymentRequest(final PaymentRequestDTO paymentRequestVO) throws FrameworkException {

        try {

            if ((paymentRequestVO.getMerchantId() != null) && (paymentRequestVO.getKey() != null)
                    && (paymentRequestVO.getSalt() != null) && (paymentRequestVO.getPgUrl() != null)) {
                if (paymentRequestVO.getEmail() != null) {
                    paymentRequestVO.getEmail();
                } else {
                    paymentRequestVO.setEmail(MainetConstants.REQUIRED_PG_PARAM.NA);
                }
                paymentRequestVO.setApplicantName(
                        paymentRequestVO.getApplicantName().replaceAll("[\\-\\+\\.\\^:,]", MainetConstants.BLANK));

                final String reqString = "SID=" + paymentRequestVO.getMerchantId() + "&ID=" + paymentRequestVO.getKey()
                        + "&Password=" + paymentRequestVO.getSalt() + "&AppTranID=" + paymentRequestVO.getTxnid() + "&Amt="
                        + paymentRequestVO.getDueAmt() + MainetConstants.BLANK
                        + "&IsReceipt=False&Email=" + paymentRequestVO.getEmail() + "&Mobile=" + paymentRequestVO.getMobNo()
                        + "&Udf1=" + paymentRequestVO.getTxnid() + "&Udf2=" + paymentRequestVO.getMobNo() + "&Udf3="
                        + paymentRequestVO.getUdf1() + "&Udf4=" + paymentRequestVO.getApplicationId() + "&Udf5="
                        + paymentRequestVO.getUdf5() + "&RU=" + paymentRequestVO.getSuccessUrl() + "&Name="
                        + paymentRequestVO.getApplicantName() + "&";

                final URL url = new URL(paymentRequestVO.getPgUrl());
                final HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(100000);
                con.setRequestMethod("POST");
                con.setDoInput(true);
                con.setDoOutput(true);
                con.setUseCaches(false);
                con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
                final DataOutputStream out = new DataOutputStream(con.getOutputStream());
                out.writeBytes(reqString);
                out.flush();
                out.close();

                final BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                final StringBuffer stringBuffer = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    stringBuffer.append(inputLine);
                }
                in.close();
                final String molResponse = stringBuffer.toString();

                LOG.info("molResponse " + molResponse.toString());

                final String[] splitStrValue = molResponse.split(MainetConstants.operator.COLON);
                final String fValue = "payreq=" + splitStrValue[0];
                final String sValue = splitStrValue[1];
                final String tValue = splitStrValue[2];
                final String completeUrl = sValue + MainetConstants.operator.COLON + tValue;
                LOG.info("Complete url ::" + completeUrl);
                LOG.info("payreq value ::" + fValue);
                final String reqStrMsg = completeUrl + MainetConstants.QUARTZ_SCHEDULE.CRON_EXPR.QUESTION_MARK + fValue;
                LOG.info("Complete payment Request ::" + reqStrMsg);
                if (reqStrMsg != null) {
                    paymentRequestVO.setPayRequestMsg(reqStrMsg);
                    paymentRequestVO.setHash(splitStrValue[0].toString());
                }

                paymentRequestVO.setPgName(MainetConstants.PG_SHORTNAME.MAHA_ONLINE);
            } else {
                LOG.error("Payment Required parameter are not fetch from tables");
                throw new FrameworkException("Payment Required parameter are not fetch from tables");
            }
        } catch (final Exception exception) {
            throw new FrameworkException("Exception occur in generatePaymentRequest in MahaOnlinePayment ...", exception);
        }
        return paymentRequestVO;
    }

    @Override
    public Map<String, String> generatePaymentResponse(final String responseString, final Map<String, String> responseMap)
            throws FrameworkException {

        if (responseString != null) {
            LOG.info("Response string from the mahaonline " + responseString);
            final List<String> resString = Arrays.asList(responseString.split("\\|"));
            LOG.info("Response string size " + resString.size());
            final DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            final Date date = new Date();
            final String currentDate = dateFormat.format(date);

            if (resString.size() > 0) {
                responseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS, resString.get(0).toLowerCase());
                responseMap.put(MainetConstants.REQUIRED_PG_PARAM.FIELD9, resString.get(0).toUpperCase());
                responseMap.put(MainetConstants.REQUIRED_PG_PARAM.TRACK_ID, resString.get(1));
                responseMap.put(MainetConstants.REQUIRED_PG_PARAM.BANKTXNID, resString.get(2));
                responseMap.put(MainetConstants.REQUIRED_PG_PARAM.BNK_REF_NO, resString.get(3));
                responseMap.put(MainetConstants.REQUIRED_PG_PARAM.BANKID, resString.get(4));
                responseMap.put(MainetConstants.REQUIRED_PG_PARAM.AMT, resString.get(5));
                responseMap.put(MainetConstants.REQUIRED_PG_PARAM.TRACK_ID, resString.get(6));
                responseMap.put(MainetConstants.BankParam.PHONE, resString.get(7));
                responseMap.put(MainetConstants.BankParam.UDF1, resString.get(8));
                responseMap.put(MainetConstants.BankParam.UDF2, resString.get(9));
                responseMap.put(MainetConstants.BankParam.UDF5, resString.get(10));
                responseMap.put(MainetConstants.REQUIRED_PG_PARAM.TRANS_ID, resString.get(11));
                responseMap.put(MainetConstants.BankParam.FNAME, resString.get(12));
                responseMap.put(MainetConstants.REQUIRED_PG_PARAM.PAYDATE, currentDate);
                responseMap.put(MainetConstants.REQUIRED_PG_PARAM.NET_AMT, resString.get(13));
                responseMap.put(MainetConstants.REQUIRED_PG_PARAM.DUE_AMT, resString.get(13));
                responseMap.put(MainetConstants.REQUIRED_PG_PARAM.ERR_STR, resString.get(15));
                responseMap.put(MainetConstants.REQUIRED_PG_PARAM.PAY_SRC, MainetConstants.PG_SHORTNAME.MAHA_ONLINE);

            }

            return responseMap;

        } else {
            throw new FrameworkException("Response string are not correct MahaOnlinePayment");
        }
    }
}
