package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AccountBillEntryMasterHistDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long hBmId;
    private Long id;
    private String billNo;
    private String billEntryDate;
    private String vendorName;
    private String invoiceNumber;
    private Date invoiceDate;
    private BigDecimal invoiceValue;
    private String workOrPurchaseOrderNumber;
    private Date workOrPurchaseOrderDate;
    private String resolutionNumber;
    private Date resolutionDate;
    private String narration;
    private BigDecimal billTotalAmount;
    private BigDecimal balanceAmount;
    private Long billIntRefId;
    private Long loanId;
    private Long advanceTypeId;
    private Character checkerAuthorization;
    private String checkerRemarks;
    private Long checkerUser;
    private Date checkerDate;
    private Long orgId;
    private String createdBy;
    private String createdDate;
    private String updatedBy;
    private String updatedDate;
    private Long languageId;
    @JsonIgnore
    @Size(max = 100)
    private String lgIpMacAddress;
    @JsonIgnore
    @Size(max = 100)
    private String lgIpMacAddressUpdated;
    private Long fi04N1;
    private String billDeletionFlag;
    private Date billDeletionDate;
    private Date deletionPostingDate;
    private String billDeletionOrderNo;
    private Long billDeletionAuthorizedBy;
    private String billDeletionRemark;
    private Character payStatus;
    private Long fieldId;
    private Character hStatus;
    private String fromDate;
    private String toDate;
    private String secHeadId;
    private String billChargeAmt;
    private String secHeadId1;
    private String deductionAmt;

    public String getDeductionAmt() {
        return deductionAmt;
    }

    public void setDeductionAmt(String deductionAmt) {
        this.deductionAmt = deductionAmt;
    }

    private List<AccountBillEntryMasterHistDto> listOfmasterData = new ArrayList<>();

    public Long gethBmId() {
        return hBmId;
    }

    public void sethBmId(Long hBmId) {
        this.hBmId = hBmId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getBillEntryDate() {
        return billEntryDate;
    }

    public void setBillEntryDate(String billEntryDate) {
        this.billEntryDate = billEntryDate;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public BigDecimal getInvoiceValue() {
        return invoiceValue;
    }

    public void setInvoiceValue(BigDecimal invoiceValue) {
        this.invoiceValue = invoiceValue;
    }

    public String getWorkOrPurchaseOrderNumber() {
        return workOrPurchaseOrderNumber;
    }

    public void setWorkOrPurchaseOrderNumber(String workOrPurchaseOrderNumber) {
        this.workOrPurchaseOrderNumber = workOrPurchaseOrderNumber;
    }

    public Date getWorkOrPurchaseOrderDate() {
        return workOrPurchaseOrderDate;
    }

    public void setWorkOrPurchaseOrderDate(Date workOrPurchaseOrderDate) {
        this.workOrPurchaseOrderDate = workOrPurchaseOrderDate;
    }

    public String getResolutionNumber() {
        return resolutionNumber;
    }

    public void setResolutionNumber(String resolutionNumber) {
        this.resolutionNumber = resolutionNumber;
    }

    public Date getResolutionDate() {
        return resolutionDate;
    }

    public void setResolutionDate(Date resolutionDate) {
        this.resolutionDate = resolutionDate;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public BigDecimal getBillTotalAmount() {
        return billTotalAmount;
    }

    public void setBillTotalAmount(BigDecimal billTotalAmount) {
        this.billTotalAmount = billTotalAmount;
    }

    public BigDecimal getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(BigDecimal balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public Long getBillIntRefId() {
        return billIntRefId;
    }

    public void setBillIntRefId(Long billIntRefId) {
        this.billIntRefId = billIntRefId;
    }

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public Long getAdvanceTypeId() {
        return advanceTypeId;
    }

    public void setAdvanceTypeId(Long advanceTypeId) {
        this.advanceTypeId = advanceTypeId;
    }

    public Character getCheckerAuthorization() {
        return checkerAuthorization;
    }

    public void setCheckerAuthorization(Character checkerAuthorization) {
        this.checkerAuthorization = checkerAuthorization;
    }

    public String getCheckerRemarks() {
        return checkerRemarks;
    }

    public void setCheckerRemarks(String checkerRemarks) {
        this.checkerRemarks = checkerRemarks;
    }

    public Long getCheckerUser() {
        return checkerUser;
    }

    public void setCheckerUser(Long checkerUser) {
        this.checkerUser = checkerUser;
    }

    public Date getCheckerDate() {
        return checkerDate;
    }

    public void setCheckerDate(Date checkerDate) {
        this.checkerDate = checkerDate;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Long languageId) {
        this.languageId = languageId;
    }

    public String getLgIpMacAddress() {
        return lgIpMacAddress;
    }

    public void setLgIpMacAddress(String lgIpMacAddress) {
        this.lgIpMacAddress = lgIpMacAddress;
    }

    public String getLgIpMacAddressUpdated() {
        return lgIpMacAddressUpdated;
    }

    public void setLgIpMacAddressUpdated(String lgIpMacAddressUpdated) {
        this.lgIpMacAddressUpdated = lgIpMacAddressUpdated;
    }

    public Long getFi04N1() {
        return fi04N1;
    }

    public void setFi04N1(Long fi04n1) {
        fi04N1 = fi04n1;
    }

    public String getBillDeletionFlag() {
        return billDeletionFlag;
    }

    public void setBillDeletionFlag(String billDeletionFlag) {
        this.billDeletionFlag = billDeletionFlag;
    }

    public Date getBillDeletionDate() {
        return billDeletionDate;
    }

    public void setBillDeletionDate(Date billDeletionDate) {
        this.billDeletionDate = billDeletionDate;
    }

    public Date getDeletionPostingDate() {
        return deletionPostingDate;
    }

    public void setDeletionPostingDate(Date deletionPostingDate) {
        this.deletionPostingDate = deletionPostingDate;
    }

    public String getBillDeletionOrderNo() {
        return billDeletionOrderNo;
    }

    public void setBillDeletionOrderNo(String billDeletionOrderNo) {
        this.billDeletionOrderNo = billDeletionOrderNo;
    }

    public Long getBillDeletionAuthorizedBy() {
        return billDeletionAuthorizedBy;
    }

    public void setBillDeletionAuthorizedBy(Long billDeletionAuthorizedBy) {
        this.billDeletionAuthorizedBy = billDeletionAuthorizedBy;
    }

    public String getBillDeletionRemark() {
        return billDeletionRemark;
    }

    public void setBillDeletionRemark(String billDeletionRemark) {
        this.billDeletionRemark = billDeletionRemark;
    }

    public Character getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Character payStatus) {
        this.payStatus = payStatus;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    public Character gethStatus() {
        return hStatus;
    }

    public void sethStatus(Character hStatus) {
        this.hStatus = hStatus;
    }

    public List<AccountBillEntryMasterHistDto> getListOfmasterData() {
        return listOfmasterData;
    }

    public void setListOfmasterData(List<AccountBillEntryMasterHistDto> listOfmasterData) {
        this.listOfmasterData = listOfmasterData;
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

    @Override
    public String toString() {
        return "AccountBillEntryMasterHistDto [hBmId=" + hBmId + ", id=" + id + ", billNo=" + billNo
                + ", billEntryDate=" + billEntryDate + ", vendorName=" + vendorName + ", invoiceNumber=" + invoiceNumber
                + ", invoiceDate=" + invoiceDate + ", invoiceValue=" + invoiceValue + ", workOrPurchaseOrderNumber="
                + workOrPurchaseOrderNumber + ", workOrPurchaseOrderDate=" + workOrPurchaseOrderDate
                + ", resolutionNumber=" + resolutionNumber + ", resolutionDate=" + resolutionDate + ", narration="
                + narration + ", billTotalAmount=" + billTotalAmount + ", balanceAmount=" + balanceAmount
                + ", billIntRefId=" + billIntRefId + ", loanId=" + loanId + ", advanceTypeId=" + advanceTypeId
                + ", checkerAuthorization=" + checkerAuthorization + ", checkerRemarks=" + checkerRemarks
                + ", checkerUser=" + checkerUser + ", checkerDate=" + checkerDate + ", orgId=" + orgId + ", createdBy="
                + createdBy + ", createdDate=" + createdDate + ", updatedBy=" + updatedBy + ", updatedDate="
                + updatedDate + ", languageId=" + languageId + ", lgIpMacAddress=" + lgIpMacAddress
                + ", lgIpMacAddressUpdated=" + lgIpMacAddressUpdated + ", fi04N1=" + fi04N1 + ", billDeletionFlag="
                + billDeletionFlag + ", billDeletionDate=" + billDeletionDate + ", deletionPostingDate="
                + deletionPostingDate + ", billDeletionOrderNo=" + billDeletionOrderNo + ", billDeletionAuthorizedBy="
                + billDeletionAuthorizedBy + ", billDeletionRemark=" + billDeletionRemark + ", payStatus=" + payStatus
                + ", fieldId=" + fieldId + ", hStatus=" + hStatus + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((advanceTypeId == null) ? 0 : advanceTypeId.hashCode());
        result = prime * result + ((balanceAmount == null) ? 0 : balanceAmount.hashCode());
        result = prime * result + ((billDeletionAuthorizedBy == null) ? 0 : billDeletionAuthorizedBy.hashCode());
        result = prime * result + ((billDeletionDate == null) ? 0 : billDeletionDate.hashCode());
        result = prime * result + ((billDeletionFlag == null) ? 0 : billDeletionFlag.hashCode());
        result = prime * result + ((billDeletionOrderNo == null) ? 0 : billDeletionOrderNo.hashCode());
        result = prime * result + ((billDeletionRemark == null) ? 0 : billDeletionRemark.hashCode());
        result = prime * result + ((billEntryDate == null) ? 0 : billEntryDate.hashCode());
        result = prime * result + ((billIntRefId == null) ? 0 : billIntRefId.hashCode());
        result = prime * result + ((billNo == null) ? 0 : billNo.hashCode());
        result = prime * result + ((billTotalAmount == null) ? 0 : billTotalAmount.hashCode());
        result = prime * result + ((checkerAuthorization == null) ? 0 : checkerAuthorization.hashCode());
        result = prime * result + ((checkerDate == null) ? 0 : checkerDate.hashCode());
        result = prime * result + ((checkerRemarks == null) ? 0 : checkerRemarks.hashCode());
        result = prime * result + ((checkerUser == null) ? 0 : checkerUser.hashCode());
        result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
        result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
        result = prime * result + ((deletionPostingDate == null) ? 0 : deletionPostingDate.hashCode());
        result = prime * result + ((fi04N1 == null) ? 0 : fi04N1.hashCode());
        result = prime * result + ((fieldId == null) ? 0 : fieldId.hashCode());
        result = prime * result + ((hBmId == null) ? 0 : hBmId.hashCode());
        result = prime * result + ((invoiceDate == null) ? 0 : invoiceDate.hashCode());
        result = prime * result + ((invoiceNumber == null) ? 0 : invoiceNumber.hashCode());
        result = prime * result + ((invoiceValue == null) ? 0 : invoiceValue.hashCode());
        result = prime * result + ((languageId == null) ? 0 : languageId.hashCode());
        result = prime * result + ((lgIpMacAddress == null) ? 0 : lgIpMacAddress.hashCode());
        result = prime * result + ((lgIpMacAddressUpdated == null) ? 0 : lgIpMacAddressUpdated.hashCode());
        result = prime * result + ((loanId == null) ? 0 : loanId.hashCode());
        result = prime * result + ((narration == null) ? 0 : narration.hashCode());
        result = prime * result + ((orgId == null) ? 0 : orgId.hashCode());
        result = prime * result + ((payStatus == null) ? 0 : payStatus.hashCode());
        result = prime * result + ((resolutionDate == null) ? 0 : resolutionDate.hashCode());
        result = prime * result + ((resolutionNumber == null) ? 0 : resolutionNumber.hashCode());
        result = prime * result + ((updatedBy == null) ? 0 : updatedBy.hashCode());
        result = prime * result + ((updatedDate == null) ? 0 : updatedDate.hashCode());
        result = prime * result + ((vendorName == null) ? 0 : vendorName.hashCode());
        result = prime * result + ((workOrPurchaseOrderDate == null) ? 0 : workOrPurchaseOrderDate.hashCode());
        result = prime * result + ((workOrPurchaseOrderNumber == null) ? 0 : workOrPurchaseOrderNumber.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AccountBillEntryMasterHistDto other = (AccountBillEntryMasterHistDto) obj;
        if (advanceTypeId == null) {
            if (other.advanceTypeId != null)
                return false;
        } else if (!advanceTypeId.equals(other.advanceTypeId))
            return false;
        if (balanceAmount == null) {
            if (other.balanceAmount != null)
                return false;
        } else if (!balanceAmount.equals(other.balanceAmount))
            return false;
        if (billDeletionAuthorizedBy == null) {
            if (other.billDeletionAuthorizedBy != null)
                return false;
        } else if (!billDeletionAuthorizedBy.equals(other.billDeletionAuthorizedBy))
            return false;
        if (billDeletionDate == null) {
            if (other.billDeletionDate != null)
                return false;
        } else if (!billDeletionDate.equals(other.billDeletionDate))
            return false;
        if (billDeletionFlag == null) {
            if (other.billDeletionFlag != null)
                return false;
        } else if (!billDeletionFlag.equals(other.billDeletionFlag))
            return false;
        if (billDeletionOrderNo == null) {
            if (other.billDeletionOrderNo != null)
                return false;
        } else if (!billDeletionOrderNo.equals(other.billDeletionOrderNo))
            return false;
        if (billDeletionRemark == null) {
            if (other.billDeletionRemark != null)
                return false;
        } else if (!billDeletionRemark.equals(other.billDeletionRemark))
            return false;
        if (billEntryDate == null) {
            if (other.billEntryDate != null)
                return false;
        } else if (!billEntryDate.equals(other.billEntryDate))
            return false;
        if (billIntRefId == null) {
            if (other.billIntRefId != null)
                return false;
        } else if (!billIntRefId.equals(other.billIntRefId))
            return false;
        if (billNo == null) {
            if (other.billNo != null)
                return false;
        } else if (!billNo.equals(other.billNo))
            return false;
        if (billTotalAmount == null) {
            if (other.billTotalAmount != null)
                return false;
        } else if (!billTotalAmount.equals(other.billTotalAmount))
            return false;
        if (checkerAuthorization == null) {
            if (other.checkerAuthorization != null)
                return false;
        } else if (!checkerAuthorization.equals(other.checkerAuthorization))
            return false;
        if (checkerDate == null) {
            if (other.checkerDate != null)
                return false;
        } else if (!checkerDate.equals(other.checkerDate))
            return false;
        if (checkerRemarks == null) {
            if (other.checkerRemarks != null)
                return false;
        } else if (!checkerRemarks.equals(other.checkerRemarks))
            return false;
        if (checkerUser == null) {
            if (other.checkerUser != null)
                return false;
        } else if (!checkerUser.equals(other.checkerUser))
            return false;
        if (createdBy == null) {
            if (other.createdBy != null)
                return false;
        } else if (!createdBy.equals(other.createdBy))
            return false;
        if (createdDate == null) {
            if (other.createdDate != null)
                return false;
        } else if (!createdDate.equals(other.createdDate))
            return false;
        if (deletionPostingDate == null) {
            if (other.deletionPostingDate != null)
                return false;
        } else if (!deletionPostingDate.equals(other.deletionPostingDate))
            return false;
        if (fi04N1 == null) {
            if (other.fi04N1 != null)
                return false;
        } else if (!fi04N1.equals(other.fi04N1))
            return false;
        if (fieldId == null) {
            if (other.fieldId != null)
                return false;
        } else if (!fieldId.equals(other.fieldId))
            return false;
        if (hBmId == null) {
            if (other.hBmId != null)
                return false;
        } else if (!hBmId.equals(other.hBmId))
            return false;
        if (invoiceDate == null) {
            if (other.invoiceDate != null)
                return false;
        } else if (!invoiceDate.equals(other.invoiceDate))
            return false;
        if (invoiceNumber == null) {
            if (other.invoiceNumber != null)
                return false;
        } else if (!invoiceNumber.equals(other.invoiceNumber))
            return false;
        if (invoiceValue == null) {
            if (other.invoiceValue != null)
                return false;
        } else if (!invoiceValue.equals(other.invoiceValue))
            return false;
        if (languageId == null) {
            if (other.languageId != null)
                return false;
        } else if (!languageId.equals(other.languageId))
            return false;
        if (lgIpMacAddress == null) {
            if (other.lgIpMacAddress != null)
                return false;
        } else if (!lgIpMacAddress.equals(other.lgIpMacAddress))
            return false;
        if (lgIpMacAddressUpdated == null) {
            if (other.lgIpMacAddressUpdated != null)
                return false;
        } else if (!lgIpMacAddressUpdated.equals(other.lgIpMacAddressUpdated))
            return false;
        if (loanId == null) {
            if (other.loanId != null)
                return false;
        } else if (!loanId.equals(other.loanId))
            return false;
        if (narration == null) {
            if (other.narration != null)
                return false;
        } else if (!narration.equals(other.narration))
            return false;
        if (orgId == null) {
            if (other.orgId != null)
                return false;
        } else if (!orgId.equals(other.orgId))
            return false;
        if (payStatus == null) {
            if (other.payStatus != null)
                return false;
        } else if (!payStatus.equals(other.payStatus))
            return false;
        if (resolutionDate == null) {
            if (other.resolutionDate != null)
                return false;
        } else if (!resolutionDate.equals(other.resolutionDate))
            return false;
        if (resolutionNumber == null) {
            if (other.resolutionNumber != null)
                return false;
        } else if (!resolutionNumber.equals(other.resolutionNumber))
            return false;
        if (updatedBy == null) {
            if (other.updatedBy != null)
                return false;
        } else if (!updatedBy.equals(other.updatedBy))
            return false;
        if (updatedDate == null) {
            if (other.updatedDate != null)
                return false;
        } else if (!updatedDate.equals(other.updatedDate))
            return false;
        if (vendorName == null) {
            if (other.vendorName != null)
                return false;
        } else if (!vendorName.equals(other.vendorName))
            return false;
        if (workOrPurchaseOrderDate == null) {
            if (other.workOrPurchaseOrderDate != null)
                return false;
        } else if (!workOrPurchaseOrderDate.equals(other.workOrPurchaseOrderDate))
            return false;
        if (workOrPurchaseOrderNumber == null) {
            if (other.workOrPurchaseOrderNumber != null)
                return false;
        } else if (!workOrPurchaseOrderNumber.equals(other.workOrPurchaseOrderNumber))
            return false;
        return true;
    }

    public String getSecHeadId() {
        return secHeadId;
    }

    public void setSecHeadId(String secHeadId) {
        this.secHeadId = secHeadId;
    }

    public String getBillChargeAmt() {
        return billChargeAmt;
    }

    public void setBillChargeAmt(String billChargeAmt) {
        this.billChargeAmt = billChargeAmt;
    }

    public String getSecHeadId1() {
        return secHeadId1;
    }

    public void setSecHeadId1(String secHeadId1) {
        this.secHeadId1 = secHeadId1;
    }

}
