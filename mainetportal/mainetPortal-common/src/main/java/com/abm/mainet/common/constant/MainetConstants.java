package com.abm.mainet.common.constant;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * This interface is useful for defining application constants.
 * <p>
 * It also contains nested interfaces for defining child level constants.
 * </p>
 *
 * @author Pranit.Mhatre
 */
public interface MainetConstants {

	String FILE_PATH_SEPARATOR = File.separator;
	int DEFAULT_LANGUAGE_ID = 1;
	int REGIONAL_LANGUAGE_ID = 2;
	String DEFAULT_LOCALE_STRING = "en";
	String LAST_DATE = "Last Date";
	String LANGUAGE = "LNG";
	String NUMBER_REGEX = "[0-9]+";
	double SIGNUM_NEGATIVE = -1.0;
	double SIGNUM_POSITIVE = 1.0;
	String GENDER = "GEN";
	String DATE_PATTERN = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";

	String TIME_PATTERN = "(20|21|22|23|[01]\\d|\\d)(([:][0-5]\\d){1,2}) (([a|A][m|M]|[p|P][m|M]))";

	String DATE_TIME_PATTERN = "(" + DATE_PATTERN + "|" + DATE_PATTERN + " " + TIME_PATTERN + ")";

	String DATE_FORMAT = "dd/MM/yyyy";

	String URL = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

	String HOUR_FORMAT = "hh:mm a";

	String DATE_HOUR_FORMAT = DATE_FORMAT + " " + HOUR_FORMAT;

	String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	String ALPHA_NUMERIC = "^[a-zA-Z0-9]+$";

	String ALPHA_NUMERIC_WITH_DASH = "^[a-zA-Z0-9-]*$";
	String PAN = "[A-Z]{5}[0-9]{4}[A-Z]{1}";
	String PASSPORT = "^(?!^0+$)[A-Z]{1}[0-9]{7}$";
	String DRIVING_LICENSE = "^[A-Z]{2}[0-9]{14}$";
	String VOTERID = "^([a-zA-Z]){3}([0-9]){7}?$";

	String NUMERIC = "^[0-9]+$";

	String MOB_PATTERN = "^[(789)](\\d){9}$";
	String MOB_EXT = "/[^0-9 +]/g";
	String MOBILE_NO_REGX = "[0-9]{7,12}$";
	String PHONE_NUMBER_REGX = "^([0-9\\(\\)\\/\\+ \\-]*)$";
	String CAPTCHA_GENERATE_VALUE = "0123456789";
	String NO_CAPTCHA_GENERATE_VALUE = "1111111111";
	String MOB_REGX_PATTERN = "^[(789)](\\d){11}$";

	String WINDOWS_SLASH = "/";
	
	String List_With_Empty_Null = "[null]";

	String FORM_NAME = "command";

	String COMMON_DATE_FORMAT = "dd/MM/yyyy";

	String COMMON_DECIMAL_FORMAT = "##.00";

	String VALIDN_SUFFIX = "Validn";

	int EMPTY_LIST = 0;

	short ENGLISH = 1;
	short MARATHI = 2;

	long DEFAULT_USER = 0L;

	String BLANK = "";
	String WHITE_SPACE = " ";

	String NEW_LINK_CODE = "N";
	String NEW_LINK_DESC = "New Links";
	String IMP_LINK_CODE = "I";
	String IMP_LINK_DESC = "Important Notices";
	String QUOTATIONS_CODE = "Q";
	String QUOTATIONS_DESC = "Quotations";
	String TENDERS_CODE = "T";
	String TENDERS_DESC = "External Link";
	String ON_GOING_PROJECTS_CODE = "P";
	String ON_GOING_PROJECTS_DESC = "On-Going Projects";

	String FlagL = "L";
	String FlagS = "S";
	String FlagF = "F";
	String FlagY = "Y";
	String FlagN = "N";
	String FLAGN = "N";
	String FLAGY = "Y";
	String FLAGI = "I";
	String EDIT = "E";
	String FlagT = "T";
	String FlagW = "W";
	String PAY_GATEWAY_AVAIL = "Y";

	String DOC_EXIST = "DOC_ALREADY_EXIST";

	String DMS_CONFIGURE = "dms.configure";
	String HOME_IMAGES = "HOME_IMAGES";
	String NEWS_IMAGES = "NEWS_IMAGES";
	String GALLERY_IMAGES = "GALLERY_IMAGES";
	String GALLERY_VIDEO = "GALLERY_VIDEO";
	String DATE_FRMAT = "dd-MMM-yyyy";

	String START_TIME = "startTime";
	String ORGID = "ORGID";
	String CHILD_ORG_ID = "child.ulb.id";
	String USER = "USER";
	String REQ_UUID = "requestUUID";
	String LOGGING_URL = "URL";
	String DURATION = "duration";
	String BEFORE_REQUEST_LOGGING = "Before request logging";
	String AFTER_REQUEST_LOGGING = "After request logging";
	String USER_SESSION = "userSession";

	String SMSEMAIL = "SMSAndEmail";
	String SMS_EMAIL_ADD = "smsAndEmailAdd";
	String SMS_EMAIL_EDIT = "smsAndEmailEdit";
	String MESSAGE_TYPE = "messageType";
	String DOCUMENT_RESUBMISSION = "DocumentResubmission.html";
	String WATER_DISCONNECTION = "WaterDisconnectionForm.html";
	String CHANGE_OF_USAGE = "ChangeOfUsage.html";
	String USER_ALREADY_LOGGEDIN = "AlreadyLoggedIn";
	String PHONE_DIRECTORY = "phoneDirectory";
	String PHONE_DIRECTORY_NAME = "Phone_Diretory";

	String COMMAND = "command";

	String MAKER_CHEKER_ERROR = "MAKER CAN'T AS A CHEKKER :: ";

     String ENV = "ENV";

	String COI = "COI";
	String TOVE = "TOVE";
	String About_KDMC="About KDMC";

	interface ENVIRNMENT_VARIABLE {
		String COI = "COI";
		String ENV = "ENV";
		String ENV_SKDCL = "SKDCL";
		String [] ENV_PRODUCT = {"SKDCL","PSCL"};
		String DYNAMIC_GALLERY_PORTAL = "DGP";
		String GENERAL_PUBLIC_INFORMATION = "GPI";
		String MULTI_ORGANISATION_APPLICATION_DATA = "MOA";
		String MDD_DASHBOARD = "MDDA";
		String ENV_PSCL = "PSCL";
	}
	interface PROJECT_SHORTCODE {
        String PRAYAGRAJ_ULB = "PNN";
    }

	interface MENU {

		String TRUE = "true";
		String FALSE = "false";
		String A = "A";
		String _0 = "0";
		String _1 = "1";
		String SF = "SF";
		String S = "S";
		String M = "M";
		String F = "F";
		String EX = "EX";
		String Y = "Y";
		String N = "N";
		String P = "P";
		String NAMEENG = "smfname";
		String NAMEREG = "smfname_mar";
		String[] ROLEMENU = new String[] { "S", "M", "F", "SF" };
		String[] DISPLAY_MENU_PARENT = new String[] { "S" };
		String[] DISPLAY_MENU_CHILD = new String[] { "M", "F" };
		String ORG_CITIZEN = "GR_CITIZEN_";
		String DEFAULT_CITIZEN = "GR_CITIZEN_DEFAULT";
		String ADMIN_CHECKER = "GR_CHEKER";
		String PORTAL_LOGIN = "GR_PORTAL_LOGIN";
		String APPROVER = "GR_CHEKER";
		String OPERATOR = "GR_OPERATOR";
		String AGENCY = "GR_AGENCY";
		String E = "E";
		String D = "D";
		String A_ID = "A\\/";
		String E_ID = "E\\/";
		String D_ID = "D\\/";
		String ALL = "ALL";
		String EndWithHTML = ".html";
		String B = "B";
		String Smart_City_Label = "Smart City";

	}

	interface operator {

		String DOT = ".";

		String EMPTY = "";

		String UNDER_SCORE = "_";

		String FORWARD_SLACE = "/";

		String COMA = ",";
		String COLON = ":";
		String SEMI_COLON = ";";
		String DOUBLE_BACKWARD_SLACE = "\\";
		String QUAD_BACKWARD_SLACE = "\\\\";
		String ARROW = "-->";
		String PERCENTILE = "%";

		String ORR = "|";

		String LEFT_SQUARE_BRACKET = "]";
		String RIGHT_SQUARE_BRACKET = "[";
		String LEFT_BRACKET = "(";
		String RIGHT_BRACKET = ")";
		String DOUBLE_QUOTES = "'";
		String DOUBLEQUOTES_COMA = "',";
		String HIPHEN = "-";
		String TILDE = "~";
		String AMPERSAND = "&";

		String COMMA = ",";
		String AT_THE_RATE = "@";
		String SEPERATOR = "~@~";

	}

	interface APP_NAME {
		String TSCL = "TSCL";
		String ASCL = "ASCL";
		String DSCL = "DSCL";
		String SKDCL = "SKDCL";
		String SUDA = "SUDA";
		String WEBLINE="WEBLINE";
	}

	interface FORM_ELEMENT {
		String ATTACHMENT_1 = "att_01";
		String ATTACHMENT_2 = "att_02";
	}

	interface URL_EVENT {

		String SERACH = "search";

		String JSON_APP = "application/json";

	}

	interface DeptCode {

		String WATER = "WT";
		String ESTATE_MANAGEMENT = "EST";
	}

	interface LookUp {
		String HIERARCHICAL = "H";
		String NON_HIERARCHICAL = "N";
		String HIRACHICAL_LEVEL_MAP = "HIRACHICAL_LEVEL_MAP";
		String HIRACHICAL_DETAIL_MAP = "HIRACHICAL_DETAIL_MAP";
		String NON_HIRACHICAL_DETAIL_MAP = "NON_HIRACHICAL_DETAIL_MAP";
		String HIRACHICAL_LIST = "HIRACHICAL_LIST";
		String NON_HIRACHICAL_LIST = "NON_HIRACHICAL_LIST";

		/*** For Charge Master [START] ***/

		String TCD = "TCD";
		String DISTRICT = "DIS";

		String TOOL_BAR_IMAGE = "TIM";

		String SECTION_TYPE = "EST";

		String EIP_IMAGE_TYPE = "EIL";

	}

	/*** For Charge Master [START] ***/

	interface IsLookUp {
		String ACTIVE = "A";
		String INACTIVE = "I";

		interface STATUS {
			String YES = "Y";
			String NO = "N";
		}
	}

	/**
	 * Use this Constant where ever you need default value for long type
	 */
	interface TypeId {

		long ZERO = 0l;

	}

	interface IsDeleted {

		String DELETE = "Y";
		String NOT_DELETE = "N";
		String ZERO = "0";
		String SUSPEND = "S";

	}

	interface AuthStatus {

		String APPROVED = "A"; // flag to identify whether requested
		// application is approved by Government
		// Body
		String REJECTED = "R"; // flag to identify whether requested
		// application is rejected
		String ONHOLD = "H"; // flag to identify whether requested
		// application has put on Hold

		String REJECT = "REJECT";
		// application has reject.

		String HOLD = "HOLD";
	}

	interface LookUpPrefix {
		String PASSWORD_VALID_DAYS = "PVD";
		String PFR = "PFR";
	}

	public enum PasswordValidType {
		CITIZEN("C"), EMPLOYEE("E"), AGENCY("A");

