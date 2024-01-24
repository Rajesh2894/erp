package com.abm.mainet.workManagement.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.workManagement.domain.MeasurementBookMaster;

/**
 * 
 * @author Jeetendra.Pal
 *
 */
public class MeasurementBookMasterDto implements Serializable {

	private static final long serialVersionUID = 1101920809747700387L;

	private Long workMbId;
	private String workMbNo;
	private Date workMbTakenDate;
	private Date workMbBroDate;
	private String pageNo;
	private String ledgerNo;
	private String description;
	private String mbStatus;
	private Long workOrId;
	private Long orgId;
	private Long createdBy;
	private Date createdDate;
	private Long updatedBy;
	private Date updatedDate;
	private String lgIpMac;
	private String lgIpMacUpd;
	private List<MeasurementBookDetailsDto> mbDetails = new ArrayList<>();
	private List<MbOverHeadDetDto> mbOverHeadDetDtos = new ArrayList<MbOverHeadDetDto>();
	/* New Attribute is added */
	private Long workId;
	private Long sordCategory;
	private String sordSubCategory;
	private String sorDIteamNo;
	private String sorDDescription;
	private String sorIteamUnitDesc;
	private BigDecimal workEstimQuantity;
	private BigDecimal workEstimQuantityUtl;
	private BigDecimal rate;
	private BigDecimal actualRate;
	private BigDecimal workActualQty;
	private BigDecimal workActualAmt;
	private String billNumber;
	private BigDecimal mbTotalAmt;
	private Date bmDate;
	private String projName;
	private String workName;

	private boolean checkBox;
	private String mbTakenDate;

	private String agreeFromDate;
	private String agreeToDate;
	private String workAssigneeName;

	private BigDecimal cummulativeAmt;
	private BigDecimal totalSubHeadAmount;
	private String remark;
	private String workOrderNo;
	private String workOrderStatus;
	private String contDate;
	private String contNo;
	private String workAssigneeDate;
	
	private Long levelCheck;
	private String vendorName;

	private List<String> mbMultiSelect = new ArrayList<>();
	private String oldMbNo;
	private String manualMbNo;

	public List<MbOverHeadDetDto> getMbOverHeadDetDtos() {
		return mbOverHeadDetDtos;
	}

