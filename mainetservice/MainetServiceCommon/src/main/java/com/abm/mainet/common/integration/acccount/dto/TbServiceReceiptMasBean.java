/**
 *
 */
package com.abm.mainet.common.integration.acccount.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.abm.mainet.cfc.challan.dto.ChallanReportDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author dharmendra.chouhan
 *
 */
public class TbServiceReceiptMasBean implements Serializable {

    private static final long serialVersionUID = -7616465501295913865L;
    /**
     *
     */
    private String vmVendorIdDesc;
    private Long rmRcptid;
    private Long rmRcptno;
    private String rmDatetemp;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date rmDate;
    private Long apmApplicationId;
    private Long rmReceiptcategoryId;
    private String rmAmount;
    private Long cmCollnid;
    private Long cuCounterid;
    private String rmReceivedfrom;

    private String rmNarration;

    private Long rmColncntRcptno;
    private Long rmCounterRcptno;
    private Long smServiceId;

    private Long orgId;
    private Long createdBy;
    private int langId;
    private Date createdDate;
    private Long updatedBy;
    private Date updatedDate;
    @JsonIgnore
    @Size(max = 100)
    private String lgIpMac;
    @JsonIgnore
    @Size(max = 100)
    private String lgIpMacUpd;
    private String rmV1;
    private String rmV2;
    private String rmV3;
    private String rmV4;
    private String rmV5;
    private Long rmN1;
    private Long rmN2;
    private Long rmN3;
    private Long rmN4;
    private Long rmN5;
    private Date rmD1;
    private Date rmD2;
    private Date rmD3;
    private String rmLo1;
    private String rmLo2;
    private String rmLo3;
    private String rmLoiNo;
    private Long coddwzId1;
    private Long coddwzId2;
    private Long coddwzId3;
    private Long coddwzId4;
    private Long coddwzId5;
    private Long dpDeptId;
    private String manualReceiptNo;
    private Long vmVendorId;
    private String mobileNumber;
    private String emailId;
    private String hasError;
    private String formattedCurrency;
    private String receiptDelFlag;
    private Date receiptDelDate;
    private String receiptDelDatetemp;
    private Date receiptDelPostingDate;
    private String receiptDelRemark;
    private String successFlag;
    private Long prAdvEntryId;

    private String deptName;
    private BigDecimal totalAmount;

    private String instrumentType;
    private String payOrderNo;
    private Date payOrderDt;
    private String drawnOnBank;
    private Date clearingDt;
    private Long modeId;
    private Long refId;
    private String receiptTypeFlag;
    private AccountFieldMasterBean tbAcFieldMaster = new AccountFieldMasterBean();
    private String selectDs;
    private String feeAmountStr;
    private String totalAmountStr;
    private String templateExistsFlag;
    private String additionalRefNo;
    private Long superOrgId;

    // fields for search
    private Long transactionType;
    private String transactionTypeDesc;
    private Long transactionNo;
    private String transactionDate;
    private BigDecimal transactionAmount;
    private String advanceFlag;
    private String balanceAmount;
    private String branch;

    private String recProperyTaxFlag;
    
    private Long recCategoryTypeId;
    private String recCategoryType;
    
    private String transactionDateDup;
    
    private String flag; 
    
    private String details;
    
    private String rmReceiptNo;
    
    private String cfcColCenterNo;
    private String cfcColCounterNo;
    private String rmAddress;
    
    private Map<String, BigDecimal> taxdetails = new HashMap<>();
        
    @JsonBackReference
    private List<TbSrcptFeesDetBean> receiptFeeDetail = new ArrayList<>();

    @JsonBackReference
    private TbSrcptModesDetBean receiptModeDetailList = new TbSrcptModesDetBean();
    
    @JsonBackReference
    private List<TbSrcptModesDetBean> receiptModeList = new ArrayList<>();
    
    private String flatNo;
    
    private Long fieldId;
    
    private String gstNo;

    public Long getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(final Long transactionType) {
        this.transactionType = transactionType;
    }

