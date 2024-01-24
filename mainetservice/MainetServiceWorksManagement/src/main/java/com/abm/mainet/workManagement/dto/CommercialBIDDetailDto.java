package com.abm.mainet.workManagement.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.abm.mainet.workManagement.domain.BIDMasterEntity;

public class CommercialBIDDetailDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2124319923174113626L;

	private Long commBidId;

	private String paramDescId;
	
	private String vendorname;
	
	private Long percenttype;
	
	private BigDecimal percentvalue;
	
	private BigDecimal quotedPrice;

	private Long baseRate;

	private Long mark;

	private Long obtained;

	private BigDecimal weightage;

	private Long finalMark;

	private BidMasterDto bidMasterDto;

	private Long orgId;

	private Date creationDate;

	private Long createdBy;

	private String lgIpMac;

	private String lgIpMacUpd;

	private Long updatedBy;

	private Date updatedDate;
	
	private Long tenderType;
	
	private Character financialflag;

	public Long getTenderType() {
		return tenderType;
	}

	public void setTenderType(Long tenderType) {
		this.tenderType = tenderType;
	}

	public String getParamDescId() {
		return paramDescId;
	}

	public void setParamDescId(String paramDescId) {
		this.paramDescId = paramDescId;
	}

	public Long getCommBidId() {
		return commBidId;
	}

	public void setCommBidId(Long commBidId) {
		this.commBidId = commBidId;
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

	public BidMasterDto getBidMasterDto() {
		return bidMasterDto;
	}

	public void setBidMasterDto(BidMasterDto bidMasterDto) {
		this.bidMasterDto = bidMasterDto;
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

	public String getVendorname() {
		return vendorname;
	}

	public void setVendorname(String vendorname) {
		this.vendorname = vendorname;
	}

	public Long getPercenttype() {
		return percenttype;
	}

	public void setPercenttype(Long percenttype) {
		this.percenttype = percenttype;
	}

	public BigDecimal getPercentvalue() {
		return percentvalue;
	}

	public void setPercentvalue(BigDecimal percentvalue) {
		this.percentvalue = percentvalue;
	}

	public Character getFinancialflag() {
		return financialflag;
	}

	public void setFinancialflag(Character financialflag) {
		this.financialflag = financialflag;
	}

}
