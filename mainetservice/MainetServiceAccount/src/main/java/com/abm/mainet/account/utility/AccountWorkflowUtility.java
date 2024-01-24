package com.abm.mainet.account.utility;

import com.abm.mainet.account.dto.AccountBillEntryMasterBean;
import com.abm.mainet.account.dto.AccountBudgetEstimationPreparationBean;
import com.abm.mainet.account.dto.AccountBudgetReappropriationMasterBean;
import com.abm.mainet.account.dto.AccountTenderEntryBean;
import com.abm.mainet.account.dto.PaymentEntryDto;
import com.abm.mainet.account.dto.RTGSPaymentEntryDTO;
import com.abm.mainet.account.dto.ReappropriationOfBudgetAuthorizationDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;

public class AccountWorkflowUtility {

    public static WorkflowProcessParameter prepareInitAccountWorkOrderEntryProcessParameter(
            AccountTenderEntryBean tenderEntryBean, WorkflowMas workflowMas, String applicationId) {
        WorkflowProcessParameter processParameter = new WorkflowProcessParameter();
        ApplicationMetadata applicationMetadata = new ApplicationMetadata();
        WorkflowTaskAction taskAction = new WorkflowTaskAction();
        processParameter.setProcessName(MainetConstants.WorkFlow.Process.MAKER_CHECKER);
        applicationMetadata.setOrgId(tenderEntryBean.getOrgid());
        applicationMetadata.setWorkflowId(workflowMas.getWfId());
        applicationMetadata.setPaymentMode(MainetConstants.FlagF);
        applicationMetadata.setIsCheckListApplicable(false);
        taskAction.setOrgId(tenderEntryBean.getOrgid());
        taskAction.setEmpId(tenderEntryBean.getCreatedBy());
        taskAction.setDateOfAction(tenderEntryBean.getCreatedDate());
        taskAction.setCreatedDate(tenderEntryBean.getCreatedDate());
        taskAction.setCreatedBy(tenderEntryBean.getCreatedBy());
        taskAction.setReferenceId(applicationId);
        taskAction.setIsFinalApproval(false);
        processParameter.setApplicationMetadata(applicationMetadata);
        processParameter.setWorkflowTaskAction(taskAction);
        applicationMetadata.setReferenceId(applicationId);
        return processParameter;

    }

    public static WorkflowProcessParameter prepareInitAccountBilllEntryProcessParameter(
            AccountBillEntryMasterBean billEntryBean, WorkflowMas workflowMas, String billNo) {
        WorkflowProcessParameter processParameter = new WorkflowProcessParameter();
        ApplicationMetadata applicationMetadata = new ApplicationMetadata();
        WorkflowTaskAction taskAction = new WorkflowTaskAction();
        processParameter.setProcessName(MainetConstants.WorkFlow.Process.MAKER_CHECKER);
        applicationMetadata.setOrgId(billEntryBean.getOrgId());
        applicationMetadata.setWorkflowId(workflowMas.getWfId());
        applicationMetadata.setPaymentMode(MainetConstants.FlagF);
        applicationMetadata.setIsCheckListApplicable(false);
        if (billEntryBean.getActualTaskId() != null) {
            taskAction.setTaskId(billEntryBean.getActualTaskId());
            taskAction.setReferenceId(billEntryBean.getBillNo());
            if (billEntryBean.getCheckerAuthorization().toString().equals(MainetConstants.Y_FLAG)) {
                taskAction.setDecision(MainetConstants.WorkFlow.Decision.APPROVED);
            } else {
                taskAction.setDecision(MainetConstants.WorkFlow.Decision.SEND_BACK);
                if(billEntryBean.getLevelCheck()!=null && billEntryBean.getLevelCheck()>1)
                {
                	 taskAction.setSendBackToGroup((billEntryBean.getLevelCheck().intValue()-1));
                     taskAction.setSendBackToLevel((billEntryBean.getLevelCheck().intValue()-1));
                }else {
                	 taskAction.setSendBackToGroup(0);
                     taskAction.setSendBackToLevel(0);
                }
               
            }
        }
        taskAction.setOrgId(billEntryBean.getOrgId());
        if(billEntryBean.getCheckerUser()!=null)
        taskAction.setEmpId(Long.valueOf(billEntryBean.getCheckerUser()));
        else {
        	taskAction.setEmpId(billEntryBean.getCreatedBy());
        }
        taskAction.setDateOfAction(billEntryBean.getCreatedDate());
        taskAction.setCreatedDate(billEntryBean.getCreatedDate());
        taskAction.setCreatedBy(billEntryBean.getCreatedBy());
        taskAction.setIsFinalApproval(false);
        taskAction.setComments(billEntryBean.getCheckerRemarks());
        taskAction.setReferenceId(billEntryBean.getBillNo());
        processParameter.setApplicationMetadata(applicationMetadata);
        processParameter.setWorkflowTaskAction(taskAction);
        applicationMetadata.setReferenceId(billNo);
        return processParameter;

    }

