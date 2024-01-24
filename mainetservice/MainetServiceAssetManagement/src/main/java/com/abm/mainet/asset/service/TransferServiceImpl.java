/**
 * 
 */
package com.abm.mainet.asset.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.asset.domain.AssetInformation;
import com.abm.mainet.asset.domain.AssetTransfer;
import com.abm.mainet.asset.domain.AssetTransferHistory;
import com.abm.mainet.asset.domain.InformationHistory;
import com.abm.mainet.asset.mapper.TransferMapper;
import com.abm.mainet.asset.repository.TransferRepo;
import com.abm.mainet.asset.ui.dto.TransferDTO;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.asset.dto.AssetClassificationDTO;
import com.abm.mainet.common.integration.asset.dto.AuditDetailsDTO;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionResponse;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;

/**
 * @author sarojkumar.yadav
 *
 */
@Service
public class TransferServiceImpl implements ITransferService {

    @Autowired
    private TransferRepo transfer;
    @Autowired
    private AuditService auditService;

    @Autowired
    private IInformationService infoService;

    @Autowired
    private IClassificationInfoService classService;
    @Autowired
    private IWorkflowTyepResolverService iWorkflowTyepResolverService;
    @Autowired
    private ServiceMasterService iServiceMasterService;
    @Autowired
    private TbDepartmentService iTbDepartmentService;
    @Autowired
    public IFileUploadService fileUpload;
    @Resource
    private IAssetWorkflowService assetWorkFlowService;
    @Resource
    private IChecklistVerificationService checkListService;
    @Autowired
    private IEmployeeService empService;

    private static final Logger LOGGER = Logger.getLogger(TransferServiceImpl.class);

    /**
     * Store Asset Transfer request in database
     * 
     * @param transferDTO
     * @param auditDTO
     * @param workfloFlag if YES then initiate workFlow else not
     */
    @Override
    @Transactional
    public String saveTransferReq(final TransferDTO dto, final AuditDetailsDTO audit, final String workFlowFlag,
            String moduleDeptCode, String serviceCodeDeptWise, List<DocumentDetailsVO> attachmentList,
            RequestDTO requestDTO) {
        final AssetTransferHistory entityHistory = new AssetTransferHistory();
        dto.setCreatedBy(audit.getEmpId());
        dto.setLgIpMac(audit.getEmpIpMac());
        dto.setCreationDate(new Date());
        AssetTransfer entity = TransferMapper.mapToEntity(dto);
        try {
        	entity.setTransferDepartment(dto.getTransferDepartment());
            entity = transfer.save(entity);
        } catch (Exception exception) {
            LOGGER.error("Exception occur while saving asset Transfer request ", exception);
            throw new FrameworkException("Exception occur while saving asset Transfer request ", exception);
        }
        String astIdStr = dto.getAssetSrNo() + "/" + MainetConstants.AssetManagement.WF_TXN_IDEN_TRF + "/"
                + entity.getTransferId();
        // file upload changes starts here -->
        List<Long> attachmentId = new ArrayList<Long>();
        if (attachmentList != null && !attachmentList.isEmpty()) {
            /*
             * fileUploadDTO.setIdfId(MainetConstants.AssetManagement.ASSET_MANAGEMENT + MainetConstants.WINDOWS_SLASH +
             * entity.getTransferId()); fileUpload.doMasterFileUpload(attachmentList, fileUploadDTO);
             */
            requestDTO.setApplicationId(entity.getTransferId());
            fileUpload.doFileUpload(attachmentList, requestDTO);
            attachmentId = checkListService.fetchAttachmentIdByAppid(entity.getTransferId(), dto.getOrgId());
        }

        // file upload changes ends here -->
        WorkflowTaskActionResponse response = initiateWorkFlow(dto.getOrgId(), audit, astIdStr, moduleDeptCode,
                serviceCodeDeptWise, attachmentId);

        if (response != null) {
            // pass astIdStr for astAppNo
            infoService.updateAppStatusFlag(dto.getAssetCode(), dto.getOrgId(),
                    MainetConstants.AssetManagement.APPROVAL_STATUS_PENDING, astIdStr);
        }
        try {
        	infoService.updateAssetHistory(dto.getAssetCode(), dto.getOrgId(), dto.getCurrentEmployee());
        	entityHistory.setAssetCode(entity.getAssetId());
            entityHistory.setHistoryFlag(MainetConstants.InsertMode.ADD.getStatus());
          
            
            AssetTransfer copyE  =new AssetTransfer();
            
            BeanUtils.copyProperties(entity, copyE);
            copyE.setTransferEmployee(dto.getCurrentEmployee());
            copyE.setTransferLocation(dto.getCurrentLocation());
            copyE.setTransferDepartment(dto.getDepartment());
            auditService.createHistory(copyE, entityHistory);
           
        } catch (Exception ex) {
            LOGGER.error("Error occured while recording history: ", ex);
        }

        return astIdStr;
    }

