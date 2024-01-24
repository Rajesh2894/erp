package com.abm.mainet.bnd.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.bnd.constant.BndConstants;
import com.abm.mainet.bnd.dto.BirthRegistrationDTO;
import com.abm.mainet.bnd.dto.BndAcknowledgementDto;
import com.abm.mainet.bnd.dto.HospitalMasterDTO;
import com.abm.mainet.bnd.service.IBirthRegService;
import com.abm.mainet.bnd.ui.validator.BirthRegValidator;
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
public class BirthCorrectionModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;

	@Autowired
	private IBirthRegService iBirthRegSevice;

	BirthRegistrationDTO birthRegDto = new BirthRegistrationDTO();

	private List<BirthRegistrationDTO> birthRegistrationDTOList;
	
	private List<HospitalMasterDTO> hospitalMasterDTOList;

	private String hospitalList;
	
	private String saveMode;
	
	private RequestDTO requestDTO = new RequestDTO();
	
	private String birthList;
	
	BndAcknowledgementDto ackDto = new BndAcknowledgementDto();
	
	private List<DocumentDetailsVO> checkList = new ArrayList<>();
	
	private String kdmcEnv;
	
	@Autowired
	private ServiceMasterService serviceMasterService;
	
	@Autowired
    IFileUploadService fileUpload;

	@Override
	public boolean saveForm() {
		

		validateBean(birthRegDto, BirthRegValidator.class);
			if (hasValidationErrors()) {
				return false;
			} else {
				Employee employee = getUserSession().getEmployee();
				int langId = UserSession.getCurrent().getLanguageId();

				birthRegDto.setUpdatedBy(employee.getEmpId());
				birthRegDto.setUpdatedDate(new Date());
				birthRegDto.setLmodDate(new Date());
				birthRegDto.setLgIpMac(employee.getEmppiservername());
				birthRegDto.setLgIpMacUpd(employee.getEmppiservername());
				birthRegDto.setOrgId(employee.getOrganisation().getOrgid());
				birthRegDto.setLangId(langId);
				birthRegDto.setUserId(employee.getEmpId());
				List<DocumentDetailsVO> documents = getCheckList();
				 documents = fileUpload.prepareFileUpload(checkList);
				birthRegDto.setUploadDocument(checkList);
				validateInputs(documents);
				if (hasValidationErrors()) {
		            return false;
		        }
				ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(BndConstants.BRC, birthRegDto.getOrgId());
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
				this.setServiceId(birthRegDto.getServiceId());
				this.getChargesAmount();
				}
				this.setBirthRegDto(birthRegDto);
				this.setRequestDTO(birthRegDto.getRequestDTO());
				iBirthRegSevice.saveBirthCorrectionDet(birthRegDto,this);
				
				this.setSuccessMessage(getAppSession().getMessage("BirthRegDto.SuccessMsgBrc") + birthRegDto.getApmApplicationId());
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
			taskAction.setApplicationId(birthRegDto.getApmApplicationId());
			taskAction.setPaymentMode("F");
			taskAction.setDecision("SUBMITED");
		return taskAction;
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
	
	

	public List<BirthRegistrationDTO> getBirthRegistrationDTOList() {
		return birthRegistrationDTOList;
	}

	public List<BirthRegistrationDTO> setBirthRegistrationDTOList(List<BirthRegistrationDTO> birthRegistrationDTOList) {
		return this.birthRegistrationDTOList = birthRegistrationDTOList;
	}

	public void setHospitalList(String hospitalList) {
		this.hospitalList = hospitalList;
	}

	public BirthRegistrationDTO getBirthRegDto() {
		return birthRegDto;
	}

	public void setBirthRegDto(BirthRegistrationDTO birthRegDto) {
		this.birthRegDto = birthRegDto;
	}

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	public String getBirthList() {
		return birthList;
	}

	public void setBirthList(String birthList) {
		this.birthList = birthList;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
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

	public String getKdmcEnv() {
		return kdmcEnv;
	}

	public void setKdmcEnv(String kdmcEnv) {
		this.kdmcEnv = kdmcEnv;
	}
	
	
}