		private String prifixCode;

		private PasswordValidType(String prifixCode) {
			this.prifixCode = prifixCode;
		}

		public String getPrifixCode() {
			return prifixCode;
		}
	}

	interface IsUploaded {
		String UPLOADED = "Y"; // Flag to identify whether Agency has
		// uploaded the documents or not.
		String NOT_UPLOADED = "N";

	}

	interface Transaction {
		interface Mode {
			String ADD = "A";
			String UPDATE = "U";
			String DELETE = "D";
		}
	}

	interface Organisation {
		String SUPER_ORG_STATUS = "Y";
	}

	interface NEC {
		String PARENT = "NEC";
		String CITIZEN = "C";
		String BUILDER = "B";
		String ARCHITECT = "A";
		String CENTER = "CFC";
		String CYBER = "CC";
		String HOSPITAL = "HS";
		String CREMATORIA = "CRM";
		String ENGINEER = "G";
		String EMPLOYEE = "E";
		String ADVOCATE = "L";
		String ADVERTISE = "AGN";
		String TOWN_PLANNER = "D";
		String STRUCTURAL_ENGINEER = "F";
		String SUPERVISOR = "E";
		String REVIEWER = "R";

	}

	interface DEPARTMENT {
		String ONLINE_SERVICES_CODE = "ONL";
		String ONLINE_DEPT_DESC = "Online Service";
		String CFC_CODE = "CFC";
	}

	interface STATUS {
		String ACTIVE = "A";
		String INACTIVE = "I";
		String CANCEL = "C";

	}

	int OTP_PRASSWORD_LENGTH = 6;
	int OTP_VALIDITITY_IN_MINS = 5;

	/***
	 * Defines constants for field type and their respective count in database.
	 */
	int TEXT_FIELD = 1;
	int TEXT_FIELD_COUNT = 12;
	String TEXT_FIELD_FACE = "TextField";
	int TEXT_AREA = 2;
	String TEXT_AREA_FACE = "TextArea";
	int DROP_DOWN_BOX = 3;
	int DROP_DOWN_BOX_COUNT = 12;
	String DROP_DOWN_BOX_FACE = "DropDown";
	int DATE_PICKER = 4;
	int DATE_PICKER_COUNT = 4;
	String DATE_PICKER_FACE = "DatetimePicker";
	int ATTACHMENT_FIELD = 5;
	int ATTACHMENT_FIELD_COUNT = 2;
	String ATTACHMENT_FIELD_FACE = "Attachment";
	int PROFILE_IMG = 9;
	int PROFILE_FIELD_COUNT = 1;
	String PROFILE_FIELD_FACE = "Photo";
	int VIDEO = 6;
	String VIDEO_FACE = "Video";
	int TEXT_AREA_HTML = 8;
	String TEXT_AREA_HTML_FACE = "HTMLTextArea";
	int LINK_FIELD = 10;
	int LINK_FIELD_COUNT = 2;
	String LINK_FIELD_FACE = "Link";

	int VALIDITY_DATE_CODE = 20;
	String VALIDITY_DATE = "Archival Date";

	String[] excludePath = { "profile_img_path", "image", "imageName", "cfcAttachments" };
	String MODEL_NAME = "ChecklistModel|WaterRateMaster";
	String DEFAULT_EXCEPTION_FORM_VIEW = "defaultExceptionFormView";
	String DEFAULT_EXCEPTION_VIEW = "defaultExceptionView";
	String SUCCESS = "success";
	String FAIL = "fail";
	String Source = "Mob";
	String AUTH = "Y";
	String UNAUTH = "N";
	String ACTIVE = "Active";
	String INACTIVE = "InActive";
	String Isdeleted = "0";
	String YES = "Y";
	String NO = "N";
	String CARE_REQUEST = "careRequest";
	String EMPLOYEE = "EMPLOYEE";
	String FORMAT_DDMMYYYY = "dd/MM/yyyy";
	String FORMAT_mmddyyyy = "mm/dd/yyyy";
	String FORMAT_DDMMMYY = "dd-MMM-yy";
	String FORMAT_DDMMMYYYY = "dd-MMM-yyyy";
	String FORMAT_YYYYMMDD = "yyyy-MM-dd";
	String FORMAT_DDMMMYYYY_HHMM = "dd-MMM-yyyy HH:mm";
	String FORMAT_YYYYMMDD_HHMM = "yyyy-MM-dd HH:mm";
	String FORMAT_YYYYMMDD_HHMMSS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
	String FORMAT_MMYY = "MMM-yy";
	String FORMAT_DDMMYY_HHMMSS_A = "dd-MM-yyyy hh:mm:ss a";
	int DOLLAR = 1;
	String DISPLAY_NAME = "DISPLAY_NAME";
	String WORKFLOW_MAILID = "myworkflows@ABMpharma.com";
	int FIRST_FISCAL_MONTH = Calendar.MARCH;
	String INR = "INR";
	String TRAVEL_BAPI = "ZBAPI_CONVNC_TO_TBL";
	String NEWLINE = "\r\n";
	int MAX_FILE_CHARACTER_TO_BE_DISPLAY = 18;
	String FILES_UPLOADED = "Files uploaded";
	String PUBLISH = "P";

	public String applicationName = "/FileNetApplicationWar";
	public String downloadURL = "/Download";
	public String uploadURL = "/MultipartUpload";
	public String fileNameListURL = "/FileDetails";
	public String MONTH = "month";
	String PAGE = "page";
	String ROWS = "rows";
	String SIDX = "sidx";
	String SORD = "sord";
	String SEARCH_FIELD = "searchField";
	String SEARCH_OPER = "searchOper";
	String SEARCH_STRING = "searchString";

	interface EIPSection {
		String DEFAULT_PAGE_URL = "SectionInformation.html";
	}

	/**
	 * To define file upload directory constants.
	 */
	interface DirectoryTree {
		String DEFAULT_CACHE_FOLDER = "cache";

		String EIP = "EIP";
		String SLIDER_IMAGE = "SLIDER";
		String QUICK_LINK = "QUICKLINK";
	}

	interface PAYMENT_STATUS {
		String SUCCESS = "success";
		String PENDING = "pending";
		String FAILURE = "failure";
		String PEND_ING = "P";
		String COMMPLETE = "C";
	}

	interface PAYMENT {
		String OFFLINE = "N";
		String ONLINE = "Y";
		String FREE = "F";
		String PAY_AT_ULB_COUNTER = "P";

		interface PaymentMode {

			String REBATE = "R";
			String TRANSFER = "T";
			String ADJUSTMENT = "A";
			String USER_ADUSTMENT = "U";
			String ONLINE_PAYMENT = "ONL";
			String DEMAND_DRAFT = "D";
			String CASH = "C";
			String CHEQUE = "Q";
			String BANK = "B";
			String RAIN_WATER = "RWH";
		}
	}

	interface PAYMENT_SERVICE {
		String LOI_PAY = "LOI";
		String RTI_APP = "RTI_APP";
		String BIRTH_REG = "BIRTH_REG";
		String BIRTH_CORRECTION = "BIRTH_CORRECTION";
		String DEATH_CORRECTION = "DEATH_CORRECTION";
		String DEATH_REG = "DEATH_REG";
		String BIRTH_REG_CERT = "BIRTH_REG_CERT";
		String DEATH_REG_CERT = "DEATH_REG_CERT";
		String INCLUSION_CHILD = "INCLUSION_CHILD";
		String ADOPTION_CHILD = "ADOPTION_CHILD";
		String BIRTH_CERT = "BIRTH_CERT";
		String DEATH_CERT = "DEATH_CERT";
		String TP_ADP = "TP_ADP";
		String STILL_BIRTH = "STILL_BIRTH";

	}

	interface DEPT_SHORT_NAME {
		String PROP_TAX = "AS";
		String ONLINE_SERVICE = "ONL";
		String EIP = "EIP";
		String MARRIAGE_CERTIFICATE = "MC";
		String BIRTH_CERTIFICATE = "BC";
		String DEATH_CERTIFICATE = "DC";
		String WATER = "WT";
		String DEPUTY_MAYOR = "DM";
		String MAYOR = "M";
		String COMPLAINT = "CM";
		String RTI = "RTI";
		String MAYOR_PROFILE = "Mayor";
		String COMMISSIONER_PROFILE = "Commissioner";
	}

	interface PG_URL {
		String ADVERTISE_AGENCY_REGISTRATION = "AdvertiseAgencyRegistration.html";
	}

	interface EIP_CHKLST {
		String CITIZEN = "CitizenRegistration.html";
		String RESET_PWD_SUCC = "PasswordReset.html";
		String COMPLAINT = "grievance.html";
		String HOME = "CitizenHome.html";
	}

	interface Advertise {
		String SUCCESS = "success";
	}

	interface SCRUTINY_COMMON_PARAM {
		String SCRUTINY_APPL_ID = "SCRUTINY_APPL_ID";

	}

	interface DASHBOARD {

		String PP = "PP";
		String AC = "AC";
		String P = "P";
		String N = "N";
		String COMPLETED = "COMPLETED";
		String CL_REJECTED = "CHECKLIST REJECTED";
		String NEW_WATER_CONNECTION = "WNC";

		String HOLD = "Hold";
		String PENDING = "Pending";
		String CLOSE = "Close";
		String LOI_GENERATED = "LoiGenerated";
		String CLOSED = "CLOSED";
		String DASH_BOARD_FEEDBACK = "DASH_FEEDBACK";

	}

	interface SMS_EMAIL {
		String NORMAL = "N";
		String OTP_MSG = "OM";
		String GENERAL_MSG = "GM";
		String APPROVAL = "AA";
		String REJECTED = "AR";
		String HOLD = "AF";
		String SUBMITTED = "AS";
		String GENERAL = "GM";
		String COMPLETED = "CM";
		String TASK_NOTIFICATION = "TN";
	}

	interface FileExt {
		String PDF = "pdf";
		String XLS = "xls";
		String XLSX = "xlsx";
		String DOC = "doc";
		String DOCX = "docx";

	}

	interface FileTypes {
		char OTHER = 'O';
		char PDF = 'P';
		char DOC = 'D';
		char XLS = 'X';
	}

	interface REG_ENG {
		String REGIONAL = "REG";
		String ENGLISH = "ENG";
	}

	interface D2KFUNCTION {
		String ENGLISH_DESC = "E";
		String REG_DESC = "R";
		String CPD_VALUE = "V";
		String CPD_OTHERVALUE = "O";

	}

	interface FileCount {
		String ZERO = "0";
		String ONE = "1";
	}

	interface HelpDoc {
		String HELPDOC = "HELP_DOCS";
	}

	interface MOBILE_NUMBER_IDENTIFICATION {
		String REGISTERED_NUMBER = "Register";
		String NON_REGISTERED_NUMBER = "Nonregister";
	}

	interface NUMBERS {
		int MINUS_ONE = -1;
		int ONE = 1;
		int TWO = 2;
		int THREE = 3;
		int FOUR = 4;
		int FIVE = 5;
		int SIX = 6;
		int SEVEN = 7;
	}

	interface RegEx {

		String FIRST_NAME = "^[a-zA-Z\\s]*$"; // allow only character including space

		String ENGLISH_REG_EX = "^[0-9a-zA-Z\\s.,-;()'\"////]*$";

