package com.abm.mainet.quartz.dao;

import java.util.List;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.quartz.domain.QuartzSchedulerMaster;

public interface IQuartzSchedulerMasterDao {

    public List<QuartzSchedulerMaster> queryForJobsInfoToBegin() throws RuntimeException;

    public QuartzSchedulerMaster saveQuartzMasterJobsInfo(QuartzSchedulerMaster master) throws RuntimeException;

    public QuartzSchedulerMaster fetchQuartzMaster(String funcOrProcName, Organisation orgId) throws RuntimeException;

    public List<QuartzSchedulerMaster> queryListOfJobs(long deptId, Organisation orgId) throws RuntimeException;

    public QuartzSchedulerMaster findQuartzMasterById(long rowId, Organisation orgId) throws RuntimeException;

}
