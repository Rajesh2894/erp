package com.abm.mainet.common.dashboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class StoreItemDataEntity {

	@Id
	@Column(name = "num")
	private int id;

	@Column(name = "Item_Code")
	private String itemCode;

	@Column(name = "item_description")
	private String itemDesc;

	@Column(name = "Item_Status")
	private String itemStatus;

	@Column(name = "Item_Group_Name")
	private String itemGroupName;

	@Column(name = "Item_belongs_to")
	private String itemBelongsTo;

	@Column(name = "Item_Valuation_Method")
	private String itemValuationMethod;

	@Column(name = "Purpose")
	private String itemPurpose;

	@Column(name = "Available_Quantity")
	private Integer itemAvailableQty;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public String getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus;
	}

	public String getItemGroupName() {
		return itemGroupName;
	}

	public void setItemGroupName(String itemGroupName) {
		this.itemGroupName = itemGroupName;
	}

	public String getItemBelongsTo() {
		return itemBelongsTo;
	}

	public void setItemBelongsTo(String itemBelongsTo) {
		this.itemBelongsTo = itemBelongsTo;
	}

	public String getItemValuationMethod() {
		return itemValuationMethod;
	}

	public void setItemValuationMethod(String itemValuationMethod) {
		this.itemValuationMethod = itemValuationMethod;
	}

	public String getItemPurpose() {
		return itemPurpose;
	}

	public void setItemPurpose(String itemPurpose) {
		this.itemPurpose = itemPurpose;
	}

	public Integer getItemAvailableQty() {
		return itemAvailableQty;
	}

	public void setItemAvailableQty(Integer itemAvailableQty) {
		this.itemAvailableQty = itemAvailableQty;
	}

	@Override
	public String toString() {
		return "StoreItemDataEntity [id=" + id + ", itemCode=" + itemCode + ", itemDesc=" + itemDesc + ", itemStatus="
				+ itemStatus + ", itemGroupName=" + itemGroupName + ", itemBelongsTo=" + itemBelongsTo
				+ ", itemValuationMethod=" + itemValuationMethod + ", itemPurpose=" + itemPurpose
				+ ", itemAvailableQty=" + itemAvailableQty + "]";
	}

}
