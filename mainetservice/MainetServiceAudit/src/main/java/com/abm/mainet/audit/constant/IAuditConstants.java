package com.abm.mainet.audit.constant;

public interface IAuditConstants {
	
	
	
	// Audit Para Entry Controller 
	String AUDIT_PARA_SUMMARY_URL = "/AuditParaEntry.html";
	String AUDIT_PARA_ADD_PARAM= "addAuditPara";
	String AUDIT_PARA_EDIT_PARAM= "editAuditPara";
	String AUDIT_PARA_VIEW_PARAM= "viewAuditPara";
	String AUDIT_PARA_SEARCH_PARAM= "searchAuditPara";
	String AUDIT_PARA_WORKFLOW_HISTORY="viewFormHistoryDetails";
	String AUDIT_PARA_ADD_VIEW_EDIT_TILES="addAuditParaEntry";
	String AUDIT_PARA_WORKFLOW_HISTORY_TILES="auditParaHistory";
	String AUDIT_PARA_STATUS_PREFIX="ADU";
	String AUDIT_PARA_AUDIT_TYPE_PREFIX="ADT";
	String AUDIT_PARA_ENTRY_URL = "AuditParaEntry.html";
	
	
	
	// L1,L2,L3 Approval Controller
	String AUDIT_PARA_WORKFLOW_TILES="auditParaApprovalWithDyanmicRadioButtons";
	String AUDIT_PARA_WORKFLOW_SHOW_DETAILS_PARAM="showDetails";
	String AUDIT_PARA_WORKFLOW_SAVE_PARAM="saveAuditParaApproval";
	String AUDIT_PARA_WORKFLOW_ACTION_PARAM= "getAuditWorkflowAction";
	String AUDIT_PARA_WORKFLOW_L1_URL="/AuditParaChiefAuditorApproval.html";
	String AUDIT_PARA_WORKFLOW_L1_RADIO_BTN="Approve,Reject,Forward";
	String AUDIT_PARA_WORKFLOW_L1_RADIO_BTN_VAL="APPROVED,REJECTED,FORWARD_TO_EMPLOYEE";
	String AUDIT_PARA_WORKFLOW_L2_URL="/AuditParaStandingCommitteeApproval.html";
	String AUDIT_PARA_WORKFLOW_L2_RADIO_BTN="Approve,Reject";
	String AUDIT_PARA_WORKFLOW_L2_RADIO_BTN_VAL="APPROVED,REJECTED";
	String AUDIT_PARA_WORKFLOW_L3_URL="/AuditParaCommissionerApproval.html";
	String AUDIT_PARA_WORKFLOW_L3_RADIO_BTN="Forward";
	String AUDIT_PARA_WORKFLOW_L3_RADIO_BTN_VAL="FORWARD_TO_EMPLOYEE";
	
	// Audit Para Entry Model
	String AUDIT_PARA_FINANCIAL_YEAR_CHECK="Exception occured while calculating financial year.";
	String AUDIT_PARA_STATUS_SAVE="D";
	String AUDIT_PARA_STATUS_SUBMIT="O";
	String AUDIT_DEPT_CODE="AD";
	String AUDIT_TABLE="TB_ADT_POSTADT_MAS";
	String AUDIT_SEQ_COLUMN_NAME="AUDIT_PARA_CODE";
	String AUDIT_PRIMARY_KEY="AUDIT_PARA_ID";
	String AUDIT_PARA_MSG_SAVE_MODE=": Objection Entry saved in Draft Mode";
	String AUDIT_HISTORY_TABLE="TB_ADT_POSTADT_MAS_HIST";
	String AUDIT_HISTORY_PRIMARY_KEY="AUDIT_PARA_ID_HIS";
	
	// Audit Para Approval Model
	String AUDIT_PARA_APPROVAL_SERVICE_CODE="APA";
	String AUDIT_PARA_STATUS_CLOSE="C";
	String AUDIT_PARA_STATUS_INTERIM="I";
	
	//Audit Service
	String AUDIT_PARA_CHIEF_AUDITOR_PREFIX="CHF";
	
	String AUDIT_PARA_SEQUENCE_PREFIX="APS";
	
	
}
