package com.abm.mainet.rti.ui.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.TbApprejMas;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.rti.dto.RtiApplicationFormDetailsReqDTO;
import com.abm.mainet.rti.dto.RtiApplicationFormDetailsResponseDTO;
import com.abm.mainet.rti.dto.RtiMediaListDTO;
import com.abm.mainet.rti.service.IRtiApplicationDetailService;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Component
@Scope("session")
public class RtiDispatchModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();
	private String mediaType;
	private List<String> mediaTypeList = new ArrayList<>();
	private CFCApplicationAddressEntity cfcAddressEntity = new CFCApplicationAddressEntity();
	private TbCfcApplicationMstEntity cfcEntity = new TbCfcApplicationMstEntity();
	private List<CFCAttachment> fetchDocumentList = new ArrayList<>();
	private RtiApplicationFormDetailsReqDTO reqDTO = new RtiApplicationFormDetailsReqDTO();
	private RtiApplicationFormDetailsResponseDTO responseDTO = new RtiApplicationFormDetailsResponseDTO();
	private List<RtiMediaListDTO> rtiMediaListDTO = new ArrayList<>();
	private ServiceMaster serviceMaster = new ServiceMaster();
	private List<TbApprejMas> apprejMasList = new ArrayList<>();
	private String pioName;
	private List<CFCAttachment> fetchApplnUpload = new ArrayList<>();
	private String deliveryMode;
	private String pioNumber;

	private List<CFCAttachment> fetchPioUploadDoc = new ArrayList<>();

	@Autowired
	IRtiApplicationDetailService rtiApplicationDetailService;

	@Resource
	private CommonService commonService;

	@Autowired
	ServiceMasterService serviceMasterService;

	@Autowired
	private IWorkflowExecutionService workflowExecutionService;

	@Resource
	private ISMSAndEmailService ismsAndEmailService;

	public List<String> getMediaTypeList() {
		return mediaTypeList;
	}

	public void setMediaTypeList(List<String> mediaTypeList) {
		this.mediaTypeList = mediaTypeList;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public ApplicantDetailDTO getApplicantDetailDto() {
		return applicantDetailDto;
	}

	public void setApplicantDetailDto(ApplicantDetailDTO applicantDetailDto) {
		this.applicantDetailDto = applicantDetailDto;
	}

	public RtiApplicationFormDetailsReqDTO getReqDTO() {
		return reqDTO;
	}

	public void setReqDTO(RtiApplicationFormDetailsReqDTO reqDTO) {
		this.reqDTO = reqDTO;
	}

	public RtiApplicationFormDetailsResponseDTO getResponseDTO() {
		return responseDTO;
	}

	public void setResponseDTO(RtiApplicationFormDetailsResponseDTO responseDTO) {
		this.responseDTO = responseDTO;
	}

	public List<CFCAttachment> getFetchDocumentList() {
		return fetchDocumentList;
	}

	public void setFetchDocumentList(List<CFCAttachment> fetchDocumentList) {
		this.fetchDocumentList = fetchDocumentList;
	}

	public List<RtiMediaListDTO> getRtiMediaListDTO() {
		return rtiMediaListDTO;
	}

	public void setRtiMediaListDTO(List<RtiMediaListDTO> rtiMediaListDTO) {
		this.rtiMediaListDTO = rtiMediaListDTO;
	}

	/*
	 * public boolean validateInputs() { validateBean(this,
	 * RtiApplicationDetailValidator.class); if (hasValidationErrors()) {
	 * this.isValidationError = MainetConstants.Y_FLAG; return false; } return true;
	 * }
	 */

	@Override
	public boolean saveForm() {
		RtiApplicationFormDetailsReqDTO requestDTO = this.getReqDTO();

		try {
         //D#140951
			if(requestDTO.getServiceId()!=null) {
			String shortCode=	serviceMasterService.fetchServiceShortCode(requestDTO.getServiceId(), requestDTO.getOrgId());
			if(StringUtils.isNotEmpty(shortCode)&&shortCode.equals(MainetConstants.RTISERVICE.RTISECONDAPPEALSERVICE)) {
				requestDTO.setRtiSecndAplStatus(MainetConstants.TASK_STATUS_COMPLETED);
			}
			}
			Employee emp = UserSession.getCurrent().getEmployee();

			/* saving RTI Dispatch Details */
			rtiApplicationDetailService.saveRtiApplication(requestDTO);
			/* end */

			/* Update workflow */
			WorkflowProcessParameter workflowdto = new WorkflowProcessParameter();
			workflowdto.setProcessName(MainetConstants.URLBasedOnShortCode
					.valueOf(MainetConstants.RTISERVICE.RTIAPPLICATIONSERVICECODE).getProcessName());
			WorkflowTaskAction workflowAction = new WorkflowTaskAction();
			workflowAction.setApplicationId(requestDTO.getApmApplicationId());
			workflowAction.setDateOfAction(new Date());
			workflowAction.setOrgId(requestDTO.getOrgId());
			workflowAction.setEmpId(emp.getEmpId());
			workflowAction.setEmpType(emp.getEmplType());
			workflowAction.setEmpName(emp.getEmpname());
			workflowAction.setCreatedBy(emp.getEmpId());
			workflowAction.setCreatedDate(new Date());
			workflowAction.setTaskId(requestDTO.getTaskId());
			workflowAction.setModifiedBy(emp.getEmpId());
			workflowAction.setDecision(MainetConstants.WorkFlow.Decision.APPROVED);
			workflowdto.setWorkflowTaskAction(workflowAction);
			workflowExecutionService.updateWorkflow(workflowdto);
			/* Sending SMS to Customer after Dispatch */
			// sendSmsAndEmail(requestDTO, model);
			/* end */
		} catch (Exception e) {
			throw new FrameworkException(e);
		}
		/* end */

		return true;

	}

	private void sendSmsAndEmail(RtiApplicationFormDetailsReqDTO requestDTO, RtiDispatchModel model) {
		// TODO Auto-generated method stub
		final SMSAndEmailDTO dto = new SMSAndEmailDTO();
		String fullName = String.join(" ", Arrays.asList(model.getCfcEntity().getApmFname(),
				model.getCfcEntity().getApmMname(), model.getCfcEntity().getApmLname()));
		dto.setAppName(fullName);
		dto.setMobnumber(model.getCfcAddressEntity().getApaMobilno());
		dto.setAppNo(requestDTO.getApmApplicationId().toString());
		dto.setServName(MainetConstants.RTISERVICE.RTIAPPLICATIONSERVICENAME);
		dto.setEmail(model.getCfcAddressEntity().getApaEmail());
		String paymentUrl = MainetConstants.RTISERVICE.RTI_DISPATCH_SMS_EMAIL;
		Organisation org = new Organisation();
		org.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
		int langId = Utility.getDefaultLanguageId(org);
		dto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		ismsAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.RTI, paymentUrl,
				PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED, dto, org, langId);
	}

	public CFCApplicationAddressEntity getCfcAddressEntity() {
		return cfcAddressEntity;
	}

	public void setCfcAddressEntity(CFCApplicationAddressEntity cfcAddressEntity) {
		this.cfcAddressEntity = cfcAddressEntity;
	}

	public TbCfcApplicationMstEntity getCfcEntity() {
		return cfcEntity;
	}

	public void setCfcEntity(TbCfcApplicationMstEntity cfcEntity) {
		this.cfcEntity = cfcEntity;
	}

	public ServiceMaster getServiceMaster() {
		return serviceMaster;
	}

	public void setServiceMaster(ServiceMaster serviceMaster) {
		this.serviceMaster = serviceMaster;
	}

	public List<TbApprejMas> getApprejMasList() {
		return apprejMasList;
	}

	public void setApprejMasList(List<TbApprejMas> apprejMasList) {
		this.apprejMasList = apprejMasList;
	}

	public String getPioName() {
		return pioName;
	}

	public void setPioName(String pioName) {
		this.pioName = pioName;
	}

	public List<CFCAttachment> getFetchApplnUpload() {
		return fetchApplnUpload;
	}

	public void setFetchApplnUpload(List<CFCAttachment> fetchApplnUpload) {
		this.fetchApplnUpload = fetchApplnUpload;
	}

	public String getDeliveryMode() {
		return deliveryMode;
	}

	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	public String getPioNumber() {
		return pioNumber;
	}

	public void setPioNumber(String pioNumber) {
		this.pioNumber = pioNumber;
	}

	public List<CFCAttachment> getFetchPioUploadDoc() {
		return fetchPioUploadDoc;
	}

	public void setFetchPioUploadDoc(List<CFCAttachment> fetchPioUploadDoc) {
		this.fetchPioUploadDoc = fetchPioUploadDoc;
	}

}