		String MARATHI_REG_EX = "^[ 0-9A-Za-z#$ %=@!{},\"`~&*()'<>?.:;_|ÈÉÊËÌÍÎÏÐÑ¡¢£¤¥¦§¨©ª«¬®¯°±²³´µ¶·¸¹º»¼½¾¿’‚“”„ÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåœŠš›™!*+,.|~€Žž­ƒˆ˜–—‘’‚“”„†‡•…‰‹\\\\^/+\\t\\r\\n\\[\\]-]*";

	}

	interface AGENCY {

		interface URL {
			String BUILDER = "BuilderRegistration.html";
			String CYBER_CAFE = "CyberAgencyRegistration.html";
			String CREMATORIA = "CrematoriaRegistration.html";
			String HOSPITAL = "HospitalRegistration.html";
			String ADVOCATE = "AdvocateRegistration.html";
			String REGISTRATION = "Registration.html";
		}

		interface NAME {

			String CITIZEN = "CITIZEN";
			String BUILDER = "BUILDER";
			String ARCHITECT = "ARCHITECT";
			String CYBER = "CYBER";
			String HOSPITAL = "HOSPITAL";
			String CREMATORIA = "CREMATORIA";
			String ENGINEER = "ENGINEER";
			String EMPLOYEE = "EMPLOYEE";
			String ADVOCATE = "ADVOCATE";
			String ADVERTISE = "ADVERTISE";
			String ADMIN = "ADMIN";
			String CFC = "CFC";

		}
	}

	interface QUARTZ_SCHEDULE {
		String JOB_DEFINATION = "JobDefination";

		String JOB_CLASS_NAME = "className";

		String JOB_METHOD_NAME = "processQuartzJob";
		String JOB_GROUP = "jobGroup";
		String TRIGGER_NAME = "trigger_";
		String JOB_DATA = "jobData";

		interface JobFrequency {
			String DAILY = "D";
			String WEEKLY = "W";
			String MONTHLY = "M";
			String QUARTERLY = "Q";
			String HALF_YEARLY = "HY";
			String YEARLY = "Y";
			String ONE_TIME = "OT";
			String REPEAT_DAILY = "1";
			String REPEAT_QUARTERLY = "3";
			String REPEAT_HALF_YEARLY = "6";
		}

		interface CRON_EXPR {
			int CRON_EXPRESSION_DAILY = 1;
			String FORWARD_SLACE = "/";
			String ASTERISK = "*";
			String QUESTION_MARK = "?";
			String MONTH_WISE = "1";
			String REPEAT_EVERY_MONTH = "1";
			String REPEAT_LAST_DAY_OF_MONTH = "L";
		}

	}

	interface COMMON_NOTICE_TYPE {
		interface URL_MAPPING {
			String MARKET_NOTICE_URL = "MarketShowCauseNotice.html";
		}
	}

	interface SEARCH_PROPERTY_DETAILS {
		interface NAME {
			String SEARCH_NOUSER_ENG = "search-content-nouser-login.properties";
			String SEARCH_NOUSER_REG = "search-content-nouser-login_reg.properties";
			String SEARCH_USER_ENG = "search-content-user-login.properties";
			String SEARCH_USER_REG = "search-content-user-login_reg.properties";
			String SEARCH_SPECIAL_CHAR_PRESENT = "?*";
			String SEARCH_SPECIAL_CHAR_REPLACE = "=";
		}

	}

	interface SECURITY_FLAG {
		String CLICK_JACKING_CHECK = "clickjacking.check";
	}

	interface Common_Constant {

		String YES = "Y";
		String NO = "N";
		String ACTIVE_FLAG = "A";
		String INACTIVE_FLAG = "I";
		long ZERO_LONG = 0l;
		String ZERO_SEC = "0";
		String ACTIVE = "Active";
		String INACTIVE = "InActive";
		String FREE = "F";
		String PAGE = "page";
		String Doon_Smart_City = "UAD";
		String PORTAL_UPLOAD_KDMC_EXTENSION="PORTAL_UPLOAD_VALIDATION_EXTENSION";

		interface NUMBER {

			String ZERO_ZERO = "00";
			String ZERO_ONE = "01";
			String ZERO_TWO = "02";
			String ZERO_THREE = "03";
			String ZERO_FOUR = "04";
			String ZERO_FIVE = "05";
			String ZERO_SIX = "06";
			String ZERO_SEVEN = "07";
			String ZERO_EIGHT = "08";
			String ZERO_NINE = "09";
			String ZERO = "0";
			String ONE = "1";
			String TWO = "2";
			String THREE = "3";
			String FOUR = "4";
			String FIVE = "5";
			String SIX = "6";
			String SEVEN = "7";
			String EIGHT = "8";
			String NINE = "9";
			String TEN = "10";
			String ELEVEN = "11";
			String TWELVE = "12";
		}

		interface MONTH {

			String JAN = "JAN";
			String FEB = "FEB";
			String MAR = "MAR";
			String APR = "APR";
			String MAY = "MAY";
			String JUN = "JUN";
			String JUL = "JUL";
			String AUG = "AUG";
			String SEP = "SEP";
			String OCT = "OCT";
			String NOV = "NOV";
			String DEC = "DEC";
		}

		interface WEEK {

			String MON = "MON";
			String TUE = "TUE";
			String WED = "WED";
			String THU = "THU";
			String FRI = "FRI";
			String SAT = "SAT";
			String SUN = "SUN";
		}

		interface INDEX {

			int ZERO = 0;
			int ONE = 1;
			int TWO = 2;
			int THREE = 3;
			int FOUR = 4;
			int FIVE = 5;
			int SIX = 6;

		}

	}

	interface QueryAttributes {
		String AGENCY = "A";

	}

	interface ApplicationMasterConstant {

		String PAYMENT_STATUS_FREE_SERVICE = "F";
		String PAYMENT_STATUS_BEFORE_PAYMENT_EMPTY = "";

		String APPLICATION_STATUS_PENDING = "PENDING";
		String APPLICATION_STATUS_OPEN = "OPEN";
		String APPLICATION_STATUS_APPROVED = "APPROVED";
		String APPLICATION_STATUS_REJECTED = "REJECTED";
		String APPLICATION_FORCE_CLOSURE = "FORCE_CLOSURE";

		String APPLICATION_STATUS_FA_OPEN = "FA OPEN";
		String APPLICATION_STATUS_FA_REJECTED = "FA REJECTED";
		String APPLICATION_STATUS_FA_APPROVED = "FA APPROVED";
		String APPLICATION_STATUS_FA_RESCHEDULE = "FA RESCHEDULE";

		String APPLICATION_STATUS_SA_OPEN = "SA OPEN";
		String APPLICATION_STATUS_SA_RESCHEDULE = "SA RESCHEDULE";

		String FA_APPLICATION_STATUS_PENDING = "FA HEARING PENDING";
		String SA_APPLICATION_STATUS_PENDING = "SA HEARING PENDING";

		String CERTIFICATE_APPLICATION_STATUS_COMPLETE = "COMPLETED";
		String CLOSED = "Closed";
	}

	interface HearingAndAppealConstant {
		String FIRST_APPEAL = "FIRST APPEAL";
		String SECOND_APPEAL = "SECOND APPEAL";
	}

	interface SmsAndEmailConstant {
		String TRANSACTION_TYPE_TRANSACTIONAL = "Y";
	}

	interface WebServiceStatus {

		String SUCCESS = "success";
		String FAIL = "fail";
	}

	interface NewWaterServiceConstants {
		String TRF = "TRF";
		String WNC = "WNC";
		String WND = "WND";
		String NO = "N";
		String YES = "Y";
		String SUCCESS = "success";
		String WWZ = "WWZ";
		String WR_ZONE = "applicantDetailDto.dwzid";
		String DEFAULT_EXCEPTION_VIEW = "defaultExceptionFormView";
		String CCG = "CCG";
		String VIEW = "view";
		String SERVICE_NAME = "New Water Connection Form";
		String CHECK_PROPERTY_DUES="CPD";
		String AT_TIME_OF_APPLICATION="TOA";
		String BOTH="BOT";
		String TFM = "TFM";
		String CAA = "CAA";
		String NA = "NA";
		String APPLICATION_TYPE = "T";
		String APL = "APL";
		String OPEN = "O";
		String CSZ = "CSZ";
		String APP = "APP";
		String CAN = "CAN";
		String APPL_G = "G";
		String APPL_TR = "TR";
		String ANY_PLUMBER = "Any Available";

		interface PROPERTY_INTEGRATION {
			String REQUEST_PROPNO = "assNo";
			String REQUEST_ORGID = "orgId";
			String RESPONSE_AMOUNT = "totalPayableAmt";
			String RESPONSE_PROPNO = "propNo";
		}

	}

	interface ServiceShortCode {
		String PlUMBER_LICENSE = "WPL";
		String WATER_BILL_PAYMENT = "WPL";
		String WATER_NO_DUES = "WND";
		String WATER_RECONN = "WRC";
		String WATER_CHANGE_OWNER = "WCO";
		String WATER_CHANGE_USAGE = "WCU";
		String LIC_TECH_PERSON = "LTP";
		String RNL_ESTATE_BOOKING = "ESR";
		String ESTATE_CANCELLATION = "EBC";
		String RNL_WATER_TANKER_BOOKING = "WTB";

	}

	interface DmsClientType {
		String DMS_CLIENT_REST = "REST";
		String DMS_CLIENT_SOAP = "SOAP";
	}

	interface CHALLAN_RECEIPT_TYPE {
		String NON_REVENUE_BASED = "N";// 'N' stands for only service charges without bill charges at application time
		String REVENUE_BASED = "Y"; // 'Y' stands for only bill without service charge at application time
		String MIXED = "M";// 'M' stands for service with application/service update as well as bill update
							// at application time
	}

	interface PAYMENT_TYPE {
		String OFFLINE = "N";
	}

	interface EASYPAY_PARAM {
		String CID = "CID"; // Corporate ID
		String RID = "RID"; // Reference ID
		String CRN = "CRN"; // Customer Reference Number
		String AMT = "AMT"; // Amount to be paid
		String VER = "VER"; // Version of the payment gateway
		String TYP = "TYP"; // Type of gateway(environment)
		String CNY = "CNY"; // Type of currency
		String RTU = "RTU"; // Response URL
		String PPI = "PPI"; // Form fields which will be displayed on the web page
		String RE1 = "RE1"; // Pass the values as “MN” to avoid any modification
		String RE2 = "RE2"; // Additional Data can be added
		String RE3 = "RE3"; // Additional Data can be added
		String RE4 = "RE4"; // Additional Data can be added
		String RE5 = "RE5"; // Additional Data can be added
		String CKS = "CKS"; // checksum:-Concatenation of (CID + RID + CRN + AMT + key)
		String EQUALS_SYMB = "=";
		String EASYPAY_ENCRYPT_DECRYPT_KEY = "axisbank12345678"; // encrypt/decrypt key
		String STC = "STC"; // The status code for this transaction
		String SUCCESS_CODE = "000";
		String FAILURE_CODE = "111";
		String TET = "TET"; // Transaction Date and Time
		String RMK = "RMK"; // The status message for this transaction
		String BRN = "BRN"; // Reference number generated by the bank post transaction
		String PMD = "PMD"; // The mode of payment used for performing transaction
		String TRN = "TRN"; // Unique Transaction ID
		String Encrypted_req_response = "i";
		String IN_PROCESS_OR_PENDING = "In Process/Pending";
		String CHECK_PAYMENT_STATUS = "CPS";
	}

