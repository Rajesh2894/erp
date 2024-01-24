package com.abm.mainet.asset.ui.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.abm.mainet.asset.domain.AssetInformation;
import com.abm.mainet.asset.service.IAssetRequisitionService;
import com.abm.mainet.asset.service.IAssetWorkflowService;
import com.abm.mainet.asset.service.IInformationService;
import com.abm.mainet.asset.ui.dto.AssetRequisitionDTO;
import com.abm.mainet.asset.ui.dto.SummaryDTO;
import com.abm.mainet.asset.ui.validator.AssetDetailsValidator;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.asset.dto.AssetInformationDTO;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;

import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionResponse;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;

@Component
public class AssetRequisitionModel extends AbstractFormModel {

    private static final long serialVersionUID = -7360254174970249191L;
    private AssetRequisitionDTO astRequisitionDTO;
    private List<AssetRequisitionDTO> requisitionList = new ArrayList<AssetRequisitionDTO>();
    private List<TbDepartment> departmentsList; 
    private String saveMode;
    private List<TbLocationMas> locList = new ArrayList<>();
    private String approvalViewFlag;
    private Long taskId;
    private List<DocumentDetailsVO> attachments = new ArrayList<>();
    private List<Employee> empList =new ArrayList<>();
    private List<String> astCodeList = new ArrayList<>();
    private List<String> serialNoList = new ArrayList<>();
    private String approvalLastFlag;
    private String completedFlag;
    private Long levelCheck;
    private List<AssetInformationDTO> astlist= new ArrayList<>();
    private boolean lastChecker;
    
  
    
    

    public AssetRequisitionDTO getAstRequisitionDTO() {
        return astRequisitionDTO;
    }

    public void setAstRequisitionDTO(AssetRequisitionDTO astRequisitionDTO) {
        this.astRequisitionDTO = astRequisitionDTO;
    }

    public List<AssetRequisitionDTO> getRequisitionList() {
        return requisitionList;
    }

    public void setRequisitionList(List<AssetRequisitionDTO> requisitionList) {
        this.requisitionList = requisitionList;
    }

    public List<TbDepartment> getDepartmentsList() {
        return departmentsList;
    }

