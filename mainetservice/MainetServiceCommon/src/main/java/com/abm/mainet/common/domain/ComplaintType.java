package com.abm.mainet.common.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author ritesh.patil
 *
 */

@Entity
@Table(name = "TB_DEP_COMPLAINT_SUBTYPE")
@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class ComplaintType {

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "COMP_ID", nullable = false)
    private Long compId;

    @Column(name = "ORGID", nullable = false)
    private Long orgId;

    @Column(name = "COMP_TYPE_DESC", nullable = false)
    private String complaintDesc;

    @Column(name = "COMP_TYPE_REG", nullable = false)
    private String complaintDescreg;

    @Column(name = "RESIDENT_ID", nullable = true)
    private String residentId;

    @Column(name = "AMT_DUES", nullable = true)
    private String amtDues;

    @Column(name = "DOCUMENT_REQ", nullable = true)
    private String documentReq;

    @Column(name = "OTP_VALID_REQ", nullable = true)
    private String otpValidReq;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DEPT_COMP_ID")
    private DepartmentComplaint departmentComplaint;

    @Column(name = "ACTIVE", length = 1, nullable = true)
    private Boolean isActive;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "CREATE_DATE")
    private Date createDate;

    @Column(name = "CREATED_BY")
    private Long createdBy;
    
    @Column(name = "EXTERNAL_FLAG", nullable = true)
    private String externalWorkFlowFlag;

    public Long getCompId() {
        return compId;
    }

    public void setCompId(Long compId) {
        this.compId = compId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getComplaintDesc() {
        return complaintDesc;
    }

    public void setComplaintDesc(String complaintDesc) {
        this.complaintDesc = complaintDesc;
    }

    public String getComplaintDescreg() {
        return complaintDescreg;
    }

    public void setComplaintDescreg(String complaintDescreg) {
        this.complaintDescreg = complaintDescreg;
    }

    public String getResidentId() {
        return residentId;
    }

    public void setResidentId(String residentId) {
        this.residentId = residentId;
    }

    public String getAmtDues() {
        return amtDues;
    }

    public void setAmtDues(String amtDues) {
        this.amtDues = amtDues;
    }

    public String getDocumentReq() {
        return documentReq;
    }

    public void setDocumentReq(String documentReq) {
        this.documentReq = documentReq;
    }

    public String getOtpValidReq() {
        return otpValidReq;
    }

    public void setOtpValidReq(String otpValidReq) {
        this.otpValidReq = otpValidReq;
    }

    public DepartmentComplaint getDepartmentComplaint() {
        return departmentComplaint;
    }

    public void setDepartmentComplaint(
            DepartmentComplaint departmentComplaint) {
        this.departmentComplaint = departmentComplaint;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

	public String getExternalWorkFlowFlag() {
		return externalWorkFlowFlag;
	}

	public void setExternalWorkFlowFlag(String externalWorkFlowFlag) {
		this.externalWorkFlowFlag = externalWorkFlowFlag;
	}

	public String[] getPkValues() {
        return new String[] { "COM", "TB_DEP_COMPLAINT_SUBTYPE", "COMP_ID" };
    }

}
