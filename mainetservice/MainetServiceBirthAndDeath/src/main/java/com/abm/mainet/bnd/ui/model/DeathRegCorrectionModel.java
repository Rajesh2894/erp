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
import com.abm.mainet.bnd.dto.BndAcknowledgementDto;
import com.abm.mainet.bnd.dto.CemeteryMasterDTO;
import com.abm.mainet.bnd.dto.DeceasedMasterDTO;
import com.abm.mainet.bnd.dto.HospitalMasterDTO;
import com.abm.mainet.bnd.dto.MedicalMasterDTO;
import com.abm.mainet.bnd.dto.TbDeathregDTO;
import com.abm.mainet.bnd.service.IdeathregCorrectionService;
import com.abm.mainet.cfc.challan.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class DeathRegCorrectionModel extends AbstractFormModel{

	private static final long serialVersionUID = -6058717265176985058L;
	
	@Autowired
	private IdeathregCorrectionService ideathregCorrectionService;
	
	private TbDeathregDTO tbDeathregDTO= new TbDeathregDTO();
	
	BndAcknowledgementDto ackDto = new BndAcknowledgementDto();
	
	private List<HospitalMasterDTO> hospitalMasterDTOList;

	private String hospitalList;
   
	private List<CemeteryMasterDTO> cemeteryMasterDTOList;
	
	private String cemeteryList;
	
	private List<TbDeathregDTO> tbDeathregDTOList;
	
	private String tbDeathRegDtoList;
	
	private String saveMode;
	
	private RequestDTO requestDTO = new RequestDTO();
	
	private MedicalMasterDTO medicalMasterDto = new MedicalMasterDTO();

	private DeceasedMasterDTO deceasedMasterDTO = new DeceasedMasterDTO();
	
	private List<DocumentDetailsVO> checkList = new ArrayList<>();
	
	private String checklistStatus;
	private String kdmcEnv;
	
	@Autowired
	private ServiceMasterService serviceMasterService;
	
	@Autowired
    IFileUploadService fileUpload;
	
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
		documents = fileUpload.prepareFileUpload(checkList);
		tbDeathregDTO.setDocumentList(checkList);
		validateInputs(documents);
		if (hasValidationErrors()) {
            return false;
        }
		ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(BndConstants.DRC, tbDeathregDTO.getOrgId());
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
		this.setRequestDTO(tbDeathregDTO.getRequestDTO());
		
		Map<String,Object> map = ideathregCorrectionService.saveDeathCorrData(tbDeathregDTO,this);
		
		this.setSuccessMessage(getAppSession().getMessage("TbDeathregDTO.SuccessMsgDrc") + map.get("ApplicationId"));
		
		return true;
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
			taskAction.setApplicationId(tbDeathregDTO.getApmApplicationId());
			taskAction.setPaymentMode("F");
			taskAction.setDecision("SUBMITED");
		return taskAction;
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
	public List<HospitalMasterDTO> getHospitalMasterDTOList() {
		return hospitalMasterDTOList;
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

	public List<TbDeathregDTO> getTbDeathregDTOList() {
		return tbDeathregDTOList;
	}

	public List<TbDeathregDTO> setTbDeathregDTOList(List<TbDeathregDTO> tbDeathregDTOList) {
		return this.tbDeathregDTOList = tbDeathregDTOList;
	}
	

	public TbDeathregDTO getTbDeathregDTO() {
		return tbDeathregDTO;
	}

	public void setTbDeathregDTO(TbDeathregDTO tbDeathregDTO) {
		this.tbDeathregDTO = tbDeathregDTO;
	}

	public String getHospitalList() {
		return hospitalList;
	}

	public void setHospitalList(String hospitalList) {
		this.hospitalList = hospitalList;
	}

	public String getCemeteryList() {
		return cemeteryList;
	}

	public void setCemeteryList(String cemeteryList) {
		this.cemeteryList = cemeteryList;
	}

	public String getTbDeathRegDtoList() {
		return tbDeathRegDtoList;
	}

	public void setTbDeathRegDtoList(String tbDeathRegDtoList) {
		this.tbDeathRegDtoList = tbDeathRegDtoList;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}
	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
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

	public RequestDTO getRequestDTO() {
		return requestDTO;
	}

	public void setRequestDTO(RequestDTO requestDTO) {
		this.requestDTO = requestDTO;
	}

	public BndAcknowledgementDto getAckDto() {
		return ackDto;
	}

	public void setAckDto(BndAcknowledgementDto ackDto) {
		this.ackDto = ackDto;
	}

	public String getChecklistStatus() {
		return checklistStatus;
	}

	public void setChecklistStatus(String checklistStatus) {
		this.checklistStatus = checklistStatus;
	}

	public String getKdmcEnv() {
		return kdmcEnv;
	}

	public void setKdmcEnv(String kdmcEnv) {
		this.kdmcEnv = kdmcEnv;
	}
	
	
	
}