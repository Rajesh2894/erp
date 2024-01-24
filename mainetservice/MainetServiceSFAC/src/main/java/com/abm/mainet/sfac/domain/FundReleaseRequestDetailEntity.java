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
@Table(name = "Tb_SFAC_FUND_RELEASE_REQ_DET")
public class FundReleaseRequestDetailEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1915273109349336406L;
	
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "FRRD_ID", nullable = false)
	private Long frrdId;
	
	@ManyToOne
	@JoinColumn(name = "FRR_ID", referencedColumnName = "FRR_ID")
	private FundReleaseRequestMasterEntity fundReleaseRequestMasterEntity;
	
	@Column(name="PURPOSE_FOR")
	private Long purposeFor;
	
	@Column(name = "ALLOCATED_NO_OF_FPO")
	private Long allocatedNoOfFPO;
	
	@Column(name = "ALLOCATED_BUDGET")
	private BigDecimal allocatedBudget;
	
	@Column(name = "TOT_FUND_REL_TILL_DT")
	private BigDecimal totalFundRelTillDate;
	
	@Column(name = "UTILIZED_AMT")
	private BigDecimal utilizedAmount;
	
	@Column(name = "NEW_DEMAND")
	private BigDecimal newDemand;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "REMARK")
	private String remark;
	
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

	public Long getFrrdId() {
		return frrdId;
	}

	public void setFrrdId(Long frrdId) {
		this.frrdId = frrdId;
	}

	public FundReleaseRequestMasterEntity getFundReleaseRequestMasterEntity() {
		return fundReleaseRequestMasterEntity;
	}

	public void setFundReleaseRequestMasterEntity(FundReleaseRequestMasterEntity fundReleaseRequestMasterEntity) {
		this.fundReleaseRequestMasterEntity = fundReleaseRequestMasterEntity;
	}

	public Long getPurposeFor() {
		return purposeFor;
	}

	public void setPurposeFor(Long purposeFor) {
		this.purposeFor = purposeFor;
	}

	public Long getAllocatedNoOfFPO() {
		return allocatedNoOfFPO;
	}

	public void setAllocatedNoOfFPO(Long allocatedNoOfFPO) {
		this.allocatedNoOfFPO = allocatedNoOfFPO;
	}

	public BigDecimal getAllocatedBudget() {
		return allocatedBudget;
	}

	public void setAllocatedBudget(BigDecimal allocatedBudget) {
		this.allocatedBudget = allocatedBudget;
	}

	public BigDecimal getTotalFundRelTillDate() {
		return totalFundRelTillDate;
	}

	public void setTotalFundRelTillDate(BigDecimal totalFundRelTillDate) {
		this.totalFundRelTillDate = totalFundRelTillDate;
	}

	public BigDecimal getUtilizedAmount() {
		return utilizedAmount;
	}

	public void setUtilizedAmount(BigDecimal utilizedAmount) {
		this.utilizedAmount = utilizedAmount;
	}

	public BigDecimal getNewDemand() {
		return newDemand;
	}

	public void setNewDemand(BigDecimal newDemand) {
		this.newDemand = newDemand;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
		return new String[] { "SFAC", "Tb_SFAC_FUND_RELEASE_REQ_DET", "FRRD_ID" };
	}

}