	interface PAY_PREFIX {
		String PREFIX_VALUE = "PAY";
		String BANK = "B";
		String ONLINE = "W";
	}

	interface Common {
		String EMP_MOB_NO = "empmobno";
		String ORGID = "orgId";
		String SMS_SERVICE = "smServiceId";
		String FROMDATE = "fromDate";
		String TODATE = "toDate";

	}

	interface RNL_Common {

		String F_FLAG = "F";
		String CHECKLIST_RNL_MODEL_NAME = "ChecklistModel|RNLRateMaster";

	}

	interface EstateBooking {

		String CATEGORY_PREFIX_NAME = "ETY";
		int LEVEL = 2;
		String RNL_DEPT_CODE = "RNL";
		String TAC_PREFIX = "TAC";
		String APL_PREFIX = "APL";
		String CAA_PREFIX = "CAA";
		String SHIFT_PREFIX_GENERAL = "GENERAL";
		String PASS = "pass";
		String ESTATE_BOOKING_HOME = "estateBookingHome";
		String WATER_TANKER_BOOKING_HOME = "waterTankerBookingHome";
		String DUPLICATE_RECIEPT_PRINT_SUMMARY = "duplicateReceiptPrintSummary";
		String propName = "propertyName";
		String RECEIPTID = "receiptId";

	}

	interface ServiceCode {
		String WATER_DISCONNECTION = "WCC";
		String CHANGE_OF_USAGE = "WCU";
		String WATER_BILL_PAYMENT = "BPW";
		String PORTAL = "P";
	}

	interface INDEX {

		int TWO = 2;

	}

	interface GrievanceConstants {

		String CARE_REQUEST = "command";
		String CARE_REQUEST_DTO = "careRequestDto";
		String APPLICATION_ID = "applicationId";
		String DEPT_ID = "deptId";
		String APPL_ID = "applId";
		String APP_ID = "appId";
		String TASKNAME = "taskName";
		String LANG = "lang";
		String REQUESTOR_ACTION = "Requester Action";
		Map<String, String> RATINGS = GrievanceConstants.createMap();
		String PROCESS_VERSION = "1.0";
		String RESPONSE_ = "response";
		String ALERT_SAVE_SUCCESS = "alert.saveSuccess";
		String SUBMITTED_ = "Submitted";
		String DECISION = "decision";
		String REQUEST_NO = "requestNo";
		String PINCODE = "pinCode";
		String FEEDBACK_SAVED_SUCCESSFULLY = "Feedback submitted successfully.";
		String PNG = "png";
		String CONTENT_TYPE_PNG = "image/png";
		String REOPENCOMPLAINTMODE = "reopenComplaintMode";
		String COMPLAINTACKNOWLEDGEMENTMODEL = "complaintAcknowledgementModel";
		String COMPLAINTACKNOWLEDGEMENTMODELLIST = "complaintAcknowledgementModelList";
		String DEPARTMENTS = "departments";
		String LOCATIONS = "locations";
		String REQUESTLISTS = "requestLists";
		String REOPENGRIEVANCEFORM = "ReopenGrievanceform";
		String REOPENGRIEVANCEFORMVALID = "ReopenGrievanceformValid";
		String SEARCHSTRING = "searchString";
		String EMPTYTOKEN = "emptyToken";
		String TOKENNUMBER = "tokenNumber";
		String MOBILENUMBER = "mobileNumber";
		String RATING_INPUT = "ratinginput";
		String RATING_CONTENT = "ratingcontent";
		String FEEDBACK_ID = "feedbackId";
		String REGISTRATIONACKNOWLEDGEMENTRECEIPTSTATUS = "RegistrationAcknowledgementReceiptStatus";
		String GRIEVANCECOMPLAINTACKLIST = "grievanceComplaintAckList";
		String GRIEVANCERESUBMISSION = "grievanceResubmission";
		String REGISTRATIONACKNOWLEDGEMENTRECEIPT = "RegistrationAcknowledgementReceipt";
		String TOKENDOESNTEXISTS = "tokenDoesntExists";
		String COMPLAINTFEEDBACK = "ComplaintFeedback";
		String FEEDBACKDETAILS = "feedbackDetails";
		String NEWCOMPLAINTREGISTRATIONFORM = "NewComplaintRegistrationForm";
		String NEWCOMPLAINTREGISTRATIONFORMPRELOGIN = "NewComplaintRegistrationFormPreLogin";
		String NEWCOMPLAINTREGISTRATIONFORMVALID = "NewComplaintRegistrationFormValid";
		String ACTIONRESPONSE = "actionResponse";
		String REGISTRATIONSTATUS = "RegistrationStatus";
		String REGISTRATIONSTATUSPRELOGIN = "RegistrationStatusPreLogin";
		String GRIEVANCE_CONTROLER = "grievance.html";
		String GRIEVANCE_RESUBMISSION_CONTROLER = "GrievanceResubmission.html";
		String SAVE_DETAILS = "saveDetails";
		String SAVE_REOPEN_DETAILS = "saveReopenDetails";
		String GET_ALL_GRIEVANCE_RAISED_BY_REGISTERED_CITIZEN_VIEW = "getAllGrievanceRaisedByRegisteredCitizenView";
		String SEARCH_GRIEVANCE_RAISED_BY_REGISTERED_CITIZEN = "searchGrievanceRaisedByRegisteredCitizen";
		String GRIEVANCE_STATUS = "grievanceStatus";
		String DISPLAY_STATUS = "displayStatus";
		String GET_ACTION_HISTORY_BY_APPLICATIONID = "getActionHistoryByApplicationId";
		String FIND_DEPARTMENT_COMPLAINT_BY_DEPARTMENTID = "findDepartmentComplaintByDepartmentId";
		String GET_CARE_WORKFLOWMASTER_DEFINED_DEPARTMENT_COMPLAINTTYPE_BY_DEPARTMENTID = "getCareWorkflowMasterDefinedDepartmentComplaintTypeByDepartmentId";
		String SHOW_FEEDBACK_DETAILS = "showFeedbackDetails";
		String SAVE_FEEDBACK_DETAILS = "saveFeedbackDetails";
		String GET_PINCODE_BY_LOCATION_ID = "getPincodeByLocationId";
		String GET_ALL_LOCATIONS_BY_PINCODE = "getAllLocationsByPinCode";
		String GET_UPLOADDED_FILES = "getUploaddedFiles";
		String GRIEVANCE_COMPLAINT_TYPES = "grievanceComplaintTypes";
		String GRIEVANCE_LOCATIONS = "grievanceLocations";
		String VALIDATE_MOBILE_OTP = "validateMobileOtp";
		String SEND_OTP = "sendOTP";
		String ORGANISATION = "organisation";
		String DEPARTMENT = "department";

		static Map<String, String> createMap() {
			final Map<String, String> ratings = new HashMap<>();
			ratings.put("1", "Dissatisfied");
			ratings.put("2", "Dislike");
			ratings.put("3", "Its Ok");
			ratings.put("4", "Liked It");
			ratings.put("5", "Satisfied");
			return ratings;
		}

		interface ValidationMessage {
			String TITLE = "care.validation.error.title";
			String GENDER = "care.validation.error.gender";
			String FIRSTNAME = "care.validation.error.firstName";
			String LASTNAME = "care.validation.error.lastName";
			String MOBILENUMBER = "care.validation.error.mobileNumber";
			String MOBILENUMBER_LENGTH = "care.validator.mobile.nolength";
			String AREANAME = "care.validation.error.areaName";
			String CITY = "care.validation.error.city";
			String PINCODE = "care.validation.error.pincode";
			String DEPARTMENT = "care.validation.error.department";
			String COMPLAINTTYPE = "care.validation.error.complaintType";
			String DESCRIPTION = "care.validation.error.description";
			String LOCATION = "care.validation.error.location";
			String LOCATION_LANDMARK = "care.validation.error.skdcllandmarkLoc";
			String ORG = "care.validation.error.organisation";
			String OTP_EMPTY = "care.validation.error.otpEmpty";
			String OTP_INVALID = "care.validation.error.otpInvalid";
			String DISTRICT = "care.validation.error.district";
			String MODE = "care.validation.error.mode";
			String REFCATAGORY = "care.validation.error.refcatagory";
			String ZONE = "care.validation.error.zone";
			String ZONE2 = "care.validation.error.zone2";
			String ZONE3 = "care.validation.error.zone3";
			String SKDCLCOMTYPE = "care.validn.skdclCompType";
			String SKDCLCOMSUBTYPE = "care.validn.skdclCompSubType";

		}

	}

	interface NAMED_QUERY {
		String UPDATE_PAYMENT_STATUS = "updatePaymentStatusFlag";

		String SELECT_SERVICE_SHORT_NAME = "selectServiceShortName";

		String UPDATE_LOI_RECEIPT_FLAG = "updateLOIReceiptFlag";
	}

	interface PAY_STATUS_FLAG {
		String YES = "Y";
		String NO = "N";
	}

	interface PAYU_STATUS {
		String SUCCESS = "Success";
		String FAIL = "failure";
		String CANCEL = "cancelPayU";
		String ABORTED = "Aborted";

	}

	interface PG_SHORTNAME {
		String PAYU = "PAYU";
		String TECH_PROCESS = "TECHPROCESS";
		String MAHA_ONLINE = "MAHAONLINE";
		String HDFC = "HDFC";
		String CCA = "CCAvenue";
		String AWL = "Worldline(PNB)";
		String EASYPAY = "Easypay(Axis-Bank)";
		String ICICI = "ICICI";
		String BILLCLOUD = "BILLCLOUD";
		String BILLDESK = "BILLDESK";
		String RAZORPAY = "RAZORPAY";
		String ATOMPAY = "ATOM";
		String EASEBUZZ = "Easebuzz";
		String ISGPAY = "ISGPay";
		String NicGateway = "NicGateway";

	}

	interface PG_REQUEST_PROPERTY {
		String REQUESTTPYE = "Y";
		String TXN = "TXN";
		String CRN = "INR";
		String SERVERTIMEOUT = "1000";
		String PAYMNETSTATUS = "0300";

	}

	interface REQUIRED_PG_PARAM {

		String OWNER_NAME = "ownerName";
		String DUE_AMT = "dueAmt";
		String SERVICE_ID = "serviceId";
		String PHONE_NO = "phone";
		String AMT = "amount";
		String SESSION_AMT = "sessionAmount";
		String STATUS = "status";
		String TRANS_ID = "mihpayid";
		String TRACK_ID = "txnid";
		String ERR_NO = "error";
		String ERR_STR = "error_Message";
		String NET_AMT = "net_amount_debit";
		String MODE = "mode";
		String KEY = "key";
		String MERCH_RESP = "merchantResponse";
		String PAY_SRC = "payment_source";
		String BNK_REF_NO = "bank_ref_num";
		String PAYDATE = "addedon";
		String EXCEPTION = "exception";
		String BANKID = "bankId";
		String BANKTXNID = "banktxnId";
		String SUCEESS_MSG = "SuccessMessage";
		String FIELD9 = "field9";
		String PG_ID = "pg_id";
		String RECEIPT_NO = "receiptNo";
		String APPLICATION_NO = "applicationId";
		String UDF1 = "udf1";
		String UDF2 = "udf2";

		String UDF5 = "udf5";
		String UDF6 = "udf6";
		String UDF7 = "udf7";
		String UDF8 = "udf8";
		String UDF9 = "udf9";

