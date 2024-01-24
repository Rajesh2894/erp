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
@Table(name = "TB_SFAC_IA_DET_HIST")
public class IAMasterDetailHistory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9001852022111099591L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "IAD_ID_H", unique = true, nullable = false)
	private Long iadIdH;

	@ManyToOne
	@JoinColumn(name = "IAH_ID", referencedColumnName = "IAH_ID")
	private IAMasterHistEntity masterHistEntity;

	@Column(name = "IAD_ID")
	private Long iadId;

	@Column(name = "DSGID")
	private String dsgId;

	@Column(name = "TITLE_ID")
	private Long titleId;

	@Column(name = "F_NAME")
	private String fName;

	@Column(name = "M_NAME")
	private String mName;

	@Column(name = "L_NAME")
	private String lName;

	@Column(name = "EMAIL_ID")
	private String emailId;

	@Column(name = "CONTACT_NO")
	private String contactNo;

	@Column(name = "STATUS", length = 1)
	private String status;

	@Column(name = "role")
	private Long role;

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
	 * @return the iadIdH
	 */
	public Long getIadIdH() {
		return iadIdH;
	}

	/**
	 * @param iadIdH the iadIdH to set
	 */
	public void setIadIdH(Long iadIdH) {
		this.iadIdH = iadIdH;
	}

	/**
	 * @return the masterHistEntity
	 */
	public IAMasterHistEntity getMasterHistEntity() {
		return masterHistEntity;
	}

	/**
	 * @param masterHistEntity the masterHistEntity to set
	 */
	public void setMasterHistEntity(IAMasterHistEntity masterHistEntity) {
		this.masterHistEntity = masterHistEntity;
	}

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

	public String[] getPkValues() {
		return new String[] { "SFAC", "TB_SFAC_CBBO_DET_HIST", "CBBOD_ID_H" };
	}

}
