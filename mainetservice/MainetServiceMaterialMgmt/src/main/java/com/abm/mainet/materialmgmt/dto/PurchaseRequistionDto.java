package com.abm.mainet.materialmgmt.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Ajay Kumar
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class PurchaseRequistionDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long prId;
	private String prNo;
	private Date prDate;
	private Long storeId;
	private Long requestedBy;
	private Long department ; 
	private String status ;
	private Long poref;
	private Long orgId;
	private Long userId;
	private String userName;
	private Long langId ;
	private Date lmoDate ;
	private Long updatedBy;
	private Date updatedDate;
	private String lgIpMac;
	private String lgIpMacUpd;
	private String wfFlag; 
	private Date fromDate;
	private Date toDate;
	private String requestedName;
	private String departmentName;
	private String redirectTender;
	private String storeName;
    private BigDecimal totalYeBugAmount;

	
	List<PurchaseRequistionDetDto> purchaseRequistionDetDtoList=new ArrayList<>(); 
	private List<Long> prIds =new ArrayList<>();
	@JsonIgnore 
	List<PurchaseRequistionYearDetDto> yearDto =new ArrayList<>();
	
	private List<Long> itemIds =new ArrayList<>();
	
	public PurchaseRequistionDto() {
		
	}

	public Long getPrId() {
		return prId;
	}

	public void setPrId(Long prId) {
		this.prId = prId;
	}

	public String getPrNo() {
		return prNo;
	}

	public void setPrNo(String prNo) {
		this.prNo = prNo;
	}

	public Date getPrDate() {
		return prDate;
	}

	public void setPrDate(Date prDate) {
		this.prDate = prDate;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public Long getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(Long requestedBy) {
		this.requestedBy = requestedBy;
	}

	public Long getDepartment() {
		return department;
	}

	public void setDepartment(Long department) {
		this.department = department;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getPoref() {
		return poref;
	}

	public void setPoref(Long poref) {
		this.poref = poref;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getLangId() {
		return langId;
	}

	public void setLangId(Long langId) {
		this.langId = langId;
	}

	public Date getLmoDate() {
		return lmoDate;
	}

	public void setLmoDate(Date lmoDate) {
		this.lmoDate = lmoDate;
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

	public String getWfFlag() {
		return wfFlag;
	}

	public void setWfFlag(String wfFlag) {
		this.wfFlag = wfFlag;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getRequestedName() {
		return requestedName;
	}

	public void setRequestedName(String requestedName) {
		this.requestedName = requestedName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public List<PurchaseRequistionDetDto> getPurchaseRequistionDetDtoList() {
		return purchaseRequistionDetDtoList;
	}

	public void setPurchaseRequistionDetDtoList(List<PurchaseRequistionDetDto> purchaseRequistionDetDtoList) {
		this.purchaseRequistionDetDtoList = purchaseRequistionDetDtoList;
	}
	
	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	@Override
	public String toString() {
		return "PurchaseRequistionDto [prId=" + prId + ", prNo=" + prNo + ", prDate=" + prDate + ", storeId=" + storeId
				+ ", requestedBy=" + requestedBy + ", department=" + department + ", status=" + status + ", poref="
				+ poref + ", orgId=" + orgId + ", userId=" + userId + ", langId=" + langId + ", lmoDate=" + lmoDate
				+ ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd="
				+ lgIpMacUpd + ", wfFlag=" + wfFlag + ", fromDate=" + fromDate + ", toDate=" + toDate
				+ ", requestedName=" + requestedName + ", departmentName=" + departmentName
				+ ", purchaseRequistionDetDtoList=" + purchaseRequistionDetDtoList + "]";
	}

	public String getRedirectTender() {
		return redirectTender;
	}

	public void setRedirectTender(String redirectTender) {
		this.redirectTender = redirectTender;
	}
	
	public List<Long> getPrIds() {
		return prIds;
	}

	public void setPrIds(List<Long> prIds) {
		this.prIds = prIds;
	}

	public List<PurchaseRequistionYearDetDto> getYearDto() {
		return yearDto;
	}

	public void setYearDto(List<PurchaseRequistionYearDetDto> yearDto) {
		this.yearDto = yearDto;
	}

	public BigDecimal getTotalYeBugAmount() {
		return totalYeBugAmount;
	}

	public void setTotalYeBugAmount(BigDecimal totalYeBugAmount) {
		this.totalYeBugAmount = totalYeBugAmount;
	}

	public List<Long> getItemIds() {
		return itemIds;
	}

	public void setItemIds(List<Long> itemIds) {
		this.itemIds = itemIds;
	}
   	
}
