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
@Table(name = "Tb_Sfac_Custom_Hiring_Service_Info_Detail")
public class CustomHiringServiceInfoDetailEntity implements Serializable {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7148410809035773421L;

	/**
	 * 
	 */
	

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "SERVICE_ID", nullable = false)
	private Long centerId;

	@ManyToOne
	@JoinColumn(name = "FPM_ID", referencedColumnName = "FPM_ID")
	private FPOProfileManagementMaster fpoProfileMgmtMaster;
	
	@Column(name="Rented_Item_Name")
	private String rentedItemName;
	
	@Column(name="Item_Quantity")
	private Long itemQuantity;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Rented_From_Date")
	private Date rentedFromDate;
	
	@Column(name="RENTED_AMT")
	private BigDecimal rentedAmount;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Rented_To_Date")
	private Date rentedToDate;
	
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

	public Long getCenterId() {
		return centerId;
	}

	
	
	



	public FPOProfileManagementMaster getFpoProfileMgmtMaster() {
		return fpoProfileMgmtMaster;
	}


	public void setFpoProfileMgmtMaster(FPOProfileManagementMaster fpoProfileMgmtMaster) {
		this.fpoProfileMgmtMaster = fpoProfileMgmtMaster;
	}



	public String getRentedItemName() {
		return rentedItemName;
	}



	public void setRentedItemName(String rentedItemName) {
		this.rentedItemName = rentedItemName;
	}



	public Long getItemQuantity() {
		return itemQuantity;
	}



	public void setItemQuantity(Long itemQuantity) {
		this.itemQuantity = itemQuantity;
	}



	public Date getRentedFromDate() {
		return rentedFromDate;
	}



	public void setRentedFromDate(Date rentedFromDate) {
		this.rentedFromDate = rentedFromDate;
	}



	public Date getRentedToDate() {
		return rentedToDate;
	}



	public void setRentedToDate(Date rentedToDate) {
		this.rentedToDate = rentedToDate;
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



	public void setCenterId(Long centerId) {
		this.centerId = centerId;
	}



	public String[] getPkValues() {
		return new String[] { "SFAC", "Tb_Sfac_Custom_Hiring_Service_Info_Detail", "SERVICE_ID" };
	}







	public BigDecimal getRentedAmount() {
		return rentedAmount;
	}







	public void setRentedAmount(BigDecimal rentedAmount) {
		this.rentedAmount = rentedAmount;
	}
	
	

}
