package com.abm.mainet.quartz.service;

import java.text.ParseException;
import java.util.List;

import org.quartz.SchedulerException;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.quartz.domain.QuartzSchedulerMaster;
import com.abm.mainet.quartz.ui.model.QuartzSchedulerMasterModel;

/**
 *
 * @author Vivek.Kumar
 * @since 05-May-2015
 */
public interface IQuartzSchedulerMasterService {

    public List<LookUp> findJobNamesByDepartment(String deptCode);

    /**
     * first entry method to invoke Quartz Scheduler
     * @throws RuntimeException
     * @throws SchedulerException
     * @throws ClassNotFoundException
     * @throws LinkageError
     */
    public void invokeQuartzShceduler() throws RuntimeException, SchedulerException, ClassNotFoundException, LinkageError;

    public boolean processAndSaveQuartzMaster(QuartzSchedulerMasterModel model)
            throws RuntimeException, ParseException, ClassNotFoundException, SchedulerException, LinkageError;

    public boolean validateProcedureOrFunction(QuartzSchedulerMasterModel model) throws RuntimeException;

    public List<LookUp> arrangeJobFrequencyInOrder(List<LookUp> lookUps) throws RuntimeException;

    public List<QuartzSchedulerMaster> queryListOfJobs(QuartzSchedulerMasterModel model, Organisation orgId)
            throws RuntimeException;

    public QuartzSchedulerMaster findQuartzMasterById(long rowId, Organisation orgId) throws RuntimeException;

    public QuartzSchedulerMaster initializeQuartzMasterDataForEditMode(QuartzSchedulerMasterModel model,
            QuartzSchedulerMaster entity) throws RuntimeException;
    
    
}