		String APP_ID_LABEL = "app_no_label";

		String NA = "NA";
		String MSG = "msg";
		String IV = "iv";
		String LANG = "lang";
		String CANCEL = "?cancelPayU";
		String SUCCESS_PAYU = "?payuSuccess";
		String CANCEL_PAYU = "?payuCancel";
		String FAIL_PAYU = "?payuFail";
		String SUCCESS_TP = "?tpSuccess";
		String FAIL_TP = "?tpFail";
		String CANCEL_TP = "?tpCancel";
		String SUCCESS_HDFC = "?hdfcSuccess";
		String FAIL_HDFC = "?hdfcFail";
		String CANCEL_HDFC = "?hdfcCancel";
		String SUCCESS_HDFC_FSS = "?hdfcFssSuccess";
		String SUCCESS_MH = "?mhSuccess";
		String FAIL_MH = "?mhFail";
		String CANCEL_MH = "?mhCancel";
		String FAIL = "?failPayU";
		String PAYU = "redirectToPayUPG";
		String HDFC = "redirectToHDFCPG";
		String MOL = "redirectToMHPG";
		String TP = "redirectToTPPG";
		String COMMAND = "command";
		String ORGID = "orgId";
		String ORGNAME = "orgName";
		String ERR_MSG = "errMsg";
		String CCA = "redirectToCCA";
		String AWL = "redirectToAWL";
		String Easypay = "redirectToEasypay";
		String ICICI = "redirectToICICIPG";
		String BILLCLOUD = "redirectToBillCloud";
		String UDF3 = "udf3";
		String UDF4 = "udf4";
		String SUCCESS_HDFC_CCA = "?hdfcCCARedirect";
		String CANCEL_HDFC_CCA = "?hdfcCCAcancel";
		String FAIL_HDFC_CCA = "?hdfcCCAfail";
		String SUCCESS_AWL = "?hdfcAWLRedirect";
		String CANCEL_AWL = "?hdfcAWLcancel";
		String FAIL_AWL = "?hdfcAWLfail";
		String DEFAULT_LANG = "EN";
		String MERCHANT_ID = "merchant_id";
		String ORDER_ID = "order_id";
		String orderNo = "orderNo";
		String CURRENCY = "currency";
		String REDIRECT_URL = "redirect_url";
		String CANCEL_URL = "cancel_url";
		String LANGUAGE = "language";
		String BILL_NAME = "billing_name";
		String MERCHANT_PER1 = "merchant_param1";
		String MERCHANT_PER2 = "merchant_param2";
		String MERCHANT_PER3 = "merchant_param3";
		String MERCHANT_PER4 = "merchant_param4";
		String MERCHANT_PER5 = "merchant_param5";
		String ACCESS_CODE = "access_code";
		String ENCRYPTED_REQUEST = "encRequest";
		String ENCRYPTED_RESPONSE = "encResp";
		String WORKING_KEY = "working_key";
		String FAILURE_MESSAGE = "failure_message";
		String RESPONSE_CODE = "response_code";
		String BANK_REF_NO = "bank_ref_no";
		String TRACKING_ID = "tracking_id";
		String ORDER_STATUS = "order_status";
		String STATUS_MESSAGE = "status_message";
		String PAYMENT_MODE = "payment_mode";
		String ORDER_NO = "orderNo";
		String SUCCESS_EASYPAY = "?easypayRedirect";
		String CANCEL_EASYPAY = "?easypayCancel";
		String FAIL_EASYPAY = "?easypayFail";
		String SUCCESS_ICICI_CCA = "?iciciCCARedirect";
		String CANCEL_ICICI_CCA = "?iciciCCAcancel";
		String FAIL_ICICI_CCA = "?iciciCCAfail";
		String SUCCESS_BILLCLOUD = "?billCloudRedirect";
		String CANCEL_BILLCLOUD = "?billCloudcancel";
		String FAIL_BILLCLOUD = "?billCloudfail";
		String BILLDESK = "redirectToBillDesk";
		String SUCCESS_RAZORPAY = "?redirectToRazorpay";
		String RAZORPAY = "redirectToRazorpay";
		String ATOMPAY = "redirectToAtompay";
		String EASEBUZZ = "redirectToEasebuzz";
		String ISGPAY = "redirectToISGPay";
		String NicGateway = "redirectToNICPay";
	
	}

	interface PAYMODE {
		String MOBILE = "M";
		String WEB = "W";
	}

	interface BankParam {
		String AMT = "amount";
		String TXN_ID = "txnid";
		String SALT = "salt";
		String PROD_INFO = "productinfo";
		String FNAME = "firstname";
		String KEY = "key";
		String EMAIL = "email";
		String PHONE = "phone";
		String SURL = "surl";
		String FURL = "furl";
		String CURL = "curl";
		String HASH = "hash";
		String PG = "pg";
		String UDF1 = "udf1";
		String UDF2 = "udf2";
		String UDF3 = "udf3";
		String UDF4 = "udf4";
		String UDF5 = "udf5";
		String UDF6 = "udf6";
		String UDF7 = "udf7";
		String UDF8 = "udf8";
		String UDF9 = "udf9";
		String UDF10 = "udf10";
		String SCHEME_CODE = "schemeCode";
		String PRODUCTION = "production";
		String REQUESTTYPE = "requestType";
		String SENDURL = "sendurl";
		String MID = "mId";
		String ORDER_ID = "orderId";
		String TRANS_AMT = "transactionAmount";
		String TRA_CIN = "transactionCurrency";
		String TRA_DEC = "transactionDescription";
		String TRANS_TYE = "transactionType";
		String RESP_URL = "responseURL";
		String ADD_F1 = "addField1";
		String ADD_F2 = "addField2";
		String ADD_F3 = "addField3";
		String ADD_F4 = "addField4";
		String ADD_F5 = "addField5";
		String ADD_F6 = "addField6";
		String ADD_F7 = "addField7";
		String ADD_F8 = "addField8";
		String ADD_F9 = "recurrPeriod";
		String ADD_F10 = "recurrDay";
		String ADD_F11 = "noOfRecurring";
		String SERVICE_SHORT_CODE = "serviceShortCode";
	}

	interface PrefixInfo {
		String SET = "SET";

		String SET_CODE = "CP";
	}

	interface FileParameters {
		String FILE_LIST_1 = "file_list_";

		String FILE_LIST_2 = "_file_";

		String FILE_LIST_3 = "file_";
	}

	interface HTML_CHARACTER {
		String UL_START = "<ul>";
		String UL_START_1 = "<ul style='margin-left: 104px;'>";
		String UL_END = "</ul>";

		String LI_START = "<li>";
		String LI_END = "</li>";

		String BOLD_START = "<b>";
		String BOLD_END = "</b>";

		String NBSP = "&nbsp;&nbsp;";

		String IMAGE_TAG_2 = "\" onclick=\"doFileDelete(this)\" >";

		String IMAGE_TAG_3 = "\" uniqueId=\"";

		String IMAGE_TAG_1 = "<img src=\"css/images/close.png\" alt=\"Remove\" width=\"17\" title=\"Remove\" id=\"";

		String INPUT = "input";

		String TYPE = "type";

		String CODE = "code";

		String CLASS = "class";

		String STYLE = "style";

		String EXT = "extension";

		String MAXSIZE = "maxSize";

		String CLASS_APPLY_1 = "fileUploadClass ";

		String CLASS_APPLY_2 = "customfileupload ";

		String STYLE_APPLY = "width: 86px;";
	}

	interface OperationMode {
		String PREFIX = "PREFIX";

		String SUFFIX = "SUFFIX";

		String START = "START";

		String END = "END";

	}

	interface ValidationMessageCode {
		String CLASS_NAME = "FileUpload";
		String SERVER_ERROR = "serverError";
		String EXTENSION_VALIDN = "extensionValidation";
		String FILE_NAME_EXISTS = "fileNameExists";
		String MAX_FILE_ERROR = "maxFileError";
		String FILE_SIZE_VALIDN = "fileSizeValidn";
		String FILE_UPLOAD_VALIDATION_MSG = "attachment";
		String FOR_CITI_REGISTRATION = "citizenRegistrationModel";
		String INVALID_FILE_ERROR = "invalidFileError";

	}

	interface FileContentType {
		String FILE_JPEG = "image/jpeg";
		String FILE_PNG = "image/png";
		String FILE_GIF = "image/gif";
		String FILE_BMP = "image/bmp";
		String FILE_PDF = "application/pdf";
		String FILE_DOC_XLS = "application/octet-stream";
		String FILE_PPT = "application/vnd.mspowerpoint";
	}

	interface Validation_Constant {

		String[] CHECK_LIST_VALIDATION_EXTENSION = { "pdf", "doc", "docx", "jpeg", "jpg", "png", "gif", "bmp", "rar",
				"zip" };
		String[] CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS = { "pdf", "doc", "docx", "xls", "xlsx" };
		String[] CHECK_LIST_VALIDATION_EXTENSION_HELPDOC = { "pdf" };
		String[] CHECK_LIST_VALIDATION_EXTENSION_TOOLTIP = {  "pdf", "doc", "docx" };
		String[] CHECK_LIST_VALIDATION_EXTENSION_DASHBOARD = { "xls", "xlsx" };
		String[] EXCEL_UPLOAD_VALIDATION_EXTENSION = { "xls" };
		String[] IMAGE_UPLOAD_VALIDATION_EXTENSION = { "jpeg", "jpg", "png", "gif", "bmp" };
		String[] TP_VALIDATION_EXTENSION = { "dxf", "dwg", "pdf", "zip", "rar" };
		String[] ALL_UPLOAD_VALID_EXTENSION = { "pdf", "doc", "docx", "jpeg", "jpg", "png", "gif", "bmp", "xls",
				"xlsx" };
		String[] CHECK_LIST_VALIDATION_EXTENSION_BND = { "pdf", "doc", "docx", "jpeg", "jpg" };
		String[] RTI_FILE_UPLOAD_EXT = { "pdf", "doc", "docx", "jpeg", "jpg" };
		String[] EXCEL_UPLOAD_EXTENSION = { "xlsx" };
		String[] PDF_UPLOAD_EXTENSION = { "pdf" };
		String[] CARE_VALIDATION_EXTENSION = { "pdf", "png", "jpg" };
		String[] CHECK_LIST_VALIDATION_EXTENSION_MRM = { "pdf", "jpeg", "jpg", "png" };
		String[] COMPLAINT_VALIDATION_EXTENSION = { "pdf", "doc", "docx", "jpeg", "jpg", "png", "gif" };
		String[] PORTAL_UPLOAD_VALIDATION_EXTENSION = { "pdf",  "jpg","jpeg", "bmp","png"  };
	}

	interface CheckList_Size {
		int COMMOM_MAX_SIZE = 2097152;

		int MAX_SIZE_100_MB = 104857600;

		int BND_COMMOM_MAX_SIZE = 5242880;

		int CHECK_COMMOM_MAX_SIZE = 20971520;

		int PORTAL_COMMON_SIZE = 15728640;

		int CARE_COMMON_MAX_SIZE = 1048576;

		int TRADE_COMMON_MAX_SIZE = 1048576;

		int MAX_SIZE_50_KB = 51200;

		int MAX_SIZE_150_KB = 153600;

		int MAX_SIZE_300_KB = 1048576;

		int MAX_SIZE_100_KB = 102400;
		
