package com.abm.mainet.lqp.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionResponse;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.lqp.domain.QueryAnswerMaster;
import com.abm.mainet.lqp.domain.QueryAnswerMasterHistory;
import com.abm.mainet.lqp.domain.QueryRegistrationMaster;
import com.abm.mainet.lqp.domain.QueryRegistrationMasterHistory;
import com.abm.mainet.lqp.dto.QueryAnswerMasterDto;
import com.abm.mainet.lqp.dto.QueryRegistrationMasterDto;
import com.abm.mainet.lqp.repository.QueryAnswerRepository;
import com.abm.mainet.lqp.repository.QueryRegistrationRepository;

@Service
public class QueryAnswerServiceImpl implements IQueryAnswerService {
    private static final Logger LOGGER = Logger.getLogger(QueryAnswerServiceImpl.class);

    @Autowired
    ILegislativeWorkflowService legislativeWorkflowService;

    @Autowired
    private IFileUploadService fileUpload;

    @Autowired
    private QueryAnswerRepository answerRepository;

    @Autowired
    private QueryRegistrationRepository registrationRepository;

    @Autowired
    AuditService auditService;

    @Override
    @Transactional
    public boolean saveAnswerAndUpdateWorkflow(WorkflowTaskAction workflowTaskAction, QueryAnswerMasterDto queryAnswerMasterDto,
            List<DocumentDetailsVO> attachments, RequestDTO requestDTO) {
        // file upload and insert in answer table and history
        // update in query TB_LQP_QUERY_REGISTRATION and insert in TB_LQP_QUERY_REGISTRATION_HIST
        // fileUpload.doFileUpload(attachments, requestDTO);
        fileUpload.doMasterFileUpload(attachments, requestDTO);
        QueryRegistrationMaster queryRegMas = new QueryRegistrationMaster();
        queryRegMas.setQuestionRegId(queryAnswerMasterDto.getQueryRegistrationMasterDto().getQuestionRegId());
        QueryAnswerMaster queryAnswerMaster = new QueryAnswerMaster();
        BeanUtils.copyProperties(queryAnswerMasterDto, queryAnswerMaster);
        queryAnswerMaster.setQueryRegistrationMaster(queryRegMas);
        try {
            boolean lastApproval = ApplicationContextProvider.getApplicationContext().getBean(IWorkFlowTypeService.class)
                    .isLastTaskInCheckerTaskList(workflowTaskAction.getTaskId());
            // update column (remark) in TB_LQP_QUERY_REGISTRATION
            BeanUtils.copyProperties(queryAnswerMasterDto.getQueryRegistrationMasterDto(), queryRegMas);
            queryRegMas.setUpdatedBy(queryAnswerMasterDto.getUpdatedBy());
            queryRegMas.setUpdatedDate(new Date());
            queryRegMas.setLgIpMacUpd(queryAnswerMasterDto.getLgIpMacUpd());
            QueryRegistrationMasterHistory history = new QueryRegistrationMasterHistory();
            queryRegMas.setStatus(workflowTaskAction.getDecision());
            history.sethStatus(MainetConstants.InsertMode.UPDATE.getStatus());
            if (workflowTaskAction.getDecision().equals(MainetConstants.WorkFlow.Decision.REJECTED)) {
                // update in TB_LQP_QUERY_REGISTRATION as set status='CONCLUDED'
                queryRegMas.setStatus(MainetConstants.LQP.STATUS.CONCLUDED);
                history.setStatus(workflowTaskAction.getDecision());
                registrationRepository.save(queryRegMas);

            } else if (workflowTaskAction.getDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)) {
                if (lastApproval) {
                    // update in TB_LQP_QUERY_REGISTRATION as set status='CONCLUDED'
                    queryRegMas.setStatus(MainetConstants.LQP.STATUS.CONCLUDED);
                    registrationRepository.save(queryRegMas);
                }
            }
            // insert in history table TB_LQP_QUERY_REGISTRATION_HIST
            auditService.createHistory(queryRegMas, history);

            queryAnswerMaster.setQueryRegistrationMaster(queryRegMas);

            if (queryAnswerMaster.getAnswerRegId() != null) {
                // update
                queryAnswerMaster = answerRepository.save(queryAnswerMaster);
                QueryAnswerMasterHistory answeHistory = new QueryAnswerMasterHistory();
                answeHistory.sethStatus(MainetConstants.InsertMode.UPDATE.getStatus());
                auditService.createHistory(queryAnswerMaster, answeHistory);
            } else {
                // insert
                queryAnswerMaster = answerRepository.save(queryAnswerMaster);
                // insert in history table
                QueryAnswerMasterHistory answeHistory = new QueryAnswerMasterHistory();
                answeHistory.sethStatus(MainetConstants.InsertMode.ADD.getStatus());
                auditService.createHistory(queryAnswerMaster, answeHistory);
            }

            // update Workflow based on task id and flag U
            WorkflowTaskActionResponse response = legislativeWorkflowService.makerCheckerWorkFlowLQPService(workflowTaskAction,
                    workflowTaskAction.getTaskId(), MainetConstants.LQP.LQA_URL, MainetConstants.FlagU,
                    MainetConstants.LQP.SERVICE_CODE.LQE);
            if (response != null && workflowTaskAction.getDecision().equalsIgnoreCase(MainetConstants.WorkFlow.Decision.APPROVED)
                    && !response.getIsProcessAlive()) {

            } else if (response != null && !response.getIsProcessAlive()
                    && workflowTaskAction.getDecision().equalsIgnoreCase(MainetConstants.WorkFlow.Decision.REJECTED)) {

            } else if (response != null && response.getIsProcessAlive()) {
                return true;
            }

        } catch (Exception exception) {
            LOGGER.error("Exception occur while saving query answer ", exception);
            throw new FrameworkException("Exception occur while saving query answer ", exception);
        }

        return false;
    }

    @Override
    public QueryAnswerMasterDto getQuestionDataByQueId(Long questionRegId) {
        QueryAnswerMasterDto dto = new QueryAnswerMasterDto();
        QueryAnswerMaster entity = answerRepository.findByQueryRegistrationMaster_questionRegId(questionRegId);
        if (entity != null) {

            QueryRegistrationMaster regMas = entity.getQueryRegistrationMaster();
            QueryRegistrationMasterDto regDto = new QueryRegistrationMasterDto();
            BeanUtils.copyProperties(regMas, regDto);
            dto.setQueryRegistrationMasterDto(regDto);
            BeanUtils.copyProperties(entity, dto);

        }
        return dto;
    }
}
