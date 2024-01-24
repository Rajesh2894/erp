package com.abm.mainet.materialmgmt.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "mm_invoice")
public class InvoiceEntryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "invoiceid")
	private Long invoiceId;

	@Column(name = "invoiceNo")
	private String invoiceNo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "invoicedate")
	private Date invoiceDate;

	@Column(name = "storeid")
	private Long storeId;

	@Column(name = "poid")
	private Long poId;

	@Column(name = "vendorid")
	private Long vendorId;

	@Column(name = "itemamt")
	private BigDecimal itemAmt;

	@Column(name = "overheadamt")
	private BigDecimal overheadAmt;

	@Column(name = "invoiceamt")
	private BigDecimal invoiceAmt;

	@Column(name = "ATD_PATH")
	private String atdPath;

	@Column(name = "ATD_FNAME")
	private String atdFname;

	@Column(name = "invoicestatus")
	private Character invoiceStatus;

	@Column(name = "paymentstatus")
	private Character paymentStatus;

	@Column(name = "paymentmade")
	private BigDecimal paymentMade;

	@Column(name = "ORGID")
	private Long orgId;

	@Column(name = "USER_ID")
	private Long userId;

	@Column(name = "LANGID")
	private Long langId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LMODDATE")
	private Date lmodDate;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "LG_IP_MAC")
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD")
	private String lgIpMacUpd;

	@Column(name = "WF_Flag")
	private String wfFlag;

	@OneToMany(mappedBy = "invoiceEntryEntity", cascade = CascadeType.ALL)
	@Where(clause = "Status!='N'")
	private List<InvoiceEntryGRNEntity> invoiceGRNEntityList = new ArrayList<>();

	@OneToMany(mappedBy = "invoiceEntryEntity", cascade = CascadeType.ALL)
	@Where(clause = "Status!='N'")
	private List<InvoiceEntryDetailEntity> invoiceDetailEntityList = new ArrayList<>();

	@OneToMany(mappedBy = "invoiceEntryEntity", cascade = CascadeType.ALL)
	@Where(clause = "Status!='N'")
	private List<InvoiceEntryOverheadsEntity> invoiceOverheadsEntityList = new ArrayList<>();

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public Long getPoId() {
		return poId;
	}

	public void setPoId(Long poId) {
		this.poId = poId;
	}

	public Long getVendorId() {
		return vendorId;
	}

	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}

	public BigDecimal getItemAmt() {
		return itemAmt;
	}

	public void setItemAmt(BigDecimal itemAmt) {
		this.itemAmt = itemAmt;
	}

	public BigDecimal getOverheadAmt() {
		return overheadAmt;
	}

	public void setOverheadAmt(BigDecimal overheadAmt) {
		this.overheadAmt = overheadAmt;
	}

	public BigDecimal getInvoiceAmt() {
		return invoiceAmt;
	}

	public void setInvoiceAmt(BigDecimal invoiceAmt) {
		this.invoiceAmt = invoiceAmt;
	}

	public String getAtdPath() {
		return atdPath;
	}

	public void setAtdPath(String atdPath) {
		this.atdPath = atdPath;
	}

	public String getAtdFname() {
		return atdFname;
	}

	public void setAtdFname(String atdFname) {
		this.atdFname = atdFname;
	}

	public Character getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(Character invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public Character getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(Character paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public BigDecimal getPaymentMade() {
		return paymentMade;
	}

	public void setPaymentMade(BigDecimal paymentMade) {
		this.paymentMade = paymentMade;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getLangId() {
		return langId;
	}

	public void setLangId(Long langId) {
		this.langId = langId;
	}

	public Date getLmodDate() {
		return lmodDate;
	}

	public void setLmodDate(Date lmodDate) {
		this.lmodDate = lmodDate;
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

	public String getWfFlag() {
		return wfFlag;
	}

	public void setWfFlag(String wfFlag) {
		this.wfFlag = wfFlag;
	}

	public List<InvoiceEntryGRNEntity> getInvoiceGRNEntityList() {
		return invoiceGRNEntityList;
	}

	public void setInvoiceGRNEntityList(List<InvoiceEntryGRNEntity> invoiceGRNEntityList) {
		this.invoiceGRNEntityList = invoiceGRNEntityList;
	}

	public List<InvoiceEntryDetailEntity> getInvoiceDetailEntityList() {
		return invoiceDetailEntityList;
	}

	public void setInvoiceDetailEntityList(List<InvoiceEntryDetailEntity> invoiceDetailEntityList) {
		this.invoiceDetailEntityList = invoiceDetailEntityList;
	}

	public List<InvoiceEntryOverheadsEntity> getInvoiceOverheadsEntityList() {
		return invoiceOverheadsEntityList;
	}

	public void setInvoiceOverheadsEntityList(List<InvoiceEntryOverheadsEntity> invoiceOverheadsEntityList) {
		this.invoiceOverheadsEntityList = invoiceOverheadsEntityList;
	}

	public String[] getPkValues() {
		return new String[] { "MMM", "mm_invoice", "invoiceid" };
	}

}
