package com.abm.mainet.config.logging;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.constant.MainetConstants;

@Aspect
public class LoggingAspect {

    /*    *//**
             * Pointcut that matches spring components.
             */
    /*
     * @Pointcut("within(@org.springframework.stereotype.Controller *)") public void springBeanPointcut() { }
     *//**
        * Pointcut that matches all classes in the application's main package.
        */
    /*
     * @Pointcut("within(" + "com.abm.mainet.*.service.* || " + "com.abm.mainet.*..service.* || " +
     * "com.abm.mainet.*..service.impl.* || " + "com.abm.mainet.*.repository.* || " + "com.abm.mainet.*..controller.* || " +
     * "com.abm.mainet.util.* || " + "com.abm.mainet.common.exception.* || " + "com.abm.mainet.config.logging.* || " +
     * "com.abm.mainet.*.utility.*)") public void applicationPackagePointcut() { }
     *//**
        * Advice that logs when a method is entered and exited.
        *
        * @param joinPoint join point for advice
        * @return result
        * @throws Throwable
        *//*
           * @Around("applicationPackagePointcut() || springBeanPointcut()") public Object logAround(ProceedingJoinPoint
           * joinPoint) throws Throwable { Logger log = Logger.getLogger(joinPoint.getTarget().getClass()); long start =
           * System.currentTimeMillis(); log.info( MainetConstants.LOGGING.START_TIME_METHOD_NAME + getMethodName(joinPoint) +
           * MainetConstants.LOGGING.START_TIME_MILLI_SECOND + getDuration(start)); Object result; try { result =
           * joinPoint.proceed(); } catch (Throwable ex) { log.error(getExceptionAsString(ex, getDuration(start)), ex); throw new
           * FrameworkException(ex) ; } log.info(MainetConstants.LOGGING.END_TIME_METHOD_NAME + getMethodName(joinPoint) +
           * MainetConstants.LOGGING.END_TIME_MILLI_SECOND + getDuration(start)); return result; } protected long getDuration(long
           * start) { return System.currentTimeMillis() - start; } protected String getMethodName(ProceedingJoinPoint joinPoint) {
           * return MethodSignature.class.cast(joinPoint.getSignature()).getMethod().getName(); } protected String
           * getExceptionAsString(Throwable ex, long duration) { return new
           * StringBuilder(" threw ").append(ex.getClass().getSimpleName()).append(" after ").append(duration)
           * .append(" msecs with message ").append(ex.getMessage()).toString(); }
           */

    @Around("within(com.abm.mainet.*.*.*.ui.controller.* || com.abm.mainet.*.*.*.service.* ||  com.abm.mainet.*.*.*.dao.* || com.abm.mainet.*.*.ui.controller.* || com.abm.mainet.*.*.service.* ||  com.abm.mainet.*.*.dao.* ||  com.abm.mainet.*.*.repository.* ||  com.abm.mainet.*.service.* || com.abm.mainet.*.dao.*|| com.abm.mainet.*.mapper.* || com.abm.mainet.*.repository.* ||  com.abm.mainet.*.model.* || com.abm.mainet.*.validator.* || com.abm.mainet.*.ui.controller.* || com.abm.mainet.bpm.utility.*)")
    public Object logAround(ProceedingJoinPoint joinPoint) throws FrameworkException {
        Logger log = Logger.getLogger(joinPoint.getTarget().getClass());
        long start = System.currentTimeMillis();
        log.info(MainetConstants.LOGGING.START_TIME_METHOD_NAME + " ClassName["
                + joinPoint.getSignature().getDeclaringType().getSimpleName() + "]->MethodName[" +
                getMethodName(joinPoint) + "] " + MainetConstants.LOGGING.START_TIME_MILLI_SECOND
                + getDuration(start));
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable ex) {
            log.error(getExceptionAsString(ex, getDuration(start)), ex);
            throw new FrameworkException(ex.getMessage(), ex);
        }
        log.info(
                MainetConstants.LOGGING.END_TIME_METHOD_NAME + " ClassName["
                        + joinPoint.getSignature().getDeclaringType().getSimpleName()
                        + "]->MethodName[" + getMethodName(joinPoint) + "] " + MainetConstants.LOGGING.END_TIME_MILLI_SECOND
                        + getDuration(start));
        return result;
    }

    protected long getDuration(long start) {
        return System.currentTimeMillis() - start;
    }

    protected String getMethodName(ProceedingJoinPoint joinPoint) {
        return MethodSignature.class.cast(joinPoint.getSignature()).getMethod().getName();
    }

    protected String getExceptionAsString(Throwable ex, long duration) {
        return new StringBuilder(" threw ").append(ex.getClass().getSimpleName()).append(" after ").append(duration)
                .append(" msecs with message ").append(ex.getMessage()).toString();
    }

}