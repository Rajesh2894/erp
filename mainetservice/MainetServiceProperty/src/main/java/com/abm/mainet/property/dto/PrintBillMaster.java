package com.abm.mainet.property.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PrintBillMaster implements Serializable {

    private static final long serialVersionUID = -1584506515396533432L;

    private String nameV;
    private String addressV;
    private String orgName;
    private String signPath;
    private String billNoV;
    private String bottomNotePath;
    private String headingL;
    private String billDateV;
    private String propNoL;
    private String propNoV;
    private String ward1L;
    private String ward3L;
    private String ward5L;
    private String ward2L;
    private String ward4L;
    private String ward1V;
    private String ward2V;
    private String ward3V;
    private String ward4V;
    private String ward5V;
    private String footerName;
    private String sectionL;
    private String yearL;
    private String nameL;
    private String addressL;
    private String billNoL;
    private String taxnameL;
    private String arrearAmtL;
    private String currentAmtL;
    private String totAmtL;
    private String billDateL;
    private String annualRentAmtL;
    private String annualRentAmtV;
    private String paymentDateL;
    private String paymentDateV;
    private String employeeName;
    private String warningL;
    private String discountL;
    private String discountV;
    private String leftLogo;
    private String rightLogo;
    private String totalPayAmtL;
    private String deptNameL;
    private String oldPropNoV;
    private double totAlvV;
    private double totRvV;
    private double excessAdjTotalV;
    private double balExcessAdjTotalV;
    private double ajdAmtArrearsV;
    private double adjAmtCurrentV;
    private double adjTotalAmtV;
    private String amtToWordsV;
    private double cvV;
    private double totArrBill;
    private double totCurrBill;
    private double totTatAmt;
    private String orgType;
    private String billDueDateV;
    private double totPayableV;
    private String noOfDays;
    private String warrantOfficer;
    private String addressOfWarrantOff;
    private String fromDate;
    private String toDate;
    private String demandDueDateV;
    private String demandNoV;
    private String demandDateV;
    private String mobileL;
    private String mobileV;
    private String annualRentAmt;
    private String signatureMsgL;
    private double buildUpArea;
    private String usageType;
    private double totalAmount;
    private double receivedAmount;
    private String flatNo;
    private Long clusterNo;
    private Long buildingNo;
    private String illegalFlag;
    private String totalAmountInWords;
    private String usageTypeResidential;
    private String usageTypeNonResidential;
    private String usageTypeMixed;
    private String usageTypeNotStated;
    private double residentialRv;
    private double nonResidentialRv;
    private double mixedRv;
    private double notStatedRv;
    private String occupierName;
    private String billingMethod;
    private double firstHalfTotCurrBill;
    private double secondHalfTotCurrBill;
    private String firstHalfFromDate;
    private String firstHalfToDate;
    private String secondHalfFromDate;
    private String secondHalfToDate;
    private String firstHalfDueDate;
    private String secondHalfDueDate;
    private String natureOfProperty1;
    private String natureOfProperty2;
    private String natureOfProperty3;
    private String natureOfProperty4;
    private String natureOfProperty5;
    

    private List<PrintBillDetails> printBillDetails = new ArrayList<>(0);

    public String getNameV() {
        return nameV;
    }

    public void setNameV(String nameV) {
        this.nameV = nameV;
    }

    public String getAddressV() {
        return addressV;
    }

    public void setAddressV(String addressV) {
        this.addressV = addressV;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getSignPath() {
        return signPath;
    }

    public void setSignPath(String signPath) {
        this.signPath = signPath;
    }

    public String getBillNoV() {
        return billNoV;
    }

    public void setBillNoV(String billNoV) {
        this.billNoV = billNoV;
    }

    public String getBottomNotePath() {
        return bottomNotePath;
    }

    public void setBottomNotePath(String bottomNotePath) {
        this.bottomNotePath = bottomNotePath;
    }

    public String getHeadingL() {
        return headingL;
    }

    public void setHeadingL(String headingL) {
        this.headingL = headingL;
    }

    public String getBillDateV() {
        return billDateV;
    }

    public void setBillDateV(String billDateV) {
        this.billDateV = billDateV;
    }

    public String getPropNoL() {
        return propNoL;
    }

    public void setPropNoL(String propNoL) {
        this.propNoL = propNoL;
    }

    public String getPropNoV() {
        return propNoV;
    }

    public void setPropNoV(String propNoV) {
        this.propNoV = propNoV;
    }

    public String getWard1L() {
        return ward1L;
    }

    public void setWard1L(String ward1l) {
        ward1L = ward1l;
    }

    public String getWard3L() {
        return ward3L;
    }

    public void setWard3L(String ward3l) {
        ward3L = ward3l;
    }

    public String getWard5L() {
        return ward5L;
    }

    public void setWard5L(String ward5l) {
        ward5L = ward5l;
    }

    public String getWard2L() {
        return ward2L;
    }

    public void setWard2L(String ward2l) {
        ward2L = ward2l;
    }

    public String getWard4L() {
        return ward4L;
    }

    public void setWard4L(String ward4l) {
        ward4L = ward4l;
    }

    public String getWard1V() {
        return ward1V;
    }

    public void setWard1V(String ward1v) {
        ward1V = ward1v;
    }

    public String getWard2V() {
        return ward2V;
    }

    public void setWard2V(String ward2v) {
        ward2V = ward2v;
    }

    public String getWard3V() {
        return ward3V;
    }

    public void setWard3V(String ward3v) {
        ward3V = ward3v;
    }

    public String getWard4V() {
        return ward4V;
    }

    public void setWard4V(String ward4v) {
        ward4V = ward4v;
    }

    public String getWard5V() {
        return ward5V;
    }

    public void setWard5V(String ward5v) {
        ward5V = ward5v;
    }

    public String getFooterName() {
        return footerName;
    }

    public void setFooterName(String footerName) {
        this.footerName = footerName;
    }

    public String getSectionL() {
        return sectionL;
    }

    public void setSectionL(String sectionL) {
        this.sectionL = sectionL;
    }

    public String getYearL() {
        return yearL;
    }

    public void setYearL(String yearL) {
        this.yearL = yearL;
    }

    public String getNameL() {
        return nameL;
    }

    public void setNameL(String nameL) {
        this.nameL = nameL;
    }

    public String getAddressL() {
        return addressL;
    }

    public void setAddressL(String addressL) {
        this.addressL = addressL;
    }

    public String getBillNoL() {
        return billNoL;
    }

    public void setBillNoL(String billNoL) {
        this.billNoL = billNoL;
    }

    public String getTaxnameL() {
        return taxnameL;
    }

    public void setTaxnameL(String taxnameL) {
        this.taxnameL = taxnameL;
    }

    public String getArrearAmtL() {
        return arrearAmtL;
    }

    public void setArrearAmtL(String arrearAmtL) {
        this.arrearAmtL = arrearAmtL;
    }

    public String getCurrentAmtL() {
        return currentAmtL;
    }

    public void setCurrentAmtL(String currentAmtL) {
        this.currentAmtL = currentAmtL;
    }

    public String getTotAmtL() {
        return totAmtL;
    }

    public void setTotAmtL(String totAmtL) {
        this.totAmtL = totAmtL;
    }

    public String getBillDateL() {
        return billDateL;
    }

    public void setBillDateL(String billDateL) {
        this.billDateL = billDateL;
    }

    public String getAnnualRentAmtL() {
        return annualRentAmtL;
    }

    public void setAnnualRentAmtL(String annualRentAmtL) {
        this.annualRentAmtL = annualRentAmtL;
    }

    public String getAnnualRentAmtV() {
        return annualRentAmtV;
    }

    public void setAnnualRentAmtV(String annualRentAmtV) {
        this.annualRentAmtV = annualRentAmtV;
    }

    public String getPaymentDateL() {
        return paymentDateL;
    }

    public void setPaymentDateL(String paymentDateL) {
        this.paymentDateL = paymentDateL;
    }

    public String getPaymentDateV() {
        return paymentDateV;
    }

    public void setPaymentDateV(String paymentDateV) {
        this.paymentDateV = paymentDateV;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getWarningL() {
        return warningL;
    }

    public void setWarningL(String warningL) {
        this.warningL = warningL;
    }

    public String getDiscountL() {
        return discountL;
    }

    public void setDiscountL(String discountL) {
        this.discountL = discountL;
    }

    public String getDiscountV() {
        return discountV;
    }

    public void setDiscountV(String discountV) {
        this.discountV = discountV;
    }

    public String getLeftLogo() {
        return leftLogo;
    }

    public void setLeftLogo(String leftLogo) {
        this.leftLogo = leftLogo;
    }

    public String getRightLogo() {
        return rightLogo;
    }

    public void setRightLogo(String rightLogo) {
        this.rightLogo = rightLogo;
    }

    public String getTotalPayAmtL() {
        return totalPayAmtL;
    }

    public void setTotalPayAmtL(String totalPayAmtL) {
        this.totalPayAmtL = totalPayAmtL;
    }

    public List<PrintBillDetails> getPrintBillDetails() {
        return printBillDetails;
    }

    public void setPrintBillDetails(List<PrintBillDetails> printBillDetails) {
        this.printBillDetails = printBillDetails;
    }

    public String getDeptNameL() {
        return deptNameL;
    }

    public void setDeptNameL(String deptNameL) {
        this.deptNameL = deptNameL;
    }

    public String getOldPropNoV() {
        return oldPropNoV;
    }

    public void setOldPropNoV(String oldPropNoV) {
        this.oldPropNoV = oldPropNoV;
    }

    public double getTotAlvV() {
        return totAlvV;
    }

    public void setTotAlvV(double totAlvV) {
        this.totAlvV = totAlvV;
    }

    public double getTotRvV() {
        return totRvV;
    }

    public void setTotRvV(double totRvV) {
        this.totRvV = totRvV;
    }

    public double getExcessAdjTotalV() {
        return excessAdjTotalV;
    }

    public void setExcessAdjTotalV(double excessAdjTotalV) {
        this.excessAdjTotalV = excessAdjTotalV;
    }

    public double getBalExcessAdjTotalV() {
        return balExcessAdjTotalV;
    }

    public void setBalExcessAdjTotalV(double balExcessAdjTotalV) {
        this.balExcessAdjTotalV = balExcessAdjTotalV;
    }

    public double getAjdAmtArrearsV() {
        return ajdAmtArrearsV;
    }

    public void setAjdAmtArrearsV(double ajdAmtArrearsV) {
        this.ajdAmtArrearsV = ajdAmtArrearsV;
    }

    public double getAdjAmtCurrentV() {
        return adjAmtCurrentV;
    }

    public void setAdjAmtCurrentV(double adjAmtCurrentV) {
        this.adjAmtCurrentV = adjAmtCurrentV;
    }

    public double getAdjTotalAmtV() {
        return adjTotalAmtV;
    }

    public void setAdjTotalAmtV(double adjTotalAmtV) {
        this.adjTotalAmtV = adjTotalAmtV;
    }

    public String getAmtToWordsV() {
        return amtToWordsV;
    }

    public void setAmtToWordsV(String amtToWordsV) {
        this.amtToWordsV = amtToWordsV;
    }

    public double getCvV() {
        return cvV;
    }

    public void setCvV(double cvV) {
        this.cvV = cvV;
    }

    public double getTotArrBill() {
        return totArrBill;
    }

    public void setTotArrBill(double totArrBill) {
        this.totArrBill = totArrBill;
    }

    public double getTotCurrBill() {
        return totCurrBill;
    }

    public void setTotCurrBill(double totCurrBill) {
        this.totCurrBill = totCurrBill;
    }

    public double getTotTatAmt() {
        return totTatAmt;
    }

    public void setTotTatAmt(double totTatAmt) {
        this.totTatAmt = totTatAmt;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getBillDueDateV() {
        return billDueDateV;
    }

    public void setBillDueDateV(String billDueDateV) {
        this.billDueDateV = billDueDateV;
    }

    public String getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(String noOfDays) {
        this.noOfDays = noOfDays;
    }

    public String getWarrantOfficer() {
        return warrantOfficer;
    }

    public void setWarrantOfficer(String warrantOfficer) {
        this.warrantOfficer = warrantOfficer;
    }

    public String getAddressOfWarrantOff() {
        return addressOfWarrantOff;
    }

    public void setAddressOfWarrantOff(String addressOfWarrantOff) {
        this.addressOfWarrantOff = addressOfWarrantOff;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getDemandDueDateV() {
        return demandDueDateV;
    }

    public void setDemandDueDateV(String demandDueDateV) {
        this.demandDueDateV = demandDueDateV;
    }

    public String getDemandNoV() {
        return demandNoV;
    }

    public void setDemandNoV(String demandNoV) {
        this.demandNoV = demandNoV;
    }

    public String getDemandDateV() {
        return demandDateV;
    }

    public void setDemandDateV(String demandDateV) {
        this.demandDateV = demandDateV;
    }

    public String getMobileL() {
        return mobileL;
    }

    public void setMobileL(String mobileL) {
        this.mobileL = mobileL;
    }

    public String getMobileV() {
        return mobileV;
    }

    public void setMobileV(String mobileV) {
        this.mobileV = mobileV;
    }

    public String getAnnualRentAmt() {
        return annualRentAmt;
    }

    public void setAnnualRentAmt(String annualRentAmt) {
        this.annualRentAmt = annualRentAmt;
    }

    public String getSignatureMsgL() {
        return signatureMsgL;
    }

    public void setSignatureMsgL(String signatureMsgL) {
        this.signatureMsgL = signatureMsgL;
    }

    public double getTotPayableV() {
        return totPayableV;
    }

    public void setTotPayableV(double totPayableV) {
        this.totPayableV = totPayableV;
    }

	public double getBuildUpArea() {
		return buildUpArea;
	}

	public void setBuildUpArea(double buildUpArea) {
		this.buildUpArea = buildUpArea;
	}

	public String getUsageType() {
		return usageType;
	}

	public void setUsageType(String usageType) {
		this.usageType = usageType;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public double getReceivedAmount() {
		return receivedAmount;
	}

	public void setReceivedAmount(double receivedAmount) {
		this.receivedAmount = receivedAmount;
	}

	public String getFlatNo() {
		return flatNo;
	}

	public void setFlatNo(String flatNo) {
		this.flatNo = flatNo;
	}

	public Long getClusterNo() {
		return clusterNo;
	}

	public void setClusterNo(Long clusterNo) {
		this.clusterNo = clusterNo;
	}

	public Long getBuildingNo() {
		return buildingNo;
	}

	public void setBuildingNo(Long buildingNo) {
		this.buildingNo = buildingNo;
	}

	public String getIllegalFlag() {
		return illegalFlag;
	}

	public void setIllegalFlag(String illegalFlag) {
		this.illegalFlag = illegalFlag;
	}

	public String getTotalAmountInWords() {
		return totalAmountInWords;
	}

	public void setTotalAmountInWords(String totalAmountInWords) {
		this.totalAmountInWords = totalAmountInWords;
	}

	public String getUsageTypeResidential() {
		return usageTypeResidential;
	}

	public void setUsageTypeResidential(String usageTypeResidential) {
		this.usageTypeResidential = usageTypeResidential;
	}

	public String getUsageTypeNonResidential() {
		return usageTypeNonResidential;
	}

	public void setUsageTypeNonResidential(String usageTypeNonResidential) {
		this.usageTypeNonResidential = usageTypeNonResidential;
	}

	public String getUsageTypeMixed() {
		return usageTypeMixed;
	}

	public void setUsageTypeMixed(String usageTypeMixed) {
		this.usageTypeMixed = usageTypeMixed;
	}

	public String getUsageTypeNotStated() {
		return usageTypeNotStated;
	}

	public void setUsageTypeNotStated(String usageTypeNotStated) {
		this.usageTypeNotStated = usageTypeNotStated;
	}

	public double getResidentialRv() {
		return residentialRv;
	}

	public void setResidentialRv(double residentialRv) {
		this.residentialRv = residentialRv;
	}

	public double getNonResidentialRv() {
		return nonResidentialRv;
	}

	public void setNonResidentialRv(double nonResidentialRv) {
		this.nonResidentialRv = nonResidentialRv;
	}

	public double getMixedRv() {
		return mixedRv;
	}

	public void setMixedRv(double mixedRv) {
		this.mixedRv = mixedRv;
	}

	public double getNotStatedRv() {
		return notStatedRv;
	}

	public void setNotStatedRv(double notStatedRv) {
		this.notStatedRv = notStatedRv;
	}

	public String getOccupierName() {
		return occupierName;
	}

	public void setOccupierName(String occupierName) {
		this.occupierName = occupierName;
	}

	public String getBillingMethod() {
		return billingMethod;
	}

	public void setBillingMethod(String billingMethod) {
		this.billingMethod = billingMethod;
	}

	public double getFirstHalfTotCurrBill() {
		return firstHalfTotCurrBill;
	}

	public void setFirstHalfTotCurrBill(double firstHalfTotCurrBill) {
		this.firstHalfTotCurrBill = firstHalfTotCurrBill;
	}

	public double getSecondHalfTotCurrBill() {
		return secondHalfTotCurrBill;
	}

	public void setSecondHalfTotCurrBill(double secondHalfTotCurrBill) {
		this.secondHalfTotCurrBill = secondHalfTotCurrBill;
	}

	public String getFirstHalfFromDate() {
		return firstHalfFromDate;
	}

	public void setFirstHalfFromDate(String firstHalfFromDate) {
		this.firstHalfFromDate = firstHalfFromDate;
	}

	public String getFirstHalfToDate() {
		return firstHalfToDate;
	}

	public void setFirstHalfToDate(String firstHalfToDate) {
		this.firstHalfToDate = firstHalfToDate;
	}

	public String getSecondHalfFromDate() {
		return secondHalfFromDate;
	}

	public void setSecondHalfFromDate(String secondHalfFromDate) {
		this.secondHalfFromDate = secondHalfFromDate;
	}

	public String getSecondHalfToDate() {
		return secondHalfToDate;
	}

	public void setSecondHalfToDate(String secondHalfToDate) {
		this.secondHalfToDate = secondHalfToDate;
	}

	public String getFirstHalfDueDate() {
		return firstHalfDueDate;
	}

	public void setFirstHalfDueDate(String firstHalfDueDate) {
		this.firstHalfDueDate = firstHalfDueDate;
	}

	public String getSecondHalfDueDate() {
		return secondHalfDueDate;
	}

	public void setSecondHalfDueDate(String secondHalfDueDate) {
		this.secondHalfDueDate = secondHalfDueDate;
	}

	public String getNatureOfProperty1() {
		return natureOfProperty1;
	}

	public void setNatureOfProperty1(String natureOfProperty1) {
		this.natureOfProperty1 = natureOfProperty1;
	}

	public String getNatureOfProperty2() {
		return natureOfProperty2;
	}

	public void setNatureOfProperty2(String natureOfProperty2) {
		this.natureOfProperty2 = natureOfProperty2;
	}

	public String getNatureOfProperty3() {
		return natureOfProperty3;
	}

	public void setNatureOfProperty3(String natureOfProperty3) {
		this.natureOfProperty3 = natureOfProperty3;
	}

	public String getNatureOfProperty4() {
		return natureOfProperty4;
	}

	public void setNatureOfProperty4(String natureOfProperty4) {
		this.natureOfProperty4 = natureOfProperty4;
	}

	public String getNatureOfProperty5() {
		return natureOfProperty5;
	}

	public void setNatureOfProperty5(String natureOfProperty5) {
		this.natureOfProperty5 = natureOfProperty5;
	}

}
