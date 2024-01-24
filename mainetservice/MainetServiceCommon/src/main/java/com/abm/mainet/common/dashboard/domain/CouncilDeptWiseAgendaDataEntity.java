package com.abm.mainet.common.dashboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CouncilDeptWiseAgendaDataEntity {

	@Id
	@Column(name = "num")
	private int id;

	@Column(name = "PROPOSAL_NUMBER")
	private String proposalNo;

	@Column(name = "DP_DEPTDESC")
	private String deptName;

	@Column(name = "AGENDA_DATE")
	private String agendaDate;

	@Column(name = "COMMITTEE_TYPE")
	private String committeeType;

	@Column(name = "PROPOSAL_DATE")
	private String proposalDate;

	@Column(name = "PROPOSAL_DETAILS")
	private String proposalDtls;

	@Column(name = "PROPOSAL_AMOUNT")
	private String proposalAmt;

	@Column(name = "AGENDA_STATUS")
	private String agendaStatus;

	@Column(name = "purpose_remark")
	private String proposalRemark;

	@Column(name = "CREATED_BY")
	private String empName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProposalNo() {
		return proposalNo;
	}

	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getAgendaDate() {
		return agendaDate;
	}

	public void setAgendaDate(String agendaDate) {
		this.agendaDate = agendaDate;
	}

	public String getCommitteeType() {
		return committeeType;
	}

	public void setCommitteeType(String committeeType) {
		this.committeeType = committeeType;
	}

	public String getProposalDate() {
		return proposalDate;
	}

	public void setProposalDate(String proposalDate) {
		this.proposalDate = proposalDate;
	}

	public String getProposalDtls() {
		return proposalDtls;
	}

	public void setProposalDtls(String proposalDtls) {
		this.proposalDtls = proposalDtls;
	}

	public String getProposalAmt() {
		return proposalAmt;
	}

	public void setProposalAmt(String proposalAmt) {
		this.proposalAmt = proposalAmt;
	}

	public String getAgendaStatus() {
		return agendaStatus;
	}

	public void setAgendaStatus(String agendaStatus) {
		this.agendaStatus = agendaStatus;
	}

	public String getProposalRemark() {
		return proposalRemark;
	}

	public void setProposalRemark(String proposalRemark) {
		this.proposalRemark = proposalRemark;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	@Override
	public String toString() {
		return "CouncilDeptWiseAgendaDataEntity [id=" + id + ", proposalNo=" + proposalNo + ", deptName=" + deptName
				+ ", agendaDate=" + agendaDate + ", committeeType=" + committeeType + ", proposalDate=" + proposalDate
				+ ", proposalDtls=" + proposalDtls + ", proposalAmt=" + proposalAmt + ", agendaStatus=" + agendaStatus
				+ ", proposalRemark=" + proposalRemark + ", empName=" + empName + "]";
	}

}
