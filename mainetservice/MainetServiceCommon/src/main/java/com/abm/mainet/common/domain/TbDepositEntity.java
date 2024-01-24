package com.abm.mainet.common.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;

/**
 * @author jeetedra.pal
 *
 */

@Entity
@Table(name = "TB_DEPOSIT")
public class TbDepositEntity implements Serializable {

    private static final long serialVersionUID = -487826889888696420L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "DEP_ID", precision = 12, scale = 0, nullable = false)
    private Long depId;

    @Column(name = "DEP_NO", precision = 19, scale = 0, nullable = false)
    private Long depNo;

    @Temporal(TemporalType.DATE)
    @Column(name = "DEP_DATE", nullable = false)
    private Date depDate;

    @Column(name = "DEP_TYPE", precision = 12, scale = 0, nullable = false)
    private Long depType;

    @Column(name = "DP_DEPTID", nullable = false)
    private Long dpDeptId;

    @Column(name = "DEP_AMOUNT", nullable = false)
    private BigDecimal depAmount;

    @OneToOne
    @JoinColumn(name = "RM_RCPTID", referencedColumnName = "RM_RCPTID", nullable = false)
    private TbServiceReceiptMasEntity rmRcptid;

    @Column(name = "DEP_REFUND_BAL", nullable = true)
    private BigDecimal depRefundBal;

    @Column(name = "SAC_HEAD_ID", nullable = false)
    private Long sacHeadId;

    @Column(name = "DEP_REF_ID", nullable = true)
    private Long depRefId;

    @Column(name = "DEP_RECEIPTNO", nullable = true)
    private Long depReceiptNo;

    @Column(name = "BM_ID", nullable = true)
    private Long bmId;

    @Column(name = "PAYMENT_ID", nullable = true)
    private Long paymentId;

    @Column(name = "DEP_ENTRYFLAG", nullable = false)
    private String depEntryFlag;

    @Column(name = "DEP_STATUS", nullable = false)
    private String depStatus;

    @Column(name = "ORGID", nullable = false)
    private Long orgId;

    @Column(name = "CREATED_BY", nullable = false, updatable = false)
    private Long createdBy;

    @Column(name = "UPDATED_BY", nullable = false)
    private Long updatedBy;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    @Column(name = "DEP_NARRATION", nullable = false)
    private String depNarration;

    @Column(name = "VM_VENDORID")
    private Long vendorId;

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

    public TbServiceReceiptMasEntity getRmRcptid() {
        return rmRcptid;
    }

    public void setRmRcptid(TbServiceReceiptMasEntity rmRcptid) {
        this.rmRcptid = rmRcptid;
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

    public String getDepNarration() {
        return depNarration;
    }

    public void setDepNarration(String depNarration) {
        this.depNarration = depNarration;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public String getDepStatus() {
        return depStatus;
    }

    public void setDepStatus(String depStatus) {
        this.depStatus = depStatus;
    }

    public String[] getPkValues() {
        return new String[] { "COM", "TB_DEPOSIT", "DEP_ID" };
    }

}
