package com.abm.mainet.common.constant;

import com.abm.mainet.common.constant.MainetConstants.WorkFlow.Decision;

public class PrefixConstants {

    /**
     * Use this to declare prefix in order to find LookUp object for that Prfix
     */
    public static interface LookUpPrefix {
        String PAY_AT_COUNTER = "PAY";
        String TFM = "TFM";
        String CAA = "CAA";
        String APL = "APL";
        String CLG = "CLG";
        String TAC = "TAC";
        String SMT = "SMT";
        String RCPT = "RCPT";
        String LPT = "LPT";
        String CLR = "CLR";
        String RND = "RND";
        String DNC = "DNC";
        String RRM = "RRM";
        String CHK = "CHK";
        String NTP = "NTP";
        String RRN = "RRN";
        String PRT = "PRT";
        String ELC = "ELC";
        String UDN = "UDN";
    }

    public static interface LookUp {
        String HIERARCHICAL = "H";
        String NON_HIERARCHICAL = "N";
        String HIRACHICAL_LEVEL_MAP = "HIRACHICAL_LEVEL_MAP";
        String HIRACHICAL_DETAIL_MAP = "HIRACHICAL_DETAIL_MAP";
        String NON_HIRACHICAL_DETAIL_MAP = "NON_HIRACHICAL_DETAIL_MAP";
        String HIRACHICAL_LIST = "HIRACHICAL_LIST";
        String NON_HIRACHICAL_LIST = "NON_HIRACHICAL_LIST";
        String DISTRICT = "DIS";
        String TOOL_BAR_IMAGE = "TIM";
        String TITLE = "TTL";
        String APT = "APT";
        String INDIVIDUAL = "I";
        String CHARGE_MASTER_CAA = "CAA";
        String FLAT_SLAB_DEPEND = "FSD";
        String ACN = "ACN";
        String OPN = "OPN";
        String BPT = "BPT";
        String LIS="LIS";
    }

    public static interface IsLookUp {
        String ACTIVE = "A";
        String INACTIVE = "I";

        interface STATUS {
            String YES = "Y";
            String NO = "N";
        }
    }

    public static interface NEC {
        String PARENT = "NEC";
        String CITIZEN = "C";
        String BUILDER = "B";
        String ARCHITECT = "A";
        String ENGINEER = "G";
        String EMPLOYEE = "E";
        String ADVOCATE = "L";
        String ADVERTISE = "AGN";
        String TOWN_PLANNER = "D";
        String STRUCTURAL_ENGINEER = "F";
        String SUPERVISOR = "E";
    }

    public static interface PaymentMode {
        String USER_ADUSTMENT = "U";
        String CASH = "C";
        String CHEQUE = "Q";
        String BANK = "B";
        String PAYORDER = "P";
        String Cash = "Cash";
        String Bank = "Bank";
        String Cheque = "Cheque";
        String DEMAND_DRAFT = "D";
        String POSTALCARDPAYMENT="PCP";
		String POS = "POS";
		String CARD = "CRD";
		String WALLET = "WAL";
		String UPI = "UPI";
		String BHARATQR = "BQR";
		String MCARD = "MCARD";
		String MCASH = "MCASH";
		String WEB = "W";
    }

    public static interface SMS_EMAIL {
        String APPROVAL = "A";
        String REJECTED = "R";
        String HOLD = "H";
        String OTP_MSG = "O";
    }

    public static interface SMS_EMAIL_ALERT_TYPE {

        String APPROVAL = "AA";
        String REJECTED = "AR";
        String SUBMITTED = "AS";
        String HOLD = "AF";
        String OTP_MSG = "OM";
        String GENERAL_MSG = "GM";
        String TASK_NOTIFICATION = "TN";
        String REJECTED_MSG = "RM";
        String COMPLETED_MESSAGE = "CM";
        String SCHEDULED_MESSAGE = "SM";
        String FORWARD_TO_DEPARTMENT = "FD";
    }

    public static interface SMS_EMAIL_ALERT_TYPE_BY_DECISION {

