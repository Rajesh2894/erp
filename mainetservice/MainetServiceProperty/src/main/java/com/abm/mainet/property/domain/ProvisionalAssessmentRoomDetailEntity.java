package com.abm.mainet.property.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_as_pro_room_det")
public class ProvisionalAssessmentRoomDetailEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "PRO_ROOMID")
	private long proRoomId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRO_ASSD_ID")
	private ProvisionalAssesmentDetailEntity proAssDetEntity;

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

	public long getProRoomId() {
		return proRoomId;
	}

	public void setProRoomId(long proRoomId) {
		this.proRoomId = proRoomId;
	}

	public ProvisionalAssesmentDetailEntity getProAssDetEntity() {
		return proAssDetEntity;
	}

	public void setProAssDetEntity(ProvisionalAssesmentDetailEntity proAssDetEntity) {
		this.proAssDetEntity = proAssDetEntity;
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

	public String[] getPkValues() {
        return new String[] { "AS", "tb_as_pro_room_det", "PRO_ROOMID" };
    }
}
