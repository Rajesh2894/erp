package com.abm.mainet.common.workflow.service;

import java.util.List;

import com.abm.mainet.quartz.domain.QuartzSchedulerMaster;

/**
 * 
 * @author Jeetendra.Pal
 *
 */
public interface IWorkFlowJobSchedulerService {

    /**
     * used to update status of objection task based on sla duration in workflow task table
     * @param runtimeBean
     * @param parameterList
     */
    void updateWorkflowObjectionTask(QuartzSchedulerMaster runtimeBean, List<Object> parameterList);

    /**
     * used to add all auto escalation task in task table from newly generated task in KIE
     * @param runtimeBean
     * @param parameterList
     */
    void updateWorkflowAutoEscalationTask(QuartzSchedulerMaster runtimeBean, List<Object> parameterList);

	void runOfflinechallanReconclitionJob(QuartzSchedulerMaster runtimeBean, List<Object> parameterList);

}
