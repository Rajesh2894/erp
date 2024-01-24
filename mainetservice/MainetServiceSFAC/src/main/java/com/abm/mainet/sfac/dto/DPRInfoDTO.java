package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.sfac.domain.FPOProfileManagementMaster;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class DPRInfoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1445964125850574134L;

	private Long dprId;

	@JsonIgnore
	private FPOProfileMasterDto fpoProfileMasterDto;

	private Date dprRecDt;

	private String dprReviewer;

	private Long dprScore;

	private Date dprRevSubmDt;

	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;

	public Long getDprId() {
		return dprId;
	}

	public void setDprId(Long dprId) {
		this.dprId = dprId;
	}

	public FPOProfileMasterDto getFpoProfileMasterDto() {
		return fpoProfileMasterDto;
	}

	public void setFpoProfileMasterDto(FPOProfileMasterDto fpoProfileMasterDto) {
		this.fpoProfileMasterDto = fpoProfileMasterDto;
	}

	public Date getDprRecDt() {
		return dprRecDt;
	}

	public void setDprRecDt(Date dprRecDt) {
		this.dprRecDt = dprRecDt;
	}

	public String getDprReviewer() {
		return dprReviewer;
	}

	public void setDprReviewer(String dprReviewer) {
		this.dprReviewer = dprReviewer;
	}

	public Long getDprScore() {
		return dprScore;
	}

	public void setDprScore(Long dprScore) {
		this.dprScore = dprScore;
	}

	public Date getDprRevSubmDt() {
		return dprRevSubmDt;
	}

	public void setDprRevSubmDt(Date dprRevSubmDt) {
		this.dprRevSubmDt = dprRevSubmDt;
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
	
	

}
