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
@Table(name = "TB_SFAC_MILESTONE_CBBO_DET")
public class MilestoneCBBODetEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2588156262703041892L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "MSCBBO_ID")
	private Long msdId;
	
	@ManyToOne
	@JoinColumn(name = "MS_ID", referencedColumnName = "MS_ID")
	private MilestoneMasterEntity milestoneMasterEntity;
	
	@Column(name = "CBBO_ID")
	private Long cbboID;
	
	@Column(name = "CBBO_NAME")
	private String cbboName;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_OF_WO")
	private Date dateOfWorkOrder;
	
	@Column(name = "TARGET_AGE")
	private Long targetAge;
	
	@Column(name = "NO_OF_FPO")
	private Long noOfFPO;
	
	@Column(name = "ALLOCATION_BUDGET")
	private BigDecimal allocationBudget;
	
	@Column(name = "PAYMENT_STATUS")
	private Long paymentStatus;
	
	@Column(name = "ORGID", nullable = false, updatable = false)
	private Long orgId;

	@Column(name = "CREATED_BY", nullable = false, updatable = false)
	private Long createdBy;

	@Column(name = "UPDATED_BY", nullable = true, updatable = true)
	private Long updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;

	@Column(name = "LG_IP_MAC", length = 100, nullable = false)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
	private String lgIpMacUpd;
	
	
	
	public Long getMsdId() {
		return msdId;
	}



	public void setMsdId(Long msdId) {
		this.msdId = msdId;
	}



	public MilestoneMasterEntity getMilestoneMasterEntity() {
		return milestoneMasterEntity;
	}



	public void setMilestoneMasterEntity(MilestoneMasterEntity milestoneMasterEntity) {
		this.milestoneMasterEntity = milestoneMasterEntity;
	}



	public Long getCbboID() {
		return cbboID;
	}



	public void setCbboID(Long cbboID) {
		this.cbboID = cbboID;
	}



	public String getCbboName() {
		return cbboName;
	}



	public void setCbboName(String cbboName) {
		this.cbboName = cbboName;
	}



	public Date getDateOfWorkOrder() {
		return dateOfWorkOrder;
	}



	public void setDateOfWorkOrder(Date dateOfWorkOrder) {
		this.dateOfWorkOrder = dateOfWorkOrder;
	}



	public Long getTargetAge() {
		return targetAge;
	}



	public void setTargetAge(Long targetAge) {
		this.targetAge = targetAge;
	}



	public Long getNoOfFPO() {
		return noOfFPO;
	}



	public void setNoOfFPO(Long noOfFPO) {
		this.noOfFPO = noOfFPO;
	}



	public BigDecimal getAllocationBudget() {
		return allocationBudget;
	}



	public void setAllocationBudget(BigDecimal allocationBudget) {
		this.allocationBudget = allocationBudget;
	}



	public Long getOrgId() {
		return orgId;
	}



	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}



	public Long getCreatedBy() {
		return createdBy;
	}



	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}



	public Long getUpdatedBy() {
		return updatedBy;
	}



	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}



	public Date getCreatedDate() {
		return createdDate;
	}



	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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


	

	public Long getPaymentStatus() {
		return paymentStatus;
	}



	public void setPaymentStatus(Long paymentStatus) {
		this.paymentStatus = paymentStatus;
	}



	public String[] getPkValues() {
		return new String[] { "SFAC", "TB_SFAC_MILESTONE_CBBO_DET", "MSCBBO_ID" };
	}

}
