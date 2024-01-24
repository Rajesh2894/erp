package com.abm.mainet.sfac.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Tb_Sfac_Equipment_Info_Detail")
public class EquipmentInfoDetailEntity implements Serializable {
	





	/**
	 * 
	 */
	private static final long serialVersionUID = 8776682916796918546L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "EQP_ID", nullable = false)
	private Long eqpId;

	@ManyToOne
	@JoinColumn(name = "FPM_ID", referencedColumnName = "FPM_ID")
	private FPOProfileManagementMaster fpoProfileMgmtMaster;
	
	@Column(name="EQIP_NAME")
	private String equipmentName;
	
	@Column(name="NO_OF_EQIP")
	private Long noOfEquipment;
	
	@Column(name="PRICE_OF_EQIP")
	private BigDecimal priceOfEquipment;
	
	@Column(name="EQIP_DESC")
	private String equipmentDesc;
	
	@Column(name = "ORGID", nullable = false)
	private Long orgId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;

	@Column(name = "CREATED_BY", nullable = false)
	private Long createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Column(name = "LG_IP_MAC", nullable = false, length = 100)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100)
	private String lgIpMacUpd;


	





	public Long getEqpId() {
		return eqpId;
	}








	public void setEqpId(Long eqpId) {
		this.eqpId = eqpId;
	}








	public FPOProfileManagementMaster getFpoProfileMgmtMaster() {
		return fpoProfileMgmtMaster;
	}








	public void setFpoProfileMgmtMaster(FPOProfileManagementMaster fpoProfileMgmtMaster) {
		this.fpoProfileMgmtMaster = fpoProfileMgmtMaster;
	}








	public String getEquipmentName() {
		return equipmentName;
	}








	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}








	public Long getNoOfEquipment() {
		return noOfEquipment;
	}








	public void setNoOfEquipment(Long noOfEquipment) {
		this.noOfEquipment = noOfEquipment;
	}








	public BigDecimal getPriceOfEquipment() {
		return priceOfEquipment;
	}








	public void setPriceOfEquipment(BigDecimal priceOfEquipment) {
		this.priceOfEquipment = priceOfEquipment;
	}








	public String getEquipmentDesc() {
		return equipmentDesc;
	}








	public void setEquipmentDesc(String equipmentDesc) {
		this.equipmentDesc = equipmentDesc;
	}








	public Long getOrgId() {
		return orgId;
	}








	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}








	public Date getCreatedDate() {
		return createdDate;
	}








	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}








	public Long getCreatedBy() {
		return createdBy;
	}








	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}








	public Date getUpdatedDate() {
		return updatedDate;
	}








	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}








	public Long getUpdatedBy() {
		return updatedBy;
	}








	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
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
		return new String[] { "SFAC", "Tb_Sfac_Equipment_Info_Detail", "EQP_ID" };
	}

}
