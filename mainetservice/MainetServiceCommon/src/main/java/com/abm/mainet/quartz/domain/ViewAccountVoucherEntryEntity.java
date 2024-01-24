package com.abm.mainet.quartz.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "vw_ac_voucher")
public class ViewAccountVoucherEntryEntity {

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "VOU_ID", precision = 12, scale = 0, nullable = false)
	private long vouId;

	@Temporal(TemporalType.DATE)
	@Column(name = "VOU_DATE", nullable = true)
	private Date vouDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "VOU_POSTING_DATE", nullable = true)
	private Date vouPostingDate;

	@Column(name = "VOU_TYPE_CPD_ID", precision = 12, scale = 0, nullable = true)
	private String vouTypeCpdId;

	@Column(name = "VOU_SUBTYPE_CPD_ID", precision = 12, scale = 0, nullable = true)
	private Long vouSubtypeCpdId;

	@Column(name = "DP_DEPTID", precision = 12, scale = 0, nullable = true)
	private Long dpDeptid;

	@Column(name = "VOU_REFERENCE_NO", length = 20, nullable = true)
	private String vouReferenceNo;

	@Column(name = "VOU_REFERENCE_NO_DATE", nullable = true)
	private Date vouReferenceNoDate;

	@Column(name = "NARRATION", length = 1000, nullable = true)
	private String narration;

	@Column(name = "PAYER_PAYEE", length = 1000, nullable = true)
	private String payerPayee;

	@Column(name = "FIELD_ID", precision = 12, scale = 0, nullable = true)
	private Long fieldId;

	@Column(name = "ORGID", nullable = false, updatable = false)
	private Long org;

	@Column(name = "CREATED_BY", precision = 7, scale = 0, nullable = false)
	private Long createdBy;

	@Column(name = "CREATED_DATE", nullable = false)
	private Date lmodDate;

	@Column(name = "LG_IP_MAC", length = 100, nullable = true)
	private String lgIpMac;

	@Column(name = "ENTRY_TYPE", precision = 2, scale = 0, nullable = false)
	private String entryType;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "master", cascade = CascadeType.ALL)
	private List<ViewAccountVoucherEntryDetailsEntity> details = new ArrayList<>(0);
	
	
	@Column(name = "CPD_FEEMODE", nullable = false, updatable = false)
	private Long feeMode;
	
	
	
	
	
	

	public String[] getPkValues() {
		return new String[] { "AC", "TB_AC_VOUCHER", "VOU_ID" };
	}

	/**
	 * @return the vouId
	 */
	public long getVouId() {
		return vouId;
	}

	/**
	 * @param vouId
	 *            the vouId to set
	 */
	public void setVouId(final long vouId) {
		this.vouId = vouId;
	}


	/**
	 * @return the vouDate
	 */
	public Date getVouDate() {
		return vouDate;
	}

	/**
	 * @param vouDate
	 *            the vouDate to set
	 */
	public void setVouDate(final Date vouDate) {
		this.vouDate = vouDate;
	}

	/**
	 * @return the vouTypeCpdId
	 */
	public String getVouTypeCpdId() {
		return vouTypeCpdId;
	}

	/**
	 * @param vouTypeCpdId
	 *            the vouTypeCpdId to set
	 */
	public void setVouTypeCpdId(final String vouTypeCpdId) {
		this.vouTypeCpdId = vouTypeCpdId;
	}

	/**
	 * @return the vouSubtypeCpdId
	 */
	public Long getVouSubtypeCpdId() {
		return vouSubtypeCpdId;
	}

	/**
	 * @param vouSubtypeCpdId
	 *            the vouSubtypeCpdId to set
	 */
	public void setVouSubtypeCpdId(final Long vouSubtypeCpdId) {
		this.vouSubtypeCpdId = vouSubtypeCpdId;
	}

	/**
	 * @return the dpDeptid
	 */
	public Long getDpDeptid() {
		return dpDeptid;
	}

	/**
	 * @param dpDeptid
	 *            the dpDeptid to set
	 */
	public void setDpDeptid(final Long dpDeptid) {
		this.dpDeptid = dpDeptid;
	}

	/**
	 * @return the vouReferenceNo
	 */
	public String getVouReferenceNo() {
		return vouReferenceNo;
	}

	/**
	 * @param vouReferenceNo
	 *            the vouReferenceNo to set
	 */
	public void setVouReferenceNo(final String vouReferenceNo) {
		this.vouReferenceNo = vouReferenceNo;
	}

	/**
	 * @return the vouReferenceNoDate
	 */
	public Date getVouReferenceNoDate() {
		return vouReferenceNoDate;
	}

	/**
	 * @param vouReferenceNoDate
	 *            the vouReferenceNoDate to set
	 */
	public void setVouReferenceNoDate(final Date vouReferenceNoDate) {
		this.vouReferenceNoDate = vouReferenceNoDate;
	}

	/**
	 * @return the narration
	 */
	public String getNarration() {
		return narration;
	}

	/**
	 * @param narration
	 *            the narration to set
	 */
	public void setNarration(final String narration) {
		this.narration = narration;
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
	public void setPayerPayee(final String payerPayee) {
		this.payerPayee = payerPayee;
	}

	/**
	 * @return the createdBy
	 */
	public Long getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(final Long createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the lmodDate
	 */
	public Date getLmodDate() {
		return lmodDate;
	}

	/**
	 * @param lmodDate
	 *            the lmodDate to set
	 */
	public void setLmodDate(final Date lmodDate) {
		this.lmodDate = lmodDate;
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
	public void setLgIpMac(final String lgIpMac) {
		this.lgIpMac = lgIpMac;
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
	public void setEntryType(final String entryType) {
		this.entryType = entryType;
	}

	/**
	 * @return the org
	 */
	public Long getOrg() {
		return org;
	}

	/**
	 * @param org
	 *            the org to set
	 */
	public void setOrg(final Long org) {
		this.org = org;
	}

	public Long getFieldId() {
		return fieldId;
	}

	/**
	 * @param fieldId
	 *            the fieldId to set
	 */
	public void setFieldId(final Long fieldId) {
		this.fieldId = fieldId;
	}

	public List<ViewAccountVoucherEntryDetailsEntity> getDetails() {
		return details;
	}

	public void setDetails(List<ViewAccountVoucherEntryDetailsEntity> details) {
		this.details = details;
	}

	public Date getVouPostingDate() {
		return vouPostingDate;
	}

	public Long getFeeMode() {
		return feeMode;
	}

	public void setFeeMode(Long feeMode) {
		this.feeMode = feeMode;
	}

	public void setVouPostingDate(Date vouPostingDate) {
		this.vouPostingDate = vouPostingDate;
	}
	
	

	
}
