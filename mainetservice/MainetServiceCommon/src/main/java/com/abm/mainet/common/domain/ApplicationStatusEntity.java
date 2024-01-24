package com.abm.mainet.common.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author vishnu.jagdale
 *
 */
@Entity
@Table(name = "VW_APPLICATION_STATUS")
public class ApplicationStatusEntity {
    @Id
    @Column(name = "APM_APPLICATION_ID", precision = 16, scale = 0, nullable = false)
    private Long apmApplicationId;

    @Column(name = "APM_APPLICATION_NO", precision = 16, scale = 0, nullable = false)
    private Long apmApplicationNo;

    @Column(name = "APM_APPLICATION_DATE", nullable = false)
    private Date apmApplicationDate;

    @Column(name = "APM_APPLICATION_NAME_ENG", length = 302, nullable = true)
    private String apmApplicationNameEng;

    @Column(name = "APM_APPLICATION_NAME_REG", length = 302, nullable = true)
    private String apmApplicationNameReg;

    @Column(name = "APPEAL_SERVICE_ID", precision = 12, scale = 0, nullable = false)
    private Long appealServiceId;

    @Column(name = "SERVICE_ID", precision = 12, scale = 0, nullable = false)
    private Long serviceId;

    @Column(name = "APM_SERVICE_NAME", length = 100, nullable = true)
    private String apmServiceName;

    @Column(name = "APM_SERVICE_NAME_MAR", length = 200, nullable = true)
    private String apmServiceNameMar;

    @Column(name = "SM_SERV_DURATION", precision = 3, scale = 0, nullable = true)
    private Long smServDuration;

    @Column(name = "SERVICE_URL", length = 100, nullable = true)
    private String serviceUrl;

    @Column(name = "APPEAL_SERVICE_URL", length = 0, nullable = true)
    private String appealServiceUrl;

    @Column(name = "ORGID")
    private Long orgId;

    @Column(name = "USERID", nullable = false, updatable = false)
    private Long userid;

    @Column(name = "APS_APPL_CLOSED_FLAG", length = 1, nullable = true)
    private String apsApplClosedFlag;

    @Column(name = "APS_AUTHO_FLAG", length = 1, nullable = true)
    private String apsAuthoFlag;

    @Column(name = "APS_APPL_SUCCESS_FLAG", length = 1, nullable = true)
    private String apsApplSuccessFlag;

    @Column(name = "APM_PAY_STAT_FLAG", length = 2, nullable = true)
    private String apmPayStatFlag;

    @Column(name = "CHKLST_VRFY_FLAG", length = 2, nullable = true)
    private String chklstVrfyFlag;

    @Column(name = "APM_STATUS", length = 40, nullable = true)
    private String apmStatus;

    /**
     * @return the apmApplicationId
     */
    public Long getApmApplicationId() {
        return apmApplicationId;
    }

    /**
     * @param apmApplicationId the apmApplicationId to set
     */
    public void setApmApplicationId(final Long apmApplicationId) {
        this.apmApplicationId = apmApplicationId;
    }

    /**
     * @return the apmApplicationNo
     */
    public Long getApmApplicationNo() {
        return apmApplicationNo;
    }

    /**
     * @param apmApplicationNo the apmApplicationNo to set
     */
    public void setApmApplicationNo(final Long apmApplicationNo) {
        this.apmApplicationNo = apmApplicationNo;
    }

    /**
     * @return the apmApplicationDate
     */
    public Date getApmApplicationDate() {
        return apmApplicationDate;
    }

    /**
     * @param apmApplicationDate the apmApplicationDate to set
     */
    public void setApmApplicationDate(final Date apmApplicationDate) {
        this.apmApplicationDate = apmApplicationDate;
    }

    /**
     * @return the apmApplicationNameEng
     */
    public String getApmApplicationNameEng() {
        return apmApplicationNameEng;
    }

    /**
     * @param apmApplicationNameEng the apmApplicationNameEng to set
     */
    public void setApmApplicationNameEng(final String apmApplicationNameEng) {
        this.apmApplicationNameEng = apmApplicationNameEng;
    }

    /**
     * @return the apmApplicationNameReg
     */
    public String getApmApplicationNameReg() {
        return apmApplicationNameReg;
    }

    /**
     * @param apmApplicationNameReg the apmApplicationNameReg to set
     */
    public void setApmApplicationNameReg(final String apmApplicationNameReg) {
        this.apmApplicationNameReg = apmApplicationNameReg;
    }

