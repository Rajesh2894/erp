package com.abm.mainet.common.exception;

public class FrameworkException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String errCode;

    private String errMsg;

    public FrameworkException() {
        super();
    }

    public FrameworkException(final String message, final Exception cause) {
        super(message, cause);
        errMsg = message;
    }
    
    public FrameworkException(final String message, final Throwable cause) {
        super(message, cause);
        errMsg = message;
    }
    
    public FrameworkException(final String message) {
        super(message);
        this.errMsg = message;
    }

    public FrameworkException(final Exception cause) {
        super(cause);

    }

    public FrameworkException(final String errCode, final String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    /**
     * @return the errCode
     */
    public String getErrCode() {
        return errCode;
    }

    /**
     * @param errCode the errCode to set
     */
    public void setErrCode(final String errCode) {
        this.errCode = errCode;
    }

    /**
     * @return the errMsg
     */
    public String getErrMsg() {
        return errMsg;
    }

    /**
     * @param errMsg the errMsg to set
     */
    public void setErrMsg(final String errMsg) {
        this.errMsg = errMsg;
    }

}
