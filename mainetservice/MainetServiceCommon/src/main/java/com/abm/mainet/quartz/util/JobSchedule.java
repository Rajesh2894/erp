package com.abm.mainet.quartz.util;

import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.quartz.domain.QuartzSchedulerMaster;

/**
 * @author Vikrant.Thakur
 *
 */

@Component
public class JobSchedule implements IJobSchedule {

    private static final long serialVersionUID = -7731487496001471659L;

    @Override
    public void scheduleEventBasedJob(final Object runTimeClass, final List<Object> dataList)
            throws FrameworkException, SchedulerException {

        Scheduler scheduler = null;

        final JobDetailsDefination jd = getJobDefinations(runTimeClass, dataList);

        final JobDataMap jm = new JobDataMap();

        jm.put(MainetConstants.QUARTZ_SCHEDULE.JOB_DEFINATION, jd);
        jm.put(MainetConstants.QUARTZ_SCHEDULE.JOB_CLASS_NAME, runTimeClass);

        final JobDetail job = JobBuilder.newJob(JobTask.class)
                .usingJobData(jm)
                .build();

        final Trigger trigger = TriggerBuilder
                .newTrigger()
                .startNow()
                .build();

        // schedule it
        scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        scheduler.scheduleJob(job, trigger);

    }

    @Override
    public void scheduleTimeBasedJob(final Object runTimeClass, final List<Object> dataList, final int repeatTime)
            throws FrameworkException, SchedulerException {

        Scheduler scheduler = null;

        final JobDetailsDefination jd = getJobDefinations(runTimeClass, dataList);

        final JobDataMap jm = new JobDataMap();

        jm.put(MainetConstants.QUARTZ_SCHEDULE.JOB_DEFINATION, jd);

        jm.put(MainetConstants.QUARTZ_SCHEDULE.JOB_CLASS_NAME, runTimeClass);

        final JobDetail job = JobBuilder.newJob(JobTask.class).usingJobData(jm).build();

        final Trigger trigger = TriggerBuilder
                .newTrigger()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(repeatTime).repeatForever())
                .build();

