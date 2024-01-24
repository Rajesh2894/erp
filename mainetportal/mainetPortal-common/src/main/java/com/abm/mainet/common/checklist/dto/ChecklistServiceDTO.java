package com.abm.mainet.common.checklist.dto;

import java.io.Serializable;
import java.util.Date;

public class ChecklistServiceDTO implements Serializable {

    private static final long serialVersionUID = 3856047259034359959L;

    private Long apmApplicationId;

    private Long organisationId;

    private Date apmApplicationDate;

    private String englisTitle;

    private String regionalTitle;

    private String applicantsName;

    private Long smServiceId;

    private String serviceName;

    private String serviceNameMar;

    private String apmChklstVrfyFlag;

    private Long cdmDeptId;

    public Long getApmApplicationId() {
        return apmApplicationId;
    }

    public void setApmApplicationId(final Long apmApplicationId) {
        this.apmApplicationId = apmApplicationId;
    }

    public Long getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(final Long organisationId) {
        this.organisationId = organisationId;
    }

    public Date getApmApplicationDate() {
        return apmApplicationDate;
    }

    public void setApmApplicationDate(final Date apmApplicationDate) {
        this.apmApplicationDate = apmApplicationDate;
    }

    public String getEnglisTitle() {
        return englisTitle;
    }

    public void setEnglisTitle(final String englisTitle) {
        this.englisTitle = englisTitle;
    }

    public String getRegionalTitle() {
        return regionalTitle;
    }

    public void setRegionalTitle(final String regionalTitle) {
        this.regionalTitle = regionalTitle;
    }

    public String getApplicantsName() {
        return applicantsName;
    }

    public void setApplicantsName(final String applicantsName) {
        this.applicantsName = applicantsName;
    }

    public Long getSmServiceId() {
        return smServiceId;
    }

    public void setSmServiceId(final Long smServiceId) {
        this.smServiceId = smServiceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(final String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceNameMar() {
        return serviceNameMar;
    }

    public void setServiceNameMar(final String serviceNameMar) {
        this.serviceNameMar = serviceNameMar;
    }

    public String getApmChklstVrfyFlag() {
        return apmChklstVrfyFlag;
    }

    public void setApmChklstVrfyFlag(final String apmChklstVrfyFlag) {
        this.apmChklstVrfyFlag = apmChklstVrfyFlag;
    }

    public Long getCdmDeptId() {
        return cdmDeptId;
    }

    public void setCdmDeptId(final Long cdmDeptId) {
        this.cdmDeptId = cdmDeptId;
    }

}
