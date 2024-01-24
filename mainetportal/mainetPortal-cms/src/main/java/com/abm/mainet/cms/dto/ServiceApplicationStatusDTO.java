package com.abm.mainet.cms.dto;

import java.io.Serializable;

public class ServiceApplicationStatusDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -6212928063451089742L;

    private String applicantName;
    private String serviceName;

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(final String applicantName) {
        this.applicantName = applicantName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(final String serviceName) {
        this.serviceName = serviceName;
    }

}
