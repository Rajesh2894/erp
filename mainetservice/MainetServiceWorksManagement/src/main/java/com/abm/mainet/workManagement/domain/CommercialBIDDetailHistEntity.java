package com.abm.mainet.workManagement.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_wms_bid_comm_det_hist")
public class CommercialBIDDetailHistEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2493429975861648886L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "comm_bid_id_h", nullable = false)
	private Long comBidHistId;

	@Column(name = "comm_bid_id", nullable = false)
	private Long commBidId;

	@Column(name = "bid_Id")
	private Long bidId;

	@Column(name = "param_desc_id", nullable = false)
	private String paramDescId;

	@Column(name = "base_rate_per_tend", nullable = false)
	private Long baseRate;

	@Column(name = "quoted_price", nullable = false)
	private BigDecimal quotedPrice;

	@Column(name = "mark", nullable = false)
	private Long mark;

	@Column(name = "obtained", nullable = false)
	private Long obtained;

	@Column(name = "weigtage", nullable = false)
	private BigDecimal weightage;

	@Column(name = "final_mark", nullable = false)
	private Long finalMark;

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

	@Column(name = "H_STATUS")
	private String histStatus;

	public String getHistStatus() {
		return histStatus;
	}

	public void setHistStatus(String histStatus) {
		this.histStatus = histStatus;
	}

	public Long getComBidHistId() {
		return comBidHistId;
	}

	public void setComBidHistId(Long comBidHistId) {
		this.comBidHistId = comBidHistId;
	}

	public Long getCommBidId() {
		return commBidId;
	}

	public void setCommBidId(Long commBidId) {
		this.commBidId = commBidId;
	}

	public Long getBidId() {
		return bidId;
	}

	public void setBidId(Long bidId) {
		this.bidId = bidId;
	}

	public String getParamDescId() {
		return paramDescId;
	}

	public void setParamDescId(String paramDescId) {
		this.paramDescId = paramDescId;
	}

	public Long getBaseRate() {
		return baseRate;
	}

	public void setBaseRate(Long baseRate) {
		this.baseRate = baseRate;
	}

	public BigDecimal getQuotedPrice() {
		return quotedPrice;
	}

	public void setQuotedPrice(BigDecimal quotedPrice) {
		this.quotedPrice = quotedPrice;
	}

	public Long getMark() {
		return mark;
	}

	public void setMark(Long mark) {
		this.mark = mark;
	}

	public Long getObtained() {
		return obtained;
	}

	public void setObtained(Long obtained) {
		this.obtained = obtained;
	}

	public BigDecimal getWeightage() {
		return weightage;
	}

	public void setWeightage(BigDecimal weightage) {
		this.weightage = weightage;
	}

	public Long getFinalMark() {
		return finalMark;
	}

	public void setFinalMark(Long finalMark) {
		this.finalMark = finalMark;
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

	public static String[] getPkValues() {
		return new String[] { "COM", "tb_wms_bid_comm_det_hist", "comm_bid_id_h" };
	}
}
