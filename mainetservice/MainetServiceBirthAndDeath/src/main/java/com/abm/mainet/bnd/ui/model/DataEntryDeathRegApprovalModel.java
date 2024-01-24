package com.abm.mainet.bnd.ui.model;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import com.abm.mainet.bnd.constant.BndConstants;
import com.abm.mainet.bnd.dto.CemeteryMasterDTO;
import com.abm.mainet.bnd.dto.DeceasedMasterDTO;
import com.abm.mainet.bnd.dto.HospitalMasterDTO;
import com.abm.mainet.bnd.dto.MedicalMasterDTO;
import com.abm.mainet.bnd.dto.TbDeathregDTO;
import com.abm.mainet.bnd.service.IDeathRegistrationService;
import com.abm.mainet.bnd.service.IdeathregCorrectionService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;


@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class DataEntryDeathRegApprovalModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;
	
	private String saveMode;
	
	 @Autowired
	 private IDeathRegistrationService iDeathRegistrationService;
	 
	 @Autowired
	 private IdeathregCorrectionService iDeathregCorrectionService;
	 
	   private TbDeathregDTO tbDeathregDTO = new TbDeathregDTO();
	
        private List<HospitalMasterDTO> hospitalMasterDTOList;

		private String hospitalList;
	   
		private List<CemeteryMasterDTO> cemeteryMasterDTOList;
		
		private String cemeteryList;
		
		private List<TbDeathregDTO> tbDeathregDTOList;
		
		private String tbDeathRegDtoList;
		
		private MedicalMasterDTO medicalMasterDto = new MedicalMasterDTO();

		private DeceasedMasterDTO deceasedMasterDTO = new DeceasedMasterDTO();
		
		private List<DocumentDetailsVO> checkList = new ArrayList<>();

		private List<CFCAttachment> fetchDocumentList = new ArrayList<>();
		
		private String cemeteryNameMar;
		
		private String cemeteryAddressMar;
		
		public String getCemeteryNameMar() {
			return cemeteryNameMar;
		}
		public void setCemeteryNameMar(String cemeteryNameMar) {
			this.cemeteryNameMar = cemeteryNameMar;
		}
		public String getCemeteryAddressMar() {
			return cemeteryAddressMar;
		}
		public void setCemeteryAddressMar(String cemeteryAddressMar) {
			this.cemeteryAddressMar = cemeteryAddressMar;
		}

		

		
	@Override 
	public boolean saveForm() {
		Employee employee = getUserSession().getEmployee();
		int langId = UserSession.getCurrent().getLanguageId();
		String status = tbDeathregDTO.getDrStatus();
		String remark = tbDeathregDTO.getDrRemarks();
		String complainNo = tbDeathregDTO.getApplicationNo();
		Long orgid = employee.getOrganisation().getOrgid();
		tbDeathregDTO.setUpdatedBy(employee.getEmpId());
		tbDeathregDTO.setUpdatedDate(new Date());
		tbDeathregDTO.setLgIpMac(employee.getEmppiservername());
		tbDeathregDTO.setLgIpMacUpd(employee.getEmppiservername());
		tbDeathregDTO.setOrgId(employee.getOrganisation().getOrgid());
		//iDeathRegistrationService.updateDeathApproveStatus(complainNo, status, remark, orgid);
		this.setSuccessMessage(getAppSession().getMessage("BirthRegDto.SuccessMsgComplntReg"));
		return true;
	}
	public boolean saveDeathApprovalDetails(String ApplicationId, Long orgId, String task)
	{
		RequestDTO requestDTO = new RequestDTO();
        requestDTO.setReferenceId(getWorkflowActionDto().getReferenceId());
        requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        requestDTO.setDepartmentName(BndConstants.BIRTH_DEATH);
        requestDTO.setServiceId(getServiceId());
        getWorkflowActionDto().setApplicationId(Long.valueOf(getWorkflowActionDto().getReferenceId()));//change vi
        getWorkflowActionDto().setDecision(tbDeathregDTO.getDeathRegstatus());
        tbDeathregDTO.setApplicationId(Long.valueOf(getWorkflowActionDto().getReferenceId()));
        requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        ServiceMaster service = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                .getServiceMasterByShortCode(BndConstants.DED,UserSession.getCurrent().getOrganisation().getOrgid());
        requestDTO.setDeptId(service.getTbDepartment().getDpDeptid());
        tbDeathregDTO.setServiceId(service.getSmServiceId());
		prepareWorkFlowTaskAction(getWorkflowActionDto());
		getWorkflowActionDto().setReferenceId(null);
		iDeathRegistrationService.updateWorkFlowDeathService(getWorkflowActionDto());
		WorkflowRequest workflowRequest = ApplicationContextProvider.getApplicationContext()
	                .getBean(IWorkflowRequestService.class)
	                .getWorkflowRequestByAppIdOrRefId(tbDeathregDTO.getApplicationId(),null,
	                        UserSession.getCurrent().getOrganisation().getOrgid());
		int size = workflowRequest.getWorkFlowTaskList().size();		
		if (workflowRequest != null
	    && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.REJECTED)) 
		{     
		    iDeathRegistrationService.updateDeathApproveStatus(tbDeathregDTO,null,workflowRequest.getLastDecision());
    		iDeathRegistrationService.updateDeathWorkFlowStatus(tbDeathregDTO.getDrId(), workflowRequest.getLastDecision(), orgId,BndConstants.DEATH_STATUS_APPROVED);
    		iDeathregCorrectionService.updateNoOfIssuedCopy(tbDeathregDTO.getDrId(), tbDeathregDTO.getOrgId(), BndConstants.APPROVED);

	    }
	    if (workflowRequest != null 
	    && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)
	    && workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Decision.PENDING)) 
	    {
	    	iDeathRegistrationService.updateDeathApproveStatus(tbDeathregDTO,workflowRequest.getLastDecision(),workflowRequest.getStatus());
    		iDeathRegistrationService.updateDeathWorkFlowStatus(tbDeathregDTO.getDrId(), workflowRequest.getStatus(), orgId,tbDeathregDTO.getDrStatus());
	    	//Current Task Name
	    	String taskName = workflowRequest.getWorkFlowTaskList().get(size-1).getTaskName();
	    	//Previous Task Name
	    	String taskNamePrevious = workflowRequest.getWorkFlowTaskList().get(size-2).getTaskName();
	    	if (!taskName.equals(taskNamePrevious))
	    	{ 
	    		iDeathRegistrationService.updateDeathWorkFlowStatus(tbDeathregDTO.getDrId(), workflowRequest.getStatus(), orgId,tbDeathregDTO.getDrStatus());
	    		tbDeathregDTO.setDeathWFStatus(taskNamePrevious);
	    	}	
	    } 
	    if (workflowRequest != null && workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Status.CLOSED)
	    && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)) 
	     {	
	    	   iDeathRegistrationService.updateDeathApproveStatus(tbDeathregDTO,workflowRequest.getLastDecision(),workflowRequest.getStatus());
	    	   tbDeathregDTO.setDeathWFStatus(workflowRequest.getWorkFlowTaskList().get(size-1).getTaskName());
	    	   String drStatus="Y";
	    	   iDeathRegistrationService.updateDeathWorkFlowStatus(tbDeathregDTO.getDrId(),workflowRequest.getLastDecision(), orgId,drStatus);
	    	   TbDeathregDTO saveDeathDet = iDeathRegistrationService.saveDeathRegDetOnApprovalTemp(tbDeathregDTO);
	    	   
	     }
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
	        workflowActionDto.setComments(tbDeathregDTO.getAuthRemark());
	        workflowActionDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
	        workflowActionDto.setReferenceId(getWorkflowActionDto().getReferenceId());
	        workflowActionDto.setPaymentMode(MainetConstants.FlagF);
	        workflowActionDto.setIsFinalApproval(false);
	        return workflowActionDto;
	    }
	
	

	public String getSaveMode() {
		return saveMode;
	}
	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}
	public IDeathRegistrationService getiDeathRegistrationService() {
		return iDeathRegistrationService;
	}
	public void setiDeathRegistrationService(IDeathRegistrationService iDeathRegistrationService) {
		this.iDeathRegistrationService = iDeathRegistrationService;
	}
	public TbDeathregDTO getTbDeathregDTO() {
		return tbDeathregDTO;
	}
	public void setTbDeathregDTO(TbDeathregDTO tbDeathregDTO) {
		this.tbDeathregDTO = tbDeathregDTO;
	}
	public List<HospitalMasterDTO> getHospitalMasterDTOList() {
		return hospitalMasterDTOList;
	}
	public List<HospitalMasterDTO> setHospitalMasterDTOList(List<HospitalMasterDTO> hospitalMasterDTOList) {
		return this.hospitalMasterDTOList = hospitalMasterDTOList;
	}
	public String getHospitalList() {
		return hospitalList;
	}
	public void setHospitalList(String hospitalList) {
		this.hospitalList = hospitalList;
	}
	public List<CemeteryMasterDTO> getCemeteryMasterDTOList() {
		return cemeteryMasterDTOList;
	}
	public List<CemeteryMasterDTO> setCemeteryMasterDTOList(List<CemeteryMasterDTO> cemeteryMasterDTOList) {
		return this.cemeteryMasterDTOList = cemeteryMasterDTOList;
	}
	public String getCemeteryList() {
		return cemeteryList;
	}
	public void setCemeteryList(String cemeteryList) {
		this.cemeteryList = cemeteryList;
	}
	public List<TbDeathregDTO> getTbDeathregDTOList() {
		return tbDeathregDTOList;
	}
	public void setTbDeathregDTOList(List<TbDeathregDTO> tbDeathregDTOList) {
		this.tbDeathregDTOList = tbDeathregDTOList;
	}
	public String getTbDeathRegDtoList() {
		return tbDeathRegDtoList;
	}
	public void setTbDeathRegDtoList(String tbDeathRegDtoList) {
		this.tbDeathRegDtoList = tbDeathRegDtoList;
	}
	public MedicalMasterDTO getMedicalMasterDto() {
		return medicalMasterDto;
	}
	public void setMedicalMasterDto(MedicalMasterDTO medicalMasterDto) {
		this.medicalMasterDto = medicalMasterDto;
	}
	public DeceasedMasterDTO getDeceasedMasterDTO() {
		return deceasedMasterDTO;
	}
	public void setDeceasedMasterDTO(DeceasedMasterDTO deceasedMasterDTO) {
		this.deceasedMasterDTO = deceasedMasterDTO;
	}
	
	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	public List<CFCAttachment> getFetchDocumentList() {
		return fetchDocumentList;
	}

	public void setFetchDocumentList(List<CFCAttachment> fetchDocumentList) {
		this.fetchDocumentList = fetchDocumentList;
	}

	
	
}
