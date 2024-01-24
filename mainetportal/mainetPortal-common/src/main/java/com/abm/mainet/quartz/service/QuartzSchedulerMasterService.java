package com.abm.mainet.quartz.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.DepartmentLookUp;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.UtilityService;
import com.abm.mainet.quartz.dao.IQuartzSchedulerMasterDao;
import com.abm.mainet.quartz.domain.QuartzSchedulerMaster;
import com.abm.mainet.quartz.ui.model.QuartzSchedulerMasterModel;
import com.abm.mainet.quartz.util.CronExpressionUtility;
import com.abm.mainet.quartz.util.IJobSchedule;

/**
 *
 * @author Vivek.Kumar
 * @since 05-May-2015
 */
@Service
public class QuartzSchedulerMasterService implements IQuartzSchedulerMasterService {

    private static final String BJO = "BJO";

    @Autowired
    private IQuartzSchedulerMasterDao iQuartzSchedulerMasterDao;

    @Autowired
    private IJobSchedule iJobSchedule;

    @Override
    @Transactional(readOnly = true)
    public List<LookUp> findJobNamesByDepartment(final String deptCode) {

        final List<LookUp> jobsLookUp = new ArrayList<>();
        final List<LookUp> lookUps = CommonMasterUtility.getListLookup(BJO, UserSession.getCurrent().getOrganisation());

        if (lookUps != null) {

            for (final LookUp lookUp : lookUps) {

                if ((lookUp.getOtherField() != null) && lookUp.getOtherField().equalsIgnoreCase(deptCode)) {
                    jobsLookUp.add(lookUp);
                }

            }
        }

        return jobsLookUp;

    }

    @Transactional(readOnly = true)
    @Override
    public void invokeQuartzShceduler() throws RuntimeException, SchedulerException, ClassNotFoundException, LinkageError {

        List<QuartzSchedulerMaster> departmentList = null;

        final Map<Long, List<QuartzSchedulerMaster>> finalJobsMap = queryForJobsInfoToBegin();
        final Set<Long> jobSet = finalJobsMap.keySet();

        if (jobSet != null) {
            for (final Long key : jobSet) {
                departmentList = finalJobsMap.get(key);
                iJobSchedule.scheduleCronBasedMultipleJobs(departmentList);
            }
        }

    }

    private Map<Long, List<QuartzSchedulerMaster>> queryForJobsInfoToBegin() {

        final List<QuartzSchedulerMaster> allJobs = iQuartzSchedulerMasterDao.queryForJobsInfoToBegin();

        final Map<Long, List<QuartzSchedulerMaster>> finalJobsMap = filterjobsByDepartmentWise(allJobs);

        return finalJobsMap;
    }

    private Map<Long, List<QuartzSchedulerMaster>> filterjobsByDepartmentWise(final List<QuartzSchedulerMaster> allJobs) {

        Long deptId = 0l;
        List<QuartzSchedulerMaster> deptWiseJobs = null;
        final Map<Long, List<QuartzSchedulerMaster>> finalJobsMap = new LinkedHashMap<>();

        if (allJobs != null) {

            for (final QuartzSchedulerMaster eachJob : allJobs) {

                if (eachJob.getStatus().equalsIgnoreCase(MainetConstants.Common_Constant.ACTIVE_FLAG)) {
                    if (deptId == 0l) {
                        deptId = eachJob.getDpDeptid();
                        deptWiseJobs = new ArrayList<>();
                        deptWiseJobs.add(eachJob);
                    } else {
                        if (deptId == eachJob.getDpDeptid()) {
                            deptWiseJobs.add(eachJob);
                        } else {
                            finalJobsMap.put(deptId, deptWiseJobs);
                            deptId = eachJob.getDpDeptid();
                            deptWiseJobs = new ArrayList<>();

                            deptWiseJobs.add(eachJob);
                        }
                    }
                }

            }

            finalJobsMap.put(deptId, deptWiseJobs);
        }

        return finalJobsMap;
    }

