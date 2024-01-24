package com.abm.mainet.common.util;

import com.abm.mainet.common.constant.MainetConstants;

public class Logger {
    private org.apache.log4j.Logger logger = null;
    private Class<?> clazz = null;
    private boolean enableMethodLogging = true;

    public Logger(final Class<?> clazz) {
        this(clazz, false);
    }

    public Logger(final Class<?> clazz, final boolean enableMethodLogging) {
        this.clazz = clazz;
        this.enableMethodLogging = enableMethodLogging;

        logger = org.apache.log4j.Logger.getLogger(clazz);
    }

    public final void inMethod(final String method) {
        if (enableMethodLogging) {
            logger.debug("Entering " + clazz.getSimpleName() + MainetConstants.operator.DOT + method);
        }
    }

    public final void outMethod(final String method) {
        if (enableMethodLogging) {
            logger.debug("Exiting " + clazz.getSimpleName() + MainetConstants.operator.DOT + method);
        }
    }

    public void debug(final Object message, final Exception t) {
        logger.debug(message, t);
    }

    public void debug(final Object message) {
        logger.debug(message);
    }

    public void error(final Object message, final Exception t) {
        logger.error(message, t);
    }

    public void error(final Object message) {
        logger.error(message);
    }

    public void fatal(final Object message, final Exception t) {
        logger.fatal(message, t);
    }

    public void fatal(final Object message) {
        logger.fatal(message);
    }

    public void info(final Object message, final Exception t) {
        logger.info(message, t);
    }

    public void info(final Object message) {
        logger.info(message);
    }

    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    public boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }

    public void trace(final Object message, final Exception t) {
        logger.trace(message, t);
    }

    public void trace(final Object message) {
        logger.trace(message);
    }

    public void warn(final Object message, final Exception t) {
        logger.warn(message, t);
    }

    public void warn(final Object message) {
        logger.warn(message);
    }
}