        // schedule it
        scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        scheduler.scheduleJob(job, trigger);

    }

    // http://www.quartz-scheduler.org/documentation/quartz-1.x/tutorials/crontrigger Use this Site for
    @Override
    public void scheduleCronBasedJob(final Object runTimeClass, final List<Object> dataList, final String cronExpression)
            throws FrameworkException, SchedulerException {
        Scheduler scheduler = null;

        final JobDetailsDefination jd = getJobDefinations(runTimeClass, dataList);

        final JobDataMap jm = new JobDataMap();

        jm.put(MainetConstants.QUARTZ_SCHEDULE.JOB_DEFINATION, jd);

        jm.put(MainetConstants.QUARTZ_SCHEDULE.JOB_CLASS_NAME, runTimeClass);

        final JobDetail job = JobBuilder.newJob(JobTask.class).usingJobData(jm).build();

        final Trigger trigger = TriggerBuilder
                .newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();

        // schedule it
        scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        scheduler.scheduleJob(job, trigger);

    }

    /**
     * schedules multiple Batch Job based on given Cron expression.
     */
    @Override
    public void scheduleCronBasedMultipleJobs(final List<QuartzSchedulerMaster> dataList) throws FrameworkException,
            SchedulerException, ClassNotFoundException, LinkageError {

        Scheduler scheduler = null;
        int count = 0;

        if (dataList != null) {
            // get the Scheduler instance from the Factory
            scheduler = new StdSchedulerFactory().getScheduler();
            // and start it
            scheduler.start();
            for (final QuartzSchedulerMaster jobData : dataList) {
                if (MainetConstants.Common_Constant.YES.equalsIgnoreCase(jobData.getInvokeOnEditOrSubmit())) {
                    if (MainetConstants.Common_Constant.INACTIVE_FLAG.equalsIgnoreCase(jobData.getStatus())) {
                        unscheduleJob(scheduler, generateTriggerName(jobData), generateJobGroup(jobData));
                    } else if (MainetConstants.Common_Constant.ACTIVE_FLAG.equalsIgnoreCase(jobData.getStatus())) {
                        reScheduleExistingTrigger(jobData, scheduler, count);
                    }
                } else {
                    scheduleActiveJobWithNewTrigger(jobData, scheduler, count);
                    count++;
                }
            }
        }
    }

    /**
     * 
     * @param jobData
     * @param scheduler
     * @param count
     * @throws SchedulerException this method is used to reschedule Trigger, check whether corresponding trigger is already
     * schedule or not if it scheduled already then only it will reschedule existing trigger without creating new one.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void reScheduleExistingTrigger(final QuartzSchedulerMaster jobData, final Scheduler scheduler, final int count)
            throws SchedulerException {

        String jobGroup = null;
        String triggerName = null;

        jobGroup = generateJobGroup(jobData);
        triggerName = generateTriggerName(jobData);
        final Trigger oldTrigger = scheduler.getTrigger(TriggerKey.triggerKey(triggerName, jobGroup));
        if (oldTrigger != null) {
            final TriggerBuilder tb = oldTrigger.getTriggerBuilder();
            final Trigger newTrigger = tb.withSchedule(CronScheduleBuilder.cronSchedule(jobData.getCronExpression()))
                    .build();
            scheduler.rescheduleJob(oldTrigger.getKey(), newTrigger);
        } else {
            scheduleActiveJobWithNewTrigger(jobData, scheduler, count);
        }
    }

    /**
     * 
     * @param jobData
     * @param scheduler
     * @param count
     * @throws SchedulerException
     * 
     * This method is responsible to create new Trigger for each job and schedule them all
     */
    private void scheduleActiveJobWithNewTrigger(final QuartzSchedulerMaster jobData, final Scheduler scheduler, final int count)
            throws SchedulerException {

        String jobKey = null;
        Trigger trigger = null;
        JobDetail job = null;
        JobDataMap jobDataMap = null;
        String jobGroup = null;
        String triggerName = null;
        JobKey jobKeyObj = null;

        jobKey = generateJobKey(jobData, count);
        jobGroup = generateJobGroup(jobData);
        triggerName = generateTriggerName(jobData);
        jobDataMap = new JobDataMap();
        jobDataMap.put(MainetConstants.QUARTZ_SCHEDULE.JOB_DATA, jobData);
        // define the job and tie it to our MultiJobTask class
        jobKeyObj = new JobKey(jobKey, jobGroup);
        job = JobBuilder.newJob(MultiJobTask.class)
                .withIdentity(jobKeyObj)
                .usingJobData(jobDataMap)
                .build();
        // Trigger the job to run at specific time
        trigger = TriggerBuilder
                .newTrigger()
                .withIdentity(triggerName, jobGroup)
                .withSchedule(CronScheduleBuilder.cronSchedule(jobData.getCronExpression()))
                .build();
        // Tell quartz to schedule the job using our trigger
        scheduler.scheduleJob(job, trigger);
    }

    /**
     * 
     * @param scheduler
     * @param triggerName
     * @param jobGroup
     * @throws SchedulerException This method is responsible to check which trigger has been inactive, find out and unSchedule
     * that particular trigger from quartz.
     */
    private void unscheduleJob(final Scheduler scheduler, final String triggerName, final String jobGroup)
            throws SchedulerException {

        final Trigger oldTrigger = scheduler.getTrigger(TriggerKey.triggerKey(triggerName, jobGroup));
        if (oldTrigger != null) {
            scheduler.unscheduleJob(TriggerKey.triggerKey(triggerName, jobGroup));
        }
    }

    private String generateTriggerName(final QuartzSchedulerMaster jobData) {

        final StringBuilder builder = new StringBuilder();
        builder.append(MainetConstants.QUARTZ_SCHEDULE.TRIGGER_NAME)
                .append(jobData.getDpDeptid())
                .append(MainetConstants.operator.UNDER_SCORE)
                .append(jobData.getJobId());
        return builder.toString();
    }

    private String generateJobGroup(final QuartzSchedulerMaster jobData) {
        final StringBuilder builder = new StringBuilder();
        builder.append(MainetConstants.QUARTZ_SCHEDULE.JOB_GROUP)
                .append(MainetConstants.operator.UNDER_SCORE)
        	.append(jobData.getOrgId().getOrgid())
                .append(MainetConstants.operator.UNDER_SCORE)
                .append(jobData.getDpDeptid());
        return builder.toString();
    }

    private String generateJobKey(final QuartzSchedulerMaster jobData, final int count) {

        final StringBuilder builder = new StringBuilder();
        builder.append(jobData.getJobProcName().trim())
                .append(MainetConstants.operator.UNDER_SCORE)
                .append(count);
        return builder.toString();
    }

    public JobDetailsDefination getJobDefinations(final Object beanName, final List<Object> dataList) {

        final JobDetailsDefination jd = new JobDetailsDefination();

        jd.setBeanName(beanName);

        jd.setParamList(dataList);

        jd.setActive(true);

        return jd;
    }

    public JobDetailsDefination getJobDefinitionFromMaster(final Object beanName, final List<QuartzSchedulerMaster> dataList) {

        final JobDetailsDefination jd = new JobDetailsDefination();

        jd.setBeanName(beanName);

        jd.setJobsParam(dataList);
        jd.setActive(true);

        return jd;
    }

}
