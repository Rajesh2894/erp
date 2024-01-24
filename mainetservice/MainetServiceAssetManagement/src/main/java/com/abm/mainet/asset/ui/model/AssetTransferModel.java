/**
 * 
 */
package com.abm.mainet.asset.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.asset.service.ITransferService;
import com.abm.mainet.asset.ui.dto.TransferDTO;
import com.abm.mainet.asset.ui.validator.AssetDetailsValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.asset.dto.AuditDetailsDTO;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;

/**
 * @author sarojkumar.yadav
 *
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class AssetTransferModel extends AbstractFormModel {

    private static final Logger LOGGER = Logger.getLogger(AssetTransferModel.class);

    /**
     * 
     */
    private static final long serialVersionUID = 4818885177426425972L;

    @Autowired
    private ITransferService service;

    @Autowired
    private ServiceMasterService iServiceMasterService;

    private TransferDTO transDTO = new TransferDTO();
    private List<TbDepartment> departmentsList;
    private List<TbLocationMas> locList = new ArrayList<>();
    private List<EmployeeBean> empList = new ArrayList<>();
    private AuditDetailsDTO audit = new AuditDetailsDTO();
    private List<LookUp> accountHead = new ArrayList<>();
    private Long taskId;

    private List<LookUp> lookUpList = new ArrayList<>();
    private Boolean clickFromNode = false;
    private String completedFlag;

    // transfer asset document upload changes starts -->
    private List<AttachDocs> attachDocsList = new ArrayList<>();
    private List<DocumentDetailsVO> attachments = new ArrayList<>();
    // transfer asset document upload changes ends -->

    public TransferDTO getTransDTO() {
        return transDTO;
    }

    public void setTransDTO(TransferDTO transDTO) {
        this.transDTO = transDTO;
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

    public List<EmployeeBean> getEmpList() {
        return empList;
    }

    public void setEmpList(List<EmployeeBean> empList) {
        this.empList = empList;
    }

    public AuditDetailsDTO getAudit() {
        return audit;
    }

    public void setAudit(AuditDetailsDTO audit) {
        this.audit = audit;
    }

    public List<LookUp> getAccountHead() {
        return accountHead;
    }

    public void setAccountHead(List<LookUp> accountHead) {
        this.accountHead = accountHead;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public List<LookUp> getLookUpList() {
        return lookUpList;
    }

    public void setLookUpList(List<LookUp> lookUpList) {
        this.lookUpList = lookUpList;
    }

    public Boolean getClickFromNode() {
        return clickFromNode;
    }

    public void setClickFromNode(Boolean clickFromNode) {
        this.clickFromNode = clickFromNode;
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
  
    public String getCompletedFlag() {
		return completedFlag;
	}

	public void setCompletedFlag(String completedFlag) {
		this.completedFlag = completedFlag;
	}

	@Override
    public boolean saveForm() {
        boolean status = false;
        String referenceId = StringUtils.EMPTY;// used for display reference no on POPUP
        final TransferDTO dto = getTransDTO();

        validateConstraints(dto, TransferDTO.class, getBindingResult());
        if (hasValidationErrors()) {
            return false;
        }
        try {
            FileUploadDTO uploadDTO = new FileUploadDTO();
            uploadDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            uploadDTO.setStatus(MainetConstants.FlagA);
            uploadDTO.setDepartmentName(MainetConstants.AssetManagement.ASSET_MANAGEMENT);
            uploadDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());

            setAttachments(ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
                    .setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));

            dto.setPostDate(new Date());
            dto.setDocDate(dto.getPostDate());
            // T#92467
            Map<String, String> data = AssetDetailsValidator.getModuleDeptAndServiceCode(
                    UserSession.getCurrent().getModuleDeptCode(),
                    MainetConstants.AssetManagement.TRF_APPR_SERV_SHORTCODE,
                    MainetConstants.ITAssetManagement.IT_TRF_SERVICE_CODE);
            dto.setDeptCode(UserSession.getCurrent().getModuleDeptCode());

            // D#135788 TB_ATTACH_CFC Prepare file upload
            ServiceMaster serviceMast = iServiceMasterService
                    .getServiceMasterByShortCode(data.get(MainetConstants.ITAssetManagement.SERVICE_CODE_KEY),
                            UserSession.getCurrent().getOrganisation().getOrgid());
            RequestDTO requestDTO = new RequestDTO();
            // requestDTO.setReferenceId(refId);
            requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            requestDTO.setDepartmentName(data.get(MainetConstants.ITAssetManagement.MODULE_DEPT_KEY));
            requestDTO.setServiceId(serviceMast.getSmServiceId());
            requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());

            requestDTO.setDeptId(serviceMast.getTbDepartment().getDpDeptid());
            referenceId = service.saveTransferReq(dto, audit, "", data.get(MainetConstants.ITAssetManagement.MODULE_DEPT_KEY),
                    data.get(MainetConstants.ITAssetManagement.SERVICE_CODE_KEY), getAttachments(), requestDTO);
            status = true;
            // D39874
            setSuccessMessage(ApplicationSession.getInstance().getMessage("asset.transfer.request.success",
                    new Object[] { referenceId }));
        } catch (FrameworkException exp) {
            LOGGER.error("Error occured while transferring asset: ", exp);
            status = false;
        }
        return status;
    }

    public void loadTransferDetails() {
        WorkflowTaskAction wfTask = this.getWorkflowActionDto();
        String uid = wfTask.getReferenceId();
        String trfId = uid.substring(uid.lastIndexOf(MainetConstants.AssetManagement.WF_TXN_IDEN_TRF) + 4);
        this.setTransDTO(service.getDetails(Long.valueOf(trfId)));
    }

    public boolean apprTransferAction(String moduleDeptCode) {
        boolean retFlag = false;
        WorkflowTaskAction wfAction = this.getWorkflowActionDto();
        String decFlag = wfAction.getDecision();
        AuditDetailsDTO auditDto = new AuditDetailsDTO();
        UserSession userSession = UserSession.getCurrent();
        auditDto.setEmpId(userSession.getEmployee().getEmpId());
        auditDto.setEmpIpMac(userSession.getEmployee().getEmppiservername());
        // T#92467
        Map<String, String> data = AssetDetailsValidator.getModuleDeptAndServiceCode(
                moduleDeptCode,
                MainetConstants.AssetManagement.TRF_APPR_SERV_SHORTCODE,
                MainetConstants.ITAssetManagement.IT_TRF_SERVICE_CODE);

        service.executeWfAction(getWorkflowActionDto().getReferenceId(), auditDto, wfAction,
                data.get(MainetConstants.ITAssetManagement.MODULE_DEPT_KEY),
                data.get(MainetConstants.ITAssetManagement.SERVICE_CODE_KEY));
        if (decFlag != null && decFlag.equals(MainetConstants.WorkFlow.Decision.APPROVED)) {
            retFlag = true;
        } else if (decFlag != null && decFlag.equals(MainetConstants.WorkFlow.Decision.REJECTED)) {
            retFlag = true;
        }
        return retFlag;
    }

}
