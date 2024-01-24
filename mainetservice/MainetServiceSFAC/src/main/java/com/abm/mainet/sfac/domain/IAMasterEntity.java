/**
 * 
 */
package com.abm.mainet.sfac.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author pooja.maske
 *
 */
@Entity
@Table(name = "TB_SFAC_IA_Master")
public class IAMasterEntity implements Serializable {

	/**
	 * 
	 */

	private static final long serialVersionUID = 8884153118635657271L;
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "IA_ID", unique = true, nullable = false)
	private Long IAId;

	@Column(name = "IA_NAME")
	private String IAName;

	@Column(name = "IA_ONBOARDING_YEAR")
	private Long alcYear;

	@Column(name = "IA_ADDRESS")
	private String iaAddress;

	@Column(name = "IA_PIN_CODE")
	private String iaPinCode;

	@Column(name = "DEPTID", nullable = true)
	private Long deptId;

	@Column(name = "ORGID", nullable = false)
	private Long orgId;

	@Column(name = "LEVEL", length = 1)
	private String level;

	@Column(name = "STATE")
	private Long State;

	@Column(name = "IA_SHORT_NAME", length = 200)
	private String iaShortName;

	@Column(name = "AC_IN_STATUS", length = 1, nullable = true)
	private String activeInactiveStatus;

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

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "masterEntity", cascade = CascadeType.ALL)
	private List<IAMasterDetailEntity> iaMasterDetail = new ArrayList<>();

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
	 * @return the iAName
	 */
	public String getIAName() {
		return IAName;
	}

	/**
	 * @param iAName the iAName to set
	 */
	public void setIAName(String iAName) {
		IAName = iAName;
	}

	/**
	 * @return the alcYear
	 */
	public Long getAlcYear() {
		return alcYear;
	}

	/**
	 * @param alcYear the alcYear to set
	 */
	public void setAlcYear(Long alcYear) {
		this.alcYear = alcYear;
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
	 * @return the iaAddress
	 */
	public String getIaAddress() {
		return iaAddress;
	}

	/**
	 * @param iaAddress the iaAddress to set
	 */
	public void setIaAddress(String iaAddress) {
		this.iaAddress = iaAddress;
	}

	/**
	 * @return the iaPinCode
	 */
	public String getIaPinCode() {
		return iaPinCode;
	}

	/**
	 * @param iaPinCode the iaPinCode to set
	 */
	public void setIaPinCode(String iaPinCode) {
		this.iaPinCode = iaPinCode;
	}

	/**
	 * @return the iaMasterDetail
	 */
	public List<IAMasterDetailEntity> getIaMasterDetail() {
		return iaMasterDetail;
	}

	/**
	 * @param iaMasterDetail the iaMasterDetail to set
	 */
	public void setIaMasterDetail(List<IAMasterDetailEntity> iaMasterDetail) {
		this.iaMasterDetail = iaMasterDetail;
	}

	/**
	 * @return the deptId
	 */
	public Long getDeptId() {
		return deptId;
	}

	/**
	 * @param deptId the deptId to set
	 */
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	/**
	 * @return the level
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(String level) {
		this.level = level;
	}

	/**
	 * @return the state
	 */
	public Long getState() {
		return State;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(Long state) {
		State = state;
	}

	/**
	 * @return the iaShortName
	 */
	public String getIaShortName() {
		return iaShortName;
	}

	/**
	 * @param iaShortName the iaShortName to set
	 */
	public void setIaShortName(String iaShortName) {
		this.iaShortName = iaShortName;
	}

	/**
	 * @return the activeInactiveStatus
	 */
	public String getActiveInactiveStatus() {
		return activeInactiveStatus;
	}

	/**
	 * @param activeInactiveStatus the activeInactiveStatus to set
	 */
	public void setActiveInactiveStatus(String activeInactiveStatus) {
		this.activeInactiveStatus = activeInactiveStatus;
	}

	public String[] getPkValues() {
		return new String[] { "SFAC", "TB_SFAC_IA_Master", "IA_ID" };
	}

}
