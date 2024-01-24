package com.abm.mainet.common.integration.acccount.domain;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * 
 * @author Jeetendra.Pal
 *
 */
@Entity
@Table(name = "TB_ADVANCE_REQ")
public class AdvanceRequisition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8242443620941039556L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "ADV_ID", nullable = false)
	private Long advId;

	@Column(name = "VM_VENDORID", nullable = false)
	private Long venderId;

	@Column(name = "DP_DEPTID", nullable = true)
	private Long deptId;

	@Column(name = "AH_HEADID", nullable = true)
	private Long headId;

	@Column(name = "ADV_TYPE", nullable = false)
	private Long advType;

	@Column(name = "ADV_STATUS", nullable = true)
	private String advStatus;

	@Column(name = "ADV_REF_NO", nullable = true)
	private String referenceNo;

	@Column(name = "ADV_PARTICULARS", nullable = false)
	private String particulars;

	@Column(name = "ADV_NO", nullable = false)
	private String advNo;

	@Column(name = "ADV_ENTRYDATE", nullable = false)
	private Date entryDate;

	@Column(name = "ADV_AMOUNT", nullable = false)
	private BigDecimal advAmount;

	@Column(name = "ORGID", nullable = false)
	private Long orgid;

	@Column(name = "CREATED_BY", nullable = false)
	private Long createdBy;

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "LG_IP_MAC", length = 100, nullable = false)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100)
	private String lgIpMacUpd;

	@Column(name = "ah_headdesc", nullable = true)
	private String headDesc;

	@Column(name = "adv_billno", nullable = true)
	private String billNumber;

	@Column(name = "adv_billdate", nullable = true)
	private Date billDate;

	public Long getAdvId() {
		return advId;
	}

	public void setAdvId(Long advId) {
		this.advId = advId;
	}

	public Long getVenderId() {
		return venderId;
	}

	public void setVenderId(Long venderId) {
		this.venderId = venderId;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Long getHeadId() {
		return headId;
	}

	public void setHeadId(Long headId) {
		this.headId = headId;
	}

	public Long getAdvType() {
		return advType;
	}

	public void setAdvType(Long advType) {
		this.advType = advType;
	}

	public String getAdvStatus() {
		return advStatus;
	}

	public void setAdvStatus(String advStatus) {
		this.advStatus = advStatus;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public String getParticulars() {
		return particulars;
	}

	public void setParticulars(String particulars) {
		this.particulars = particulars;
	}

	public String getAdvNo() {
		return advNo;
	}

	public void setAdvNo(String advNo) {
		this.advNo = advNo;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public BigDecimal getAdvAmount() {
		return advAmount;
	}

	public void setAdvAmount(BigDecimal advAmount) {
		this.advAmount = advAmount;
	}

	public Long getOrgid() {
		return orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
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

	public String getHeadDesc() {
		return headDesc;
	}

	public void setHeadDesc(String headDesc) {
		this.headDesc = headDesc;
	}

	public String getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public static String[] getPkValues() {
		return new String[] { "COM", "TB_ADVANCE_REQ", "ADV_ID" };
	}

}
