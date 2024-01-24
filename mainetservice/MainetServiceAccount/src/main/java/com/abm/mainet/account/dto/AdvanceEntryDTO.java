package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;

public class AdvanceEntryDTO implements Serializable {

    private static final long serialVersionUID = 8683880608834172574L;

    private String alreadyExists = "N";
    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    // ----------------------------------------------------------------------
    private Long prAdvEntryId;
    private Long pacHeadId;
    private Long advanceTypeId;
    private String advanceTypeDesc;
    private Long vendorId;
    private Long advanceNumber;
    private String advanceDate;
    private String advanceAmount;
    private String balanceAmount;
    private String partOfAdvance;
    private Date prAdvEntryDate;
    private Long prAdvEntryNo;

    // Payment Related All Required Fields
    private String paymentOrderNo;
    private Date paymentOrderDate;
    private Date paymentDate;
    private String paymentOrderDateDup;
    private String paymentNumber;
    private String paymentDateDup;
    private String paymentAmount;
    private String payAdvParticulars;
    private Long payAdvSettlementNumber;
    private String seas_Deas;
    private List<AdvanceEntryDTO> advanceLedgerList;
    private Long categoryTypeId;
    private Long paymentReferenceId;
    private String receiptRepaymentDate;
    private Long receiptAdjustmentNo;
    private String voucherDate;
    private Long voucherNo;
    private String AdvPaymentDate;
    private String AdvPaymentOrderDate;
    private BigDecimal sumAdvAmt;
    private BigDecimal sumblncAmt;
    private List<TbServiceReceiptMasBean> receiptList = new ArrayList<>();
    private List<AccountBillEntryMasterBean> billList = new ArrayList<>();
    // common Fields for all
    @NotNull
    private Long orgid;

    @NotNull
    private Long createdBy;

    @NotNull
    private int langId;

    @NotNull
    private Date createdDate;

    private Long updatedBy;

    private Date updatedDate;

    @Size(max = 100)
    private String lgIpMac;

    @Size(max = 100)
    private String lgIpMacUpd;

    private Long fi04N1;

    @Size(max = 200)
    private String fi04V1;

    private Date fi04D1;

    @Size(max = 1)
    private String fi04Lo1;

    private String hasError;

    @Size(max = 1)
    private String cpdIdStatus;

    private String cpdIdStatusDup;

    private Long deptId;
    private String vendorName;
    private String adv_Flg;
    private String liveModeDate;
    private Date liveModeDateDup;
    private String sliStatusFlag;
    private String billDate;
    private BigDecimal billPaidAmount;
    private String natureOfbill;
    private String successFlag;
    // setters & getters

    public String getAlreadyExists() {
        return alreadyExists;
    }

    public void setAlreadyExists(final String alreadyExists) {
        this.alreadyExists = alreadyExists;
    }

    public Long getPrAdvEntryId() {
        return prAdvEntryId;
    }

    public void setPrAdvEntryId(final Long prAdvEntryId) {
        this.prAdvEntryId = prAdvEntryId;
    }

    public String getHasError() {
        return hasError;
    }

    public void setHasError(final String hasError) {
        this.hasError = hasError;
    }

    public Long getPacHeadId() {
        return pacHeadId;
    }

    public void setPacHeadId(final Long pacHeadId) {
        this.pacHeadId = pacHeadId;
    }

    public Long getAdvanceTypeId() {
        return advanceTypeId;
    }

