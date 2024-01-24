package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MilestoneMasterDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5601767118551163859L;
	
	
	private Long msId;
	
	private Long iaId;
	
	private String iaName;
	
	private String milestoneId;
	
	private String description;
	
	private BigDecimal overallBudget;
	
	private Long targetAge;
	
	private BigDecimal percantageOfPayment;
	
	private Long orgId;

	private Long createdBy;

	private Long updatedBy;

	private Date createdDate;

	private Date updatedDate;

	private String lgIpMac;

	private String lgIpMacUpd;
	
	private String paymentStatus;
	
	private List<MilestoneDeliverablesDto> milestoneDeliverablesDtos = new ArrayList<>();

	private List<MilestoneCBBODetDto> milestoneCBBODetDtos = new ArrayList<>();

	public Long getMsId() {
		return msId;
	}

	public void setMsId(Long msId) {
		this.msId = msId;
	}

	public Long getIaId() {
		return iaId;
	}

	public void setIaId(Long iaId) {
		this.iaId = iaId;
	}

	public String getIaName() {
		return iaName;
	}

	public void setIaName(String iaName) {
		this.iaName = iaName;
	}

	public String getMilestoneId() {
		return milestoneId;
	}

	public void setMilestoneId(String milestoneId) {
		this.milestoneId = milestoneId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getOverallBudget() {
		return overallBudget;
	}

	public void setOverallBudget(BigDecimal overallBudget) {
		this.overallBudget = overallBudget;
	}

	public Long getTargetAge() {
		return targetAge;
	}

	public void setTargetAge(Long targetAge) {
		this.targetAge = targetAge;
	}

	public BigDecimal getPercantageOfPayment() {
		return percantageOfPayment;
	}

	public void setPercantageOfPayment(BigDecimal percantageOfPayment) {
		this.percantageOfPayment = percantageOfPayment;
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

	public List<MilestoneDeliverablesDto> getMilestoneDeliverablesDtos() {
		return milestoneDeliverablesDtos;
	}

	public void setMilestoneDeliverablesDtos(List<MilestoneDeliverablesDto> milestoneDeliverablesDtos) {
		this.milestoneDeliverablesDtos = milestoneDeliverablesDtos;
	}

	public List<MilestoneCBBODetDto> getMilestoneCBBODetDtos() {
		return milestoneCBBODetDtos;
	}

	public void setMilestoneCBBODetDtos(List<MilestoneCBBODetDto> milestoneCBBODetDtos) {
		this.milestoneCBBODetDtos = milestoneCBBODetDtos;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	
	
	

}
