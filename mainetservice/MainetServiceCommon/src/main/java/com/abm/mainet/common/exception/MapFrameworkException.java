package com.abm.mainet.common.exception;

public class MapFrameworkException extends FrameworkException {
	
    private static final long serialVersionUID = 1L;

    private String errCode = "BPM_ERROR";

    private String errMsg = "BPM_ERROR";


    public MapFrameworkException(final Throwable cause) {
        super(cause.getMessage(),cause);
    }
    
    public MapFrameworkException(final Exception cause) {
    	 super(cause.getMessage(),cause);
    }

    public MapFrameworkException(final Exception cause, final String message) {
        super(message, cause);
    }

    public MapFrameworkException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public MapFrameworkException(final String message, final MapFrameworkException cause) {
        super(message, cause);
    }

    public MapFrameworkException(final String message) {
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
