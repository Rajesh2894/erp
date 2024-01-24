package com.abm.mainet.common.dashboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class RtiApplicationDtoByCondEntity {
	
	@Id
	@Column(name = "num")
	private int id;

	@Column(name = "APM_APPLICATION_ID")
	private String applId;
	
	@Column(name = "RTI_NO")
	private String rtiNo;
	
	@Column(name = "CREATED_BY")
	private String createdBy;	
	
	@Column(name = "DATE_OF_REQUEST")
	private String dateOfRequest;
	
	@Column(name = "INWORD_TYPE")
	private String inwordType;

	@Column(name = "RTI_DESC")
	private String rtiDesc;

	@Column(name = "BPL_Flag")
	private String bpl;
	
	@Column(name = "Status")
	private String status;

	public int getId() {
		return id;
	}

	public String getApplId() {
		return applId;
	}

	public String getRtiNo() {
		return rtiNo;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public String getDateOfRequest() {
		return dateOfRequest;
	}

	public String getInwordType() {
		return inwordType;
	}

	public String getRtiDesc() {
		return rtiDesc;
	}


	public String getBpl() {
		return bpl;
	}


	public void setId(int id) {
		this.id = id;
	}

	public void setApplId(String applId) {
		this.applId = applId;
	}

	public void setRtiNo(String rtiNo) {
		this.rtiNo = rtiNo;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setDateOfRequest(String dateOfRequest) {
		this.dateOfRequest = dateOfRequest;
	}

	public void setInwordType(String inwordType) {
		this.inwordType = inwordType;
	}

	public void setRtiDesc(String rtiDesc) {
		this.rtiDesc = rtiDesc;
	}

	public String getStatus() {
		return status;
	}

	public void setBpl(String bpl) {
		this.bpl = bpl;
	}

	public void setStatus(String status) {
		this.status = status;
	}



}
