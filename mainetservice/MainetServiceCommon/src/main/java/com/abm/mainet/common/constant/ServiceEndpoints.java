package com.abm.mainet.common.constant;

import com.abm.mainet.common.utility.ApplicationSession;

/**
 * @author Harsha.Ramachandran
 *
 */

public interface ServiceEndpoints {

    public static String getMessage(String code) {
        return ApplicationSession.getInstance().getMessage(code);
    }

    interface BRMSMappingURL {

        // common URL
        String INITIALIZE_MODEL_URL = getMessage("INITIALIZE_MODEL_URL");
        String CHECKLIST_URL = getMessage("CHECKLIST_URL");

        // water module specific URL
        String WATER_SERVICE_CHARGE_URL = getMessage("WATER_SERVICE_CHARGE_URL");
        String WATER_INITIALIZE_OTHER_FIELDS = getMessage("WATER_INITIALIZE_OTHER_FIELDS");

        String WATER_NO_OF_DAYS_URL = getMessage("WATER_NO_OF_DAYS_URL");
        String WATER_RATE_URL = getMessage("WATER_RATE_URL");
        String WATER_TAX_URL = getMessage("WATER_TAX_URL");
        String WATER_CONSUMPTION_NODAYS_URL = getMessage("WATER_CONSUMPTION_NODAYS_URL");

        String WATER_RATE_BILL_URL = getMessage("WATER_RATE_BILL_URL");
        String WATER_TAX_BILL_URL = getMessage("WATER_TAX_BILL_URL");
        String WATER_BILL_BY_CONN_NO = getMessage("WATER_BILL_BY_CONN_NO");

        // Rent and Lease URL
        String RNL_SERVICE_CHARGE_URI = getMessage("RNL_SERVICE_CHARGE_URI");
        String RNL_CHARGES_FOR_MULTI_PROP = getMessage("RNL_CHARGES_FOR_MULTI_PROP");
        String RNL_TAX_PERCENTAGE_URI = getMessage("RNL_TAX_PERCENTAGE_URI");
        String CALL_RNL_SERVICE_FROM_CHALLAN = getMessage("CALL_RNL_SERVICE_FROM_CHALLAN");

        // RTI URL
        String RTI_SERVICE_CHARGE_URI = getMessage("RTI_SERVICE_CHARGE_URI");

        /* SWM endpoint url */
        String SWM_WASTE_COLLECTOR = getMessage("SWM_WASTE_COLLECTOR");

        /* ADH Application Service Charge Endpoint URL */
        String ADH_SERVICE_CHARGE_URI = getMessage("ADH_SERVICE_CHARGE_URI");

        // BND
        String BND_RATE_MASTER_URL = getMessage("BND_RATE_MASTER_URL");

        /* Marriage Registration Service Charge URL */
        String MRM_SERVICE_CHARGE_URI = getMessage("MRM_SERVICE_CHARGE_URI");
        
        String ADDITIONAL_SERVICE_CHARGE_URI = getMessage("ADDITIONAL_SERVICE_CHARGE_URI");
    }

    interface Property {
        String PROP_FACT = getMessage("PROP_FACT");
        String PROP_SDDR = getMessage("PROP_SDDR");
        String PROP_ALV = getMessage("PROP_ALV");
        String PROP_RATE = getMessage("PROP_RATE");
        String PROP_LEVEL_RATE = getMessage("PROP_LEVEL_RATE");
        String GET_PROPERTY_DETAILS_BY_PROPERTY_NO_AND_ORGID = getMessage("GET_PROPERTY_DETAILS_BY_PROPERTY_NO_AND_ORGID");

    }

    interface ITMS {
        String CHALLAN_DETAILS = getMessage("CHALLAN_DETAILS");
        String VIOLATION_COUNT = getMessage("VIOLATION_COUNT");
        String INCIDENT_DETAILS = getMessage("INCIDENT_DETAILS");

    }