    public static WorkflowProcessParameter prepareInitAccountPaymentEntryProcessParameter(
            PaymentEntryDto paymentEntryDto, WorkflowMas workflowMas, String paymentNo) {
        WorkflowProcessParameter processParameter = new WorkflowProcessParameter();
        ApplicationMetadata applicationMetadata = new ApplicationMetadata();
        WorkflowTaskAction taskAction = new WorkflowTaskAction();
        processParameter.setProcessName(MainetConstants.WorkFlow.Process.MAKER_CHECKER);
        applicationMetadata.setOrgId(paymentEntryDto.getOrgId());
        applicationMetadata.setWorkflowId(workflowMas.getWfId());
        applicationMetadata.setPaymentMode(MainetConstants.FlagF);
        applicationMetadata.setIsCheckListApplicable(false);
        taskAction.setOrgId(paymentEntryDto.getOrgId());
        taskAction.setEmpId(paymentEntryDto.getCreatedBy());
        taskAction.setDateOfAction(paymentEntryDto.getCreatedDate());
        taskAction.setCreatedDate(paymentEntryDto.getCreatedDate());
        taskAction.setCreatedBy(paymentEntryDto.getCreatedBy());
        taskAction.setIsFinalApproval(false);
        processParameter.setApplicationMetadata(applicationMetadata);
        processParameter.setWorkflowTaskAction(taskAction);
        applicationMetadata.setReferenceId(paymentNo);
        return processParameter;

    }

    public static WorkflowProcessParameter prepareInitAccountWorkOrderEntryUpdateProcessParameter(
            AccountTenderEntryBean tenderEntryBean, WorkflowMas workflowMas, String applicationId) {
        WorkflowProcessParameter processParameter = new WorkflowProcessParameter();
        WorkflowTaskAction taskAction = new WorkflowTaskAction();
        processParameter.setProcessName(MainetConstants.WorkFlow.Process.MAKER_CHECKER);
        taskAction.setOrgId(tenderEntryBean.getOrgid());
        taskAction.setEmpId(tenderEntryBean.getCreatedBy());
        taskAction.setDateOfAction(tenderEntryBean.getCreatedDate());
        taskAction.setCreatedDate(tenderEntryBean.getCreatedDate());
        taskAction.setCreatedBy(tenderEntryBean.getCreatedBy());
        taskAction.setTaskId(Long.valueOf(tenderEntryBean.getActualTaskId()));
        taskAction.setReferenceId(applicationId);
        if (tenderEntryBean.getAuthStatus().equals("Y")) {
            taskAction.setDecision(MainetConstants.WorkFlow.Decision.APPROVED);
        }
        taskAction.setComments(tenderEntryBean.getAuthRemark());
        taskAction.setIsFinalApproval(false);
        processParameter.setWorkflowTaskAction(taskAction);
        return processParameter;

    }

    public static WorkflowProcessParameter prepareInitAccountBudgetReappropriationProcessParameter(
            AccountBudgetReappropriationMasterBean acBudgetReappropriationMasterBean, WorkflowMas workflowMas,
            String budgTranRefNo) {

        WorkflowProcessParameter processParameter = new WorkflowProcessParameter();
        ApplicationMetadata applicationMetadata = new ApplicationMetadata();
        WorkflowTaskAction taskAction = new WorkflowTaskAction();
        processParameter.setProcessName(MainetConstants.WorkFlow.Process.MAKER_CHECKER);
        applicationMetadata.setOrgId(acBudgetReappropriationMasterBean.getOrgid());
        applicationMetadata.setWorkflowId(workflowMas.getWfId());
        applicationMetadata.setPaymentMode(MainetConstants.FlagF);
        applicationMetadata.setIsCheckListApplicable(false);
        if (acBudgetReappropriationMasterBean.getActualTaskId() != null) {
            taskAction.setTaskId(acBudgetReappropriationMasterBean.getActualTaskId());
            taskAction.setReferenceId(acBudgetReappropriationMasterBean.getBudgetTranRefNo());
            if (acBudgetReappropriationMasterBean.getAuthFlag().toString().equals(MainetConstants.Y_FLAG)) {
                taskAction.setDecision(MainetConstants.WorkFlow.Decision.APPROVED);
            } else {
                taskAction.setDecision(MainetConstants.WorkFlow.Decision.SEND_BACK);
                taskAction.setSendBackToGroup(0);
                taskAction.setSendBackToLevel(0);
            }
        }
        taskAction.setOrgId(acBudgetReappropriationMasterBean.getOrgid());
        taskAction.setEmpId(acBudgetReappropriationMasterBean.getUserId());
        taskAction.setDateOfAction(acBudgetReappropriationMasterBean.getLmoddate());
        taskAction.setCreatedDate(acBudgetReappropriationMasterBean.getLmoddate());
        taskAction.setCreatedBy(acBudgetReappropriationMasterBean.getUserId());
        taskAction.setIsFinalApproval(false);
        processParameter.setApplicationMetadata(applicationMetadata);
        processParameter.setWorkflowTaskAction(taskAction);
        applicationMetadata.setReferenceId(budgTranRefNo);
        return processParameter;

    }

