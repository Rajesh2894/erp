package com.abm.mainet.cfc.challan.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.abm.mainet.common.dto.CommonChallanPayModeDTO;

/**
 * @author Rahul.Yadav
 *
 */
public class ChallanReceiptPrintDTO implements Serializable {

    private static final long serialVersionUID = 7275794732760793867L;
    private Long receiptId;
    private String receiptNo;
    private Long applicationNumber;
    private String receiptTime;
    private String receiptDate;
    private Long challanNo;
    private String orgName;
    private String orgNameMar;
    private String name;
    private String finYear;
    private String paymentMode;
    private String deptName;
    private String cfcRef;
    private Long counterRef;
    private String receivedFrom;
    private String subject;
    private String address;
    private String dwz1L;
    private String dwz2L;
    private String dwz3L;
    private String dwz4L;
    private String dwz5L;
    private String dwz1;
    private String dwz2;
    private String dwz3;
    private String dwz4;
    private String dwz5;
    private Long paymentModeValue;
    private double amount;
    private String amountInWords;
    private String ddOrPpDate;
    private String bankName;
    private Long ddOrPpnumber;
    private String referenceNo;
    private String date;
    private Double amountPayable;
    private double totalAmountPayable;
    // private Double receivedAmount;
    private double totalReceivedAmount;
    private String paymentText;
    private String receiverName;
    private String from_finYear;
    private String to_finYear;
    private String propNo_connNo_estateNo_L;
    private String propNo_connNo_estateN_V;
    private String old_propNo_connNo_L;
    private String old_propNo_connNo_V;
    private String usageType1_L;
    private String usageType1_V;
    private String usageType2_L;
    private String usageType2_V;
    private String usageType3_L;
    private String usageType3_V;
    private String usageType4_L;
    private String usageType4_V;
    private String usageType5_L;
    private String usageType5_V;
    private double rebateAmount;
    private double advOrExcessAmt;
    private double totalPayableArrear;
    private double totalPayableCurrent;
    private double totalReceivedArrear;
    private double totalreceivedCurrent;
    private String forTaxName;
    private long orgid;
    private long deptId;
    private String plotNo;
    private String rcptDate;
    private String ownerFullName;
    private Double pdRv;
    private Map<String, String> billDetails = null;// <bill number,bill date> for revenue receipt
    private Map<String, String> billYearDetails = null;// <bill number,bill year> for revenue receipt

    private List<ChallanReportDTO> paymentList = new ArrayList<>(0);
    private double earlyPaymentDiscount;
    
    private String manualReceiptDate;
    
    private String manualReceiptNo;
    
    private String deptShortCode;
    
    private List<String[]> wardZoneList = new ArrayList<String[]>();
    
    private String transferType;

    private List<CommonChallanPayModeDTO> multiPayModeList = new ArrayList<>(0);
    
    private String cfcCenter;
    private String cfcCounterNo;
    private String payeeName;
    private String userName;
    private String cfcDate;
    private String chkPmtMode;
    private String bankAccountId;
    private Long rmReceiptcategoryId;
    private String accountHead;
    private String envFlag;
    
    private String loiNo;
    
    private String narration;
    
    private Date recptCreatedDate;
    private Long recptCreatedBy;
    
    private String serviceCodeflag;
    
    private String occupierName;
    
    private String serviceCode;
    
    private String cfcColCenterNo;
    private String cfcColCounterNo;
    private String pushToPayErrMsg;
    private Double balanceAmount;
    private String newHouseNo;
    private double applicationFee;
	private String flatNo;
	private String rcptTime;
	
	private String receiptType;
	
	 private String serviceName;
 
	 private String parshadWard1;
	 private String parshadWard2;
	 private String parshadWard3;
	 private String parshadWard4;
	 private String parshadWard5;
	 private String mobileNumber;
	 private String locEng;
	 private String locReg;
	 private Long serviceId;
	 private Long fieldId;
	 private Date rmDate;
	 private String gstNo;

	public String getAccountHead() {
		return accountHead;
	}

	public void setAccountHead(String accountHead) {
		this.accountHead = accountHead;
	}