    interface RESPONSE_URL_PARAM {
        String MOBILE_PAYU_SUCCESS = getMessage("MOBILE_PAYU_SUCCESS");
        String MOBILE_PAYU_FAIL = getMessage("MOBILE_PAYU_FAIL");
        String MOBILE_PAYU_CANCEL = getMessage("MOBILE_PAYU_CANCEL");
        String MOBILE_HDFC_SUCCESS = getMessage("MOBILE_HDFC_SUCCESS");
        String MOBILE_HDFC_FAIL = getMessage("MOBILE_HDFC_FAIL");
        String MOBILE_HDFC_CANCEL = getMessage("MOBILE_HDFC_CANCEL");
        String MOBILE_MOL_SUCCESS = getMessage("MOBILE_MOL_SUCCESS");
        String MOBILE_MOL_FAIL = getMessage("MOBILE_MOL_FAIL");
        String MOBILE_MOL_CANCEL = getMessage("MOBILE_MOL_CANCEL");
        String MOBILE_TP_SUCCESS = getMessage("MOBILE_TP_SUCCESS");
        String MOBILE_TP_FAIL = getMessage("MOBILE_TP_FAIL");
        String MOBILE_TP_CANCEL = getMessage("MOBILE_TP_CANCEL");
        String MOBILE_CCA_SUCCESS = getMessage("MOBILE_CCA_SUCCESS");
        String MOBILE_CCA_CANCEL = getMessage("MOBILE_CCA_CANCEL");
        String MOBILE_CCA_FAIL = getMessage("MOBILE_CCA_FAIL");
        String MOBILE_AWL_SUCCESS = getMessage("MOBILE_AWL_SUCCESS");
        String MOBILE_AWL_CANCEL = getMessage("MOBILE_AWL_CANCEL");
        String MOBILE_AWL_FAIL = getMessage("MOBILE_AWL_FAIL");
        String MOBILE_ICICI_SUCCESS = getMessage("MOBILE_ICICI_SUCCESS");
        String MOBILE_ICICI_CANCEL = getMessage("MOBILE_ICICI_CANCEL");
        String MOBILE_ICICI_FAIL = getMessage("MOBILE_ICICI_FAIL");
        String MOBILE_EASYPAY_SUCCESS = getMessage("MOBILE_EASYPAY_SUCCESS");
        String MOBILE_RAZORPAY_SUCCESS = getMessage("MOBILE_RAZORPAY_SUCCESS");
		String MOBILE_ATOM_SUCCESS = getMessage("MOBILE_ATOM_SUCCESS");
		String MOBILE_EASEBUZZ_SUCCESS=getMessage("MOBILE_EASEBUZZ_SUCCESS");
		String NicGateway = getMessage("MOBILE_EASEBUZZ_SUCCESS");
    }

    interface WorkflowExecutionURLS {

        String INITIATE = getMessage("BPM_PROCESS_INITIATE");
        String UPDATE = getMessage("BPM_TASK_COMPLETE");
        String TASK_CONTENT = getMessage("BPM_TASK_CONTENT");
        String TASK_LIST_USER = getMessage("BPM_TASK_LIST_USER");
        String TASK_LIST_UUID = getMessage("BPM_TASK_LIST_UUID");
        String TASK_LIST_SEARCH = getMessage("BPM_TASK_LIST_SEARCH");
        String SIGNAL = getMessage("BPM_PROCESS_SIGNAL");
        String WORKFLOW_REQUEST_BY_UUID = getMessage("WORKFLOW_REQUEST_BY_UUID");
        String WORKFLOW_REQUEST_BY_APP_ID = getMessage("WORKFLOW_REQUEST_BY_APP_ID");
        String WORKFLOW_REQUEST_BY_APP_ID_AND_WORKFLOW_ID = getMessage("WORKFLOW_REQUEST_BY_APP_ID_AND_WORKFLOW_ID");
        String WORKFLOW_TASK_BY_TASK_AND_APP_ID = getMessage("WORKFLOW_TASK_BY_TASK_AND_APP_ID");
        String WORKFLOW_TASK_BY_TASK_AND_REF_ID = getMessage("WORKFLOW_TASK_BY_TASK_AND_REF_ID");
        String WORKFLOW_ACTION = getMessage("WORKFLOW_ACTION");
        String WORKFLOW_ACTION_PENDING = getMessage("WORKFLOW_ACTION_PENDING");
        String WORKFLOW_AUTO_ESCALATION_TASK = getMessage("WORKFLOW_AUTO_ESCALATION_TASK");
        String WORKFLOW_AUTO_OBJECTION_TASK = getMessage("WORKFLOW_AUTO_OBJECTION_TASK");
		String WORKFLOW_REQUEST_BY_APP_ID_ORG_ID = getMessage("WORKFLOW_REQUEST_BY_APP_ID_ORG_ID");
    }

    interface VehicleManagement {
        String VEHICLE_MASTER_All = getMessage("VEHICLE_MASTER_All");
        String VEHICLE_MASTER_BY_ID = getMessage("VEHICLE_MASTER_BY_ID");
        String VEHICLE_MASTER_BY_DEPT =getMessage("VEHICLE_MASTER_BY_DEPT");
    }

