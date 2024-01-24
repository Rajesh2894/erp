package com.abm.mainet.tradeLicense.dto;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;

public class InspectionDetailDto implements Serializable {

	private static final long serialVersionUID = 4813309383322165745L;

	private Long inId;

	@JsonIgnore
	private TradeMasterDetailDTO masterTradeId;
	private Long inspNo;
	private String licNo;
	private Date inspDate;
	private String inspectorName;
	private String description;
	private String remark;
	private Long orgId;
	private Date createdDate;
	private Long createdBy;
	private Date updatedDate;
	private Long updatedBy;
	private String lgIpMac;
	private String lgIpMacUpd;
	private String mobNo;
	private String ApplicantName;
	private String emailId;

	public Long getInId() {
		return inId;
	}

	public void setInId(Long inId) {
		this.inId = inId;
	}

	public TradeMasterDetailDTO getMasterTradeId() {
		return masterTradeId;
	}

	public void setMasterTradeId(TradeMasterDetailDTO masterTradeId) {
		this.masterTradeId = masterTradeId;
	}

	public Long getInspNo() {
		return inspNo;
	}

	public void setInspNo(Long inspNo) {
		this.inspNo = inspNo;
	}

	public String getLicNo() {
		return licNo;
	}

	public void setLicNo(String licNo) {
		this.licNo = licNo;
	}

	public Date getInspDate() {
		return inspDate;
	}

	public void setInspDate(Date inspDate) {
		this.inspDate = inspDate;
	}

	public String getInspectorName() {
		return inspectorName;
	}

	public void setInspectorName(String inspectorName) {
		this.inspectorName = inspectorName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
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

	public String getMobNo() {
		return mobNo;
	}

	public void setMobNo(String mobNo) {
		this.mobNo = mobNo;
	}

	public String getApplicantName() {
		return ApplicantName;
	}

	public void setApplicantName(String applicantName) {
		ApplicantName = applicantName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

}
