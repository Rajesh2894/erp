/**
 * 
 */
package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.domain.BankMasterEntity;
import com.abm.mainet.sfac.dto.FPOMasterDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author pooja.maske
 *
 */
public class FPOBankDetailDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1789642678841006293L;

	private Long bkId;

	@JsonIgnore
	private FPOMasterDto masterDto;

	private String bankName;

	private String accountNo;

	private String ifscCode;

	private String branchName;

	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;

	private Long selectedBankId;

	private String ifscCodeId;

	private Long applicationId;

	private String appStatus;

	private List<BankMasterEntity> bankMasterList = new ArrayList<BankMasterEntity>();

	/**
	 * @return the bkId
	 */

	public Long getBkId() {
		return bkId;
	}

	public List<BankMasterEntity> getBankMasterList() {
		return bankMasterList;
	}

	public void setBankMasterList(List<BankMasterEntity> bankMasterList) {
		this.bankMasterList = bankMasterList;
	}

	/**
	 * @param bkId the bkId to set
	 */
	public void setBkId(Long bkId) {
		this.bkId = bkId;
	}

	/**
	 * @return the masterDto
	 */
	public FPOMasterDto getMasterDto() {
		return masterDto;
	}

	/**
	 * @param masterDto the masterDto to set
	 */
	public void setMasterDto(FPOMasterDto masterDto) {
		this.masterDto = masterDto;
	}

	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param bankName the bankName to set
	 */
	public void setBankName(String bankName) {
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
	 * @return the selectedBankId
	 */
	public Long getSelectedBankId() {
		return selectedBankId;
	}

	/**
	 * @param selectedBankId the selectedBankId to set
	 */
	public void setSelectedBankId(Long selectedBankId) {
		this.selectedBankId = selectedBankId;
	}

	/**
	 * @return the ifscCodeId
	 */
	public String getIfscCodeId() {
		return ifscCodeId;
	}

	/**
	 * @param ifscCodeId the ifscCodeId to set
	 */
	public void setIfscCodeId(String ifscCodeId) {
		this.ifscCodeId = ifscCodeId;
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

}
