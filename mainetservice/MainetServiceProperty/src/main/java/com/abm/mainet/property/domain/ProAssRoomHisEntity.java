package com.abm.mainet.property.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_as_pro_room_his")
public class ProAssRoomHisEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "PRO_HIS_ROOMID")
	private long proRoomId;
	
	@Column(name = "pro_assd_id")
	private Long proAssdId;

	@Column(name = "PRO_ROOM_NO")
	private Long roomNo;

	@Column(name = "IS_ACTIVE")
	private String isActive;

	@Column(name = "CPD_RMTYPEID")
	private Long roomType;

	@Column(name = "PRO_RMLENGTH")
	private Double roomLength;

	@Column(name = "PRO_RMWIDTH")
	private Double roomWidth;

	@Column(name = "PRO_RMAREA")
	private Double roomArea;

	@Column(name = "ORGID")
	private Long orgId;

	@Column(name = "USER_ID")
	private Long createdBy;

	@Column(name = "LANG_ID")
	private Long langId;

	@Column(name = "LMODDATE")
	private Date createdDate;
	
	@Column(name = "H_STATUS")
    private String hStatus;

	public long getProRoomId() {
		return proRoomId;
	}

	public void setProRoomId(long proRoomId) {
		this.proRoomId = proRoomId;
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

	public Long getLangId() {
		return langId;
	}

	public void setLangId(Long langId) {
		this.langId = langId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
		
	public Long getProAssdId() {
		return proAssdId;
	}

	public void setProAssdId(Long proAssdId) {
		this.proAssdId = proAssdId;
	}

	public String gethStatus() {
		return hStatus;
	}

	public void sethStatus(String hStatus) {
		this.hStatus = hStatus;
	}

	public String[] getPkValues() {
        return new String[] { "AS", "tb_as_pro_room_his", "PRO_HIS_ROOMID" };
    }

}
