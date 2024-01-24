package com.abm.mainet.lqp.service;

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.workflow.dao.IWorkflowTypeDAO;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.TaskAssignment;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionResponse;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.lqp.dao.ILegislativeDao;

@Service
public class LegislativeWorkflowServiceImpl implements ILegislativeWorkflowService {

    private static final Logger LOGGER = Logger.getLogger(LegislativeWorkflowServiceImpl.class);

    @Autowired
    private IWorkflowExecutionService iWorkflowExecutionService;

    @Autowired
    private ServiceMasterService serviceMasterService;

    @Autowired
    private IWorkflowTypeDAO workflowTypeDAO;

    @Autowired
    private ILegislativeDao legislativeDao;

    @Override
    public WorkflowTaskActionResponse makerCheckerWorkFlowLQPService(WorkflowTaskAction prepareWorkFlowTaskAction, Long workFlowId,
            String url, String workFlowFlag, String shortCode) {

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
            requesterTaskAssignment.setActorId(prepareWorkFlowTaskAction.getEmpId().toString());
            requesterTaskAssignment.addActorId(prepareWorkFlowTaskAction.getEmpId().toString());
            requesterTaskAssignment.setOrgId(prepareWorkFlowTaskAction.getOrgId());
            requesterTaskAssignment.setUrl(url);

            // Reviewer TaskAssignment has been removed from here,because it will be fetch
            // on the fly by BPM to Service callback.

            processParameter.setRequesterTaskAssignment(requesterTaskAssignment);
            processParameter.setApplicationMetadata(applicationMetadata);

            processParameter.setWorkflowTaskAction(prepareWorkFlowTaskAction);
            WorkflowTaskActionResponse response = null;
            if (workFlowFlag.equals(MainetConstants.FlagU)) {
                response = iWorkflowExecutionService.updateWorkflow(processParameter);
            } else {
                response = iWorkflowExecutionService.initiateWorkflow(processParameter);
            }
            return response;
        } catch (Exception e) {
            LOGGER.error("Exception occured while call workflow action execution: ", e);
            throw new FrameworkException("Exception occur while saving query answer ", e);
        }
    }

    @Override
    public WorkflowMas resolveServiceWorkflowType(Long orgId, Long deptId, Long serviceId, BigDecimal amount,
            Long sourceOfFund, Long codIdOperLevel1, Long codIdOperLevel2, Long codIdOperLevel3,
            Long codIdOperLevel4, Long codIdOperLevel5) {
        WorkflowMas workflowType = null;

        if (orgId != null && deptId != null && serviceId != null) {
            workflowType = workflowTypeDAO.getServiceWorkFlowForAllWardZone(orgId, deptId, serviceId, amount,
                    sourceOfFund);
        }

        // Search for work-flow defined for specific ward and zone by OGR, DEPARTMENT,
        // SERVICE, AMOUNT,SOURCEOFFUND
        if (workflowType == null) {
            if (orgId != null && deptId != null && serviceId != null && codIdOperLevel1 != null) {
                workflowType = legislativeDao.getServiceWorkFlowForWardZone(orgId, deptId, serviceId, amount, sourceOfFund,
                        codIdOperLevel1, codIdOperLevel2, codIdOperLevel3, codIdOperLevel4, codIdOperLevel5);

            }
        }
        // check All option present in workflow
        if (workflowType == null) {
            // by using deptId ,serviceId,orgId,wt.type = 'N'
            WorkflowMas workflowTypeAll = legislativeDao.getServiceWorkFlowForWardZone(orgId, deptId, serviceId, amount,
                    sourceOfFund, codIdOperLevel1, null, null, null, null);
            if (workflowTypeAll != null) {
                workflowType = checkAllOptionSelectedAtAnyLevel(workflowTypeAll, orgId, codIdOperLevel1, codIdOperLevel2,
                        codIdOperLevel3, codIdOperLevel4, codIdOperLevel5);
            }
        }
        /*#D107424*/
        /*if (workflowType == null) {
            throw new FrameworkException("Workflow Not Found");
        }*/
        return workflowType;
    }

    // common method for check All option selected or not at the time of workflow create ward-zone wise
    public WorkflowMas checkAllOptionSelectedAtAnyLevel(WorkflowMas workflowTypeAll, final Long codIdOperLevel1,
            final Long codIdOperLevel2, final Long codIdOperLevel3,
            final Long codIdOperLevel4, final Long codIdOperLevel5, Long orgId) {

        if (workflowTypeAll.getCodIdOperLevel2() != null && workflowTypeAll.getCodIdOperLevel2().equals(codIdOperLevel2)) {
            // go for 3rd level
            if (workflowTypeAll.getCodIdOperLevel3() != null && workflowTypeAll.getCodIdOperLevel3().equals(codIdOperLevel3)) {
                // go for 4th level
                if (workflowTypeAll.getCodIdOperLevel4() != null
                        && workflowTypeAll.getCodIdOperLevel4().equals(codIdOperLevel4)) {
                    // go for 5th level
                    if (workflowTypeAll.getCodIdOperLevel5() != null
                            && !workflowTypeAll.getCodIdOperLevel5().equals(codIdOperLevel5)) {
                        // check for All option select
                        if (!checkAllOptionPresent(workflowTypeAll.getCodIdOperLevel5(), orgId)) {
                            workflowTypeAll = null;
                        }
                    }
                } else if (!checkAllOptionPresent(workflowTypeAll.getCodIdOperLevel4(), orgId)) {
                    workflowTypeAll = null;
                }
            } else if (!checkAllOptionPresent(workflowTypeAll.getCodIdOperLevel3(), orgId)) {
                // check for All option select
                workflowTypeAll = null;
            }
        } else if (!checkAllOptionPresent(workflowTypeAll.getCodIdOperLevel2(), orgId)) {
            workflowTypeAll = null;
        }

        return workflowTypeAll;

    }

    public Boolean checkAllOptionPresent(Long level, Long orgId) {
        LookUp lookUp2 = CommonMasterUtility.getHierarchicalLookUp(level, orgId);
        return (lookUp2 != null && lookUp2.getLookUpDesc().equalsIgnoreCase(MainetConstants.All) ? true : false);
    }

}
