package com.abm.mainet.common.exception;

import org.springframework.core.NestedRuntimeException;

public class FrameworkException extends NestedRuntimeException {
    private static final long serialVersionUID = 1L;

    private String errCode = "BPM_ERROR";

    private String errMsg = "BPM_ERROR";


    public FrameworkException(final Throwable cause) {
        super(cause.getMessage(),cause);
    }
    
    public FrameworkException(final Exception cause) {
    	 super(cause.getMessage(),cause);
    }

    public FrameworkException(final Exception cause, final String message) {
        super(message, cause);
    }

    public FrameworkException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public FrameworkException(final String message, final FrameworkException cause) {
        super(message, cause);
    }

    public FrameworkException(final String message) {
        super(message);
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
