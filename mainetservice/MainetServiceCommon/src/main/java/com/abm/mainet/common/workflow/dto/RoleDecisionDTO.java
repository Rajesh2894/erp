package com.abm.mainet.common.workflow.dto;

public class RoleDecisionDTO {

	private long decisionId;
	private String decisionDescFirst;
	private String decisionDescSecond;
	private String decisionValue;

	public long getDecisionId() {
		return decisionId;
	}

	public void setDecisionId(long decisionId) {
		this.decisionId = decisionId;
	}

	public String getDecisionDescFirst() {
		return decisionDescFirst;
	}

	public void setDecisionDescFirst(String decisionDescFirst) {
		this.decisionDescFirst = decisionDescFirst;
	}

	public String getDecisionDescSecond() {
		return decisionDescSecond;
	}

	public void setDecisionDescSecond(String decisionDescSecond) {
		this.decisionDescSecond = decisionDescSecond;
	}

	public String getDecisionValue() {
		return decisionValue;
	}

	public void setDecisionValue(String decisionValue) {
		this.decisionValue = decisionValue;
	}

}
