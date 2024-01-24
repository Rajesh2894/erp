package com.abm.mainet.common.utility;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abm.mainet.common.constant.MainetConstants;

/**
 *
 * @author Vivek.Kumar
 * @since 12-Jan-2016
 */
public final class CommonUtility {

    /**
     * Logger to log error if something goes wrong
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtility.class);

    private static final String ENC_UTF_8 = "UTF-8";

    /**
     * restrict object instantiation for the class
     */
    private CommonUtility() {
    };

    /**
     * use this method to encode field data,form parameters or URL in order to prevent all the vulnerability attacks. also can be
     * used for i18(local language) messages to make guarantee for the correct send-receive from server to client.
     *
     * @param field :String to be encode
     * @return an encoded String
     */
    public static String encodeField(final String field) {
        String encodedField = null;
        try {
            encodedField = URLEncoder.encode(field, ENC_UTF_8);
        } catch (final UnsupportedEncodingException ex) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("Error during encoding (" + field + "):", ex);
            }
        }

        return encodedField;
    }

    /**
     * use this method to decode field data,form parameters or URL in order to prevent all the vulnerability attacks. also can be
     * used for i18(local language) messages to make guarantee for the correct send-receive from server to client.
     *
     * @param field :String to be decode
     * @return an decoded String
     */
    public static String decodeField(final String field) {
        String decodedField = null;
        try {
            decodedField = URLDecoder.decode(field, ENC_UTF_8);
        } catch (final UnsupportedEncodingException ex) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("Error during decoding (" + field + "):", ex);
            }
        }

        return decodedField;
    }

    /**
     * @author Pranit.Mhatre To convert current date into string.
     * @return string value of current date object.
     */
    public static String dateToString(final Date date) {

        String strDate = MainetConstants.BLANK;

        try {
            DateFormat formatter;
            formatter = new SimpleDateFormat(MainetConstants.DATE_FORMAT);
            if (date != null) {
                strDate = formatter.format(date);
            }
        } catch (final Exception e) {
        }

        return strDate;
    }

}
