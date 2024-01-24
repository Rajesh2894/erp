package com.abm.mainet.workManagement.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author vishwajeet.kumar
 * @since 21 May 2018
 */
@Entity
@Table(name = "TB_WMS_WORKEORDER_HIST")
public class WorkOrderHistory implements Serializable {

	private static final long serialVersionUID = 3086787106340483749L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "WORKOR_ID_H", nullable = false)
	private Long workHistId;

	@Column(name = "WORKOR_ID", nullable = false)
	private Long workId;

	@Column(name = "CONT_ID")
	private Long contId;

	@Column(name = "TND_ID")
	private Long tenderId;

	@Column(name = "WORKOR_STARTDATE", nullable = false)
	private Date startDate;

	@Column(name = "WORKOR_NO", nullable = false)
	private String workOrderNo;

	@Column(name = "WORKOR_DEFECTLIABILITYPER", nullable = false)
	private Long liabilityPeriod;

	@Column(name = "WORKOR_DATE", nullable = true)
	private Date orderDate;

	@Column(name = "WORKOR_AGTODATE", nullable = false)
	private Date agreementFromDate;

	@Column(name = "WORKOR_AGFROMDATE", nullable = false)
	private Date agreementToDate;

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

	@Column(name = "H_STATUS", length = 1)
	private String status;

	public Long getWorkHistId() {
		return workHistId;
	}

	public void setWorkHistId(Long workHistId) {
		this.workHistId = workHistId;
	}

	public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}

	public Long getContId() {
		return contId;
	}

	public void setContId(Long contId) {
		this.contId = contId;
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

	public Date getAgreementFromDate() {
		return agreementFromDate;
	}

	public void setAgreementFromDate(Date agreementFromDate) {
		this.agreementFromDate = agreementFromDate;
	}

	public Date getAgreementToDate() {
		return agreementToDate;
	}

	public void setAgreementToDate(Date agreementToDate) {
		this.agreementToDate = agreementToDate;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getTenderId() {
		return tenderId;
	}

	public void setTenderId(Long tenderId) {
		this.tenderId = tenderId;
	}

	public String[] getPkValues() {
		return new String[] { "WMS", "TB_WMS_WORKEORDER_HIST", "WORKOR_ID_H" };
	}
}
