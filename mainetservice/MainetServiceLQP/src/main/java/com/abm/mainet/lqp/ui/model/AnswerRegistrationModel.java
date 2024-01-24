package com.abm.mainet.lqp.ui.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.lqp.dto.DocumentDto;
import com.abm.mainet.lqp.dto.QueryAnswerMasterDto;
import com.abm.mainet.lqp.service.ILegislativeWorkflowService;
import com.abm.mainet.lqp.service.IQueryAnswerService;
import com.abm.mainet.lqp.service.IQueryRegistrationService;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class AnswerRegistrationModel extends AbstractFormModel {

    private static final long serialVersionUID = 628070053671234L;
    private String saveMode;
    private String approvalViewFlag;
    private Long taskId;
    private QueryAnswerMasterDto queryAnsrMstDto = new QueryAnswerMasterDto();

    private List<TbDepartment> departmentList = new ArrayList<>();
    private List<AttachDocs> attachDocsList = new ArrayList<>();
    private List<DocumentDetailsVO> attachments = new ArrayList<>();

    private List<DocumentDto> documentDtos = new ArrayList<>();

    @Autowired
    IQueryRegistrationService registrationService;

    @Autowired
    private SeqGenFunctionUtility seqGenFunctionUtility;

    @Autowired
    private IFileUploadService fileUpload;

    @Autowired
    private IChecklistVerificationService checkListService;

    @Autowired
    private ILegislativeWorkflowService legislativeWorkflowService;

    @Autowired
    private IQueryAnswerService queryAnswerService;

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public String getApprovalViewFlag() {
        return approvalViewFlag;
    }

    public void setApprovalViewFlag(String approvalViewFlag) {
        this.approvalViewFlag = approvalViewFlag;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public QueryAnswerMasterDto getQueryAnsrMstDto() {
        return queryAnsrMstDto;
    }

    public void setQueryAnsrMstDto(QueryAnswerMasterDto queryAnsrMstDto) {
        this.queryAnsrMstDto = queryAnsrMstDto;
    }

    public List<TbDepartment> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<TbDepartment> departmentList) {
        this.departmentList = departmentList;
    }

    public IQueryRegistrationService getRegistrationService() {
        return registrationService;
    }

    public void setRegistrationService(IQueryRegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    public SeqGenFunctionUtility getSeqGenFunctionUtility() {
        return seqGenFunctionUtility;
    }

    public void setSeqGenFunctionUtility(SeqGenFunctionUtility seqGenFunctionUtility) {
        this.seqGenFunctionUtility = seqGenFunctionUtility;
    }

    public List<AttachDocs> getAttachDocsList() {
        return attachDocsList;
    }

    public void setAttachDocsList(List<AttachDocs> attachDocsList) {
        this.attachDocsList = attachDocsList;
    }

    public List<DocumentDetailsVO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<DocumentDetailsVO> attachments) {
        this.attachments = attachments;
    }

    public List<DocumentDto> getDocumentDtos() {
        return documentDtos;
    }

    public void setDocumentDtos(List<DocumentDto> documentDtos) {
        this.documentDtos = documentDtos;
    }

    public boolean updateAnswerWrkflowApprovalFlag(Long orgId) {
        ServiceMaster serviceMast = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                .getServiceMasterByShortCode(MainetConstants.LQP.SERVICE_CODE.LQE,
                        UserSession.getCurrent().getOrganisation().getOrgid());
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setReferenceId(getWorkflowActionDto().getReferenceId());
        requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        requestDTO.setDepartmentName(MainetConstants.LQP.LQP_DEPT_CODE);
        requestDTO.setServiceId(serviceMast.getSmServiceId());
        requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        requestDTO.setDeptId(serviceMast.getTbDepartment().getDpDeptid());
        requestDTO.setStatus(MainetConstants.FlagA);
        requestDTO.setIdfId(MainetConstants.LQP.SERVICE_CODE.LQE + MainetConstants.WINDOWS_SLASH
                + queryAnsrMstDto.getQueryRegistrationMasterDto().getQuestionRegId());

        this.attachments = setDocumentsDetailVO();
        setAttachments(fileUpload.prepareFileUpload(getAttachments()));
        /*
         * List<Long> attacheMentIds = checkListService.fetchAllAttachIdByReferenceId( getWorkflowActionDto().getReferenceId(),
         * UserSession.getCurrent().getOrganisation().getOrgid()); getWorkflowActionDto().setAttachementId(attacheMentIds);
         */

        WorkflowTaskAction workflowActionDtoTmp = prepareWorkFlowTaskActionUpdate(getWorkflowActionDto());
        workflowActionDtoTmp.setTaskId(getTaskId());// task id set
        queryAnsrMstDto.setAnswerDate(new Date());
        queryAnsrMstDto.setRemark(getWorkflowActionDto().getComments());
        // common data set
        if (queryAnsrMstDto.getAnswerRegId() == null) {
            queryAnsrMstDto.setOrgId(orgId);
            queryAnsrMstDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            queryAnsrMstDto.setCreatedDate(new Date());
            queryAnsrMstDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
        } else {
            queryAnsrMstDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            queryAnsrMstDto.setUpdatedDate(new Date());
            queryAnsrMstDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
        }

        queryAnswerService.saveAnswerAndUpdateWorkflow(workflowActionDtoTmp, queryAnsrMstDto, getAttachments(), requestDTO);

        return true;
    }

    public List<DocumentDetailsVO> setDocumentsDetailVO() {
        List<DocumentDetailsVO> docVOList = new ArrayList<DocumentDetailsVO>();

        if ((FileUploadUtility.getCurrent().getFileMap() != null)
                && !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
            for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
                final List<File> list = new ArrayList<>(entry.getValue());
                for (final File file : list) {
                    DocumentDetailsVO docVO = new DocumentDetailsVO();
                    docVO.setDoc_DESC_ENGL(file.getName());
                    docVOList.add(docVO);
                }
            }
        }
        return docVOList;
    }

    private WorkflowTaskAction prepareWorkFlowTaskActionUpdate(WorkflowTaskAction workflowActionDto) {
        getWorkflowActionDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        workflowActionDto.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
        workflowActionDto.setEmpName(UserSession.getCurrent().getEmployee().getEmplname());
        workflowActionDto.setEmpEmail(UserSession.getCurrent().getEmployee().getEmpemail());
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
        if (getWorkflowActionDto().getDecision().equals(MainetConstants.WorkFlow.Decision.FORWARD_TO_EMPLOYEE))
        	workflowActionDto.setEmpId(Long.valueOf(workflowActionDto.getForwardToEmployee()));
        else
        	workflowActionDto.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());

        return workflowActionDto;

    }

}