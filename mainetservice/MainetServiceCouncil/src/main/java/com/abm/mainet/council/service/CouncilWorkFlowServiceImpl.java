package com.abm.mainet.council.service;

import java.util.Locale;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.TaskAssignment;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;

/**
 * @author aarti.paan
 * @since 22 May 2019
 */

@Service
public class CouncilWorkFlowServiceImpl implements CouncilWorkFlowService {

    @Override
    public String initiateWorkFlowCouncilService(WorkflowTaskAction workflowActionDto, WorkflowMas workFlowMas, String url,
            String workFlowFlag) {

        try {

            WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();

            workflowProcessParameter.setProcessName(MainetConstants.WorkFlow.Process.MAKER_CHECKER);
            ApplicationMetadata applicationMetadata = new ApplicationMetadata();

            applicationMetadata.setReferenceId(workflowActionDto.getReferenceId());
            applicationMetadata.setOrgId(workflowActionDto.getOrgId());
            applicationMetadata.setWorkflowId(workFlowMas.getWfId());
            applicationMetadata.setPaymentMode(workflowActionDto.getPaymentMode());
            applicationMetadata.setIsCheckListApplicable(false);

            TaskAssignment assignment = new TaskAssignment();

            ApplicationSession appSession = ApplicationSession.getInstance();

            assignment.setActorId(workflowActionDto.getEmpId().toString());
            assignment.addActorId(workflowActionDto.getEmpId().toString());
            assignment.setOrgId(workflowActionDto.getOrgId());
            assignment.setServiceEventId(-1L);
            String reqTaskname = MainetConstants.WorkFlow.EventLabels.INITIATOR;
            assignment.setServiceEventName(appSession
                    .getMessage(reqTaskname, reqTaskname, new Locale(MainetConstants.DEFAULT_LOCALE_STRING), (Object[]) null));

            assignment.setServiceEventNameReg(appSession
                    .getMessage(reqTaskname, reqTaskname, new Locale(MainetConstants.REGIONAL_LOCALE_STRING), (Object[]) null));

            assignment.setDeptId(workFlowMas.getDepartment().getDpDeptid());
            assignment.setDeptName(workFlowMas.getDepartment().getDpDeptdesc());
            assignment.setDeptNameReg(workFlowMas.getDepartment().getDpNameMar());
            assignment.setServiceId(workFlowMas.getService().getSmServiceId());
            assignment.setServiceName(workFlowMas.getService().getSmServiceNameMar());
            assignment.setServiceEventNameReg(workFlowMas.getService().getSmServiceNameMar());
            assignment.setUrl(url);

            workflowProcessParameter.setRequesterTaskAssignment(assignment);
            workflowProcessParameter.setApplicationMetadata(applicationMetadata);
            workflowProcessParameter.setWorkflowTaskAction(workflowActionDto);

            ApplicationContextProvider.getApplicationContext().getBean(IWorkflowExecutionService.class)
                    .initiateWorkflow(workflowProcessParameter);

        } catch (Exception exception) {
            throw new FrameworkException("Exception  Occured when Initiate Workflow methods", exception);
        }
        return null;
    }

    @Override
    @Transactional
    public String updateWorkFlowProposalService(WorkflowTaskAction workflowTaskAction) {
        WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();
        workflowProcessParameter.setProcessName(MainetConstants.WorkFlow.Process.MAKER_CHECKER);
        workflowProcessParameter.setWorkflowTaskAction(workflowTaskAction);
        try {

            ApplicationContextProvider.getApplicationContext().getBean(IWorkflowExecutionService.class)
                    .updateWorkflow(workflowProcessParameter);
        } catch (Exception exception) {
            throw new FrameworkException("Exception  Occured when Update Workflow methods", exception);
        }
        return null;
    }

}
