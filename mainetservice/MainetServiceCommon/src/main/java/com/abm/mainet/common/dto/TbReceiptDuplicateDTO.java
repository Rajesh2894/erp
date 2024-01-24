/**
 * 
 */
package com.abm.mainet.common.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author sarojkumar.yadav
 *
 */
public class TbReceiptDuplicateDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8031129980760491332L;

	private Long dupReceiptId;
	private Long rmRcpId;
	private Long rmRcpNo;
	private Date rmDate;
	private Long applicationId;
	private String DeptCode;
	private String serviceCode;
	private Long refId;
	private String additionalRefNo;
	private String dupReceiptData;
	private Long orgId;
	private Long createdBy;
	private Date createdDate;
	private Long updatedBy;
	private Date updatedDate;
	private String lgIpMac;
	private String lgIpMacUpd;

	public Long getDupReceiptId() {
		return dupReceiptId;
	}

	public void setDupReceiptId(Long dupReceiptId) {
		this.dupReceiptId = dupReceiptId;
	}

	public Long getRmRcpId() {
		return rmRcpId;
	}

	public void setRmRcpId(Long rmRcpId) {
		this.rmRcpId = rmRcpId;
	}

	public Long getRmRcpNo() {
		return rmRcpNo;
	}

	public void setRmRcpNo(Long rmRcpNo) {
		this.rmRcpNo = rmRcpNo;
	}

	public Date getRmDate() {
		return rmDate;
	}

	public void setRmDate(Date rmDate) {
		this.rmDate = rmDate;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public String getDeptCode() {
		return DeptCode;
	}

	public void setDeptCode(String deptCode) {
		DeptCode = deptCode;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public Long getRefId() {
		return refId;
	}

	public void setRefId(Long refId) {
		this.refId = refId;
	}

	public String getAdditionalRefNo() {
		return additionalRefNo;
	}

	public void setAdditionalRefNo(String additionalRefNo) {
		this.additionalRefNo = additionalRefNo;
	}

	public String getDupReceiptData() {
		return dupReceiptData;
	}

	public void setDupReceiptData(String dupReceiptData) {
		this.dupReceiptData = dupReceiptData;
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

	@Override
	public String toString() {
		return "TbReceiptDuplicateDTO [dupReceiptId=" + dupReceiptId + ", rmRcpId=" + rmRcpId + ", rmRcpNo=" + rmRcpNo
				+ ", rmDate=" + rmDate + ", applicationId=" + applicationId + ", DeptCode=" + DeptCode
				+ ", serviceCode=" + serviceCode + ", refId=" + refId + ", additionalRefNo=" + additionalRefNo
				+ ", dupReceiptData=" + dupReceiptData + ", orgId=" + orgId + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate
				+ ", lgIpMac=" + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd + "]";
	}

}
