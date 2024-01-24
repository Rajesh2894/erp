package com.abm.mainet.legal.dto;

import java.io.Serializable;
import java.util.Date;

public class CounterAffidavitDTO implements Serializable

{

	private static final long serialVersionUID = 6984287810580677651L;

	private Long cafId;
	private Long caseId;
	private Date afDate;
	private Date cafDate;

	private String cafType;
	private String cafDefender;

	private String cafPlaintiff;

	private long orgId;

	private Date createdDate;

	private Long updatedBy;

	private Date updatedDate;

	private String lgIpMac;

	private String lgIpMacUpd;

	private Long createdBy;

	public long getCaseId() {
		return caseId;
	}

	public void setCaseId(long caseId) {
		this.caseId = caseId;
	}

	public Date getAfDate() {
		return afDate;
	}

	public void setAfDate(Date afDate) {
		this.afDate = afDate;
	}

	public Date getCafDate() {
		return cafDate;
	}

	public void setCafDate(Date cafDate) {
		this.cafDate = cafDate;
	}

	public String getCafType() {
		return cafType;
	}

	public void setCafType(String cafType) {
		this.cafType = cafType;
	}

	public String getCafDefender() {
		return cafDefender;
	}

	public void setCafDefender(String cafDefender) {
		this.cafDefender = cafDefender;
	}

	public String getCafPlaintiff() {
		return cafPlaintiff;
	}

	public void setCafPlaintiff(String cafPlaintiff) {
		this.cafPlaintiff = cafPlaintiff;
	}

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
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

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getCafId() {
		return cafId;
	}

	public void setCafId(Long cafId) {
		this.cafId = cafId;
	}

}
