package com.abm.mainet.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;

/**
 * @author Saiprasad.Vengurlekar
 *
 */
public class TbDepositDto implements Serializable {

    private static final long serialVersionUID = -2685087576192698745L;

    private Long depId;

    private Long depNo;

    private Date depDate;

    private Long depType;

    private Long dpDeptId;

    private BigDecimal depAmount;

    private TbServiceReceiptMasBean receiptMasBean;

    private BigDecimal depRefundBal;

    private Long sacHeadId;

    private Long depRefId;

    private Long depReceiptNo;

    private Long bmId;

    private Long paymentId;

    private String depEntryFlag;

    private String depStatus;

    private Long orgId;

    private Long createdBy;

    private Long updatedBy;

    private Date createdDate;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long taxId;

    private Long vendorId;

    private String vendorName;

    private Long vouId;

    private String depNarration;

    public Long getDepId() {
        return depId;
    }

    public void setDepId(Long depId) {
        this.depId = depId;
    }

    public Long getDepNo() {
        return depNo;
    }

    public void setDepNo(Long depNo) {
        this.depNo = depNo;
    }

    public Date getDepDate() {
        return depDate;
    }

    public void setDepDate(Date depDate) {
        this.depDate = depDate;
    }

    public Long getDepType() {
        return depType;
    }

    public void setDepType(Long depType) {
        this.depType = depType;
    }

    public Long getDpDeptId() {
        return dpDeptId;
    }

    public void setDpDeptId(Long dpDeptId) {
        this.dpDeptId = dpDeptId;
    }

    public BigDecimal getDepAmount() {
        return depAmount;
    }

    public void setDepAmount(BigDecimal depAmount) {
        this.depAmount = depAmount;
    }

    public TbServiceReceiptMasBean getReceiptMasBean() {
        return receiptMasBean;
    }

    public void setReceiptMasBean(TbServiceReceiptMasBean receiptMasBean) {
        this.receiptMasBean = receiptMasBean;
    }

    public BigDecimal getDepRefundBal() {
        return depRefundBal;
    }

    public void setDepRefundBal(BigDecimal depRefundBal) {
        this.depRefundBal = depRefundBal;
    }

    public Long getSacHeadId() {
        return sacHeadId;
    }

    public void setSacHeadId(Long sacHeadId) {
        this.sacHeadId = sacHeadId;
    }

    public Long getDepRefId() {
        return depRefId;
    }

    public void setDepRefId(Long depRefId) {
        this.depRefId = depRefId;
    }

    public Long getDepReceiptNo() {
        return depReceiptNo;
    }

    public void setDepReceiptNo(Long depReceiptNo) {
        this.depReceiptNo = depReceiptNo;
    }

    public Long getBmId() {
        return bmId;
    }

    public void setBmId(Long bmId) {
        this.bmId = bmId;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public String getDepEntryFlag() {
        return depEntryFlag;
    }

    public void setDepEntryFlag(String depEntryFlag) {
        this.depEntryFlag = depEntryFlag;
    }

    public String getDepStatus() {
        return depStatus;
    }

    public void setDepStatus(String depStatus) {
        this.depStatus = depStatus;
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

    public Long getTaxId() {
        return taxId;
    }

    public void setTaxId(Long taxId) {
        this.taxId = taxId;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public Long getVouId() {
        return vouId;
    }

    public void setVouId(Long vouId) {
        this.vouId = vouId;
    }

    public String getDepNarration() {
        return depNarration;
    }

    public void setDepNarration(String depNarration) {
        this.depNarration = depNarration;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

}
