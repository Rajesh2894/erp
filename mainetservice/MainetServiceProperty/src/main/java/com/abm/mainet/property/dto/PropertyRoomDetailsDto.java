package com.abm.mainet.property.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Arun Shinde
 *
 */
public class PropertyRoomDetailsDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long proRoomId;

	private Long roomType;
	
	private Long roomNo;
	
	private String isActive;

	private Double roomLength;

	private Double roomWidth;

	private Double roomArea;

	private Long orgId;

	private Long createdBy;

	private Date createdDate;

	private Long updatedBy;

	private Date updatedDate;

	private String lgIpMac;

	private String lgIpMacUpd;
	
	private String roomTypeDesc;
	
	public Long getRoomType() {
		return roomType;
	}

	public void setRoomType(Long roomType) {
		this.roomType = roomType;
	}

	public Double getRoomLength() {
		return roomLength;
	}

	public void setRoomLength(Double roomLength) {
		this.roomLength = roomLength;
	}

	public Double getRoomWidth() {
		return roomWidth;
	}

	public void setRoomWidth(Double roomWidth) {
		this.roomWidth = roomWidth;
	}

	public Double getRoomArea() {
		return roomArea;
	}

	public void setRoomArea(Double roomArea) {
		this.roomArea = roomArea;
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

	public Long getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(Long roomNo) {
		this.roomNo = roomNo;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	

	public long getProRoomId() {
		return proRoomId;
	}

	public void setProRoomId(long proRoomId) {
		this.proRoomId = proRoomId;
	}

	public String getRoomTypeDesc() {
		return roomTypeDesc;
	}

	public void setRoomTypeDesc(String roomTypeDesc) {
		this.roomTypeDesc = roomTypeDesc;
	}

}
