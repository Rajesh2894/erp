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
public class AssetAccountPostDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7870250774404150039L;

	private Long accountHead;
	private Long disposalAccountHead;
	private BigDecimal accountHeadAmount;
	private BigDecimal disposalAccountHeadAmount;
	private Long payModeId;
	private BigDecimal payModeIdAmount;
	private String payerPayee;
	private String entryType;
	private Long voucherSubTypeId;
	private Date billVouPostingDate;
	private Date createdDate;
	private Date voucherDate;
	private String voucherSubTypeName;
	private String lgIpMac;

	/**
	 * @return the accountHead
	 */
	public Long getAccountHead() {
		return accountHead;
	}

	/**
	 * @param accountHead
	 *            the accountHead to set
	 */
	public void setAccountHead(Long accountHead) {
		this.accountHead = accountHead;
	}

	/**
	 * @return the disposalAccountHead
	 */
	public Long getDisposalAccountHead() {
		return disposalAccountHead;
	}

	/**
	 * @param disposalAccountHead
	 *            the disposalAccountHead to set
	 */
	public void setDisposalAccountHead(Long disposalAccountHead) {
		this.disposalAccountHead = disposalAccountHead;
	}

	/**
	 * @return the accountHeadAmount
	 */
	public BigDecimal getAccountHeadAmount() {
		return accountHeadAmount;
	}

	/**
	 * @param accountHeadAmount
	 *            the accountHeadAmount to set
	 */
	public void setAccountHeadAmount(BigDecimal accountHeadAmount) {
		this.accountHeadAmount = accountHeadAmount;
	}

	/**
	 * @return the disposalAccountHeadAmount
	 */
	public BigDecimal getDisposalAccountHeadAmount() {
		return disposalAccountHeadAmount;
	}

	/**
	 * @param disposalAccountHeadAmount
	 *            the disposalAccountHeadAmount to set
	 */
	public void setDisposalAccountHeadAmount(BigDecimal disposalAccountHeadAmount) {
		this.disposalAccountHeadAmount = disposalAccountHeadAmount;
	}

	/**
	 * @return the payModeId
	 */
	public Long getPayModeId() {
		return payModeId;
	}

	/**
	 * @param payModeId
	 *            the payModeId to set
	 */
	public void setPayModeId(Long payModeId) {
		this.payModeId = payModeId;
	}

	/**
	 * @return the payModeIdAmount
	 */
	public BigDecimal getPayModeIdAmount() {
		return payModeIdAmount;
	}

	/**
	 * @param payModeIdAmount
	 *            the payModeIdAmount to set
	 */
	public void setPayModeIdAmount(BigDecimal payModeIdAmount) {
		this.payModeIdAmount = payModeIdAmount;
	}

	/**
	 * @return the payerPayee
	 */
	public String getPayerPayee() {
		return payerPayee;
	}

	/**
	 * @param payerPayee
	 *            the payerPayee to set
	 */
	public void setPayerPayee(String payerPayee) {
		this.payerPayee = payerPayee;
	}

	/**
	 * @return the entryType
	 */
	public String getEntryType() {
		return entryType;
	}

	/**
	 * @param entryType
	 *            the entryType to set
	 */
	public void setEntryType(String entryType) {
		this.entryType = entryType;
	}

	/**
	 * @return the voucherSubTypeId
	 */
	public Long getVoucherSubTypeId() {
		return voucherSubTypeId;
	}

	/**
	 * @param voucherSubTypeId
	 *            the voucherSubTypeId to set
	 */
	public void setVoucherSubTypeId(Long voucherSubTypeId) {
		this.voucherSubTypeId = voucherSubTypeId;
	}

	/**
	 * @return the billVouPostingDate
	 */
	public Date getBillVouPostingDate() {
		return billVouPostingDate;
	}

	/**
	 * @param billVouPostingDate
	 *            the billVouPostingDate to set
	 */
	public void setBillVouPostingDate(Date billVouPostingDate) {
		this.billVouPostingDate = billVouPostingDate;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate
	 *            the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the voucherDate
	 */
	public Date getVoucherDate() {
		return voucherDate;
	}

	/**
	 * @param voucherDate
	 *            the voucherDate to set
	 */
	public void setVoucherDate(Date voucherDate) {
		this.voucherDate = voucherDate;
	}

	/**
	 * @return the voucherSubTypeName
	 */
	public String getVoucherSubTypeName() {
		return voucherSubTypeName;
	}

	/**
	 * @param voucherSubTypeName
	 *            the voucherSubTypeName to set
	 */
	public void setVoucherSubTypeName(String voucherSubTypeName) {
		this.voucherSubTypeName = voucherSubTypeName;
	}

	/**
	 * @return the lgIpMac
	 */
	public String getLgIpMac() {
		return lgIpMac;
	}

	/**
	 * @param lgIpMac
	 *            the lgIpMac to set
	 */
	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AssetAccountPostDTO [accountHead=" + accountHead + ", disposalAccountHead=" + disposalAccountHead
				+ ", accountHeadAmount=" + accountHeadAmount + ", disposalAccountHeadAmount="
				+ disposalAccountHeadAmount + ", payModeId=" + payModeId + ", payModeIdAmount=" + payModeIdAmount
				+ ", payerPayee=" + payerPayee + ", entryType=" + entryType + ", voucherSubTypeId=" + voucherSubTypeId
				+ ", billVouPostingDate=" + billVouPostingDate + ", createdDate=" + createdDate + ", voucherDate="
				+ voucherDate + ", voucherSubTypeName=" + voucherSubTypeName + ", lgIpMac=" + lgIpMac + "]";
	}

}
