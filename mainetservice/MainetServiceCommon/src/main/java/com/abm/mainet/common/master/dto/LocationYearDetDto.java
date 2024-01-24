package com.abm.mainet.common.master.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class LocationYearDetDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long pbId;

    private Long workId;

    private Long sacHeadId;

    private Long faYearId;

    private BigDecimal yearPercntWork;

    private String yeDocRefNo;

    private BigDecimal yeBugAmount;

    private String yeActive;

    private Long orgId;

    private Long createdBy;

    private Long updatedBy;

    private Date createdDate;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private String financeCodeDesc;

    private String finActiveFlag;

    private String faYearFromTo;
    
    private Long fieldId;
    
    public Long getWorkId() {
        return workId;
    }

    public void setWorkId(Long workId) {
        this.workId = workId;
    }

    public Long getSacHeadId() {
        return sacHeadId;
    }

    public void setSacHeadId(Long sacHeadId) {
        this.sacHeadId = sacHeadId;
    }

    public Long getFaYearId() {
        return faYearId;
    }

    public void setFaYearId(Long faYearId) {
        this.faYearId = faYearId;
    }

    public BigDecimal getYearPercntWork() {
        return yearPercntWork;
    }

    public void setYearPercntWork(BigDecimal yearPercntWork) {
        this.yearPercntWork = yearPercntWork;
    }

    public String getYeDocRefNo() {
        return yeDocRefNo;
    }

    public void setYeDocRefNo(String yeDocRefNo) {
        this.yeDocRefNo = yeDocRefNo;
    }

    public BigDecimal getYeBugAmount() {
        return yeBugAmount;
    }

    public void setYeBugAmount(BigDecimal yeBugAmount) {
        this.yeBugAmount = yeBugAmount;
    }

    public String getYeActive() {
        return yeActive;
    }

    public void setYeActive(String yeActive) {
        this.yeActive = yeActive;
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

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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

    public String getFinanceCodeDesc() {
        return financeCodeDesc;
    }

    public void setFinanceCodeDesc(String financeCodeDesc) {
        this.financeCodeDesc = financeCodeDesc;
    }

    public String getFinActiveFlag() {
        return finActiveFlag;
    }

    public void setFinActiveFlag(String finActiveFlag) {
        this.finActiveFlag = finActiveFlag;
    }

    public String getFaYearFromTo() {
        return faYearFromTo;
    }

    public void setFaYearFromTo(String faYearFromTo) {
        this.faYearFromTo = faYearFromTo;
    }

	public Long getPbId() {
		return pbId;
	}

	public void setPbId(Long pbId) {
		this.pbId = pbId;
	}

	public Long getFieldId() {
		return fieldId;
	}

	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}

}