	public void setMbOverHeadDetDtos(List<MbOverHeadDetDto> mbOverHeadDetDtos) {
		this.mbOverHeadDetDtos = mbOverHeadDetDtos;
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

	public String getMbStatus() {
		return mbStatus;
	}

	public void setMbStatus(String mbStatus) {
		this.mbStatus = mbStatus;
	}

	public Long getWorkOrId() {
		return workOrId;
	}

	public void setWorkOrId(Long workOrId) {
		this.workOrId = workOrId;
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

	public List<MeasurementBookDetailsDto> getMbDetails() {
		return mbDetails;
	}

	public void setMbDetails(List<MeasurementBookDetailsDto> mbDetails) {
		this.mbDetails = mbDetails;
	}

	public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}

	public Long getSordCategory() {
		return sordCategory;
	}

	public void setSordCategory(Long sordCategory) {
		this.sordCategory = sordCategory;
	}

	public String getSordSubCategory() {
		return sordSubCategory;
	}

	public void setSordSubCategory(String sordSubCategory) {
		this.sordSubCategory = sordSubCategory;
	}

	public String getSorDIteamNo() {
		return sorDIteamNo;
	}

	public void setSorDIteamNo(String sorDIteamNo) {
		this.sorDIteamNo = sorDIteamNo;
	}

	public String getSorDDescription() {
		return sorDDescription;
	}

	public void setSorDDescription(String sorDDescription) {
		this.sorDDescription = sorDDescription;
	}

	public String getSorIteamUnitDesc() {
		return sorIteamUnitDesc;
	}

	public void setSorIteamUnitDesc(String sorIteamUnitDesc) {
		this.sorIteamUnitDesc = sorIteamUnitDesc;
	}

	public BigDecimal getWorkEstimQuantity() {
		return workEstimQuantity;
	}

	public void setWorkEstimQuantity(BigDecimal workEstimQuantity) {
		this.workEstimQuantity = workEstimQuantity;
	}

	public BigDecimal getWorkEstimQuantityUtl() {
		return workEstimQuantityUtl;
	}

	public void setWorkEstimQuantityUtl(BigDecimal workEstimQuantityUtl) {
		this.workEstimQuantityUtl = workEstimQuantityUtl;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public BigDecimal getActualRate() {
		return actualRate;
	}

	public void setActualRate(BigDecimal actualRate) {
		this.actualRate = actualRate;
	}

	public BigDecimal getWorkActualQty() {
		return workActualQty;
	}

	public void setWorkActualQty(BigDecimal workActualQty) {
		this.workActualQty = workActualQty;
	}

	public BigDecimal getWorkActualAmt() {
		return workActualAmt;
	}

	public void setWorkActualAmt(BigDecimal workActualAmt) {
		this.workActualAmt = workActualAmt;
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

	public String getProjName() {
		return projName;
	}

	public void setProjName(String projName) {
		this.projName = projName;
	}

	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	public boolean isCheckBox() {
		return checkBox;
	}

	public void setCheckBox(boolean checkBox) {
		this.checkBox = checkBox;
	}

	public String getMbTakenDate() {
		return mbTakenDate;
	}

	public void setMbTakenDate(String mbTakenDate) {
		this.mbTakenDate = mbTakenDate;
	}

	public List<String> getMbMultiSelect() {
		return mbMultiSelect;
	}

	public void setMbMultiSelect(List<String> mbMultiSelect) {
		this.mbMultiSelect = mbMultiSelect;
	}

	public String getAgreeFromDate() {
		return agreeFromDate;
	}

	public void setAgreeFromDate(String agreeFromDate) {
		this.agreeFromDate = agreeFromDate;
	}

	public String getAgreeToDate() {
		return agreeToDate;
	}

	public void setAgreeToDate(String agreeToDate) {
		this.agreeToDate = agreeToDate;
	}

	public String getWorkAssigneeName() {
		return workAssigneeName;
	}

	public void setWorkAssigneeName(String workAssigneeName) {
		this.workAssigneeName = workAssigneeName;
	}

	public BigDecimal getCummulativeAmt() {
		return cummulativeAmt;
	}

	public void setCummulativeAmt(BigDecimal cummulativeAmt) {
		this.cummulativeAmt = cummulativeAmt;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getTotalSubHeadAmount() {
		return totalSubHeadAmount;
	}

	public void setTotalSubHeadAmount(BigDecimal totalSubHeadAmount) {
		this.totalSubHeadAmount = totalSubHeadAmount;
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

	public String getWorkOrderNo() {
		return workOrderNo;
	}

	public void setWorkOrderNo(String workOrderNo) {
		this.workOrderNo = workOrderNo;
	}

	public String getWorkOrderStatus() {
		return workOrderStatus;
	}

	public void setWorkOrderStatus(String workOrderStatus) {
		this.workOrderStatus = workOrderStatus;
	}

	public String getContDate() {
		return contDate;
	}

	public void setContDate(String string) {
		this.contDate = string;
	}

	public String getContNo() {
		return contNo;
	}

	public void setContNo(String contNo) {
		this.contNo = contNo;
	}

	public String getWorkAssigneeDate() {
		return workAssigneeDate;
	}

	public void setWorkAssigneeDate(String workAssigneeDate) {
		this.workAssigneeDate = workAssigneeDate;
	}

	public Long getLevelCheck() {
		return levelCheck;
	}

	public void setLevelCheck(Long levelCheck) {
		this.levelCheck = levelCheck;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}	
}
