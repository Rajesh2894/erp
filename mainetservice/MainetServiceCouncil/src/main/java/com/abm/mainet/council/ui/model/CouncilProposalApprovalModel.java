package com.abm.mainet.council.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadSecondaryAccountCodeMasterEntity;
import com.abm.mainet.common.integration.acccount.dto.AccountFundMasterBean;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionWithDocs;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.common.workflow.ui.validator.CheckerActionValidator;
import com.abm.mainet.council.dto.CouncilProposalMasterDto;
import com.abm.mainet.council.service.CouncilWorkFlowService;
import com.abm.mainet.council.service.ICouncilProposalMasterService;

/**
 * @author aarti.paan
 * @since 24th May 2019
 */
@Component
@Scope("session")

public class CouncilProposalApprovalModel extends AbstractFormModel {

    private static final long serialVersionUID = 1138974562574265741L;
    @Autowired
    public IFileUploadService fileUpload;

    private CouncilProposalMasterDto couProposalMasterDto = new CouncilProposalMasterDto();
    private List<LookUp> lookupListLevel1 = new ArrayList<LookUp>();
    private List<WorkflowTaskActionWithDocs> actionHistory = new ArrayList<WorkflowTaskActionWithDocs>();
    private List<DocumentDetailsVO> attachments;
    private List<AttachDocs> attachDocsList = new ArrayList<>();
    private String proposalNumber;
    private List<AccountHeadSecondaryAccountCodeMasterEntity> budgetList = new ArrayList<>();
    private List<TbFinancialyear> faYears = new ArrayList<>();
    private String cpdMode;
    private Long deptId;
    List<AccountFundMasterBean> fundList =new ArrayList<>();
    Map<Long, String> fieldList = new HashMap<>();
    private String viewFlag;
    private Boolean sendBackFlag;

    public boolean saveProposalApprovalDetails() {
        final WorkflowTaskAction workFlowActionDto = getWorkflowActionDto();
        if (!sendBackFlag) {
        	workFlowActionDto.setDecision(MainetConstants.WorkFlow.Decision.APPROVED);
        }
       
        validateBean(workFlowActionDto, CheckerActionValidator.class);
        if (hasValidationErrors()) {
            return false;
        }

        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setReferenceId(getWorkflowActionDto().getReferenceId());
        requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        requestDTO.setDepartmentName(MainetConstants.Council.COUNCIL_MANAGEMENT);
        requestDTO.setServiceId(getServiceId());
        requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        ServiceMaster service = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                .getServiceMasterByShortCode(
                        MainetConstants.Council.Proposal.SERVICE_COUNCIL_PROPOSAL,
                        UserSession.getCurrent().getOrganisation().getOrgid());
        requestDTO.setDeptId(service.getTbDepartment().getDpDeptid());
        List<DocumentDetailsVO> docs = new ArrayList<>();
        DocumentDetailsVO document = new DocumentDetailsVO();
        document.setDocumentSerialNo(1L);
        docs.add(document);
        setAttachments(fileUpload.prepareFileUpload(docs));
        fileUpload.doFileUpload(getAttachments(), requestDTO);
        List<Long> attacheMentIds = ApplicationContextProvider.getApplicationContext()
                .getBean(IChecklistVerificationService.class).fetchAllAttachIdByReferenceId(
                        getWorkflowActionDto().getReferenceId(), UserSession.getCurrent().getOrganisation().getOrgid());
        prepareWorkFlowTaskAction(getWorkflowActionDto());
        getWorkflowActionDto().setAttachementId(attacheMentIds);

        ApplicationContextProvider.getApplicationContext().getBean(CouncilWorkFlowService.class)
                .updateWorkFlowProposalService(getWorkflowActionDto());

        WorkflowRequest workflowRequest = ApplicationContextProvider.getApplicationContext()
                .getBean(IWorkflowRequestService.class)
                .getWorkflowRequestByAppIdOrRefId(null, getCouProposalMasterDto().getProposalNo(),
                        UserSession.getCurrent().getOrganisation().getOrgid());

        if (workflowRequest != null
                && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.REJECTED)) {
            ApplicationContextProvider.getApplicationContext().getBean(ICouncilProposalMasterService.class).updateProposalStatus(
                    getCouProposalMasterDto().getProposalId(),
                    MainetConstants.FlagR);
        }

