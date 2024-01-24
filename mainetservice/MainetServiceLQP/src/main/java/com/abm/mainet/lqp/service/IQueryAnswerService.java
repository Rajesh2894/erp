package com.abm.mainet.lqp.service;

import java.util.List;

import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.lqp.dto.QueryAnswerMasterDto;

public interface IQueryAnswerService {

    boolean saveAnswerAndUpdateWorkflow(WorkflowTaskAction workflowTaskAction, QueryAnswerMasterDto queryAnswerMasterDto,
            List<DocumentDetailsVO> attachments, RequestDTO requestDTO);

    QueryAnswerMasterDto getQuestionDataByQueId(Long questionRegId);

}
