package com.abm.mainet.care.ui.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.care.domain.CareRequest;
import com.abm.mainet.care.dto.LandInspectionDto;
import com.abm.mainet.care.service.ICareRequestService;
import com.abm.mainet.care.service.ICareWorkflowService;
import com.abm.mainet.care.service.ILandInspectionService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionResponse;

@Component
@Scope("session")
public class LandInspectionModel extends AbstractFormModel {

    private static final long serialVersionUID = -7576442370915373375L;

    private LandInspectionDto inspectionDto = new LandInspectionDto();
    private List<AttachDocs> complaintDocsList = new ArrayList<>();
    private List<DocumentDetailsVO> photosList = new ArrayList<>();
    private List<AttachDocs> photosDocs = new ArrayList<>();
    private List<DocumentDetailsVO> attachments = new ArrayList<>();
    private List<AttachDocs> attachDocsList = new ArrayList<>();
    private String modeType;
    // passing from JAVA because design create issue
    private List<LookUp> vlgLookUpList = new ArrayList<>();
    // for complaint respected task will close after land inspection form submit
    private String complaintId;
    private Long taskId;
    private Long applicationId;

    public LandInspectionDto getInspectionDto() {
        return inspectionDto;
    }

    public void setInspectionDto(LandInspectionDto inspectionDto) {
        this.inspectionDto = inspectionDto;
    }

    public List<AttachDocs> getComplaintDocsList() {
        return complaintDocsList;
    }

    public void setComplaintDocsList(List<AttachDocs> complaintDocsList) {
        this.complaintDocsList = complaintDocsList;
    }

    public List<DocumentDetailsVO> getPhotosList() {
        return photosList;
    }

    public void setPhotosList(List<DocumentDetailsVO> photosList) {
        this.photosList = photosList;
    }

    public List<AttachDocs> getPhotosDocs() {
        return photosDocs;
    }

    public void setPhotosDocs(List<AttachDocs> photosDocs) {
        this.photosDocs = photosDocs;
    }

