package com.abm.mainet.materialmgmt.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "MM_STOREMASTER_HIST")
public class StoreMasterHistory implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "storehistid")
    private Long storeHistId;
	
	@Column(name = "storeid")
    private Long storeId;
    
	
	@Column(name = "storeCode")
	private String storeCode;
	
	@Column(name = "storeName")
	private String storeName;
	
	@Column(name = "location")
	private Long location;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "storeIncharge")
	private Long storeIncharge;
	
	@Column(name = "status")
	private char status;
	
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

    public StoreMasterHistory() {
		
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

	public Long getStoreHistId() {
		return storeHistId;
	}


	public void setStoreHistId(Long storeHistId) {
		this.storeHistId = storeHistId;
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

        return new String[] { "ITP", "MM_STOREMASTER_HIST", "storehistid" };
    }


	

}