    /**
     * It is used to initiate workflow
     * 
     * @param orgId
     * @param serialNo
     * @param asstId
     */
    private WorkflowTaskActionResponse initiateWorkFlow(final Long orgId, final AuditDetailsDTO auditDto, String referenceId,
            String moduleDeptCode, String serviceCodeDeptWise, List<Long> attachmentId) {
        WorkflowTaskActionResponse response = null;
        try {
            TbDepartment deptObj = iTbDepartmentService.findDeptByCode(orgId, MainetConstants.FlagA, moduleDeptCode);
            ServiceMaster sm = iServiceMasterService
                    .getServiceMasterByShortCode(serviceCodeDeptWise, orgId);
            // Code related to work flow
            WorkflowMas workFlowMas = iWorkflowTyepResolverService.resolveServiceWorkflowType(orgId, deptObj.getDpDeptid(),
                    sm.getSmServiceId(), null, null, null, null, null);
            response = assetWorkFlowService.initiateWorkFlowAssetService(
                    prepareWorkFlowTaskActionCreate(referenceId, auditDto, orgId, attachmentId),
                    workFlowMas.getWfId(), "AssetTransfer.html", MainetConstants.FlagA, serviceCodeDeptWise);
        } catch (Exception ex) {
            LOGGER.error("Error occured while initiating workflow: ", ex);
            response = null;
        }
        return response;
    }

    // set all relevant Work flow Task Action Data For initiating Work Flow -initial request
    private WorkflowTaskAction prepareWorkFlowTaskActionCreate(final String serialNo, final AuditDetailsDTO auditDto,
            final Long orgId, List<Long> attachmentId) {
        EmployeeBean emp = empService.findById(auditDto.getEmpId());
        WorkflowTaskAction taskAction = new WorkflowTaskAction();
        taskAction.setOrgId(orgId);
        taskAction.setEmpId(emp.getEmpId());
        taskAction.setEmpType(emp.getEmplType());
        taskAction.setDateOfAction(new Date());
        taskAction.setCreatedDate(new Date());
        taskAction.setCreatedBy(emp.getEmpId());
        taskAction.setEmpName(emp.getEmplname());
        taskAction.setEmpEmail(emp.getEmpemail());
        taskAction.setReferenceId(serialNo);
        taskAction.setPaymentMode(MainetConstants.FlagF);
        taskAction.setAttachementId(attachmentId);
        return taskAction;
    }

