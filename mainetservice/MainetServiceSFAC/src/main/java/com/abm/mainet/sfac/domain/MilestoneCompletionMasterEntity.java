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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_SFAC_MILESTONE_COMP_MASTER")
public class MilestoneCompletionMasterEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2862622371689844665L;
	
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "MSC_ID")
	private Long mscId;
	
	@Column(name = "IA_ID")
	private Long iaId;
	
	@Column(name = "IA_NAME")
	private String iaName;
	
	
	@Column(name = "CBBO_ID")
	private Long cbboId;
	
	@Column(name = "MILESTONE_NAME")
	private String milestoneName;
	
	@Column(name = "MS_ID")
	private Long msId;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_OF_WO")
	private Date dateOfWorkOrder;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "TARGET_DATE")
	private Date targetDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "ACTUAL_COMP_DATE")
	private Date actualCompletionDate;
	
	@Column(name = "ALLOCATION_BUDGET")
	private BigDecimal allocationBudget;
	
	@Column(name = "INVOICE_AMT")
	private BigDecimal invoiceAmount;
	
	@Column(name = "INVOICE_DESC")
	private String invoiceDesc;
	
	@Column(name = "APP_NO")
	private Long applicationNumber;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "AUTH_REMARK")
	private String authRemark;
	
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
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "milestoneCompletionMasterEntity", cascade = CascadeType.ALL)
	private List<MilestoneCompletionDocDetailsEntity> milestoneCompletionDocDetailsEntities = new ArrayList<>();
	
	

	public Long getCbboId() {
		return cbboId;
	}

	public void setCbboId(Long cbboId) {
		this.cbboId = cbboId;
	}

	public Long getMscId() {
		return mscId;
	}

	public void setMscId(Long mscId) {
		this.mscId = mscId;
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

	public String getMilestoneName() {
		return milestoneName;
	}

	public void setMilestoneName(String milestoneName) {
		this.milestoneName = milestoneName;
	}

	public Long getMsId() {
		return msId;
	}

	public void setMsId(Long msId) {
		this.msId = msId;
	}

	public Date getDateOfWorkOrder() {
		return dateOfWorkOrder;
	}

	public void setDateOfWorkOrder(Date dateOfWorkOrder) {
		this.dateOfWorkOrder = dateOfWorkOrder;
	}

	public Date getTargetDate() {
		return targetDate;
	}

	public void setTargetDate(Date targetDate) {
		this.targetDate = targetDate;
	}

	public Date getActualCompletionDate() {
		return actualCompletionDate;
	}

	public void setActualCompletionDate(Date actualCompletionDate) {
		this.actualCompletionDate = actualCompletionDate;
	}

	public BigDecimal getAllocationBudget() {
		return allocationBudget;
	}

	public void setAllocationBudget(BigDecimal allocationBudget) {
		this.allocationBudget = allocationBudget;
	}

	public String getInvoiceDesc() {
		return invoiceDesc;
	}

	public void setInvoiceDesc(String invoiceDesc) {
		this.invoiceDesc = invoiceDesc;
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

	public List<MilestoneCompletionDocDetailsEntity> getMilestoneCompletionDocDetailsEntities() {
		return milestoneCompletionDocDetailsEntities;
	}

	public void setMilestoneCompletionDocDetailsEntities(
			List<MilestoneCompletionDocDetailsEntity> milestoneCompletionDocDetailsEntities) {
		this.milestoneCompletionDocDetailsEntities = milestoneCompletionDocDetailsEntities;
	}
	
	public String[] getPkValues() {
		return new String[] { "SFAC", "TB_SFAC_MILESTONE_COMP_MASTER", "MSC_ID" };
	}

	public Long getApplicationNumber() {
		return applicationNumber;
	}

	public void setApplicationNumber(Long applicationNumber) {
		this.applicationNumber = applicationNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAuthRemark() {
		return authRemark;
	}

	public void setAuthRemark(String authRemark) {
		this.authRemark = authRemark;
	}

	public BigDecimal getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(BigDecimal invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}
	
	
	
}
