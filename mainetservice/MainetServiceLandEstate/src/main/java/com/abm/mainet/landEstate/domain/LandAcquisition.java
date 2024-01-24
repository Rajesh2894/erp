package com.abm.mainet.landEstate.domain;

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
 * @author israt.ali
 */
@Entity
@Table(name = "TB_EST_AQUISN")
public class LandAcquisition implements Serializable {

    private static final long serialVersionUID = 3878722981247558649L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "LNAQ_ID")
    private Long lnaqId;

    @Column(name = "APM_APPLICATION_ID", length = 30, nullable = false)
    private Long apmApplicationId;

    @Column(name = "LN_DESC", length = 100, nullable = false)
    private String lnDesc;

    @Column(name = "LOC_ID", nullable = false)
    private Long locId;

    @Column(name = "ACQ_MODE", nullable = false)
    private Long acqModeId; // prefix AQM

    @Column(name = "LN_SERVNO", length = 100, nullable = false)
    private String lnServno;

    @Column(name = "LN_AREA", nullable = false)
    private String lnArea;

    @Column(name = "PAY_TO", length = 100, nullable = false)
    private String payTo; // to whom paid (land owner name)

    @Column(name = "LN_OTH", length = 1, nullable = false)
    private String lnOth; // Y/N store

    @Column(name = "ACQ_PURPOSE", length = 100, nullable = false)
    private String acqPurpose;

    @Column(name = "LN_TTL", length = 1)
    private String lnTitle;

    @Column(name = "ACQ_DT", length = 100)
    private Date acqDate;

    @Column(name = "CUR_USG", length = 100, nullable = false)
    private String currentUsage;

    @Column(name = "CUR_MKTV", precision = 12, scale = 2)
    private BigDecimal currentMarktValue;

    @Column(name = "ACQ_COST", precision = 12, scale = 2)
    private BigDecimal acqCost;

    @Column(name = "LN_REMARK", length = 100)
    private String lnRemark;

    @Column(name = "ACQ_STATUS", length = 1, nullable = false)
    private String acqStatus; // T - transit (In process) / A - Acquired

    @Column(name = "TRANSFER_STATUS", length = 1, nullable = false)
    private String transferStatus; // Y - when entry in asset N- DEFAULT SET

    @Column(name = "LN_MOBNO", length = 10)
    private Long lnMobNo;

    @Column(name = "LN_ADDRESS", length = 500)
    private String lnAddress;

    @Column(name = "LN_BILLNO", length = 40)
    private String lnBillNo; // BILL NO of TB_AC_BILL_MAS

    @Column(name = "ASSET_ID", nullable = false)
    private Long assetId;

    @Column(name = "VENDOR_ID", nullable = false)
    private Long vendorId;

    @Column(name = "ORGID", nullable = false, updatable = false)
    private Long orgId;

    @Column(name = "CREATED_BY", nullable = false, updatable = false)
    private Long createdBy;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "UPDATED_BY", nullable = true, updatable = false)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = false)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    public Long getLnaqId() {
        return lnaqId;
    }

    public void setLnaqId(Long lnaqId) {
        this.lnaqId = lnaqId;
    }

    public Long getApmApplicationId() {
        return apmApplicationId;
    }

    public void setApmApplicationId(Long apmApplicationId) {
        this.apmApplicationId = apmApplicationId;
    }

    public String getLnDesc() {
        return lnDesc;
    }

    public void setLnDesc(String lnDesc) {
        this.lnDesc = lnDesc;
    }

    public Long getLocId() {
        return locId;
    }

    public void setLocId(Long locId) {
        this.locId = locId;
    }

    public Long getAcqModeId() {
        return acqModeId;
    }

    public void setAcqModeId(Long acqModeId) {
        this.acqModeId = acqModeId;
    }

    public String getLnServno() {
        return lnServno;
    }

    public void setLnServno(String lnServno) {
        this.lnServno = lnServno;
    }

    public String getLnArea() {
        return lnArea;
    }

    public void setLnArea(String lnArea) {
        this.lnArea = lnArea;
    }

    public String getPayTo() {
        return payTo;
    }

    public void setPayTo(String payTo) {
        this.payTo = payTo;
    }

    public String getLnOth() {
        return lnOth;
    }

    public void setLnOth(String lnOth) {
        this.lnOth = lnOth;
    }

    public String getAcqPurpose() {
        return acqPurpose;
    }

    public void setAcqPurpose(String acqPurpose) {
        this.acqPurpose = acqPurpose;
    }

    public String getLnTitle() {
        return lnTitle;
    }

    public void setLnTitle(String lnTitle) {
        this.lnTitle = lnTitle;
    }

    public Date getAcqDate() {
        return acqDate;
    }

    public void setAcqDate(Date acqDate) {
        this.acqDate = acqDate;
    }

    public String getCurrentUsage() {
        return currentUsage;
    }

    public void setCurrentUsage(String currentUsage) {
        this.currentUsage = currentUsage;
    }

    public BigDecimal getCurrentMarktValue() {
        return currentMarktValue;
    }

    public void setCurrentMarktValue(BigDecimal currentMarktValue) {
        this.currentMarktValue = currentMarktValue;
    }

    public BigDecimal getAcqCost() {
        return acqCost;
    }

    public void setAcqCost(BigDecimal acqCost) {
        this.acqCost = acqCost;
    }

    public String getLnRemark() {
        return lnRemark;
    }

    public void setLnRemark(String lnRemark) {
        this.lnRemark = lnRemark;
    }

    public String getAcqStatus() {
        return acqStatus;
    }

    public void setAcqStatus(String acqStatus) {
        this.acqStatus = acqStatus;
    }

    public String getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(String transferStatus) {
        this.transferStatus = transferStatus;
    }

    public Long getLnMobNo() {
        return lnMobNo;
    }

    public void setLnMobNo(Long lnMobNo) {
        this.lnMobNo = lnMobNo;
    }

    public String getLnAddress() {
        return lnAddress;
    }

    public void setLnAddress(String lnAddress) {
        this.lnAddress = lnAddress;
    }

    public String getLnBillNo() {
        return lnBillNo;
    }

    public void setLnBillNo(String lnBillNo) {
        this.lnBillNo = lnBillNo;
    }

    public Long getAssetId() {
        return assetId;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
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

    public static String[] getPkValues() {
        return new String[] { "EST", "TB_EST_AQUISN", "LNAQ_ID" };
    }

}
