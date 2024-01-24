package com.abm.mainet.common.logging;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;

@Aspect
public class LoggingAspect {

    @Around("within(com.abm.mainet.*.*.*.ui.controller.* || com.abm.mainet.*.*.*.service.* ||  com.abm.mainet.*.*.*.dao.* || com.abm.mainet.*.*.ui.controller.* || com.abm.mainet.*.*.service.* ||  com.abm.mainet.*.*.dao.* ||  com.abm.mainet.*.*.repository.* ||  com.abm.mainet.*.service.* || com.abm.mainet.*.dao.*|| com.abm.mainet.*.mapper.* || com.abm.mainet.*.repository.* ||  com.abm.mainet.*.model.* || com.abm.mainet.*.validator.* || com.abm.mainet.*.ui.controller.* || com.abm.mainet.*.utility.Utility.java)")
    public Object logAround(ProceedingJoinPoint joinPoint) throws FrameworkException {
        Logger log = Logger.getLogger(joinPoint.getTarget().getClass());
        long start = System.currentTimeMillis();
        log.info(MainetConstants.START_TIME_METHOD_NAME + " ClassName[" + joinPoint.getSignature().getDeclaringType().getSimpleName() + "]->MethodName[" +
                getMethodName(joinPoint) + "] " + MainetConstants.START_TIME_MILLI_SECOND
                        + getDuration(start));
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable ex) {
            log.error(getExceptionAsString(ex, getDuration(start)), ex);
            throw new FrameworkException(ex.getMessage(), ex);
        }
        log.info(MainetConstants.END_TIME_METHOD_NAME + " ClassName[" + joinPoint.getSignature().getDeclaringType().getSimpleName() + "]->MethodName[" + getMethodName(joinPoint) + "] " + MainetConstants.END_TIME_MILLI_SECOND
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