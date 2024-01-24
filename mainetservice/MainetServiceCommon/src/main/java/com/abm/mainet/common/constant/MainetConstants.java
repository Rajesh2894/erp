package com.abm.mainet.common.constant;

import java.io.File;

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
    String REGIONAL_LOCALE_STRING = "reg";
    public String EXCEPTION_OCCURED = "**Exception occured**";
    String BFR = "BFR";
    String STATUS_API = "ENQUIRY";
    String ONLINE = "Real Time";
    String RTI_APPLICATION = "fetchRtiAppDetailByRefNo";
    String CHALLAN_VERIFICATION = "getForChallanVerification";
    String NARATION_PROPERTY = "getForPropertyname";
    String LANGUAGE = "LNG";
    String OBJ_PREFIX = "AOT";
    String ALL = "ALL";
    String YES = "YES";
    String NO = "NO";
    String YESL = "Yes";
    String NOL = "No";
    String ASSET_CLASS_PREFIX = "ACL";
    String CL = "CL";
    String GENDER = "GEN";
    String TCD = "TCD";
    String XX = "XX";
    String NULL = "null";
    String MR = "Mr";
    String MS = "Ms";
    String MRS = "Mrs";

    String DATE_PATTERN = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";

    String TIME_PATTERN = "(20|21|22|23|[01]\\d|\\d)(([:][0-5]\\d){1,2}) (([a|A][m|M]|[p|P][m|M]))";

    String DATE_TIME_PATTERN = "(" + DATE_PATTERN + "|" + DATE_PATTERN + " " + TIME_PATTERN + ")";

    String DATE_FORMAT = "dd/MM/yyyy";
    String DATE_FORMATS = "yyyy-MM-dd";

    String DATE_FORMAT_UPLOAD = "dd-MM-yyyy";

    String TIME_FORMAT = "hh:mm:ss aa";

    String YEAR_FORMAT = "yyyy";

    String HOUR_FORMAT = "hh:mm a";

    String DATE_HOUR_FORMAT = DATE_FORMAT + " " + HOUR_FORMAT;

    int DAS_IN_YEAR = 365;
    String INTEGER_DATE_FORMAT = "ddMMyyyy";

    String DATE_FORMAT_WITH_HOURS_TIME = "dd/MM/yyyy HH:mm";

    String DATE_AND_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    String DATE_AND_TIME_FORMAT_PAY = "yyyy-MM-dd HH:mm:ss";
    
    String DATE_AND_TIME = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    
    String DATE_AND_TIME_FORMAT_PAYRECIEPT = "dd-MM-yyyy  HH:mm:ss";

    String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    String MOB_PATTERN = "^[(789)](\\d){9}$";

    String PAN_NO = "^([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}?$";

    String PIN_CODE = "^([0-9]{6})?$";
    
    String RECEIPT_NO_PATTERN="((?<=[a-zA-Z])(?=[0-9]))|((?<=[0-9])(?=[a-zA-Z]))";
    String STRING_DATA ="^[a-zA-Z]*$";

    String NUMERIC_PATTERN = "[0-9]+";

    String WINDOWS_SLASH = "/";

    String FORM_NAME = "command";

    String COMMON_DATE_FORMAT = "dd/MM/yyyy";

    String COMMON_DECIMAL_FORMAT = "##.00";

    String SERVER_ERROR = "SERVERROR";

    String SERVER_ERROR_URL = "ServerError";

    String VALIDN_SUFFIX = "Validn";

    int EMPTY_LIST = 0;

    boolean SUCCESS = true;
    boolean FAILED = false;

    short ENGLISH = 1;
    short MARATHI = 2;
    short NO_LANG = 0;

    String BLANK = "";
    String WHITE_SPACE = " ";
    String BLANK_WITH_SPACE = " ,";
    String WINDOWS_SLASH_WITH_SPACE = " / ";
    String AMPERSAND = "*";
    String HYPHEN = "-";
    String SLASH = "/";

    String FlagL = "L";
    String FlagS = "S";
    String FlagB = "B";
    String FlagA = "A";
    String FlagR = "R";
    String FlagC = "C";
    String FlagH = "H";
    String FlagM = "M";
    String FlagN = "N";
    String FlagF = "F";
    String FlagD = "D";
    String FlagP = "P";
    String FlagI = "I";
    String FlagU = "U";
    String FlagY = "Y";
    String FlagW = "W";
    String FlagV = "V";
    String FlagE = "E";
    String U_FLAG = "U";
    String FlagT = "T";
    String FlagQ = "Q";
    String ResponseCode = "Responsecode";
    String ResponseMessage = "ResponseMessage";
    String ResponseDiscription = "ResponseDiscription";
    String Transaction_No_not_Dount = "Record not found against this Transaction No.";
    String DISABLED = "disabled";

    String HIT = "hit";
    String ERROR = "error";
    String LEVEL = "Level";
    String GROUP = "Group";

    String YEAR_FORMAT1 = "yy";

    String BEFORE_REQUEST_LOGGING = "Before request logging";
    String AFTER_REQUEST_LOGGING = "After request logging";
    String START_TIME = "startTime";
    String ORGID = "ORGID";
    String USER = "USER";
    String REQ_UUID = "requestUUID";
    String LOGGING_URL = "URL";
    String DURATION = "duration";
    String USER_SESSION = "userSession";
    String SUBMITTED_SUCCESSFULLY = " submitted successfully";
    String All = "All";
    String DOC_EXIST = "DOC_ALREADY_EXIST";
    String DMS_CONFIGURE = "dms.configure";
    String GET_SOR_NAME = "getSorNames";
    String GET_SOR_DATE = "getSorDates";
    String GRID_DATA = "gridData";
    String ADD_LEAD_LIFT = "addLeadLiftMaster";
    String SEARCH_DATA = "searchData";
    String EDIT_LEAD_LIFT_DATA = "editLeadLiftMasterData";
    String DELETE_LEAD_LIFT_DATA = "deleteLeadLiftMaster";
    String LELI_FLAG = "leLiFlag";
    String LEAD = "Lead";
    String LIFT = "Lift";
    String SOR_TYPE_ID = "sorTypeId";
    public String ERROR_OCCURED = "Error occured";
    String XLSX_EXT = ".xlsx";
    String UNCHECKED = "unchecked";
    String ROWTYPES = "rawtypes";
    String ERROR_UPLOAD_EXL = "Error While Uploading Excel: ";
    String CHECK_ERROR_LOG = ": For Details Check Error Log.";
    String COMMON_FILE_ATTACHMENT = "commonFileAttachment";
    String TB_ADVANCE_REQ = "TB_ADVANCE_REQ";
    String ADV_NO = "ADV_NO";
    String ARN = "ARN";
    String CAR = "CAR";
    String ADVANCE_REQUISITION_URL = "AdvanceRequisition.html";
    String ADVANCE_REQUISITION_FORM = "AdvanceRequisitionForm";
    String SEND_FOR_APPROVAL = "sendForApproval";
    String ABT = "ABT";
    String AD = "AD";
    String START_TIME_METHOD_NAME = "Start--------" + "MethodName-------";
    String END_TIME_METHOD_NAME = "End--------" + "MethodName---------";
    String START_TIME_MILLI_SECOND = "---------" + "StartTime MiliSecond --------";
    String END_TIME_MILLI_SECOND = "---------" + "EndTime MiliSecond ----------";
    String SUDHA_URL = "www.sudaca.in";
    String OPEN_TAXDEF_FORM = "openTaxDefinationForm";
    String TAXDEF_FORM = "TaxDefinationForm";
    String CHECK_TAXES = "checkForTaxes";
    String UPDATE_TAXDEF = "updateTaxDefinitionForm";
    String TAXDEF_ID = "taxDefId";
    String FORM_MODE = "formMode";
    String SEARCH_TAXDEF = "searchTaxDefinationForm";
    String TAX_ID = "taxId";
    String PAN_CARD = "panCard";
    String PAN_APPLICABLE = "panApp";
    String CHALLAN_BILL = "Bill.";
    String CHALLAN_VERIFY = "Bill.RL";
    String CAPTCHA_GENERATE_VALUE = "0123456789";
    String NO_CAPTCHA_GENERATE_VALUE = "1111111111";
    String CONTENT_TYPE_PNG = "image/png";
    String PNG = "png";
    String CAPTCHA_NOT_MATCHED = "Captcha Not Matched";
    String AUTH = "Y";
    Long NoOfDays = 30l;
    String DMS_LIST = "DMS_DOC_LIST";
    String DMS_TABLE = "TB_DMS_METADATA";
    String SEQ_NO = "SEQ_NO";
    String MAKER_CHECKER_NA = "Maker-checker not applicable";
    String EUC = "EUC";
    String ENV = "ENV";
    String CMC = "CMC";
    String PSC = "PSC";
    String ABW = "ABW";
    String DWE = "DWE";
    String MOAL = "MOAL";
    String TOVE = "TOVE";
    String OPL = "OPL";
    String LOI_STRING="LOI";
    String CERTIFICATE="Certificate";
    String WORKORDER="WorkOrder";
    String WATER_DOMESTIC="DOM";
    String SEWER_DOMESTIC="DOMS";
    String WATER_DOM_DM="DM";
    String ACCESS_TOCKEN = "access_token";
    Long TOSTOPLEVEL=13L;
    
    String DESIGNATION_DRIVER = "%Driver%";
    
    String[] ones = {"", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};
	String[] tens = {"", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"};
    String[] thousands = {"", "thousand", "million", "billion", "trillion", "quadrillion", "quintillion", "sextillion", "septillion", "octillion", "nonillion"};
    
    String DEVELOPER_REGISTRATION="DRN";
    String CHECKLIST_DEVELOPER_REGISTRATION_MODEL = "ChecklistModel";
    
    String TCP_NEW_LICENSE="NL";
    String CHECKLIST_TCP_NEW_LICENSE_MODEL = "ChecklistModel";
    String BPMS_RATE_MASTER_MODEL = "BPMSRateMaster";
    
    String APPLICANT_INFORMATION ="Applicant Information";
    String LAND_SCHEDULE = "Land Schedule";
    String DETAILS_OF_LAND = "Details Of Applied Land";
    String KHASRA_DEVELOPED_COLLABORATION ="Khasra developed collaboration";
    String KHASRA_DEVELOPED_COLLABORATION_QUESTION = "Whether Khasra been developed in collaboration";
    String ENCUMBRANCE_DOCUMENTS = "Encumbrance Document";
    String ENCUMBRANCE_CHECKLIST_DOCUMENTS = "Any encumbrance with respect to following";
    String COURT_ORDER_DOC_TYPE1 = "Existing litigation, if any, concerning applied land including co-sharers and collaborator";
    String COURT_ORDER_DOC_TYPE2 = "Court orders, if any, affecting applied land";
    String COURT_ORDER_DOCUMENTS = "Court Orders Document";
    String INSOLVENCY_LAND_DOC_TYPE1 = "Any insolvency/liquidation proceedings against the Land Owing Company/Developer Company";
    String INSOLVENCY_DOCUMENTS = "insolvency Document";
    String SHAJRA_APP_LAND_TYPE1 = "Shajra Plan";
    String SHAJRA_APP_LAND_TYPE2 = "As per applied land";
    String SHAJRA_APP_LAND_DOCUMENTS = "Shajra Plan Document";
    String RELEASE_ORDER_DOC_TYPE1 = "Acquisition status";
    String RELEASE_ORDER_DOC_TYPE2 = "Whether Land Released/Excluded from aqusition proceeding";
    String RELEASE_ORDER_DOC = "Release Order Document";
    String USAGE_ALLOTEE_DOC_TYPE1 = "Category-II approach";
    String USAGE_ALLOTEE_DOC_TYPE2 = "Whether irrevocable consent from such developer/ colonizer for uninterrupted usage of such internal road for the purpose of development of the colony by the applicant or by its agencies and for usage by its allottees submitted";
    String USAGE_ALLOTEE_DOC= "Usage Allotee Document";
    String ACCESS_NHSR_DOC_TYPE1 = "Category-II approach";
    String ACCESS_NHSR_DOC_TYPE2 = "Access from NH/SR";
    String ACCESS_NHSR_DOC = "Access NH/SR";
    String EXISTING_APPROACH_DOC_TYPE1 = "Details of proposed approach";
    String EXISTING_APPROACH_DOC_TYPE2 = "Any other type of existing approach available";
    String EXISTING_APPROACH_DOC = "Existing Approach";
    String DGPS_DOC_TYPE1 = "Details of applied land";
    String DGPS_DOC_TYPE2 = "Upload DGPS Point";
    String DGPS_DOC = "DGPS Document";
    

    
    interface MENU {
    	 String PC="PC";
         String WB = "W";
        String TRUE = "true";
        String FALSE = "false";
        String A = "A";
        String _0 = "0";
        String _1 = "1";
        String SF = "SF";
        String S = "S";
        String M = "M";
        String F = "F";
        String R = "R";
        String EX = "EX";
        String Y = "Y";
        String N = "N";
        String P = "P";
        String B = "B";
        String NAMEENG = "smfname";
        String NAMEREG = "smfname_mar";
        String[] ROLEMENU = new String[] { "S", "M", "F", "SF" };
        String[] DISPLAY_MENU_PARENT = new String[] { "S" };
        String[] DISPLAY_MENU_CHILD = new String[] { "M", "F" };
        String PORTAL_LOGIN = "GR_PORTAL_LOGIN";
        String EndWithHTML = ".html";
        String E = "E";
        String D = "D";
        String A_ID = "A\\/";
        String E_ID = "E\\/";
        String D_ID = "D\\/";
        String ALL = "ALL";
        String POS="POS";
     

    }

    interface QUARTZ_SCHEDULE {
        String JOB_DEFINATION = "JobDefination";

        String JOB_CLASS_NAME = "className";

        String JOB_METHOD_NAME = "processQuartzJob";
        String JOB_GROUP = "jobGroup";
        String TRIGGER_NAME = "trigger_";
        String JOB_DATA = "jobData";
        String MAXMINUTES = "60";
        String MAXHOURS = "24";

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
            String HOURLY = "HR";
            String MINUTES = "MIN";
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

    interface operator {

        String QUE_MARK = "?";

        String QUOTES = ":";

        String AMPERSENT = "&";

        String DOT = ".";

        String EMPTY = "";

        String UNDER_SCORE = "_";

        String COMMA = ",";

        String PLUS = "+";

        String MINUS = "-";

        String FORWARD_SLACE = "/";

        String COMMA_WITH_SPACE = " ,";

        String COMA = ",";
        String COLON = ":";
        String SEMI_COLON = ";";
        String DOUBLE_BACKWARD_SLACE = "\\";
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

    }

    interface URL_EVENT {
        String SERACH = "search";
        String OPEN_NEXT_TAB = "generatePDFNextTab";
        String JSON_APP = "application/json";
    }

    interface APP_NAME {
        String TSCL = "TSCL";
        String ASCL = "ASCL";
        String DSCL = "DSCL";
        String SKDCL = "SKDCL";
        String PSCL = "PSCL";
        String TCP = "TCP";
        String WEBLINE = "WEBLINE";
    }
    interface PROJECT_SHORTCODE {
        String PRAYAGRAJ = "PNN";
    }

    interface IsReadOnly {
        boolean TRUE = true;
        boolean FALSE = false;
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

        /**
         * ZERO means Not Deleted
         */
        String ZERO = "0";

        /**
         * ONE means Deleted
         */
        String ONE = "1";
    }

    interface AuthStatus {

        String APPROVED = "A"; // flag to identify whether requested application is approved by Government Body
        String ONHOLD = "H"; // flag to identify whether requested application has put on Hold
        String FORWARDTODEPARTMENT = "FDE";
        // String FORWARDTOEMPLOYEE = "FDE";
        String REJECTED = "R";
        String FORWARDTOOTHERORGANISATION = "FO";
        String FORWARDTOOTHELOCATION = "FDL";
    }

    interface IsUploaded {
        String UPLOADED = "Y"; // Flag to identify whether Agency has uploaded the documents or not.
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

    interface STATUS {
        String ACTIVE = "A";
        String INACTIVE = "I";

    }

    int OTP_PRASSWORD_LENGTH = 6;
    int OTP_VALIDITITY_IN_MINS = 30;

    String[] excludePath = { "profile_img_path", "image", "imageName", "cfcAttachments" };

    /**
     * To define file upload directory constants.
     */
    interface DirectoryTree {
        String DEFAULT_CACHE_FOLDER = "cache";
        String SLIDER_IMAGE = "SLIDER";
    }

    interface PAYMENT_STATUS {
        String SUCCESS = "success";
        String PENDING = "pending";
        String FAILURE = "failure";
        String P2P_DEVICE_TXN_DONE="P2P_DEVICE_TXN_DONE";
		String P2P_DEVICE_CANCELED = "P2P_DEVICE_CANCELED";
    }

    interface PAYMENT {
        String ONLINE = "Y";
        String OFFLINE = "N";
        String PAY_AT_ULB_COUNTER = "P";
        String FREE = "F";
    }

    interface DEPT_SHORT_NAME {
        String CFC_CENTER = "CFC";
        String WATER = "WT";
        String RTI = "RTI";
        String PROPERTY = "AS";
        String ACCOUNT = "AC";
        String RNL = "RL";
        String BND = "BND";
        String ADH = "ADH";
        String CFC= "Citizen Facility Center";
        String STORE="SD";
        String WORKFLEETMGMT = "VM";
    }

    interface SMS_EMAIL_URL {
        String REGISTRATION = "Registration.html";
        String CHECKLIST_VERIF = "ChecklistVerification.html";
        String CHALLAN_UPDATE = "ChallanUpdate.html";
        String LOI_GENERATION = "LoiGeneration.html";
        String LOI_PAYMENT = "LoiPayment.html";
        String CHALLAN_AT_ULB_COUNTER = "ChallanAtULBCounter.html";
        String TENDER_INITIATION = "TenderInitiation.html";
        String WORK_ASSIGNEE = "WorkAssignee.html";
        String CHANGE_OF_OWNER = "ChangeOfOwnership.html";
        String RejectionLetterReport = "RejectionLetterReport.rptdesign";
        String HoldOfDocumentLetterReport = "HoldOfDocumentLetterReport.rptdesign";

    }

    interface SCRUTINY_COMMON_PARAM {
        String USERID = "USERID";
        String LANGID = "LANGID";
        String FINYEAR = "FINYEAR";
        String APM_APPLICATION_ID = "APM_APPLICATION_ID";
        String SM_SERVICE_ID = "SM_SERVICE_ID";
        String REFERENCE_ID = "REFERENCE_ID";
        String CFC_URL = "CFC_URL";
        String SENDTOBACK = "cfc.sendtoback.save";
        String PROCCESS_FAIL = "cfc.sendtoback.fail";
        String PROCCESS_SAVE = "cfc.label.save";
        String PROCCESS_SUBMIT = "cfc.label.submit";
        String SCRUTINY_AUTH = "cfc.label.autho";
        String TASK_ID = "taskId";
        String WORK_FLOW_ID = "workflowId";
    }

    interface DASHBOARD {
        String HOLD = "Hold";
        String PENDING = "Pending";
        String CLOSE = "Close";
        String LOI_GENERATED = "LoiGenerated";
        String CLOSED = "CLOSED";
    }

    interface NUMBERS {
        int ZERO = 0;
        int ONE = 1;
        int TWO = 2;
        int THREE = 3;
        int FOUR = 4;
        int FIVE = 5;
        int SIX = 6;
        int SEVEN = 7;
    }

    interface RegEx {
        String ENGLISH_REG_EX = "^[0-9a-zA-Z\\s.,-;()'\"////]*$";
        String BANK_IFSC_REG_EX = "^[A-Z]{4}\\d{1}[A-Z0-9]{6}$";
        String BANK_MICR_REG_EX = "^[0-9]{9}$";
        String BANK_ACC_NUM_REG_EX = "[0-9]{9,18}";
        String GSTIN_REG_EX = "^([0-9]){2}([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}([0-9]){1}([a-zA-Z]){1}([0-9]){1}?$";
        String UID_REG_EX = "^\\d{12}$";
    }

    interface AGENCY {
        String AUT = "AUT";
        String AGENCY_AUTH = "AgencyAuthorization";
        String AGENCY_AUTH_FORM = "AgencyAuthorizationForm";
        String AGENCY_TYPE = "agencyType";
    }

    interface CHECKLIST_VERIFICATION_MAPPING {
        String METHOD_NAME = "performCheckListOperation";
    }

    interface SECURITY_FLAG {
        String CLICK_JACKING_CHECK = "clickjacking.check";
    }

    interface Common_Constant {

        String YES = "Y";
        String NO = "N";
        String ACTIVE_FLAG = "A";
        String INACTIVE_FLAG = "I";
        String ZERO_SEC = "0";
        String ACTIVE = "Active";
        String INACTIVE = "InActive";
        String CANCELLED = "Cancelled";
        String TERMINATED = "Terminated";
        String FREE = "F";
        String DELETE_FLAG = "D";
        String Isdeleted = "0";
        String ROLE = "R";
        String ORG_ID = ".orgId";
        String VIEW = "View";
        String ORGID = "orgId";
        String DEPTID = "deptId";
        String DISTRICT_ID = "districtId";
        String SERVICEID = "serviceId";
        String SERVICECODE = "serviceCode";
        String COMPID = "compId";
        String FLAG = "flag";
        String FIRST_LEVEL = "firstLevel";
        String SECOND_LEVEL = "secondLevel";
        String THIRD_LEVEL = "thirdLevel";
        String FOURTH_LEVEL = "fourthLevel";
        String FIVE_LEVEL = "fiveLevel";
        String TYPE = "type";
        String WORDZONE_TYPE = "wardZoneType";
        String ID = "id";
        String GET_GRID_DATA = "getGridData";
        String FORM = "form";
        String DEPARTMENT = "department";
        String SERVICES = "services";
        String WARDZONE_MAPPING = "wardZoneMapping";
        String ACN = "ACN";
        String FROM_AMOUNT = "fromAmount";
        String TO_AMOUNT = "toAmount";
        String TASKID = "taskId";
        String APPLICATION_ID = "applicationId";
        String WORK_FLOW_ID = "workFlowId";
        String UUID = "uuid";
        String SOURCE_OF_FUND = "sourceOfFund";
        String FIRST = "F";
        String ZERO_MIN = "0";
        String ZERO_HR = "0";
        String CWZ = "CWZ";
        String TRI_CODE1 = "triCode1";
        String TRI_COD1 = "triCod1";
        String Doon_Smart_City = "UAD"; 
        String DEPT_CODE = "deptCode";
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
            String THIRTEEN = "13";
            String FOURTEEN = "14";
            String FIFTEN = "15";
            String SIXTEEN = "16";
            Double FIVE_DOUBLE = 5.0d;
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

    }

    interface INDEX {
        int ZERO = 0;
        int ONE = 1;
        int TWO = 2;
        int THREE = 3;
        int FOUR = 4;
        int FIVE = 5;
        int SIX = 6;
        int SEVEN = 7;
        int Eight = 8;
    }

    interface WebServiceStatus {

        String SUCCESS = "success";
        String FAIL = "fail";
    }

    interface FINYEAR_DATE {
        String FIRST = "F";
        String LAST = "L";
    }

    interface ServiceShortCode {

        String WATER_CHANGEOFOWNER = "WCO";
        String DUPLICATE_BILL_PRINT ="WDB";
        String STORE_TND_SHORT_CODE ="STEND";
        String STORE_INV_SHORT_CODE ="INV";
        String APPLICATION_FOR_BIRTH_CERTIFICATE="ABC";
        String APPLICATION_FOR_DEATH_CERTIFICATE="ADC";
    }

    interface DeptCode {

        String WATER = "WT";
        String WTD = "WTD";
    }

    String FIN_YEAR_YOC = "YOC";
    String ERRORS = "Errors";
    String TRUE = "true";
    String FALSE = "false";
    String SAVE = "Save ok";
    String DELETE = "delete.ok";

    interface MASTER {

        String N = "N";
        String E = "E";
        String Y = "Y";
        String A = "A";
        String BK = "BK";
        String U = "U";
    }

    interface WaterServiceShortCode {
        String PlUMBER_LICENSE = "WPL";
        String WATER_NEW_CONNECION = "WNC";
        String WATER_NO_DUES = "WND";
        String WATER_RECONN = "WRC";
        String WATER_DISCONNECTION = "WCC";
        String CHANGE_OF_USAGE = "WCU";
        String RENEWAL_PLUMBER_LICENSE = "PLR";

    }

    interface BILL_TABLE {
        String Module = "WT";
        String Table = "TB_WT_BILL_MAS";
        String Column = "BM_NO";

        String PropertyTable = "TB_AS_PRO_BILL_MAS";
        String PropertyColumn = "PRO_BM_NO";
        String PropBmIdColumn = "PARENT_MN_NO";

        interface BILLING_TYPE {
            String FB = "FB";
        }
    }

    String DETAIL = "D";
    String FORM_DATA = "FD";

    interface CommonConstant {
        long ZERO_LONG = 0l;
        String BLANK = "";
    }

    interface InputError {
        String ERROR_CODE = "E300"; // custom error code for invalid input
        String ORGID_NOT_FOUND = "orgId not found";
        String SERVICE_ID_NOT_FOUND = "serviceId not found";
        String APPLICATION_NO_NOT_FOUND = "application No. not found";
        String LANG_ID_NOT_FOUND = "langId not found";
        String Application_Status_Not_Found = "application status not found";
        String UserName_Not_Found = "UserName are not found";
        String WaterConn_NO_Not_Found = "Water Connection No. not found";
        String WARDZONE_WORKFLOW_NOT_FOUND = "WNF";
        String TASK_NOT_FOUND = "TNF";
        String NO_CONTENT = "204";
    }

    String CMD = "CMD";
    String SEPARATOR = " - ";
    String COLON_SEPARATOR = " : ";
    int NUMBER_ONE = 1;
    String DEFAULT_EXCEPTION_FORM_VIEW = "defaultExceptionFormView";
    String DEFAULT_EXCEPTION_VIEW = "defaultExceptionView";
    String N_FLAG = "N";
    String Y_FLAG = "Y";
    String MODE_CREATE = "C";
    String MODE_EDIT = "E";
    String MODE_VIEW = "V";
    String MODE_UPLOAD = "U";
    String DOUBLE_BACK_SLACE = "\\";
    String DOUBLE_FORWARD_SLACE = "//";
    String QUAD_FORWARD_SLACE = "////";
    String QUAD_BACK_SLACE = "\\\\";
    String SEARCH = "S";
    String APPLICABLE = "A";
    long ZERO_LONG = 0l;
    int MAX_FILE_CHARACTER_TO_BE_DISPLAY = 18;
    String FILES_UPLOADED = "Files uploaded";
    String FROM_SYS = "FROM SYSTEM";
    String JASPER_REPORT_NAME = "jasperReport";
    String PDF_CREATOR = "PDFCreator";
    String PDFFOLDERNAME = "PDFReport";
    String PDF_EXTENSION = ".pdf";
    String JRPRINT_EXTENSION = ".jrprint";
    String REPORT_EXTENSION = "jrxml";
    String IMAGES = "images";

    String TASK_STATUS_PENDING = "Pending";
    String TASK_STATUS_APPROVED = "Approved";
    String TASK_STATUS_DRAFT = "Draft";
    String TASK_STATUS_INITIATED = "Initiated";
    String TASK_STATUS_TENDER_INITIATED = "Tender Initiated";
    String TASK_STATUS_COMPLETED = "Completed";
    String TASK_STATUS_REJECTED = "Rejected";
    String DATE_FRMAT = "dd-MMM-yyyy";
    String BUG_OPENBAL_MASTER = "tbAcBugopenBalance";
    int CHEQUEDATEVALIDATION_MONTHS = 3;
    String ONE = "1";
    String VIEW_MODE = "viewMode";
    String VIEW = "VIEW";
    String EDIT = "EDIT";
    String ZERO = "0";
    String MODE = "MODE";
    String SUCCESS_MSG = "Success";
    String FAILURE_MSG = "Fail";
    String MAX_PARENT_LVL = "maxParentLvlLength";
    String CHILD_CODE_DIGIT = "childCodeDigit";
    String SEPARATOR_AC_HEAD = " - ";
    String RESPONSE = "response";
    String ALERT_SUBMIT_SUCCESS = "alert.submitSuccess";
    String ALERT_SAVE_SUCCESS = "alert.saveSuccess";
    String DECISION = "decision";
    String REQUEST_NO = "requestNo";
    String RNL_ESTATE_BOOKING = "ESR";
    String RNL_WATER_TANKER_BOOKING = "WTB";
    String RNL_DEPT_CODE = "RNL";
    String SHIFT_PREFIX_GENERAL = "GENERAL";
    String WATER_DEPARTMENT_CODE = "WT";
    String WATER_RATE_MASTER = "WaterRateMaster";
    String BILL_DISTRIBUTION = "BD";
    String DEMAND_DISTRIBUTION = "DN";
    String FINAL_DEMAND_DISTRIBUTION = "FDN";
    String WATER_DEPT = "WT";
    String DATE_FORMAT_CAPS = "dd/MM/YYYY";
    String EXP_DET_LIST_DTO = "expDetListDto[ ";
    String BA_ACCOUNT_ID = " baAccountId=";
    String DPE = "DPE";
    String BPE = "BPE";
    String BP = "BP";
    String RP = "RP";
    String DSE = "DSE";
    String CONTRACT = "Contract";
    String SOLID_WASTE_MGMT = "SWM";

    interface COMMON_PREFIX {
        String COA = "COA";
        String SAM = "SAM";
        String CWZ = "CWZ";
        String RCZ = "RCZ";
        String RWS = "RWS";
    }

    interface FileStorage {
        String DMS = "D";
    }

    interface Actions {
        String CREATE = "create";
        String UPDATE = "update";
        String DELETE = "delete";
    }

    interface ERROR_CODE {
        String PROCESS_REQUESTORE_ERROR = "SP102";
        String ERROR_CAUSE = "could not process request due to : ";
        String BAD_REQUEST = "400";
        String INTERNAL_SERVER_ERROR = "500";
        String CARE_REOPEN_EXPIRED_ERROR = "ERR500";
    }

    interface COMMON_STATUS {
        String SUCCESS = "success";
        String FAILURE = "failure";
        String FAIL = "fail";
        String SAVE_OK = "save.ok";
    }

    interface SECONDARY_MASTER {
        String SECONDARY_SEQ_TABLE_NAME = "TB_AC_SECONDARYHEAD_MASTER";
        String SECONDARY_CODE_SEQ_FIELD_NAME = "SAC_HEAD_CODE";
        String SECONDARY_SEQ_CONTINUE_VALUE = "C";
    }

    interface ApplicationStatus {
        String HOLD = "H";
        String PENDING = "P";
        String RESUBMIT = "R";
    }

    interface FORM_URL {
        String PAY_AT_ULB_COUNTER = "ChallanAtULBCounter.html";
        String CHECKLIST_SEARCH = "ChecklistVerificationSearch.html";
        String DOCUMENT_RESUBMISSION = "DocumentResubmission.html";
    }

    interface PAY_STATUS {
        String PAID = "Y";
        String NOT_PAID = "N";
    }

    interface CHALLAN_STATUS {
        String INACTIVE = "I";
        String ACTIVE = "A";
    }

    interface CHALLAN_RECEIPT_TYPE {
        String REVENUE_BASED = "Y";// 'Y' stands for only bill charges without service charges at application time
        String NON_REVENUE_BASED = "N";// 'N' stands for only service charges without bill charges at application time
        String MIXED = "M";// 'M' stands for service with application/Service update as well as bill update
        String OBJECTION = "O"; // at application time
    }

    interface CHALLAN_NO_PARAM {
        String Module = "CFC";
        String Table = "TB_CHALLAN_MASTER";
        String Column = "CHALLAN_NO";
        String Reset_Type = "F";
    }

    interface RECEIPT_MASTER {
        String Module = "AC";
        String Table = "TB_RECEIPT_MAS";
        String Column = "RM_RCPTNO";
        String Reset_Type = "F";
        String VOUCHER_NO = "0000000000";
    }

    interface PAYMENT_TYPE {
        String OFFLINE = "N";
        String PAY_AT_COUNTER = "P";
        String ONLINE = "Y";
    }

    interface TABLE_COLUMN {
        String CHALLAN_ID = "challanId";
        String CHALLAN_NO = "challanNo";
        String APPLICATION_NO = "applicationNo";
        String REF_NO = "REF_NO";
        String ORGANISATION = "orgId";
        String PAYMENT_MODE = "paymentMode";
        String RECEIVE_FLAG = "receivedFlag";
        String RECEIVE_DATE = "challanRcvdDate";
        String RECEIVE_BY = "challanRcvdBy";
        String CHECKLIST_FLAG = "apmChklstVrfyFlag";
        String APPLICATION_SUCESS_FLAG = "apmApplSuccessFlag";
        String APPLICATION_ID = "apmApplicationId";
        String CHALLAN_STATUS = "challanStatus";
    }

    interface ComparamMasterConstants {
        String CHILDFORMMODE = "childFormMode";
        String DISHONOUR_CHARGE = "Dishonour Charge";
        String WATER_CHARGES = "Water Charges";
        String WATER_TAX = "waterTax";
        String METER_RENT = "Meter Rent";
    }

    interface CommonConstants {
        String ADD = "ADD";
        String EDIT = "EDIT";
        String VIEW = "VIEW";
        String COMMAND = "command";
        String LEVEL = "level";
        String SUCCESS_PAGE = "successPage";
        String ERROR = "Errors";
        String PAGE = "page";
        String ROWS = "rows";
        String INACTIVE = "I";
        String ACTIVE = "A";
        String ZERO = "0";
        String ONE = "1";
        String BLANK = "";
        String DATE_FORMAT_HH_mm_ss = "HH:mm:ss";
        String EDIT_MODE = "editMode";
        String DATE_FORMATE = "dd/mm/yyyy";
        String D_FORMATE = "dd/MM/yyyy";
        String SUCCESS_URL = "successUrl";
        String LOCID = "locId";
        String NA = "NA";
        String COM = "COM";
        String V = "V";
        String C = "C";
        String Q = "Q";
        String B = "B";
        String N = "N";
        Character CHAR_N = 'N';
        Character CHAR_Y = 'Y';
        Character CHAR_C = 'C';
        Character CHAR_U = 'U';
        Character CHAR_D = 'D';
        Character CHAR_A = 'A';
        Character CHAR_R = 'R';
        Character CHAR_P = 'P';
        String E = "E";
        String DATE_F = "d-M-yyyy";
        String U = "U";
        String Y = "Y";
        String EMPTY_LIST = "EmptyList";
        String DEPARTMENT_LIST = "departmentList";
        String P = "P";
        String FORTY_FIVE = "45";
        String NOD = "NOD";
    }

    interface Complaint {

        String[] ExludeCopyArrayDEPTCOMPL = { "updatedBy", "updatedDate", "deptCompId" };
        String[] ExludeCopyArrayCOMPLAINTSUBTYPE = { "updatedBy", "updatedDate", "compId" };
        String MODE_CREATE = "C";
        String MODE_EDIT = "E";
        String MODE_VIEW = "V";
    }

    interface DEPENDENT_TABLE_COLUMN {

        String UPDATED_BY = "updatedBy";

        String UPDATED_DATE = "updatedDate";

        String LG_IP_MAC = "lgIpMacUpd";
    }

    interface fieldName {

        String EWZFieldName = "tbLocationMas.locElectrolWZMappingDto[0].codIdElecLevel";
        String OWZFieldName = "tbLocationMas.locOperationWZMappingDto[0].codIdOperLevel";
        String OWZFieldNameCare = "locOperationWZMappingDto[0].codIdOperLevel";
        String LCT = "LCT";
    }

    interface Req_Status {
        String SUCCESS = "success";
        String FAIL = "fail";
    }

    interface WorkFlow {

        String STATUS = "P";
        String ISDELETED = "N";
        String INACTIVE = "Y";
        String CONDITIONAL_STEP = "C";
        String ACTIONS = "actions";
        String ADMIN_USERID = "admin";
        String EMPLOYEE = "EMPLOYEE";
        String WORKFLOW_LEVEL_DECIDE = "WFD";
        String LAST_APPROVE_LEVEL = "LAL";
        String APPLICATIONID_ERROR = "workflow.applicationId.noRecordFound";
        String REFERENCEID_ERROR = "No Record found Or Please enter correct Reference Id";
        String FORWARD_FOR_CLARIFICATION_PARENT="FORWARD_FOR_CLARIFICATION_PARENT";

        interface Designation {
            String CITIZEN = "workflow.designation.citizen";
            String SYSTEM = "workflow.user.system";
            String DSGN_SHORTNAME = "OPR";
        }

        interface Remarks {
            String ESCALATED = "workflow.remark.escalated";
            String DECISION_ROOT = "workflow.action.decision.";
            String WORKFLOW_STATUS = "workflow.status";
        }

        interface BpmpDelayPeriods {
            String DAY = "d";
            String HOURS = "h";
            String MINUTES = "m";
            String SECONDS = "s";
            String MILLISECONDS = "ms";
        }

        interface Process {
            String CARE = "care";
            String WATER = "water";
            String MAKER_CHECKER = "maker-checker";
            String MAKER_CHECKER_OBJ_LOI ="maker-checker-obj-loi";
        }

        interface EventNames {
            String CHECKER = "Checker";
            String INITIATOR = "Initiator";
        }

        interface EventLabels {
            String INITIATOR = "workflow.eventLabels.initiator";
            String EVENT_ROOT = "workflow.eventLabels.event.";
        }

        interface WorkflowExecutionParameters {
            String CHECKER_LEVELS = "checkerLevels";
            String CHECKER_LEVEL_GROUPS = "checkerLevelGroups";
            String CURRENT_CHECKER_GROUP = "currentCheckerGroup";
            String CURRENT_CHECKER_LEVEL = "currentCheckerLevel";
            String REQUESTER_TASK_ASSIGNMENT = "requesterTaskAssignment";
        }

        interface Status {
            String PENDING = "PENDING";
            String EXPIRED = "EXPIRED";
            String CLOSED = "CLOSED";
            String HOLD = "HOLD";
            String IN_PROGRESS = "IN_PROGRESS";
            String ESCALATED = "ESCALATED";
            String COMPLETED = "COMPLETED";
            String NOT_ASSIGNED = "NOT_ASSIGNED";
            String EXITED = "EXITED";
        }

        interface Decision {
            String SUBMITTED = "SUBMITTED";
            String APPROVED = "APPROVED";
            String HOLD = "HOLD";
            String FORWARD_TO_DEPARTMENT = "FORWARD_TO_DEPARTMENT";
            String FORWARD_TO_EMPLOYEE = "FORWARD_TO_EMPLOYEE";
            String REJECTED = "REJECTED";
            String REOPENED = "REOPENED";
            String PENDING = "PENDING";
            String SEND_BACK = "SEND_BACK";
            String LOI_DELETED = "LOI_DELETED";
            String FORWARD_TO_OTHER_ORGANISATION = "FORWARD_TO_ORGANISATION";
            String INSPECTION = "INSPECTION";
            String HEARING = "HEARING";
            String ESCALATED = "ESCALATED";
            String FOLLOWUP = "FOLLOWUP";
            String SEND_FOR_FEEDBACK = "SEND_FOR_FEEDBACK";
            String SEND_TO_CLERK = "SEND_TO_CLERK";
            String SEND_BACK_REMARK = "Send Back";
            String FORCE_CLOSURE = "FORCE_CLOSURE";
            String SEND_BACK_TO_APPLICANT="SEND_BACK_TO_APPLICANT";
            String INITIATOR="INITIATOR";
            String FORWARD_FOR_CLARIFICATION="FORWARD_FOR_CLARIFICATION";
        }

        interface Signal {
            String HEARING = "SIGNAL_HEARING";
        }

        interface StatusForDecision {
            static String getStatusForDecision(String decision) {
                String status = "";
                switch (decision) {
                case Decision.APPROVED:
                case Decision.FORCE_CLOSURE:
                    status = "CLOSED";
                    break;
                case Decision.HOLD:
                    status = "HOLD";
                    break;
                case Decision.REJECTED:
                    status = "REJECTED";
                    break;
                case Decision.FORWARD_TO_DEPARTMENT:
                case Decision.FORWARD_TO_EMPLOYEE:
                case Decision.LOI_DELETED:
                case Decision.SEND_BACK:
                case Decision.PENDING:
                case Decision.SUBMITTED:
                case Decision.REOPENED:
                case Decision.FOLLOWUP:
                case Decision.SEND_FOR_FEEDBACK:
                    status = "PENDING";
                    break;
                }
                return status;
            }

        }

        interface DecisionResponseData {
            static String getResponseData(String decision) {
                String getResponseData = "";
                switch (decision) {
                case Decision.APPROVED:
                    getResponseData = "is Approved";
                    break;
                case Decision.HOLD:
                    getResponseData = "is Kept On Hold";
                    break;
                case Decision.FORWARD_TO_DEPARTMENT:
                    getResponseData = "is Forwarded to Department";
                    break;
                case Decision.FORWARD_TO_EMPLOYEE:
                    getResponseData = "is Forwarded to Employee";
                    break;
                case Decision.REJECTED:
                    getResponseData = "is Rejected";
                    break;
                case Decision.PENDING:
                    getResponseData = "is Pending";
                    break;
                }
                return getResponseData;
            }
        }

        interface BpmBrmDeployment {
            String BPMBRMDEPLOYMENT_MASTER_FORM = "BpmBrmDeploymentMasterForm";
            String ADD_BPMBRMDEPLOYMENT_MASTER = "AddBpmBrmDeploymentMaster";
            String EDIT_BPMBRMDEPLOYMENT_MASTER = "EditBpmBrmDeploymentMaster";
            String VIEW_BPMBRMDEPLOYMENT_MASTER = "ViewBpmBrmDeploymentMaster";
            String DELETE_BPMBRMDEPLOYMENT_MASTER = "DeleteBpmBrmDeploymentMaster";
            String GROUPID = "groupId";
            String ARTIFACTID = "artifactId";
            String PROCESSID = "processId";
            String VERSION = "version";
            String STATUS = "status";
            String BPMRUNTIME = "bpmRuntime";
            String NOTIFYUSERS = "notifyUsers";
            String NOTIFYTODEPARTMENT = "notifyToDepartment";
            String DEPLOYMENT_CHANGE_LISTENER_ROOT = "mainet.bpm.deploymentChangeListener.";

            // BPM SaveMode
            interface SaveMode {
                String EDIT = "E";
                String CREATE = "C";
                String VIEW = "V";
                String DELETE = "D";
            }

            interface MessagesKey {
                String VALIDATION_DEPLOYMENT_ALREADY_EXIST = "bpm.validation.deploymentAlreadyExist";
            }
        }
    }

    interface MeterCutOffRestoration {
        String METER_CUTOFF = "C";
        String METER_RESTORATION = "R";
        String METER_STATUS_ACTIVE = "A";
        String METER_STATUS_INACTIVE = "I";
        String BILLING_STATUS_ACTIVE = "A";
        String BILLING_STATUS_INACTIVE = "I";

    }

    interface AUDIT {
        String METHOD_NAME_SAVE = "createHistoryForObject";
        String POINTCUT_SAVE = "execution(* com.abm.mainet.common.audit.service.AuditServiceImpl.createHistory(..)) && args (sourceObject,targetObject)";
    }

    interface FileParameters {
        String FILE_LIST_1 = "file_list_";

        String FILE_LIST_2 = "_file_";

        String FILE_LIST_3 = "file_";
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

    interface HTML_CHARACTER {
        String UL_START = "<ul>";
        String UL_START_1 = "<ul style='margin-left: 104px;'>";
        String UL_END = "</ul>";

        String LI_START = "<li>";
        String LI_END = "</li>";

        String BOLD_START = "<b>";
        String BOLD_END = "</b>";

        String NBSP = "&nbsp;&nbsp;";

        String IMAGE_TAG_3 = "\" uniqueId=\"";

        String IMAGE_TAG_2 = "\" onclick=\"doFileDelete(this)\" >";

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
        String INVALID_FILE_ERROR = "invalidFileError";
        String FILE_SIZE_VALIDN = "fileSizeValidn";
        String FILE_UPLOAD_VALIDATION_MSG = "attachment";
        // String FOR_CITI_REGISTRATION = "citizenRegistrationModel";

    }

    interface Validation_Constant {

        String[] CHECK_LIST_VALIDATION_EXTENSION = { "pdf", "doc", "docx", "jpeg", "jpg", "png", "gif", "bmp" };
        String[] CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS = { "pdf", "doc", "docx", "xls", "xlsx" };
        String[] CHECK_LIST_VALIDATION_EXTENSION_HELPDOC = { "pdf" };
        String[] EXCEL_UPLOAD_VALIDATION_EXTENSION = { "xls" };
        String[] IMAGE_UPLOAD_VALIDATION_EXTENSION = { "jpg", "jpeg", "png", "gif", "bmp" };
        String[] TP_VALIDATION_EXTENSION = { "dxf", "dwg", "pdf", "zip", "rar" };
        String[] ALL_UPLOAD_VALID_EXTENSION = { "pdf", "doc", "docx", "jpeg", "jpg", "png", "gif", "bmp", "xls",
                "xlsx" };
        String[] CHECK_LIST_VALIDATION_EXTENSION_BND = { "pdf", "doc", "docx", "jpeg", "jpg" };
        String[] RTI_FILE_UPLOAD_EXT = { "pdf", "doc", "docx", "jpeg", "jpg" };
        String[] EXCEL_IMPORT_VALIDATION_EXTENSION = { "xlsx", "csv" };
        String[] EXCEL_IMPORT_VALIDATION_EXTENSION_XLS = { "xlsx", "csv","xls"};
        String[] CARE_VALIDATION_EXTENSION = { "pdf", "png", "jpg" };
        String[] CHECK_LIST_VALIDATION_EXTENSION_MRM = { "pdf", "jpeg", "jpg", "png" };
        String[] CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC = { "pdf", "doc", "docx" };
        String[] CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS_PNG = { "pdf", "doc", "docx", "xls", "xlsx", "png", "jpeg"};
        String[] CHECK_LIST_VALIDATION_EXTENSION_ECHALLAN = {"png", "jpeg", "pdf", "avi", "mp4", "mpeg", "3gp", "wmv", "flv", "mkv","webm"};
        String[] CHECK_LIST_VALIDATION_EXTENSION_TCP = { "pdf", "jpeg", "jpg"};
        String[] EXCEL_UPLOAD_VALIDATION_EXTENSION_KML = { "kml" };
    }

    interface CheckList_Size {
        int COMMOM_MAX_SIZE = 2097152;
        int BND_COMMOM_MAX_SIZE = 5242880;
        int CHECK_COMMOM_MAX_SIZE = 20971520;
        int PORTAL_COMMON_SIZE = 15728640;
        int WORK_COMMON_MAX_SIZE = 5242880;
        int CARE_COMMON_MAX_SIZE = 1048576;
        int TRADE_COMMON_MAX_SIZE = 1048576;
        int MAX_FILE_SIZE = 102400;
        int MIN_FILE_SIZE = 204800;
        int DMS_MAX_SIZE = 10485760;
        int OBJ_DOC_SIZE = 26214400;  // 25 MB
        int ECHALLAN_MAX_SIZE = 52428800; // 50 MB
        int FILE_SIZE = 1048576;
        int COUNCIL_MAX_SIZE = 62914560;//60 MB
        int TCPHR_MAX_FILE_SIZE = 104857600; // 100 MB
        int COUNCIL_MOM_MAX_SIZE = 125829120;//120 MB
    }

    interface MAX_FILEUPLOAD_COUNT {
        int CHECK_LIST_MAX_COUNT = 1;
        int CHECKLIST_VRFN_ALL = 3;
        int CHECKLIST_MAX_UPLOAD_COUNT = 5;
        int QUESTION_FILE_COUNT = 3;
        int RTI_FILE_COUNT = 3;
        int WORKS_MANAGEMENT_MAXSIZE = 20;
        int COUNCIL_FILE_COUNT = 1;
    }

    // **********PaymentConstant********
    interface COMMON {

        String VIEWNAME = "MobilePayOnline";
        String COMMAND = "command";
        String DEFAULT_EXE_VIEWNAME = "defaultExceptionView";
        String FAILVIEWNAME = "MobilePayOnlineFail";
        String COMMON_ACKNOWLEDGEMENT = "CommonAcknowledgement";
        String EMPLOYEE="EMPLOYEE";
        String CENTRALENO="CENTRALENO";
		String VIEWNAME_ONL = "onlineReciept";
		String VIEWNAME_OFL = "oflineReciept";
    }

    interface HelpDoc {
        String HELPDOC = "HELP_DOCS";
    }

    interface FileCount {
        String ZERO = "0";
        String ONE = "1";
    }

    interface REG_ENG {
        String REGIONAL = "REG";
        String ENGLISH = "ENG";
    }

    interface IsLookUp {
        String ACTIVE = "A";
        String INACTIVE = "I";

        interface STATUS {
            String YES = "Y";
            String NO = "N";
        }
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

    interface PAYMODE {

        String MOBILE = "M";
        String WEB = "W";
    }

    interface BankParam {
        String PROD_INFO = "productinfo";
        String AMT = "amount";
        String TXN_ID = "txnid";
        String SALT = "salt";
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

        String ENABLE_CHILD_WINDOW_POSTING = "enableChildWindowPosting";
        String ENABLE_PAYMENT_RETRY = "enablePaymentRetry";
        String RETRY_ATTEMPT_COUNT = "retryAttemptCount";
        String TXT_PAY_CATEGORY = "txtPayCategory";
        String CURRENCY_TYPE = "currencyType";
        String TY_F1 = "typeField1";
        String TY_F2 = "typeField2";
        String TY_F3 = "typeField3";
        String SECURITYID = "securityId";
        String CustAccNo = "custacc";
		String Login = "login";
		String Password="pass";
		String prodId = "prodid";
		String ChallanYear = "ChallanYear";
		String DDO = "DDO";
		String DTO = "DTO";
		String Deptcode = "Deptcode";
		String STO = "STO";
		String BankId = "BankId";
		String SchemeCount = "SchemeCount";
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
        String STC = "STC"; // The status code for this transaction
        String SUCCESS_CODE = "000";
        String FAILURE_CODE = "111";
        String TET = "TET"; // Transaction Date and Time
        String RMK = "RMK"; // The status message for this transaction
        String BRN = "BRN"; // Reference number generated by the bank post transaction
        String PMD = "PMD"; // The mode of payment used for performing transaction
        String TRN = "TRN"; // Unique Transaction ID
        String Encrypted_req_response = "i";
        String IN_PROCESS_OR_PENDING = "pending";
        String DOUBLE_DASH = "--";
        String MERCHANT_REQUEST = "merchantRequest";
        String MID_AWL = "MID";
        String EASYPAY_TOKEN = "j";
        String EASYPAY_DEFAULT_KEY = "token";
        String EASYPAY_DEFAULT_TOKEN = "NO_TOKEN_RECEIVED";
    }

    interface PAY_PREFIX {
        String PREFIX_VALUE = "PAY";
        String BANK = "B";
        String ONLINE = "W";
    }

    interface PAYMENT_POSTING_API {
        String REQUEST_BODY = "requestBody";

        String RESPONSE_BODY = "responseBody";
        String ERROR_CODE_101 = "E-101";
        String ERROR_CODE_101_MSG = "Invalid parameters found in request body";
        String ERROR_CODE_102 = "E-102";
        String ERROR_CODE_102_MSG = "Duplicate Transaction Number";
        String ERROR_CODE_103 = "E-103";
        String ERROR_CODE_103_MSG = "Technical Error while processing request";
        String ERROR_CODE_104 = "E-104";
        String ERROR_CODE_104_MSG = "Mandatory parameter missing";
        String ERROR_CODE_105 = "E-105";
        String ERROR_CODE_105_MSG = "Record Not Found For Given request";
        String ERROR_CODE_106 = "E-106";
        String ERROR_CODE_106_MSG = "Record Not Found For Given Transaction Number";
        String ERROR_CODE_107 = "E-107";
        String ERROR_CODE_107_MSG = "Unable to decrypt request body";
        String ERROR_CODE_108 = "E-108";
        String ERROR_CODE_108_MSG = "Unable to process transaction for this request";
        String ERROR_CODE_109 = "E-109";
        String ERROR_CODE_109_MSG = "Invalid Organisation Code or Department Code";
        String PAYTM = "Paytm";
        String WATER_BILL_SERVICE = "WaterBillService";
        String PROPERTY_BILL_PAYMENT = "PropertyBillPayment";
        String RNL_BILL_PAYMENT = "RNLBillService";
        String ERROR_CODE_110 = "E-110";
        String ERROR_CODE_110_MSG = "Unauthorized UserId And Password";
        String ERROR_CODE_111 = "E-111";
        String ERROR_CODE_111_MSG = "Invalid Organisation Code";
        String ORG_CODE = "orgCode";
        String AUTHORIZATION = "Authorization";
        String PG_NAME = "pgName";
        String AUTH_STATUS = "authStatus";
        String ERROR_CODE = "error_Code";
        String ERROR_MSG = "error_msg";
        String DEFAULT_KEY = "Defa@ltkey$4321#";
        String SUCCESS_TRANS_MSG = "Your Transaction is Successfull for Transaction No. ";
        String SUCCESS_RECEIPT_MSG = " and Receipt No. for This Transaction is ";
        String ERROR_CODE_112 = "E-112";
        String ERROR_CODE_112_MSG = "No Receipt Found for This Transaction No.";
        String RTI_BILL_PAYMENT = "RTIBillService";
        String ADH_BILL_PAYMENT = "ADHBillPayment";
    }

    interface REQUIRED_PG_PARAM {
        String OWNER_NAME = "ownerName";
        String ORGNAME = "orgName";
        String APP_ID_LABEL = "app_no_label";
        String PAYU = "redirectToPayUPG";
        String HDFC = "redirectToHDFCPG";
        String MOL = "redirectToMHPG";
        String TP = "redirectToTPPG";
        String NA = "NA";
        String DUE_AMT = "dueAmt";
        String SERVICE_ID = "serviceId";
        String PHONE_NO = "phone";
        String AMT = "amount";
        String SESSION_AMT = "sessionAmount";
        String TRANS_ID = "mihpayid";
        String STATUS = "status";
        String EMAIL = "email";
        String TRACK_ID = "txnid";
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
        String PG_ID = "pgId";
        String APPLICATION_NO = "applicationId";
        String COMPLAINT_NO = "complaintId";
        String UDF1 = "udf1";
        String UDF2 = "udf2";
        String UDF3 = "udf3";
        String UDF5 = "udf5";
        String UDF6 = "udf6";
        String UDF7 = "udf7";
        String UDF8 = "udf8";
        String UDF9 = "udf9";
        String MSG = "msg";
        String IV = "iv";
        String LANG = "lang";
        String ORGID = "orgId";
        String EIP_PAY_KEY = "eip.payment.key";
        String EIP_PAY_IV = "eip.payment.iv";
        String EIP_PAY_BANKID = "eip.payment.bankId";
        String EIP_PAY_APPNO = "eip.payment.applNo";
        String EIP_PAY_MSG = "eip.payment.message";
        String EIP_PAY_TEMPLATE_KEY = "eip.payment.templateId";
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
        String CCA = "redirectToCCA";
        String SUCCESS_HDFC_CCA = "?hdfcCCARedirect";
        String CANCEL_HDFC_CCA = "?hdfcCCAcancel";
        String FAIL_HDFC_CCA = "?hdfcCCAfail";
        String AWL = "redirectToAWL";
        String SUCCESS_HDFC_AWL = "?hdfcAWLRedirect";
        String CANCEL_HDFC_AWL = "?hdfcAWLcancel";
        String FAIL_HDFC_AWL = "?hdfcAWLfail";
        String DEFAULT_LANG = "EN";
        String MERCHANT_ID = "merchant_id";
        String ORDER_ID = "order_id";
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
        String ORDER_NO = "orderNo";
        String WORKING_KEY = "working_key";
        String FAILURE_MESSAGE = "failure_message";
        String RESPONSE_CODE = "response_code";
        String BANK_REF_NO = "bank_ref_no";
        String TRACKING_ID = "tracking_id";
        String ORDER_STATUS = "order_status";
        String STATUS_MESSAGE = "status_message";
        String PAYMENT_MODE = "payment_mode";
        String SEARCH_SPECIAL_CHAR_REPLACE = "=";
        String DATEFORMAT_DDMMYYYY = "dd-MM-yyyy";
        String BILLING_NAME = "billing_name";
        String FAIL = "?failPayU";
        String CANCEL = "?cancelPayU";
        String SUCCESS_EASYPAY = "?easypayRedirect";
        String CANCEL_EASYPAY = "?easypayCancel";
        String FAIL_EASYPAY = "?easypayFail";
        String RESPONSE_MSG = "response_msg";
        String SUCCESS_ICICI_CCA = "?iciciCCARedirect";
        String CANCEL_ICICI_CCA = "?iciciCCAcancel";
        String FAIL_ICICI_CCA = "?iciciCCAfail";
        String SUCCESS_BILLCLOUD = "?billCloudRedirect";
        String CANCEL_BILLCLOUD = "?billCloudcancel";
        String FAIL_BILLCLOUD = "?billCloudfail";
		String SUCCESS_NIC = "?nicSuccess";
    }

    interface PAY_STATUS_FLAG {
        String NO = "N";
    }

    interface PAYU_STATUS {
        String SUCCESS = "success";
        String FAIL = "failure";
        String PENDING = "pending";
        String CANCEL = "Cancel";
        String ABORTED = "Aborted";
        String RECEIVED = "RECEIVED";
        String IN_PROGRESS = "In progress";
        String COMM_RES_MSG = "Your Request Completely has been  Procced";
        String COMM_ERR_MSG = "Sorry, Unable to process your request.Please contact to Administrator and tryagain later";
        String TECH_ERROR = "Technical Error";
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
		String NicGateway = "NicGateway";
    }

    interface PG_REQUEST_PROPERTY {
        String TXN = "TXN";
        String CRN = "INR";
        String SERVERTIMEOUT = "1000";
        String PAYMNETSTATUS = "0300";
        String STATUSCODE = "001";

    }

    interface WORKFLOWTYPE {
        String WORKFLOW_HTML = "WorkFlowType.html";
        String[] ExludeCopyArrayFlow = { "updatedBy", "updatedDate", "tntId" };
        String[] ExludeCopyArrayFlowMapping = { "updatedBy", "updatedDate", "propDetId" };
        String MODE_CREATE = "C";
        String MODE_EDIT = "E";
        String MODE_VIEW = "V";
        String Flag_A = "A";
        String Flag_D = "D";
        String WFM = "WFM";
        String ALL = "All";
        String EMP_OR_ROLE = "empOrRole";
        String MAPPING = "mapping";
        String EMP_ROLE_WARD_ZONE = "empOrRoleWardZone";
        String DELETE_FLOW = "deleteFlow";
        String CHECK_EXISTING = "checkExisting";
        String WORKFLOW_TYPE_HOME = "workflowType/Home";
        String WORKFLOW_TYPE_FORM = "workflowType/Form";
        String WORKFLOW_TYPE_EDIT_FORM = "workflowType/EditForm";
        String WORK_FLOW_WARD_ZONE = "workFlowWardZone";
        String WORK_FLOW_TYPE_DTO_COD_ID_OPER_LEVEL = "workFlowMasDTO.codIdOperLevel";
        String GET_TOTAL_EMP_COUNT = "getTotalEmployeeCountByRoles";
        String ROLE_IDS = "roleIds";

    }

    interface COMMON_ENTITY_FIELD_CONSTANT {
        String ORGID = "orgId";
        String PAY_TYPE_FLAG = "paymentTypeFlag";
        String FUNCTION_ID = "functionId";
        String FUND_ID = "fundId";
        String LIST = "list";
        String PARENTCODE = "parentCode";
        String FIELD_ID = "fieldId";
        String PAC_HEAD_ID = "pacHeadId";
        String SAC_HEAD_ID = "sacHeadId";
        String EXCEPTION_VIEW = "error/generic_errorView";
        String FAYEAR_ID = "faYearid";
        String DEPT_ID = "dpDeptid";
        String CPD_BUG_TYPE_ID = "cpdBugtypeId";
        String CPD_ID_DR_CR = "cpdIdDrcr";
        String BUDGET_CODE_ID = "prBudgetCodeid";

    }

    interface BANK_MASTER {
        String BANK_TYPE = "bankType";
        String ACCOUNT_TYPE = "accountType";
        String ACCOUNT_STATUS = "accountStatus";
        String BANKNAME_LIST = "bankNameList";
        String FIELD_LIST = "listOfTbAcFieldMasterItems";
        String I = "I";
        String PRIMARY_LIST = "listOfTbAcFunctionMasterItems";
        String FUNCTION_LIST = "functionMasterList";
        String FTY = "FTY";
        String BK = "BK";
        String ACCOUNT_HEAD = "accountHead";
        String LIST_BANK_ACCOUNT_CURRENT_DATE = "listOfTbBankaccount.baCurrentdate";
        String CREATE_FUND = "createFund";
        String VIEW_FUND = "viewFund";
        String BANK_MAP_LIST = "bankMap";
        String BANK_ACCOUNT_MAP_LIST = "bankAccountMap";
        String BANK_ACCOUNT_INT_TYPE = "bankAccountIntType";

    }

    interface VENDOR_MASTER {
        String CUST_BANKLIST = "custBankList";
        String MAIN_ENTITY_NAME = "tbAcVendormaster";
        String MAIN_LIST_NAME = "list";
        String CPD_VENDOR_TYPE = "cpdVendortype";
        String ERROR_VENDOR_TYPE = "ErrorcpdVendortype";
        String VM_VENDOR_NAME = "vmVendorname";
        String ERROR_VENDOR_NAME = "ErrorvmVendorname";
        String PAN_NUMBER_TEMP = "vmPanNumberTemp";
        String ERROR_PAN_NUMBER = "ErrorvmPanNumber";
        String VENDOR_SUB_TYPE = "cpdVendorSubType";
        String ERROR_VENDOR_SUB_TYPE = "ErrorcpdVendorSubTypes";
        String VM_VENDOR_ADD = "vmVendoradd";
        String ERROR_VM_VENDOR_ADD = "ErrorvmVendoradd";
        String VM_CPD_STATUS = "vmCpdStatus";
        String VENDOR_VMVENDORCODE2 = "vendor_vmvendorcode";
        String ORGID2 = "orgid";

    }

    interface TAX_HEAD_MAPPING_MASTER {

        String MAIN_LIST_NAME = "list";
        String MODE_DATA = "MODE_DATA";
        String TAX_HEADS_MASTER_BEAN = "tdsTaxHeadsMasterBean";
        String TAX_DESCRIPTION_ITEMS = "listOfTbComparamDetItems";
        String FIELD_MASTER_ITEMS = "listOfTbAcFieldMasterItems";
        String TDS_ID = "tdsId";

    }

    interface BANK_MASTER_PAY_TDS {
        String MAIN_ENTITY_NAME = "tbAcPayToBank";
    }

    interface PRIMARYCODEMASTER {
        String PRIMARYCODE_BEAN = "primaryCodeMasterBean";
        String PRIMARY_CODE_STATUS = "primaryCodeStatus";
        String HEAD_TYPE = "headType";
        String NODE = "node";
        String OT = "OT";
        String MODE_FLAG = "modeFlag";
        String VIEW_MODE = "viewmode";
        String ADD = "ADD";
        String COUNT = "count";
        String BUDGET_CHECK_FLAG_LIST = "budgetCheckFlagList";

    }

    interface BUG_HEAD_OPENING_BALANCE_MASTER {
        String BUG_PAC_SAC_HEAD_LIST = "bugpacsacHeadList";
        String BUG_CPD_VALUE = "DCR";
        String BUG_BAL_TYPE_LIABILITY = "DR";
        String BUG_BAL_TYPE_ASSETS = "CR";
        String LEVELS_MAP = "levelMap";
        String FINANCE_MAP = "financeMap";
        String MODE = "MODE_DATA";
        String MODVIEW = "modeView";
        String MODE_CREATE = "create";
        String MODE_UPDATE = "update";
        String OPN_ID = "opnId";
        String FALSE = "false";
        String TRUE = "true";
        String FUND_WISE_STATUS = "FN";
        String FIELD_WISE_STATUS = "FL";
        String FUND_FIELD_STATUS_PREFIX = "OBC";
        String SLI_PREFIX_VALUE = "SLI";
        String SLI_PREFIX_LIVE_VALUE = "L";
        String BUG_OPN_BAL_TYPE_VALUE = "COA";
        String LIABILITY_OPN_STATUS = "L";
        String ASSET_OPN_BAL_TYPES = "A";
        String DRCR_LEVELS_MAP = "drCrLevelMap";
        String STATUS_CPD_VALUE = "statusLookUpList";
        String OPN_BAL_NEW_LIST = "pacHeadMap";
        String OPN_BAL_MASTER = "tbAcBugopenBalance";
    }

    interface CONFIG_MASTER {
        String COMPONENT = "component";
        String MAIN_ENTITY_NAME = "tbAcCodingstructureMas";
        String LISTOFLOOKUP = "tempList";
        String STATUSLOOKUP = "statuslookUpId";
    }

    interface BUDGET_PROJECTED_REVENUE_ENTRY_MASTER {

        String FINANCE_MAP = "financeMap";
        String FUND_MASTER_ITEMS = "listOfTbAcFundMasterItems";
        String FUNCTION_MASTER_ITEMS = "listOfTbAcFunctionMasterItems";
        String PAC_HEAD_MASTER_ITEMS = "listOfPrimaryAcHeadMapMasterItems";
        String BUG_SUB_TYPE_LEVEL_MAP = "bugSubTypelevelMap";
        String DEPT_ID = "dpDeptid";
        String FA_YEAR_ID = "faYearid";
        String INCOME_OTHER_FIELD = "I";
        String EXPENDITURE_OTHER_FIELD = "E";
        String LIABILITY_OTHER_FIELD = "L";
        String ASSET_OTHER_FIELD = "A";
        String ACCOUNT_BUDGET_CODE_MAP = "accountBudgetCodeMap";
        String BUDGET_CODE_ID = "prBudgetCodeid";
        String BUDGET_SUB_TYPE_ID = "cpdBugsubtypeId";
        String BUDGET_TYPE_ID = "cpdBugtypeId";
        String FUND_MAP = "fundMap";
        String FUNCTION_MAP = "functionMap";
        String MAIN_ENTITY_NAME = "tbAcBudgetProjectedRevenueEntry";
        String BUDGET_REV_PERCENT = "BudgetRevPercent";
    }

    interface BUDGET_PROJECTED_EXPENDITURE_MASTER {

        String FINANCE_MAP = "financeMap";
        String FIELD_MASTER_ITEMS = "listOfTbAcFieldMasterItems";
        String FA_YEAR_ID = "faYearid";
        String DEPT_ID = "dpDeptid";
        String ACCOUNT_BUDGET_CODE_MAP = "accountBudgetCodeMap";
        String BUDGETCODE_ID = "prBudgetCodeid";
        String BUDGETSUB_TYPE_ID = "cpdBugsubtypeId";
        String FUND_MAP = "fundMap";
        String FUND_LISI = "fundList";
        String IS_FUND_APPLICABLE = "isApplicable";
        String FUNCTION_MAP = "functionMap";
        String MAIN_ENTITY_NAME = "tbAcBudgetProjectedExpenditure";
        String FIELD_ID = "fieldId";
        String BUDGET_EXP_PERCENT = "BudgetEXPPercent";

    }

    interface BUDGET_REAPPROPRIATION_MASTER {

        String FINANCE_MAP = "financeMap";
        String BUDGET_REV_ORIGINAL_ESTIMATE = "OAMT";
        String BUDGET_REV_BALANCE_PROVISION = "BAMT";
        String BUDGET_EXP_ORIGINAL_ESTIMATE = "OEAMT";
        String BUDGET_EXP_BALANCE_PROVISION = "BEAMT";
        String BUDGET_IDENTIFY_FLAG = "R";
        String FA_YEAR_ID = "faYearid";
        String BUDGET_TYPE_ID = "cpdBugtypeId";
        String DEPT_ID = "dpDeptid";
        String BUDGET_CODE_ID = "prBudgetCodeid";
        String BUDGET_IDENTIFY_FLAG_ID = "budgIdentifyFlag";
        String FIELD_ID = "fieldId";
        String BCR = "BCR";

    }

    interface VOUCHER_TEMPLATE_ENTRY {
        String GRID_DATA = "gridData";
        String COMMAND = "command";
        String FORM_DTO = "formDTO";
        String VOUCHER_TEMPLATE = "voucherTemplate";
        String DTO = "dto";
        String NO_DATA_FOUND = "noDataFound";
        String MODE_FLAG = "modeFlag";
        String VIEW_DATA = "viewData";
        String VIEW_PAGE = "voucherTemplate/view";
        String EDIT_PAGE = "voucherTemplate/edit";
        String TEMPLATE_TYPE_PREFIX = "MTP";
        String PAC_HEAD_MAP_I = "pacSacHeadMapI";

        String PAC_HEAD_MAP_E = "pacSacHeadMapE";
        String PAC_HEAD_MAP_L = "pacSacHeadMapL";
        String PAC_HEAD_MAP_A = "pacSacHeadMapA";
    }

    interface DEPOSIT {
        String ACCOUNT_DEPOSIT = "accountDepositBean";
        String ACCOUNT_DEPOSIT_ID = "depId";
    }

    interface BUDGET_CODE {

        String ACTIVE_VALUE = "A";
        String STATUS_FLAG = "ACN";
        String FUND_WISE_STATUS = "FWB";
        String FIELD_WISE_STATUS = "FLB";
        String FUNCTION_WISE_STATUS = "FNR";
        String DEPT_WISE_STATUS = "FNW";
        String PRIMARY_WISE_STATUS = "PAH";
        String OBJECT_WISE_STATUS = "OHC";
        String FUND_FIELD_DEPT_STATUS_PREFIX = "BHC";
        String ACTIVE_INACTIVE_STATUS_PREFIX = "ACN";
        String ACTIVE_INACTIVE_MAP = "activeDeActiveMap";
        String CPD_ID_STATUS_FLAG = "cpdIdStatusFlag";
        String MAIN_ENTITY_NAME = "tbAcBudgetCode";
    }

    interface BUDGETARY_REVISION {

        String BUDGET_AFTR_TILL_NOV_AMT = "ATN";
        String BUDGET_DEC_TO_MAR_AMT = "DTM";
        String BUDGET_EXP_AFTR_TILL_NOV_AMT = "ATNE";
        String BUDGET_EXP_DEC_TO_MAR_AMT = "DTME";
        String BUDGETORY_REVISION_MASTER = "tbAcBudgetoryRevision";
        String BUDGET_REV_BUDGET_CODE = "prRevBudgetCode";
        String FUND_MAP = "fundMap";
        String FUNCTION_MAP = "functionMap";
    }

    interface TENDER_ENTRY {

        String ACCOUNT_TENDER_ENTRY = "accountTenderEntryBean";
        String DEPT_MAP = "deptMap";
        String TENDER_ENTRY_BEAN = "tenderEntryBean";
        String TENDERID = "trTenderId";
        String MAIN_LIST_NAME = "list";
        String TENDER_AMOUNT = "tenderAmount";
        String TR_TYPE_CPD_ID = "trTypeCpdId";
        String TR_TENDER_NO = "trTenderNo";

    }

    interface BUDGET_ESTIMATION_PREPARATION {

        String FINA_YEAR_START_MONTH = "April";
        String FINA_YEAR_START_DAY = "01";
        String FINA_YEAR_ACTUAL_TILL_NOV_AMT_DAY = "30";
        String FINA_YEAR_ACTUAL_TILL_NOV_AMT_MONTH = "November";
        String ORIGINAL_ESMT_AMT_REV = "OEA";
        String ORIGINAL_ESMT_AMT_EXP = "OEAE";
        String BUDGET_ESTIMATION_PREPARATION_MASTER = "tbAcBudgetEstimationPreparation";
        String BUDGET_REV_BUDGET_CODE = "prRevBudgetCode";
        String FUND_MAP = "fundMap";
        String FUNCTION_MAP = "functionMap";

    }

    interface BUDGET_ADDITIONALSUPPLEMENTAL {

        String FINANCE_MAP = "financeMap";
        String DEPT_MAP = "deptMap";
        String BUG_SUB_TYPE_LEVEL_MAP = "bugSubTypelevelMap";
        String EMPLOYEE_MAP = "employeeMap";
        String BUDGET_IDENTIFY_FLAG = "A";
        String FA_YEAR_ID = "faYearid";
        String BUDGET_TYPE_ID = "cpdBugtypeId";
        String DEPT_ID = "dpDeptid";
        String BUDGET_CODE_ID = "prBudgetCodeid";
        String BUDGET_IDENTIFY_FLAG_ID = "budgIdentifyFlag";
    }

    interface ADVNC_DEPOSIT_MAPP_ENTRY {
        String FUNC_MAS_LAST_LVL = "functionMasterLastLvls";
        String FUND_MAS_LAST_LVL = "fundMasterLastLvls";
        String FIELD_MAS_LAST_LVL = "fieldMasterLastLvls";
        String PRIMARY_MAS_LAST_LVL = "primaryCodeMasterLastLvls";
        String SEC_MAS_LAST_LVL = "secondaryCodeMasterLvls";
        String DEPT_LIST = "departmentlist";
        String BEAN = "accountDepositeAndAdvnHeadsMappingEntryMasterBean";
        String MAPP_ID = "mappingId";
        String DEPOS_ID = "depositeId";
        String ADVN_ID = "advncId";
        String MAPP_TYPE = "mappingType";
        String DEPT_ID = "deptId";
    }

    interface BUDGET_ALLOCATION_MASTER {

        String DEPT_MAP = "deptMap";
        String REV_ORG_ESMT_VALUE = "OAMT";
        String EXP_ORG_ESMT_VALUE = "OEAMT";
        String REV_BUDGET_CODE_MAP = "accountBudgetCodeAllocationMap";
        String EXP_BUDGET_CODE_MAP = "accountBudgetCodeAllocationExpMap";
        String BUDGET_ALLOCATION_MASTER = "tbAcBudgetAllocation";
        String BUDGET_REV_CODE_ID = "prRevBudgetCode";

    }

    interface ACCOUNT_RECEIPT_ENTRY_MASTER {
        String FIELD_MASTER_ITEMS = "listOfTbAcFieldMasterItems";
    }

    interface FIELD_MASTER {
        String MAIN_ENTITY_NAME = "accountFieldMasterBean";
        String MAIN_LIST_NAME = "list";
        String MODE_DATA = "mode";
        String MODE = "MODE_DATA";
        String LEVELS = "levels";
        String LEVELS_MAP = "levelsMap";
        String LEVELS_PARENT_MAP = "levelsParentMap";
        String CODING_DIGIT_MAP = "codingDigitMap";
        String NODE = "node";
        String FIELD_STATUS = "fieldStatus";
        String PARENT_FLAG_STATUS = "defaultOrgFlagParentStatus";
        String DEFAULT_FLAG_STATUS = "defaultOrgFlagStatus";
        String REMOVE_ROW = "removeRow";
        String ADD_ROW = "addRow";
        String FIELD_CODE = "fieldCode";
        String FIELD_DESC = "fieldDesc";
        String PARENT_FINAL_CODE = "parentFinalCode";
        String PARENT_DESC = "parentDesc";

    }

    interface FUND_MASTER {
        String MODE_VIEW = "modeView";
        String FUND_GROUP_CODE = "Function Group Code";
        String MAIN_LIST_NAME = "list";
        String PARENT_CODE = "parentCode";
        String GET_LVLS = "getLevels";
        String SET_OF_NODE = "setOfNode";
        String ACCOUNT_FUND_MASTER_BEAN = "accountFundMasterBean";
        String LEVELS = "levels";
        String LEVELS_MAP = "levelMap";
        String LEVELS_PARENT_MAP = "levelsParentMap";
        String CODING_DIGIT_MAP = "codingDigitMap";
        String FUND_STATUS = "fundStatus";
        String OPEN_BRACKET = "(";
        String CLOSE_BRACKET = ")";
        String FUND_CODE = "fundCode";
        String FUND_DESC = "fundDesc";
        String INVESTMENT_MATURITY_ALERT = "MRI";
        String INVESTMENT_URL = "investmentMaster.html";
    }

    interface LOAN_MASTER {
        String LOAN_REPAYMENT_ALERT = "LPA";
        String LOAN_URL = "loanmaster.html";

    }

    interface FUNCTION_MASTER {
        String LVL_SIZE = "levelSize";
        String GET_LVL = "getLevels";
        String PARENT_LVL = "parentLevel";
        String FUNC_MASTER = "tbAcFunctionMaster";
        String FUN_MASTER_BEAN = "accountFunctionMasterBean";
        String JSP_FORM = "acFunctionMaster/form";
        String JSP_LIST = "acFunctionMaster/list";
        String CONFIG_MISSING = "Configuration Missing";
        String SETOF_NODE_FUNCTION = "setOfNodeForFunction";
        String FUNCTION_STATUS = "functionStatus";
        String VIEW_MODE_LEVEL = "viewModeLevel";

    }

    public enum FormMode {
        CREATE, UPDATE, VIEW, EDIT, PREFIX, BULKEDITSEARCH, BULKEDITSAVE;
    }

    public enum AccountConstants {

        CONTRA_TRANSFER("T"), CONTRA_WITHDRAW("W"), CONTRA_DEPOSIT("D"), NOT_ISSUED("NSD"), ISSUED("ISD"), STOP_PAYMENT(
                "STP"), READY_FOR_ISSUE("RFI"), CANCELLED("CND"), CLEARED("CLD"), DISHONORED("DSH"), BANK("B"), CHEQUE("Q"), CASH(
                        "C"), AC("AC"), AS("AS"), BC("BC"), FNC("FNC"), TABLEPOST("TB_AC_VOUCHER_POST_MASTER"), TRANTOKENNO(
                                "TRAN_TOKENNO"), PAGE("page"), DOUBLE_COLON("::"), TRUE("true"), FALSE("false"), POSTRESET_TYPE(
                                        "F"), VOUCHERTABLE("TB_AC_VOUCHER"), VOUCHERCOLUNVOU_NO("VOU_NO"),

        RV("RV"), // Receipt Voucher
        JV("JV"), // Journal Voucher
        CV("CV"), // Contra Voucher
        BI("BI"), // Bills/Invoice
        DR("DR"), // Debit
        CR("CR"), // Credit
        BTE("BTE"), // Bank Transfer
        CWE("CWE"), // Cash Withdrawal
        DS("DS"), // Deposit Slip
        PN("PN"), // Template type -Permanent
        BE("BE"), // Bill Entry
        PCA("PCA"), // Petty Cash
        PV("PV"), // Payment Voucher
        PVE("PVE"), // Payment Voucher Entry
        AD("AD"), // Advance
        P("P"), // Bill payment
        D("D"), // Direct Payment
        U("U"), T("T"), // Transfer
        W("W"), // Withdraw
        RTGS("RT"), FDR("F"), NEFT("N"), DRAFT("D"), PCD("PCD"), // Petty Cash Deposit
        RRE("RRE"), // receipt reversals
        DSR("DSR"), // receipt reversals
        RBI("RBI"), // receipt reversals
        RBP("RBP"), // receipt reversals
        EXS("EXS"), // External System
        AHP("AHP"), // Account Head Primary
        AHS("AHS"), // Account Head Secondary
        A("A"), // Adjustment Payment
        SAD("SAD"), // Reverse And Surpress Transfer
        CIR("CIR"),// cheque issuance required
        RMA("RMA"), // receipt multi mode allowed
        SPR("SPR"), // stop payment required
        BRO("BRO"),// Bill Authorization Read only
        BMC("BMC"),//budgte method control for cash or Accrual budting system
        ASD("ASD"),//Assigned Self Deparment
        EEC("EEC"), // External E-Chavani System
        AUTHORIZED("Authorized"), UNAUTHORIZED("Unauthorized"), REJECTED("Rejected"), AUTHORIZATION_MODE("Auth"),

        Y("Y"), N("N"), S("S"), M("M"), I("I"), C("C"), DP("DP"), // Deposit

        WEB_REQ_DTO_ERR("WSRequestDTO cannot be null."), DATA_MODEL_ERR("dataModel cannot be null."), VALIDATED(
                "validated"), Entry("Entry"), Auth("Auth"),

        /**
         * Numeric Constants
         */
        // ZERO(0),

        /**
         * Account Tables and column names
         */
        TB_AC_VOUCHER("TB_AC_VOUCHER"), VOU_NO("VOU_NO"),

        ;

        private String value;
        private int integerValue;
        private boolean booleanValue;

        private AccountConstants() {
        }

        private AccountConstants(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        private AccountConstants(int integerValue) {
            this.integerValue = integerValue;
        }

        public int getIntValue() {
            return integerValue;
        }

        private AccountConstants(boolean booleanValue) {
            this.booleanValue = booleanValue;
        }

        public boolean getBooleanValue() {
            return booleanValue;
        }

    }

    public enum ReportType {

        BUDGET_ESTIMATE("BER"), // Budget Estimate
        GENERAL_CASH_BOOK("GCB"), // General Cash Book
        GENERAL_BANK_BOOK("GBB"), // General Bank Book
        JOURNAL_REGISTER("JRB"), // Journal Register
        GENERAL_LEDGER("GLR"), // General Ledger
        TRIAL_BALANCE("TBR"), // Trial Balance
        BANK_RECONCILIATION_STATEMENT("BRS"), // Bank Reconciliation Statement
        REGISTER_OF_DEPOSITS("RDP"), // Register of Reposits
        CLASSIFIED_ABSTRACT("CAR"), // Classified Abstract
        BALANCE_SHEET("BSR"), // Balance Sheet
        INCOME_AND_EXPENDITURE_STATEMENT("INE"), // Income and Expenditure Statement
        RECEIPTS_AND_PAYMENT_ACCOUNT("RPR"), // Receipts and Payment Account
        DAY_BOOK("DYB"), CASH_CASH_EQUIVALENTS("CAE"), APPROVED_RE_APPROPRIATION("RAS"), REGISTER_OF_SECURITY_DEPOSIT(
                "SDR"), OPENING_BALANCES_REPORT("OBS"), PAYMENT_CHEQUE_REGISTER("PCR"), DISHONOR_CHEQUE_REGISTER(
                        "DCR"), COLLECTION_SUMMARY_REPORT("CSR"), COLLECTION_DETAIL_REPORT("CDR"), BANK_ACCOUNTS_SUMMARY_REPORT(
                                "BAS"), CHEQUE_BOOK_CONTROL_REGISTER("CCR"), TRANSACTION_REVERSAL_REPORT(
                                        "TRR"), EXPENDITURE_BUDGET_STATUS_REPORT("EBS"), RECEIPTS_BUDGET_STATUS_REPORT(
                                                "RBS"), CHEQUE_RECEIVED_REPORT("CRR"), CHEQUE_CANCELLATIN_REPORT(
                                                        "CCN"), TDS_CERTIFICATE(
                                                                "TFC"), AUDIT_TRAIL_REPORT("ATR"), CASH_FLOW_REPORT("CFS");

        private String stringVal;

        private ReportType() {
        }

        private ReportType(String param) {
            this.stringVal = param;
        }

        public String stringVal() {

            return this.stringVal;
        }

        public static String reportName(String reportType) {
            ReportType[] reportArr = ReportType.values();
            String val = "";
            for (ReportType report : reportArr) {
                if (report.stringVal.equals(reportType)) {
                    return report.toString();
                }
            }
            return val;
        }

    }

    interface CONTRA_VOUCHER_ENTRY {
        String DEN_LOOKUP_LIST = "denLookupList";

    }

    enum WaterConnectionSearch {

        propertyNo("propertyNo", "", "propertyNo"), rowId("csCcn", "", "csCcn"), csCcn("csCcn", "", "csCcn"), csName("csName", "",
                "csName"), csContactno("csContactno", "", "csContactno"), csAdd("csAdd", "", "csAdd");

        private String colName;
        private String colType;
        private String propertyName;

        private WaterConnectionSearch(String colName, String colType, String propertyName) {
            this.colName = colName;
            this.colType = colType;
            this.propertyName = propertyName;
        }

        public String getColName() {
            return this.colName;
        }

        public String getPropertyName() {
            return this.propertyName;
        }
    }

    enum ConnectionSearch {
        applicationNumber("woApplicationId", "long", "woApplicationId"), rowId("woApplicationId", "long", "woApplicationId");

        private String colName;
        private String colType;
        private String propertyName;

        private ConnectionSearch(String colName, String colType, String propertyName) {
            this.colName = colName;
            this.colType = colType;
            this.propertyName = propertyName;
        }

        public String getColName() {
            return this.colName;
        }

        public String getPropertyName() {
            return this.propertyName;
        }

    }

    enum PropertySearch {

        proertyNo("PM_PROP_NO", "", "PM_PROP_NO"), rowId("PM_PROP_NO", "", "PM_PROP_NO"), oldPid("PM_PROP_OLDPROPNO", "",
                "PM_PROP_OLDPROPNO"), ownerName("MN_ASSO_OWNER_NAME", "", "MN_ASSO_OWNER_NAME"), mobileno("MN_ASSO_MOBILENO", "",
                        "MN_ASSO_MOBILENO"), propertyno("propertyno", "", "propertyno"), currentOwner("personName", "",
                                "personName"), village("villageCode", "",
                                        "villageCode"), khasraPloatNo("khasraOrPlotNo", "", "khasraOrPlotNo");

        private String colName;
        private String colType;
        private String propertyName;

        private PropertySearch(String colName, String colType, String propertyName) {
            this.colName = colName;
            this.colType = colType;
            this.propertyName = propertyName;
        }

        public String getColName() {
            return this.colName;
        }

        public String getPropertyName() {
            return this.propertyName;
        }
    }

    enum MuatationSearch {

        rowId("mutId", "", "mutId"), propertyno("propertyno", "", "propertyno"), currentOwner("personName", "",
                "personName"), village("villageCode", "", "villageCode"), khasraPloatNo("khasraOrPlotNo", "", "khasraOrPlotNo");

        private String colName;
        private String colType;
        private String propertyName;

        private MuatationSearch(String colName, String colType, String propertyName) {
            this.colName = colName;
            this.colType = colType;
            this.propertyName = propertyName;
        }

        public String getColName() {
            return this.colName;
        }

        public String getPropertyName() {
            return this.propertyName;
        }
    }

    enum SupplementaryBillPrintFiles {
        SUPPLEMENTARY_BILL_PRINT_CCN("Aqua_Ccn_Supplimentry_r.jrxml"), SUPPLEMENTARY_BILL_PRINT_IDN("Aqua_Supplimentry_r.jrxml");

        private String colDescription;

        private SupplementaryBillPrintFiles(String colDescription) {

            this.colDescription = colDescription;
        }

        public String getColDescription() {
            return colDescription;
        }

        public void setColDescription(String colDescription) {
            this.colDescription = colDescription;
        }

    }

    interface RnLCommon {

        String MODE_CREATE = "C";
        String MODE_EDIT = "E";
        String MODE_VIEW = "V";
        String Flag_A = "A";
        String Flag_D = "D";
        String RentLease = "RL";
        String RL_SHORT_CODE = "RNL";

        String Flag_C = "C";
        Character Y = 'Y';
        Character N = 'N';
        String Tripe_Zero = "000";
        String Double_Zero = "00";
        String Single_Zero = "0";
        String Y_FLAG = "Y";
        String N_FLAG = "N";
        String L_FLAG = "L";
        String S_FLAG = "S";
        String F_FLAG = "F";
        String CHECKLIST_RNLRATEMASTER = "ChecklistModel|RNLRateMaster";
        String RNLRATEMASTER = "RNLRateMaster";
        String CONTRACT_RENEWAL_URL = "ContractAgreementRenewal.html";
        String BOOKING_CANCEL_URL = "EstateBookingCancel.html";
        String CITIZEN_RATING_URL = "EstateCitizenRating.html";
        String CITIZEN_RATING_FORM = "EstateCitizenRating";
        String DEMAND_NOTICE = "DemandNotice";
        String CONTRACTOR_CPD_VALUE = "C";
        String DEMAND_NOTICE_SUMMARY = "demandNoticeSummary";
        String DEMAND_NOTICE_VALIDN = "demandNoticeValidn";
        String DEMAND_NOTICE_PRINT = "demandNoticePrint";
        String LEASE_FLAG="leaseFlag";

    }

    interface EstateMaster {
        Character Single = 'S';
        Character Group = 'G';
        String TB_RL_ESTATE_MAS = "TB_RL_ESTATE_MAS";
        String ES_CODE = "ES_CODE";
        String[] ExludeCopyArray = { "updatedBy", "updatedDate", "esId" };
        String EST = "EST";

    }

    interface RL_Identifier {
        String NameEstateMaster = "EST_MASTER";
        String NameTenantMaster = "TEN_MASTER";
        String NamePropMaster = "PROP_MASTER";
    }

    interface TenantMaster {

        String[] ExludeCopyArrayTenant = { "updatedBy", "updatedDate", "tntId" };
        String[] ExludeCopyArrayTenantOwner = { "updatedBy", "updatedDate", "propDetId" };
        String TenantShortType = "TEN";
        Character FLAG_S = 'S';
        String TITLE = "title";
    }

    interface PropMaster {

        String TB_RL_PROPERTY_MAS = "TB_RL_PROPERTY_MAS";
        String[] ExludeCopyArrayProp = { "updatedBy", "updatedDate", "propId" };
        String PropShortType = "AS";
        Character FLAG_O = 'O';
        String Lease = "Lease";

    }

    interface EstateBooking {

        int LEVEL = 2;
        String PASS = "pass";
        String FAIL = "fail";
        String ESTATE_BOOKING_HOME = "estateBookingHome";
        String ESTATE_BOOKING = "EstateBooking";
        String PROP_INFO = "propInfo";
        String PROP_LIST = "propList";
        String CATEGORY = "categorySubType";
        String PAYMENT_CHECK_LIST_RENT = "paymantAndCheckListRent";
        String ESTATE_BOOKIN_VALID = "EstateBooking";
        String PAYMENT_MODE = "rnl.payment.mode";
        String PAY_MODE_CHNG = "rnl.payment.change";
        String PAY_STATUS = "rnl.payment.status";
        String APP_NO = "rnl.appln.no";
        String BOOK_NO = "and Booking No.";
        String RECEIPT_STATUS = "rnl.receipt.status";
        String RNL_CHARGES_DETAIL = "ChargesDetailRnL";
        String ESTATE_BOOKING_CANCELLATION_SERVICECODE = "EBC";

        String DUPLICATE_RECIEPT_PRINT_SUMMARY = "duplicateReceiptPrintSummary";
        String GET_BOOKING_DETAILS = "Fetch estate Booking details";

    }
    interface WaterTankerBooking {

        int LEVEL = 2;
        String PASS = "pass";
        String FAIL = "fail";
        String WATER_TANKER_BOOKING_HOME = "WaterTankerBookingHome";
        String WATER_TANKER_BOOKING = "WaterTankerBooking";
        String WATER_TANKER_BOOKIN_VALID = "WaterTankerBooking";
        String WATER_TANKER_APPROVAL = "WaterTankerApproval";

    }

    interface RnlBillPayment {
        String NO_RECORD = "rnl.renewal.billPayment.NoRecord";
        String INVALID_CONTRACT = "rnl.renewal.billPayment.ContractInvalid";
        String INVALID_MOBILE = "rnl.renewal.billpayment.mobileInvalid";
    }

    interface RnlPrefix_Field {

        interface EstateMaster {

            String Type = "estateMaster.type";
        }
    }

    interface NewWaterServiceConstants {
        String APPLICATION_TYPE = "T";
        String CAA = "CAA";
        String WNC = "WNC";
        String NO = "N";
        String YES = "Y";
        String WMN = "WMN";
        String MOK = "MOK";
        String NOG = "NOG";
        String BILL_SCHEDULE_DATE = "B";
        String DUE_DATE_Meter = "M";
        String DUE_DATE_Non_Meter = "N";
        String CUT_OFF = "C";
        String RESTORATION = "R";
        String METER = "MTR";
        String NON_METER = "NMR";
        String APL = "APL";
        String CHECKLIST_WATERRATEMASTER_MODEL = "ChecklistModel|WaterRateMaster";
        String RFC = "RFC";
        String YEARLY_BILL_FREQUENCY = "12";
        String QUARTERLY_BILL_FREQUENCY = "3";
        String HALFYEARLY_BILL_FREQUENCY = "6";
        String BIMONTHLY_BILL_FREQUENCY = "2";
        String BILL_SCHEDULE_START_DATE = "BSS";
        String BILL_SCHEDULE_END_DATE = "BSE";
        String CCG = "CCG";
        String CWF = "CWF";
        String CUS_METER_BILL ="CUS";
        String ANY_PLUMBER = "Any Available";

        interface PROPERTY_INTEGRATION {
            String REQUEST_PROPNO = "assNo";
            String REQUEST_ORGID = "orgId";
            String RESPONSE_AMOUNT = "totalPayableAmt";
            String RESPONSE_PROPNO = "propNo";
        }
    }

    interface WATERMASTERS {

        interface INSTITUTEWISECONSUMPTIONMASTER {
            String LIST_OF_INST_TYPE = "listOfInstType";
        }

        interface DIAMETERWISECONNSIZE {
            String URL = "TBCcnsizePrm.html";
            String TB_CCNSIZE_PRM_ERROR_UPDATE = "tbCcnsizePrm.error.update";
            String TB_CCNSIZE_PRM_ERROR_CREATE = "tbCcnsizePrm.error.create";
        }

    }

    interface PlumberLicense {

        String TODATE_CALENDER_YEAR_WISE = "C";
        String TODATE_FINANCIAL_YEAR_WISE = "F";
        String TODATE_ON_LICENSE_DATE_WISE = "L";
        String TODATE_ON_LICENSE_ASDATE_WISE = "A";
        String YES = "Y";
        String NO = "N";
        String ISDELETED_N = "N";
        String ISDELETED_Y = "Y";
        String MONTHLY = "M";
        String YEARLY = "Y";

        interface PLUM_MASTER_TABLE {
            String MODULE = "WT";
            String TABLE = "TB_WT_PLUM_MAS";
            String COLUMN = "PLUM_LIC_NO";
        }

    }

    interface ServiceUrl {
        String WATER_DISCONNECTION = "WaterDisconnectionForm.html";
        String CHANGE_OF_USAGE = "ChangeOfUsage.html";
    }

    interface DependsOnFactors {
        String RDL = "RDL";
        String RDT = "RDT";
    }

    interface TAX_TYPE {
        String FLAT = "F";
        String SLAB = "S";
        String PERCENTAGE = "P";
        String TELESCOPIC = "T";
        String LONG_TERM_DEPOSITS = "Long Term Deposits";
		String METER_CHARGE = "Meter Charge";
    }

    interface DemandNotice {
        String MODULE = WATER_DEPARTMENT_CODE;
        String TABLE = "TB_WT_DEMAND_NOTICE";
        String COLUMN = "NB_NOTICENO";
        String RESET = "F";

        String DEMAND_TYPE = PrefixConstants.WATERMODULEPREFIX.WDN;
        String DEMAND_NOTICE = "DN";
        String FINAL_DEMAND_NOTICE = "FDN";
    }

    interface NoDuesCertificate {
        String WATERDUES = "waterDues";
        String PROPERTYDUES = "propertyDues";
        String NO_DUE_CERTIFICATE = "WND";
    }

    interface MobileCommon {
        String ORGID = "orgId";
        String IOS ="ios";

    }

    interface AdjustmentType {
        String POSITIVE = "Positive";
        String NEGATIVE = "Negative";
    }

    interface BILL_MASTER_COMMON {
        String DMD_VALUE = "DMD";
        String ACCOUNT_TEMPLATE_FOR_PREFIX = "TDP";
        String ENTRY_TYPE = "INS";
        String MAN_ENTRY_TYPE = "MAN";
        String PREFIX_CODE_RV = "RV";
        String DEMAND_COLLECTION = "RDC";
        String REBATE_COLLECTION = "RBT";
        String GOVT_LIABILITY = "GLT";
        String DMP = "DMP";// --permanent for control arrears account
        String REBATE_REVERSAL = "RRE";
        String NEGATIVE_ADJUST = "NAJ";
        String POSITIVE_ADJUST = "PAJ";
        String TAX_BILL_RECEIPT_REVERSAL = "RCR";

    }

    interface ContractAgreement {

        String CONTRACT_AGREEMENT_SUMMARY = "contractAgreementSummary";
        String CONTRACT_AGREEMENT = "ContractAgreement";
        String CONTRACT_AGREEMENT_UPLOAD = "ContractAgreementUpload";
        String VENDER_lIST = "getVenderList";
        String CONTRACT_NO = "contracNo";
        String CONTRACT_DT = "contracDate";
        String VENDER_ID = "venderId";
        String VIEW_CLOSED_ON = "viewClosedCon";
        String CONTRACT_WITH = "rnl.contract.with";
        String CONTRACT_STATUS = "rnl.contract.status";
        String VSS = "VSS";
        String STATUS_MSG = "rnl.status";
        String CONTRACT_CREATE = "contract.saved.success";
        String CONTRACT_UPDATE = "contract.update.success";
        String CONTRACT_MSG = "contract.status";
    }

    String TO_DATE = "toDate";
    String FROM_DATE = "fromDate";
    String FRM_DATE = "frmDate";
    String TODATE2 = "todate";
    String DATE = "date";
    String TB_AC_BANK_DEPOSITSLIP_MASTER = "TB_AC_BANK_DEPOSITSLIP_MASTER";
    String DPS_SLIPNO = "DPS_SLIPNO";
    String TB_RECEIPT_MAS = "TB_RECEIPT_MAS";
    String RM_RCPTNO = "RM_RCPTNO";
    String TWO_PERCENT = "2%";
    String ONE_PERCENT = "1%";
    String FOUR_PERCENT = "4%";
    String CR_ID = "crId";
    String DR_ID = "drId";
    String FIN_YEAR_ID = "finYearId";
    String THREE_PERCENT = "3%";
    String COMP_CODE = "compCode";
    String TB_AC_DEPOSITS = "TB_AC_DEPOSITS";
    String VOUCHER_POST_DETAIL_DTO = ",VoucherPostDetailDTO ";
    String SEQUENCE_NO_ERROR = "unable to generate sequenceNo for orgId=";
    String SEQUENCE_NO = "0000000000";
    String ORG_ID = ", orgId=";
    String INTERNAL_ERROR = "Internal Error";
    String ACTIVE_CLASS = "activeClass";
    String SERVICES_EVENT_DTO = "servicesEventDTO";
    String EVENT_MASTER = "eventMaster";
    String TASK_ID = "taskId";
    String REFE_ID = "referenceId";
    String SERVICE_LIST = "serviceList";
    String EVENTS_SELECTED_LST = "eventsSelectedLst";
    String EVENTS_NOT_SELECTED = "eventsNotSelected";

    String WE_CONDIATINAL_FALSE_NEXT_STEP = "weCondiatinalFalseNextStep";
    String WE_SERVICE_EVENT = "weServiceEvent";
    String WD_NAME = "wdName";
    String ON_ORG_SELECT = "onOrgSelect";
    String NOT_APPLICABLE = "Not Applicable";
    String SMS_AND_E_MAIL = "SMS and E-Mail";
    String E_MAIL = "E-Mail ";
    String SMS = "SMS";
    String SMS_EMAIL = "SMSAndEmail";
    String SMS_EMAIL_ADD = "smsAndEmailAdd";
    String SMS_EMAIL_EDIT = "smsAndEmailEdit";
    String MESSAGE_TYPE = "messageType";
    String AES = "AES";
    String SHA_256 = "SHA-256";
    String ROW_ID = "rowId";
    String ASC = "asc";
    String CN = "cn";
    String NE = "ne";
    String EQ = "eq";
    String DEFAULT_DOMAIN_URL = "default.domain.url";
    String WEB_PROPERTIES = "webProperties";
    String LOGGED_IN_SESSION_ID_MAP = "LoggedInSessionIdMap";
    String LOGGED_IN_USER_MAP = "LoggedInUserMap";
    String PRE_LOGIN_LANGUAGE_ID = "PRE_LOGIN_LANGUAGE_ID";
    String DOMAIN_URL = ".domain.url";
    String DEFAULT_DATA_SOURCE = "defaultDataSource";
    String HRMS_DATA_SOURCE = "hrmsDataSource";
    String STORE_DATA_SOURCE = "storeDataSource";
    String MESSAGES = "messages";
    String ERROR_WITH_CAUSE = "error.with.cause";
    String APPLICATION_JSON_UTF_8 = "application/json; UTF-8";
    String ISO_8859_1 = "ISO-8859-1";
    String CHARACTER_ENCODING = "characterEncoding";
    String USER_AGENT = "User-Agent";
    String CONTENT_TYPE = "content-Type";
    String UTF_8 = "UTF-8";
    String FA_TODATE = "fa_todate";
    String FA_FROMDATE = "fa_fromdate";
    String FA_YEARID = "FA_YEARID";
    String MILLIS = "millis";
    String SECOND = "second";
    String MINUTE = "minute";
    String HOUR = "hour";
    String DAY = "day";
    String DAYS = "DAYS";
    String MONTH = "month";
    String YEAR = "year";
    String YEARS = "YRS";
    String MEASUREMENT_UNIT = "UOM";
    String REG = "reg";
    String PARENT_FROM_TO_RANGE = "ParentFromToRange";
    String CURRENT_DATE_RANGE = "CurrentDateRange";
    String INVALID_ROW_DATE = "InvalidRowDate";
    String FROM_TO_RANGE = "FromToRange";
    String DATE_DIFF = "DateDiff";
    String NOT_NULL = "NotNull";
    String SEARCH_FIELD = "searchField";
    String SEARCH_OPER = "searchOper";
    String SEARCH_STRING = "searchString";
    String SORD = "sord";
    String SIDX = "sidx";
    String INSPECTION_LETTER_DTO = "inspectionLetterDto";
    String LABEL_VAL = "labelVal";
    String LABEL_ID = "labelId";
    String APPL_ID = "applId";
    String REJ_LETTER_NO = "REJ_LETTER_NO";
    String TB_REJECTION_MST = "TB_REJECTION_MST";
    String SECOND_ATTEMPT = "Secondattempt";
    String FIRST_ATTEMPT = "firstattempt";
    String REJ_APPLICATIONID = "rejApplicationId";
    String LABLE_ID = "lableID";
    String REJCTION = "Rejction";
    String REJCTION_NO = "RejctionNO";
    String DOCUMENT_LIST = "documentList";
    String REJ_APPLICATION_ID = "rejapplicationid";
    String REJ_DATE = "rejdate";
    String SERVICE_NAME = "ServiceName";
    String SERVICE_ID = "ServiceId";
    String NAME = "Name";
    String REMARK_LIST = "RemarkList";
    String APM_APPLICATION_DATE = "ApmApplicationDate";
    String LANGUAGE_ID = "languageId";
    String IS_DEFAULT_ORG = "isDefaultOrg";
    String SERVICE_MAS_LIST = "serviceMasList";
    String TB_APPREJ_MAS = "tbApprejMas";
    String ERR_CODE = "errCode";
    String ERR_MSG = "errMsg";
    String CAUSE = "cause";
    String MESSAGE = "message";
    String INVALID_REQUEST = "InvalidRequest";
    String COMPLAINT_MASTER_LIST = "complaintMaster/list";
    String SUCCESSFULLY = " successfully";
    String REQUEST_NUMBER = "Request No ";
    String ERROR_MESSAGE = "errorMessage";
    String ERROR_MESSAGE_REG = "errorMessageReg";
    String SUCCESS_MESSAGE = "successMessage";
    String DELETE_ERROR = "deleteError";
    String CHARGES_DETAIL = "ChargesDetail";
    String CHALLAN_AT_ULB_RECEIPT_PRINT = "ChallanAtULBReceiptPrint";
    String COMMON_CHALLAN_BANK_REPORT = "CommonChallanBankReport";
    String COMMON_CHALLAN_ULB_REPORT = "CommonChallanULBReport";
    String REDIRECT = "redirect:/";
    String REDIRECT_TO_PAY = "redirectToPay";
    String APPLICATION_REGISTER = "ApplicationRegister";
    String FILE_EXISTS = "FILEEXISTS";
    String MAX_FILE_COUNT = "MAXFILECOUNT";
    String PLUMBER_ID = "PlumberID";
    String CONNCETION_NO = "conncetionNo";
    String REDIRECT_WORK_ORDER_HTML = "redirect:/WorkOrder.html?generatWorkOrder";
    String POLICY = "POLICY";
    String VIEW_HELP = "viewHelp";
    String VIEW_DOWNLOAD_FILE_HELP = "viewDownloadFileHelp";
    String TRY = "TRY";
    String CONTROLLER = "Controller";
    String RESUBMISSION = "Resubmission";
    String SENDER_PWD = "SENDERPWD";
    String SENDER_ID = "SENDERID";
    public String REGISTRATION_ACKNOWLEDGEMENT_RECEIPT = "RegistrationAcknowledgementReceipt";
    public String COMPLAINT_ACKNOWLEDGEMENT_MODEL = "complaintAcknowledgementModel";
    public String GRIEVANCE_DEPARTMENT_REGISTRATION = "GrievanceDepartmentRegistration";
    public String AREA_MAPPING_WARD_ZONE = "areaMappingWardZoneForCare";
    public String START = "Start";
    public String Hidden_Task_Requester = "Hidden_Task Requester";
    public String YEAR_ID_NOT_NULL = "Year id should not be null";
    public String ORGANISATION_ID_NOT_NULL = "Organisation id should not be null";
    public String POPULATION_ID_NOT_NULL = "Population id should not be null";

    public String VEHICLE_ESTIMATED_DOWNTIME_NULL = "Estimated downtime should not be null";
    public String VEHICLE_TYPE_NOT_NULL = "Vehicle Type should not be null";
    public String VEHICLE_MAINTENANCE_AFTER_NOT_NULL = "Vehicle Maintenance After should not be null";
    public String VEHICLE_RegNo_NOT_NULL = "Vehicle Reg No should not be null";
    public String VEHICLE_FROMDATE_NOT_NULL = "Vehicle from date should not be null";
    public String VEHICLE_TODATE_NOT_NULL = "Vehicle to date should not be null";
    public String ACCOUNTBUDGETPROJECTEDEXPENDITUREUPLOADDTO = "AccountBudgetProjectedExpenditure";
    public String ACCOUNTBUDGETCODEUPLOADDTO = "AccountBudgetCode";
    public String ACCOUNTSECONDARYHEADMASTEREXPORTDTO = "SecondaryHeadMaster";
    public String ACCOUNTBUDGETPROJECTEDREVENUEENTRYUPLOADDTO = "BudgetRevenueEntry";
    public String ACCOUNTBUDGETOPENBALANCEMASTERUPLOADDTO = "OpenBalanceMaster";
    public String VENDORMASTERUPLOADDTO = "VendorMaster";
    public String DEPOSITENTRYUPLOADDTO = "DepositEntry";
    public String BANKMASTERUPLOADDTO = "BankMaster";
    public String BANKACCOUNTMASTERUPLOADDTO = "BankAccount";
    public String VOUCHERENTRYUPLOADDTO = "VoucherEntry";
    public String LOCATIONMASTERUPLOADDTO = "LocationMaster";
    String TASK_NAME = "TASK_NAME";
    String ENV_SKDCL = "SKDCL";
    String [] ENV_PRODUCT = {"SKDCL","PSCL"};
    String ENV_ASCL = "ASCL";
    String ENV_DSCL = "DSCL";
    String ENV_PSCL = "PSCL";
    String CANCEL_BY_FORCE = "CANCELLED_BY_FORCE";
    String TRI_COD1 = "TRI_COD1";
    String TRI_COD2 = "TRI_COD2";
    String ENV_SUDA = "SUDA";
    String ENV_SUDA_UAT = "SUDA_UAT";
    String ENV_TSCL = "TSCL";
    String ENV_TSCL_PROD = "ENV_TSCL_PROD";
	String NO_RECORD_FOUND = "No record found";
	String BILL_ARV="BGA";
	String ENV_SFAC = "SFAC";
	String ZERO_ZERO = "00";
	String RTI_SLI_DAYS_RANGE = "RSDR";
	String StatusMessage = "statusMessage";
	String PaymentMethod="paymentMethod";
	String PaymentTransaction="paymentTransaction";
	String ENV_TCP = "TCP";
	String EGRASSVIEW = "egrassViewForm";
	String Egrass_Redirect = "redirectEgrass";
	String PayUView = "payUView";
	
    interface SANITATION_MASTER_VALIDATION {
        public String SANITATION_NAME_NOT_NULL = "Sanitation Name should not be null";
        public String SANITATION_TYPE_NOT_NULL = "Sanitation Type should not be null";
        public String SANITATION_WARD_NOT_NULL = "Sanitation ward should not be null";
        public String SANITATION_ZONE_NOT_NULL = "Sanitation zone should not be null";
        public String SANITATION_COUNT_NOT_NULL = "Sanitation count should not be null";
        public String SANITATION_LOCATION_NOT_NULL = "Sanitation Colony should not be null";
    }

    interface PUMP_MASTER_VALIDATION {
        public String PUMP_NAME_NOT_NULL = "Pump Name should not be null";
        public String PUMP_TYPE_NOT_NULL = "Pump Type should not be null";
        public String PUMP_ADDRESS_NOT_NULL = "Pump Address should not be null";
        public String PUMP_VENDOR_NAME_NOT_NULL = "Pump Vendor should not be null";

    }

    interface PAYMENT_MODES {
        String PCB_MODE = "PCB";
        String PCU_MODE = "PCU";
        String PPO_MODE = "PPO";
        String PDD_MODE = "PDD";
    }

    interface EstateContract {
	    String ESTATE_CONTRACT_MAP_HOME = "EstateContractMappingHome";
	    String ESTATE_CONT_MAP = "EstateContMapProp";
	    String ESTATE_CONT_PRINT = "EstateContractPrint";
	    String CBP = "CBP";
	    String RNL_BILL_PAY = "rnl/billPayment";
	    String CONTRACT_NO = "rnl.estate.contract.bill";
	    String CONTRACT_lIST = "contractList";
	    String ESTATE_CONTRACT_MAPIN = "EstateContractMapping";
	    String CONTRACT_MAPVIEW = "EstateContractMappingView";
	    String WATER_BILL_AMNT = "water.billPayment.amount";
	    String WATER_BILL_CCNO = "water.billPayment.ccnNumber";
	    String PRINT_CHALLAN_STATUS = "rnl.challan.bill.status";
	    String PAYMENT_RECEIPT_STATUS = "rnl.payment.status";
	    String ESTATE_CONTRACT_MAP = "rnl.estate.contract.map";
	    String ESTATE_CONTRACT = "rnl.estate.contract.status";
	    String CTA = "CTA";
	    String ESTATE_CONTRACT_MAPPING_APPROVAL = "EstateContractMappingApproval";
	    String ESTATE_CONTRACT_APPROVAL_FORM = "ECA";
	}

    interface CFC_CHECKLIST {
        String CLG_PREFIX = "CLG";
        String SET_PREFIX = "SET";
    }

    interface EstateMasters {
        String ESTATE_MASTER_FORM = "estateMaster/form";
        String LOCATION_LIST = "locationList";
        String ESTATE_MASTER_LIST = "estateMaster/list";
        String ESTATE_CAT_SINGLE = "estate.category.single";
        String ESTATE_CAT_GROUP = "estate.category.group";
        String LOCATION_ID = "locationId";
        String ESTATE_ID = "estateId";
        String ESTATE_LABEL = "estate.label.code";
        String ESTATE_STATUS = "estate.code.edit.success.msg";
        String PURPOSE = "purpose";
        String ESTATE_TYPE = "estateType";
        String SUB_TYPE = "subType";
        String ACQTYPE = "acqType";
    }

    interface EstateProMaster {
        String ESTATE_PROP_MAS = "estatePropMaster/list";
        String ESTATE_PROP_MAS_FORM = "estatePropMaster/form";
    }

    interface PropFreeze {
        String PROP_FREEZE_LIST = "propFreeze/list";
        String PROP_FREEZE_FORM = "propFreeze/form";
        String PROP_FREEZE_STATUS = "rnl.estate.contract.status";
    }

    interface TenantMasters {
        String TENANT_MAST_LIST = "tenantMaster/list";
        String TENANT_MAST_FORM = "tenantMaster/form";
    }

    interface EstateBookingRest {
        String FROM_DATE = "From Date Can not be empty";
        String TO_DATE = "To Date Can not be empty, ";
        String APP_DTO_EMPTY = "ApplicantDetailDTO can not be empty, ";
        String TITLE_EMPTY = "Title can not be empty, ";
        String FIRST_NAME = "First Name can not be empty, ";
        String LAST_NAME = "Last NAme can not be empty, ";
        String GEN_EMPTY = "Gender can not be empty, ";
        String MOBILE_NO = "Mobile No. can not be empty, ";
        String AREA_NO = "Area Name can not be empty, ";
        String VILLAGE_STUB = "Village Town Stub can not be empty, ";
        String PIN_NO = "Pin Code can not be empty, ";
        String ESTATE = "EstateBookingDTO can not be empty, ";
        String SHIFT_EMPTY = "Shift Id can not be empty, ";
        String PURPOSE_EMPTY = "Purpose can not be empty, ";
        String lANG_EMPTY = "Lang can not be empty, ";
        String CREATED_BY = "Created By can not be empty, ";
        String CREATED_DATE = "Created date can not be empty, ";
        String UPLOAD_DOCS = "Please Upload Madatory Documents";
        String PAY_AMOUNTS = "Pay Amount can not be empty, ";
        String IP_MAC = "Ip Mac Address can not be empty, ";
        String SERVICE_EMPTY = "Service Id can not be empty, ";
        String DEPT_EMPTY = "Dept Id can not be empty, ";
        String BOOK_DTO_EMPTY = "BookingReqDTO can not be empty, ";
        String FILTER_TYPE = "Filter type can not be empty, ";

    }

    interface RnLService {
        String REMARKS = "and above ids remarks";
    }

    interface RnLDetailEntity {

        String TB_CONTRACT = "TB_CONTRACT_DETAIL";
        String CONTD_ID = "CONTD_ID";
        String CONTP1_ID = "CONTP1_ID";
        String TB_CONTRACT_DETAIL = "TB_CONTRACT_INSTALMENT_DETAIL";
        String TB_CONTRACT_MAST = "TB_CONTRACT_MAST";
        String TB_CONTRACT_PART1 = "TB_CONTRACT_PART1_DETAIL";
        String TB_CONT_PART2 = "TB_CONTRACT_PART2_DETAIL";
        String CONTP2_ID = "CONTP2_ID";
        String TB_CONT_TERMS = "TB_CONTRACT_TERMS_DETAIL";
        String CONTT_ID = "CONTT_ID";
        String RL = "RL";
        String TB_RL_ESTATE = "TB_RL_ESTATE_BOOKING";
        String EBK_ID = "EBK_ID";
        String TB_RL_EST = "TB_RL_EST_CONTRACT_MAPPING";
        String CONT_EST = "CONT_EST_MAPID";
        String ES_ID = "ES_ID";
        String TB_RL_PROPERTY = "TB_RL_PROPERTY_DTL";
        String PROPD_ID = "PROPD_ID";
        String PROP_ID = "PROP_ID";
        String TB_RL_BILL_MAST = "TB_RL_BILL_MAST";
        String BM_BMNO = "BM_BMNO";

    }

    interface AccountJournalVoucherEntry {
        String AUTH = "AUTH";
        String VOUCHER_LIST = "VocherList";
        String SET_AMOUNT = "0.00";
        String REPORT_TO = "reportDto";
        String CV = "CV";
        String PV = "PV";
        public String TB_AC_VOUCHER = "TB_AC_VOUCHER";
        public String VOU_NO = "VOU_NO";
        String JV = "JV";
        public String AUTH_REMARK = "authRemark";
        public String VOU_ID = "vouId";
        public String RECEIPT_DATE = "receiptDate";
        public String VOUCHER_TYPE_ID = "voucherTypeId";
        public String DEPOSIT_DATE = "depositDate";
        public String DEPOSIT_SLIP_NO = "depositSlipNo";
        public String VOUDET_ID = "voudetId";
        public String ROW_ID = "rowId";
        public String ORG2 = "org";
        public String REF_NO = "refNo";
        public String VOUCHER_TYPE = "voucherType";
        public String AUTH_STATUS2 = "authStatus2";
        public String AUTH_STATUS1 = "authStatus1";
        public String VOU_SUB_TYPE_DFA = "DFA";
        public String VOUCHER_AUTH_TYPE = "VT";
        public String VOU_SUB_TYPE_TEC = "TEC";
        public String DEPT_ID = "dpDeptId";
        public String VOUCHER_SUB_TYPES = "vouSubTypes";
        public String ENTRY_TYPE_ID = "entryTypeId";

    }

    interface AccountBudgetAdditionalSupplemental {

        String APPROVED = "Approved";
        String DISAPPROVED = "Disapproved";
        String REV = "REV";
        String BUDGET_TYPE_STATUS = "budgetTypeStatus";
        String BUDGET_SUBTYPE_STATUS = "budgetSubTypeStatus";
        String KEY_TEST = "keyTest";
        String RECORD_SAVE_SUCCESS = "Records has been saved successfully";
        String RECORD_UPDATE_SUCCESS = "Records has been updated successfully";
        String UNAPPROVED = "Unapproved";
    }

    interface PaymentEntry {
        String GRID_ITEM = "gridItems";
        String PAYMENT_ENTRY = "PaymentEntry/index";
        String BILL_DETAIL = "billDetails";
        String PAYMENT_DETAIL = "paymentDetailList";
        String GRID_RECORD = "gridRecords";
        public String PAYMENT_ID = "paymentId";
        public String DELETION_REMARK = "deletionRemark";
        public String PAYMENT_DELETION_DATE = "paymentDeletionDate";
        public String DELETION_AUTHORIZED_BY = "deletionAuthorizedBy";
        public String PAYMENT_DELETION_ORDER_NO = "paymentDeletionOrderNo";
        public String PAYMENT_DELETION_FLAG = "paymentDeletionFlag";
        public String PAYMENT_TYPE_FLAG = "paymentTypeFlag";
        public String BA_ACCOUNTID = "baAccountid";
        public String PAYMENT_NO = "paymentNo";
        public String BUDGET_CODE_ID = "budgetCodeId";
        public String PAYMENT_AMOUNT = "paymentAmount";
        public String PAYMENT_ENTRY_DATE = "paymentEntryDate";
        String SEARCH_PAYMENT_DETAILS = "searchPaymentDetails";
        String INSTRUMENT_NUMBER = "instrumentNumber";
        String PAYMENT_DATE = "paymentDate";
        String STOP_PAYMENT_HTML_URL = "/StopPayment.html";
    }

    interface RTGSPaymentEntry {
        String GRID_ITEM = "gridItems";
        String RTGS_PAYMENT_ENTRY = "RTGSPaymentEntry/index";
    }

    interface AccountContraVoucherEntry {
        String TRANSFER_ENTRY = "Transfer Entry";
        String CASH_WITHDRAW_ENTRY = "Cash Withdrawal Entry";
        String CASH_DEPOSIT_ENTRY = "Cash Deposit Entry";
        String ACCOUNT_CONTRA_LIST = "accountContraList";
        Character T = 'T';
        Character W = 'W';
        Character D = 'D';
        String ORG_ID = ", orgId=";
        public String TB_AC_PAYMENT_MAS2 = "TB_AC_PAYMENT_MAS";
        public String PAYMENT_NO2 = "PAYMENT_NO";
        public String TRANSACTION_ID = "transactionId";
        public String BANK_AC_ID = "bankAcId";
        public String TRANSACTION_NO = "transactionNo";
        public String TRANSACTION_TYPE = "transactionType";
        String TF = "T";
        String WD = "W";
        String PE = "P";
        String DE = "D";
    }

    interface AccountChequeDishonour {
        String PAYMENT_ENTRY = "PAYMENT ENTRY";
        String NUMBER = "1234";
        public String VOUCHER_SUB_TYPE_ID = "voucherSubTypeId";
        public String REMARKS2 = "remarks";
        public String CHEQUE_DIS_CHG_AMT = "chequeDisChgAmt";
        public String CHEQUE_DISHONOUR_DATE = "chequeDishonourDate";
        public String FLAG2 = "flag";
        public String RECEIPT_MODE_ID = "receiptModeId";
        public String BANK_ACCOUNT = "bankAccount";
        public String NUMBER2 = "number";

    }

    interface AccountChequeOrCash {
        String ARROW = "-->";
        String TB_ACC_DEPOSITSLIP = "TB_AC_BANK_DEPOSITSLIP_MASTER";
        String DPS_SLIP_NO = "DPS_SLIPNO";
        String SET_ORG_ID = "setOrgid";
        public String SET_CREATED_BY = "setCreatedBy";
        public String SET_CREATED_DATE = "setCreatedDate";
        String DEPOSIT_SLIP_ENTRY = "Deposit Slip Entry";
        public String SET_UPDATED_BY = "setUpdatedBy";
        public String SET_UPDATED_DATE = "setUpdatedDate";
        public String SET_LG_IP_MAC = "setLgIpMac";

    }

    interface AccountReceiptEntry {
        String RECEIPT_LIST = "ReceiptList";
        String RT = "RT";
        String HEAD_CODE_MAP = "headCodeMap";
        String BY = "By";
        String ACC_RECEIPT_REPORT = "oAccountReceiptReportMasDto";
        String RECEIPT_VIEW_DATA = "receiptViewData";
        String RECEIPT_VIEW_PAGE = "ReceiptViewPage";
        public String APPEND_AND = "  and ";
        public String PAYEE_NAME = "PayeeName, ";
        public String CREATED_BY = "createdBy, ";
        public String LANG_ID = "langId, ";
        public String RECEIPT_COLLECTION_MODE_DETAILS = "ReceiptCollectionModeDetails, ";
        public String FEEAMOUNT = "].feeamount ";
        public String VOUCHER_DETAILS = "voucherDetails[";
        public String RECEIPT_HEAD = "].receiptHead, ";
        public String RECEIPTR_DETAILS = "receiptrDetails[";
        public String RECEIPT_COLLECTION_DETAILS = "ReceiptCollectionDetails, ";
        public String LG_IP_MAC = "lgIpMac, ";
        public String TRN_DATE = "TRN.Date, ";
        public String TRN_NO = "TRN.No, ";
        public String BANKNAME = "bankname, ";
        public String MODE = "mode, ";
        public String RM_DATE = "rmDate";
        public String RM_RECEIVEDFROM = "rmReceivedfrom";
        public String RM_AMOUNT = "rmAmount";
        public String RM_RCPTNO = "rmRcptno";
        public String RM_BANK_ID_NOT_EXIST = "Bank Account Id Does Not Match With Existing Bank Account Id, ";
    }

    public static interface CHEQUE_DISHONOUR {
        String IS_VIEW_MODE = "isViewMode";
        String CHEQUE_OR_CASH = "chequeOrCash";
        String CHEQUE_OR_DD = "chequeOrDDDetailsDiv";
        String RECEIPT_DETAIL = "listOfReceiptDetails";
        String BANK_LIST = "banklist";
        String BANK_AC_LIST = "bankList";
        String PAY = "PAY";
        String PERMANENT_PAY_LIST = "permanentPayList";
        String DEPT_LIST = "deptList";

    }

    interface AccountBillEntry {
        String AUTH = "Auth";
        String MODEL_CHK = "modeCheck";
        String AD = "AD";
        String DE = "DE";
        String LI = "LI";
        String ENTRY = "Entry";
        String ERROR_MSG = "errorMsg";
        String DEPT_LIST = "departmentList";
        String EDIT_DATA = "editData";
        String EXPENDITURE_HEAD_MAP = "expenditureHeadMap";
        String DEDUCT_HEAD_MAP = "deductionHeadMap";
        String PAYMENT_REVIEW = "paymentEntryView";
        String PAYMENT = "payment";
        String VOT = "VOT";
        String TDP = "TDP";
        String ON_BACK_REVARSAL_URL = "onBackReversalUrl";
        String REVERSAL_GRID_DATA = "reversalGridData";
        String LIVE_DATE_IN_SLI = "Live Date not set into SLI Prefix's other field";
        String SUCCESS = "SUCCESS";
        String OK = "OK";
        String VALIDATE_TRANSACTION_DATE = "Transaction date must be either greater than or equal to ";
        String VALIDATE_CURRENT_DATE = " Or less than or equal to Current Date.";
        String INTERNAL_SERVER_ERROR = "INTERNAL SERVER ERROR!";
        String VALIDATE_AUTH_DATE = "account.bill.authorization.date.entry.date.validation";
        String VALIDATE_ENTRY_DATE = "entryDate date cannot be null";
        String VALIDATE_AUTH_DATE_NULL = "Authorization date cannot be null";
        String INVOICE_ADVANCE_NO = "Invoice / Bill Entry against advance number is : ";
        String INVOICE_DEPOSIT_NO = "Invoice / Bill Entry against deposit number is : ";
        String INVOICE_WORKORDER_NO = "Invoice / Bill Entry against work order number is : ";
        String WORKORDER_DATE = "work order date is :";
        String DEPOSIT_DATE = "deposit date is :";
        String ADVANCE_DATE = "advance date is :";
        Character R = 'R';
        String ORG_ID = "orgId ";
        String APPEND_ORG_ID = " orgId ";
        String LANG_ID = " languagetId";
        String ORG = "orgId, ";
        String BILL_ID = "billId ";
        String BILL_TYPE_ID = "billTypeId, ";
        String INVOICE_AMT = "invoiceAmount, ";
        String NARRATION = "narration, ";
        String CHK_AUTH = "checkerAuthorization, ";
        String CHK_REMARK = "checkerRemarks, ";
        String EXP_LIST_DTO = "expDetListDto, ";
        String BUDGET_CODE_ID = "budgetCodeId, ";
        String BCH_ID = "bchId, ";
        String AMOUNT = "amount ";
        String SANCTION_AMT = "sanctionedAmount, ";
        String DIS_ALLOWED_REMARK = "disallowedRemark ";
        String DED_DET_LIST = "dedDetListDto[ ";
        String RATE = "rate, ";
        String BUGDET_CODE = " budgetCodeId ";
        String FIN_YEAR_ID = " finYearId ";
        String SUP_ORG_ID = " superOrgId";
        String BUDGET_CODE = " budgetCodeId";
        String SANCTIONED_AMOUNT = " sanctionedAmount";
        String DELETION_POSTING_DATE = "deletionPostingDate";
        String ID = "id";
        String LG_IP_MAC_ADDRESS_UPDATED = "lgIpMacAddressUpdated";
        String UPDATED_DATE = "updatedDate";
        String UPDATED_BY = "updatedBy";
        String BILL_DELETION_REMARK = "billDeletionRemark";
        String BILL_DELETION_DATE = "billDeletionDate";
        String BILL_DELETION_AUTHORIZED_BY = "billDeletionAuthorizedBy";
        String BILL_DELETION_ORDER_NO = "billDeletionOrderNo";
        String BILL_DELETION_FLAG = "billDeletionFlag";
        String VENDOR_ID = "vendorId";
        String BILL_NO = "billNo";
        String BILL_TYPE = "billType";
        String UNCHECKED = "unchecked";
        String DEPARTMENT_ID = "departmentId";
        String BUDGET_PARAMETERS_STATUS = "budgetDefParamStatus";
        String FIELD_ID = "fieldId, ";
        String ORGID = "orgId, ";
        String CREATED_BY = "createdBy, ";
        String CREATED_DATE = "createdDate, ";
        String LG_IP_MAC = "lgIpMac, ";
        String BILL_ENTRY_DATE = "billEntryDate, ";
        String BILL_TYPE_ESB = "ESB";
        String ACCOUNT_BILL_TYPE = "MI,ESB";
        String TB_AC_BILL_MAS = "tb_ac_bill_mas";
        String BM_BILLNO = "BM_BILLNO";
        String BILL_ENTRY_URL="AccountBillEntry.html";
        String BPR="BPR";
    }

    interface TbAcVendormaster {
        String VENDOR_STATUS = "vendorStatusAcIn";
        String VENDOR_TYPE = "venderType";
        String VENDOR_STATUS_IN = "vendorStatus";
        String SLI_MODE = "sliMode";
        public String VM_VENDORCODE = "VM_VENDORCODE";
        public String TB_VENDORMASTER = "TB_VENDORMASTER";
        public String FI04 = "FI04";
        String TENANT = "T";
        String CONTRACTOR = "C";
    }

    interface AccountBudgetCode {
        String DEPT_STATUS = "deptStatus";
        String PRIMARY_STATUS = "primaryStatus";
        String BUDGET_CODE = "tbAcBudgetCode";
        String OBJECTWISE_STATUS = "objectWiseStatus";
		String NAB = "NAB";
		String CFB = "CFB";
		String BSB = "BSB";
		String NMB = "NMB";
		String IEB = "IEB";
    }

    interface AccountBudgetOpenBalance {
        String SLI_PREFIX_MODE = "Kindly set SLI prefix default for Live Mode.";
        String SLI_PREFIX_LIVE_DATE = "Kindly set Go-live date (DD/MM/YYYY) format in other value of SLI prefix:";
    }

    interface BankReconciliation {
        String BANK_LIST = "bankList";
        String CH_LIST = "chList";
        public String RECEIPT_TYPE_FLAG = "receiptTypeFlag";
        public String TRAN_DATE = "tranDate";
        public String TRAN_MODE = "tranMode";
        public String TO_DTE = "toDte";
        public String FROM_DTE = "fromDte";
        public String DEPOSITE_TYPE_FLAG = "depositeTypeFlag";
        public String CATEGORY_LIST = "clearLookUpList";
        public String updatedBy = "updatedBy";
        public String updatedDate = "updatedDate";
        public String updatedIpmacId = "updatedIpmacId";
    }

    interface AccountBudgetProjectedExpenditure {
        String BUDGET_PROJ_EXPENDITURE = "tbAcBudgetProjectedExpenditure";
        String BUDGET_EXPENDITURE_MAP = "budrexpMap";
        public String FA_YEAR_ID = "faYearId";
        public String PR_EXPENDITURE_ID = "prExpenditureId";
        public String PR_PROJECTION_ID = "prProjectionId";
        public String TB_AC_PROJECTEDPROVISIONADJ = "TB_AC_PROJECTEDPROVISIONADJ";
        public String RP_TRNNO = "RP_TRNNO";
        public String SAC_HEAD_STATUS ="sacHeadStatus";
    }

    interface AccountChequeOrCashDeposite {

        public String DEPOSITE_NULL = "depositeNull";
        public String UPDATED_DATE = "updatedDate";
        public String UPDATED_BY = "updatedBy";
        public String DPS_DEL_REMARK = "dps_del_remark";
        public String DPS_DEL_DATE = "dps_del_date";
        public String DPS_DEL_AUTH_BY = "dps_del_auth_by";
        public String DPS_DEL_ORDER_NO = "dps_del_order_no";
        public String DPS_DEL_FLAG = "dps_del_flag";
        public String DEPOSITE_SLIP_ID = "depositeSlipId";
        public String SLIP_ID = "slipId";
        public String SLIP_NO = "slipNo";
        public String ACCOUNT_ID = "accountId";
        public String DP_DEPT_ID = "dpDeptId";
        public String CPD_MODE = "cpdMode";
        public String FEE_MODE = "feeMode";
        public String DEPOSIT_SLIP_ID = "depositSlipId";

    }

    interface AccountDeposit {
        String ACCOUNT_HEAD_MAP = "accountHeadsMap";
        String DO = "DO";
        String DP = "DP";
        String DEPT_TYPE_MAP = "deptTyepsMap";
        public String DEP_AMT = "depAmt";
        public String CPD_DEPOSIT_TYPE = "cpdDepositType";
        public String VM_VENDORID = "vmVendorid";
        public String DEPNO = "depNo";
        public String DEP_NO = "DEP_NO";
        String TB_AC_DEPOSITS = "TB_AC_DEPOSITS";
    }

    interface AccountTDSTaxHeads {
        String ACTIVE_ID = "activeID";
        public String TDS_TYPE_ID = "tdsTypeId";
        public String ACCOUNT_HEAD_ID = "accountHeadId";
    }

    interface AdvanceEntry {
        String OPEN = "Open";
        String ATY = "ATY";
        String ADVANCE_TYPE = "AdvanceType";
        String ADVNCE_TYPE = "advanceType";
        String VENDOR_LIST = "vendorList";
        String ADVANCE_HEAD = "advanceHead";
        String CPD_ID_STATUS = "cpdIdStatus";
        String ADV_AMOUNT = "advAmount";
        String ADV_NAME = "advName";
        String ADV_DATE = "advDate";
        String ADVANCE_NUMBER = "advanceNumber";
    }

    interface AccountFinancialReport {
        public String RECEIPT_SIDE_LIST = "receiptSideList";
        public String PAYMENT_SIDE_LIST = "paymentSideList";
        public String OPENING_RECEIPT_SIDE_LIST = "openingReceiptSideList";
        public String NON_OPENING_RECEIPT_SIDE_LIST = "nonOpeningReceiptSideList";
        public String OPENING_PAYMENT_SIDE_LIST = "openingPaymentSideList";
        public String NON_OPENING_PAYMENT_SIDE_LIST = "nonOpeingPaymentSideList";
        public String RPR2 = "RPR";
        public String GRT = "GRT";
        public String GFD = "GFD";
    }

    interface StandardAccountHeadMapping {
        String IVT = "IVT";
        String AST = "ACL";
        String LNT = "LNT";
        String TDS = "TDS";
        String DTY = "DTY";
        String PAY_MODE = "payMode";
        String UPDATE = "UPDATE";
        String PRIMARY_HEAD = "primaryHead";
        String VNT = "VNT";
        String VENDOR_TYPE = "vendorType";
        String DEPOSIT_TYPE = "depositType";
        String INVEST_TYPE = "investmentType";
        String ADVANCE_TYPE = "advanceType";
        String ASSET = "asset";
        String LOAN = "loans";
        String STATUTORY_DEDUCTION = "statutoryDeductions";
        String CREATE = "CREATE";
        String NULL = "null";
        String VN = "VN";
        String IN = "IN";
        String AS = "AS";
        String SD = "SD";
        String LO = "LO";
        String FAD = "FAD";
        String ADP = "ADP";

    }

    interface AccountBudgetProjectedRevenueEntry {
        String BUDGET_REVENUE_MAP = "budrevMap";
        String BUDGET_PROJ_REVENUE_ENTRY = "tbAcBudgetProjectedRevenueEntry";
    }

    interface AccountDepositeAndAdvnHeadsMapping {
        String DEPOSIT_TYPE_LEVEL = "depositTypeLevel";
        String ADVANCE_TYPE_LEVEL = "advancedTypeLevel";
        String ACC_DEPOSIT_ADVN_HEAD = "accountDepositeAndAdvnHeadsMappingEntryMaster/list/validation";
        String NAME = "name";
        String MESSAGE = "message";
    }

    interface BankAccountMaster {
        String PRIMARY_MASTER_ITEM = "listOfTbAcPrimaryMasterItems";
        String BRANCH_LOOKUP = "branchlookUp";
        String BANK_ACC_MASTER = "bankAccountMaster";
        String GRAMIN_BANK="Chhattisgarh Rajya Gramin Bank";

    }

    interface AccountBudgetReappropriationMaster {
        String AC_REAPPROPRIATE_MAP = "acReappMap";
        String DEPT_MAP_DATA = "deptMapData";
        String BUDGET_CODE_MAP = "budgetCodeMap";
        String BUDGET_EXP_MAP = "budgetCodeExpStaticMap";
    }

    interface AccountTenderEntryAuthorization {
        String DEPOSIT_CODE_MAP = "depositCodeMap";
    }

    interface DirectPaymentEntry {
        String PAY_DETAIL_LIST = "paymentDetailsList";
        String PAY_DETAIL = "paymentDetails";
        String PAYMENT_LIST = "paymentList";
        /*
         * String CASH = "Cash"; String CHEQUE = "Cheque";
         */
    }

    interface AccountDepositSlip {
        String CLEARING_DATE = "listOfChequeDDPoDetails.clearingDt";
        String PAY_ORDER_DATE = "listOfChequeDDPoDetails.payOrderDt";
        String NORMAL = "NORMAL";
        String Receipt_DETAIL = "receiptDetailDiv";
        String LEDGER_DETAIL = "ledgerDetailDiv";
        String D_RAW_E_BANK = "draweeBank";
        String SEARCH_TYPE = "searchType";
        String LIST_LEDGER_DETAIL = "listOfLedgerDetails";
        String LIST_DRAW_E_BANK = "listOfDraweeBank";
        String LIST_CHQ_DETAIL = "listOfChequeDDPoDetails";
        String ACC_DEPOSIT_URL = "AccountDepositSlip.html";
        String LIST_DEP_SLIP_TYPES = "listOfDepSlipTypes";
    }

    interface RecieptRegister {
        String EMP_MAP = "employee_Map";
        String EMP_LIST = "employeeList";
    }

    interface SecondaryheadMaster {
        String SECONDARY_HEAD = "secondaryheadMaster";
        String SECONDARY_STATUS = "secondaryStatus";
        String HAS_ERROR = "hasError";
    }

    interface TbAcChequebookleafMas {
        String CHQ_BOOK_RETURN_DATE = "chkbookRtnDatetemp";
        String L_MOD_DATE = "lmoddate";
        String BANK_MAP = "bankMap";
        String EMP_LIST = "emplist";
        String LIST_TO_SHOW = "listToshow";
        String FROM_CHQ_NO = "fromChequeNo";

    }

    interface TbAcCodingstructureMas {
        String DEFAULT_STATUS = "defaultFlagStatus";
        String NON_DEFAULT_STATUS = "nonDefaultFlagStatus";
        String DEFAULT_ID = "defaultOrgIdStatus";
        String NON_DEFAULT_ID = "nonDefaultOrgIdStatus";
    }

    interface TbAcPayToBank {
        String TB_ENTRY_DATE = "ptbEntrydatetemp";
        String TB_BANK_CODE = "ptbBankcode";
        String ERROR_BANK_CODE = "ErrorvmPtbBankcode";
    }

    interface TransactionTracking {
        String ACCOUNT_HEAD = "accountHeads";
        String FINANCE_YEAR_MAP = "financialYearMap";
    }

    interface LOI {
        String TB_LOI_MAS = "TB_LOI_MAS";
        String LOI_ID = "LOI_ID";
        String LOI_NO = "LOI_NO";
        String TB_LOI_DET = "TB_LOI_DET";
        String LOI_DET_ID = "LOI_DET_ID";
        String LOI_NO_FORMAT = "%05d";
    }

    interface AdminForgotpassword {
        // String SUCCESS = "admin.common.result";
        String ADMIN_OTP_FORGOT_PASS = "AdminOTPForgotPassword";
        String ADMIN_SET_FORGOT_PASS = "AdminSetForgotPassword";
        String ADMIN_FORGOT_PASS_MOBILE = "eip.admin.forgotPassword.isRegisteredMobile";
        String ADMIN_MOBILE_MANDATORY = "eip.admin.forgotPassword.mobileNoMandatory";
        String ADMIN_OTP_FAIL = "admin.otp.fail";
        String ADMIN_MOBILE_REG = "admin.mobile.notregistered";
        String ADMIN_PASS_SET_FAIL = "admin.passwordset.failed";
        String ADMIN_MOBILE_VERIFIED = "admin.mobile.verified";
        String ADMIN_OTP_EXPIRED = "admin.otp.expired";
        String ADMIN_SET_VALDIITOR_MOBILE = "AdminResetValidateMobile";
        String ADMIN_REGISTRED_MOBILE = "eip.citizen.forgotPassword.isRegisteredMobile";
        String ADMIN_FORGOT_PASS_MOBILE_MANDTORY = "eip.citizen.forgotPassword.mobileNoMandatory";
        String ADMIN_OTP_RESET = "AdminOTPResetPassword";
        String ADMIN_OTP_RESET_VALIDN = "AdminOTPResetPasswordValidn";
        String ADMIN_CITIZEN_OTP_SEND_FAIL = "eip.citizen.otp.send.failed";
        String EMPLOYEE_MOBILE_NUM_NOT_REG = "Employee.mobileNumNotReg";
        String ADMIN_RESET_PASSWORD = "AdminResetPassword";
        String CITIZEN_RESET_PASSWORD_FAILED = "eip.citizen.resetPassword.failed";
        String CITIZEN_INVALID_USER = "eip.citizen.invalid.user";

    }

    interface AdminHome {
        String XFRAME_OPTIONS = "X-FRAME-OPTIONS";
        String DENY = "DENY";
        String CITIZEN_NOUSER_LOGINAME = "citizen.noUser.loginName";
        String ADMIN = "ADMIN";
        String DEPT_HOME_EIP = "DeptHomeEIP";
        String REDIRECT_DEPT_DOC_VERY = "redirect:DeptDocVerification.html";
        String REDIRECT = "redirect:Home.html";
        String HOME_PAGE = "HomePage";
        String CITIZEN_EDIT_PROFILE = "CitizenEditProfile";
        String CITIZEN_UPDATE_EMAIL = "citizenUpdateEmail";
        String EMPLOYEE_UNIQUE = "Employee.uniqueEMailId";
        String USER_PROFFILE = "eip.citizen.editUserProile.title";
        String USERPROFFILE_GENDER = "eip.citizen.editUserProile.gender";
        String USERPROFFILE_EMAIL = "eip.citizen.editUserProile.eMail";
        String USER_PROFILE_FNAME = "eip.citizen.editUserProile.firstName";
        String USER_PROFILE_LNAME = "eip.citizen.editUserProile.lastName";
        String USER_PROFILE_DOB = "eip.citizen.editUserProile.dob";
        String USER_PROFILE_PERADD = "eip.citizen.editUserProile.permaAddress";
        String USER_PROFILE_CUR_ADDRESS = "eip.citizen.editUserProile.corresAddress";
        String USER_PROFILE_PINCODE = "eip.citizen.editUserProile.permaPinCode";
        String USER_CORRESS_PINCODE = "eip.citizen.editUserProile.corresPinCode";
        String USER_PROFILE_LENGTH = "citizen.editProfile.corr.pincode.length.error";
        String USER_PROFILE_PINCODE_LENGTH = "citizen.editProfile.per.pincode.length.error";
        String USER_PROFILE_SUCESS_MSG = "citizen.editProfile.successMsg";
        String CITIZEN_PROFILE_VAL = "CitizenEditProfileValidn";
        String Edit_login_profile = "EditAgencyLoginProfile";
        String ADMMIN_LOGIN = "AdminLogin";
        String ADMIN_LOG_ORG_SELECTED = "admin.login.org.selected";
        String ADMIN_ALREADY_LOGGEDIN = "AlreadyLoggedIn";
    }

    interface TbDeporgMap {
        String LIST_OF_TBDEPART_ITEMS = "listOfTbDepartmentItems";
        String ERROR_MSG = "errormsg";
        String ALL_DEPARMENT_MAPPED = "All the departments are mapped with ";
        String LIST_OF_ORGANIZATION_ITEMS = "listOfTbOrganisationItems";
        String EXIST = "exist";
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
        String SHOW_DOCS = "SHOW_DOCS";
        String ORG_DEFAULT_STATUS = "orgDefaultStatus";
        String TB_ORGANIZATION_ERROR_ORGSHRNAME = "tbOrganisation.error.orgShrtNme";
        String VIEW_HELP = "viewHelp";
        String DESC_LIST = "desgList";
        String TB_ORGINATION = "tbOrgDesignation";
        String lIST_OF_SCRUITINY_MSTITEMS = "listOfTbScrutinyMstItems";
        String DTT = "DTT";
        String FORM_BUILDER_TYPE = "FBT";
        String SERVICE_LIST = "serviceList";
        String SCRUTINY_LABELS_DTO = "scrutinyLabelsDto";
        String FORM_BUILDERS_DTO = "formBuildersDto";
        String GROUP_DATA_LIST = "groupDataList";
        String YES_NO_LIST = "yesNoList";
        String COMPAREN_MAS_LIST = "comparentMasList";
        String PREFIX_LIST = "prefixList";
        String GET_CHILD_PREFIX_DATA = "getChildPrefixData";
        String SCRUNITY_DET_LIST = "scrutinyDetList";
        String WARD_LEVEL = "wardLevel";
        String WARDLEVEL_ID = "wardLevelId";
        String PREFIX_VAL = "prefixVal";
        String PREFIX_ID = "prefixId";
        String SERVICE_MAS_LIST = "serviceMasList";
        String COMPARE_DET_LIST = "comparentDetList";
        String VIEW = "view";
        String ACTIVE_ID = "activeId";
        String DEPT_LIST = "deptList";
        String CHILD_DEPT_LIST = "childDeptList";
        String IS_DEFAULT_ORG = "isDefaultOrg";
        String LANGUAGE_ID = "languageId";
        String ACN_PREFIX_LIST = "acnPrefixList";
        String UTS_PREFIX_LIST = "utsPrefixList";
        String BPT_PREFIX_LIST = "bptPrefixList";
        String TRANSACTION_COUNTER = "transactionCounter";
        String ACTIVE_NESS_LOOK_UPID = "activenessLookupId";
        String EDIT = "Edit";
        String VIEWS = "View";
        String STY = "STY";
        String SPT = "SPT";
        String PRN = "PRN";
        String ACK = "ACK";
        String STY_PREFIX_DATA = "styPrefixData";
        String SPT_PREFIX_DATA = "sptPrefixData";
        String PRN_PRE_FIX_DATA = "prnPrefixData";
        String ACNPRE_FIXDATA = "acnPrefixData";
        String APL_PRE_FIX_DATA = "aplPrefixData";
        String ACK_PREFIX_DATA = "ackPrefixData";
        String TXN_PREFIX_DATA = "txnPrefixData";
        String FSD = "FSD";
        String VTY = "VTY";
        String PON = "PON";
        String TAG = "TAG";
        String BUDGET_LIST = "budgetList";
        String FSD_PREFIX_DATA = "fsdPrefixData";
        String VTY_PREFIX_DATA = "vtyPrefixData";
        String PON_PREFIX_DATA = "ponPrefixData";
        String TAG_PREFIX_DATA = "tagPrefixData";
        String TAX_APPLICABLE = "taxApplicable";
        String DEPT = "dept";
        String TAX_CODE_LIST = "taxCodeList";
        String DEPENDS_ON_FACTORS = "dependsOnFactors";
        String EVENT_MAPNOT_SELECTEDlIST = "eventMapNotSelectedList";
        String FACT_FOR_DEPT = "factForDept";
        String SECONDARY_HEAD = "secondaryHead";
        String CDM_DEPTID = "cdmDeptId";
        String SMS_SERVICE_NAME = "smServiceName";
        String SMS_SHORT_DESC = "smShortdesc";
        String SMS_CHECK_VERIFY = "smChklstVerify";
        String SMS_CHALLAN_DURATION = "smChallanDuration";
        String SMS_FEE_SCHEDULE = "smFeesSchedule";
        String SM_PRINT_RESPONSE = "smPrintRespons";
        String TAX_DESC_ID = "taxDescId";
        String TAX_DESCID_EMPTY = "tax.taxDescId.empty.err";
        String TAX_DP_DEPTID = "tax.dpDeptId.empty.err";
        String TAX_METHOD = "taxMethod";
        String TAX_GROUP = "taxGroup";
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
        String BA_ACCOUNT_NO = "baAccountNo";
        String BANK_NAME = "bankName";
        String APP_REQ_VO_NOT_NULL = "ApplicationStatusRequestVO can not be null ";
        String TB_CFC_APP_MST = "TB_CFC_APPLICATION_MST";
        String D = "D";
        String TB_CSMR_INFO = "TB_CSMR_INFO";
        String CS_CCN = "CS_CCN";
        String CD = "%07d";
        String PADDING_SIX = "%06d";
        String PADDING_THREE = "%03d";
        String TB_REJECTION_MST = "TB_REJECTION_MST";
        String REJ_ID = "REJ_ID";
        String TB_DEPORG_MAP = "tbDeporgMap";
        String DESIGNATION_BEAN = "designationBean";
        String LOI_APPLICABLE = "LA";
        String LOI_NOTAPPLICABLE = "LNA";
        
        String STATE_LIST = "stateList";
    	String DISTRICT_LIST = "districtList";
		String BLOCK_LIST = "blockList";

        // new added for BRMS related changes
        String CAA = "CAA";
        String SLD = "SLD";
        String TAC = "TAC";
        String RSD = "RSD";
        String ADF = "ADF";

        // TDS Calculation
        String TRP = "TRP";
        String RTD = "RTD";
        String PADDING_FIVE = "%05d";
        String PADDING_FOUR = "%04d";
        String PADDING_TWO = "%02d";

    }

    interface CommonMasterUiValidator {
        String DSG_NAME = "dsgname";
        String DSG_MUST_NOT_EMPTY = "Designation Name must not empty";
        String DSG_NAME_REG = "dsgnameReg";
        String DSG_NAME_REG_NOT_EMPTY = "Designation Name Reg must not empty";
        String DSG_SHORT_NAME = "dsgshortname";
        String DSG_SHORT_NAME_NOT_EMPTY = "Designation Short Name must not empty";
        String SCRUTINYLABELS_SCRUTNITYID = "scrutinyLabels.scrutinyId";
        String SERVICE_NAME_NOT_EMPTY = "common.master.service.not.empty";
        String SCRUTINY_LABELS_LAVELS = "scrutinyLabels.levels";
        String SCRUTINY_NOT_EMPTY = "common.master.scrutiny.not.empty";
        String SCRUTINY_LABELS_GMID = "scrutinyLabels.gmId";
        String SELECT_ROLE = "common.master.select.role";
        String SCRUTINY_LABELS_SLFORM_MODE = "scrutinyLabels.slFormMode";
        String MODE_NOT_EMPTY = "common.master.mode.not.empty";
        String SCRUTINY_SLDATATYPE = "scrutinyLabels.slDatatype";
        String SELECT_DATATYPE = "common.master.select.datatype";
        String SCRUTINY_LABELS_SLABEL = "scrutinyLabels.slLabel";
        String LABEL_NOT_EMPTY = "common.master.label.not.empty";
        String SCRUTINY_LABELS_SLABELMAR = "scrutinyLabels.slLabelMar";
        String REGIONAL_LABEL_NOT_EMPTY = "common.master.regional.label.not.empty";
        String SERVICE_DEPTID_EMPTY_ERR = "service.deptid.empty.err";
        String SERVICE_MASTER_EMPTY = "common.master.service.master.empty.err";
        String SERVICE_SHORT_DESC_EMPTY = "service.short.desc.empty.err";
        String SERVICE_SHORT_DESC = "common.master.service.short.desc";
        String SERVICE_CHECKLIST_VERIFY = "service.chklist.verify.empty.err";
        String CHECK_LIST_VERIFICATION_APP = "common.master.checklist.verification.app";
        String CHALLAN_DURATION = "common.master.challan.duration";
        String SELECT_APPLICABLE_CHARGE = "common.master.select.applicable.charge";
        String SERVICE_PRINT_RESP_EMPTY_ERR = "service.print.resp.empty.err";
        String PRINT_RESPONSE_SELECTED = "common.master.print.response.selected";
        String TAX_APPLICABLE_EMPTY = "tax.taxApplicable.empty.err";
        String TAX_METHOD_EMPTY = "tax.taxMethod.empty.err";
        String TAX_GROUP_EMPTY = "tax.taxGroup.empty.err";
        String TAXNAME_NOT_EMPTY = "common.master.taxname.not.empty";
        String SELECT_DEPT = "common.master.select.dept";
        String SELECT_APPLICABLE = "common.master.select.applicable";
        String TAX_CAL_METHOD = "common.master.tax.cal.method";
        String TAX_GROUP = "common.master.tax.group";
        String SELECT_ORGID = "common.master.select.orgid";
        String ENETER_ENGNAME = "common.master.enter.engname";
        String ENTER_SHIRT_CODE = "common.master.enter.shortcode";
        String ENTER_REGIONAL = "common.master.enter.regional";
        String ENTER_STATE = "common.master.enter.state";
        String ENTER_TYPE = "common.master.enter.type";
        String ENTER_DISTRICT = "common.master.enter.district";
        String ENTER_SUBTYPE = "common.master.enter.subtype";
        String ENTER_DIVISION = "common.master.enter.division";
        String SCRUTINY_LABEL_ALREADY_EXIST = "scrutiny.label.exist";
        String ENTER_BLOCK= "common.master.enter.block";
    }

    interface ServiceCareCommon {
        String ALERTTYPE = " Please Select Alert ype";
        String CARE_REQUEST = "careRequest";
        String TASKNAME = "taskName";
        String UTS = "UTS";
        String EMPLOYEE = "employee";
        String REQUEST_CARE = "Requester Action";
        String DEPARTMENTS = "departments";
        String LOCATION = "locations";
        String DEPT_LIST = "deptList";
        String GENDER = "gender";
        String EMPTY = "care.grievance.empty";
        String NOT_EMPTY = "care.grievance.notempty";
        String REPORTTYPE = "reportType";
        String SEARCH_STRING = "searchString";
        String GRIEVENCE_SOLUTION = "GrievanceResolution";
        String REGISTRATION_ACKNOWLEDGEMENT_RECEIPT = "RegistrationAcknowledgementReceipt";
        String REGISTRATION_ACKNOWLEDGEMENT_RECEIPT_VALIDN = "RegistrationAcknowledgementReceiptValidn";
        String COMPLAINT_ACKNOWLEDGEMENT_MODEL = "complaintAcknowledgementModel";
        String GRIEVANCE_DEPARTMENT_REGISTRATION = "GrievanceDepartmentRegistration";
        String AREA_MAPPING_WARD_ZONE = "areaMappingWardZoneForCare";
        String GRIEVANCEDEPARTMENTREGISTRATION = "GrievanceDepartmentRegistration.html";
        String GRIEVANCEDEPARTMENTREOPEN = "GrievanceDepartmentReopen.html";
        String GRIEVANCEDEPARTMENTRESUBMISSION = "GrievanceResubmission.html";
        String GRIEVANCERESUBMISSION = "grievanceResubmission";
        String VIEW_GRIEVANCE_SEARCH_FORM = "viewGrievanceSearchForm";
        String VIEW_GRIEVANCE_STATUS = "viewGrievanceStatus";
        String VIEW_GRIEVANCE_LIST = "viewGrievanceList";
        String SEARCH_GRIEVANCE = "searchGrievance";
        String REOPEN_GRIEVANCE = "reopenGrievance";
        String GRIEVANCE_FEEDBACK = "grievanceFeedback";
        String RESUBMIT_GRIEVANCE = "resubmitGrievance";
        String FINDDEPARTMENTCOMPLAINTBYDEPARTMENTID = "findDepartmentComplaintByDepartmentId";
        String GRIEVANCE_COMPLAINTTYPES = "grievanceComplaintTypes";
        String GRIEVANCE_ORGANISATIONS = "grievanceOrganisations";
        String GRIEVANCE_DEPARTMENTS = "grievanceDepartments";
        String GRIEVANCE_LOCATIONS = "grievanceLocations";
        String WORKFL_DEFINITION_NOT_FOUND_GENERIC = "care.validator.worklfow.notfound.generic";
        String WORKFL_DEFINITION_NOT_FOUND_LOCATION = "care.validator.worklfow.notfound.location";
        String WITHIN_SLA = "care.withinSla";
        String BEYOND_SLA = "care.beyondSla";
        String ALL = "care.all";
        String LEVEL = "care.level";
        String SEARCH_DETAIL = "searchDetail";
        String VIEW_DETAIL = "viewDetail";
        String SHOW_DASHBAORD = "showDashboard";

        String GRIEVANCECOMPLAINTSTATUS = "GrievanceComplaintStatus.html";
        String GRIEVANCE_SEARCH_STATUS_FORM = "GrievanceSearchStatus";
        String OPERATOR_DASHBOARD_FORM = "OperatorDashBoard";
        String OPERATOR_DASHBOARD_VAL_FORM = "OperatorDashBoardValidn";
        String VIEW_GRIEVANCE_DETAILS = "viewGrievanceDetailsForm";
        String CARE_REOPEN_ERROR = "care.reopen.error.msg";
        String CARE_CN = "CN";

        interface Report {

            interface Titel {
                String GRIEVANCE_DEPT_WISE_REPORT = "care.reports.department.title";
                String GRIEVANCE_DEPT_WISE_SUMMARY_REPORT = "care.reports.department.summary.title";
                String GRIEVANCE_DEPT_STAT_WISE_REPORT = "care.reports.department.status.title";
                String GRIEVANCE_DEPT_STAT_WISE_SUMMARY_REPORT = "care.reports.department.status.summary.title";
                String GRIEVANCE_FEEDBACK_REPORT = "care.reports.feedback.title";
                String GRIEVANCE_FEEDBACK_REPORT_SUMMARY = "care.reports.feedback.summary.title";
                String GRIEVANCE_AGEING_DETAILED_REPORT = "care.reports.ageing.detailed.title";
                String GRIEVANCE_AGEING_SUMMARY_REPORT = "care.reports.ageing.summary.title";
                String GRIEVANCE_SLA_WISE_REPORT = "care.reports.sla.title";
                String GRIEVANCE_SLA_WISE_SUMMARY_REPORT = "care.reports.sla.summary.title";
                String GRIEVANCE_GRADING_DETAILED_REPORT = "care.reports.grading.detailed.title";
                String GRIEVANCE_GRADING_SUMMARY_REPORT = "care.reports.grading.summary.title";
                String GRIEVANCE_REPORT_DEPT_SERVICE_COMP_WISE_REPORT = "care.reports.department.service.title";
                String GRIEVANCE_REPORT_DEPT_SERVICE_MODE_COMP_WISE_REPORT = "care.reports.department.service.mode.title";
                String GRIEVANCE_REPORT_DEPT_SERVICE_MODE_COMP_WISE_SUMMARY_REPORT = "care.reports.department.service.mode.summary.title";
                String GRIEVANCE_SMS_EMAIL_HISTORY_REPORT = "care.reports.email.history.title";
            }

            String NORECORDFOUND = "care.reports.noRecordFound";
            String GRIEVANCE_SERVICETYPE_WISE = "grievanceServiceWise";
            String GRIEVANCE_USERTYPE_WISE = "grievanceUserWise";
            String GRIEVANCE_SERVICETYPE_WISE_REPORT = "grievanceDeptAndServiceWiseReport";
            String GRIEVANCE_USERETYPE_WISE_REPORT = "grievanceDeptAndServiceAndUserWiseReport";
            String GRIEVANCE_USERETYPE_WISE_SUMMARY_REPORT = "grievanceDeptAndServiceAndUserWiseSummaryReport";

            String GRIEVANCEREPORT = "GrievanceReport.html";
            String GRIEVANCE_REPORT_WARDZONE = "grievanceReportWardZone";
            String GRIEVANCE_REPORT_COMPLAINT_TYPE = "grievanceReportComplaintType";
            String GRIEVANCEREPORTAGEINGSLAB = "grievanceReportAgeingSlab";

            String GRIEVANCE_DEPT_WISE = "grievanceDeptWise";
            String GRIEVANCE_DEPT_WISE_REPORT = "grievanceDeptWiseReport";
            String GRIEVANCE_DEPT_WISE_SUMMARY_REPORT = "grievanceDeptWiseSummaryReport";

            String GRIEVANCE_DEPT_STAT_WISE = "grievanceDeptStatWise";
            String GRIEVANCE_DEPT_STAT_WISE_REPORT = "grievanceDeptStatWiseReport";
            String GRIEVANCE_DEPT_STAT_WISE_SUMMARY_REPORT = "grievanceDeptStatWiseSummaryReport";

            String GRIEVANCEFEEDBACK = "grievanceFeedback";
            String GRIEVANCE_FEEDBACK_REPORT = "grievanceFeedbackReport";
            String GRIEVANCE_FEEDBACK_SUMMARY_REPORT = "grievanceFeedbackSummaryReport";

            String GRIEVANCE_AGEING = "grievanceAgeing";
            String GRIEVANCE_AGEING_REPORT = "grievanceAgeingReport";
            String GRIEVANCE_AGEING_DETAILED_REPORT = "grievanceAgeingDetailedReport";
            String GRIEVANCE_AGEING_SUMMARY_REPORT = "grievanceAgeingSummaryReport";

            String GRIEVANCE_SLA_WISE = "grievanceSlaWise";
            String GRIEVANCE_SLA_WISE_REPORT = "grievanceSlaWiseReport";
            String GRIEVANCE_SLA_WISE_SUMMARY_REPORT = "grievanceSlaWiseSummaryReport";

            String GRIEVANCE_GRADING = "grievanceGrading";
            String GRIEVANCE_GRADING_REPORT = "grievanceGradingReport";
            String GRIEVANCE_GRADING_DEPT_WISE_REPORT = "grievanceGradingDeptWiseReport";

            String GRIEVANCE_SMS_EMAIL_HISTORY = "generateSmsAndEmailReport";
            String GRIEVANCE_SMS_EMAIL_HISTORY_FORM = "generateSmsAndEmailReportForm";
            
            String VALID_FROM_DATE="care.reports.validFromDate";
            String VALID_TO_DATE="care.reports.validToDate";
        }

        interface SMS_EMAIL_URL {
            String GRIEVANCE_DEPARTMENT_REGISTRATION = "GrievanceDepartmentRegistration.html";
            String GRIEVANCE_RESOLUTION = "GrievanceResolution.html";
        }

        interface StarRatings {
            static String getStarRatings(int star) {
                String starRatings = "";
                switch (star) {
                case 1:
                    starRatings = "Dissatisfied";
                    break;
                case 2:
                    starRatings = "Dislike";
                    break;
                case 3:
                    starRatings = "Its Ok";
                    break;
                case 4:
                    starRatings = "Liked It";
                    break;
                case 5:
                    starRatings = "Satisfied";
                    break;
                }
                return starRatings;
            }
        }

        interface SQLTables {
            String TB_CARE_REQUEST = "TB_CARE_REQUEST";
            String COMPLAINT_NO = "COMPLAINT_NO";
            String COMPLAINT_NO_FORMAT = "%04d";
        }
    }

    public enum URLBasedOnShortCode {
        WNC("NewWaterConnectionForm.html", "water"), WCO("ChangeOfOwnership.html", "water"), WCC("WaterDisconnectionForm.html",
                "water"), WCU("ChangeOfUsage.html", "water"), WRC("WaterReconnectionForm.html", "water"), WPL(
                        "PlumberLicenseForm.html",
                        "water"), WND("NoDuesCertificateController.html", "water"), RAF("RtiApplicationDetailForm.html",
                                "rti"), SAS("SelfAssessmentForm.html", "maker-checker"), NCA("NoChangeInAssessment.html",
                                        "maker-checker"), CIA("ChangeInAssessmentForm.html", "maker-checker"), NPR(
                                                "NewPropertyRegistration.html", "maker-checker"), AML("AmalgamationForm.html",
                                                        "maker-checker"), BIF("BifurcationForm.html", "maker-checker"), MUT(
                                                                "MutationForm.html",
                                                                "property"), DES("DataEntrySuite.html", "maker-checker"), RCP(
                                                                        "RoadCutting.html",
                                                                        "water"),RCW(
                                                                                "RoadCutting.html",
                                                                                "roadCutting"), NTL("TradeApplicationForm.html", "water"), CCS(
                                                                                "ChangeInCategorySubcategoryForm.html",
                                                                                "water"), WIL("IllegalToLegalConnection.html",
                                                                                        "water"), LAQ("LandAcquisition.html",
                                                                                                "water"), PLCA(
                                                                                                        "PlumberLicenseAuth.html",
                                                                                                        "maker-checker"), PLRA(
                                                                                                                "PLumberRenewalAuth.html",
                                                                                                                "maker-checker"), DPL(
                                                                                                                        "DuplicatePlumberLicense.html",
                                                                                                                        "maker-checker"), ADH(
                                                                                                                                "NewAdvertisementApplication.html",
                                                                                                                                "scrutiny"), PLR(
                                                                                                                                        "PlumberLicenseRenewal.html",
                                                                                                                                        "maker-checker"), RAL(
                                                                                                                                                "RenewalAdvertisementApplication.html",
                                                                                                                                                "scrutiny"), TLA(
                                                                                                                                                        "TransperLicense.html",
                                                                                                                                                        "scrutiny"), CTL(
                                                                                                                                                                "CancellationLicenseForm.html",
                                                                                                                                                                "scrutiny"), CBN(
                                                                                                                                                                        "ChangeInBusinessNameForm.html",
                                                                                                                                                                        "scrutiny"), RTL(
                                                                                                                                                                                "RenewalLicenseForm.html",
                                                                                                                                                                                "scrutiny"), HBP(
                                                                                                                                                                                        "NewHoardingApplication.html",
                                                                                                                                                                                        "scrutiny"), NHR(
                                                                                                                                                                                                "NursingHomePermissopnApproval.html",
                                                                                                                                                                                                "scrutiny"), HSR(
                                                                                                                                                                                                        "NursingHomePermissopnApproval.html",
                                                                                                                                                                                                        "scrutiny"), CAL(
                                                                                                                                                                                                                "AdvertisercancellationForm.html",
                                                                                                                                                                                                                "scrutiny"), BMC(
                                                                                                                                                                                                                        "BillingMethodChange.html",
                                                                                                                                                                                                                        "scrutiny"), NL(
                                                                                                                                                                                                                                "NewTCPLicenseForm.html",
                                                                                                                                                                                                                                "maker-checker-obj-loi"), ADA(
                                                                                                                                                                                                                                        "NewTCPLicenseForm.html",
                                                                                                                                                                                                                                        "maker-checker-obj-loi"), LNL(
                                                                                                                                                                                                                                                "NewTCPLicenseForm.html",
                                                                                                                                                                                                                                                "maker-checker-obj-loi"), DDA(
                                                                                                                                                                                                                                                        "NewTCPLicenseForm.html",
                                                                                                                                                                                                                                                        "maker-checker-obj-loi"), LAO(
                                                                                                                                                                                                                                                                "NewTCPLicenseForm.html",
                                                                                                                                                                                                                                                                "maker-checker-obj-loi"), DFOD(
                                                                                                                                                                                                                                                                        "NewTCPLicenseForm.html",
                                                                                                                                                                                                                                                                        "maker-checker-obj-loi"), UE(
                                                                                                                                                                                                                                                                                "NewTCPLicenseForm.html",
                                                                                                                                                                                                                                                                                "maker-checker-obj-loi"), DCA(
                                                                                                                                                                                                                                                                                        "NewTCPLicenseForm.html",
                                                                                                                                                                                                                                                                                        "maker-checker-obj-loi"), FOS(
                                                                                                                                                                                                                                                                                                "NewTCPLicenseForm.html",
                                                                                                                                                                                                                                                                                                "maker-checker-obj-loi");
        private URLBasedOnShortCode(String url, String processName) {
            this.url = url;
            this.processName = processName;
        }

        private String url;

        private String processName;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getProcessName() {
            return processName;
        }

        public void setProcessName(String processName) {
            this.processName = processName;
        }

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

    interface DmsClientType {
        String DMS_CLIENT_REST = "REST";
        String DMS_CLIENT_SOAP = "SOAP";
    }

    interface Dms {
        String DMS_DOC_ID = "DOC_ID";
        String DMS_DOCUMENT_DETAILS_BASE_URL = "/share/page/document-details";
        String DMS_NODEREF_PARAM = "?nodeRef=workspace://SpacesStore/";
        String DMS_NODE_PARAM = "&node=workspace://SpacesStore/";
        String DMS_USER_PARAM = "&user=";
        String DMS_PASS_PARAM = "&pass=";
        String MTD = "MTD";
        String KTD = "KTD";
        String KMS = "KMS";
        String DMS = "DMS";
        String DCT = "DCT";
        String KDT = "KDT";
        String CARE_METADATA = "CareMetadata";
        String PROPERTY_METADATA = "PropertyMetadata";
        String TRADE_MEADATA = "TradeMetaService";
        String WATER_MEADATA = "WaterMetaService";
        String AUDIT_MEADATA = "AuditMetaService";
        String ASSET_METADATA = "AssetMetaService";
        String ADH_METADATA = "AdhMetaService";
        String RNL_METADATA = "RnlMetaService";
        String COMMON_DEPT = "CFC";
        String PROPERTY_DEPT = "AS";
        String TRADE_DEPT = "ML";
        String E101 = "E101";
        String E102 = "E102";
        String E103 = "E103";
        String E104 = "E104";
        String ERROR_CODE = "error_Code";
        String ERROR_MSG = "error_msg";
        String DOCUMENT_EXISTS = "Document Already Exists";
        String METADATA_NOT_MAP = "Metadata not mapped in Alfresco";
        String METADATA_NOT_SAVED = "Unable to save data";
        String SITE_NOT_PRESENT = "Specified site is not present in Alfresco";
        String FILE = "file";
        String INPUTXML = "inputXml";
        String DOON_DMS = "DOON_DMS";
        String DMS_ROOT_PATH = "Dehradun Smart City Ltd.";
        String APPLICATION_ID = "applicationId";
        String APPLICANT_NAME = "applicantName";
        String SUBMISSION_DATE = "submissionDate";
        String SERVICE_NAME = "serviceName";
        String DEPT_NAME = "departmentName";
        String Doon_file_path="https://cdn.pixabay.com/photo/2018/01/14/23/12/nature-3082832_960_720.jpg";
    }

    interface ScheduleOfRate {
        String HTML = "/ScheduleOfRate.html";
        String MSTDTO_SORWORKCAT = "mstDto.sorWCategory";
        String SOR_FORM = "ScheduleOfRateForm";
        String SOR_REPORT = "ScheduleOfRateReport";
        String SOR_FORM_EDIT = "ScheduleOfRateFormEdit";
        String SOR_DID = "sorDId";
        String SOR_ID = "sorId";
        String WUT = "WUT";
        String MST_DTO_LIST = "mstDtoList";
        String WKC = "WKC";
        String SORNAME_ID = "sorNameId";
        String SOR_FROM_DATE = "sorFromDate";
        String SOR_TO_DATE = "sorToDate";
        String FILTER_SOR_DATA = "filterSORListData";
        String INACTIVE_SOR_MASTER = "inactiveSORMaster";
        String PRINT_SOR_REPORT = "printSORReport";
        String SOR_FILENAME = "ScheduleOfRate_";
        String VALIDATE_SOR_DATE = "validateSorStartDate";
        String SOR_CPD_ID = "sorCpdId";
        String HSF = "HSF";

    }

    interface WorkDefination {

        String WORK_DEF_HTML = "/WmsWorkDefinationMaster.html";
        String WORK_DEF_FORM = "WorkDefinationForm";
        String WORK_ID = "workId";
        String FILTER_RECORDS = "filterRecords";
        String DEFAUL_SLI_CHECK = "checkForDefaultSLI";
        String FILE_UPLOAD_COUNT = "WorkDefinitionFileUpload";
        String GIS_LAYER_NAME_POINT = "test_integration_point";
        String GIS_LAYER_NAME_LINE = "test_integration_line";
        String GIS_LAYER_NAME_POLYGONE = "test_integration_poly";
        String WORK_CATAGORY_BUILDING = "B";
        String WORK_CATAGORY_LAND = "L";
        String WORK_CATAGORY_ROAD = "R";
        String WORK_CATAGORY_BRIDGE = "BRI";
        String WORK_CATAGORY_WATER = "WWD";
        String WORK_CATAGORY_DRAINAGE = "DR";
        String WORK_COME_FROM = "comeFrom";

    }

    interface WorksManagement {
        String WORKS_MANAGEMENT = "WMS";
        String TB_WMS_PROJECT_MAST = "TB_WMS_PROJECT_MAST";
        String PROJECT_CODE = "PROJ_CODE";
        String WORK_CODE = "WORK_CODE";
        String TB_WMS_WORKDEFINATION = "TB_WMS_WORKDEFINATION";

        String PROJ_STARTDATE = "projStartDate";
        String PROJ_ENDDATE = "projEndDate";
        String PROJ_NAMEENG = "projNameEng";
        String PROJ_ACTIVE = "projActive";
        String SOR_ID = "sorId";
        String SOR_NAME = "sorName";
        String SORT_TYPE = "sorType";
        String SCHEME_ACTIVE = "schemeActive";
        String SCH_STARTDATE = "wmSchStrDate";
        String SCH_ENDDATE = "wmSchEndDate";
        String SCH_NAME = "wmSchNameEng";
        String EDIT = "E";
        String ADD = "A";
        String VIEW = "V";
        String UPLOAD = "U";
        String RATE_TYPE = "RA";
        String Send_for_RA_Bill = "C";
        String send_for_approval = "SA";
        String SOR_TYPE = "SOR";
        String MODE = "mode";
        String PROJECT_ADD = "P";
        String REPORT = "R";
        String APPROVAL = "AP";
        String SAVEMODE = "saveMode";
        String ABSTRACT_SHEET = "A";
        String MEASUREMENT_SHEET = "M";
        String LO = "LO";
        String UN = "UN";
        String ST = "ST";
        String LF = "LF";
        String RO = "RO";
        String From_Lead_Lift = "(From)-";
        String To_Lead_Lift = "(To)-";
        String ROYALITY = " Royality";

        String SAVE_AND_UPDATE_PROJECTMASTER = "saveAndUpdateProjectMaster";
        String EDIT_PROJECT_MASTERDATA = "editProjectMasterData";
        String PROJ_ID = "projId";
        String DEPART_ID = "departId";
        String RA_CODEMB = "racode";
        String RACODE_Mb_View = "racode";
        String WORKID_FOR_RA = "workId";
        String ADD_PROJECT_MASTER = "AddProjectMaster";
        String DELETE_PROJECT_MASTER = "deleteProjectMaster";
        String PROJECT_MASTER = "ProjectMaster";
        String START_DATE = "startDate";
        String END_DATE = "endDate";
        String PROJECT_NAME = "projectName";
        String GET_PROJECT_MASTER_GRID_DATA = "getProjectMasterGridData";
        String CHECK_FOR_DUPLICATE_PROJECT_CODE = "checkForDuplicateProjectCode";
        String PROJ_CODE = "projCode";
        String DATA_TYPE = "Data Type";
        String NULL_CHECK = "Can not Be null";
        String DUPLICATE_DATA = " Duplicate Data";
        String RATE_NEGATIVE_CHECK = "Rate Cannot Be negative";

        String ADD_MATERIAL_MASTER = "AddMaterialMaster";
        String DELETE_MATERIAL_MASTER = "deleteMaterialMaster";
        String SAVE_AND_UPDATE_MATERIAL_MASTER = "saveAndUpdateMaterialMaster";
        String EDIT_MATERIAL_MASTER_DATA = "editMaterialMasterData";
        String SOR_NAMESTR = "sorNameStr";
        String GET_MATERIAL_MASTER_GRID_DATA = "getMaterialMasterGridData";
        String GET_DATE_BY_SORNAME = "getDateBySorName";
        String GET_SORNAMES = "getSorNames";
        String MATERIAL_MASTER = "MaterialMaster";
        String LOAD_EXCEL_DATA = "loadExcelData";

        String SCHEMEMASTER_GETGRID = "schemeMasterGetGrid";
        String ADD_SCHEME_MASTER = "AddSchemeMaster";
        String SAVE_AND_UPDATE_SCHEME_MASTER = "saveAndUpdateSchemeMaster";
        String CHECK_DUPLICATE_SCHEME_CODE = "checkDuplicateSchemeCode";
        String EDIT_SCHEME_MASTER_DATA = "editSchemeMasterData";
        String GET_SCHEME_MASTER_GRID_SUMMRY = "getSchemeMasterGridData";
        String SCHEME_NAME = "wmSchNameEng";
        String DELETE_SCHEME_MASTER = "deleteSchemeMaster";
        String SHOW_SCHEME_MASTER = "showSchemeMaster";
        String VIEW_SCHEME_MASTER = "ViewSchemeMaster";
        String SCHEME_TO_PROJECT_VIEW = "schemeToProjectMasterView";
        String CHECK_PROJECT = "checkSchemeProjectAssociation";
        String SCHEME_ID = "wmSchId";
        String SCHEME_CODE = "wmSchCode";
        String SBY = "SBY";
        String MTY = "MTY";
        String WUT = "WUT";
        String VTY = "VTY";
        String WET = "WET";
        String TPA = "TPA";
        String ERR_FLAG = "errFlag";
        String MA_TYPE_ID = "maTypeId";
        String MA_ITEM_NO = "maItemNo";
        String LE = "LE";
        String LELIREPORT = "leadliftReport";
        String EXPORT_EXEL = "exportExcelData";
        String PPH = "PPH";

        String FORM_FOR_UPDATE = "formForUpdate";
        String PROJECT_FILE_UPLOAD = "ProjectMasterFileUpload";
        String FILE_COUNT_UPLOAD = "fileCountUpload";
        String LELI_IMPORT_EXCEPTION = "Exception while Importing Lead-Lift Master Data :";
        String LELI_EXPORT_EXCEPTION = "Exception while Exporting Lead-Lift Master Data :";
        String LELI_ENTRY = "LeadLiftEntry_";
        String FOR_ENTRY = "For Entry No. ";
        String NULL = "null";
        String FILTER_ESTIMATE_LIST_DATA = "filterEstimateListData";
        String WORK_ESTIMATE_GET_GRID = "workEstimateGetGrid";
        String ADD_WORK_ESTIMATE = "AddWorkEstimate";
        String ADD_LOGS_MEASUREMENT_SHEET = "AddLogsMeasurementSheet";
        String SELECT_ALL_SOR_DATA = "selectAllSorData";
        String ADD_WORK_ESTIMATE_LIST = "AddWorkEstimateList";
        String ADD_WORK_LABOUR = "AddWorkLabour";
        String ADD_WORK_MACHINERY = "AddWorkMachinery";
        String ADD_MEASUREMENT_SHEET = "AddMeasurementSheet";
        String ADD_MEASUREMENT_FORM = "AddMeasurementForm";
        String SELECT_ALL_SOR_DATA_BY_SORID = "selectAllSorDataBySorId";
        String SELECT_ALL_LOG_SOR_DATA_BY_SORID = "selectAllLogSorDataBySorId";
        String MEASUREMENT_SHEET_DETAILS = "MeasurementSheetDetails";
        String MEASUREMENT_SHEET_LOG_DETAILS = "MeasurementSheetLogDetails";
        String RATE_ANALYSIS_FORM = "RateAnalysisForm";
        String RATE_ANALYSIS_LOG_FORM = "RateAnalysisLogForm";
        String OPEN_SAVE_RATE_FROM_OTHER_TYPE = "openSaveRateFromOthertype";
        String SAVE_RATE_ANALYSIS = "saveRateAnalysis";
        String SAVE_ESTIMATE_DATA = "saveEstimateData";
        String SAVE_LBH_FORM = "SaveLbhForm";
        String ADD_WORK_OVERHEADS_FORM = "AddWorkOverheadsForm";
        String ADD_WORK_ENCLOSUERS_FORM = "AddWorkEnclosuersForm";
        String ADD_WORK_NON_SOR_FORM = "AddWorkNonSorForm";
        String SAVE_OVERHEAD_DATA = "saveOverHeadData";
        String SAVE_NON_SOR_DATA = "saveNonSorData";
        String SAVE_MACHINERY_DATA = "saveMachineryData";
        String SAVE_LABOUR_DATA = "saveLabourData";
        String SAVE_WORK_ENCLOSURES_DATA = "saveWorkEnclosuresData";
        String WORK_ENCLOSURES_FILE_UPLOAD = "workEnclosuresFileUpload";
        String WORK_WORKFLOW_HISTORY = "workWorkFlowHistory";
        String GET_WORKFLOW_HISTORY = "getWorkFlowHistory";
        String ACTIONS = "actions";
        String EDIT_ESTIMATION = "editEstimation";
        String LOGS_ESTIMATION = "logsEstimation";
        String GET_RATE_LIST_BY_TYPE = "getRateListByType";
        String OPEN_RATE_ANALYSIS = "openRateAnalysis";
        String OPEN_RATE_ANALYSIS_lOG = "openRateAnalysisLog";
        String PROJECT_ID = "projectId";
        String ESTIMATE_NO = "estimateNo";
        String WORK_NAME = "workName";
        String STATUS = "status";
        String MBSTATUS = "mbStatus";
        String FROM_DATE = "fromDate";
        String TO_DATE = "toDate";
        String ACTION_PARAM = "actionParam";
        String WORK_EID = "workEId";
        String RATE_TYPES = "rateType";
        String L_E = "Le";
        String L_I = "Li";
        String ADD_CONTRACT_VARIATION = "AddContractVariation";
        String CONTRACT_VARIATION = "ContractVariation";
        String MEASUREMENT_SHEET_VARIATION = "MeasurementSheetVariation";
        String WORK_NONSOR_TABLEDETAILS = "WorkNonSorTableDetails";
        String BILL_OF_QUANTITY = "BillOfQuantity";
        String SAVE_SOR_DETAILS = "saveSorDetails";
        String SAVE_NONSOR_DETAILS = "saveNonSorDetails";
        String SAVE_BILLQUANTITY_DETAILS = "saveBillQuantityDetails";
        String FILTER_CONTRACTVARIATIONLIST_DATA = "filterContractVariationListData";
        String VIEW_CONTRACT_VARIATION = "viewContractVariation";
        String GETNONSORFORM_LIST = "getNonSorFormList";
        String GETCONTRACT_DETAILS = "getContractDetails";

        String TENDER_INITIATION_HTML = "/TenderInitiation.html";
        String IN = "IN";
        String TND_INITIATIONNO = "TND_INITIATIONNO";
        String TND_LOA_NO = "TND_LOA_NO";
        String TB_WMS_TENDER_MAST = "TB_WMS_TENDER_MAST";
        String TB_WMS_TENDER_WORK = "TB_WMS_TENDER_WORK";
        String TENDER_LIST = "tenderList";
        String TENDER_PROJECT = "tenderProjects";
        String TD_ID = "tdId";
        String TENDER_INITIATION_FORM = "tenderinitiationform";
        String GET_PROJECT_LIST = "getProjectList";
        String GET_ESTIMATED_WORK = "getEstimatedWork";
        String TENDER_ESTIMATED_PROJECT = "tenderEstimatedProject";
        String GET_TENDER_WORK = "getAvailableTenderWork";
        String TND_ID = "tndId";
        String FILTER_TENDER_DATA = "filterTenderData";
        String INITIATION_NO = "initiationNo";
        String INITIAITON_DATE = "initiationDate";
        String TENDER_CATEGORY = "tenderCategory";
        String VENDER_CLASS_ID = "venderClassId";
        String DELETE_TENDER = "deleteTender";
        String INITIATE_TENDER = "initiateTender";
        String TENDER_ID = "tenderId";
        String UPDATE_TENDER = "updateTender";
        String VENDOR_LIST = "vendorList";
        String TENDER_DETAIL_UPDATE = "tenderDetailUpdate";
        String SAVE_UPDATED_TENDER = "saveUpdateTender";
        String TENDER_DETAIL_VALIDN = "tenderDetailValidn";
        String OPEN_ESTIMATION = "openEstimation";
        String TNDR = "TNDR";
        String SHOW_CURRENT_FORM = "showCurrentForm";

        String TB_WMS_SANC_DET = "TB_WMS_SANCTION_DET";
        String TECH_SANCTION = "TS";
        String ADMIN_SANCTION = "AS";
        String FIN_SANCTION = "FS";
        String TB_WMS_WORKESTIMATE_MAS = "TB_WMS_WORKESTIMATE_MAS";
        String WORK_ASSIGNEE_HTML = "/WorkAssignee.html";

        String WORK_ASSIGNEE_FORM = "WorkAssigneeForm";
        String ASSIGNEE_FORM = "assigneeForm";
        String WORK_ORDER_ID = "workOrderId";
        String ASSIGNEE_ID = "assignee";
        String GET_WORK_LIST = "getWorkList";
        String WORK_ORDER_LIST = "workOrderList";
        String WORK_ASSIGNEE_GROUP = "workAssigneeGroup";

        String TB_WMS_MEASUREMENTBOOK_MAST = "TB_WMS_MEASUREMENTBOOK_MAST";
        String TB_WMS_WORKEORDER = "TB_WMS_WORKEORDER";
        String WORK_MB_NO = "WORK_MBNO";
        String WORKOR_NO = "WORKOR_NO";
        String WORKE_ESTIMATE_NO = "WORKE_ESTIMATE_NO";
        String SEARCH_ASSIGNEE = "searchAssignee";
        String TENDER_LOA_LATTER = "tenderLOALatter";
        String OPEN = "O";
        String CLOSED = "C";
        String INPROGRESS = "I";
        String NOT_RECEIVED = "N";

        String GetWorkOrderDetail = "getWorkOrderDetail";
        String FilterMeasurementBookData = "filterMeasurementBookData";
        String CreateMb = "CreateMb";
        String TenderNo = "tenderNo";
        String MeasurementBookForm = "MeasurementBookForm";
        String SaveMbEstimationData = "saveMbEstimationData";
        String AddMeasurementDetail = "AddMeasurementDetail";
        String MeasurementListByworkEstimateId = "measurementListByworkEstimateId";
        String MbDetId = "mbDetId";
        String MeasurementListDetails = "MeasurementListDetails";
        String MbId = "mbId";
        String RateAnalysis = "RateAnalysis";
        String OpenMbMasForm = "openMbMasForm";
        String AddMbEnclosuersForm = "AddMbEnclosuersForm";
        String AddMbOverheadsForm = "AddMbOverheadsForm";
        String MbEnclosuresFileUpload = "mbEnclosuresFileUpload";
        String SaveMbEnclosuers = "saveMbEnclosuers";
        String EditMb = "editMb";
        String VeiwMB = "editMb";
        String MbTaxDetailsForm = "mbTaxDetailsForm";
        String SaveMbTaxDetails = "saveMbTaxDetails";
        String WORK_VIGILANCE_HTML = "/WorkVigilance.html";
        String VIGILANCEDTO_LIST = "vigilanceDtosList";
        String OPEN_WORK_VIGILANCE_FORM = "OpenWorkVigilanceForm";
        String MBSET_IN_GRID = "workVigilanceRes";
        String FILTER_WORK_VIGILANCELIST_DATA = "filterWorkVigilanceListData";
        String REFERENCE_TYPE = "referenceType";
        String REFERENCE_NO = "vigilenceReferenceNo";
        String MEMEO_DATE = "memoDate";
        String INSPECTION_DATE = "inspectionDate";
        String EDIT_WORK_VIGILANCE = "editWorkVigilance";
        String PROJECT_V_ID = "vigilanceId";
        String VIEW_WORK_VIGILAANCE = "viewWorkVigilance";
        String VIEW_RESPONSE_AND_ACTION = "viewResponseAndAction";
        String WORK_VIGILANCE_RESPONSE_FORM = "workVigilanceResponseMemoForm";
        String WORK_VIGILANCE_FILEUPLOAD_TAG = "workVigilanceFileUploadTag";
        String WORK_ESTIMATE_REPORT_HTML = "/WorkEstimateReport.html";
        String WORKS_NAME = "worksName";
        String PROJ_LIST = "projList";
        String ADD_RA = "addRaBill";
        String ADD_MB = "addMb";
        String WORKTYPE = "workType";
        String RACODE_MB = "racodeMb";

        String SANCTION_NUMBER = "sanctionNumber";
        String VIEW_WORK_REPORT = "viewWorkReport";
        String GET_ESTIMATE_ABSTRACT_SHEET = "getWorkEstimateAbstractSheet";
        String REPORT_TYPE = "reportType";
        String WORK_ID = "workId";
        String WORK_REPORT_ABSTRACT_SHEET = "WorkReportAbstractSheet";
        String WORK_REPORT_MEASUREMENT_SHEET = "WorkReportMeasurementSheet";
        String WORK_REPORT_RATE_ANALYSIS = "WorkReportRateAnalysis";
        String WORK_ORDER_GENERATION_HTML = "/PwWorkOrderGeneration.html";
        String WORK_ORDER_DTO_LIST = "workOrderDtosList";
        String FILTER_WORK_ORDERLIST_DATA = "filterWorkOrderListData";
        String WORK_ORDER_NO = "workOrderNo";
        String WORK_ORDER_DATE = "workOrderDate";
        String WORK_VENDOR_NAME = "vendorName";
        String WORK_AGREE_FROM_DATE = "agreementFormDate";
        String WORK_AGREE_TO_DATE = "agreementToDate";
        String SHOW_WORK_ORDER_GENERATION = "ShowWorkOrderGeneration";
        String GET_ALL_TENDER_DETAILS = "getAllTenderDetails";
        String WORK_ORDER_TENDOR_DETAILS = "workOrderTendorDetails";
        String EDIT_VIEW_WORK_ORDER_GENERATION = "editViewWorkOrderGeneration";
        String PRINT_WORK_ORDER_GENERATION = "printWorkOrderGeneration";
        String WORK_ORDER_GENERATION_PRINTING = "WorkOrderGenerationPrinting";
        String WORK_ORDER_GENERATION_FILE_UPLOAD = "workOrderGenerationFileUpload";
        String ABOVE = "above";
        String BELOW = "below";
        String WORK_ESTIMATE_APPROVAL = "WorkEstimateApproval";
        String Measurement_Book_Approval="MeasurementBookApproval";
        String GET_WORK_ABSTRACTSHEET = "getWorkAbstractSheet";
        String WORKS_PARENT_ORGID = "worksParentOrgId";
        String EDIT_WORK_ESTIMATE = "editWorkEstimate";
        String SHOWAPPROVAL_CURRENTFORM = "showApprovalCurrentForm";
        String SHOW_ESTIMATE_CURRENTFORM = "showEstimateCurrentForm";
        String WOA = "WOA";
        String FLAG_T = "T";
        String GETWORKS_LIST = "getWorksList";
        String MBA = "MBA";
        String WORK_MB_APPROVAL = "WorkMBApproval";
        String WORK_VIGILANCE_ABSTRACT = "workVigilanceAbst";

        String WORKMB_VIEW_BACK = "workmbViewBack";
        String GET_WORKMB_ABSTRACT_SHEET = "getWorkMBAbstractSheet";
        String WORK_MB_ID = "workMbId";
        String WORK_MB_ABSTRACT_SHEETFORM = "workMbAbstractSheetForm";
        String CONTRACT_VARIATION_APPROVAL = "contractVariationApproval";
        String VIEW_WORK_CONTRACT_VARIATION = "viewWorkContractVariation";
        String CONTRACT_ID = "contId";
        String CVA = "CVA";
        String PROJECT_REGISETR_REPORT = "ProjectRegisterReport";
        String GET_WORK_PROJECT_REGISTER_REPORT = "getWorkProjectRegisterReport";
        String PROJECT_REGISTER_REPORT_SHEET = "ProjectRegisterReportSheet";
        String APPROVAL_LETTER_PRINT = "ApprovalLetterPrint";
        String TECHNICAL_APPROVAL_REPORT = "TechnicalApprovalReport";
        String DEDUCTION_SUMMARY = "DeductionSummary";
        String VIEW_DEDUCTION_REPORT = "viewDeductionReport";
        String DEDUCTION_REPORT = "DeductionReport";
        String TENDER_GENERATED = "Tender Generated";
        String WCV = "WCV";
        String BUDGET_WORKS = "BudgetWorks";
        String VIEW_BUDGET_REPORT = "viewBudgetReport";
        String BUDGET_WORKS_REPORT = "BudgetWorksReport";
        String DEPOSIT_REGISTER = "DepositRegister";
        String VIEW_DEPOSIT_REGISTER_REPORT = "viewDepositRegisterReport";
        String DEPOSIT_REGISTER_REPORT = "DepositRegisterReport";
        String EXCEPTION_IN_FINANCIAL_YEAR_DETAIL = "Exception while getting financial year Details :";
        String WORKS_MANAGEMENT_PROCESS = "works-management";
        String LOA = "LOA";
        String TB_WMS_RABILL = "TB_WMS_RABILL";
        String RA_CODE = "RA_CODE";
        String RA_SERIAL_NO = "RA_SERIALNO";
        String PAYMENT_ORDER = "paymentOrder";
        String SHOW_APPROVAL_CURRENT_FORM = "showApprovalCurrentForm";
        String INITIATOR = "Initiator";
        String SAVE_SANCTION_DETAILS = "saveSanctionDetails";
        String WORK_STATUS = "workStatus";
        String WORK_SANC_NO = "workSancNo";
        String GET_TERMS_AND_CONDITION = "getTermsAndCondition";
        String CHECK_FLAG = "checkStausApproval";
        String NOTICE_INVITING_TENDER = "noticeInvitingTender";
        String PRE_QUALIFICATION_DOCUMENT = "preQualificationDocument";
        String FORM_A = "printFormA";
        String RABILL = "RA BILL";
        String SendRAbill="Send for RA bill";
        String SendForApproval="Send For Approval";
        String MN = "MN";
        String contractNo = "contractNo";
        String directFlag = "directFlag";
        String mbDirectMeasureDetailsForm = "mbDirectMeasureDetailsForm";
        String mbTaxDetailsForm = "mbTaxDetailsForm";
        String openMbNonSorForm = "openMbNonSorForm";
        String MbNonSorForm = "MbNonSorForm";
        String saveMbNonSorItems = "saveMbNonSorItems";
        String openMBEstimateForm = "openMBEstimateForm";
        String getMbAmountSheet = "getMbAmountSheet";
        String Hundred_Per = "100";
        String deviation = "deviation";
        String mbAmount = "mbAmount";
        String estimateAmount = "estimateAmount";
        String estimateAcessAmount = "estimateAcessAmount";
        String mbConsumed = "mbConsumed";
        String itemCode = "itemCode";
        String updateRaBillStatus = "updateRaBillStatus";
        String getUploadedImage = "getUploadedImage";
        String schDetId = "schDetId";
        String UPDATED_BY = "updatedBy";
        String schDActive = "schDActive";
        String mileStoneFlag = "mileStoneFlag";
        String schId = "schId";
        String WORKCODE = "workcode";
        String WORK_STARTDATE = "workStartDate";
        String WORK_ENDDATE = "workEndDate";
        String WORK_TYPE = "workType";
        String WORK_PROJ_PHASE = "workProjPhase";
        String WORK_IDLIST = "workIdList";
        String RACODE_UI = "workMb";
        String RACODE = "racode";
        String COMPLETION_NO = "completionNo";
        String ORGID = "orgid";
        String WORK_SAN_BY = "workSancBy";
        String WORK_DESIGN_BY = "workDesignBy";
        String WORK_SAN_ID = "workSancId";
        String WORK_ESTIM_ACTIVE = "workEstimActive";
        String WORKIDLIST = "workIDList";
        String WORK_ESTIM_FLAG = "workEstimFlag";
        String MA_ID = "maId";
        String ACTIVE = "active";
        String REMOVED_WORKID_LIST = "removeWorkIdList";
        String TOTAL_AMT = "totalAmount";
        String MEASURE_WORK_ID = "measurementWorkId";
        String WORK_ESTIM_ID = "workEstimateId";
        String UTL_QUANTITY = "utlQuantity";
        String MB_NO = "mbNumber";
        String WORKESTIMID = "workEstemateId";
        String WORK_ESTIM_AMT = "workEstimAmount";
        String EMP_ID = "empId";
        String CURRENT_DATE = "CURRENT_DATE";
        String CONTRACTID = "contractId";
        String WORK_ESTIM_TYPE = "workEstimateType";
        String WORK_ESTIM_QNTY_UTL = "workEstimQuantityUtl";
        String WORK_ESTIM_AMT_UTL = "workEstimAmountUtl";
        String MEASUREMENT_ACTIVE = "meMentActive";
        String MEASUREMENT_ID = "meMentId";
        String NOS_UTL = "nosUtilize";
        String REMOVED_IDS = "removeIds";
        String FOUR_PERCENTILE = "%04d";
        String BIG_ZERO = "0.0";
        String MB = "MB";
        String FW_ARROW = " ==> ";
        String SRM = "SRM";
        String STAT_OPEN = "Open";
        String WORK_MB_APPROVE = "WorkMBApproval.html";
        String STAT_CLOSED = "Closed";
        String STAT_INPROGRESS = "Inprogress";
        String THREE_PERCENTILE = "%03d";
        String CALCULATED = "Calculated";
        String DIRECT = "Direct";
        String FORMULA = "Formula";
        String NonSOR = "Non-SOR";
        String DirectSOR = "Direct-SOR";
        String SOR_ADDITIONAL = "Sor-Additional";
        String MS = "MS";
        String NONSOR_ADDITIONAL = "Non-Sor-Additional";
        String WO = "WO";
        String CW = "CW";
        String CNT = "CNT";
        String CONTRACT_DETAILS = "Contract details fetch";
        String VENDOR_ID = "venderId";
        String FETCH_WORK_ORDER = "Fetch work order details";
        String FETCH_CONTRACT_DETAILS = "Fetch contract details based on Loa Number";
        String REF_ID = "referenceId";
        String GET_COMPLETION_DATA = "getAllCompletionData";
        String COMPLETION_FORM = "completionCertificateForm";
        String GET_COMPLETIONDATA = "getCompletionCertificateData";
        String GET_ASSET_DETAIL = "getAssetDetails";
        String ASSET_SERIAL_NO = "astSerialNo";
        String WRT = "WRT";
        String GET_WORK_DETAILS = "getWorkDetails";
        String COMPLETIONFORM = "completionForm";
        String GET_COMPLETIONFORM = "getCompletionForm";
        String PRINT_CONTRACT_DET = "printContractDetails";
        String CONTRACT_PRINT = "contractPrint";
        String CONTRACT_TIME = "contractTimeVariationUpload";
        String UPDATE_CONTRACT_PERIOD = "updateContractPeriod";
        String WORKNAME_BYPROJ = "worksNameByProjId";
        String CHECK_PROJ = "checkForProjId";
        String CHECK_MILESTONE = "checkMilestone";
        String EDIT_MILESTONE = "editMilestone";
        String FORM_MODE = "formMode";
        String ADD_FINMILESTONE = "AddFinancialMilestone";
        String GET_MILESTONES = "getAllMileStoneData";
        String AND_MODE = "&mode=";
        String REDIRECT_WORKESTIMATE = "redirect:/WorkEstimate.html?editEstimation=&workId=";
        String MILESTONES_PROGRESS = "milestoneProgress";
        String MILESTONE_ID = "mileId";
        String PROGRESS_FORM = "ProgressForm";
        String ADD_MILESTONES_PROGRESS = "AddMilestoneProgress";
        String ENTRY_DELETE = "doEntryDeletion";
        String ID = "id";
        String ADD_PHY_MILESTONES = "AddPhysicalMilestone";
        String GET_PROJPROGRESS = "getProjectProgressReportData";
        String PROJ_PROGRESS = "projectProgress";
        String CREATE_RA = "CreateRaBill";
        String OTHER_DEDUCTION_TAX = "Other Deduction Tax";
        String WITHHELD_TAX = "Withheld Deduction Tax";
        String VALIDATE_MB = "validateMbForRa";
        String REDIRECT_RABILL = "redirect:/raBillGeneration.html";
        String PAY_ORDER = "paymentOrder";
        String RABILL_FORM = "RaBillForm";
        String RA_ID = "raId";
        String EDIT_RA = "editRa";
        String BILL_GEN = "Bill Generated";
        String SEND_FOR_APPROVAL = "Send For Approval";
        String SEARCH_RA = "searchRaBill";
        String SOR_DET_CPD = "ScheduleOfRateDetCpdMode";
        String OR = " | ";
        String DUPLICATE_COMBINATION = "Duplicate Combination :";
        String SOR_CHAPTERWISE = "sorFormChapterWise";
        String CHAPTERID = "chapterId";
        String GET_ALLPROJVIEW = "getAllProjectView";
        String SM = "SM";
        String REDIRECT_PROJMASTER = "redirect:/WmsProjectMaster.html?viewProjectMasterData=&projId=";
        String SHOW_SCHEMEVIEW = "showSchemeViewCurrentForm";
        String VIEW_SCHEME = "ViewSchemeMaster";
        String VEC = "VEC";
        String UTS = "UTS";
        String VALUE_TYPELIST = "valueTypeList";
        String VALUE_TYPEAMOUNT = "valueTypeAmount";
        String LOA_MODE = "loaMode";
        String TND_WORKID = "tndAndWorkId";
        String PRINT_NOTICE = "printNoticeInvitingTender";
        String GEN_PRINT_LOA = "generateAndPrintLOA";
        String PER = "PER";
        String AMT = "AMT";
        String GEN_RATETYPE = "GeneralRateType_";
        String EXPORT_EXCEL = "exportRateExcelData";
        String OPEN_SCHEME_MASTER = "openSchemeMasterForm";
        String VIEW_PROJ = "ViewProjectMaster";
        String SHOW_CURRENTPROJ = "showProjectCurrentForm";
        String GET_CONTID = "getContractId";
        String VIEW_PROJMASDATA = "viewProjectMasterData";
        String REDIRECT_SCHEME = "redirect:/WmsSchemeMaster.html?AddSchemeMaster";
        String EMPLOYEE = "employee";
        String GET_REGISTER = "getRegister";
        String WORK_REG_ENTRY = "WorkRegisterEntry";
        String PRINT_WORK_REG = "printWorkCompletionRegister";
        String SEND_BACK = "SEND_BACK";
        String CONT_MODE = "contractMode";
        String REDIRECT_CONTRACTVARIATION = "redirect:/ContractVariation.html?viewContractVariation=&contId=";
        String ESTMATE_TYPE = "&estimateType=";
        String CONTRACTNO = "contractAgreementNo";
        String CONTRACTDATE = "contractAgreementDate";
        String ESTIM_TYPE = "estimateType";
        String VALUE = "value";
        String WORK_CONTRACT_VARIATION = "WorkContractVariationApproval.html";
        String REDIRECTTO_APPROVALLETTER = "redirect:/ApprovalLetterPrint.html?viewWorkReport=&projId=";
        String AND_WORKID = "&workId=";
        String AND_DEPTID = "&deptId=";
        String AND_WORKTYP = "&workTyp=";
        String AND_WORKSAN_NO = "&workSancNo=";
        String REDIRECTTO_WORKESTIMATE = "redirect:/WorkEstimate.html?editEstimation=&workId=";
        String REDIRECTTO_MBBOOK = "redirect:/MeasurementBook.html?editMb=&workId=";
        String REDIRECTTO_PURCHASE_REQ_1= "redirect:/PurchaseRequisition.html?viewPurchaseRequisition=&prId=";
        String REDIRECTTO_DISPOSAL_REQ_1= "redirect:/DisposalOfStock.html?viewDisposalOfStock=&expiryId=";        
        String REDIRECTTO_PURCHASE_REQ_2= "&redirectTender=TenderInitiation.html";
        String REDIRECTTO_WORKESTIMATE_REPORT = "redirect:/WorkEstimateReport.html?getWorkEstimateAbstractSheet=&projId=";
        String AND_REPORTTYPE = "&reportType=";
        String TECH_SANC_APPROVE = "Technical Sanction Approved";
        String ADMIN_SANC_APPROVE = "Admin Sanction Approved";
        String LABOUR_FORM = "LabourForm";
        String ADDLABOUR_FORM = "AddWorkLabourForm";
        String SAVE_LABOURFORM = "saveLabourFormData";
        String WKC = "WKC";
        String WORKESTIMATE_CPDMODE = "WorkEstimateMasterCpdMode";
        String DIRECT_ESTIMATE = "Direct_Estimate.xlsx";
        String WORK_EST_APPROVAL = "WorkEstimateApproval.html";
        String REDIRECTTO_MB = "redirect:/MeasurementBook.html?editMb=&workId=";
        String REDIRECT_MB = "redirect:/MeasurementBook.html?editMb=&workId=";
        String REDIRECTTO_ADMIN = "redirect:/AdminHome.html";
        String CONTRACT_FROMDATE = "contractFromDate";
        String CONTRACT_TODATE = "contractToDate";
        String GET_WORKNAME = "getWorkName";
        String VIEW_CONTRACTDET = "viewContractDetails";
        String WOG = "WOG";
        String REDIRECT_TOCONTRACT = "redirect:/ContractAgreement.html?form=&contId=";
        String AND_TYPE = "&type=";
        String AND_SHOWFORM = "&showForm=";
        String SHOW_WORKORDER = "showWorkOrderCurrentForm";
        String RM = "RM";
        String WORK_SANNO = "WORK_SACNO";
        String LI = "LI";
        String BILL_AGASINST = "Bill Against MB no - ";
        String WORKORDER_NO = ", Work Order No : ";
        String PERCENT = "PER";
        String PRINT_FORM_B = "printFormB";
        String AMOUNT = "50000000.00";
        String PRINT_FORM_A = "printFormA";
        String FLAG_FORMS = "flagForms";
        String PRINT_FORM_A_AND_B = "printFormAandB";
        String DATE_FORMAT = "dd/MM/yyyy" + " " + "hh:mm";
        String UpdateMbImages = "updateMBImages";
        String UpdateMbImg = "updateMbImages";
        String SaveUploadedMbImg = "saveMbUploadedImage";
        String OpenCheckList = "openCheckList";
        String MCS = "MCS";
        String MBC = "MBC";
        String MB_CHKLIST_FORM = "mbCheckListForm";
        String SAVE_CHECKLIST = "saveCheckListData";
        String FILTER_VENDOR_CLASS = "filterVendorClass";
        String GET_ALL_ITEMS_LIST = "getAllItemsList";
        String ESTIMATED_AMOUNT = "estimatedAmount";
        String SOR_CHAPTER_VAL = "sorChapterVal";
        String SHOW_CURRENT_TENDER_VIEFORM = "showCurrentTenderViewForm";
        String PRINT_FORM_F = "printFormF";
        String CHARGE_APPLICABLE_AT = "Bill";
        String TAX_CODE = "WMS9";
        String PARENT_TAX_CODE = "NA";
        String TAX_CATEGORY = "Deposite";
        String TAX_SUB_CATEGORY = "Short Term Deposits";
        String GET_EMD_AMOUNT = "getEmdAmount";
        String CSR = "CSR";
        String SOURCE_CODE = "sourceCode";
        String SOURCE_NAME = "sourceName";
        String SOURCE_ID = "sourceId";
        String SSF = "SSF";
        String GET_SCHEMEFUND = "getSchemeFundSource";
        String PROJ_STATUS = "projStatus";
        String DEPT_ID = "dpDeptId";
        String GET_MAPDATA = "getMapData";
        String GET_BUDGETHEAD = "getBudgetHeadDetails";
        String FA_YEARID = "faYearId";
        String BUDGET_AMT = "yeBugAmount";
        String TECHNICAL_SANCTION = "Technical Sanction";
        String ADM_SANCTION = "Admin Sanction";
        String CAPITALIZATION_MESSAGE = "Voucher For Capitalization of workCode : ";
        String SAVE_WORK_DEFINATION = "saveWorkDefinition";
        String ACTIONABLE = "Actionable";
        String INFORMATIONAL = "Informational";
        String REDIRECT_REVISEDESTIMATE = "redirect:/WorksRevisedEstimate.html?editViewRevisedEstimate=&contId=";
        String TND_SHORT_CODE = "TEND";
        String SEARCH_DATA = "searchDetails";
        String VIGILANCE_SUMMARY = "SearchWorkVigilance";

        String OPEN_BID_FORM = "openBIDAddForm";
        String OPEN_BID_DETAIL_FORM = "openAddBIDDetailsForm";
        String SAVE_BID_DETAILS = "saveBIDDetails";
        String GET_BID_DETAILS = "getDetailsOfBID";
        String EDIT_BID_FORM = "editBIDForm";
        String OPEN_TENDER_UPDATE_FOR_BID = "openTenderUpdateForm";
        String OPEN_BID_COMPARE_FORM = "openBidCompareForm";
        String OPEN_BID_SEARCH_FORM = "openBIDCompareSearchForm";
        String SEARCH_TENDER = "searchTender";
        String SEARCH_BID_DETAILS = "searchBIdDetByVendorIdandBidId";
        String VPCA = "VPC";
        String LAND = "LAN";
        String Water_Sewarage = "WAS";

    }

    public static interface Property {
        String PROP_DEPT_SHORT_CODE = "AS";
        String PROP_BRMS_MODEL = "FactorMasterModel|ALVMasterModel|PropertyRateMasterModel|PropertyTaxDataModel";
        String PROP_BRMS_MODEL_FOR_INT_CAL = "PropertyRateMasterModel";
        String CHECK_LIST_MODEL = "ChecklistModel";
        String NEW_ASESS = "N";
        String CHN_IN_ASESS = "C";
        String NO_CHN_ASESS = "NC";
        String ASESS_AUT = "A";
        String APPROVAL = "APR";
        String NEW_PROP_REG = "R";
        String DATA_ENTRY_SUITE = "DES";
        String ASSESS = "A";
        String TAX = "T";
        String ASS_AND_TAX = "AT";
        String OWNER = "O";

        String SAS = "SAS";
        String MUT = "MUT";
        String MOS = "MOS";
        String NPR = "NPR";
        String DES = "DES";
        String PNC = "PNC";
        String IBP = "IBP";
        String PP = "PP"; // Partial Payment
        String AP = "AP"; // Advance Payment
        String PAA = "PAA"; // Partial/ advance payment active
        String PAI = "PAI"; // Partial/ advance payment Inactive
        String REBATE = "REBATE";
        String USA = "USA";
        String PTP = "PTP";
        String EARLY_PAY_DISCOUNT = "EPD";
        String SURCHARGE = "SC";
        String PENLTY_AGAINST_MISLEADING_INFROMATION = "PMI";
        String CHEQUE_DISHONR_CHARGES = "CDC";
        String REBATE_DELETE_REMARK = "Rebate Inactive At Authorization Edit";
        String ACG = "ACG";

        String Amalgamation = "AML";
        String Bifurcation = "BIF";

        String ENL = "ENL";
        String MAIN_TABLE = "M";
        String PROVISIONAL_TABLE = "P";

        String MIX_USAGE = "Mix Usage";
        String MIXED_USAGE = "Mixed";
        String EXT = "EXT";
        String VIW = "VIW";
        String DUBLICATE_BILL = "DUB";
        String SKDCL_SERVICES_USED_REFERENCE_ID = "EXT|NDT|DUB|ACG";

        String NDT = "NDT";

        String GET_PROPERTY_DETAILS = "getPropertyDetails";
        String GET_CHECKLIST_AND_CHANGES = "getCheckListAndCharges";
        String GENERATE_NO_DUES_CERTI = "proceed";
        String PROP_NO_DUES_CERTIFICATE_REPORT = "PropertyNoDuesCertificateReport";
        String PROP_NO_DUES_CERTIFICATE_VALIDN = "PropertyNoDuesCertificateValidn";
        String CLR = "CLR";
        String CLEARED = "CLD";
        String DISHONOUR = "DSH";
        Long All = -1l;
        int rate = 10;
        Double MAINTAINCE_CHARGE_ZERO = 0.0;
        int SECOND_LEVEL = 2;
        String BIG_DEC_ZERO = "0.0";
        String PAR = "PAR";
        String REBATE_fOR_PROPERTY = "Rebate";
        Long UserId = 2L;
        String EMC = "EMC";
        String ES = "ES";
        String PROP_NOT_URL = "PropertyNotification.html";
        String ASCL_STATE_CODE = "09";
        String ASCL_ULB_CODE = "196";
        String UNIQUE_PROPERTY_ID = "UNIQUE_PROPERTY_ID";
        String BMC = "BMC";
        String BILL_REV_INACTIVE_COMM = "Inactive at the time of bill revision";
		String BILL_MET_CHNG_SAVE = "execution(* com.abm.mainet.common.audit.service.AuditServiceImpl.saveDataForProperty(..)) && args (appId,serviceId,workflowDecision,orgId,empId,lgIpMac,level)";
		String SAVE_BILL_CHANGE_DATA = "saveBillMethodChangeData";
		String DEMAND_GEN_PENDING="DEMAND_PENDING";
		String GET_PROPERTY_BILLING_METHOD="getPropertyBillingMethod";
		String GET_PROPERTY_FLAT_LIST="getPropertyFlatList";
		String GET_PROPERY_OUTSTANDING="getTotalOutStandingOfPropertyNumber";
		String HALF = "H";
		String ZERO = "Z";
		
        interface serviceShortCode {
            String NCA = "NCA";
            String CIA = "CIA";
        }

        interface TaxCalLevel {
            String PROPERTY = "P";
            String UNIT = "U";
        }

        interface LandType {
            String KPK = "KPK";
            String NZL = "NZL";
            String DIV = "DIV";
            String ENT = "ENT";
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

        interface table {
            String TB_AS_PRO_ASSESMENT_MAST = "TB_AS_PRO_ASSESMENT_MAST";
            String TB_AS_PRO_ASSESMENT_DETAIL = "TB_AS_PRO_ASSESMENT_DETAIL";
            String TB_AS_PRO_ASSESMENT_FACTOR_DTL = "TB_AS_PRO_ASSESMENT_FACTOR_DTL";
            String TB_AS_PRO_ASSESMENT_OWNER_DTL = "TB_AS_PRO_ASSESMENT_OWNER_DTL";
            String TB_AS_PRO_BILL_MAS = "TB_AS_PRO_BILL_MAS";
            String TB_AS_PRO_BILL_DET = "TB_AS_PRO_BILL_DET";
        }

        interface primColumn {
            String PRO_ASS_ID = "PRO_ASS_ID";
            String PRO_ASSD_ID = "PRO_ASSD_ID";
            String PRO_ASSF_ID = "proAssfId";
            String PRO_ASSO_ID = "PRO_ASSO_ID";
            String PRO_BM_IDNO = "proBmIdno";
            String PRO_BILL_DET_ID = "proBdBilldetid";
        }

        interface propPref {
            String BILL = "BILL";
            String RCPT = "RCPT";
            String CAA = "CAA";
            String MONTH = "MON";
            String ASS = "ASS";
            String FCT = "FCT";
            String OWT = "OWT";
            String SNC = "SNC";
            String NTY = "NTY";
            String SVP = "SVP";
            String APP = "APP";
            String TFT = "TFT";
            String LDT = "LDT";
            String MTD = "MTD";
            String USA = "USA";
            String PTP = "PTP";
            String WZB = "WZB";
            String TCL = "TCL";
            String CSC = "CSC";
            String BILL_RECEIPT = "BR";
            String PAR = "PAR";
        }

        interface SPC_NOT_DUE_DATE {
            String GENERATION_DATE = "GN";
            String DISTRIBUTION_DATE = "DN";
        }

        interface AssStatus {
            String ASS_FILED = "SA";
            String SAVE_AS_DRAFT = "SD";
            String SPEC_NOTICE = "SN";
            String OBJECTION = "OB";
            String INSPECTION = "IN";
            String HEARING_PROCESS = "HE";
            String DREAFT = "D";
            String NORMAL = "N";
        }

        interface Floor {
            String GROUND_FLOOR = "G";
            String FIRST = "1";
            String SECOND = "2";
            String THIRD = "3";
            String OTHER = "Other";
            String LAND = "L";
            String BASEMENT = "B";
        }

        interface AuthStatus {
            String NON_AUTH = "N";
            String AUTH = "Y";
            String AUTH_WITH_CHNG = "AC";
        }

        interface TaxCalculationLevel {
            String PROPERTY = "Property";
            String UNIT = "Unit";
            String APPLICATION = "Application";
        }

        interface NoticeNo {
            String TB_AS_NOT_MAS = "tb_as_not_mas";
            String PRO_PROP_NO = "PRO_PROP_NO";
        }

        interface MutationIntimation {
            String CLAIMANT = "C";
            String EXECUTANTS = "E";
            String REV_DEPT = "REV";
        }

        interface validation {
            String HISTORY_MASG = "could ot make entry for";
        }

        String CV = "CV";
        String ARV = "ARV";
        String RV = "RV";
        String MANUAL = "M";
        String NOT_APP = "N";
        String INT_RECAL_YES = "Y";
        String INT_RECAL_NO = "N";
        String SELL = "SL";
        String MANUAL_DESC = "Manual";
        String NOT_APP_DESC = "Not Applicable";
        String MONTHLY = "MI";
        String YEARLY = "YI";
        String ALL = "ALL";
        String PRIMARY_OWN = "P";
        String TB_AS_PRO_ASSE_MAST = "TB_AS_PRO_ASSESMENT_MAST";
        String TB_AS_TRANSFER_MST = "tb_as_transfer_mst";
        String CERTIFICATE_NO = "CERTIFICATE_NO";
        String TB_AS_ASSESMENT_MAST = "tb_as_assesment_mast";
        String MN_ASS_NO = "MN_ASS_NO";
        String PRO_PROP_NO = "PRO_PROP_NO";
        String TB_AS_PRO_BILL_MAS = "TB_AS_PRO_BILL_MAS";
        String PRO_BM_NO = "pro_bm_no";
        String ALV = "ALV";
        String FACT_LOG = "Error While fetching factor rate for property";
        String SDDR_LOG = "Error While fetching SDDR rate for property";
        String RATE_LOG = "Error While fetching tax master rate for property";
        String SO = "SO"; // single owner
        String JO = "JO"; // joint owner
        String PROP_NO_CHAR = "CG";
        String Tripe_Zero = "000";
        String BILL_DUE_DATE = "7";
        String LAND = "LAND";
        String Vaccant_Land = "VCL"; // Vaccant Land
        String CD = "CD";
        String TB_AS_NO_DUES_PROP_DETAILS = "TB_AS_NO_DUES_PROP_DETAILS";
        String AUTHORIZED = "AUTHORIZED";
		String UNAUTHORIZED = "UNAUTHORIZED";
        String OUTWARD_NO = "OUTWARD_NO";

    }

    public static interface BBPS_API{
    	String ERROR_CODE_500 = "500";
    	String ERROR_CODE_500_MSG = "Internal Server Error";
    	String ERROR_CODE_400 = "400";
    	String ERROR_CODE_400_MSG = "Bad Request";
    	String SUCCESS_CODE_200 = "200";
    	String SUCCESS_CODE_200_MSG = "OK";
    	String ERROR_AMOUNT_CODE = "101";
		String ERROR_AMOUNT_MSG = "Amount should be greater or equal to 10";
		String EMPTY_PROP_CODE = "102";
		String EMPTY_PROP_MSG = "Property Number Connot be null";
		String EMPTY_FLAT_CODE = "103";
		String EMPTY_FLAT_MSG = "Flat Number Connot be null";
		String INVALID_PROP_CODE = "104";
		String INVALID_PROP_MSG = "Invalid Property Number";
		String NOT_FOUND_CODE = "404";
		String NOT_FOUND_MSG = "No record found";
		String TX_REF_CODE = "105";
		String TX_REF_MSG = "Transaction ref Connot be null";
		String SUCCESSFUL = "Successful";
		String SUCCESS = "success";
		String FAIL = "fail";
    	
    }
    
    interface RTISERVICE {
        String RTIFIRSTAPPEAL = "RFA";
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
        String GET_RTI_CHECKLIST_AND_CHARGE = "getRtiCheckListAndCharge";
        String GET_LOCATION_BY_DEPARTMENT = "getLocationByDepartment";
        String REMARK_DETAILS_BY_ACTION = "remarkDetailsByAction";
        String GET_EMP_NAME = "getEmpName";
        String FILE_COUNT_UPLOAD = "fileCountUpload";
        String RTI_FILE_UPLOAD = "RtiFileUpload";
        String SAVE_AND_MEDIA_CHARGE = "saveAndMediaCharge";
        String PRINT_FORME_REPORT = "PrintFormEReport";
        String SAVE_DISPATCH = "saveDispatch";
        String SERVICE_EVENT_NAME = "PIO Response";
        String RTISECONDAPPEALSERVICE = "RSA";
        String RTIEDITABLELOI = "saveEditableLOI";
        String RTI_MODEL_N_SAMPLE_SHORTCODE = "MS";
        String DISPATCH_NO = "DISPATCH_NO";
        String FIRST_APPEAL_REMINDER = "FAR";
        String RTI_TOKEN_NO = "RTI_TOKEN_NO";
        String CHECK_DUPLICATE_EMPLOYEE = "checkDuplicateEmpName";
        String TASK_STATUS_SECONDAPPEAL = "SECONDAPPEAL";
        String TASK_STATUS_SCNDAPPL_COMP = "SCNDAPPPL_CMPLT";

    }

    interface AssetManagement {
        String ASSET_MANAGEMENT = "AST";
        String SAVE_ASSET_INFORMATION = "saveAssetInformation";
        String SAVE_ASSET_CLASSIFICATION = "saveAssetClassification";
        String SAVE_ASSET_INSURANCE_DETAILS = "saveAssetInsuranceDetails";
        String SAVE_ASSET_LEASING_COMPANY = "saveLeasingCompany";
        String SAVE_ASSET_PURCHASER_INFORMATION = "saveAssetPurchaserInformation";
        String SAVE_ASSET_SERVICE_INFORMATION = "saveAssetServiceInformation";
        String SAVE_ASSET_CHART_OF_DEPRECIATION_MASTER = "saveAssetChartOfDepreciationMaster";
        String ASSET_INFORMATION_URL = "/AssetInformation.html";
        String ASSET_CLASSIFICATION_URL = "/AssetClassification.html";
        String ASSET_INSURANCE_DETAILS_URL = "/AssetInsuranceDetail.html";
        String ASSET_LEASING_COMPANY_URL = "/AssetLeasingCompany.html";
        String ASSET_PURCHASER_INFORMATION_URL = "/AssetPurchaserInformation.html";
        String ASSET_SERVICE_INFORMATION_URL = "/AssetServiceInformation.html";
        String ASSET_CHART_OF_DEPRECIATION_MASTER_URL = "/ChartOfDepreciationMaster.html";
        String ASSET_REGISTRATION_URL = "/AssetRegistration.html";
        String ASSET_REVALUATION_URL = "AssetRevaluation.html";
        String ASSET_REVALUTATION_PATH = "/" + ASSET_REVALUATION_URL;
        String EDIT = "E";
        String CREATE = "C";
        String NEW = "NEW";
        String UPDATE = "UPD";
        String VIEW = "V";
        String CHART_OF_DEPRECIATION_MASTER_GROUP_ID = "groupId";
        String CHART_OF_DEPRECIATION_MASTER_NAME = "name";
        String CHART_OF_DEPRECIATION_MASTER_DEPRECIATION_ID = "depreciationMasterId";
        String MST_DTO_LIST = "mstDtoList";
        String CDM_FORM = "ChartOfDepreciationMasterForm";
        String GROUP_ID = "groupId";
        String GROUP_NAME = "name";
        String ASSET_CLASS = "assetClass";
        String ACCOUNT_CODE = "accountCode";
        String FREQUENCY = "frequency";
        String FILTER_CDM_DATA = "filterCDMListData";
        String TYPE = "type";
        String ADD_CHART_OF_DEPRECIATION_MASTER = "AddChartOfDepreciationMaster";
        String FREQUENCY_PREFIX = "PRF";
        String ASSET_CLASS_PREFIX = "IMO";
        String ACCOUNT_CODE_PREFIX = "AQM";
        String DEPRECIATION_KEY_PREFIX = "DPK";
        String AST_REG_FORM = "AssetRegistrationForm";
        String AST_ID = "assetId";
        String REVAL_MODE = "revalMode";
        String LEASE = "Lease";
        String LINEAR = "Linear";
        String ASSET_FUNCTIONAL_LOCATION_URL = "/AssetFunctionalLocation.html";
        String ASSET_FUNCTIONAL_LOCATION_SEARCH = "searchfuncLocCode";
        String FILTER_FUNC_Code = "filterFuncCode";
        String ASSET_FUNCTIONAL_LOCATION_FORM = "AssetFunctionalLocationForm";
        String NONE = "None";
        String ASSET_CLASSIFICATION = "CLS";
        String ASSET_TRANSFER_URL = "AssetTransfer.html";
        String ASSET_TRANSFER_PATH = "/" + ASSET_TRANSFER_URL;
        String ASSET_DETAILS_REPORT = "AssetDetailsReport.html";
        String ASSET_RETIRE_URL = "AssetRetirement.html";
        String ASSET_RETIRE_PATH = "/" + ASSET_RETIRE_URL;
        String APPROVAL_STATUS_PENDING = "P";
        String APPROVAL_STATUS_APPROVED = "A";
        String APPROVAL_STATUS_DRAFT = "D";
        String ASSET_REGISTER_UPLOAD = "AssetRegisterUpload.html";
        String ASSET_REGISTER_UPLOAD_DTO = "AssetRegisterUpload";
        String EXCEL_TEMPLATE = "ExcelTemplateData";
        String ASSET_LIMIT_ERROR_CODE = "limitError";
        String ASSETCODE = "AST";
        String ASSET_REG_SERV_SHORTCODE = "AR";
        String TRF_APPR_SERV_SHORTCODE = "ATA";
        String TRF_RETIRE_SERV_SHORTCODE = "ARA";
        String TRF_REVAL_SERV_SHORTCODE = "ARVA";
        String AST_REQ_SERV_SHORTCODE = "ARS";
        String AST_INVENTORY_SERV_SHORTCODE = "AIP";
        String SHOW_AST_CLASS_Page = "showAstClsPage";
        String ASR = "ASR"; // asset Repoerts
        String DEPRECIATION_REPORT = "DepreciationReport.html";
        String DEPR_ACCT_TMPLT_PRF = "ADE";
        String REVAL_INC_TMPLT_PRF = "RCE";
        String REVAL_DEC_TMPLT_PRF = "RDE";
        String REVAL_IMP_TYPE_PRF = "IMT";
        String IMP_TYPE_CAPITAL = "CPL";
        String AST_SALE_ACCT_TMPLT_PRF = "ASE";
        String AST_PROFIT_ACCT_TMPLT_PRF = "APE";
        String LOSS_ON_DISPOSAL_OF_FIXED_ASSET = "ALE";
        String AST_DISPOSAL_ACCT_TMPLT_PRF = "AD";
        String PAY_MODE_PRF = "PAY";
        String PAY_MODE_TRANSFER = "T";

        String CHECK_TRANSACTION_YES = "Y";
        String CHECK_TRANSACTION_NO = "N";

        String SHOW_AST_INFO_Page = "showAstInfoPage";
        String SHOW_AST_PRUCH_Page = "showAstPurchPage";
        String SHOW_AST_REAL_ESTATE_Page = "showAstRealEstate";
        String SHOW_AST_LEASE_Page = "showAstLeasePage";
        String SHOW_AST_SERVICE_Page = "showAstSerPage";
        String SHOW_AST_INSU_Page = "showAstInsuPage";
        String SHOW_AST_DEPRE_CHART_Page = "showAstDepreChartPage";
        String SHOW_AST_LINEAR_Page = "showAstLinePage";
        String APPROVAL_STATUS_REJECTED = "R";

        String ASSET_STATUS_PREFIX = "AST";
        String ASSET_DISPOSAL_CODE = "DSP";
        String ASSET_DISPOSAL_METHOD_CODE = "DCM";
        String DISPOSAL_SALE_CODE = "SALE";

        String WF_TXN_IDEN_REVAL = "RVL";
        String WF_TXN_IDEN_RETIRE = "RTR";
        String WF_TXN_IDEN_TRF = "TRF";
        String ASSET_CAP_SERV_SHORTCODE = "AC";

        // asset account integration
        String TAX_PREFIX = "TXN";
        String SALE_PROCEEDS_FROM_ASSETS = "SPA";
        String ASSET_WRITE_OFF = "AWE";

        String SHOW_AST_INSU_Page_DATATABLE = "showAstInsuPageDataTable";

        String AST_INFO_URL_CODE = "INFO";
        String AST_CLASS_URL_CODE = "CLSL";
        String AST_PURCH_URL_CODE = "PURCH";
        String AST_REAL_ESTATE_URL_CODE = "REEST";
        String AST_LEASE_URL_CODE = "LEASE";
        String AST_SERVICE_URL_CODE = "SERVI";
        String AST_INSU_URL_CODE = "INSUR";
        String AST_DEPRE_CHART_URL_CODE = "DEPCHAT";
        String AST_LINEAR_URL_CODE = "LINEAR";

        public enum ValuationType {
            DPR("DPR"), NEW("NEW"), RETIRE("RET"), REVAL("RVL"), IMPROVEMENT("IMP");

            private String value;

            private ValuationType() {
            }

            private ValuationType(String value) {
                this.value = value;
            }

            public String getValue() {
                return value;
            }
        }

        String ASSET_SCHEDULER_MAS_URL = "/AssetSchedulerMaster.html";
        String ASSET_ENTRY_CODE_OF_ACCOUNT = "INS";
        String ASSET_ENTRY_CODE_OF_ACCOUNT_EXTERNAL = "EXS";
        String PAYMENT = "P";
        String RECEIPT = "R";
        String SYSTEM = "System";
        /* works integration */
        String THROW_EX = "data not found";

        String ASSET_REQUISITION_URL = "AssetRequisition.html";
        String ASSET_ANNUAL_INVENTORY_URL = "AssetAnnualPlan.html";

        interface STATUS {
            String SUBMITTED = "SUBMITTED";
            String IN_PROCESS = "IN-PROCESS";
            String APPROVED = "APPROVED";
            String REJECTED = "REJECTED";
        }

    }

    interface ITAssetManagement {
        String MODULE_DEPT_KEY = "moduleDeptCode";
        String SERVICE_CODE_KEY = "serviceCodeModuleWise";

        String IT_ASSET_CODE = "IAST";
        String IT_ASSET_INFORMATION_URL = "ITAssetInformation.html";
        String IT_ASSET_REG_SERVICE_CODE = "IAR";
        String IT_AST_INVENTORY_SERVICE_CODE = "IAIP";
        String IT_AST_REQ_SERVICE_CODE = "IARS";
        String IT_TRF_SERVICE_CODE = "IATA";
        String IT_RETIRE_APPR_SERVICE_CODE = "IARA";

        String IT_ASSET_DISPOSAL_METHOD_CODE = "ICM";

    }

    interface SolidWasteManagement {
        int LEVEL_ONE = 1;
        int LEVEL_TWO = 2;
        String PROJECT_ADD = "P";
        String ADD = "A";
        String EDIT = "E";
        String CREATE = "C";
        String VIEW = "V";
        String DELETE = "D";
        String SHORT_CODE = "SWM";
        String ROUTE_COLLECTION = "RouteCollection_";
        String VMC = "VMC";
        String CHECKLIST_SWMRATEMASTER = "ChecklistModel|SWMRateMaster";
        String CHECK_LIST_MODEL = "ChecklistModel";
        String SWMRATEMASTER_MODEL = "SWMRateMaster";
        String DEPT_WASTE_COLLECTOR_SHORT_CODE = "CDG";
        // String TAX_CODE_WASTE_COLLECTOR_SHORT_CODE = "SWM1";
        String SW_TABLE = "SW";
        String GARBAGE_COLLECTOR_TABLE = "TB_SW_CONSTDEMO_GARBAGECOLL";
        // solid_waste_management_vehicle

        String VEHICLESCHEDULING_FORM = "VehicleSchedulingForm";
        String VEHICLESCHEDULING_GRID = "VehicleScheduleGrid";
        String VEHICLESCHEDULING_SEARCH = "VehicleSchedulingSearch";
        String ADD_VEHICLESCHEDULING = "AddVehicleScheduling";
        String EDIT_VEHICLESCHEDULING = "EditVehicleScheduling";
        String VIEW_VEHICLESCHEDULING = "ViewVehicleScheduling";
        String DELETE_VEHICLESCHEDULING = "DeleteVehicleScheduling";
        String SEARCH_VEHICLESCHEDULING = "SearchVehicleScheduling";
        String COLLECTION_IMPORT_EXCEPTION = "Exception while Importing Root-Collection Master Data :";
        String COLLECTION_EXPORT_EXCEPTION = "Exception while Exporting Root-Collection Master Data :";
        String WASTETYPE = "WTY";
        String WETWASTE = "WW";
        String PAS = "PAS";
        String SWA = "SWA";

        interface SaveMode {
            String EDIT = "E";
            String CREATE = "C";
            String VIEW = "V";
            String DELETE = "D";
            String SEARCH = "S";
        }

        interface SolidWasteManagementRest {
            String APP_DTO_EMPTY = "ApplicantDetailDTO can not be empty, ";
            String TITLE_EMPTY = "Title can not be empty, ";
            String FIRST_NAME = "First Name can not be empty, ";
            String LAST_NAME = "Last NAme can not be empty, ";
            String GEN_EMPTY = "Gender can not be empty, ";
            String MOBILE_NO = "Mobile No. can not be empty, ";
            String ADDRESS = "Address can not be empty, ";
            String VILLAGE_STUB = "Village Town Stub can not be empty, ";
            String PIN_NO = "Pin Code can not be empty, ";
            String WasteCollector = "WasteCollector can not be empty, ";
            String UPLOAD_DOCS = "Please Upload Madatory Documents";
            String PAY_AMOUNTS = "Pay Amount can not be empty, ";
            String SERVICE_EMPTY = "Service Id can not be empty, ";
            String DEPT_EMPTY = "Dept Id can not be empty, ";
            String COLLECTOR_DTO_EMPTY = "CollectorReqDTO can not be empty, ";
            String CAPACITY = "Waste Capacity can not be empty, ";
            String BLDG_PERMISSION = "Building Permission can not be empty, ";
            String LOCATION = "Location can not be empty, ";
            String CONSTRUCTION_SITE = "Address Of Construction Site can not be empty, ";
            String VEHICLE = "Vehicle can not be empty, ";
            String COMPLAINT_NO = "Complaint Number can not be empty, ";
            String NO_OF_TRIP = "No of Trip can not be empty, ";
            String REQUEST_DTO_EMPTY = "Request DTO can not be empty, ";
            String REQUEST_DTO_MODEL_NAME = "Request DTO model name can not be empty, ";
        }

        // solid_waste_management_sanitary_staff_scheduling

        String SANITARYSTAFFSCHEDULING_FORM = "SanitaryStaffSchedulingForm";
        String ADD_SANITARYSTAFFSCHEDULING = "AddSanitaryStaffScheduling";
        String EDIT_SANITARYSTAFFSCHEDULING = "EditSanitaryStaffScheduling";
        String VIEW_SANITARYSTAFFSCHEDULING = "ViewSanitaryStaffScheduling";
        String DELETE_SANITARYSTAFFSCHEDULING = "DeleteSanitaryStaffScheduling";
        String SEARCH_SANITARYSTAFFSCHEDULING = "SearchSanitaryStaffScheduling";

        // Trip Sheet Master
        String ADD_TRIP_SHEET = "AddTripSheetMaster";
        String TRIP_SHEET_FORM = "TripSheetMasterForm";
        String EDIT_TRIP_SHEET = "EditTripSheetMaster";
        String VIEW_TRIP_SHEET = "ViewTripSheetMaster";
        String SEARCH_TRIP_SHEET = "SearchTripSheetMaster";
        String TRIP_SHEET = "TripSheetMaster";
        String TRIP_SHEET_SEARCH = "TripSheetMasterSearch";

        String SWM_APPROVAL = "SendForApproval";

        // Segregation Summary
        String ADD_SEGREGATION = "AddSegregation";
        String SEGREGATION_FORM = "SegregationForm";
        String EDIT_SEGREGATION = "EditSegregation";
        String VIEW_SEGREGATION = "ViewSegregation";
        String SEARCH_SEGREGATION = "SearchSegregation";
        String SEGREGATION = "Segregation";
        String SEGREGATION_SEARCH = "SegregationSearch";

        // Sanitation Staff Target
        String ADD_SANITATION_STAFF_TARGET = "AddSanitationStaffTarget";
        String SANITATION_STAFF_TARGET_FORM = "SanitationStaffTargetForm";
        String SEARCH_SANITATION_STAFF_TARGET = "SearchSanitationStaffTarget";
        String EDIT_SANITATION_STAFF_TARGET = "EditSanitationStaffTarget";
        String DELETE_SANITATION_STAFF_TARGET = "DeleteSanitationStaffTarget";
        String SELECT_SANITATION_STAFF_TARGET = "SanitationStaffTargetSearchSelect";
        String SELECT_TARGET = "sTargetType";
        String DISPOSAL_SITE = "DISPOSAL";

        // SMS AND EMAIL URL
        String CONSUMER_REG = "SolidWasteConsumerRegistration.html";
        String USER_CHARGE = "SolidWasteUserCharge.html";
        String FINE_CHARGE = "SolidWasteFineCharge.html";

        // SERVICE CODE
        String SOLID_WASTE_FINE_CHARGED = "SWFC";
        String SOLID_WASTE_USER_CHARGED = "SWUC";

        String SURVEY_DOC = "Survey";
        String GEO_TAG_DOC = "GEO_TAG";

    }

    interface SOLID_WATSE_FLOWTYPE {

        String SOLID_WASTE_TYPE_DTO_COD_ID_OPER_LEVEL = "populationMasterDTO.codDwzid";
        String SANITAION_MAS_WARD_ZONE_LEVEL = "sanitationMasterDTO.codWard";
        String ROUTE_MAS_TYPE_DTO_COD_ID_OPER_LEVEL = "RouteDetailsDTO.codWard";
        String VENDOR_CONTRACT_MAPPING_WARD_ZONE_LEVEL = "VendorContractMappingDTO.codWard";
        String EMPLOYEE_SANITARY_SHEDULING__WARD_ZONE_LEVEL = "EmployeeScheduleDetailDTO.codWard";
        String CONTRACT_MAPPING_WASTE_LEVEL = "wastageSegregationDet.codWast";

    }

    interface WorkServiceShortCode {
        String FINANCIAL = "WEF";
        String TECHNICAL = "WET";
        String ADMIN = "WAD";
    }

    interface Legal {
        // Court Master
        String COURT_MASTER_FORM = "CourtMasterForm";
        String ADD_COURT_MASTER = "AddCourtMaster";
        String EDIT_COURT_MASTER = "EditCourtMaster";
        String VIEW_COURT_MASTER = "ViewCourtMaster";
        String DELETE_COURT_MASTER = "DeleteCourtMaster";
        String GET_COURT_BY_ID = "getCourtById";
        String SHORT_CODE = "LEGAL";
        String FILE_COUNT_UPLOAD = "fileCountUpload";

        // Judge Master
        String JUDGE_MASTER_FORM = "JudgeMasterForm";
        String ADD_JUDGE_MASTER = "AddJudgeMaster";
        String EDIT_JUDGE_MASTER = "EditJudgeMaster";
        String VIEW_JUDGE_MASTER = "ViewJudgeMaster";
        String DELETE_JUDGE_MASTER = "DeleteJudgeMaster";

        // Act & Rule Master
        String ADD_ACTANDRULE_MASTER = "ActAndRuleMaster";
        String ACTANDRULE_MASTER_FORM = "ActAndRuleMasterForm";

        // Advocate Master
        String ADD_ADVOCATE_MASTER = "AddAdvocateMaster";
        String ADVOCATE_MASTER_FORM = "AdvocateMasterForm";
        String EDIT_ADVOCATE_MASTER = "EditAdvocateMaster";
        String VIEW_ADVOCATE_MASTER = "ViewAdvocateMaster";

        // Case Entry
        String CASE_ENTRY = "CaseEntry";
        String CASE_ENTRY_FORM = "CaseEntryForm";
        String CSS_PREFIX = "CSS";
        String TOC_PREFIX = "TOC";

        // Hearing Date
        String HEARING_DATE = "HearingDate";
        String HEARING_DATE_FORM = "HearingDate/FORM";
        String HEARING_DETAILS_FORM = "HearingDetails/FORM";

        String COUNTER_AFFIDAVIT = "CounterAffidavit";

        String TB_LGL_CASE_MAS = "TB_LGL_CASE_MAS";
        String CASE_NO = "CSE_NO";
        String CASE_ENTRY_CODE = "CE";

        interface SaveMode {
            String EDIT = "E";
            String CREATE = "C";
            String VIEW = "V";
            String DELETE = "D";
        }
    }

    interface OBJECTION_COMMON {
        String OBJECTION_FORM_URL = "ObjectionDetails";
        String OBJECTION_SEARCH_URL = "ObjectionSearchList";
        String OBJECTION_TB_OBJECTION_ENTITY = "TB_OBJECTION_MAST";
        String OBJECTION_NUMBER = "OBJECTION_NUMBER";
        String OBJECTION_SERVICE_NAME = "OBJ";
        String OBJECTION_SMS_EMAIL = "ObjectionDetails.html";
        String SCHEDULING_SMS_EMAIL = "HearingInspection.html";
        String INSPECTION_SMS_EMAIL = "InspectionDetailEntry.html";
        String HEARING_SMS_EMAIL = "HearingDetailEntry.html";
        String OBJECTION_REFERENCE_URL = "objectionRefNumber";
        String OBJECTION_ADD_URL = "objectionAddDetails";
        String OBJECTION_VALIDATION_URL = "objectionDetailFormValdin";
        String OBJECTION_SUCCESS_URL = "objectionSuccess";
        String PROCESS_OBJECTION = "objection";

        interface OBJECTION_SERVICE_LIST {
            String OBJ_RTI_SERVICE = "APP";
        }
    }

    interface VOUCHER_REVERSAL_URLS {

        // Action URL's for Voucher Reversal
        String RECEIPT_DETAIL_VIEW_URL = "AccountReceiptEntry.html?viewReceiptDetail";
        String RECEIPT_REVERSAL_URL = "AccountVoucherReversal.html?reverse";
        String BILL_INVOICE_DETAIL_VIEW_URL = "AccountBillEntry.html?viewBillInvoiceDetail";
        String BILL_INVOICE_REVERSAL_URL = "AccountBillEntry.html?reverse";
        String DEPOSITSLIP_DETAIL_VIEW_URL = "AccountDepositSlip.html?viewDepositSlipDetail";
        String DEPOSITSLIP_REVERSAL_URL = "AccountDepositSlip.html?reverse";
        String PAYMENT_ENTRY_REVERSAL_URL = "PaymentEntry.html?reverse";
        String DIRECT_PAYMENT_DETAIL_VIEW_URL = "DirectPaymentEntry.html?viewDirectPaymentDetail";
        String PAYMENT_DETAIL_VIEW_URL = "PaymentEntry.html?viewPaymentEntryDetail";
    }

    interface RESPONSE_URL_PARAM {
        String CANCEL = "?cancelPayU";
        String SUCCESS_PAYU = "?Success";
        // String SUCCESS_TP = getMessage("SUCCESS_TP"); //commented as per instruction
        // of rajesh sir
        String SUCCESS_HDFC = "?hdfcSuccess";
        String SUCCESS_HDFC_FSS = "?hdfcFssSuccess";
        // String c = getMessage("SUCCESS_MH"); ///commented as per instruction of
        // rajesh sir
        String FAIL = "?failPayU";

    }

    interface WORKS_MANAGEMENT_WORKFLOW {
        String SHOWDETAILS = "showDetails";
        String APP_NO = "appNo";
        String ACTUAL_TASKID = "actualTaskId";
        String TASK_ID = "taskId";
        String WORKFLOW_ID = "workflowId";
        String TASK_NAME = "taskName";
        String SEND_FOR_APPROVAL = "sendForApproval";
        String PRINT_RA_BILL = "printRABill";

    }

    interface ChargeApplicableAt {
        String APPLICATION = "APL";
        String SCRUTINY = "SCL";
        String BILL = "BILL";
        String RECEIPT = "RCPT";

    }

    interface Objection {

        String TB_INSPECTION_MAS = "tb_inspection_mas";
        String INS_ID = "INS_ID";
        String TB_HEARING_MAS = "tb_hearing_mas";
        String HR_ID = "HR_ID";
        String TB_NOTICE_MAS = "tb_notice_mas";
        String NOT_ID = "NOT_ID";
        String INSPECTION = "I";
        String HEARING = "H";
        String OBJ_DETAIL_INACTIVE = "Inactive entry of Objection";
        String APPLICTION_NO = "APPLICTION_NO";
        String PERIOD = "PERIOD";

        interface ObjectionOn {
            String NOTICE = "SN";
            String BILL = "BILL";
            String FIRST_APPEAL = "FRTI";
            String SECOND_APPEAL = " SRTI";
            String LICENSE = "LICN";
            String MUTATION = "MTN";
        }

        interface InspHearStatus {
            String REJECT = "R";
            String SCHEDULE = "S";
            String COMPLETE = "C";
        }
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
        String FETCH_TRADE_LICENSE_WITH_DETAILS = "Fetch trade licence with details by Application no";
        String FETCH_TRADE_LICENSE = "Fetch trade licence by Application no";
        String GET_CHARGES_FROM_BRMS_RULE = "Get Charges from BRMS Rule";
        String SERVICE_SHORT_CODE = "NTL";
        String GET_PROPERTY_DETAILS = "getPropertyDetails";
        String CHECK_ASS_STATUS = "checkAssStatus";
        String TB_ML_TRADE_MAST = "TB_ML_TRADE_MAST";
        String TRD_LICNO = "TRD_LICNO";
        String TL = "TL";
        String MARKET_LICENSE = "ML";
        String PAYMENT_CATEGORY = "B";
        String NOT_APPLICABLE = "NA";
        String ITC = "ITC";
        String SERVICE_CODE = "NTL";
        String TAX_TYPE = "Flat";
        String TAX_CATEGORY = "Service Charge";
        String TAX_SUB_CATEGORY = "License Fee";
        String CHECK_LIST_MODEL = "ChecklistModel";
        String CREATE_DATA_ENTRY = "createDataEntrySuite";
        String DATA_ENTRYSUITE_FORM = "DataEntrySuiteForm";
        String EDIT_DATA_ENTRYSUITE = "editDataEntry";
        String TRD_ID = "trdId";
        String GET_DATA_ENTRYSUITE = "getDataEntryList";
        String LICENSE_TYPE = "licenseType";
        String OLD_LICENSENO = "oldLicenseNo";
        String TRD_WARD1 = "trdWard1";
        String TRD_WARD2 = "trdWard2";
        String TRD_WARD3 = "trdWard3";
        String TRD_WARD4 = "trdWard4";
        String TRD_WARD5 = "trdWard5";
        String NEW_LICENSENO = "newLicenseNo";
        String TYPE = "type";
        String VALIDATE_OLDLICNO = "validateOldLiscenseNo";
        String OLDLICNO = "oldLiscenseNo";
        String MLI = "MLI";
        String AS = "AS";
        String CHARGES_DETAIL = "ChargesDetailMarketLicense";
        String OWNERSHIP_TABLE = "OwnershipTable";
        String APL = "APL";
        String TRADE_LICENSE_PRINT = "TradeLicensePrint";
        String VIEW_LICENSE = "viewLicense";
        String TRADE_LICENSE_REPORT_FORMAT = "TradeLicenseReportFormat";
        String GET_CHECKLIST_AND_CHARGES = "getCheckListAndCharges";
        String EDIT_APPLICATION = "editApplication";
        String TRADE_LICENSE_EDIT = "TradeLicenseEdit";
        String LICENSE_GENERATION_HTML = "/LicenseGeneration.html";
        String TRADE_LICENSE_APPROVAL = "TradeLicenseApproval";
        String GET_LICENSE_PRINT = "getLicensePrint";
        String TRADE_LICENSE_REPORT_APPROVAL = "TradeLicenseReportApproval";
        String GENERATE_LICENSE_NUMBER = "generateLicenseNumber";
        String SHOWDETAILS = "showDetails";
        String GENERATE_CHALLAN_AND_PAYMENT = "generateChallanAndPayement";
        String SHOW_CHARGE_DETAILS_MARKET = "showChargeDetailsMarket";
        String TRADE_APPLICATION_FORM_VIEW = "TradeApplicationFormView";
        String RENEWAL_LICENSE_FORM_HTML = "/RenewalLicenseForm.html";
        String RENEWAL_SERVICE_SHORT_CODE = "RTL";
        String SAVE_UPDATE_RENEWAL_LICENSE_APPLICATION = "save renewal license";
        String RENEWAL_LICENSE_FORM = "RenewalLicenseForm";
        String GET_LICENSE_DETAILS = "getLicenseDetails";
        String RENEWAL_LICENSE_FORM_VALIDN = "RenewalLicenseFormValidn";
        String GET_CHARGES_FROM_BRMS = "getChargesFromBrms";
        String SAVE_RENEWAL_LICENSE = "saveRenewalLicense";
        String LICENSE_DETAILS_BY_LICENSE_NO = "license details from license no";
        String RENEWAL_LICENSE_PRINTING = "/RenewalLicensePrinting.html";
        String RENEWAL_LICENSE_REPORT_FORMAT = "RenewalLicenseReportFormat";
        String DUPLICATE_SERVICE_SHORT_CODE = "DTL";
        String DUPLICATE_LICENSE_FORM = "DuplicateLicenseForm";
        String DUPLICATE_LICENSE_FORM_DETAILS = "DuplicateLicenseFormDetails";
        String DUPLICATE_LICENSE_FORM_DETAILS_VALIDN = "DuplicateLicenseFormDetailsValidn";
        String GET_DUPLICATE_SERVICE_CHARGES_FROM_BRMS_RULE = "Get Duplicate Service Charges from BRMS Rule";
        String SAVE_DUPLICATE_SERVICE_APPLICATION = "Save Duplicate Service Application";
        String LICENSE_TYPE_PREFIX = "LIT";
        String GET_DUPLICATE_LICENSE_PRINT = "getDuplicateLicensePrint";
        String DUPLICATE_LICENSE_PRINT = "DuplicateLicensePrint";
        String CHANGE_BUSINESS_NAME_SHORT_CODE = "CBN";
        String GET_BUSINESS_NAME_SERVICE_CHARGES_FROM_BRMS_RULE = "Get Business Name Service Charges from BRMS Rule";
        String SAVE_CHANGE_IN_BUSINESS_NAME_SERVICE_APPLICATION = "Save Change In Buisness Name Service Application";
        String CHANGE_IN_BUSINESS_NAME_FORM = "ChangeInBusinessNameForm";
        String GET_CHANGE_IN_BUSINESS_LICENSE_PRINT = "getChangeInBusNameLicensePrint";
        String CHANGE_IN_BUS_NAME_LICENSE_PRINT = "changeInBusNameLicensePrint";
        String GET_CATEGORY_SUBCATEGORY_CHARGES_FROM_BRMS_RULE = "Get Category Sub Category Charges from BRMS Rule";
        String SAVE_CHANGE_IN_CATEGORY_SUBCATEGORY_SERVICE_APPLICATION = "Save Change In Category Sub Category Service Application";
        String CHNAGE_IN_CATEGORY_SUBCATEGORY_FORM = "ChangeInCategorySubcategoryForm";
        String CHANGE_IN_VATEGORY_SUBCATEGORY_EDIT = "ChangeInCategorySubcategoryEdit";
        String SAVE_CATEGORY_SUBCATEGORY_FORM = "saveCategorySubCategoryForm";
        String CHANGE_IN_CATEGORY_SUBCATEGORY_SERVICE_SHORTCODE = "CCS";
        String DELETE_CATEGORY_TABLE_ROW = "deleteCategoryTableRow";
        String EDIT_CHANGE_CATEGORY_APPLICATION = "editChangeCategoryApplication";
        String CHANGE_IN_CATEGORY_SUBCATEGORY_FORM_VIEW = "ChangeInCategorySubcategoryFormView";
        String SAVE_CANCELLATION_LICENSE_SERVICE = "Save Cancellation License Service";
        String PRINT_AGENCY_REG_ACK = "printAgencyRegAckw";
        String AGENCY_REGISTRATION_ACK = "AgencyRegistrationAcknowledgement";
        String CANCELLATION_SHORT_CODE = "CTL";
        String CHECK_WORKFLOW_EXIST_OR_NOT = "checkWorkFlowExistOrNot";
        String FETCH_TRADE_LICENSE_REPRINT_DETAILS = "tradeLicenseReprintDetails";
        String FETCH_LIC_NO_APP_NO = "For Fetching Application and License No";
        String TRD_ENV = "TEV";
        String GET_CANCELLATION_SERVICE_CHARGES_FROM_BRMS_RULE = "Get Cancellation Service Charges from BRMS Rule";
        String TRANSFER_SERVICE_SHORT_CODE = "TLA";
        String INSPECTION_DETAIL_FORM = "/InspectionDetailForm.html";
        String INSPECTION_DETAIL_ENTRY_FORM = "InspectionDetailForm";
        String SAVE_INSPECTION = "saveInspection";
        String SHOWCAUSE_NOTICE_FORM = "showCauseNoticeForm";
        String LIC_NO = "licNo";
        String INSP_NO = "inspNo";
        String SAVE_NOTICE_DETAILS = "saveNoticeDetails";
        String LICENSE_OBJ = "OBJ";
        String SHOW_CAUSE_NOTICE = "showCauseNoticeLetter";
        String TB_MTL_NOTICE_MAS = "TB_MTL_NOTICE_MAS";
        String NT_NO = "NT_NO";
        String GET_LICENSE_SUBCATAGORY_BY_CATID = "getLicenseSubCatagory";
        String RENEWAL_REMINDER_NOTICE = "RenewalReminderNotice";
        String SEARCH_DETAILS = "searchDetails";
        String TB_MTL_INSPECTION_REG = "tb_mtl_inspection_reg";
        String IN_NO = "IN_NO";
        String RENEWAL_REMINDER_NOTICE_VALIDN = "RenewalReminderNoticeValidn";
        String RENEWAL_REMINDE_RNOTICE_PRINT = "RenewalReminderNoticePrint";
        String PRINT_RENEWAL_REMINDER_NOTICE = "printRenewalreminderNotice";
        String NOTICE_NO = "noticeNo";
        String CHECK_APP_REJECT_ORNOT = "For Checking Application Rejected Or Not";
        String DEPOSITS = "Deposits";
        String PRI_OWNER = "pimaryOwner";
        String BUSINESS_NAME = "businessName";
    }

    interface RoadCuttingConstant {
        String RAOD_CUTTING_URL = "/RoadCutting.html";
        String RCP = "RCP";
        String RCW = "RCW";
        String CHECKLIST_ROADCUTTING_RATE_MASTER = "ChecklistModel|RoadCuttingRateMaster";
        String ROADCUTTING_RATE_MASTER = "RoadCuttingRateMaster";
        String PROJECT_LOCATION = "PROJECT_LOCATION";
        String OPR = "OPR";
        String SRP = "SRP";
        String WOR = "WOR";
        String DEPT_CODE = "WMS";
    }

    interface ReceiptForm {
        String RECEIPT_MASBEANLIST = "receiptMasBeanList";
        String SERVICE_RECEIPTMASTER = "tbServiceReceiptMasBean";
        String SERVICE_RECEIPTMAS = "tbServiceReceiptMas";
        String RECEIPT_ENTRY_FORM = "receiptEntryForm";
        String BANK_ACCOUNT_MAP = "bankAccountMap";
        String PAYEE_LIST = "payeeList";
        String PAY_MODE = "paymentMode";
        String RECEIPT_TYPE = "Receipt";
        String PAYMENT_TYPE = "Payment";
        String CUSTOMER_BANK_LIST = "customerBankList";
        String RECEIPT_VOUTYPE = "recieptVouType";
        String FORM_MODE = "form_mode";
        String TAC = "TAC";
        String SC = "SC";
        String GETRECEIPT_ACCOUNTHEAD_DATA = "getReceiptAccountHeadData";
        String DPT = "DPT";
        String GETALL_RECEIPTDATA = "getAllReceiptData";
        String CHECK_BUDGECODE_FEEMODE = "checkBudgetCodeForFeeMode";
        String ACTION_VIEW = "actionForView";
        String VIEW = "view";
        String VALIDATE_CHEQUE_DATA = "validateChequeDate";
        String GET_VENDOR_PHONENO_EMAILID = "getVendorPhoneNoAndEmailId";
    }

    interface ReceivableDemandEntry {
        String REFERANACE_NUMBER_WISE = "R";
        String APPLICATION_NUMBER_WISE = "A";
        String NEW_IDN = "N";
        String OLD_IDN = "O";
        int SECOND_LEVEL = 2;
        String RECEIVABLE_DEMAND_JSP_FORM = "ReceivableDemandEntryForm";
        String RECEIVABLE_DEMAND_JSP_FORM_EDIT = "EditReceivableDemandEntryForm";
        String RECEIVABLE_DEMAND_JSP_FORM_SEARCH = "ReceivableDemandEntrySearch";
        String RECEIVABLE_DEMAND_JSP_FORM_VIEW = "ViewReceivableDemandEntryForm";
        String RECEIVABLE_DEMAND_JSP_FORM_PRINT_CHALLAN = "ReceivableDemandChallan";
        String OPEN_DEMAND_ENTRY_FORM = "openForm";
        String EDIT_DEMAND_ENTRY_FORM = "editReceivableDemandEntry";
        String VIEW_DEMAND_ENTRY_FORM = "viewReceivableDemandEntry";
        String SEARCH_DEMAND_ENTRY_FORM = "searchReceivableDemandEntry";
        String PRINT_CHALLAN = "printChallan";
        String BILL_NO_FORMAT = "HES";
        String FORM_ERROR = "formError";
        String SUPPLEMENTRY_BILL_DUE_DATE = "SBD";

        String GET_TAX_SUBCATEGORY = "selectTaxCategory";
        String REFRESH_SERVICE_DATA = "refreshServiceData";
        String GET_TAX_DETAILS = "getTaxDetails";
        String GET_ACCOUNT_HEAD = "getHeadList";
        String GET_ALL_ACCOUNT_HEAD = "getAccountHeadCode";
        String ACCOUNT_HEAD_CODE = "accountHeadCode";
        String TAX_CATEGORY = "taxCat";
        String CUSTOMER = "cust";
        String IDN = "IDN";
        String CCN = "CCN";
        String MODULE = "COM";
        String GET_BY_ID = "getById";

        String CURRENT_YEAR = "CYR";
        String DEMAND_CLASSIFICATION = "DMC";

        String SUPPLEMENTRY_BILL = "SBILL";

        String RECEIVABELE_DEMAND_ENTRY = "Receivable.demand.entry";
        String GET_CONSUMER_DETAILS_BY_REF_NO = "getConsumerDetailsByRefNo";
        String GET_CONSUMER_DETAILS_BY_CCNID = "getConsumerDetailsByccnId";

        // # Miscellaneous Demand Collection
        String SEARCH_CONNECTION_NUMBER = "searchConnection";
        String MISC_JSP_FORM_SEARCH = "MiscDemandCollectionSearch";

        // # Validation Messages
        String BILL_NUMBER_NOT_FOUND = "misc.demand.collection.billNo.not.found";
        String BILL_PAID = "misc.demand.collection.bill.paid";
        String BILL_AMOUNT = "misc.demand.collection.bill.amount";
        String RECEIVABELE_DEMAND_ENTRY_EDIT_SUCCESS = "receivable.demand.entry.validation.edit.success";
        String RECEIVABELE_DEMAND_ENTRY_SAVE_SUCCESS = "receivable.demand.entry.validation.save.success";
        String SAME_ACCOUNTHEAD_MAPPED_WITH_TAXID = "receivable.demand.entry.validation.multiple.taxId";
        String EXCEPTION_OCCURED = "receivable.demand.entry.validation.exception.occure";
        String ACCOUNT_HEAD_NOT_FOUND = "receivable.demand.entry.validation.not.found.accoundCode";
        String NO_CUSTOMER_FOUND_REF_NO_WISE = "receivable.demand.entry.validation.notFound.refNo";
        String NO_CUSTOMER_FOUND_APPLICATION_NO_WISE = "receivable.demand.entry.validation.notFound.appNo";
        String MISC_DEMAND_COLLECTION_SUCCESS = "misc.demand.collection.payment.success";
        String MISC_DEMAND_COLLECTION_EXCEPTION = "misc.demand.collection.payment.exception";
        String EXCESS_AMOUNT_ENTERED = "misc.demand.collection.validation.receipt.amt";
        String BILL_NUMBER = "receivable.demand.entry.validation.bill.no";
        String SEARCH_BILL_NO_EXCEPTION = "misc.demand.collection.search.exception";
        String BILL_DUE_DATE_OVER = "misc.demand.collection.bill.due.date.over";
        String UPDATE_BILL_DISPUTE_FLAG = "BILL.DISPUTE.FLAG.UPDATE.";
        String UPDATE_BILL_DISPUTE_FLAG_BILL_MAS = "updateBillDisputeFlagAfterPayment";
    }

    // this interface is created for the constant which is use in social security
    interface SocialSecurity {
        String PENSION_SCHEME_MASTER_URL = "PensionSchemeMaster.html";
        String FTR = "FTR";
        String CRA = "CRA";
        String DEPARTMENT_SORT_CODE = "SWD";
        String RENEWAL_OF_LIFE_CERTIFICATE_SERVICE_CODE = "RLC";
        String CANCELLATION_OF_PENSION_SERVICE_CODE = "CNP";
        String SBY = "SBY";
        String PAYMENT_PREFIX = "BSC";
        String SCHEME_APPLICATION_FORM_URL = "SchemeApplicationForm.html";
        String SERVICE_CODE = "SAA";
        String SOCIAL_CHECKLIST = "ChecklistModel";
        String CHECK_LIST_FORM = "checkListApplicationForm";
        String BENEFICIARY_PAYMENT_ORDER = "BeneficiaryPaymentOrder.html";
        String BENEFICIARY_SERVICE_CODE = "BPY";
        String SAVE_UPDATE_SOCIAL_SECURITY_APPLICATION = "save social security";
        String SAVE_RENEWAL_OF_LIFE_CERTIFICATE = "Save Renewal of Life Certificate";
        String FIND_RENEWAL_DETAILS = "Find Renewal of Life Certificate";
        String TAX_MASTER_PREFIX_TXN = "TXN";
        String TAX_PREFIX = "PNS";
        String MISCELLANEOUS = "MI";
        String VST = "VST";
        String PENSION = "P";
        String BNF = "BNF";
        String RBM = "RBM";
        String IGNDS = "IGNDS";
        String FETCH_DATA_ON_BENEF = "Fetch Data on Beneficiary";
        String WORKFLOW_FORM_VIEW = "Workflow Form View";
        String REJECTED = "RM";

        String SHOWDETAILS = "showDetails";
        String APP_NO = "appNo";
        String ACTUAL_TASKID = "actualTaskId";
        String TASK_ID = "taskId";
        String WORKFLOW_ID = "workflowId";
        String TASK_NAME = "taskName";
        String SEND_FOR_APPROVAL = "sendForApproval";
        String PRINT_RA_BILL = "printRABill";

        String CONFIGURATION_MASTER_URL = "schemeConfigurationMaster.html";
        String SEARCH = "search";
        String SUMMARY_FORM = "ConfigurationMaster";
        String CONFIGURATION_FORM = "schemeConfigurationMaster/Form";
        String CONFIGURATION_SUMMARY = "ConfigurationMasterSummaryForm";

        String LIFE_CERTIFICATE = "Life Certifcate's Date Expired: ";

        String MONTHLY = "Monthly";
        String BIMONTHLY = "Bi-Monthly";
        String HALFYEARLY = "Half-Yearly";
        String YEARLY = "Yearly";
        String QUATERLY = "Quarterly";

        String MONTHLY_PAYMENT = "Monthly Payment done for Beneficiary No. ";
        String BIMONTHLY_PAYMENT = "Bi-Monthly Payment done for Beneficiary No. ";
        String HALFYEARLY_PAYMENT = "Half-Yearly Payment done for Beneficiary No. ";
        String YEARLY_PAYMENT = "Yearly Payment done for Beneficiary No. ";
        String FIRST_HALF_YEARLY_PAYMENT = "First Half Yearly Payment done for Beneficiary No. ";
        String SECOND_HALF_YEARLY_PAYMENT = "Second Half Yearly Payment done for Beneficiary No. ";
        String FIRST_QUATERLY_PAYMENT = "First Quaterly Payment done for Beneficiary No. ";
        String SECOND_QUATERLY_PAYMENT = "Second Quaterly Payment done for Beneficiary No. ";
        String THIRD_QUATERLY_PAYMENT = "Third Quaterly Payment done for Beneficiary No. ";
        String FOURTH_QUATERLY_PAYMENT = "Fourth Quaterly Payment done for Beneficiary No. ";

        interface SOCIAL_SECURITY_WORKFLOW {
            String SHOWDETAILS = "showDetails";
            String APP_NO = "appNo";
            String ACTUAL_TASKID = "actualTaskId";
            String TASK_ID = "taskId";
            String WORKFLOW_ID = "workflowId";
            String TASK_NAME = "taskName";
            String SEND_FOR_APPROVAL = "sendForApproval";
            String PRINT_RA_BILL = "printRABill";

        }
    }

    /**
     * Use this to declare prefix in order to find LookUp object for that Prfix
     */
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

    /*
     * Council Related Constant Define
     */
    interface Council {
        String COUNCIL_MANAGEMENT = "CMT";
        String AGENDA_MASTER_URL = "CouncilAgendaMaster.html";
        String MEETING_MASTER_URL = "CouncilMeetingMaster.html";
        String COMMITTEE_MEMBER_MASTER_URL = "CouncilMemberCommitteeMaster.html";
        String ATTENDANCE_MASTER_URL = "CouncilAttendanceMaster.html";
        String MOM_URL = "CouncilMOM.html";
        String TB_COU_MEETING_MASTER = "TB_CMT_COUNCIL_MEETING_MAST";
        String MEETING_NO = "MEETING_NUMBER";
        String DB_STATUS_APPROVED = "A";
        String DB_STATUS_REJECT = "R";
        String STATUS_APPROVED = "APPROVED";
        String STATUS_REJECTED = "REJECTED";
        String STATUS_COMPLETED = "COMPLETED";
        int PRESENT_STATUS = 1;
        int ABSENT_STATUS = 0;
        String DB_STATUS_PENDING = "P";
        String DB_STATUS_DRAFT = "D";
        String STATUS_PENDING = "PENDING";
        String STATUS_DRAFT = "DRAFT";
        String CREATE = "C";
        String EDIT = "E";
        String VIEW = "V";
        String ELECTION_WARD_PREFIX = "EWZ";
        String MONTH_FORMAT = "MMM";
        String NOT_AVAILABLE = "NA";
        String ACTIVE_STATUS = "Y";
        String INACTIVE_STATUS = "N";
        String PROPOSAL_TYPE_PREFIX="PTS";

        interface Agenda {
            String AGENDA_STATUS_YES = "Y";
        }

        interface Proposal {
            String INI_STATUS = "INT";
            String SUP_STATUS = "SUP";
            String CAN_STATUS = "CAN";
            String NDS_STATUS = "NDS";
            String FIN_STATUS = "FIN";
            String APPROVE_STATUS = "A";
            String PROPOSAL_NO = "PROPOSAL_NUMBER";
            String TB_COU_PROPOSAL_MASTER = "TB_CMT_COUNCIL_PROPOSAL_MAST";
            String PROPOSAL_URL = "/CouncilProposalMaster.html";
            String ADD_PROPOSAL = "addproposal";
            String ADD_PROPOSAL_FORM = "addCouncilProposalMaster";
            String SEARCH_COUNCIL_PROPOSAL = "searchCouncilProposal";
            String DEPTID = "proposalDepId";
            String PROPOSALNO = "proposalNo";
            String FROM_DATE = "fromDate";
            String Proposal_type = "type";
            String TO_DATE = "toDate";
            String PROPOSAL_STATUS = "proposalStatus";
            String ELECTIONWZID = "wardId";
            String EDIT_PROPOSAL = "editCouncilProposalMasterData";
            String PROPOSAL_ID = "proposalId";
            String FILE_UPLOAD_IDENTIFIER = "PRO";
            String VIEW_PROPOSAL = "viewCouncilProposalMasterData";
            String SENT_FOR_APPROVAL = "sendForApproval";
            String SERVICE_COUNCIL_PROPOSAL = "COP";
            String PROPOSAL_APPROVAL_URL = "CouncilProposalApproval.html";
            String CHECK_STATUS_APPROVAL = "checkStausApproval";
            String SET_DECISION_SUBMITTED = "SUBMITED";
            String GET_BUDGET_HEAD_DETAIL = "getBudgetHeadDetails";
            String SAC_HEAD_ID = "sacHeadId";
            String YEAR_ID = "yearId";
            String PROPOSAL_AMOUNT = "proposalAmt";
            String GET_FINANCIALYEARIDBYDATE = "getFinancialYearByProposalDate";
            String PROPOSAL_DATE = "proposalDate";
            String MEETING_DECISION = "INTRIM";
            String TB_CMT_COUNCIL_AGENDA_MAST = "TB_CMT_COUNCIL_AGENDA_MAST";
            String AGENDA_ID = "AGENDA_ID";
        }

        interface Council_Management_Workflow {
            String PROPOSALAPPROVAL_URL = "/CouncilProposalApproval.html";
            String COUNCIL_PROPOSAL_APPROVAL = "CouncilProposalApproval";
            String SAVE_PROPOSAL_APPROVAL = "saveProposalApprovalDetails";
            String WORKFLOW_STATUS = "wfStatus";
            String SHOWDETAILS = "showDetails";
            String APP_NO = "appNo";
            String ACTUAL_TASKID = "actualTaskId";
            String TASK_ID = "taskId";
            String WORKFLOW_ID = "workflowId";
            String TASK_NAME = "taskName";
            String SEND_FOR_APPROVAL = "sendForApproval";
        }

        interface Meeting {
            String MEETING_DATE_FORMATE = "dd/MM/yyyy HH:mm";
            String ATTENDANCE_ALL = "ALL";
            String ATTENDANCE_STATUS_TRUE = "ATTENDANCE_STATUS_TRUE";
            String ATTENDANCE_STATUS_FALSE = "ATTENDANCE_STATUS_FALSE";
            String APPLICATION_MEETING_INVITATION = "Council Meeting Invitation";
            String MEETING_SERVICE_NAME = "CMI";
            String ORDER_BY_MEETING_DATE = "MEETING_DATE";
            String ORDER_BY_MEETING_ID = "MEETING_ID";
            String NO_COMMENTS = "No Comments";
            String FILE_UPLOAD_IDENTIFIER_ATD = "ATD";
        }

        interface MOM {
            String APPLICATION_MOM_REPORTS = "MOM Reports";
            String SERVICE_NAME = "MOM";
            String MEETING_PROPOSAL_STATUS_PREFIX = "PPS";
            String APPROVED_PROPOSAL_CODE = "APP";

        }

        interface CommitteeMapping {
            String VALIDITY_ERROR_MSG = "Committee date validity is finished for process";
        }

        interface MemberMaster {
            String MEMBER_MASTER_URL = "CouncilMemberMaster.html";
            String ADD_COUNCIL_MEMBER = "addCouncilMember";
            String ADD_COUNCIL_MEMBER_MASTER = "addCouncilMemberMaster";
            String SEARCH_COUNCIL_MEMBER = "SearchCouncilMember";
            String DESIGNATION = "designation";
            String MEMBER_NAME = "couMemName";
            String Party_AFFILATION = "couPartyAffilation";
            String ELECTION_WARD_ZONE_ID1 = "couEleWZId1";
            String ELECTION_WARD_ZONE_ID2 = "couEleWZId2";
            String EDIT_COUNCIL_MEMBER_MASTER = "editCouncilMemberMasterData";
            String COUNCIL_ID = "couId";
            String FILE_UPLOAD_IDENTIFIER = "MEM";
            String VIEW_COUNCIL_MEMBER = "viewCouncilMemberMasterData";
            String MEMBER_TYPE = "couMemberType";
        }

    }

    /*
     * Land Estate Related Constant Define
     */
    interface LandEstate {
        String LandEstateCode = "EST";
        String LAND_URL = "LandAcquisition.html";

        String LAND_BILL_URL = "LandBill.html";

        String ACTIVE_STATUS = "Y";
        String INACTIVE_STATUS = "N";
        String GET_CHECKLIST_AND_CHARGES = "getCheckListAndCharges";
        String MODULE_LIVE_INTEGRATION = "MLI";
        String ASSET_CODE_VALUE = "FAS";
        String ASSET_NOT_LIVE_MSG = "Asset module is not live";
        String BILL_TYPE_MISCELLANEOUS = "MI";
        String PROCESS_SCRUITNY = "scrutiny";

        interface LandAcquisition {
            String SERVICE_SHOT_CODE = "LAQ";
            String CHECK_LIST_MODEL = "ChecklistModel";
            String TB_LN_AQUISN = "TB_LN_AQUISN";
            String ACQ_NO = "ACQ_NO";
            String ESTATE = "EST";
            String TMC = "TMC";
            String ACQUIRED_STATUS = "A";
            String TRANSIT_STATUS = "T";
            String ACQUIRED = "ACQUIRED";
            String TRANSIT = "TRANSIT";
        }

    }

    interface AdminUpdatePersonalDtls {
        String ADMMIN_UPDATE_PERSONAL_DTLS = "AdminUpdatePersonalDtls";
        String ENTER_MANDATORY_FIELDS = "eip.citizen.upd.enterMandatoryFields";
        String MOBILE_NUMBER_ALREADY_EXISTS = "eip.citizen.upd.mobileNoExists";
        String INTERNAL_SERVER_ERROR = "eip.citizen.upd.internalServerError";
        String ACTIVE = "0";
        String SUCCESS = "success";
    }

    interface AdvertisingAndHoarding {

        String ADVERTISER_NUMBER = "advertiserNumber";
        String ADVERTISER_OLD_NUMBER = "advertiserOldNumber";
        String ADVERTISER_NAME = "advertiserName";
        String ADVERTISER_STATUS = "advertiserStatus";
        String SEARCH_ADVERTISER_MASTER = "searchAdvertiserMaster";
        String ADD_ADVERTISER_MASTER = "addAdvertiserMaster";
        String AGENCY_ID = "agencyId";
        String SAVE_MODE = "saveMode";
        String ADD_HOARDING_MASTER = "addHoardingMaster";
        String HOARDING_NUMBER = "hoardingNumber";
        String HOARDING_STATUS = "hoardingStatus";
        String HOARDING_TYPE = "hoardingType";
        String HOARDING_SUB_TYPE = "hoardingSubType";
        String HOARDING_SUB_TYPE3 = "hoardingSubType3";
        String HOARDING_SUB_TYPE4 = "hoardingSubType4";
        String HOARDING_SUB_TYPE5 = "hoardingSubType5";
        String HOARDING_LOCATION = "hoardingLocation";
        String SEARCH_HOARDING_MASTER = "searchHoardingMaster";
        String HOARDING_ID = "hoardingId";
        String HOARDING_MASTER = "ADH";
        String TB_HOARDING_MAS = "TB_ADH_HOARDING_MAS";
        String HRD_NO = "HRD_NO";
        String AGENCY_CHECKLIST_RATE_MODEL = "ChecklistModel|ADHRateMaster";
        String GET_CHECKLIST_CHARGES = "getChecklistAndCharges";
        String ADH_SHORT_CODE = "ADH";
        String OWNERSHIP_DETAIL_TABLE = "adhOwnershipDetailTable";
        String RENEWAL_OWNERSHIP_DETAIL_TABLE = "adhRenewalOwnershipDetailTable";
        String OWNERSHIP_TABLE = "adhOwnershipTable";
        String GET_OWNERSHIP_TYPE_DIV = "getOwnershipTypeDiv";
        String GET_RENEWAL_OWNERSHIP_TYPE_DIV = "getRenewalOwnershipTypeDiv";
        String OWNERSHIP_TYPE = "ownershipType";
        String RENEWAL_OWNERSHIP_TYPE = "RenewalownershipType";
        String AGENCY_REG_SHORT_CODE = "AGL";
        String CHECK_LIST = "ChecklistModel";
        String TRANSIENT_STATUS = "T";
        String AGENCY_REG_REN_SHORT_CODE = "AGR";
        String SEARCH_SERVICES_BY_DEPTID = "searchServicesBydeptId";
        String SEARCH_LIC_SUB_CATAGORY_BY_CATAGORY_ID = "getLicenseSubCatagory";
        String ADD_LICENSE_VALIDITY_ENTRY = "addLicenseValidityEntry";
        String SEARCH_LICENSE_VALIDITY_DATA = "searchLicenseValidityData";
        String EDIT_OR_VIEW_LICENSE_VALIDITY = "editOrViewLicenseValidity";
        String LIC_ID = "licId";
        String AGENCY_REGISTRATION_ACKNOWLEDGEMENT = "AgencyRegistrationAcknowledgement";
        String PRINT_AGENCY_REG_ACKW = "printAgencyRegAckw";
        String SAVE_AGENCY_REGISTRATION = "saveAgencyRegistration";
        String AGENCY_REGISTRATION_VALIDN = "AgencyRegistrationValidn";
        String PRINT_AGENCY_LICENSE_LETTER = "printAgencyLicenseLetter";
        String PRINT_AGENCY_LIC_LETTER = "printAgencyLicenseLetter";
        String AGENCY_REGISTRATION_SMS_URL = "AgencyRegistration.html";
        String AGENCY_REGISTRATION_REN_SMS_URL = "AgencyRegistrationRenewal.html";
        String AGENCY_REGISTRATION_AUTH_SMS_URL = "AgencyRegistrationAuth.html";
        String AGENCY_REGISTRATION_APPROVAL = "AgencyRegistrationApproval";
        String ADH_SHOW_DETAILS = "showDetails";
        String APP_NO = "appNo";
        String TASK_ID = "taskId";
        String ACTUAL_TASKID = "actualTaskId";
        String SAVE = "save";
        String AGENCY_REGISTRATION_RENEWAL_APPROVAL = "AgencyRegistrationRenewalApproval";
        String AGENCY_REGISTRATION_REN_AUTH_SMS_URL = "AgencyRegistrationRenewalAuth.html";
        String REJECTED_MESSAGE = "RM";

        String NEW_ADV_APP_HTML = "/NewAdvertisementApplication.html";
        String NEW_ADVERTISEMENT_APPLICATION = "NewAdvertisementApplication";
        String GET_AGENCY_NAME = "getAgencyName";
        String ADVERTSIER_CAT = "advertiserCategoryId";
        String APPLICABLE_CHECKLIST_CHARGE = "getApplicableCheckListAndCharges";
        String NEW_ADVERTISEMENT_DATA = "NewAdvertisementData";
        String YOUR_APPLICATION_NO = " Your Application No.";
        String SAVE_NEW_ADVERTISEMENT_APPLICATION = "Save New Advertisement Application ";
        String SAVE_HOARDING_APPLICATION = "Save New Hoarding Application ";
        String REPLACE_NO = "[^0-9]";
        String GET_ALL_ADVERTISEMENT_APPLICATION_DATA_BY_APPLCATIONID = "Get All  New Advertisement Application Data By ApplicationId";
        String APPLICATION_ID = "applicationId";
        String TB_ADH_MAS = "TB_ADH_MAS";
        String ADH_LICNO = "ADH_LICNO";
        String NO_FORMAT = "%02d";
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
        String AGENCY_REG_RENEW_VALIDN = "AgencyRegistrationRenewalValidn";

        String NEW_HRD_APP_HTML = "/NewHoardingApplication.html";
        String GET_HRD_DET_BY_NUM = "searchHoardingDetailsByHoardingNumber";
        String NEW_HOARDING_DATA = "NewHoardingData";

        String ADD_ADH_CONT_MAP = "addAdvertisementContractMapping";
        String ADVERTISE_CONTRACT_MAPPING_FORM = "AdvertisementContractMappingForm";
        String GET_HOARD_DET_BY_HOARDID = "getHoardingDetailByHoardingNo";
        String SEARCH_CONTRACT_BY_CONTNO_OR_CON_DATE = "searchContractByContNoOrContDate";
        String CONTRACT_NO = "contractNo";
        String CONT_DATE = "contDate";
        String VIEW_CONTRACT_MAPPING = "viewAdhContractMapping";
        String CONT_ID = "contId";
        String ADVERTISE_CONTRACT_MAPPING_VALIDN = "AdvertisementContractMappingValidn";
        String CHECK_DUP_CONTRACT = "checkDuplicateContract";

        String SEARCH_ADH_BILL_RECORD = "searchADHBillRecord";
        String ADH_BILL_PAYMENT_VALIDN = "ADHBillPaymentValidn";
        String ACP = "ACP";

        String NOT_APPLICABLE = "NA";
        String AREA = "AR";
        String UNIT = "UN";
        String MODE_OF_ADV = "MOA";
        String ADVERTISEMENT = "ADH";
        String LOCATION = "LOC";
        String LICENSE_TYPE = "LT";
        String ADV_ZONE_WARD = "ADZ";
        String EXISTING_LOCATION = "Existing Location";
        String NEW_LOCATION = "New Location";
        String PROCESS_SCRUITNY = "scrutiny";
        String ADVERTISEMENT_LICENSE_GEN = "advertisementLicenseGeneration";
        String GET_ADVERTISEMENT_APPLICATION_FORM = "getAdevertisementApplicationForm";
        String LICENSE_NO = "licenseNo";
        String RENEWAL_ADV_SHORTCODE = "RAL";
        String RENEWAL_ADVERTISEMENT_FORM = "renewalOfAdvertisementApplicationForm";
        String RENEWAL_ADV_DATA = "renewalAdvertisementData";
        String RENEWAL_ADV_APPLICATION_VALIDN = "renewalAdvertisementApplicationValidn";
        String RENEWAL = "RENEWAL";
        String NEW = "NEW";
        String ADVERTISEMENT_DATA_ENTRY_URL = "/AdvertisementDataEntry.html";
        String ADD_DATA_ENTRY = "addAdvertisementEntry";
        String ADH_STATUS = "adhStatus";
        String LOCID = "locId";
        String LIC_TYPE = "licenseType";
        String ORG_ID = "orgId";
        String ADH_ID = "adhId";
        String SEARCH_DATA_ENTRY = "searchAdvtDataEntry";
        String NEW_HRD_APP_VALIDN = "NewHoardingApplicationValidn";
        String GET_LOCATION_MAPPING = "getLocationMapping";
        String LOCATION_ID = "locationId";
        String HOARDING_REGISTRATION_SHORT_CODE = "HBP";
        String AGENCY_ALERT_REMINDER = "RMP";
        String AOT = "AOT";
        String UPDATE_LICENSE_NO_Flag_C = "UPDATE_LICENSE_NO_Flag_C";
        String AGENCY_CAN_SHORT_CODE = "CAL";
        String SAVE_CANCELLATION_LICENSE_SERVICE = "Save Cancellation License Service";
        String PROPERTY_DATA_FETCH_SERVICE = "Property Data Fetch Service";
        String AGENCY_REG_Cancel_VALIDN = "AdvertiserCancellationFormValidn";
        String GET_LICENCE_TYPE = "getLicenceType";
        String GET_CALCULATE_YEAR_TYPE = "getCalculateYearType";
        String AGENCY_REGISTRATION_Cancellation = "AdvertisercancellationFormView";
        String ADH = "ADH";

    }

    // Define factors name for the custom sequence generation
    interface SQ_FACT_ID {
        String ORG_ID = "ULB Code";
        String SERVICE_CODE = "Service Code";
        String BUSINESS_UNIT_CODE = "Business Unit Code";
        String DEPARTMENT_CODE = "Department Code";
        String STATE_CODE = "State Code";
        String DIVISION_CODE = "Division Code";
        String DISTRICT_CODE = "District Code";
        String DEPARTMENT_PREFIX = "Department Prefix";
        String LEVEL = "Level";
        String USAGE_TYPE = "Usage Type";
        String FINANCIAL_YEAR_WISE = "Financial Year Wise";
        String MIX_USAGE_CODE = "MU";
        String DESIGNATION = "Designation";
        String TRADE_CAT = "Trade Category";
        String Calendar_Year_wise = "Calendar Year Wise";
        String FIELD = "Field";
        String Date = "Date";
        String ORGANISATION_ID = "Organisation Id";
    }

    // Define prefixes for the sequence configuration
    interface SEQ_PREFIXES {
        String SQT = "SQT";
        String SQN = "SQN";
        String SEC = "SEC";
        String SEP = "SEP";
        String AC = "AC";
    }

    // Define for the sequence categories
    interface CAT_ID {
        String NORMAL_BASE = "NRM";
        String PREFIX_BASE = "PRB";
    }

    interface RightToService {
        String RTS_DEPT_CODE = "RTS";
        String APPLICANTFORM = "rtsApplicantForm";
        String DRAINAGECONNECTION = "rtsDrainageConnectionForm";
        String CHECKLISTFORM = "rtsCheckListForm";
        String DCS = "DCS";
        String RTS_CHECKLIST = "ChecklistModel";
        String CHECKLIST_RTSRATE_MASTER = "ChecklistModel|WaterRateMaster";
        String FIRST_APPEAL_URL = "FirstAppeal.html";
        String SECOND_APPEAL_URL = "SecondAppeal.html";
        String PREFIX_OBJ = "OBJ";
        String FIRST_APPEAL_PREF_CODE = "FRTS";
        String SECOND_APPEAL_PREF_CODE = "SRTS";
        String DRN_ACK_PAGE = "rtsAcknowldgeMentStatusForm";
        String EXTERNAL_FLAG = "ExternalFlag";

        interface REDIRECT_URLS {
            String BIRTH_CERTIFICATE = "ApplyforBirthCertificate.html";
            String DEAT_CERTIFICATE = "applyForDeathCertificate.html";
        }

        interface SERVICE_CODE {
            String BIRTH_CERTIFICATE = "RBC";
            String DEATH_CERTIFICATE = "RDC";
            String FIRST_APPEAL = "FAR";
            String SECOND_APPEAL = "SAR";
        }
    }

    interface LQP {
        String LQP_DEPT_CODE = "LQP";
        String LQE_URL = "LegislativeQuestion.html";
        String LQA_URL = "LegislativeAnswer.html";
        String TB_LQP_QUERY_REGISTRATION = "TB_LQP_QUERY_REGISTRATION";
        String QUESTION_ID = "QUESTION_ID";

        interface SERVICE_CODE {
            String LQE = "LQE";
        }

        interface STATUS {
            String OPEN = "OPEN";
            String CONCLUDED = "CONCLUDED";
            String REOPEN = "REOPEN";
        }
    }

    enum PlumberJasperFiles {

        REJECTION_JRXML("Plumber_rejection_letter.jrxml"), PLUM_APP(
                "Plumbing_Licence_Application_mysql.jrxml"), PLUM_SUB_APP_REPORT("Plumbing_Application_Subreport_mysql.jrxml");

        private String colDescription;

        private PlumberJasperFiles(String colDescription) {

            this.colDescription = colDescription;
        }

        public String getColDescription() {
            return colDescription;
        }

        public void setColDescription(String colDescription) {
            this.colDescription = colDescription;
        }

    }

    enum DuplicateBillJasperFiles {

        DUP_AQUA_BILL("Duplicate_Aqua_Bill.jrxml");

        private String colDescription;

        public String getColDescription() {
            return colDescription;
        }

        public void setColDescription(String colDescription) {
            this.colDescription = colDescription;
        }

        private DuplicateBillJasperFiles(String colDescription) {
            this.colDescription = colDescription;
        }

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
        String NOW_PREFIX = "NOW";
        String NOW_DESC = "Number of witness";
        String hitFrom = "DEPT";
        String PNG_EXTENSION = ".png";

        interface STATUS {
            String OPEN = "OPEN";
            String CONCLUDED = "CONCLUDED";
            String REOPEN = "REOPEN";
            String FORM_STATUS_DRAFT = "D";
        }
    }

    interface BILLCLOUD_PARAM {
        String BILLER_ID = "billerID"; // Merchant Short Name
        String SERVICE_ID = "serviceID"; // Merchant Service
        String FUND_ID = "fundID"; // Merchant Fund ID
        String CONSUMER_ID = "consumerID"; // Merchant Customer ID
        String BILLER_TRANSACTION_ID = "billerTransactionID"; // Unique Transaction ID generated by the Merchant
        String AMOUNT = "amount";
        String RESPONSE_URL = "responseUrl"; // URL on which merchants needs the transaction status
        String EDITABLE = "editable"; // Merchant wants to edit the transaction amount by consumer
        String NAME = "name";
        String ADDRESS = "address";
        String EMAIL = "email";
        String MOBILE = "mobile";
        String UNIT = "Consumer’s Administrative Unit (e.g. Ward, Zone…)"; // Additional Data can be added
        String SUBUNIT = "Consumer’s Administrative SubUnit"; // Additional Data can be added
        String LOCATION = "Location"; // checksum:-Concatenation of (CID + RID + CRN + AMT + key)
        String CUSTOMFIELD1 = "customField1";// Additional information
        String CUSTOMFIELD2 = "customField2";
        String CUSTOMFIELD3 = "customField3";
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

    interface BILLDESK {
        String MSG = "msg";
        String OPTIONS = "options";
        String ENABLE_CHILD_WINDOW_POSTING = "enableChildWindowPosting";
        String ENABLE_PAYMENT_RETRY = "enablePaymentRetry";
        String RETRY_ATTEMPT_COUNT = "retry_attempt_count";
        String TXT_PAY_CATEGORY = "txtPayCategory";
        String CALLBACKURL = "callbackUrl";

        String MERCHANTID = "MerchantID";
        String CUSTOMERID = "CustomerID";
        String TXNREFERENCENO = "TxnReferenceNo";
        String BANKREFERENCENO = "BankReferenceNo";
        String TXNAMOUNT = "TxnAmount";
        String BANKID = "BankID";
        String BANKMERCHANTID = "BankMerchantID";
        String TXNTYPE = "TxnType";
        String CURRENCYNAME = "CurrencyName";
        String ITEMCODE = "ItemCode";
        String SECURITYTYPE = "SecurityType";
        String SECURITYID = "SecurityID";
        String SECURITYPASSWORD = "SecurityPassword";
        String TXNDATE = "TxnDate";
        String AUTHSTATUS = "AuthStatus";
        String SETTLEMENTTYPE = "SettlementType";
        String ERRORSTATUS = "ErrorStatus";
        String ERRORDESCRIPTION = "ErrorDescription";
        String CHECKSUM = "CheckSum";

    }

    interface LEAVEMANAGEMENT {

        String EMPNAME = "empName";
        String EMPDESIGNATION = "empDesignation";
        String EMPREPORTINGTO = "empReportingTo";
        String TOTALLEAVEBALANCE = "totalLeaveBalance";
        String CATEGEORYWISELEAVEBALANCE = "catagoryWiseLeaveBal";
        String CL = "CL";
        String PL = "PL";
        String ML = "ML";
        String PIL = "PIL";
        String SL = "SL";
        String APPLY_LEAVE_SUCCESS = "Your Leave submitted successfully.";
        String APPLY_LEAVE_EXCEPTION = "Unable to save your leave request.";
        String LEAVE_BALANCE_EXCEPTION = "Unable to fetch Leave Balance.";
        String APPROVE_MSG = "Your Leave Approved Successfully.";
        String REJECTED_MSG = "Your Leave Rejected.";
        String ALREADY_APPROVE = "Your Leave Already Approved.";
        String REJECTED = "R";
        String APPROVED = "A";
        String DEFAULT_FLAG = "N";
        String LEAVE_TYPE_FH = "FH";
        String LEAVE_TYPE_SH = "SH";
        String LEAVE_COUNT = "LeaveCount";
        String LEAVE_ERR_LIST = "LeaveErrors";
    }

    interface Role {
        String OPERATOR = "OPERATOR";
        String DEVLOPER_AUTH_USER = "DEVLOPER_AUTH_USER";
    }

    interface ServiceEventName {
        String COMPLAINT_RESOLUTION = "Complaint Resolution";
    }

    interface TaskName {
        String COMPLAINT = "complaint";
        String CALL = "call";
        String GRIEVANCE = "grievance";
    }

    interface CFCServiceCode {
        String Nursing_Home_Reg = "NHR";
        String Hospital_Sonography_center = "HSR";
        String Tree_Cutting_service = "TCP";
        String NOC_For_Other_Govt_Dept = "NOC";
        String DUPULICATE_RECEIPT_PRINTING="DRP";
    }

    // 119534 Prefix added for SKDCL ENV Scheduling check
    interface SchedulingForTrx {
        String CSH = "CSH";
    }

    interface RAZORYPAY_PARAM {
        String ORDER_ID = "razorpay_order_id";
        String PAYMENT_ID = "razorpay_payment_id";
        String UDF3_VALUE = "CitizenHome.html";
        String Signature = "razorpay_signature";
        String SECRETKEY = "iv";
        String KEY = "key";
        String AMOUNT = "amount";
        String CURRENCY = "currency";
        String NAME = "name";
        String DESCRIPTION = "description";
        String DESCRIPTION_VALUE = "Transactional Payment";
        String IMAGE = "image";
        String IMAGE_VALUE = "NA";
        String ORDERID = "order_id";
        String CALLBACKURL = "callback_url";
        String EMAIL = "email";
        String CONTACT = "contact";
        String ADDRESS = "address";
        String ADDRESS_VALUE = "NA";
    }

    interface PASSWORD {
        int MIN_LENGTH = 6;
        int MAX_LENGTH = 20;
    }

    interface METER_MAS_COLUMNS{
    	String METER_MAKE = "mmMtrmake";
    	String METER_NO = "mmMtrno";
    	String INITIAL_READING = "mmInitialReading";
    	String MAX_READING = "maxMeterRead";
    	String INSTALL_DATE = "mmInstallDate";
    	String METER_COST= "mmMtrcost";
    	String OWNERSHIP  = "mmOwnership";
    	String IP_UPLOAD = "lgIpMacUpd";
    	String ORG_ID = "orgid";
    	String CS_IDN = "csIdn";
    }
    
    interface MaterialManagement {
    	String MaterialDeptCode = "SD";
    	String DeptIndentShortCODE = "DIR";
    	String STORE_Indent_SERVICE_CODE = "SIR";
    	String INDENT_RETURN_CODE="DR";
    }
    
    interface EncroachmentChallan {
    	String CHALLAN_SHORT_CODE = "ELC";
    	String CHALLAN_DEPT_SHORT_CODE = "ENC";
    	String RAID_NO = "RAID";
    	String CHALLAN_DETAILS_FORM = "ChallanDetailsForm";
    	String RAID_DETAILS_FORM = "RaidDetailsForm";
    	String ECHALLAN_ENTRY_SMS_URL = "EChallanEntry.html";
    	String ECHALLAN_PAYMENT_SMS_URL = "EChallanPayment.html";
    	String AGAINST_SEIZED_ITEMS = "AS";
    	String ON_SPOT_CHALLAN = "OS";
    }

	interface Sfac {
    	String FARMER_MASTER_FORM_HTML = "/FarmerMasterForm.html";
    	String FARMER_MASTER_FORM = "FarmerMaster";
    	String FARMER_MASTER_SUMMARY_FORM = "FarmerMasterSummary";
    	String ALLOCATION_OF_BLOCK_HTML = "/AllocationOfBlocks.html";
    	String ALLOCATION_OF_BLOCK_SUMMARY_FORM = "AllocationOfBlocksForm";
    	String ALLOCATION_OF_BLOCK_FORM = "AllocationOfBlocksAddForm";
    	String GET_ALLBLOCK_DATA = "getAllBlockData";
    	String GET_ALLBLOCK_DETAILS = "getAllBlockDetails";
    	String CBBO_MASTER_FORM_HTML="CBBOMasterForm.html";
    	String CBBO_MASTER_SUMMARY_FORM = "CBBOMasterSummaryForm";
    	String CBBO_MAST_SUMMARY_FORM_VALIDN = "CBBOMasterSummaryFormValidn";
    	String CBBO_MASTER_FORM = "CBBOMaster";
    	String IA_MASTER_FORM_HTML = "IAMasterForm.html";
    	String IA_MASTER_SUMMARY_FORM = "IAMasterSummaryForm";
    	String IAMAST_SUMMARY_FORM_VALIDN ="IAMasterSummaryFormValidn";
    	String IA_MASTER_FORM = "IAMasterForm";
    	String TB_FPO_MASTER ="TB_FPO_MASTER";
    	String FPO_REG_NO ="FPO_REG_NO";
    	String  GET_IA_DETAILS_BY_IDS="getIADetails";
    	String CHANGE_OF_BLOCK_HTML="ChangeofBlockForm.html";
    	String CHANGE_OF_BLOCK = "ChangeofBlock";
    	String CHANGE_OF_SUMMARY_BLOCK = "ChangeofBlockSummary";
    	String CHANGE_OF_BLOCK_VALIDN = "ChangeofBlockValidn";
    	String CBR ="CBR";
    	String SAVE_BLOCK_DETAILS="saveBlockDetails";
    	String GET_VACANT_BLOCK_LIST = "getVacantBlockList";
    	String CHANGE_OF_BLOCK_APPROVAL ="ChangeofBlockApproval.html";
    	String SHOWDETAILS = "showDetails";
    	String APP_NO = "appNo";
        String ACTUAL_TASKID = "actualTaskId";
        String TASK_ID = "taskId";
        String WORKFLOW_ID = "workflowId";
        String TASK_NAME = "taskName";
        String CHANGE_BLOCK_APPROVAL ="changeBlockApproval";
        String  STATE_INFO_MAST_HTML = "StateInformationMaster.html";
        String STATE_INFO_MAST_SUMMARY ="StateInfoMastSummaryForm";
        String STATE_INFO_MAST_FORM ="StateInfoMastForm";
        String EDIT_AND_VIEW_FORM ="editAndViewForm";
        String NPMA = "NPMA";
        String CB="CB";
        String SAVE_ALLOCATION_OF_BLOCK_FORM ="saveAllocationOfBlockForm";
        String EQUITY_GRANT_REQUEST = "EquityGrantRequest";
        String EQUITY_GRANT_REQUEST_HTML = "EquityGrantRequest.html";
        String MEETING_MASTER_FORM_HTML = "MeetingMasterForm.html";
        String MEETING_MASTER_FORM = "MeetingMasterForm";
        String MEETING_MASTER_FORM_VALIDN = "MeetingMasterFormValidn";
        String MEETING_MASTER_SUMMARY_FORM = "MeetingMasterSummaryForm";
        String ADD_MEETING="addMeetingDetails";
        String SFAC = "SFAC";
        String TB_SFAC_MEETING_MASTER = "TB_SFAC_MEETING_MASTER";
        String MEETING_NO = "MEETING_NUMBER";
        String FILE_UPLOAD_IDENTIFIER = "SMM";
        String MEETING_PRINT_PAGE = "meetingPrintPage";
        String IA="IA";
        String CBBO="CBBO";
        String FPO="FPO";
        String CBBO_ASSESEMENT_ENTRY_HTML ="CBBOAssessmentEntry.html";
        String CBBO_ASS_ENTRY_SUMMARY = "CBBOAssesementEntrySummary";
        String CBBO_ASS_ENTRY = "CBBOAssesementEntry";
        String  MILESTONE_ENTRY_FORM_HTML ="MilestoneEntryForm.html";
        String MILESTONE_ENTRY_FORM ="MilestoneEntryForm";
        String MILESTONE_ENTRY_SUMMARY ="MilestoneEntrySummary";
        String MILESTONE_ENTRY_SUMMARY_VALIDN ="MilestoneEntrySummaryValidn";
        String  MILESTONE_COMP_FORM_APPROVAL_HTML ="MilestoneCompletionApproval.html";
        String  MILESTONE_COMP_FORM_APPROVAL ="MilestoneComletionApprovalForm";
        String  MILESTONE_COMP_FORM_HTML ="MilestoneCompletionForm.html";
        String MILESTONE_COMP_FORM ="MilestoneCompletionForm";
        String MILESTONE_COMP_SUMMARY ="MilestoneCompletionSummary";
        String MILESTONE_COMP_SUMMARY_VALIDN ="MilestoneCompletionSummaryValidn";
        
        String CBBO_ASS_APPROVAL_HTML ="CBBOAssesementApproval.html";
        
        String FPO_MGMT_COST_FORM = "FPOManagementCostMaster";
		String FPO_MGMT_COST_SUMMARY_FORM = "FPOManagementCostSummary";
		String FPO_MGMT_COST_SUMMARY_FORM_VALID = "FPOManagementCostSummaryValid";
		String FPO_MGMT_COST_FORM_HTML = "FPOManagementCost.html";
		String CAE = "CAE";
		String TB_SFAC_ASSESSMENT_MASTER ="TB_SFAC_ASSESSMENT_MASTER";
		String ASSESSMENT_NO = "ASSESSMENT_NO";
		String ASSNO = "AST";
		String SENT_FOR_APPROVAL = "sendForApproval";
		String CHECK_STATUS_APPROVAL = "checkStausApproval";
		String ASSESSMENT_APPROVAL_URL = "AssessmentEntryApproval.html";
		String SET_DECISION_SUBMITTED = "SUBMITED";
		
		String CGF_REQUEST = "CreditGrantEntry";
		String CGF_REQUEST_SUMMARY_FORM = "CreditGrantEntrySummary";
		String CGF_REQUEST_SUMMARY_VALIDN = "CreditGrantEntryValidn";
		String CGF_REQUEST_HTML = "CreditGrantEntry.html";
		
		
		String EQUITY_GRANT_REQUEST_SUMMARY_FORM = "EquityGrantRequestSummary";
		String EQUITY_GRANT_REQUEST_SUMMARY_VALIDN = "EquityGrantRequestValidn";
		
		String COMMITTEE_MEMBER_MASTER_URL = "CommitteeMemberMaster.html";
		String COMMITTEE_MEMBER_MAST_SUMMARY ="committeeMemberMastSummaryForm";
		String COMMITTEE_MEMBER_MAST_FORM ="committeeMemberMastForm";
		String COMMITTEE_MEMBER_SUMMARY_VALIDN ="committeeMemberSummaryFormValidn";
		String FPO_MASTER_APPROVAL_CONTROLLER="FpoMastApprovalController.html";
		String FPO_MASTER_APPROVAL="";
		String PRINT_ASSESSMENT_DETAILS ="printAssessMentDetails";
		String APPROVED = "Approved";
		String NOTAPPROVED = "Not Approved";
		String FPO_ASSESSMENT_ENTRY = "FPOAssessmentEntry.html";
		String FPO_ASSESSMENT_SUMMARY_FORM = "FPOAssessmentSummary";
		String FPO_ASSESSMENT_FORM = "fpoAssessmentEntry";
		String FPO_ASSESSMENT_APPROVAL = "FPOAssessmentApproval.html";
		String FAE = "FAE";
		String TB_SFAC_FPO_ASS_MASTER ="TB_SFAC_FPO_ASS_MASTER";
		String FPO_ASSESSMENT_NO = "ASSESSMENT_NO";
		String FPO_ASSNO = "AST";
		
		String CIRCULAR_NOTIFY_FORM_HTML = "CircularNotificationForm.html";
		String CIRCULAR_NOTIFY_FORM = "CircularNotificationForm";
		String CIRCULAR_NOTIFY_SUMMARY ="CircularNotificationSummary";
	    String CIRCULAR_NOTIFY_SUMMARY_VALIDN ="CircularNotificationSummaryValidn";
	    String CBBO_MASTER_APPROVAL_HTML="CBBOMasterApproval.html";
	    String CBBO_MASTER_APPROVAL_FORM ="cbboMasterApprovalForm";
	    String MEETING_CALENDAR_FORM ="MeetingCalendar.html";
	    String ORDER_BY_MEETING_DATE = "MEETING_DATE";
	    
	    String CBBO_FIELD_STAFF_FORM_HTML = "CBBOFiledStaffDetailForm.html";
		String CBBO_FIELD_STAFF_FORM = "CBBOFiledStaffDetailForm";
		String CBBO_FIELD_STAFF_FORM_VALIDN = "CBBOFiledStaffDetailFormValidn";
		String CBBO_FIELD_STAFF_SUMMARY ="CBBOFiledStaffDetailSummary";
	    String CBBO_FIELD_STAFF_SUMMARY_VALIDN ="CBBOFiledStaffDetailSummaryValidn";
	    
	    String FUND_RELEASE_REQ_FORM_HTML = "FundReleaseRequestForm.html";
		String FUND_RELEASE_REQ_FORM = "FundReleaseRequestForm";
		String FUND_RELEASE_REQ_SUMMARY ="FundReleaseRequestSummary";
	    String FUND_RELEASE_REQ_SUMMARY_VALIDN ="FundReleaseRequestSummaryValidn";
	    String  FUND_RELEASE_REQ_FORM_APPROVAL_HTML ="FundReleaseRequestFormApproval.html";
        String  FUND_RELEASE_REQ_FORM_APPROVAL ="FundReleaseRequestApprovalForm";
	    
	    String DPR_ENTRY_REQ_FORM_HTML = "DPREntryForm.html";
		String DPR_ENTRY_REQ_FORM = "DPREntryForm";
		String DPR_ENTRY_REQ_FORM_APPROVAL_HTML = "DPREntryApprovalForm.html";
		String DPR_ENTRY_REQ_FORM_APPROVAL = "DPREntryApprovalForm";
		String DPR_ENTRY_REQ_SUMMARY = "DPREntrySummary";
		String DPR_ENTRY_REQ_SUMMARY_VALIDN ="DPREntrySummaryValidn";
		
		String ABS_ENTRY_REQ_FORM_HTML = "ABSEntryForm.html";
		String ABS_ENTRY_REQ_FORM = "ABSEntryForm";
		String ABS_ENTRY_REQ_FORM_APPROVAL_HTML = "ABSEntryApprovalForm.html";
		String ABS_ENTRY_REQ_FORM_APPROVAL = "ABSEntryApprovalForm";
		String ABS_ENTRY_REQ_SUMMARY = "ABSEntrySummary";
		String ABS_ENTRY_REQ_SUMMARY_VALIDN ="ABSEntrySummaryValidn";
		
		String FUND_REL_BPM_SHORTCODE = "FRR";
		String MILESTONE_COM_BPM_SHORTCODE = "MSC";
		String DPR_ENTRY_BPM_SHORTCODE = "DPR";
		String ABS_ENTRY_BPM_SHORTCODE = "ABS";
	    
		
    }

	interface doonComplaintType{
    	String Dead_Animal = "Dead animal";
    	String Dustbins_not_cleaned = "Dustbins not cleaned"; 
    	String Garbage_dump ="Garbage dump";
    	String Garbage_vehicle_not_arrived ="Garbage vehicle not arrived";
    	String Sweeping_not_done = "Sweeping not done";
    	String No_electricity_in_public_toilet = "No electricity in public toilet";
    	String No_water_supply_in_public_toilet = "No water supply in public toilet";
    	String Public_toilet_blockage = "Public toilet blockage";
    	String Public_toilet_cleaning = "Public toilet cleaning";
    	String Open_Manholes_Or_Drains = "Open Manholes Or Drains";
    	String Overflow_of_Sewerage_or_Storm_Water = "Overflow of Sewerage or Storm Water";
    	String Stagnant_Water_On_The_Road = "Stagnant Water On The Road";
    	String Improper_Disposal_of_FecalWaste_Septage ="Improper Disposal of Fecal waste septage";
    	String Debris_Removal_Construction_Material = "Debris Removal Construction Material";
    	String Burning_Of_Garbage_In_Open_Space = "Burning Of Garbage In Open Space";
    	String Open_Defecation = "Open Defecation";
    	String Overflow_of_Septic_Tanks  = "Overflow of Septic Tanks";
    	String Yellow_Spot = "Yellow Spot";
    }
	
	interface waterDashboard{
		String CONNECTION_TYPE="connectionType";
		String METER_TYPE="meterType";
		String USAGE_TYPE="usageType";
		String PAY_MODE="paymentChannelType";
		String TAX_GROUP="taxHeads";
		
	}
    
	interface propertyDashboard{
		String APPLICATION_STATUS="applicationStatus";
		String FINANCIAL_YEAR="financialYear";
		String USAGE_TYPE="usageCategory";
		String PAY_MODE="paymentChannelType";
		String TAX_GROUP="taxHeads";
		String PENALTY="Penalty";
		String INTEREST="Interest";
		String REBATE="Rebate";
		String CESS="Cess";
		
	}
	
    interface ComplaintType {
        static String getComplaintId(String complaintType) {
            String complaintId = "1";
            switch (complaintType) {
            
            case doonComplaintType.Dead_Animal:
            	complaintId = "1";
                break;
            
            case doonComplaintType.Dustbins_not_cleaned:
            	complaintId = "2";
                break;
                
            case doonComplaintType.Garbage_dump:
            	complaintId = "3";
                break;
                
            case doonComplaintType.Garbage_vehicle_not_arrived:
            	complaintId = "4";
                break;
                
            case doonComplaintType.Sweeping_not_done:
            	complaintId = "5";
                break;
                
            case doonComplaintType.No_electricity_in_public_toilet:
            	complaintId = "6";
                break;
                
            case doonComplaintType.No_water_supply_in_public_toilet:
            	complaintId = "7";
                break;
                
            case doonComplaintType.Public_toilet_blockage:
            	complaintId = "8";
                break;
                
            case doonComplaintType.Public_toilet_cleaning:
            	complaintId = "9";
                break;
                
            case doonComplaintType.Open_Manholes_Or_Drains:
            	complaintId = "10";
                break;
                
            case doonComplaintType.Overflow_of_Sewerage_or_Storm_Water:
            	complaintId = "11";
                break;
                
            case doonComplaintType.Stagnant_Water_On_The_Road:
            	complaintId = "12";
                break;
                
            case doonComplaintType.Improper_Disposal_of_FecalWaste_Septage:
            	complaintId = "13";
                break;
                
            case doonComplaintType.Debris_Removal_Construction_Material:
            	complaintId = "14";
                break;
                
            case doonComplaintType.Burning_Of_Garbage_In_Open_Space:
            	complaintId = "15";
                break;
                
            case doonComplaintType.Open_Defecation:
            	complaintId = "16";
                break;
            
            case doonComplaintType.Overflow_of_Septic_Tanks:
            	complaintId = "17";
                break;
            
            case doonComplaintType.Yellow_Spot:
            	complaintId = "18";
                break;
                
            }
            return complaintId;
        }

    }
    
	interface BuildingPlanning {
		String PROF_REG_FORM_HTML = "/ProfessionalRegistrationForm.html";
		String PROF_REG_FORM_URL ="ProfessionalRegistrationApproval.html";
		String PROF_REG_APPROVAL_HTML = "/ProfessionalRegistrationApproval.html";
		String PREFIX_AWZ = "AWZ";
		String PREFIX_DDZ = "DDZ";
		String PROF_REG_FORM = "ProfessionalRegistrationForm";
		String PROF_REG_FORM_VALIDN = "ProfessionalRegistrationFormValidn";
		String GET_CHECKLIST = "getCheckList";
		String SERVICE_CODE_TPPR = "TPPR";
		String TCP_CHECKLIST = "ChecklistModel";
		String SHOWDETAILS = "showDetails";
		String SAVE_AUTHORIZATION ="saveAuthorization";
		String APP_NO = "appNo";
        String ACTUAL_TASKID = "actualTaskId";
        String TASK_ID = "taskId";
        String NEW_LICENSE_SERVICE = "NL";
        String ARCHITECT = "Architect";
        String SUPERVISOR = "Supervisor";
        String STRUCTURE_ENGINEER = "Structure engineer";
        String PROOF_CONSULTANT = "Proof Consultant";
        String ENGINEER = "Engineer";
        String CIVIL_ENGINEER = "Civil Engineer";
        String DATE_FORMAT = "yyyy/MM/dd";
	}
	
}