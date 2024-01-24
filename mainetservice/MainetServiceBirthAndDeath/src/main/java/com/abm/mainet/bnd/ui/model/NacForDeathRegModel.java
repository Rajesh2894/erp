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
import com.abm.mainet.bnd.dto.DeathCertificateDTO;
import com.abm.mainet.bnd.service.IssuenceOfDeathCertificateService;
import com.abm.mainet.cfc.challan.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author Arun Shinde
 *
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class NacForDeathRegModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String serviceName;
	private Long applicationId;
	private String applicantName;
	private boolean noCheckListFound;
	private String errorMessage;
	private String saveMode;
	private String formName;
	private Map<Long, String> wardList = null;

	private DeathCertificateDTO deathCertificateDTO = new DeathCertificateDTO();
	
	BndAcknowledgementDto ackDto = new BndAcknowledgementDto();

	private List<DocumentDetailsVO> checkList = new ArrayList<>();

	private List<CFCAttachment> documentList = new ArrayList<>();

	private RequestDTO requestDTO = new RequestDTO();

	private TbCfcApplicationMstEntity cfcEntity = new TbCfcApplicationMstEntity();

	@Autowired
	private ServiceMasterService serviceMasterService;

	@Autowired
	private IssuenceOfDeathCertificateService issuenceOfDeathCertificateService;

	@Override
	public boolean saveForm() {

		Employee employee = getUserSession().getEmployee();
		int langId = UserSession.getCurrent().getLanguageId();

		deathCertificateDTO.setUpdatedBy(employee.getEmpId());
		deathCertificateDTO.setUpdatedDate(new Date());
		deathCertificateDTO.setLgIpMacUpd(employee.getEmppiservername());
		deathCertificateDTO.setLmoddate(new Date());
		deathCertificateDTO.setLgIpMac(employee.getEmppiservername());
		deathCertificateDTO.setOrgId(employee.getOrganisation().getOrgid());
		deathCertificateDTO.setLangId(langId);
		deathCertificateDTO.setUserId(employee.getUserId());
		requestDTO.setUpdatedBy(deathCertificateDTO.getUpdatedBy());
		requestDTO.setMacId(deathCertificateDTO.getLgIpMacUpd());
		requestDTO.setLangId(Long.valueOf(deathCertificateDTO.getLangId()));
		ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
				.prepareFileUpload(checkList);
		deathCertificateDTO.setDocumentList(checkList);
		ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(BndConstants.NDR,
				deathCertificateDTO.getOrgId());
		if (serviceMas.getSmFeesSchedule() != 0) {
			CommonChallanDTO offline = getOfflineDTO();
			final String modeDesc = getNonHierarchicalLookUpObject(offline.getOflPaymentMode()).getLookUpCode();
			offline.setOfflinePaymentText(modeDesc);
			// offline.setAmountToShow(deathCertificateDTO.getAmount());
			if(this.getChargesAmount()!=null)
				offline.setAmountToShow(Double.valueOf(this.getChargesAmount()));
			if (offline.getAmountToShow() > 0d) {
				validateBean(offline, CommonOfflineMasterValidator.class);
				if (hasValidationErrors()) {
		            return false;
		        }
			}
			this.setServiceId(serviceMas.getSmServiceId());
			this.getChargesAmount();
		}
		if(this.getChargesAmount()!=null)
			deathCertificateDTO.setAmount(Double.valueOf(this.getChargesAmount()));
		this.setDeathCertificateDTO(deathCertificateDTO);
		this.setRequestDTO(requestDTO);
		issuenceOfDeathCertificateService.saveDeathRegDetails(deathCertificateDTO, this);
		this.setSuccessMessage(getAppSession().getMessage("death.succes.msg") + deathCertificateDTO.getApplnId());
		return true;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public boolean isNoCheckListFound() {
		return noCheckListFound;
	}

	public void setNoCheckListFound(boolean noCheckListFound) {
		this.noCheckListFound = noCheckListFound;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public DeathCertificateDTO getDeathCertificateDTO() {
		return deathCertificateDTO;
	}

	public void setDeathCertificateDTO(DeathCertificateDTO deathCertificateDTO) {
		this.deathCertificateDTO = deathCertificateDTO;
	}

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	public List<CFCAttachment> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<CFCAttachment> documentList) {
		this.documentList = documentList;
	}

	public RequestDTO getRequestDTO() {
		return requestDTO;
	}

	public void setRequestDTO(RequestDTO requestDTO) {
		this.requestDTO = requestDTO;
	}

	public TbCfcApplicationMstEntity getCfcEntity() {
		return cfcEntity;
	}

	public void setCfcEntity(TbCfcApplicationMstEntity cfcEntity) {
		this.cfcEntity = cfcEntity;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Map<Long, String> getWardList() {
		return wardList;
	}

	public void setWardList(Map<Long, String> wardList) {
		this.wardList = wardList;
	}

	public BndAcknowledgementDto getAckDto() {
		return ackDto;
	}

	public void setAckDto(BndAcknowledgementDto ackDto) {
		this.ackDto = ackDto;
	}
	
	
}
