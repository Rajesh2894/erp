package com.abm.mainet.common.utility;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.abm.mainet.common.constant.MainetConstants;

/**
 * @author Bhavesh.Gadhe
 */
public class UtilityService {

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final String NUMERIC_STRING = "9874563210";
    private static final String BLANK = "";
    private static final String INPUTDATEMESSAGE = "Entered Date is Null or Blank";

    public static String generateRandomNumericCode(int count) {
        final StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            final int character = (int) (Math.random() * NUMERIC_STRING.length());
            builder.append(NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    public static String generateRandomAlphaNumericCode(int count) {
        final StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            final int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    /**
     * @Method to check OTP[One Time Password] validity.
     * @param validFrom is {@link Date} onDate/updated date.
     * @param validUpToMinutes
     * @param dateToCompare {@link Date} date to compare with @param validFrom
     * @return true if valid
     */
    public static boolean checkOTPValidityPeriod(final Date validFrom, final int validUpToMinutes, final Date dateToCompare) {
        try {

            final Calendar fromDateCal = Calendar.getInstance();
            fromDateCal.setTime(validFrom);

            final Calendar toDateCal = Calendar.getInstance();
            toDateCal.setTime(validFrom);
            toDateCal.add(Calendar.MINUTE, validUpToMinutes);

            final Calendar dateToCompareCal = Calendar.getInstance();
            dateToCompareCal.setTime(dateToCompare);

            if ((dateToCompareCal.after(fromDateCal) && dateToCompareCal.before(toDateCal))
                    || dateToCompareCal.equals(fromDateCal)
                    || dateToCompareCal.equals(toDateCal)) {
                return true;
            }
        } catch (final Exception e) {
            return false;
        }
        return false;
    }

    public static void main(final String[] args) {

    }

    /**
     *
     * @param inputDate
     * @return {@value date in String format} Use this method to convert the Date into String format
     *
     */
    public static String convertDateToDDMMYYYY(final Date inputDate) {

        String convertedDate = INPUTDATEMESSAGE;

        if ((inputDate != null) && !inputDate.equals(BLANK)) {
            final Format formatter = new SimpleDateFormat(MainetConstants.DATE_FORMAT);
            convertedDate = formatter.format(inputDate);
        }

        return convertedDate;
    }

    /**
     *
     * @param inputDate
     * @return Use this method to convert the String format dd-MMM-yyyy into Date
     */
    public static Date convertStringToDDMMMYYYYDate(final String inputDate) {

        final DateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = fmt.parse(inputDate);
        } catch (final ParseException e) {
        }

        return date;
    }

    /**
     *
     * @param inputDate
     * @return Date object Use this method when you need to convert String Date("21/09/2014") exactly as Date object
     */
    public static Date convertStringDateToDateFormat(final String inputDate) {

        Date date = null;
        final DateFormat formatter = new SimpleDateFormat(MainetConstants.DATE_FORMAT);
        try {

            date = formatter.parse(inputDate);
        } catch (final ParseException e) {

        }

        return date;
    }

    /**
     *
     * @param utilDate
     * @return {@link java.sql.Date} Use this method to convert java.util.Date to java.sql.Date
     */
    public static java.sql.Date getSQLDate(final Date utilDate) {

        java.sql.Date sqlDate = null;
        if (utilDate != null) {
            sqlDate = new java.sql.Date(utilDate.getTime());
        }

        return sqlDate;
    }

    public static Calendar convertDateToGregorianCalendar(final java.util.Date date) {

        final Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        return calendar;

    }

    public static Date convertCalendarToUtilDate(final Calendar calendar) {

        return calendar.getTime();
    }

    /**
     *
     * @param utilDate : pass either java.util.Date instance
     * @param stringDate : or pass String format date like {16/05/2015}
     * @return Name of the Day like {MON,TUE,THU,FRI,SAT,SUN}
     */
    public static String getNameOfDayFromUtilDateOrStringDate(final java.util.Date utilDate, final String stringDate) {

        String nameOfDay = null;
        Calendar calendar = null;
        java.util.Date date = null;
        int day = 0;

        if (utilDate != null) {
            calendar = Calendar.getInstance();
            calendar.setTime(utilDate);
            day = calendar.get(Calendar.DAY_OF_WEEK);
        } else if ((stringDate != null) && !stringDate.trim().equals(MainetConstants.BLANK)) {

            date = UtilityService.convertStringDateToDateFormat(stringDate);
            calendar = Calendar.getInstance();
            calendar.setTime(date);
            day = calendar.get(Calendar.DAY_OF_WEEK);

        }

        switch (day) {

        case MainetConstants.NUMBERS.ONE:
            nameOfDay = MainetConstants.Common_Constant.WEEK.SUN;
            break;

        case MainetConstants.NUMBERS.TWO:
            nameOfDay = MainetConstants.Common_Constant.WEEK.MON;
            break;

        case MainetConstants.NUMBERS.THREE:
            nameOfDay = MainetConstants.Common_Constant.WEEK.TUE;
            break;

        case MainetConstants.NUMBERS.FOUR:
            nameOfDay = MainetConstants.Common_Constant.WEEK.WED;
            break;

        case MainetConstants.NUMBERS.FIVE:
            nameOfDay = MainetConstants.Common_Constant.WEEK.THU;
            break;

        case MainetConstants.NUMBERS.SIX:
            nameOfDay = MainetConstants.Common_Constant.WEEK.FRI;
            break;

        case MainetConstants.NUMBERS.SEVEN:
            nameOfDay = MainetConstants.Common_Constant.WEEK.SAT;
            break;

        default:

            break;
        }

        return nameOfDay;
    }

    /**
     * use this method to get month name, pass month like {01,02,03...}
     * @param month
     * @return Name of Month like: [JAN,FEB,MAR...]
     */
    public static String getNameOfMonthFromNumericMonth(final String month) {

        String nameOfMonth = null;

        if (month != null) {

            switch (month) {

            case MainetConstants.Common_Constant.NUMBER.ZERO_ONE:
                nameOfMonth = MainetConstants.Common_Constant.MONTH.JAN;
                break;

            case MainetConstants.Common_Constant.NUMBER.ZERO_TWO:
                nameOfMonth = MainetConstants.Common_Constant.MONTH.FEB;
                break;

            case MainetConstants.Common_Constant.NUMBER.ZERO_THREE:
                nameOfMonth = MainetConstants.Common_Constant.MONTH.MAR;
                break;

            case MainetConstants.Common_Constant.NUMBER.ZERO_FOUR:
                nameOfMonth = MainetConstants.Common_Constant.MONTH.APR;
                break;

            case MainetConstants.Common_Constant.NUMBER.ZERO_FIVE:
                nameOfMonth = MainetConstants.Common_Constant.MONTH.MAY;
                break;

            case MainetConstants.Common_Constant.NUMBER.ZERO_SIX:
                nameOfMonth = MainetConstants.Common_Constant.MONTH.JUN;
                break;

            case MainetConstants.Common_Constant.NUMBER.ZERO_SEVEN:
                nameOfMonth = MainetConstants.Common_Constant.MONTH.JUL;
                break;

            case MainetConstants.Common_Constant.NUMBER.ZERO_EIGHT:
                nameOfMonth = MainetConstants.Common_Constant.MONTH.AUG;
                break;

            case MainetConstants.Common_Constant.NUMBER.ZERO_NINE:
                nameOfMonth = MainetConstants.Common_Constant.MONTH.SEP;
                break;

            case MainetConstants.Common_Constant.NUMBER.TEN:
                nameOfMonth = MainetConstants.Common_Constant.MONTH.OCT;
                break;

            case MainetConstants.Common_Constant.NUMBER.ELEVEN:
                nameOfMonth = MainetConstants.Common_Constant.MONTH.NOV;
                break;

            case MainetConstants.Common_Constant.NUMBER.TWELVE:
                nameOfMonth = MainetConstants.Common_Constant.MONTH.DEC;
                break;

            default:
                break;
            }

        }
        return nameOfMonth;

    }

    /**
     * To compare two date
     * @param firstDate the first Date object to compare
     * @param secondDate the second Date object to compare
     * @return boolean
     */
    public static boolean compareDate(final Date firstDate, final Date secondDate) {

        final long firstTime = Utility.getTimeByDate(firstDate);
        final long secondTime = Utility.getTimeByDate(secondDate);

        return (secondTime >= firstTime) ? true : false;
    }

    /**
     * To compare two date
     * @param firstDate the first Date object to compare
     * @param secondDate the second Date object to compare
     * @return boolean
     */
    public static boolean compareDateField(final Date firstDate, final Date secondDate) {

        final long firstTime = Utility.getTimeByDate(firstDate);
        final long secondTime = Utility.getTimeByDate(secondDate);

        return (secondTime > firstTime) ? true : false;
    }

    
    
    public static boolean validateChequeDate(Date dodayDate, Date chequeDate) {
        boolean isValid = false;
        Calendar currentDateBefore3Months = Calendar.getInstance();
        currentDateBefore3Months.setTime(dodayDate);
        currentDateBefore3Months.add(Calendar.MONTH, -3);
        if (chequeDate.after(currentDateBefore3Months.getTime())) {
            isValid = true;
        }
        return isValid;
    }
}
