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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "MM_PURCHASEORDER")
public class PurchaseOrderEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "poid")
	private Long poId; 
	
	@Column(name = "pono")
	private String poNo;  
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="podate")
	private Date poDate;  
	
	@Column(name="storeid")
	private Long storeId;  
	
	@Column(name="workorderid")
	private Long workOrderId; 
	
	@Column(name="vendorid")
	private Long vendorId;  
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="expecteddeliverydate")
	private Date expectedDeliveryDate;
	
	@Column(name="Status")
	private char status; 
	
	@Column(name="ORGID")
	private Long orgId; 
	
	@Column(name="USER_ID")
	private Long userId; 
	
	@Column(name="LANGID")
	private Long langId; 
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="LMODDATE")
	private Date lmoDate; 
	
	@Column(name="UPDATED_BY")
	private Long updatedBy; 
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name="LG_IP_MAC")
	private String lgIpMac; 
	
	@Column(name="LG_IP_MAC_UPD")
	private String lgIpMacUpd;  
	
	@Column(name="WF_Flag")
	private String wfFlag; 
	
	public PurchaseOrderEntity() {
		
	}
   
    @OneToMany(mappedBy = "purchaseOrderEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Where(clause = "Status!='N'")
    private List<PurchaseOrderDetEntity> purchaseOrderDetEntity=new ArrayList<>();
	
   
    @OneToMany(mappedBy = "purchaseOrderEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Where(clause = "Status!='N'")
    private List<PurchaseOrderOverheadsEntity> purchaseOrderOverheadsEntity=new ArrayList<>();
    
    
    @OneToMany(mappedBy = "purchaseOrderEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Where(clause = "Status!='N'")
    private List<PurchaseorderTncEntity> purchaseorderTncEntity=new ArrayList<>();
    
   
    @OneToMany(mappedBy = "purchaseOrderEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Where(clause = "ATD_STATUS!='N'")
    private List<PurchaseorderAttachmentEntity> purchaseorderAttachmentEntity=new ArrayList<>();
    
    public void addPurchaseOrderdetMapping(PurchaseOrderDetEntity purchaseOrderDetEntity) {
    	this.purchaseOrderDetEntity.add(purchaseOrderDetEntity);
    	purchaseOrderDetEntity.setPurchaseOrderEntity(this);
    }
    
    public void addPurchaseOrderOverheadsMapping(PurchaseOrderOverheadsEntity purchaseOrderOverheadsEntity) {
    	this.purchaseOrderOverheadsEntity.add(purchaseOrderOverheadsEntity);
    	purchaseOrderOverheadsEntity.setPurchaseOrderEntity(this);
    }
    
    public void addPurchaseorderTncMapping(PurchaseorderTncEntity purchaseorderTncEntity) {
    	this.purchaseorderTncEntity.add(purchaseorderTncEntity);
    	purchaseorderTncEntity.setPurchaseOrderEntity(this);
    }
    
    public void addPurchaseorderAttachmentMapping(PurchaseorderAttachmentEntity purchaseorderAttachmentEntity) {
    	this.purchaseorderAttachmentEntity.add(purchaseorderAttachmentEntity);
    	purchaseorderAttachmentEntity.setPurchaseOrderEntity(this);
    }
    
	public Long getPoId() {
		return poId;
	}

	public void setPoId(Long poId) {
		this.poId = poId;
	}
	
	public String getPoNo() {
		return poNo;
	}

	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}

	public Date getPoDate() {
		return poDate;
	}

	public void setPoDate(Date poDate) {
		this.poDate = poDate;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}


	public Long getWorkOrderId() {
		return workOrderId;
	}

	public void setWorkOrderId(Long workOrderId) {
		this.workOrderId = workOrderId;
	}

	public Long getVendorId() {
		return vendorId;
	}

	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}

	public Date getExpectedDeliveryDate() {
		return expectedDeliveryDate;
	}

	public void setExpectedDeliveryDate(Date expectedDeliveryDate) {
		this.expectedDeliveryDate = expectedDeliveryDate;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
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

	public Long getLangId() {
		return langId;
	}

	public void setLangId(Long langId) {
		this.langId = langId;
	}

	public Date getLmoDate() {
		return lmoDate;
	}

	public void setLmoDate(Date lmoDate) {
		this.lmoDate = lmoDate;
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

	public List<PurchaseOrderDetEntity> getPurchaseOrderDetEntity() {
		return purchaseOrderDetEntity;
	}

	public void setPurchaseOrderDetEntity(List<PurchaseOrderDetEntity> purchaseOrderDetEntity) {
		this.purchaseOrderDetEntity = purchaseOrderDetEntity;
	}

	public List<PurchaseOrderOverheadsEntity> getPurchaseOrderOverheadsEntity() {
		return purchaseOrderOverheadsEntity;
	}

	public void setPurchaseOrderOverheadsEntity(List<PurchaseOrderOverheadsEntity> purchaseOrderOverheadsEntity) {
		this.purchaseOrderOverheadsEntity = purchaseOrderOverheadsEntity;
	}

	public List<PurchaseorderTncEntity> getPurchaseorderTncEntity() {
		return purchaseorderTncEntity;
	}

	public void setPurchaseorderTncEntity(List<PurchaseorderTncEntity> purchaseorderTncEntity) {
		this.purchaseorderTncEntity = purchaseorderTncEntity;
	}

	public List<PurchaseorderAttachmentEntity> getPurchaseorderAttachmentEntity() {
		return purchaseorderAttachmentEntity;
	}

	public void setPurchaseorderAttachmentEntity(List<PurchaseorderAttachmentEntity> purchaseorderAttachmentEntity) {
		this.purchaseorderAttachmentEntity = purchaseorderAttachmentEntity;
	}

	public String[] getPkValues() {
        return new String[] { "MMM", "MM_PURCHASEORDER", "poid" };
    }
	
}
