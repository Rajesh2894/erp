package com.abm.mainet.care.service;

import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.care.domain.CareRequest;
import com.abm.mainet.care.utility.CareUtility;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.TaskAssignment;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionResponse;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;

@Service
public class CareWorkflowService implements ICareWorkflowService {

    @Autowired
    private IWorkflowExecutionService iWorkflowExecutionService;

    @Autowired
    private ServiceMasterService serviceMasterService;

    @Override
    public WorkflowProcessParameter prepareInitCareWorkflowProcessParameter(RequestDTO applicantDetailDto,
            CareRequest careRequest, WorkflowMas workflowType, WorkflowTaskAction workflowTaskAction) {

        ApplicationSession appSession = ApplicationSession.getInstance();
        WorkflowProcessParameter wpp = new WorkflowProcessParameter();
        wpp.setProcessName(MainetConstants.WorkFlow.Process.CARE);

        ApplicationMetadata applicationMetadata = new ApplicationMetadata();
        applicationMetadata.setApplicationId(careRequest.getApplicationId());
        applicationMetadata.setReferenceId(careRequest.getComplaintId());
        applicationMetadata.setOrgId(careRequest.getOrgId());
        applicationMetadata.setAppTypeFlag(careRequest.getApplnType());
        applicationMetadata.setIsCallCenterApplicable(MainetConstants.FlagY);
        // D#96749 SKDCL check here code running on KDMC or not

        try {
            if (!CareUtility.isCallCenterApplicable(careRequest.getOrgId())) {
                applicationMetadata.setIsCallCenterApplicable(MainetConstants.FlagN);
            }
        } catch (Exception e) {
            // log.info("Prefix CCA not found");
        }

        if (CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT, careRequest.getOrgId())) {
            applicationMetadata.setIsCallCenterApplicable(MainetConstants.FlagN);
        }

        applicationMetadata.setWorkflowId(workflowType.getWfId());

        Optional<LookUp> lookup = CommonMasterUtility
                .getLookUps(PrefixConstants.ComplaintPrefix.COMPLAINT_EXPIRY_DURATION_DAYS_PREFIX,
                        workflowType.getOrganisation())
                .stream().findFirst();
        if (lookup.isPresent()) {
            if (!lookup.get().getLookUpCode().equals(MainetConstants.Common_Constant.ZERO_SEC)) {
                Long slaInMs = TimeUnit.DAYS.toMillis(
                        Long.valueOf(lookup.get().getLookUpCode()));
                applicationMetadata.setApplicationExpiryDuration(String.valueOf(slaInMs));
            } else {
                Long defaultslaInMs = TimeUnit.DAYS.toMillis(
                        Long.valueOf(1));
                applicationMetadata.setApplicationExpiryDuration(String.valueOf(defaultslaInMs));
            }
        }
        String code = CommonMasterUtility
                .getNonHierarchicalLookUpObject(workflowType.getWorkflowMode(), workflowType.getOrganisation())
                .getLookUpCode();
        applicationMetadata.setIsAutoEscalate(code.equals(PrefixConstants.WORKFLOW.AUTO_ESCALATE));
        workflowTaskAction.setDateOfAction(new Date());

        // Task manager assignment is depends no LDAP integration his check added in
        // BRm/BPM layer
        TaskAssignment requesterTaskAssignment = new TaskAssignment();
        requesterTaskAssignment.setActorId(workflowTaskAction.getCreatedBy().toString());
        requesterTaskAssignment.addActorId(workflowTaskAction.getCreatedBy().toString());
        requesterTaskAssignment.setOrgId(applicantDetailDto.getOrgId());
        requesterTaskAssignment.setUrl(MainetConstants.ServiceCareCommon.GRIEVANCEDEPARTMENTRESUBMISSION);
        requesterTaskAssignment.setDeptId(workflowType.getDepartment().getDpDeptid());
        requesterTaskAssignment.setDeptName(workflowType.getDepartment().getDpDeptdesc());
        requesterTaskAssignment.setDeptNameReg(workflowType.getDepartment().getDpNameMar());
        requesterTaskAssignment.setServiceId(workflowType.getService().getSmServiceId());
        requesterTaskAssignment.setServiceName(workflowType.getService().getSmServiceName());
        requesterTaskAssignment.setServiceNameReg(workflowType.getService().getSmServiceNameMar());
        requesterTaskAssignment.setServiceEventId(-1l);
        String reqTaskname = MainetConstants.WorkFlow.EventLabels.INITIATOR;
        requesterTaskAssignment.setServiceEventName(appSession.getMessage(reqTaskname, reqTaskname,
                new Locale(MainetConstants.DEFAULT_LOCALE_STRING), (Object[]) null));
        requesterTaskAssignment.setServiceEventNameReg(appSession.getMessage(reqTaskname, reqTaskname,
                new Locale(MainetConstants.REGIONAL_LOCALE_STRING), (Object[]) null));

