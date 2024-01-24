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

@Entity
@Table(name = "MM_STOREMASTER")
public class StoreMaster implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "storeid")
    private Long storeId;
	
	@Column(name = "storecode")
	private String storeCode;
	
	@Column(name = "storename")
	private String storeName;
	
	@Column(name = "location")
	private Long location;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "storeincharge")
	private Long storeIncharge;
	
	@Column(name = "status")
	private Character status;
	
	@OneToMany(mappedBy = "storeMasterEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StoreGroupMapping>	grMappingList= new ArrayList<>();
	
	@Column(name = "ORGID")
	private Long orgId;
	
	@Column(name = "USER_ID")
    private Long userId;
	
	@Column(name = "LANGID")
    private Long langId;
	
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LMODDATE")
    private Date lmoDate;
    
    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;
    
    public StoreMaster() {
		
	}

	public void addStoreMasterMapping(StoreGroupMapping storeGroupMasterEntity) {
    	this.grMappingList.add(storeGroupMasterEntity);
    	storeGroupMasterEntity.setStoreMasterEntity(this);
    }
	
	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public Long getLocation() {
		return location;
	}

	public void setLocation(Long location) {
		this.location = location;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getStoreIncharge() {
		return storeIncharge;
	}

	public void setStoreIncharge(Long storeIncharge) {
		this.storeIncharge = storeIncharge;
	}

	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	public List<StoreGroupMapping> getGrMappingList() {
		return grMappingList;
	}

	public void setGrMappingList(List<StoreGroupMapping> grMappingList) {
		this.grMappingList = grMappingList;
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

	public String[] getPkValues() {

        return new String[] { "ITP", "MM_STOREMASTER", "storeid" };
    }
}
