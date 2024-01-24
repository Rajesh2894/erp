package com.abm.mainet.payment.service;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.antlr.stringtemplate.StringTemplate;
import org.apache.log4j.Logger;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.payment.dto.PaymentRequestDTO;

/**
 * @author umashanker.kanaujiya
 *
 */

public class PayuPayment implements PaymentStrategy {
    protected Logger LOG = Logger.getLogger(PayuPayment.class);

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.payment.service.PaymentStrategy#generatePaymentRequest(com.abm.mainet.dto.PayURequestDTO)
     */
    @Override
    public PaymentRequestDTO generatePaymentRequest(final PaymentRequestDTO requestDTO) throws FrameworkException {
        try {
            requestDTO.setUdf4(requestDTO.getUdf6() + MainetConstants.operator.TILDE + requestDTO.getUdf7());
            requestDTO.setHash(generateHash(requestDTO));

        } catch (final Exception exception) {
            throw new FrameworkException("Exception occur in genreatePaymentRequest ...", exception);
        }
        return requestDTO;
    }

    private String generateHash(final PaymentRequestDTO requestDTO) throws FrameworkException {

        final String hashSequence = "$key$|$txnid$|$dueAmt$|$serviceName$|$applicantName$|$email$|$udf1$|$udf2$|$udf3$|$udf4$|$udf5$|$udf6$|$udf7$|$udf8$|$udf9$|$udf10$|";

        final StringTemplate template = new StringTemplate(hashSequence);

        String tempSequence = MainetConstants.BLANK;

        final Field fld[] = requestDTO.getClass().getDeclaredFields();

        for (final Field field : fld) {

            field.setAccessible(true);

            try {
                if (hashSequence.contains(field.getName()) && (field.get(requestDTO) != null)
                        && (field.get(requestDTO).toString().length() > 0)) {
                    template.setAttribute(field.getName(), field.get(requestDTO).toString());
                }
            } catch (IllegalArgumentException | IllegalAccessException | SecurityException e) {
                throw new FrameworkException(e);
            }
        }

        tempSequence = template.toString();

        if (requestDTO.getSalt() != null) {
            tempSequence = tempSequence.concat(requestDTO.getSalt());
        }

        return getDigest("SHA-512", tempSequence);
    }

    private String getDigest(final String type, final String str) {

        final byte[] hashseq = str.getBytes();
        final StringBuffer hexString = new StringBuffer();
        try {
            final MessageDigest algorithm = MessageDigest.getInstance(type);
            algorithm.reset();
            algorithm.update(hashseq);
            final byte messageDigest[] = algorithm.digest();

            for (final byte element : messageDigest) {
                final String hex = Integer.toHexString(0xFF & element);
                if (hex.length() == 1) {
                    hexString.append(MainetConstants.IsDeleted.ZERO);
                }
                hexString.append(hex);
            }
        } catch (final NoSuchAlgorithmException e) {
            throw new FrameworkException("Problem with generation of hash string!", e);
        }

        return hexString.toString();
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.payment.service.PaymentStrategy#generatePaymentResponse(java.lang.String, java.util.Map)
     */
    @Override
    public Map<String, String> generatePaymentResponse(final String responseString, final Map<String, String> responseMap)
            throws FrameworkException {

        if (responseMap.get(MainetConstants.REQUIRED_PG_PARAM.STATUS).equalsIgnoreCase(MainetConstants.PAYU_STATUS.SUCCESS)
                || responseMap.get(MainetConstants.REQUIRED_PG_PARAM.FIELD9)
                        .equalsIgnoreCase(MainetConstants.PAYU_STATUS.SUCCESS)) {
            responseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS, MainetConstants.PAYU_STATUS.SUCCESS);
            responseMap.put(MainetConstants.REQUIRED_PG_PARAM.FIELD9, MainetConstants.PAYU_STATUS.SUCCESS.toUpperCase());
        }
        final String[] str = responseMap.get(MainetConstants.BankParam.UDF4).toString().split("\\~");
        responseMap.put(MainetConstants.BankParam.UDF6, str[0]);
        responseMap.put(MainetConstants.BankParam.UDF7, str[1]);

        return responseMap;

    }

}
