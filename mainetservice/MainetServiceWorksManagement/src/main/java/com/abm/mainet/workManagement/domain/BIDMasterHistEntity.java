package com.abm.mainet.workManagement.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_wms_bid_master_hist")
public class BIDMasterHistEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2237035623973374219L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "bid_Id_h", nullable = false)
	private Long bidHistId;

	@Column(name = "bid_Id", nullable = false)
	private Long bidId;

	@Column(name = "vendor_id", nullable = false)
	private Long vendorId;

	@Column(name = "bid_id_desc", nullable = false)
	private String bidIdDesc;

	@Column(name = "overall_tender_score", nullable = false)
	private Long overAllTechScore;

	@Column(name = "overall_comm_score", nullable = false)
	private Long overAllCommScore;

	@Column(name = "status", nullable = false)
	private String status;

	@Column(name = "bid_type", nullable = false)
	private String billType;

	@Column(name = "ORGID", nullable = false)
	private Long orgId;

	@Column(name = "CREATED_DATE", nullable = false)
	private Date creationDate;

	@Column(name = "CREATED_BY", updatable = false, nullable = false)
	private Long createdBy;

	@Column(name = "LG_IP_MAC", length = 100, nullable = false)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
	private String lgIpMacUpd;

	@Column(name = "UPDATED_BY", nullable = true)
	private Long updatedBy;

	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;

	@Column(name = "TND_ID")
	private Long tndId;

	@Column(name = "H_STATUS")
	private String histStatus;

	public String getHistStatus() {
		return histStatus;
	}

	public void setHistStatus(String histStatus) {
		this.histStatus = histStatus;
	}

	public static String[] getPkValues() {
		return new String[] { "COM", "tb_wms_bid_master_hist", "bid_Id_h" };
	}

	public Long getBidHistId() {
		return bidHistId;
	}

	public void setBidHistId(Long bidHistId) {
		this.bidHistId = bidHistId;
	}

	public Long getBidId() {
		return bidId;
	}

	public void setBidId(Long bidId) {
		this.bidId = bidId;
	}

	public Long getVendorId() {
		return vendorId;
	}

	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}

	public String getBidIdDesc() {
		return bidIdDesc;
	}

	public void setBidIdDesc(String bidIdDesc) {
		this.bidIdDesc = bidIdDesc;
	}

	public Long getOverAllTechScore() {
		return overAllTechScore;
	}

	public void setOverAllTechScore(Long overAllTechScore) {
		this.overAllTechScore = overAllTechScore;
	}

	public Long getOverAllCommScore() {
		return overAllCommScore;
	}

	public void setOverAllCommScore(Long overAllCommScore) {
		this.overAllCommScore = overAllCommScore;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
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

	public Long getTndId() {
		return tndId;
	}

	public void setTndId(Long tndId) {
		this.tndId = tndId;
	}

}
