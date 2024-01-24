package com.abm.mainet.materialmgmt.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "MM_GROUPMAPPING")
public class StoreGroupMapping implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "groupmapid")
    private Long groupMapId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeid")
    private StoreMaster storeMasterEntity;
	
	@Column(name = "itemgroupid")
	private Long itemGroupId;
	
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

    public StoreGroupMapping() {
		
	}

	public Long getGroupMapId() {
		return groupMapId;
	}

	public void setGroupMapId(Long groupMapId) {
		this.groupMapId = groupMapId;
	}

	public StoreMaster getStoreMasterEntity() {
		return storeMasterEntity;
	}

	public void setStoreMasterEntity(StoreMaster storeMasterEntity) {
		this.storeMasterEntity = storeMasterEntity;
	}

	public Long getItemGroupId() {
		return itemGroupId;
	}

	public void setItemGroupId(Long itemGroupId) {
		this.itemGroupId = itemGroupId;
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

	public String[] getPkValues() {

        return new String[] { "ITG", "MM_GROUPMAPPING", "groupmapid" };
    }    
}
