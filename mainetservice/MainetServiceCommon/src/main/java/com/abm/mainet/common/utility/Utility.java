package com.abm.mainet.common.utility;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.DigestException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
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

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.utils.JAXRSUtils;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
//import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.web.client.HttpClientErrorException;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Class Utility.
 */
public final class Utility {

	private static ObjectMapper mapper = new ObjectMapper();
	private final static Logger LOGGER = org.slf4j.LoggerFactory.getLogger(Utility.class);

	private static final String ENC_UTF_8 = "UTF-8";


	/**
	 * Password encryption.
	 *
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

	@Deprecated
	private static InetAddress getIpAddress() {
		InetAddress ip = null;
		try {
			ip = InetAddress.getLocalHost();
		} catch (final UnknownHostException e) {
			LOGGER.error("Cant Find IP address:: ", e);
		}
		return ip;

	}

	/**
	 * @return this method returns local addresses if find otherwise it returns Loop
	 *         back address
	 */
	@Deprecated
	public static String getMacAddress() {
		StringBuilder strBuild = new StringBuilder();
		try {
			Enumeration<NetworkInterface> enumNet = NetworkInterface.getNetworkInterfaces();
			while (enumNet.hasMoreElements()) {
				NetworkInterface netIntf = enumNet.nextElement();
				Enumeration<InetAddress> enumNetAddr = netIntf.getInetAddresses();
				while (enumNetAddr.hasMoreElements()) {
					InetAddress netAddr = enumNetAddr.nextElement();
					if (strBuild.length() > 0) {
						strBuild.append("/");
					}
					if (netAddr.isSiteLocalAddress()) {
						strBuild.append(netAddr.getHostAddress());
					}
				}
			}
		} catch (final Exception e) {

			LOGGER.error("exception occures while getting ip address", e);
		}
		if (strBuild.length() > 0 && !strBuild.toString().isEmpty()) {
			return strBuild.toString();
		} else {
			return InetAddress.getLoopbackAddress().getHostAddress();
		}

	}

	public static int getDefaultLanguageId(final Organisation org) {

		final List<LookUp> lookUps = CommonMasterUtility.getLookUps(MainetConstants.LANGUAGE, org);
		if (lookUps != null) {
			for (final LookUp lookUp : lookUps) {

				if (lookUp.getDefaultVal().equals(PrefixConstants.IsLookUp.STATUS.YES)) {
					return Integer.parseInt(lookUp.getLookUpCode());
				}
			}
		}
		// TODO: Get the default value from application properties
		return MainetConstants.DEFAULT_LANGUAGE_ID;
	}

	public static short getLanguageId(final String language) {
		if (MainetConstants.REG.equals(language)) {
			return 2;
		} else if (MainetConstants.DEFAULT_LOCALE_STRING.equals(language)) {
			return 1;
		} else {
			return MainetConstants.DEFAULT_LANGUAGE_ID;
		}
	}

	public static String getLocaleString(final int langId) {
		if (langId == 2) {
			return MainetConstants.REG;
		} else if (langId == 1) {
			return MainetConstants.DEFAULT_LOCALE_STRING;
		} else {
			return MainetConstants.DEFAULT_LOCALE_STRING;
		}
	}