    public void setDepartmentsList(List<TbDepartment> departmentsList) {
        this.departmentsList = departmentsList;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public List<TbLocationMas> getLocList() {
        return locList;
    }

    public void setLocList(List<TbLocationMas> locList) {
        this.locList = locList;
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

    public List<DocumentDetailsVO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<DocumentDetailsVO> attachments) {
        this.attachments = attachments;
    }

    public List<Employee> getEmpList() {
		return empList;
	}

	public void setEmpList(List<Employee> empList) {
		this.empList = empList;
	}

	public List<String> getAstCodeList() {
		return astCodeList;
	}

	public void setAstCodeList(List<String> astCodeList) {
		this.astCodeList = astCodeList;
	}

	public List<String> getSerialNoList() {
		return serialNoList;
	}

	public void setSerialNoList(List<String> serialNoList) {
		this.serialNoList = serialNoList;
	}

	public String getApprovalLastFlag() {
		return approvalLastFlag;
	}

	public void setApprovalLastFlag(String approvalLastFlag) {
		this.approvalLastFlag = approvalLastFlag;
	}
	
	

	public String getCompletedFlag() {
		return completedFlag;
	}

	public void setCompletedFlag(String completedFlag) {
		this.completedFlag = completedFlag;
	}

	


	public Long getLevelCheck() {
		return levelCheck;
	}

	public void setLevelCheck(Long levelCheck) {
		this.levelCheck = levelCheck;
	}




	public List<AssetInformationDTO> getAstlist() {
		return astlist;
	}

	public void setAstlist(List<AssetInformationDTO> astlist) {
		this.astlist = astlist;
	}




	@Autowired
    IAssetRequisitionService assetRequisitionService;

    @Autowired
    public IFileUploadService fileUpload;

    @Autowired
    private IChecklistVerificationService checkListService;

    @Autowired
    private IAssetWorkflowService assetWorkFlowService;
    
    @Autowired
    private IInformationService astService;

    @Override
    public boolean saveForm() {
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        astRequisitionDTO.setOrgId(orgId);
        astRequisitionDTO.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        astRequisitionDTO.setCreationDate(new Date());
        astRequisitionDTO.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());

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
        // T#92467
        Map<String, String> data = AssetDetailsValidator.getModuleDeptAndServiceCode(UserSession.getCurrent().getModuleDeptCode(),
                MainetConstants.AssetManagement.AST_REQ_SERV_SHORTCODE,
                MainetConstants.ITAssetManagement.IT_AST_REQ_SERVICE_CODE);
        astRequisitionDTO.setDeptCode(UserSession.getCurrent().getModuleDeptCode());
        astRequisitionDTO.setStatus(MainetConstants.AssetManagement.STATUS.SUBMITTED);
        String referenceId = assetRequisitionService.saveInRequisition(astRequisitionDTO, getWorkflowActionDto(),
                MainetConstants.FlagA, orgId, data.get(MainetConstants.ITAssetManagement.MODULE_DEPT_KEY),
                data.get(MainetConstants.ITAssetManagement.SERVICE_CODE_KEY));
        if (StringUtils.isNotEmpty(referenceId)) {
            setSuccessMessage(ApplicationSession.getInstance().getMessage("asset.requisition.savesuccess",
                    new Object[] { referenceId }));
        } else {
           this.addValidationError(ApplicationSession.getInstance().getMessage("asset.requisition.workflowfailedmsg"));
           return false;
        }

        return true;

    }

  
	public boolean updateApprovalFlag(Long orgId, String moduleDeptCode) {
        // T#92467
        Map<String, String> data = AssetDetailsValidator.getModuleDeptAndServiceCode(moduleDeptCode,
                MainetConstants.AssetManagement.AST_REQ_SERV_SHORTCODE,
                MainetConstants.ITAssetManagement.IT_AST_REQ_SERVICE_CODE);

        ServiceMaster serviceMast = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                .getServiceMasterByShortCode(data.get(MainetConstants.ITAssetManagement.SERVICE_CODE_KEY),
                        UserSession.getCurrent().getOrganisation().getOrgid());
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setReferenceId(getWorkflowActionDto().getReferenceId());
        requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        requestDTO.setDepartmentName(data.get(MainetConstants.ITAssetManagement.MODULE_DEPT_KEY));
        requestDTO.setServiceId(serviceMast.getSmServiceId());
        requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        requestDTO.setApplicationId(astRequisitionDTO.getAssetRequisitionId());
        requestDTO.setDeptId(serviceMast.getTbDepartment().getDpDeptid());

        // astDetailsDTO
        // fileUpload.doFileUpload(getAttachments(), requestDTO);
        this.attachments = setDocumentsDetailVO();
        setAttachments(fileUpload.prepareFileUpload(getAttachments()));
        fileUpload.doFileUpload(getAttachments(), requestDTO);
        List<Long> attacheMentIds = checkListService.fetchAllAttachIdByReferenceId(
                getWorkflowActionDto().getReferenceId(), UserSession.getCurrent().getOrganisation().getOrgid());
        getWorkflowActionDto().setAttachementId(attacheMentIds);

        boolean lastApproval = ApplicationContextProvider.getApplicationContext().getBean(IWorkFlowTypeService.class)
                .isLastTaskInCheckerTaskList(getWorkflowActionDto().getTaskId());

        WorkflowTaskAction workFlowTaskAction = getWorkflowActionDto();
        WorkflowTaskAction workflowActionDtoTmp = prepareWorkFlowTaskActionUpdate(workFlowTaskAction);
        WorkflowTaskActionResponse response = assetWorkFlowService.initiateWorkFlowAssetService(workflowActionDtoTmp, getTaskId(),
                MainetConstants.AssetManagement.ASSET_REQUISITION_URL, MainetConstants.FlagU,
                data.get(MainetConstants.ITAssetManagement.SERVICE_CODE_KEY));
        if (response != null && workFlowTaskAction.getDecision().equalsIgnoreCase(MainetConstants.WorkFlow.Decision.APPROVED)
                && !response.getIsProcessAlive()) {
            astRequisitionDTO.setStatus(MainetConstants.AssetManagement.STATUS.APPROVED);
        } else if (response != null && !response.getIsProcessAlive()
                && workFlowTaskAction.getDecision().equalsIgnoreCase(MainetConstants.WorkFlow.Decision.REJECTED)) {
            astRequisitionDTO.setStatus(MainetConstants.AssetManagement.STATUS.REJECTED);
        } else if (response != null && response.getIsProcessAlive()) {
            // return true;
            astRequisitionDTO.setStatus(MainetConstants.AssetManagement.STATUS.IN_PROCESS);
        }
        // D#89523 update the status field inside TB_AST_REQUISITION
        astRequisitionDTO.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        astRequisitionDTO.setUpdatedDate(new Date());
        astRequisitionDTO.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
        assetRequisitionService.updateRequisition(astRequisitionDTO);
		if (lastApproval) {
			String depCode = ApplicationContextProvider.getApplicationContext().getBean(TbDepartmentService.class).findDepartmentShortCodeByDeptId(astRequisitionDTO.getAstDept(), orgId);
			List<String> ids=new ArrayList<>();
			for (final SummaryDTO astDto : astRequisitionDTO.getDto()) {
				
				AssetInformationDTO dto = astService.getInfoByCodeAndSerialNo(orgId,astDto.getAstCode(),astDto.getSerialNo());
				if(dto!=null) {
					ids.add(String.valueOf(dto.getAssetId()));
				}
				
				else {
					AssetInformation entity= astService.getAssetCodeIdentifier(orgId, astDto.getAstCode(), astDto.getSerialNo());
					if (entity != null) {
						ids.add(String.valueOf(entity.getAssetId()));
						}
				}
				
				
				astService.upDateEmpDept(orgId, astDto.getSerialNo(), astDto.getAstCode(), astRequisitionDTO.getEmpId(),depCode, astRequisitionDTO.getDispatchedDate(),astRequisitionDTO.getAstLoc());
			}
			if (ids.size() > 0) {
				astService.updateAssetIdbySerialNo(astRequisitionDTO.getAssetRequisitionId(), orgId, String.join(",",  ids));
			}
		}
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
        workflowActionDto.setTaskId(getTaskId());
        return workflowActionDto;

    }

	public boolean isLastChecker() {
		return lastChecker;
	}

	public void setLastChecker(boolean lastChecker) {
		this.lastChecker = lastChecker;
	}

}
