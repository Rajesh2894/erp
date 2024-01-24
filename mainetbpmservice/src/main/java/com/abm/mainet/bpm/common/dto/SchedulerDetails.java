package com.abm.mainet.bpm.common.dto;

import java.util.ArrayList;
import java.util.List;

public class SchedulerDetails {
    private List<Long> applicationId = new ArrayList<>();
    private List<Long> workFlowId = new ArrayList<>();

    public List<Long> getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(List<Long> applicationId) {
        this.applicationId = applicationId;
    }

    public List<Long> getWorkFlowId() {
        return workFlowId;
    }

    public void setWorkFlowId(List<Long> workFlowId) {
        this.workFlowId = workFlowId;
    }

}
