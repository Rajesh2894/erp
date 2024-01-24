package com.abm.mainet.adh.ui.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.adh.dto.AdvertiserMasterDto;
import com.abm.mainet.adh.dto.AgencyRegistrationRequestDto;
import com.abm.mainet.adh.service.IAdvertiserMasterService;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.StringUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

/**
 * @author Sharvan kumar Mandal
 * @since 24-06-2021
 */

@Component
@Scope("session")
public class ADHCancellationApprovalModel extends AbstractFormModel {

	private static final long serialVersionUID = 8187986486520652402L;

	private static Logger log = Logger.getLogger(ADHCancellationApprovalModel.class);

	@Autowired
	private IAdvertiserMasterService avertiserMasterService;
	
	@Autowired
    private ICFCApplicationMasterService cfcApplicationService;

	private ServiceMaster serviceMaster = new ServiceMaster();
	
	private String payableFlag;
	
	private List<DocumentDetailsVO> approvalDocumentAttachment = new ArrayList<>();
	
	private AgencyRegistrationRequestDto agencyRequestDto = new AgencyRegistrationRequestDto();
	
	private ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();
	
	private List<CFCAttachment> documentList = new ArrayList<>();
	
	 // Added new model class for User Story 112154  

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

	public void sendSmsEmail(ADHCancellationApprovalModel model, String menuUrl, String msgType) {
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
	
	
	public void populateApplicationData(final long applicationId) {

        ApplicantDetailDTO applicantDetail = new ApplicantDetailDTO();
        applicantDetail.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        populateApplicantionDetails(applicantDetail, applicationId);
        /*AgencyRegistrationResponseDto agencyRegDto = advertiserMasterService.getAgencyDetailByLicnoAndOrgId(agencyLicNo,
                UserSession.getCurrent().getOrganisation().getOrgid());*/
        List<AdvertiserMasterDto> agencyRegistrationByAppIdAndOrgId = ApplicationContextProvider.getApplicationContext()
                .getBean(IAdvertiserMasterService.class).getAgencyRegistrationByAppIdAndOrgId(applicationId,
                        UserSession.getCurrent().getOrganisation().getOrgid());
        /*ServiceMaster master = ApplicationContextProvider.getApplicationContext().getBean(TbServicesMstService.class)
                .findShortCodeByOrgId(MainetConstants.AdvertisingAndHoarding.AGENCY_CAN_SHORT_CODE, UserSession.getCurrent().getOrganisation().getOrgid());
        applicantDetail.setServiceId(master.getSmServiceId());*/

        //getAgencyRequestDto().setMasterDtolist(agencyRegistrationByAppIdAndOrgId);
        getAgencyRequestDto().setMasterDto(agencyRegistrationByAppIdAndOrgId.get(0));
        setDocumentList(ApplicationContextProvider.getApplicationContext().getBean(IChecklistVerificationService.class)
                .getDocumentUploaded(applicationId, UserSession.getCurrent().getOrganisation().getOrgid()));
        setApplicantDetailDto(applicantDetail);
        //this.setScrutunyEditMode(MainetConstants.FlagV);

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
  

	public ServiceMaster getServiceMaster() {
		return serviceMaster;
	}

	public void setServiceMaster(ServiceMaster serviceMaster) {
		this.serviceMaster = serviceMaster;
	}

	public String getPayableFlag() {
		return payableFlag;
	}

	public void setPayableFlag(String payableFlag) {
		this.payableFlag = payableFlag;
	}
	
	public List<DocumentDetailsVO> getApprovalDocumentAttachment() {
		return approvalDocumentAttachment;
	}

	public void setApprovalDocumentAttachment(List<DocumentDetailsVO> approvalDocumentAttachment) {
		this.approvalDocumentAttachment = approvalDocumentAttachment;
	}
	
	public AgencyRegistrationRequestDto getAgencyRequestDto() {
		return agencyRequestDto;
	}

	public void setAgencyRequestDto(AgencyRegistrationRequestDto agencyRequestDto) {
		this.agencyRequestDto = agencyRequestDto;
	}
	
	public ApplicantDetailDTO getApplicantDetailDto() {
        return applicantDetailDto;
    }

    public void setApplicantDetailDto(ApplicantDetailDTO applicantDetailDto) {
        this.applicantDetailDto = applicantDetailDto;
    }
    
    public List<CFCAttachment> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<CFCAttachment> documentList) {
        this.documentList = documentList;
    }


}
