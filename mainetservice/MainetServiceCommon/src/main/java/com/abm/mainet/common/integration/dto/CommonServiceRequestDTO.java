package com.abm.mainet.common.integration.dto;

import java.io.Serializable;

public class CommonServiceRequestDTO implements Serializable {

    private static final long serialVersionUID = -7311384506139584909L;

    private Long applicationId;
    private Long serviceId;
    private long orgId;

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(final long orgId) {
        this.orgId = orgId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(final Long applicationId) {
        this.applicationId = applicationId;
    }

}
