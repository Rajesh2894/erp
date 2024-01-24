package com.abm.mainet.bpm.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.bpm.common.dto.LookUp;
import com.abm.mainet.bpm.common.dto.TaskSearchRequest;
import com.abm.mainet.bpm.common.dto.WorkflowTaskActionWithDocs;
import com.abm.mainet.bpm.domain.TaskDetailView;
import com.abm.mainet.bpm.repository.TaskDetailsRepository;
import com.abm.mainet.bpm.repository.WorkflowTaskRepository;
import com.abm.mainet.constant.MainetConstants;

/**
 * 
 * @author Jeetendra.Pal
 *
 */
@Service
public class WorkflowActionServiceImpl implements IWorkflowActionService {

    @Autowired
    private TaskDetailsRepository taskDetailsRepository;

    @Autowired
    private WorkflowTaskRepository workflowRepository;
    @Override
    @Transactional(readOnly = true)
    public List<WorkflowTaskActionWithDocs> findByApplicationIdWithDetails(TaskSearchRequest ts) {

        final List<WorkflowTaskActionWithDocs> actions = new ArrayList<>();
        List<Object[]> taskDetails = new ArrayList<>();

        if ((ts.getApplicationId() == null) && (ts.getReferenceId() == null || ts.getReferenceId().isEmpty()))
            return actions;

        if (ts.getWorkFlowRequestId() == null) {
        	/*Calling separate method for application Id Or reference Id - Told By Rajesh Sir*/
        	if(ts.getApplicationId()!=null)
        		taskDetails = workflowRepository.findByApplicationIdWithDetails(ts.getApplicationId());
        	else
        		taskDetails = workflowRepository.findByReferenceIdWithDetails(ts.getReferenceId());
        }else{
        	/*Calling separate method for application Id Or reference Id - Told By Rajesh Sir*/
        	if(ts.getApplicationId()!=null)
        		taskDetails = workflowRepository.findByApplicationIdWithDetails(ts.getApplicationId(),ts.getWorkFlowRequestId());
        	else
        		taskDetails = workflowRepository.findByReferenceIdWithDetails(ts.getReferenceId(),ts.getWorkFlowRequestId());
		}

        for (Object[] obj : taskDetails) {
            final WorkflowTaskActionWithDocs action = new WorkflowTaskActionWithDocs();
            if(obj[1]!=null)
            	action.setId(Long.parseLong(obj[1].toString()));
            action.setApplicationId((obj[0] != null) ? Long.parseLong(obj[0].toString()) : null);
            
            if(ts.getLangId() == MainetConstants.Lang.REG) {
            	if (obj[5]!=null)
            		action.setDeptName( (String) obj[5]);
            }else {
            	if (obj[4]!=null)
            		action.setDeptName( (String) obj[4]);
            }
            if(obj[18]!=null)
            	action.setDecision( (String) obj[18]);
            if(obj[20]!=null)
            	action.setComments( (String) obj[20]);
            if (action.getDecision().equalsIgnoreCase(MainetConstants.WorkFlow.Decision.ESCALATED.getValue()))
                action.setComments(MainetConstants.EntityFields.ESCALATED_REMARK);
            if(obj[2]!=null)
            	action.setOrgId((obj[2] != null) ? Long.parseLong(obj[2].toString()) : null);
            if(obj[19]!=null)
            	action.setEmpId((obj[19] != null) ? Long.parseLong(obj[19].toString()) : null);
            if(obj[22]!=null)
            	action.setEmpType((obj[22] != null) ? Long.parseLong(obj[22].toString()) : null); 
            if(obj[12]!=null)
            	action.setTaskId((obj[12] != null) ? Long.parseLong(obj[12].toString()) : null);
            if(obj[13]!=null)
            	action.setTaskName( (String) obj[13]);
            if(obj[23]!=null)
            	action.setDateOfAction( ( java.sql.Timestamp) obj[23]); 
            if(obj[24]!=null)
            	action.setCreatedDate( ( java.sql.Timestamp) obj[24]);
            if(obj[25]!=null)
            	action.setCreatedBy((obj[25] != null) ? Long.parseLong(obj[25].toString()) : null);
            if(obj[26]!=null)
            	action.setModifiedDate(( java.sql.Timestamp) obj[26]);
            action.setModifiedBy( (obj[27] != null) ? Long.parseLong(obj[27].toString()) : null);
            final String fn = MainetConstants.OPERATOR.EMPTY; 
            final String mn = MainetConstants.OPERATOR.EMPTY;
            final String ln = MainetConstants.OPERATOR.EMPTY;
            final String empName = (fn + MainetConstants.Common.WHITE_SPACE + mn + MainetConstants.Common.WHITE_SPACE + ln)
                    .trim();
            action.setEmpName((empName.isEmpty()) ? null : empName);
            action.setEmpEmail(MainetConstants.OPERATOR.EMPTY);
            action.setEmpGroupDescEng(MainetConstants.OPERATOR.EMPTY);
            action.setEmpGroupDescReg(MainetConstants.OPERATOR.EMPTY);
            if (ts.getLangId() == MainetConstants.Lang.REG)
                action.setEmpGroupDescEng(MainetConstants.OPERATOR.EMPTY);
            if(obj[21]!=null)
            	action.setReferenceId((String) obj[21]);
            if(obj[28]!=null)
            	prepareAttachmentLookUp((String) obj[28], action);
            if(obj[9]!=null)
            	action.setEventId((obj[9] != null) ? Long.parseLong(obj[9].toString()) : null);
            if(obj[29]!=null)
            	action.setEmpLoginName((obj[29] != null ? (String) obj[29] : null));
            if(obj[30]!=null)
            	action.setApplicantName((obj[30] != null ? (String) obj[30] : null));
            	
            actions.add(action);
        }

        return actions;
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkflowTaskActionWithDocs> findPendingActionByUuid(TaskSearchRequest ts) {
        final List<WorkflowTaskActionWithDocs> pendingActions = new LinkedList<>();

        List<TaskDetailView> taskDetails = taskDetailsRepository.findPendingActionByUuid(ts.getApplicationId(),
                ts.getReferenceId());

        taskDetails.forEach(obj -> {
            WorkflowTaskActionWithDocs workflowTaskActionWithDocs = new WorkflowTaskActionWithDocs();
            workflowTaskActionWithDocs.setApplicationId(obj.getApplicationId());
            workflowTaskActionWithDocs.setReferenceId(obj.getReferenceId());
            workflowTaskActionWithDocs.setCurrentEscalationLevel(obj.getCurrentEscalationLevel());
            workflowTaskActionWithDocs.setDateOfAction(obj.getDateOfAction());
            workflowTaskActionWithDocs.setEmpId(obj.getEmpId());
            final String fn = (obj.getEmpName() != null) ? obj.getEmpName() : MainetConstants.OPERATOR.EMPTY;
            final String mn = (obj.getEmpMName() != null) ? obj.getEmpMName() : MainetConstants.OPERATOR.EMPTY;
            final String ln = (obj.getEmpLName() != null) ? obj.getEmpLName() : MainetConstants.OPERATOR.EMPTY;
            final String empName = (fn + MainetConstants.Common.WHITE_SPACE + mn + MainetConstants.Common.WHITE_SPACE + ln)
                    .trim();
            workflowTaskActionWithDocs.setEmpName((empName.isEmpty()) ? null : empName);
            workflowTaskActionWithDocs.setEmpEmail(obj.getEmpEmail());
            workflowTaskActionWithDocs.setEmpMobile(obj.getMobileNumber());
            workflowTaskActionWithDocs.setEmpGroupDescEng(obj.getGrDescEng());
            workflowTaskActionWithDocs.setEmpGroupDescReg(obj.getGrDescReg());
            if (ts.getLangId() == MainetConstants.Lang.REG)
                workflowTaskActionWithDocs.setEmpGroupDescEng(obj.getGrDescReg());
            workflowTaskActionWithDocs.setReferenceId(obj.getReferenceId());
            // for all pending task status should be pending
            workflowTaskActionWithDocs.setDecision(MainetConstants.WorkFlow.Status.PENDING.getValue());
            prepareAttachmentLookUp(obj.getAttPath(), workflowTaskActionWithDocs);
            pendingActions.add(workflowTaskActionWithDocs);

        });
        return pendingActions;
    }

    /**
     * use to prepare attachment LookUp for showing attachment history
     * @param obj
     * @param action
     */
    private void prepareAttachmentLookUp(String attPath, WorkflowTaskActionWithDocs action) {
        List<LookUp> lookUpList = new ArrayList<>();
        LookUp lookUpObj = null;
        String[] pairs = null;
        if (attPath != null && !attPath.isEmpty()) {
            for (String keyValue : attPath.split("\\*")) {
                lookUpObj = new LookUp();
                pairs = keyValue.split(MainetConstants.OPERATOR.COLON, 3);
                lookUpObj.setDefaultVal(pairs[0]);
                lookUpObj.setLookUpCode(pairs[1]);
                /*DmsId added*/
                lookUpObj.setDmsDocId(pairs[2]);
                lookUpList.add(lookUpObj);
            }
        }
        action.setAttachements(lookUpList);
    }
}
