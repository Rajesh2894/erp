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
@Table(name = "Tb_Sfac_License_Info_Detail")
public class LicenseInformationDetEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7664574094496963284L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "LIC_ID", nullable = false)
	private Long licId;

	@ManyToOne
	@JoinColumn(name = "FPM_ID", referencedColumnName = "FPM_ID")
	private FPOProfileManagementMaster fpoProfileMgmtMaster;

	@Column(name = "LICENSE_TYPE")
	private Long LicenseType;
	
	@Column(name = "LICENSE_NAME")
	private String licenseName;
	
	@Column(name = "LICENSE_DESC")
	private String licenseDesc;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LIC_ISSUE_DATE")
	private Date licIssueDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LIC_EXP_DATE")
	private Date licExpDate;

	@Column(name = "LIC_ISSUE_AUTH")
	private String licIssueAuth;
	
/*	@Column(name = "DCOUMENT_NAME")
	private String documentName;*/

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
	 * @return the licId
	 */
	public Long getLicId() {
		return licId;
	}

	/**
	 * @param licId the licId to set
	 */
	public void setLicId(Long licId) {
		this.licId = licId;
	}

	/**
	 * @return the licenseType
	 */
	public Long getLicenseType() {
		return LicenseType;
	}

	/**
	 * @param licenseType the licenseType to set
	 */
	public void setLicenseType(Long licenseType) {
		LicenseType = licenseType;
	}

	/**
	 * @return the licenseDesc
	 */
	public String getLicenseDesc() {
		return licenseDesc;
	}

	/**
	 * @param licenseDesc the licenseDesc to set
	 */
	public void setLicenseDesc(String licenseDesc) {
		this.licenseDesc = licenseDesc;
	}

	/**
	 * @return the licIssueDate
	 */
	public Date getLicIssueDate() {
		return licIssueDate;
	}

	/**
	 * @param licIssueDate the licIssueDate to set
	 */
	public void setLicIssueDate(Date licIssueDate) {
		this.licIssueDate = licIssueDate;
	}

	/**
	 * @return the licExpDate
	 */
	public Date getLicExpDate() {
		return licExpDate;
	}

	/**
	 * @param licExpDate the licExpDate to set
	 */
	public void setLicExpDate(Date licExpDate) {
		this.licExpDate = licExpDate;
	}

	/**
	 * @return the licIssueAuth
	 */
	public String getLicIssueAuth() {
		return licIssueAuth;
	}

	/**
	 * @param licIssueAuth the licIssueAuth to set
	 */
	public void setLicIssueAuth(String licIssueAuth) {
		this.licIssueAuth = licIssueAuth;
	}
	
	

	/*public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
*/
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
	 * @return the fPOProfileMgmtMaster
	 */
	public FPOProfileManagementMaster getFPOProfileMgmtMaster() {
		return fpoProfileMgmtMaster;
	}

	/**
	 * @param fPOProfileMgmtMaster the fPOProfileMgmtMaster to set
	 */
	public void setFPOProfileMgmtMaster(FPOProfileManagementMaster fPOProfileMgmtMaster) {
		fpoProfileMgmtMaster = fPOProfileMgmtMaster;
	}

	public String[] getPkValues() {
		return new String[] { "SFAC", "Tb_Sfac_License_Info_Detail", "LIC_ID" };
	}

	public String getLicenseName() {
		return licenseName;
	}

	public void setLicenseName(String licenseName) {
		this.licenseName = licenseName;
	}
	
	

}
