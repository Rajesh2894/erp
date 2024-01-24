/**
 * 
 */
package com.abm.mainet.common.integration.asset.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * DTO class for Asset Management Leasing Company
 * 
 * @author sarojkumar.yadav
 *
 */
@XmlRootElement(name = "AssetLeasingDetails")
public class AssetLeasingCompanyDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 936372032677991657L;

    private Long assetLeasingId;
    private Long assetId;
    private Long contractAgreementNo;
    private Date agreementDate;
    private Date noticeDate;
    private Date leaseStartDate;
    private Date leaseEndDate;
    private Long leaseType;
    private BigDecimal purchasePrice;
    private Long noOfInstallment;
    private Long paymentFrequency;
    private BigDecimal advancedPayment;
    private Date creationDate;
    private Long createdBy;
    private Long updatedBy;
    private Date updatedDate;
    private String lgIpMac;
    private String lgIpMacUpd;

    /**
     * @return the assetLeasingId
     */
    public Long getAssetLeasingId() {
        return assetLeasingId;
    }

    /**
     * @param assetLeasingId the assetLeasingId to set
     */
    public void setAssetLeasingId(Long assetLeasingId) {
        this.assetLeasingId = assetLeasingId;
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
     * @return the contractAgreementNo
     */
    public Long getContractAgreementNo() {
        return contractAgreementNo;
    }

    /**
     * @param contractAgreementNo the contractAgreementNo to set
     */
    public void setContractAgreementNo(Long contractAgreementNo) {
        this.contractAgreementNo = contractAgreementNo;
    }

    /**
     * @return the agreementDate
     */
    public Date getAgreementDate() {
        return agreementDate;
    }

    /**
     * @param agreementDate the agreementDate to set
     */
    public void setAgreementDate(Date agreementDate) {
        this.agreementDate = agreementDate;
    }

    /**
     * @return the noticeDate
     */
    public Date getNoticeDate() {
        return noticeDate;
    }

    /**
     * @param noticeDate the noticeDate to set
     */
    public void setNoticeDate(Date noticeDate) {
        this.noticeDate = noticeDate;
    }

    /**
     * @return the leaseStartDate
     */
    public Date getLeaseStartDate() {
        return leaseStartDate;
    }

    /**
     * @param leaseStartDate the leaseStartDate to set
     */
    public void setLeaseStartDate(Date leaseStartDate) {
        this.leaseStartDate = leaseStartDate;
    }

    /**
     * @return the leaseEndDate
     */
    public Date getLeaseEndDate() {
        return leaseEndDate;
    }

    /**
     * @param leaseEndDate the leaseEndDate to set
     */
    public void setLeaseEndDate(Date leaseEndDate) {
        this.leaseEndDate = leaseEndDate;
    }

    /**
     * @return the leaseType
     */
    public Long getLeaseType() {
        return leaseType;
    }

    /**
     * @param leaseType the leaseType to set
     */
    public void setLeaseType(Long leaseType) {
        this.leaseType = leaseType;
    }

    /**
     * @return the purchasePrice
     */
    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    /**
     * @param purchasePrice the purchasePrice to set
     */
    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    /**
     * @return the noOfInstallment
     */
    public Long getNoOfInstallment() {
        return noOfInstallment;
    }

    /**
     * @param noOfInstallment the noOfInstallment to set
     */
    public void setNoOfInstallment(Long noOfInstallment) {
        this.noOfInstallment = noOfInstallment;
    }

    /**
     * @return the paymentFrequency
     */
    public Long getPaymentFrequency() {
        return paymentFrequency;
    }

    /**
     * @param paymentFrequency the paymentFrequency to set
     */
    public void setPaymentFrequency(Long paymentFrequency) {
        this.paymentFrequency = paymentFrequency;
    }

    /**
     * @return the advancedPayment
     */
    public BigDecimal getAdvancedPayment() {
        return advancedPayment;
    }

    /**
     * @param advancedPayment the advancedPayment to set
     */
    public void setAdvancedPayment(BigDecimal advancedPayment) {
        this.advancedPayment = advancedPayment;
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

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "AssetLeasingCompanyDTO [assetLeasingId=" + assetLeasingId + ", assetId=" + assetId + ", contractAgreementNo="
                + contractAgreementNo + ", agreementDate=" + agreementDate + ", noticeDate=" + noticeDate + ", leaseStartDate="
                + leaseStartDate + ", leaseEndDate=" + leaseEndDate + ", leaseType=" + leaseType + ", purchasePrice="
                + purchasePrice + ", noOfInstallment=" + noOfInstallment + ", paymentFrequency=" + paymentFrequency
                + ", advancedPayment=" + advancedPayment + ", creationDate=" + creationDate + ", createdBy=" + createdBy
                + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd="
                + lgIpMacUpd + "]";
    }

}
