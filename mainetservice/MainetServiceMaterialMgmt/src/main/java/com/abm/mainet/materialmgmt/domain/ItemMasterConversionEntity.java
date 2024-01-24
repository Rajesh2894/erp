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

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name = "MM_ITEMCONVERSION")
public class ItemMasterConversionEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "convid")
    private Long convId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemid" , referencedColumnName = "itemId")
    private ItemMasterEntity itemMasterEntity;

    @Column(name = "convuom", nullable = false)
    private Long convUom;
    
    @Column(name = "units", nullable = false)
    private Long units;
    
    @Column(name = "Status", length = 1 , nullable = false)
    private String status;
    
    @Column(name = "ORGID" , nullable = false)
    private Long orgId;
    
    @Column(name = "LANGID" , nullable = false)
    private Integer langId;
    
    
    @Column(name = "USER_ID", nullable = false)
    private Long userId;

 
    @Temporal(TemporalType.DATE)
    @Column(name = "LMODDATE" , nullable = true)
    private Date createdDate;

    @Column(name = "UPDATED_BY" , nullable = true)
    private Long updatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATED_DATE" , nullable = true)
    private Date updatedDate;

    @JsonIgnore
    @Column(name = "LG_IP_MAC", length = 100 , nullable = true)
    private String lgIpMac;

    @JsonIgnore
    @Column(name = "LG_IP_MAC_UPD", length = 100 , nullable = true)
    private String lgIpMacUpd;

	

	public ItemMasterEntity getItemMasterEntity() {
		return itemMasterEntity;
	}

	public void setItemMasterEntity(ItemMasterEntity itemMasterEntity) {
		this.itemMasterEntity = itemMasterEntity;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	public Long getUnits() {
		return units;
	}

	public void setUnits(Long units) {
		this.units = units;
	}

	public Integer getLangId() {
		return langId;
	}

	public void setLangId(Integer langId) {
		this.langId = langId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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

	public static String[] getPkValues() {
        return new String[] { "ITMC", "mm_itemconversion", "convid" };
    }

	public Long getConvId() {
		return convId;
	}

	public void setConvId(Long convId) {
		this.convId = convId;
	}

	public Long getConvUom() {
		return convUom;
	}

	public void setConvUom(Long convUom) {
		this.convUom = convUom;
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

	@Override
	public String toString() {
		return "ItemMasterConversionEntity [convId=" + convId + ", itemMasterEntity=" + itemMasterEntity + ", convUom="
				+ convUom + ", units=" + units + ", status=" + status + ", orgId=" + orgId + ", langId=" + langId
				+ ", userId=" + userId + ", createdDate=" + createdDate + ", updatedBy=" + updatedBy + ", updatedDate="
				+ updatedDate + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd + "]";
	}
	
}
