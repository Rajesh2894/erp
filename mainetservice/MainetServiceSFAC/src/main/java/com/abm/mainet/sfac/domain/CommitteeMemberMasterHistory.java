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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author pooja.maske
 *
 */
@Entity
@Table(name = "TB_SFAC_COMMITTEE_MEMBER_MAST_HIST")
public class CommitteeMemberMasterHistory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4843816640178989415L;
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "COM_MEM_ID_H", unique = true, nullable = false)
	private Long comMemIdH;

	@Column(name = "COM_MEM_ID")
	private Long comMemberId;

	@Column(name = "COMMITTEE_TYPE_ID")
	private Long committeeTypeId;

	@Temporal(TemporalType.DATE)
	@Column(name = "DISSOLVE_DATE")
	private Date dissolveDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "FROM_DATE")
	private Date fromDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "TO_DATE")
	private Date toDate;

	@Column(name = "STATUS")
	private Long status;

	@Column(name = "MEMBER_NAME")
	private String memberName;

	@Column(name = "DESIGNATION")
	private String designation;

	@Column(name = "EMAIL_ID")
	private String emailId;

	@Column(name = "CONTACT_NO")
	private String contactNo;

	@Column(name = "ORGANIZATION")
	private String organization;

	@Column(name = "H_STATUS", length = 1)
	private String historyStatus;

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
	 * @return the comMemIdH
	 */
	public Long getComMemIdH() {
		return comMemIdH;
	}

	/**
	 * @param comMemIdH the comMemIdH to set
	 */
	public void setComMemIdH(Long comMemIdH) {
		this.comMemIdH = comMemIdH;
	}

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

	public static String[] getPkValues() {
		return new String[] { "SFAC", "TB_SFAC_COMMITTEE_MEMBER_MAST_HIST", "COM_MEM_ID_H" };
	}

}
