package com.abm.mainet.lqp.service;

import java.math.BigDecimal;

import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionResponse;

public interface ILegislativeWorkflowService {

    WorkflowTaskActionResponse makerCheckerWorkFlowLQPService(WorkflowTaskAction prepareWorkFlowTaskAction, Long workFlowId,
            String url, String workFlowFlag, String shortCode);

    WorkflowMas resolveServiceWorkflowType(Long orgId, Long deptId, Long serviceId, BigDecimal amount, Long sourceOfFund,
            Long codIdOperLevel1, Long codIdOperLevel2, Long codIdOperLevel3, Long codIdOperLevel4, Long codIdOperLevel5);
}