	public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(final String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public Long getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(final Long applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    public String getReceiptTime() {
        return receiptTime;
    }

    public void setReceiptTime(final String receiptTime) {
        this.receiptTime = receiptTime;
    }

    public String getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(final String receiptDate) {
        this.receiptDate = receiptDate;
    }

    public Long getChallanNo() {
        return challanNo;
    }

    public void setChallanNo(final Long challanNo) {
        this.challanNo = challanNo;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(final String orgName) {
        this.orgName = orgName;
    }

    public String getOrgNameMar() {
        return orgNameMar;
    }

    public void setOrgNameMar(String orgNameMar) {
        this.orgNameMar = orgNameMar;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getFinYear() {
        return finYear;
    }

    public void setFinYear(final String finYear) {
        this.finYear = finYear;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(final String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(final String deptName) {
        this.deptName = deptName;
    }

    public String getCfcRef() {
        return cfcRef;
    }

    public void setCfcRef(final String cfcRef) {
        this.cfcRef = cfcRef;
    }

    public Long getCounterRef() {
        return counterRef;
    }

    public void setCounterRef(final Long counterRef) {
        this.counterRef = counterRef;
    }

    public String getReceivedFrom() {
        return receivedFrom;
    }

    public void setReceivedFrom(final String receivedFrom) {
        this.receivedFrom = receivedFrom;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public Long getPaymentModeValue() {
        return paymentModeValue;
    }

    public void setPaymentModeValue(final Long paymentModeValue) {
        this.paymentModeValue = paymentModeValue;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(final double amount) {
        this.amount = amount;
    }

    public String getAmountInWords() {
        return amountInWords;
    }

    public void setAmountInWords(final String amountInWords) {
        this.amountInWords = amountInWords;
    }

    public String getDdOrPpDate() {
        return ddOrPpDate;
    }

    public void setDdOrPpDate(final String ddOrPpDate) {
        this.ddOrPpDate = ddOrPpDate;
    }

    public Long getDdOrPpnumber() {
        return ddOrPpnumber;
    }

    public void setDdOrPpnumber(final Long ddOrPpnumber) {
        this.ddOrPpnumber = ddOrPpnumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(final String bankName) {
        this.bankName = bankName;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(final String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(final String date) {
        this.date = date;
    }

    public Double getAmountPayable() {
        return amountPayable;
    }

    public void setAmountPayable(final Double amountPayable) {
        this.amountPayable = amountPayable;
    }

    public double getTotalAmountPayable() {
        return totalAmountPayable;
    }

    public void setTotalAmountPayable(final double totalAmountPayable) {
        this.totalAmountPayable = totalAmountPayable;
    }

    public double getTotalReceivedAmount() {
        return totalReceivedAmount;
    }

    public void setTotalReceivedAmount(final double totalReceivedAmount) {
        this.totalReceivedAmount = totalReceivedAmount;
    }

    public String getPaymentText() {
        return paymentText;
    }

    public void setPaymentText(final String paymentText) {
        this.paymentText = paymentText;
    }

    public List<ChallanReportDTO> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(final List<ChallanReportDTO> paymentList) {
        this.paymentList = paymentList;
    }

    public String getDwz1() {
        return dwz1;
    }

    public void setDwz1(final String dwz1) {
        this.dwz1 = dwz1;
    }

    public String getDwz2() {
        return dwz2;
    }

    public void setDwz2(final String dwz2) {
        this.dwz2 = dwz2;
    }

    public String getDwz3() {
        return dwz3;
    }

    public void setDwz3(final String dwz3) {
        this.dwz3 = dwz3;
    }

    public String getDwz4() {
        return dwz4;
    }

    public void setDwz4(final String dwz4) {
        this.dwz4 = dwz4;
    }

    public String getDwz5() {
        return dwz5;
    }

    public void setDwz5(final String dwz5) {
        this.dwz5 = dwz5;
    }

    public Long getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(Long receiptId) {
        this.receiptId = receiptId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getPropNo_connNo_estateNo_L() {
        return propNo_connNo_estateNo_L;
    }

    public void setPropNo_connNo_estateNo_L(String propNo_connNo_estateNo_L) {
        this.propNo_connNo_estateNo_L = propNo_connNo_estateNo_L;
    }

    public String getPropNo_connNo_estateN_V() {
        return propNo_connNo_estateN_V;
    }

    public void setPropNo_connNo_estateN_V(String propNo_connNo_estateN_V) {
        this.propNo_connNo_estateN_V = propNo_connNo_estateN_V;
    }

    public String getOld_propNo_connNo_L() {
        return old_propNo_connNo_L;
    }

    public void setOld_propNo_connNo_L(String old_propNo_connNo_L) {
        this.old_propNo_connNo_L = old_propNo_connNo_L;
    }

    public String getOld_propNo_connNo_V() {
        return old_propNo_connNo_V;
    }

    public void setOld_propNo_connNo_V(String old_propNo_connNo_V) {
        this.old_propNo_connNo_V = old_propNo_connNo_V;
    }

    public String getUsageType1_L() {
        return usageType1_L;
    }

    public void setUsageType1_L(String usageType1_L) {
        this.usageType1_L = usageType1_L;
    }

    public String getUsageType1_V() {
        return usageType1_V;
    }

    public void setUsageType1_V(String usageType1_V) {
        this.usageType1_V = usageType1_V;
    }

    public String getUsageType2_L() {
        return usageType2_L;
    }

    public void setUsageType2_L(String usageType2_L) {
        this.usageType2_L = usageType2_L;
    }

    public String getUsageType2_V() {
        return usageType2_V;
    }

    public void setUsageType2_V(String usageType2_V) {
        this.usageType2_V = usageType2_V;
    }

    public String getUsageType3_L() {
        return usageType3_L;
    }

    public void setUsageType3_L(String usageType3_L) {
        this.usageType3_L = usageType3_L;
    }

    public String getUsageType3_V() {
        return usageType3_V;
    }

    public void setUsageType3_V(String usageType3_V) {
        this.usageType3_V = usageType3_V;
    }

    public String getUsageType4_L() {
        return usageType4_L;
    }

    public void setUsageType4_L(String usageType4_L) {
        this.usageType4_L = usageType4_L;
    }

    public String getUsageType4_V() {
        return usageType4_V;
    }

    public void setUsageType4_V(String usageType4_V) {
        this.usageType4_V = usageType4_V;
    }

    public String getUsageType5_L() {
        return usageType5_L;
    }

    public void setUsageType5_L(String usageType5_L) {
        this.usageType5_L = usageType5_L;
    }

    public String getUsageType5_V() {
        return usageType5_V;
    }

    public void setUsageType5_V(String usageType5_V) {
        this.usageType5_V = usageType5_V;
    }

    public String getDwz1L() {
        return dwz1L;
    }

    public void setDwz1L(String dwz1l) {
        dwz1L = dwz1l;
    }

    public String getDwz2L() {
        return dwz2L;
    }

    public void setDwz2L(String dwz2l) {
        dwz2L = dwz2l;
    }

    public String getDwz3L() {
        return dwz3L;
    }

    public void setDwz3L(String dwz3l) {
        dwz3L = dwz3l;
    }

    public String getDwz5L() {
        return dwz5L;
    }

    public void setDwz5L(String dwz5l) {
        dwz5L = dwz5l;
    }

    public String getFrom_finYear() {
        return from_finYear;
    }

    public void setFrom_finYear(String from_finYear) {
        this.from_finYear = from_finYear;
    }

    public String getTo_finYear() {
        return to_finYear;
    }

    public void setTo_finYear(String to_finYear) {
        this.to_finYear = to_finYear;
    }

    public String getForTaxName() {
        return forTaxName;
    }

    public void setForTaxName(String forTaxName) {
        this.forTaxName = forTaxName;
    }

    public String getDwz4L() {
        return dwz4L;
    }

    public void setDwz4L(String dwz4l) {
        dwz4L = dwz4l;
    }

    public Map<String, String> getBillDetails() {
        return billDetails;
    }

    public void setBillDetails(Map<String, String> billDetails) {
        this.billDetails = billDetails;
    }

    public double getRebateAmount() {
        return rebateAmount;
    }

    public void setRebateAmount(double rebateAmount) {
        this.rebateAmount = rebateAmount;
    }

    public double getAdvOrExcessAmt() {
        return advOrExcessAmt;
    }

    public void setAdvOrExcessAmt(double advOrExcessAmt) {
        this.advOrExcessAmt = advOrExcessAmt;
    }

    public double getTotalPayableArrear() {
        return totalPayableArrear;
    }

    public void setTotalPayableArrear(double totalPayableArrear) {
        this.totalPayableArrear = totalPayableArrear;
    }

    public double getTotalPayableCurrent() {
        return totalPayableCurrent;
    }

    public void setTotalPayableCurrent(double totalPayableCurrent) {
        this.totalPayableCurrent = totalPayableCurrent;
    }

    public double getTotalReceivedArrear() {
        return totalReceivedArrear;
    }

    public void setTotalReceivedArrear(double totalReceivedArrear) {
        this.totalReceivedArrear = totalReceivedArrear;
    }

    public double getTotalreceivedCurrent() {
        return totalreceivedCurrent;
    }

    public void setTotalreceivedCurrent(double totalreceivedCurrent) {
        this.totalreceivedCurrent = totalreceivedCurrent;
    }

    public long getOrgid() {
        return orgid;
    }

    public void setOrgid(long orgid) {
        this.orgid = orgid;
    }

    public long getDeptId() {
        return deptId;
    }

    public void setDeptId(long deptId) {
        this.deptId = deptId;
    }

    public String getOwnerFullName() {
        return ownerFullName;
    }

    public void setOwnerFullName(String ownerFullName) {
        this.ownerFullName = ownerFullName;
    }

    public Double getPdRv() {
        return pdRv;
    }

    public void setPdRv(Double pdRv) {
        this.pdRv = pdRv;
    }

    public String getPlotNo() {
        return plotNo;
    }

    public void setPlotNo(String plotNo) {
        this.plotNo = plotNo;
    }

    public String getRcptDate() {
        return rcptDate;
    }

    public void setRcptDate(String rcptDate) {
        this.rcptDate = rcptDate;
    }

    public double getEarlyPaymentDiscount() {
        return earlyPaymentDiscount;
    }

    public void setEarlyPaymentDiscount(double earlyPaymentDiscount) {
        this.earlyPaymentDiscount = earlyPaymentDiscount;
    }
	

	public String getManualReceiptDate() {
		return manualReceiptDate;
	}

	public void setManualReceiptDate(String manualReceiptDate) {
		this.manualReceiptDate = manualReceiptDate;
	}

	public String getManualReceiptNo() {
		return manualReceiptNo;
	}

	public void setManualReceiptNo(String manualReceiptNo) {
		this.manualReceiptNo = manualReceiptNo;
	}

	public String getDeptShortCode() {
		return deptShortCode;
	}

	public void setDeptShortCode(String deptShortCode) {
		this.deptShortCode = deptShortCode;
	}

	public List<String[]> getWardZoneList() {
		return wardZoneList;
	}

	public void setWardZoneList(List<String[]> wardZoneList) {
		this.wardZoneList = wardZoneList;
	}

	public String getTransferType() {
		return transferType;
	}

	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}

	public List<CommonChallanPayModeDTO> getMultiPayModeList() {
		return multiPayModeList;
	}

	public void setMultiPayModeList(List<CommonChallanPayModeDTO> multiPayModeList) {
		this.multiPayModeList = multiPayModeList;
	}

	public String getCfcCenter() {
		return cfcCenter;
	}

	public void setCfcCenter(String cfcCenter) {
		this.cfcCenter = cfcCenter;
	}

	public String getCfcCounterNo() {
		return cfcCounterNo;
	}

	public void setCfcCounterNo(String cfcCounterNo) {
		this.cfcCounterNo = cfcCounterNo;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCfcDate() {
		return cfcDate;
	}

	public void setCfcDate(String cfcDate) {
		this.cfcDate = cfcDate;
	}

	public String getChkPmtMode() {
		return chkPmtMode;
	}

	public void setChkPmtMode(String chkPmtMode) {
		this.chkPmtMode = chkPmtMode;
	}

	public String getBankAccountId() {
		return bankAccountId;
	}

	public void setBankAccountId(String bankAccountId) {
		this.bankAccountId = bankAccountId;
	}

	public Long getRmReceiptcategoryId() {
		return rmReceiptcategoryId;
	}

	public void setRmReceiptcategoryId(Long rmReceiptcategoryId) {
		this.rmReceiptcategoryId = rmReceiptcategoryId;
	}

	public String getEnvFlag() {
		return envFlag;
	}

	public void setEnvFlag(String envFlag) {
		this.envFlag = envFlag;
	}
	public String getLoiNo() {
		return loiNo;
	}

	public void setLoiNo(String loiNo) {
		this.loiNo = loiNo;
	}

	public String getNarration() {
		return narration;
	}

	public void setNarration(String narration) {
		this.narration = narration;
	}

	public Map<String, String> getBillYearDetails() {
		return billYearDetails;
	}

	public void setBillYearDetails(Map<String, String> billYearDetails) {
		this.billYearDetails = billYearDetails;
	}

	public Date getRecptCreatedDate() {
		return recptCreatedDate;
	}

	public void setRecptCreatedDate(Date recptCreatedDate) {
		this.recptCreatedDate = recptCreatedDate;
	}

	public Long getRecptCreatedBy() {
		return recptCreatedBy;
	}

	public void setRecptCreatedBy(Long recptCreatedBy) {
		this.recptCreatedBy = recptCreatedBy;
	}

	public String getServiceCodeflag() {
		return serviceCodeflag;
	}

	public void setServiceCodeflag(String serviceCodeflag) {
		this.serviceCodeflag = serviceCodeflag;
	}

	public String getOccupierName() {
		return occupierName;
	}

	public void setOccupierName(String occupierName) {
		this.occupierName = occupierName;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getCfcColCenterNo() {
		return cfcColCenterNo;
	}

	public void setCfcColCenterNo(String cfcColCenterNo) {
		this.cfcColCenterNo = cfcColCenterNo;
	}

	public String getCfcColCounterNo() {
		return cfcColCounterNo;
	}

	public void setCfcColCounterNo(String cfcColCounterNo) {
		this.cfcColCounterNo = cfcColCounterNo;
	}

	public String getPushToPayErrMsg() {
		return pushToPayErrMsg;
	}

	public void setPushToPayErrMsg(String pushToPayErrMsg) {
		this.pushToPayErrMsg = pushToPayErrMsg;
	}

	public Double getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(Double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public String getNewHouseNo() {
		return newHouseNo;
	}

	public void setNewHouseNo(String newHouseNo) {
		this.newHouseNo = newHouseNo;
	}

	public double getApplicationFee() {
		return applicationFee;
	}

	public void setApplicationFee(double applicationFee) {
		this.applicationFee = applicationFee;
	}
	
	public String getFlatNo() {
		return flatNo;
	}

	public void setFlatNo(String flatNo) {
		this.flatNo = flatNo;
	}

	public String getRcptTime() {
		return rcptTime;
	}

	public void setRcptTime(String rcptTime) {
		this.rcptTime = rcptTime;
	}

	public String getReceiptType() {
		return receiptType;
	}

	public void setReceiptType(String receiptType) {
		this.receiptType = receiptType;
	}
	
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getParshadWard1() {
		return parshadWard1;
	}

	public void setParshadWard1(String parshadWard1) {
		this.parshadWard1 = parshadWard1;
	}

	public String getParshadWard2() {
		return parshadWard2;
	}

	public void setParshadWard2(String parshadWard2) {
		this.parshadWard2 = parshadWard2;
	}

	public String getParshadWard3() {
		return parshadWard3;
	}

	public void setParshadWard3(String parshadWard3) {
		this.parshadWard3 = parshadWard3;
	}

	public String getParshadWard4() {
		return parshadWard4;
	}

	public void setParshadWard4(String parshadWard4) {
		this.parshadWard4 = parshadWard4;
	}

	public String getParshadWard5() {
		return parshadWard5;
	}

	public void setParshadWard5(String parshadWard5) {
		this.parshadWard5 = parshadWard5;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getLocEng() {
		return locEng;
	}

	public void setLocEng(String locEng) {
		this.locEng = locEng;
	}

	public String getLocReg() {
		return locReg;
	}

	public void setLocReg(String locReg) {
		this.locReg = locReg;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public Long getFieldId() {
		return fieldId;
	}

	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}

	public Date getRmDate() {
		return rmDate;
	}

	public void setRmDate(Date rmDate) {
		this.rmDate = rmDate;
	}

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}
}