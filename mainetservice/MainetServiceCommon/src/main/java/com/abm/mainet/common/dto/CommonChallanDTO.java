package com.abm.mainet.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.abm.mainet.common.integration.dto.DocumentDetailsVO;

@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
public class CommonChallanDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 582860496426627080L;

    private String challanNo;

    private Long serviceId;

    private long oflPaymentMode;

    private Long applNo;

    private String ddNo;

    private Date ddDate;

    private String poNo;

    private Date poDate;

    private Long bankaAccId;

    private String isDeleted;

    private Long orgId;

    private Long userId;

    private int langId;

    private Date lmodDate;

    private String lgIpMac;

    private Long bmBankAccountId;							// holds
    // Account
    // No

    private Long payModeIn;									// holds
    // Payment
    // Mode

    private String bmDrawOn;									// Banks IFSC Code

    private Long bmChqDDNo;									// hold
    // cheque
    // No

    private Date bmChqDDDate;								// hold
    // cheque
    // date

    private String amountToPay;

    private Double amountToShow;

    private String hidebmChqDDNo;

    private String hidebmChqDDDate;

    private Long deptId;

    private String faYearId;

    private String challanServiceType;

    private Date challanValidDate;

    private Map<Long, Double> feeIds = new HashMap<>(
            0);
    private Map<Long, Long> billDetIds = null;

    private Date finYearStartDate;

    private Date finYearEndDate;

    private String applicantAddress;

    private boolean documentUploaded;

    private List<String> bankData = new ArrayList<>(
            0);

    private String loiNo;

    private String mobileNumber;

    private String emailId;

    private String applicantName;

    private String offlinePaymentText;

    private String onlineOfflineCheck;

    private Long cbBankId;									// holds Bank Id

    private String uniquePrimaryId;

    private String paymentCategory;

    private Long taskId;

    private Long empType;

    private Long loggedLocId;

    private Long tdpPrefixId;

    private String narration; // used for manual receipt entry

    private String manualReceiptNo; // used for manual receipt entry

    private Date manualReeiptDate; // used for manual receipt entry

    private List<DocumentDetailsVO> documentList;// used for manual receipt entry-- upload receipt through mobile

    private String paymentStatus;

    private String objectionNumber;

    private Map<Long, Double> chargesMap = new HashMap<>();

    private Double charges;

    private Long vendorId;

    private Long receiptcategoryId;

    private double demandLevelRebate;

    private String referenceNo;

    private String usageType;

    private String propNoConnNoEstateNoL;

    private String propNoConnNoEstateNoV;

    private String deptCode;

    private String serviceCode;

    private String serviceName;
    private String serviceNameMar;

    private Date fromedate;

    private Date toDate;

    String propName;

    private Double pdRv;

    private String applicantFullName;

    private String plotNo;

    private Map<String, String> billDetails = new LinkedHashMap<>();// <bill number,bill date> for revenue receipt
    
    private Map<String, String> billYearDetails = new LinkedHashMap<>();// <bill number,bill date> for revenue receipt

    private List<BillReceiptPrintingDTO> printDtoList = null;

    private ConcurrentHashMap<Long, Long> supplimentryBillIdMap = new ConcurrentHashMap<>();

    private WardZoneBlockDTO dwzDTO = new WardZoneBlockDTO();

    private Long pgRefId;// P.K of tb_onl_tran_mas_service

    private String transferType;

    private String workflowEnable;

    private String flatNo;

    private String licNo;

	/*
	 * private List<CommonChallanPayModeDTO> multiModeList = new
	 * CopyOnWriteArrayList<CommonChallanPayModeDTO>();
	 */

    private CommonChallanPayModeDTO rebateMode;

    private Double rebateAmt; // rebate amount or exempted amount
    private String fromDateStr;

    private String toDateStr;
    private String orgName;
    private String orgNameMar;
    private String orgAddress;
    private String orgAddressMar;
    private Long slaSmDuration;
    private String time;
    private String cfcCenter;
    private String cfcCounterNo;
    private String payeeName;
    private String transferOwnerFullName;
    private String transferInitiatedDate;
    private String transferDate;
    private String regNo;
    private String transferAddress;
    private String certificateNo;
    private String parentPropNo;
    private List<DocumentDetailsVO> postalCardDocList;
    private String uniquePropertyId;
    private String occupierName;
    private String pushToPayErrMsg;
    //for storing POS payment mode
    private String posPayMode;
    //for before payment done or not
    private boolean isPosPayApplicable;
    private String posTxnId;
    private String newHouseNo;
    private Date referenceDate;
	private String registrationYear;
    private String registrationNo;
    
    private String parshadWard1;
	 private String parshadWard2;
	 private String parshadWard3;
	 private String parshadWard4;
	 private String parshadWard5;
	 private String tranRefNumber;
	 private Date tranRefDate;
	 
	 private Long assParshadWard1;
	 private Long assParshadWard2;
	 private Long assParshadWard3;
	 private Long assParshadWard4;
	 private Long assParshadWard5;
	 
	 private String rdV2;
	 private String rdV3;
	 private String rdV4;
	 private String rdV5;
	 
	 private String gstNo; 
    
    public Long getEmpType() {
        return empType;
    }

    public Double getPdRv() {
        return pdRv;
    }

    public void setPdRv(Double pdRv) {
        this.pdRv = pdRv;
    }

    public void setEmpType(Long empType) {
        this.empType = empType;
    }

    public String getChallanNo() {
        return challanNo;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public void setChallanNo(final String challanNo) {
        this.challanNo = challanNo;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }

    public long getOflPaymentMode() {
        return oflPaymentMode;
    }

    public void setOflPaymentMode(final long oflPaymentMode) {
        this.oflPaymentMode = oflPaymentMode;
    }

    public Long getApplNo() {
        return applNo;
    }

    public void setApplNo(final Long applNo) {
        this.applNo = applNo;
    }

    public String getDdNo() {
        return ddNo;
    }

    public void setDdNo(final String ddNo) {
        this.ddNo = ddNo;
    }

    public Date getDdDate() {
        return ddDate;
    }

    public void setDdDate(final Date ddDate) {
        this.ddDate = ddDate;
    }

    public String getPoNo() {
        return poNo;
    }

    public void setPoNo(final String poNo) {
        this.poNo = poNo;
    }

    public Date getPoDate() {
        return poDate;
    }

    public void setPoDate(final Date poDate) {
        this.poDate = poDate;
    }

    public Long getBankaAccId() {
        return bankaAccId;
    }

    public void setBankaAccId(final Long bankaAccId) {
        this.bankaAccId = bankaAccId;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(final String isDeleted) {
        this.isDeleted = isDeleted;
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

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(final String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(final String emailId) {
        this.emailId = emailId;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(final String applicantName) {
        this.applicantName = applicantName;
    }

    public String getOfflinePaymentText() {
        return offlinePaymentText;
    }

    public void setOfflinePaymentText(
            final String offlinePaymentText) {
        this.offlinePaymentText = offlinePaymentText;
    }

    public String getOnlineOfflineCheck() {
        return onlineOfflineCheck;
    }

    public void setOnlineOfflineCheck(
            final String onlineOfflineCheck) {
        this.onlineOfflineCheck = onlineOfflineCheck;
    }

    public Long getCbBankId() {
        return cbBankId;
    }

    public void setCbBankId(final Long cbBankId) {
        this.cbBankId = cbBankId;
    }

    public Long getBmBankAccountId() {
        return bmBankAccountId;
    }

    public void setBmBankAccountId(final Long bmBankAccountId) {
        this.bmBankAccountId = bmBankAccountId;
    }

    public Long getPayModeIn() {
        return payModeIn;
    }

    public void setPayModeIn(final Long payModeIn) {
        this.payModeIn = payModeIn;
    }

    public String getBmDrawOn() {
        return bmDrawOn;
    }

    public void setBmDrawOn(final String bmDrawOn) {
        this.bmDrawOn = bmDrawOn;
    }

    public Long getBmChqDDNo() {
        return bmChqDDNo;
    }

    public void setBmChqDDNo(final Long bmChqDDNo) {
        this.bmChqDDNo = bmChqDDNo;
    }

    public Date getBmChqDDDate() {
        return bmChqDDDate;
    }

    public void setBmChqDDDate(final Date bmChqDDDate) {
        this.bmChqDDDate = bmChqDDDate;
    }

    public String getAmountToPay() {
        return amountToPay;
    }

    public void setAmountToPay(final String amountToPay) {
        this.amountToPay = amountToPay;
    }

    public String getHidebmChqDDNo() {
        return hidebmChqDDNo;
    }

    public void setHidebmChqDDNo(final String hidebmChqDDNo) {
        this.hidebmChqDDNo = hidebmChqDDNo;
    }

    public String getHidebmChqDDDate() {
        return hidebmChqDDDate;
    }

    public void setHidebmChqDDDate(final String hidebmChqDDDate) {
        this.hidebmChqDDDate = hidebmChqDDDate;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(final Long deptId) {
        this.deptId = deptId;
    }

    public String getChallanServiceType() {
        return challanServiceType;
    }

    public void setChallanServiceType(
            final String challanServiceType) {
        this.challanServiceType = challanServiceType;
    }

    public Date getChallanValidDate() {
        return challanValidDate;
    }

    public void setChallanValidDate(final Date challanValidDate) {
        this.challanValidDate = challanValidDate;
    }

    public Map<Long, Double> getFeeIds() {
        return feeIds;
    }

    public void setFeeIds(final Map<Long, Double> feeIds) {
        this.feeIds = feeIds;
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

    public String getFaYearId() {
        return faYearId;
    }

    public void setFaYearId(final String faYearId) {
        this.faYearId = faYearId;
    }

    public Date getFinYearStartDate() {
        return finYearStartDate;
    }

    public void setFinYearStartDate(final Date finYearStartDate) {
        this.finYearStartDate = finYearStartDate;
    }

    public Date getFinYearEndDate() {
        return finYearEndDate;
    }

    public void setFinYearEndDate(final Date finYearEndDate) {
        this.finYearEndDate = finYearEndDate;
    }

    public String getApplicantAddress() {
        return applicantAddress;
    }

    public void setApplicantAddress(final String applicantAddress) {
        this.applicantAddress = applicantAddress;
    }

    public boolean isDocumentUploaded() {
        return documentUploaded;
    }

    public void setDocumentUploaded(final boolean documentUploaded) {
        this.documentUploaded = documentUploaded;
    }

    public List<String> getBankData() {
        return bankData;
    }

    public void setBankData(final List<String> bankData) {
        this.bankData = bankData;
    }

    public String getLoiNo() {
        return loiNo;
    }

    public void setLoiNo(final String loiNo) {
        this.loiNo = loiNo;
    }

    public Double getAmountToShow() {
        return amountToShow;
    }

    public void setAmountToShow(final Double amountToShow) {
        this.amountToShow = amountToShow;
    }

    public Map<Long, Long> getBillDetIds() {
        return billDetIds;
    }

    public void setBillDetIds(final Map<Long, Long> billDetIds) {
        this.billDetIds = billDetIds;
    }

    public String getUniquePrimaryId() {
        return uniquePrimaryId;
    }

    public void setUniquePrimaryId(final String uniquePrimaryId) {
        this.uniquePrimaryId = uniquePrimaryId;
    }

    public String getPaymentCategory() {
        return paymentCategory;
    }

    public void setPaymentCategory(final String paymentCategory) {
        this.paymentCategory = paymentCategory;
    }

    public Long getLoggedLocId() {
        return loggedLocId;
    }

    public void setLoggedLocId(Long loggedLocId) {
        this.loggedLocId = loggedLocId;
    }

    public Long getTdpPrefixId() {
        return tdpPrefixId;
    }

    public void setTdpPrefixId(Long tdpPrefixId) {
        this.tdpPrefixId = tdpPrefixId;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getManualReceiptNo() {
        return manualReceiptNo;
    }

    public void setManualReceiptNo(String manualReceiptNo) {
        this.manualReceiptNo = manualReceiptNo;
    }

    public Date getManualReeiptDate() {
        return manualReeiptDate;
    }

    public void setManualReeiptDate(Date manualReeiptDate) {
        this.manualReeiptDate = manualReeiptDate;
    }

    public List<DocumentDetailsVO> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<DocumentDetailsVO> documentList) {
        this.documentList = documentList;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getObjectionNumber() {
        return objectionNumber;
    }

    public void setObjectionNumber(String objectionNumber) {
        this.objectionNumber = objectionNumber;
    }

    public Map<Long, Double> getChargesMap() {
        return chargesMap;
    }

    public void setChargesMap(Map<Long, Double> chargesMap) {
        this.chargesMap = chargesMap;
    }

    public Double getCharges() {
        return charges;
    }

    public void setCharges(Double charges) {
        this.charges = charges;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public Long getReceiptcategoryId() {
        return receiptcategoryId;
    }

    public void setReceiptcategoryId(Long receiptcategoryId) {
        this.receiptcategoryId = receiptcategoryId;
    }

    public double getDemandLevelRebate() {
        return demandLevelRebate;
    }

    public void setDemandLevelRebate(double demandLevelRebate) {
        this.demandLevelRebate = demandLevelRebate;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public String getUsageType() {
        return usageType;
    }

    public void setUsageType(String usageType) {
        this.usageType = usageType;
    }

    public Map<String, String> getBillDetails() {
        return billDetails;
    }

    public void setBillDetails(Map<String, String> billDetails) {
        this.billDetails = billDetails;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getPropNoConnNoEstateNoL() {
        return propNoConnNoEstateNoL;
    }

    public void setPropNoConnNoEstateNoL(String propNoConnNoEstateNoL) {
        this.propNoConnNoEstateNoL = propNoConnNoEstateNoL;
    }

    public String getPropNoConnNoEstateNoV() {
        return propNoConnNoEstateNoV;
    }

    public void setPropNoConnNoEstateNoV(String propNoConnNoEstateNoV) {
        this.propNoConnNoEstateNoV = propNoConnNoEstateNoV;
    }

    public List<BillReceiptPrintingDTO> getPrintDtoList() {
        return printDtoList;
    }

    public void setPrintDtoList(List<BillReceiptPrintingDTO> printDtoList) {
        this.printDtoList = printDtoList;
    }

    public WardZoneBlockDTO getDwzDTO() {
        return dwzDTO;
    }

    public void setDwzDTO(WardZoneBlockDTO dwzDTO) {
        this.dwzDTO = dwzDTO;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public ConcurrentHashMap<Long, Long> getSupplimentryBillIdMap() {
        return supplimentryBillIdMap;
    }

    public void setSupplimentryBillIdMap(ConcurrentHashMap<Long, Long> supplimentryBillIdMap) {
        this.supplimentryBillIdMap = supplimentryBillIdMap;
    }

    public Date getFromedate() {
        return fromedate;
    }

    public void setFromedate(Date fromedate) {
        this.fromedate = fromedate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getPropName() {
        return propName;
    }

    public void setPropName(String propName) {
        this.propName = propName;
    }

    public String getApplicantFullName() {
        return applicantFullName;
    }

    public void setApplicantFullName(String applicantFullName) {
        this.applicantFullName = applicantFullName;
    }

    public String getPlotNo() {
        return plotNo;
    }

    public void setPlotNo(String plotNo) {
        this.plotNo = plotNo;
    }

    public Long getPgRefId() {
        return pgRefId;
    }

    public void setPgRefId(Long pgRefId) {
        this.pgRefId = pgRefId;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public String getWorkflowEnable() {
        return workflowEnable;
    }

    public void setWorkflowEnable(String workflowEnable) {
        this.workflowEnable = workflowEnable;
    }

    public String getFlatNo() {
        return flatNo;
    }

    public void setFlatNo(String flatNo) {
        this.flatNo = flatNo;
    }

    public String getLicNo() {
        return licNo;
    }

    public void setLicNo(String licNo) {
        this.licNo = licNo;
    }

	/*
	 * public List<CommonChallanPayModeDTO> getMultiModeList() { return
	 * multiModeList; }
	 * 
	 * public void setMultiModeList(List<CommonChallanPayModeDTO> multiModeList) {
	 * this.multiModeList = multiModeList; }
	 */

    public CommonChallanPayModeDTO getRebateMode() {
        return rebateMode;
    }

    public void setRebateMode(CommonChallanPayModeDTO rebateMode) {
        this.rebateMode = rebateMode;
    }

    public Double getRebateAmt() {
        return rebateAmt;
    }

    public void setRebateAmt(Double rebateAmt) {
        this.rebateAmt = rebateAmt;
    }

    public String getFromDateStr() {
        return fromDateStr;
    }

    public void setFromDateStr(String fromDateStr) {
        this.fromDateStr = fromDateStr;
    }

    public String getToDateStr() {
        return toDateStr;
    }

    public void setToDateStr(String toDateStr) {
        this.toDateStr = toDateStr;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgNameMar() {
        return orgNameMar;
    }

    public void setOrgNameMar(String orgNameMar) {
        this.orgNameMar = orgNameMar;
    }

    public String getServiceNameMar() {
        return serviceNameMar;
    }

    public void setServiceNameMar(String serviceNameMar) {
        this.serviceNameMar = serviceNameMar;
    }

    public String getOrgAddress() {
        return orgAddress;
    }

    public void setOrgAddress(String orgAddress) {
        this.orgAddress = orgAddress;
    }

    public String getOrgAddressMar() {
        return orgAddressMar;
    }

    public void setOrgAddressMar(String orgAddressMar) {
        this.orgAddressMar = orgAddressMar;
    }

    public Long getSlaSmDuration() {
        return slaSmDuration;
    }

    public void setSlaSmDuration(Long slaSmDuration) {
        this.slaSmDuration = slaSmDuration;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public String getTransferOwnerFullName() {
        return transferOwnerFullName;
    }

    public void setTransferOwnerFullName(String transferOwnerFullName) {
        this.transferOwnerFullName = transferOwnerFullName;
    }

    public String getTransferInitiatedDate() {
        return transferInitiatedDate;
    }

    public void setTransferInitiatedDate(String transferInitiatedDate) {
        this.transferInitiatedDate = transferInitiatedDate;
    }

    public String getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(String transferDate) {
        this.transferDate = transferDate;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getTransferAddress() {
        return transferAddress;
    }

    public void setTransferAddress(String transferAddress) {
        this.transferAddress = transferAddress;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public String getParentPropNo() {
        return parentPropNo;
    }

    public void setParentPropNo(String parentPropNo) {
        this.parentPropNo = parentPropNo;
    }

    public List<DocumentDetailsVO> getPostalCardDocList() {
        return postalCardDocList;
    }

    public void setPostalCardDocList(List<DocumentDetailsVO> postalCardDocList) {
        this.postalCardDocList = postalCardDocList;
    }

    public String getUniquePropertyId() {
        return uniquePropertyId;
    }

    public void setUniquePropertyId(String uniquePropertyId) {
        this.uniquePropertyId = uniquePropertyId;
    }

	
	public Map<String, String> getBillYearDetails() {
		return billYearDetails;
	}

	public void setBillYearDetails(Map<String, String> billYearDetails) {
		this.billYearDetails = billYearDetails;
	}

	public String getOccupierName() {
		return occupierName;
	}

	public void setOccupierName(String occupierName) {
		this.occupierName = occupierName;
	}

	public String getPushToPayErrMsg() {
		return pushToPayErrMsg;
	}

	public void setPushToPayErrMsg(String pushToPayErrMsg) {
		this.pushToPayErrMsg = pushToPayErrMsg;
	}

	public String getPosPayMode() {
		return posPayMode;
	}

	public void setPosPayMode(String posPayMode) {
		this.posPayMode = posPayMode;
	}

	public boolean isPosPayApplicable() {
		return isPosPayApplicable;
	}

	public void setPosPayApplicable(boolean isPosPayApplicable) {
		this.isPosPayApplicable = isPosPayApplicable;
	}

	public String getPosTxnId() {
		return posTxnId;
	}

	public void setPosTxnId(String posTxnId) {
		this.posTxnId = posTxnId;
	}

	public String getNewHouseNo() {
		return newHouseNo;
	}

	public void setNewHouseNo(String newHouseNo) {
		this.newHouseNo = newHouseNo;
	}

    public Date getReferenceDate() {
		return referenceDate;
	}

	public void setReferenceDate(Date referenceDate) {
		this.referenceDate = referenceDate;
	}
	
	
	public String getRegistrationYear() {
		return registrationYear;
	}

	public void setRegistrationYear(String registrationYear) {
		this.registrationYear = registrationYear;
	}

	public String getRegistrationNo() {
		return registrationNo;
	}

	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
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

	public String getTranRefNumber() {
		return tranRefNumber;
	}

	public void setTranRefNumber(String tranRefNumber) {
		this.tranRefNumber = tranRefNumber;
	}

	public Date getTranRefDate() {
		return tranRefDate;
	}

	public void setTranRefDate(Date tranRefDate) {
		this.tranRefDate = tranRefDate;
	}

	public Long getAssParshadWard1() {
		return assParshadWard1;
	}

	public void setAssParshadWard1(Long assParshadWard1) {
		this.assParshadWard1 = assParshadWard1;
	}

	public Long getAssParshadWard2() {
		return assParshadWard2;
	}

	public void setAssParshadWard2(Long assParshadWard2) {
		this.assParshadWard2 = assParshadWard2;
	}

	public Long getAssParshadWard3() {
		return assParshadWard3;
	}

	public void setAssParshadWard3(Long assParshadWard3) {
		this.assParshadWard3 = assParshadWard3;
	}

	public Long getAssParshadWard4() {
		return assParshadWard4;
	}

	public void setAssParshadWard4(Long assParshadWard4) {
		this.assParshadWard4 = assParshadWard4;
	}

	public Long getAssParshadWard5() {
		return assParshadWard5;
	}

	public void setAssParshadWard5(Long assParshadWard5) {
		this.assParshadWard5 = assParshadWard5;
	}

	public String getRdV2() {
		return rdV2;
	}

	public void setRdV2(String rdV2) {
		this.rdV2 = rdV2;
	}

	public String getRdV3() {
		return rdV3;
	}

	public void setRdV3(String rdV3) {
		this.rdV3 = rdV3;
	}

	public String getRdV4() {
		return rdV4;
	}

	public void setRdV4(String rdV4) {
		this.rdV4 = rdV4;
	}

	public String getRdV5() {
		return rdV5;
	}

	public void setRdV5(String rdV5) {
		this.rdV5 = rdV5;
	}

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}
}
