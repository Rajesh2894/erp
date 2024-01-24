package com.abm.mainet.common.integration.acccount.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @author Jeetendra.Pal
 *
 */
public class AdvanceRequisitionDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8031052869508870527L;

	private Long advId;
	private Long venderId;
	private Long deptId;
	private Long headId;
	private Long advType;
	private String advStatus;
	private String referenceNo;
	private String particulars;
	private String advNo;
	private Date entryDate;
	private BigDecimal advAmount;
	private Long orgid;
	private Long createdBy;
	private Date createdDate;
	private Long updatedBy;
	private Date updatedDate;
	private String lgIpMac;
	private String lgIpMacUpd;
	private String advDateStr;
	private BigDecimal referenceAmount;
	private String headDesc;
	private String billNumber;
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
	public String getAdvDateStr() {
		return advDateStr;
	}
	public void setAdvDateStr(String advDateStr) {
		this.advDateStr = advDateStr;
	}
	public BigDecimal getReferenceAmount() {
		return referenceAmount;
	}
	public void setReferenceAmount(BigDecimal referenceAmount) {
		this.referenceAmount = referenceAmount;
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

}
