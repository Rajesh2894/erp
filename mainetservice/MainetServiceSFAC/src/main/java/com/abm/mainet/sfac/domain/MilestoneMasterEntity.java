package com.abm.mainet.sfac.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_SFAC_MILESTONE_MASTER")
public class MilestoneMasterEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7069159092233825707L;
	
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "MS_ID")
	private Long msId;
	
	@Column(name = "IA_ID")
	private Long iaId;
	
	@Column(name = "IA_NAME")
	private String iaName;
	
	@Column(name = "MILESTONE_ID")
	private String milestoneId;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "OVERALL_BUDGET")
	private BigDecimal overallBudget;
	
	@Column(name = "TARGET_AGE")
	private Long targetAge;
	
	@Column(name = "PER_OF_PAYMENT")
	private BigDecimal percantageOfPayment;
	
	@Column(name = "PAYMENT_STATUS")
	private String paymentStatus;
	
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
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "milestoneMasterEntity", cascade = CascadeType.ALL)
	private List<MilestoneDeliverablesEntity> milestoneDeliverablesEntities = new ArrayList<>();

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "milestoneMasterEntity", cascade = CascadeType.ALL)
	private List<MilestoneCBBODetEntity> milestoneCBBODetEntities = new ArrayList<>();
	
	
	
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



	public List<MilestoneDeliverablesEntity> getMilestoneDeliverablesEntities() {
		return milestoneDeliverablesEntities;
	}



	public void setMilestoneDeliverablesEntities(List<MilestoneDeliverablesEntity> milestoneDeliverablesEntities) {
		this.milestoneDeliverablesEntities = milestoneDeliverablesEntities;
	}



	public List<MilestoneCBBODetEntity> getMilestoneCBBODetEntities() {
		return milestoneCBBODetEntities;
	}



	public void setMilestoneCBBODetEntities(List<MilestoneCBBODetEntity> milestoneCBBODetEntities) {
		this.milestoneCBBODetEntities = milestoneCBBODetEntities;
	}



	public String getPaymentStatus() {
		return paymentStatus;
	}



	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}



	public String[] getPkValues() {
		return new String[] { "SFAC", "TB_SFAC_MILESTONE_MASTER", "MS_ID" };
	}

}
