package com.abm.mainet.workManagement.ui.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.ContractAgreementSummaryDTO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.workManagement.dto.ApprovalTermsConditionDto;
import com.abm.mainet.workManagement.service.ApprovalTermsConditionService;
import com.abm.mainet.workManagement.service.WorkOrderService;
import com.abm.mainet.workManagement.service.WorksWorkFlowService;
import com.abm.mainet.workManagement.ui.validator.WorkContractVariationApprovalValidator;

@Component
@Scope("session")
public class WorkContractVariationModel extends AbstractFormModel {

    private static final long serialVersionUID = 6192970828340765802L;

    @Autowired
    public IFileUploadService fileUpload;

    private ContractAgreementSummaryDTO contractAgreementMastDTO;

    private Long parentOrgId;

    private String contractMode;

    private Long actualTaskId;

    private String saveMode;

    private BigDecimal variationAmount;

    private List<TbDepartment> departmentsList;

    private List<Object[]> employeeList;

    private List<DocumentDetailsVO> attachments = new ArrayList<>();

    private List<ApprovalTermsConditionDto> termsConditionDtosList = new ArrayList<>();

    private String removeTermsById;

    private Long workId;

    private String ContractNo;

    private BigDecimal contractAmount;

    private String deptName;

    private Long contractId;

    private String contractTaskName;

    private String flagForSendBack;
    
    private String completedFlag;

    @Override
    public boolean saveForm() {

        boolean status = true;
        final WorkflowTaskAction workFlowActionDto = getWorkflowActionDto();
        validateBean(workFlowActionDto, WorkContractVariationApprovalValidator.class);

        if (hasValidationErrors()) {
            return false;
        }
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setReferenceId(getWorkflowActionDto().getReferenceId());
        requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        requestDTO.setDepartmentName(MainetConstants.WorksManagement.WORKS_MANAGEMENT);
        requestDTO.setServiceId(getServiceId());
        requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());

        ServiceMaster service = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                .getServiceMasterByShortCode(MainetConstants.WorksManagement.CVA, getParentOrgId());
        requestDTO.setDeptId(service.getTbDepartment().getDpDeptid());

        List<DocumentDetailsVO> docs = new ArrayList<>();
        DocumentDetailsVO document = new DocumentDetailsVO();
        document.setDocumentSerialNo(1L);
        docs.add(document);

        setAttachments(fileUpload.prepareFileUpload(docs));

        fileUpload.doFileUpload(getAttachments(), requestDTO);

        List<Long> attacheMentIds = ApplicationContextProvider.getApplicationContext()
                .getBean(IChecklistVerificationService.class)
                .fetchAllAttachIdByReferenceId(getWorkflowActionDto().getReferenceId(),
                        UserSession.getCurrent().getOrganisation().getOrgid());

        prepareWorkFlowTaskAction(getWorkflowActionDto());
        getWorkflowActionDto().setAttachementId(attacheMentIds);

        ApplicationContextProvider.getApplicationContext().getBean(WorksWorkFlowService.class)
                .updateWorkFlowWorksService(getWorkflowActionDto());

        createTermsAndApplication(getTermsConditionDtosList());

        WorkflowRequest workflowRequest = ApplicationContextProvider.getApplicationContext()
                .getBean(IWorkflowRequestService.class)
                .getWorkflowRequestByAppIdOrRefId(null, getContractNo(), getParentOrgId());

