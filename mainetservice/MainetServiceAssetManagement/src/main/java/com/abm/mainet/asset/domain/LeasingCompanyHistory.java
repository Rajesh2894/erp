/**
 * 
 */
package com.abm.mainet.asset.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.constant.MainetConstants;

/**
 * @author satish.rathore
 *
 */
@Entity
@Table(name = "TB_AST_LEASING_HIST")
public class LeasingCompanyHistory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4917888487583401812L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "ASSET_LEASING_HIST_ID", nullable = false)
	private Long astLeaseHistId;

	@Column(name = "ASSET_LEASING_ID", nullable = false)
	private Long assetLeasingID;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ASSET_ID", referencedColumnName = "ASSET_ID", nullable = false, updatable = false)
	private AssetInformation assetId;

	@Column(name = "CONTRACT_AGREEMENT_NO", nullable = true)
	private Long contractAgreementNo;

	@Column(name = "AGREEMENT_DATE", nullable = true)
	private Date agreementDate;

	@Column(name = "NOTICE_DATE", nullable = true)
	private Date noticeDate;

	@Column(name = "LEASE_START_DATE", nullable = true)
	private Date leaseStartDate;

	@Column(name = "LEASE_END_DATE", nullable = true)
	private Date leaseEndDate;

	@Column(name = "LEASE_TYPE", nullable = true)
	private Long leaseType;

	@Column(name = "PURCHASE_PRIZE", nullable = true)
	private BigDecimal purchasePrice;

	@Column(name = "NO_OF_INSTALLMENT", nullable = true)
	private Long noOfInstallment;

	@Column(name = "PAYMENT_FREQUENCY", nullable = true)
	private Long paymentFrequency;

	@Column(name = "ADVANCE_PAYMENT", nullable = true)
	private BigDecimal advancedPayment;

	@Column(name = "CREATION_DATE", nullable = true)
	private Date createdDate;

	@Column(name = "CREATED_BY", nullable = true)
	private Long createdBy;

	@Column(name = "UPDATED_BY", nullable = true)
	private Long updatedBy;

	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;

	@Column(name = "LG_IP_MAC", nullable = true)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", nullable = true)
	private String lgIpMacUpd;

	@Column(name = "H_STATUS", nullable = true)
	private String historyFlag;

	public Long getAstLeaseHistId() {
		return astLeaseHistId;
	}

	public void setAstLeaseHistId(Long astLeaseHistId) {
		this.astLeaseHistId = astLeaseHistId;
	}

	public Long getAssetLeasingID() {
		return assetLeasingID;
	}

	public void setAssetLeasingID(Long assetLeasingID) {
		this.assetLeasingID = assetLeasingID;
	}

	public AssetInformation getAssetId() {
		return assetId;
	}

	public void setAssetId(AssetInformation assetId) {
		this.assetId = assetId;
	}

	public Long getContractAgreementNo() {
		return contractAgreementNo;
	}

	public void setContractAgreementNo(Long contractAgreementNo) {
		this.contractAgreementNo = contractAgreementNo;
	}

	public Date getAgreementDate() {
		return agreementDate;
	}

	public void setAgreementDate(Date agreementDate) {
		this.agreementDate = agreementDate;
	}

	public Date getNoticeDate() {
		return noticeDate;
	}

	public void setNoticeDate(Date noticeDate) {
		this.noticeDate = noticeDate;
	}

	public Date getLeaseStartDate() {
		return leaseStartDate;
	}

	public void setLeaseStartDate(Date leaseStartDate) {
		this.leaseStartDate = leaseStartDate;
	}

	public Date getLeaseEndDate() {
		return leaseEndDate;
	}

	public void setLeaseEndDate(Date leaseEndDate) {
		this.leaseEndDate = leaseEndDate;
	}

	public Long getLeaseType() {
		return leaseType;
	}

	public void setLeaseType(Long leaseType) {
		this.leaseType = leaseType;
	}

	public BigDecimal getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public Long getNoOfInstallment() {
		return noOfInstallment;
	}

	public void setNoOfInstallment(Long noOfInstallment) {
		this.noOfInstallment = noOfInstallment;
	}

	public Long getPaymentFrequency() {
		return paymentFrequency;
	}

	public void setPaymentFrequency(Long paymentFrequency) {
		this.paymentFrequency = paymentFrequency;
	}

	public BigDecimal getAdvancedPayment() {
		return advancedPayment;
	}

	public void setAdvancedPayment(BigDecimal advancedPayment) {
		this.advancedPayment = advancedPayment;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getLgIpMac() {
		return lgIpMac;
	}

	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}

	public String getLgIpMacUpd() {
		return lgIpMacUpd;
	}

	public void setLgIpMacUpd(String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}

	public String getHistoryFlag() {
		return historyFlag;
	}

	public void setHistoryFlag(String historyFlag) {
		this.historyFlag = historyFlag;
	}

	public String[] getPkValues() {
		return new String[] { MainetConstants.AssetManagement.ASSET_MANAGEMENT, "TB_AST_LEASING_HIST",
				"ASSET_LEASING_HIST_ID" };
	}
}
