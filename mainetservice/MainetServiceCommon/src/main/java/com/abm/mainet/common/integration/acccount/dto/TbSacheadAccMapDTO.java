package com.abm.mainet.common.integration.acccount.dto;

import java.io.Serializable;
import java.util.Date;

public class TbSacheadAccMapDTO implements Serializable {
    /**
    *
    */
   private static final long serialVersionUID = -1677851844038384367L;
	
	private Long mapId;
    private Long sacHeadId;
    private String baAccountName;
    private String sacHeadName;
    private Long baAccountId;
    private Long orgId;
    private Long createdBy;
    private Date createdDate;
    private Long updatedBy;
    private Date updatedDate;
    private String lgIpMac;
    private String lgIpMacUpd;
    
    
	public Long getMapId() {
		return mapId;
	}
	public void setMapId(Long mapId) {
		this.mapId = mapId;
	}
	public Long getSacHeadId() {
		return sacHeadId;
	}
	public void setSacHeadId(Long sacHeadId) {
		this.sacHeadId = sacHeadId;
	}
	
	public String getBaAccountName() {
		return baAccountName;
	}
	public void setBaAccountName(String baAccountName) {
		this.baAccountName = baAccountName;
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
	public Long getBaAccountId() {
		return baAccountId;
	}
	public void setBaAccountId(Long baAccountId) {
		this.baAccountId = baAccountId;
	}
	public String getSacHeadName() {
		return sacHeadName;
	}
	public void setSacHeadName(String sacHeadName) {
		this.sacHeadName = sacHeadName;
	}
    

}
