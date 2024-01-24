package com.abm.mainet.common.dto;

import java.io.Serializable;

public class WebServiceResponseDTO implements Serializable {

    private static final long serialVersionUID = 7846465012569981244L;

    private String status;
    private String responseMsg;
    private String errorMsg;
    private String errorCode;
    private Long synchTime;
    private String successCode;

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

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

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(final String errorCode) {
        this.errorCode = errorCode;
    }

    public Long getSynchTime() {
        return synchTime;
    }

    public void setSynchTime(final Long synchTime) {
        this.synchTime = synchTime;
    }

    /**
     * @return the successCode
     */
    public String getSuccessCode() {
        return successCode;
    }

    /**
     * @param successCode the successCode to set
     */
    public void setSuccessCode(final String successCode) {
        this.successCode = successCode;
    }

}
