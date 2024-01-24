package com.abm.mainet.common.util;

import static com.abm.mainet.common.constant.MainetConstants.DEFAULT_LANGUAGE_ID;
import static com.abm.mainet.common.constant.MainetConstants.DEFAULT_LOCALE_STRING;
import static com.abm.mainet.common.constant.MainetConstants.ERROR_OCCURED;
import static com.abm.mainet.common.constant.MainetConstants.FILE_PATH_SEPARATOR;
import static com.abm.mainet.common.constant.MainetConstants.LANGUAGE;
import static com.abm.mainet.common.constant.MainetConstants.MONTH;
import static com.abm.mainet.common.constant.MainetConstants.UTF8;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.DirectoryTree;
import com.abm.mainet.common.constant.MainetConstants.IsLookUp;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.IEntitlementService;
import com.abm.mainet.common.ui.model.Filepaths;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.dms.client.FileNetApplicationClient;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.lowagie.text.pdf.Barcode39;

/**
 * The Class Utility.
 */
public final class Utility {
    private static final String NUMBER_FORMAT_EXCEPTION = "NumberFormatException ";
    private static final String MH_0140 = "MH-0140/192.168.100.152/FC-AA-14-37-BE-6D";
    private static final Logger LOGGER = Logger.getLogger(Utility.class);
    private final static FileNetApplicationClient fileNetApplicationClient = FileNetApplicationClient.getInstance();
    private static ObjectMapper mapper = new ObjectMapper();


    /**
     * Password encryption.
     * @param userName = Login Name
     * @param password = Password for encryption
     * @return String encrypted password
     */
    public static String encryptPassword(String userName, final String password) {
        String encrypted = MainetConstants.BLANK;
        int passwordlength = 0;
        int loginnumber = 0;

        userName = userName.toUpperCase();
        final int lengthOfusername = userName.length();

        for (int i = 0; i <= (lengthOfusername - 2); i++) {
            loginnumber += getAssciFromChar(userName.charAt(i));
        }

        passwordlength = password.length();

        for (int i = 0; i <= (passwordlength - 1); i++) {
            int ascii = getAssciFromChar(password.charAt(i));
            ascii += (loginnumber / 256) + i;
            encrypted += getCharFromAssci(ascii);
        }

        return encrypted;
    }

    private static char getCharFromAssci(final int i) {
        return (char) i;
    }

    private static int getAssciFromChar(final char c) {
        return c;
    }

    private static InetAddress getIpAddress() {
        InetAddress ip = null;
        try {
            ip = InetAddress.getLocalHost();
        } catch (final UnknownHostException e) {
            LOGGER.error(ERROR_OCCURED, e);

        }
        return ip;

    }

