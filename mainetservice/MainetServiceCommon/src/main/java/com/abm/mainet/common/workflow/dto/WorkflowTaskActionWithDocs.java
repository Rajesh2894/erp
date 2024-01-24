package com.abm.mainet.common.workflow.dto;

import java.util.ArrayList;
import java.util.List;

import com.abm.mainet.common.utility.LookUp;

public class WorkflowTaskActionWithDocs extends WorkflowTaskAction {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<String> attachementUrls = new ArrayList<>();

    private List<LookUp> attachements = new ArrayList<>();
    
    private String taskDecision;
    
    private String errorMsg;
    
    public List<String> getAttachementUrls() {
        return attachementUrls;
    }

    public void setAttachementUrls(List<String> attachementUrls) {
        this.attachementUrls = attachementUrls;
    }

    public WorkflowTaskActionWithDocs addAttachementUrls(String url) {
        this.attachementUrls.add(url);
        return this;
    }

    public List<LookUp> getAttachements() {
        return attachements;
    }

    public void setAttachements(List<LookUp> attachements) {
        this.attachements = attachements;
    }

    public WorkflowTaskActionWithDocs addAttachement(LookUp attachement) {
        this.attachements.add(attachement);
        return this;
    }

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getTaskDecision() {
		return taskDecision;
	}

	public void setTaskDecision(String taskDecision) {
		this.taskDecision = taskDecision;
	}

}