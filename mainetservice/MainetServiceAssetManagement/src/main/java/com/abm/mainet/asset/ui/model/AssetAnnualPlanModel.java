package com.abm.mainet.asset.ui.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.abm.mainet.asset.service.IAssetAnnualPlanService;
import com.abm.mainet.asset.service.IAssetWorkflowService;
import com.abm.mainet.asset.ui.dto.AssetAnnualPlanDTO;
import com.abm.mainet.asset.ui.dto.AssetAnnualPlanDetailsDTO;
import com.abm.mainet.asset.ui.validator.AssetDetailsValidator;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionResponse;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;

@Component
public class AssetAnnualPlanModel extends AbstractFormModel {

    private static final long serialVersionUID = -3294621080714751871L;

    private String approvalViewFlag;
    private String saveMode;
    // private Map<Long, String> financialYearMap = new HashMap<>();
    private List<TbFinancialyear> faYears = new ArrayList<>();
    private AssetAnnualPlanDTO astAnnualPlanDTO;
    private List<AssetAnnualPlanDetailsDTO> astAnnualPlanDetDTO = new ArrayList<>();
    private List<AssetAnnualPlanDTO> astAnnualPlanDTOs = new ArrayList<AssetAnnualPlanDTO>();
    private List<TbDepartment> departmentsList;
    private List<TbLocationMas> locList = new ArrayList<>();
    private Long taskId;
    private List<DocumentDetailsVO> attachments = new ArrayList<>();
    private List<LookUp> astClassMovList = new ArrayList<>();
    private String completedFlag;

    public String getApprovalViewFlag() {
        return approvalViewFlag;
    }

    public void setApprovalViewFlag(String approvalViewFlag) {
        this.approvalViewFlag = approvalViewFlag;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public List<TbFinancialyear> getFaYears() {
        return faYears;
    }

    public void setFaYears(List<TbFinancialyear> faYears) {
        this.faYears = faYears;
    }

    public AssetAnnualPlanDTO getAstAnnualPlanDTO() {
        return astAnnualPlanDTO;
    }

    public void setAstAnnualPlanDTO(AssetAnnualPlanDTO astAnnualPlanDTO) {
        this.astAnnualPlanDTO = astAnnualPlanDTO;
    }

    public List<AssetAnnualPlanDetailsDTO> getAstAnnualPlanDetDTO() {
        return astAnnualPlanDetDTO;
    }

    public void setAstAnnualPlanDetDTO(List<AssetAnnualPlanDetailsDTO> astAnnualPlanDetDTO) {
        this.astAnnualPlanDetDTO = astAnnualPlanDetDTO;
    }

    public List<AssetAnnualPlanDTO> getAstAnnualPlanDTOs() {
        return astAnnualPlanDTOs;
    }

    public void setAstAnnualPlanDTOs(List<AssetAnnualPlanDTO> astAnnualPlanDTOs) {
        this.astAnnualPlanDTOs = astAnnualPlanDTOs;
    }

    public List<TbDepartment> getDepartmentsList() {
        return departmentsList;
    }

    public void setDepartmentsList(List<TbDepartment> departmentsList) {
        this.departmentsList = departmentsList;
    }

    public List<TbLocationMas> getLocList() {
        return locList;
    }

    public void setLocList(List<TbLocationMas> locList) {
        this.locList = locList;
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

    public List<LookUp> getAstClassMovList() {
        return astClassMovList;
    }

    public void setAstClassMovList(List<LookUp> astClassMovList) {
        this.astClassMovList = astClassMovList;
    }
    
    

    public String getCompletedFlag() {
		return completedFlag;
	}

	public void setCompletedFlag(String completedFlag) {
		this.completedFlag = completedFlag;
	}



	@Autowired
    IAssetAnnualPlanService annualPlanService;

    @Autowired
    public IFileUploadService fileUpload;

    @Autowired
    private IChecklistVerificationService checkListService;

    @Autowired
    private IAssetWorkflowService assetWorkFlowService;

    @Override
    public boolean saveForm() {
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

        getAstAnnualPlanDTO().setOrgId(orgId);
        getAstAnnualPlanDTO().setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        getAstAnnualPlanDTO().setCreatedDate(new Date());
        getAstAnnualPlanDTO().setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());

        astAnnualPlanDetDTO.forEach(annualPlan -> {
            annualPlan.setOrgId(orgId);
            annualPlan.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            annualPlan.setCreatedDate(new Date());
            annualPlan.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
        });

        getAstAnnualPlanDTO().setAstAnnualPlanDetailsDTO(astAnnualPlanDetDTO);

        // set all relevant Work flow Task Action Data For initiating Work Flow - initial request
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
                MainetConstants.AssetManagement.AST_INVENTORY_SERV_SHORTCODE,
                MainetConstants.ITAssetManagement.IT_AST_INVENTORY_SERVICE_CODE);
        astAnnualPlanDTO.setDeptCode(UserSession.getCurrent().getModuleDeptCode());
        astAnnualPlanDTO.setStatus(MainetConstants.AssetManagement.STATUS.SUBMITTED);
        String referenceId = annualPlanService.saveInAssetInventoryPlan(astAnnualPlanDTO, getWorkflowActionDto(),
                orgId, data.get(MainetConstants.ITAssetManagement.MODULE_DEPT_KEY),
                data.get(MainetConstants.ITAssetManagement.SERVICE_CODE_KEY));

        if (referenceId != null) {
            setSuccessMessage(ApplicationSession.getInstance().getMessage("asset.annualPlan.savesuccessmsg",
                    new Object[] { referenceId }));
        } else {
            setSuccessMessage(ApplicationSession.getInstance().getMessage("asset.requisition.workflowfailedmsg"));
        }

        return true;

    }