    interface GisItegration {
        String GIS_URI = getMessage("GIS_URI");
        String COMMON_GIS_LAYER_NAME = getMessage("COMMON_GIS_LAYER_NAME");
        String WATER_GIS_LAYER_NAME = getMessage("WATER_GIS_LAYER_NAME");
        String LICENSE_TRADE_LAYER_NAME = getMessage("LICENSE_TRADE_LAYER_NAME");
        String ADH_LAYER_NAME = getMessage("ADH_LAYER_NAME");
        String ESTATE_NAGAR_NIGAM_PROPERTY = getMessage("ESTATE_NAGAR_NIGAM_PROPERTY");
        String PROPERTY_GIS_LAYER_NAME = getMessage("PROPERTY_GIS_LAYER_NAME");
        String LAYER_NAME_POINT = getMessage("LAYER_NAME_POINT");
        String ASSET_LAYER_NAME = getMessage("ASSET_LAYER_NAME");
        String BUIDING_PERMISSION_LAYER_NAME = getMessage("BUIDING_PERMISSION_LAYER_NAME");
    }

    // service to portal organization posting
    String ORG_POST_ORGANISATION = getMessage("ORG_POST_ORGANISATION");
    String ORG_POST_UPDATE_ORGANISATION = getMessage("ORG_POST_UPDATE_ORGANISATION");

    // Account Posting
    String ACCOUNT_POSTING = getMessage("ACCOUNT_POSTING");
    String RECEIPT_POSTING = getMessage("RECEIPT_POSTING");
    String SALARY_POSTING = getMessage("SALARY_POSTING");
    String ACCOUNT_POSTING_VALIDATE = getMessage("ACCOUNT_POSTING_VALIDATE");
    String ACCOUNT_REVERSE_POSTING = getMessage("ACCOUNT_REVERSE_POSTING");
    String SALARY_BILL_BUDGET_DETAILS = getMessage("SALARY_BILL_BUDGET_DETAILS");
    String ACCOUNT_EXT_SYS_POSTING = getMessage("ACCOUNT_EXT_SYS_POSTING");
    String GET_BUDGET = getMessage("GET_BUDGET");
    String COUNCIL_BILL_BUDGET_DETAILS = getMessage("COUNCIL_BILL_BUDGET_DETAILS");

    /*
     * DMS End Point Url
     */
    String DMS_CREATE_DOC_URL = getMessage("DMS_CREATE_DOC_URL");
    String DMS_BASE_URL = getMessage("DMS_BASE_URL");
    String DMS_CREATE_FOLDER_URL = getMessage("DMS_CREATE_FOLDER_URL");
    String DMS_GET_DOC_BY_ID_URL = getMessage("DMS_GET_DOC_BY_ID_URL");
    String DMS_GET_WORKFLOW_STATUS_BY_ID_URL = getMessage("DMS_GET_WORKFLOW_STATUS_BY_ID_URL");
    String WMS_ADVANCE_REQUISATION = getMessage("WMS_ADVANCE_REQUISATION");
    String DOON_DMS_URL = getMessage("DOON_DMS_URL");
    String DMS_SCAN_URL = getMessage("DMS_SCAN_URL");

    String WMS_CONTRACT_DETAILS = getMessage("WMS_CONTRACT_DETAILS");
    String Get_ASSET_DETAILS_BY_ASSETID_AND_ORGID = getMessage("Get_ASSET_DETAILS_BY_ASSETID_AND_ORGID");
    String WMS_ASSET_DETAILS = getMessage("WMS_ASSET_DETAILS");
    String GET_ASSET_DETAILS_BY_ASSETCODE_AND_ORGID = getMessage("GET_ASSET_DETAILS_BY_ASSETCODE_AND_ORGID");

    // WSDL URL'S of GRP Environment.
    String GRP_ACCOUNT_HEAD_WSDL_URL = getMessage("GRP_ACCOUNT_HEAD_WSDL_URL");
    String GRP_FIELD_MASTER_WSDL_URL = getMessage("GRP_FIELD_MASTER_WSDL_URL");
    String GRP_FUNCTION_MASTER_WSDL_URL = getMessage("GRP_FUNCTION_MASTER_WSDL_URL");
    String GRP_VENDOR_MASTER_WSDL_URL = getMessage("GRP_VENDOR_MASTER_WSDL_URL");
    String GRP_BANK_MASTER_WSDL_URL = getMessage("GRP_BANK_MASTER_WSDL_URL");

    /* grp posting wsdl url */

    String GRP_EMP_WSDL_URL = getMessage("GRP_EMP_WSDL_URL");
    String GRP_DEPT_WSDL_URL = getMessage("GRP_DEPT_WSDL_URL");
    String GRP_FIN_WSDL_URL = getMessage("GRP_FIN_WSDL_URL");
    String GRP_ORG_WSDL_URL = getMessage("GRP_ORG_WSDL_URL");
    String GRP_LOC_WSDL_URL = getMessage("GRP_LOC_WSDL_URL");
    String GRP_HOLIDAY_WSDL_URL = getMessage("GRP_HOLIDAY_WSDL_URL");
    String GRP_DESIGORGMAP_WSDL_URL = getMessage("GRP_DESIGORGMAP_WSDL_URL");
    String GRP_DEPTORGMAP_WSDL_URL = getMessage("GRP_DEPTORGMAP_WSDL_URL");

