/*
 * Created on 13 May 2016 ( Time 13:31:59 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.abm.mainet.water.rest.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ViewBillMas implements Serializable {

    private static final long serialVersionUID = 3855218431953233753L;

    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    // ----------------------------------------------------------------------
    //@NotNull
    private long bmIdno;

    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------
    //@NotNull
   // private Long csIdn;

    @NotNull
    private String bmYear;

    private Date bmBilldt;

    @NotNull
    private Date bmFromdt;
    @NotNull
    private Date bmTodt;

    private Date bmDuedate;

    private double bmTotalAmount;

    private double bmTotalBalAmount;

    private double bmIntValue;

    private double bmTotalArrears;

    private double bmTotalOutstanding;

    private double bmTotalArrearsWithoutInt;

    private double bmTotalCumIntArrears;

    private double bmActualArrearsAmt;

    private String genFlag;

    private double bmToatlInt;

    private double bmLastRcptamt;

    private Date bmLastRcptdt;

    private double bmToatlRebate;

    @Size(max = 1)
    private String bmPaidFlag;

    private double bmFyTotalArrears;

    private double bmFyTotalInt;

    @Size(max = 1)
    private String flagJvPost;

    private Date distDate;

    @Size(max = 100)
    private String bmRemarks;

    private Date bmPrintdate;

    @Size(max = 1)
    private String arrearsBill;

    private double bmTotpayamtAftdue;

    private double bmIntamtAftdue;

    @Size(max = 15)
    private String bmIntType;

    private BigDecimal bmTrdPremis;

    private BigDecimal bmCcnSize;

    @Size(max = 500)
    private String bmCcnOwner;

    @Size(max = 1)
    private String bmEntryFlag;

    @Size(max = 1)
    private String bmIntChargedFlag;

    private Long amendForBillId;

    @Size(max = 1)
    private String bmMeteredccn;

    private Date bmDuedate2;

    @Size(max = 1)
    private String chShdIntChargedFlag;

    private double bmSecDepAmt;

    @Size(max = 20)
    private String bmLastSecDepRcptno;

    private Date bmLastSecDepRcptdt;

    @Size(max = 100)
    private String wtV1;

    @Size(max = 100)
    private String wtV2;

    @Size(max = 100)
    private String wtV3;

    @Size(max = 100)
    private String wtV4;

    private BigDecimal wtN1;

    private BigDecimal wtN2;

    private BigDecimal wtN3;

    private BigDecimal wtN4;

    private BigDecimal wtN5;

    private Date wtD1;

    private Date wtD2;

    private Date wtD3;

    @Size(max = 1)
    private String wtLo1;

    @Size(max = 1)
    private String wtLo2;

    @Size(max = 1)
    private String wtLo3;

   // @NotNull
    //private Long orgid;

   // @NotNull
    //private Long userId;

    //@NotNull
    //private int langId;

    //@NotNull
   //// private Date lmoddate;

    //private Long updatedBy;

    /////private Date updatedDate;

    //@Size(max = 100)
    //private String lgIpMac;

    //@Size(max = 100)
    //private String lgIpMacUpd;

    private Date intFrom;

    private Date intTo;

    private BigDecimal fyiIntArrears;

    private BigDecimal fyiIntPerc;

    private double grandTotal;

    private double arrearsTotal;

    private double taxtotal;

    private String amountInwords;

    private String uniqueReferenceNumber;

    private Long rmRcptid;

    private String bmNo;

    private double excessAmount;

    private double rebateAmount;

    private String interstCalMethod;

    private String taxCode;

    private double totalPenalty;

    private String bmGenDes;

    // Property Tax extra field
    private Long assId;

    private String propNo;

    private long dummyMasId;

    private String currentBillFlag;

    private List<ViewBillDet> viewBillDet = new ArrayList<>(0);

    private Map<String, Double> taxWiseReabte = new HashMap<String, Double>();
    
    private Double surcharge;

    public void setBmBilldt(final Date bmBilldt) {
        this.bmBilldt = bmBilldt;
    }

    public Date getBmBilldt() {
        return bmBilldt;
    }

    public void setBmFromdt(final Date bmFromdt) {
        this.bmFromdt = bmFromdt;
    }

    public Date getBmFromdt() {
        return bmFromdt;
    }

    public void setBmTodt(final Date bmTodt) {
        this.bmTodt = bmTodt;
    }

    public Date getBmTodt() {
        return bmTodt;
    }

    public void setBmDuedate(final Date bmDuedate) {
        this.bmDuedate = bmDuedate;
    }

    public Date getBmDuedate() {
        return bmDuedate;
    }

    public void setBmTotalAmount(final double bmTotalAmount) {
        this.bmTotalAmount = bmTotalAmount;
    }

    public double getBmTotalAmount() {
        return bmTotalAmount;
    }

    public void setBmLastRcptdt(final Date bmLastRcptdt) {
        this.bmLastRcptdt = bmLastRcptdt;
    }

    public Date getBmLastRcptdt() {
        return bmLastRcptdt;
    }

    public void setBmPaidFlag(final String bmPaidFlag) {
        this.bmPaidFlag = bmPaidFlag;
    }

    public String getBmPaidFlag() {
        return bmPaidFlag;
    }

    public void setFlagJvPost(final String flagJvPost) {
        this.flagJvPost = flagJvPost;
    }

    public String getFlagJvPost() {
        return flagJvPost;
    }

    public void setDistDate(final Date distDate) {
        this.distDate = distDate;
    }

    public Date getDistDate() {
        return distDate;
    }

    public void setBmRemarks(final String bmRemarks) {
        this.bmRemarks = bmRemarks;
    }

    public String getBmRemarks() {
        return bmRemarks;
    }

    public void setBmPrintdate(final Date bmPrintdate) {
        this.bmPrintdate = bmPrintdate;
    }

    public Date getBmPrintdate() {
        return bmPrintdate;
    }

    public void setArrearsBill(final String arrearsBill) {
        this.arrearsBill = arrearsBill;
    }

    public String getArrearsBill() {
        return arrearsBill;
    }

    public void setBmIntType(final String bmIntType) {
        this.bmIntType = bmIntType;
    }

    public String getBmIntType() {
        return bmIntType;
    }

    public void setBmTrdPremis(final BigDecimal bmTrdPremis) {
        this.bmTrdPremis = bmTrdPremis;
    }

    public BigDecimal getBmTrdPremis() {
        return bmTrdPremis;
    }

    public void setBmCcnSize(final BigDecimal bmCcnSize) {
        this.bmCcnSize = bmCcnSize;
    }

    public BigDecimal getBmCcnSize() {
        return bmCcnSize;
    }

    public void setBmCcnOwner(final String bmCcnOwner) {
        this.bmCcnOwner = bmCcnOwner;
    }

    public String getBmCcnOwner() {
        return bmCcnOwner;
    }

    public void setBmEntryFlag(final String bmEntryFlag) {
        this.bmEntryFlag = bmEntryFlag;
    }

    public String getBmEntryFlag() {
        return bmEntryFlag;
    }

    public void setBmIntChargedFlag(final String bmIntChargedFlag) {
        this.bmIntChargedFlag = bmIntChargedFlag;
    }

    public String getBmIntChargedFlag() {
        return bmIntChargedFlag;
    }

    public void setBmMeteredccn(final String bmMeteredccn) {
        this.bmMeteredccn = bmMeteredccn;
    }

    public String getBmMeteredccn() {
        return bmMeteredccn;
    }

    public void setBmDuedate2(final Date bmDuedate2) {
        this.bmDuedate2 = bmDuedate2;
    }

    public Date getBmDuedate2() {
        return bmDuedate2;
    }

    public void setChShdIntChargedFlag(
            final String chShdIntChargedFlag) {
        this.chShdIntChargedFlag = chShdIntChargedFlag;
    }

    public String getChShdIntChargedFlag() {
        return chShdIntChargedFlag;
    }

    public void setBmLastSecDepRcptno(
            final String bmLastSecDepRcptno) {
        this.bmLastSecDepRcptno = bmLastSecDepRcptno;
    }

    public String getBmLastSecDepRcptno() {
        return bmLastSecDepRcptno;
    }

    public void setBmLastSecDepRcptdt(
            final Date bmLastSecDepRcptdt) {
        this.bmLastSecDepRcptdt = bmLastSecDepRcptdt;
    }

    public Date getBmLastSecDepRcptdt() {
        return bmLastSecDepRcptdt;
    }

    public void setWtV1(final String wtV1) {
        this.wtV1 = wtV1;
    }

    public String getWtV1() {
        return wtV1;
    }

    public void setWtV2(final String wtV2) {
        this.wtV2 = wtV2;
    }

    public String getWtV2() {
        return wtV2;
    }

    public void setWtV3(final String wtV3) {
        this.wtV3 = wtV3;
    }

    public String getWtV3() {
        return wtV3;
    }

    public void setWtV4(final String wtV4) {
        this.wtV4 = wtV4;
    }

    public String getWtV4() {
        return wtV4;
    }

    public void setWtN1(final BigDecimal wtN1) {
        this.wtN1 = wtN1;
    }

    public BigDecimal getWtN1() {
        return wtN1;
    }

    public void setWtN2(final BigDecimal wtN2) {
        this.wtN2 = wtN2;
    }

    public BigDecimal getWtN2() {
        return wtN2;
    }

    public void setWtN3(final BigDecimal wtN3) {
        this.wtN3 = wtN3;
    }

    public BigDecimal getWtN3() {
        return wtN3;
    }

    public void setWtN4(final BigDecimal wtN4) {
        this.wtN4 = wtN4;
    }

    public BigDecimal getWtN4() {
        return wtN4;
    }

    public void setWtN5(final BigDecimal wtN5) {
        this.wtN5 = wtN5;
    }

    public BigDecimal getWtN5() {
        return wtN5;
    }

    public void setWtD1(final Date wtD1) {
        this.wtD1 = wtD1;
    }

    public Date getWtD1() {
        return wtD1;
    }

    public void setWtD2(final Date wtD2) {
        this.wtD2 = wtD2;
    }

    public Date getWtD2() {
        return wtD2;
    }

    public void setWtD3(final Date wtD3) {
        this.wtD3 = wtD3;
    }

    public Date getWtD3() {
        return wtD3;
    }

    public void setWtLo1(final String wtLo1) {
        this.wtLo1 = wtLo1;
    }

    public String getWtLo1() {
        return wtLo1;
    }

    public void setWtLo2(final String wtLo2) {
        this.wtLo2 = wtLo2;
    }

    public String getWtLo2() {
        return wtLo2;
    }

    public void setWtLo3(final String wtLo3) {
        this.wtLo3 = wtLo3;
    }

    public String getWtLo3() {
        return wtLo3;
    }

/*    public void setLmoddate(final Date lmoddate) {
        this.lmoddate = lmoddate;
    }

    public Date getLmoddate() {
        return lmoddate;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }*/

    public void setIntFrom(final Date intFrom) {
        this.intFrom = intFrom;
    }

    public Date getIntFrom() {
        return intFrom;
    }

    public void setIntTo(final Date intTo) {
        this.intTo = intTo;
    }

    public Date getIntTo() {
        return intTo;
    }

    public void setFyiIntArrears(final BigDecimal fyiIntArrears) {
        this.fyiIntArrears = fyiIntArrears;
    }

    public BigDecimal getFyiIntArrears() {
        return fyiIntArrears;
    }

    public void setFyiIntPerc(final BigDecimal fyiIntPerc) {
        this.fyiIntPerc = fyiIntPerc;
    }

    public BigDecimal getFyiIntPerc() {
        return fyiIntPerc;
    }

    // ----------------------------------------------------------------------
    // toString METHOD
    // ----------------------------------------------------------------------

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(bmYear);
        sb.append("|");
        sb.append(bmBilldt);
        sb.append("|");
        sb.append(bmFromdt);
        sb.append("|");
        sb.append(bmTodt);
        sb.append("|");
        sb.append(bmDuedate);
        sb.append("|");
        sb.append(bmTotalAmount);
        sb.append("|");
        sb.append(bmTotalBalAmount);
        sb.append("|");
        sb.append(bmIntValue);
        sb.append("|");
        sb.append(bmTotalArrears);
        sb.append("|");
        sb.append(bmTotalOutstanding);
        sb.append("|");
        sb.append(bmTotalArrearsWithoutInt);
        sb.append("|");
        sb.append(bmTotalCumIntArrears);
        sb.append("|");
        sb.append(bmToatlInt);
        sb.append("|");
        sb.append(bmLastRcptamt);
        sb.append("|");
        sb.append(bmLastRcptdt);
        sb.append("|");
        sb.append(bmToatlRebate);
        sb.append("|");
        sb.append(bmPaidFlag);
        sb.append("|");
        sb.append(bmFyTotalArrears);
        sb.append("|");
        sb.append(bmFyTotalInt);
        sb.append("|");
        sb.append(flagJvPost);
        sb.append("|");
        sb.append(distDate);
        sb.append("|");
        sb.append(bmRemarks);
        sb.append("|");
        sb.append(bmPrintdate);
        sb.append("|");
        sb.append(arrearsBill);
        sb.append("|");
        sb.append(bmTotpayamtAftdue);
        sb.append("|");
        sb.append(bmIntamtAftdue);
        sb.append("|");
        sb.append(bmIntType);
        sb.append("|");
        sb.append(bmTrdPremis);
        sb.append("|");
        sb.append(bmCcnSize);
        sb.append("|");
        sb.append(bmCcnOwner);
        sb.append("|");
        sb.append(bmEntryFlag);
        sb.append("|");
        sb.append(bmIntChargedFlag);
        sb.append("|");
        sb.append(amendForBillId);
        sb.append("|");
        sb.append(bmMeteredccn);
        sb.append("|");
        sb.append(bmDuedate2);
        sb.append("|");
        sb.append(chShdIntChargedFlag);
        sb.append("|");
        sb.append(bmSecDepAmt);
        sb.append("|");
        sb.append(bmLastSecDepRcptno);
        sb.append("|");
        sb.append(bmLastSecDepRcptdt);
        sb.append("|");
        sb.append(wtV1);
        sb.append("|");
        sb.append(wtV2);
        sb.append("|");
        sb.append(wtV3);
        sb.append("|");
        sb.append(wtV4);
        sb.append("|");
        sb.append(wtN1);
        sb.append("|");
        sb.append(wtN2);
        sb.append("|");
        sb.append(wtN3);
        sb.append("|");
        sb.append(wtN4);
        sb.append("|");
        sb.append(wtN5);
        sb.append("|");
        sb.append(wtD1);
        sb.append("|");
        sb.append(wtD2);
        sb.append("|");
        sb.append(wtD3);
        sb.append("|");
        sb.append(wtLo1);
        sb.append("|");
        sb.append(wtLo2);
        sb.append("|");
        sb.append(wtLo3);
        sb.append("|");
        sb.append("|");
        sb.append(intFrom);
        sb.append("|");
        sb.append(intTo);
        sb.append("|");
        sb.append(fyiIntArrears);
        sb.append("|");
        sb.append(fyiIntPerc);
        return sb.toString();
    }

    /*public long getBmIdno() {
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
    }*/



    public Long getAmendForBillId() {
        return amendForBillId;
    }

    public String getBmYear() {
		return bmYear;
	}

	public void setBmYear(String bmYear) {
		this.bmYear = bmYear;
	}

	public void setAmendForBillId(final Long amendForBillId) {
        this.amendForBillId = amendForBillId;
    }

