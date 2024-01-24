package com.abm.mainet.workManagement.domain;

import java.io.Serializable;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.domain.ContractMastEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author Jeetendra.Pal
 * @Since 14-May-2018
 *
 */
@Entity
@Table(name = "TB_WMS_WORKEORDER")
public class WorkOrder implements Serializable {

	private static final long serialVersionUID = 3086787106340483749L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "WORKOR_ID", nullable = false)
	private Long workId;

	@Column(name = "WORKOR_STARTDATE", nullable = false)
	private Date startDate;

	@Column(name = "WORKOR_NO", nullable = false)
	private String workOrderNo;

	@Column(name = "WORKOR_DEFECTLIABILITYPER", nullable = false)
	private Long liabilityPeriod;

	@Column(name = "WORKOR_DATE", nullable = true)
	private Date orderDate;

	@Column(name = "WORKOR_AGTODATE", nullable = false)
	private Date contractToDate;

	@Column(name = "WORKOR_AGFROMDATE", nullable = false)
	private Date contractFromDate;
	
	@Column(name = "COMPLETION_DATE", nullable = true)
	private Date completionDate;

	@OneToOne
	@JoinColumn(name = "TND_ID", referencedColumnName = "TND_ID", nullable = true)
	private TenderMasterEntity tenderMasterEntity;

	@OneToOne
	@JoinColumn(name = "CONT_ID", referencedColumnName = "CONT_ID", nullable = true)
	private ContractMastEntity contractMastEntity;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "workOrder", cascade = CascadeType.ALL)
	private List<WorkOrderTerms> workOrderTermsList = new ArrayList<>();

	@Column(name = "ORGID", nullable = false)
	private Long orgId;

	@Column(name = "CREATED_BY", nullable = false)
	private Long createdBy;

	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;

	@Column(name = "UPDATED_BY", nullable = true)
	private Long updatedBy;

	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;

	@Column(name = "LG_IP_MAC", nullable = false)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", nullable = true)
	private String lgIpMacUpd;

	@Column(name = "WORKOR_STATUS")
	private String workOrderStatus;

	@Column(name = "WORK_ASSIGNEE")
	private String workAssignee;

	@Column(name = "WORK_ASSIGNEEDATE")
	private Date workAssigneeDate;
	
	@Column(name = "REMARK", nullable = true)
	private String remark;

	public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getWorkOrderNo() {
		return workOrderNo;
	}

	public void setWorkOrderNo(String workOrderNo) {
		this.workOrderNo = workOrderNo;
	}

	public Long getLiabilityPeriod() {
		return liabilityPeriod;
	}

	public void setLiabilityPeriod(Long liabilityPeriod) {
		this.liabilityPeriod = liabilityPeriod;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public ContractMastEntity getContractMastEntity() {
		return contractMastEntity;
	}

	public void setContractMastEntity(ContractMastEntity contractMastEntity) {
		this.contractMastEntity = contractMastEntity;
	}

	public List<WorkOrderTerms> getWorkOrderTermsList() {
		return workOrderTermsList;
	}

	public void setWorkOrderTermsList(List<WorkOrderTerms> workOrderTermsList) {
		this.workOrderTermsList = workOrderTermsList;
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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

	public TenderMasterEntity getTenderMasterEntity() {
		return tenderMasterEntity;
	}

	public void setTenderMasterEntity(TenderMasterEntity tenderMasterEntity) {
		this.tenderMasterEntity = tenderMasterEntity;
	}

	public Date getContractFromDate() {
		return contractFromDate;
	}

	public void setContractFromDate(Date contractFromDate) {
		this.contractFromDate = contractFromDate;
	}

	public Date getContractToDate() {
		return contractToDate;
	}

	public void setContractToDate(Date contractToDate) {
		this.contractToDate = contractToDate;
	}

	public String getWorkOrderStatus() {
		return workOrderStatus;
	}

	public void setWorkOrderStatus(String workOrderStatus) {
		this.workOrderStatus = workOrderStatus;
	}

	public String getWorkAssignee() {
		return workAssignee;
	}

	public void setWorkAssignee(String workAssignee) {
		this.workAssignee = workAssignee;
	}

	public Date getWorkAssigneeDate() {
		return workAssigneeDate;
	}
	
	public Date getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setWorkAssigneeDate(Date workAssigneeDate) {
		this.workAssigneeDate = workAssigneeDate;
	}

	public String[] getPkValues() {
		return new String[] { "WMS", "TB_WMS_WORKEORDER", "WORKOR_ID" };
	}
}
