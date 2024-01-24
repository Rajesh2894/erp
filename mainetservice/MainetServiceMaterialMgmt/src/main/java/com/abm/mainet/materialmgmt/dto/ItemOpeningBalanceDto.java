package com.abm.mainet.materialmgmt.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ItemOpeningBalanceDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
    private Long openBalId;
	private Date openingDate;
	private Long storeId;
	private String storeName;
	private String itemCode;
	private Long itemId;
	private BigDecimal openingBalance;
	private BigDecimal unitPrice;
	private String group;
	private String subGroup;
	private String itemName;
	private String uom;
	private String valueMethod;
	private Long orgId;
	private Long userId;
	private Long langId;
	private Date lmodDate;
	private Long updatedBy;
	private Date updatedDate;
	private String lgIpMac;
	private String lgIpMacUpd;
	private Long createdBy;
	private Date createdDate;
	private Long itemGroup1;
    private Long itemGroup2;
    private Long uomId;
    private Long methodId;
    private Long locId;
    private boolean status;
    private String valueMethodCode;
    private String isExpiry;
    private List<ItemOpeningBalanceDetDto> itemOpeningBalanceDetDto;
    
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
	public Long getStoreId() {
		return storeId;
	}
	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
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
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getSubGroup() {
		return subGroup;
	}
	public void setSubGroup(String subGroup) {
		this.subGroup = subGroup;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public String getValueMethod() {
		return valueMethod;
	}
	public void setValueMethod(String valueMethod) {
		this.valueMethod = valueMethod;
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
	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Long getItemGroup1() {
		return itemGroup1;
	}
	public void setItemGroup1(Long itemGroup1) {
		this.itemGroup1 = itemGroup1;
	}
	public Long getItemGroup2() {
		return itemGroup2;
	}
	public void setItemGroup2(Long itemGroup2) {
		this.itemGroup2 = itemGroup2;
	}
	
	public Long getUomId() {
		return uomId;
	}
	public void setUomId(Long uomId) {
		this.uomId = uomId;
	}
	public Long getMethodId() {
		return methodId;
	}
	public void setMethodId(Long methodId) {
		this.methodId = methodId;
	}
	public Long getLocId() {
		return locId;
	}
	public void setLocId(Long locId) {
		this.locId = locId;
	}
	public List<ItemOpeningBalanceDetDto> getItemOpeningBalanceDetDto() {
		return itemOpeningBalanceDetDto;
	}
	public void setItemOpeningBalanceDetDto(List<ItemOpeningBalanceDetDto> itemOpeningBalanceDetDto) {
		this.itemOpeningBalanceDetDto = itemOpeningBalanceDetDto;
	}
	public String getValueMethodCode() {
		return valueMethodCode;
	}
	public void setValueMethodCode(String valueMethodCode) {
		this.valueMethodCode = valueMethodCode;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getIsExpiry() {
		return isExpiry;
	}
	public void setIsExpiry(String isExpiry) {
		this.isExpiry = isExpiry;
	}

	
}
