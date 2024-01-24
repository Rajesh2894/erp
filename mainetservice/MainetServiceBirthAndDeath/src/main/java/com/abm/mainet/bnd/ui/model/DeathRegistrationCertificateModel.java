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
import com.abm.mainet.bnd.dto.HospitalMasterDTO;
import com.abm.mainet.bnd.dto.TbDeathregDTO;
import com.abm.mainet.bnd.service.IssuenceOfDeathCertificateService;
import com.abm.mainet.cfc.challan.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;

@Component
@Scope(value =WebApplicationContext.SCOPE_SESSION)
public class DeathRegistrationCertificateModel extends AbstractFormModel{


	private static final long serialVersionUID = 1L;
	
	@Autowired
	private IssuenceOfDeathCertificateService iDeathRegistrationService;
	
	TbDeathregDTO tbDeathregDTO = new  TbDeathregDTO();
	
	BndAcknowledgementDto ackDto = new BndAcknowledgementDto();
	
	private List<DocumentDetailsVO> checkList = new ArrayList<>();
	
	private List<HospitalMasterDTO> hospitalMasterDTOList;
	
	private String saveMode;
	
	private String actionViewFlag;
	
	private RequestDTO requestDTO = new RequestDTO();
	
	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	private String hospitalList;
	
	@Autowired
	private ServiceMasterService serviceMasterService;
	
	@Override
	public boolean saveForm() {
		Employee employee = getUserSession().getEmployee();
		int langId = UserSession.getCurrent().getLanguageId();
		
		tbDeathregDTO.setUpdatedBy(employee.getEmpId());
		tbDeathregDTO.setUpdatedDate(new Date());
		tbDeathregDTO.setLmoddate(new Date());
		tbDeathregDTO.setLgIpMac(employee.getEmppiservername());
		tbDeathregDTO.setLgIpMacUpd(employee.getEmppiservername());
		tbDeathregDTO.setOrgId(employee.getOrganisation().getOrgid());
		tbDeathregDTO.setLangId(langId);
		tbDeathregDTO.setDrRegdate(new Date());
		tbDeathregDTO.setRegAplDate(new Date());
		
		ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(BndConstants.IDC, tbDeathregDTO.getOrgId());
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
		Map<String, Object> applicationNo = iDeathRegistrationService.saveIssuanceDeathCertificateDetail(tbDeathregDTO,this);
		tbDeathregDTO.setApplicationNo(applicationNo.get("ApplicationId").toString());
		this.setSuccessMessage(getAppSession().getMessage("TbDeathregDTO.SuccessMsgDrCert") + applicationNo.get("ApplicationId"));
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
			taskAction.setApplicationId(Long.valueOf(tbDeathregDTO.getApplicationNo()));
			taskAction.setPaymentMode("F");
			taskAction.setDecision("SUBMITED");
		return taskAction;
	}
	
	
	
	
	
	
	
	
	
	
	
	/*public IDeathRegistrationService getiDeathRegistrationService() {
		return iDeathRegistrationService;
	}

	public void setiDeathRegistrationService(IDeathRegistrationService iDeathRegistrationService) {
		this.iDeathRegistrationService = iDeathRegistrationService;
	}*/

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

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	public String getActionViewFlag() {
		return actionViewFlag;
	}

	public void setActionViewFlag(String actionViewFlag) {
		this.actionViewFlag = actionViewFlag;
	}
	
	
	
}
