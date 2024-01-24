package com.abm.mainet.rts.dto;

import java.io.Serializable;
import java.util.Date;



public class DrainageConnectionRoadDetDTO implements Serializable {
	

	private static final long serialVersionUID = 1L;

	private Long cnnRdId;
	
	private Long connectionId;
	
	private Long orgId;

	private Long createdBy;

	private Date createdDate;
	
	private String lgIpMac;

	private String lgIpMacUpd;
	
	private Long updatedBy;

	private Date updatedDate;
	
	private Long roadType;
	 
	 private Long lenRoad;

	public Long getCnnRdId() {
		return cnnRdId;
	}

	public void setCnnRdId(Long cnnRdId) {
		this.cnnRdId = cnnRdId;
	}

	

	public Long getConnectionId() {
		return connectionId;
	}

	public void setConnectionId(Long connectionId) {
		this.connectionId = connectionId;
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

	public Long getRoadType() {
		return roadType;
	}

	public void setRoadType(Long roadType) {
		this.roadType = roadType;
	}

	public Long getLenRoad() {
		return lenRoad;
	}

	public void setLenRoad(Long lenRoad) {
		this.lenRoad = lenRoad;
	}
	 
	 

}
