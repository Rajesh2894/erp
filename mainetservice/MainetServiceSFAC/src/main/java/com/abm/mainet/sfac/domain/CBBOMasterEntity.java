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
@Table(name = "Tb_SFAC_Cbbo_Master")
public class CBBOMasterEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3556318245557883295L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "CBBO_ID", unique = true, nullable = false)
	private Long cbboId;

	@Column(name = "CBBO_NAME")
	private String cbboName;

	@Column(name = "CBBO_UNIQUE_ID")
	private String cbboUniqueId;

	@Column(name = "IA_NAME")
	private String IAName;

	@Column(name = "SDB1")
	private Long sdb1;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "PIN_CODE")
	private String pinCode;

	@Column(name = "TYPE_OF_PROM_AGE")
	private Long typeofPromAgen;

	@Column(name = "IA_ID")
	private Long iaId;

	@Column(name = "PAN_NO")
	private String panNo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CBBO_ONBOARDING_YEAR")
	private Date alcYearToCBBO;

	@Column(name = "CBBO_APPOINTMENT_YEAR")
	private Long cbboAppoitmentYr;

	@Column(name = "DEPTID", nullable = true)
	private Long deptId;

	@Column(name = "AC_IN_STATUS", length = 1, nullable = true)
	private String activeInactiveStatus;

	@Column(name = "APPL_PENDING_DATE")
	private Date appPendingDate;

	@Column(name = "FPO_ALLOCATION_TARGET")
	private Long fpoAllocationTarget;

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

	@Column(name = "APPLICATION_ID")
	private Long applicationId;

	@Column(name = "STATUS")
	private String appStatus;

	@Column(name = "REMARK")
	private String remark;

	@Column(name = "CB_ID")
	private Long cbId;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "masterEntity", cascade = CascadeType.ALL)
	private List<CBBOMastDetailEntity> cbboMasterDetail = new ArrayList<>();

	/**
	 * @return the cbboId
	 */
	public Long getCbboId() {
		return cbboId;
	}

	/**
	 * @param cbboId the cbboId to set
	 */
	public void setCbboId(Long cbboId) {
		this.cbboId = cbboId;
	}

	/**
	 * @return the cbboName
	 */
	public String getCbboName() {
		return cbboName;
	}

	/**
	 * @param cbboName the cbboName to set
	 */
	public void setCbboName(String cbboName) {
		this.cbboName = cbboName;
	}

	/**
	 * @return the cbboUniqueId
	 */
	public String getCbboUniqueId() {
		return cbboUniqueId;
	}

	/**
	 * @param cbboUniqueId the cbboUniqueId to set
	 */
	public void setCbboUniqueId(String cbboUniqueId) {
		this.cbboUniqueId = cbboUniqueId;
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
	 * @return the sdb1
	 */
	public Long getSdb1() {
		return sdb1;
	}

	/**
	 * @param sdb1 the sdb1 to set
	 */
	public void setSdb1(Long sdb1) {
		this.sdb1 = sdb1;
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
	 * @return the iaId
	 */
	public Long getIaId() {
		return iaId;
	}

	/**
	 * @param iaId the iaId to set
	 */
	public void setIaId(Long iaId) {
		this.iaId = iaId;
	}

	/**
	 * @return the panNo
	 */
	public String getPanNo() {
		return panNo;
	}

	/**
	 * @param panNo the panNo to set
	 */
	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	/**
	 * @return the cbboMasterDetail
	 */
	public List<CBBOMastDetailEntity> getCbboMasterDetail() {
		return cbboMasterDetail;
	}

	/**
	 * @param cbboMasterDetail the cbboMasterDetail to set
	 */
	public void setCbboMasterDetail(List<CBBOMastDetailEntity> cbboMasterDetail) {
		this.cbboMasterDetail = cbboMasterDetail;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the pinCode
	 */
	public String getPinCode() {
		return pinCode;
	}

	/**
	 * @param pinCode the pinCode to set
	 */
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	/**
	 * @return the typeofPromAgen
	 */
	public Long getTypeofPromAgen() {
		return typeofPromAgen;
	}

	/**
	 * @param typeofPromAgen the typeofPromAgen to set
	 */
	public void setTypeofPromAgen(Long typeofPromAgen) {
		this.typeofPromAgen = typeofPromAgen;
	}

	/**
	 * @return the alcYearToCBBO
	 */
	public Date getAlcYearToCBBO() {
		return alcYearToCBBO;
	}

	/**
	 * @param alcYearToCBBO the alcYearToCBBO to set
	 */
	public void setAlcYearToCBBO(Date alcYearToCBBO) {
		this.alcYearToCBBO = alcYearToCBBO;
	}

	/**
	 * @return the cbboAppoitmentYr
	 */
	public Long getCbboAppoitmentYr() {
		return cbboAppoitmentYr;
	}

	/**
	 * @param cbboAppoitmentYr the cbboAppoitmentYr to set
	 */
	public void setCbboAppoitmentYr(Long cbboAppoitmentYr) {
		this.cbboAppoitmentYr = cbboAppoitmentYr;
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

	/**
	 * @return the appPendingDate
	 */
	public Date getAppPendingDate() {
		return appPendingDate;
	}

	/**
	 * @param appPendingDate the appPendingDate to set
	 */
	public void setAppPendingDate(Date appPendingDate) {
		this.appPendingDate = appPendingDate;
	}

	/**
	 * @return the fpoAllocationTarget
	 */
	public Long getFpoAllocationTarget() {
		return fpoAllocationTarget;
	}

	/**
	 * @param fpoAllocationTarget the fpoAllocationTarget to set
	 */
	public void setFpoAllocationTarget(Long fpoAllocationTarget) {
		this.fpoAllocationTarget = fpoAllocationTarget;
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

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the cbId
	 */
	public Long getCbId() {
		return cbId;
	}

	/**
	 * @param cbId the cbId to set
	 */
	public void setCbId(Long cbId) {
		this.cbId = cbId;
	}

	public String[] getPkValues() {
		return new String[] { "SFAC", "Tb_SFAC_Cbbo_Master", "CBBO_ID" };
	}

}
