package com.abm.mainet.materialmgmt.utility;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.materialmgmt.dto.ExpiryItemsDto;

public class StoreWorkflowUtility {

	
	 public static WorkflowProcessParameter prepareInitAccountBilllEntryProcessParameter(
	        ExpiryItemsDto expiryEntryBean, WorkflowMas workflowMas, Long expiryId) {
	        WorkflowProcessParameter processParameter = new WorkflowProcessParameter();
	        ApplicationMetadata applicationMetadata = new ApplicationMetadata();
	        WorkflowTaskAction taskAction = new WorkflowTaskAction();
	        processParameter.setProcessName(MainetConstants.WorkFlow.Process.MAKER_CHECKER);
	        applicationMetadata.setOrgId(expiryEntryBean.getOrgId());
	        applicationMetadata.setWorkflowId(workflowMas.getWfId());
	        applicationMetadata.setPaymentMode(MainetConstants.FlagF);
	        applicationMetadata.setIsCheckListApplicable(false);
	        if (expiryEntryBean.getActualTaskId() != null) {
	            taskAction.setTaskId(expiryEntryBean.getActualTaskId());
	            taskAction.setReferenceId(expiryEntryBean.getMovementNo());
	            if (expiryEntryBean.getCheckerAuthorization().toString().equals(MainetConstants.Y_FLAG)) {
	                taskAction.setDecision(MainetConstants.WorkFlow.Decision.APPROVED);
	            } else {
	                taskAction.setDecision(MainetConstants.WorkFlow.Decision.SEND_BACK);
	                taskAction.setSendBackToGroup(0);
	                taskAction.setSendBackToLevel(0);
	            }
	        }
	        taskAction.setOrgId(expiryEntryBean.getOrgId());
	        taskAction.setEmpId(expiryEntryBean.getUserId());
	        taskAction.setDateOfAction(expiryEntryBean.getLmodDate());
	        taskAction.setCreatedDate(expiryEntryBean.getLmodDate());
	        taskAction.setCreatedBy(expiryEntryBean.getUserId());
	        taskAction.setIsFinalApproval(false);
	        taskAction.setComments(expiryEntryBean.getCheckerRemarks());
	        taskAction.setReferenceId(expiryEntryBean.getMovementNo());
	        processParameter.setApplicationMetadata(applicationMetadata);
	        processParameter.setWorkflowTaskAction(taskAction);
	        applicationMetadata.setReferenceId(expiryEntryBean.getMovementNo());
	        return processParameter;

	    }
}
