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
@Table(name = "TB_WT_BILL_MAS_HIST")
public class TbWtBIllMasHist implements Serializable {
    private static final long serialVersionUID = 8055787525124886741L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "H_BMIDNO", precision = 12, scale = 0, nullable = false)
    private long hBmidno;

    @Column(name = "BM_IDNO", precision = 12, scale = 0, nullable = false)
    private long bmIdno;

    @Column(name = "CS_IDN", precision = 12, scale = 0, nullable = false)
    private Long csIdn;

    @Column(name = "BM_YEAR", precision = 4, scale = 0, nullable = false)
    private Long bmYear;

    @Column(name = "BM_BILLDT", nullable = true)
    private Date bmBilldt;

    @Column(name = "BM_FROMDT", nullable = false)
    private Date bmFromdt;

    @Column(name = "BM_TODT", nullable = false)
    private Date bmTodt;

    @Column(name = "BM_DUEDATE", nullable = true)
    private Date bmDuedate;

    @Column(name = "BM_TOTAL_AMOUNT", precision = 15, scale = 2, nullable = true)
    private Double bmTotalAmount;

    @Column(name = "BM_TOTAL_BAL_AMOUNT", precision = 15, scale = 2, nullable = true)
    private Double bmTotalBalAmount;

    @Column(name = "BM_INT_VALUE", precision = 15, scale = 2, nullable = true)
    private Double bmIntValue;

    @Column(name = "BM_TOTAL_ARREARS", precision = 15, scale = 2, nullable = true)
    private Double bmTotalArrears;

    @Column(name = "BM_TOTAL_OUTSTANDING", precision = 15, scale = 2, nullable = true)
    private Double bmTotalOutstanding;

    @Column(name = "BM_TOTAL_ARREARS_WITHOUT_INT", precision = 15, scale = 2, nullable = true)
    private Double bmTotalArrearsWithoutInt;

    @Column(name = "BM_TOTAL_CUM_INT_ARREARS", precision = 15, scale = 2, nullable = true)
    private Double bmTotalCumIntArrears;

    @Column(name = "BM_TOATL_INT", precision = 15, scale = 2, nullable = true)
    private Double bmToatlInt;

    @Column(name = "BM_LAST_RCPTAMT", precision = 12, scale = 2, nullable = true)
    private Double bmLastRcptamt;

    @Column(name = "BM_LAST_RCPTDT", nullable = true)
    private Date bmLastRcptdt;

    @Column(name = "BM_TOATL_REBATE", precision = 15, scale = 2, nullable = true)
    private Double bmToatlRebate;

    @Column(name = "BM_PAID_FLAG", length = 1, nullable = true)
    private String bmPaidFlag;

    @Column(name = "BM_FY_TOTAL_ARREARS", precision = 15, scale = 2, nullable = true)
    private Double bmFyTotalArrears;

    @Column(name = "BM_FY_TOTAL_INT", precision = 15, scale = 2, nullable = true)
    private Double bmFyTotalInt;

    @Column(name = "FLAG_JV_POST", length = 1, nullable = true)
    private String flagJvPost;

    @Column(name = "DIST_DATE", nullable = true)
    private Date distDate;

    @Column(name = "BM_REMARKS", length = 100, nullable = true)
    private String bmRemarks;

    @Column(name = "BM_PRINTDATE", nullable = true)
    private Date bmPrintdate;

    @Column(name = "ARREARS_BILL", length = 1, nullable = true)
    private String arrearsBill;

    @Column(name = "BM_TOTPAYAMT_AFTDUE", precision = 15, scale = 2, nullable = true)
    private Double bmTotpayamtAftdue;

    @Column(name = "BM_INTAMT_AFTDUE", precision = 15, scale = 2, nullable = true)
    private Double bmIntamtAftdue;

    @Column(name = "BM_INT_TYPE", length = 15, nullable = true)
    private String bmIntType;

    @Column(name = "BM_TRD_PREMIS", precision = 0, scale = 0, nullable = true)
    private Long bmTrdPremis;

    @Column(name = "BM_CCN_SIZE", precision = 0, scale = 0, nullable = true)
    private Long bmCcnSize;

    @Column(name = "BM_CCN_OWNER", length = 500, nullable = true)
    private String bmCcnOwner;

    @Column(name = "BM_ENTRY_FLAG", length = 1, nullable = true)
    private String bmEntryFlag;

    @Column(name = "BM_INT_CHARGED_FLAG", length = 1, nullable = true)
    private String bmIntChargedFlag;

    @Column(name = "AMEND_FOR_BILL_ID", precision = 12, scale = 0, nullable = true)
    private Long amendForBillId;

    @Column(name = "BM_METEREDCCN", length = 1, nullable = true)
    private String bmMeteredccn;

    @Column(name = "BM_DUEDATE2", nullable = true)
    private Date bmDuedate2;

    @Column(name = "CH_SHD_INT_CHARGED_FLAG", length = 1, nullable = true)
    private String chShdIntChargedFlag;

    @Column(name = "BM_SEC_DEP_AMT", precision = 15, scale = 2, nullable = true)
    private Double bmSecDepAmt;

    @Column(name = "BM_LAST_SEC_DEP_RCPTNO", length = 20, nullable = true)
    private String bmLastSecDepRcptno;

    @Column(name = "BM_LAST_SEC_DEP_RCPTDT", nullable = true)
    private Date bmLastSecDepRcptdt;

    @Column(name = "WT_V1", length = 100, nullable = true)
    private String wtV1;

    @Column(name = "WT_V2", length = 100, nullable = true)
    private String wtV2;

    @Column(name = "WT_V3", length = 100, nullable = true)
    private String wtV3;

    @Column(name = "WT_V4", length = 100, nullable = true)
    private String wtV4;

    @Column(name = "WT_N1", precision = 15, scale = 0, nullable = true)
    private Long wtN1;

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

    @Column(name = "INT_FROM", nullable = true)
    private Date intFrom;

    @Column(name = "INT_TO", nullable = true)
    private Date intTo;

    @Column(name = "FYI_INT_ARREARS", precision = 15, scale = 2, nullable = true)
    private Double fyiIntArrears;

    @Column(name = "FYI_INT_PERC", precision = 15, scale = 2, nullable = true)
    private Double fyiIntPerc;

    @Column(name = "RM_RCPTID", precision = 12, scale = 0, nullable = true)
    private Long rmRcptid;

    @Column(name = "BM_NO", length = 16, nullable = true)
    private String bmNo;

    @Column(name = "H_STATUS", length = 1, nullable = true)
    private String hStatus;
    
    @Column(name = "BM_TOTAL_PENALTY") 
    private double totalPenalty;

    public String[] getPkValues() {
        return new String[] { "WT", "TB_WT_BILL_MAS_HIST", "H_BMIDNO" };
    }

    public long gethBmidno() {
        return hBmidno;
    }

    public void sethBmidno(final long hBmidno) {
        this.hBmidno = hBmidno;
    }

    public long getBmIdno() {
        return bmIdno;
    }

    public void setBmIdno(final long bmIdno) {
        this.bmIdno = bmIdno;
    }

    public Long getCsIdn() {
        return csIdn;
    }

    public void setCsIdn(final Long csIdn) {
        this.csIdn = csIdn;
    }

    public Long getBmYear() {
        return bmYear;
    }

    public void setBmYear(final Long bmYear) {
        this.bmYear = bmYear;
    }

    public Date getBmBilldt() {
        return bmBilldt;
    }

    public void setBmBilldt(final Date bmBilldt) {
        this.bmBilldt = bmBilldt;
    }

    public Date getBmFromdt() {
        return bmFromdt;
    }

    public void setBmFromdt(final Date bmFromdt) {
        this.bmFromdt = bmFromdt;
    }

    public Date getBmTodt() {
        return bmTodt;
    }

    public void setBmTodt(final Date bmTodt) {
        this.bmTodt = bmTodt;
    }

    public Date getBmDuedate() {
        return bmDuedate;
    }

    public void setBmDuedate(final Date bmDuedate) {
        this.bmDuedate = bmDuedate;
    }

    public Double getBmTotalAmount() {
        return bmTotalAmount;
    }

    public void setBmTotalAmount(final Double bmTotalAmount) {
        this.bmTotalAmount = bmTotalAmount;
    }

    public Double getBmTotalBalAmount() {
        return bmTotalBalAmount;
    }

    public void setBmTotalBalAmount(final Double bmTotalBalAmount) {
        this.bmTotalBalAmount = bmTotalBalAmount;
    }

    public Double getBmIntValue() {
        return bmIntValue;
    }

    public void setBmIntValue(final Double bmIntValue) {
        this.bmIntValue = bmIntValue;
    }

    public Double getBmTotalArrears() {
        return bmTotalArrears;
    }

    public void setBmTotalArrears(final Double bmTotalArrears) {
        this.bmTotalArrears = bmTotalArrears;
    }

    public Double getBmTotalOutstanding() {
        return bmTotalOutstanding;
    }

    public void setBmTotalOutstanding(final Double bmTotalOutstanding) {
        this.bmTotalOutstanding = bmTotalOutstanding;
    }

    public Double getBmTotalArrearsWithoutInt() {
        return bmTotalArrearsWithoutInt;
    }

    public void setBmTotalArrearsWithoutInt(final Double bmTotalArrearsWithoutInt) {
        this.bmTotalArrearsWithoutInt = bmTotalArrearsWithoutInt;
    }

    public Double getBmTotalCumIntArrears() {
        return bmTotalCumIntArrears;
    }

    public void setBmTotalCumIntArrears(final Double bmTotalCumIntArrears) {
        this.bmTotalCumIntArrears = bmTotalCumIntArrears;
    }

    public Double getBmToatlInt() {
        return bmToatlInt;
    }

    public void setBmToatlInt(final Double bmToatlInt) {
        this.bmToatlInt = bmToatlInt;
    }

    public Double getBmLastRcptamt() {
        return bmLastRcptamt;
    }

    public void setBmLastRcptamt(final Double bmLastRcptamt) {
        this.bmLastRcptamt = bmLastRcptamt;
    }

    public Date getBmLastRcptdt() {
        return bmLastRcptdt;
    }

    public void setBmLastRcptdt(final Date bmLastRcptdt) {
        this.bmLastRcptdt = bmLastRcptdt;
    }

    public Double getBmToatlRebate() {
        return bmToatlRebate;
    }

    public void setBmToatlRebate(final Double bmToatlRebate) {
        this.bmToatlRebate = bmToatlRebate;
    }

    public String getBmPaidFlag() {
        return bmPaidFlag;
    }

    public void setBmPaidFlag(final String bmPaidFlag) {
        this.bmPaidFlag = bmPaidFlag;
    }

    public Double getBmFyTotalArrears() {
        return bmFyTotalArrears;
    }

    public void setBmFyTotalArrears(final Double bmFyTotalArrears) {
        this.bmFyTotalArrears = bmFyTotalArrears;
    }

    public Double getBmFyTotalInt() {
        return bmFyTotalInt;
    }

    public void setBmFyTotalInt(final Double bmFyTotalInt) {
        this.bmFyTotalInt = bmFyTotalInt;
    }

    public String getFlagJvPost() {
        return flagJvPost;
    }

    public void setFlagJvPost(final String flagJvPost) {
        this.flagJvPost = flagJvPost;
    }

    public Date getDistDate() {
        return distDate;
    }

    public void setDistDate(final Date distDate) {
        this.distDate = distDate;
    }

    public String getBmRemarks() {
        return bmRemarks;
    }

    public void setBmRemarks(final String bmRemarks) {
        this.bmRemarks = bmRemarks;
    }

    public Date getBmPrintdate() {
        return bmPrintdate;
    }

    public void setBmPrintdate(final Date bmPrintdate) {
        this.bmPrintdate = bmPrintdate;
    }

    public String getArrearsBill() {
        return arrearsBill;
    }

    public void setArrearsBill(final String arrearsBill) {
        this.arrearsBill = arrearsBill;
    }

    public Double getBmTotpayamtAftdue() {
        return bmTotpayamtAftdue;
    }

    public void setBmTotpayamtAftdue(final Double bmTotpayamtAftdue) {
        this.bmTotpayamtAftdue = bmTotpayamtAftdue;
    }

    public Double getBmIntamtAftdue() {
        return bmIntamtAftdue;
    }

    public void setBmIntamtAftdue(final Double bmIntamtAftdue) {
        this.bmIntamtAftdue = bmIntamtAftdue;
    }

    public String getBmIntType() {
        return bmIntType;
    }

    public void setBmIntType(final String bmIntType) {
        this.bmIntType = bmIntType;
    }

    public Long getBmTrdPremis() {
        return bmTrdPremis;
    }

    public void setBmTrdPremis(final Long bmTrdPremis) {
        this.bmTrdPremis = bmTrdPremis;
    }

    public Long getBmCcnSize() {
        return bmCcnSize;
    }

    public void setBmCcnSize(final Long bmCcnSize) {
        this.bmCcnSize = bmCcnSize;
    }

    public String getBmCcnOwner() {
        return bmCcnOwner;
    }

    public void setBmCcnOwner(final String bmCcnOwner) {
        this.bmCcnOwner = bmCcnOwner;
    }

    public String getBmEntryFlag() {
        return bmEntryFlag;
    }

    public void setBmEntryFlag(final String bmEntryFlag) {
        this.bmEntryFlag = bmEntryFlag;
    }

    public String getBmIntChargedFlag() {
        return bmIntChargedFlag;
    }

    public void setBmIntChargedFlag(final String bmIntChargedFlag) {
        this.bmIntChargedFlag = bmIntChargedFlag;
    }

    public Long getAmendForBillId() {
        return amendForBillId;
    }

    public void setAmendForBillId(final Long amendForBillId) {
        this.amendForBillId = amendForBillId;
    }

    public String getBmMeteredccn() {
        return bmMeteredccn;
    }

    public void setBmMeteredccn(final String bmMeteredccn) {
        this.bmMeteredccn = bmMeteredccn;
    }

    public Date getBmDuedate2() {
        return bmDuedate2;
    }

    public void setBmDuedate2(final Date bmDuedate2) {
        this.bmDuedate2 = bmDuedate2;
    }

    public String getChShdIntChargedFlag() {
        return chShdIntChargedFlag;
    }

    public void setChShdIntChargedFlag(final String chShdIntChargedFlag) {
        this.chShdIntChargedFlag = chShdIntChargedFlag;
    }

    public Double getBmSecDepAmt() {
        return bmSecDepAmt;
    }

    public void setBmSecDepAmt(final Double bmSecDepAmt) {
        this.bmSecDepAmt = bmSecDepAmt;
    }

    public String getBmLastSecDepRcptno() {
        return bmLastSecDepRcptno;
    }

    public void setBmLastSecDepRcptno(final String bmLastSecDepRcptno) {
        this.bmLastSecDepRcptno = bmLastSecDepRcptno;
    }

    public Date getBmLastSecDepRcptdt() {
        return bmLastSecDepRcptdt;
    }

    public void setBmLastSecDepRcptdt(final Date bmLastSecDepRcptdt) {
        this.bmLastSecDepRcptdt = bmLastSecDepRcptdt;
    }

    public String getWtV1() {
        return wtV1;
    }

    public void setWtV1(final String wtV1) {
        this.wtV1 = wtV1;
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

    public Long getWtN1() {
        return wtN1;
    }

    public void setWtN1(final Long wtN1) {
        this.wtN1 = wtN1;
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

    public Date getIntFrom() {
        return intFrom;
    }

    public void setIntFrom(final Date intFrom) {
        this.intFrom = intFrom;
    }

    public Date getIntTo() {
        return intTo;
    }

    public void setIntTo(final Date intTo) {
        this.intTo = intTo;
    }

    public Double getFyiIntArrears() {
        return fyiIntArrears;
    }

    public void setFyiIntArrears(final Double fyiIntArrears) {
        this.fyiIntArrears = fyiIntArrears;
    }

    public Double getFyiIntPerc() {
        return fyiIntPerc;
    }

    public void setFyiIntPerc(final Double fyiIntPerc) {
        this.fyiIntPerc = fyiIntPerc;
    }

    public Long getRmRcptid() {
        return rmRcptid;
    }

    public void setRmRcptid(final Long rmRcptid) {
        this.rmRcptid = rmRcptid;
    }

    public String getBmNo() {
        return bmNo;
    }

    public void setBmNo(final String bmNo) {
        this.bmNo = bmNo;
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

    public double getTotalPenalty() {
        return totalPenalty;
    }

    public void setTotalPenalty(double totalPenalty) {
        this.totalPenalty = totalPenalty;
    }

}