package com.abm.mainet.constant;

public interface MainetConstants {

    String AUTO_SCAN = "Y";
    String APPLICABLE = "A";
    int LEVEL_ONE = 1;
    int LEVEL_TWO = 2;
    int INDEX_ZERO = 0;
    int INDEX_ONE = 1;
    String MAINET_SERVICE_URL_KEY = "mainet.service.rest.url";
    String ESCALATION_URL = "Escalation.html";

    /**
     * set of array for Hierarchical and Non-Hierarchical prefix which are being used in BRMS NOTE: provide only those prefix
     * which are being used by BRMS
     */
    String NON_HIERARCHICAL_PREFIX[] = { "APL", "FSD", "CAA", "TAG", "SLD", "SET" };
    String HIERARCHICAL_PREFIX[] = { "TAC" };
    String NON_REPLICATED_PREFIX[] = { "APL", "FSD", "CAA", "TAG", "SLD", "SET" };
    
    interface tasks {
    	String PENDING= "PENDING";
    	String COMPLETED= "COMPLETED";
    	String EXITED="EXITED";
    }
    
    interface Common {
        String BLANK = "";
        String NA = "NA";
        String WHITE_SPACE = " ";
        String DATE_FORMAT = "dd/MM/yyyy";
        String TIME_FORMAT = "hh:mm a";
        String DATE_TIME_FORMAT = DATE_FORMAT + " " + TIME_FORMAT;
        String REJECTED_MSG = "Rejected by : ";
    }

    interface Lang {
        int ENG = 1;
        int REG = 2;
    }

    interface ActiveStatus {
        String ACTIVE = "A";
        String INACTIVE = "I";

    }

    interface EntityFields {
        String ORG_ID = "orgId";
        String SERVICE_EVENT_ID = "serviceEventId";
        String ACTOR_ID = "actorId";
        String EMPID = "empId";
        String SERVICE_EVENT_NAME = "serviceEventName";
        String TASK_STATUS = "taskStatus";
        String DATE_OF_ASSIGNMENT = "dateOfAssignment";
        String ESCALATED_REMARK = "Auto escalation";
        String DECISION = "lastDecision";
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
        String NO_CONTENT= "204";
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

    interface Prefix {
        String FSD = "FSD";
        String CAA = "CAA";
        String SLD = "SLD";
        String TAC = "TAC";

    }

    interface LookUp {
        String HIERARCHICAL = "H";
        String NON_HIERARCHICAL = "N";
        String HIRACHICAL_LEVEL_MAP = "HIRACHICAL_LEVEL_MAP";
        String HIRACHICAL_DETAIL_MAP = "HIRACHICAL_DETAIL_MAP";
        String NON_HIRACHICAL_DETAIL_MAP = "NON_HIRACHICAL_DETAIL_MAP";
        String HIRACHICAL_LIST = "HIRACHICAL_LIST";
        String NON_HIRACHICAL_LIST = "NON_HIRACHICAL_LIST";
        String DISTRICT = "DIS";
        String MEDIA_TYPE = "CMT";
        String RTI_INFORMATION_TYPE = "RIT";
        String TOOL_BAR_IMAGE = "TIM";
        String TITLE = "TTL";
        String APPLICANT_TYPE = "APT";
        String INDIVIDUAL = "I";
        String CHARGE_MASTER_CAA = "CAA";
        String CHARGE_MASTER_CHT = "CHT";
        String CHARGE_MASTER_SLD = "SLD";
        String RTI_CHARGE_TYPE = "CTR";
        String FLAT_SLAB_DEPEND = "FSD";

        String FACTOR_VAL_TYPE = "VTY";

        String PBD = "PBD";
        String ITSCALLTYPE = "CTT";
        String SLIDING_IMAGE = "EFI";
        String USAGE_TYPE = "UTP";
        String ACS = "ACS";
        String ACN = "ACN";

        interface Bnd {
            String AUTHO_FLAG_STATUS = "Y";
            String FREE_SERVICE_FLAG = "F";
        }
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
        String CIRCUMFLEX = "^";
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

    interface Organisation {
        String SUPER_ORG_STATUS = "Y";
        String MUNICIPAL_ORG_STATUS = "N";
        String ORGN = "organisation";

    }

    interface Rules {

        interface Model {
            String CHECKLIST_MODEL = "CHECKLISTMODEL";
            String CONSUMPTION = "CONSUMPTION";
            String WATER_RATE_MASTER = "WATERRATEMASTER";
            String WATER_TAX_CALCULATION = "WATERTAXCALCULATION";
            String NO_OF_DAYS = "NOOFDAYS";
            String RNL_RATE_MASTER = "RNLRATEMASTER";
            String RTI_RATE_MASTER = "RTIRATEMASTER";
            String FACTOR_MASTER_MODEL = "FACTORMASTERMODEL";
            String ALV_MASTER_MODEL = "ALVMASTERMODEL";
            String PROPERTY_RATE_MASTER_MODEL = "PROPERTYRATEMASTERMODEL";
            String PROPERTY_TAX_DATA_MODEL = "PROPERTYTAXDATAMODEL";
            String SWM_RATE_MASTER = "SWMRATEMASTER";
            String CONSUMPTION_AQUA = "CONSUMPTION_AQUA";
            String WATER_RATE_MASTER_AQUA = "WATERRATEMASTER_AQUA";
            String WATER_TAX_CALCULATION_AQUA = "WATERTAXCALCULATION_AQUA";
            String ROAD_CUTTING_RATE_MASTER = "ROADCUTTINGRATEMASTER";
            String ADH_RATE_MASTER = "ADHRATEMASTER";
            String MRM_RATE_MASTER = "MRMRATEMASTER";
            String BND_RATE_MASTER = "BNDRATEMASTER";
            String ADDITIONAL_SERVICES_MODEL = "ADDITIONALSERVICESMODEL";
            String BPMS_RATER_MASTER="BPMSRATEMASTER";
        }

