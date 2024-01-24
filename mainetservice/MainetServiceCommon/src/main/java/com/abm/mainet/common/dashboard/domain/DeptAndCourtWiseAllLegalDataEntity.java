package com.abm.mainet.common.dashboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DeptAndCourtWiseAllLegalDataEntity {

	@Id
	@Column(name = "num")
	private int id;

	@Column(name = "cse_suit_no")
	private String caseNo;

	@Column(name = "cse_name")
	private String caseName;

	@Column(name = "cse_category")
	private String caseCategory;

	@Column(name = "cse_date")
	private String caseRegDate;

	@Column(name = "crt_name")
	private String courtName;

	@Column(name = "cse_matdet_1")
	private String caseMatDet;

	@Column(name = "cse_status")
	private String caseStatus;

	@Column(name = "cse_dept")
	private String caseDept;

	@Column(name = "case_filed_by")
	private String caseFiledByName;

	@Column(name = "case_filed_on")
	private String caseFiledOnName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCaseNo() {
		return caseNo;
	}

	public void setCaseNo(String caseNo) {
		this.caseNo = caseNo;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getCaseCategory() {
		return caseCategory;
	}

	public void setCaseCategory(String caseCategory) {
		this.caseCategory = caseCategory;
	}

	public String getCaseRegDate() {
		return caseRegDate;
	}

	public void setCaseRegDate(String caseRegDate) {
		this.caseRegDate = caseRegDate;
	}

	public String getCourtName() {
		return courtName;
	}

	public void setCourtName(String courtName) {
		this.courtName = courtName;
	}

	public String getCaseMatDet() {
		return caseMatDet;
	}

	public void setCaseMatDet(String caseMatDet) {
		this.caseMatDet = caseMatDet;
	}

	public String getCaseStatus() {
		return caseStatus;
	}

	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
	}

	public String getCaseDept() {
		return caseDept;
	}

	public void setCaseDept(String caseDept) {
		this.caseDept = caseDept;
	}

	public String getCaseFiledByName() {
		return caseFiledByName;
	}

	public void setCaseFiledByName(String caseFiledByName) {
		this.caseFiledByName = caseFiledByName;
	}

	public String getCaseFiledOnName() {
		return caseFiledOnName;
	}

	public void setCaseFiledOnName(String caseFiledOnName) {
		this.caseFiledOnName = caseFiledOnName;
	}

	@Override
	public String toString() {
		return "DeptAndCourtWiseAllLegalDataEntity [id=" + id + ", caseNo=" + caseNo + ", caseName=" + caseName
				+ ", caseCategory=" + caseCategory + ", caseRegDate=" + caseRegDate + ", courtName=" + courtName
				+ ", caseMatDet=" + caseMatDet + ", caseStatus=" + caseStatus + ", caseDept=" + caseDept
				+ ", caseFiledByName=" + caseFiledByName + ", caseFiledOnName=" + caseFiledOnName + "]";
	}

}