        static String getSmsEmailAlertType(String decision) {
            String type = null;
            switch (decision) {
            case Decision.SUBMITTED:
                type = "AS";
                break;
            case Decision.REOPENED:
                type = "AS";
                break;
            case Decision.APPROVED:
                type = "AA";
                break;
            case Decision.REJECTED:
                type = "AR";
                break;
            case Decision.HOLD:
                type = "AF";
                break;
            }
            return type;
        }
    }

    public static interface D2KFUNCTION {
        String ENGLISH_DESC = "E";
        String REG_DESC = "R";
        String CPD_VALUE = "V";
        String CPD_OTHERVALUE = "O";

    }

    public static interface NewWaterServiceConstants {

        String SCRUTINY = "SCL";
        String BILL = "BILL";
        String CAA = "CAA";
        String TRF = "TRF";
        String WNC = "WNC";
        String NO = "N";
        String YES = "Y";
        String SUCCESS = "success";
        String FAIL = "fail";
        String WWZ = "WWZ";
        String DEFAULT_EXCEPTION_VIEW = "defaultExceptionFormView";
        String APP_DTO_DWZID = "applicantDetailDto.dwzid";
        String GAP_CODE = "GAP";
        String METER_STATUS = "MST";
        String WMN = "WMN";
        String WMO = "WMO";
        String RDC = "RDC";
        String CO = "CO";
        String WND = "WND";
        String BPW = "BPW";
        String BILL_RECEIPT = "BR";
        String SWR = "SWR";
        String CCN_TYP_SEWER = "SEWER";
        String CCN_TYP_WTR = "WATER";
        String PENALTY = "Penalty";
        String RD = "RD";
        String SEWER = "Sewer";
        String WATER = "Water";
        String CUSTOM = "C";
        String SCHEDULE = "S";
        String CHECK_PROPERTY_DUES="CPD";
        String AT_TIME_OF_SCRUTINY="TOS";
        String AT_TIME_OF_APPLICATION="TOA";
        String BOTH="BOT";
        String RR = "RR";
		String APPLICATION = "Application";
		String SURCHARGE = "Surcharge";
		String ADVANCE_PAYMENT = "Advance Payment";
		String ARREAR = "A";

        interface Water_prefix {
            String BSC = "BSC";
        }
    }

    public static interface TAX_CATEGORY {
        String DEMAND = "N";
        String NOTICE = "D";
        String PENALTY = "P";
        String INTERST = "I";
        String ADVANCE = "A";
        String REBATE = "R";
        String EXEMPTION = "EXE";
        String SURCHARGE = "SC";
    }

    public static interface TAX_SUBCATEGORY {
        String ADVANCE_PAYMENT = "AP";
        String WATER_TAX_METER = "WTM";
        String WATER_TAX_NONMETER = "WTN";

    }

    public static interface TAX_TYPE {
        String FLAT = "F";
        String SLAB = "S";
        String PERCENTAGE = "P";
        String TELESCOPIC = "T";
    }

    public static interface ACCOUNT_MASTERS {
        String FUND_CPD_VALUE = "AF";
        String FIELD_CPD_VALUE = "AFD";
        String FUNCTION_CPD_VALUE = "AFN";
        String AHP = "AHP";
        String ACTIVE_STATUS_CPD_VALUE = "A";
        String INACTIVE_STATUS_CPD_VALUE = "I";
        String ACTIVE_INACTIVE_PREFIX = "ACN";
        String STOP_PAYMENT = "STP";
        String CHEQUE_STATUS = "CLR";

        interface SECONDARY_MASTER {
            String AHS = "AHS";
            String SECONDARY_LOOKUPCODE = "FTY";
            String SECONDARY_OTHER_CPD_VALUE = "OT";
            String SECONDARY_SEQ_DEPARTMENT_TYPE = "AC";
        }
    }

    public static interface OFFLINE_TYPE {
        String PAY_AT_BANK = "PCB";
        String PAY_At_ULB = "PCU";
    }

    public static interface OFFLINE_MODE {
        String PAYMENT_MODE = "COS";
        String OFFLINE_MODE = "OPF";
    }

    public static interface PAY_PREFIX {
        String PREFIX_VALUE = "PAY";
        String BANK = "B";
        String WEB = "W";
    }

