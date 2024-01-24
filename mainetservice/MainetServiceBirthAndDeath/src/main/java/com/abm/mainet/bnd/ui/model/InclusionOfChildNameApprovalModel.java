package com.abm.mainet.bnd.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.bnd.dto.BirthRegistrationCorrDTO;
import com.abm.mainet.bnd.dto.BirthRegistrationDTO;
import com.abm.mainet.bnd.dto.CemeteryMasterDTO;
import com.abm.mainet.bnd.dto.DeathCauseMasterDTO;
import com.abm.mainet.bnd.dto.DeceasedMasterCorrDTO;
import com.abm.mainet.bnd.dto.DeceasedMasterDTO;
import com.abm.mainet.bnd.dto.HospitalMasterDTO;
import com.abm.mainet.bnd.dto.MedicalMasterCorrectionDTO;
import com.abm.mainet.bnd.dto.MedicalMasterDTO;
import com.abm.mainet.bnd.dto.ParentDetailDTO;
import com.abm.mainet.bnd.dto.TbBdDeathregCorrDTO;
import com.abm.mainet.bnd.dto.TbDeathregDTO;
import com.abm.mainet.bnd.service.IBirthRegService;
import com.abm.mainet.bnd.service.IDeathRegistrationService;
import com.abm.mainet.bnd.service.IdeathregCorrectionService;
import com.abm.mainet.bnd.service.InclusionOfChildNameService;
import com.abm.mainet.bnd.service.IssuenceOfBirthCertificateService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class InclusionOfChildNameApprovalModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;
	
	private String saveMode;
	
	 @Autowired
	 private IDeathRegistrationService iDeathRegistrationService;
	 
	 @Autowired
	 private IssuenceOfBirthCertificateService issuenceOfBirthCertificateService;
	 
	   private TbDeathregDTO tbDeathregDTO = new TbDeathregDTO();
	   
	   private CemeteryMasterDTO cemeteryMasterDTO = new CemeteryMasterDTO(); 
	
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
		
		private List<TbBdDeathregCorrDTO> tbDeathregcorrDTOList;
		
		private TbBdDeathregCorrDTO tbDeathregcorrDTO = new TbBdDeathregCorrDTO();
		
		private MedicalMasterCorrectionDTO medicalMasterCorrDto = new MedicalMasterCorrectionDTO();
		
		private DeathCauseMasterDTO deathCauseMasterDTO = new DeathCauseMasterDTO();
		
		private DeceasedMasterCorrDTO deceasedMasterCorrDTO = new DeceasedMasterCorrDTO(); 
		
		private BirthRegistrationDTO birthRegDto = new BirthRegistrationDTO();
		
		private ParentDetailDTO parentDtlDto = new ParentDetailDTO();
		
		private BirthRegistrationCorrDTO tbBirthregcorrDTO = new BirthRegistrationCorrDTO();
		
		@Autowired
		private InclusionOfChildNameService inclusionOfChildNameService;
		
		 @Autowired
		 private IBirthRegService iBirthRegService;
		
		public String saveInclusionOfChildApprovalDetails(String ApplicationId, Long orgId, String task)
		{
			RequestDTO requestDTO = new RequestDTO();
			String certificateno=null;
	        requestDTO.setReferenceId(getWorkflowActionDto().getReferenceId());
	        requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
	        requestDTO.setDepartmentName("BND");
	        requestDTO.setServiceId(getServiceId());
	        getWorkflowActionDto().setApplicationId(Long.valueOf(getWorkflowActionDto().getReferenceId()));//change vi
	        getWorkflowActionDto().setDecision(tbBirthregcorrDTO.getBirthRegstatus());
	        tbBirthregcorrDTO.setApmApplicationId(Long.valueOf(getWorkflowActionDto().getReferenceId()));
	        requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
	        ServiceMaster service = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
	                .getServiceMasterByShortCode("INC",UserSession.getCurrent().getOrganisation().getOrgid());
	        requestDTO.setDeptId(service.getTbDepartment().getDpDeptid());
	        tbBirthregcorrDTO.setServiceId(service.getSmServiceId());
			prepareWorkFlowTaskAction(getWorkflowActionDto());
			tbBirthregcorrDTO.setOrgId(requestDTO.getOrgId());
			getWorkflowActionDto().setReferenceId(null);
			iBirthRegService.updateWorkFlowDeathService(getWorkflowActionDto());
			iBirthRegService.updateBirthRemark(tbBirthregcorrDTO.getBrId(), tbBirthregcorrDTO.getBirthRegremark(),orgId);
			WorkflowRequest workflowRequest = ApplicationContextProvider.getApplicationContext()
		                .getBean(IWorkflowRequestService.class)
		                .getWorkflowRequestByAppIdOrRefId(tbBirthregcorrDTO.getApmApplicationId(),null ,
		                        UserSession.getCurrent().getOrganisation().getOrgid());
			int size = workflowRequest.getWorkFlowTaskList().size();		
			if (workflowRequest != null && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.REJECTED)) { 
				iBirthRegService.updateBirthApproveStatus(tbBirthregcorrDTO,null,workflowRequest.getLastDecision());
				iBirthRegService.updateBirthWorkFlowStatus(tbBirthregcorrDTO.getBrId(),MainetConstants.WorkFlow.Decision.REJECTED, orgId);
				iBirthRegService.updateBirthWorkFlowStatusBR(tbBirthregcorrDTO.getBrId(), MainetConstants.WorkFlow.Decision.REJECTED, orgId, tbBirthregcorrDTO.getBrStatus());
				inclusionOfChildNameService.smsAndEmailApproval(birthRegDto,workflowRequest.getLastDecision());
		    }
		    if (workflowRequest != null && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)
		    		&& workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Decision.PENDING)) {
		    	iBirthRegService.updateBirthApproveStatus(tbBirthregcorrDTO,workflowRequest.getLastDecision(),workflowRequest.getStatus());
		    	
		    	//Current Task Name
		    	String taskName = workflowRequest.getWorkFlowTaskList().get(size-1).getTaskName();
		    	//Previous Task Name
		    	String taskNamePrevious = workflowRequest.getWorkFlowTaskList().get(size-2).getTaskName();
		    	if (!taskName.equals(taskNamePrevious)) {
		    		iBirthRegService.updateBirthWorkFlowStatus(tbBirthregcorrDTO.getBrId(), MainetConstants.WorkFlow.Decision.PENDING, orgId);
		    		tbBirthregcorrDTO.setBirthWfStatus(taskNamePrevious);
		    	}	
		    } 
		    if (workflowRequest != null && workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Status.CLOSED)
		    		&& workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)) {	
		    	iBirthRegService.updateBirthApproveStatus(tbBirthregcorrDTO, workflowRequest.getLastDecision(),workflowRequest.getStatus());
		    	tbBirthregcorrDTO.setBirthWfStatus(workflowRequest.getWorkFlowTaskList().get(size-1).getTaskName());
		    	iBirthRegService.updateBirthWorkFlowStatus(tbBirthregcorrDTO.getBrId(),MainetConstants.WorkFlow.Status.CLOSED, orgId);
		    	//certificate generation/update
		    	certificateno=iBirthRegService.updateBirthRegCorrApprove(tbBirthregcorrDTO,workflowRequest.getLastDecision(),workflowRequest.getStatus());
		    	// save data to birth registration entity after final approval 
		    	BeanUtils.copyProperties(tbBirthregcorrDTO, birthRegDto);
		    	birthRegDto.getParentDetailDTO().setPdRegUnitId(tbBirthregcorrDTO.getPdRegUnitId());
		    	birthRegDto.setApmApplicationId(tbBirthregcorrDTO.getApmApplicationId());
		    	BirthRegistrationDTO saveInclOnAppr = inclusionOfChildNameService.saveInclusionOfChildOnApproval(birthRegDto);
		    	issuenceOfBirthCertificateService.updatNoOfcopyStatus(tbBirthregcorrDTO.getBrId(), tbBirthregcorrDTO.getOrgId(), tbBirthregcorrDTO.getBrId(), saveInclOnAppr.getNoOfCopies());
		    	birthRegDto.setBrCertNo(certificateno);
		    	inclusionOfChildNameService.smsAndEmailApproval(birthRegDto,workflowRequest.getLastDecision());
		     }
			return certificateno;
		}
		
		
		 private WorkflowTaskAction prepareWorkFlowTaskAction(WorkflowTaskAction workflowActionDto) {
		        getWorkflowActionDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		        workflowActionDto.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
		        workflowActionDto.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		        workflowActionDto.setEmpName(UserSession.getCurrent().getEmployee().getEmplname());
		        workflowActionDto.setEmpEmail(UserSession.getCurrent().getEmployee().getEmpemail());
		        workflowActionDto.setDateOfAction(new Date());
		        workflowActionDto.setCreatedDate(new Date());
		        workflowActionDto.setComments(tbBirthregcorrDTO.getBirthRegremark());
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

	public TbBdDeathregCorrDTO getTbDeathregcorrDTO() {
		return tbDeathregcorrDTO;
	}
	public void setTbDeathregcorrDTO(TbBdDeathregCorrDTO tbDeathregcorrDTO) {
		this.tbDeathregcorrDTO = tbDeathregcorrDTO;
	}
	
	public TbDeathregDTO getTbDeathregDTO() {
		return tbDeathregDTO;
	}
	public void setTbDeathregDTO(TbDeathregDTO tbDeathregDTO) {
		this.tbDeathregDTO = tbDeathregDTO;
	}

	public List<TbBdDeathregCorrDTO> getTbDeathregcorrDTOList() {
		return tbDeathregcorrDTOList;
	}

	public void setTbDeathregcorrDTOList(List<TbBdDeathregCorrDTO> tbDeathregcorrDTOList) {
		this.tbDeathregcorrDTOList = tbDeathregcorrDTOList;
	}

    public CemeteryMasterDTO getCemeteryMasterDTO() {
    	return cemeteryMasterDTO;
    }

	public void setCemeteryMasterDTO(CemeteryMasterDTO cemeteryMasterDTO) {
		this.cemeteryMasterDTO = cemeteryMasterDTO;
	}

	public MedicalMasterCorrectionDTO getMedicalMasterCorrDto() {
		return medicalMasterCorrDto;
	}

	public void setMedicalMasterCorrDto(MedicalMasterCorrectionDTO medicalMasterCorrDto) {
		this.medicalMasterCorrDto = medicalMasterCorrDto;
	}
	
	public DeathCauseMasterDTO getDeathCauseMasterDTO() {
		return deathCauseMasterDTO;
	}

	public void setDeathCauseMasterDTO(DeathCauseMasterDTO deathCauseMasterDTO) {
		this.deathCauseMasterDTO = deathCauseMasterDTO;
	}

	public DeceasedMasterCorrDTO getDeceasedMasterCorrDTO() {
		return deceasedMasterCorrDTO;
	}

	public void setDeceasedMasterCorrDTO(DeceasedMasterCorrDTO deceasedMasterCorrDTO) {
		this.deceasedMasterCorrDTO = deceasedMasterCorrDTO;
	}
	
	public BirthRegistrationDTO getBirthRegDto() {
		return birthRegDto;
	}

	public void setBirthRegDto(BirthRegistrationDTO birthRegDto) {
		this.birthRegDto = birthRegDto;
	}
	
	public ParentDetailDTO getParentDtlDto() {
		return parentDtlDto;
	}

	public void setParentDtlDto(ParentDetailDTO parentDtlDto) {
		this.parentDtlDto = parentDtlDto;
	}
	
	public BirthRegistrationCorrDTO getTbBirthregcorrDTO() {
		return tbBirthregcorrDTO;
	}
	
	public void setTbBirthregcorrDTO(BirthRegistrationCorrDTO tbBirthregcorrDTO) {
		this.tbBirthregcorrDTO = tbBirthregcorrDTO;
	}
	
	
	
	
}
