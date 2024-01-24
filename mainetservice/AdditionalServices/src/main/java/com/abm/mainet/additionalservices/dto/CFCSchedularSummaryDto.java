package com.abm.mainet.additionalservices.dto;

import java.io.Serializable;
import java.util.Date;

public class CFCSchedularSummaryDto implements Serializable {

	private static final long serialVersionUID = -2110998441074192112L;

	private String collectionnNo;
	private String counterNo;
	private Long userId;
	private String userName;
	private Date startDate;
	private Date endDate;
	private String status;
	private Long collectionId;
	private Long counterScheduleId;

	public Long getCounterScheduleId() {
		return counterScheduleId;
	}

	public void setCounterScheduleId(Long counterScheduleId) {
		this.counterScheduleId = counterScheduleId;
	}

	public String getCollectionnNo() {
		return collectionnNo;
	}

	public void setCollectionnNo(String collectionnNo) {
		this.collectionnNo = collectionnNo;
	}

	public String getCounterNo() {
		return counterNo;
	}

	public void setCounterNo(String counterNo) {
		this.counterNo = counterNo;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(Long collectionId) {
		this.collectionId = collectionId;
	}

}
