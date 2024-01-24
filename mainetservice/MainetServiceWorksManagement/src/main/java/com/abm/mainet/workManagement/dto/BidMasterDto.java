package com.abm.mainet.workManagement.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.integration.dto.DocumentDetailsVO;

public class BidMasterDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6783002984703610076L;

	private Long bidId;

	private Long vendorId;

	private String vendorName;
	
	private Long projectid;
	
	private String tndNo;
	
	private String bidNo;

	private String bidIdDesc;

	private Long overAllTechScore;

	private Long overAllCommScore;

	private String status;

	private String billType;

	private Long orgId;

	private Date creationDate;

	private Long createdBy;

	private String lgIpMac;

	private String lgIpMacUpd;

	private Long updatedBy;

	private Date updatedDate;
	
	private String tenderstatus;
	
	private String financestatus;
	
	private Long rank;
	
	public Long getRank() {
		return rank;
	}

	public void setRank(Long rank) {
		this.rank = rank;
	}

	private List<TechnicalBIDDetailDto> technicalBIDDetailDtos;

	private List<CommercialBIDDetailDto> commercialBIDDetailDtos;

	private List<ItemRateBidDetailDto> itemRateBidDetailDtos;
		
	private Long tndId;

	private String histFlag;
	

	private List<DocumentDetailsVO> ternitDoc = new ArrayList<>();

	public String getHistFlag() {
		return histFlag;
	}

	public void setHistFlag(String histFlag) {
		this.histFlag = histFlag;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getBidNo() {
		return bidNo;
	}

	public void setBidNo(String bidNo) {
		this.bidNo = bidNo;
	}

	public Long getTndId() {
		return tndId;
	}

	public void setTndId(Long tndId) {
		this.tndId = tndId;
	}

	public List<TechnicalBIDDetailDto> getTechnicalBIDDetailDtos() {
		return technicalBIDDetailDtos;
	}

	public void setTechnicalBIDDetailDtos(List<TechnicalBIDDetailDto> technicalBIDDetailDtos) {
		this.technicalBIDDetailDtos = technicalBIDDetailDtos;
	}

	public List<CommercialBIDDetailDto> getCommercialBIDDetailDtos() {
		return commercialBIDDetailDtos;
	}

	public void setCommercialBIDDetailDtos(List<CommercialBIDDetailDto> commercialBIDDetailDtos) {
		this.commercialBIDDetailDtos = commercialBIDDetailDtos;
	}

	public List<ItemRateBidDetailDto> getItemRateBidDetailDtos() {
		return itemRateBidDetailDtos;
	}

	public void setItemRateBidDetailDtos(List<ItemRateBidDetailDto> itemRateBidDetailDtos) {
		this.itemRateBidDetailDtos = itemRateBidDetailDtos;
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

	public String getTenderstatus() {
		return tenderstatus;
	}

	public void setTenderstatus(String tenderstatus) {
		this.tenderstatus = tenderstatus;
	}

	public String getFinancestatus() {
		return financestatus;
	}

	public void setFinancestatus(String financestatus) {
		this.financestatus = financestatus;
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
	public List<DocumentDetailsVO> getTernitDoc() {
		return ternitDoc;
	}

	public void setTernitDoc(List<DocumentDetailsVO> ternitDoc) {
		this.ternitDoc = ternitDoc;
	}

	@Override
	public String toString() {
		return "BidMasterDto [bidId=" + bidId + ", vendorId=" + vendorId + ", bidIdDesc=" + bidIdDesc + ", bidNo=" + bidNo
				+ ", overAllTechScore=" + overAllTechScore + ", overAllCommScore=" + overAllCommScore + ", status="
				+ status + ", billType=" + billType + ", orgId=" + orgId + ", creationDate=" + creationDate
				+ ", createdBy=" + createdBy + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd + ", updatedBy="
				+ updatedBy + ", updatedDate=" + updatedDate + ", technicalBIDDetailDtos=" + technicalBIDDetailDtos
				+ ", commercialBIDDetailDtos=" + commercialBIDDetailDtos + "]";
	}

}