    public static WorkflowProcessParameter prepareInitAccountReappropriationBudgetProcessParameter(
            ReappropriationOfBudgetAuthorizationDTO acBudgetReappropriationMasterBean, WorkflowMas workflowMas,
            String budgTranRefNo) {

        WorkflowProcessParameter processParameter = new WorkflowProcessParameter();
        ApplicationMetadata applicationMetadata = new ApplicationMetadata();
        WorkflowTaskAction taskAction = new WorkflowTaskAction();
        processParameter.setProcessName(MainetConstants.WorkFlow.Process.MAKER_CHECKER);
        applicationMetadata.setOrgId(acBudgetReappropriationMasterBean.getOrgid());
        applicationMetadata.setWorkflowId(workflowMas.getWfId());
        applicationMetadata.setPaymentMode(MainetConstants.FlagF);
        applicationMetadata.setIsCheckListApplicable(false);
        if (acBudgetReappropriationMasterBean.getActualTaskId() != null) {
            taskAction.setTaskId(acBudgetReappropriationMasterBean.getActualTaskId());
            taskAction.setReferenceId(acBudgetReappropriationMasterBean.getBudgetTranRefNo());
            if (acBudgetReappropriationMasterBean.getApproved().toString().equals(MainetConstants.Y_FLAG)) {
                taskAction.setDecision(MainetConstants.WorkFlow.Decision.APPROVED);
            } else {
                taskAction.setDecision(MainetConstants.WorkFlow.Decision.SEND_BACK);
                taskAction.setSendBackToGroup(0);
                taskAction.setSendBackToLevel(0);
            }
        }
        taskAction.setOrgId(acBudgetReappropriationMasterBean.getOrgid());
        taskAction.setEmpId(acBudgetReappropriationMasterBean.getUserId());
        taskAction.setDateOfAction(acBudgetReappropriationMasterBean.getLmoddate());
        taskAction.setCreatedDate(acBudgetReappropriationMasterBean.getLmoddate());
        taskAction.setCreatedBy(acBudgetReappropriationMasterBean.getUserId());
        taskAction.setIsFinalApproval(false);
        processParameter.setApplicationMetadata(applicationMetadata);
        processParameter.setWorkflowTaskAction(taskAction);
        applicationMetadata.setReferenceId(budgTranRefNo);
        return processParameter;

    }

    public static WorkflowProcessParameter prepareInitAccountPaymentEntryProcessParameter(
            RTGSPaymentEntryDTO rtgsPaymentEntryDTO, WorkflowMas workflowMas, String paymentNo) {
        // TODO Auto-generated method stub
        return null;
    }
    
    
    public static WorkflowProcessParameter prepareInitAccountBudgetEstmationPreparationProcessParameter(
    		AccountBudgetEstimationPreparationBean tbAcBudgetEstimationPreparation, WorkflowMas workflowMas, String transactionNo) {
        WorkflowProcessParameter processParameter = new WorkflowProcessParameter();
        ApplicationMetadata applicationMetadata = new ApplicationMetadata();
        WorkflowTaskAction taskAction = new WorkflowTaskAction();
        processParameter.setProcessName(MainetConstants.WorkFlow.Process.MAKER_CHECKER);
        applicationMetadata.setOrgId(tbAcBudgetEstimationPreparation.getOrgid());
        applicationMetadata.setWorkflowId(workflowMas.getWfId());
        applicationMetadata.setPaymentMode(MainetConstants.FlagF);
        applicationMetadata.setIsCheckListApplicable(false);
        if (tbAcBudgetEstimationPreparation.getActualTaskId() != null) {
            taskAction.setTaskId(tbAcBudgetEstimationPreparation.getActualTaskId());
            taskAction.setReferenceId(tbAcBudgetEstimationPreparation.getTransactionNo());
            if (tbAcBudgetEstimationPreparation.getCheckerAuthorization().toString().equals(MainetConstants.Y_FLAG)) {
                taskAction.setDecision(MainetConstants.WorkFlow.Decision.APPROVED);
            } else {
                taskAction.setDecision(MainetConstants.WorkFlow.Decision.SEND_BACK);
                taskAction.setSendBackToGroup(0);
                taskAction.setSendBackToLevel(0);
            }
        }
        taskAction.setOrgId(tbAcBudgetEstimationPreparation.getOrgid());
        taskAction.setEmpId(tbAcBudgetEstimationPreparation.getCreatedBy());
        taskAction.setDateOfAction(tbAcBudgetEstimationPreparation.getCreatedDate());
        taskAction.setCreatedDate(tbAcBudgetEstimationPreparation.getCreatedDate());
        taskAction.setCreatedBy(tbAcBudgetEstimationPreparation.getCreatedBy());
        taskAction.setIsFinalApproval(false);
        processParameter.setApplicationMetadata(applicationMetadata);
        processParameter.setWorkflowTaskAction(taskAction);
        applicationMetadata.setReferenceId(transactionNo);
        return processParameter;

    }
    
}