        if (workflowRequest != null && workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Status.CLOSED)
                && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)) {
            ApplicationContextProvider.getApplicationContext().getBean(ICouncilProposalMasterService.class).updateProposalStatus(
                    getCouProposalMasterDto().getProposalId(),
                    MainetConstants.FlagA);
        }

        if (workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.REJECTED)) {
            setSuccessMessage(
                    ApplicationSession.getInstance().getMessage("council.proposal.rejection"));

            this.setSuccessMessage(
                    ApplicationSession.getInstance().getMessage("council.proposal.creation"));

        }
       
		if (!sendBackFlag) {
			FileUploadDTO uploadDTO = new FileUploadDTO();
			Long deleteFileId = null;
			List<Long> removeYearIdList = null;
			 if (workflowRequest != null && workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Status.CLOSED)
		                && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)) {
				 couProposalMasterDto.setProposalStatus(MainetConstants.FlagA);
			 }
			ApplicationContextProvider.getApplicationContext().getBean(ICouncilProposalMasterService.class)
					.saveCouncil(couProposalMasterDto, getAttachments(), uploadDTO, deleteFileId, removeYearIdList);
		}
        couProposalMasterDto.setWfStatus(workflowRequest.getLastDecision());

        return true;

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

    public CouncilProposalMasterDto getCouProposalMasterDto() {
        return couProposalMasterDto;
    }

    public void setCouProposalMasterDto(CouncilProposalMasterDto couProposalMasterDto) {
        this.couProposalMasterDto = couProposalMasterDto;
    }

    public List<LookUp> getLookupListLevel1() {
        return lookupListLevel1;
    }

    public void setLookupListLevel1(List<LookUp> lookupListLevel1) {
        this.lookupListLevel1 = lookupListLevel1;
    }

    public List<WorkflowTaskActionWithDocs> getActionHistory() {
        return actionHistory;
    }

    public void setActionHistory(List<WorkflowTaskActionWithDocs> actionHistory) {
        this.actionHistory = actionHistory;
    }

    public List<DocumentDetailsVO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<DocumentDetailsVO> attachments) {
        this.attachments = attachments;
    }

    public String getProposalNumber() {
        return proposalNumber;
    }

    public void setProposalNumber(String proposalNumber) {
        this.proposalNumber = proposalNumber;
    }

    public List<AttachDocs> getAttachDocsList() {
        return attachDocsList;
    }

    public void setAttachDocsList(List<AttachDocs> attachDocsList) {
        this.attachDocsList = attachDocsList;
    }

	public List<AccountHeadSecondaryAccountCodeMasterEntity> getBudgetList() {
		return budgetList;
	}

	public void setBudgetList(List<AccountHeadSecondaryAccountCodeMasterEntity> budgetList) {
		this.budgetList = budgetList;
	}

	public List<TbFinancialyear> getFaYears() {
		return faYears;
	}

	public void setFaYears(List<TbFinancialyear> faYears) {
		this.faYears = faYears;
	}

	public String getCpdMode() {
		return cpdMode;
	}

	public void setCpdMode(String cpdMode) {
		this.cpdMode = cpdMode;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public List<AccountFundMasterBean> getFundList() {
		return fundList;
	}

	public void setFundList(List<AccountFundMasterBean> fundList) {
		this.fundList = fundList;
	}

	public Map<Long, String> getFieldList() {
		return fieldList;
	}

	public void setFieldList(Map<Long, String> fieldList) {
		this.fieldList = fieldList;
	}

	public String getViewFlag() {
		return viewFlag;
	}

	public void setViewFlag(String viewFlag) {
		this.viewFlag = viewFlag;
	}

	public Boolean getSendBackFlag() {
		return sendBackFlag;
	}

	public void setSendBackFlag(Boolean sendBackFlag) {
		this.sendBackFlag = sendBackFlag;
	}
	
}