    public static interface PRIFIX {
        String ONLINE_OFFLINE = "OFL";
    }

    public static interface PRIFIX_CODE {
        String PAY_AT_ULB_COUNTER = "PCU";
        String PAY_AT_BANK = "PCB";
    }

    public static interface prefixName {
        String ElectrolWZ = "EWZ";
    }

    public static interface Prefix {
        String DSR = "DSR";
        String RTP = "RTP";
    }

    public static final String SECONDARY_LOOKUPCODE = "FTY";
    public static final String SECONDARY_CPD_VALUE = "AHS";
    public static final String CMD_PREFIX = "CMD";
    public static final String PREFIX_VALUE_IELA = "COA";
    public static final String REV_CPD_VALUE = "REV";
    public static final String PREFIX = "REX";
    public static final String EXP_CPD_VALUE = "EXP";
    public static final String REV_TYPE_CPD_VALUE = "TDP";
    public static final String REV_PREFIX = "PRV";
    public static final String STATUS_ACTIVE_PREFIX = "A";
    public static final String STATUS_PREFIX = "ACN";
    public static final String REV_SUB_CPD_VALUE = "RV";
    public static final String PAY_SUB_CPD_VALUE = "PV";
    public static final String BUG_SUB_PREFIX = "FTP";
    public static final String EXP_SUB_CPD_VALUE = "CP";
    public static final String REV_TYPE_CPD_VALE = "ADP";
    public static final String FIELD_CPD_VALUE = "AFD";
    public static final String ACN = "ACN";
    public static final String FUND_CPD_VALUE = "AF";
    public static final String FUNCTION_CPD_VALUE = "AFN";
    public static final String COA = "COA";
    public static final String FTY = "FTY";
    public static final String CMD = "CMD";
    public static final String AHP = "AHP";
    public static final String BAS = "BAS";
    public static final String BAT = "BAT"; // Bank Type
    public static final String ACT = "ACT"; // Bank Account Type
    public static final String FTP = "FTP"; // Fund Type (BUDGET SUB TYPE)
    public static final String VNT = "VNT"; // Vendor Type
    public static final String VST = "VST"; // Vendor Status
    public static final String VSS = "VSS"; // Vendor Status
    public static final String TDS = "TDS"; // List of Tax Deductions
    public static final String DCR = "DCR";
    public static final String VOT = "VOT";
    public static final String ITP = "ITP";//Bank Account Interest Type
    // Debit / Credit Type
    public static final String HDM = "HDM";
    public static final String QDT = "QDT"; // Quarter Id
    public static final String BDP = "BDP"; // Budget Parameters
    public static final String BILL_ENTRY_CPD_VALUE = "BTE";
    public static final String BCE = "BCE";
    public static final String PAYMENT_ENTRY_CPD_VALUE = "BPE";
    public static final String WORK_ORDER_ENTRY_CPD_VALUE = "WOE";
    public static final String RDS = "RDS";
	public static final String GPI = "GPI";
    // Account Head Mapping

    public enum AccountPrefix {
        DTY, // Type of Deposits
        ATY, // Advance Type
        CLR, // Cheque Book Utilization
        PAY, // Pay mode
        ABT, // Account Bill Type
        DEN, // Denomination
        PTY, AEP, // Posting Type
        TSH, // Transaction Head Selection
        COA, // Account Head Types
        LTY, TOS, RDC, SAM, ACN, // Activeness
        AUT, // Maker Checker Required
        TDP, // Account Template For
        VOT, // Voucher Type
        MTP, // Template Types
        REX, // BUDGET TYPE
        SPL, // MAKER/CHECKER SPEACIAL TYPE
        VET, // Voucher Entry Type
        FRT, // Financial Report Type
        CFD, // Deposit Slip Types
        PDM, // Payment Type
        AIC,// Account integration control
        ;

        private String value;

        private AccountPrefix() {
        }

        private AccountPrefix(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }

    public interface ComplaintPrefix {
        String COMPLAINT_EXPIRY_DURATION_DAYS_PREFIX = "CRO";
        String COMPLAINT_DECISION_PREFIX = "CDL";

    }