    public void setAdvanceTypeId(final Long advanceTypeId) {
        this.advanceTypeId = advanceTypeId;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(final Long vendorId) {
        this.vendorId = vendorId;
    }

    public String getAdvanceAmount() {
        return advanceAmount;
    }

    public void setAdvanceAmount(final String advanceAmount) {
        this.advanceAmount = advanceAmount;
    }

    public String getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(final String balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public String getPartOfAdvance() {
        return partOfAdvance;
    }

    public void setPartOfAdvance(final String partOfAdvance) {
        this.partOfAdvance = partOfAdvance;
    }

    public String getPaymentOrderNo() {
        return paymentOrderNo;
    }

    public void setPaymentOrderNo(final String paymentOrderNo) {
        this.paymentOrderNo = paymentOrderNo;
    }

    public String getPaymentNumber() {
        return paymentNumber;
    }

    public void setPaymentNumber(final String paymentNumber) {
        this.paymentNumber = paymentNumber;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(final String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
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

    public Long getFi04N1() {
        return fi04N1;
    }

    public void setFi04N1(final Long fi04n1) {
        fi04N1 = fi04n1;
    }

    public String getFi04V1() {
        return fi04V1;
    }

    public void setFi04V1(final String fi04v1) {
        fi04V1 = fi04v1;
    }

    public Date getFi04D1() {
        return fi04D1;
    }

    public void setFi04D1(final Date fi04d1) {
        fi04D1 = fi04d1;
    }

    public String getFi04Lo1() {
        return fi04Lo1;
    }

    public void setFi04Lo1(final String fi04Lo1) {
        this.fi04Lo1 = fi04Lo1;
    }

    public String getCpdIdStatus() {
        return cpdIdStatus;
    }

    public void setCpdIdStatus(final String cpdIdStatus) {
        this.cpdIdStatus = cpdIdStatus;
    }

    public Date getPrAdvEntryDate() {
        return prAdvEntryDate;
    }

    public void setPrAdvEntryDate(final Date prAdvEntryDate) {
        this.prAdvEntryDate = prAdvEntryDate;
    }

    public Long getPrAdvEntryNo() {
        return prAdvEntryNo;
    }

    public void setPrAdvEntryNo(final Long prAdvEntryNo) {
        this.prAdvEntryNo = prAdvEntryNo;
    }

    public String getPayAdvParticulars() {
        return payAdvParticulars;
    }

    public void setPayAdvParticulars(final String payAdvParticulars) {
        this.payAdvParticulars = payAdvParticulars;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getAdvanceNumber() {
        return advanceNumber;
    }

    public void setAdvanceNumber(final Long advanceNumber) {
        this.advanceNumber = advanceNumber;
    }

    public String getAdvanceDate() {
        return advanceDate;
    }

    public void setAdvanceDate(final String advanceDate) {
        this.advanceDate = advanceDate;
    }

    public String getCpdIdStatusDup() {
        return cpdIdStatusDup;
    }

    public void setCpdIdStatusDup(final String cpdIdStatusDup) {
        this.cpdIdStatusDup = cpdIdStatusDup;
    }

    public String getPaymentOrderDateDup() {
        return paymentOrderDateDup;
    }

    public void setPaymentOrderDateDup(final String paymentOrderDateDup) {
        this.paymentOrderDateDup = paymentOrderDateDup;
    }

    public String getPaymentDateDup() {
        return paymentDateDup;
    }

    public void setPaymentDateDup(final String paymentDateDup) {
        this.paymentDateDup = paymentDateDup;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(final Long deptId) {
        this.deptId = deptId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(final String vendorName) {
        this.vendorName = vendorName;
    }

    public String getAdvanceTypeDesc() {
        return advanceTypeDesc;
    }

    public void setAdvanceTypeDesc(final String advanceTypeDesc) {
        this.advanceTypeDesc = advanceTypeDesc;
    }

    public Long getPayAdvSettlementNumber() {
        return payAdvSettlementNumber;
    }

    public void setPayAdvSettlementNumber(final Long payAdvSettlementNumber) {
        this.payAdvSettlementNumber = payAdvSettlementNumber;
    }

    public String getSeas_Deas() {
        return seas_Deas;
    }

    public void setSeas_Deas(final String seas_Deas) {
        this.seas_Deas = seas_Deas;
    }

    public String getAdv_Flg() {
        return adv_Flg;
    }

    public void setAdv_Flg(final String adv_Flg) {
        this.adv_Flg = adv_Flg;
    }

    public String getLiveModeDate() {
        return liveModeDate;
    }

    public void setLiveModeDate(final String liveModeDate) {
        this.liveModeDate = liveModeDate;
    }

    public Date getLiveModeDateDup() {
        return liveModeDateDup;
    }

    public void setLiveModeDateDup(final Date liveModeDateDup) {
        this.liveModeDateDup = liveModeDateDup;
    }

    public List<AdvanceEntryDTO> getAdvanceLedgerList() {
        return advanceLedgerList;
    }

    public void setAdvanceLedgerList(List<AdvanceEntryDTO> advanceLedgerList) {
        this.advanceLedgerList = advanceLedgerList;
    }

    public String getSliStatusFlag() {
        return sliStatusFlag;
    }

    public void setSliStatusFlag(String sliStatusFlag) {
        this.sliStatusFlag = sliStatusFlag;
    }

    public Long getCategoryTypeId() {
        return categoryTypeId;
    }

    public void setCategoryTypeId(Long categoryTypeId) {
        this.categoryTypeId = categoryTypeId;
    }

    public Long getPaymentReferenceId() {
        return paymentReferenceId;
    }

    public void setPaymentReferenceId(Long paymentReferenceId) {
        this.paymentReferenceId = paymentReferenceId;
    }

    public String getReceiptRepaymentDate() {
        return receiptRepaymentDate;
    }

    public void setReceiptRepaymentDate(String receiptRepaymentDate) {
        this.receiptRepaymentDate = receiptRepaymentDate;
    }

    public Long getReceiptAdjustmentNo() {
        return receiptAdjustmentNo;
    }

    public void setReceiptAdjustmentNo(Long receiptAdjustmentNo) {
        this.receiptAdjustmentNo = receiptAdjustmentNo;
    }

    public String getVoucherDate() {
        return voucherDate;
    }

    public void setVoucherDate(String voucherDate) {
        this.voucherDate = voucherDate;
    }

    public Long getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(Long voucherNo) {
        this.voucherNo = voucherNo;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public BigDecimal getBillPaidAmount() {
        return billPaidAmount;
    }

    public void setBillPaidAmount(BigDecimal billPaidAmount) {
        this.billPaidAmount = billPaidAmount;
    }

    public String getNatureOfbill() {
        return natureOfbill;
    }

    public void setNatureOfbill(String natureOfbill) {
        this.natureOfbill = natureOfbill;
    }

    public Date getPaymentOrderDate() {
        return paymentOrderDate;
    }

    public void setPaymentOrderDate(Date paymentOrderDate) {
        this.paymentOrderDate = paymentOrderDate;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getAdvPaymentDate() {
        return AdvPaymentDate;
    }

    public void setAdvPaymentDate(String advPaymentDate) {
        AdvPaymentDate = advPaymentDate;
    }

    public String getAdvPaymentOrderDate() {
        return AdvPaymentOrderDate;
    }

    public void setAdvPaymentOrderDate(String advPaymentOrderDate) {
        AdvPaymentOrderDate = advPaymentOrderDate;
    }

    public BigDecimal getSumAdvAmt() {
        return sumAdvAmt;
    }

    public void setSumAdvAmt(BigDecimal sumAdvAmt) {
        this.sumAdvAmt = sumAdvAmt;
    }

    public BigDecimal getSumblncAmt() {
        return sumblncAmt;
    }

    public void setSumblncAmt(BigDecimal sumblncAmt) {
        this.sumblncAmt = sumblncAmt;
    }

    public List<TbServiceReceiptMasBean> getReceiptList() {
		return receiptList;
	}

	public void setReceiptList(List<TbServiceReceiptMasBean> receiptList) {
		this.receiptList = receiptList;
	}

	public List<AccountBillEntryMasterBean> getBillList() {
		return billList;
	}

	public void setBillList(List<AccountBillEntryMasterBean> billList) {
		this.billList = billList;
	}

	public String getSuccessFlag() {
		return successFlag;
	}

	public void setSuccessFlag(String successFlag) {
		this.successFlag = successFlag;
	}

	@Override
    public String toString() {
        return "AdvanceEntryDTO [alreadyExists=" + alreadyExists + ", prAdvEntryId=" + prAdvEntryId + ", pacHeadId=" + pacHeadId
                + ", advanceTypeId=" + advanceTypeId + ", advanceTypeDesc=" + advanceTypeDesc + ", vendorId=" + vendorId
                + ", advanceNumber=" + advanceNumber + ", advanceDate=" + advanceDate + ", advanceAmount=" + advanceAmount
                + ", balanceAmount=" + balanceAmount + ", partOfAdvance=" + partOfAdvance + ", prAdvEntryDate=" + prAdvEntryDate
                + ", prAdvEntryNo=" + prAdvEntryNo + ", paymentOrderNo=" + paymentOrderNo + ", paymentOrderDate="
                + paymentOrderDate + ", paymentDate=" + paymentDate + ", paymentOrderDateDup=" + paymentOrderDateDup
                + ", paymentNumber=" + paymentNumber + ", paymentDateDup=" + paymentDateDup + ", paymentAmount=" + paymentAmount
                + ", payAdvParticulars=" + payAdvParticulars + ", payAdvSettlementNumber=" + payAdvSettlementNumber
                + ", seas_Deas=" + seas_Deas + ", advanceLedgerList=" + advanceLedgerList + ", categoryTypeId=" + categoryTypeId
                + ", paymentReferenceId=" + paymentReferenceId + ", receiptRepaymentDate=" + receiptRepaymentDate
                + ", receiptAdjustmentNo=" + receiptAdjustmentNo + ", voucherDate=" + voucherDate + ", voucherNo=" + voucherNo
                + ", AdvPaymentDate=" + AdvPaymentDate + ", AdvPaymentOrderDate=" + AdvPaymentOrderDate + ", sumAdvAmt="
                + sumAdvAmt + ", sumblncAmt=" + sumblncAmt + ", orgid=" + orgid + ", createdBy=" + createdBy + ", langId="
                + langId + ", createdDate=" + createdDate + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate
                + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd + ", fi04N1=" + fi04N1 + ", fi04V1=" + fi04V1
                + ", fi04D1=" + fi04D1 + ", fi04Lo1=" + fi04Lo1 + ", hasError=" + hasError + ", cpdIdStatus=" + cpdIdStatus
                + ", cpdIdStatusDup=" + cpdIdStatusDup + ", deptId=" + deptId + ", vendorName=" + vendorName + ", adv_Flg="
                + adv_Flg + ", liveModeDate=" + liveModeDate + ", liveModeDateDup=" + liveModeDateDup + ", sliStatusFlag="
                + sliStatusFlag + ", billDate=" + billDate + ", billPaidAmount=" + billPaidAmount + ", natureOfbill="
                + natureOfbill + "]";
    }

}