        if (workflowRequest != null
                && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.REJECTED)) {
            ApplicationContextProvider.getApplicationContext().getBean(WorkOrderService.class).updateContractVariationStatus(
                    getContractId(),
                    MainetConstants.FlagR);
        }

        if (workflowRequest != null && workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Status.CLOSED)
                && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)) {
            ApplicationContextProvider.getApplicationContext().getBean(WorkOrderService.class).updateContractVariationStatus(
                    getContractId(), MainetConstants.FlagA);
        }
        if (workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.REJECTED)) {
            setSuccessMessage(
                    ApplicationSession.getInstance().getMessage("work.contract.variation.approval.rejected.success"));

        } else {
            setSuccessMessage(
                    ApplicationSession.getInstance().getMessage("work.contract.variation.approval.creation.success"));
        }

        return status;
    }

    private void createTermsAndApplication(List<ApprovalTermsConditionDto> termsConditionDtos) {
        List<ApprovalTermsConditionDto> conditionDtos = new ArrayList<>();
        if (termsConditionDtos != null) {
            termsConditionDtos.forEach(dtos -> {
                dtos.setOrgId(getParentOrgId());
                dtos.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                dtos.setCreatedDate(new Date());
                dtos.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
                dtos.setWorkId(getWorkId());
                dtos.setRefId(getWorkflowActionDto().getReferenceId());
                conditionDtos.add(dtos);
            });
            List<Long> deletedTermsId = removeTermsCondByIdAsList();
            ApplicationContextProvider.getApplicationContext().getBean(ApprovalTermsConditionService.class).saveTermsCondition(
                    conditionDtos, deletedTermsId);
        }
    }

    private WorkflowTaskAction prepareWorkFlowTaskAction(WorkflowTaskAction workflowActionDto) {

        getWorkflowActionDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        workflowActionDto.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
        workflowActionDto.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
        workflowActionDto.setEmpName(UserSession.getCurrent().getEmployee().getEmplname());
        workflowActionDto.setEmpEmail(UserSession.getCurrent().getEmployee().getEmpemail());

        workflowActionDto.setDateOfAction(new Date());
        workflowActionDto.setCreatedDate(new Date());
        workflowActionDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        workflowActionDto.setReferenceId(getWorkflowActionDto().getReferenceId());
        workflowActionDto.setPaymentMode(MainetConstants.FlagF);
        workflowActionDto.setIsFinalApproval(false);
        return workflowActionDto;
    }

    private List<Long> removeTermsCondByIdAsList() {
        List<Long> removeTermsList = null;
        String termsIdList = getRemoveTermsById();
        if (termsIdList != null && !termsIdList.isEmpty()) {
            removeTermsList = new ArrayList<>();
            String termsArray[] = termsIdList.split(MainetConstants.operator.COMMA);
            for (String termsCondId : termsArray) {
                removeTermsList.add(Long.valueOf(termsCondId));
            }
        }
        return removeTermsList;
    }

    public Long getParentOrgId() {
        return parentOrgId;
    }

    public void setParentOrgId(Long parentOrgId) {
        this.parentOrgId = parentOrgId;
    }

    public String getContractMode() {
        return contractMode;
    }

    public void setContractMode(String contractMode) {
        this.contractMode = contractMode;
    }

    public Long getActualTaskId() {
        return actualTaskId;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public void setActualTaskId(Long actualTaskId) {
        this.actualTaskId = actualTaskId;
    }

    public BigDecimal getVariationAmount() {
        return variationAmount;
    }

    public void setVariationAmount(BigDecimal variationAmount) {
        this.variationAmount = variationAmount;
    }

    public List<TbDepartment> getDepartmentsList() {
        return departmentsList;
    }

    public void setDepartmentsList(List<TbDepartment> departmentsList) {
        this.departmentsList = departmentsList;
    }

    public List<Object[]> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Object[]> employeeList) {
        this.employeeList = employeeList;
    }

    public List<DocumentDetailsVO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<DocumentDetailsVO> attachments) {
        this.attachments = attachments;
    }

    public ContractAgreementSummaryDTO getContractAgreementMastDTO() {
        return contractAgreementMastDTO;
    }

    public void setContractAgreementMastDTO(ContractAgreementSummaryDTO contractAgreementMastDTO) {
        this.contractAgreementMastDTO = contractAgreementMastDTO;
    }

    public List<ApprovalTermsConditionDto> getTermsConditionDtosList() {
        return termsConditionDtosList;
    }

    public void setTermsConditionDtosList(List<ApprovalTermsConditionDto> termsConditionDtosList) {
        this.termsConditionDtosList = termsConditionDtosList;
    }

    public String getRemoveTermsById() {
        return removeTermsById;
    }

    public void setRemoveTermsById(String removeTermsById) {
        this.removeTermsById = removeTermsById;
    }

    public Long getWorkId() {
        return workId;
    }

    public void setWorkId(Long workId) {
        this.workId = workId;
    }

    public String getContractNo() {
        return ContractNo;
    }

    public void setContractNo(String contractNo) {
        ContractNo = contractNo;
    }

    public BigDecimal getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(BigDecimal contractAmount) {
        this.contractAmount = contractAmount;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public String getContractTaskName() {
        return contractTaskName;
    }

    public void setContractTaskName(String contractTaskName) {
        this.contractTaskName = contractTaskName;
    }

    public String getFlagForSendBack() {
        return flagForSendBack;
    }

    public void setFlagForSendBack(String flagForSendBack) {
        this.flagForSendBack = flagForSendBack;
    }

	public String getCompletedFlag() {
		return completedFlag;
	}

	public void setCompletedFlag(String completedFlag) {
		this.completedFlag = completedFlag;
	}

}
