package com.abm.mainet.water.domain;

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
 * @since 17 Jan 2017
 */
@Entity
@Table(name = "TB_WT_BILL_DET_HIST")
public class TbWtBIllDetHist implements Serializable {
    private static final long serialVersionUID = 5404895100150443767L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "H_BILLDETID", precision = 12, scale = 0, nullable = false)
    private long hBilldetid;

    @Column(name = "BD_BILLDETID", precision = 12, scale = 0, nullable = false)
    private Long bdBilldetid;

    @Column(name = "BM_IDNO", precision = 12, scale = 0, nullable = false)
    private Long bmIdno;

    @Column(name = "TAX_ID", precision = 12, scale = 0, nullable = false)
    private Long taxId;

    @Column(name = "REBATE_ID", precision = 12, scale = 0, nullable = true)
    private Long rebateId;

    @Column(name = "ADJUSTMENT_ID", precision = 12, scale = 0, nullable = true)
    private Long adjustmentId;

    @Column(name = "BD_CUR_TAXAMT", precision = 15, scale = 2, nullable = false)
    private Double bdCurTaxamt;

    @Column(name = "BD_CUR_BAL_TAXAMT", precision = 15, scale = 2, nullable = true)
    private Double bdCurBalTaxamt;

    @Column(name = "BD_PRV_BAL_ARRAMT", precision = 15, scale = 2, nullable = true)
    private Double bdPrvBalArramt;

    @Column(name = "BD_FYI_END_BAL", precision = 15, scale = 2, nullable = true)
    private Double bdFyiEndBal;

    @Column(name = "TDD_TAXID", precision = 12, scale = 0, nullable = true)
    private Long tddTaxid;

    @Column(name = "BD_CSMP", precision = 12, scale = 2, nullable = true)
    private Double bdCsmp;

    @Column(name = "BD_CUR_ARRAMT", precision = 15, scale = 2, nullable = true)
    private Double bdCurArramt;

    @Column(name = "BD_PRV_ARRAMT", precision = 15, scale = 2, nullable = true)
    private Double bdPrvArramt;

    @Column(name = "BD_CUR_BAL_ARRAMT", precision = 15, scale = 2, nullable = true)
    private Double bdCurBalArramt;

    @Column(name = "BD_BILLFLAG", length = 1, nullable = true)
    private String bdBillflag;

    @Column(name = "BD_CUR_TAXAMT_PRINT", precision = 15, scale = 2, nullable = true)
    private Double bdCurTaxamtPrint;

    @Column(name = "BD_DEMAND_FLAG", length = 1, nullable = true)
    private String bdDemandFlag;

    @Column(name = "RULE_ID")
    private String ruleId;

    @Column(name = "BASE_RATE")
    private Double baseRate;

    @Column(name = "WT_V2", length = 100, nullable = true)
    private String wtV2;

    @Column(name = "WT_V3", length = 100, nullable = true)
    private String wtV3;

    @Column(name = "WT_V4", length = 100, nullable = true)
    private String wtV4;

    @Column(name = "WT_V5", length = 100, nullable = true)
    private String wtV5;

    @Column(name = "WT_N2", precision = 15, scale = 0, nullable = true)
    private Long wtN2;

    @Column(name = "WT_N3", precision = 15, scale = 0, nullable = true)
    private Long wtN3;

    @Column(name = "WT_N4", precision = 15, scale = 0, nullable = true)
    private Long wtN4;

    @Column(name = "WT_N5", precision = 15, scale = 0, nullable = true)
    private Long wtN5;

    @Column(name = "WT_D1", nullable = true)
    private Date wtD1;

    @Column(name = "WT_D2", nullable = true)
    private Date wtD2;

    @Column(name = "WT_D3", nullable = true)
    private Date wtD3;

    @Column(name = "WT_LO1", length = 1, nullable = true)
    private String wtLo1;

    @Column(name = "WT_LO2", length = 1, nullable = true)
    private String wtLo2;

    @Column(name = "WT_LO3", length = 1, nullable = true)
    private String wtLo3;

    @Column(name = "ORGID", nullable = false, updatable = false)
    private Long orgid;

    @Column(name = "USER_ID", nullable = false, updatable = false)
    private Long userId;

    @Column(name = "LANG_ID", precision = 7, scale = 0, nullable = false)
    private int langId;

    @Column(name = "LMODDATE", nullable = false)
    private Date lmoddate;

    @Column(name = "UPDATED_BY", nullable = false, updatable = false)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    @Column(name = "TAX_CATEGORY", precision = 12, scale = 0, nullable = true)
    private Long taxCategory;

    @Column(name = "COLL_SEQ", precision = 12, scale = 0, nullable = true)
    private Long collSeq;

    @Column(name = "H_STATUS", length = 1, nullable = true)
    private String hStatus;

    public String[] getPkValues() {
        return new String[] { "WT", "TB_WT_BILL_DET_HIST", "H_BILLDETID" };
    }

    public long gethBilldetid() {
        return hBilldetid;
    }

    public void sethBilldetid(final long hBilldetid) {
        this.hBilldetid = hBilldetid;
    }

    public Long getBdBilldetid() {
        return bdBilldetid;
    }

    public void setBdBilldetid(final Long bdBilldetid) {
        this.bdBilldetid = bdBilldetid;
    }

    public Long getBmIdno() {
        return bmIdno;
    }

    public void setBmIdno(final Long bmIdno) {
        this.bmIdno = bmIdno;
    }

    public Long getTaxId() {
        return taxId;
    }

    public void setTaxId(final Long taxId) {
        this.taxId = taxId;
    }

    public Long getRebateId() {
        return rebateId;
    }

    public void setRebateId(final Long rebateId) {
        this.rebateId = rebateId;
    }

    public Long getAdjustmentId() {
        return adjustmentId;
    }

    public void setAdjustmentId(final Long adjustmentId) {
        this.adjustmentId = adjustmentId;
    }

    public Double getBdCurTaxamt() {
        return bdCurTaxamt;
    }

    public void setBdCurTaxamt(final Double bdCurTaxamt) {
        this.bdCurTaxamt = bdCurTaxamt;
    }

    public Double getBdCurBalTaxamt() {
        return bdCurBalTaxamt;
    }

    public void setBdCurBalTaxamt(final Double bdCurBalTaxamt) {
        this.bdCurBalTaxamt = bdCurBalTaxamt;
    }

    public Double getBdPrvBalArramt() {
        return bdPrvBalArramt;
    }

    public void setBdPrvBalArramt(final Double bdPrvBalArramt) {
        this.bdPrvBalArramt = bdPrvBalArramt;
    }

    public Double getBdFyiEndBal() {
        return bdFyiEndBal;
    }

    public void setBdFyiEndBal(final Double bdFyiEndBal) {
        this.bdFyiEndBal = bdFyiEndBal;
    }

    public Long getTddTaxid() {
        return tddTaxid;
    }

    public void setTddTaxid(final Long tddTaxid) {
        this.tddTaxid = tddTaxid;
    }

    public Double getBdCsmp() {
        return bdCsmp;
    }

    public void setBdCsmp(final Double bdCsmp) {
        this.bdCsmp = bdCsmp;
    }

    public Double getBdCurArramt() {
        return bdCurArramt;
    }

    public void setBdCurArramt(final Double bdCurArramt) {
        this.bdCurArramt = bdCurArramt;
    }

    public Double getBdPrvArramt() {
        return bdPrvArramt;
    }

    public void setBdPrvArramt(final Double bdPrvArramt) {
        this.bdPrvArramt = bdPrvArramt;
    }

    public Double getBdCurBalArramt() {
        return bdCurBalArramt;
    }

    public void setBdCurBalArramt(final Double bdCurBalArramt) {
        this.bdCurBalArramt = bdCurBalArramt;
    }

    public String getBdBillflag() {
        return bdBillflag;
    }

    public void setBdBillflag(final String bdBillflag) {
        this.bdBillflag = bdBillflag;
    }

    public Double getBdCurTaxamtPrint() {
        return bdCurTaxamtPrint;
    }

    public void setBdCurTaxamtPrint(final Double bdCurTaxamtPrint) {
        this.bdCurTaxamtPrint = bdCurTaxamtPrint;
    }

    public String getBdDemandFlag() {
        return bdDemandFlag;
    }

    public void setBdDemandFlag(final String bdDemandFlag) {
        this.bdDemandFlag = bdDemandFlag;
    }

    public String getWtV2() {
        return wtV2;
    }

    public void setWtV2(final String wtV2) {
        this.wtV2 = wtV2;
    }

    public String getWtV3() {
        return wtV3;
    }

    public void setWtV3(final String wtV3) {
        this.wtV3 = wtV3;
    }

    public String getWtV4() {
        return wtV4;
    }

    public void setWtV4(final String wtV4) {
        this.wtV4 = wtV4;
    }

    public String getWtV5() {
        return wtV5;
    }

    public void setWtV5(final String wtV5) {
        this.wtV5 = wtV5;
    }

    public Long getWtN2() {
        return wtN2;
    }

    public void setWtN2(final Long wtN2) {
        this.wtN2 = wtN2;
    }

    public Long getWtN3() {
        return wtN3;
    }

    public void setWtN3(final Long wtN3) {
        this.wtN3 = wtN3;
    }

    public Long getWtN4() {
        return wtN4;
    }

    public void setWtN4(final Long wtN4) {
        this.wtN4 = wtN4;
    }

    public Long getWtN5() {
        return wtN5;
    }

    public void setWtN5(final Long wtN5) {
        this.wtN5 = wtN5;
    }

    public Date getWtD1() {
        return wtD1;
    }

    public void setWtD1(final Date wtD1) {
        this.wtD1 = wtD1;
    }

    public Date getWtD2() {
        return wtD2;
    }

    public void setWtD2(final Date wtD2) {
        this.wtD2 = wtD2;
    }

    public Date getWtD3() {
        return wtD3;
    }

    public void setWtD3(final Date wtD3) {
        this.wtD3 = wtD3;
    }

    public String getWtLo1() {
        return wtLo1;
    }

    public void setWtLo1(final String wtLo1) {
        this.wtLo1 = wtLo1;
    }

    public String getWtLo2() {
        return wtLo2;
    }

    public void setWtLo2(final String wtLo2) {
        this.wtLo2 = wtLo2;
    }

    public String getWtLo3() {
        return wtLo3;
    }

    public void setWtLo3(final String wtLo3) {
        this.wtLo3 = wtLo3;
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

    public Long getTaxCategory() {
        return taxCategory;
    }

    public void setTaxCategory(final Long taxCategory) {
        this.taxCategory = taxCategory;
    }

    public Long getCollSeq() {
        return collSeq;
    }

    public void setCollSeq(final Long collSeq) {
        this.collSeq = collSeq;
    }

    public String gethStatus() {
        return hStatus;
    }

    public void sethStatus(final String hStatus) {
        this.hStatus = hStatus;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    public Date getLmoddate() {
        return lmoddate;
    }

    public void setLmoddate(final Date lmoddate) {
        this.lmoddate = lmoddate;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public Double getBaseRate() {
        return baseRate;
    }

    public void setBaseRate(Double baseRate) {
        this.baseRate = baseRate;
    }

}