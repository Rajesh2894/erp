package com.abm.mainet.materialmgmt.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "MM_ITEMOPENINGBALANCE")
public class ItemOpeningBalanceEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "OPENBALID", nullable = false)
	private Long openBalId;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "OPENINGDATE",nullable = false)
	private Date openingDate;
	
	@ManyToOne
	@JoinColumn(name = "STOREID", nullable = false)
	private StoreMaster storeMaster;
	
	@ManyToOne
	@JoinColumn(name = "ITEMID", nullable = false)
	private ItemMasterEntity itemMasterEntity;
	
	@Column(name = "OPENINGBALANCE",nullable = false)
	private BigDecimal openingBalance;
	
	@Column(name = "UNITPRICE",nullable = false)
	private BigDecimal unitPrice;
	
	@Column(name = "ORGID", nullable = false)
	private Long orgId;

	@Column(name = "USER_ID", nullable = false)
	private Long userId;

	@Column(name = "LANGID", nullable = false)
	private Long langId;

	@Temporal(TemporalType.DATE)
	@Column(name = "LMODDATE", nullable = false)
	private Date lmoddate;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@JsonIgnore
	@Column(name = "LG_IP_MAC", length = 100, nullable = false)
	private String lgIpMac;

	@JsonIgnore
	@Column(name = "LG_IP_MAC_UPD", length = 100)
	private String lgIpMacUpd;
	
	@Column(name="STATUS")
	private boolean status;
    
	@Where(clause = "ACTIVE = '1'")
    @OneToMany(mappedBy = "openingBalanceEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ItemOpeningBalanceDetEntity> itemOpeningBalanceDetEntities = new ArrayList<>();

	public Long getOpenBalId() {
		return openBalId;
	}

	public void setOpenBalId(Long openBalId) {
		this.openBalId = openBalId;
	}

	public Date getOpeningDate() {
		return openingDate;
	}

	public void setOpeningDate(Date openingDate) {
		this.openingDate = openingDate;
	}

	public StoreMaster getStoreMaster() {
		return storeMaster;
	}

	public void setStoreMaster(StoreMaster storeMaster) {
		this.storeMaster = storeMaster;
	}

	public ItemMasterEntity getItemMasterEntity() {
		return itemMasterEntity;
	}

	public void setItemMasterEntity(ItemMasterEntity itemMasterEntity) {
		this.itemMasterEntity = itemMasterEntity;
	}

	public BigDecimal getOpeningBalance() {
		return openingBalance;
	}

	public void setOpeningBalance(BigDecimal openingBalance) {
		this.openingBalance = openingBalance;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
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

	public Date getLmoddate() {
		return lmoddate;
	}

	public void setLmoddate(Date lmoddate) {
		this.lmoddate = lmoddate;
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

	public List<ItemOpeningBalanceDetEntity> getItemOpeningBalanceDetEntities() {
		return itemOpeningBalanceDetEntities;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public void setItemOpeningBalanceDetEntities(List<ItemOpeningBalanceDetEntity> itemOpeningBalanceDetEntities) {
		this.itemOpeningBalanceDetEntities = itemOpeningBalanceDetEntities;
	}

	public static String[] getPkValues() {
        return new String[] { "MM", "MM_ITEMOPENINGBALANCE", "OPENBALID" };
    }
	

}
