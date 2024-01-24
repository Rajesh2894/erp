package com.abm.mainet.rule.datamodel;

import java.util.HashMap;
import java.util.Map;

import com.abm.mainet.rule.propertytax.datamodel.PropertyRateMasterModel;

/**
 * 
 * @author Vivek.Kumar
 *
 */
public class CheckListModel extends CommonModel{
	
	
	   /**
	 * 
	 */
	private static final long serialVersionUID = -8270319551138230983L;
	   private String applicantType;
	   private String isOutStandingPending;
	   private String isExistingConnectionOrConsumerNo;
	   private String isExistingProperty;
	   private String disConnectionType;
	   
	   private Map<String, String> factor = new HashMap<>(0);
	   /*
	    *  this field is the Rule result field, 
	    *  Client need not to be set this field while setting Checklist parameters
	    *  related to service. Client need to invoke getter method {getDocumentGroup()}
	    *  in order to get Document Group. 
	    */
	   
	   private String documentGroup;
	   private double documentGroupFactor;
	   
	   public CheckListModel() {
		   super();
		   this.applicantType = "NA";
		   this.isOutStandingPending = "NA";
		   this.isExistingConnectionOrConsumerNo = "NA";
		   this.isExistingProperty = "NA";
		   this.disConnectionType = "NA";
	   }
	   
	   public CheckListModel ruleResult() {
	        return this;
	    }
	
	  public Map<String, String> getFactor() { 
		  return factor; 
	  }
	  
	  public void setFactor(Map<String, String> factor) {
		  this.factor = factor;
		 }
	    
	  
	public java.lang.String getApplicantType() {
		return applicantType;
	}
	public void setApplicantType(java.lang.String applicantType) {
		this.applicantType = applicantType;
	}
	public java.lang.String getIsOutStandingPending() {
		return isOutStandingPending;
	}
	public void setIsOutStandingPending(java.lang.String isOutStandingPending) {
		this.isOutStandingPending = isOutStandingPending;
	}
	public java.lang.String getIsExistingConnectionOrConsumerNo() {
		return isExistingConnectionOrConsumerNo;
	}
	public void setIsExistingConnectionOrConsumerNo(java.lang.String isExistingConnectionOrConsumerNo) {
		this.isExistingConnectionOrConsumerNo = isExistingConnectionOrConsumerNo;
	}
	public java.lang.String getIsExistingProperty() {
		return isExistingProperty;
	}
	public void setIsExistingProperty(java.lang.String isExistingProperty) {
		this.isExistingProperty = isExistingProperty;
	}
	
	public String getDisConnectionType () {
		return disConnectionType;
	}
	
	public void setDisConnectionType (String disConnectionType) {
		this.disConnectionType = disConnectionType;
	}
	public String getFactorValue(String key) {
        if (factor != null && factor.get(key) != null) {
            return factor.get(key);
        }
        return "";
    }
	public double getDocumentGroupFactor() {
		return documentGroupFactor;
	}

	public void setDocumentGroupFactor(double documentGroupFactor) {
		this.documentGroupFactor = documentGroupFactor;
	}
	public String getDocumentGroup() {
		return documentGroup;
	}

	public void setDocumentGroup(String documentGroup) {
		this.documentGroup = documentGroup;
	}

	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CheckListModel [");
		builder.append(super.toString());
		builder.append(", applicantType=");
		builder.append(applicantType);
		builder.append(", isOutStandingPending=");
		builder.append(isOutStandingPending);
		builder.append(", isExistingConnectionOrConsumerNo=");
		builder.append(isExistingConnectionOrConsumerNo);
		builder.append(", isExistingProperty=");
		builder.append(isExistingProperty);
		builder.append(", documentGroup=");
		builder.append(documentGroup);
		builder.append(", disConnectionType=");
		builder.append(disConnectionType);
		builder.append("]");
		return builder.toString();
	}
}