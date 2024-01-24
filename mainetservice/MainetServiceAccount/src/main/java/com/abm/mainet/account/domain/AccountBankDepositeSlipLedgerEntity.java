
package com.abm.mainet.account.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.acccount.domain.TbSrcptFeesDetEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author prasant.sahu
 *
 */

@Entity
@Table(name = "TB_AC_BANK_DEPOSITSLIP_DET")
public class AccountBankDepositeSlipLedgerEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "CH_SLIPHEADID")
    private Long chequeSlipHeadId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "DPS_SLIPID", referencedColumnName = "DPS_SLIPID")
    private AccountBankDepositeSlipMasterEntity depositeSlipId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "RF_FEEID", referencedColumnName = "RF_FEEID")
    private TbSrcptFeesDetEntity tbSrcptFeesDetEntity;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "RM_RCPTID", referencedColumnName = "RM_RCPTID")
    private TbServiceReceiptMasEntity tbServiceReceiptMasEntity;

    @Column(name = "DEPO_AMOUNT")
    private Double depositeAmount;

    @Column(name = "ACT_AMT")
    private Double headWiseActualReceiptAmt;

    @Column(name = "ORGID")
    private Long orgid;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "LANG_ID")
    private Integer langId;

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @JsonIgnore
    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @JsonIgnore
    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @Column(name = "FI04_N1", length = 15)
    private Long fi04N1;

    @Column(name = "FI04_V1", length = 100)
    private String fi04V1;

    @Column(name = "FI04_D1")
    private Date fi04D1;

    @Column(name = "FI04_LO1", length = 1)
    private String fi04Lo1;

    /**
     * @return the chequeSlipHeadId
     */
    public Long getChequeSlipHeadId() {
        return chequeSlipHeadId;
    }

    /**
     * @param chequeSlipHeadId the chequeSlipHeadId to set
     */
    public void setChequeSlipHeadId(final Long chequeSlipHeadId) {
        this.chequeSlipHeadId = chequeSlipHeadId;
    }

    /**
     * @return the depositeSlipId
     */
    public AccountBankDepositeSlipMasterEntity getDepositeSlipId() {
        return depositeSlipId;
    }

    /**
     * @param depositeSlipId the depositeSlipId to set
     */
    public void setDepositeSlipId(final AccountBankDepositeSlipMasterEntity depositeSlipId) {
        this.depositeSlipId = depositeSlipId;
    }

    /**
     * @return the headWiseActualReceiptAmt
     */
    public Double getHeadWiseActualReceiptAmt() {
        return headWiseActualReceiptAmt;
    }

    /**
     * @param headWiseActualReceiptAmt the headWiseActualReceiptAmt to set
     */
    public void setHeadWiseActualReceiptAmt(final Double headWiseActualReceiptAmt) {
        this.headWiseActualReceiptAmt = headWiseActualReceiptAmt;
    }

    /**
     * @return the orgid
     */
    public Long getOrgid() {
        return orgid;
    }

    /**
     * @param orgid the orgid to set
     */
    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    /**
     * @return the createdBy
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the langId
     */
    public Integer getLangId() {
        return langId;
    }

    /**
     * @param langId the langId to set
     */
    public void setLangId(final Integer langId) {
        this.langId = langId;
    }

    /**
     * @return the createdDate
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return the updatedBy
     */
    public Long getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return the updatedDate
     */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate the updatedDate to set
     */
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return the lgIpMac
     */
    public String getLgIpMac() {
        return lgIpMac;
    }

    /**
     * @param lgIpMac the lgIpMac to set
     */
    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    /**
     * @return the lgIpMacUpd
     */
    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    /**
     * @param lgIpMacUpd the lgIpMacUpd to set
     */
    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    /**
     * @return the fi04N1
     */
    public Long getFi04N1() {
        return fi04N1;
    }

    /**
     * @param fi04n1 the fi04N1 to set
     */
    public void setFi04N1(final Long fi04n1) {
        fi04N1 = fi04n1;
    }

    /**
     * @return the fi04V1
     */
    public String getFi04V1() {
        return fi04V1;
    }

    /**
     * @param fi04v1 the fi04V1 to set
     */
    public void setFi04V1(final String fi04v1) {
        fi04V1 = fi04v1;
    }

    /**
     * @return the fi04D1
     */
    public Date getFi04D1() {
        return fi04D1;
    }

    /**
     * @param fi04d1 the fi04D1 to set
     */
    public void setFi04D1(final Date fi04d1) {
        fi04D1 = fi04d1;
    }

    /**
     * @return the fi04Lo1
     */
    public String getFi04Lo1() {
        return fi04Lo1;
    }

    /**
     * @param fi04Lo1 the fi04Lo1 to set
     */
    public void setFi04Lo1(final String fi04Lo1) {
        this.fi04Lo1 = fi04Lo1;
    }

    /**
     * @return the depositeAmount
     */
    public Double getDepositeAmount() {
        return depositeAmount;
    }

    /**
     * @param depositeAmount the depositeAmount to set
     */
    public void setDepositeAmount(final Double depositeAmount) {
        this.depositeAmount = depositeAmount;
    }

    public static String[] getPkValues() {
        return new String[] { "AC", "TB_AC_BANK_DEPOSITSLIP_DET", "CH_SLIPHEADID" };
    }

    /**
     * @return the tbSrcptFeesDetEntity
     */
    public TbSrcptFeesDetEntity getTbSrcptFeesDetEntity() {
        return tbSrcptFeesDetEntity;
    }

    /**
     * @param tbSrcptFeesDetEntity the tbSrcptFeesDetEntity to set
     */
    public void setTbSrcptFeesDetEntity(final TbSrcptFeesDetEntity tbSrcptFeesDetEntity) {
        this.tbSrcptFeesDetEntity = tbSrcptFeesDetEntity;
    }
}
