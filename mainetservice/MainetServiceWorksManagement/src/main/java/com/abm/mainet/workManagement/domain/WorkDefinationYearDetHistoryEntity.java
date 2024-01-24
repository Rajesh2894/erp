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

/**
 * @author hiren.poriya
 * @Since 27-Mar-2018
 */
@Entity
@Table(name = "TB_WMS_WORKDEFINATION_YEAR_DET_HIST")
public class WorkDefinationYearDetHistoryEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "YE_ID_H", nullable = false)
    private Long yearIdH;

    @Column(name = "YE_ID")
    private Long yearId;

    @Column(name = "WORK_ID")
    private Long workId;

    @Column(name = "SAC_HEAD_ID")
    private Long sacHeadId;

    @Column(name = "FA_YEARID")
    private Long faYearId;

    @Column(name = "YE_PERCENT_WORK")
    private BigDecimal yearPercntWork;

    @Column(name = "YE_DOC_REFERENCENO")
    private String yeDocRefNo;

    @Column(name = "YE_BUGEDED_AMOUNT")
    private BigDecimal yeBugAmount;

    @Column(name = "YE_ACTIVE")
    private String yeActive;

    @Column(name = "H_STATUS")
    private String hStatus;

    @Column(name = "ORGID")
    private Long orgId;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    @Column(name = "YE_FINANCE_CODE_DESC")
    private String financeCodeDesc;

    public Long getYearId() {
        return yearId;
    }

    public void setYearId(Long yearId) {
        this.yearId = yearId;
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

    public Long getYearIdH() {
        return yearIdH;
    }

    public void setYearIdH(Long yearIdH) {
        this.yearIdH = yearIdH;
    }

    public Long getWorkId() {
        return workId;
    }

    public void setWorkId(Long workId) {
        this.workId = workId;
    }

    public String gethStatus() {
        return hStatus;
    }

    public void sethStatus(String hStatus) {
        this.hStatus = hStatus;
    }

    public String[] getPkValues() {
        return new String[] { "WMS", "TB_WMS_WORKDEFINATION_YEAR_DET_HIST", "YE_ID_H" };
    }

}
