package com.abm.mainet.cfc.challan.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Rahul.Yadav
 * @since 23 Mar 2016
 */
@Entity
@Table(name = "TB_CHALLAN_DET")
public class ChallanDetails implements Serializable {
    private static final long serialVersionUID = -1368501476148737508L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "CH_ID", precision = 12, scale = 0, nullable = false)
    private Long chId;

    @Column(name = "CHALLAN_NO", length = 30, nullable = true)
    private String challanNo;

    @Column(name = "TAX_ID", precision = 12, scale = 0, nullable = true)
    private Long taxId;

    @Column(name = "RF_FEEAMOUNT", precision = 12, scale = 2, nullable = true)
    private Double rfFeeamount;

    @Column(name = "ORGID", nullable = false, updatable = false)
    private Long orgId;

    @Column(name = "USER_ID", nullable = false, updatable = false)
    private Long userId;

    @Column(name = "LANG_ID", precision = 7, scale = 0, nullable = true)
    private int langId;

    @Column(name = "LMODDATE", nullable = true)
    private Date lmodDate;

    @Column(name = "UPDATED_BY", nullable = false, updatable = false)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    @Column(name = "BILL_DETID", precision = 12, scale = 0, nullable = true)
    private Long billdetId;

    public Long getChId() {
        return chId;
    }

    public void setChId(final Long chId) {
        this.chId = chId;
    }

    public String getChallanNo() {
        return challanNo;
    }

    public void setChallanNo(final String challanNo) {
        this.challanNo = challanNo;
    }

    public Long getTaxId() {
        return taxId;
    }

    public void setTaxId(final Long taxId) {
        this.taxId = taxId;
    }

    public Double getRfFeeamount() {
        return rfFeeamount;
    }

    public void setRfFeeamount(final Double rfFeeamount) {
        this.rfFeeamount = rfFeeamount;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public int getLangId() {
        return langId;
    }

    public void setLangId(final int langId) {
        this.langId = langId;
    }

    public Date getLmodDate() {
        return lmodDate;
    }

    public void setLmodDate(final Date lmodDate) {
        this.lmodDate = lmodDate;
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

    public String[] getPkValues() {
        return new String[] { "CFC", "TB_CHALLAN_DET", "CH_ID" };
    }

    public Long getBilldetId() {
        return billdetId;
    }

    public void setBilldetId(final Long billdetId) {
        this.billdetId = billdetId;
    }
}