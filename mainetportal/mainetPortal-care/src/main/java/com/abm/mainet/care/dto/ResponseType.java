package com.abm.mainet.care.dto;

public enum ResponseType {
    Success("SUCCESS"), Fail("FAIL");

    /**
     * Response of Action execution
     */
    private String response;

    /**
     * constructor
     * @param response response in string format
     */
    private ResponseType(final String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return response;
    }
}
