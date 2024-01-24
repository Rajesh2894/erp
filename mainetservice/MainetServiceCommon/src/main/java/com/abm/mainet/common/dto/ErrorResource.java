package com.abm.mainet.common.dto;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResource {
    private String code;
    private String status;
    private String message;
    private List<FieldErrorResource> fieldErrors;

    public ErrorResource() {
    }

    public ErrorResource(final String code, final String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public List<FieldErrorResource> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(final List<FieldErrorResource> fieldErrors) {
        this.fieldErrors = fieldErrors;
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

}