    public List<DocumentDetailsVO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<DocumentDetailsVO> attachments) {
        this.attachments = attachments;
    }

    public List<AttachDocs> getAttachDocsList() {
        return attachDocsList;
    }

    public void setAttachDocsList(List<AttachDocs> attachDocsList) {
        this.attachDocsList = attachDocsList;
    }

    public String getModeType() {
        return modeType;
    }

    public void setModeType(String modeType) {
        this.modeType = modeType;
    }

    public List<LookUp> getVlgLookUpList() {
        return vlgLookUpList;
    }

    public void setVlgLookUpList(List<LookUp> vlgLookUpList) {
        this.vlgLookUpList = vlgLookUpList;
    }

    public String getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(String complaintId) {
        this.complaintId = complaintId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    @Autowired
    ILandInspectionService inspectionService;

    @Autowired
    private ICareWorkflowService careWorkflowService;

    @Autowired
    private ICareRequestService careRequestService;

    @Override
    public boolean saveForm() {
        // code for save Land Inspection
        // first organize the Documents
        // a.photosList b.attachments
        // preparing DTO for FILEUPLOAD attachment
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        requestDTO.setStatus(MainetConstants.FlagA);
        requestDTO.setDepartmentName("LIS");// Land Inspection System For DEHRADUN
        requestDTO.setServiceId(getServiceId());
        requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());

        /*
         * D#126294 setPhotosList(ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
         * .setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
         */
        setPhotosList(setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));

        getInspectionDto().setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        getInspectionDto().setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
        getInspectionDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        getInspectionDto().setCreatedDate(new Date());

        // logic for save ENCROACHMENT Data
        if (inspectionDto.getEncrMultipleSelect().equals("Y")) {
            // add multiEncroachment in encroachmentsDtos
            getInspectionDto().getEncroachmentsDtos().addAll(getInspectionDto().getMultiEncroachmentsDtos());
        }

        // set all relevant Work flow Task Action Data For initiating Work Flow -initial request
        getWorkflowActionDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        getWorkflowActionDto().setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
        getWorkflowActionDto().setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
        getWorkflowActionDto().setDateOfAction(new Date());
        getWorkflowActionDto().setCreatedDate(new Date());
        getWorkflowActionDto().setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        getWorkflowActionDto().setEmpName(UserSession.getCurrent().getEmployee().getEmplname());
        getWorkflowActionDto().setEmpEmail(UserSession.getCurrent().getEmployee().getEmpemail());
        getWorkflowActionDto().setPaymentMode(MainetConstants.FlagF);
        String referenceId = inspectionService.saveLandInspectionEntryAndInitiate(getInspectionDto(), getPhotosList(), requestDTO,
                getWorkflowActionDto());
        if (referenceId != null) {
            setSuccessMessage(ApplicationSession.getInstance().getMessage("ln.inspec.savesuccessmsg",
                    new Object[] { referenceId }));
            // D#108406 close complaint task
            Long applicationId = getApplicationId();

            WorkflowTaskAction departmentAction = new WorkflowTaskAction();
            departmentAction.setTaskId(getTaskId());
            departmentAction.setApplicationId(applicationId);
            departmentAction.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            departmentAction.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            departmentAction.setCreatedDate(new Date());
            departmentAction.setDateOfAction(new Date());
            departmentAction.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
            departmentAction.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
            departmentAction.setDecision(MainetConstants.WorkFlow.Decision.APPROVED);
            CareRequest careRequest = careRequestService.findByApplicationId(applicationId);
            RequestDTO applicantDetailDto = careRequestService.getApplicationDetails(careRequest);
            applicantDetailDto.setApplicationId(applicationId);
            departmentAction.setReferenceId(careRequest.getComplaintId());

            applicantDetailDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());

            try {
                careRequestService.updateCareProces(careRequest, departmentAction);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            setSuccessMessage(ApplicationSession.getInstance().getMessage("ln.inspec.workflowfailedmsg"));
        }

        return true;
    }

    public List<DocumentDetailsVO> setFileUploadMethod(final List<DocumentDetailsVO> docs) {

        Base64 base64 = null;
        List<File> list = null;
        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
            list = new ArrayList<>(entry.getValue());
            for (final File file : list) {
                try {
                    base64 = new Base64();
                    final String bytestring = base64.encodeToString(FileUtils.readFileToByteArray(file));

                    DocumentDetailsVO d = new DocumentDetailsVO();
                    d.setDocumentName(file.getName());
                    d.setDocumentByteCode(bytestring);
                    if (entry.getKey() == 0) {
                        d.setDoc_DESC_ENGL("photo");
                    } else {
                        d.setDoc_DESC_ENGL("endDoc");
                    }

                    docs.add(d);

                } catch (final IOException e) {
                    // LOGGER.error("Exception has been occurred in file byte to string conversions", e);
                }
            }
        }

        return docs;
    }

    public boolean updateLandInspectionWrkflowApproval(Long orgId) {
        ServiceMaster serviceMast = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                .getServiceMasterByShortCode("LIE",
                        UserSession.getCurrent().getOrganisation().getOrgid());
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setReferenceId(getWorkflowActionDto().getReferenceId());
        requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        requestDTO.setDepartmentName(MainetConstants.DEPT_SHORT_NAME.CFC_CENTER);
        requestDTO.setServiceId(serviceMast.getSmServiceId());
        requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        requestDTO.setDeptId(serviceMast.getTbDepartment().getDpDeptid());
        requestDTO.setStatus(MainetConstants.FlagA);
        /*
         * requestDTO.setIdfId(MainetConstants.LQP.SERVICE_CODE.LQE + MainetConstants.WINDOWS_SLASH +
         * queryAnsrMstDto.getQueryRegistrationMasterDto().getQuestionRegId()); this.attachments = setDocumentsDetailVO();
         * setAttachments(fileUpload.prepareFileUpload(getAttachments()));
         */

        WorkflowTaskAction workflowTaskAction = prepareWorkFlowTaskActionUpdate(getWorkflowActionDto());
        workflowTaskAction.setTaskId(getTaskId());// task id set

        // update Workflow based on task id and flag U

        WorkflowTaskActionResponse response = careWorkflowService.initiateAndUpdateWorkFlowMC(workflowTaskAction,
                workflowTaskAction.getTaskId()/* wflowId */, "LandInspection.html", MainetConstants.FlagU,
                serviceMast.getSmShortdesc());
        // based on response do something
        if (response != null && workflowTaskAction.getDecision().equalsIgnoreCase(MainetConstants.WorkFlow.Decision.APPROVED)
                && !response.getIsProcessAlive()) {

        } else if (response != null && !response.getIsProcessAlive()
                && workflowTaskAction.getDecision().equalsIgnoreCase(MainetConstants.WorkFlow.Decision.REJECTED)) {

        } else if (response != null && response.getIsProcessAlive()) {
            return true;
        }

        return true;
    }

    private WorkflowTaskAction prepareWorkFlowTaskActionUpdate(WorkflowTaskAction workflowActionDto) {
        getWorkflowActionDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        workflowActionDto.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
        workflowActionDto.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
        workflowActionDto.setEmpName(UserSession.getCurrent().getEmployee().getEmplname());
        workflowActionDto.setEmpEmail(workflowActionDto.getEmpName());
        workflowActionDto.setDateOfAction(new Date());
        workflowActionDto.setCreatedDate(new Date());
        workflowActionDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        workflowActionDto.setReferenceId(getWorkflowActionDto().getReferenceId());
        workflowActionDto.setPaymentMode(MainetConstants.FlagF);
        workflowActionDto.setIsFinalApproval(false);
        workflowActionDto.setTaskId(getTaskId());
        if (getWorkflowActionDto().getDecision().equals(MainetConstants.WorkFlow.Decision.SEND_BACK)) {
            workflowActionDto.setSendBackToGroup(getWorkflowActionDto().getSendBackToGroup());
            workflowActionDto.setSendBackToLevel(getWorkflowActionDto().getSendBackToLevel());
        }
        if (getWorkflowActionDto().getDecision().equals(MainetConstants.WorkFlow.Decision.FORWARD_TO_EMPLOYEE)) {
            workflowActionDto.setEmpId(Long.valueOf(workflowActionDto.getForwardToEmployee()));
        }

        return workflowActionDto;

    }

}