    public enum TaxDependsOnFactor {

        TP("DEP"), WT("SLD"), AS("FCT"), RL("RSD"), ADH("ADF"), OTHER("APL"),ENC("ENF"),RTS("RDF");

        private String prefix;

        private TaxDependsOnFactor(String prefix) {
            this.prefix = prefix;
        }

        public String getPrefix() {
            return prefix;
        }

    }

    public static interface RnLPrefix {
        String ETY = "ETY";
        String RATING = "RTN"; // prefix for rating
        String OCCUPANCY = "ROC";
        String WDN = "WDN";
        String RNG = "RNG";
        String EPR = "EPR";
    }

    public static interface WATERMODULEPREFIX {
        String ITP = "ITP";
        String MONTH = "MON";
        String CT = "CT";
        String RT = "RT";
        String RB = "RB";
        String WTP = "WTP";
        String FB = "FB";
        String DDM = "DDM";
        String DUE = "DUE";
        String TAC = "TAC";
        String WDN = "WDN";
        String WFB = "WFB";
        String TRF = "TRF";
        String WWZ = "WWZ";
        String CSZ = "CSZ";
        String SLN = "SLN";
        String CCG = "CCG";
        String REM = "REM";
        String PLR = "PLR";
        String APP = "APP";
        String CAN = "CAN";
        String WPC = "WPC";
        String WOC = "WOC";
        String TBM = "TBM";
        String THM = "THM";
        String TPM = "TPM";
        String AVG = "AVG";
        String MUM = "MUM";
        

    }

    public static interface MobilePreFix {
        String TITLE = "TTL";
        String NEC = "NEC";
        String GENDER = "GEN";
    }

    public static interface ChequeDishonour {
        String DWR = "DWR";
        String CFD = "CFD";
        String FLR = "FLR";
        String DS = "DS";
        String DHC = "DHC";
        String RRE = "RRE";
    }

    public static interface ContraVoucherEntry {
        String PN = "PN";
        String MTP = "MTP";
        String CV = "CV";
        String BTE = "BTE";
        String CWE = "CWE";
        String VOT = "VOT";
        String PCA = "PCA";
    }

    public static interface TenderEntryAuthorization {
        String EM = "EM";
        public String BE = "BE";
    }

    public static interface DirectPaymentEntry {
        String PV = "PV";
        public String PVE = "PVE";
        public String RBP = "RBP";
    }

    public static interface TbAcVendormaster {
        String VN = "VN";
        String SAM = "SAM";
        String VD = "VD";
        String IN = "IN";
        String BL = "BL";
        String VEC = "VEC";
    }

    public static interface VoucherTemplate {
        String CA = "CA";
    }

    public static interface AccountJournalVoucherEntry {
        String JV = "JV";
        String DR = "DR";
        String CR = "CR";
        String VT = "VT";
        String AUT = "AUT";
        String UPL = "UPL";
        String BK = "BK";
        public String MAN = "MAN";
        public String VET = "VET";
    }

    public static interface AccountBillEntry {
        String RCI = "RCI";
        String DP = "DP";
        String BP = "BP";
        String TOS = "TOS";
        String DO = "DO";
        String BI = "BI";
        String RBI = "RBI";
        String DR = "DR";
        String RD = "RD";
        String PRD = "PRD";
    }

    public static interface AccountBudgetReappropriationMaster {
        String RPR = "RPR";
    }

    public static interface AccountReceiptEntry {
        String RP = "RP";
    }

    public static interface StandardAccountHeadMapping {

        public String LNT = "LNT";
        public String LO = "LO";
        public String SD = "SD";
        public String AST = "AST";
        public String ATY = "ATY";
        public String AD = "AD";
        public String DTY = "DTY";
        public String IVT = "IVT";
        public String BK = "BK";

    }

    public static interface CHEQUE_DISHONOUR {
        String BANK_AC_STATUS_PREFIX = "BAS";
        String PREFIX_CODE_Q = "Q";
        String PREFIX_CODE_P = "P";
        String PREFIX_CODE_D = "D";

    }

