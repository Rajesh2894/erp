package com.abm.mainet.water.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dto.TbBillDet;

/**
 * @author Rahul.Yadav
 *
 */
public class WaterBillPrintingDTO implements Serializable {

	private static final long serialVersionUID = -5577526454551084817L;
	@NotNull
	private long bmIdno;

	@NotNull
	private Long csIdn;

	@NotNull
	private Long bmYear;

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

	@NotNull
	private Long orgid;

	@NotNull
	private Long userId;

	@NotNull
	private int langId;

	@NotNull
	private Date lmoddate;

	private Date intFrom;

	private Date intTo;

	private BigDecimal fyiIntArrears;

	private BigDecimal fyiIntPerc;

	private List<TbBillDet> tbWtBillDet = new ArrayList<>(0);

	private TbCsmrInfoDTO waterMas = new TbCsmrInfoDTO();

	private TbMeterMas meterMas = new TbMeterMas();

	private MeterReadingDTO meterRead = new MeterReadingDTO();

	private String connectionCategory;

	private String tarriffCategory;

	private String conSize;

	private double grandTotal;

	private double arrearsTotal;

	private double taxtotal;

	private String amountInwords;

	private String pmPropno;

	private Long rmRcptid;

	private String bmNo;

	private double excessAmount;

	private double rebateAmount;

	private BigDecimal balanceExcessAmount;

	private int totalCount;

	private String bplFlag;

	private String securityDepositAmount;

	private String connectionType;
	
	private double totalYearlyBill;
	
	private double totalMonthlyBill;

	public BigDecimal getBalanceExcessAmount() {
		return balanceExcessAmount;
	}

