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

/**
 * The persistent class for the tb_employee_scheduling_hist database table.
 * 
 */


@Entity
@Table(name = "tb_employee_scheduling_hist")
public class EmployeeSchedulingHist implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "H_EMPL_SCDL_ID")
	private Long hEmplScdlId;

	@Column(name = "CPD_SHIFT_ID")
	private Long cpdShiftId;

	@Column(name = "CREATED_BY")
	private Long createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "DAY_PREFIX_ID")
	private Long dayPrefixId;

	@Column(name = "EMPL_SCDL_ID")
	private Long emplScdlId;

	@Column(name = "LG_IP_MAC")
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD")
	private String lgIpMacUpd;

	@Column(name = "LOC_ID")
	private Long locId;

	private Long orgid;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "VENDOR_ID")
	private Long vendorId;
	
	@Column(name="CONT_STAFF_SCH_FROM")
	private Date contStaffSchFrom;

	@Column(name="CONT_STAFF_SCH_TO")
	private Date contStaffSchTo;

	@Column(name="CONT_STAFF_ID_NO")
	private String contStaffIdNo;

	@Column(name="EMP_TYPE_ID")
	private Long empTypeId;

	public EmployeeSchedulingHist() {
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

	public String getContStaffIdNo() {
		return contStaffIdNo;
	}

	public void setContStaffIdNo(String contStaffIdNo) {
		this.contStaffIdNo = contStaffIdNo;
	}

	public Long getEmpTypeId() {
		return empTypeId;
	}

	public void setEmpTypeId(Long empTypeId) {
		this.empTypeId = empTypeId;
	}

	public Long gethEmplScdlId() {
		return hEmplScdlId;
	}

	public void sethEmplScdlId(Long hEmplScdlId) {
		this.hEmplScdlId = hEmplScdlId;
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

	public Long getDayPrefixId() {
		return dayPrefixId;
	}

	public void setDayPrefixId(Long dayPrefixId) {
		this.dayPrefixId = dayPrefixId;
	}

	
	public Long getEmplScdlId() {
		return emplScdlId;
	}

	public void setEmplScdlId(Long emplScdlId) {
		this.emplScdlId = emplScdlId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setLocId(Long locId) {
		this.locId = locId;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}

	public String getLgIpMac() {
		return this.lgIpMac;
	}

	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}

	public String getLgIpMacUpd() {
		return this.lgIpMacUpd;
	}

	public void setLgIpMacUpd(String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}

	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getLocId() {
		return locId;
	}

	public Long getOrgid() {
		return orgid;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public Long getVendorId() {
		return vendorId;
	}
	public String[] getPkValues() {
        return new String[] { "ESH", "tb_employee_scheduling_hist", "hEmplScdlId" };
    }

}