    /**
     * @return the appealServiceId
     */
    public Long getAppealServiceId() {
        return appealServiceId;
    }

    /**
     * @param appealServiceId the appealServiceId to set
     */
    public void setAppealServiceId(final Long appealServiceId) {
        this.appealServiceId = appealServiceId;
    }

    /**
     * @return the serviceId
     */
    public Long getServiceId() {
        return serviceId;
    }

    /**
     * @param serviceId the serviceId to set
     */
    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }

    /**
     * @return the apmServiceName
     */
    public String getApmServiceName() {
        return apmServiceName;
    }

    /**
     * @param apmServiceName the apmServiceName to set
     */
    public void setApmServiceName(final String apmServiceName) {
        this.apmServiceName = apmServiceName;
    }

    /**
     * @return the apmServiceNameMar
     */
    public String getApmServiceNameMar() {
        return apmServiceNameMar;
    }

    /**
     * @param apmServiceNameMar the apmServiceNameMar to set
     */
    public void setApmServiceNameMar(final String apmServiceNameMar) {
        this.apmServiceNameMar = apmServiceNameMar;
    }

    /**
     * @return the smServDuration
     */
    public Long getSmServDuration() {
        return smServDuration;
    }

    /**
     * @param smServDuration the smServDuration to set
     */
    public void setSmServDuration(final Long smServDuration) {
        this.smServDuration = smServDuration;
    }

    /**
     * @return the serviceUrl
     */
    public String getServiceUrl() {
        return serviceUrl;
    }

    /**
     * @param serviceUrl the serviceUrl to set
     */
    public void setServiceUrl(final String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    /**
     * @return the appealServiceUrl
     */
    public String getAppealServiceUrl() {
        return appealServiceUrl;
    }

    /**
     * @param appealServiceUrl the appealServiceUrl to set
     */
    public void setAppealServiceUrl(final String appealServiceUrl) {
        this.appealServiceUrl = appealServiceUrl;
    }

    /**
     * @return the orgId
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    /**
     * @return the userid
     */
    public Long getUserid() {
        return userid;
    }

    /**
     * @param userid the userid to set
     */
    public void setUserid(final Long userid) {
        this.userid = userid;
    }

    /**
     * @return the apsApplClosedFlag
     */
    public String getApsApplClosedFlag() {
        return apsApplClosedFlag;
    }

    /**
     * @param apsApplClosedFlag the apsApplClosedFlag to set
     */
    public void setApsApplClosedFlag(final String apsApplClosedFlag) {
        this.apsApplClosedFlag = apsApplClosedFlag;
    }

    /**
     * @return the apsAuthoFlag
     */
    public String getApsAuthoFlag() {
        return apsAuthoFlag;
    }

    /**
     * @param apsAuthoFlag the apsAuthoFlag to set
     */
    public void setApsAuthoFlag(final String apsAuthoFlag) {
        this.apsAuthoFlag = apsAuthoFlag;
    }

    /**
     * @return the apsApplSuccessFlag
     */
    public String getApsApplSuccessFlag() {
        return apsApplSuccessFlag;
    }

    /**
     * @param apsApplSuccessFlag the apsApplSuccessFlag to set
     */
    public void setApsApplSuccessFlag(final String apsApplSuccessFlag) {
        this.apsApplSuccessFlag = apsApplSuccessFlag;
    }

    /**
     * @return the apmPayStatFlag
     */
    public String getApmPayStatFlag() {
        return apmPayStatFlag;
    }

    /**
     * @param apmPayStatFlag the apmPayStatFlag to set
     */
    public void setApmPayStatFlag(final String apmPayStatFlag) {
        this.apmPayStatFlag = apmPayStatFlag;
    }

    /**
     * @return the chklstVrfyFlag
     */
    public String getChklstVrfyFlag() {
        return chklstVrfyFlag;
    }

    /**
     * @param chklstVrfyFlag the chklstVrfyFlag to set
     */
    public void setChklstVrfyFlag(final String chklstVrfyFlag) {
        this.chklstVrfyFlag = chklstVrfyFlag;
    }

    /**
     * @return the apmStatus
     */
    public String getApmStatus() {
        return apmStatus;
    }

    /**
     * @param apmStatus the apmStatus to set
     */
    public void setApmStatus(final String apmStatus) {
        this.apmStatus = apmStatus;
    }

}