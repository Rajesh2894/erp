package com.abm.mainet.quartz.util;

import java.lang.reflect.Method;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;

/**
 * @author Vikrant.Thakur
 *
 */
@Component
public class JobTask implements Job {

    @Override
    public void execute(final JobExecutionContext context) {
        try {
            final JobDataMap dataMap = context.getJobDetail().getJobDataMap();

            final JobDetailsDefination jobParameterDefination = (JobDetailsDefination) dataMap
                    .get(MainetConstants.QUARTZ_SCHEDULE.JOB_DEFINATION);

            final Object runTimeClassNameInstance = jobParameterDefination.getBeanName();

            final List<Object> dataList = jobParameterDefination.getParamList();

            final Class<?> classDetails = Class.forName(runTimeClassNameInstance.getClass().getName());

            final Object runTimeClassName = classDetails.newInstance();

            final Method setMethod = classDetails.getDeclaredMethod(MainetConstants.QUARTZ_SCHEDULE.JOB_METHOD_NAME, Object.class,
                    List.class);

            setMethod.invoke(runTimeClassName, runTimeClassNameInstance, dataList);

        } catch (final Exception e) {

            throw new FrameworkException(e);

        }

    }

}
