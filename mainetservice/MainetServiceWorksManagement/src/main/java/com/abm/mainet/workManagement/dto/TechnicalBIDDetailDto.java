package com.abm.mainet.workManagement.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.abm.mainet.workManagement.domain.BIDMasterEntity;

public class TechnicalBIDDetailDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8567049884090703988L;

	private Long techBidId;

	private String paramDescId;

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
	
	private String evaluation;
	
	private String vendorname;
	
	private String overallscore;
	
	private String acceptreject;
	
	private String criteria;
	
	private Character technicalflag ;

	public Long getTechBidId() {
		return techBidId;
	}

	public void setTechBidId(Long techBidId) {
		this.techBidId = techBidId;
	}

	public Long getMark() {
		return mark;
	}

	public void setMark(Long mark) {
		this.mark = mark;
	}

	public String getParamDescId() {
		return paramDescId;
	}

	public void setParamDescId(String paramDescId) {
		this.paramDescId = paramDescId;
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

	@Override
	public String toString() {
		return "TechnicalBIDDetailDto [techBidId=" + techBidId + ", paramDescId=" + paramDescId + ", mark=" + mark
				+ ", obtained=" + obtained + ", weightage=" + weightage + ", finalMark=" + finalMark
				+ ", bidMasterEntity=" + bidMasterDto + ", orgId=" + orgId + ", creationDate=" + creationDate
				+ ", createdBy=" + createdBy + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd + ", updatedBy="
				+ updatedBy + ", updatedDate=" + updatedDate + "]";
	}

	

	public String getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(String evaluation) {
		this.evaluation = evaluation;
	}

	public String getVendorname() {
		return vendorname;
	}

	public void setVendorname(String vendorname) {
		this.vendorname = vendorname;
	}

	public String getOverallscore() {
		return overallscore;
	}

	public void setOverallscore(String overallscore) {
		this.overallscore = overallscore;
	}

	public String getAcceptreject() {
		return acceptreject;
	}

	public void setAcceptreject(String acceptreject) {
		this.acceptreject = acceptreject;
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	public Character getTechnicalflag() {
		return technicalflag;
	}

	public void setTechnicalflag(Character technicalflag) {
		this.technicalflag = technicalflag;
	}

}