/*    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }*/

    public double getBmTotalBalAmount() {
        return bmTotalBalAmount;
    }

    public void setBmTotalBalAmount(final double bmTotalBalAmount) {
        this.bmTotalBalAmount = bmTotalBalAmount;
    }

    public double getBmIntValue() {
        return bmIntValue;
    }

    public void setBmIntValue(final double bmIntValue) {
        this.bmIntValue = bmIntValue;
    }

    public double getBmTotalArrears() {
        return bmTotalArrears;
    }

    public void setBmTotalArrears(final double bmTotalArrears) {
        this.bmTotalArrears = bmTotalArrears;
    }

    public double getBmTotalOutstanding() {
        return bmTotalOutstanding;
    }

    public void setBmTotalOutstanding(
            final double bmTotalOutstanding) {
        this.bmTotalOutstanding = bmTotalOutstanding;
    }

    public double getBmTotalArrearsWithoutInt() {
        return bmTotalArrearsWithoutInt;
    }

    public void setBmTotalArrearsWithoutInt(
            final double bmTotalArrearsWithoutInt) {
        this.bmTotalArrearsWithoutInt = bmTotalArrearsWithoutInt;
    }

    public double getBmTotalCumIntArrears() {
        return bmTotalCumIntArrears;
    }

    public void setBmTotalCumIntArrears(
            final double bmTotalCumIntArrears) {
        this.bmTotalCumIntArrears = bmTotalCumIntArrears;
    }

    public double getBmToatlInt() {
        return bmToatlInt;
    }

    public void setBmToatlInt(final double bmToatlInt) {
        this.bmToatlInt = bmToatlInt;
    }

    public double getBmLastRcptamt() {
        return bmLastRcptamt;
    }

    public void setBmLastRcptamt(final double bmLastRcptamt) {
        this.bmLastRcptamt = bmLastRcptamt;
    }

    public double getBmToatlRebate() {
        return bmToatlRebate;
    }

    public void setBmToatlRebate(final double bmToatlRebate) {
        this.bmToatlRebate = bmToatlRebate;
    }

    public double getBmFyTotalArrears() {
        return bmFyTotalArrears;
    }

    public void setBmFyTotalArrears(final double bmFyTotalArrears) {
        this.bmFyTotalArrears = bmFyTotalArrears;
    }

    public double getBmFyTotalInt() {
        return bmFyTotalInt;
    }

    public void setBmFyTotalInt(final double bmFyTotalInt) {
        this.bmFyTotalInt = bmFyTotalInt;
    }

    public double getBmTotpayamtAftdue() {
        return bmTotpayamtAftdue;
    }

    public void setBmTotpayamtAftdue(
            final double bmTotpayamtAftdue) {
        this.bmTotpayamtAftdue = bmTotpayamtAftdue;
    }

    public double getBmIntamtAftdue() {
        return bmIntamtAftdue;
    }

    public void setBmIntamtAftdue(final double bmIntamtAftdue) {
        this.bmIntamtAftdue = bmIntamtAftdue;
    }

    public double getBmSecDepAmt() {
        return bmSecDepAmt;
    }

    public void setBmSecDepAmt(final double bmSecDepAmt) {
        this.bmSecDepAmt = bmSecDepAmt;
    }

