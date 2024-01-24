package com.abm.mainet.cfc.scrutiny.dto;

import java.util.Date;

import com.abm.mainet.common.utility.StringUtility;

public class ViewCFCScrutinyLabelValue {

    private Long slLabelId;

    private Long scrutinyId;

    private Long applicationId;

    private Long saApplicationId;

    private String slLabel;

    private String slLabelMar;

    private String slFormMode;

    private String slFormName;

    private String slDatatype;

    private String slPreValidation;

    private String slTableColumn;

    private String slWhereClause;

    private Long slDsgid;

    private String svValue;

    private Long orgId;

    private String slDisplayFlag;

    private String slAuthorising;

    private String slValidationText;
    private String smShortDesc;

    private long levels;
    
    private Long formId;
    
    private String resolutionComments;
    
    private String slQuery;
    
    private String inputData;
    
    private String empName;
    
    private String slDsgName;
    
    private Date stringDate;
    
    private Long taskId;

    /**
     * @return the slValidationText
     */
    public String getSlValidationText() {
        return slValidationText;
    }

    /**
     * @param slValidationText the slValidationText to set
     */
    public void setSlValidationText(final String slValidationText) {
        this.slValidationText = slValidationText;
    }

    public String getSlAuthorising() {
        return slAuthorising;
    }

    public void setSlAuthorising(final String slAuthorising) {
        this.slAuthorising = slAuthorising;
    }

    public String getSlDisplayFlag() {
        return slDisplayFlag;
    }

    public void setSlDisplayFlag(final String slDisplayFlag) {
        this.slDisplayFlag = slDisplayFlag;
    }

    public Long getScrutinyId() {
        return scrutinyId;
    }

    public void setScrutinyId(final Long scrutinyId) {
        this.scrutinyId = scrutinyId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(final Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getSlLabel() {
        return slLabel;
    }

    public void setSlLabel(final String slLabel) {
        this.slLabel = slLabel;
    }

    public Long getSlDsgid() {
        return slDsgid;
    }

    public void setSlDsgid(final Long slDsgid) {
        this.slDsgid = slDsgid;
    }

    public String getSvValue() {
        return svValue;
    }

    public void setSvValue(final String svValue) {
        this.svValue = svValue;
    }

    /**
     * @return the orgId
     */
    public Long getOrgId() {
        return orgId;
    }

    public String getSlFormMode() {
        return slFormMode;
    }

    public void setSlFormMode(final String slFormMode) {
        this.slFormMode = slFormMode;
    }

    public String getSlFormName() {
        return slFormName;
    }

    public void setSlFormName(final String slFormName) {
        this.slFormName = slFormName;
    }

    public String getSlDatatype() {
        return slDatatype;
    }

    public void setSlDatatype(final String slDatatype) {
        this.slDatatype = slDatatype;
    }

    public String getSlPreValidation() {
        return slPreValidation;
    }

    public void setSlPreValidation(final String slPreValidation) {
        this.slPreValidation = slPreValidation;
    }

    public Long getSaApplicationId() {
        return saApplicationId;
    }

    public void setSaApplicationId(final Long saApplicationId) {
        this.saApplicationId = saApplicationId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public Long getSlLabelId() {
        return slLabelId;
    }

    public void setSlLabelId(final Long slLabelId) {
        this.slLabelId = slLabelId;
    }

    public String getSlTableColumn() {
        return slTableColumn;
    }

    public void setSlTableColumn(final String slTableColumn) {
        this.slTableColumn = slTableColumn;
    }

    public String getSlWhereClause() {
        return slWhereClause;
    }

    public void setSlWhereClause(final String slWhereClause) {
        this.slWhereClause = slWhereClause;
    }

    
    public String getSmShortDesc() {
		return smShortDesc;
	}

	public void setSmShortDesc(String smShortDesc) {
		this.smShortDesc = smShortDesc;
	}

	
	public Long getFormId() {
		return formId;
	}

	public void setFormId(Long formId) {
		this.formId = formId;
	}

	/**
     * @return the levels
     */
    public long getLevels() {
        return levels;
    }

    /**
     * @param levels the levels to set
     */
    public void setLevels(final long levels) {
        this.levels = levels;
    }

    public String getTableClause() {
        if ((slTableColumn != null) && (slWhereClause != null)) {
            return "SELECT "
                    + StringUtility.staticStringAfterChar(".",
                            slTableColumn)
                    + " "
                    + "FROM "
                    + StringUtility.staticStringBeforeChar(".",
                            slTableColumn)
                    + " " + " WHERE "
                    + slWhereClause;
        }

        return null;
    }

    public String getSlLabelMar() {
        return slLabelMar;
    }

    public void setSlLabelMar(final String slLabelMar) {
        this.slLabelMar = slLabelMar;
    }

	public String getResolutionComments() {
		return resolutionComments;
	}

	public void setResolutionComments(String resolutionComments) {
		this.resolutionComments = resolutionComments;
	}

	public String getInputData() {
		return inputData;
	}

	public void setInputData(String inputData) {
		this.inputData = inputData;
	}

	public String getSlQuery() {
		return slQuery;
	}

	public void setSlQuery(String slQuery) {
		this.slQuery = slQuery;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getSlDsgName() {
		return slDsgName;
	}

	public void setSlDsgName(String slDsgName) {
		this.slDsgName = slDsgName;
	}

	public Date getStringDate() {
		return stringDate;
	}

	public void setStringDate(Date stringDate) {
		this.stringDate = stringDate;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	
}