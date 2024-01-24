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
@Table(name = "Tb_SFAC_Cbbo_Mast_Det")
public class CBBOMastDetailEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 712563534532732759L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "CBBOD_ID", unique = true, nullable = false)
	private Long cbboDId;

	@ManyToOne
	@JoinColumn(name = "CBBO_ID", referencedColumnName = "CBBO_ID")
	private CBBOMasterEntity masterEntity;

	@Column(name = "DSG_ID")
	private Long dsgId;

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
	 * @return the cbboDId
	 */
	public Long getCbboDId() {
		return cbboDId;
	}

	/**
	 * @param cbboDId the cbboDId to set
	 */
	public void setCbboDId(Long cbboDId) {
		this.cbboDId = cbboDId;
	}

	/**
	 * @return the masterEntity
	 */
	public CBBOMasterEntity getMasterEntity() {
		return masterEntity;
	}

	/**
	 * @param masterEntity the masterEntity to set
	 */
	public void setMasterEntity(CBBOMasterEntity masterEntity) {
		this.masterEntity = masterEntity;
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
	 * @return the dsgId
	 */
	public Long getDsgId() {
		return dsgId;
	}

	/**
	 * @param dsgId the dsgId to set
	 */
	public void setDsgId(Long dsgId) {
		this.dsgId = dsgId;
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

	public String[] getPkValues() {
		return new String[] { "SFAC", "Tb_SFAC_Cbbo_Mast_Det", "CBBOD_ID" };
	}

}
