package com.abm.mainet.common.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author Jeetendra.Pal
 *
 */

public class HolidayMasterDto implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 7169304189734289335L;
	private Long hoId;
    private Date hoYearStartDate;
    private Date hoYearEndDate;
    private Date hoDate;
    private String hoDescription;
    private Long orgId;
    private Long createdBy;
    private Long updatedBy;
    private Date createdDate;
    private Date updatedDate;
    @JsonIgnore
    @Size(max=100)
    private String lgIpMac;
    @JsonIgnore
    @Size(max=100)
    private String lgIpMacUpd;
    private String hoActive;
    
	public Long getHoId() {
		return hoId;
	}
	public void setHoId(Long hoId) {
		this.hoId = hoId;
	}
	public Date getHoYearStartDate() {
		return hoYearStartDate;
	}
	public void setHoYearStartDate(Date hoYearStartDate) {
		this.hoYearStartDate = hoYearStartDate;
	}
	public Date getHoYearEndDate() {
		return hoYearEndDate;
	}
	public void setHoYearEndDate(Date hoYearEndDate) {
		this.hoYearEndDate = hoYearEndDate;
	}
	public Date getHoDate() {
		return hoDate;
	}
	public void setHoDate(Date hoDate) {
		this.hoDate = hoDate;
	}
	public String getHoDescription() {
		return hoDescription;
	}
	public void setHoDescription(String hoDescription) {
		this.hoDescription = hoDescription;
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
	public String getHoActive() {
		return hoActive;
	}
	public void setHoActive(String hoActive) {
		this.hoActive = hoActive;
	}
    
    


}