    public static interface BillKnowkOffPrior {
        String INT = "INT";
        String INTEREST = "I";
        String PRINCIPAL = "P";
    }

    public static interface Objection {
        String HRD = "HRD";
    }

    public static final String CATEGORY_PREFIX_NAME = "ETY";
    public static final String EPLOYEE_ORGNA_ISBPLM = "IEB";
    public static final String EMP_ORG = "IE";
    public static final String IS_BPL = "IB";
    public static final String CPD_VALUE_RENT = "RE";
    public static final String CPD_VALUE_LEASE = "LE";
    public static final String TAC_PREFIX = "TAC";
    public static final String SHIFT_PREFIX = "SHF";
    public static final String CND = "CND";
    public static final String SLI = "SLI";
    public static final String LIVE_MODE="L";
    public static final String D = "D";
    public static final String MLA = "MLA";
    public static final String REJ = "REJ";
    public static final String TRU = "TRU";
    public static final String UTS = "UTS";
    public static final String APPLICATION_TYPE_PREFIX = "ATP";
    public static final String INWARD_TYPE = "RIT";
    public static final String MEDIA_TYPE = "MDT";
    public static final String LOI_APPLICABLE = "RLF";
    public static final String ACTION = "RAC";
    public static final String DELIVERY_MODE = "DMT";
    public static final String CONTRACT_TYPE = "CNT";
    public static final String CDC="CDC";
    public static interface OrgnisationType {
        String COUNCIL = "CON";
        String CORPORATION = "COP";
        String PANCHAYAT = "P";
    }

    public static interface RTI_PREFIX {
        String APPLICANT = "ATP";
        String REFERENCE_MODE = "RRM";
        String BPL = "YNC";
        String MEDIA_TYPE_A4 = "A4";
        String MEDIA_TYPE_A3_A4 = "A4-A3";

        interface APPLICANT_TYPE {
            String O = "O";
            String I = "I";
        }

        interface REFERENCE_MODE_TYPE {
            String D = "D";
        }

        interface BPL_TYPE {
            String N = "N";
        }
    }

    public static interface SOLID_WASTE_MGMT {
        String VCH = "VCH";
    }

    public static interface WORKFLOW {
        String AUTO_ESCALATE = "AE";
    }

    public static interface RVPrefix {
        String ADVANCE_RECEIV = "A";
        String AJUST_RECEIPT = "AR";
    }

    public static interface LegalPrefix {
        String CASE_STATUS = "CSS";
        String COMNT_REVW_DAYS = "LCD";
        String NO_OF_DAYS = "NOD";
    }

    public static interface ADVERTISEMENT_AND_HOARDING_PREFIX {
        String AGN = "AGN";
        String ADC = "ADC";
        String SCRUTINY = "SCL";
        String APP = "APP";
        String REM = "REM";
        String CAA = "CAA";
        String Propretership = "P";
        String Partnership = "A";

    }

    public static interface ASSET_PREFIX {
        String ASSET_CLASSIFICATION = "ASC";
        String ASEET_STATUS = "AST";
        String ASSET_CLASS = "ACL";

        String ASSET_CLASSIFICATION_MOV = "MOV";
        String ASSET_CLASSIFICATION_IMO = "IMO";
    }

    public static interface IT_ASSET_PREFIX {
        String IT_ASSET_CLASSIFICATION = "ISC";
        String IT_ASSET_CLASS = "ICL";

    }

    public static interface LQP_PREFIX {
        String QUESTION_TYPE = "QTP";
        String ASSEMBLY_TYPE = "ALT";
        String ALERT_REMINDER_DAYS = "ARD";// common prefix
    }

    public static interface ENV {
        String SNR = "SNR";
        String BGD = "BGD";
        String DBA = "DBA";
        String ABD = "ABD";
        String FYD = "FYD";
        String MCS = "MCS";
        String MAS = "MAS";
        String UPI = "UPI";
        String ACT = "ACT";
        String VDWP = "VDWP";
        String AE = "AE";
    }
    
    public static interface Sfac {
    	String ORG_TYPE = "OTY";
    	String STATE_DISTRICT_BLOCK = "SDB";
    }
}
