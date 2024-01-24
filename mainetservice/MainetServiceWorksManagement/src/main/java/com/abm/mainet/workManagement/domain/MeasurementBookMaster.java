package com.abm.mainet.workManagement.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author Jeetendra.Pal
 *
 */
@Entity
@Table(name = "TB_WMS_MEASUREMENTBOOK_MAST")
public class MeasurementBookMaster implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1512865529626363113L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "MB_ID", nullable = false)
	private Long workMbId;

	@Column(name = "MB_NO", nullable = true)
	private String workMbNo;

	@Column(name = "MB_TAKENDATE", nullable = false)
	private Date workMbTakenDate;

	@Column(name = "MB_BRODATE", nullable = true)
	private Date workMbBroDate;

	@Column(name = "MB_STATUS", nullable = true)
	private String mbStatus;

	@Column(name = "PAGENO", nullable = true)
	private String pageNo;

	@Column(name = "LEDGERNO", nullable = true)
	private String ledgerNo;

	@Column(name = "DESCRIPTION", nullable = true)
	private String description;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "mbMaster", cascade = CascadeType.ALL)
	private List<MeasurementBookDetails> bookDetailsList = new ArrayList<>();

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "mbMaster", cascade = CascadeType.ALL)
	private List<MeasurementBookTaxDetails> mbTaxDetailsList = new ArrayList<>();

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "mbMaster", cascade = CascadeType.ALL)
	private List<MbOverHeadDetails> mbOverHeadDetails = new ArrayList<>();

	@OneToOne
	@JoinColumn(name = "WORKOR_ID", referencedColumnName = "WORKOR_ID", nullable = false)
	private WorkOrder workOrder;

	@Column(name = "ORGID", nullable = false)
	private Long orgId;

	@Column(name = "CREATED_BY", nullable = false)
	private Long createdBy;

	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;

	@Column(name = "UPDATED_BY", nullable = true)
	private Long updatedBy;

	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;

	@Column(name = "LG_IP_MAC", nullable = false)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", nullable = true)
	private String lgIpMacUpd;

	@Column(name = "BM_BILLNO", nullable = true)
	private String billNumber;

	@Column(name = "MB_TOTALAMT", nullable = true)
	private BigDecimal mbTotalAmt;

	@Column(name = "BM_DATE", nullable = true)
	private Date bmDate;

	@Column(name = "MB_OLDMBNO", nullable = true)
	private String oldMbNo;
	
	@Column(name = "MANUAL_MB_NO")
	private String manualMbNo;

	public List<MbOverHeadDetails> getMbOverHeadDetails() {
		return mbOverHeadDetails;
	}

	public void setMbOverHeadDetails(List<MbOverHeadDetails> mbOverHeadDetails) {
		this.mbOverHeadDetails = mbOverHeadDetails;
	}

	public Long getWorkMbId() {
		return workMbId;
	}

	public void setWorkMbId(Long workMbId) {
		this.workMbId = workMbId;
	}

	public String getWorkMbNo() {
		return workMbNo;
	}

	public void setWorkMbNo(String workMbNo) {
		this.workMbNo = workMbNo;
	}

	public Date getWorkMbTakenDate() {
		return workMbTakenDate;
	}

	public void setWorkMbTakenDate(Date workMbTakenDate) {
		this.workMbTakenDate = workMbTakenDate;
	}

	public Date getWorkMbBroDate() {
		return workMbBroDate;
	}

	public void setWorkMbBroDate(Date workMbBroDate) {
		this.workMbBroDate = workMbBroDate;
	}

	public String getMbStatus() {
		return mbStatus;
	}

	public void setMbStatus(String mbStatus) {
		this.mbStatus = mbStatus;
	}

	public String getPageNo() {
		return pageNo;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	public String getLedgerNo() {
		return ledgerNo;
	}

	public void setLedgerNo(String ledgerNo) {
		this.ledgerNo = ledgerNo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<MeasurementBookDetails> getBookDetailsList() {
		return bookDetailsList;
	}

	public void setBookDetailsList(List<MeasurementBookDetails> bookDetailsList) {
		this.bookDetailsList = bookDetailsList;
	}

	public List<MeasurementBookTaxDetails> getMbTaxDetailsList() {
		return mbTaxDetailsList;
	}

	public void setMbTaxDetailsList(List<MeasurementBookTaxDetails> mbTaxDetailsList) {
		this.mbTaxDetailsList = mbTaxDetailsList;
	}

	public WorkOrder getWorkOrder() {
		return workOrder;
	}

	public void setWorkOrder(WorkOrder workOrder) {
		this.workOrder = workOrder;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
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

	public String getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}

	public BigDecimal getMbTotalAmt() {
		return mbTotalAmt;
	}

	public void setMbTotalAmt(BigDecimal mbTotalAmt) {
		this.mbTotalAmt = mbTotalAmt;
	}

	public Date getBmDate() {
		return bmDate;
	}

	public void setBmDate(Date bmDate) {
		this.bmDate = bmDate;
	}

	public String getOldMbNo() {
		return oldMbNo;
	}

	public void setOldMbNo(String oldMbNo) {
		this.oldMbNo = oldMbNo;
	}

	public String getManualMbNo() {
		return manualMbNo;
	}

	public void setManualMbNo(String manualMbNo) {
		this.manualMbNo = manualMbNo;
	}

	public String[] getPkValues() {
		return new String[] { "WMS", "TB_WMS_MEASUREMENTBOOK_MAST", "MB_ID" };
	}

}