    public static String getMacAddress() {
        final StringBuilder sb = new StringBuilder();
        try {
            final InetAddress ip = Utility.getIpAddress();

            final NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            final byte[] mac = network.getHardwareAddress();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i],
                        (i < (mac.length - 1)) ? MainetConstants.operator.HIPHEN : MainetConstants.BLANK));
            }
            return (ip.toString() + MainetConstants.operator.FORWARD_SLACE + sb.toString());
        } catch (final Exception e) {

            LOGGER.error("Cant Find Network interface:: ", e);
            sb.append(MH_0140);
        }
        return sb.toString();

    }

    public static int getDefaultLanguageId(final Organisation org) {
        final List<LookUp> lookUps = ApplicationSession.getInstance().getNonHierarchicalLookUp(org, LANGUAGE)
                .get(LANGUAGE);
        try {
			//Defect #25954
        	if(CollectionUtils.isNotEmpty(lookUps)) {
		        for (final LookUp lookUp : lookUps) {
		
		            if (lookUp.getDefaultVal().equals(IsLookUp.STATUS.YES)) {
		                return Integer.parseInt(lookUp.getLookUpCode());
		            }
		        }
        	}
        }
        catch(Exception e) {
        	 LOGGER.error(ERROR_OCCURED, e);
        	 return DEFAULT_LANGUAGE_ID;
        }
		return  DEFAULT_LANGUAGE_ID;
    }

    public static short getLanguageId(final String language) {
        if (MainetConstants.DEFAULT_LOCAL_REG_STRING.equals(language)) {
            return 2;
        } else if (MainetConstants.DEFAULT_LOCALE_STRING.equals(language)) {
            return 1;
        } else {
            return DEFAULT_LANGUAGE_ID;
        }
    }

    public static String getLocaleString(final int langId) {
        if (langId == 2) {
            return MainetConstants.DEFAULT_LOCAL_REG_STRING;
        } else if (langId == 1) {
            return MainetConstants.DEFAULT_LOCALE_STRING;
        } else {
            return DEFAULT_LOCALE_STRING;
        }
    }

    public String sha1(final String str) {

        try {
            final MessageDigest message = MessageDigest.getInstance("SHA-1");
            message.update(str.getBytes());
            final byte[] shaStr = message.digest();
            return bytesToHex(shaStr).toString();
        } catch (final Exception ex) {
            LOGGER.error(ERROR_OCCURED, ex);
            return null;
        }
    }

    private String bytesToHex(final byte[] b) {
        final char hexDigit[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        final StringBuffer buf = new StringBuffer();
        for (final byte element : b) {
            buf.append(hexDigit[(element >> 4) & 0x0f]);
            buf.append(hexDigit[element & 0x0f]);
        }
        return buf.toString();
    }

    public static Date convertStringToDate(final int month, final int date, final int year) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat(MainetConstants.FORMAT_mmddyyyy);
        final String in = month + MainetConstants.operator.FORWARD_SLACE + date + MainetConstants.operator.FORWARD_SLACE + year;
        try {
            final Date theDate = dateFormat.parse(in);
            return theDate;
        } catch (final Exception e) {
            LOGGER.error(ERROR_OCCURED, e);
            return null;
        }
    }

    /**
     * @author Pranit.Mhatre To check entered string is valid percentage or not?
     * @param percentage value to be check for valid percentage.
     * @return true if valid percentage value otherwise false.
     */
    public static boolean isPercentage(final String percentage) {
        final String regx = "(^100(\\.0{1,2})?$)|(^([1-9]([0-9])?|0)(\\.[0-9]{1,2})?$)";

        return percentage.matches(regx);
    }

    /**
     * @author Pranit.Mhatre To check for the valied commodity item code.
     * @param itemCode code value to be check for value code.
     * @return true if item code is valid otherwise false.
     */
    public static boolean isItemCode(final String itemCode) {
        final String regex = "(^[0-9 ]{2,12}$)";

        return itemCode.trim().matches(regex);
    }

    /**
     * @author Pranit.Mhatre To convert String date to actual Date object
     * @param strDate String date to be converted.
     * @return converted Date object
     */
    public static Date stringToDate(final String strDate) {

        Date date = null;
        DateFormat formatter = null;

        try {

            formatter = new SimpleDateFormat(MainetConstants.COMMON_DATE_FORMAT);
            if (strDate != null) {
                date = formatter.parse(strDate);
            }

        } catch (final Exception e) {
            LOGGER.error(ERROR_OCCURED, e);
            return null;
        }

        return date;

    }

    public static Date stringToDate(final String dateString, final String dateFormat) {
        Date date = null;
        DateFormat formatter = null;

        try {

            formatter = new SimpleDateFormat(dateFormat);
            if (dateString != null) {
                date = formatter.parse(dateString);
            }

        } catch (final Exception e) {
            LOGGER.error(ERROR_OCCURED, e);
            return null;
        }

        return date;
    }

    /**
     * @author Pranit.Mhatre To convert current date into string.
     * @return string value of current date object.
     */
    public static String dateToString(final Date date) {

        String strDate = new String();

        try {
            DateFormat formatter;
            formatter = new SimpleDateFormat(MainetConstants.COMMON_DATE_FORMAT);
            if (date != null) {
                strDate = formatter.format(date);
            }
        } catch (final Exception e) {
            LOGGER.error(ERROR_OCCURED, e);
        }

        return strDate;
    }

    public static String dateToString(Date date, String format) {

        String strDate = "";

        if (date == null || format == null || format.isEmpty())
            return strDate;
        try {
            DateFormat formatter = new SimpleDateFormat(format);
            strDate = formatter.format(date);
        } catch (Exception e) {
        }

        return strDate;
    }

    /**
     * To test whether entered string is valid digits or not ?
     * @param strNumber string to be tested.
     * @return true if string is valid digit otherwise false.
     */

    public static boolean isNumeric(final String strNumber) {
        final String regx = "(^[0-9])(.*)$";
        return strNumber.matches(regx);
    }

    /**
     * To test whether entered string is valid digits or not ? with given min max range
     * @param strNumber string to be tested.
     * @param min the minimum size
     * @param max the the maximum size
     * @return true if string is valid digit otherwise false.
     */
    public static boolean isNumeric(final String strNumber, final int min, final int max) {

        final String regx = "^([0-9]){" + min + MainetConstants.operator.COMA + max + "}$";
        return strNumber.matches(regx);
    }

    /**
     * To test whether entered string is valid characters or not ?
     * @param strCharacters string to be tested.
     * @return true if string is has valid characters otherwise false.
     */
    public static boolean hasCharacter(final String strCharacters) {

        final String regx = "^([a-zA-Z ])+$";
        return strCharacters.matches(regx);
    }

    /**
     * @author Pranit.Mhatre To convert given string date to date-1. eg. 07/12/2012 to 06/12/2012 in string
     * @param strDate string to converted
     * @return String in dd/mm/yyyy format.
     */
    public static String getBeforeDateByString(final String strDate) {

        return Utility.dateToString(Utility.getBeforeDateBy(strDate));
    }

    /**
     * @author Pranit.Mhatre To convert given date object to date-1. eg. 07/12/2012 to 06/12/2012 in date
     * @param strDate string to converted
     * @return String in dd/mm/yyyy format.
     */
    private static Date getBeforeDateBy(final String strDate) {

        final Date date = Utility.stringToDate(strDate);

        final Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);

        return calendar.getTime();
    }

    /**
     * @author Pranit.Mhatre To get year of the given date e.g. 2012
     * @param date the date object whose year to get
     * @return int the year of the date
     * @since 17 December, 2012
     */
    private static int getYearByDate(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * @author Pranit.Mhatre To get month of the given date e.g. 1 for January,2 for February and so on.
     * @param date the date object whose month to get
     * @return int the month of the date
     * @since 17 December, 2012
     */
    private static int getMonthByDate(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH);
    }

    /**
     * @author Pranit.Mhatre To get day of the given date e.g. if date is 28-06-1990 then it give 28
     * @param date the date object whose day to get
     * @return int the day of the date
     * @since 17 December, 2012
     */
    public static int getDateByDate(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }

    /**
     * @author Pranit.Mhatre To get time in milliseconds of the given date in string form.
     * @param stringDate the String object which is in date format
     * @return long the time in milliseconds
     * @since 19 December, 2012
     */
    public static long getTimeByString(final String stringDate) {
        return Utility.stringToDate(stringDate).getTime();
    }

    /**
     * @param date
     * @return
     */
    public static long getTimeByDate(final Date date) {
        return date.getTime();
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

        return (secondTime > firstTime) ? true : false;
    }

    /**
     * @author Umesh Gokhale
     * @param date
     * @return
     */
    private static HashMap<String, Integer> getAllparamsfromDate(final Date date) {
        final HashMap<String, Integer> datecredentials = new HashMap<>();

        if (date != null) {
            final Calendar calenderobj = Calendar.getInstance();
            calenderobj.setTime(date);

            final int year = calenderobj.get(Calendar.YEAR);
            final int month = calenderobj.get(Calendar.MONTH); // Note: zero based!
            final int day = calenderobj.get(Calendar.DAY_OF_MONTH);
            final int hour = calenderobj.get(Calendar.HOUR_OF_DAY);
            final int minute = calenderobj.get(Calendar.MINUTE);
            final int second = calenderobj.get(Calendar.SECOND);
            final int millis = calenderobj.get(Calendar.MILLISECOND);

            datecredentials.put("year", year);
            datecredentials.put(MONTH, month);
            datecredentials.put("day", day);
            datecredentials.put("hour", hour);
            datecredentials.put("minute", minute);
            datecredentials.put("second", second);
            datecredentials.put("millis", millis);

        } else {
            datecredentials.put("year", 0);
            datecredentials.put(MONTH, 0);
            datecredentials.put("day", 0);
            datecredentials.put("hour", 0);
            datecredentials.put("minute", 0);
            datecredentials.put("second", 0);
            datecredentials.put("millis", 0);
        }

        return datecredentials;
    }

    /**
     * To convert given string date to date+1. eg. 07/12/2012 to 08/12/2012 in string
     * @param strDate string to converted
     * @return String in dd/mm/yyyy format.
     */
    public static String getAfterDateByString(final String strDate) {

        return Utility.dateToString(getAfterDateBy(strDate));
    }

    /**
     * To convert given date object to date+1. eg. 07/12/2012 to 08/12/2012 in date
     * @param strDate string to converted
     * @return String in dd/mm/yyyy format.
     */
    private static Date getAfterDateBy(final String strDate) {

        final Date date = Utility.stringToDate(strDate);

        final Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);

        return calendar.getTime();
    }

    /**
     * @author Umesh Gokhale
     * @param dateString
     * @return
     */
    public static HashMap<String, Integer> getAllparamsfromDateString(final String dateString) {
        final Date date = stringToDate(dateString);
        return getAllparamsfromDate(date);
    }

    /**
     * @author Umesh Gokhale
     * @param date1
     * @param date2
     * @return
     */
    public static boolean comapreDates(final Date date1, final Date date2) {
        boolean result = false;

        final HashMap<String, Integer> datecredentials1 = getAllparamsfromDate(date1);
        final HashMap<String, Integer> datecredentials2 = getAllparamsfromDate(date2);

        final int date1year = datecredentials1.get("year");
        final int date2year = datecredentials2.get("year");
        final int date1month = datecredentials1.get(MONTH);
        final int date2month = datecredentials2.get(MONTH);
        final int date1day = datecredentials1.get("day");
        final int date2day = datecredentials2.get("day");

        final boolean yearequal = (date1year == date2year);
        final boolean monthequal = (date1month == date2month);
        final boolean dayequal = (date1day == date2day);

        if (yearequal && monthequal && dayequal) {
            result = true;
        }
        return result;
    }

    /**
     * This method is used to get {@link List} of {@link String} which contains financial years of specified number. Which is used
     * to displayed at drop-down box.
     * @param noOfPastFinancialYears is {@link Integer} which will specifies how many financial years should be returned.
     * @return {@link List} {@link String}
     * @author Swapnil.Pisat
     * @since 19/12/2012
     */
    public static List<String> getFinancialYearList(final int noOfPastFinancialYears) {

        final List<String> financialYear = new ArrayList<>();
        final Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.MONTH) > 3) {
            int startYear = calendar.get(Calendar.YEAR) - 1;
            int endYear = calendar.get(Calendar.YEAR);

            financialYear.add(startYear + MainetConstants.operator.HIPHEN + endYear);

            for (int i = 0; i < (noOfPastFinancialYears - 1); i++) {
                startYear = startYear - (1);
                endYear = endYear - (1);
                financialYear.add(startYear + MainetConstants.operator.HIPHEN + endYear);
            }

        } else if (calendar.get(Calendar.MONTH) <= 3) {
            int startYear = calendar.get(Calendar.YEAR) - 2;

            int endYear = calendar.get(Calendar.YEAR) - 1;

            financialYear.add(startYear + MainetConstants.operator.HIPHEN + endYear);

            for (int i = 0; i < (noOfPastFinancialYears - 1); i++) {
                startYear = startYear - (1);
                endYear = endYear - (1);
                financialYear.add(startYear + MainetConstants.operator.HIPHEN + endYear);
            }
        }

        return financialYear;
    }

    /**
     * Gets the current financial year.
     * @return the current financial year
     * @author Swapnil.Pisat
     * @since 25/02/2013
     */
    public static String getCurrentFinancialYear() {
        String startYear = MainetConstants.BLANK;
        String endYear = MainetConstants.BLANK;

        final Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.MONTH) > 2) {
            startYear = MainetConstants.BLANK + calendar.get(Calendar.YEAR);
            endYear = MainetConstants.BLANK + (calendar.get(Calendar.YEAR) + 1);
        } else {
            startYear = MainetConstants.BLANK + (calendar.get(Calendar.YEAR) - 1);
            endYear = MainetConstants.BLANK + calendar.get(Calendar.YEAR);
        }
        return startYear + MainetConstants.operator.HIPHEN + endYear;
    }

    /**
     * Gets the no of financial year including current.
     * @param noOfFinancialYears the no of financial years
     * @return the no of financial year including current
     * @author Swapnil.Pisat
     * @since 25/02/2013
     */
    public static List<String> getNoOfFinancialYearIncludingCurrent(final int noOfFinancialYears) {
        final List<String> financialYears = new ArrayList<>();
        int startYear = 0;
        int endYear = 0;

        final Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.MONTH) > 2) {
            startYear = calendar.get(Calendar.YEAR);
            endYear = calendar.get(Calendar.YEAR) + 1;
        } else {
            startYear = calendar.get(Calendar.YEAR) - 1;
            endYear = calendar.get(Calendar.YEAR);
        }
        for (int i = 0; i < noOfFinancialYears; i++) {
            financialYears.add(startYear + MainetConstants.operator.HIPHEN + endYear);
            startYear--;
            endYear--;
        }
        return financialYears;
    }

    public static String getActualNameOfTheFieldstatic(final long id, final ArrayList<String[]> Records) {
        final String name = Utility.getActualNameOfTheField(id, Records);
        return name;
    }

    private static String getActualNameOfTheField(final long id, final ArrayList<String[]> Records) {
        String AcualName = MainetConstants.BLANK;

        final Iterator<String[]> it = Records.iterator();
        while (it.hasNext()) {

            final String[] rec = it.next();
            final long i = Long.parseLong(rec[0]);
            if (id == i) {
                AcualName = rec[1];
                break;
            }
        }
        return AcualName;
    }

    public static boolean notNullOrzeronumberstatic(final Long teststring) {
        final Utility utility = new Utility();
        return utility.notNullOrzeronumber(teststring);
    }

    private boolean notNullOrzeronumber(final Long teststring) {

        if ((teststring != null) && (teststring != 0L)) {
            return true;
        } else {
            return false;
        }

    }

    public static boolean notNullOrEmptystatic(final String teststring) {
        return notNullOrEmpty(teststring);
    }

    private static boolean notNullOrEmpty(String teststring) {
        if (teststring != null) {
            teststring = teststring.trim();
            if (!teststring.equalsIgnoreCase(MainetConstants.BLANK) && !teststring.equalsIgnoreCase("null")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static int getCurrentYear() {
        final Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    public static int getCurrentMonth() {
        final Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH);
    }

    /**
     * This method provides the mechanism to get from and to date of given financial year
     * @param financeYear the financial year e.g. 2011-2012
     * @return <code>Date[]</code> array containing from date and to date.
     */
    public static Date[] getFromAndToDate(final String financeYear) {
        final String temp[] = financeYear.split(MainetConstants.operator.HIPHEN);

        return new Date[] { stringToDate("01/04/" + temp[0]), stringToDate("31/03/" + temp[1]) };
    }

    /**
     * This method provides the mechanism to get from date and to date of given financial year and given month.
     * @param financeYear the {@link String} object containing financial year.
     * @param selMonth the int value containing month.
     * @return {@link Date} array containing from date and to date.
     */
    public static Date[] getFromAndToDate(final String financeYear, final int selMonth) {

        final Calendar calendar = Calendar.getInstance();

        final String temp[] = financeYear.split(MainetConstants.operator.HIPHEN);

        if (selMonth < 4) {
            calendar.set(Calendar.YEAR, Integer.parseInt(temp[1]));
        } else {
            calendar.set(Calendar.YEAR, Integer.parseInt(temp[0]));
        }
        calendar.set(Calendar.MONTH, selMonth - 1);

        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        final Date fromDate = calendar.getTime();

        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        final Date toDate = calendar.getTime();

        return new Date[] { fromDate, toDate };

    }

    /**
     * This method provides the mechanism to get from date and to date of given financial year and given month.
     * @param financeYear the {@link String} object containing financial year.
     * @param selMonth the int value containing month.
     * @return {@link Date} array containing from date and to date.
     */
    public static Date[] getFromAndToDates(final String financeYear, final int selMonth) {

        final Calendar calendar = Calendar.getInstance();

        final String temp[] = financeYear.split(MainetConstants.operator.HIPHEN);

        if (selMonth > 3) {
            calendar.set(Calendar.YEAR, Integer.parseInt(temp[1]));
        } else {
            calendar.set(Calendar.YEAR, Integer.parseInt(temp[0]));
        }
        calendar.set(Calendar.MONTH, selMonth - 1);

        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        final Date fromDate = calendar.getTime();

        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        final Date toDate = calendar.getTime();

        return new Date[] { fromDate, toDate };

    }

    /**
     * This method convert given value to two decimal places.
     * @param value the double value to be converted.
     * @return {@link String} object contains converted value.
     */
    public static String convertTwoDecPalce(final double value) {
        final DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(value);
    }

    /**
     * This method provides the facility to get days for given month of given financial year
     * @param financeYear the selected financial year
     * @param month the month of the financial year.
     * @return last day ofthe month.
     */
    public static int getDaysForMonth(final String financeYear, final int month) {

        final Calendar calendar = Calendar.getInstance();

        final String temp[] = financeYear.split(MainetConstants.operator.HIPHEN);

        if (month < 4) {
            calendar.set(Calendar.YEAR, Integer.parseInt(temp[1]));
        } else {
            calendar.set(Calendar.YEAR, Integer.parseInt(temp[0]));
        }

        calendar.set(Calendar.MONTH, month - 1);

        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * This method provide facility to get {@link Date} object for given day,month and year.
     * @param DATE the date value
     * @param MONTH the month value
     * @param YEAR the year value
     * @return {@link Date} object
     */
    public static Date getDateByParam(final int DATE, final int MONTH, final int YEAR) {

        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, DATE);
        calendar.set(Calendar.MONTH, MONTH);
        calendar.set(Calendar.YEAR, YEAR);

        return calendar.getTime();

    }

    public static String getfinancialYearForMonthStatic(final String month, final String financialyear) {

        final Utility utility = new Utility();
        final String yr = utility.getfinancialYearForMonth(month, financialyear);
        return yr;
    }

    private String getfinancialYearForMonth(final String month, final String financialyear) {
        final long monthlong = Long.parseLong(month);
        final String[] finyrs = financialyear.split(MainetConstants.operator.HIPHEN);
        final String financialyear1 = finyrs[0];
        final String financialyear2 = finyrs[1];
        if (monthlong <= 9L) {
            return financialyear1;
        } else {
            return financialyear2;
        }
    }

    /**
     * This method provides the facility to get financial year for the given date.
     * @param date the {@link Date} object of which financial to be get.
     * @return {@link String} object containing financial year.
     * @throws Exception the exception if date is not valid
     */
    public static String getFinancialYearFromDate(final Date date) throws Exception {

        try {
            final int month = getMonthByDate(date);
            final int year = getYearByDate(date);
            if (month >= 3) {
                return year + MainetConstants.operator.HIPHEN + (year + 1);
            } else {
                return (year - 1) + MainetConstants.operator.HIPHEN + year;
            }
        } catch (final Exception e) {
            LOGGER.error(ERROR_OCCURED, e);
            return e.getMessage();
        }
    }

    /**
     * This method provides the facility for how many financial years to be show.
     * @param startFY the starting financial year.
     * @param currentFY the current financial year
     * @return total financial year to be show.
     */
    public static int totalFYCountToDisplay(final String startFY, final String currentFY) {

        final String start[] = startFY.split(MainetConstants.operator.HIPHEN);
        final String current[] = currentFY.split(MainetConstants.operator.HIPHEN);

        return (Integer.parseInt(current[1]) - Integer.parseInt(start[1])) + 1;
    }

    /**
     * This method provides the mechanism to get from date and to date of give date.
     * @param date the {@link Date} object of which from date and to date to be find.
     * @return {@link Date} array containing from date and to date.
     */
    public static Date[] getMonthFromToDates(final Date date) {

        final Calendar calendar = Calendar.getInstance();

        Date fromDate = null, toDate = null;

        calendar.setTime(date);

        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
        fromDate = calendar.getTime();

        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        toDate = calendar.getTime();

        return new Date[] { fromDate, toDate };

    }

    /**
     * This method provides the facility to check data is null string or blank.
     * @param data the data value to be check.
     * @return {@link Boolean} <code>true</code> if data is either null or blank otherwise <code>false</code>
     */
    public static boolean hasEmptyOrNull(final String data) {

        if (data == null) {
            return true;
        } else if (data.trim() == MainetConstants.BLANK) {
            return true;
        }

        return false;

    }

    public static Date getDateWithoutTimeFromCalenderstatic(final Calendar calenderobj) {
        final Utility utility = new Utility();
        final Date resultdate = utility.getDateWithoutTimeFromCalender(calenderobj);
        return resultdate;
    }

    private Date getDateWithoutTimeFromCalender(final Calendar calenderobj) {
        calenderobj.set(Calendar.HOUR, 0);
        calenderobj.set(Calendar.MINUTE, 0);
        calenderobj.set(Calendar.SECOND, 0);
        calenderobj.set(Calendar.MILLISECOND, 0);
        final Date resultdate = calenderobj.getTime();
        return resultdate;
    }

    public static Date[] getFromAndToDateforsms(final int selMonth) {

        final Calendar calendar = Calendar.getInstance();
        if (selMonth == 0) {
            int year = calendar.get(Calendar.YEAR);
            year--;
            calendar.set(Calendar.YEAR, year);
        }

        calendar.set(Calendar.MONTH, selMonth);

        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        final Date fromDate = calendar.getTime();

        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        final Date toDate = calendar.getTime();

        return new Date[] { fromDate, toDate };

    }

    public static Date removeTimeFromDatestatic(final Date date) {
        final Utility utility = new Utility();
        final Date resultdate = utility.removeTimeFromDate(date);
        return resultdate;
    }

    private Date removeTimeFromDate(final Date date) {
        Date resultdate = null;
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        resultdate = getDateWithoutTimeFromCalender(calendar);
        return resultdate;
    }

    public static Date[] getFromAndToDateforhalfyearly(final String financeYear) {
        final String temp[] = financeYear.split(MainetConstants.operator.HIPHEN);

        return new Date[] { stringToDate("01/04/" + temp[0]), stringToDate("30/09/" + temp[0]) };
    }

    public static String getMonthandYearName(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        final int year = calendar.get(Calendar.YEAR);

        String result = calendar.getDisplayName(Calendar.MONTH, Calendar.ALL_STYLES, Locale.US);
        result += MainetConstants.WHITE_SPACE + year;
        return result;
    }

    public static String getActualValueOfNameStatic(final String name, final List<String[]> demolist) {
        return new Utility().getActualValueOfName(name, demolist);
    }

    private String getActualValueOfName(final String name, final List<String[]> demolist) {
        String result = MainetConstants.IsDeleted.ZERO;
        final ListIterator<String[]> items = demolist.listIterator();
        while (items.hasNext()) {
            final String[] element = items.next();
            final String elementname = element[1];
            if (notNullOrEmpty(elementname) && notNullOrEmpty(name) && elementname.equals(name)) {
                result = element[0];
                break;
            }
        }
        return result;
    }

    /**
     * This method provides the mechanism to convert null object value into it's default constructor value.
     * @param object the {@link Object} class
     * @param clazz
     * @return
     */
    public static Object hasNull(final Object object, final Class<?> clazz) {

        if (object == null) {
            try {
                return clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException exception) {
                LOGGER.error(ERROR_OCCURED, exception);
                return null;
            }
        } else {
            return object;
        }
    }

    public static String decodeDescription(String string_to_decode) {
        if (notNullOrEmpty(string_to_decode)) {
            string_to_decode = string_to_decode.replaceAll("#,#", MainetConstants.operator.DOUBLE_QUOTES);
            string_to_decode = string_to_decode.replaceAll("#:#", "`");
            string_to_decode = string_to_decode.replaceAll("#DQ#", "\"");
            string_to_decode = string_to_decode.replaceAll("#amp;#", "&");
        }
        return string_to_decode;
    }

    public static String getFileExtension(final String file_path) {
        String extension = MainetConstants.BLANK;
        final File file = new File(file_path);

        final boolean exists = file.exists();
        if (exists) {
            final int dotposition = file_path.lastIndexOf(MainetConstants.operator.DOT);
            final String ext = file_path.substring(dotposition + 1, file_path.length());
            extension = ext;
        }
        return extension;
    }

    public static String removeStringExtension(final String file_path) {
        String newName = MainetConstants.BLANK;

        final int dotposition = file_path.lastIndexOf(MainetConstants.operator.DOT);
        try {
            newName = file_path.substring(0, dotposition);
        } catch (final Exception e) {
            LOGGER.error(ERROR_OCCURED, e);
            newName = file_path;
        }

        return newName;
    }

    public static boolean CheckifFileExists(final String file_path) {

        final File file = new File(file_path);

        final boolean exists = file.exists();
        return exists;
    }

    public static boolean isDocument(final String extension) {
        boolean result = false;
        if (extension.equals("doc") && extension.equals("pdf") && extension.equals("xls") && extension.equals("txt")) {
            result = true;
        }
        return result;
    }

    public static String getDownloadImagePath(final String extension) {
        String download_image_path = MainetConstants.BLANK;
        if (extension.equals("doc")) {
            download_image_path = "images/docImage.jpeg";
        } else if (extension.equals("pdf")) {
            download_image_path = "images/pdfImage.jpeg";
        } else if (extension.equals("xls")) {
            download_image_path = "images/excelImage.jpeg";
        } else if (extension.equals("txt")) {
            download_image_path = "images/textImage.jpeg";
        }
        return download_image_path;
    }

    public static String getImagePath(final String image_name, final List<String> inner_folders_name, final long orgid) {
        String file_path = MainetConstants.BLANK;

        file_path += String.valueOf(orgid);

        if (inner_folders_name != null) {
            final ListIterator<String> items = inner_folders_name.listIterator();
            while (items.hasNext()) {
                final String inner_folder_name = items.next();
                file_path += MainetConstants.WINDOWS_SLASH + inner_folder_name;
            }
        }
        file_path += MainetConstants.WINDOWS_SLASH + image_name;
        return file_path;
    }

    public static boolean isFileExists(final String phisicalImagePath) {
        final File file = new File(phisicalImagePath);

        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }

    public static String getDefaultImagePath(final String image_name, final List<String> inner_folders_name) {
        String file_path = MainetConstants.BLANK;
        if (inner_folders_name != null) {
            final ListIterator<String> items = inner_folders_name.listIterator();
            if (items.hasNext()) {
                file_path += items.next();
            }
            while (items.hasNext()) {
                final String inner_folder_name = items.next();
                file_path += MainetConstants.WINDOWS_SLASH + inner_folder_name;
            }
        }
        file_path += MainetConstants.WINDOWS_SLASH + image_name;
        return file_path;
    }

    /**
     * @param date
     * @return
     */
    public static Date getPreviousDate(final Date date) {
        final Calendar cal = Calendar.getInstance();

        cal.setTimeInMillis(date.getTime());

        cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);

        return cal.getTime();

    }

    /**
     * To make comparison between two date object.
     * @param fromDate the first {@link Date} object.
     * @param toDate the second {@link Date} object.
     * @return the value 0 if the first Date is equal to second Date; a value less than 0 if the first Date is before the second
     * Date; and a value greater than 0 if first Date is after the second Date.
     */
    public static int compareDates(final Date firstDate, final Date secondDate) {
        return firstDate.compareTo(secondDate);
    }

    public static Date converObjectToDate(final Object val) {
        if (val == null) {
            return null;
        }

        final Date date = (Date) val;

        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.getTime();
    }

    public static long getTimestamp() {
        return Utility.getTimeByDate(new Date());
    }

    public static String convertNumberToWord(final int number) {
        final NumberToWord numberToWord = new NumberToWord();

        return numberToWord.convert(number);
    }

    public static String convertNumberToWord(final double number) {
        String amountTOWords = null;
        String hindiWord = MainetConstants.BLANK;
        final NumberToWord numberToWord = new NumberToWord();
        final int repee = (int) Math.floor(number);
        int paiseAsInt;
        final String s = String.valueOf(number);
        final int index = s.indexOf('.');
        if (index == -1) {
            paiseAsInt = 0;
        } else {
            paiseAsInt = Integer.parseInt(s.substring(index + 1));
        }
        if (repee > 0) {
            amountTOWords = numberToWord.convert(repee);
        }
        if (paiseAsInt > 0) {
            if ((amountTOWords != null) && !amountTOWords.isEmpty()) {
                amountTOWords = amountTOWords + MainetConstants.WHITE_SPACE + ApplicationSession.getInstance().getMessage("and")
                        + MainetConstants.WHITE_SPACE
                        + numberToWord.convert(paiseAsInt) + MainetConstants.WHITE_SPACE
                        + ApplicationSession.getInstance().getMessage("paise") + MainetConstants.WHITE_SPACE
                        + ApplicationSession.getInstance().getMessage("only");
            } else {
                amountTOWords = (numberToWord.convert(paiseAsInt) + MainetConstants.WHITE_SPACE
                        + ApplicationSession.getInstance().getMessage("paise")
                        + MainetConstants.WHITE_SPACE + ApplicationSession.getInstance().getMessage("only"));
            }
        }
        if ((repee == 0) && (paiseAsInt == 0)) {
            amountTOWords = ApplicationSession.getInstance().getMessage("zero") + MainetConstants.WHITE_SPACE
                    + ApplicationSession.getInstance().getMessage("only");
        }
        if ((repee > 0) && (paiseAsInt == 0)) {
            amountTOWords = amountTOWords + MainetConstants.WHITE_SPACE + ApplicationSession.getInstance().getMessage("only");
        }
        if ((amountTOWords != null) && (amountTOWords.length() > 0)) {

            final String[] splitStr = amountTOWords.split("\\s+");
            if ((splitStr != null) && (splitStr.length > 0)) {
                for (final String element : splitStr) {
                    if (element != null) {
                        final String value = element.trim();
                        if (hindiWord.length() > 0) {
                            hindiWord += MainetConstants.WHITE_SPACE + ApplicationSession.getInstance().getMessage(value);
                        } else {
                            hindiWord += ApplicationSession.getInstance().getMessage(value);
                        }
                    }
                }
            }
        }
        return hindiWord;
    }

    public static Long[] adjustSearchParam(final Long value) {
        // 0 means 'Select' option selected.
        // -1 means 'All' option selected.

        return (value == -1l) ? null : (value == 0L ? new Long[] {} : new Long[] { value });
    }

    public static String getGUIDNumber() {
        final UUID guid = UUID.randomUUID();

        return guid.toString().replace(MainetConstants.operator.HIPHEN, MainetConstants.BLANK);

    }

    public static boolean createDirectory(final String directoryUrl) {
        final File directory = new File(directoryUrl);

        return directory.mkdirs();

    }

    public static boolean deleteDirectory(final String directoryUrl) {
        final File directory = new File(directoryUrl);

        if (directory.exists() && directory.isDirectory() && (directory.list().length == 0)) {
            return directory.delete();
        } else {
            for (final File file : directory.listFiles()) {
                if (file.exists() && file.isFile()) {
                    file.delete();
                } else {
                    return deleteDirectory(file.getAbsolutePath());
                }
            }
        }

        return true;
    }

    public static String downloadedFileUrl(String existingPath, String outputpath,
            final FileNetApplicationClient fileNetApplicationClient) {

        if (MainetConstants.FILE_PATH_SEPARATOR.equals(MainetConstants.operator.DOUBLE_BACKWARD_SLACE)) {
            existingPath = existingPath.replace(MainetConstants.operator.FORWARD_SLACE, MainetConstants.operator.DOUBLE_BACKWARD_SLACE);
        } else {
            existingPath = existingPath.replace(MainetConstants.operator.DOUBLE_BACKWARD_SLACE,MainetConstants.operator.FORWARD_SLACE);
        }

        final String fileName = StringUtility.staticStringAfterChar(FILE_PATH_SEPARATOR, existingPath);

        String directoryPath = StringUtility.staticStringBeforeChar(FILE_PATH_SEPARATOR, existingPath);

        directoryPath = directoryPath.replace(FILE_PATH_SEPARATOR, MainetConstants.operator.COMA);
        FileOutputStream fos = null;
        File file = null;
        try {

            final byte[] image = fileNetApplicationClient.getFileByte(fileName, directoryPath);

            Utility.createDirectory(Filepaths.getfilepath() + outputpath + FILE_PATH_SEPARATOR);

            

            if (null != image) {
            	file = new File(Filepaths.getfilepath() + outputpath + FILE_PATH_SEPARATOR + fileName);
                fos = new FileOutputStream(file);
                fos.write(image);
                fos.close();
            } else {
                LOGGER.error("File not found");
                throw new Exception("");
                //return MainetConstants.BLANK;
            }

            

        } catch (final HttpClientErrorException fileException) {
            LOGGER.error(ERROR_OCCURED, fileException);
            return MainetConstants.BLANK;
        }

        catch (final Exception e) {

            LOGGER.error(ERROR_OCCURED, e);
            return MainetConstants.BLANK;
        } finally {
            try {

                if (fos != null) {
                    fos.close();
                }

            } catch (final IOException e) {
                LOGGER.error(ERROR_OCCURED, e);
            }
        }

        outputpath = outputpath + FILE_PATH_SEPARATOR;

        outputpath = outputpath.replace(FILE_PATH_SEPARATOR, MainetConstants.WINDOWS_SLASH);

        return outputpath + file.getName();

    }
    
    
    public static String encodeBase64Image(String existingPath,
            final FileNetApplicationClient fileNetApplicationClient) {
    	
    	String encodedFile ="";

        if (MainetConstants.FILE_PATH_SEPARATOR.equals(MainetConstants.operator.DOUBLE_BACKWARD_SLACE)) {
            existingPath = existingPath.replace(MainetConstants.operator.FORWARD_SLACE, MainetConstants.operator.DOUBLE_BACKWARD_SLACE);
        } else {
            existingPath = existingPath.replace(MainetConstants.operator.DOUBLE_BACKWARD_SLACE,MainetConstants.operator.FORWARD_SLACE);
        }

        final String fileName = StringUtility.staticStringAfterChar(FILE_PATH_SEPARATOR, existingPath);

        String directoryPath = StringUtility.staticStringBeforeChar(FILE_PATH_SEPARATOR, existingPath);

        directoryPath = directoryPath.replace(FILE_PATH_SEPARATOR, MainetConstants.operator.COMA);
       
        try {
            final byte[] image = fileNetApplicationClient.getFileByte(fileName, directoryPath);
            if (null != image) {
            	 encodedFile = Base64.getEncoder().encodeToString(image);            
            }
        } catch (final HttpClientErrorException fileException) {
            LOGGER.error(ERROR_OCCURED, fileException);
            return MainetConstants.BLANK;
        }

        catch (final Exception e) {

            LOGGER.error(ERROR_OCCURED, e);
            return MainetConstants.BLANK;
        } 


        return encodedFile;

    }


    public static void cutPasteFile(final String source, final String target) {
        final File sourceFile = new File(source);

        final String name = sourceFile.getName();

        final File targetFile = new File(target + name);

        try {

            FileUtils.copyFile(sourceFile, targetFile);

            sourceFile.delete();

        } catch (final IOException e) {
            LOGGER.error(ERROR_OCCURED, e);
        }
    }

    /**
     * @author umashanker.kanaujiya 119-Nov-2014 2:53:00 pm 2014
     */
    public static String passWordInHasH(final String str) {
        if ((str == null) || (str.length() == 0)) {
            throw new IllegalArgumentException(
                    "String to encrypt cannot be null or zero length");
        }

        final StringBuffer hexString = new StringBuffer();

        try {
            final MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(str.getBytes());
            final byte[] hash = md.digest();

            for (final byte element : hash) {
                if ((0xff & element) < 0x10) {
                    hexString.append(MainetConstants.IsDeleted.ZERO + Integer.toHexString((0xFF & element)));
                } else {
                    hexString.append(Integer.toHexString(0xFF & element));
                }
            }
        } catch (final NoSuchAlgorithmException e) {
            LOGGER.error(ERROR_OCCURED, e);
        }

        return hexString.toString();
    }

    /**
     *
     * @author umashanker.kanaujiya 10-Feb-20153:50:28 pm 2015
     */
    @SuppressWarnings("unused")
    public static String toCamelCase(final String inputString) {
        String result = MainetConstants.BLANK;
        if (inputString.isEmpty() && (inputString == null) && (inputString.length() == 0)) {
            return result;
        }
        final char firstChar = inputString.charAt(0);
        final char firstCharToUpperCase = Character.toUpperCase(firstChar);
        result = result + firstCharToUpperCase;
        for (int i = 1; i < inputString.length(); i++) {
            final char currentChar = inputString.charAt(i);
            final char previousChar = inputString.charAt(i - 1);
            if (previousChar == ' ') {
                final char currentCharToUpperCase = Character.toUpperCase(currentChar);
                result = result + currentCharToUpperCase;
            } else {
                final char currentCharToLowerCase = Character.toLowerCase(currentChar);
                result = result + currentCharToLowerCase;
            }
        }
        return result;
    }

    /**
     * 
     * @author umashanker.kanaujiya 11-Feb-2015
     */
    @SuppressWarnings("unused")
    public static String toSentenceCase(final String inputString) {
        String result = MainetConstants.BLANK;
        if (inputString.isEmpty() && (inputString == null) && (inputString.length() == 0)) {
            return result;
        }
        final char firstChar = inputString.charAt(0);
        final char firstCharToUpperCase = Character.toUpperCase(firstChar);
        result = result + firstCharToUpperCase;
        boolean terminalCharacterEncountered = false;
        final char[] terminalCharacters = { '.', '?', '!' };
        for (int i = 1; i < inputString.length(); i++) {
            final char currentChar = inputString.charAt(i);
            if (terminalCharacterEncountered) {
                if (currentChar == ' ') {
                    result = result + currentChar;
                } else {
                    final char currentCharToUpperCase = Character.toUpperCase(currentChar);
                    result = result + currentCharToUpperCase;
                    terminalCharacterEncountered = false;
                }
            } else {
                final char currentCharToLowerCase = Character.toLowerCase(currentChar);
                result = result + currentCharToLowerCase;
            }
            for (final char terminalCharacter : terminalCharacters) {
                if (currentChar == terminalCharacter) {
                    terminalCharacterEncountered = true;
                    break;
                }
            }
        }
        return result;
    }

    public static String getStackTrace(final Exception ex) {
        final StringWriter errors = new StringWriter();

        ex.printStackTrace(new PrintWriter(errors));

        final String allStackTrace = errors.toString();

        return allStackTrace;

    }

    public static String getClientIpAddress(final HttpServletRequest request) {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();

        }
        return ipAddress;
    }

    public static String generateBarCode(final String name) {
        String path = StringUtils.EMPTY;
        try {

            final Barcode39 code39ext = new Barcode39();
            code39ext.setCode(name);
            code39ext.setStartStopText(false);
            code39ext.setExtended(true);
            final java.awt.Image rawImage = code39ext.createAwtImage(Color.BLACK, Color.WHITE);
            final BufferedImage outImage = new BufferedImage(rawImage.getWidth(null), rawImage.getHeight(null),
                    BufferedImage.TYPE_INT_RGB);
            outImage.getGraphics().drawImage(rawImage, 0, 0, null);
            final ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
            ImageIO.write(outImage, "png", bytesOut);
            bytesOut.flush();

            final String newPath = DirectoryTree.DEFAULT_CACHE_FOLDER + FILE_PATH_SEPARATOR
                    + Utility.getGUIDNumber() + FILE_PATH_SEPARATOR + name;

            path = newPath + FILE_PATH_SEPARATOR + name + ".png";

            Utility.createDirectory(Filepaths.getfilepath() + newPath);

            final FileOutputStream fos = new FileOutputStream(
                    new File(Filepaths.getfilepath() + newPath + FILE_PATH_SEPARATOR + name + ".png"));

            fos.write(bytesOut.toByteArray());
            fos.flush();
            fos.close();

        } catch (final Exception e) {
        }

        return path;

    }

    public static double round(double value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        final long factor = (long) Math.pow(10, places);
        value = value * factor;
        final long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    /**
     * use this method to encode field data,form parameters or URL in order to prevent all the vulnerability attacks. also can be
     * used for i18(local language) messages to make guarantee for the correct send-receive from server to client.
     * @param field :String to be encode
     * @return an encoded String
     */
    public static String encodeField(final String field) {
        String encodedField = null;
        try {
            encodedField = URLEncoder.encode(field, UTF8);
        } catch (final UnsupportedEncodingException ex) {
            LOGGER.error("Error during encoding " + field + MainetConstants.operator.COLON, ex);
        }

        return encodedField;
    }

    /**
     * use this method to decode field data,form parameters or URL in order to prevent all the vulnerability attacks. also can be
     * used for i18(local language) messages to make guarantee for the correct send-receive from server to client.
     * @param field :String to be decode
     * @return an decoded String
     */
    public static String decodeField(final String field) {
        String decodedField = null;
        try {
            decodedField = URLDecoder.decode(field, UTF8);
        } catch (final UnsupportedEncodingException ex) {
            LOGGER.error("Error during decoding " + field + MainetConstants.operator.COLON, ex);
        }

        return decodedField;
    }

    /**
     * 
     * @param reqStr
     * @return
     */
    public static String generateCheckSumValue(final String reqStr) {
        try {
            final String str = reqStr;
            // Convert string to bytes
            final byte bytes[] = str.getBytes();
            final Checksum checksum = new CRC32();
            checksum.update(bytes, 0, bytes.length);
            final long lngChecksum = checksum.getValue();
            return String.valueOf(lngChecksum);
        } catch (final Exception ee) {
            LOGGER.error(ERROR_OCCURED, ee);
            return StringUtils.EMPTY;
        } finally {
        }
    }

    /**
     *
     * @param RawData
     * @return
     */
    public static String GenerateCRC32(final String RawData) {
        final String str = RawData;
        // Convert string to bytes
        final byte bytes[] = str.getBytes();

        final Checksum checksum = new CRC32();
        checksum.update(bytes, 0, bytes.length);

        final long lngChecksum = checksum.getValue();

        return Integer.toString((int) lngChecksum);
    }

    /**
     * 
     * @param Data
     * @param strKey
     * @param striv
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * @throws IllegalStateException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static String simpleTripleDes(final String Data, final String strKey, final String striv)
            throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalStateException, IllegalBlockSizeException, BadPaddingException {
        final byte[] cipherBytes = Data.getBytes(UTF8);
        final byte[] iv = striv.getBytes(UTF8);
        final byte[] keyBytes = strKey.getBytes(UTF8);

        final SecretKey aesKey = new SecretKeySpec(keyBytes, "TripleDES");

        final Cipher cipher = Cipher.getInstance("TripleDES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, aesKey, new IvParameterSpec(iv));

        final byte[] result = cipher.doFinal(cipherBytes);

        return ByteArrayToString(result);
    }

    static private byte[] StringToByteArray(final String s) {
        if (s == null) {
            return null;
        }
        final int l = s.length();
        if ((l % 2) == 1) {
            return null;
        }
        final byte[] b = new byte[l / 2];
        for (int i = 0; i < (l / 2); i++) {
            b[i] = (byte) Integer.parseInt(s.substring(i * 2, (i * 2) + 2), 16);
        }
        return b;
    }

    static private String ByteArrayToString(final byte[] ba) {
        final String hex = new String(bitConverterToString(ba));
        return hex.replace(MainetConstants.operator.HIPHEN, MainetConstants.BLANK);
    }

    private static String bitConverterToString(final byte[] data) {
        final StringBuilder sb = new StringBuilder();
        for (final byte b : data) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    public static String simpleTripleDesDecryptHypen(final String Data, final String strKey, final String striv) {

        final byte[] cipherBytes = StringToByteArray(Data);

        byte[] iv;
        byte[] result = null;

        try {
            iv = striv.getBytes(UTF8);
            final byte[] keyBytes = strKey.getBytes(UTF8);
            final SecretKey aesKey = new SecretKeySpec(keyBytes, "TripleDES");
            final Cipher cipher = Cipher.getInstance("TripleDES/CBC/Nopadding");
            cipher.init(Cipher.DECRYPT_MODE, aesKey, new IvParameterSpec(iv));
            result = cipher.doFinal(cipherBytes);
        } catch (final Exception e) {
            LOGGER.error(ERROR_OCCURED, e);
        }

        return ByteArrayToStringHypen(result);

    }

    private static String ByteArrayToStringHypen(final byte[] ba) {
        final String hex = new String(ba);
        return hex;
    }

    /**
     *
     * @param utilDate
     * @return
     */
    public static Timestamp convertUtilDateToTimeStamp(final Date utilDate) {
        Timestamp timeStamp = null;
        if (utilDate != null) {
            timeStamp = new Timestamp(utilDate.getTime());
        }

        return timeStamp;
    }

    public static String getMD5(final String input) {
        try {
            final MessageDigest md = MessageDigest.getInstance("MD5");
            final byte[] messageDigest = md.digest(input.getBytes());
            final BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext.length() < 32) {
                hashtext = MainetConstants.IsDeleted.ZERO + hashtext;
            }
            return hashtext.toUpperCase();
        } catch (final NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String md5ConvertToString(final String str) {

        final StringBuffer hexString = new StringBuffer();

        try {
            final byte[] bytesOfMessage = str.getBytes(UTF8);

            final MessageDigest md = MessageDigest.getInstance("MD5");
            final byte[] thedigest = md.digest(bytesOfMessage);

            for (final byte element : thedigest) {
                final String hex = Integer.toHexString(0xff & element);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
        } catch (final Exception e) {
            LOGGER.error(ERROR_OCCURED, e);
        }
        return hexString.toString();

    }

    /**
     * use this method to get current financial year Ex- if current date is 03/02/2016 it will return 2016
     * @return
     */
    public static int currentFinancialYear() {

        int endYear;

        final Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.MONTH) > 2) {

            endYear = (calendar.get(Calendar.YEAR) + 1);
        } else {

            endYear = calendar.get(Calendar.YEAR);
        }
        return endYear;
    }

    public static final long getFormatDate(final Date slaDate) {
        final Calendar start = Calendar.getInstance();
        start.setTimeZone(start.getTimeZone());
        start.setTimeInMillis(start.getTimeInMillis());
        final Calendar end = Calendar.getInstance();
        end.setTimeZone(end.getTimeZone());
        end.setTimeInMillis(end.getTimeInMillis());
        start.setTime(start.getTime());
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.MILLISECOND, 0);
        end.setTime(slaDate);
        end.set(Calendar.HOUR_OF_DAY, 0);
        end.set(Calendar.MINUTE, 0);
        end.set(Calendar.SECOND, 0);
        end.set(Calendar.MILLISECOND, 0);
        return TimeUnit.MILLISECONDS.toDays(
                Math.abs(end.getTimeInMillis() - start.getTimeInMillis()));
    }

    public static final long getCompareDate(final Date slaDate) {

        final Calendar start = Calendar.getInstance();
        start.setTimeZone(start.getTimeZone());
        start.setTimeInMillis(start.getTimeInMillis());
        final Calendar end = Calendar.getInstance();
        end.setTimeZone(end.getTimeZone());
        end.setTimeInMillis(end.getTimeInMillis());
        start.setTime(start.getTime());
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.MILLISECOND, 0);
        end.setTime(slaDate);
        end.set(Calendar.HOUR_OF_DAY, 0);
        end.set(Calendar.MINUTE, 0);
        end.set(Calendar.SECOND, 0);
        end.set(Calendar.MILLISECOND, 0);
        return end.getTime().compareTo(start.getTime());

    }

    public static String getFileUrl(String outputPath, final String docName, final byte[] byteArray) {

        Utility.createDirectory(Filepaths.getfilepath() + outputPath);

        outputPath = outputPath.replace(FILE_PATH_SEPARATOR, MainetConstants.WINDOWS_SLASH);
        outputPath = outputPath + docName;
        final Path path = Paths.get(Filepaths.getfilepath() + outputPath);
        try {
            java.nio.file.Files.write(path, byteArray);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        return outputPath;
    }

    public static boolean isValidNumber(final String value) {
        try {
            if ((value == null) || value.isEmpty()) {
                return false;
            } else {

                final long longVal = Long.parseLong(value);
                if (longVal > 0.0) {
                    return true;
                } else {
                    return false;
                }
            }

        } catch (final NumberFormatException nfe) {
            LOGGER.error(NUMBER_FORMAT_EXCEPTION, nfe);
            return false;
        }
    }

    public static String fileSize(String existingPath, String fileName) {
        try {
        	LOGGER.info("Inside fileSize method  of  Utility class start");
            String filePath = ApplicationSession.getInstance().getMessage("upload.physicalPath")
                    + FILE_PATH_SEPARATOR + existingPath + FILE_PATH_SEPARATOR + fileName;
            
            LOGGER.info(">>>>>>>>>>>-----------------File PathName >>>>>>>>>>>>>>"+filePath);
            
            filePath = filePath.replace('\\', '/');
            
            LOGGER.info(">>>>>>>>>>>-----------------File PathName after replacing separator   >>>>>>>>>>>>>>"+filePath);
            
            File file = new File(filePath);
            
            LOGGER.info(">>>>>>>>>>>-----------------File Size  >>>>>>>>>>>>>>"+file.length());
            
            LOGGER.info("Inside fileSize method  of  Utility class End");
            
            return (file.length() / 1024) + " KB";
        } catch (final NumberFormatException nfe) {
            LOGGER.error(NUMBER_FORMAT_EXCEPTION, nfe);
            return "";
        }
    }

    public static String getImageDetails(String Path) {
        String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER
                + MainetConstants.FILE_PATH_SEPARATOR
                + UserSession.getCurrent().getOrganisation().getOrgid() + MainetConstants.FILE_PATH_SEPARATOR +
                MainetConstants.HOME_IMAGES;
        Path = Path.replace('\\', '/');
        try {
            return Utility.downloadedFileUrl(Path,
                    outputPath, fileNetApplicationClient);
        } catch (final Exception e) {
            LOGGER.error(MainetConstants.ERROR_OCCURED, e);
            return MainetConstants.BLANK;
        }
    }
    
    public static String getImageDetails(String Path,Long orgid) {
        String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER
                + MainetConstants.FILE_PATH_SEPARATOR
                + orgid + MainetConstants.FILE_PATH_SEPARATOR +
                MainetConstants.HOME_IMAGES;
        Path = Path.replace('\\', '/');
        try {
            return Utility.downloadedFileUrl(Path,
                    outputPath, fileNetApplicationClient);
        } catch (final Exception e) {
            LOGGER.error(MainetConstants.ERROR_OCCURED, e);
            return MainetConstants.BLANK;
        }
    }

	/**
	 * return days between two dates
	 * 
	 * @param faFromDate
	 *            from date inclusive
	 * @param faTodate
	 *            To date inclusive
	 * @return
	 */
	public static int getDaysBetweenDates(Date faFromDate, Date faTodate) {
		long difference = faTodate.getTime() - faFromDate.getTime();

		// Below lines gives difference between startDate (which is exclusive) and endDate (which inclusive) so adding 1 while returning
		float daysBetween = TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);

		return Math.round(daysBetween)+1;
	}
        
	
	
	public static Date getSubtractedDateBy(final Date strDate, final int day) {

        //final Date date = Utility.stringToDate(strDate);

        final Calendar calendar = Calendar.getInstance();

        calendar.setTime(strDate);

        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - day);

        return calendar.getTime();
    }
	
	public static Date getAddedDateBy(final Date strDate, final int day) {

        //final Date date = Utility.stringToDate(strDate);

        final Calendar calendar = Calendar.getInstance();

        calendar.setTime(strDate);

        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + day);

        return calendar.getTime();
    }
        	
	public static Date getFullYearByDate(final Date date) {
       
        final Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);

        calendar.add(Calendar.DATE, -1);

        return calendar.getTime();
    }

	public static long getOrgId()
    {
		String childOrgId = null;
		long orgId = 0l;
		try {
			final LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.COI, MainetConstants.ENV,
					UserSession.getCurrent().getOrganisation());
			if (lookup != null && StringUtils.isNotBlank(lookup.getOtherField())) {
				childOrgId = lookup.getOtherField();
			}

		} catch (Exception e) {
			//LOGGER.info("COI value not configure in ENV Perfix");
		}

		if (StringUtils.isNotBlank(childOrgId)) {
			try {
				orgId = Long.parseLong(childOrgId);
			} catch (NumberFormatException e) {
				orgId = UserSession.getCurrent().getOrganisation().getOrgid();
			}

		} else {
			orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		}
        return orgId;
    }
	
    public static void sendSmsAndEmail(String contentName, String flag, Long id, Employee employee ) {
        final SMSAndEmailDTO dto = new SMSAndEmailDTO();
        IEntitlementService iEntitlementService = ApplicationContextProvider.getApplicationContext()
                .getBean(IEntitlementService.class);
        IEmployeeService iEmployeeService = ApplicationContextProvider.getApplicationContext()
                .getBean(IEmployeeService.class);
        ISMSAndEmailService ismsAndEmailService=ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class);
        String messageType="";
        if(flag==null || flag.isEmpty()) {
        	 long gmid = iEntitlementService.getGroupIdByName(MainetConstants.MENU.ADMIN_CHECKER,
                     UserSession.getCurrent().getOrganisation().getOrgid());
             List<Employee> empList=iEmployeeService.getEmployeeByGroupId(gmid, UserSession.getCurrent().getOrganisation().getOrgid());
             if(empList!=null && empList.size()>0) {
            	 for(Employee emp:empList) {
            		 if(emp.getEmpemail()!=null) {
                      	dto.setEmail(emp.getEmpemail());
                           }
            		 if(emp.getEmpmobno()!=null) {
                      	dto.setMobnumber(emp.getEmpmobno());
            		 }
                     
            		//add condition for check atleast one of mobile or email present
					if (emp.getEmpemail() != null || emp.getEmpmobno() != null) {
						dto.setServName(contentName);
						if (id == 0) {
							messageType = MainetConstants.SMS_EMAIL.COMPLETED;
						}
						if (id > 0) {
							messageType = MainetConstants.SMS_EMAIL.GENERAL;
						}
						if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
							dto.setOrganizationName(UserSession.getCurrent().getOrganisation().getONlsOrgname());
						} else {
							dto.setOrganizationName(UserSession.getCurrent().getOrganisation().getONlsOrgnameMar());
						}
						ismsAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.ONLINE_SERVICE,
								MainetConstants.EIP_CHKLST.HOME, messageType, dto,
								UserSession.getCurrent().getOrganisation(), UserSession.getCurrent().getLanguageId());

					}

            	 }
             }
        	
        	
        }
        else {
        	if(employee.getEmpemail()!=null) {
        	dto.setEmail(employee.getEmpemail());
        	}
        	if(employee.getEmpmobno()!=null) {
        		dto.setMobnumber(employee.getEmpmobno());
        	 }
        	//add condition for check atleast one of mobile or email present
			if (employee.getEmpemail() != null || employee.getEmpmobno() != null) {
				dto.setServName(contentName);
				if (flag.equals(MainetConstants.AUTH)) {
					messageType = MainetConstants.SMS_EMAIL.APPROVAL;
				}
				if (flag.equals(MainetConstants.UNAUTH)) {
					messageType = MainetConstants.SMS_EMAIL.REJECTED;
				}
				if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
					dto.setOrganizationName(UserSession.getCurrent().getOrganisation().getONlsOrgname());
				} else {
					dto.setOrganizationName(UserSession.getCurrent().getOrganisation().getONlsOrgnameMar());
				}
				ismsAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.ONLINE_SERVICE,
						MainetConstants.EIP_CHKLST.HOME, messageType, dto, UserSession.getCurrent().getOrganisation(),
						UserSession.getCurrent().getLanguageId());

			}
		}
        
	}

	public static void sendSmsAndEmailBeforeExpire(String contentName, Organisation organisation, int langId) {
        final SMSAndEmailDTO dto = new SMSAndEmailDTO();
        IEntitlementService iEntitlementService = ApplicationContextProvider.getApplicationContext()
                .getBean(IEntitlementService.class);
        IEmployeeService iEmployeeService = ApplicationContextProvider.getApplicationContext()
                .getBean(IEmployeeService.class);
        ISMSAndEmailService ismsAndEmailService=ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class);
        String messageType="";
 
        	 long gmid = iEntitlementService.getGroupIdByName(MainetConstants.MENU.ADMIN_CHECKER,
        			 organisation.getOrgid());
             List<Employee> empList=iEmployeeService.getEmployeeByGroupId(gmid, organisation.getOrgid());
             if(empList!=null && empList.size()>0) {
            	 for(Employee emp:empList) {
            		 if(emp.getEmpemail()!=null) {
                      	dto.setEmail(emp.getEmpemail());
                           }
                      	dto.setMobnumber(emp.getEmpmobno());
                      	dto.setServName(contentName);
                    	messageType=MainetConstants.SMS_EMAIL.TASK_NOTIFICATION;
                    	ismsAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.ONLINE_SERVICE,
                            MainetConstants.EIP_CHKLST.HOME,
                            messageType, dto, organisation,langId);
            	 }
             }
        	
       
	}
	
	
	// Number conversion in Regional Language
    static HashMap<String, String> unicodeConversionList = new HashMap<>();

    public static void prepareList(String lang) {
        unicodeConversionList.clear();
        if ("marathi".equalsIgnoreCase(lang)) {
            unicodeConversionList.put("0", "\u0966");
            unicodeConversionList.put("1", "\u0967");
            unicodeConversionList.put("2", "\u0968");
            unicodeConversionList.put("3", "\u0969");
            unicodeConversionList.put("4", "\u096A");
            unicodeConversionList.put("5", "\u096B");
            unicodeConversionList.put("6", "\u096C");
            unicodeConversionList.put("7", "\u096D");
            unicodeConversionList.put("8", "\u096E");
            unicodeConversionList.put("9", "\u096F");
        } else if ("hindi".equalsIgnoreCase(lang)) {
            unicodeConversionList.put("0", "\u0966");
            unicodeConversionList.put("1", "\u0967");
            unicodeConversionList.put("2", "\u0968");
            unicodeConversionList.put("3", "\u0969");
            unicodeConversionList.put("4", "\u096A");
            unicodeConversionList.put("5", "\u096B");
            unicodeConversionList.put("6", "\u096C");
            unicodeConversionList.put("7", "\u096D");
            unicodeConversionList.put("8", "\u096E");
            unicodeConversionList.put("9", "\u096F");
        } else if ("gujarati".equalsIgnoreCase(lang)) {
            unicodeConversionList.put("0", "\u0AE6");
            unicodeConversionList.put("1", "\u0AE7");
            unicodeConversionList.put("2", "\u0AE8");
            unicodeConversionList.put("3", "\u0AE9");
            unicodeConversionList.put("4", "\u0AEA");
            unicodeConversionList.put("5", "\u0AEB");
            unicodeConversionList.put("6", "\u0AEC");
            unicodeConversionList.put("7", "\u0AED");
            unicodeConversionList.put("8", "\u0AEE");
            unicodeConversionList.put("9", "\u0AEF");
        } else {
            unicodeConversionList.put("0", "0");
            unicodeConversionList.put("1", "1");
            unicodeConversionList.put("2", "2");
            unicodeConversionList.put("3", "3");
            unicodeConversionList.put("4", "4");
            unicodeConversionList.put("5", "5");
            unicodeConversionList.put("6", "6");
            unicodeConversionList.put("7", "7");
            unicodeConversionList.put("8", "8");
            unicodeConversionList.put("9", "9");
        }
    }

    public static String convertToRegional(String lang, String str) {
        prepareList(lang);

        StringBuilder response = new StringBuilder("");
        if (str != null && str.trim().length() > 0 && unicodeConversionList.size() == 10)
            for (int i = 0; i < str.length(); i++) {
                String temp = "" + str.charAt(i);
                if (unicodeConversionList.containsKey(temp))
                    response.append(unicodeConversionList.get(temp));
                else
                    response.append(temp);
            }
        else
            response.append(str);

        return response.toString();
    }

	public static ObjectMapper getMapper() {
		return mapper;
	}

	public static void setMapper(ObjectMapper mapper) {
		Utility.mapper = mapper;
	}
	 public static Date getAddedDateBy2(final Date strDate, final int day) {

	        // final Date date = Utility.stringToDate(strDate);

	        final Calendar calendar = Calendar.getInstance();

	        calendar.setTime(strDate);

	        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + day);

	        return calendar.getTime();
	    }
	 
	
	    public static void changeLanguage(final HttpServletRequest request, final HttpServletResponse response,int langId) {
	    	final String requestLang = getLocaleString(langId);
	        final LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
	        localeResolver.setLocale(request, response, org.springframework.util.StringUtils.parseLocaleString(requestLang));
	        UserSession.getCurrent().setLanguageId(langId);
	        
	    }
	    
	    public static String passwordLengthValidationCheck(final String password) {
	        if(password.length() > MainetConstants.PASSWORD.MAX_LENGTH) {
	        	return ApplicationSession.getInstance().getMessage("admin.login.passMustContain.error");
	        }
	        return MainetConstants.operator.EMPTY;
	    }
	    
	    public static String getProfileImagePath() {	    	
	    	SimpleDateFormat formatter = new SimpleDateFormat(MainetConstants.FORMAT_DDMMMYY);
	        String date = formatter.format(new Date());
	        String citizenImagePath = UserSession.getCurrent().getOrganisation().getOrgid() + File.separator + date
	                + File.separator
	                + MainetConstants.DirectoryTree.EIP + File.separator + "CITIZEN_HOME" + File.separator + "IMAGE" + File.separator
	                + ((UserSession.getCurrent() !=null && UserSession.getCurrent().getEmployee() !=null) ? UserSession.getCurrent().getEmployee().getEmpId() : MainetConstants.NOUSER )+ File.separator + Utility.getTimestamp();
	    	return citizenImagePath;
	    }
	 // To check value is present in 'ENV' prefix or not
		public static boolean isEnvPrefixAvailable(Organisation org, String code) {
			LookUp lookup = null;
			try {
				lookup = CommonMasterUtility.getValueFromPrefixLookUp(code, MainetConstants.ENV, org);
			} catch (Exception e) {
				LOGGER.error("No Prefix found for ENV :" + code);
			}
			if (lookup != null) {
				return StringUtils.equals(MainetConstants.FlagY, lookup.getOtherField());
			}
			return false;
		}
		
		public static boolean isEnvPrefixAvailable(Organisation org, String[] code) {
			LookUp lookup = null;
				for (String envCode : code) {
					try {
					lookup = CommonMasterUtility.getValueFromPrefixLookUp(envCode, MainetConstants.ENV, org);
					if (lookup != null) {
						if(StringUtils.equals(MainetConstants.FlagY, lookup.getOtherField())){
						   return true;	
						}
					}
					}
					catch (Exception e) {
						LOGGER.error("No Prefix found for ENV :" + code);
					}
				}
			return false;
		}
}
