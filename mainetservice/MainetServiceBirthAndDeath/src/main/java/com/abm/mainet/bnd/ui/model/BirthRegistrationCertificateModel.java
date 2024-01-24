package com.abm.mainet.bnd.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.bnd.constant.BndConstants;
import com.abm.mainet.bnd.dto.BirthReceiptDTO;
import com.abm.mainet.bnd.dto.BirthRegistrationDTO;
import com.abm.mainet.bnd.dto.BndAcknowledgementDto;
import com.abm.mainet.bnd.dto.HospitalMasterDTO;
import com.abm.mainet.bnd.service.IssuenceOfBirthCertificateService;
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
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class BirthRegistrationCertificateModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;

	@Autowired
	private IssuenceOfBirthCertificateService issuenceOfBirthCertificateService;

	BirthRegistrationDTO birthRegDto = new BirthRegistrationDTO();
	
	BirthReceiptDTO birthReceiptData = new BirthReceiptDTO();

	private List<HospitalMasterDTO> hospitalMasterDTOList;
	
	private String saveMode;
	
	private String actionViewFlag;
	private RequestDTO requestDTO = new RequestDTO();
	
	BndAcknowledgementDto ackDto = new BndAcknowledgementDto();
	
	private List<DocumentDetailsVO> checkList = new ArrayList<>();
	
	private String kdmcEnv;

	@Autowired
	private ServiceMasterService serviceMasterService;
	
	public List<HospitalMasterDTO> getHospitalMasterDTOList() {
		return hospitalMasterDTOList;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public List<HospitalMasterDTO> setHospitalMasterDTOList(List<HospitalMasterDTO> hospitalMasterDTOList) {
		return this.hospitalMasterDTOList = hospitalMasterDTOList;
	}

	@Override
	public boolean saveForm() {
		Employee employee = getUserSession().getEmployee();
		int langId = UserSession.getCurrent().getLanguageId();
		birthRegDto.setUpdatedBy(employee.getEmpId());
		birthRegDto.setUpdatedDate(new Date());
		birthRegDto.setLgIpMac(employee.getEmppiservername());
		birthRegDto.setLgIpMacUpd(employee.getEmppiservername());
		birthRegDto.setOrgId(employee.getOrganisation().getOrgid());
		birthRegDto.setLangId(langId);
		birthRegDto.setRegAplDate(new Date());
		birthRegDto.setUserId(employee.getUserId());
		
		ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(BndConstants.IBC, birthRegDto.getOrgId());
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
		Long ApplicationNo = issuenceOfBirthCertificateService.saveIssuanceOfBirtCert(birthRegDto,this);
		if(ApplicationNo==null)
			return false;
		birthRegDto.setApplicationId(ApplicationNo.toString());
		this.setSuccessMessage(getAppSession().getMessage("BirthRegDto.SuccessMsgBrCert") + ApplicationNo);
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
			taskAction.setApplicationId(Long.valueOf(birthRegDto.getApplicationId()));
			taskAction.setPaymentMode("F");
			taskAction.setDecision("SUBMITED");
		return taskAction;
	}
	
	public BirthRegistrationDTO getBirthRegDto() {
		return birthRegDto;
	}

	public void setBirthRegDto(BirthRegistrationDTO birthRegDto) {
		this.birthRegDto = birthRegDto;
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

	public BirthReceiptDTO getBirthReceiptData() {
		return birthReceiptData;
	}

	public void setBirthReceiptData(BirthReceiptDTO birthReceiptData) {
		this.birthReceiptData = birthReceiptData;
	}

	public String getActionViewFlag() {
		return actionViewFlag;
	}

	public void setActionViewFlag(String actionViewFlag) {
		this.actionViewFlag = actionViewFlag;
	}

	public String getKdmcEnv() {
		return kdmcEnv;
	}

	public void setKdmcEnv(String kdmcEnv) {
		this.kdmcEnv = kdmcEnv;
	}
	
	

}