		int MIN_FILE_SIZE = 204800; //200KB
		
		int SKDCL_MAX_FILE_SIZE = 20971520; //20 MB

	}

	interface MAX_FILEUPLOAD_COUNT {
		int CHECK_LIST_MAX_COUNT = 1;

		int CHECKLIST_VRFN_ALL = 3;

		int CHECKLIST_MAX_UPLOAD_COUNT = 5;

		int QUESTION_FILE_COUNT = 3;

		int RTI_FILE_COUNT = 3;
	}

	interface DESIGNATION {
		String ADMIN_DESIGNATION_NAME = "ADMIN";
		String CITIZEN_DESIGNATION_NAME = "Citizen";
	}

	interface GROUPMASTER {
		String ADMIN_GROUP_CODE = "SUPER_ADMIN";
		String ADMIN_MAKER = "MAKER";
		String ADMIN_GR_BOTH = "GR_both";
	}

	/* NEW CONSTANT ADDED BY Vishwajeet */

	public String ERROR_OCCURED = "Error occured";
	public String EXCEPTION_OCCURED = "**Exception occured**";
	public String UTF8 = "UTF-8";
	public String CITIZEN_LOGIN_REG_DOB_ERROR1 = "citizen.login.reg.dob.error1";
	public String HTTPS_URL = "httpsURL";
	public String USER_NAME = "uname";
	public String SMS_FAILED_ERROR = "SMS Sending Failed...";
	public String HTTP = "http://";
	String PANCARD_PATTERN = "[A-Z]{5}[0-9]{4}[A-Z]{1}";

	String CAPTCHA_NOT_MATCHED = "Captcha Not Matched";

	String DENY = "DENY";

	String RESET_PASSWORD_OTP = "reset password otp :";
	String FORGOT_PASSWORD_OTP = "forgot password otp :";

	String ERROR_OCCURED_SAVEORUPDATE = "Error occur druring saveOrUpdate";
	String ERROR_OCCURED_SAVE_SCHEMEMASTER = "Error occured during saveSchemeMaster ";

	String GET_GUESTAll_PROFILEMASTERS_ERROR = "Error occured during getGuestAllProfileMasters ";
	String FINDBYID_ERROR = "Error occured during findById";
	String DURING_DELETE_ERROR = "Error occured during delete";
	String FINDHOMEPAGE_PHOTOS_ERROR = "Error occured during findhomepagephotos";
	String FINDHOMEPAGE_VIDEOS_ERROR = "Error occured during findhomepagevideos";
	String HOMEMAX_IMAGE_ERROR = "homeMaxImageError";
	String EIP_IMAGEVALIDATE_VIEW = "EIPHomeImagesSearchValidn";
	String EIP_HOMEIMAGES_VIEW = "EIPHomeImages";
	String AUTH_USER_ERROR = "Error occurred while authenticating user";

	String OBJECT_NOTSUPPORT_CLONING = "Object instance does not support cloning.";
	String EXCEPTION_SAVEAPPLICATION_MASTER = "Exception at PortalServiceMasterService.saveApplicationMaster";

	String FILENET_ERROR = "File not available on filenet";
	String SHOWDOC_ERROR = "Error occured during showDoc";
	String EIP_WARD = "eip.ward.";
	String LEFT = "Left";
	String RIGHT = "right";

	String ADVANCE = "A";
	String BILL = "B";
	String COMMOM_TIME_FORMAT = "hh:mm aa";
	String W = "\\W+";

	String NON = "No";
	String UNICODE = "unicode";
	String INVALID_REQUEST = "InvalidRequest";
	String DEFAULT_LOCAL_REG_STRING = "reg";
	String AGE_PATTERN = "[0-9]+$";
	String CHAR_VALIDATOR = "[a-zA-Z]+$";
	String PATNA = "Patna";
	String DATEFORMAT_DDMMYYYY = "dd-MM-yyyy";
	String REGEX = "\\~";
	String LEFT_CURLYBRACET = "}";
	String SQUARE_CURLY_BRACKET = "[{}]";
	String ZERO_0 = "_0.0";
	String NUMB470 = "470";
	public String CFC_ATTACHMENT = "CFC_ATTACHMENT";
	public String AGENCY_UPLOADED_DOC = "AGENCY_UPLOADED_DOC";
	public String AFTER_REJECT = "After_Reject";
	public String PRIVACY_POLICY = "privacyPolicyPage.html";
	public String WEBSITE_POLICY = "Websitepolicy.html";
	// Organization Master Related Constant

	String DOUBLE_BACK_SLACE = "\\";
	String MAIN_LIST_NAME = "list";
	public String FILE_NAME = "FILE_NAME";
	public String DOC_ID = "DOC_ID";
	public String FOLDER_PATH = "FOLDER_PATH";
	public String CHILD_PATH = "../";
	public String CURRENT_PATH = "\\\"" + "";
	public String PARENT_PATH = "\\\"..";
	String SMS = "SMS";
	String E_MAIL = "E-Mail";
	String SMS_AND_E_MAIL = "SMS and E-Mail";
	String NOT_APPLICABLE = "Not Applicable";
	public String HELPDESK_GROUP = "GR_ABMHELPDESK";
	public String COMPLETED = "C";
	public String OPEN = "O";

	public enum FormMode {
		CREATE, UPDATE, VIEW, EDIT, PREFIX

	}

	interface OrgMaster {
		String MODE = "mode";
		String MODE_CREATE = "create";
		String MODE_UPDATE = "update";
		String MODE_EDIT = "edit";
		String MODE_VIEW = "view";
		String SAVE_ACTION = "saveAction";
		String ENTITY_NAME = "tbOrganisation";
		String LIST = "list";
		String JSP_LIST = "tbOrganisation/list";
		String JSP_FORM = "tbOrganisation/form";
		String SAVE_ACTION_CREATE = "Organisation.html?create";
		String SAVE_ACTION_UPDATE = "Organisation.html?update";
		String FAILURE_MSG = "failure";
		String PATH = "filePath";

	}

	interface TbDeporgMap {

		String EXIST = "exist";
	}

	interface COMMON_STATUS {
		String SUCCESS = "success";
		String FAIL = "fail";

	}

	interface CommonMasterUi {
		String OTY = "OTY";
		String DVN = "DVN";
		String OST = "OST";
		String DIS = "DIS";
		String SIT = "STT";
		String LOOK_UP_LIST = "lookUpList";
		String DIVISION_LIST = "divisionList";
		String OST_LIST = "ostList";
		String DIS_LIST = "disList";
		String STT_LIST = "sttList";
		String TB_ORGANIZATION_ERROR_ORGSHRNAME = "tbOrganisation.error.orgShrtNme";
		String SHOW_DOCS = "SHOW_DOCS";
		String ORG_DEFAULT_STATUS = "orgDefaultStatus";
		String VIEW_HELP = "viewHelp";
		String ULB_ORG_ID = "ulbOrgID";

		String ONLS_ORG_NAME = "oNlsOrgname";
		String TB_ORG_ERR_ONLS_ORG_NAME = "tbOrganisation.error.oNlsOrgname";
		String ONLS_ORG_NAME_MAR = "oNlsOrgnameMar";
		String TB_ORG_ERR_ONLS_ORG_NAMEMAR = "tbOrganisation.error.oNlsOrgnameMar";
		String ORG_SHORT_NAME = "orgShortNm";
		String TB_ORG_ERR_ORG_SHORT_NAME = "tbOrganisation.error.orgShortNm";
		String ORG_CPID_STATE = "orgCpdIdState";
		String ORG_CPDID = "orgCpdId";
		String ORG_CPIDIS = "orgCpdIdDis";
		String ORG_CPID_OST = "orgCpdIdOst";
		String ORG_CPID_DIV = "orgCpdIdDiv";
	}

	interface AUDIT {
		String METHOD_NAME_SAVE = "createHistoryForObject";
		String POINTCUT_SAVE = "execution(* com.abm.mainet.common.audit.service.AuditServiceImpl.createHistory(..)) && args (sourceObject,targetObject)";
	}

	public enum InsertMode {
		ADD("A"), UPDATE("U"), DELETE("D");

		private String status;

		InsertMode(String status) {
			this.status = status;
		}

		public String getStatus() {
			return status;
		}

	}

	public static interface Property {
		String PROP_DEPT_SHORT_CODE = "AS";
		String PROP_BRMS_MODEL = "FactorMasterModel|ALVMasterModel|PropertyRateMasterModel|PropertyTaxDataModel";
		String PROP_BRMS_MODEL_FOR_INT_CAL = "PropertyRateMasterModel";
		String CHECK_LIST_MODEL = "ChecklistModel";
		String NEW_ASESS = "N";
		String View_ASESS = "VA";
		String CHN_IN_ASESS = "C";
		String NO_CHN_ASESS = "NC";
		String ASESS_AUT = "A";
		String ASSESS = "A";
		String TAX = "T";
		String ASS_AND_TAX = "AT";
		String KPK = "KPK";
		String NZL = "NZL";
		String DIV = "DIV";
		String SAS = "SAS";
		String VIW = "VIW";
		String REBATE = "REBATE";
		String USA = "USA";
		String PTP = "PTP";
		String IBP = "IBP";
		String APP = "APP";
		String PP = "PP";
		String AP = "AP";
		String PAA = "PAA";
		String PAI = "PAI";
		String MUT = "MUT";
		String NDT = "NDT";
		String BIG_DEC_ZERO = "0.0";
		String REBATE_fOR_PROPERTY = "R";
		String WHOLE = "W";
		String INDIVIDUAL = "I";
		String PROP_BILL_PAYMENT = "PPB";
		String PORTAL = "P";
		String HALF = "H";
		String ZERO = "Z";

		interface serviceShortCode {
			String NCA = "NCA";
			String CIA = "CIA";
		}

		interface DueDatePerf {
			String BGD = "BGD";
			String BDD = "BDD";
			String FYS = "FYS";
			String SSD = "SSD";
			String SED = "SED";
		}

		interface BillSchedule {
			String FORM = "BillingScheduleForm";
			String BSC = "BSC";
			String MON = "MON";
			String DELETE_SCHEDULE = "deleteSchedule";
			String CREATE_SCHEDULE = "createSchedule";
			String DUE_DATE = "BillingScheduleDueDate";
		}

		interface BillPayment {
			String ORG_EMPTY = " OrgId can not be empty ";
			String USER_EMPTY = " USERId can not be empty ";
			String PROP_NO_EMPTY = " PropertyNo Can not be empty ";
			String DEPT_ID = " dept_id can not be empty ";
			String SERVICE_ID = " service_id can not be empty ";
			String PAY_MODE = " pay_mode can not be empty ";
			String PAY_AMT = " Paybale Amt must be greater 0 ";
			String BANK_ID = " bank_id can not be empty ";
			String CHQ_DD_NO = " chque_DD_no can not be empty ";
			String CHQ_DD_DATE = " chque_DD_Date can not be empty ";

			String NOT_FOUND = "Detail not Found";
			String EXCE_FOUND = "Exception occured";
			String CREATE_SCHEDULE = "createSchedule";
			String DUE_DATE = "BillingScheduleDueDate";
		}

		interface propPref {
			String BILL = "BILL";
			String RCPT = "RCPT";
			String CAA = "CAA";
			String MONTH = "MON";
			String ASS = "ASS";
			String FCT = "FCT";
			String OWT = "OWT";
			String LDT = "LDT";
		}

