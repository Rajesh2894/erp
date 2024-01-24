package com.abm.mainet.legal.dto;

import java.io.Serializable;

public class EventDatecalendarDTO implements Serializable {

	private static final long serialVersionUID = 5970749988195877229L;

	private String date;

	private String courtName;
	private String advocateName;
    private String caseNo;
	
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private String description;


	public String getCourtName() {
		return courtName;
	}

	public void setCourtName(String courtName) {
		this.courtName = courtName;
	}

	public String getAdvocateName() {
		return advocateName;
	}

	public void setAdvocateName(String advocateName) {
		this.advocateName = advocateName;
	}

	public String getCaseNo() {
		return caseNo;
	}

	public void setCaseNo(String caseNo) {
		this.caseNo = caseNo;
	}
	
	

}