	public void setBalanceExcessAmount(final BigDecimal balanceExcessAmount) {
		this.balanceExcessAmount = balanceExcessAmount;
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

	public double getBmTotalAmount() {
		return bmTotalAmount;
	}

	public void setBmTotalAmount(final double bmTotalAmount) {
		this.bmTotalAmount = bmTotalAmount;
	}

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

	public void setBmTotalOutstanding(final double bmTotalOutstanding) {
		this.bmTotalOutstanding = bmTotalOutstanding;
	}

	public double getBmTotalArrearsWithoutInt() {
		return bmTotalArrearsWithoutInt;
	}

	public void setBmTotalArrearsWithoutInt(final double bmTotalArrearsWithoutInt) {
		this.bmTotalArrearsWithoutInt = bmTotalArrearsWithoutInt;
	}

	public double getBmTotalCumIntArrears() {
		return bmTotalCumIntArrears;
	}

	public void setBmTotalCumIntArrears(final double bmTotalCumIntArrears) {
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

	public Date getBmLastRcptdt() {
		return bmLastRcptdt;
	}

	public void setBmLastRcptdt(final Date bmLastRcptdt) {
		this.bmLastRcptdt = bmLastRcptdt;
	}

	public double getBmToatlRebate() {
		return bmToatlRebate;
	}

	public void setBmToatlRebate(final double bmToatlRebate) {
		this.bmToatlRebate = bmToatlRebate;
	}

	public String getBmPaidFlag() {
		return bmPaidFlag;
	}

	public void setBmPaidFlag(final String bmPaidFlag) {
		this.bmPaidFlag = bmPaidFlag;
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

	public double getBmTotpayamtAftdue() {
		return bmTotpayamtAftdue;
	}

	public void setBmTotpayamtAftdue(final double bmTotpayamtAftdue) {
		this.bmTotpayamtAftdue = bmTotpayamtAftdue;
	}

	public double getBmIntamtAftdue() {
		return bmIntamtAftdue;
	}

	public void setBmIntamtAftdue(final double bmIntamtAftdue) {
		this.bmIntamtAftdue = bmIntamtAftdue;
	}

	public String getBmIntType() {
		return bmIntType;
	}

	public void setBmIntType(final String bmIntType) {
		this.bmIntType = bmIntType;
	}

	public BigDecimal getBmTrdPremis() {
		return bmTrdPremis;
	}

	public void setBmTrdPremis(final BigDecimal bmTrdPremis) {
		this.bmTrdPremis = bmTrdPremis;
	}

	public BigDecimal getBmCcnSize() {
		return bmCcnSize;
	}

	public void setBmCcnSize(final BigDecimal bmCcnSize) {
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

	public double getBmSecDepAmt() {
		return bmSecDepAmt;
	}

	public void setBmSecDepAmt(final double bmSecDepAmt) {
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

	public Long getOrgid() {
		return orgid;
	}

	public void setOrgid(final Long orgid) {
		this.orgid = orgid;
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

	public Date getLmoddate() {
		return lmoddate;
	}

	public void setLmoddate(final Date lmoddate) {
		this.lmoddate = lmoddate;
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

	public BigDecimal getFyiIntArrears() {
		return fyiIntArrears;
	}

	public void setFyiIntArrears(final BigDecimal fyiIntArrears) {
		this.fyiIntArrears = fyiIntArrears;
	}

	public BigDecimal getFyiIntPerc() {
		return fyiIntPerc;
	}

	public void setFyiIntPerc(final BigDecimal fyiIntPerc) {
		this.fyiIntPerc = fyiIntPerc;
	}

	public List<TbBillDet> getTbWtBillDet() {
		return tbWtBillDet;
	}

	public void setTbWtBillDet(final List<TbBillDet> tbWtBillDet) {
		this.tbWtBillDet = tbWtBillDet;
	}

	public TbCsmrInfoDTO getWaterMas() {
		return waterMas;
	}

	public void setWaterMas(final TbCsmrInfoDTO waterMas) {
		this.waterMas = waterMas;
	}

	public TbMeterMas getMeterMas() {
		return meterMas;
	}

	public void setMeterMas(final TbMeterMas meterMas) {
		this.meterMas = meterMas;
	}

	public MeterReadingDTO getMeterRead() {
		return meterRead;
	}

	public void setMeterRead(final MeterReadingDTO meterRead) {
		this.meterRead = meterRead;
	}

	public String getConnectionCategory() {
		return connectionCategory;
	}

	public void setConnectionCategory(final String connectionCategory) {
		this.connectionCategory = connectionCategory;
	}

	public String getTarriffCategory() {
		return tarriffCategory;
	}

	public void setTarriffCategory(final String tarriffCategory) {
		this.tarriffCategory = tarriffCategory;
	}

	public String getConSize() {
		return conSize;
	}

	public void setConSize(final String conSize) {
		this.conSize = conSize;
	}

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

	public String getPmPropno() {
		return pmPropno;
	}

	public void setPmPropno(final String pmPropno) {
		this.pmPropno = pmPropno;
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

	public String getSelectCheckBoxTemplet() {
		String datastring = MainetConstants.BLANK;
		datastring += "<input type='checkbox' id='" + bmIdno + "' class='checkall' name='status' checked />";
		return datastring;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(final int totalCount) {
		this.totalCount = totalCount;
	}

	public String getBplFlag() {
		return bplFlag;
	}

	public void setBplFlag(String bplFlag) {
		this.bplFlag = bplFlag;
	}

	public String getSecurityDepositAmount() {
		return securityDepositAmount;
	}

	public void setSecurityDepositAmount(String securityDepositAmount) {
		this.securityDepositAmount = securityDepositAmount;
	}

	public String getConnectionType() {
		return connectionType;
	}

	public void setConnectionType(String connectionType) {
		this.connectionType = connectionType;
	}

	public double getTotalYearlyBill() {
		return totalYearlyBill;
	}

	public void setTotalYearlyBill(double totalYearlyBill) {
		this.totalYearlyBill = totalYearlyBill;
	}

	public double getTotalMonthlyBill() {
		return totalMonthlyBill;
	}

	public void setTotalMonthlyBill(double totalMonthlyBill) {
		this.totalMonthlyBill = totalMonthlyBill;
	}

	
}
