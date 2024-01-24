package com.abm.mainet.securitymanagement.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="TB_SM_CONTRACT_STAFF_MASTER")
public class ContractualStaffMaster implements Serializable {

	private static final long serialVersionUID = 4969055362999468277L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
	@Column(name="CONT_STSFF_ID")
	private Long contStsffId;

	@Column(name="CONT_STAFF_ADDRESS")
	private String contStaffAddress;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CONT_STAFF_APPOINT_DATE")
	private Date contStaffAppointDate;

	@Column(name="CONT_STAFF_ID_NO")
	private String contStaffIdNo;

	@Column(name="CONT_STAFF_MOB")
	private String contStaffMob;

	@Column(name="CONT_STAFF_NAME")
	private String contStaffName;

	@Column(name="CONT_STAFF_SCH_FROM")
	private Date contStaffSchFrom;

	@Column(name="CONT_STAFF_SCH_TO")
	private Date contStaffSchTo;

	@Column(name="CPD_SHIFT_ID")
	private Long cpdShiftId;

	@Column(name="CREATED_BY")
	private Long createdBy;
	
	@Column(name="DAY_PREFIX_ID")
	private Long dayPrefixId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name="DSG_ID")
	private Long dsgId;

	@Column(name="LG_IP_MAC")
	private String lgIpMac;

	@Column(name="LG_IP_MAC_UPD")
	private String lgIpMacUpd;

	@Column(name="LOC_ID")
	private Long locId;
	
	@Column(name="ORGID")
	private Long orgid;

	@Column(name="UPDATED_BY")
	private Long updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATED_DATE")
	private Date updatedDate;

	@Column(name="VENDOR_ID")
	private Long vendorId;

	@Column(name = "STATUS", nullable = false, length = 2)
	private String status;
	
	@Column(name = "SEX", length = 1, nullable = false)
	private String sex;
	
	@Column(name="DOB")
	private Date dob;
	
	@Column(name = "EMP_TYPE_FLAG")
	private String empType;

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Long getContStsffId() {
		return this.contStsffId;
	}

	public void setContStsffId(Long contStsffId) {
		this.contStsffId = contStsffId;
	}
	
	public Long getDayPrefixId() {
		return dayPrefixId;
	}

	public void setDayPrefixId(Long dayPrefixId) {
		this.dayPrefixId = dayPrefixId;
	}

	public String getContStaffAddress() {
		return this.contStaffAddress;
	}

	public void setContStaffAddress(String contStaffAddress) {
		this.contStaffAddress = contStaffAddress;
	}

	public Date getContStaffAppointDate() {
		return this.contStaffAppointDate;
	}

	public void setContStaffAppointDate(Date contStaffAppointDate) {
		this.contStaffAppointDate = contStaffAppointDate;
	}

	public String getContStaffIdNo() {
		return this.contStaffIdNo;
	}

	public void setContStaffIdNo(String contStaffIdNo) {
		this.contStaffIdNo = contStaffIdNo;
	}

	public String getContStaffMob() {
		return this.contStaffMob;
	}

	public void setContStaffMob(String contStaffMob) {
		this.contStaffMob = contStaffMob;
	}

	public String getContStaffName() {
		return this.contStaffName;
	}

	public void setContStaffName(String contStaffName) {
		this.contStaffName = contStaffName;
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
		return this.cpdShiftId;
	}

	public void setCpdShiftId(Long cpdShiftId) {
		this.cpdShiftId = cpdShiftId;
	}

	public Long getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	

	public Long getDsgId() {
		return dsgId;
	}

	public void setDsgId(Long dsgId) {
		this.dsgId = dsgId;
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

	public Long getLocId() {
		return this.locId;
	}

	public void setLocId(Long locId) {
		this.locId = locId;
	}

	public Long getOrgid() {
		return this.orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}

	public Long getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getVendorId() {
		return this.vendorId;
	}

	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}

	public String getEmpType() {
		return empType;
	}

	public void setEmpType(String empType) {
		this.empType = empType;
	}

	public String[] getPkValues() {
        return new String[] { "SM", "TB_SM_CONTRACT_STAFF_MASTER", "CONT_STSFF_ID" };
    }

	@Override
	public int hashCode() {
		return Objects.hash(contStaffAddress, contStaffAppointDate, contStaffIdNo, contStaffMob, contStaffName,
				contStaffSchFrom, contStaffSchTo, contStsffId, cpdShiftId, createdBy, createdDate, dsgId, lgIpMac,
				lgIpMacUpd, locId, orgid, updatedBy, updatedDate, vendorId,dayPrefixId, status, sex, dob);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ContractualStaffMaster)) {
			return false;
		}
		ContractualStaffMaster other = (ContractualStaffMaster) obj;
		return Objects.equals(contStaffAddress, other.contStaffAddress)
				&& Objects.equals(contStaffAppointDate, other.contStaffAppointDate)
				&& Objects.equals(contStaffIdNo, other.contStaffIdNo)
				&& Objects.equals(contStaffMob, other.contStaffMob)
				&& Objects.equals(contStaffName, other.contStaffName)
				&& Objects.equals(contStaffSchFrom, other.contStaffSchFrom)
				&& Objects.equals(contStaffSchTo, other.contStaffSchTo)
				&& Objects.equals(contStsffId, other.contStsffId) && Objects.equals(cpdShiftId, other.cpdShiftId)
				&& Objects.equals(createdBy, other.createdBy) && Objects.equals(createdDate, other.createdDate)
				&& Objects.equals(dsgId, other.dsgId) && Objects.equals(lgIpMac, other.lgIpMac)
				&& Objects.equals(lgIpMacUpd, other.lgIpMacUpd) && Objects.equals(locId, other.locId)
				&& Objects.equals(orgid, other.orgid) && Objects.equals(updatedBy, other.updatedBy)
				&& Objects.equals(updatedDate, other.updatedDate) && Objects.equals(vendorId, other.vendorId);
	}

	@Override
	public String toString() {
		return "ContractualStaffMaster [contStsffId=" + contStsffId + ", contStaffAddress=" + contStaffAddress
				+ ", contStaffAppointDate=" + contStaffAppointDate + ", contStaffIdNo=" + contStaffIdNo
				+ ", contStaffMob=" + contStaffMob + ", contStaffName=" + contStaffName + ", contStaffSchFrom="
				+ contStaffSchFrom + ", contStaffSchTo=" + contStaffSchTo + ", cpdShiftId=" + cpdShiftId
				+ ", createdBy=" + createdBy + ", dayPrefixId=" + dayPrefixId + ", createdDate=" + createdDate
				+ ", dsgId=" + dsgId + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd + ", locId=" + locId
				+ ", orgid=" + orgid + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + ", vendorId="
				+ vendorId + ", status=" + status + ", sex=" + sex + ", dob=" + dob + "]";
	}

}
