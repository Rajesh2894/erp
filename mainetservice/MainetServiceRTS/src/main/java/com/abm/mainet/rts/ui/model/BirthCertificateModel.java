package com.abm.mainet.rts.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.cfc.challan.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
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
import com.abm.mainet.rts.constant.RtsConstants;
import com.abm.mainet.rts.dto.BirthCertificateDTO;
import com.abm.mainet.rts.service.IBirthCertificateService;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class BirthCertificateModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;

	@Autowired
	private IBirthCertificateService birthCer;

	@Autowired
	private ServiceMasterService serviceMasterService;

	RequestDTO requestDTO = new RequestDTO();

	BirthCertificateDTO birthCertificateDto = new BirthCertificateDTO();

	private List<DocumentDetailsVO> checkList = new ArrayList<>();

	private List<CFCAttachment> fetchDocumentList = new ArrayList<>();

	private List<CFCAttachment> documentList = new ArrayList<>();

	private TbCfcApplicationMstEntity cfcEntity = new TbCfcApplicationMstEntity();

	private Long applicationId;
	
	private String saveMode;
	
	private String formName;
	
	private String orgName;

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

	private String applicantName;

	public List<CFCAttachment> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<CFCAttachment> documentList) {
		this.documentList = documentList;
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

	public BirthCertificateDTO getBirthCertificateDto() {
		return birthCertificateDto;
	}

	public void setBirthCertificateDto(BirthCertificateDTO birthCertificateDto) {
		this.birthCertificateDto = birthCertificateDto;
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

	@Override
	public boolean saveForm() {

		Employee employee = getUserSession().getEmployee();
		int langId = UserSession.getCurrent().getLanguageId();
		birthCertificateDto.setUpdatedBy(employee.getEmpId());
		birthCertificateDto.setUpdatedDate(new Date());
		birthCertificateDto.setLmoddate(new Date());
		birthCertificateDto.setLgIpMac(employee.getEmppiservername());
		birthCertificateDto.setLgIpMacUpd(employee.getEmppiservername());
		birthCertificateDto.setOrgId(employee.getOrganisation().getOrgid());
		birthCertificateDto.setLangId(langId);
		birthCertificateDto.setUserId(employee.getUserId());
		this.getRequestDTO().setLangId(Long.valueOf(birthCertificateDto.getLangId()));
		ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
				.prepareFileUpload(checkList);
		birthCertificateDto.setUploadDocument(checkList);
		// birthCertificateDto.setBrRegDate(new Date());
		ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(RtsConstants.RBC,
				birthCertificateDto.getOrgId());
		if (serviceMas.getSmFeesSchedule() != 0) {
			CommonChallanDTO offline = getOfflineDTO();
			final String modeDesc = getNonHierarchicalLookUpObject(offline.getOflPaymentMode()).getLookUpCode();
			offline.setOfflinePaymentText(modeDesc);
			offline.setAmountToShow(Double.parseDouble(this.getChargesAmount()));
			if (offline.getAmountToShow() > 0d) {
				validateBean(offline, CommonOfflineMasterValidator.class);
			}
			birthCertificateDto.setSmServiceId(serviceMas.getSmServiceId());
			this.setServiceId(birthCertificateDto.getSmServiceId());
			this.getChargesAmount();
		}
		birthCer.saveBirthCertificate(birthCertificateDto, this);
		this.setSuccessMessage(getAppSession().getMessage("BirthCertificateDTO.succes.msg")
				+ birthCertificateDto.getApmApplicationId());
		
		return true;

	}

}
