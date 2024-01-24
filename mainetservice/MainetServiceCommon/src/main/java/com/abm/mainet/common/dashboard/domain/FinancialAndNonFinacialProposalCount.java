package com.abm.mainet.common.dashboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class FinancialAndNonFinacialProposalCount {
	
	@Id
	@Column(name = "num")
	private int id;
	
	@Column(name = "ProposalType")
	private String ProposalType;

	@Column(name = "ProposalCount")
	private int ProposalCount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProposalType() {
		return ProposalType;
	}

	public void setProposalType(String proposalType) {
		ProposalType = proposalType;
	}

	public int getProposalCount() {
		return ProposalCount;
	}

	public void setProposalCount(int proposalCount) {
		ProposalCount = proposalCount;
	}
	
	


}