    public Long getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(final Long transactionNo) {
        this.transactionNo = transactionNo;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(final String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(final BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    /**
     * @return the deptName
     */
    public String getDeptName() {
        return deptName;
    }

    /**
     * @param deptName the deptName to set
     */
    public void setDeptName(final String deptName) {
        this.deptName = deptName;
    }
   

    public TbSrcptModesDetBean getReceiptModeDetailList() {
		return receiptModeDetailList;
	}

	public void setReceiptModeDetailList(TbSrcptModesDetBean receiptModeDetailList) {
		this.receiptModeDetailList = receiptModeDetailList;
	}

	/**
     * @return the rmRcptid
     */
    public Long getRmRcptid() {
        return rmRcptid;
    }

    /**
     * @param rmRcptid the rmRcptid to set
     */
    public void setRmRcptid(final Long rmRcptid) {
        this.rmRcptid = rmRcptid;
    }

    /**
     * @return the rmRcptno
     */
    public Long getRmRcptno() {
        return rmRcptno;
    }

    /**
     * @param rmRcptno the rmRcptno to set
     */
    public void setRmRcptno(final Long rmRcptno) {
        this.rmRcptno = rmRcptno;
    }

    /**
     * @return the rmDate
     */
    public Date getRmDate() {
        return rmDate;
    }

    /**
     * @param rmDate the rmDate to set
     */
    public void setRmDate(final Date rmDate) {
        this.rmDate = rmDate;
    }

    /**
     *
     * /**
     * @return the rmReceiptcategoryId
     */
    public Long getRmReceiptcategoryId() {
        return rmReceiptcategoryId;
    }

    /**
     * @param rmReceiptcategoryId the rmReceiptcategoryId to set
     */
    public void setRmReceiptcategoryId(final Long rmReceiptcategoryId) {
        this.rmReceiptcategoryId = rmReceiptcategoryId;
    }

    /**
     * @return the rmAmount
     */

    /**
     * @return the cmCollnid
     */
    public Long getCmCollnid() {
        return cmCollnid;
    }

    /**
     * @param cmCollnid the cmCollnid to set
     */
    public void setCmCollnid(final Long cmCollnid) {
        this.cmCollnid = cmCollnid;
    }

    /**
     * @return the cuCounterid
     */
    public Long getCuCounterid() {
        return cuCounterid;
    }

    /**
     * @param cuCounterid the cuCounterid to set
     */
    public void setCuCounterid(final Long cuCounterid) {
        this.cuCounterid = cuCounterid;
    }

    /**
     * @return the rmReceivedfrom
     */
    public String getRmReceivedfrom() {
        return rmReceivedfrom;
    }

    /**
     * @param rmReceivedfrom the rmReceivedfrom to set
     */
    public void setRmReceivedfrom(final String rmReceivedfrom) {
        this.rmReceivedfrom = rmReceivedfrom;
    }

    /**
     * @return the rmNarration
     */
    public String getRmNarration() {
        return rmNarration;
    }

    /**
     * @param rmNarration the rmNarration to set
     */
    public void setRmNarration(final String rmNarration) {
        this.rmNarration = rmNarration;
    }

    /**
     * @return the modeId
     */
    public Long getModeId() {
        return modeId;
    }

    /**
     * @param modeId the modeId to set
     */
    public void setModeId(final Long modeId) {
        this.modeId = modeId;
    }

    /**
     * @return the instrumentType
     */
    public String getInstrumentType() {
        return instrumentType;
    }

    /**
     * @param instrumentType the instrumentType to set
     */
    public void setInstrumentType(final String instrumentType) {
        this.instrumentType = instrumentType;
    }

    /**
     * @return the payOrderNo
     */
    public String getPayOrderNo() {
        return payOrderNo;
    }

    /**
     * @param payOrderNo the payOrderNo to set
     */
    public void setPayOrderNo(final String payOrderNo) {
        this.payOrderNo = payOrderNo;
    }

    /**
     * @return the payOrderDt
     */
    public Date getPayOrderDt() {
        return payOrderDt;
    }

    /**
     * @param payOrderDt the payOrderDt to set
     */
    public void setPayOrderDt(final Date payOrderDt) {
        this.payOrderDt = payOrderDt;
    }

    /**
     * @return the drawnOnBank
     */
    public String getDrawnOnBank() {
        return drawnOnBank;
    }

    /**
     * @param drawnOnBank the drawnOnBank to set
     */
    public void setDrawnOnBank(final String drawnOnBank) {
        this.drawnOnBank = drawnOnBank;
    }

    /**
     * @return the clearingDt
     */
    public Date getClearingDt() {
        return clearingDt;
    }

    /**
     * @param clearingDt the clearingDt to set
     */
    public void setClearingDt(final Date clearingDt) {
        this.clearingDt = clearingDt;
    }

    /**
     * @return the totalAmount
     */
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    /**
     * @param totalAmount the totalAmount to set
     */
    public void setTotalAmount(final BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * @return the rmColncntRcptno
     */
    public Long getRmColncntRcptno() {
        return rmColncntRcptno;
    }

    /**
     * @param rmColncntRcptno the rmColncntRcptno to set
     */
    public void setRmColncntRcptno(final Long rmColncntRcptno) {
        this.rmColncntRcptno = rmColncntRcptno;
    }

    /**
     * @return the rmCounterRcptno
     */
    public Long getRmCounterRcptno() {
        return rmCounterRcptno;
    }

    /**
     * @param rmCounterRcptno the rmCounterRcptno to set
     */
    public void setRmCounterRcptno(final Long rmCounterRcptno) {
        this.rmCounterRcptno = rmCounterRcptno;
    }

    /**
     * @return the smServiceId
     */
    public Long getSmServiceId() {
        return smServiceId;
    }

    /**
     * @param smServiceId the smServiceId to set
     */
    public void setSmServiceId(final Long smServiceId) {
        this.smServiceId = smServiceId;
    }

    /**
     * @return the orgId
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public int getLangId() {
        return langId;
    }

    /**
     * @param langId the langId to set
     */
    public void setLangId(final int langId) {
        this.langId = langId;
    }

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
     * @return the rmLoiNo
     */
    public String getRmLoiNo() {
        return rmLoiNo;
    }

    /**
     * @param rmLoiNo the rmLoiNo to set
     */
    public void setRmLoiNo(final String rmLoiNo) {
        this.rmLoiNo = rmLoiNo;
    }

    /**
     * @return the coddwzId1
     */
    public Long getCoddwzId1() {
        return coddwzId1;
    }

    /**
     * @param coddwzId1 the coddwzId1 to set
     */
    public void setCoddwzId1(final Long coddwzId1) {
        this.coddwzId1 = coddwzId1;
    }

    /**
     * @return the coddwzId2
     */
    public Long getCoddwzId2() {
        return coddwzId2;
    }

    /**
     * @param coddwzId2 the coddwzId2 to set
     */
    public void setCoddwzId2(final Long coddwzId2) {
        this.coddwzId2 = coddwzId2;
    }

    /**
     * @return the coddwzId3
     */
    public Long getCoddwzId3() {
        return coddwzId3;
    }

    /**
     * @param coddwzId3 the coddwzId3 to set
     */
    public void setCoddwzId3(final Long coddwzId3) {
        this.coddwzId3 = coddwzId3;
    }

    /**
     * @return the coddwzId4
     */
    public Long getCoddwzId4() {
        return coddwzId4;
    }

    /**
     * @return the formattedCurrency
     */
    public String getFormattedCurrency() {
        return formattedCurrency;
    }

    /**
     * @param formattedCurrency the formattedCurrency to set
     */
    public void setFormattedCurrency(final String formattedCurrency) {
        this.formattedCurrency = formattedCurrency;
    }

    /**
     * @param coddwzId4 the coddwzId4 to set
     */
    public void setCoddwzId4(final Long coddwzId4) {
        this.coddwzId4 = coddwzId4;
    }

    /**
     * @return the coddwzId5
     */
    public Long getCoddwzId5() {
        return coddwzId5;
    }

    /**
     * @param coddwzId5 the coddwzId5 to set
     */
    public void setCoddwzId5(final Long coddwzId5) {
        this.coddwzId5 = coddwzId5;
    }

    /**
     * @return the dpDeptId
     */
    public Long getDpDeptId() {
        return dpDeptId;
    }

    /**
     * @param dpDeptId the dpDeptId to set
     */
    public void setDpDeptId(final Long dpDeptId) {
        this.dpDeptId = dpDeptId;
    }

    /**
     * @return the manualReceiptNo
     */
    public String getManualReceiptNo() {
        return manualReceiptNo;
    }

    /**
     * @param manualReceiptNo the manualReceiptNo to set
     */
    public void setManualReceiptNo(final String manualReceiptNo) {
        this.manualReceiptNo = manualReceiptNo;
    }

    /**
     * @return the vmVendorId
     */
    public Long getVmVendorId() {
        return vmVendorId;
    }

    /**
     * @param vmVendorId the vmVendorId to set
     */
    public void setVmVendorId(final Long vmVendorId) {
        this.vmVendorId = vmVendorId;
    }

    /**
     * @return the mobileNumber
     */
    public String getMobileNumber() {
        return mobileNumber;
    }

    /**
     * @param mobileNumber the mobileNumber to set
     */
    public void setMobileNumber(final String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    /**
     * @return the emailId
     */
    public String getEmailId() {
        return emailId;
    }

    /**
     * @param emailId the emailId to set
     */
    public void setEmailId(final String emailId) {
        this.emailId = emailId;
    }

    /**
     * @return the hasError
     */
    public String getHasError() {
        return hasError;
    }

    /**
     * @return the receiptFeeDetail
     */
    public List<TbSrcptFeesDetBean> getReceiptFeeDetail() {
        return receiptFeeDetail;
    }

    /**
     * @param receiptFeeDetail the receiptFeeDetail to set
     */
    public void setReceiptFeeDetail(final List<TbSrcptFeesDetBean> receiptFeeDetail) {
        this.receiptFeeDetail = receiptFeeDetail;
    }

    /**
     * @param hasError the hasError to set
     */
    public void setHasError(final String hasError) {
        this.hasError = hasError;
    }

    /**
     * @return the rmDatetemp
     */
    public String getRmDatetemp() {
        return rmDatetemp;
    }

    /**
     * @param rmDatetemp the rmDatetemp to set
     */
    public void setRmDatetemp(final String rmDatetemp) {
        this.rmDatetemp = rmDatetemp;
    }

    /**
     * @return the receiptDelFlag
     */
    public String getReceiptDelFlag() {
        return receiptDelFlag;
    }

    /**
     * @param receiptDelFlag the receiptDelFlag to set
     */
    public void setReceiptDelFlag(final String receiptDelFlag) {
        this.receiptDelFlag = receiptDelFlag;
    }

    /**
     * @return the receiptDelDate
     */
    public Date getReceiptDelDate() {
        return receiptDelDate;
    }

    /**
     * @param receiptDelDate the receiptDelDate to set
     */
    public void setReceiptDelDate(final Date receiptDelDate) {
        this.receiptDelDate = receiptDelDate;
    }

    /**
     * @return the receiptDelPostingDate
     */
    public Date getReceiptDelPostingDate() {
        return receiptDelPostingDate;
    }

    /**
     * @param receiptDelPostingDate the receiptDelPostingDate to set
     */
    public void setReceiptDelPostingDate(final Date receiptDelPostingDate) {
        this.receiptDelPostingDate = receiptDelPostingDate;
    }

    /**
     * @return the receiptDelRemark
     */
    public String getReceiptDelRemark() {
        return receiptDelRemark;
    }

    /**
     * @param receiptDelRemark the receiptDelRemark to set
     */
    public void setReceiptDelRemark(final String receiptDelRemark) {
        this.receiptDelRemark = receiptDelRemark;
    }

    /**
     * @return the successFlag
     */
    public String getSuccessFlag() {
        return successFlag;
    }

    /**
     * @param successFlag the successFlag to set
     */
    public void setSuccessFlag(final String successFlag) {
        this.successFlag = successFlag;
    }

    /**
     * @return the receiptDelDatetemp
     */
    public String getReceiptDelDatetemp() {
        return receiptDelDatetemp;
    }

    /**
     * @param receiptDelDatetemp the receiptDelDatetemp to set
     */
    public void setReceiptDelDatetemp(final String receiptDelDatetemp) {
        this.receiptDelDatetemp = receiptDelDatetemp;
    }

    /**
     * @return the tbAcFieldMaster
     */
    public AccountFieldMasterBean getTbAcFieldMaster() {
        return tbAcFieldMaster;
    }

    /**
     * @param tbAcFieldMaster the tbAcFieldMaster to set
     */
    public void setTbAcFieldMaster(final AccountFieldMasterBean tbAcFieldMaster) {
        this.tbAcFieldMaster = tbAcFieldMaster;
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
     * @return the rmV1
     */
    public String getRmV1() {
        return rmV1;
    }

    /**
     * @param rmV1 the rmV1 to set
     */
    public void setRmV1(final String rmV1) {
        this.rmV1 = rmV1;
    }

    /**
     * @return the rmV2
     */
    public String getRmV2() {
        return rmV2;
    }

    /**
     * @param rmV2 the rmV2 to set
     */
    public void setRmV2(final String rmV2) {
        this.rmV2 = rmV2;
    }

    /**
     * @return the rmV3
     */
    public String getRmV3() {
        return rmV3;
    }

    /**
     * @param rmV3 the rmV3 to set
     */
    public void setRmV3(final String rmV3) {
        this.rmV3 = rmV3;
    }

    /**
     * @return the rmV4
     */
    public String getRmV4() {
        return rmV4;
    }

    /**
     * @param rmV4 the rmV4 to set
     */
    public void setRmV4(final String rmV4) {
        this.rmV4 = rmV4;
    }

    /**
     * @return the rmV5
     */
    public String getRmV5() {
        return rmV5;
    }

    /**
     * @param rmV5 the rmV5 to set
     */
    public void setRmV5(final String rmV5) {
        this.rmV5 = rmV5;
    }

    /**
     * @return the rmN1
     */
    public Long getRmN1() {
        return rmN1;
    }

    /**
     * @param rmN1 the rmN1 to set
     */
    public void setRmN1(final Long rmN1) {
        this.rmN1 = rmN1;
    }

    /**
     * @return the rmN2
     */
    public Long getRmN2() {
        return rmN2;
    }

    /**
     * @param rmN2 the rmN2 to set
     */
    public void setRmN2(final Long rmN2) {
        this.rmN2 = rmN2;
    }

    /**
     * @return the rmN3
     */
    public Long getRmN3() {
        return rmN3;
    }

    /**
     * @param rmN3 the rmN3 to set
     */
    public void setRmN3(final Long rmN3) {
        this.rmN3 = rmN3;
    }

    /**
     * @return the rmN4
     */
    public Long getRmN4() {
        return rmN4;
    }

    /**
     * @param rmN4 the rmN4 to set
     */
    public void setRmN4(final Long rmN4) {
        this.rmN4 = rmN4;
    }

    /**
     * @return the rmN5
     */
    public Long getRmN5() {
        return rmN5;
    }

    /**
     * @param rmN5 the rmN5 to set
     */
    public void setRmN5(final Long rmN5) {
        this.rmN5 = rmN5;
    }

    /**
     * @return the rmD1
     */
    public Date getRmD1() {
        return rmD1;
    }

    /**
     * @param rmD1 the rmD1 to set
     */
    public void setRmD1(final Date rmD1) {
        this.rmD1 = rmD1;
    }

    /**
     * @return the rmD2
     */
    public Date getRmD2() {
        return rmD2;
    }

    /**
     * @param rmD2 the rmD2 to set
     */
    public void setRmD2(final Date rmD2) {
        this.rmD2 = rmD2;
    }

    /**
     * @return the rmD3
     */
    public Date getRmD3() {
        return rmD3;
    }

    /**
     * @param rmD3 the rmD3 to set
     */
    public void setRmD3(final Date rmD3) {
        this.rmD3 = rmD3;
    }

    /**
     * @return the rmLo1
     */
    public String getRmLo1() {
        return rmLo1;
    }

    /**
     * @param rmLo1 the rmLo1 to set
     */
    public void setRmLo1(final String rmLo1) {
        this.rmLo1 = rmLo1;
    }

    /**
     * @return the rmLo2
     */
    public String getRmLo2() {
        return rmLo2;
    }

    /**
     * @param rmLo2 the rmLo2 to set
     */
    public void setRmLo2(final String rmLo2) {
        this.rmLo2 = rmLo2;
    }

    /**
     * @return the rmLo3
     */
    public String getRmLo3() {
        return rmLo3;
    }

    /**
     * @param rmLo3 the rmLo3 to set
     */
    public void setRmLo3(final String rmLo3) {
        this.rmLo3 = rmLo3;
    }

    /**
     * @return the apmApplicationId
     */
    public Long getApmApplicationId() {
        return apmApplicationId;
    }

    /**
     * @param apmApplicationId the apmApplicationId to set
     */
    public void setApmApplicationId(final Long apmApplicationId) {
        this.apmApplicationId = apmApplicationId;
    }

    /**
     * @return the selectDs
     */
    public String getSelectDs() {
        return selectDs;
    }

    /**
     * @param selectDs the selectDs to set
     */
    public void setSelectDs(final String selectDs) {
        this.selectDs = selectDs;
    }

    /**
     * @return the refId
     */
    public Long getRefId() {
        return refId;
    }

    /**
     * @param refId the refId to set
     */
    public void setRefId(final Long refId) {
        this.refId = refId;
    }

    /**
     * @return the receiptTypeFlag
     */
    public String getReceiptTypeFlag() {
        return receiptTypeFlag;
    }

    /**
     * @param receiptTypeFlag the receiptTypeFlag to set
     */
    public void setReceiptTypeFlag(final String receiptTypeFlag) {
        this.receiptTypeFlag = receiptTypeFlag;
    }

    public String getVmVendorIdDesc() {
        return vmVendorIdDesc;
    }

    public void setVmVendorIdDesc(final String vmVendorIdDesc) {
        this.vmVendorIdDesc = vmVendorIdDesc;
    }

    public String getFeeAmountStr() {
        return feeAmountStr;
    }

    public void setFeeAmountStr(final String feeAmountStr) {
        this.feeAmountStr = feeAmountStr;
    }

    public String getTotalAmountStr() {
        return totalAmountStr;
    }

    public void setTotalAmountStr(final String totalAmountStr) {
        this.totalAmountStr = totalAmountStr;
    }

    public String getTemplateExistsFlag() {
        return templateExistsFlag;
    }

    public void setTemplateExistsFlag(final String templateExistsFlag) {
        this.templateExistsFlag = templateExistsFlag;
    }

    public String getAdditionalRefNo() {
        return additionalRefNo;
    }

    public void setAdditionalRefNo(final String additionalRefNo) {
        this.additionalRefNo = additionalRefNo;
    }

    public Long getSuperOrgId() {
        return superOrgId;
    }

    public void setSuperOrgId(final Long superOrgId) {
        this.superOrgId = superOrgId;
    }

    public String getTransactionTypeDesc() {
        return transactionTypeDesc;
    }

    public void setTransactionTypeDesc(final String transactionTypeDesc) {
        this.transactionTypeDesc = transactionTypeDesc;
    }

    public String getAdvanceFlag() {
        return advanceFlag;
    }

    public void setAdvanceFlag(final String advanceFlag) {
        this.advanceFlag = advanceFlag;
    }

    public String getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(final String balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public Long getPrAdvEntryId() {
        return prAdvEntryId;
    }

    public void setPrAdvEntryId(final Long prAdvEntryId) {
        this.prAdvEntryId = prAdvEntryId;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getRmAmount() {
        return rmAmount;
    }

    public void setRmAmount(String rmAmount) {
        this.rmAmount = rmAmount;
    }

    public String getRecProperyTaxFlag() {
		return recProperyTaxFlag;
	}

	public void setRecProperyTaxFlag(String recProperyTaxFlag) {
		this.recProperyTaxFlag = recProperyTaxFlag;
	}

	public Long getRecCategoryTypeId() {
		return recCategoryTypeId;
	}

	public void setRecCategoryTypeId(Long recCategoryTypeId) {
		this.recCategoryTypeId = recCategoryTypeId;
	}

	public String getRecCategoryType() {
		return recCategoryType;
	}

	public void setRecCategoryType(String recCategoryType) {
		this.recCategoryType = recCategoryType;
	}

	public String getTransactionDateDup() {
		return transactionDateDup;
	}

	public void setTransactionDateDup(String transactionDateDup) {
		this.transactionDateDup = transactionDateDup;
	}
	

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public List<TbSrcptModesDetBean> getReceiptModeList() {
		return receiptModeList;
	}

	public void setReceiptModeList(List<TbSrcptModesDetBean> receiptModeList) {
		this.receiptModeList = receiptModeList;
	}

	
	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	
	public Map<String, BigDecimal> getTaxdetails() {
		return taxdetails;
	}

	public void setTaxdetails(Map<String, BigDecimal> taxdetails) {
		this.taxdetails = taxdetails;
	}
	

	public String getRmReceiptNo() {
		return rmReceiptNo;
	}

	public void setRmReceiptNo(String rmReceiptNo) {
		this.rmReceiptNo = rmReceiptNo;
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

	public String getFlatNo() {
		return flatNo;
	}

	public void setFlatNo(String flatNo) {
		this.flatNo = flatNo;
	}

	public String getRmAddress() {
		return rmAddress;
	}

	public void setRmAddress(String rmAddress) {
		this.rmAddress = rmAddress;
	}

	public Long getFieldId() {
		return fieldId;
	}

	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}
	

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	
	@Override
	public String toString() {
		return "TbServiceReceiptMasBean [vmVendorIdDesc=" + vmVendorIdDesc + ", rmRcptid=" + rmRcptid + ", rmRcptno="
				+ rmRcptno + ", rmDatetemp=" + rmDatetemp + ", rmDate=" + rmDate + ", apmApplicationId="
				+ apmApplicationId + ", rmReceiptcategoryId=" + rmReceiptcategoryId + ", rmAmount=" + rmAmount
				+ ", cmCollnid=" + cmCollnid + ", cuCounterid=" + cuCounterid + ", rmReceivedfrom=" + rmReceivedfrom
				+ ", rmNarration=" + rmNarration + ", rmColncntRcptno=" + rmColncntRcptno + ", rmCounterRcptno="
				+ rmCounterRcptno + ", smServiceId=" + smServiceId + ", orgId=" + orgId + ", createdBy=" + createdBy
				+ ", langId=" + langId + ", createdDate=" + createdDate + ", updatedBy=" + updatedBy + ", updatedDate="
				+ updatedDate + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd + ", rmV1=" + rmV1 + ", rmV2="
				+ rmV2 + ", rmV3=" + rmV3 + ", rmV4=" + rmV4 + ", rmV5=" + rmV5 + ", rmN1=" + rmN1 + ", rmN2=" + rmN2
				+ ", rmN3=" + rmN3 + ", rmN4=" + rmN4 + ", rmN5=" + rmN5 + ", rmD1=" + rmD1 + ", rmD2=" + rmD2
				+ ", rmD3=" + rmD3 + ", rmLo1=" + rmLo1 + ", rmLo2=" + rmLo2 + ", rmLo3=" + rmLo3 + ", rmLoiNo="
				+ rmLoiNo + ", coddwzId1=" + coddwzId1 + ", coddwzId2=" + coddwzId2 + ", coddwzId3=" + coddwzId3
				+ ", coddwzId4=" + coddwzId4 + ", coddwzId5=" + coddwzId5 + ", dpDeptId=" + dpDeptId
				+ ", manualReceiptNo=" + manualReceiptNo + ", vmVendorId=" + vmVendorId + ", mobileNumber="
				+ mobileNumber + ", emailId=" + emailId + ", hasError=" + hasError + ", formattedCurrency="
				+ formattedCurrency + ", receiptDelFlag=" + receiptDelFlag + ", receiptDelDate=" + receiptDelDate
				+ ", receiptDelDatetemp=" + receiptDelDatetemp + ", receiptDelPostingDate=" + receiptDelPostingDate
				+ ", receiptDelRemark=" + receiptDelRemark + ", successFlag=" + successFlag + ", prAdvEntryId="
				+ prAdvEntryId + ", deptName=" + deptName + ", totalAmount=" + totalAmount + ", instrumentType="
				+ instrumentType + ", payOrderNo=" + payOrderNo + ", payOrderDt=" + payOrderDt + ", drawnOnBank="
				+ drawnOnBank + ", clearingDt=" + clearingDt + ", modeId=" + modeId + ", refId=" + refId
				+ ", receiptTypeFlag=" + receiptTypeFlag + ", tbAcFieldMaster=" + tbAcFieldMaster + ", selectDs="
				+ selectDs + ", feeAmountStr=" + feeAmountStr + ", totalAmountStr=" + totalAmountStr
				+ ", templateExistsFlag=" + templateExistsFlag + ", additionalRefNo=" + additionalRefNo
				+ ", superOrgId=" + superOrgId + ", transactionType=" + transactionType + ", transactionTypeDesc="
				+ transactionTypeDesc + ", transactionNo=" + transactionNo + ", transactionDate=" + transactionDate
				+ ", transactionAmount=" + transactionAmount + ", advanceFlag=" + advanceFlag + ", balanceAmount="
				+ balanceAmount + ", branch=" + branch + ", recProperyTaxFlag=" + recProperyTaxFlag
				+ ", recCategoryTypeId=" + recCategoryTypeId + ", recCategoryType=" + recCategoryType
				+ ", transactionDateDup=" + transactionDateDup + ", flag=" + flag + ", receiptFeeDetail="
				+ receiptFeeDetail + ", receiptModeDetail=" + receiptModeDetailList + ", details=" + details + ", flatNo=" + flatNo  
				+", taxdetails=" + taxdetails + ", cfcColCenterNo=" + cfcColCenterNo + ", cfcColCounterNo=" + cfcColCounterNo +"]";
	}


}
