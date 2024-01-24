package com.abm.mainet.dashboard.citizen.dto;

import java.util.List;

/**
 * @author ritesh.patil
 *
 */
public class DashBoardDTO {

    private List<CitizenDashBoardResDTO> citizenDashBoard;

    // field, to identify whether WS Request fail or success
    private String status;

    // field ,in case error return by stored procedure call
    private String errorMsg;

    // fields, in case problem occurred during request processing
    private String errorCode;

    private String reason;  // possible cause of the error

    /**
     * @return the citizenDashBoard
     */
    public List<CitizenDashBoardResDTO> getCitizenDashBoard() {
        return citizenDashBoard;
    }

    /**
     * @param citizenDashBoard the citizenDashBoard to set
     */
    public void setCitizenDashBoard(final List<CitizenDashBoardResDTO> citizenDashBoard) {
        this.citizenDashBoard = citizenDashBoard;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(final String status) {
        this.status = status;
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
     * @return the reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * @param reason the reason to set
     */
    public void setReason(final String reason) {
        this.reason = reason;
    }

}