/*    public Long getUserId() {
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
    }*/


    public List<ViewBillDet> getViewBillDet() {
		return viewBillDet;
	}

	public void setViewBillDet(List<ViewBillDet> viewBillDet) {
		this.viewBillDet = viewBillDet;
	}

	/*
     * public TbCsmrInfoDTO getWaterMas() { return waterMas; } public void setWaterMas(TbCsmrInfoDTO waterMas) { this.waterMas =
     * waterMas; } public String getConnectionCategory() { return connectionCategory; } public void setConnectionCategory( String
     * connectionCategory) { this.connectionCategory = connectionCategory; } public String getTarriffCategory() { return
     * tarriffCategory; } public void setTarriffCategory(String tarriffCategory) { this.tarriffCategory = tarriffCategory; }
     * public String getConSize() { return conSize; } public void setConSize(String conSize) { this.conSize = conSize; }
     */
    public double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(final double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public double getArrearsTotal() {
        return arrearsTotal;
    }

    public void setArrearsTotal(final double arrearsTotal) {
        this.arrearsTotal = arrearsTotal;
    }

    public double getTaxtotal() {
        return taxtotal;
    }

    public void setTaxtotal(final double taxtotal) {
        this.taxtotal = taxtotal;
    }

    public String getAmountInwords() {
        return amountInwords;
    }

    public void setAmountInwords(final String amountInwords) {
        this.amountInwords = amountInwords;
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

    public double getExcessAmount() {
        return excessAmount;
    }

    public void setExcessAmount(final double excessAmount) {
        this.excessAmount = excessAmount;
    }

    public double getRebateAmount() {
        return rebateAmount;
    }

    public void setRebateAmount(final double rebateAmount) {
        this.rebateAmount = rebateAmount;
    }

    public Long getAssId() {
        return assId;
    }

    public void setAssId(Long assId) {
        this.assId = assId;
    }

    public String getPropNo() {
        return propNo;
    }

    public void setPropNo(String propNo) {
        this.propNo = propNo;
    }

    public String getInterstCalMethod() {
        return interstCalMethod;
    }

    public void setInterstCalMethod(String interstCalMethod) {
        this.interstCalMethod = interstCalMethod;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getUniqueReferenceNumber() {
        return uniqueReferenceNumber;
    }

    public void setUniqueReferenceNumber(String uniqueReferenceNumber) {
        this.uniqueReferenceNumber = uniqueReferenceNumber;
    }

    public long getDummyMasId() {
        return dummyMasId;
    }

    public void setDummyMasId(long dummyMasId) {
        this.dummyMasId = dummyMasId;
    }

    public double getTotalPenalty() {
        return totalPenalty;
    }

    public void setTotalPenalty(double totalPenalty) {
        this.totalPenalty = totalPenalty;
    }

    public double getBmActualArrearsAmt() {
        return bmActualArrearsAmt;
    }

    public void setBmActualArrearsAmt(double bmActualArrearsAmt) {
        this.bmActualArrearsAmt = bmActualArrearsAmt;
    }

    public String getGenFlag() {
        return genFlag;
    }

    public void setGenFlag(String genFlag) {
        this.genFlag = genFlag;
    }

    public String getBmGenDes() {
        return bmGenDes;
    }

    public void setBmGenDes(String bmGenDes) {
        this.bmGenDes = bmGenDes;
    }

    public String getCurrentBillFlag() {
        return currentBillFlag;
    }

    public void setCurrentBillFlag(String currentBillFlag) {
        this.currentBillFlag = currentBillFlag;
    }

	public Map<String, Double> getTaxWiseReabte() {
		return taxWiseReabte;
	}

	public void setTaxWiseReabte(Map<String, Double> taxWiseReabte) {
		this.taxWiseReabte = taxWiseReabte;
	}

	public long getBmIdno() {
		return bmIdno;
	}

	public void setBmIdno(long bmIdno) {
		this.bmIdno = bmIdno;
	}

	public Double getSurcharge() {
		return surcharge;
	}

	public void setSurcharge(Double surcharge) {
		this.surcharge = surcharge;
	}
    
	
}
