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
@Table(name = "MM_REQUISITION")
public class PurchaseRequistionEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "prid")
	private Long prId;
	
	@Column(name = "prno")
	private String prNo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "prdate")
	private Date prDate;
	
	@Column(name = "storeid")
	private Long storeId;
	
	@Column(name = "requestedby")
	private Long requestedBy;

	@Column(name = "department")
	private Long department ; 
	
	@Column(name = "Status")
	private String status ;
	
	@Column(name = "poref")
	private Long poref;
	
	@Column(name = "ORGID")
	private Long orgId;
	
	@Column(name = "USER_ID")
	private Long userId;
	
	@Column(name = "LANGID")
	private Long langId ;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LMODDATE")
	private Date lmoDate ;
	
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
	
	@OneToMany(mappedBy = "purchaseRequistionEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Where(clause = "status = '0'")
	private List<PurchaseRequistionDetEntity> purchaseRequistionDetEntity= new ArrayList<>();
	
	@OneToMany(mappedBy = "purchaseRequistionEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Where(clause = "YE_ACTIVE='Y'")
    private List<PurchaseRequistionYearDetEntity> purchaseRequistionYearDetEntity= new ArrayList<>();
	
	public void addStoreMasterMapping(PurchaseRequistionDetEntity purchaseRequistionDetEntity) {
    	this.purchaseRequistionDetEntity.add(purchaseRequistionDetEntity);
    	purchaseRequistionDetEntity.setPurchaseRequistionEntity(this);
    }

	public Long getPrId() {
		return prId;
	}

	public void setPrId(Long prId) {
		this.prId = prId;
	}

	public String getPrNo() {
		return prNo;
	}

	public void setPrNo(String prNo) {
		this.prNo = prNo;
	}

	public Date getPrDate() {
		return prDate;
	}

	public void setPrDate(Date prDate) {
		this.prDate = prDate;
	}

	
	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public Long getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(Long requestedBy) {
		this.requestedBy = requestedBy;
	}

	public Long getDepartment() {
		return department;
	}

	public void setDepartment(Long department) {
		this.department = department;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getPoref() {
		return poref;
	}

	public void setPoref(Long poref) {
		this.poref = poref;
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

	public List<PurchaseRequistionDetEntity> getPurchaseRequistionDetEntity() {
		return purchaseRequistionDetEntity;
	}

	public void setPurchaseRequistionDetEntity(List<PurchaseRequistionDetEntity> purchaseRequistionDetEntity) {
		this.purchaseRequistionDetEntity = purchaseRequistionDetEntity;
	}
	public PurchaseRequistionEntity() {

	}
	public String[] getPkValues() {

        return new String[] { "MMM", "MM_REQUISITION", "prid" };
    }

	public List<PurchaseRequistionYearDetEntity> getPurchaseRequistionYearDetEntity() {
		return purchaseRequistionYearDetEntity;
	}

	public void setPurchaseRequistionYearDetEntity(List<PurchaseRequistionYearDetEntity> purchaseRequistionYearDetEntity) {
		this.purchaseRequistionYearDetEntity = purchaseRequistionYearDetEntity;
	}
}
