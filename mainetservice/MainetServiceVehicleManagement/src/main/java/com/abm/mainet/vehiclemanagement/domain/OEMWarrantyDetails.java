package com.abm.mainet.vehiclemanagement.domain;

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
@Table(name = "tb_vm_oem_warrantydet")

public class OEMWarrantyDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6872746566293447726L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "OEMD_ID", unique = true, nullable = false)
	private Long oemDetId;

	@Column(name = "CREATED_BY", nullable = true)
	private Long createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;

	@Column(name = "LG_IP_MAC", nullable = false, length = 100)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100)
	private String lgIpMacUpd;

	@Column(nullable = false)
	private Long orgid;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "part_type", nullable = false)
	private Long partType;

	@Column(name = "part_position", nullable = false)
	private Long partPosition;

	@Column(name = "part_name", nullable = false)
	private String partName;

	@Column(name = "warranty_period")
	private Long warrantyPeriod;

	@Temporal(TemporalType.DATE)
	@Column(name = "part_puchaseDT", nullable = false)
	private Date purchaseDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "last_dt_warranty", nullable = false)
	private Date lastDateOfWarranty;

	// bi-directional many-to-one association to TbSwVehicleScheduling
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OEM_ID", nullable = false)
	private OEMWarranty tboemwarranty;
	
//	//@JsonIgnore
//	@OneToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "BR_ID",  nullable = true)
//	//comments : Birth identity
//	private BirthRegistrationEntity	 brId;
	@Column(name = "unit")
	private Long unit;
	
	@Column(name = "PART_ID_NO")
	private String partIdNo;
	
	@Column(name = "PART_POSITION_DESC")
	private String partPositionDesc;
	
	@Column(name = "PART_QTY")
	private Long partQty;
	
	@Column(name = "PART_REMARK")
	private String partRemark;

	
	public OEMWarrantyDetails() {
	}

	public Long getOemDetId() {
		return oemDetId;
	}

	public void setOemDetId(Long oemDetId) {
		this.oemDetId = oemDetId;
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

	public String getLgIpMacUpd() {
		return lgIpMacUpd;
	}

	public void setLgIpMacUpd(String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}

	public Long getOrgid() {
		return orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
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

	public Long getPartType() {
		return partType;
	}

	public void setPartType(Long partType) {
		this.partType = partType;
	}

	public Long getPartPosition() {
		return partPosition;
	}

	public void setPartPosition(Long partPosition) {
		this.partPosition = partPosition;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public Long getWarrantyPeriod() {
		return warrantyPeriod;
	}

	public void setWarrantyPeriod(Long warrantyPeriod) {
		this.warrantyPeriod = warrantyPeriod;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}



	public Date getLastDateOfWarranty() {
		return lastDateOfWarranty;
	}

	public void setLastDateOfWarranty(Date lastDateOfWarranty) {
		this.lastDateOfWarranty = lastDateOfWarranty;
	}

	public OEMWarranty getTboemwarranty() {
		return tboemwarranty;
	}

	public void setTboemwarranty(OEMWarranty tboemwarranty) {
		this.tboemwarranty = tboemwarranty;
	}

	public Long getUnit() {
		return unit;
	}

	public void setUnit(Long unit) {
		this.unit = unit;
	}



	public String getPartIdNo() {
		return partIdNo;
	}

	public void setPartIdNo(String partIdNo) {
		this.partIdNo = partIdNo;
	}

	public String getPartPositionDesc() {
		return partPositionDesc;
	}

	public void setPartPositionDesc(String partPositionDesc) {
		this.partPositionDesc = partPositionDesc;
	}

	public Long getPartQty() {
		return partQty;
	}

	public void setPartQty(Long partQty) {
		this.partQty = partQty;
	}

	public String getPartRemark() {
		return partRemark;
	}

	public void setPartRemark(String partRemark) {
		this.partRemark = partRemark;
	}

	public String[] getPkValues() {

		return new String[] { "VM", "tb_vm_oem_warrantydet", "OEMD_ID" };
	}


}
