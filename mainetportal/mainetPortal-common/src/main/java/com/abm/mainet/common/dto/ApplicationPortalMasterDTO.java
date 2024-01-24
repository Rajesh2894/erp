package com.abm.mainet.common.dto;

import java.io.Serializable;
import java.util.Date;

import com.abm.mainet.common.constant.MainetConstants;

/**
 * @author ritesh.patil
 *
 */
public class ApplicationPortalMasterDTO implements Serializable {

    private static final long serialVersionUID = 6811884985773726622L;
    private Long id;
    private Long citizenId;
    private Long orgId;
    private Long applicationId;
    private Long serviceId;
    private Date applicationDate;
    private String shortName;
    private String flag;
    private Date slaDate;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(final String serviceName) {
        this.serviceName = serviceName;
    }

    private String paymentStatus;
    private String appStatus;
    private String serviceName;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getCitizenId() {
        return citizenId;
    }

    public void setCitizenId(final Long citizenId) {
        this.citizenId = citizenId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(final String flag) {
        this.flag = flag;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(final Long applicationId) {
        this.applicationId = applicationId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(final Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(final String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(final String appStatus) {
        this.appStatus = appStatus;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(final String shortName) {
        this.shortName = shortName;
    }

    public Date getSlaDate() {
        return slaDate;
    }

    public void setSlaDate(final Date slaDate) {
        this.slaDate = slaDate;
    }

    public final String getDownloadDocs() {
        String datastring = MainetConstants.BLANK;
        datastring += "<a href='javascript:void(0);' onclick=\"downloadDocs('" + getApplicationId() + "','" + getOrgId() + "','"
                + getServiceId() + "','" + getFlag() + "')\">";
        datastring += "<i class='fa fa-external-link fa-2x blue'></i>";
        datastring += "</a>";
        return datastring;
    }

}
