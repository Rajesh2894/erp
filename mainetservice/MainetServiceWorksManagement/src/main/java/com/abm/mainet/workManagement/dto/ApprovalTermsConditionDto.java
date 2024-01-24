package com.abm.mainet.workManagement.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author Jeetendra.Pal
 *
 */
public class ApprovalTermsConditionDto implements Serializable {

	private static final long serialVersionUID = -8367085072615361925L;

	private Long teId;
	private Long workId;
	private String termDesc;
	private String termActive;
	private String refId;
	private String workSancNo;
	private Long orgId;
	private Long createdBy;
	private Date createdDate;
	private Long updatedBy;
	private Date updatedDate;
	private String lgIpMac;
	private String lgIpMacUpd;

	public Long getTeId() {
		return teId;
	}

	public void setTeId(Long teId) {
		this.teId = teId;
	}

	public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}

	public String getTermDesc() {
		return termDesc;
	}

	public void setTermDesc(String termDesc) {
		this.termDesc = termDesc;
	}

	public String getTermActive() {
		return termActive;
	}

	public void setTermActive(String termActive) {
		this.termActive = termActive;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public String getWorkSancNo() {
		return workSancNo;
	}

	public void setWorkSancNo(String workSancNo) {
		this.workSancNo = workSancNo;
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
