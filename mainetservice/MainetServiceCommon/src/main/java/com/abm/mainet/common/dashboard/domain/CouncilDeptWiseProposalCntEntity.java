package com.abm.mainet.common.dashboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CouncilDeptWiseProposalCntEntity {
	
	@Id
	@Column(name = "num")
	private int id;
	
	@Column(name = "DEPT_NAME")
	private String deptName;
	
	
	@Column(name = "PROPOSAL_COUNT")
	private int proposalCount;


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getDeptName() {
		return deptName;
	}


	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}


	public int getProposalCount() {
		return proposalCount;
	}


	public void setProposalCount(int proposalCount) {
		this.proposalCount = proposalCount;
	}

	@Override
	public String toString() {
		return "CouncilDeptWiseProposalCntEntity [id=" + id + ", deptName=" + deptName + ", proposalCount=" + proposalCount +"]";
	}
}
