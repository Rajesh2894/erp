package com.abm.mainet.care.dto;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class ComplaintSearchDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long orgId;
    private Long applicationId;
    private String complaintId;
    private Long pincode;
    private String mobileNumber;
    private Date dateOfRequest;
    private Long empId;
    private Long emplType;
    private Date fromDate;
    private Date toDate;
    private Long departmentComplaint;
    private Long complaintTypeId;
    private String status;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(String complaintId) {
        this.complaintId = complaintId;
    }

    public Long getPincode() {
        return pincode;
    }

    public void setPincode(Long pincode) {
        this.pincode = pincode;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Date getDateOfRequest() {
        return dateOfRequest;
    }

    public void setDateOfRequest(Date dateOfRequest) {
        this.dateOfRequest = dateOfRequest;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public Long getEmplType() {
        return emplType;
    }

    public void setEmplType(Long emplType) {
        this.emplType = emplType;
    }

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}


	public Long getDepartmentComplaint() {
		return departmentComplaint;
	}

	public void setDepartmentComplaint(Long departmentComplaint) {
		this.departmentComplaint = departmentComplaint;
	}

	public Long getComplaintTypeId() {
		return complaintTypeId;
	}

	public void setComplaintTypeId(Long complaintTypeId) {
		this.complaintTypeId = complaintTypeId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

    
}