    /**
     * @param workflowActionDto
     */
    private WorkflowTaskAction prepareWorkFlowTaskActionUpdate(final String refId, final Long taskId, final List<Long> attIds,
            final AuditDetailsDTO auditDto, final Long orgId) {
        EmployeeBean emp = empService.findById(auditDto.getEmpId());
        WorkflowTaskAction workflowActionDto = new WorkflowTaskAction();
        workflowActionDto.setOrgId(orgId);
        workflowActionDto.setEmpId(emp.getEmpId());
        workflowActionDto.setEmpType(emp.getEmplType());
        workflowActionDto.setEmpName(emp.getEmplname());
        workflowActionDto.setEmpEmail(emp.getEmpemail());
        workflowActionDto.setAttachementId(attIds);
        workflowActionDto.setDateOfAction(new Date());
        workflowActionDto.setCreatedDate(new Date());
        workflowActionDto.setCreatedBy(emp.getEmpId());
        workflowActionDto.setReferenceId(refId);
        workflowActionDto.setPaymentMode(MainetConstants.FlagF);
        workflowActionDto.setIsFinalApproval(false);
        workflowActionDto.setTaskId(taskId);
        return workflowActionDto;

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private WorkflowTaskActionResponse executeWorkflowAction(final String refId, final TransferDTO trfDto,
            final AuditDetailsDTO auditDto, final WorkflowTaskAction wfAction, String moduleDeptCode,
            String serviceCodeDeptWise) {
        WorkflowTaskActionResponse response = null;
        ServiceMaster serviceMast = iServiceMasterService
                .getServiceMasterByShortCode(serviceCodeDeptWise, trfDto.getOrgId());
        // Prepare file upload
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setReferenceId(refId);
        requestDTO.setOrgId(trfDto.getOrgId());
        requestDTO.setDepartmentName(moduleDeptCode);
        requestDTO.setServiceId(serviceMast.getSmServiceId());
        requestDTO.setUserId(auditDto.getEmpId());
        requestDTO.setApplicationId(trfDto.getAssetCode());
        requestDTO.setDeptId(serviceMast.getTbDepartment().getDpDeptid());
        // process and upload
        List<DocumentDetailsVO> attachments = prepareWfAttachments();
        attachments = fileUpload.prepareFileUpload(attachments);
        fileUpload.doFileUpload(attachments, requestDTO);
        List<Long> attIds = checkListService.fetchAllAttachIdByReferenceId(refId, trfDto.getOrgId());
        // execute workflow task action
        WorkflowTaskAction workflowActionDtoTmp = prepareWorkFlowTaskActionUpdate(refId, wfAction.getTaskId(), attIds, auditDto,
                trfDto.getOrgId());
        workflowActionDtoTmp.setDecision(wfAction.getDecision());
        response = assetWorkFlowService.initiateWorkFlowAssetService(workflowActionDtoTmp, wfAction.getTaskId(),
                MainetConstants.AssetManagement.ASSET_TRANSFER_URL, MainetConstants.FlagU,
                serviceCodeDeptWise);

        return response;
    }

    private List<DocumentDetailsVO> prepareWfAttachments() {
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

    @Override
    @Transactional
    public TransferDTO getDetails(final Long transferId) {
        AssetTransfer astTransfer = transfer.findOne(transferId);
        if (astTransfer == null) {
            throw new FrameworkException("Could not find transfer details for ID: " + transferId);
        }
        // initalize AssetInformation by calling getter
        astTransfer.getAssetId().getAssetId();
        return TransferMapper.mapToDTO(astTransfer);
    }

    @Override
    @Transactional
    public boolean executeWfAction(final String wfReferenceId, final AuditDetailsDTO auditDto,
            final WorkflowTaskAction wfAction, String moduleDeptCode, String serviceCodeDeptWise) {
        String trfId = wfReferenceId.substring(wfReferenceId.lastIndexOf(MainetConstants.AssetManagement.WF_TXN_IDEN_TRF) + 4);
        TransferDTO trfDto = getDetails(Long.valueOf(trfId));
        boolean retVal = false;
        WorkflowTaskActionResponse response = null;
        try {
            response = executeWorkflowAction(wfReferenceId, trfDto, auditDto, wfAction, moduleDeptCode, serviceCodeDeptWise);
            retVal = true;
        } catch (Exception ex) {
            LOGGER.error("Exception occurred during workflow action execution ", ex);
            retVal = false;
        }
        if (response != null && !response.getIsProcessAlive()) {
            if (wfAction.getDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)) {
            	
                doTransfer(trfDto, auditDto);
                transfer.updateAuthStatus("A", auditDto.getEmpId(), new Date(), trfDto.getAssetCode(), trfDto.getTransferId(),
                        trfDto.getOrgId());
                // pass wfReferenceId for astAppNo
                retVal = infoService.updateAppStatusFlag(trfDto.getAssetCode(), trfDto.getOrgId(),
                        MainetConstants.AssetManagement.APPROVAL_STATUS_APPROVED, wfReferenceId);
            } else {
                transfer.updateAuthStatus("R", auditDto.getEmpId(), new Date(), trfDto.getAssetCode(), trfDto.getTransferId(),
                        trfDto.getOrgId());
                // pass wfReferenceId for astAppNo
                retVal = infoService.updateAppStatusFlag(trfDto.getAssetCode(), trfDto.getOrgId(),
                        MainetConstants.AssetManagement.APPROVAL_STATUS_APPROVED, wfReferenceId);
            }
        }
        return retVal;
    }

    @Transactional
    public void doTransfer(final TransferDTO dto, final AuditDetailsDTO audit) {
    	
    	
    	
    	
        if (dto.getTransferType().equals("trans-emp")) {
        	if(dto.getTransferEmployee()!=null) {
        	
        		
            infoService.updateEmployee(dto.getAssetCode(), dto.getTransferEmployee());
				Employee emp = empService.findEmployeeByIdAndOrgId(dto.getTransferEmployee(),
						dto.getOrgId());
				if (emp.getTbLocationMas().getLocId() != null) {
					infoService.updateLocationId(dto.getAssetCode(), emp.getTbLocationMas().getLocId());
				}            
            
				if (emp.getTbDepartment().getDpDeptid() != null) {
					String deptcode = iTbDepartmentService.findDepartmentShortCodeByDeptId(
							emp.getTbDepartment().getDpDeptid(),dto.getOrgId());
					infoService.updateDepartment(dto.getAssetCode(), deptcode);
				}
            
        	}
        } else if (dto.getTransferType().equals("trans-loc")) {
        	
            AssetClassificationDTO classDTO = classService.getclassByAssetId(dto.getAssetCode());
            if (classDTO != null) {
                classService.updateLocation(dto.getAssetCode(), dto.getTransferLocation());
            } else {
                classDTO = new AssetClassificationDTO();
                classDTO.setAssetId(dto.getAssetCode());
                classDTO.setLocation(dto.getTransferLocation());
                classService.saveClassInfo(classDTO);
            }
        } else if (dto.getTransferType().equals("trans-dept")) {
        
            AssetClassificationDTO classDTO = classService.getclassByAssetId(dto.getAssetCode());
            if (classDTO != null) {
                classService.updateDepartment(dto.getAssetCode(), dto.getTransferDepartment());
            } else {
                classDTO = new AssetClassificationDTO();
                classDTO.setAssetId(dto.getAssetCode());
                classDTO.setDepartment(dto.getTransferDepartment());
                classService.saveClassInfo(classDTO);
            }
        }
    }

}
