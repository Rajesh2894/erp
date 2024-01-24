package com.abm.mainet.constant;

public interface MainetConstants {

	String AUTO_SCAN = "Y";
	String APPLICABLE = "A";
	int LEVEL_ONE = 1;
	int LEVEL_TWO = 2;
	int INDEX_ZERO = 0;
	int INDEX_ONE = 1;
	String MAINET_SERVICE_URL_KEY = "mainet.service.rest.url";
	String NOT_APPLICABLE="NA";

	interface Common {
		String BLANK = "";
		String WHITE_SPACE = " ";
		String DATE_FORMAT = "dd/MM/yyyy";
		String TIME_FORMAT = "hh:mm a";
		String DATE_TIME_FORMAT = DATE_FORMAT + " " + TIME_FORMAT;
	}

	interface Lang {
		int ENG = 1;
		int REG = 2;
	}

	interface ActiveStatus {
		String ACTIVE = "A";
		String INACTIVE = "I";

	}

	interface TaxCategory {
		String DEMAND = "N";
	}

	interface Status {
		String SUCCESS = "success";
		String FAIL = "fail";
	}

	interface Regex {
		String SPECIAL_CHAR = "[$&+,:;=?@#]";
		String NUMERIC_PATTERN = "[0-9]+";
		String CSV = "\\s*,\\s*";
		String PIPE = "\\|";
	}

	interface StatusCode {
		String INTERNAL_SERVER_ERROR = "500";
	}

	interface Numbers {
		int MINUS_ONE = -1;
		int ZERO = 0;
		int ONE = 1;
		int TWO = 2;
		int THREE = 3;
		int FOUR = 4;
		int FIVE = 5;
		int SIX = 6;
		int SEVEN = 7;
		int EIGHT = 8;
		int NINE = 9;
		Long MINUS_ONE_LONG = -1L;
		Long ZERO_Long = 0L;
		Long ONE_LONG = 1L;
		Long TWO_LONG = 2L;
	}

	interface IsLookUp {
		String ACTIVE = "A";
		String INACTIVE = "I";

		interface STATUS {
			String YES = "Y";
			String NO = "N";
		}
	}

	interface COMMON_STATUS {
		String SUCCESS = "success";
		String FAILURE = "failure";
		String FAIL = "fail";
		String SAVE_OK = "save.ok";
		String STATUS = "status";
		String MESSAGE = "message";
		String CAUSE = "cause";

	}

	interface OPERATOR {

		String EQUAT_TO = "=";
		String QUE_MARK = "?";
		String COLON = ":";
		String AMPERSENT = "&";
		String HASH = "#";
		String DOT = ".";
		String EMPTY = "";
		String UNDER_SCORE = "_";
		String COMMA = ",";
		String MINUS = "-";
		String PER = "%";
		String FORWARD_SLACE = "/";
		String COMMA_WITH_SPACE = " ,";
		String AT = "@";
	}

	interface LOGGING {

		String START_TIME_METHOD_NAME = "Start--------" + "MethodName-------";
		String END_TIME_METHOD_NAME = "End--------" + "MethodName---------";
		String START_TIME_MILLI_SECOND = "---------" + "StartTime MiliSecond --------";
		String END_TIME_MILLI_SECOND = "---------" + "EndTime MiliSecond ----------";
		String USER_SESSION = "userSession";
		String START_TIME = "startTime";
		String ORGID = "ORGID";
		String USER = "USER";
		String REQ_UUID = "requestUUID";
		String LOGGING_URL = "URL";
		String NULL = "NULL";
		String BEFORE_REQUEST_LOGGING = "Before request logging";
		String DURATION = "duration";
		String AFTER_REQUEST_LOGGING = "After request logging";
		String BEFORE_METHOD_LOGGING = "Enter : {}() with argument[s] = {}";
		String AFTER_METHOD_LOGGING = "Exit after {}ms : {}() with result = {}";
		String AFTER_METHOD_ERROR = "Exception after {}ms in {}() with cause = \'{}\' and exception = \'{}\'";
	}

	interface WorkFlow {
	}

	interface URLParameterName {
		String USERNAME = "u";
		String PASSWORD = "pw";
	}

	interface Responce {
		String JSON = "format=json";
	}

	interface STRING {
		String WORKSPACE = "workspace";
		String SPACESSTORE = "SpacesStore";
		String ALF_TICKET = "alf_ticket";
	}

	interface CMIS {
		String CMIS_DOCUMENT = "cmis:document";
		String CMIS_FOLDER = "cmis:folder";

	}
	interface EXCEPTION{
		String EXCEPTION_OCCURED = "**Exception occured**";
	}

	interface ASPECT {
		String ASPECT_REMOTE_REFERENCE_ID = "abm:remoteReferenceIds";
	}

	interface PROPERTIES {
		String PROP_PROJECTREFERID = "abm:projectReferId";
		String PROP_APPLICATIONID = "abm:applicationId";
	}
	
	interface prefix{
		String PREFIX="P:";
		String ASPECT="dc:";
		String PRIMARY_METADATA="primary";
		String DOC_TYPE="docType";
		String DEPT_NAME="deptName";
	}
	
	interface DmsErrorCodes{
		String E101="E101";
		String E102="E102";
		String E103="E103";
		String E104="E104";
	}
}
