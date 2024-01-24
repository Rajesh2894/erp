package com.abm.mainet.common.dashboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class StoreItemWiseInventoryCntEntity {

	@Id
	@Column(name = "num")
	private int id;

	@Column(name = "item_group_name")
	private String itemGroupName;

	@Column(name = "item_description")
	private String itemDesc;

	@Column(name = "CNT")
	private Integer count;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getItemGroupName() {
		return itemGroupName;
	}

	public void setItemGroupName(String itemGroupName) {
		this.itemGroupName = itemGroupName;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "StoreItemWiseInventoryCntEntity [id=" + id + ", itemGroupName=" + itemGroupName + ", itemDesc="
				+ itemDesc + ", count=" + count + "]";
	}

}