	public static Date convertStringToDate(final int month, final int date, final int year) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");
		final String in = month + "/" + date + "/" + year;
		try {
			final Date theDate = dateFormat.parse(in);
			return theDate;
		} catch (final Exception e) {
			return null;
		}
	}

    public static LocalDate convertDateToLocalDate(Date date) {
		return LocalDate.parse(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(date),
				DateTimeFormatter.ofPattern(MainetConstants.DATE_FORMAT));
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

			formatter = new SimpleDateFormat(MainetConstants.DATE_FORMAT);
			if (strDate != null) {
				date = formatter.parse(strDate);
			}

		} catch (final Exception e) {
			return null;
		}

		return date;

	}
	
	//for solid waste date format
	public static Date stringToDateFormatUpload(final String strDate) {

		Date date = null;
		DateFormat formatter = null;

		try {

			formatter = new SimpleDateFormat(MainetConstants.DATE_FORMAT_UPLOAD);
			if (strDate != null) {
				date = formatter.parse(strDate);
			}

		} catch (final Exception e) {
			return null;
		}

		return date;

	}
	
	//for solid waste date format
	public static boolean isValidFormat(String format, String value) {
        Date date = null;
        try {
            date = new SimpleDateFormat(format).parse(value);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return date != null;
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
			return null;
		}

		return date;
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
			LOGGER.warn(e.getMessage());
		}

		return strDate;
	}

	/**
	 * @author Pranit.Mhatre To convert current date into string.
	 * @return string value of current date object.
	 */
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
	 *
	 * @param strNumber string to be tested.
	 * @return true if string is valid digit otherwise false.
	 */

	public static boolean isNumeric(final String strNumber) {
		final String regx = "(^[0-9])(.*)$";
		return strNumber.matches(regx);
	}

	/**
	 * To test whether entered string is valid digits or not ? with given min max
	 * range
	 *
	 * @param strNumber string to be tested.
	 * @param min       the minimum size
	 * @param max       the the maximum size
	 * @return true if string is valid digit otherwise false.
	 */
	public static boolean isNumeric(final String strNumber, final int min, final int max) {

		final String regx = "^([0-9]){" + min + "," + max + "}$";
		return strNumber.matches(regx);
	}

	/**
	 * To test whether entered string is valid characters or not ?
	 *
	 * @param strCharacters string to be tested.
	 * @return true if string is has valid characters otherwise false.
	 */
	public static boolean hasCharacter(final String strCharacters) {

		final String regx = "^([a-zA-Z ])+$";
		return strCharacters.matches(regx);
	}

	/**
	 * @author Pranit.Mhatre To convert given string date to date-1. eg. 07/12/2012
	 *         to 06/12/2012 in string
	 * @param strDate string to converted
	 * @return String in dd/mm/yyyy format.
	 */
	public static String getBeforeDateByString(final String strDate) {

		return Utility.dateToString(Utility.getBeforeDateBy(strDate));
	}

	/**
	 * @author Pranit.Mhatre To convert given date object to date-1. eg. 07/12/2012
	 *         to 06/12/2012 in date
	 * @param strDate string to converted
	 * @return String in dd/mm/yyyy format.
	 */
	public static Date getBeforeDateBy(final String strDate) {

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
	public static int getYearByDate(final Date date) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * @author Pranit.Mhatre To get month of the given date e.g. 1 for January,2 for
	 *         February and so on.
	 * @param date the date object whose month to get
	 * @return int the month of the date
	 * @since 17 December, 2012
	 */
	public static int getMonthByDate(final Date date) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH);
	}

	/**
	 * @author Pranit.Mhatre To get day of the given date e.g. if date is 28-06-1990
	 *         then it give 28
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
	 * @author Pranit.Mhatre To get time in milliseconds of the given date in string
	 *         form.
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
	 *
	 * @param firstDate  the first Date object to compare
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
	public static HashMap<String, Integer> getAllparamsfromDate(final Date date) {
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

			datecredentials.put(MainetConstants.YEAR, year);
			datecredentials.put(MainetConstants.MONTH, month);
			datecredentials.put(MainetConstants.DAY, day);
			datecredentials.put(MainetConstants.HOUR, hour);
			datecredentials.put(MainetConstants.MINUTE, minute);
			datecredentials.put(MainetConstants.SECOND, second);
			datecredentials.put(MainetConstants.MILLIS, millis);

		} else {
			datecredentials.put(MainetConstants.YEAR, 0);
			datecredentials.put(MainetConstants.MONTH, 0);
			datecredentials.put(MainetConstants.DAY, 0);
			datecredentials.put(MainetConstants.HOUR, 0);
			datecredentials.put(MainetConstants.MINUTE, 0);
			datecredentials.put(MainetConstants.SECOND, 0);
			datecredentials.put(MainetConstants.MILLIS, 0);
		}

		return datecredentials;
	}

	/**
	 * To convert given string date to date+1. eg. 07/12/2012 to 08/12/2012 in
	 * string
	 *
	 * @param strDate string to converted
	 * @return String in dd/mm/yyyy format.
	 */
	public static String getAfterDateByString(final String strDate) {

		return Utility.dateToString(getAfterDateBy(strDate));
	}

	/**
	 * To convert given date object to date+1. eg. 07/12/2012 to 08/12/2012 in date
	 *
	 * @param strDate string to converted
	 * @return String in dd/mm/yyyy format.
	 */
	public static Date getAfterDateBy(final String strDate) {

		final Date date = Utility.stringToDate(strDate);

		final Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);

		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);

		return calendar.getTime();
	}

	public static Date getAddedDateBy(final String strDate, final int day) {

		final Date date = Utility.stringToDate(strDate);

		final Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);

		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + day);

		return calendar.getTime();
	}

	public static Date getAddedDateBy2(final Date strDate, final int day) {

		// final Date date = Utility.stringToDate(strDate);

		final Calendar calendar = Calendar.getInstance();

		calendar.setTime(strDate);

		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + day);

		return calendar.getTime();
	}

	public static Date getSubtractedDateBy(final Date strDate, final int day) {

		// final Date date = Utility.stringToDate(strDate);

		final Calendar calendar = Calendar.getInstance();

		calendar.setTime(strDate);

		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - day);

		return calendar.getTime();
	}

	public static Date getAddedMonthBy(final String strDate, final int month) {

		final Date date = Utility.stringToDate(strDate);

		final Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);

		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + month);

		return calendar.getTime();
	}

	public static Date getAddedMonthsBy(final Date strDate, final int month) {

		// final Date date = Utility.stringToDate(strDate);

		final Calendar calendar = Calendar.getInstance();

		calendar.setTime(strDate);

		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + month);

		return calendar.getTime();
	}

	public static Date getAddedYearBy(final String strDate, final int year) {

		final Date date = Utility.stringToDate(strDate);

		final Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);

		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + year);

		return calendar.getTime();
	}

	public static Date getAddedYearsBy(final Date strDate, final int year) {

		// final Date date = Utility.stringToDate(strDate);

		final Calendar calendar = Calendar.getInstance();

		calendar.setTime(strDate);

		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + year);

		return calendar.getTime();
	}

	public static Date getFullYearByDate(final Date date) {

		final Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);

		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);

		calendar.add(Calendar.DATE, -1);

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

		final int date1year = datecredentials1.get(MainetConstants.YEAR);
		final int date2year = datecredentials2.get(MainetConstants.YEAR);
		final int date1month = datecredentials1.get(MainetConstants.MONTH);
		final int date2month = datecredentials2.get(MainetConstants.MONTH);
		final int date1day = datecredentials1.get(MainetConstants.DAY);
		final int date2day = datecredentials2.get(MainetConstants.DAY);

		final boolean yearequal = (date1year == date2year);
		final boolean monthequal = (date1month == date2month);
		final boolean dayequal = (date1day == date2day);

		if (yearequal && monthequal && dayequal) {
			result = true;
		}
		return result;
	}

	/**
	 * This method is used to get {@link List} of {@link String} which contains
	 * financial years of specified number. Which is used to displayed at drop-down
	 * box.
	 *
	 * @param noOfPastFinancialYears is {@link Integer} which will specifies how
	 *                               many financial years should be returned.
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

			financialYear.add(startYear + MainetConstants.HYPHEN + endYear);

			for (int i = 0; i < (noOfPastFinancialYears - 1); i++) {
				startYear = startYear - (1);
				endYear = endYear - (1);
				financialYear.add(startYear + MainetConstants.HYPHEN + endYear);
			}

		} else if (calendar.get(Calendar.MONTH) <= 3) {
			int startYear = calendar.get(Calendar.YEAR) - 2;

			int endYear = calendar.get(Calendar.YEAR) - 1;

			financialYear.add(startYear + MainetConstants.HYPHEN + endYear);

			for (int i = 0; i < (noOfPastFinancialYears - 1); i++) {
				startYear = startYear - (1);
				endYear = endYear - (1);
				financialYear.add(startYear + MainetConstants.HYPHEN + endYear);
			}
		}

		return financialYear;
	}

	/**
	 * Gets the current financial year.
	 *
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
		return startYear + MainetConstants.HYPHEN + endYear;
	}

	/**
	 * Gets the no of financial year including current.
	 *
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
			financialYears.add(startYear + MainetConstants.HYPHEN + endYear);
			startYear--;
			endYear--;
		}
		return financialYears;
	}

	public static String getActualNameOfTheFieldstatic(final long id, final ArrayList<String[]> Records) {
		final String name = Utility.getActualNameOfTheField(id, Records);
		return name;
	}

	public static String getActualNameOfTheField(final long id, final ArrayList<String[]> Records) {
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
	 * This method provides the mechanism to get from and to date of given financial
	 * year
	 *
	 * @param financeYear the financial year e.g. 2011-2012
	 * @return <code>Date[]</code> array containing from date and to date.
	 */
	public static Date[] getFromAndToDate(final String financeYear) {
		final String temp[] = financeYear.split(MainetConstants.HYPHEN);

		return new Date[] { stringToDate("01/04/" + temp[0]), stringToDate("31/03/" + temp[1]) };
	}

	/**
	 * This method provides the mechanism to get from date and to date of given
	 * financial year and given month.
	 *
	 * @param financeYear the {@link String} object containing financial year.
	 * @param selMonth    the int value containing month.
	 * @return {@link Date} array containing from date and to date.
	 */
	public static Date[] getFromAndToDate(final String financeYear, final int selMonth) {

		final Calendar calendar = Calendar.getInstance();

		final String temp[] = financeYear.split(MainetConstants.HYPHEN);

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
	 * This method provides the mechanism to get from date and to date of given
	 * financial year and given month.
	 *
	 * @param financeYear the {@link String} object containing financial year.
	 * @param selMonth    the int value containing month.
	 * @return {@link Date} array containing from date and to date.
	 */
	public static Date[] getFromAndToDates(final String financeYear, final int selMonth) {

		final Calendar calendar = Calendar.getInstance();

		final String temp[] = financeYear.split(MainetConstants.HYPHEN);

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
	 *
	 * @param value the double value to be converted.
	 * @return {@link String} object contains converted value.
	 */
	public static String convertTwoDecPalce(final double value) {
		final DecimalFormat df = new DecimalFormat("#0.00");
		return df.format(value);
	}

	/**
	 * This method provides the facility to get days for given month of given
	 * financial year
	 *
	 * @param financeYear the selected financial year
	 * @param month       the month of the financial year.
	 * @return last day ofthe month.
	 */
	public static int getDaysForMonth(final String financeYear, final int month) {

		final Calendar calendar = Calendar.getInstance();

		final String temp[] = financeYear.split(MainetConstants.HYPHEN);

		if (month < 4) {
			calendar.set(Calendar.YEAR, Integer.parseInt(temp[1]));
		} else {
			calendar.set(Calendar.YEAR, Integer.parseInt(temp[0]));
		}

		calendar.set(Calendar.MONTH, month - 1);

		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * This method provide facility to get {@link Date} object for given day,month
	 * and year.
	 *
	 * @param DATE  the date value
	 * @param MONTH the month value
	 * @param YEAR  the year value
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

	public String getfinancialYearForMonth(final String month, final String financialyear) {
		final long monthlong = Long.parseLong(month);
		final String[] finyrs = financialyear.split(MainetConstants.HYPHEN);
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
	 *
	 * @param date the {@link Date} object of which financial to be get.
	 * @return {@link String} object containing financial year.
	 * @throws Exception the exception if date is not valid
	 */
	public static String getFinancialYearFromDate(final Date date) throws Exception {

		try {
			final int month = getMonthByDate(date);
			final int year = getYearByDate(date);
			if (month >= 3) {
				return year + MainetConstants.HYPHEN + (year + 1);
			} else {
				return (year - 1) + MainetConstants.HYPHEN + year;
			}
		} catch (final Exception e) {
			return e.getMessage();
		}
	}

	/**
	 * This method provides the facility to get financial year with Date in yyyymm
	 * format
	 *
	 * @param date the {@link Date} object of which financial to be get.
	 * @return {@link String} pass the format "yyyymm".
	 * @throws Exception the exception if date is not valid
	 */
	public static int getFinancialFormatedDateFromDate(final Date date, String pattern) throws Exception {
		int currentDate = 0;
		if (StringUtils.isEmpty(pattern)) {
			pattern = "yyyyMM";
		}
		SimpleDateFormat yearMonth = new SimpleDateFormat(pattern);
		try {
			Calendar cal = Calendar.getInstance();
			currentDate = Integer.valueOf(yearMonth.format(cal.getTime()));

			cal.setTime(date);

			final int month = getMonthByDate(date);
			cal.set(Calendar.MONTH, month);

			final int year = getYearByDate(date);
			if (month >= 3) {

				cal.set(Calendar.YEAR, (year));
			} else {
				cal.set(Calendar.YEAR, (year + 1));
			}

			currentDate = Integer.valueOf(yearMonth.format(cal.getTime()));
			System.out.println("currentDate -> " + currentDate);

		} catch (final Exception e) {
			LOGGER.error("ERROR -> getFinancialMonthAndYearFromDate", e);
		}

		return currentDate;
	}

	/**
	 * This method provides the facility to get year with date in yyyymm fromat for
	 * the given date.
	 *
	 * @param date the {@link Date} object of which financial to be get.
	 * @return {@link String} pass the format "yyyymm".
	 * @throws Exception the exception if date is not valid
	 */
	public static int getFormatedDateFromDate(final Date date, String pattern) throws Exception {
		int currentDate = 0;
		if (StringUtils.isEmpty(pattern)) {
			pattern = "yyyyMM";
		}
		SimpleDateFormat yearMonth = new SimpleDateFormat(pattern);
		try {
			Calendar cal = Calendar.getInstance();
			currentDate = Integer.valueOf(yearMonth.format(cal.getTime()));
			cal.setTime(date);
			currentDate = Integer.valueOf(yearMonth.format(cal.getTime()));
		} catch (final Exception e) {
			LOGGER.error("ERROR -> getFinancialMonthAndYearFromDate", e);
		}

		return currentDate;
	}

	/**
	 * This method provides the facility for how many financial years to be show.
	 *
	 * @param startFY   the starting financial year.
	 * @param currentFY the current financial year
	 * @return total financial year to be show.
	 */
	public static int totalFYCountToDisplay(final String startFY, final String currentFY) {

		final String start[] = startFY.split(MainetConstants.HYPHEN);
		final String current[] = currentFY.split(MainetConstants.HYPHEN);

		return (Integer.parseInt(current[1]) - Integer.parseInt(start[1])) + 1;
	}

	/**
	 * This method provides the mechanism to get from date and to date of give date.
	 *
	 * @param date the {@link Date} object of which from date and to date to be
	 *             find.
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
	 *
	 * @param data the data value to be check.
	 * @return {@link Boolean} <code>true</code> if data is either null or blank
	 *         otherwise <code>false</code>
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
		final String temp[] = financeYear.split(MainetConstants.HYPHEN);

		return new Date[] { stringToDate("01/04/" + temp[0]), stringToDate("30/09/" + temp[0]) };
	}

	public static String getMonthandYearName(final Date date) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		final int year = calendar.get(Calendar.YEAR);

		String result = calendar.getDisplayName(Calendar.MONTH, Calendar.ALL_STYLES, Locale.US);
		result += " " + year;
		return result;
	}

	public static String getActualValueOfNameStatic(final String name, final List<String[]> demolist) {
		return new Utility().getActualValueOfName(name, demolist);
	}

	public String getActualValueOfName(final String name, final List<String[]> demolist) {
		String result = "0";
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
	 * This method provides the mechanism to convert null object value into it's
	 * default constructor value.
	 *
	 * @param object the {@link Object} class
	 * @param clazz
	 * @return
	 */
	public static Object hasNull(final Object object, final Class<?> clazz) {

		if (object == null) {
			try {
				return clazz.newInstance();
			} catch (InstantiationException | IllegalAccessException exception) {

				return null;
			}
		} else {
			return object;
		}
	}

	public static String decodeDescription(String string_to_decode) {
		if (notNullOrEmpty(string_to_decode)) {
			string_to_decode = string_to_decode.replaceAll("#,#", "'");
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
			final int dotposition = file_path.lastIndexOf(".");
			final String ext = file_path.substring(dotposition + 1, file_path.length());
			extension = ext;
		}
		return extension;
	}

	public static String removeStringExtension(final String file_path) {
		String newName = MainetConstants.BLANK;

		final int dotposition = file_path.lastIndexOf(".");
		try {
			newName = file_path.substring(0, dotposition);
		} catch (final Exception e) {
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

	public static String getImagePath(final String image_name, final List<String> inner_folders_name,
			final long orgid) {
		String file_path = MainetConstants.BLANK;

		file_path += String.valueOf(orgid);

		if (inner_folders_name != null) {
			final ListIterator<String> items = inner_folders_name.listIterator();
			while (items.hasNext()) {
				final String inner_folder_name = items.next();
				file_path += "/" + inner_folder_name;
			}
		}
		file_path += "/" + image_name;
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
				file_path += "/" + inner_folder_name;
			}
		}
		file_path += "/" + image_name;
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
	 *
	 * @param fromDate the first {@link Date} object.
	 * @param toDate   the second {@link Date} object.
	 * @return the value 0 if the first Date is equal to second Date; a value less
	 *         than 0 if the first Date is before the second Date; and a value
	 *         greater than 0 if first Date is after the second Date.
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
		final String s = String.valueOf(Math.round(number));
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
				amountTOWords = amountTOWords + " " + ApplicationSession.getInstance().getMessage("and") + " "
						+ numberToWord.convert(paiseAsInt) + " " + ApplicationSession.getInstance().getMessage("paise")
						+ " " + ApplicationSession.getInstance().getMessage("only");
			} else {
				amountTOWords = (numberToWord.convert(paiseAsInt) + " "
						+ ApplicationSession.getInstance().getMessage("paise") + " "
						+ ApplicationSession.getInstance().getMessage("only"));
			}
		}
		if ((repee == 0) && (paiseAsInt == 0)) {
			amountTOWords = ApplicationSession.getInstance().getMessage("zero") + " "
					+ ApplicationSession.getInstance().getMessage("only");
		}
		if ((repee > 0) && (paiseAsInt == 0)) {
			amountTOWords = amountTOWords + " " + ApplicationSession.getInstance().getMessage("only");
		}
		if ((amountTOWords != null) && (amountTOWords.length() > 0)) {

			final String[] splitStr = amountTOWords.split("\\s+");
			if ((splitStr != null) && (splitStr.length > 0)) {
				for (final String element : splitStr) {
					if (element != null) {
						final String value = element.trim();
						if (hindiWord.length() > 0) {
							hindiWord += " " + ApplicationSession.getInstance().getMessage(value);
						} else {
							hindiWord += ApplicationSession.getInstance().getMessage(value);
						}
					}
				}
			}
		}
		return hindiWord;
	}

	/*
	 * Convert Number To word (from 0 to 13 digit only)
	 */
	/*
	 * public static String convertBiggerNumberToWord(BigDecimal number) { String
	 * numberStr = number.setScale(2, BigDecimal.ROUND_HALF_EVEN).toString();
	 * String[] numberToConvert = numberStr.split("\\."); String inWords =
	 * NumberToWord(numberToConvert[0]); String paise = numberToConvert[1]; if
	 * (!paise.isEmpty() && !paise.equals("00")) { if ((inWords != null) &&
	 * !inWords.trim().isEmpty()) { inWords = inWords + MainetConstants.WHITE_SPACE
	 * + ApplicationSession.getInstance().getMessage("and") +
	 * MainetConstants.WHITE_SPACE + NumberToWord(paise) + " " +
	 * ApplicationSession.getInstance().getMessage("paise") +
	 * MainetConstants.WHITE_SPACE +
	 * ApplicationSession.getInstance().getMessage("only"); } else { inWords =
	 * (NumberToWord(paise) + MainetConstants.WHITE_SPACE +
	 * ApplicationSession.getInstance().getMessage("paise") +
	 * MainetConstants.WHITE_SPACE +
	 * ApplicationSession.getInstance().getMessage("only")); } } else if ((inWords
	 * != null) && !inWords.trim().isEmpty()) { inWords +=
	 * MainetConstants.WHITE_SPACE +
	 * ApplicationSession.getInstance().getMessage("only"); } else { inWords =
	 * ApplicationSession.getInstance().getMessage("zero") + " " +
	 * ApplicationSession.getInstance().getMessage("only"); } return inWords; }
	 * private static String NumberToWord(String number) { String twodigitword =
	 * MainetConstants.BLANK; String word = MainetConstants.BLANK; String[] HTLC = {
	 * MainetConstants.BLANK, "Hundred", "Thousand", "Lakh", "Crore", "Arab",
	 * "kharab" }; int split[] = { 0, 2, 3, 5, 7, 9, 11, 13 }; String[] temp = new
	 * String[split.length]; boolean addzero = true; for (int l = 1; l <
	 * split.length; l++) if (number.length() == split[l]) addzero = false; if
	 * (addzero == true) number = MainetConstants.Common_Constant.ZERO_SEC + number;
	 * int len = number.length(); int j = 0; while (split[j] < len) { int beg = len
	 * - split[j + 1]; int end = beg + split[j + 1] - split[j]; temp[j] =
	 * number.substring(beg, end); j = j + 1; } for (int k = 0; k < j; k++) {
	 * twodigitword = ConvertOnesTwos(temp[k]); if (k >= 1) { if
	 * (twodigitword.trim().length() != 0) word = twodigitword +
	 * MainetConstants.WHITE_SPACE + HTLC[k] + MainetConstants.WHITE_SPACE + word; }
	 * else word = twodigitword; } return (word); } private static String
	 * ConvertOnesTwos(String t) { final String[] ones = { "", "One", "Two",
	 * "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven",
	 * "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen",
	 * "Eighteen", "Nineteen" }; final String[] tens = { "", "Ten", "Twenty",
	 * "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety" }; String
	 * word = MainetConstants.BLANK; int num = Integer.parseInt(t); if (num % 10 ==
	 * 0) word = tens[num / 10] + MainetConstants.WHITE_SPACE + word; else if (num <
	 * 20) word = ones[num] + MainetConstants.WHITE_SPACE + word; else { word =
	 * tens[(num - (num % 10)) / 10] + word; word = word +
	 * MainetConstants.WHITE_SPACE + ones[num % 10]; } return word; }
	 */
	public static String convertBiggerNumberToWord(BigDecimal number) {

		String subString1 = "";
		String subString2 = "";
		String inWords = "";

		String numberStr = number.setScale(2, BigDecimal.ROUND_HALF_EVEN).toString();
		LOGGER.info(" Start Method convertBiggerNumberToWord total amount is  : " + number);

		String[] numberToConvert = numberStr.split("\\.");

		if (numberToConvert[0] != null && !numberToConvert[0].isEmpty() && numberToConvert[0].trim().length() > 7) {
			int length = numberToConvert[0].trim().length();
			subString1 = NumberToWord(numberToConvert[0].substring(0, length - 7));
			subString1 += " Crore ";
			subString2 = NumberToWord(numberToConvert[0].substring(length - 7, length));
			subString1 += subString2;
			inWords = subString1;
		} else
			inWords = NumberToWord(numberToConvert[0]);
		String paise = numberToConvert[1];

		if (!paise.isEmpty() && !paise.equals("00")) {
			if ((inWords != null) && !inWords.trim().isEmpty()) {
				inWords = inWords + MainetConstants.WHITE_SPACE
						+ ApplicationSession.getInstance().getMessage("Rupees.and") + MainetConstants.WHITE_SPACE
						+ NumberToWord(paise) + " " + ApplicationSession.getInstance().getMessage("paise")
						+ MainetConstants.WHITE_SPACE + ApplicationSession.getInstance().getMessage("only");
			} else {
				inWords = (NumberToWord(paise) + MainetConstants.WHITE_SPACE
						+ ApplicationSession.getInstance().getMessage("paise") + MainetConstants.WHITE_SPACE
						+ ApplicationSession.getInstance().getMessage("only"));
			}
		} else if ((inWords != null) && !inWords.trim().isEmpty()) {
			inWords += MainetConstants.WHITE_SPACE + ApplicationSession.getInstance().getMessage("Rupees.only");
		} else {
			inWords = ApplicationSession.getInstance().getMessage("zero") + " "
					+ ApplicationSession.getInstance().getMessage("only");
		}
		LOGGER.info(" Start Method convertBiggerNumberToWord total amount is  : " + inWords);
		return inWords;
	}

	private static String NumberToWord(String number) {

		String twodigitword = MainetConstants.BLANK;
		String word = MainetConstants.BLANK;
		String[] HTLC = { MainetConstants.BLANK, "Hundred", "Thousand", "Lakh" };
		int split[] = { 0, 2, 3, 5, 7, 9 };
		String[] temp = new String[split.length];
		boolean addzero = true;

		for (int l = 1; l < split.length; l++)
			if (number.length() == split[l])
				addzero = false;
		if (addzero == true)
			number = MainetConstants.Common_Constant.ZERO_SEC + number;

		int len = number.length();
		int j = 0;

		while (split[j] < len) {
			int beg = len - split[j + 1];
			int end = beg + split[j + 1] - split[j];
			temp[j] = number.substring(beg, end);
			j = j + 1;
		}

		for (int k = 0; k < j; k++) {
			twodigitword = ConvertOnesTwos(temp[k]);
			if (k >= 1) {
				if (twodigitword.trim().length() != 0)
					word = twodigitword + MainetConstants.WHITE_SPACE + HTLC[k] + MainetConstants.WHITE_SPACE + word;
			} else
				word = twodigitword;
		}
		return (word);
	}

	private static String ConvertOnesTwos(String t) {

		final String[] ones = { "", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten",
				"Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen" };
		final String[] tens = { "", "Ten", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty",
				"Ninety" };

		String word = MainetConstants.BLANK;
		int num = Integer.parseInt(t);
		if (num % 10 == 0)
			word = tens[num / 10] + MainetConstants.WHITE_SPACE + word;
		else if (num < 20)
			word = ones[num] + MainetConstants.WHITE_SPACE + word;
		else {
			word = tens[(num - (num % 10)) / 10] + word;
			word = word + MainetConstants.WHITE_SPACE + ones[num % 10];
		}
		return word;
	}

	// end

	public static String convertBigNumberToWord(BigDecimal number) {

		LOGGER.info(" Start Method convertBigNumberToWord total amount is  : " + number);
		String amountTOWords = null;
		String hindiWord = MainetConstants.BLANK;
		final NumberToWord numberToWord = new NumberToWord();

		BigDecimal amount = number.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		final int repee = Integer.valueOf(amount.setScale(0, RoundingMode.FLOOR).toString());// (int)
																								// Math.floor(number);

		// final int repee = (int) Math.floor(number);
		int paiseAsInt;
		final String s = String.valueOf(amount);
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
				amountTOWords = amountTOWords + " " + ApplicationSession.getInstance().getMessage("Rupees.and") + " "
						+ numberToWord.convert(paiseAsInt) + " " + ApplicationSession.getInstance().getMessage("paise")
						+ " " + ApplicationSession.getInstance().getMessage("only");
			} else {
				amountTOWords = (numberToWord.convert(paiseAsInt) + " "
						+ ApplicationSession.getInstance().getMessage("paise") + " "
						+ ApplicationSession.getInstance().getMessage("only"));
			}
		}
		if ((repee == 0) && (paiseAsInt == 0)) {
			amountTOWords = ApplicationSession.getInstance().getMessage("zero") + " "
					+ ApplicationSession.getInstance().getMessage("only");
		}
		if ((repee > 0) && (paiseAsInt == 0)) {
			amountTOWords = amountTOWords + " " + ApplicationSession.getInstance().getMessage("Rupees.only");
		}
		if ((amountTOWords != null) && (amountTOWords.length() > 0)) {

			final String[] splitStr = amountTOWords.split("\\s+");
			if ((splitStr != null) && (splitStr.length > 0)) {
				for (final String element : splitStr) {
					if (element != null) {
						final String value = element.trim();
						if (hindiWord.length() > 0) {
							hindiWord += " " + ApplicationSession.getInstance().getMessage(value);
						} else {
							hindiWord += ApplicationSession.getInstance().getMessage(value);
						}
					}
				}
			}
		}
		LOGGER.info("End Method convertBigNumberToWord Conver amount in Language is  : " + hindiWord);
		return hindiWord;
	}

	public static Long[] adjustSearchParam(final Long value) {
		return (value == -1l) ? null : (value == 0L ? new Long[] {} : new Long[] { value });
	}

	public static String getGUIDNumber() {
		final UUID guid = UUID.randomUUID();

		return guid.toString().replace(MainetConstants.HYPHEN, MainetConstants.BLANK);

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

	public static Set<File> getUploadedFileList(List<AttachDocs> attachDocs,
			FileNetApplicationClient fileNetApplicationClient) {

		Set<File> fileList = new HashSet<>();
		for (AttachDocs doc : attachDocs) {

			final String outputCacheRelativePath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER
					+ MainetConstants.FILE_PATH_SEPARATOR + MainetConstants.CommonMasterUi.SHOW_DOCS;

			String existingAbsoluteFilePath = doc.getAttPath() + MainetConstants.FILE_PATH_SEPARATOR
					+ doc.getAttFname();

			final String fileName = StringUtility.staticStringAfterChar(MainetConstants.FILE_PATH_SEPARATOR,
					existingAbsoluteFilePath);

			String existingFilenetRelativeDirPath = StringUtility
					.staticStringBeforeChar(MainetConstants.FILE_PATH_SEPARATOR, existingAbsoluteFilePath);

			String commaSeperatordirectoryPath = existingFilenetRelativeDirPath
					.replace(MainetConstants.FILE_PATH_SEPARATOR, MainetConstants.operator.COMMA);
			FileOutputStream fos = null;
			File file = null;
			try {
				LOGGER.info(
						"fileName[" + fileName + "],  existingFilenetRelativeDirPath[" + existingFilenetRelativeDirPath
								+ "], commaSeperatordirectoryPath[" + commaSeperatordirectoryPath + "]");

				final byte[] image = fileNetApplicationClient.getFileByte(fileName, commaSeperatordirectoryPath);

				Utility.createDirectory(
						Filepaths.getfilepath() + outputCacheRelativePath + MainetConstants.FILE_PATH_SEPARATOR);

				file = new File(Filepaths.getfilepath() + outputCacheRelativePath + MainetConstants.FILE_PATH_SEPARATOR
						+ fileName);

				fos = new FileOutputStream(file);

				fos.write(image);

				fos.close();

			} catch (final Exception e) {

			} finally {
				try {

					if (fos != null) {
						fos.close();
					}

				} catch (final IOException e) {
				}
			}
			fileList.add(file);
		}
		return fileList;
	}

	public static String downloadedFileUrl(String existingFilenetRelativePathWithFilename, String cacheRelativePath,
			final FileNetApplicationClient fileNetApplicationClient) {

		// if (MainetConstants.FILE_PATH_SEPARATOR.equals("\\")) { existingPath
		// = existingPath.replace('/', '\\'); } else { existingPath =
		// existingPath.replace('\\', '/'); }
		if (org.apache.commons.lang.StringUtils.isNotBlank(existingFilenetRelativePathWithFilename)) {
			if (!StringUtils.isEmpty(existingFilenetRelativePathWithFilename)) {
				existingFilenetRelativePathWithFilename = existingFilenetRelativePathWithFilename.replace('\\',
						MainetConstants.FILE_PATH_SEPARATOR.charAt(0));

				cacheRelativePath = cacheRelativePath.replace('\\', MainetConstants.FILE_PATH_SEPARATOR.charAt(0));
			}
		}

		final String fileName = StringUtility.staticStringAfterChar(MainetConstants.FILE_PATH_SEPARATOR,
				existingFilenetRelativePathWithFilename);

		String existingFilenetRelativeDirPath = StringUtility
				.staticStringBeforeChar(MainetConstants.FILE_PATH_SEPARATOR, existingFilenetRelativePathWithFilename);

		String commaSeperatordirectoryPath = existingFilenetRelativeDirPath.replace(MainetConstants.FILE_PATH_SEPARATOR,
				MainetConstants.operator.COMMA);
		File file = null;
		try {

			LOGGER.info("fileName[" + fileName + "],  existingFilenetRelativeDirPath[" + existingFilenetRelativeDirPath
					+ "], commaSeperatordirectoryPath[" + commaSeperatordirectoryPath + "]");

			final byte[] image = fileNetApplicationClient.getFileByte(fileName, commaSeperatordirectoryPath);

			Utility.createDirectory(Filepaths.getfilepath() + cacheRelativePath + MainetConstants.FILE_PATH_SEPARATOR);

			file = new File(
					Filepaths.getfilepath() + cacheRelativePath + MainetConstants.FILE_PATH_SEPARATOR + fileName);

			try (FileOutputStream fos = new FileOutputStream(file)) {
				fos.write(image);

				fos.close();

			} catch (Exception e) {
				LOGGER.warn("Exception occured while downloading file", e);
				return MainetConstants.BLANK;
			}

		} catch (final HttpClientErrorException fileException) {
			LOGGER.warn("Exception occured while downloading file", fileException);
			return MainetConstants.BLANK;
		} catch (final Exception e) {
			LOGGER.warn("Exception occured while downloading file", e);
			return MainetConstants.BLANK;
		}

		cacheRelativePath = cacheRelativePath + MainetConstants.FILE_PATH_SEPARATOR;

		cacheRelativePath = cacheRelativePath.replace(MainetConstants.FILE_PATH_SEPARATOR, "/");

		return cacheRelativePath + file.getName();

	}

	public static String downloadedPreviewFileUrl(String localFilePathWithFilename, String cacheRelativePath,
			final FileNetApplicationClient fileNetApplicationClient) {

		/*
		 * if (MainetConstants.FILE_PATH_SEPARATOR.equals("\\")) { existingPath =
		 * existingPath.replace('/', '\\'); } else { existingPath =
		 * existingPath.replace('\\', '/'); }
		 */
		if (isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)) {
			if (StringUtils.isNotBlank(localFilePathWithFilename)) {
				localFilePathWithFilename = localFilePathWithFilename.replace('\\',
						MainetConstants.FILE_PATH_SEPARATOR.charAt(0));
				cacheRelativePath = cacheRelativePath.replace('\\', MainetConstants.FILE_PATH_SEPARATOR.charAt(0));
			}
		}
		final String fileName = StringUtility.staticStringAfterChar(MainetConstants.FILE_PATH_SEPARATOR,
				localFilePathWithFilename);

		String existingFilenetRelativeDirPath = StringUtility
				.staticStringBeforeChar(MainetConstants.FILE_PATH_SEPARATOR, localFilePathWithFilename);

		String commaSeperatordirectoryPath = existingFilenetRelativeDirPath.replace(MainetConstants.FILE_PATH_SEPARATOR,
				MainetConstants.operator.COMMA);
		File file = null;
		try {

			LOGGER.info("fileName[" + fileName + "],  existingFilenetRelativeDirPath[" + existingFilenetRelativeDirPath
					+ "], commaSeperatordirectoryPath[" + commaSeperatordirectoryPath + "]");

			final byte[] image = fileNetApplicationClient.getFileByte(fileName, commaSeperatordirectoryPath,
					Boolean.TRUE);

			Utility.createDirectory(Filepaths.getfilepath() + cacheRelativePath + MainetConstants.FILE_PATH_SEPARATOR);

			file = new File(
					Filepaths.getfilepath() + cacheRelativePath + MainetConstants.FILE_PATH_SEPARATOR + fileName);

			try (FileOutputStream fos = new FileOutputStream(file)) {
				fos.write(image);

				fos.close();

			} catch (Exception e) {
				LOGGER.warn("Exception occured while downloading file", e);
				return MainetConstants.BLANK;
			}

		} catch (final HttpClientErrorException fileException) {
			LOGGER.warn("Exception occured while downloading file", fileException);
			return MainetConstants.BLANK;
		} catch (final Exception e) {
			LOGGER.warn("Exception occured while downloading file", e);
			return MainetConstants.BLANK;
		}

		cacheRelativePath = cacheRelativePath + MainetConstants.FILE_PATH_SEPARATOR;

		cacheRelativePath = cacheRelativePath.replace(MainetConstants.FILE_PATH_SEPARATOR, "/");

		return cacheRelativePath + file.getName();

	}

	public static void cutPasteFile(final String source, final String target) {
		final File sourceFile = new File(source);

		final String name = sourceFile.getName();

		final File targetFile = new File(target + name);

		try {

			FileUtils.copyFile(sourceFile, targetFile);

			sourceFile.delete();

		} catch (final IOException e) {

		}
	}

	public static String passWordInHasH(final String str) {
		if ((str == null) || (str.length() == 0)) {
			throw new IllegalArgumentException("String to encrypt cannot be null or zero length");
		}

		final StringBuffer hexString = new StringBuffer();

		try {
			final MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(str.getBytes());
			final byte[] hash = md.digest();

			for (final byte element : hash) {
				if ((0xff & element) < 0x10) {
					hexString.append("0" + Integer.toHexString((0xFF & element)));
				} else {
					hexString.append(Integer.toHexString(0xFF & element));
				}
			}
		} catch (final NoSuchAlgorithmException e) {
			e.getStackTrace();
		}

		return hexString.toString();
	}

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

		String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        
		return ipAddress;
	}

	/**
	 * It gets the jax-rs client ip address. This is only for CXF based JAX RS
	 * implementation
	 * 
	 * @return client IP address if available else returns null
	 */
	public static String getJAXRSClientIpAddress() {
		Message rsMsg = JAXRSUtils.getCurrentMessage();
		String ipAddress = null;
		if (rsMsg != null) {
			HttpServletRequest request = (HttpServletRequest) rsMsg.get(AbstractHTTPDestination.HTTP_REQUEST);
			ipAddress = request.getHeader("X-FORWARDED-FOR");
			if (ipAddress == null) {
				ipAddress = request.getRemoteAddr();

			}
		}
		return ipAddress;
	}

	public static String generateBarCode(final String name) {
		final String path = StringUtils.EMPTY;
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
		final byte[] cipherBytes = Data.getBytes("UTF-8");
		final byte[] iv = striv.getBytes("UTF-8");
		final byte[] keyBytes = strKey.getBytes("UTF-8");

		final SecretKey aesKey = new SecretKeySpec(keyBytes, "TripleDES");

		final Cipher cipher = Cipher.getInstance("TripleDES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, aesKey, new IvParameterSpec(iv));

		final byte[] result = cipher.doFinal(cipherBytes);
		return ByteArrayToString(result);
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
	public static String simpleTripleDesDecrypt(final String Data, final String strKey, final String striv)
			throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalStateException, IllegalBlockSizeException, BadPaddingException {
		final byte[] cipherBytes = StringToByteArray(Data);
		final byte[] iv = striv.getBytes("UTF-8");
		final byte[] keyBytes = strKey.getBytes("UTF-8");

		final SecretKey aesKey = new SecretKeySpec(keyBytes, "TripleDES");

		final Cipher cipher = Cipher.getInstance("TripleDES/CBC/NoPadding");
		cipher.init(Cipher.DECRYPT_MODE, aesKey, new IvParameterSpec(iv));

		final byte[] result = cipher.doFinal(cipherBytes);
		return new String(result, StandardCharsets.UTF_8);

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
		return hex.replace(MainetConstants.HYPHEN, MainetConstants.BLANK);
	}

	public static String bitConverterToString(final byte[] data) {
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
			iv = striv.getBytes("UTF-8");
			final byte[] keyBytes = strKey.getBytes("UTF-8");
			final SecretKey aesKey = new SecretKeySpec(keyBytes, "TripleDES");
			final Cipher cipher = Cipher.getInstance("TripleDES/CBC/Nopadding");
			cipher.init(Cipher.DECRYPT_MODE, aesKey, new IvParameterSpec(iv));
			result = cipher.doFinal(cipherBytes);
		} catch (final Exception e) {
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

	/**
	 * use this method to get current financial year Ex- if current date is
	 * 03/02/2016 it will return 2016
	 *
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

	public static void main(final String[] args)
			throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, IllegalStateException, IllegalBlockSizeException, BadPaddingException {
		"d8bc5c58-0b97-40d3-89cd-c3196cbee72d|635888009269721735|ixgnouy1w1lfh4nijoh11zah|1461799497".trim();
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
		return TimeUnit.MILLISECONDS.toDays(Math.abs(end.getTimeInMillis() - start.getTimeInMillis()));
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
	 * Password encryption.
	 *
	 * @param userName = Login Name
	 * @param password = Password for encryption
	 * @return String encrypted password
	 */
	public static String encryptPasswordMainet(String userName, final String password) {

		userName = userName.toUpperCase();// converts login name to uppercase

		final int login_number = getLoginNumber(userName);

		final char[] newpasswordArr = new char[password.length()];// forms a character
		// array with length
		// of password
		password.getChars(0, password.length(), newpasswordArr, 0);//

		for (int i = 0; i < newpasswordArr.length; i++) {
			newpasswordArr[i] = (char) (newpasswordArr[i] + (login_number / 256) + i);
		}
		return new String(newpasswordArr);

	}

	private static int getLoginNumber(final String login_name) {
		int login_number = 0;

		final char[] loginarr = new char[login_name.length()];
		login_name.getChars(0, login_name.length() - 1, loginarr, 0);

		for (final char element : loginarr) {
			login_number += element;
		}
		return login_number;
	}

	public static String getGenderId(final String lookUpCode) {
		String genderId = StringUtils.EMPTY;
		final List<LookUp> lookUps = CommonMasterUtility.getLookUps(PrefixConstants.MobilePreFix.GENDER,
				UserSession.getCurrent().getOrganisation());
		for (final LookUp lookUp : lookUps) {
			if ((lookUpCode != null) && !lookUpCode.isEmpty()) {
				if (lookUpCode.equalsIgnoreCase(lookUp.getLookUpCode())) {
					genderId = Long.toString(lookUp.getLookUpId());
					break;
				}
			}
		}
		return genderId;
	}

	public static Date dateFromMonth(final String financeYear, final int month, final String day) {
		final Calendar calendar = Calendar.getInstance();

		final String temp[] = financeYear.split(MainetConstants.HYPHEN);

		if (month < 4) {
			calendar.set(Calendar.YEAR, Integer.parseInt(temp[1]));
		} else {
			calendar.set(Calendar.YEAR, Integer.parseInt(temp[0]));
		}

		calendar.set(Calendar.MONTH, month - 1);

		if (MainetConstants.FINYEAR_DATE.LAST.equals(day)) {
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		} else {
			calendar.set(Calendar.DAY_OF_MONTH, 1);
		}
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static void deleteFile(final File file) throws IOException {
		if (file.isDirectory()) {

			if (file.list().length == 0) {

				file.delete();
				file.deleteOnExit();
				System.out.println("Directory is deleted : " + file.getAbsolutePath());

			} else {
				final String files[] = file.list();

				for (final String temp : files) {
					final File fileDelete = new File(file, temp);
					fileDelete.delete();
					file.deleteOnExit();
				}

			}

			Runtime.getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() {
					file.delete();
				}
			});

		} else {

			file.delete();
			file.deleteOnExit();
			Runtime.getRuntime().addShutdownHook(new Thread() {

				@Override
				public void run() {
					file.delete();
				}

			});
		}
	}

	public static String getDurationBreakdown(long millis) {
		String durationBreakdown = null;
		boolean isNegative = false;
		StringBuilder sb = new StringBuilder();

		if (millis < 0) {
			millis *= -1;
			isNegative = true;
		}

		if (millis == 0) {
			sb.append(0);
			sb.append(MainetConstants.WHITE_SPACE + MainetConstants.FlagD + MainetConstants.WHITE_SPACE);
		}

		if (millis > 0) {
			long days = TimeUnit.MILLISECONDS.toDays(millis);
			millis -= TimeUnit.DAYS.toMillis(days);
			long hours = TimeUnit.MILLISECONDS.toHours(millis);
			millis -= TimeUnit.HOURS.toMillis(hours);
			long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
			millis -= TimeUnit.MINUTES.toMillis(minutes);
			long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

			if (days > 0) {
				sb.append(days);
				sb.append(MainetConstants.WHITE_SPACE + MainetConstants.FlagD + MainetConstants.WHITE_SPACE);
			}
			if (hours > 0) {
				sb.append(hours);
				sb.append(MainetConstants.WHITE_SPACE + MainetConstants.FlagH + MainetConstants.WHITE_SPACE);
			}
			if (minutes > 0) {
				sb.append(minutes);
				sb.append(MainetConstants.WHITE_SPACE + MainetConstants.FlagM + MainetConstants.WHITE_SPACE);
			}
			if (seconds > 0) {
				sb.append(seconds);
				sb.append(MainetConstants.WHITE_SPACE + MainetConstants.FlagS + MainetConstants.WHITE_SPACE);
			}
		}

		if (isNegative) {
			durationBreakdown = MainetConstants.operator.HIPHEN + MainetConstants.WHITE_SPACE + sb.toString().trim();
		} else {
			durationBreakdown = sb.toString().trim();
		}
		return durationBreakdown;
	}

	/**
	 * Output Iterable of non Collection elements to String without [,] (brackets)
	 * appearing.
	 * 
	 * @param collection Iterable to get in String
	 * @param separator  String Value to separate Iterable elements.
	 * @return
	 */
	public static String iterableToString(Iterable<?> collection, String separator) {

		if (collection == null || separator == null)
			return null;
		StringBuilder builder = new StringBuilder();
		for (Object o : collection) {
			if (o != null && !(o instanceof Collection || o instanceof Map)) {
				String value = o.toString();
				if (builder.length() == 0) {
					if (!value.isEmpty())
						builder.append(value);
				} else {
					if (!value.isEmpty())
						builder.append(MainetConstants.WHITE_SPACE + separator + MainetConstants.WHITE_SPACE + value);
				}
			}
		}
		return builder.toString();
	}

	/**
	 * return financial year in string format (eg. 2015-16)
	 * 
	 * @param faFromDate financial year from date
	 * @param faTpdate   financial year To date
	 * @return
	 */
	public static String getFinancialYear(Date faFromDate, Date faTpdate) {
		String fromYear = String.valueOf(Utility.getYearByDate(faFromDate));
		String toFullYearYear = String.valueOf(Utility.getYearByDate(faTpdate));
		String toYear = String.valueOf(toFullYearYear).substring(toFullYearYear.length() - 2);
		String finacialYear = fromYear + MainetConstants.operator.HIPHEN + toYear;
		return finacialYear;
	}

	/**
	 * return financial year in string format (eg. 2015-2016)
	 * 
	 * @param faFromDate financial year from date
	 * @param faTpdate   financial year To date
	 * @return
	 */
	public static String getCompleteFinancialYear(Date faFromDate, Date faTpdate) {
		String fromYear = String.valueOf(Utility.getYearByDate(faFromDate));
		String toFullYearYear = String.valueOf(Utility.getYearByDate(faTpdate));
		String toYear = String.valueOf(toFullYearYear).substring(toFullYearYear.length() - 2);
		String finacialYear = fromYear + MainetConstants.operator.HIPHEN + toFullYearYear;
		return finacialYear;
	}

	/**
	 * return months between two dates
	 * 
	 * @param faFromDate from date
	 * @param faTodate   To date
	 * @return
	 */
	public static int getMonthsBetweenDates(Date faFromDate, Date faTodate) {
		Calendar cal = Calendar.getInstance();

		cal.setTime(faFromDate);
		int faFromMonth = cal.get(Calendar.MONTH);
		int faFromYear = cal.get(Calendar.YEAR);
		int faFromDays = cal.get(Calendar.DAY_OF_MONTH);

		cal.setTime(faTodate);
		int faToMonth = cal.get(Calendar.MONTH);
		int faToYear = cal.get(Calendar.YEAR);
		int faToDays = cal.get(Calendar.DAY_OF_MONTH);

		int diffYear = Math.abs(faFromYear - faToYear);
		int diffMonth = diffYear * 12 + Math.abs(faFromMonth - faToMonth);
		int diffdays = Math.abs(faFromDays - faToDays);

		return diffMonth;
	}

	/**
	 * return years between two dates
	 * 
	 * @param faFromDate from date
	 * @param faTodate   To date
	 * @return
	 */
	public static int getYearsBetweenDates(Date faFromDate, Date faTodate) {
		Calendar cal = Calendar.getInstance();

		cal.setTime(faFromDate);
		int faFromYear = cal.get(Calendar.YEAR);

		cal.setTime(faTodate);
		int faToYear = cal.get(Calendar.YEAR);

		return Math.abs(faFromYear - faToYear);
	}

	/**
	 * return days between two dates
	 * 
	 * @param faFromDate from date inclusive
	 * @param faTodate   To date inclusive
	 * @return
	 */
	public static int getDaysBetweenDates(Date faFromDate, Date faTodate) {
		long difference = faTodate.getTime() - faFromDate.getTime();

		// Below lines gives difference between startDate (which is exclusive) and
		// endDate (which inclusive) so adding 1 while
		// returning
		float daysBetween = TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);

		return Math.round(daysBetween) + 1;
	}

	public static int getDaysBetDates(Date faFromDate, Date faTodate) {
		long difference = faTodate.getTime() - faFromDate.getTime();
		float daysBetween = TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);
		return Math.round(daysBetween);
	}

	public static ObjectMapper getMapper() {
		return mapper;
	}

	public static void setMapper(ObjectMapper mapper) {
		Utility.mapper = mapper;
	}

	// T#89750
	public static String getFilePathForPdfUsingBirt(String URL) {
		String pdfNameGenarated = null;
		byte[] imageBytes = new byte[20480];

		// read PDF file from BIRT Viewer URL below is demo URLs which is working
		// String URL =
		// "http://192.168.100.79:8090/birt/frameset?__report=DetailDemandRegister.rptdesign&__format=pdf&OrgId=87&WZB1=915&WZB2=916&WZB3=0&WZB4=0&WZB5=0&FromDate=2020-09-01&ToDate=2020-09-02&PropertyNo=X";
		try {
			URL url = new URL(URL);
			imageBytes = IOUtils.toByteArray(url.openConnection().getInputStream());
			// to check file is PDF or not
			if (url.openConnection().getContentType() != null
					&& url.openConnection().getContentType().equalsIgnoreCase("application/pdf")) {
				// make path for write
				pdfNameGenarated = Filepaths.getfilepath() + MainetConstants.PDFFOLDERNAME
						+ MainetConstants.FILE_PATH_SEPARATOR + "BirtReports - " + Utility.getTimestamp()
						+ MainetConstants.PDF_EXTENSION;
				final File file = new File(pdfNameGenarated);
				FileOutputStream fos = null;
				fos = new FileOutputStream(file);
				fos.write(imageBytes);
				fos.close();
			}
		} catch (FileNotFoundException e) {
			LOGGER.info("Image Not found from Url" + e);
		} catch (IOException ioe) {
			LOGGER.info("Exception while reading the Image " + ioe);
		}

		return pdfNameGenarated;
	}

	// T#89750
	public static FileUploadDTO dataSetForDMS(String filePath, String idfId, String deptCode) {
		Set<File> fileDetails = new LinkedHashSet<>();
		final File file = new File(filePath);
		fileDetails.add(file);
		Map<Long, Set<File>> fileMap = new HashMap<>();
		fileMap.put(0L, fileDetails);
		FileUploadUtility.getCurrent().setFileMap(fileMap);
		FileUploadDTO uploadDTO = new FileUploadDTO();
		uploadDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		uploadDTO.setStatus(MainetConstants.FlagA);
		uploadDTO.setDepartmentName(deptCode);
		uploadDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		uploadDTO.setIdfId(idfId);
		return uploadDTO;
	}

	public static int monthsBetweenDates(Date fromDate, Date toDate) {
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(fromDate);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(toDate);

		int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
		int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
		return diffMonth;
	}

	// US#102672
	public static void pushDocumentToDms(String URL, String refNo, String deptCode, IFileUploadService fileUpload) {
		if (MainetConstants.Common_Constant.YES.equals(ApplicationSession.getInstance().getMessage("dms.configure"))) {
			String filePath = Utility.getFilePathForPdfUsingBirt(URL);
			if (filePath != null && refNo != null) {
				String idfId = MainetConstants.DMS_LIST + MainetConstants.FILE_PATH_SEPARATOR + refNo;
				FileUploadDTO uploadDTO = Utility.dataSetForDMS(filePath, idfId, deptCode);
				fileUpload.doMasterFileUpload(fileUpload.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()),
						uploadDTO);
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

	public static Date getStartOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static Date getEndOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}

	public static FinancialYear getPreviousFinYearByCurrYear(FinancialYear currentFinYear) {
		FinancialYear previousFinYear = null;
		Timestamp currFinYearTimeStamp = new Timestamp(currentFinYear.getFaFromDate().getTime());
		Date currentFinYearDate = new Date(currFinYearTimeStamp.getTime());
		LocalDate convertFinFromDateToLocalDate = currentFinYearDate.toInstant().atZone(ZoneId.systemDefault())
				.toLocalDate();
		currentFinYear.getFaToDate().getTime();
		Date afterSubMonths = Date
				.from(convertFinFromDateToLocalDate.minusMonths(5).atStartOfDay(ZoneId.systemDefault()).toInstant());
		previousFinYear = ApplicationContextProvider.getApplicationContext().getBean(TbFinancialyearService.class)
				.getFinanciaYearByDate(afterSubMonths);
		return previousFinYear;
	}

	public static boolean isCallCenterApplicable(Long orgId) {
		Organisation org = new Organisation();
		org.setOrgid(orgId);
		LookUp ccaLookup = null;
		try {
			ccaLookup = CommonMasterUtility.getValueFromPrefixLookUp("CCA", "CCA", org);
		} catch (Exception e) {

		}
		if (ccaLookup != null && ccaLookup.getOtherField().equals("N")) {
			return false;
		}

		return true;
	}

	public static String getFilePathForPdfBirt(String URL, Long applicationNo) {
		String pdfNameGenarated = null;
		byte[] imageBytes = new byte[20480];
		LOGGER.info("PdfBirt Conversion started------------------------------->");

		// read PDF file from BIRT Viewer URL below is demo URLs which is working
		// String URL =
		// "http://192.168.100.79:8090/birt/frameset?__report=DetailDemandRegister.rptdesign&__format=pdf&OrgId=87&WZB1=915&WZB2=916&WZB3=0&WZB4=0&WZB5=0&FromDate=2020-09-01&ToDate=2020-09-02&PropertyNo=X";
		try {
			URL url = new URL(URL);
			LOGGER.info("Byte conversion Stared------------------------------->");
			imageBytes = IOUtils.toByteArray(url.openConnection().getInputStream());
			LOGGER.info("Byte conversion completed------------------------------->");
			LOGGER.info("URL content type------------------------------->"+url.openConnection().getContentType());
			// to check file is PDF or not
			if (url.openConnection().getContentType() != null
					&& url.openConnection().getContentType().equalsIgnoreCase("application/pdf")) {
				// make path for write
				pdfNameGenarated = Filepaths.getfilepath() + MainetConstants.PDFFOLDERNAME
						+ MainetConstants.FILE_PATH_SEPARATOR + applicationNo + MainetConstants.PDF_EXTENSION;
				LOGGER.info("File path------------------------------->"+pdfNameGenarated);
				final File file = new File(pdfNameGenarated);
				FileOutputStream fos = null;
				fos = new FileOutputStream(file);
				LOGGER.info("pdf writer stated------------------------------->"+pdfNameGenarated);
				fos.write(imageBytes);
				fos.close();
				LOGGER.info("pdf writer closed------------------------------->"+pdfNameGenarated);
			}
		} catch (FileNotFoundException e) {
			LOGGER.info("Image Not found from Url" + e);
		} catch (IOException ioe) {
			LOGGER.info("Exception while reading the Image " + ioe);
		}

		return pdfNameGenarated;
	}
	// D#129853 for round off the charge value
	public static boolean isRoundedOffApplicable(Organisation org) {

		LookUp ccaLookup = null;
		try {
			ccaLookup = CommonMasterUtility.getValueFromPrefixLookUp("RNF", "RNF", org);
		} catch (Exception e) {
			LOGGER.info("Exception while Fetch  RNF Prefix Value");
		}
		if (ccaLookup != null && ccaLookup.getOtherField().equals("Y")) {
			return true;
		}

		return false;
	}
	public static String passwordLengthValidationCheck(final String password) {
        if(password.length() > MainetConstants.PASSWORD.MAX_LENGTH) {
        	return ApplicationSession.getInstance().getMessage("admin.login.passMustContain.error");
        }
        return MainetConstants.operator.EMPTY;
    }
	
	public static byte[] textToImage(String text, String fontPath, float size,boolean isbold) throws IOException, FontFormatException {
		BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		try {
			Graphics2D g2d = img.createGraphics();
			Font fnt = Font.createFont(Font.TRUETYPE_FONT, new File(fontPath));
			if(!isbold)
			fnt = fnt.deriveFont(Font.PLAIN, size);
			else
			fnt = fnt.deriveFont(Font.BOLD,size);
			g2d.setFont(fnt);
			FontMetrics fm = g2d.getFontMetrics();
			int width = fm.stringWidth(text);
			int height = fm.getHeight();
			g2d.dispose();

			img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			g2d = img.createGraphics();
			//g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
			g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
			g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HBGR);
	        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HBGR);
	        g2d.setRenderingHint(RenderingHints.KEY_TEXT_LCD_CONTRAST, 100);

			g2d.setFont(fnt);
			fm = g2d.getFontMetrics();
			g2d.setBackground(Color.WHITE);
			g2d.setColor(Color.BLACK);
			g2d.drawString(text, 0, fm.getAscent());
			g2d.dispose();
		} catch (Exception e) {
			LOGGER.info("Exception Occur" + e);
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(img, "png", baos);
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		baos.close();

		return imageInByte;
	}
	
	public static byte[] textToImageColor(String text, String fontPath, float size,boolean isbold) throws IOException, FontFormatException {
		BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		try {
			Graphics2D g2d = img.createGraphics();
			Font fnt = Font.createFont(Font.TRUETYPE_FONT, new File(fontPath));
			if(!isbold)
			fnt = fnt.deriveFont(Font.PLAIN, size);
			else
			fnt = fnt.deriveFont(Font.BOLD,size);
			g2d.setFont(fnt);
			FontMetrics fm = g2d.getFontMetrics();
			int width = fm.stringWidth(text);
			int height = fm.getHeight();
			g2d.dispose();

			img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			g2d = img.createGraphics();
			//g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
			g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
			g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HBGR);
	        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HBGR);
	        g2d.setRenderingHint(RenderingHints.KEY_TEXT_LCD_CONTRAST, 100);

			g2d.setFont(fnt);
			fm = g2d.getFontMetrics();
			g2d.setBackground(Color.BLACK);
			g2d.setColor(Color.WHITE);
			g2d.drawString(text, 0, fm.getAscent());
			g2d.dispose();
		} catch (Exception e) {
			LOGGER.info("Exception Occur" + e);
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(img, "png", baos);
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		baos.close();

		return imageInByte;
	}

	
	// US#134797
	public static Long getReceiptIdFromCustomRcptNO(String rcptNo) {
		String[] recptNo = rcptNo.split(MainetConstants.RECEIPT_NO_PATTERN);
		List<String> list = Arrays.asList(recptNo);
		String temp = "";
		Long reciptNo = 0L;
		for (String s : list) {
			if (s.matches(MainetConstants.STRING_DATA))
				temp = s;
			else
				reciptNo = Long.valueOf(s);
		}
		return reciptNo;
	}

	public static String getDeptCodeFromCustomRcptNO(String rcptNo) {
		String[] recptNo = rcptNo.split(MainetConstants.RECEIPT_NO_PATTERN);
		List<String> list = Arrays.asList(recptNo);
		String code = null;
		for (String s : list) {
			if (s.matches(MainetConstants.STRING_DATA))
				code = s;
		}
		return code;
	}

	public static int getLastLevel(String prefixCode, Organisation org) {
		try {
			List<LookUp> lookupList = CommonMasterUtility.getListLookup(prefixCode, org);
			return lookupList.size();
		} catch (Exception e) {
			LOGGER.error("No Prefix found for ENV :" + prefixCode);	
		}

		return 0;
	}
	
	
	/**
	 * A utility class containing a method to convert a large number represented as a BigDecimal
	 * into its English word representation.
	 */
	public static String convertBigDecimalToWord(BigDecimal number) {
    	String[] thousands = MainetConstants.thousands;
    	BigInteger bigNumber = number.toBigInteger();

        if (bigNumber.equals(BigInteger.ZERO)) {
            return "Zero";
        }

        String sign = bigNumber.signum() < 0 ? "Minus " : "";
        bigNumber = bigNumber.abs();

        StringBuilder sb = new StringBuilder(sign);

        int groupIndex = 0;
        while (bigNumber.compareTo(BigInteger.ZERO) > 0) {
            BigInteger triplet = bigNumber.mod(BigInteger.valueOf(1000));
            bigNumber = bigNumber.divide(BigInteger.valueOf(1000));

            int tripletValue = triplet.intValue();
            if (tripletValue > 0) {
                String tripletWords = convertTripletToWords(tripletValue);
                if (groupIndex > 0) {
                    sb.insert(0, ", ");
                }
                sb.insert(0, tripletWords + " " + thousands[groupIndex]);
            }

            groupIndex++;
        }

        String result = sb.toString().trim();
        return result.substring(0, 1).toUpperCase() + result.substring(1) + " " + ApplicationSession.getInstance().getMessage("Rupees.only");
    }

    private static String convertTripletToWords(int number) {
    	String[] ones = MainetConstants.ones;
    	String[] tens = MainetConstants.tens;
        int hundred = number / 100;
        int remainder = number % 100;

        String words = "";
        if (hundred > 0) {
            words = ones[hundred] + " hundred";
            if (remainder > 0) {
                words += " and ";
            }
        }

        if (remainder > 0) {
            if (remainder < 20) {
                words += ones[remainder];
            } else {
                int tensDigit = remainder / 10;
                int onesDigit = remainder % 10;
                words += tens[tensDigit];
                if (onesDigit > 0) {
                    words += "-" + ones[onesDigit];
                }
            }
        }

        return words;
    }

    public static int calculateMonthsBetweenTwoDates(Date fromDate, Date toDate) {
		LocalDate frmDateLoc = fromDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate toDateLoc = toDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	    int months = (int) ChronoUnit.MONTHS.between(YearMonth.from(frmDateLoc), YearMonth.from(toDateLoc));
		return months;
	}
    public static String convertInByteCode(String docName, String docPath) {
        String base64String = null;
        String existingPath = null;
        if (MainetConstants.FILE_PATH_SEPARATOR.equals("\\")) {
            existingPath = docPath.replace('/', '\\');
        } else {
            existingPath = docPath.replace('\\', '/');
        }
        String directoryPath = existingPath.replace(MainetConstants.FILE_PATH_SEPARATOR, MainetConstants.operator.COMA);
        try {
            final byte[] image = FileNetApplicationClient.getInstance().getFileByte(docName, directoryPath);
            
            //base64String = base64.encodeToString(image);
            base64String = new String(Base64.getEncoder().encode(image));
            

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return base64String;
    }
    
    public static String decryptText(String cipherText,String secret){

        String decryptedText=null;
        byte[] cipherData = java.util.Base64.getDecoder().decode(cipherText);
        byte[] saltData = Arrays.copyOfRange(cipherData, 8, 16);
        try{
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            final byte[][] keyAndIV = GenerateKeyAndIV(32, 16, 1, saltData, secret.getBytes(StandardCharsets.UTF_8), md5);
            SecretKeySpec key = new SecretKeySpec(keyAndIV[0], "AES");
            IvParameterSpec iv = new IvParameterSpec(keyAndIV[1]);

            byte[] encrypted = Arrays.copyOfRange(cipherData, 16, cipherData.length);
            Cipher aesCBC = Cipher.getInstance("AES/CBC/PKCS5Padding");
            aesCBC.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] decryptedData = aesCBC.doFinal(encrypted);
            decryptedText = new String(decryptedData, StandardCharsets.UTF_8);
            return decryptedText;
        }
        catch (Exception ex){
            return decryptedText;
        }
    }

    public static byte[][] GenerateKeyAndIV(int keyLength, int ivLength, int iterations, byte[] salt, byte[] password, MessageDigest md) {

        int digestLength = md.getDigestLength();
        int requiredLength = (keyLength + ivLength + digestLength - 1) / digestLength * digestLength;
        byte[] generatedData = new byte[requiredLength];
        int generatedLength = 0;

        try {
            md.reset();

            // Repeat process until sufficient data has been generated
            while (generatedLength < keyLength + ivLength) {

                // Digest data (last digest if available, password data, salt if available)
                if (generatedLength > 0)
                    md.update(generatedData, generatedLength - digestLength, digestLength);
                md.update(password);
                if (salt != null)
                    md.update(salt, 0, 8);
                md.digest(generatedData, generatedLength, digestLength);

                // additional rounds
                for (int i = 1; i < iterations; i++) {
                    md.update(generatedData, generatedLength, digestLength);
                    md.digest(generatedData, generatedLength, digestLength);
                }

                generatedLength += digestLength;
            }

            // Copy key and IV into separate byte arrays
            byte[][] result = new byte[2][];
            result[0] = Arrays.copyOfRange(generatedData, 0, keyLength);
            if (ivLength > 0)
                result[1] = Arrays.copyOfRange(generatedData, keyLength, keyLength + ivLength);

            return result;

        } catch (DigestException e) {

            throw new RuntimeException(e);

        } finally {
            // Clean out temporary data
            Arrays.fill(generatedData, (byte)0);
        }
    }
    
}
