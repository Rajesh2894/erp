package com.abm.mainet.cfc.checklist.dto;

import com.abm.mainet.common.integration.dto.RequestDTO;

public class DocumentResubmissionRequestDTO extends RequestDTO {

    private static final long serialVersionUID = 9101416101762834001L;
    private String applicationStatus;

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(final String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

}