		interface AssStatus {
			String ASS_FILED = "SA";
			String SPEC_NOTICE = "SN";
			String OBJECTION = "OB";
			String INSPECTION = "IN";
			String HEARING_PROCESS = "HE";
			String DREAFT = "D";
			String NORMAL = "N";
		}

		interface AuthStatus {
			String NON_AUTH = "N";
			String AUTH = "Y";
			String AUTH_WITH_CHNG = "AC";
		}

		String CV = "CV";
		String ARV = "ARV";
		String RV = "RV";
		String MANUAL = "M";
		String NOT_APP = "N";
		String INT_RECAL_YES = "Y";
		String INT_RECAL_NO = "N";
		String MANUAL_DESC = "Manual";
		String NOT_APP_DESC = "Not Applicable";
		String MONTHLY = "MI";
		String YEARLY = "YI";
		String ALL = "ALL";
		String PRIMARY_OWN = "P";
		String TB_AS_PRO_ASSE_MAST = "TB_AS_PRO_ASSESMENT_MAST";
		String PRO_PROP_NO = "PRO_PROP_NO";
		String ALV = "ALV";
		String FACT_LOG = "Error While fetching factor rate for property";
		String SDDR_LOG = "Error While fetching SDDR rate for property";
		String RATE_LOG = "Error While fetching tax master rate for property";
		String SO = "SO"; // single owner
		String JO = "JO"; // joint owner
		String Y = "Y";
		String GENERATE_NO_DUES_CERTI = "proceed";
		String GET_PROPERTY_DETAILS = "getPropertyDetails";
		String GET_CHECKLIST_AND_CHANGES = "getCheckListAndCharges";
		String BILL_PAYMENT_FOR_PROPERTY_HTML="/BillPaymentForProperty.html";
		String BILL_PAYMENT_FOR_PROPERTY="BillPaymentForProperty";
		String PROP_QR_QUERY_STRING="PropertyBillPayment.html?payment&propNo=";
		String WT_QR_QUERY_STRING="WaterBillPayment.html?payment&conNo=";

	}

	interface RFM {
		String WEB_BROWSER = "WB";
	}

	public String OFFICE_DOCUMENTS = "Office Documents";
	public String TENDERS = "Tenders";

	interface OBJECTION_COMMON {
		String OBJECTION_VALIDATION_URL = "objectionDetailFormValdin";

		interface ObjectionOn {
			String NOTICE = "SN";
			String BILL = "BILL";
			String FIRST_APPEAL = "FRTI";
			String SECOND_APPEAL = " SRTI";
		}
	}

	interface RTISERVICE {
		String RTIAPPLICATIONSERVICECODE = "RAF";
		String RTIAPPLICATIONSERVICENAME = "RTI";
		String TB_RTI_APPLICATION = "TB_RTI_APPLICATION";
		String RTI_NO = "RTI_NO";
		String APPEALURL = "Appeal.html";
		String RTI_SMS_EMAIL = "RtiApplicationDetailForm.html";
		String PIO_RESPONSE = "PioResponse";
		String RTI_PIO_SMS_EMAIL = "PioResponse.html";
		String RTI_PIO_VALDIN = "PioResponseValidn";
		String RTI_SUCCESS_PAGE = "RtiApplicationStatusForm";
		String RTI_VALIDATION_VIEW = "RtiApplicationDetailFormValdin";
		String RTI_CHECKLIST_CHARGE_VIEW = "rtiChecklistAndCharge";
		String RTI_INITIATE_FORM_VIEW = "RtiApplicationDetailForm";
		String RTI_APPLICATION_DETAIL = "RtiApplicationDetail";
		String DISPATCH = "RtiDispatch";
		String RTI_DISPATCH_SMS_EMAIL = "RtiDispatch.html";
		String RTI_DISPATCH_VALDIN = "RtiDispatchFormValdin";
		String RTI_APPLICATION_FORM = "RtiApplicationDetailForm";
		String RTI_EMPLOYEE = "EMPLOYEE";
		String RTI_RATE_MASTER = "RtiRateMaster";
		String CHECKLIST_RTIRATE_MASTER = "ChecklistModel|RtiRateMaster";
		String APL = "APL";
	}

	interface SolidWasteManagement {

		String DEPT_WASTE_COLLECTOR_SHORT_CODE = "CDG";
		String CHECK_LIST_MODEL = "ChecklistModel";
		String SWMRATEMASTER_MODEL = "SWMRateMaster";
	}

	String START_TIME_METHOD_NAME = "Start--------" + "MethodName-------";
	String END_TIME_METHOD_NAME = "End--------" + "MethodName---------";
	String START_TIME_MILLI_SECOND = "---------" + "StartTime MiliSecond --------";
	String END_TIME_MILLI_SECOND = "---------" + "EndTime MiliSecond ----------";

	interface RoadCuttingConstant {
		String RAOD_CUTTING_URL = "/RoadCutting.html";
		String RCP = "RCP";
		String CHECKLIST_ROADCUTTING_RATE_MASTER = "ChecklistModel|RoadCuttingRateMaster";
		String NA = "NA";
		String NO = "N";
		String WMS = "WMS";
		String APL = "APL";

	}

	interface TradeLicense {

		String TRADE_APPLICATION_FORM_HTML = "/TradeApplicationForm.html";
		String TRADE_APPLICATION_FORM = "TradeApplicationForm";
		String VIEW_TERMS_CONDITION = "viewTermsCondition";
		String TRADE_TERMS_CONDITION = "TradeTermsCondition";
		String BACKPAGE = "backPage";
		String TRADE_APPLICATION_BACK_FORM = "TradeApplicationBackForm";
		String SAVE_TRADE_LICENSE_FORM = "saveTradeLicenseForm";
		String GET_OWNERSHIP_TYPE_DIV = "getOwnershipTypeDiv";
		String OWNERSHIP_TYPE = "ownershipType";
		String OWNERSHIP_DETAIL_TABLE = "OwnershipDetailTable";
		String SAVE_UPDATE_TRADE_LICENSE_APPLICATION = "save trade license";
		String CHECKLIST_MODEL = "ChecklistModel";
		String APL = "APL";
		String ITC = "ITC";
		String FILE_COUNT_UPLOAD = "fileCountUpload";
		String OWNERSHIP_TABLE = "OwnershipTable";
		String PRINT_CHALLAN_AND_ONLINE = "printChallanAndOnline";
		String SHOW_CHARGE_DETAILS_MARKET = "showChargeDetailsMarket";
		String CHARGE_DETAILS_MARKET_LICENSE = "ChargesDetailMarketLicense";
		String GET_CHECKLIST_AND_CHARGE = "getCheckListAndCharge";
		String SERVICE_SHORT_CODE = "NTL";
		String MARKET_LICENSE = "ML";
		String GET_PROPERTY_DETAILS = "getPropertyDetails";
		String EDIT_APPLICATION = "editApplication";
		String TRADE_LICENSE_EDIT = "TradeLicenseEdit";
		String TRADE_APPLICATION_FORM_VALIDN = "TradeApplicationFormValidn";
		String RENEWAL_LICENSE_FORM_HTML = "/RenewalLicenseForm.html";
		String RENEWAL_LICENSE_FORM = "RenewalLicenseForm";
		String GET_LICENSE_DETAILS = "getLicenseDetails";
		String RENEWAL_LICENSE_FORM_VALIDN = "RenewalLicenseFormValidn";
		String GET_CHARGES_FROM_BRMS = "getChargesFromBrms";
		String SAVE_RENEWAL_LICENSE = "saveRenewalLicense";
		String GENERATE_CHALLAN_AND_PAYMENT = "generateChallanAndPayement";
		String CHARGES_DETAIL = "ChargesDetailMarketLicense";
		String RENEWAL_SERVICE_SHORT_CODE = "RTL";
		String FlagY = "Y";
		String DUPLICATE_SERVICE_SHORT_CODE = "DTL";
		String DUPLICATE_LICENSE_FORM = "DuplicateLicenseForm";
		String GENERATE_OTP = "generateOtp";
		String GET_DUPLICATE_LICENSE_PRINT = "getDuplicateLicensePrint";
		String DUPLICATE_LICENSE_PRINT = "DuplicateLicensePrint";
		String GET_OTP_BTN_SHOW = "getOtpBtnShow";
		String CHANGE_IN_BUSINESS_NAME_FORM = "ChangeInBusinessNameForm";
		String GET_CHANGE_IN_BUSINESS_LICENSE_PRINT = "getChangeInBusNameLicensePrint";
		String CHANGE_IN_BUS_NAME_LICENSE_PRINT = "changeInBusNameLicensePrint";
		String CHANGE_IN_BUSINESS_NAME_SHORT_CODE = "CBN";
		String CHANGE_IN_VATEGORY_SUBCATEGORY_EDIT = "ChangeInCategorySubcategoryEdit";
		String TRD_ENV = "TEV";
		String CANCELLATION_LICENSE_FORM = "CTL";
		String TLA = "TLA";
		String CHECK_ASS_STATUS = "checkAssStatus";

	}

	interface MASTER {
		String A = "A";
	}

	// this interface is created for the constant which is use in social security
	interface SocialSecurity {
		String PENSION_SCHEME_MASTER_URL = "PensionSchemeMaster.html";
		String FTR = "FTR";
		String CRA = "CRA";

		String DEPARTMENT_SORT_CODE = "SWD";
		String SBY = "SBY";
		String PAYMENT_PREFIX = "BSC";
		String SCHEME_APPLICATION_FORM_URL = "SchemeApplicationForm.html";
		String SERVICE_CODE = "SAA";
		String SOCIAL_CHECKLIST = "ChecklistModel";
		String CHECK_LIST_FORM = "checkListApplicationForm";
		String GENERAL_MSG = "GM";

		String A = "A";
		String N = "N";
		String _1 = "1";
		String RENEWAL_OF_LIFE_CERTIFICATE_SERVICE_CODE = "RLC";

	}

	interface RightToService {
		String RTS_DEPT_CODE = "RTS";
		String DCS = "DCS";
		String RTS_CHECKLIST = "ChecklistModel";
		String CHECKLIST_RTSRATE_MASTER = "ChecklistModel|RtiRateMaster";
		String FIRST_APPEAL_URL = "FirstAppeal.html";
		String SECOND_APPEAL_URL = "SecondAppeal.html";
		String DRN_ACK_PAGE = "rtsAcknowldgeMentStatusForm";
		String APPLY_DRAINAGE_CONNECTION = "Apply For Drainage Connection Service";

		interface REDIRECT_URLS {
			String BIRTH_CERTIFICATE = "ApplyforBirthCertificate.html";
			String DEAT_CERTIFICATE = "applyForDeathCertificate.html";
		}

		interface SERVICE_CODE {
			String BIRTH_CERTIFICATE = "RBC";
			String DEATH_CERTIFICATE = "RDC";
		}
	}

	interface AdvertisingAndHoarding {

		String AGENCY_ID = "agencyId";
		String AGENCY_CHECKLIST_RATE_MODEL = "ChecklistModel|ADHRateMaster";
		String GET_CHECKLIST_CHARGES = "getChecklistAndCharges";
		String ADH_SHORT_CODE = "ADH";
		String AGENCY_REG_SHORT_CODE = "AGL";
		String HOARDING_REG_SHORT_CODE = "HBP";
		String CHECK_LIST = "ChecklistModel";
		String TRANSIENT_STATUS = "T";
		String AGENCY_REG_REN_SHORT_CODE = "AGR";
		String HOARDING_ID = "hoardingId";
		String NEW_HOARDING_DATA = "NewHoardingData";
		String NEW_HRD_APP_VALIDN = "NewHoardingApplicationValidn";

