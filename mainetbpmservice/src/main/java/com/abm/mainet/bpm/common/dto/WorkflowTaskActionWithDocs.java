package com.abm.mainet.bpm.common.dto;

import java.util.ArrayList;
import java.util.List;

public class WorkflowTaskActionWithDocs extends WorkflowTaskAction {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String empMobile;
    private Long currentEscalationLevel;
    private List<String> attachementUrls = new ArrayList<>();
    private List<LookUp> attachements = new ArrayList<>();

    public String getEmpMobile() {
        return empMobile;
    }

    public void setEmpMobile(String empMobile) {
        this.empMobile = empMobile;
    }

    public Long getCurrentEscalationLevel() {
        return currentEscalationLevel;
    }

    public void setCurrentEscalationLevel(Long currentEscalationLevel) {
        this.currentEscalationLevel = currentEscalationLevel;
    }

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

}