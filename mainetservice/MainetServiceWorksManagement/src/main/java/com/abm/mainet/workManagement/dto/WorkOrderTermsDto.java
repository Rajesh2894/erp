package com.abm.mainet.workManagement.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author Jeetendra.Pal
 *
 */
public class WorkOrderTermsDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1852935573097205790L;

	private Long termsId;
	private String termsDesc;
	private String active;
	private WorkOrderDto workOrder;
	private Long orgId;
	private Long createdBy;
	private Date createdDate;
	private Long updatedBy;
	private Date updatedDate;
	private String lgIpMac;
	private String lgIpMacUpd;
	public Long getTermsId() {
		return termsId;
	}
	public void setTermsId(Long termsId) {
		this.termsId = termsId;
	}
	public String getTermsDesc() {
		return termsDesc;
	}
	public void setTermsDesc(String termsDesc) {
		this.termsDesc = termsDesc;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public WorkOrderDto getWorkOrder() {
		return workOrder;
	}
	public void setWorkOrder(WorkOrderDto workOrder) {
		this.workOrder = workOrder;
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
	
}