		String AGENCY_REGISTRATION_ACKNOWLEDGEMENT = "AgencyRegistrationAcknowledgement";
		String PRINT_AGENCY_REG_ACKW = "printAgencyRegAckw";
		String SAVE_AGENCY_REGISTRATION = "saveAgencyRegistration";
		String AGENCY_REGISTRATION_VALIDN = "AgencyRegistrationFormValidn";
		String PRINT_AGENCY_LICENSE_LETTER = "printAgencyLicenseLetter";
		String PRINT_AGENCY_LIC_LETTER = "printAgencyLicenseLetter";
		String AGENCY_REGISTRATION_SMS_URL = "AgencyRegistration.html";
		String AGENCY_REGISTRATION_REN_SMS_URL = "AgencyRegistrationRenewal.html";
		String AGENCY_REGISTRATION_AUTH_SMS_URL = "AgencyRegistrationAuth.html";
		String AGENCY_REGISTRATION_APPROVAL = "AgencyRegistrationApproval";
		String ADH_SHOW_DETAILS = "showDetails";
		String GET_HOARD_DET_BY_HOARDID = "getHoardingDetailByHoardingNo";

		String GET_AGENCY_NAME = "getAgencyName";
		String ADVERTSIER_CAT = "advertiserCategoryId";
		String APPLICABLE_CHECKLIST_CHARGE = "getApplicableCheckListAndCharges";
		String NEW_ADVERTISEMENT_DATA = "NewAdvertisementData";
		String YOUR_APPLICATION_NO = " Your Application No.";
		String SAVE_NEW_ADVERTISEMENT_APPLICATION = "Save New Advertisement Application ";

		String FOUR_NO_PERCENTILE = "%04d";
		String GET_ADVERTISER_BY_ADVERTISER_TYPE = "getAdvertiserByAdvertiserType";
		String ADVERTISER_TYPE = "advertiserType";
		String SEARCH_AGENCY_BY_LICNO = "searchAgencyByLicNo";
		String RENEWAL_REMAINDER_NOTICE_VALIDN = "RenewalRemainderNoticeValidn";
		String PRINT_RENEWAL_REMAINDER_NOTICE = "printRenewalremainderNotice";
		String RENEWAL_REMAINDER_NOTICE_PRINT = "RenewalRemainderNoticePrint";
		String CHECKLIST_ADHRATEMASTER = "ChecklistModel|ADHRateMaster";
		String APL = "APL";
		String ADH_Rate_Master = "ADHRateMaster";
		String AGENCY_LIC_NO = "agencyLicNo";
		String AGENCY_REG_RENEW_VALIDN = "AgencyRegistrationRenewalFormValidn";

		String AGENCY_REG_Cancel_VALIDN = "AdvertiserCancellationFormValidn";

		String CHECKLIST_APPL_FLAG = "checkListApplFlag";
		String APPL_CHARGE_FLAG = "applicationchargeApplFlag";
		String LIC_MAX_TENURE_DAYS = "licMaxTenureDays";
		String APPLICATION_ID = "applicationId";
		String ERROR = "error";
		String FLAGC = "C";
		String SERVICE_SGORTCODE = "serviceShortName";
		String FLAGT = "T";
		String GET_ADVERTISEMENT_APP_FORM = "getAdevertisementApplicationForm";
		String LICENSE_NO = "licenseNo";
		String RENEWAL_SERVICE_CODE = "RAL";
		String FLAG_R = "R";
		String RENEWAL_ADVERTISEMENT_APP_FORM = "renewalAdvertisementApplicationForm";
		String RENEWAL_ADVERTISEMENT_DATA = "renewalAdvertisementData";
		String RENEWAL_ADVERTISEMENT_APP_VALID = "renewalAdvertisementApplicationValidn";
		String SAVE_ADVERTISEMENT_APP = "saveAdvertisementApplication";
		String APPLICATION_NO = "applicationNo";
		String SERVICE_FREE = "servicefree";
		String RENEWAL = "RENEWAL";
		String ONLINE = "Online";
		String VIEW = "VIEW";
		String REJECTED_MESSAGE = "RM";
		String AGENCY_CAN_SHORT_CODE = "CAL";
		String FlagY = "Y";
		String OWNERSHIP_DETAIL_TABLE = "adhOwnershipDetailTable";
		String OWNERSHIP_TABLE = "adhOwnershipTable";
		String GET_OWNERSHIP_TYPE_DIV = "getOwnershipTypeDiv";
		String GET_RENEWAL_OWNERSHIP_TYPE_DIV = "getRenewalOwnershipTypeDiv";
		String RENEWAL_OWNERSHIP_DETAIL_TABLE = "adhRenewalOwnershipDetailTable";
		String OWNERSHIP_TYPE = "ownershipType";
		String RENEWAL_OWNERSHIP_TYPE = "RenewalownershipType";
		String ENTRY_DELETE = "doEntryDeletion";
		String ID = "id";
		String FILE_COUNT_UPLOAD = "fileCountUpload";
		String GET_LICENCE_TYPE = "getLicenceType";
		String GET_CALCULATE_YEAR_TYPE = "getCalculateYearType";
		String LICTYPE = "licType";
		String ADH_BILL_PAYMENT_VALIDN = "ADHBillPaymentValidn";

	}

	interface MRM {
		String MRM_DEPT_CODE = "MRM";
		String MAR_REG_URL = "MarriageRegistration.html";

		String CHECKLIST_MRMRATEMASTER = "ChecklistModel|MRMRateMaster";
		String CHECK_LIST = "ChecklistModel";

		interface SERVICE_CODE {
			String MRG = "MRG";
		}

		String MRG_URL_CODE = "MRG";
		String HUS_URL_CODE = "HUSB";
		String WIFE_URL_CODE = "WIFE";
		String WITNESS_LEASE_URL_CODE = "WITN";
		String APPOINTMENT_URL_CODE = "APPOINT";

		String SHOW_MRG_Page = "showMarriagePage";
		String SHOW_HUS_Page = "showHusbandPage";
		String SHOW_WIFE_Page = "showWifePage";
		String SHOW_WITNESS_Page = "showWitnessPage";
		String SHOW_APPOINTMENT_Page = "showAppointmentPage";
		String ERROR = "error";
		String NOW_PREFIX = "NOW";
		String NOW_DESC = "Number of witness";

		interface STATUS {
			String OPEN = "OPEN";
			String CONCLUDED = "CONCLUDED";
			String REOPEN = "REOPEN";
			String FORM_STATUS_DRAFT = "D";
		}
	}

	interface FRONMEDIASECTION {
		String PHOTO = "photo";
		String VIDEO = "vedio";
	}

	interface BILLCLOUD_PARAM {
		String BILLER_ID = "billerID"; // Merchant Short Name
		String SERVICE_ID = "serviceID"; // Merchant Service
		String FUND_ID = "fundID"; // Merchant Fund ID
		String CONSUMER_ID = "consumerID"; // Merchant Customer ID
		String BILLER_TRANSACTION_ID = "billerTransactionID"; // Unique Transaction ID generated by the Merchant
		String AMOUNT = "Amount";
		String RESPONSE_URL = "responseUrl"; // URL on which merchants needs the transaction status
		String EDITABLE = "editable"; // Merchant wants to edit the transaction amount by consumer
		String NAME = "name";
		String ADDRESS = "address";
		String EMAIL = "email";
		String MOBILE = "Mobile";
		String UNIT = "Consumer’s Administrative Unit (e.g. Ward, Zone…)"; // Additional Data can be added
		String SUBUNIT = "Consumer’s Administrative SubUnit"; // Additional Data can be added
		String LOCATION = "Location"; // checksum:-Concatenation of (CID + RID + CRN + AMT + key)
		String CUSTOMFIELD1 = "customField1";// Additional information
		String CUSTOMFIELD2 = "customField2";
		String CUSTOMFIELD3 = "customField4";
		String CUSTOMFIELD4 = "customField4";
		String CUSTOMFIELD5 = "customField5";
		String CHECKSUM = "checksum";// BillCloud validates checksum passed by merchant
		String EQUALS_SYMB = "=";
		String SUCCESS_CODE = "Successful";
		String FAIL_CODE = "Failed";
		String TRANSACTIONID = "transactionID";
		String CHANNELTRANSACTIONID = "channelTransactionID";
		String TRANSACTIONDATE = "transactionDate";
		String CHANNELID = "channelID";
		String PAYMENTMODE = "paymentMode";
		String REASON = "Reason";
		String NETAMOUNT = "netAmount";
		String SERVICETAX = "serviceTax";

	}

	String ORG_CODE_KDMC = "KDMC";
	String ORG_CODE_SKDCL = "SKDCL";
	String ORG_CODE_ASCL = "ASCL";
	String FlagE = "E";
	String FlagA = "A";
	String FlagM = "M";
	String FlagV = "V";
	String FlagD = "D";
	String FlagR = "R";
	String FlagB = "B";

	interface ORG_CODE {
		String KDMC = "KDMC";
		String SKDCL = "SKDCL";
	}

	String ERROR = "error";

	String FlagC = "C";

	String TABLE = "TABLE";

	String STRING1 = "String1";
	String STRING2 = "String2";

	interface PASSWORD {
		int MIN_LENGTH = 6;
		int MAX_LENGTH = 20;
	}

	
	interface CommonConstants {
		
		String COMMAND = "command";
		String Y = "Y";
	}

	
    String LANG_ID ="langId";
    String MENU_ARROW = "=>";
    Long ADMIN_EMP_TYPE = null;

    String NOUSER ="NOUSER";
    String EDIT_USER ="EditUserProfile";

    
    interface TraficUpdate{
    	
    	 String SignalSCN = "SignalSCN";
    	 String ShortDescription= "ShortDescription";
    	 String Congestion= "Congestion";
    	 String status= "status";
    	String LOW = "Low";
    	String MEDIUM = "Medium";
    	String HIGH ="High";
    	
    }
    interface ItmsUpdate{
    	
    	 String signalSCN = "SignalSCN";
    	 String accidentType= "AccidentType";
    	 String weatherType= "WeatherType";
    	 String roadType= "RoadType";
    	 String areaType= "AreaType";
    	 String roadFeature= "RoadFeature";
   	
   }
    
    String ACCESS_TOCKEN = "access_token";
    String ENVIRONMENT = "Environment";
    String DATA ="data";
    String MYOBJECTLIST = "myObjectList";
    String IMAGE = "IMAGE";
	String ENV_PSCL = "PSCL";
	String ENV_other="Other";
	String FlagH = "H";
	String FORMAT_YYYYMMDD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
	String Nine_Zero_Nine = "909";
 
	 interface Legal {
    	 // Advocate Master
        String ADVOCATE_MASTER_FORM = "AdvocateMasterForm";
        String ADVOCATE_MASTER_VALIDN = "AdvocateMasterValidn";
        String DEPT_CODE ="LEGL";
        
    }
  
    interface Profile_image{
    	
    	String Name="name";
    	String NameReg="nameReg";
    	String Designation = "designation";
    	String DesignationReg = "designationReg";
    	String ContactNumber ="contactNumber";
    	String Image ="image";
    	String Email ="email";
    	
    	
    }
}