package com.abm.mainet.rnl.ui.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.TbApprejMas;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.dto.ContractMappingDTO;
import com.abm.mainet.common.master.dto.ContractMastDTO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.rnl.dto.ContractPropListDTO;
import com.abm.mainet.rnl.dto.EstateContMappingDTO;
import com.abm.mainet.rnl.service.IEstateContractMappingService;

/**
 * @author ritesh.patil
 *
 */
@Component
@Scope("session")
public class EstateContractMappingModel extends AbstractFormModel {
    private static final long serialVersionUID = 7600962094291718078L;
    TbDepartment tbDepartment;
    private String modeType;
    private List<Object[]> estateMasters = Collections.emptyList();
    private List<ContractMappingDTO> contractList;
    private EstateContMappingDTO estateContMappingDTO;
    private String dupCheckFlag;
    private List<Object[]> props = Collections.emptyList();
    private ServiceMaster serviceMaster;
    private List<TbApprejMas> remarkList;
    private ContractMappingDTO contractMappingDTO = new ContractMappingDTO();
    private List<DocumentDetailsVO> attachments = new ArrayList<>();
    private List<AttachDocs> attachDocsList = new ArrayList<>();
    private List<DocumentDetailsVO> documents = new ArrayList<>();
    private String showForm;
    private ContractMastDTO contractMastDTO = new ContractMastDTO();
    private String removeChildIds;
    
    @Autowired
    private IEstateContractMappingService iEstateContractMappingService;

    public EstateContractMappingModel() {
        tbDepartment = new TbDepartment();
        contractList = new ArrayList<>();
        estateContMappingDTO = new EstateContMappingDTO();
    }

    public TbDepartment getTbDepartment() {
        return tbDepartment;
    }

    public void setTbDepartment(final TbDepartment tbDepartment) {
        this.tbDepartment = tbDepartment;
    }

    public String getModeType() {
        return modeType;
    }

    public void setModeType(final String modeType) {
        this.modeType = modeType;
    }

    public List<Object[]> getEstateMasters() {
        return estateMasters;
    }

    public void setEstateMasters(final List<Object[]> estateMasters) {
        this.estateMasters = estateMasters;
    }

    public List<ContractMappingDTO> getContractList() {
        return contractList;
    }

    public void setContractList(final List<ContractMappingDTO> contractList) {
        this.contractList = contractList;
    }

    public EstateContMappingDTO getEstateContMappingDTO() {
        return estateContMappingDTO;
    }

    public void setEstateContMappingDTO(final EstateContMappingDTO estateContMappingDTO) {
        this.estateContMappingDTO = estateContMappingDTO;
    }

    @Override
    public boolean saveForm() {

        final EstateContMappingDTO estateContMappingDTO = getEstateContMappingDTO();
        if (getDupCheckFlag().equals(MainetConstants.CommonConstants.Y)) {
            addValidationError(getAppSession()
                    .getMessage(ApplicationSession.getInstance().getMessage(MainetConstants.EstateContract.ESTATE_CONTRACT_MAP)));
            setDupCheckFlag(MainetConstants.CommonConstants.N);
            estateContMappingDTO.setContractPropListDTO(new ArrayList<ContractPropListDTO>());
        }
        if (hasValidationErrors()) {
            return false;
        }
        estateContMappingDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        estateContMappingDTO.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        estateContMappingDTO.setCreatedDate(new Date());
        estateContMappingDTO.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
        iEstateContractMappingService.save(estateContMappingDTO);
        setSuccessMessage(ApplicationSession.getInstance().getMessage(MainetConstants.EstateContract.ESTATE_CONTRACT));
        return true;
    }
    
