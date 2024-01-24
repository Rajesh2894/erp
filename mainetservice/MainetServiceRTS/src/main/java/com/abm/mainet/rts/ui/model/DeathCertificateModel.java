package com.abm.mainet.rts.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.cfc.challan.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.rts.constant.RtsConstants;
import com.abm.mainet.rts.dto.DeathCertificateDTO;
import com.abm.mainet.rts.service.IDeathCertificateService;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class DeathCertificateModel extends AbstractFormModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ServiceMasterService serviceMasterService;
	
	@Autowired
	private IDeathCertificateService iDeathCertificateService;
	
	@Autowired
	private TbTaxMasService tbTaxMasService;
	
	private DeathCertificateDTO deathCertificateDTO = new DeathCertificateDTO();
	
	private List<DocumentDetailsVO> checkList = new ArrayList<>();
	
	 private List<CFCAttachment> documentList = new ArrayList<>();
	 
	 private RequestDTO requestDTO = new RequestDTO();
	 
	 private String serviceName;
	 private Long applicationId;
	 private String applicantName;
	 private boolean noCheckListFound ;
	 private String errorMessage;
	 private String saveMode;
	private String formName;
	private String orgName;
	 
	 private TbCfcApplicationMstEntity cfcEntity = new TbCfcApplicationMstEntity();
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
		ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class).prepareFileUpload(checkList);
		deathCertificateDTO.setDocumentList(checkList);
		ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(RtsConstants.RDC, deathCertificateDTO.getOrgId());
		if(serviceMas.getSmFeesSchedule()!=0)
		{ 
			CommonChallanDTO offline = getOfflineDTO();
			final String modeDesc = getNonHierarchicalLookUpObject(offline.getOflPaymentMode()).getLookUpCode();
			offline.setOfflinePaymentText(modeDesc);
			offline.setAmountToShow(deathCertificateDTO.getAmount());
			if (offline.getAmountToShow() > 0d) {
				validateBean(offline, CommonOfflineMasterValidator.class);
			}
			this.setServiceId(serviceMas.getSmServiceId());
			this.getChargesAmount();
		}
		this.setDeathCertificateDTO(deathCertificateDTO);
		this.setRequestDTO(requestDTO);
		iDeathCertificateService.saveDeathCertificate(deathCertificateDTO, this);
		
		this.setSuccessMessage(getAppSession().getMessage("TbDeathregDTO.succes.msg")
	 					+ deathCertificateDTO.getApplnId());
	return true;
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


	public TbCfcApplicationMstEntity getCfcEntity() {
		return cfcEntity;
	}


	public void setCfcEntity(TbCfcApplicationMstEntity cfcEntity) {
		this.cfcEntity = cfcEntity;
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


	public RequestDTO getRequestDTO() {
		return requestDTO;
	}


	public void setRequestDTO(RequestDTO requestDTO) {
		this.requestDTO = requestDTO;
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


	public String getOrgName() {
		return orgName;
	}


	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	
	
}
