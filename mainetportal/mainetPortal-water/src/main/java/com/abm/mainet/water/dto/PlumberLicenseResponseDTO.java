package com.abm.mainet.water.dto;

import java.io.Serializable;

public class PlumberLicenseResponseDTO implements Serializable {

    private static final long serialVersionUID = -6908903517721303997L;

    private Long applicationId;

    private int uploadedDocSize;

    private String status;

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(final Long applicationId) {
        this.applicationId = applicationId;
    }

    public int getUploadedDocSize() {
        return uploadedDocSize;
    }

    public void setUploadedDocSize(final int uploadedDocSize) {
        this.uploadedDocSize = uploadedDocSize;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

}
