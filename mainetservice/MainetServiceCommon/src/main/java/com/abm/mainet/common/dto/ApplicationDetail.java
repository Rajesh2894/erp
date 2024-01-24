package com.abm.mainet.common.dto;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * @author vishnu.jagdale
 *
 */
@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
public class ApplicationDetail implements Serializable {

    private static final long serialVersionUID = 7058016929104225713L;

    private Long apmApplicationId;

    private Long apmApplicationNo;

    private Date apmApplicationDate;

    private String apmApplicationNameEng;

    private String apmApplicationNameReg;

    private Long appealServiceId;

    private Long serviceId;

    private String apmServiceName;

    private String apmServiceNameMar;

    private Long smServDuration;

    private String serviceUrl;

    private String appealServiceUrl;

    private Long orgId;

    private Long userid;

    private String apsApplClosedFlag;

    private String apsAuthoFlag;

    private String apsApplSuccessFlag;

    private String apmPayStatFlag;

    private String chklstVrfyFlag;

    private String apmStatus;

    private String errorCode;

    private String errorCause;

    private String errorMsg;

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

    /**
     * @return the errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode the errorCode to set
     */
    public void setErrorCode(final String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @return the errorCause
     */
    public String getErrorCause() {
        return errorCause;
    }

    /**
     * @param errorCause the errorCause to set
     */
    public void setErrorCause(final String errorCause) {
        this.errorCause = errorCause;
    }

    /**
     * @return the errorMsg
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * @param errorMsg the errorMsg to set
     */
    public void setErrorMsg(final String errorMsg) {
        this.errorMsg = errorMsg;
    }

}
