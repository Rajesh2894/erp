package com.abm.mainet.workManagement.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author hiren.poriya
 * @Since 10-Apr-2018
 */
public class TenderMasterDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long tndId;
    private Long projId;
    private Long tenderCategory;
    private String resolutionNo;
    private Date resolutionDate;
    private Date issueFromDate;
    private Date issueToDate;
    private Date publishDate;
    private Date technicalOpenDate;
    private Date financialeOpenDate;
    private String initiationNo;
    private Date initiationDate;
    private String tenderNo;
    private Date tenderDate;
    private BigDecimal tenderEmdAmt;

    // private BigDecimal tenderSecAmt;
    private BigDecimal tenderAmount;
    // private Long venderClassId;
    // private Long venderId;
    private Long vendorType;
    private String status;
    private Long orgId;
    private Long createdBy;
    private Date createdDate;
    private Long updatedBy;
    private Date updatedDate;
    private String lgIpMac;
    private String lgIpMacUpd;
    private BigDecimal tenderTotalEstiAmount;
    private BigDecimal workEstAmt;

    private Long deptId;
    private String projectName;
    // private String vendorClassDesc;
    private String tenderCategoryDesc;
    private String initiationDateDesc;
    private String projectCode;
    // private String vendorName;
    // private String vendorAddress;
    private String tenderAllWorks;
    private String workAssigneeName;
    private Long workAssigneeId;
    private String tndLOANo;
    private Date tndLOADate;
    private List<TenderWorkDto> workDto = new ArrayList<>();
    private boolean loaGenerated;
    private Long tndValidityDay;
    private Date preBidMeetDate;
    private String tenderMeetingLoc;
    private String preBidMeetDateDesc;
    private String publishDateDesc;
    private String technicalOpenDateDesc;
    private String financialeOpenDateDesc;
    private String issueFromDateDesc;
    private String issueToDateDesc;
    private String resolutionDateDesc;
    private String serviceFlag;
    private Long tndRefNo;
    private String statusDesc;
    private BigDecimal tenderBankAmt;
    private BigDecimal tenderProvAmt;
    private BigDecimal tenderSecAmt;
    private String deptCode;
    private String processName;
	private String tenderFormMode;
	private BigDecimal deviationPercent;
	
    /**
	 * @return the deviationPercent
	 */
	public BigDecimal getDeviationPercent() {
		return deviationPercent;
	}

	/**
	 * @param deviationPercent the deviationPercent to set
	 */
	public void setDeviationPercent(BigDecimal deviationPercent) {
		this.deviationPercent = deviationPercent;
	}

	public Long getTndId() {
        return tndId;
    }

    public void setTndId(Long tndId) {
        this.tndId = tndId;
    }

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public Long getTenderCategory() {
        return tenderCategory;
    }

    public void setTenderCategory(Long tenderCategory) {
        this.tenderCategory = tenderCategory;
    }

    public String getResolutionNo() {
        return resolutionNo;
    }

    public void setResolutionNo(String resolutionNo) {
        this.resolutionNo = resolutionNo;
    }

    public Date getResolutionDate() {
        return resolutionDate;
    }

    public void setResolutionDate(Date resolutionDate) {
        this.resolutionDate = resolutionDate;
    }

    public Date getIssueFromDate() {
        return issueFromDate;
    }

    public void setIssueFromDate(Date issueFromDate) {
        this.issueFromDate = issueFromDate;
    }

    public Date getIssueToDate() {
        return issueToDate;
    }

    public void setIssueToDate(Date issueToDate) {
        this.issueToDate = issueToDate;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Date getTechnicalOpenDate() {
        return technicalOpenDate;
    }

    public void setTechnicalOpenDate(Date technicalOpenDate) {
        this.technicalOpenDate = technicalOpenDate;
    }

    public Date getFinancialeOpenDate() {
        return financialeOpenDate;
    }

    public void setFinancialeOpenDate(Date financialeOpenDate) {
        this.financialeOpenDate = financialeOpenDate;
    }

    public String getInitiationNo() {
        return initiationNo;
    }

    public void setInitiationNo(String initiationNo) {
        this.initiationNo = initiationNo;
    }

    public Date getInitiationDate() {
        return initiationDate;
    }

    public void setInitiationDate(Date initiationDate) {
        this.initiationDate = initiationDate;
    }

    public String getTenderNo() {
        return tenderNo;
    }

    public void setTenderNo(String tenderNo) {
        this.tenderNo = tenderNo;
    }

    public Date getTenderDate() {
        return tenderDate;
    }

    public void setTenderDate(Date tenderDate) {
        this.tenderDate = tenderDate;
    }

    public BigDecimal getTenderEmdAmt() {
        return tenderEmdAmt;
    }

    public void setTenderEmdAmt(BigDecimal tenderEmdAmt) {
        this.tenderEmdAmt = tenderEmdAmt;
    }
    /*
     * public BigDecimal getTenderSecAmt() { return tenderSecAmt; } public void setTenderSecAmt(BigDecimal tenderSecAmt) {
     * this.tenderSecAmt = tenderSecAmt; }
     */

    public BigDecimal getTenderAmount() {
        return tenderAmount;
    }

    public void setTenderAmount(BigDecimal tenderAmount) {
        this.tenderAmount = tenderAmount;
    }

    /*
     * public Long getVenderClassId() { return venderClassId; } public void setVenderClassId(Long venderClassId) {
     * this.venderClassId = venderClassId; }
     */

    public Long getVendorType() {
        return vendorType;
    }

    public void setVendorType(Long vendorType) {
        this.vendorType = vendorType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public List<TenderWorkDto> getWorkDto() {
        return workDto;
    }

    public void setWorkDto(List<TenderWorkDto> workDto) {
        this.workDto = workDto;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /*
     * public String getVendorClassDesc() { return vendorClassDesc; } public void setVendorClassDesc(String vendorClassDesc) {
     * this.vendorClassDesc = vendorClassDesc; }
     */

    public String getTenderCategoryDesc() {
        return tenderCategoryDesc;
    }

    public void setTenderCategoryDesc(String tenderCategoryDesc) {
        this.tenderCategoryDesc = tenderCategoryDesc;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public BigDecimal getTenderTotalEstiAmount() {
        return tenderTotalEstiAmount;
    }

    public void setTenderTotalEstiAmount(BigDecimal tenderTotalEstiAmount) {
        this.tenderTotalEstiAmount = tenderTotalEstiAmount;
    }

    /*
     * public Long getVenderId() { return venderId; } public void setVenderId(Long venderId) { this.venderId = venderId; }
     */

    public String getInitiationDateDesc() {
        return initiationDateDesc;
    }

    public void setInitiationDateDesc(String initiationDateDesc) {
        this.initiationDateDesc = initiationDateDesc;
    }

    public String getTenderAllWorks() {
        return tenderAllWorks;
    }

    public void setTenderAllWorks(String tenderAllWorks) {
        this.tenderAllWorks = tenderAllWorks;
    }

    public String getWorkAssigneeName() {
        return workAssigneeName;
    }

    public void setWorkAssigneeName(String workAssigneeName) {
        this.workAssigneeName = workAssigneeName;
    }

    public Long getWorkAssigneeId() {
        return workAssigneeId;
    }

    public void setWorkAssigneeId(Long workAssigneeId) {
        this.workAssigneeId = workAssigneeId;
    }

    /*
     * public String getVendorName() { return vendorName; } public void setVendorName(String vendorName) { this.vendorName =
     * vendorName; } public String getVendorAddress() { return vendorAddress; } public void setVendorAddress(String vendorAddress)
     * { this.vendorAddress = vendorAddress; }
     */

    public String getTndLOANo() {
        return tndLOANo;
    }

    public void setTndLOANo(String tndLOANo) {
        this.tndLOANo = tndLOANo;
    }

    public Date getTndLOADate() {
        return tndLOADate;
    }

    public void setTndLOADate(Date tndLOADate) {
        this.tndLOADate = tndLOADate;
    }

    public boolean isLoaGenerated() {
        return loaGenerated;
    }

    public void setLoaGenerated(boolean loaGenerated) {
        this.loaGenerated = loaGenerated;
    }

    public Long getTndValidityDay() {
        return tndValidityDay;
    }

    public void setTndValidityDay(Long tndValidityDay) {
        this.tndValidityDay = tndValidityDay;
    }

    public Date getPreBidMeetDate() {
        return preBidMeetDate;
    }

    public void setPreBidMeetDate(Date preBidMeetDate) {
        this.preBidMeetDate = preBidMeetDate;
    }

    public String getTenderMeetingLoc() {
        return tenderMeetingLoc;
    }

    public void setTenderMeetingLoc(String tenderMeetingLoc) {
        this.tenderMeetingLoc = tenderMeetingLoc;
    }

    public String getPreBidMeetDateDesc() {
        return preBidMeetDateDesc;
    }

    public void setPreBidMeetDateDesc(String preBidMeetDateDesc) {
        this.preBidMeetDateDesc = preBidMeetDateDesc;
    }

    public String getPublishDateDesc() {
        return publishDateDesc;
    }

    public void setPublishDateDesc(String publishDateDesc) {
        this.publishDateDesc = publishDateDesc;
    }

    public String getTechnicalOpenDateDesc() {
        return technicalOpenDateDesc;
    }

    public void setTechnicalOpenDateDesc(String technicalOpenDateDesc) {
        this.technicalOpenDateDesc = technicalOpenDateDesc;
    }

    public String getFinancialeOpenDateDesc() {
        return financialeOpenDateDesc;
    }

    public void setFinancialeOpenDateDesc(String financialeOpenDateDesc) {
        this.financialeOpenDateDesc = financialeOpenDateDesc;
    }

    public String getServiceFlag() {
        return serviceFlag;
    }

    public void setServiceFlag(String serviceFlag) {
        this.serviceFlag = serviceFlag;
    }

    public Long getTndRefNo() {
        return tndRefNo;
    }

    public void setTndRefNo(Long tndRefNo) {
        this.tndRefNo = tndRefNo;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

	public BigDecimal getWorkEstAmt() {
		return workEstAmt;
	}

	public void setWorkEstAmt(BigDecimal workEstAmt) {
		this.workEstAmt = workEstAmt;
	}

	public String getIssueFromDateDesc() {
		return issueFromDateDesc;
	}

	public void setIssueFromDateDesc(String issueFromDateDesc) {
		this.issueFromDateDesc = issueFromDateDesc;
	}

	public String getIssueToDateDesc() {
		return issueToDateDesc;
	}

	public void setIssueToDateDesc(String issueToDateDesc) {
		this.issueToDateDesc = issueToDateDesc;
	}

	public String getResolutionDateDesc() {
		return resolutionDateDesc;
	}

	public void setResolutionDateDesc(String resolutionDateDesc) {
		this.resolutionDateDesc = resolutionDateDesc;
	}

	public BigDecimal getTenderBankAmt() {
		return tenderBankAmt;
	}

	public void setTenderBankAmt(BigDecimal tenderBankAmt) {
		this.tenderBankAmt = tenderBankAmt;
	}

	public BigDecimal getTenderProvAmt() {
		return tenderProvAmt;
	}

	public void setTenderProvAmt(BigDecimal tenderProvAmt) {
		this.tenderProvAmt = tenderProvAmt;
	}

	public BigDecimal getTenderSecAmt() {
		return tenderSecAmt;
	}

	public void setTenderSecAmt(BigDecimal tenderSecAmt) {
		this.tenderSecAmt = tenderSecAmt;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getProcessName() {
		return processName;
	}

	public String getTenderFormMode() {
		return tenderFormMode;
	}

	public void setTenderFormMode(String tenderFormMode) {
		this.tenderFormMode = tenderFormMode;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}
    	
}
