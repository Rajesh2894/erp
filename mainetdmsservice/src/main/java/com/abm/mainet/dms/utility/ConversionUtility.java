package com.abm.mainet.dms.utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.abm.mainet.constant.MainetConstants;

/**
 * Type Conversion Utility
 * 
 * @author sanket.joshi
 *
 */
public class ConversionUtility {

	 @SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(ConversionUtility.class);
	 

    /**
     * @author harshit.kumar To convert current date into string.
     * @return string value of current date object.
     */
    public static String dateToString(final Date date, final String dateFormat) {
        String strDate = MainetConstants.Common.BLANK;
        try {
            DateFormat formatter;
            formatter = new SimpleDateFormat(dateFormat);
            if (date != null) {
                strDate = formatter.format(date);
            }
        } catch (final Exception e) {
        }
        return strDate;
    }

    /**
     * @author harshit.kumar To convert string to date.
     * @return string value of current date object.
     */
    public static Date stringToDate(final String dateString, final String dateFormat) {
        Date date = null;
        DateFormat formatter = null;
        try {
            formatter = new SimpleDateFormat(dateFormat);
            if (dateString != null) {
                date = formatter.parse(dateString);
            }
        } catch (final Exception e) {
            return null;
        }
        return date;
    }


}
