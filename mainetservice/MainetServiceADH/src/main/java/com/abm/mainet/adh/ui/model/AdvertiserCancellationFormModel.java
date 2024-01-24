package com.abm.mainet.adh.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.adh.dto.AdvertiserMasterDto;
import com.abm.mainet.adh.service.IAdvertiserMasterService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.ibm.icu.math.BigDecimal;
import java.io.File;
import java.util.Map;
import java.util.Set;
import com.abm.mainet.adh.dto.AgencyRegistrationRequestDto;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.dto.DocumentDetailsDto;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.StringUtility;

/**
 * @author Sharvan kumar Mandal
 * @since 18-06-2021
 */

@Component
@Scope("session")
public class AdvertiserCancellationFormModel extends AbstractFormModel {

	private static final long serialVersionUID = 8187986486520652402L;

	private static Logger log = Logger.getLogger(AdvertiserCancellationFormModel.class);

	@Autowired
	IFileUploadService fileUpload;

	@Autowired
	private IChallanService challanService;

	@Autowired
	ISMSAndEmailService iSMSAndEmailService;

	@Autowired
	private ICFCApplicationMasterService cfcApplicationService;

	@Autowired
	private IAdvertiserMasterService avertiserMasterService;

	private AgencyRegistrationRequestDto agencyRequestDto = new AgencyRegistrationRequestDto();

	private List<AdvertiserMasterDto> advertiserMasterDtoList = new ArrayList<>();

	private List<DocumentDetailsVO> approvalDocumentAttachment = new ArrayList<>();

	private ServiceMaster serviceMaster = new ServiceMaster();

	private List<DocumentDetailsDto> checkList = new ArrayList<>();

	private String paymentCheck;

	private String checklistCheck;

	private String checkListApplFlag;

	private String appChargeFlag;

	private BigDecimal totalApplicationFee;

	private String applicationchargeApplFlag;

	private LookUp agenctCategoryLookUp = new LookUp();

	private String payableFlag;

	private double amountToPay;

	private String paymentMode;

	private String saveMode = "C";

	private String scrutunyEditMode;

	@Autowired
	IAdvertiserMasterService advertiserMasterService;

	private AdvertiserMasterDto advertiserDto = new AdvertiserMasterDto();

	private List<String[]> agencyLicNoAndNameList = new ArrayList<>();

	private ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();

	private List<CFCAttachment> documentList = new ArrayList<>();
	
	// Added new Model class for User Story 112154 

	public LookUp getAgenctCategoryLookUp() {
		return agenctCategoryLookUp;
	}

	public void setAgenctCategoryLookUp(LookUp agenctCategoryLookUp) {
		this.agenctCategoryLookUp = agenctCategoryLookUp;
	}

	public List<AdvertiserMasterDto> getAdvertiserMasterDtoList() {
		return advertiserMasterDtoList;
	}

	public void setAdvertiserMasterDtoList(List<AdvertiserMasterDto> advertiserMasterDtoList) {
		this.advertiserMasterDtoList = advertiserMasterDtoList;
	}

	public ApplicantDetailDTO getApplicantDetailDto() {
		return applicantDetailDto;
	}

	public void setApplicantDetailDto(ApplicantDetailDTO applicantDetailDto) {
		this.applicantDetailDto = applicantDetailDto;
	}

	public ServiceMaster getServiceMaster() {
		return serviceMaster;
	}

	public void setServiceMaster(ServiceMaster serviceMaster) {
		this.serviceMaster = serviceMaster;
	}

	public AgencyRegistrationRequestDto getAgencyRequestDto() {
		return agencyRequestDto;
	}

	public void setAgencyRequestDto(AgencyRegistrationRequestDto agencyRequestDto) {
		this.agencyRequestDto = agencyRequestDto;
	}

	public String getPaymentCheck() {
		return paymentCheck;
	}

	public void setPaymentCheck(String paymentCheck) {
		this.paymentCheck = paymentCheck;
	}

	public String getChecklistCheck() {
		return checklistCheck;
	}

	public void setChecklistCheck(String checklistCheck) {
		this.checklistCheck = checklistCheck;
	}

	public String getCheckListApplFlag() {
		return checkListApplFlag;
	}

	public void setCheckListApplFlag(String checkListApplFlag) {
		this.checkListApplFlag = checkListApplFlag;
	}

	public String getAppChargeFlag() {
		return appChargeFlag;
	}

	public void setAppChargeFlag(String appChargeFlag) {
		this.appChargeFlag = appChargeFlag;
	}

	public BigDecimal getTotalApplicationFee() {
		return totalApplicationFee;
	}

	public void setTotalApplicationFee(BigDecimal totalApplicationFee) {
		this.totalApplicationFee = totalApplicationFee;
	}

	public String getApplicationchargeApplFlag() {
		return applicationchargeApplFlag;
	}

	public void setApplicationchargeApplFlag(String applicationchargeApplFlag) {
		this.applicationchargeApplFlag = applicationchargeApplFlag;
	}

