package com.abm.mainet.common.dashboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CouncilDeptWiseProposalDataEntity {

	@Id
	@Column(name = "num")
	private int id;

	@Column(name = "PROPOSAL_NUMBER")
	private String proposalNo;

	@Column(name = "DP_DEPTDESC")
	private String deptName;

	@Column(name = "PROPOSAL_DATE")
	private String proposalDate;

	@Column(name = "PROPOSAL_DETAILS")
	private String proposalDtls;

	@Column(name = "PROPOSAL_AMOUNT")
	private String proposalAmt;

	@Column(name = "PROPOSAL_STATUS")
	private String proposalStatus;

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

	public String getProposalStatus() {
		return proposalStatus;
	}

	public void setProposalStatus(String proposalStatus) {
		this.proposalStatus = proposalStatus;
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
		return "CouncilDeptWiseProposalDataEntity [id=" + id + ", proposalNo=" + proposalNo + ", deptName=" + deptName
				+ ", proposalDate=" + proposalDate + ", proposalDtls=" + proposalDtls + ", proposalAmt=" + proposalAmt
				+ ", proposalStatus=" + proposalStatus + ", proposalRemark=" + proposalRemark + ", empName=" + empName
				+ "]";
	}

}
