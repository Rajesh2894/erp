package com.abm.mainet.common.dto;

import java.io.Serializable;

import com.abm.mainet.common.utility.ApplicationSession;

/**
 * @author Pranit.Mhatre
 * @since 20 December, 2013
 */
public final class JsonViewObject implements Serializable {
    private JsonViewObject() {
    }

    private static final long serialVersionUID = -5026072606916679985L;
    private boolean status;
    private String message;
    private String url;
    private String hiddenOtherVal;
    private boolean hasValidationError;

    /**
     * @return the status
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(final boolean status) {
        this.status = status;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(final String message) {
        this.message = message;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(final String url) {
        this.url = url;
    }

    public static JsonViewObject successResult() {
        return successResult(null);
    }

    public static JsonViewObject successResult(final String successMessage) {
        final JsonViewObject result = new JsonViewObject();
        result.setMessage(successMessage);
        result.setStatus(true);
        result.setHiddenOtherVal("");
        return result;
    }

    public static JsonViewObject successResult(final String msgTemplate, final Object... objects) {
        final JsonViewObject result = new JsonViewObject();
        result.setMessage(ApplicationSession.getInstance().getMessage(msgTemplate, objects));
        result.setStatus(true);
        result.setHiddenOtherVal("");
        return result;
    }

    public static JsonViewObject failureResult(final Throwable ex) {
        final JsonViewObject result = new JsonViewObject();
        result.setStatus(false);
        result.setMessage(ApplicationSession.getInstance().getMessage("Operation.FAIL"));
        result.setHiddenOtherVal("SERVERERROR");
        return result;
    }

    public static JsonViewObject failureResult(final String messageText) {
        final JsonViewObject result = new JsonViewObject();
        result.setStatus(false);
        result.setMessage(messageText);
        return result;
    }

    public boolean isHasValidationError() {
        return hasValidationError;
    }

    public void setHasValidationError(final boolean hasValidationError) {
        this.hasValidationError = hasValidationError;
    }

    /**
     * @return the hiddenOtherVal
     */
    public String getHiddenOtherVal() {
        return hiddenOtherVal;
    }

    /**
     * @param hiddenOtherVal the hiddenOtherVal to set
     */
    public void setHiddenOtherVal(final String hiddenOtherVal) {
        this.hiddenOtherVal = hiddenOtherVal;
    }

}