	public boolean closeWorkFlowTask() {
		boolean status = false;
		WorkflowTaskAction taskAction = getWorkflowActionDto();
		taskAction.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		taskAction.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
		taskAction.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		taskAction.setDateOfAction(new Date());
		taskAction.setCreatedDate(new Date());
		taskAction.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		taskAction.setIsFinalApproval(true);
		taskAction.setIsObjectionAppealApplicable(false);
		if (StringUtils.isNotBlank(UserSession.getCurrent().getEmployee().getEmpemail())) {
			taskAction.setEmpEmail(UserSession.getCurrent().getEmployee().getEmpemail());
		}
		taskAction.setApplicationId(getApmApplicationId());
		taskAction.setDecision(MainetConstants.WorkFlow.Decision.APPROVED);
		taskAction.setTaskId(getWorkflowActionDto().getTaskId());

		WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();
		LookUp workProcessLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(
				getServiceMaster().getSmProcessId(), UserSession.getCurrent().getOrganisation());
		workflowProcessParameter.setProcessName(workProcessLookUp.getDescLangFirst());
		workflowProcessParameter.setWorkflowTaskAction(taskAction);
		try {
			ApplicationContextProvider.getApplicationContext().getBean(IWorkflowExecutionService.class)
					.updateWorkflow(workflowProcessParameter);
			status = true;
		} catch (Exception exception) {
			throw new FrameworkException("Exception occured while updating work flow", exception);
		}
		return status;
	}
	
	public boolean updateEstateContractMappingDecision() {
		WorkflowTaskAction taskAction = getWorkflowActionDto();
		taskAction.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		taskAction.setEmpName(UserSession.getCurrent().getEmployee().getEmpname());
		taskAction.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		taskAction.setCreatedDate(new Date());
		taskAction.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
		taskAction.setDateOfAction(new Date());
		taskAction.setIsFinalApproval(MainetConstants.FAILED);
		taskAction.setIsObjectionAppealApplicable(MainetConstants.FAILED);
		taskAction.setDecision(getWorkflowActionDto().getDecision());
		taskAction.setApplicationId(getApmApplicationId());
		taskAction.setTaskId(getWorkflowActionDto().getTaskId());

		return iEstateContractMappingService.executeApprovalWorkflowAction(taskAction, getServiceMaster().getSmShortdesc(),
				getServiceMaster().getSmServiceId(), getServiceMaster().getSmShortdesc());

	}
	
	public boolean saveAdjustment() {
		boolean status =false;
		String ipAddress = UserSession.getCurrent().getEmployee().getEmppiservername();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		int langId=UserSession.getCurrent().getLanguageId();
		Long empId = UserSession.getCurrent().getEmployee().getEmpId();
		final List<Long> removeIds = new ArrayList<>();
		 final String ids = getRemoveChildIds();
         if (null!=ids && !ids.isEmpty()) {
             final String array[] = ids.split(",");
             for (final String string : array) {
                 removeIds.add(Long.valueOf(string));
             }
         }
		
		try {
			iEstateContractMappingService.saveContractAdjustment(contractMastDTO,orgId, langId,empId,ipAddress,removeIds);
			iEstateContractMappingService.saveRlBillAdjustment(contractMastDTO.getContId(),orgId, langId,empId,ipAddress,removeIds);
			status=true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return status;
	}

    public String getDupCheckFlag() {
        return dupCheckFlag;
    }

    public void setDupCheckFlag(final String dupCheckFlag) {
        this.dupCheckFlag = dupCheckFlag;
    }

    public List<Object[]> getProps() {
        return props;
    }

    public void setProps(final List<Object[]> props) {
        this.props = props;
    }

	public ServiceMaster getServiceMaster() {
		return serviceMaster;
	}

	public void setServiceMaster(ServiceMaster serviceMaster) {
		this.serviceMaster = serviceMaster;
	}

	public List<TbApprejMas> getRemarkList() {
		return remarkList;
	}

	public void setRemarkList(List<TbApprejMas> remarkList) {
		this.remarkList = remarkList;
	}

	public ContractMappingDTO getContractMappingDTO() {
		return contractMappingDTO;
	}

	public void setContractMappingDTO(ContractMappingDTO contractMappingDTO) {
		this.contractMappingDTO = contractMappingDTO;
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

	public List<DocumentDetailsVO> getDocuments() {
		return documents;
	}

	public void setDocuments(List<DocumentDetailsVO> documents) {
		this.documents = documents;
	}

	public String getShowForm() {
		return showForm;
	}

	public void setShowForm(String showForm) {
		this.showForm = showForm;
	}

	public ContractMastDTO getContractMastDTO() {
		return contractMastDTO;
	}

	public void setContractMastDTO(ContractMastDTO contractMastDTO) {
		this.contractMastDTO = contractMastDTO;
	}

	public String getRemoveChildIds() {
		return removeChildIds;
	}

	public void setRemoveChildIds(String removeChildIds) {
		this.removeChildIds = removeChildIds;
	}
	
	
	
}
