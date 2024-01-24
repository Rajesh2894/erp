package com.abm.mainet.common.integration.acccount.domain;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Rahul.Yadav
 * @since 22 Mar 2016
 */
@Entity
@Table(name = "TB_RECEIPT_DET")
public class TbSrcptFeesDetEntity implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -3221407513663173760L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "RF_FEEID", precision = 12, scale = 0, nullable = false)
    private long rfFeeid;

    /*
     * @Column(name = "RM_RCPTID", precision = 12, scale = 0, nullable = true) private Long rmRcptid;
     */
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "RM_RCPTID", nullable = false, referencedColumnName = "RM_RCPTID")
    private TbServiceReceiptMasEntity rmRcptid;

    @Column(name = "TAX_ID", precision = 12, scale = 0, nullable = true)
    private Long taxId;

    @Column(name = "RF_FEEAMOUNT", precision = 12, scale = 2, nullable = true)
    private BigDecimal rfFeeamount;

    @Column(name = "RM_DEMAND", precision = 12, scale = 2, nullable = true)
    private BigDecimal rmDemand;

    @Column(name = "ORGID", nullable = false, updatable = false)
    private Long orgId;

    @Column(name = "CREATED_BY", nullable = false, updatable = false)
    private Long createdBy;

    // @Column(name = "LANG_ID", precision = 7, scale = 0, nullable = true)
    // private int langId;

    @Column(name = "CREATED_DATE", nullable = true)
    private Date createdDate;

    @Column(name = "UPDATED_BY", nullable = false, updatable = false)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @JsonIgnore
    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    private String lgIpMac;

    @JsonIgnore
    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    @Column(name = "RF_V1", length = 100, nullable = true)
    private String rfV1;

    @Column(name = "RF_V2", length = 100, nullable = true)
    private String rfV2;

    @Column(name = "RF_V3", length = 100, nullable = true)
    private String rfV3;

    @Column(name = "RF_V4", length = 100, nullable = true)
    private String rfV4;

    @Column(name = "RF_V5", length = 100, nullable = true)
    private String rfV5;

    /*
     * @Column(name = "TDD_TAXID", precision = 15, scale = 0, nullable = true) private Long tddTaxid;
     */

    @Column(name = "SAC_HEAD_ID", precision = 12, scale = 0, nullable = true)
    private Long sacHeadId;

    @Column(name = "BM_IDNO", precision = 12, scale = 0, nullable = true)
    private Long bmIdNo;

    @Column(name = "RF_N4", precision = 15, scale = 0, nullable = true)
    private Long rfN4;

    @Column(name = "RF_N5", precision = 15, scale = 0, nullable = true)
    private Long rfN5;

    @Column(name = "RF_D1", nullable = true)
    private Date rfD1;

    @Column(name = "RF_D2", nullable = true)
    private Date rfD2;

    @Column(name = "RF_D3", nullable = true)
    private Date rfD3;

    @Column(name = "RF_LO1", length = 1, nullable = true)
    private String rfLo1;

    @Column(name = "RF_LO2", length = 1, nullable = true)
    private String rfLo2;

    @Column(name = "RF_LO3", length = 1, nullable = true)
    private String rfLo3;

    /*
     * @Column(name = "RF_CPD_MODEID", precision = 12, scale = 0, nullable = true) private Long rfCpdModeid;
     * @Column(name = "RF_SR_CHK_DIS", length = 1, nullable = true) private String rfSrChkDis;
     */

    @Column(name = "BILLDET_ID", precision = 12, scale = 0, nullable = true)
    private Long billdetId;

    /*
     * @OneToOne(fetch = FetchType.LAZY, mappedBy = "tbSrcptFeesDet", cascade = CascadeType.ALL) private
     * AccountReceiptHeaddetEntity accountReceiptHeaddetEntity = new AccountReceiptHeaddetEntity();
     */

    /*
     * @JsonBackReference
     * @OneToMany(mappedBy="tbSrcptFeesDet", targetEntity=AccountReceiptHeaddetEntity.class,fetch = FetchType.EAGER,cascade =
     * CascadeType.ALL) private List<AccountReceiptHeaddetEntity> accountReceiptHeaddetEntity;
     *//**
        * @return the accountReceiptHeaddetEntity
        *//*
           * public List<AccountReceiptHeaddetEntity> getAccountReceiptHeaddetEntity() { return accountReceiptHeaddetEntity; }
           */

    // ----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    // ----------------------------------------------------------------------
    /*
     * @ManyToOne
     * @JoinColumn(name="FUNCTION_ID", referencedColumnName="FUNCTION_ID") private AccountFunctionMasterEntity tbAcFunctionMaster;
     * @ManyToOne
     * @JoinColumn(name="FUND_ID", referencedColumnName="FUND_ID") private AccountFundMasterEntity tbAcFundMaster;
     * @ManyToOne
     * @JoinColumn(name="PAC_HEAD_ID", referencedColumnName="PAC_HEAD_ID") private AccountHeadPrimaryAccountCodeMasterEntity
     * tbAcPrimaryheadMaster;
     * @ManyToOne
     * @JoinColumn(name="SAC_HEAD_ID", referencedColumnName="SAC_HEAD_ID") private AccountHeadSecondaryAccountCodeMasterEntity
     * tbAcSecondaryheadMaster;
     */

    @Column(name = "DPS_SLIPID", precision = 12, scale = 0, nullable = true)
    private Long depositeSlipId;

    // @ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne
    @JoinColumn(name = "BUDGETCODE_ID", nullable = true)
    private AccountBudgetCodeEntity budgetCode;

    /**
     * @param accountReceiptHeaddetEntity the accountReceiptHeaddetEntity to set
     *//*
        * public void setAccountReceiptHeaddetEntity( List<AccountReceiptHeaddetEntity> accountReceiptHeaddetEntity) {
        * this.accountReceiptHeaddetEntity = accountReceiptHeaddetEntity; }
        */

    /**
     * @return the accountReceiptHeaddetEntity
     */
    @Transient
    private BigDecimal balAmount;

    @Transient
    private BigDecimal arrearAmount;

    @Transient
    private String taxCatCode;

    public long getRfFeeid() {
        return rfFeeid;
    }

    public void setRfFeeid(final long rfFeeid) {
        this.rfFeeid = rfFeeid;
    }

    public Long getTaxId() {
        return taxId;
    }

    public void setTaxId(final Long taxId) {
        this.taxId = taxId;
    }

    public BigDecimal getRfFeeamount() {
        return rfFeeamount;
    }

    public void setRfFeeamount(final BigDecimal rfFeeamount) {
        this.rfFeeamount = rfFeeamount;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    /*
     * public int getLangId() { return langId; } public void setLangId(int langId) { this.langId = langId; }
     */

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

    /*
     * public Long getTddTaxid() { return tddTaxid; } public void setTddTaxid(Long tddTaxid) { this.tddTaxid = tddTaxid; }
     */

    /*
     * public Long getRfCpdModeid() { return rfCpdModeid; } public void setRfCpdModeid(Long rfCpdModeid) { this.rfCpdModeid =
     * rfCpdModeid; } public String getRfSrChkDis() { return rfSrChkDis; } public void setRfSrChkDis(String rfSrChkDis) {
     * this.rfSrChkDis = rfSrChkDis; }
     */

    public String[] getPkValues() {
        return new String[] { "AC", "TB_RECEIPT_DET", "RF_FEEID" };
    }

    public TbServiceReceiptMasEntity getRmRcptid() {
        return rmRcptid;
    }

    public void setRmRcptid(final TbServiceReceiptMasEntity rmRcptid) {
        this.rmRcptid = rmRcptid;
    }

    public Long getBilldetId() {
        return billdetId;
    }

    public void setBilldetId(final Long billdetId) {
        this.billdetId = billdetId;
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
     * @return the rfV1
     */
    public String getRfV1() {
        return rfV1;
    }

    /**
     * @param rfV1 the rfV1 to set
     */
    public void setRfV1(final String rfV1) {
        this.rfV1 = rfV1;
    }

    /**
     * @return the rfV2
     */
    public String getRfV2() {
        return rfV2;
    }

    /**
     * @param rfV2 the rfV2 to set
     */
    public void setRfV2(final String rfV2) {
        this.rfV2 = rfV2;
    }

    /**
     * @return the rfV3
     */
    public String getRfV3() {
        return rfV3;
    }

    /**
     * @param rfV3 the rfV3 to set
     */
    public void setRfV3(final String rfV3) {
        this.rfV3 = rfV3;
    }

    /**
     * @return the rfV4
     */
    public String getRfV4() {
        return rfV4;
    }

    /**
     * @param rfV4 the rfV4 to set
     */
    public void setRfV4(final String rfV4) {
        this.rfV4 = rfV4;
    }

    /**
     * @return the rfV5
     */
    public String getRfV5() {
        return rfV5;
    }

    /**
     * @param rfV5 the rfV5 to set
     */
    public void setRfV5(final String rfV5) {
        this.rfV5 = rfV5;
    }

    public Long getSacHeadId() {
        return sacHeadId;
    }

    public void setSacHeadId(final Long sacHeadId) {
        this.sacHeadId = sacHeadId;
    }

    /**
     * @return the rfN4
     */
    public Long getRfN4() {
        return rfN4;
    }

    /**
     * @param rfN4 the rfN4 to set
     */
    public void setRfN4(final Long rfN4) {
        this.rfN4 = rfN4;
    }

    /**
     * @return the rfN5
     */
    public Long getRfN5() {
        return rfN5;
    }

    /**
     * @param rfN5 the rfN5 to set
     */
    public void setRfN5(final Long rfN5) {
        this.rfN5 = rfN5;
    }

    /**
     * @return the rfD1
     */
    public Date getRfD1() {
        return rfD1;
    }

    /**
     * @param rfD1 the rfD1 to set
     */
    public void setRfD1(final Date rfD1) {
        this.rfD1 = rfD1;
    }

    /**
     * @return the rfD2
     */
    public Date getRfD2() {
        return rfD2;
    }

    /**
     * @param rfD2 the rfD2 to set
     */
    public void setRfD2(final Date rfD2) {
        this.rfD2 = rfD2;
    }

    /**
     * @return the rfD3
     */
    public Date getRfD3() {
        return rfD3;
    }

    /**
     * @param rfD3 the rfD3 to set
     */
    public void setRfD3(final Date rfD3) {
        this.rfD3 = rfD3;
    }

    /**
     * @return the rfLo1
     */
    public String getRfLo1() {
        return rfLo1;
    }

    /**
     * @param rfLo1 the rfLo1 to set
     */
    public void setRfLo1(final String rfLo1) {
        this.rfLo1 = rfLo1;
    }

    /**
     * @return the rfLo2
     */
    public String getRfLo2() {
        return rfLo2;
    }

    /**
     * @param rfLo2 the rfLo2 to set
     */
    public void setRfLo2(final String rfLo2) {
        this.rfLo2 = rfLo2;
    }

    /**
     * @return the rfLo3
     */
    public String getRfLo3() {
        return rfLo3;
    }

    /**
     * @param rfLo3 the rfLo3 to set
     */
    public void setRfLo3(final String rfLo3) {
        this.rfLo3 = rfLo3;
    }

    /**
     * @return the depositeSlipId
     */
    public Long getDepositeSlipId() {
        return depositeSlipId;
    }

    /**
     * @param depositeSlipId the depositeSlipId to set
     */
    public void setDepositeSlipId(final Long depositeSlipId) {
        this.depositeSlipId = depositeSlipId;
    }

    public AccountBudgetCodeEntity getBudgetCode() {
        return budgetCode;
    }

    public void setBudgetCode(final AccountBudgetCodeEntity budgetCode) {
        this.budgetCode = budgetCode;
    }

    public Long getBmIdNo() {
        return bmIdNo;
    }

    public void setBmIdNo(Long bmIdNo) {
        this.bmIdNo = bmIdNo;
    }

    public BigDecimal getBalAmount() {
        return balAmount;
    }

    public void setBalAmount(BigDecimal balAmount) {
        this.balAmount = balAmount;
    }

    public BigDecimal getRmDemand() {
        return rmDemand;
    }

    public void setRmDemand(BigDecimal rmDemand) {
        this.rmDemand = rmDemand;
    }

    public BigDecimal getArrearAmount() {
        return arrearAmount;
    }

    public void setArrearAmount(BigDecimal arrearAmount) {
        this.arrearAmount = arrearAmount;
    }

    public String getTaxCatCode() {
        return taxCatCode;
    }

    public void setTaxCatCode(String taxCatCode) {
        this.taxCatCode = taxCatCode;
    }

}