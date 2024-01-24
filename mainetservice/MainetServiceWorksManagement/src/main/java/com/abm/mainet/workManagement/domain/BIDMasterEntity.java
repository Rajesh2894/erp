package com.abm.mainet.workManagement.domain;

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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_wms_bid_master")
public class BIDMasterEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1741066931895943983L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "bid_Id", nullable = false)
	private Long bidId;

	@OneToMany(mappedBy = "bidMasterEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<TechnicalBIDDetailEntity> technicalBidDetailEntities = new ArrayList<>();

	@OneToMany(mappedBy = "bidMasterEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<CommercialBIDDetailEntity> commercialBIDDetailEntities = new ArrayList<>();;

	@OneToMany(mappedBy = "bidMasterEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ItemRateBidDetailEntity> itemRateBidDetailEntities = new ArrayList<>();;
	
	@Column(name = "vendor_id", nullable = false)
	private Long vendorId;
	
	@Column(name = "bid_NO", nullable = false)
	private String bidNo;

	@Column(name = "projectid", nullable = false)
	private Long projectid;
	
	@Column(name = "TND_NO", nullable = false)
	private String tndNo;

	@Column(name = "bid_id_desc", nullable = true)
	private String bidIdDesc;

	@Column(name = "tenderstatus", nullable = false)
	private Long tenderstatus;

	@Column(name = "financestatus", nullable = false)
	private Long financestatus;

	@Column(name = "rank", nullable = false)
	private String rank;

	@Column(name = "bid_type", nullable = true)
	private String billType;

	@Column(name = "ORGID", nullable = false)
	private Long orgId;

	@Column(name = "CREATED_DATE", nullable = true)
	private Date creationDate;

	@Column(name = "CREATED_BY", updatable = false, nullable = true)
	private Long createdBy;

	@Column(name = "LG_IP_MAC", length = 100, nullable = true)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
	private String lgIpMacUpd;

	@Column(name = "UPDATED_BY", nullable = true)
	private Long updatedBy;

	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;

	@ManyToOne
	@JoinColumn(name = "TND_ID")
	private TenderMasterEntity tenderMasterEntity;

	public TenderMasterEntity getTenderMasterEntity() {
		return tenderMasterEntity;
	}

	public void setTenderMasterEntity(TenderMasterEntity tenderMasterEntity) {
		this.tenderMasterEntity = tenderMasterEntity;
	}

	public List<TechnicalBIDDetailEntity> getTechnicalBidDetailEntities() {
		return technicalBidDetailEntities;
	}

	public void setTechnicalBidDetailEntities(List<TechnicalBIDDetailEntity> technicalBidDetailEntities) {
		this.technicalBidDetailEntities = technicalBidDetailEntities;
	}

	public List<CommercialBIDDetailEntity> getCommercialBIDDetailEntities() {
		return commercialBIDDetailEntities;
	}

	public void setCommercialBIDDetailEntities(List<CommercialBIDDetailEntity> commercialBIDDetailEntities) {
		this.commercialBIDDetailEntities = commercialBIDDetailEntities;
	}

	public List<ItemRateBidDetailEntity> getItemRateBidDetailEntities() {
		return itemRateBidDetailEntities;
	}

	public void setItemRateBidDetailEntities(List<ItemRateBidDetailEntity> itemRateBidDetailEntities) {
		this.itemRateBidDetailEntities = itemRateBidDetailEntities;
	}

	public Long getBidId() {
		return bidId;
	}

	public void setBidId(Long bidId) {
		this.bidId = bidId;
	}
	
	public Long getTenderstatus() {
		return tenderstatus;
	}

	public void setTenderstatus(Long tenderstatus) {
		this.tenderstatus = tenderstatus;
	}
	
	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
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

	public Long getProjectid() {
		return projectid;
	}

	public void setProjectid(Long projectid) {
		this.projectid = projectid;
	}

	public String getTndNo() {
		return tndNo;
	}

	public void setTndNo(String tndNo) {
		this.tndNo = tndNo;
	}

	public Long getFinancestatus() {
		return financestatus;
	}

	public void setFinancestatus(Long financestatus) {
		this.financestatus = financestatus;
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
	
	public String getBidNo() {
		return bidNo;
	}

	public void setBidNo(String bidNo) {
		this.bidNo = bidNo;
	}

	public static String[] getPkValues() {
		return new String[] { "COM", "tb_wms_bid_master", "bid_Id" };
	}

}