        interface Artifacts {
            String CHECKLIST = "CheckList";
            String WATER_TAX = "WaterTax";
            String WATER_RATE_MASTER = "WaterRateMaster";
            String RNL_RATE_MASTER = "RNLRateMaster";
            String PROPERTY_ALV_MASTER = "PropertyALVMaster";
            String PROPERTY_FACTOR_MASTER = "PropertyFactorMaster";
            String PROPERTY_RATE_MASTER = "PropertyRateMaster";
            String PROPERTY_SDDR_RATE = "PropertySDDRRate";
            String RTI_RATE_MASTER = "RtiRateMaster";
            String TDS_CALCULATION = "TDSCalculation";
            String SWM_RATE_MASTER = "SWMRateMaster";
            String WMS_RATE_MASTER = "WMSRateMaster";
            String AQUA = "Aqua";
            String MLNewTradeLicense = "MarketLicense";
            String ADH_RATE_MASTER = "ADHRateMaster";
            String MRM_RATE_MASTER = "MRMRateMaster";
            String BND_RATE_MASTER = "BNDRateMaster";
            String ADDITIONAL_SERVICES_RATE_MASTER = "AdditionalServicesRateMaster";
            String BPMS_RATE_MASTER="BPMSRateMaster";
        }

        interface RuleParameter {
            String CLASSTYPE_ROOT = "drools.dataobject.class.";
        }

        interface ErrorMessages {
            String RUNITME_EXCEPTION = "Exception while firing rules!";
            String INVALID_MODEL_OR_IMPROPER_PIPE_DATA = "Invalid model name! Model name should be null/Empty. Multiple model name can be passed  separated by '|' as delimiter";
            String INVALID_ORGID_SERVICE_CODE = "Invalid serviceCode/orgId! Service code should not be null/empty and orgId can not be zero(0)";
            String NO_CHECKLIST_FOUND = "No checklist found!";
            String TASK_NOT_FOUND = "TNF";
            String UNKNOWN_USER = "UKU";
        }
    }

    interface WorkFlow {

        public enum Task {
            SIGNAL("Signal "), START("Start"), CSO("Call Center"),GRIEVANCE("Complaint Resolution"),HIDDEN("Hidden"), LOI_Payment("Payment");

            private String value;

            private Task(String value) {
                this.value = value;
            }

            public String getValue() {
                return value;
            }
        }

        interface ImplementationService {
            String JBPM = "jbpm";
            String ORACLE = "oracle";
        }

        interface ImplementationServiceStatuses {
            interface Jbpm {
                String READY = "Ready";
                String RESERVED = "Reserved";
                String INPROGRESS = "InProgress";
            }
        }

        public enum Status {
            STARTED("STARTED"), INPROCESS("IN_PROCESS"), CLOSED("CLOSED"), PENDING("PENDING"), EXPIRED("EXPIRED"), COMPLETED(
                    "COMPLETED"), ESCALATED("ESCALATED"), EXITED("EXITED"), START("START");
            private String value;

            private Status(String value) {
                this.value = value;
            }

            public String getValue() {
                return value;
            }

        }

        interface BpmTaskStatus {
            static String getStatus(String sts) {
                String status = "";
                switch (sts) {
                case "Completed":
                    status = "COMPLETED";
                    break;
                case "Exited":
                    status = "EXITED";
                    break;
                case "Failed":
                    status = "FAILED";
                    break;
                case "Created":
                case "Ready":
                case "Reserved":
                case "InProgress":
                case "Suspended":
                    status = "PENDING";
                    break;
                }
                return status;
            }
        }

        public enum Decision {
            SUBMITTED("SUBMITTED"), APPROVED("APPROVED"), HOLD("HOLD"), FORWARD_TO_DEPARTMENT(
                    "FORWARD_TO_DEPARTMENT"), FORWARD_TO_EMPLOYEE("FORWARD_TO_EMPLOYEE"), REJECTED(
                            "REJECTED"), FORCE_CLOSURE("FORCE_CLOSURE"), REOPENED("REOPENED"), PENDING("PENDING"), SEND_BACK("SEND_BACK"), ESCALATED("ESCALATED"),
            					FORWARD_TO_ORGANISATION("FORWARD_TO_ORGANISATION"),LOCATION("LOCATION"), SEND_TO_CLERK("SEND_TO_CLERK"),
            					LOI_DELETED("LOI_DELETED");

            private String value;

            private Decision(String value) {
                this.value = value;
            }

            public String getValue() {
                return value;
            }
        }

        interface ProcessVariables {
            String TASK_ASSIGNMENT = "taskAssignment";
            String RESTAPI = "RESTAPI";
            String TARGET_USER_ID = "targetUserId";
            String FAULT_DATA = "faultData";
            String PROCESSNAME = "processName";
        }

        interface WorkflowParameter {
            String CLASSTYPE_ROOT = "workflow.dataobject.class.";
        }

        interface WorkflowExecutionParameters {
            String CHECKER_LEVELS = "checkerLevels";
            String CHECKER_LEVEL_GROUPS = "checkerLevelGroups";
            String APPLICATION_METADATA = "applicationMetadata";
            String TASK_ASSIGNMENT = "taskAssignment";
            String REQUESTER_TASK_ASSIGNMENT = "requesterTaskAssignment";
        }
    }

}
