package com.abm.mainet.workManagement.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author Jeetendra.Pal
 *
 */

public class VigilanceDto implements Serializable {

    private static final long serialVersionUID = -2616426825542685603L;

    private Long vigilanceId;
    private Long projId;
     private Long workId;
    private String status;
    private String referenceType;
    private String memoType;
    private String memoDescription;
    private String referenceNumber;
    private Date memoDate;
    private Date inspectionDate;

    private Long orgId;
    private Long createdBy;
    private Date createdDate;
    private Long updatedBy;
    private Date updatedDate;
    private String lgIpMac;
    private String lgIpMacUpd;
    private String memoDateDesc;
    private String inspectDateDesc;
    private String empDesignation;
    private String inspRequire;
    private String memoTypeDesc;
    private String dsgName;
    private String projName;
    private String workName; 
    private Date contractFromDate;
    private Date contractToDate;
    private String projCode;
    
     private String workCode;
    
    public Long getVigilanceId() {
        return vigilanceId;
    }

    public void setVigilanceId(Long vigilanceId) {
        this.vigilanceId = vigilanceId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    public String getMemoType() {
        return memoType;
    }

    public void setMemoType(String memoType) {
        this.memoType = memoType;
    }

    public String getMemoDescription() {
        return memoDescription;
    }

    public void setMemoDescription(String memoDescription) {
        this.memoDescription = memoDescription;
    }

    public Date getMemoDate() {
        return memoDate;
    }

    public void setMemoDate(Date memoDate) {
        this.memoDate = memoDate;
    }

    public Date getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(Date inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getMemoDateDesc() {
        return memoDateDesc;
    }

    public void setMemoDateDesc(String memoDateDesc) {
        this.memoDateDesc = memoDateDesc;
    }

    public String getInspectDateDesc() {
        return inspectDateDesc;
    }

    public void setInspectDateDesc(String inspectDateDesc) {
        this.inspectDateDesc = inspectDateDesc;
    }

    public String getEmpDesignation() {
        return empDesignation;
    }

    public void setEmpDesignation(String empDesignation) {
        this.empDesignation = empDesignation;
    }

    public String getInspRequire() {
        return inspRequire;
    }

    public void setInspRequire(String inspRequire) {
        this.inspRequire = inspRequire;
    }

    public String getMemoTypeDesc() {
        return memoTypeDesc;
    }

    public void setMemoTypeDesc(String memoTypeDesc) {
        this.memoTypeDesc = memoTypeDesc;
    }

    public String getDsgName() {
        return dsgName;
    }

    public void setDsgName(String dsgName) {
        this.dsgName = dsgName;
    }

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public Long getWorkId() {
        return workId;
    }

    public void setWorkId(Long workId) {
        this.workId = workId;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

	public Date getContractFromDate() {
		return contractFromDate;
	}

	public void setContractFromDate(Date contractFromDate) {
		this.contractFromDate = contractFromDate;
	}

	public Date getContractToDate() {
		return contractToDate;
	}

	public void setContractToDate(Date contractToDate) {
		this.contractToDate = contractToDate;
	}

    public String getProjCode() {
        return projCode;
    }

    public void setProjCode(String projCode) {
        this.projCode = projCode;
    }

    public String getWorkCode() {
        return workCode;
    }

    public void setWorkCode(String workCode) {
        this.workCode = workCode;
    }

  
   
    
}
