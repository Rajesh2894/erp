package com.abm.mainet.securitymanagement.domain;

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

@Entity
@Table(name="tb_sm_deployment_of_staff_his")
public class DeploymentOfStaffHist implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
	@Column(name="H_DEPL_ID")
	private Long hDeplId;

	@Column(name="CONT_STAFF_ID_NO")
	private String contStaffIdNo;

	@Column(name="VENDOR_ID")
	private Long vendorId;
	
	@Column(name="CONT_STAFF_MOB")
	private String contStaffMob;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CONT_STAFF_SCH_FROM")
	private Date contStaffSchFrom;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CONT_STAFF_SCH_TO")
	private Date contStaffSchTo;

	@Column(name="CPD_SHIFT_ID")
	private Long cpdShiftId;

	@Column(name="CREATED_BY")
	private Long createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name="DEPL_ID")
	private Long deplId;

	@Column(name="EMP_TYPE_ID")
	private Long empTypeId;

	@Column(name="LG_IP_MAC")
	private String lgIpMac;

	@Column(name="LG_IP_MAC_UPD")
	private String lgIpMacUpd;

	@Column(name="LOC_ID")
	private Long locId;

	private Long orgid;

	private String remarks;

	@Column(name="UPDATED_BY")
	private Long updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATED_DATE")
	private Date updatedDate;

	public DeploymentOfStaffHist() {
	}

	public Long gethDeplId() {
		return hDeplId;
	}

	public Long getVendorId() {
		return vendorId;
	}

	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}

	public void sethDeplId(Long hDeplId) {
		this.hDeplId = hDeplId;
	}
	
	public String getContStaffIdNo() {
		return contStaffIdNo;
	}

	public void setContStaffIdNo(String contStaffIdNo) {
		this.contStaffIdNo = contStaffIdNo;
	}

	public String getContStaffMob() {
		return contStaffMob;
	}

	public void setContStaffMob(String contStaffMob) {
		this.contStaffMob = contStaffMob;
	}

	public Date getContStaffSchFrom() {
		return contStaffSchFrom;
	}

	public void setContStaffSchFrom(Date contStaffSchFrom) {
		this.contStaffSchFrom = contStaffSchFrom;
	}

	public Date getContStaffSchTo() {
		return contStaffSchTo;
	}

	public void setContStaffSchTo(Date contStaffSchTo) {
		this.contStaffSchTo = contStaffSchTo;
	}

	public Long getCpdShiftId() {
		return cpdShiftId;
	}

	public void setCpdShiftId(Long cpdShiftId) {
		this.cpdShiftId = cpdShiftId;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getDeplId() {
		return deplId;
	}

	public void setDeplId(Long deplId) {
		this.deplId = deplId;
	}

	public Long getEmpTypeId() {
		return empTypeId;
	}

	public void setEmpTypeId(Long empTypeId) {
		this.empTypeId = empTypeId;
	}

	public String getLgIpMac() {
		return lgIpMac;
	}

	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}

	public String getLgIpMacUpd() {
		return lgIpMacUpd;
	}

	public void setLgIpMacUpd(String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}

	public Long getLocId() {
		return locId;
	}

	public void setLocId(Long locId) {
		this.locId = locId;
	}

	public Long getOrgid() {
		return orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String[] getPkValues() {
        return new String[] { "DH", "tb_sm_deployment_of_staff_his", "hDeplId" };
    }
}
