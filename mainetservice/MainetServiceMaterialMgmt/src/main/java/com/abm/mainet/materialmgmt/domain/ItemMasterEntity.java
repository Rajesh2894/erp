/**
 *
 */
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

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "MM_ITEMMASTER")

public class ItemMasterEntity implements Serializable {

    private static final long serialVersionUID = 8683880608834172574L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "Itemid", nullable = false)
    private Long itemId;
    
    @OneToMany(mappedBy = "itemMasterEntity", targetEntity = ItemMasterConversionEntity.class,fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ItemMasterConversionEntity> itemMasterConversionEntity ;
    
    @OneToMany(mappedBy = "itemMasterEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<GrnInspectionItemDetEntity> grnItemEntities=new ArrayList<>();;

    public List<ItemMasterConversionEntity> getItemMasterConversionEntity() {
		return itemMasterConversionEntity;
	}

	public void setItemMasterConversionEntity(List<ItemMasterConversionEntity> itemMasterConversionEntity) {
		this.itemMasterConversionEntity = itemMasterConversionEntity;
	}


	@Column(name = "itemcode", nullable = false)
    private String itemCode;
   
    @Column(name = "name",  nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "uom", nullable = false)
    private Long uom;
    
    @Column(name = "itemgroup", nullable = false)
    private Long itemGroup;
    
    @Column(name = "itemsubgroup", nullable = false)
    private Long itemSubGroup;
    
    @Column(name = "type", nullable = false)
    private Long type;
    
    @Column(name = "isasset", length = 1 , nullable = true)
    private String isAsset;
    
    @Column(name = "classification", nullable = true)
    private Long classification;
    
    @Column(name = "valuemethod" , nullable = true)
    private String valueMethod;
    
    @Column(name = "management", length = 1, nullable = true)
    private String management;
    
    @Column(name = "minlevel", nullable = true)
    private Long minLevel;
    
    @Column(name = "reorderlevel" , nullable = true)
    private Long reorderLevel;
    
    @Column(name = "category" , nullable = false)
    private Long category;
    
    @Column(name = "isexpiry", length = 1 , nullable = true)
    private String isExpiry;
    
    @Column(name = "expirytype" , nullable = true)
    private Long expiryType;
    
    @Column(name = "hsncode" , nullable = true)
    private Long hsnCode;
    
    @Column(name = "taxpercentage" , nullable = true)
    private Double taxPercentage;
    
    @Column(name = "Status", length = 1 , nullable = true)
    private String status;
   
    @Column(name = "EntryFlag", length = 1, nullable = false)
    private String entryFlag;
    
    
    @Column(name = "ORGID", nullable = false)
    private Long orgId;
    
    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "LANGID", nullable = false)
    private Long langId;

    @Temporal(TemporalType.DATE)
    @Column(name = "LMODDATE", nullable = true)
    private Date lmodDate;

    @Column(name = "UPDATED_BY" , nullable = true)
    private Long updatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATED_DATE" , nullable = true)
    private Date updatedDate;

    @JsonIgnore
    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    private String lgIpMac;

    @JsonIgnore
    @Column(name = "LG_IP_MAC_UPD", length = 100 , nullable = true)
    private String lgIpMacUpd;

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getUom() {
		return uom;
	}

	public void setUom(Long uom) {
		this.uom = uom;
	}

	public Long getItemGroup() {
		return itemGroup;
	}

	public void setItemGroup(Long itemGroup) {
		this.itemGroup = itemGroup;
	}

	public Long getItemSubGroup() {
		return itemSubGroup;
	}

	public void setItemSubGroup(Long itemSubGroup) {
		this.itemSubGroup = itemSubGroup;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public String getIsAsset() {
		return isAsset;
	}

	public void setIsAsset(String isAsset) {
		this.isAsset = isAsset;
	}

	public Long getClassification() {
		return classification;
	}

	public void setClassification(Long classification) {
		this.classification = classification;
	}

	public String getValueMethod() {
		return valueMethod;
	}

	public void setValueMethod(String valueMethod) {
		this.valueMethod = valueMethod;
	}

	public String getManagement() {
		return management;
	}

	public void setManagement(String management) {
		this.management = management;
	}

	public Long getMinLevel() {
		return minLevel;
	}

	public void setMinLevel(Long minLevel) {
		this.minLevel = minLevel;
	}

	public Long getReorderLevel() {
		return reorderLevel;
	}

	public void setReorderLevel(Long reorderLevel) {
		this.reorderLevel = reorderLevel;
	}

	public Long getCategory() {
		return category;
	}

	public void setCategory(Long category) {
		this.category = category;
	}

	public String getIsExpiry() {
		return isExpiry;
	}

	public void setIsExpiry(String isExpiry) {
		this.isExpiry = isExpiry;
	}

	public Long getExpiryType() {
		return expiryType;
	}

	public void setExpiryType(Long expiryType) {
		this.expiryType = expiryType;
	}

	public Long getHsnCode() {
		return hsnCode;
	}

	public void setHsnCode(Long hsnCode) {
		this.hsnCode = hsnCode;
	}

	public Double getTaxPercentage() {
		return taxPercentage;
	}

	public void setTaxPercentage(Double taxPercentage) {
		this.taxPercentage = taxPercentage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEntryFlag() {
		return entryFlag;
	}

	public void setEntryFlag(String entryFlag) {
		this.entryFlag = entryFlag;
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

	public static String[] getPkValues() {
        return new String[] { "ITM", "mm_itemmaster", "Itemid" };
    }
	
	public ItemMasterEntity() {
        super();
    }
	
	public List<GrnInspectionItemDetEntity> getGrnItemEntities() {
		return grnItemEntities;
	}

	public void setGrnItemEntities(List<GrnInspectionItemDetEntity> grnItemEntities) {
		this.grnItemEntities = grnItemEntities;
	}

	@Override
	public String toString() {
		return "ItemMasterEntity [itemId=" + itemId + ", itemMasterConversionEntity=" + itemMasterConversionEntity
				+ ", itemCode=" + itemCode + ", name=" + name + ", description=" + description + ", uom=" + uom
				+ ", itemGroup=" + itemGroup + ", itemSubGroup=" + itemSubGroup + ", type=" + type + ", isAsset="
				+ isAsset + ", classification=" + classification + ", valueMethod=" + valueMethod + ", management="
				+ management + ", minLevel=" + minLevel + ", reorderLevel=" + reorderLevel + ", category=" + category
				+ ", isExpiry=" + isExpiry + ", expiryType=" + expiryType + ", hsnCode=" + hsnCode + ", taxPercentage="
				+ taxPercentage + ", status=" + status + ", entryFlag=" + entryFlag + ", orgId=" + orgId + ", userId="
				+ userId + ", langId=" + langId + ", lmodDate=" + lmodDate + ", updatedBy=" + updatedBy
				+ ", updatedDate=" + updatedDate + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd + "]";
	}

}