    String WMS_RATE_MASTER = getMessage("WMS_RATE_MASTER");
    String ML_NEW_TRADE_LICENSE = getMessage("ML_NEW_TRADE_LICENSE");

    String PROP_BY_PROP_ID = getMessage("PROP_BY_PROP_ID");

    String PROP_BY_PROP_NO_AND_FLATNO = getMessage("PROP_BY_PROP_NO_AND_FLATNO");

    String GET_BILLING_MEHOD_BY_PROP_NO = getMessage("GET_BILLING_MEHOD_BY_PROP_NO");

    String GET_FLAT_LIST_BY_PROP_NO = getMessage("GET_FLAT_LIST_BY_PROP_NO");

    String WMS_ROAD_CUTTING_RATE_MASTER = getMessage("WMS_ROAD_CUTTING_RATE_MASTER");

    String WATER_BIRT_REPORT_URL = getMessage("WATER_BIRT_REPORT_URL");

    String TRADE_LICENSE_BIRT_REPORT_URL = getMessage("TRADE_LICENSE_BIRT_REPORT_URL");
    
    String NOC_BIRT_REPORT_URL = getMessage("NOC_BIRT_REPORT_URL");

    String PROPERTY_BIRT_REPORT_URL = getMessage("PROPERTY_BIRT_REPORT_URL");

    String LEGAL_CASE_BIRT_REPORT_URL = getMessage("LEGAL_CASE_BIRT_REPORT_URL");

    String COUNCIL_PROPOSAL_DETAILS = getMessage("COUNCIL_PROPOSAL_DETAILS");

    String ALL_ATTENDED_DISASTER_REPORT_URL = getMessage("ALL_ATTENDED_DISASTER_REPORT_URL");

    String BIRTH_REGISTER_REPORT_URL = getMessage("BIRTH_REGISTER_REPORT_URL");

    String CALL_DETAILS_BIRT_REPORT_URL = getMessage("CALL_DETAILS_BIRT_REPORT_URL");

    String DISASTER_CALL_DETAILS_REPORT_URL = getMessage("DISASTER_CALL_DETAILS_REPORT_URL");

    String WORK_INSPECTION_DETAILS_URL = getMessage("WORK_INSPECTION_DETAILS_URL");

    String BIRT_REPORT_URL = getMessage("BIRT_REPORT_URL");

    String BIRT_REPORT_DMS_URL = getMessage("BIRT_REPORT_DMS_URL");

    String PROP_DETAILS_BY_PROP_NO = getMessage("PROP_DETAILS_BY_PROP_NO");

    String PROP_BY_APP_ID = getMessage("PROP_BY_APP_ID");

    String PORTAL_DEPT_LOGIN_URL = getMessage("PORTAL_DEPT_LOGIN_URL");

    String SAVE_MUTATION_AFTER_LOI_PAYMENT = getMessage("SAVE_MUTATION_AFTER_LOI_PAYMENT");
    
    String WATER_DUE_AMOUNT_BY_PROP_NO = getMessage("WATER_DUE_AMOUNT_BY_PROP_NO");
    
	String WATER_DUES_BY_PROP_NO_AND_CONN_NO = getMessage("WATER_DUES_BY_PROP_NO_AND_CONN_NO");
	
	String SAVE_ASSESSMENT_AFTER_OBJECTION = getMessage("SAVE_ASSESSMENT_AFTER_OBJECTION");
	String FETCH_PURCHASE_REQ = getMessage("FETCH_PURCHASE_REQ");
	String UPDATE_PURCHASE_REQ_STATUS = getMessage("UPDATE_PURCHASE_REQ_STATUS");
	String FETCH_PURCHASE_REQUISATION =getMessage("FETCH_PURCHASE_REQUISATION");
	String FETCH_PURCHASE_REQUISATION_DETAILS =getMessage("FETCH_PURCHASE_REQUISATION_DETAILS");
	String FETCH_ITEM_DATA = getMessage("FETCH_ITEM_DATA");
	
	String FETCH_EXPIRY_DISPOSAL = getMessage("FETCH_EXPIRY_DISPOSAL");
	String UPDATE_EXPIRY_DISPOSAL_STATUS = getMessage("UPDATE_EXPIRY_DISPOSAL_STATUS");
	String FETCH_EXPIRY_DISPOSAL_CODES = getMessage("FETCH_EXPIRY_DISPOSAL_CODES");
	
	String FETCH_WATER_DASHBOARD_DATA=getMessage("FETCH_WATER_DASHBOARD_DATA");
	String FETCH_PROPERTY_DASHBOARD_DATA = getMessage("FETCH_PROPERTY_DASHBOARD_DATA");
	
	String NL_LICENSE_FEE = getMessage("NL_LICENSE_FEE");
}
