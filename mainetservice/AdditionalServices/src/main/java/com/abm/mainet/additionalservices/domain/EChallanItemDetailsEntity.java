/**
 * 
 */
package com.abm.mainet.additionalservices.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author divya.marshettiwar
 *
 */
@Entity
@Table(name = "Tb_Echallan_Itemdet")
public class EChallanItemDetailsEntity implements Serializable{

	private static final long serialVersionUID = 3260066776411636316L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "ITEM_ID")
	private Long itemId;
	
	@Column(name = "ITEM_NO", nullable = true)
	private String itemNo;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="CHLN_ID") 	
	private EChallanMasterEntity challanMaster;
	
	@Column(name = "ITEM_DESC", nullable = true)
	private String itemDesc;
	
	@Column(name = "ITEM_NAME")
	private String itemName;
		
	@Column(name = "ITEM_QUANT")
	private Long itemQuantity;
	
	@Column(name = "STORE_ID", nullable = true)
	private String storeId;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "ORGID")
	private Long orgid;
	
	@Column(name = "CREATED_BY", nullable = true)
	private Long createdBy;
	
	@Column(name = "CREATED_Date")
	private Date createdDate;
	
	@Column(name = "LG_IP_MAC")
	private String lgIpMac;
	
	@Column(name = "UPDATED_BY", nullable = true)
	private Long updatedBy;
	
	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;
	
	@Column(name = "LG_IP_MAC_UPD", nullable = true)
	private String lgIpMacUpd;
	
	@Column(name = "ITEM_AMOUNT", nullable = true)
	private Double itemAmount;
	
	public static String[] getPkValues() {
		return new String[] { "ENC", "Tb_Echallan_Itemdet", "ITEM_ID" };
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public String getItemNo() {
		return itemNo;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public Long getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(Long itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getOrgid() {
		return orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
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

	public String getLgIpMac() {
		return lgIpMac;
	}

	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
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

	public String getLgIpMacUpd() {
		return lgIpMacUpd;
	}

	public void setLgIpMacUpd(String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}

	public EChallanMasterEntity getChallanMaster() {
		return challanMaster;
	}

	public void setChallanMaster(EChallanMasterEntity challanMaster) {
		this.challanMaster = challanMaster;
	}
	public Double getItemAmount() {
		return itemAmount;
	}
	public void setItemAmount(Double itemAmount) {
		this.itemAmount = itemAmount;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
}