    /**
     * 
     * @param object : Runtime passed Object
     * @param parameterList : list of needed parameters passed this method is the final execution job point event based trigger
     * @throws SQLException
     * @throws FrameworkException
     */
    public void processQuartzJob(final Object object, final List<Object> parameterList) throws FrameworkException, SQLException {

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean processAndSaveQuartzMaster(final QuartzSchedulerMasterModel model)
            throws RuntimeException, ParseException, ClassNotFoundException, SchedulerException, LinkageError {

        boolean success = false;
        final List<QuartzSchedulerMaster> list = new ArrayList<>();
        final QuartzSchedulerMaster entity = model.getEntity();

        final long deptId = findDeptIdByDeptCode(model.getDepartmentForQuartz(), model.getDepartmentList());
        entity.setDpDeptid(deptId);
        entity.setJobProcName(entity.getJobProcName().toUpperCase());
        final QuartzSchedulerMaster savedEntity = iQuartzSchedulerMasterDao
                .saveQuartzMasterJobsInfo(prepareQuartzMasterByJobFrequency(model));
        if (savedEntity != null) {
            savedEntity.setInvokeOnEditOrSubmit(MainetConstants.Common_Constant.YES);
            list.add(savedEntity);
            if (savedEntity.getJobId() != 0) {
                iJobSchedule.scheduleCronBasedMultipleJobs(list);
                success = true;
            }
        }

        return success;
    }

    public long findDeptIdByDeptCode(final String deptCode, final List<DepartmentLookUp> departmentLookUps) {

        long deptId = 0l;
        if (departmentLookUps != null) {
            for (final DepartmentLookUp departmentLookUp : departmentLookUps) {

                if ((departmentLookUp.getLookUpCode() != null) && departmentLookUp.getLookUpCode().equalsIgnoreCase(deptCode)) {
                    deptId = departmentLookUp.getLookUpId();
                    break;
                }
            }
        }

        return deptId;
    }

    private QuartzSchedulerMaster prepareQuartzMasterByJobFrequency(final QuartzSchedulerMasterModel model)
            throws ParseException {

        String startTime = null;
        String[] hrMin = null;
        String hr = null;
        String min = null;
        String hrTime = null;
        String minTime = null;
        String cronExpression = null;
        final Date currentDate = new Date();
        String startOnDate = null;
        String weekDay = null;
        String[] dates = null;
        String dateOfMonth = null;
        String month = null;
        String year = null;
        String monthName = null;
        String repeatOnLastDayOfMonth = null;
        String fireTriggerOnMonth = null;

        final QuartzSchedulerMaster entity = model.getEntity();

        if ((entity.getHiddenJobFrequencyType() != null)
                && entity.getHiddenJobFrequencyType().equals(MainetConstants.QUARTZ_SCHEDULE.JobFrequency.DAILY)) {

            startTime = entity.getStartAtTime_Daily();
        } else {

            startTime = entity.getStartAtTime();
            startOnDate = entity.getStartOnDate_monthly();

            if ((entity.getHiddenJobFrequencyType() != null)
                    && (entity.getHiddenJobFrequencyType().equals(MainetConstants.QUARTZ_SCHEDULE.JobFrequency.MONTHLY)
                            || entity.getHiddenJobFrequencyType().equals(MainetConstants.QUARTZ_SCHEDULE.JobFrequency.QUARTERLY)
                            || entity.getHiddenJobFrequencyType()
                                    .equals(MainetConstants.QUARTZ_SCHEDULE.JobFrequency.HALF_YEARLY))) {

                startOnDate = entity.getStartOnDate_monthly();
                dates = startOnDate.split(MainetConstants.operator.FORWARD_SLACE);
                dateOfMonth = dates[0];
                fireTriggerOnMonth = dates[1];
                dateOfMonth = CronExpressionUtility.removeZeroPrefixFromTimeHrOrMin(dateOfMonth);
                fireTriggerOnMonth = CronExpressionUtility.removeZeroPrefixFromTimeHrOrMin(fireTriggerOnMonth);

                final Date utilDate = UtilityService.convertStringDateToDateFormat(startOnDate);
                final Calendar calendar = Calendar.getInstance();
                calendar.setTime(utilDate);
                final int date = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                if (date == Integer.parseInt(dateOfMonth)) {
                    repeatOnLastDayOfMonth = MainetConstants.QUARTZ_SCHEDULE.CRON_EXPR.REPEAT_LAST_DAY_OF_MONTH;
                }
            } else if ((entity.getHiddenJobFrequencyType() != null)
                    && (entity.getHiddenJobFrequencyType().equals(MainetConstants.QUARTZ_SCHEDULE.JobFrequency.YEARLY)
                            || entity.getHiddenJobFrequencyType()
                                    .equals(MainetConstants.QUARTZ_SCHEDULE.JobFrequency.ONE_TIME))) {

                startOnDate = entity.getStartOnDate_yearly();
                dates = startOnDate.split(MainetConstants.operator.FORWARD_SLACE);
                dateOfMonth = dates[0];
                month = dates[1];
                year = dates[2];
                monthName = UtilityService.getNameOfMonthFromNumericMonth(month);
                month = CronExpressionUtility.removeZeroPrefixFromTimeHrOrMin(month);
            }

        }

        hrMin = startTime.split(MainetConstants.operator.COLON);
        hr = hrMin[0];
        min = hrMin[1];

        hrTime = CronExpressionUtility.removeZeroPrefixFromTimeHrOrMin(hr);
        minTime = CronExpressionUtility.removeZeroPrefixFromTimeHrOrMin(min);

        if (entity.getHiddenJobFrequencyType() != null) {

            switch (entity.getHiddenJobFrequencyType()) {
            case MainetConstants.QUARTZ_SCHEDULE.JobFrequency.DAILY:
                cronExpression = CronExpressionUtility.cronExpressionForDailyJobFrequency(
                        MainetConstants.Common_Constant.ZERO_SEC,
                        minTime, hrTime, MainetConstants.QUARTZ_SCHEDULE.JobFrequency.REPEAT_DAILY);
                break;

            case MainetConstants.QUARTZ_SCHEDULE.JobFrequency.WEEKLY:
                weekDay = UtilityService.getNameOfDayFromUtilDateOrStringDate(null, startOnDate);
                cronExpression = CronExpressionUtility.cronExpressionForWeeklyJobFrequency(
                        MainetConstants.Common_Constant.ZERO_SEC,
                        minTime, hrTime, weekDay);
                break;

            case MainetConstants.QUARTZ_SCHEDULE.JobFrequency.MONTHLY:
                cronExpression = CronExpressionUtility.cronExpressionForMonthlyJobFrequency(
                        MainetConstants.Common_Constant.ZERO_SEC,
                        minTime, hrTime, fireTriggerOnMonth, dateOfMonth, repeatOnLastDayOfMonth);
                break;

            case MainetConstants.QUARTZ_SCHEDULE.JobFrequency.YEARLY:
                cronExpression = CronExpressionUtility.cronExpressionForYearlyJobFrequency(
                        MainetConstants.Common_Constant.ZERO_SEC,
                        minTime, hrTime, dateOfMonth, month);
                break;

            case MainetConstants.QUARTZ_SCHEDULE.JobFrequency.ONE_TIME:
                cronExpression = CronExpressionUtility.cronExpressionForOneTimeJobFrequency(
                        MainetConstants.Common_Constant.ZERO_SEC,
                        minTime, hrTime, month, monthName, year);
                break;

            case MainetConstants.QUARTZ_SCHEDULE.JobFrequency.QUARTERLY:
                cronExpression = CronExpressionUtility.cronExpressionForQuarterlyJobFrequency(
                        MainetConstants.Common_Constant.ZERO_SEC,
                        minTime, hrTime, fireTriggerOnMonth, dateOfMonth);
                break;

            case MainetConstants.QUARTZ_SCHEDULE.JobFrequency.HALF_YEARLY:
                cronExpression = CronExpressionUtility.cronExpressionForHalfYearlyJobFrequency(
                        MainetConstants.Common_Constant.ZERO_SEC,
                        minTime, hrTime, fireTriggerOnMonth, dateOfMonth);
                break;

            default:
                break;
            }

        }

        entity.setCronExpression(cronExpression);
        if ((model.getJobId() != null) && !model.getJobId().trim().equals(MainetConstants.BLANK)) {
            entity.setCpdIdBjo(Long.parseLong(model.getJobId()));
        }
        entity.setCjDate(currentDate);
        entity.setCjRepeat(MainetConstants.Common_Constant.YES);
        entity.updateAuditFields();

        return entity;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean validateProcedureOrFunction(final QuartzSchedulerMasterModel model)
            throws RuntimeException {

        final String procOrFunName = model.getEntity().getJobProcName();
        boolean isExist = false;
        final QuartzSchedulerMaster master = iQuartzSchedulerMasterDao.fetchQuartzMaster(procOrFunName,
                model.getUserSession().getOrganisation());

        if (master != null) {
            isExist = true;
        }

        return isExist;
    }

    @Override
    public List<LookUp> arrangeJobFrequencyInOrder(final List<LookUp> lookUps)
            throws RuntimeException {

        int count = 0;

        if (lookUps != null) {
            for (final LookUp lookUp : lookUps) {

                switch (lookUp.getLookUpCode()) {

                case MainetConstants.QUARTZ_SCHEDULE.JobFrequency.ONE_TIME:
                    Collections.swap(lookUps, MainetConstants.Common_Constant.INDEX.ZERO, count);
                    break;

                case MainetConstants.QUARTZ_SCHEDULE.JobFrequency.DAILY:
                    Collections.swap(lookUps, MainetConstants.Common_Constant.INDEX.ONE, count);
                    break;

                case MainetConstants.QUARTZ_SCHEDULE.JobFrequency.WEEKLY:
                    Collections.swap(lookUps, MainetConstants.Common_Constant.INDEX.TWO, count);
                    break;

                case MainetConstants.QUARTZ_SCHEDULE.JobFrequency.MONTHLY:
                    Collections.swap(lookUps, MainetConstants.Common_Constant.INDEX.THREE, count);
                    break;

                case MainetConstants.QUARTZ_SCHEDULE.JobFrequency.QUARTERLY:
                    Collections.swap(lookUps, MainetConstants.Common_Constant.INDEX.FOUR, count);
                    break;

                case MainetConstants.QUARTZ_SCHEDULE.JobFrequency.HALF_YEARLY:
                    Collections.swap(lookUps, MainetConstants.Common_Constant.INDEX.FIVE, count);
                    break;

                case MainetConstants.QUARTZ_SCHEDULE.JobFrequency.YEARLY:
                    Collections.swap(lookUps, MainetConstants.Common_Constant.INDEX.SIX, count);
                    break;

                default:
                    break;
                }

                count++;
            }
        }

        return lookUps;
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuartzSchedulerMaster> queryListOfJobs(final QuartzSchedulerMasterModel model, final Organisation orgId)
            throws RuntimeException {

        final List<DepartmentLookUp> lookUps = model.getDepartmentList();
        final List<LookUp> jobsFrequency = model.getJobRunFrequencyLookUp();
        long deptId = 0;
        String[] deptArray = null;
        String deptName = null;
        String deptCode = null;
        List<LookUp> availableJobs = null;

        if (lookUps != null) {
            for (final DepartmentLookUp lookUp : lookUps) {

                if (lookUp.getLookUpCode().equalsIgnoreCase(model.getDepartmentForQuartz())) {
                    deptId = lookUp.getLookUpId();
                    break;
                }
            }
        }

        final List<QuartzSchedulerMaster> list = iQuartzSchedulerMasterDao.queryListOfJobs(deptId, orgId);

        for (final QuartzSchedulerMaster quartzSchedulerMaster : list) {

            deptArray = findDepartmentName(lookUps, quartzSchedulerMaster.getDpDeptid());
            deptName = deptArray[0];
            deptCode = deptArray[1];
            quartzSchedulerMaster.setDepartmentName(deptName);
            availableJobs = findJobNamesByDepartment(deptCode);

            if (availableJobs != null) {
                for (final LookUp lookUp : availableJobs) {
                    if (lookUp.getLookUpId() == quartzSchedulerMaster.getCpdIdBjo()) {
                        quartzSchedulerMaster.setJobName(lookUp.getLookUpDesc());
                        break;
                    }

                }
            }

            if (jobsFrequency != null) {
                for (final LookUp lookUp : jobsFrequency) {
                    if ((lookUp != null) && (quartzSchedulerMaster.getCpdIdBfr() != null)) {
                        if (lookUp.getLookUpId() == quartzSchedulerMaster.getCpdIdBfr()) {
                            quartzSchedulerMaster.setJobFrequency(lookUp.getLookUpDesc());
                            break;
                        }
                    }
                }
            }

            if ((MainetConstants.Common_Constant.ACTIVE_FLAG).equals(quartzSchedulerMaster.getStatus())) {
                quartzSchedulerMaster.setJobStatus(MainetConstants.Common_Constant.ACTIVE);
            } else {
                quartzSchedulerMaster.setJobStatus(MainetConstants.Common_Constant.INACTIVE);
            }

        }

        return list;
    }

    private String[] findDepartmentName(final List<DepartmentLookUp> lookUps, final long deptId) {

        String deptName = null;
        String deptCode = null;
        final String[] deptArray = { deptName, deptCode };
        if (lookUps != null) {
            for (final DepartmentLookUp departmentLookUp : lookUps) {

                if (departmentLookUp.getLookUpId() == deptId) {
                    deptName = departmentLookUp.getLookUpDesc();
                    deptCode = departmentLookUp.getLookUpCode();
                    deptArray[0] = deptName;
                    deptArray[1] = deptCode;
                    break;
                }

            }
        }

        return deptArray;
    }

    @Override
    @Transactional(readOnly = true)
    public QuartzSchedulerMaster findQuartzMasterById(final long rowId, final Organisation orgId)
            throws RuntimeException {

        final QuartzSchedulerMaster master = iQuartzSchedulerMasterDao.findQuartzMasterById(rowId, orgId);

        return master;
    }

    @Override
    public QuartzSchedulerMaster initializeQuartzMasterDataForEditMode(
            final QuartzSchedulerMasterModel model, final QuartzSchedulerMaster entity) throws RuntimeException {

        final Date date = entity.getCjDate();
        final String jobDate = UtilityService.convertDateToDDMMYYYY(date);

        final String time = CronExpressionUtility.findTimeFromCronExpression(entity.getCronExpression());

        final List<LookUp> lookUps = model.getJobRunFrequencyLookUp();
        if (lookUps != null) {

            for (final LookUp lookUp : lookUps) {

                if (lookUp.getLookUpId() == entity.getCpdIdBfr()) {
                    if ((MainetConstants.QUARTZ_SCHEDULE.JobFrequency.DAILY).equals(lookUp.getLookUpCode())) {
                        entity.setStartAtTime_Daily(time);
                    } else if (MainetConstants.QUARTZ_SCHEDULE.JobFrequency.MONTHLY.equals(lookUp.getLookUpCode())
                            || MainetConstants.QUARTZ_SCHEDULE.JobFrequency.QUARTERLY.equals(lookUp.getLookUpCode())
                            || MainetConstants.QUARTZ_SCHEDULE.JobFrequency.HALF_YEARLY.equals(lookUp.getLookUpCode())
                            || MainetConstants.QUARTZ_SCHEDULE.JobFrequency.YEARLY.equals(lookUp.getLookUpCode())
                            || MainetConstants.QUARTZ_SCHEDULE.JobFrequency.ONE_TIME.equals(lookUp.getLookUpCode())) {
                        entity.setStartOnDate_yearly(jobDate);
                        entity.setStartOnDate_monthly(jobDate);
                        entity.setStartAtTime(time);
                    }
                    break;
                }

            }
        }

        if (entity != null) {
            for (final DepartmentLookUp lookUp : model.getDepartmentList()) {

                if (lookUp.getLookUpId() == entity.getDpDeptid()) {
                    entity.setDepartmentForQuartz(lookUp.getLookUpCode());
                    model.setDepartmentForQuartz(lookUp.getLookUpCode());
                    break;
                }
            }
            model.setJobId(Long.toString(entity.getJobId()));
        }

        return entity;
    }

}
