/**
 * 
 */
package com.abm.mainet.sfac.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author pooja.maske
 *
 */
@Entity
@Table(name = "TB_SFAC_FPO_BANK_DET_HIST")
public class FPOBankDetailHistory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6998656135998993886L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "BK_ID_H", unique = true, nullable = false)
	private Long bkIdH;

	@Column(name = "BK_ID")
	private Long bkId;

	@ManyToOne
	@JoinColumn(name = "FPO_ID_H", referencedColumnName = "FPO_ID_H")
	private FPOMasterHistory masterHistEntity;

	@Column(name = "BANK_NAME")
	private Long bankName;

	@Column(name = "ACCOUNT_NO")
	private String accountNo;

	@Column(name = "IFSC_CODE")
	private String ifscCode;

	@Column(name = "BRANCH_NAME")
	private String branchName;

	@Column(name = "H_STATUS", length = 1)
	private String historyStatus;

	@Column(name = "APPLICATION_ID")
	private Long applicationId;

	@Column(name = "APP_STATUS")
	private String appStatus;

	@Column(name = "ORGID", nullable = false)
	private Long orgId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;

	@Column(name = "CREATED_BY", nullable = false)
	private Long createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Column(name = "LG_IP_MAC", nullable = false, length = 100)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100)
	private String lgIpMacUpd;

	/**
	 * @return the bkIdH
	 */
	public Long getBkIdH() {
		return bkIdH;
	}

	/**
	 * @param bkIdH the bkIdH to set
	 */
	public void setBkIdH(Long bkIdH) {
		this.bkIdH = bkIdH;
	}

	/**
	 * @return the bkId
	 */
	public Long getBkId() {
		return bkId;
	}

	/**
	 * @param bkId the bkId to set
	 */
	public void setBkId(Long bkId) {
		this.bkId = bkId;
	}

	/**
	 * @return the masterHistEntity
	 */
	public FPOMasterHistory getMasterHistEntity() {
		return masterHistEntity;
	}

	/**
	 * @param masterHistEntity the masterHistEntity to set
	 */
	public void setMasterHistEntity(FPOMasterHistory masterHistEntity) {
		this.masterHistEntity = masterHistEntity;
	}

	/**
	 * @return the bankName
	 */
	public Long getBankName() {
		return bankName;
	}

	/**
	 * @param bankName the bankName to set
	 */
	public void setBankName(Long bankName) {
		this.bankName = bankName;
	}

	/**
	 * @return the accountNo
	 */
	public String getAccountNo() {
		return accountNo;
	}

	/**
	 * @param accountNo the accountNo to set
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	/**
	 * @return the ifscCode
	 */
	public String getIfscCode() {
		return ifscCode;
	}

	/**
	 * @param ifscCode the ifscCode to set
	 */
	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	/**
	 * @return the branchName
	 */
	public String getBranchName() {
		return branchName;
	}

	/**
	 * @param branchName the branchName to set
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	/**
	 * @return the historyStatus
	 */
	public String getHistoryStatus() {
		return historyStatus;
	}

	/**
	 * @param historyStatus the historyStatus to set
	 */
	public void setHistoryStatus(String historyStatus) {
		this.historyStatus = historyStatus;
	}

	/**
	 * @return the orgId
	 */
	public Long getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the createdBy
	 */
	public Long getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the updatedDate
	 */
	public Date getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * @return the updatedBy
	 */
	public Long getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the lgIpMac
	 */
	public String getLgIpMac() {
		return lgIpMac;
	}

	/**
	 * @param lgIpMac the lgIpMac to set
	 */
	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}

	/**
	 * @return the lgIpMacUpd
	 */
	public String getLgIpMacUpd() {
		return lgIpMacUpd;
	}

	/**
	 * @param lgIpMacUpd the lgIpMacUpd to set
	 */
	public void setLgIpMacUpd(String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}

	
	/**
	 * @return the applicationId
	 */
	public Long getApplicationId() {
		return applicationId;
	}

	/**
	 * @param applicationId the applicationId to set
	 */
	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	/**
	 * @return the appStatus
	 */
	public String getAppStatus() {
		return appStatus;
	}

	/**
	 * @param appStatus the appStatus to set
	 */
	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}

	public String[] getPkValues() {
		return new String[] { "SFAC", "TB_SFAC_FPO_BANK_DET_HIST", "BK_ID_H" };
	}
}
