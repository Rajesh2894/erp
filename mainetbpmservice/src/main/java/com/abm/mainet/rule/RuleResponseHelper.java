package com.abm.mainet.rule;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author Vivek.Kumar
 * @since  02 June 2016
 */
public class RuleResponseHelper implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8063401404224869030L;

	public static List<Rule> ruleResponseList = new ArrayList<Rule>();
	
	
	public static void addResponse(Object ruleResult, String ruleId,
			String description) {

		Rule rule = new Rule();
		// set fired rule values
		rule.setRuleResult(ruleResult);
		rule.setRuleId(ruleId);
		rule.setDescription(description);
		
		// add to rule list
		ruleResponseList.add(rule);
		
	}

	public static List<Rule> getRuleResponseList() {
		return ruleResponseList;
	}
	
	
}
