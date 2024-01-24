package com.abm.mainet.workManagement.service;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.TaskAssignment;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.common.workflow.service.IWorkflowTaskService;
import com.abm.mainet.workManagement.dto.MeasurementBookMasterDto;

/**
 * @author vishwajeet.kumar
 * @since 8 May 2018
 */
@Service
public class WorksWorkFlowServiceImpl implements WorksWorkFlowService {

    @Autowired
    private ServiceMasterService serviceMaster;
    
    @Autowired
	private IWorkflowTaskService iWorkflowTaskService;

    /**
     * Method is used for initiate work flow
     * @param workflowActionDto
     * @param workFlowId
     * @param url
     * @param workFlowFlag
     * @return String
     */

    @Override
    @Transactional
    public String initiateWorkFlowWorksService(WorkflowTaskAction workflowActionDto, WorkflowMas workFlowMas, String url,
            String workFlowFlag) {
        String status = MainetConstants.FAILURE_MSG;
        String processName = serviceMaster.getProcessName(workFlowMas.getService().getSmServiceId(),
                workflowActionDto.getOrgId());
        if (processName != null) {
            try {
                WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();
                // workflowProcessParameter.setProcessName(MainetConstants.WorksManagement.WORKS_MANAGEMENT_PROCESS);
                workflowProcessParameter.setProcessName(processName);
                ApplicationMetadata applicationMetadata = new ApplicationMetadata();

                applicationMetadata.setReferenceId(workflowActionDto.getReferenceId());
                applicationMetadata.setOrgId(workflowActionDto.getOrgId());
                applicationMetadata.setWorkflowId(workFlowMas.getWfId());
                applicationMetadata.setPaymentMode(workflowActionDto.getPaymentMode());
                applicationMetadata.setIsCheckListApplicable(false);

                /*
                 * Task manager assignment is depends no LDAP integration his check added in BRm/BPM layer
                 */
                TaskAssignment assignment = new TaskAssignment();

                ApplicationSession appSession = ApplicationSession.getInstance();

                assignment.setActorId(workflowActionDto.getEmpId().toString());
                assignment.addActorId(workflowActionDto.getEmpId().toString());
                assignment.setOrgId(workflowActionDto.getOrgId());
                assignment.setServiceEventId(-1L);
                String reqTaskname = MainetConstants.WorkFlow.EventLabels.INITIATOR;
                assignment.setServiceEventName(appSession
                        .getMessage(reqTaskname, reqTaskname, new Locale(MainetConstants.DEFAULT_LOCALE_STRING),
                                (Object[]) null));

                assignment.setServiceEventNameReg(appSession
                        .getMessage(reqTaskname, reqTaskname, new Locale(MainetConstants.REGIONAL_LOCALE_STRING),
                                (Object[]) null));

                assignment.setDeptId(workFlowMas.getDepartment().getDpDeptid());
                assignment.setDeptName(workFlowMas.getDepartment().getDpDeptdesc());
                assignment.setDeptNameReg(workFlowMas.getDepartment().getDpNameMar());
                assignment.setServiceId(workFlowMas.getService().getSmServiceId());
                assignment.setServiceName(workFlowMas.getService().getSmServiceName());
                assignment.setServiceNameReg(workFlowMas.getService().getSmServiceNameMar());
                assignment.setUrl(url);

                /*
                 * Reviewer TaskAssignment has been removed from here,because it will be fetch on the fly by BPM to Service
                 * callback.
                 */
                //118940  setter has been change 
                workflowProcessParameter.setRequesterTaskAssignment(assignment);
                workflowProcessParameter.setApplicationMetadata(applicationMetadata);
                workflowProcessParameter.setWorkflowTaskAction(workflowActionDto);

                ApplicationContextProvider.getApplicationContext().getBean(IWorkflowExecutionService.class)
                        .initiateWorkflow(workflowProcessParameter);
                status = MainetConstants.SUCCESS_MSG;
            } catch (Exception exception) {
                throw new FrameworkException("Exception  Occured when Initiate Workflow methods", exception);
            }
        }
        return status;
    }

    /**
     * Method Is used for Update Work flow
     * @param workflowTaskAction
     * @return string
     */
    @Override
    @Transactional
    public String updateWorkFlowWorksService(WorkflowTaskAction workflowTaskAction) {
        WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();
        workflowProcessParameter.setProcessName(MainetConstants.WorksManagement.WORKS_MANAGEMENT_PROCESS);
        workflowProcessParameter.setWorkflowTaskAction(workflowTaskAction);
        try {

            ApplicationContextProvider.getApplicationContext().getBean(IWorkflowExecutionService.class)
                    .updateWorkflow(workflowProcessParameter);
        } catch (Exception exception) {
            throw new FrameworkException("Exception  Occured when Update Workflow methods", exception);
        }
        return null;
    }

    @Override
    @Transactional
    public String updateWorkFlowWorksService(WorkflowTaskAction workflowActionDto, Long smServiceId, Long parentOrgId) {
        String processName = serviceMaster.getProcessName(smServiceId,
                parentOrgId);
        Long level=null;
        level=iWorkflowTaskService.findByTaskId(workflowActionDto.getTaskId()).getCurentCheckerLevel();
        MeasurementBookMasterDto measurementBookMasterDto = new MeasurementBookMasterDto();
        if(level!=null) {
        	measurementBookMasterDto.setLevelCheck(level);
        }
       
        if(measurementBookMasterDto.getLevelCheck()!=null && measurementBookMasterDto.getLevelCheck()>1)
        {
        	workflowActionDto.setSendBackToGroup((measurementBookMasterDto.getLevelCheck().intValue()-1));
        	workflowActionDto.setSendBackToLevel((measurementBookMasterDto.getLevelCheck().intValue()-1));
        }else {
        	workflowActionDto.setSendBackToGroup(0);
        	workflowActionDto.setSendBackToLevel(0);
        }
       
        WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();
        workflowProcessParameter.setProcessName(processName);
        
        workflowProcessParameter.setWorkflowTaskAction(workflowActionDto);
        try {

            ApplicationContextProvider.getApplicationContext().getBean(IWorkflowExecutionService.class)
                    .updateWorkflow(workflowProcessParameter);
        } catch (Exception exception) {
            throw new FrameworkException("Exception  Occured when Update Workflow methods", exception);
        }
        return null;
    }

}