    public boolean updateApprovalFlag(Long orgId, String moduleDeptCode) {
        // T#92467
        Map<String, String> data = AssetDetailsValidator.getModuleDeptAndServiceCode(moduleDeptCode,
                MainetConstants.AssetManagement.AST_INVENTORY_SERV_SHORTCODE,
                MainetConstants.ITAssetManagement.IT_AST_INVENTORY_SERVICE_CODE);

        ServiceMaster serviceMast = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                .getServiceMasterByShortCode(data.get(MainetConstants.ITAssetManagement.SERVICE_CODE_KEY),
                        UserSession.getCurrent().getOrganisation().getOrgid());
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setReferenceId(getWorkflowActionDto().getReferenceId());
        requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        requestDTO.setDepartmentName(data.get(MainetConstants.ITAssetManagement.MODULE_DEPT_KEY));
        requestDTO.setServiceId(serviceMast.getSmServiceId());
        requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        requestDTO.setApplicationId(getAstAnnualPlanDTO().getAstAnnualPlanId());
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
                MainetConstants.AssetManagement.ASSET_ANNUAL_INVENTORY_URL, MainetConstants.FlagU,
                data.get(MainetConstants.ITAssetManagement.SERVICE_CODE_KEY));

        if (response != null && workFlowTaskAction.getDecision().equalsIgnoreCase(MainetConstants.WorkFlow.Decision.APPROVED)
                && !response.getIsProcessAlive()) {
            getAstAnnualPlanDTO().setStatus(MainetConstants.AssetManagement.STATUS.APPROVED);
        } else if (response != null && !response.getIsProcessAlive()
                && workFlowTaskAction.getDecision().equalsIgnoreCase(MainetConstants.WorkFlow.Decision.REJECTED)) {
            getAstAnnualPlanDTO().setStatus(MainetConstants.AssetManagement.STATUS.REJECTED);
        } else if (response != null && response.getIsProcessAlive()) {
            getAstAnnualPlanDTO().setStatus(MainetConstants.AssetManagement.STATUS.IN_PROCESS);
            // return true;
        }
        // D#85168 update the status field inside TB_AST_ANNUAL_PLAN_MST
        getAstAnnualPlanDTO().setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        getAstAnnualPlanDTO().setUpdatedDate(new Date());
        getAstAnnualPlanDTO().setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
        annualPlanService.updateAnnualPlan(getAstAnnualPlanDTO());
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

}
