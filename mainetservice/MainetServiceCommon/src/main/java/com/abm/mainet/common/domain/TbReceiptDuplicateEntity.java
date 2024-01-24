/**
 * 
 */
package com.abm.mainet.common.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.constant.MainetConstants;

/**
 * @author sarojkumar.yadav
 *
 */
@Entity
@Table(name = "TB_DUP_RECEIPT")
public class TbReceiptDuplicateEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9102279112901327076L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "DUP_RCID", nullable = false)
	private Long dupReceiptId;

	@Column(name = "RM_RCPTID", nullable = false)
	private Long rmRcpId;

	@Column(name = "RM_RCPTNO", nullable = false)
	private Long rmRcpNo;

	@Column(name = "RM_DATE", nullable = false)
	private Date rmDate;

	@Column(name = "APM_APPLICATION_ID", nullable = true)
	private Long applicationId;

	@Column(name = "DP_DEPTCODE", nullable = false)
	private String DeptCode;

	@Column(name = "SM_SHORTDESC", nullable = true)
	private String serviceCode;

	@Column(name = "REF_ID", nullable = true)
	private Long refId;

	@Column(name = "ADDITIONAL_REF_NO", nullable = true)
	private String additionalRefNo;

	@Column(name = "DUP_RECEIPT", nullable = false)
	private String dupReceiptData;

	@Column(name = "ORGID", nullable = true)
	private Long orgId;

	@Column(name = "CREATED_BY", nullable = false)
	private Long createdBy;

	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;

	@Column(name = "UPDATED_BY", nullable = true)
	private Long updatedBy;

	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;

	@Column(name = "LG_IP_MAC", nullable = false)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", nullable = true)
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

	public static String[] getPkValues() {
		return new String[] { MainetConstants.CommonConstants.COM, "TB_DUP_RECEIPT", "DUP_RCID" };
	}
}
