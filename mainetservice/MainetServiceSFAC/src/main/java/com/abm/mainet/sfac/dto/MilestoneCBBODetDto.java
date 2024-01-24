package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.sfac.domain.MilestoneMasterEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class MilestoneCBBODetDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3206888455415634819L;
	
	
	private Long msdId;
	
	@JsonIgnore
	private MilestoneMasterDto milestoneMasterDto;
	
	private Long cbboID;
	
	private String cbboName;
	
	private Date dateOfWorkOrder;
	
	private Long targetAge;
	
	private Long noOfFPO;
	
	private BigDecimal allocationBudget;
	
	private Long orgId;

	private Long createdBy;

	private Long updatedBy;

	private Date createdDate;

	private Date updatedDate;

	private String lgIpMac;

	private String lgIpMacUpd;
	
	private Long paymentStatus;
	
	

	public Long getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(Long paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Long getMsdId() {
		return msdId;
	}

	public void setMsdId(Long msdId) {
		this.msdId = msdId;
	}

	public MilestoneMasterDto getMilestoneMasterDto() {
		return milestoneMasterDto;
	}

	public void setMilestoneMasterDto(MilestoneMasterDto milestoneMasterDto) {
		this.milestoneMasterDto = milestoneMasterDto;
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
	
	

}
