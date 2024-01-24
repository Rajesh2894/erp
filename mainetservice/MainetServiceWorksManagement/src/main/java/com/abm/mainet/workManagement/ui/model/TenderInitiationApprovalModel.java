package com.abm.mainet.workManagement.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.workManagement.dto.TenderMasterDto;
import com.abm.mainet.workManagement.dto.WorkDefinitionDto;
import com.abm.mainet.workManagement.service.TenderInitiationService;
import com.abm.mainet.workManagement.service.WorkDefinitionService;
import com.abm.mainet.workManagement.service.WorksWorkFlowService;

/**
 * @author vishwajeet.kumar
 * @since 05 feb 2020
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class TenderInitiationApprovalModel extends AbstractFormModel {

    private static final long serialVersionUID = -5273303826977145809L;

    @Autowired
    public IFileUploadService fileUpload;

    @Autowired
    private TenderInitiationService tenderInitiationService;

    @Autowired
    private WorkDefinitionService workDefinitionService;

	@Resource
	TbDepartmentService departmentService;
	
    private TenderMasterDto initiationDto;
    private List<TbDepartment> departmentList;
    private WorkflowTaskAction workflowActionDto = new WorkflowTaskAction();
    private List<DocumentDetailsVO> attachments = new ArrayList<>();
    private String initiationNo;
    private String tndApprovalStatus;
    private String completedFlag;
    
    private List<WorkDefinitionDto> workList;
    private List<LookUp> venderCategoryList = new ArrayList<>();
    private List<LookUp> tenderTpyes;
    private List<LookUp> workDurationUnit = new ArrayList<>();

    public boolean saveTenderApproval() {
    	ServiceMaster service = null;
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setReferenceId(getWorkflowActionDto().getReferenceId());
        requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
             
        
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)
				&& (MainetConstants.DEPT_SHORT_NAME.STORE).equalsIgnoreCase(departmentService.findDepartmentShortCodeByDeptId(initiationDto.getDeptId(), 
						UserSession.getCurrent().getOrganisation().getOrgid()))) {
	       	requestDTO.setDepartmentName(MainetConstants.DEPT_SHORT_NAME.STORE);
        	service = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                    .getServiceMasterByShortCode(MainetConstants.ServiceShortCode.STORE_TND_SHORT_CODE,
					initiationDto.getOrgId());			
		}else {
			requestDTO.setDepartmentName(MainetConstants.WorksManagement.WORKS_MANAGEMENT);
			service = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
	                .getServiceMasterByShortCode(MainetConstants.WorksManagement.TND_SHORT_CODE,
	                        UserSession.getCurrent().getOrganisation().getOrgid());			
		}
        
        requestDTO.setDeptId(service.getTbDepartment().getDpDeptid());
        requestDTO.setServiceId(service.getSmServiceId());
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
        ApplicationContextProvider.getApplicationContext().getBean(WorksWorkFlowService.class)
                .updateWorkFlowWorksService(getWorkflowActionDto(), service.getSmServiceId(),
                        UserSession.getCurrent().getOrganisation().getOrgid());

        WorkflowRequest workflowRequest = ApplicationContextProvider.getApplicationContext()
                .getBean(IWorkflowRequestService.class)
                .getWorkflowRequestByAppIdOrRefId(null, getWorkflowActionDto().getReferenceId(),
                        UserSession.getCurrent().getOrganisation().getOrgid());

        if (workflowRequest != null
                && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.REJECTED)) {
            tenderInitiationService.updateTenderStatus(UserSession.getCurrent().getOrganisation().getOrgid(),
                    getWorkflowActionDto().getReferenceId(), MainetConstants.FlagR);
        } else if (workflowRequest != null
                && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)
                && workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Status.CLOSED)) {
            tenderInitiationService.updateTenderStatus(UserSession.getCurrent().getOrganisation().getOrgid(),
                    getWorkflowActionDto().getReferenceId(), MainetConstants.FlagA);

            List<Long> worksId = new ArrayList<>();
            this.getInitiationDto().getWorkDto().forEach(work -> {
                worksId.add(work.getWorkId());
            });
            workDefinitionService.updateWorksStatusToInitiated(worksId);
            this.setInitiationNo(getWorkflowActionDto().getReferenceId());
        }
        this.setTndApprovalStatus(workflowRequest.getLastDecision());
        return true;
    }

    private WorkflowTaskAction prepareWorkFlowTaskAction(WorkflowTaskAction workflowActionDto) {

        getWorkflowActionDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        workflowActionDto.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
        workflowActionDto.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
        workflowActionDto.setEmpName(
                UserSession.getCurrent().getEmployee().getEmpname() + " "
                        + UserSession.getCurrent().getEmployee().getEmplname());
        workflowActionDto.setEmpEmail(UserSession.getCurrent().getEmployee().getEmpemail());
        workflowActionDto.setDateOfAction(new Date());
        workflowActionDto.setCreatedDate(new Date());
        workflowActionDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        workflowActionDto.setReferenceId(getWorkflowActionDto().getReferenceId());
        workflowActionDto.setPaymentMode(MainetConstants.FlagF);
        workflowActionDto.setIsFinalApproval(false);
        return workflowActionDto;

    }

    public TenderMasterDto getInitiationDto() {
        return initiationDto;
    }

    public void setInitiationDto(TenderMasterDto initiationDto) {
        this.initiationDto = initiationDto;
    }

    public List<TbDepartment> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<TbDepartment> departmentList) {
        this.departmentList = departmentList;
    }

    public WorkflowTaskAction getWorkflowActionDto() {
        return workflowActionDto;
    }

    public void setWorkflowActionDto(WorkflowTaskAction workflowActionDto) {
        this.workflowActionDto = workflowActionDto;
    }

    public List<DocumentDetailsVO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<DocumentDetailsVO> attachments) {
        this.attachments = attachments;
    }

    public String getInitiationNo() {
        return initiationNo;
    }

    public void setInitiationNo(String initiationNo) {
        this.initiationNo = initiationNo;
    }

    public String getTndApprovalStatus() {
        return tndApprovalStatus;
    }

    public void setTndApprovalStatus(String tndApprovalStatus) {
        this.tndApprovalStatus = tndApprovalStatus;
    }

	public List<WorkDefinitionDto> getWorkList() {
		return workList;
	}

	public void setWorkList(List<WorkDefinitionDto> workList) {
		this.workList = workList;
	}

	public List<LookUp> getVenderCategoryList() {
		return venderCategoryList;
	}

	public void setVenderCategoryList(List<LookUp> venderCategoryList) {
		this.venderCategoryList = venderCategoryList;
	}

	public List<LookUp> getTenderTpyes() {
		return tenderTpyes;
	}

	public void setTenderTpyes(List<LookUp> tenderTpyes) {
		this.tenderTpyes = tenderTpyes;
	}

	public List<LookUp> getWorkDurationUnit() {
		return workDurationUnit;
	}

	public void setWorkDurationUnit(List<LookUp> workDurationUnit) {
		this.workDurationUnit = workDurationUnit;
	}

	public String getCompletedFlag() {
		return completedFlag;
	}

	public void setCompletedFlag(String completedFlag) {
		this.completedFlag = completedFlag;
	}
    
    

}
