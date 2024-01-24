package com.abm.mainet.common.audit;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.ReflectionUtils;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.utility.ApplicationContextProvider;

/**
 * @author ritesh.patil
 *
 */

@Aspect
public class AsyncAspect {

    /**
     * this method execute in new thread using Taskexecutor
     *
     * @param joinPoint The primary use of this reflective information is for tracing and logging applications.
     * @param designationEntitySaved pass the saved object
     *
     * @throws AsyncUncaughtExceptionHandler throws exception
     *
     */
    @After(MainetConstants.AUDIT.POINTCUT_SAVE)
    @Async
    public void saveRecord(final JoinPoint joinPoint, final Object sourceObject, final Object targetObject) {
        final Object targetObjectJoint = joinPoint.getTarget();
        BeanUtils.copyProperties(sourceObject, targetObject);
        callMehodForSaving(targetObjectJoint, targetObject, MainetConstants.AUDIT.METHOD_NAME_SAVE);
    }

    /**
     * Call at run time using Reflection.
     *
     * @param targetObject The target object to point to target class where you will be performed.
     * @param objClass Name of the method
     * @param objClass paramTypes the parameter types of the method (may be null to indicate any signature
     *
     * @param methodName name of the method
     *
     */
    private void callMehodForSaving(final Object targetObject, final Object object, final String methodName) {
        final Method method = ReflectionUtils.findMethod(targetObject.getClass(), methodName, new Class[] { Object.class });
        final Object runtimeService = ApplicationContextProvider.getApplicationContext().getAutowireCapableBeanFactory()
                .autowire(targetObject.getClass(), MainetConstants.NUMBERS.FOUR, true);
        ReflectionUtils.invokeMethod(method, runtimeService, new Object[] { object });
    }
}
