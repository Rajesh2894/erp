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
public class CommitteeMemberMasterDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4659632271180454509L;

	private Long comMemberId;

	private Long committeeTypeId;

	private Long memberId;

	private Date fromDate;

	private Date toDate;

	private Date dissolveDate;

	private String memberName;

	private String designation;

	private String emailId;

	private String contactNo;

	private String organization;

	private Long status;

	private String statusDesc;

	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private Long langId;

	private String memberSince;

	/**
	 * @return the comMemberId
	 */
	public Long getComMemberId() {
		return comMemberId;
	}

	/**
	 * @param comMemberId the comMemberId to set
	 */
	public void setComMemberId(Long comMemberId) {
		this.comMemberId = comMemberId;
	}

	/**
	 * @return the committeeTypeId
	 */
	public Long getCommitteeTypeId() {
		return committeeTypeId;
	}

	/**
	 * @param committeeTypeId the committeeTypeId to set
	 */
	public void setCommitteeTypeId(Long committeeTypeId) {
		this.committeeTypeId = committeeTypeId;
	}

	/**
	 * @return the memberId
	 */
	public Long getMemberId() {
		return memberId;
	}

	/**
	 * @param memberId the memberId to set
	 */
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	/**
	 * @return the fromDate
	 */
	public Date getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the toDate
	 */
	public Date getToDate() {
		return toDate;
	}

	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return the dissolveDate
	 */
	public Date getDissolveDate() {
		return dissolveDate;
	}

	/**
	 * @param dissolveDate the dissolveDate to set
	 */
	public void setDissolveDate(Date dissolveDate) {
		this.dissolveDate = dissolveDate;
	}

	/**
	 * @return the memberName
	 */
	public String getMemberName() {
		return memberName;
	}

	/**
	 * @param memberName the memberName to set
	 */
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	/**
	 * @return the designation
	 */
	public String getDesignation() {
		return designation;
	}

	/**
	 * @param designation the designation to set
	 */
	public void setDesignation(String designation) {
		this.designation = designation;
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
	 * @return the organization
	 */
	public String getOrganization() {
		return organization;
	}

	/**
	 * @param organization the organization to set
	 */
	public void setOrganization(String organization) {
		this.organization = organization;
	}

	/**
	 * @return the status
	 */
	public Long getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Long status) {
		this.status = status;
	}

	/**
	 * @return the statusDesc
	 */
	public String getStatusDesc() {
		return statusDesc;
	}

	/**
	 * @param statusDesc the statusDesc to set
	 */
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	/**
	 * @return the memberSince
	 */
	public String getMemberSince() {
		return memberSince;
	}

	/**
	 * @param memberSince the memberSince to set
	 */
	public void setMemberSince(String memberSince) {
		this.memberSince = memberSince;
	}

}
