package com.abm.mainet.rule;

import java.io.Serializable;

/**
 * 
 * @author Vivek.Kumar
 * @since  02 June 2016
 */
public class Rule implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3211902025903272613L;

	private String ruleId;
	private Object ruleResult;
	private String description;
	
	
	public String getRuleId() {
		return ruleId;
	}
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	public Object getRuleResult() {
		return ruleResult;
	}
	public void setRuleResult(Object ruleResult) {
		this.ruleResult = ruleResult;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
