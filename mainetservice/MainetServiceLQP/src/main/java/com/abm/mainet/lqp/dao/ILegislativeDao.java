package com.abm.mainet.lqp.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.lqp.domain.QueryRegistrationMaster;

public interface ILegislativeDao {
    public List<QueryRegistrationMaster> searchQueryRegisterMasterData(Long deptId, Long questionTypeId, String questionId,
            Date fromDate, Date toDate, Long orgId);

    public WorkflowMas getServiceWorkFlowForWardZone(Long orgId, Long deptId, Long serviceId, BigDecimal amount,
            Long sourceOfFund, Long codIdOperLevel1, Long codIdOperLevel2, Long codIdOperLevel3, Long codIdOperLevel4,
            Long codIdOperLevel5);

}
