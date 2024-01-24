package com.abm.mainet.adh.dto;

import java.io.Serializable;

public class ADHRequestDto implements Serializable {

    private static final long serialVersionUID = -2211287407701737142L;

    private Long applicationId;
    private Long orgId;
    private String serviceCode;

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    @Override
    public String toString() {
        return "ADHRequestDto [applicationId=" + applicationId + ", orgId=" + orgId + ", serviceCode=" + serviceCode + "]";
    }

}
