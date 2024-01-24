package com.abm.mainet.care.dto;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class ComplaintRegistrationAcknowledgementDTO {

    private String organizationName;
    private String tokenNumber;
    private String complainantName;
    private String complainantMobileNo;
    private String complainantEmail;
    private String complaintType;
    private String complaintSubType;
    private String ward;
    private String status;
    private String lastDecision;
    private String description;
    private String department;
    private Date date;
    private String formattedDate;
    private List<EscalationDetailsListDTO> escalationDetailsList;
    private List<ActionDTOWithDoc> actions;
    private String errors;
    private String landmark;
    private Long applicationId;
    
    private String extReferenceNo;
    private String complaintDeptCode;
    
    private String prefix;
    private Long wardValue1;
    private Long wardValue2;
    private Long wardValue3;
    private Long wardValue4;
    private Long wardValue5;
    
    
    private String ward1Desc;
    private String ward2Desc;
    private String ward3Desc;
    private String ward4Desc;
    private String ward5Desc;
    
    
    private String ward1Label;
    private String ward2Label;
    private String ward3Label;
    private String ward4Label;
    private String ward5Label;
    
    private String orgShortNm;

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

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<EscalationDetailsListDTO> getEscalationDetailsList() {
        return escalationDetailsList;
    }

    public void setEscalationDetailsList(List<EscalationDetailsListDTO> escalationDetailsList) {
        this.escalationDetailsList = escalationDetailsList;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }
    
    

    public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public String getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }

    public List<ActionDTOWithDoc> getActions() {
        return actions;
    }

    public void setActions(List<ActionDTOWithDoc> actions) {
        this.actions = actions;
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

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public Long getWardValue1() {
		return wardValue1;
	}

	public void setWardValue1(Long wardValue1) {
		this.wardValue1 = wardValue1;
	}

	public Long getWardValue2() {
		return wardValue2;
	}

	public void setWardValue2(Long wardValue2) {
		this.wardValue2 = wardValue2;
	}

	public Long getWardValue3() {
		return wardValue3;
	}

	public void setWardValue3(Long wardValue3) {
		this.wardValue3 = wardValue3;
	}

	public Long getWardValue4() {
		return wardValue4;
	}

	public void setWardValue4(Long wardValue4) {
		this.wardValue4 = wardValue4;
	}

	public Long getWardValue5() {
		return wardValue5;
	}

	public void setWardValue5(Long wardValue5) {
		this.wardValue5 = wardValue5;
	}

	public String getWard1Desc() {
		return ward1Desc;
	}

	public void setWard1Desc(String ward1Desc) {
		this.ward1Desc = ward1Desc;
	}

	public String getWard2Desc() {
		return ward2Desc;
	}

	public void setWard2Desc(String ward2Desc) {
		this.ward2Desc = ward2Desc;
	}

	public String getWard3Desc() {
		return ward3Desc;
	}

	public void setWard3Desc(String ward3Desc) {
		this.ward3Desc = ward3Desc;
	}

	public String getWard4Desc() {
		return ward4Desc;
	}

	public void setWard4Desc(String ward4Desc) {
		this.ward4Desc = ward4Desc;
	}

	public String getWard5Desc() {
		return ward5Desc;
	}

	public void setWard5Desc(String ward5Desc) {
		this.ward5Desc = ward5Desc;
	}

	public String getOrgShortNm() {
		return orgShortNm;
	}

	public void setOrgShortNm(String orgShortNm) {
		this.orgShortNm = orgShortNm;
	}

	public String getWard1Label() {
		return ward1Label;
	}

	public void setWard1Label(String ward1Label) {
		this.ward1Label = ward1Label;
	}

	public String getWard2Label() {
		return ward2Label;
	}

	public void setWard2Label(String ward2Label) {
		this.ward2Label = ward2Label;
	}

	public String getWard3Label() {
		return ward3Label;
	}

	public void setWard3Label(String ward3Label) {
		this.ward3Label = ward3Label;
	}

	public String getWard4Label() {
		return ward4Label;
	}

	public void setWard4Label(String ward4Label) {
		this.ward4Label = ward4Label;
	}

	public String getWard5Label() {
		return ward5Label;
	}

	public void setWard5Label(String ward5Label) {
		this.ward5Label = ward5Label;
	}
    
    

}
