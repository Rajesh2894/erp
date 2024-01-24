package com.abm.mainet.mobile.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author umashanker.kanaujiya
 *
 */
@JsonFormat
public class CommonAppResponseDTO implements Serializable {

    private static final long serialVersionUID = -832384532568233613L;
    private String status;
    private Long userId;
    private Long orgId;
    private String responseMsg;
    private String errorMsg;
    private HttpStatus httpstatus;

    @NotNull
    @NotEmpty
    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    @NotNull
    @NotEmpty
    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(final String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(final String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Long getSynchTime() {
        return new Date().getTime();
    }

    public void setSynchTime(final Long synchTime) {
    }

    /**
     * @return the successCode
     */

    /**
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @return the httstatus
     */
    public HttpStatus getHttpstatus() {

        return httpstatus;
    }

    /**
     * @param httstatus the httstatus to set
     */
    public void setHttpstatus(final HttpStatus httstatus) {

        httpstatus = httstatus;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    /**
     * @return the orgId
     */
    @NotNull
    public Long getOrgId() {
        return orgId;
    }

    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

}
