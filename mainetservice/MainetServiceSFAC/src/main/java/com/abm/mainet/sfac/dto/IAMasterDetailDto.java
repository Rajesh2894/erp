/**
 * 
 */
package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author pooja.maske
 *
 */

public class IAMasterDetailDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7307448848320278548L;

	private Long iadId;

	private Long IAId; // fk

	private String  dsgId;

	private Long titleId;

	private String fName;

	private String mName;

	private String lName;

	private String emailId;

	private String contactNo;

	private Long langId;

	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;
	
	private String status;
	
	private Long role;

	/**
	 * @return the iadId
	 */
	public Long getIadId() {
		return iadId;
	}

	/**
	 * @param iadId the iadId to set
	 */
	public void setIadId(Long iadId) {
		this.iadId = iadId;
	}

	/**
	 * @return the iAId
	 */
	public Long getIAId() {
		return IAId;
	}

	/**
	 * @param iAId the iAId to set
	 */
	public void setIAId(Long iAId) {
		IAId = iAId;
	}

	/**
	 * @return the fName
	 */
	public String getfName() {
		return fName;
	}

	/**
	 * @param fName the fName to set
	 */
	public void setfName(String fName) {
		this.fName = fName;
	}

	/**
	 * @return the mName
	 */
	public String getmName() {
		return mName;
	}

	/**
	 * @param mName the mName to set
	 */
	public void setmName(String mName) {
		this.mName = mName;
	}

	/**
	 * @return the lName
	 */
	public String getlName() {
		return lName;
	}

	/**
	 * @param lName the lName to set
	 */
	public void setlName(String lName) {
		this.lName = lName;
	}

	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/**
	 * @return the contactNo
	 */
	public String getContactNo() {
		return contactNo;
	}

	/**
	 * @param contactNo the contactNo to set
	 */
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	/**
	 * @return the langId
	 */
	public Long getLangId() {
		return langId;
	}

	/**
	 * @param langId the langId to set
	 */
	public void setLangId(Long langId) {
		this.langId = langId;
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
	 * @return the titleId
	 */
	public Long getTitleId() {
		return titleId;
	}

	/**
	 * @param titleId the titleId to set
	 */
	public void setTitleId(Long titleId) {
		this.titleId = titleId;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the role
	 */
	public Long getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(Long role) {
		this.role = role;
	}

	/**
	 * @return the dsgId
	 */
	public String getDsgId() {
		return dsgId;
	}

	/**
	 * @param dsgId the dsgId to set
	 */
	public void setDsgId(String dsgId) {
		this.dsgId = dsgId;
	}
	
	

}
