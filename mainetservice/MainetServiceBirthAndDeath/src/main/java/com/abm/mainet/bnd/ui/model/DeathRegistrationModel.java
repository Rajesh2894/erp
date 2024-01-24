package com.abm.mainet.bnd.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.bnd.constant.BndConstants;
import com.abm.mainet.bnd.dto.CemeteryMasterDTO;
import com.abm.mainet.bnd.dto.DeceasedMasterDTO;
import com.abm.mainet.bnd.dto.HospitalMasterDTO;
import com.abm.mainet.bnd.dto.MedicalMasterDTO;
import com.abm.mainet.bnd.dto.TbDeathRegdraftDto;
import com.abm.mainet.bnd.dto.TbDeathregDTO;
import com.abm.mainet.bnd.service.IDeathRegistrationService;
import com.abm.mainet.cfc.challan.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class DeathRegistrationModel extends AbstractFormModel {

	private static final long serialVersionUID = 929635112508809678L;

	private IDeathRegistrationService iDeathRegistrationService;

	private TbDeathregDTO tbDeathregDTO = new TbDeathregDTO();
	
	// private static final Logger LOGGER =
	// Logger.getLogger(BirthRegistrationModel.class);

	private List<HospitalMasterDTO> hospitalMasterDTOList;

	private String hospitalList;
	
	private List<CemeteryMasterDTO> cemeteryMasterDTOList;
	
	private String cemeteryList;
	
	private List<DocumentDetailsVO> checkList = new ArrayList<>();

	private ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();

	private MedicalMasterDTO medicalMasterDto = new MedicalMasterDTO();

	private DeceasedMasterDTO deceasedMasterDTO = new DeceasedMasterDTO();
	
	private CemeteryMasterDTO cemeteryMasterDTO =new CemeteryMasterDTO() ;
	
	private List<TbDeathregDTO> tbDeathregDTOList;

	private List<TbDeathRegdraftDto> tbDeathRegdraftDtoList;
	
	private String tbDeathRegDtoList;
	
	private String tbDeathRegdraftDTOList;
	
	private String saveMode;
	
	private String chargesFetched;
	
	private List<CFCAttachment> fetchDocumentList = new ArrayList<>();
	
	@Autowired
	private ServiceMasterService serviceMasterService;
	
	@Override
	public boolean saveForm() {
		
		Employee employee = getUserSession().getEmployee();
		int langId = UserSession.getCurrent().getLanguageId();

		tbDeathregDTO.setUpdatedBy(employee.getEmpId());
		tbDeathregDTO.setUpdatedDate(new Date());
		tbDeathregDTO.setLgIpMacUpd(employee.getEmppiservername());
		tbDeathregDTO.setLmoddate(new Date());
		tbDeathregDTO.setLgIpMac(employee.getEmppiservername());
		tbDeathregDTO.setOrgId(employee.getOrganisation().getOrgid());
		tbDeathregDTO.setLangId(langId);
		tbDeathregDTO.setUserId(employee.getEmpId());
		List<DocumentDetailsVO> documents = getCheckList();
		 documents = ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class).prepareFileUpload(checkList);
		tbDeathregDTO.setDocumentList(checkList);
		validateInputs(documents);
		if (hasValidationErrors()) {
			return false;
		}

		if(this.getApmApplicationId()!=0) {
		tbDeathregDTO.setApmApplicationId(this.getApmApplicationId());
		}
		if(tbDeathregDTO.getStatusFlag().equals("D")){
			tbDeathregDTO.getDrDraftId();
			
			iDeathRegistrationService.saveDeathRegDraft(tbDeathregDTO);
			this.setSuccessMessage(getAppSession().getMessage("TbDeathregDTO.SuccessMsgDrDraft") + tbDeathregDTO.getApmApplicationId());
						
		}
		else {
			ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(BndConstants.DR, tbDeathregDTO.getOrgId());
			if(serviceMas.getSmFeesSchedule()!=0)
			{
			CommonChallanDTO offline = getOfflineDTO();
			final String modeDesc = getNonHierarchicalLookUpObject(offline.getOflPaymentMode()).getLookUpCode();
			offline.setOfflinePaymentText(modeDesc);
			
			if(this.getChargesAmount()!=null) {
				offline.setAmountToShow(Double.parseDouble(this.getChargesAmount()));
			} else {
				offline.setAmountToShow(0.0);
			}
			
			if (offline.getAmountToShow() > 0d) {
				validateBean(offline, CommonOfflineMasterValidator.class);
				if (hasValidationErrors()) {
					return false;
				}
			}
			this.setServiceId(tbDeathregDTO.getServiceId());
			this.getChargesAmount();
			}
			this.setTbDeathregDTO(tbDeathregDTO);
			Map<String, Object> applicationNo = iDeathRegistrationService.saveDeathRegDet(tbDeathregDTO, this);
			if(applicationNo==null)
				return false;

			this.setSuccessMessage(getAppSession().getMessage("TbDeathregDTO.SuccessMsgDr") + applicationNo.get("ApplicationId"));
		}
		return true;
	}

	public boolean validateInputs(final List<DocumentDetailsVO> dto) {
		boolean flag = false;
		if ((dto != null) && !dto.isEmpty()) {
			for (final DocumentDetailsVO doc : dto) {
				if (doc.getCheckkMANDATORY().equals(MainetConstants.FlagY)) {
					if (doc.getDocumentByteCode() == null) {
						addValidationError(
								ApplicationSession.getInstance().getMessage("bnd.upload.mandatory.documents"));
						flag = true;
						break;
					}
				}
			}
		}
		return flag;
	}
	
	public WorkflowTaskAction prepareWorkFlowTaskAction() {
		WorkflowTaskAction taskAction = new WorkflowTaskAction();
			taskAction.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			taskAction.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
			//taskAction.setEmpType(UserSession.getCurrent().getEmployee().getEmplType()); //getting null value ashish
			taskAction.setDateOfAction(new Date());
			taskAction.setCreatedDate(new Date());
			taskAction.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
			taskAction.setEmpName(UserSession.getCurrent().getEmployee().getEmplname());
			//taskAction.setEmpEmail(UserSession.getCurrent().getEmployee().getEmpemail()); //getting null value ashish
			//taskAction.setReferenceId(tbDeathregDTO.getDrId().toString());
			taskAction.setComments(tbDeathregDTO.getAuthRemark());
			taskAction.setApplicationId(tbDeathregDTO.getApmApplicationId());
			taskAction.setPaymentMode("F");
			taskAction.setDecision("SUBMITED");
		return taskAction;
	}
	
	
	public IDeathRegistrationService getiDeathRegistrationService() {
		return iDeathRegistrationService;
	}

	@Autowired
	public void setiDeathRegistrationService(IDeathRegistrationService iDeathRegistrationService) {
		this.iDeathRegistrationService = iDeathRegistrationService;
	}

	/*
	 * public TbDeathregDTO getTbDeathregDTO() { return tbDeathregDTO; }
	 * 
	 * public void setTbDeathregDTO(final TbDeathregDTO tbDeathregDTO) {
	 * this.tbDeathregDTO = tbDeathregDTO; }
	 */
	

	public List<HospitalMasterDTO> getHospitalMasterDTOList() {
		return hospitalMasterDTOList;
	}

	public TbDeathregDTO getTbDeathregDTO() {
		return tbDeathregDTO;
	}


	public void setTbDeathregDTO(TbDeathregDTO tbDeathregDTO) {
		this.tbDeathregDTO = tbDeathregDTO;
	}


	public List<HospitalMasterDTO> setHospitalMasterDTOList(List<HospitalMasterDTO> hospitalMasterDTOList) {
		return this.hospitalMasterDTOList = hospitalMasterDTOList;
	}
	
	

	public List<CemeteryMasterDTO> getCemeteryMasterDTOList() {
		return cemeteryMasterDTOList;
	}

	public List<CemeteryMasterDTO> setCemeteryMasterDTOList(List<CemeteryMasterDTO> cemeteryMasterDTOList) {
		return this.cemeteryMasterDTOList = cemeteryMasterDTOList;
	}

	public String getHospitalList() {
		return hospitalList;
	}

	public void setHospitalList(String hospitalList) {
		this.hospitalList = hospitalList;
	}

	public ApplicantDetailDTO getApplicantDetailDto() {
		return applicantDetailDto;
	}

	public void setApplicantDetailDto(ApplicantDetailDTO applicantDetailDto) {
		this.applicantDetailDto = applicantDetailDto;
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

	public String getCemeteryList() {
		return cemeteryList;
	}

	public void setCemeteryList(String cemeteryList) {
		this.cemeteryList = cemeteryList;
	}

	public CemeteryMasterDTO getCemeteryMasterDTO() {
		return cemeteryMasterDTO;
	}

	public void setCemeteryMasterDTO(CemeteryMasterDTO cemeteryMasterDTO) {
		this.cemeteryMasterDTO = cemeteryMasterDTO;
	}


	public List<TbDeathregDTO> getTbDeathregDTOList() {
		return tbDeathregDTOList;
	}


	public void setTbDeathregDTOList(List<TbDeathregDTO> tbDeathregDTOList) {
		this.tbDeathregDTOList = tbDeathregDTOList;
	}


	public List<TbDeathRegdraftDto> getTbDeathRegdraftDtoList() {
		return tbDeathRegdraftDtoList;
	}


	public void setTbDeathRegdraftDtoList(List<TbDeathRegdraftDto> tbDeathRegdraftDtoList) {
		this.tbDeathRegdraftDtoList = tbDeathRegdraftDtoList;
	}


	public String getTbDeathRegDtoList() {
		return tbDeathRegDtoList;
	}


	public void setTbDeathRegDtoList(String tbDeathRegDtoList) {
		this.tbDeathRegDtoList = tbDeathRegDtoList;
	}


	public String getTbDeathRegdraftDTOList() {
		return tbDeathRegdraftDTOList;
	}


	public void setTbDeathRegdraftDTOList(String tbDeathRegdraftDTOList) {
		this.tbDeathRegdraftDTOList = tbDeathRegdraftDTOList;
	}


	public String getSaveMode() {
		return saveMode;
	}


	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}


	public List<CFCAttachment> getFetchDocumentList() {
		return fetchDocumentList;
	}


	public void setFetchDocumentList(List<CFCAttachment> fetchDocumentList) {
		this.fetchDocumentList = fetchDocumentList;
	}


	public String getChargesFetched() {
		return chargesFetched;
	}


	public void setChargesFetched(String chargesFetched) {
		this.chargesFetched = chargesFetched;
	}

	
	
}
