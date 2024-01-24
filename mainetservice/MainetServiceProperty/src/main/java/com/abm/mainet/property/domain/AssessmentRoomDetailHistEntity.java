package com.abm.mainet.property.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Arun Shinde
 *
 */
@Entity
@Table(name = "tb_as_assesment_room_detail_hist")
public class AssessmentRoomDetailHistEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "MN_HIS_ROOMID")
	private long mnAsHisRoomId;

	@Column(name = "MN_assd_id")
	private long mnAssdId;

	@Column(name = "CPD_RMTYPEID")
	private Long roomType;

	@Column(name = "MN_ROOM_NO")
	private Long roomNo;

	@Column(name = "IS_ACTIVE")
	private String isActive;

	@Column(name = "PR_RMLENGTH")
	private Double roomLength;

	@Column(name = "PR_RMWIDTH")
	private Double roomWidth;

	@Column(name = "PR_RMAREA")
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

	public long getMnAsHisRoomId() {
		return mnAsHisRoomId;
	}

	public void setMnAsHisRoomId(long mnAsHisRoomId) {
		this.mnAsHisRoomId = mnAsHisRoomId;
	}

	public Long getRoomType() {
		return roomType;
	}

	public void setRoomType(Long roomType) {
		this.roomType = roomType;
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

	public long getMnAssdId() {
		return mnAssdId;
	}

	public void setMnAssdId(long mnAssdId) {
		this.mnAssdId = mnAssdId;
	}

	public String[] getPkValues() {
		return new String[] { "AS", "tb_as_assesment_room_detail", "MN_AS_HIS_ROOMID" };
	}

	public String gethStatus() {
		return hStatus;
	}

	public void sethStatus(String hStatus) {
		this.hStatus = hStatus;
	}
	
	

}
