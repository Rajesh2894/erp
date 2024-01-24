package com.abm.mainet.quartz.util;

import java.lang.reflect.Method;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.quartz.domain.QuartzSchedulerMaster;

/**
 *
 * @author Vivek.Kumar
 * @since 11-May-2015
 */
@Component
public class MultiJobTask implements Job {

    private static final String ERROR_OCCURED_DURING_EXECUTING_MULTI_JOB_TASK_EXECUTE = "Error occured during executing MultiJobTask.execute";

    @Override
    public void execute(final JobExecutionContext context) throws JobExecutionException {

        Class<?> clazz = null;
        Object runtimeBean = null;
        QuartzSchedulerMaster jobData = null;
        JobDataMap dataMap = null;
        final List<QuartzSchedulerMaster> dataList = null;
        String serviceName = null;

        try {
            dataMap = context.getJobDetail().getJobDataMap();
            jobData = (QuartzSchedulerMaster) dataMap.get(MainetConstants.QUARTZ_SCHEDULE.JOB_DATA);
            serviceName = jobData.getJobClassName();
            clazz = ClassUtils.forName(serviceName, ApplicationContextProvider.getApplicationContext()
                    .getClassLoader());
            runtimeBean = ApplicationContextProvider.getApplicationContext().getAutowireCapableBeanFactory()
                    .autowire(clazz, 4, false);

            final Method method = ReflectionUtils.findMethod(clazz, jobData.getJobFuncName(),
                    new Class[] { QuartzSchedulerMaster.class, List.class });
            ReflectionUtils.invokeMethod(method, runtimeBean, new Object[] {
                    jobData, dataList });

        } catch (final Exception e) {
            throw new FrameworkException(ERROR_OCCURED_DURING_EXECUTING_MULTI_JOB_TASK_EXECUTE, e);

        }

    }

}
