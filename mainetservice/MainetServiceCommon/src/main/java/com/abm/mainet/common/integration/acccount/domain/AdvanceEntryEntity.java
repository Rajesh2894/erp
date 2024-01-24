package com.abm.mainet.common.integration.acccount.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_AC_ADVANCE")
public class AdvanceEntryEntity implements Serializable {

    private static final long serialVersionUID = 8683880608834172574L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "ADVANCE_ID", nullable = false)
    private Long prAdvEntryId;

    @Column(name = "ORGID", nullable = false)
    private Long orgid;

    @Column(name = "CREATED_BY", nullable = false)
    private Long createdBy;

    /*
     * @Column(name="LANG_ID") private int langId ;
     */

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @JsonIgnore
    @Column(name = "LG_IP_MAC", length = 100, nullable = false)
    private String lgIpMac;

    @JsonIgnore
    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    /*
     * @Column(name="FI04_N1") private Long fi04N1 ;
     * @Column(name="FI04_V1") private String fi04V1 ;
     * @Temporal(TemporalType.DATE)
     * @Column(name="FI04_D1") private Date fi04D1 ;
     * @Column(name="FI04_LO1", length=1) private String fi04Lo1 ;
     */

    @Column(name = "ADV_STATUS", length = 1)
    private String cpdIdStatus;

    @Temporal(TemporalType.DATE)
    @Column(name = "PAY_ADV_ENTRYDATE", nullable = false)
    private Date prAdvEntryDate;

    @Column(name = "PAY_ADVANCE_NO", nullable = false)
    private Long prAdvEntryNo;

    @Column(name = "CPD_ADVANCE_TYPE", nullable = false)
    private Long advanceTypeId;

    @Column(name = "VM_VENDORID", nullable = false)
    private Long vendorId;

    @Column(name = "AH_HEADID")
    private Long pacHeadId;

    @Column(name = "PAY_ADV_PARTICULARS", nullable = false)
    private String payAdvParticulars;

    @Column(name = "PAY_ADV_VOUCHERNO")
    private Long paymentNumber;

    @Temporal(TemporalType.DATE)
    @Column(name = "PAY_ADV_VOUCHERDT")
    private Date paymentDate;

    @Column(name = "PAY_ADV_BAL_TO_ADJUST")
    private BigDecimal balanceAmount;

    @Temporal(TemporalType.DATE)
    @Column(name = "PAY_ADV_SETTLEMENT_DATE")
    private Date paymentOrderDate;

    @Column(name = "PAY_ADV_AMOUNT", nullable = false)
    private BigDecimal advanceAmount;

    @Column(name = "DP_DEPTID", nullable = false)
    private Long deptId;

    @Column(name = "VM_VENDORNAME", nullable = false)
    private String vendorName;

    @Column(name = "SEAS_DEAS")
    private Long paymentReferenceId;

    @Column(name = "ADV_FLG", length = 1)
    private String adv_Flg;

    @Column(name = "PAY_ADV_SETTLEMENT_NUMBER")
    private Long payAdvSettlementNumber;

    @Column(name = "ADV_DEL_FLAG", length = 1)
    private String adv_del_flag;

    @Temporal(TemporalType.DATE)
    @Column(name = "ADV_DEL_DATE")
    private Date adv_del_date;

    @Temporal(TemporalType.DATE)
    @Column(name = "ADV_POSTING_DATE")
    private Date adv_posting_date;

    @Column(name = "ADV_ORDER_NO")
    private String adv_order_no;

    @Column(name = "ADV_DEL_AUTH_BY")
    private Long adv_del_auth_by;

    @Column(name = "ADV_DEL_REMARK")
    private String adv_del_remark;

    // ----------------------------------------------------------------------
    // toString METHOD
    // ----------------------------------------------------------------------
    public static String[] getPkValues() {
        return new String[] { "AC", "TB_AC_ADVANCE", "ADVANCE_ID" };
    }

    public Long getPrAdvEntryId() {
        return prAdvEntryId;
    }

    public void setPrAdvEntryId(final Long prAdvEntryId) {
        this.prAdvEntryId = prAdvEntryId;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    /*
     * public int getLangId() { return langId; } public void setLangId(int langId) { this.langId = langId; }
     */

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getCpdIdStatus() {
        return cpdIdStatus;
    }

    public void setCpdIdStatus(final String cpdIdStatus) {
        this.cpdIdStatus = cpdIdStatus;
    }

    public Date getPrAdvEntryDate() {
        return prAdvEntryDate;
    }

    public void setPrAdvEntryDate(final Date prAdvEntryDate) {
        this.prAdvEntryDate = prAdvEntryDate;
    }

    public Long getPrAdvEntryNo() {
        return prAdvEntryNo;
    }

    public void setPrAdvEntryNo(final Long prAdvEntryNo) {
        this.prAdvEntryNo = prAdvEntryNo;
    }

    public Long getAdvanceTypeId() {
        return advanceTypeId;
    }

    public void setAdvanceTypeId(final Long advanceTypeId) {
        this.advanceTypeId = advanceTypeId;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(final Long vendorId) {
        this.vendorId = vendorId;
    }

    public Long getPacHeadId() {
        return pacHeadId;
    }

    public void setPacHeadId(final Long pacHeadId) {
        this.pacHeadId = pacHeadId;
    }

    public String getPayAdvParticulars() {
        return payAdvParticulars;
    }

    public void setPayAdvParticulars(final String payAdvParticulars) {
        this.payAdvParticulars = payAdvParticulars;
    }

    public Long getPaymentNumber() {
        return paymentNumber;
    }

    public void setPaymentNumber(final Long paymentNumber) {
        this.paymentNumber = paymentNumber;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(final Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(final BigDecimal balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public Date getPaymentOrderDate() {
        return paymentOrderDate;
    }

    public void setPaymentOrderDate(final Date paymentOrderDate) {
        this.paymentOrderDate = paymentOrderDate;
    }

    public BigDecimal getAdvanceAmount() {
        return advanceAmount;
    }

    public void setAdvanceAmount(final BigDecimal advanceAmount) {
        this.advanceAmount = advanceAmount;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(final Long deptId) {
        this.deptId = deptId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(final String vendorName) {
        this.vendorName = vendorName;
    }

  

   

    public Long getPaymentReferenceId() {
        return paymentReferenceId;
    }

    public void setPaymentReferenceId(Long paymentReferenceId) {
        this.paymentReferenceId = paymentReferenceId;
    }

    public String getAdv_Flg() {
        return adv_Flg;
    }

    public void setAdv_Flg(final String adv_Flg) {
        this.adv_Flg = adv_Flg;
    }

    public Long getPayAdvSettlementNumber() {
        return payAdvSettlementNumber;
    }

    public void setPayAdvSettlementNumber(final Long payAdvSettlementNumber) {
        this.payAdvSettlementNumber = payAdvSettlementNumber;
    }

    public String getAdv_del_flag() {
        return adv_del_flag;
    }

    public void setAdv_del_flag(final String adv_del_flag) {
        this.adv_del_flag = adv_del_flag;
    }

    public Date getAdv_del_date() {
        return adv_del_date;
    }

    public void setAdv_del_date(final Date adv_del_date) {
        this.adv_del_date = adv_del_date;
    }

    public Date getAdv_posting_date() {
        return adv_posting_date;
    }

    public void setAdv_posting_date(final Date adv_posting_date) {
        this.adv_posting_date = adv_posting_date;
    }

    public String getAdv_order_no() {
        return adv_order_no;
    }

    public void setAdv_order_no(final String adv_order_no) {
        this.adv_order_no = adv_order_no;
    }

    public Long getAdv_del_auth_by() {
        return adv_del_auth_by;
    }

    public void setAdv_del_auth_by(final Long adv_del_auth_by) {
        this.adv_del_auth_by = adv_del_auth_by;
    }

    public String getAdv_del_remark() {
        return adv_del_remark;
    }

    public void setAdv_del_remark(final String adv_del_remark) {
        this.adv_del_remark = adv_del_remark;
    }

    @Override
    public String toString() {
        return "AdvanceEntryEntity [prAdvEntryId=" + prAdvEntryId + ", orgid=" + orgid + ", createdBy=" + createdBy
                + ", createdDate=" + createdDate + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + ", lgIpMac="
                + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd + ", cpdIdStatus=" + cpdIdStatus + ", prAdvEntryDate=" + prAdvEntryDate
                + ", prAdvEntryNo=" + prAdvEntryNo + ", advanceTypeId=" + advanceTypeId + ", vendorId=" + vendorId
                + ", pacHeadId=" + pacHeadId + ", payAdvParticulars=" + payAdvParticulars + ", paymentNumber=" + paymentNumber
                + ", paymentDate=" + paymentDate + ", balanceAmount=" + balanceAmount + ", paymentOrderDate=" + paymentOrderDate
                + ", advanceAmount=" + advanceAmount + ", deptId=" + deptId + ", vendorName=" + vendorName
                + ", paymentReferenceId=" + paymentReferenceId + ", adv_Flg=" + adv_Flg + ", payAdvSettlementNumber="
                + payAdvSettlementNumber + ", adv_del_flag=" + adv_del_flag + ", adv_del_date=" + adv_del_date
                + ", adv_posting_date=" + adv_posting_date + ", adv_order_no=" + adv_order_no + ", adv_del_auth_by="
                + adv_del_auth_by + ", adv_del_remark=" + adv_del_remark + "]";
    }

   
}
