package com.abm.mainet.cfc.checklist.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.cfc.checklist.domain.ChecklistStatusView;
import com.abm.mainet.cfc.checklist.service.ChecklistMstService;
import com.abm.mainet.cfc.checklist.service.IChecklistSearchService;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.BaseEntity;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractEntryFormModel;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class ChecklistResubmissionModel extends AbstractEntryFormModel<BaseEntity> {

    private static final long serialVersionUID = -1707159826199117144L;

    @Autowired
    private IChecklistVerificationService documentUplodService;

    @Autowired
    private IChecklistSearchService checklistSearchService;

    @Autowired
    private IFileUploadService fileUploadService;

    @Autowired
    private ChecklistMstService checklistMstService;

    @Resource
    private IWorkflowExecutionService workflowExecutionService;

    @Autowired
    private ServiceMasterService serviceMasterService;

    private Long applicationId;
    private Date applicationDate;
    private CFCAttachment attachment;
    private ChecklistStatusView applicationDetails = new ChecklistStatusView();
    private List<CFCAttachment> attachmentList = new ArrayList<>(0);
    private boolean resubmitedApplication;

    public List<CFCAttachment> querySearchResults() {

        attachmentList.clear();
        final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        fileUploadService.sessionCleanUpForFileUpload();
        if (applicationId != null) {

            applicationDetails = checklistSearchService.getCheckListDataByApplication(orgId, applicationId);
            if (null != applicationDetails.getApmChklstVrfyFlag()) {
                if (applicationDetails.getApmChklstVrfyFlag().equals(MainetConstants.ApplicationStatus.HOLD)) {

                    attachmentList = documentUplodService.getAttachDocumentByDocumentStatus(applicationId,
                            MainetConstants.Common_Constant.NO, orgId);
                } else {
                    addValidationError(getAppSession().getMessage("cfc.docresubmit.error",
                            new Object[] { applicationId.toString(), applicationDetails.getApprovalStatus() }));
                }

            } else {

                if (attachmentList.isEmpty()) {

                    addValidationError(getAppSession().getMessage("challan.noRecord"));
                }
            }
        } else {

            addValidationError(getAppSession().getMessage("cfc.applicationid.valid"));
        }

        return attachmentList;
    }

    @Override
    public boolean saveForm() {

        fileUploadService.validateUpload(getBindingResult());

        if (hasValidationErrors()) {
            return false;
        }

        checklistSearchService.updateApplicationChecklistStatus(applicationDetails.getApmApplicationId(),
                getUserSession().getOrganisation().getOrgid(), MainetConstants.ApplicationStatus.RESUBMIT);
        checklistMstService.uploadForChecklistVerification(attachmentList.get(0).getAttPath(),
                attachmentList.get(0).getDept(), applicationDetails.getSmServiceId(), getFileNetClient(),
                applicationDetails.getApmApplicationId(), new String[0]);

        List<Long> attachtentIds = documentUplodService.fetchAttachmentIdByAppid(applicationId,
                UserSession.getCurrent().getOrganisation().getOrgid());

        String processName = serviceMasterService.getProcessName(applicationDetails.getSmServiceId(),
                UserSession.getCurrent().getOrganisation().getOrgid());
        if (processName != null) {
            final Employee empId = UserSession.getCurrent().getEmployee();

            WorkflowProcessParameter workflowdto = new WorkflowProcessParameter();
            WorkflowTaskAction workflowAction = new WorkflowTaskAction();
            workflowAction.setTaskId(this.getTaskId());
            workflowAction.setApplicationId(applicationDetails.getApmApplicationId());
            workflowAction.setDateOfAction(new Date());
            //changes Approved to Submitted as discussion with Akshay D#131715
            workflowAction.setDecision(MainetConstants.WorkFlow.Decision.SUBMITTED);
            workflowAction.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            workflowAction.setEmpId(empId.getEmpId());
            workflowAction.setModifiedBy(empId.getEmpId());
            workflowAction.setEmpType(empId.getEmplType());
            workflowAction.setEmpName(empId.getEmpname());
            workflowAction.setCreatedBy(empId.getEmpId());
            workflowAction.setCreatedDate(new Date());
            /*
             * List<Long> attachementIds = new ArrayList<>(); attachmentList.forEach(doc -> { attachementIds.add(doc.getAttId());
             * });
             */
            workflowAction.setAttachementId(attachtentIds);
            workflowdto.setProcessName(processName);
            try {
                workflowdto.setWorkflowTaskAction(workflowAction);
                workflowExecutionService.updateWorkflow(workflowdto);
            } catch (final Exception e) {
                throw new FrameworkException("Exception in checklist verification for bpm workflow : " + e.getMessage(),
                        e);
            }
        }
        return true;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(final Long applicationId) {
        this.applicationId = applicationId;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(final Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public List<CFCAttachment> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(final List<CFCAttachment> attachmentList) {
        this.attachmentList = attachmentList;
    }

    public ChecklistStatusView getApplicationDetails() {
        return applicationDetails;
    }

    public void setApplicationDetails(final ChecklistStatusView applicationDetails) {
        this.applicationDetails = applicationDetails;
    }

    public boolean isResubmitedApplication() {
        return resubmitedApplication;
    }

    public void setResubmitedApplication(final boolean resubmitedApplication) {
        this.resubmitedApplication = resubmitedApplication;
    }

    public List<LookUp> getDocumentsList() {
        final List<LookUp> documentDetailsList = new ArrayList<>(0);

        LookUp lookUp = null;
        for (final CFCAttachment temp : attachmentList) {
            lookUp = new LookUp(temp.getAttId(), temp.getAttPath());
            lookUp.setOtherField(temp.getMandatory());
            lookUp.setDescLangFirst(temp.getClmDescEngl());
            lookUp.setDescLangSecond(temp.getClmDesc());
            lookUp.setLookUpId(temp.getClmId());
            lookUp.setLookUpCode(temp.getAttFname());
            lookUp.setLookUpType(temp.getClmStatus());
            lookUp.setLookUpParentId(temp.getAttId());
            lookUp.setLookUpExtraLongOne(temp.getClmSrNo());
            lookUp.setDescLangSecond(temp.getAttPath());
            lookUp.setExtraStringField1(temp.getDmsDocId());
            lookUp.setDocDescription(temp.getDocDescription());
            documentDetailsList.add(lookUp);
        }
        return documentDetailsList;
    }

    public CFCAttachment getAttachment() {
        return attachment;
    }

    public void setAttachment(final CFCAttachment attachment) {
        this.attachment = attachment;
    }

}
