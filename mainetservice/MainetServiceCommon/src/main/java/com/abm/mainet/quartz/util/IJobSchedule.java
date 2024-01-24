package com.abm.mainet.quartz.util;

import java.io.Serializable;
import java.util.List;

import org.quartz.SchedulerException;

import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.quartz.domain.QuartzSchedulerMaster;

/**
 * @author Vikrant.Thakur
 *
 */

public interface IJobSchedule extends Serializable {
    public void scheduleEventBasedJob(Object runTimeCLass, List<Object> dataList) throws FrameworkException, SchedulerException;

    public void scheduleTimeBasedJob(Object runTimeCLass, List<Object> dataList, int repeatTime)
            throws FrameworkException, SchedulerException;

    public void scheduleCronBasedJob(Object runTimeCLass, List<Object> dataList, String cronExpression)
            throws FrameworkException, SchedulerException;

    /**
     * this is the main method from where Quartz Scheduler start and get scheduled with jobs information that what and when have
     * to execute.
     * @param dataList
     * @throws FrameworkException
     * @throws SchedulerException
     * @throws ClassNotFoundException
     * @throws LinkageError
     */
    public void scheduleCronBasedMultipleJobs(List<QuartzSchedulerMaster> dataList)
            throws FrameworkException, SchedulerException, ClassNotFoundException, LinkageError;

}