	public List<CFCAttachment> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<CFCAttachment> documentList) {
		this.documentList = documentList;
	}

	public AdvertiserMasterDto getAdvertiserDto() {
		return advertiserDto;
	}

	public void setAdvertiserDto(AdvertiserMasterDto advertiserDto) {
		this.advertiserDto = advertiserDto;
	}

	public List<String[]> getAgencyLicNoAndNameList() {
		return agencyLicNoAndNameList;
	}

	public void setAgencyLicNoAndNameList(List<String[]> agencyLicNoAndNameList) {
		this.agencyLicNoAndNameList = agencyLicNoAndNameList;
	}

	public boolean validateInputs(final List<DocumentDetailsVO> dto) {
		boolean flag = false;
		if ((dto != null) && !dto.isEmpty()) {
			for (final DocumentDetailsVO doc : dto) {
				if (doc.getCheckkMANDATORY().equals(MainetConstants.FlagY)) {
					if (doc.getDocumentByteCode() == null) {
						addValidationError(
								ApplicationSession.getInstance().getMessage("adh.upload.mandatory.documents"));
						flag = true;
						break;
					}
				}
			}

		}

		return flag;
	}

	public void populateApplicationData(final long applicationId) {

		ApplicantDetailDTO applicantDetail = new ApplicantDetailDTO();
		applicantDetail.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		populateApplicantionDetails(applicantDetail, applicationId);
		List<AdvertiserMasterDto> agencyRegistrationByAppIdAndOrgId = ApplicationContextProvider.getApplicationContext()
				.getBean(IAdvertiserMasterService.class).getAgencyRegistrationByAppIdAndOrgId(applicationId,
						UserSession.getCurrent().getOrganisation().getOrgid());

		// getAgencyRequestDto().setMasterDtolist(agencyRegistrationByAppIdAndOrgId);
		getAgencyRequestDto().setMasterDto(agencyRegistrationByAppIdAndOrgId.get(0));
		setApplicantDetailDto(applicantDetail);
		this.setScrutunyEditMode(MainetConstants.FlagV);

	}

	public ApplicantDetailDTO populateApplicantionDetails(ApplicantDetailDTO detailDto, Long applicationNo) {

		TbCfcApplicationMstEntity masterEntity = cfcApplicationService.getCFCApplicationByApplicationId(applicationNo,
				detailDto.getOrgId());

		if (masterEntity != null) {
			detailDto.setApplicantTitle(masterEntity.getApmTitle());
			detailDto.setApplicantFirstName(masterEntity.getApmFname());
			detailDto.setApplicantLastName(masterEntity.getApmLname());
			if (StringUtils.isNotBlank(masterEntity.getApmMname())) {
				detailDto.setApplicantMiddleName(masterEntity.getApmMname());
			}
			if (StringUtils.isNotBlank(masterEntity.getApmBplNo())) {
				detailDto.setIsBPL(MainetConstants.YES);
				detailDto.setBplNo(masterEntity.getApmBplNo());
			} else {
				detailDto.setIsBPL(MainetConstants.NO);
			}

			if (masterEntity.getApmUID() != null && masterEntity.getApmUID() != 0) {
				detailDto.setAadharNo(String.valueOf(masterEntity.getApmUID()));
			}
		}

		CFCApplicationAddressEntity addressEntity = cfcApplicationService.getApplicantsDetails(applicationNo);
		if (addressEntity != null) {
			detailDto.setMobileNo(addressEntity.getApaMobilno());
			detailDto.setEmailId(addressEntity.getApaEmail());
			detailDto.setAreaName(addressEntity.getApaAreanm());
			detailDto.setPinCode(String.valueOf(addressEntity.getApaPincode()));
			if (addressEntity.getApaZoneNo() != null && addressEntity.getApaZoneNo() != 0) {
				detailDto.setDwzid1(addressEntity.getApaZoneNo());
			}
			if (addressEntity.getApaWardNo() != null && addressEntity.getApaWardNo() != 0) {
				detailDto.setDwzid2(addressEntity.getApaWardNo());
			}
			if (StringUtils.isNotBlank(addressEntity.getApaBlockno())) {
				detailDto.setDwzid3(Long.valueOf(addressEntity.getApaBlockno()));
			}
			if (StringUtils.isNotBlank(addressEntity.getApaCityName())) {
				detailDto.setVillageTownSub(addressEntity.getApaCityName());
			}
			if (StringUtils.isNotBlank(addressEntity.getApaRoadnm())) {
				detailDto.setRoadName(addressEntity.getApaRoadnm());
			}
		}
		return detailDto;
	}

	public boolean AgencyRegistrationApprovalAction() {
		WorkflowTaskAction taskAction = getWorkflowActionDto();
		taskAction.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		taskAction.setEmpName(UserSession.getCurrent().getEmployee().getEmpname());
		taskAction.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		taskAction.setCreatedDate(new Date());
		taskAction.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
		taskAction.setDateOfAction(new Date());
		taskAction.setIsFinalApproval(MainetConstants.FAILED);
		taskAction.setIsObjectionAppealApplicable(MainetConstants.FAILED);
		taskAction.setDecision(getWorkflowActionDto().getDecision());
		taskAction.setApplicationId(getApmApplicationId());
		taskAction.setTaskId(getWorkflowActionDto().getTaskId());

		return avertiserMasterService.executeApprovalWorkflowAction(taskAction, getServiceMaster().getSmShortdesc(),
				getServiceMaster().getSmServiceId(), getServiceMaster().getSmShortdesc());

	}

	public boolean closeWorkFlowTask() {
		boolean status = false;
		WorkflowTaskAction taskAction = new WorkflowTaskAction();
		taskAction.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		taskAction.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
		taskAction.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		taskAction.setDateOfAction(new Date());
		taskAction.setCreatedDate(new Date());
		taskAction.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		taskAction.setIsFinalApproval(true);
		taskAction.setIsObjectionAppealApplicable(false);
		if (StringUtils.isNotBlank(UserSession.getCurrent().getEmployee().getEmpemail())) {
			taskAction.setEmpEmail(UserSession.getCurrent().getEmployee().getEmpemail());
		}
		taskAction.setApplicationId(getApmApplicationId());
		taskAction.setDecision(MainetConstants.WorkFlow.Decision.APPROVED);
		taskAction.setTaskId(getWorkflowActionDto().getTaskId());

		WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();
		LookUp workProcessLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(
				getServiceMaster().getSmProcessId(), UserSession.getCurrent().getOrganisation());
		workflowProcessParameter.setProcessName(workProcessLookUp.getDescLangFirst());
		workflowProcessParameter.setWorkflowTaskAction(taskAction);
		try {
			ApplicationContextProvider.getApplicationContext().getBean(IWorkflowExecutionService.class)
					.updateWorkflow(workflowProcessParameter);
			status = true;
		} catch (Exception exception) {
			throw new FrameworkException("Exception occured while updating work flow", exception);
		}
		return status;
	}

	public void sendSmsEmail(AdvertiserCancellationFormModel model, String menuUrl, String msgType) {
		SMSAndEmailDTO smsDto = new SMSAndEmailDTO();
		smsDto.setOrgId(model.getOrgId());
		smsDto.setLangId(UserSession.getCurrent().getLanguageId());
		smsDto.setAppNo(String.valueOf(model.getApmApplicationId()));
		smsDto.setServName(getServiceMaster().getSmServiceName());
		smsDto.setMobnumber(model.getAgencyRequestDto().getMasterDto().getAgencyContactNo());
		smsDto.setAppName(model.getAgencyRequestDto().getMasterDto().getAgencyOwner());
		smsDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		if (StringUtils.isNotBlank(model.getAgencyRequestDto().getMasterDto().getAgencyEmail())) {
			smsDto.setEmail(model.getAgencyRequestDto().getMasterDto().getAgencyEmail());
		}

		ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class).sendEmailSMS(
				MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE, menuUrl, msgType, smsDto,
				UserSession.getCurrent().getOrganisation(), UserSession.getCurrent().getLanguageId());

	}

	public List<CFCAttachment> preparePreviewOfFileUpload(final List<CFCAttachment> downloadDocs,
			List<DocumentDetailsVO> docs) {
		if ((FileUploadUtility.getCurrent().getFileMap() != null)
				&& !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
			long count = 1;
			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				final List<File> list = new ArrayList<>(entry.getValue());
				for (final File file : list) {
					try {
						CFCAttachment c = new CFCAttachment();
						String path = StringUtility.staticStringBeforeChar(MainetConstants.FILE_PATH_SEPARATOR,
								file.getPath());
						c.setAttPath(path);
						c.setAttFname(file.getName());
						c.setClmSrNo(count);
						docs.stream().filter(doc -> doc.getDocumentSerialNo().equals(entry.getKey() + 1))
								.forEach(doc -> {
									c.setClmDesc(doc.getDoc_DESC_ENGL());
									c.setClmId(doc.getDocumentId());
									c.setClmDesc(doc.getDoc_DESC_Mar());
									c.setClmDesc(doc.getDoc_DESC_ENGL());

								});
						count++;
						downloadDocs.add(c);
					} catch (final Exception e) {
						log.error("Exception has been occurred in file byte to string conversions", e);
					}
				}
			}
		}

		return downloadDocs;
	}

	public double getAmountToPay() {
		return amountToPay;
	}

	public void setAmountToPay(double amountToPay) {
		this.amountToPay = amountToPay;
	}

	public String getPayableFlag() {
		return payableFlag;
	}

	public void setPayableFlag(String payableFlag) {
		this.payableFlag = payableFlag;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public List<DocumentDetailsVO> getApprovalDocumentAttachment() {
		return approvalDocumentAttachment;
	}

	public void setApprovalDocumentAttachment(List<DocumentDetailsVO> approvalDocumentAttachment) {
		this.approvalDocumentAttachment = approvalDocumentAttachment;
	}

	public String getScrutunyEditMode() {
		return scrutunyEditMode;
	}

	public void setScrutunyEditMode(String scrutunyEditMode) {
		this.scrutunyEditMode = scrutunyEditMode;
	}

}
