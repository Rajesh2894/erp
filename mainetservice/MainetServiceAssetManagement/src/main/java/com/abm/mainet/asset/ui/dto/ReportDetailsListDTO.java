/**
 * 
 */
package com.abm.mainet.asset.ui.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author sarojkumar.yadav
 *
 */
public class ReportDetailsListDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6352062913037532439L;

    private Long assetId;
    private String serialNo;
    private Long unitsUsed;
    private Date dateOfTransfer;
    private String voucherNo;
    private BigDecimal sourceAmount;
    private String assetDesc;
    private Long assetLocation;
    private String assetLocationDesc;
    private Date dateOfacquisition;
    private Long modeOfAcquisition;
    private String modeOfAcquisitionDesc;
    private String payOrderNo;
    private String refCashBook;
    private String refRegisterAsset;
    private BigDecimal costOfAcquisition;
    private Long paidPersonName;
    private String paidPersonNameDesc;
    private String expensePurpose;
    private String fundSource;
    private Long noOfBldgUsed;
    private BigDecimal openWrittenValue;
    private Long DepreciationYear;
    private String DepreciationYearDesc;
    private BigDecimal depreciation;
    private BigDecimal closeWrittenValue;
    private Date dateOfDisposal;
    private String receiptVoucherNo;
    private String landUsageDesc;
    private Long disposedPersonName;
    private String disposedPersonNameDesc;
    private Long disposedNature;
    private String disposedNatureDesc;
    private Long noOfDisposalOrder;
    private Date dateOfDisposalOrder;
    private Long quantityDisposed;
    private Date soldDate;
    private Long balanceQuantity;
    private BigDecimal retainSecurityDeposit;
    private BigDecimal releasedSecurityDeposit;
    private Date releasedSecurityDepositDate;
    private BigDecimal saleValueRealised;
    private Long autOfficer;
    private String autOfficerDesc;
    private String remarks;
    private BigDecimal balanceAmount;
    private Long orgId;
    private Date creationDate;
    private Long createdBy;
    private Long updatedBy;
    private Date updatedDate;
    private String lgIpMac;
    private String lgIpMacUpd;
    private String dateOfdisposals;
    private String dateOfacquisitions;
    private String assetCode;

    public String getDateOfacquisitions() {
        return dateOfacquisitions;
    }

    public void setDateOfacquisitions(String dateOfacquisitions) {
        this.dateOfacquisitions = dateOfacquisitions;
    }

    public String getDateOfdisposals() {
        return dateOfdisposals;
    }

    public void setDateOfdisposals(String dateOfdisposals) {
        this.dateOfdisposals = dateOfdisposals;
    }

    /**
     * @return the assetId
     */
    public Long getAssetId() {
        return assetId;
    }

    /**
     * @param assetId the assetId to set
     */
    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    /**
     * @return the serialNo
     */
    public String getSerialNo() {
        return serialNo;
    }

    /**
     * @param serialNo the serialNo to set
     */
    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    /**
     * @return the unitsUsed
     */
    public Long getUnitsUsed() {
        return unitsUsed;
    }

    /**
     * @param unitsUsed the unitsUsed to set
     */
    public void setUnitsUsed(Long unitsUsed) {
        this.unitsUsed = unitsUsed;
    }

    /**
     * @return the dateOfTransfer
     */
    public Date getDateOfTransfer() {
        return dateOfTransfer;
    }

    /**
     * @param dateOfTransfer the dateOfTransfer to set
     */
    public void setDateOfTransfer(Date dateOfTransfer) {
        this.dateOfTransfer = dateOfTransfer;
    }

    /**
     * @return the voucherNo
     */
    public String getVoucherNo() {
        return voucherNo;
    }

    /**
     * @param voucherNo the voucherNo to set
     */
    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    /**
     * @return the sourceAmount
     */
    public BigDecimal getSourceAmount() {
        return sourceAmount;
    }

    /**
     * @param sourceAmount the sourceAmount to set
     */
    public void setSourceAmount(BigDecimal sourceAmount) {
        this.sourceAmount = sourceAmount;
    }

    /**
     * @return the assetDesc
     */
    public String getAssetDesc() {
        return assetDesc;
    }

    /**
     * @param assetDesc the assetDesc to set
     */
    public void setAssetDesc(String assetDesc) {
        this.assetDesc = assetDesc;
    }

    /**
     * @return the assetLocation
     */
    public Long getAssetLocation() {
        return assetLocation;
    }

    /**
     * @param assetLocation the assetLocation to set
     */
    public void setAssetLocation(Long assetLocation) {
        this.assetLocation = assetLocation;
    }

    /**
     * @return the assetLocationDesc
     */
    public String getAssetLocationDesc() {
        return assetLocationDesc;
    }

    /**
     * @param assetLocationDesc the assetLocationDesc to set
     */
    public void setAssetLocationDesc(String assetLocationDesc) {
        this.assetLocationDesc = assetLocationDesc;
    }

    /**
     * @return the dateOfacquisition
     */
    public Date getDateOfacquisition() {
        return dateOfacquisition;
    }

    /**
     * @param dateOfacquisition the dateOfacquisition to set
     */
    public void setDateOfacquisition(Date dateOfacquisition) {
        this.dateOfacquisition = dateOfacquisition;
    }

    /**
     * @return the modeOfAcquisition
     */
    public Long getModeOfAcquisition() {
        return modeOfAcquisition;
    }

    /**
     * @param modeOfAcquisition the modeOfAcquisition to set
     */
    public void setModeOfAcquisition(Long modeOfAcquisition) {
        this.modeOfAcquisition = modeOfAcquisition;
    }

    /**
     * @return the modeOfAcquisitionDesc
     */
    public String getModeOfAcquisitionDesc() {
        return modeOfAcquisitionDesc;
    }

    /**
     * @param modeOfAcquisitionDesc the modeOfAcquisitionDesc to set
     */
    public void setModeOfAcquisitionDesc(String modeOfAcquisitionDesc) {
        this.modeOfAcquisitionDesc = modeOfAcquisitionDesc;
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
    public void setPayOrderNo(String payOrderNo) {
        this.payOrderNo = payOrderNo;
    }

    /**
     * @return the refCashBook
     */
    public String getRefCashBook() {
        return refCashBook;
    }

    /**
     * @param refCashBook the refCashBook to set
     */
    public void setRefCashBook(String refCashBook) {
        this.refCashBook = refCashBook;
    }

    /**
     * @return the refRegisterAsset
     */
    public String getRefRegisterAsset() {
        return refRegisterAsset;
    }

    /**
     * @param refRegisterAsset the refRegisterAsset to set
     */
    public void setRefRegisterAsset(String refRegisterAsset) {
        this.refRegisterAsset = refRegisterAsset;
    }

    /**
     * @return the costOfAcquisition
     */
    public BigDecimal getCostOfAcquisition() {
        return costOfAcquisition;
    }

    /**
     * @param costOfAcquisition the costOfAcquisition to set
     */
    public void setCostOfAcquisition(BigDecimal costOfAcquisition) {
        this.costOfAcquisition = costOfAcquisition;
    }

    /**
     * @return the paidPersonName
     */
    public Long getPaidPersonName() {
        return paidPersonName;
    }

    /**
     * @param paidPersonName the paidPersonName to set
     */
    public void setPaidPersonName(Long paidPersonName) {
        this.paidPersonName = paidPersonName;
    }

    /**
     * @return the paidPersonNameDesc
     */
    public String getPaidPersonNameDesc() {
        return paidPersonNameDesc;
    }

    /**
     * @param paidPersonNameDesc the paidPersonNameDesc to set
     */
    public void setPaidPersonNameDesc(String paidPersonNameDesc) {
        this.paidPersonNameDesc = paidPersonNameDesc;
    }

    /**
     * @return the expensePurpose
     */
    public String getExpensePurpose() {
        return expensePurpose;
    }

    /**
     * @param expensePurpose the expensePurpose to set
     */
    public void setExpensePurpose(String expensePurpose) {
        this.expensePurpose = expensePurpose;
    }

    /**
     * @return the fundSource
     */
    public String getFundSource() {
        return fundSource;
    }

    /**
     * @param fundSource the fundSource to set
     */
    public void setFundSource(String fundSource) {
        this.fundSource = fundSource;
    }

    /**
     * @return the noOfBldgUsed
     */
    public Long getNoOfBldgUsed() {
        return noOfBldgUsed;
    }

    /**
     * @param noOfBldgUsed the noOfBldgUsed to set
     */
    public void setNoOfBldgUsed(Long noOfBldgUsed) {
        this.noOfBldgUsed = noOfBldgUsed;
    }

    /**
     * @return the openWrittenValue
     */
    public BigDecimal getOpenWrittenValue() {
        return openWrittenValue;
    }

    /**
     * @param openWrittenValue the openWrittenValue to set
     */
    public void setOpenWrittenValue(BigDecimal openWrittenValue) {
        this.openWrittenValue = openWrittenValue;
    }

    /**
     * @return the depreciationYear
     */
    public Long getDepreciationYear() {
        return DepreciationYear;
    }

    /**
     * @param depreciationYear the depreciationYear to set
     */
    public void setDepreciationYear(Long depreciationYear) {
        DepreciationYear = depreciationYear;
    }

    /**
     * @return the depreciationYearDesc
     */
    public String getDepreciationYearDesc() {
        return DepreciationYearDesc;
    }

    /**
     * @param depreciationYearDesc the depreciationYearDesc to set
     */
    public void setDepreciationYearDesc(String depreciationYearDesc) {
        DepreciationYearDesc = depreciationYearDesc;
    }

    /**
     * @return the depreciation
     */
    public BigDecimal getDepreciation() {
        return depreciation;
    }

    /**
     * @param depreciation the depreciation to set
     */
    public void setDepreciation(BigDecimal depreciation) {
        this.depreciation = depreciation;
    }

    /**
     * @return the closeWrittenValue
     */
    public BigDecimal getCloseWrittenValue() {
        return closeWrittenValue;
    }

    /**
     * @param closeWrittenValue the closeWrittenValue to set
     */
    public void setCloseWrittenValue(BigDecimal closeWrittenValue) {
        this.closeWrittenValue = closeWrittenValue;
    }

    /**
     * @return the dateOfDisposal
     */
    public Date getDateOfDisposal() {
        return dateOfDisposal;
    }

    /**
     * @param dateOfDisposal the dateOfDisposal to set
     */
    public void setDateOfDisposal(Date dateOfDisposal) {
        this.dateOfDisposal = dateOfDisposal;
    }

    /**
     * @return the receiptVoucherNo
     */
    public String getReceiptVoucherNo() {
        return receiptVoucherNo;
    }

    /**
     * @param receiptVoucherNo the receiptVoucherNo to set
     */
    public void setReceiptVoucherNo(String receiptVoucherNo) {
        this.receiptVoucherNo = receiptVoucherNo;
    }

    /**
     * @return the landUsageDesc
     */
    public String getLandUsageDesc() {
        return landUsageDesc;
    }

    /**
     * @param landUsageDesc the landUsageDesc to set
     */
    public void setLandUsageDesc(String landUsageDesc) {
        this.landUsageDesc = landUsageDesc;
    }

    /**
     * @return the disposedPersonName
     */
    public Long getDisposedPersonName() {
        return disposedPersonName;
    }

    /**
     * @param disposedPersonName the disposedPersonName to set
     */
    public void setDisposedPersonName(Long disposedPersonName) {
        this.disposedPersonName = disposedPersonName;
    }

    /**
     * @return the disposedPersonNameDesc
     */
    public String getDisposedPersonNameDesc() {
        return disposedPersonNameDesc;
    }

    /**
     * @param disposedPersonNameDesc the disposedPersonNameDesc to set
     */
    public void setDisposedPersonNameDesc(String disposedPersonNameDesc) {
        this.disposedPersonNameDesc = disposedPersonNameDesc;
    }

    /**
     * @return the disposedNature
     */
    public Long getDisposedNature() {
        return disposedNature;
    }

    /**
     * @param disposedNature the disposedNature to set
     */
    public void setDisposedNature(Long disposedNature) {
        this.disposedNature = disposedNature;
    }

    /**
     * @return the disposedNatureDesc
     */
    public String getDisposedNatureDesc() {
        return disposedNatureDesc;
    }

    /**
     * @param disposedNatureDesc the disposedNatureDesc to set
     */
    public void setDisposedNatureDesc(String disposedNatureDesc) {
        this.disposedNatureDesc = disposedNatureDesc;
    }

    /**
     * @return the noOfDisposalOrder
     */
    public Long getNoOfDisposalOrder() {
        return noOfDisposalOrder;
    }

    /**
     * @param noOfDisposalOrder the noOfDisposalOrder to set
     */
    public void setNoOfDisposalOrder(Long noOfDisposalOrder) {
        this.noOfDisposalOrder = noOfDisposalOrder;
    }

    /**
     * @return the dateOfDisposalOrder
     */
    public Date getDateOfDisposalOrder() {
        return dateOfDisposalOrder;
    }

    /**
     * @param dateOfDisposalOrder the dateOfDisposalOrder to set
     */
    public void setDateOfDisposalOrder(Date dateOfDisposalOrder) {
        this.dateOfDisposalOrder = dateOfDisposalOrder;
    }

    /**
     * @return the quantityDisposed
     */
    public Long getQuantityDisposed() {
        return quantityDisposed;
    }

    /**
     * @param quantityDisposed the quantityDisposed to set
     */
    public void setQuantityDisposed(Long quantityDisposed) {
        this.quantityDisposed = quantityDisposed;
    }

    /**
     * @return the soldDate
     */
    public Date getSoldDate() {
        return soldDate;
    }

    /**
     * @param soldDate the soldDate to set
     */
    public void setSoldDate(Date soldDate) {
        this.soldDate = soldDate;
    }

    /**
     * @return the balanceQuantity
     */
    public Long getBalanceQuantity() {
        return balanceQuantity;
    }

    /**
     * @param balanceQuantity the balanceQuantity to set
     */
    public void setBalanceQuantity(Long balanceQuantity) {
        this.balanceQuantity = balanceQuantity;
    }

    /**
     * @return the retainSecurityDeposit
     */
    public BigDecimal getRetainSecurityDeposit() {
        return retainSecurityDeposit;
    }

    /**
     * @param retainSecurityDeposit the retainSecurityDeposit to set
     */
    public void setRetainSecurityDeposit(BigDecimal retainSecurityDeposit) {
        this.retainSecurityDeposit = retainSecurityDeposit;
    }

    /**
     * @return the releasedSecurityDeposit
     */
    public BigDecimal getReleasedSecurityDeposit() {
        return releasedSecurityDeposit;
    }

    /**
     * @param releasedSecurityDeposit the releasedSecurityDeposit to set
     */
    public void setReleasedSecurityDeposit(BigDecimal releasedSecurityDeposit) {
        this.releasedSecurityDeposit = releasedSecurityDeposit;
    }

    /**
     * @return the releasedSecurityDepositDate
     */
    public Date getReleasedSecurityDepositDate() {
        return releasedSecurityDepositDate;
    }

    /**
     * @param releasedSecurityDepositDate the releasedSecurityDepositDate to set
     */
    public void setReleasedSecurityDepositDate(Date releasedSecurityDepositDate) {
        this.releasedSecurityDepositDate = releasedSecurityDepositDate;
    }

    /**
     * @return the saleValueRealised
     */
    public BigDecimal getSaleValueRealised() {
        return saleValueRealised;
    }

    /**
     * @param saleValueRealised the saleValueRealised to set
     */
    public void setSaleValueRealised(BigDecimal saleValueRealised) {
        this.saleValueRealised = saleValueRealised;
    }

    /**
     * @return the autOfficer
     */
    public Long getAutOfficer() {
        return autOfficer;
    }

    /**
     * @param autOfficer the autOfficer to set
     */
    public void setAutOfficer(Long autOfficer) {
        this.autOfficer = autOfficer;
    }

    /**
     * @return the autOfficerDesc
     */
    public String getAutOfficerDesc() {
        return autOfficerDesc;
    }

    /**
     * @param autOfficerDesc the autOfficerDesc to set
     */
    public void setAutOfficerDesc(String autOfficerDesc) {
        this.autOfficerDesc = autOfficerDesc;
    }

    /**
     * @return the remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * @param remarks the remarks to set
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * @return the balanceAmount
     */
    public BigDecimal getBalanceAmount() {
        return balanceAmount;
    }

    /**
     * @param balanceAmount the balanceAmount to set
     */
    public void setBalanceAmount(BigDecimal balanceAmount) {
        this.balanceAmount = balanceAmount;
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
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    /**
     * @return the creationDate
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * @param creationDate the creationDate to set
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
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
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the updatedBy
     */
    public Long getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    public void setUpdatedBy(Long updatedBy) {
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
    public void setUpdatedDate(Date updatedDate) {
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
    public void setLgIpMac(String lgIpMac) {
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
    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    @Override
    public String toString() {
        return "ReportDetailsListDTO [assetId=" + assetId + ", serialNo=" + serialNo + ", unitsUsed=" + unitsUsed
                + ", dateOfTransfer=" + dateOfTransfer + ", voucherNo=" + voucherNo + ", sourceAmount=" + sourceAmount
                + ", assetDesc=" + assetDesc + ", assetLocation=" + assetLocation + ", assetLocationDesc=" + assetLocationDesc
                + ", dateOfacquisition=" + dateOfacquisition + ", modeOfAcquisition=" + modeOfAcquisition
                + ", modeOfAcquisitionDesc=" + modeOfAcquisitionDesc + ", payOrderNo=" + payOrderNo + ", refCashBook="
                + refCashBook + ", refRegisterAsset=" + refRegisterAsset + ", costOfAcquisition=" + costOfAcquisition
                + ", paidPersonName=" + paidPersonName + ", paidPersonNameDesc=" + paidPersonNameDesc + ", expensePurpose="
                + expensePurpose + ", fundSource=" + fundSource + ", noOfBldgUsed=" + noOfBldgUsed + ", openWrittenValue="
                + openWrittenValue + ", DepreciationYear=" + DepreciationYear + ", DepreciationYearDesc=" + DepreciationYearDesc
                + ", depreciation=" + depreciation + ", closeWrittenValue=" + closeWrittenValue + ", dateOfDisposal="
                + dateOfDisposal + ", receiptVoucherNo=" + receiptVoucherNo + ", landUsageDesc=" + landUsageDesc
                + ", disposedPersonName=" + disposedPersonName + ", disposedPersonNameDesc=" + disposedPersonNameDesc
                + ", disposedNature=" + disposedNature + ", disposedNatureDesc=" + disposedNatureDesc + ", noOfDisposalOrder="
                + noOfDisposalOrder + ", dateOfDisposalOrder=" + dateOfDisposalOrder + ", quantityDisposed=" + quantityDisposed
                + ", soldDate=" + soldDate + ", balanceQuantity=" + balanceQuantity + ", retainSecurityDeposit="
                + retainSecurityDeposit + ", releasedSecurityDeposit=" + releasedSecurityDeposit
                + ", releasedSecurityDepositDate=" + releasedSecurityDepositDate + ", saleValueRealised=" + saleValueRealised
                + ", autOfficer=" + autOfficer + ", autOfficerDesc=" + autOfficerDesc + ", remarks=" + remarks
                + ", balanceAmount=" + balanceAmount + ", orgId=" + orgId + ", creationDate=" + creationDate + ", createdBy="
                + createdBy + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + ", lgIpMac=" + lgIpMac
                + ", lgIpMacUpd=" + lgIpMacUpd + ", dateOfdisposals=" + dateOfdisposals + ", dateOfacquisitions="
                + dateOfacquisitions + ", assetCode=" + assetCode + "]";
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */

}