        wpp.setRequesterTaskAssignment(requesterTaskAssignment);
        wpp.setApplicationMetadata(applicationMetadata);
        wpp.setWorkflowTaskAction(workflowTaskAction);
        return wpp;
    }

    @Override
    public WorkflowProcessParameter prepareUpdateCareWorkflowProcessParameter(WorkflowTaskAction workflowTaskAction) {
        WorkflowProcessParameter wpp = new WorkflowProcessParameter();
        wpp.setProcessName(MainetConstants.WorkFlow.Process.CARE);
        wpp.setWorkflowTaskAction(workflowTaskAction);
        return wpp;
    }

    @Override
    public WorkflowProcessParameter prepareReopenCareWorkflowProcessParameter(WorkflowTaskAction workflowTaskAction) {
        WorkflowProcessParameter wpp = new WorkflowProcessParameter();
        wpp.setProcessName(MainetConstants.WorkFlow.Process.CARE);
        wpp.setWorkflowTaskAction(workflowTaskAction);
        return wpp;
    }

    @Override
    @Transactional
    public WorkflowTaskActionResponse initiateAndUpdateWorkFlowMC(WorkflowTaskAction prepareWorkFlowTaskAction, Long workFlowId,
            String url, String workFlowFlag, String shortCode) {
        WorkflowTaskActionResponse response = null;
        WorkflowProcessParameter processParameter = new WorkflowProcessParameter();
        try {

            ServiceMaster serviceMast = serviceMasterService.getServiceMasterByShortCode(shortCode,
                    prepareWorkFlowTaskAction.getOrgId());
            String processName = serviceMasterService.getProcessName(serviceMast.getSmServiceId(),
                    prepareWorkFlowTaskAction.getOrgId());
            processParameter.setProcessName(processName);

            ApplicationMetadata applicationMetadata = null;
            applicationMetadata = new ApplicationMetadata();
            applicationMetadata.setReferenceId(prepareWorkFlowTaskAction.getReferenceId());
            applicationMetadata.setOrgId(prepareWorkFlowTaskAction.getOrgId());
            applicationMetadata.setWorkflowId(workFlowId);
            applicationMetadata.setPaymentMode(prepareWorkFlowTaskAction.getPaymentMode());
            applicationMetadata.setIsCheckListApplicable(false);

            // Task manager assignment is depends no LDAP integration his check added in
            // BRm/BPM layer
            TaskAssignment requesterTaskAssignment = new TaskAssignment();
            if (prepareWorkFlowTaskAction.getEmpId() != null) {
                requesterTaskAssignment.setActorId(prepareWorkFlowTaskAction.getEmpId().toString());
            }
            if (prepareWorkFlowTaskAction.getEmpId() != null) {
                requesterTaskAssignment.addActorId(prepareWorkFlowTaskAction.getEmpId().toString());
            }
            requesterTaskAssignment.setOrgId(prepareWorkFlowTaskAction.getOrgId());
            requesterTaskAssignment.setUrl(url);

            // Reviewer TaskAssignment has been removed from here,because it will be fetch
            // on the fly by BPM to Service callback.

            processParameter.setRequesterTaskAssignment(requesterTaskAssignment);
            processParameter.setApplicationMetadata(applicationMetadata);

            processParameter.setWorkflowTaskAction(prepareWorkFlowTaskAction);

            if (workFlowFlag.equals(MainetConstants.FlagU)) {
                response = iWorkflowExecutionService.updateWorkflow(processParameter);
            } else {
                response = iWorkflowExecutionService.initiateWorkflow(processParameter);
            }

        } catch (Exception e) {
            throw new FrameworkException("Exception occured while call workflow action execution", e);
        }

        return response;
    }

}
