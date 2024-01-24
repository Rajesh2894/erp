package com.abm.mainet.materialmgmt.domain;

import java.io.Serializable;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.ibm.icu.math.BigDecimal;

@Entity
@Table(name = "MM_EXPIRED")
public class ExpiryItemsEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "expiryid")
	private Long expiryId;

	@Column(name = "storeid")
	private Long storeId;

	@Column(name = "movementno")
	private String movementNo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "movementdate")
	private Date movementDate;

	@Column(name = "movementby")
	private Long movementBy;

	@Column(name = "expirycheck")
	private Date expiryCheck;

	@Column(name = "Status")
	private String status;

	@Column(name = "ORGID")
	private Long orgId;

	@Column(name = "USER_ID")
	private Long userId;

	@Column(name = "LANGID")
	private int langId;

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

	@OneToMany(mappedBy = "expiryItemsEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ExpiryItemsDetEntity> expiryItemsDetEntity = new ArrayList<>();

	
	//********** from Disposal ************
	@Column(name = "SCRAPNO")
	private String scrapNo;
	
	@Column(name = "SCRAPDATE")
	private Date scrapDate;

	@Column(name = "INITIATOR")
	private Long initiator;

	@Column(name = "VENDORID")
	private Long vendorId;

	@Column(name = "WORKORDERID")
	private Long workorderId;
		
	@Column(name = "DISPOSEDDATE")
	private Date disposedDate;
	
	@Column(name = "PAYMENTFLAG")
	private Character paymentFlag;
	
	@Column(name = "RECEIPTAMT")
	private Double receiptAmt;	
	
	@Column(name = "MODE")
	private String mode;

	@Column(name = "BANKID")
	private Long bankId;	
	
	@Column(name = "INSTRUMENTNO")
	private Long instrumentNo;	
	
	@Column(name = "INSTRUMENTDATE")
	private Date instrumentDate;

	@Column(name = "INSTRUMENTAMT")
	private Double instrumentAmt;	
	
    
    @Column(name = "APM_APPLICATION_ID")
    private Long applicationId;
	

	@Column(name = "department")
	private Long department ; 
    
    
	//@OneToOne(mappedBy = "expiryItemsEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	//private DisposalItemsEntity disposalItemsEntity = new DisposalItemsEntity();
	
	public void addSExpiryItemsDetMapping(ExpiryItemsDetEntity expiryItemsDetEntity) {
		this.expiryItemsDetEntity.add(expiryItemsDetEntity);
		expiryItemsDetEntity.setExpiryItemsEntity(this);
	}

	public ExpiryItemsEntity() {

	}

	public Long getExpiryId() {
		return expiryId;
	}

	public void setExpiryId(Long expiryId) {
		this.expiryId = expiryId;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public String getMovementNo() {
		return movementNo;
	}

	public void setMovementNo(String movementNo) {
		this.movementNo = movementNo;
	}

	public Date getMovementDate() {
		return movementDate;
	}

	public void setMovementDate(Date movementDate) {
		this.movementDate = movementDate;
	}

	public Long getMovementBy() {
		return movementBy;
	}

	public void setMovementBy(Long movementBy) {
		this.movementBy = movementBy;
	}

	public Date getExpiryCheck() {
		return expiryCheck;
	}

	public void setExpiryCheck(Date expiryCheck) {
		this.expiryCheck = expiryCheck;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public int getLangId() {
		return langId;
	}

	public void setLangId(int langId) {
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

	public List<ExpiryItemsDetEntity> getExpiryItemsDetEntity() {
		return expiryItemsDetEntity;
	}

	public void setExpiryItemsDetEntity(List<ExpiryItemsDetEntity> expiryItemsDetEntity) {
		this.expiryItemsDetEntity = expiryItemsDetEntity;
	}

	/*public DisposalItemsEntity getDisposalItemsEntity() {
		return disposalItemsEntity;
	}
	
	public void setDisposalItemsEntity(DisposalItemsEntity disposalItemsEntity) {
		this.disposalItemsEntity = disposalItemsEntity;
	}*/

	public String getScrapNo() {
		return scrapNo;
	}

	public void setScrapNo(String scrapNo) {
		this.scrapNo = scrapNo;
	}

	public Date getScrapDate() {
		return scrapDate;
	}

	public void setScrapDate(Date scrapDate) {
		this.scrapDate = scrapDate;
	}

	public Long getInitiator() {
		return initiator;
	}

	public void setInitiator(Long initiator) {
		this.initiator = initiator;
	}

	public Long getVendorId() {
		return vendorId;
	}

	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}

	public Long getWorkorderId() {
		return workorderId;
	}

	public void setWorkorderId(Long workorderId) {
		this.workorderId = workorderId;
	}

	public Date getDisposedDate() {
		return disposedDate;
	}

	public void setDisposedDate(Date disposedDate) {
		this.disposedDate = disposedDate;
	}

	public Character getPaymentFlag() {
		return paymentFlag;
	}

	public void setPaymentFlag(Character paymentFlag) {
		this.paymentFlag = paymentFlag;
	}

	public Double getReceiptAmt() {
		return receiptAmt;
	}

	public void setReceiptAmt(Double receiptAmt) {
		this.receiptAmt = receiptAmt;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public Long getInstrumentNo() {
		return instrumentNo;
	}

	public void setInstrumentNo(Long instrumentNo) {
		this.instrumentNo = instrumentNo;
	}

	public Date getInstrumentDate() {
		return instrumentDate;
	}

	public void setInstrumentDate(Date instrumentDate) {
		this.instrumentDate = instrumentDate;
	}

	public Double getInstrumentAmt() {
		return instrumentAmt;
	}

	public void setInstrumentAmt(Double instrumentAmt) {
		this.instrumentAmt = instrumentAmt;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public Long getDepartment() {
		return department;
	}

	public void setDepartment(Long department) {
		this.department = department;
	}

	public String[] getPkValues() {

		return new String[] { "MMM", "MM_EXPIRED", "expiryid" };
	}
}
