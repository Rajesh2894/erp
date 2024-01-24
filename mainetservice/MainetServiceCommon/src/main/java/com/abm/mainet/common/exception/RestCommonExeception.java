package com.abm.mainet.common.exception;

public class RestCommonExeception extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RestCommonExeception(final String message, final Exception exception) {
        super(message, exception);

    }

    public RestCommonExeception(final String message) {
        super(message);

    }

}
