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
@Table(name="tb_transfer_duty_scheduling_hist")
public class TransferAndDutySchedulingOfStaffHist implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name="TRANSFER_ID_HIS")
	private Long transferIdHis;

	@Column(name="CONT_STAFF_ID_NO")
	private String contStaffIdNo;

	@Column(name="CONT_STAFF_NAME")
	private String contStaffName;

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

	@Column(name="DAY_PREFIX_ID")
	private Long dayPrefixId;

	@Column(name="EMP_TYPE_ID")
	private Long empTypeId;

	@Column(name="LG_IP_MAC")
	private String lgIpMac;

	@Column(name="LG_IP_MAC_UPD")
	private String lgIpMacUpd;

	@Column(name="LOC_ID")
	private Long locId;

	private Long orgid;

	@Column(name="REASON_TRANSFER")
	private String reasonTransfer;

	private String remarks;

	@Column(name="TRANSFER_ID")
	private Long transferId;

	@Column(name="UPDATED_BY")
	private Long updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATED_DATE")
	private Date updatedDate;

	public TransferAndDutySchedulingOfStaffHist() {
	}

	public Long getTransferIdHis() {
		return transferIdHis;
	}

	public void setTransferIdHis(Long transferIdHis) {
		this.transferIdHis = transferIdHis;
	}

	public String getContStaffIdNo() {
		return contStaffIdNo;
	}

	public void setContStaffIdNo(String contStaffIdNo) {
		this.contStaffIdNo = contStaffIdNo;
	}

	public String getContStaffName() {
		return contStaffName;
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

	public String getReasonTransfer() {
		return reasonTransfer;
	}

	public void setReasonTransfer(String reasonTransfer) {
		this.reasonTransfer = reasonTransfer;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Long getTransferId() {
		return transferId;
	}

	public void setTransferId(Long transferId) {
		this.transferId = transferId;
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
        return new String[] { "TDH", "tb_transfer_duty_scheduling_hist", "transferIdHis" };
    }
	
	
}
