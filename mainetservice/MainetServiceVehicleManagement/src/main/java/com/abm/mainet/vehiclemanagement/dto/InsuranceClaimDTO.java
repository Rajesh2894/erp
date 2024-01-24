package com.abm.mainet.vehiclemanagement.dto;

import java.io.Serializable;
import java.util.Date;

public class InsuranceClaimDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long insuranceClaimId;

	private Long department;

	private String deptDesc;

	private Long vehicleType;

	private String veNo;

	private Long veId;

	private String veDesc;

	private Long insuredBy;

	private String insuredName;
	
	private Date issueDate;

	private Date endDate;

	private Double insuredAmount;
    
	private String policyNo; 
	
	private Long orgid;

	private Long createdBy;

	private Date createdDate;

	private Long updatedBy;

	private Date updatedDate;

	private String lgIpMac;

	private String lgIpMacUpd;
	
	private Double claimAmount;
	
	private Double claimApprovedAmount;
	
	private String removeFileById;
	
	public String getInsuredName() {
		return insuredName;
	}

	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}

	public Long getInsuranceClaimId() {
		return insuranceClaimId;
	}

	public void setInsuranceClaimId(Long insuranceClaimId) {
		this.insuranceClaimId = insuranceClaimId;
	}

	public Long getDepartment() {
		return department;
	}

	public void setDepartment(Long department) {
		this.department = department;
	}

	public String getDeptDesc() {
		return deptDesc;
	}

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}

	public Long getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(Long vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getVeNo() {
		return veNo;
	}

	public void setVeNo(String veNo) {
		this.veNo = veNo;
	}

	public Long getVeId() {
		return veId;
	}

	public void setVeId(Long veId) {
		this.veId = veId;
	}

	public String getVeDesc() {
		return veDesc;
	}

	public void setVeDesc(String veDesc) {
		this.veDesc = veDesc;
	}

	public Long getInsuredBy() {
		return insuredBy;
	}

	public void setInsuredBy(Long insuredBy) {
		this.insuredBy = insuredBy;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Double getInsuredAmount() {
		return insuredAmount;
	}

	public void setInsuredAmount(Double insuredAmount) {
		this.insuredAmount = insuredAmount;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getOrgid() {
		return orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
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

	public Double getClaimAmount() {
		return claimAmount;
	}

	public void setClaimAmount(Double claimAmount) {
		this.claimAmount = claimAmount;
	}

	public Double getClaimApprovedAmount() {
		return claimApprovedAmount;
	}

	public void setClaimApprovedAmount(Double claimApprovedAmount) {
		this.claimApprovedAmount = claimApprovedAmount;
	}

	public String getRemoveFileById() {
		return removeFileById;
	}

	public void setRemoveFileById(String removeFileById) {
		this.removeFileById = removeFileById;
	}

}
