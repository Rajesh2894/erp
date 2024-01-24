package com.abm.mainet.lqp.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.lqp.domain.QueryRegistrationMaster;
import com.abm.mainet.lqp.dto.QueryRegistrationMasterDto;

public interface IQueryRegistrationService {

    public List<QueryRegistrationMasterDto> searchQueryRegisterMasterData(Long deptId, Long questionTypeId, String questionId,
            Date fromDate, Date toDate, Long orgId);

    public List<QueryRegistrationMasterDto> fetchQueryRegisterMasterDataByOrgId(Long orgId);

    Boolean queryRegistrationAndInitiateWorkflow(QueryRegistrationMasterDto registrationMasterDto,
            WorkflowTaskAction workflowActionDto, String sendBackflag, Long orgId, WorkflowMas workFlowMas,
            List<DocumentDetailsVO> attachmentList,Long deleteFileId);

    public QueryRegistrationMasterDto getQueryRegisterMasterDataByQuestId(String qustnId);

    public QueryRegistrationMasterDto getQueryRegisterMasterDataByQuestRegId(Long qustnRegId);
    
    public List<Employee> fetchEmpDetailList(String referenceId,Long orgId);
    
    public void uploadDocument(List<DocumentDetailsVO> attachmentList, Long deleteFileId,QueryRegistrationMaster queryRegistrationMaster);

}
