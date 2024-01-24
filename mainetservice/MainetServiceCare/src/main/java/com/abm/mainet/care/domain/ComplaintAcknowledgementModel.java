package com.abm.mainet.care.domain;

import java.util.Date;
import java.util.List;

import com.abm.mainet.common.workflow.dto.WorkflowTaskActionWithDocs;

public class ComplaintAcknowledgementModel {

    private String organizationName;
    private String tokenNumber;
    private String complainantName;
    private String complainantMobileNo;
    private String complainantEmail;
    private String complaintType;
    private String complaintSubType;
    private String ward;
    private String description;
    private String department;
    private String status;
    private String lastDecision;
    private Date date;
    private String formattedDate;
    private String errors;
    private String landmark;
    private String address;
    private List<EscalationDetailsList> escalationDetailsList;
    private List<WorkflowTaskActionWithDocs> actions;
    private List<WorkflowTaskActionWithDocs> actionsPending;
    private Long applicationId;
    //#158786
    private String extReferenceNo;
    private String complaintDeptCode;
    
    private String ward1;
    private String ward2;
    private String ward3;
    private String ward4;
    private String prefixName;
    /*#162579*/
    private String orgShortNm;
    
    private String rfmMode;

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getTokenNumber() {
        return tokenNumber;
    }

    public void setTokenNumber(String tokenNumber) {
        this.tokenNumber = tokenNumber;
    }

    public String getComplainantName() {
        return complainantName;
    }

    public void setComplainantName(String complainantName) {
        this.complainantName = complainantName;
    }

    public String getComplainantMobileNo() {
        return complainantMobileNo;
    }

    public void setComplainantMobileNo(String complainantMobileNo) {
        this.complainantMobileNo = complainantMobileNo;
    }

    public String getComplainantEmail() {
        return complainantEmail;
    }

    public void setComplainantEmail(String complainantEmail) {
        this.complainantEmail = complainantEmail;
    }

    public String getComplaintType() {
        return complaintType;
    }

    public void setComplaintType(String complaintType) {
        this.complaintType = complaintType;
    }

    public String getComplaintSubType() {
        return complaintSubType;
    }

    public void setComplaintSubType(String complaintSubType) {
        this.complaintSubType = complaintSubType;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastDecision() {
        return lastDecision;
    }

    public void setLastDecision(String lastDecision) {
        this.lastDecision = lastDecision;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    public List<EscalationDetailsList> getEscalationDetailsList() {
        return escalationDetailsList;
    }

    public void setEscalationDetailsList(List<EscalationDetailsList> escalationDetailsList) {
        this.escalationDetailsList = escalationDetailsList;
    }

    public List<WorkflowTaskActionWithDocs> getActions() {
        return actions;
    }

    public void setActions(List<WorkflowTaskActionWithDocs> actions) {
        this.actions = actions;
    }

    public List<WorkflowTaskActionWithDocs> getActionsPending() {
        return actionsPending;
    }

    public void setActionsPending(List<WorkflowTaskActionWithDocs> actionsPending) {
        this.actionsPending = actionsPending;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

	public String getExtReferenceNo() {
		return extReferenceNo;
	}

	public void setExtReferenceNo(String extReferenceNo) {
		this.extReferenceNo = extReferenceNo;
	}

	public String getComplaintDeptCode() {
		return complaintDeptCode;
	}

	public void setComplaintDeptCode(String complaintDeptCode) {
		this.complaintDeptCode = complaintDeptCode;
	}

	public String getWard1() {
		return ward1;
	}

	public void setWard1(String ward1) {
		this.ward1 = ward1;
	}

	public String getWard2() {
		return ward2;
	}

	public void setWard2(String ward2) {
		this.ward2 = ward2;
	}

	public String getWard3() {
		return ward3;
	}

	public void setWard3(String ward3) {
		this.ward3 = ward3;
	}

	public String getWard4() {
		return ward4;
	}

	public void setWard4(String ward4) {
		this.ward4 = ward4;
	}

	public String getPrefixName() {
		return prefixName;
	}

	public void setPrefixName(String prefixName) {
		this.prefixName = prefixName;
	}

	public String getOrgShortNm() {
		return orgShortNm;
	}

	public void setOrgShortNm(String orgShortNm) {
		this.orgShortNm = orgShortNm;
	}

	public String getRfmMode() {
		return rfmMode;
	}

	public void setRfmMode(String rfmMode) {
		this.rfmMode = rfmMode;
	}

}
