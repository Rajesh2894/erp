package com.abm.mainet.materialmgmt.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class InvoiceEntryDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long invoiceId;

	private String invoiceNo;

	private Date invoiceDate;

	private Long storeId;

	private String storeName;

	private Long poId;

	private String poNumber;

	private Long vendorId;

	private String vendorName;

	private BigDecimal itemAmt;

	private BigDecimal overheadAmt;

	private BigDecimal invoiceAmt;

	private String atdPath;

	private String atdFname;

	private Character invoiceStatus;

	private Character paymentStatus;

	private BigDecimal paymentMade;

	private Long orgId;

	private Long userId;

	private String userName;

	private Long langId;

	private Date lmodDate;

	private Long updatedBy;

	private Date updatedDate;

	private String lgIpMac;

	private String lgIpMacUpd;

	private String wfFlag;

	private Long grnId;

	private List<Long> grnIdList;

	private List<InvoiceEntryGRNDTO> invoiceEntryGRNDTOList = new ArrayList<>();

	private List<InvoiceEntryDetailDTO> invoiceEntryDetailDTOList = new ArrayList<>();

	private List<InvoiceEntryOverheadsDTO> invoiceOverheadsDTOList = new ArrayList<>();
	
    private String removeFileById;

	private Date fromDate;

	private Date toDate;
	
	
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

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public Long getPoId() {
		return poId;
	}

	public void setPoId(Long poId) {
		this.poId = poId;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public Long getVendorId() {
		return vendorId;
	}

	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public Long getGrnId() {
		return grnId;
	}

	public void setGrnId(Long grnId) {
		this.grnId = grnId;
	}

	public List<Long> getGrnIdList() {
		return grnIdList;
	}

	public void setGrnIdList(List<Long> grnIdList) {
		this.grnIdList = grnIdList;
	}

	public List<InvoiceEntryGRNDTO> getInvoiceEntryGRNDTOList() {
		return invoiceEntryGRNDTOList;
	}

	public void setInvoiceEntryGRNDTOList(List<InvoiceEntryGRNDTO> invoiceEntryGRNDTOList) {
		this.invoiceEntryGRNDTOList = invoiceEntryGRNDTOList;
	}

	public List<InvoiceEntryDetailDTO> getInvoiceEntryDetailDTOList() {
		return invoiceEntryDetailDTOList;
	}

	public void setInvoiceEntryDetailDTOList(List<InvoiceEntryDetailDTO> invoiceEntryDetailDTOList) {
		this.invoiceEntryDetailDTOList = invoiceEntryDetailDTOList;
	}

	public List<InvoiceEntryOverheadsDTO> getInvoiceOverheadsDTOList() {
		return invoiceOverheadsDTOList;
	}

	public void setInvoiceOverheadsDTOList(List<InvoiceEntryOverheadsDTO> invoiceOverheadsDTOList) {
		this.invoiceOverheadsDTOList = invoiceOverheadsDTOList;
	}

	public String getRemoveFileById() {
		return removeFileById;
	}

	public void setRemoveFileById(String removeFileById) {
		this.removeFileById = removeFileById;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}


}
