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
import com.abm.mainet.bnd.dto.BirthCertificateDTO;
import com.abm.mainet.bnd.dto.BndAcknowledgementDto;
import com.abm.mainet.bnd.service.IssuenceOfBirthCertificateService;
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
public class NacForBirthRegModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;

	private String saveMode;

	private String formName;

	private Long applicationId;

	private String applicantName;

	private BirthCertificateDTO nacForBirthRegDTO = new BirthCertificateDTO();
	
	BndAcknowledgementDto ackDto = new BndAcknowledgementDto();

	private List<DocumentDetailsVO> checkList = new ArrayList<>();

	private RequestDTO requestDTO = new RequestDTO();

	private List<CFCAttachment> fetchDocumentList = new ArrayList<>();

	private List<CFCAttachment> documentList = new ArrayList<>();

	private TbCfcApplicationMstEntity cfcEntity = new TbCfcApplicationMstEntity();
	
	private Map<Long, String> wardList = null;

	@Autowired
	private ServiceMasterService serviceMasterService;

	@Autowired
	private IssuenceOfBirthCertificateService birthrRgSrevice;

	@Override
	public boolean saveForm() {

		Employee employee = getUserSession().getEmployee();
		int langId = UserSession.getCurrent().getLanguageId();
		nacForBirthRegDTO.setUpdatedBy(employee.getEmpId());
		nacForBirthRegDTO.setUpdatedDate(new Date());
		nacForBirthRegDTO.setLmoddate(new Date());
		nacForBirthRegDTO.setLgIpMac(employee.getEmppiservername());
		nacForBirthRegDTO.setLgIpMacUpd(employee.getEmppiservername());
		nacForBirthRegDTO.setOrgId(employee.getOrganisation().getOrgid());
		nacForBirthRegDTO.setLangId(langId);
		nacForBirthRegDTO.setUserId(employee.getUserId());
		this.getRequestDTO().setLangId(Long.valueOf(nacForBirthRegDTO.getLangId()));
		ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
				.prepareFileUpload(checkList);
		nacForBirthRegDTO.setUploadDocument(checkList);
		ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(BndConstants.NBR,
				nacForBirthRegDTO.getOrgId());
		if (serviceMas.getSmFeesSchedule() != 0) {
			CommonChallanDTO offline = getOfflineDTO();
			final String modeDesc = getNonHierarchicalLookUpObject(offline.getOflPaymentMode()).getLookUpCode();
			offline.setOfflinePaymentText(modeDesc);
			offline.setAmountToShow(Double.parseDouble(this.getChargesAmount()));
			if (offline.getAmountToShow() > 0d) {
				validateBean(offline, CommonOfflineMasterValidator.class);
				if (hasValidationErrors()) {
		            return false;
		        }
			}
			nacForBirthRegDTO.setSmServiceId(serviceMas.getSmServiceId());
			this.setServiceId(nacForBirthRegDTO.getSmServiceId());
			this.getChargesAmount();
		}
		birthrRgSrevice.saveBirthCertificate(nacForBirthRegDTO, this);
		this.setSuccessMessage(
				getAppSession().getMessage("bnd.succes.msg") + nacForBirthRegDTO.getApmApplicationId());

		return true;

	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public BirthCertificateDTO getNacForBirthRegDTO() {
		return nacForBirthRegDTO;
	}

	public void setNacForBirthRegDTO(BirthCertificateDTO nacForBirthRegDTO) {
		this.nacForBirthRegDTO = nacForBirthRegDTO;
	}

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	public RequestDTO getRequestDTO() {
		return requestDTO;
	}

	public void setRequestDTO(RequestDTO requestDTO) {
		this.requestDTO = requestDTO;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public List<CFCAttachment> getFetchDocumentList() {
		return fetchDocumentList;
	}

	public void setFetchDocumentList(List<CFCAttachment> fetchDocumentList) {
		this.fetchDocumentList = fetchDocumentList;
	}

	public List<CFCAttachment> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<CFCAttachment> documentList) {
		this.documentList = documentList;
	}

	public TbCfcApplicationMstEntity getCfcEntity() {
		return cfcEntity;
	}

	public void setCfcEntity(TbCfcApplicationMstEntity cfcEntity) {
		this.cfcEntity = cfcEntity;
	}

	public ServiceMasterService getServiceMasterService() {
		return serviceMasterService;
	}

	public void setServiceMasterService(ServiceMasterService serviceMasterService) {
		this.serviceMasterService = serviceMasterService;
	}

	public IssuenceOfBirthCertificateService getBirthrRgSrevice() {
		return birthrRgSrevice;
	}

	public void setBirthrRgSrevice(IssuenceOfBirthCertificateService birthrRgSrevice) {
		this.birthrRgSrevice = birthrRgSrevice;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
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
