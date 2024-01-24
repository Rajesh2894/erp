package com.abm.mainet.cfc.checklist.ui.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.cfc.checklist.domain.CheckListReportEntity;
import com.abm.mainet.cfc.checklist.domain.ChecklistStatusView;
import com.abm.mainet.cfc.checklist.domain.ViewCheckList;
import com.abm.mainet.cfc.checklist.service.ChecklistMstService;
import com.abm.mainet.cfc.checklist.service.ICheckListVerificationReportService;
import com.abm.mainet.cfc.checklist.service.IChecklistSearchService;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.BaseEntity;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadClass;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.service.ICFCApplicationAddressService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractEntryFormModel;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

/**
 * @author Vinay.Jangir
 *
 */

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class ChecklistVerificationSearchModel extends AbstractEntryFormModel<BaseEntity> {

	private static final long serialVersionUID = -6026306523201402609L;

	private static final Logger LOGGER = Logger.getLogger(ChecklistVerificationSearchModel.class);
			
	@Autowired
	private IChecklistVerificationService iChecklistVerificationService;

	@Autowired
	private ICheckListVerificationReportService iCheckListVerificationReportService;

	@Autowired
	private IChecklistSearchService checklistSearchService;

	@Resource
	private IFileUploadService fileUploadService;

	@Resource
	private IWorkflowExecutionService workflowExecutionService;

	@Autowired
	private ServiceMasterService serviceMasterService;

	@Autowired
	private ICFCApplicationMasterService applicationMstService;

	@Autowired
	private ICFCApplicationAddressService iCFCApplicationAddressService;

	@Autowired
	ISMSAndEmailService ismsAndEmailService;

	@Autowired
	private ChecklistMstService checklistMstService;

	private List<CheckListReportEntity> checkListReportEntitylist = Collections.emptyList();

	private List<CFCAttachment> attachmentList = new ArrayList<>(0);

	private List<CFCAttachment> finalattachmentList = new ArrayList<>(0);

	private String statusVariable;

	private String path;

	private int fileSize;

	private String[] listOfChkboxStatus;

	private List<CFCAttachment> rejectionArray = new ArrayList<>(0);

	private String serviceName;

	private boolean newApplication;

	private long currentServiceId;

	private ChecklistStatusView applicationDetails;

	private String redirectFlag;

	private String mobNo;

	private String email;
	private String appAddress;
	private String servShortCode;

	private CFCAttachment attechEntity = new CFCAttachment();

	public List<LookUp> getDocumentsList() {
		final List<LookUp> documentDetailsList = new ArrayList<>(0);
		setPath(attachmentList.get(0).getAttPath());
		LookUp lookUp = null;
		for (final CFCAttachment temp : attachmentList) {
			lookUp = new LookUp(temp.getAttId(), temp.getAttPath());
			lookUp.setDescLangFirst(temp.getClmDescEngl());
			lookUp.setDescLangSecond(temp.getClmDesc());
			lookUp.setLookUpId(temp.getClmId());
			lookUp.setDefaultVal(temp.getAttPath());
			lookUp.setLookUpCode(temp.getAttFname());
			lookUp.setLookUpType(temp.getDmsDocId());
			lookUp.setLookUpParentId(temp.getAttId());
			lookUp.setLookUpExtraLongOne(temp.getClmSrNo());
			lookUp.setOtherField(temp.getMandatory());
			lookUp.setExtraStringField1(temp.getClmRemark());// reject message
			lookUp.setDocDescription(temp.getDocDescription());
			documentDetailsList.add(lookUp);
		}
		fileSize = 0;
		if (!documentDetailsList.isEmpty()) {
			fileSize = documentDetailsList.size();
		}
		return documentDetailsList;
	}

	private boolean isChecked(final int i) {
		for (final String s : listOfChkboxStatus) {
			if (!(s == null) && s.equals(i + "")) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean saveForm() {
		LOGGER.info("Begin--> " + this.getClass().getSimpleName() + " saveForm() method");
		final Employee empId = UserSession.getCurrent().getEmployee();
		// Defect #34046-for verification of mandatory documents only
		List<String> mandatoryList = new ArrayList<>();
		StringBuilder remark=new StringBuilder();
		for (int i = 0; i < attachmentList.size(); i++) {
			if (!isChecked(i) && StringUtils.equals(getAttachmentList().get(i).getMandatory(), MainetConstants.FlagY)) {
				if ((getAttachmentList().get(i).getClmRemark() == null)
						|| getAttachmentList().get(i).getClmRemark().isEmpty()) {
					addValidationError(getAppSession().getMessage("cfc.rejection.remark.valid"));
				}
			} // Defect #34046-for verification of mandatory documents only
			if (StringUtils.equals(getAttachmentList().get(i).getMandatory(), MainetConstants.FlagY)) {
				mandatoryList.add(getAttachmentList().get(i).getMandatory());
			}
		}

		if ((getStatusVariable() == null) || getStatusVariable().isEmpty()) {
			addValidationError(getAppSession().getMessage("checklistverification.selectbox"));
		}

		final ArrayList<Long> arrayToCompare = new ArrayList<>(0);
		for (final String value : getListOfChkboxStatus()) {
			if (value != null) {
				arrayToCompare.add(new Long(value));
			}

		}

		if (!(hasValidationErrors())) {
			if ((getStatusVariable().equals(MainetConstants.FlagA))
					&& !(mandatoryList.size() == arrayToCompare.size())) {
				addValidationError(getAppSession().getMessage("checklistverification.errormsg"));
			}
			if (getStatusVariable().equals(MainetConstants.FlagH)
					&& (getAttachmentList().size() == arrayToCompare.size())) {
				addValidationError(getAppSession().getMessage("checklistverification.holdmsg"));
			}

			if (!hasValidationErrors()) {
				LOGGER.info("Start-->  updateCFCApplicationStatus() method");
				updateCFCApplicationStatus();
				LOGGER.info("End-->  updateCFCApplicationStatus() method");

				final Organisation orgid = UserSession.getCurrent().getOrganisation();

				/*
				 * Rejection Part - Updating tb_cfc_rejection for Reject and Hold scenarios
				 */
				rejectionArray.clear();
				List<Long> attachmentId = new ArrayList<Long>();
				if (getStatusVariable().equals(MainetConstants.FlagR)
						|| getStatusVariable().equals(MainetConstants.FlagH)) {

					for (int i = 0; i < attachmentList.size(); i++) {
						if (!isChecked(i)) {
							rejectionArray.add(attachmentList.get(i));
							remark.append(attachmentList.get(i).getClmRemark()).append(",");
							attachmentId.add(attachmentList.get(i).getAttId());
						}
					}
				}

				/*
				 * Updating tb_attach_cfc for all scenarios - Approve, Reject, Hold
				 */
				if (arrayToCompare.size() != 0) {
					CFCAttachment attachment = null;

					if (FileUploadUtility.getCurrent().getFileMap().entrySet().size() != 0) {
						for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap()
								.entrySet()) {
							final Iterator<File> setFilesItr = entry.getValue().iterator();
							while (setFilesItr.hasNext()) {
								setFilesItr.next();
								for (final FileUploadClass fileUploadClass : FileUploadUtility.getCurrent()
										.getFileUploadSet()) {
									if (entry.getKey().toString()
											.equals(fileUploadClass.getCurrentCount().toString())) {
										if (isChecked(entry.getKey().toString(), listOfChkboxStatus)) {
											for (final ListIterator<CFCAttachment> j = getAttachmentList()
													.listIterator(); j.hasNext();) {
												attachment = j.next();

												if (fileUploadClass.getCheckListId().equals(attachment.getClmId())) {
													attachment.setClmAprStatus(MainetConstants.Common_Constant.NO);// document
																													// status
												}

												j.set(attachment);
											}
										}
									}
								}
							}
						}
					} else {
						int k = 0;
						for (final ListIterator<CFCAttachment> i = getAttachmentList().listIterator(); i
								.hasNext(); k++) {
							attachment = i.next();

							if (isChecked(k)) {
								attachment.setClmAprStatus(MainetConstants.Common_Constant.YES);// document status
							}

							i.set(attachment);
						}
					}

				}
				LOGGER.info("Start-->  updateCFCAttachment() method");
				updateCFCAttachment(getAttachmentList(), getStatusVariable());
				LOGGER.info("End-->  updateCFCAttachment() method");

				LOGGER.info("Start-->  updateCFCRejection() method");
				iChecklistVerificationService.updateCFCRejection(rejectionArray, getStatusVariable());
				LOGGER.info("End-->  updateCFCRejection() method");

				if ((getStatusVariable() != null) && !getStatusVariable().equals(MainetConstants.FlagA)) {
					setCheckListReportEntitylist(iCheckListVerificationReportService
							.getRejLetterList(applicationDetails.getApmApplicationId(), getStatusVariable(), orgid));
				}
				LOGGER.info("Start-->  uploadForChecklistVerification() method");
				checklistMstService.uploadForChecklistVerification(path, applicationDetails.getCdmDeptId(),
						currentServiceId, getFileNetClient(), applicationDetails.getApmApplicationId(),
						listOfChkboxStatus);
				LOGGER.info("End-->  uploadForChecklistVerification() method");

				String processName = serviceMasterService.getProcessName(currentServiceId, orgid.getOrgid());
				if (processName != null) {

					String decision = null;

					WorkflowProcessParameter workflowdto = new WorkflowProcessParameter();
					WorkflowTaskAction workflowAction = new WorkflowTaskAction();
					workflowAction.setTaskId(this.getTaskId());
					workflowAction.setApplicationId(applicationDetails.getApmApplicationId());
					workflowAction.setDateOfAction(new Date());
					if (getStatusVariable().equals(MainetConstants.FlagA)) {
						decision = MainetConstants.WorkFlow.Decision.APPROVED;
					} else if (getStatusVariable().equals(MainetConstants.FlagR)) {
						decision = MainetConstants.WorkFlow.Decision.REJECTED;
					} else if (getStatusVariable().equals(MainetConstants.FlagH)) {
						decision = MainetConstants.WorkFlow.Decision.HOLD;
					}
					workflowAction.setDecision(decision);
					workflowAction.setOrgId(orgid.getOrgid());
					workflowAction.setEmpId(empId.getEmpId());
					workflowAction.setModifiedBy(empId.getEmpId());
					workflowAction.setEmpType(empId.getEmplType());
					workflowAction.setEmpName(empId.getEmpname());
					workflowAction.setCreatedBy(empId.getEmpId());
					workflowAction.setCreatedDate(new Date());
					workflowdto.setProcessName(processName);
					workflowAction.setAttachementId(attachmentId);
					if(!remark.toString().isEmpty()) {
						workflowAction.setComments((remark.deleteCharAt(remark.length()-1)).toString());
					}
					try {
						workflowdto.setWorkflowTaskAction(workflowAction);
						LOGGER.info("Start-->  updateWorkflow() method");
						workflowExecutionService.updateWorkflow(workflowdto);
						LOGGER.info("End-->  updateWorkflow() method");
					} catch (final Exception e) {
						throw new FrameworkException(
								"Exception in checklist verification for jbpm workflow : " + e.getMessage(), e);
					}
				}

				if (getStatusVariable().equalsIgnoreCase(MainetConstants.FlagA)) {
					/*
					 * iChecklistVerificationService.perFromModuleWiseOperationAndSMSEMAIL(
					 * applicationDetails.getSmServiceId(), getStatusVariable(),
					 * applicationDetails.getApmApplicationId(), null);
					 */

				}
				//Defect Id 132562 and 132551
				if (getStatusVariable().equals(MainetConstants.FlagR) || getStatusVariable().equals(MainetConstants.FlagH)) {
					LOGGER.info("Start-->  updateApplicationMastrRejection() method");
				 long rejno = iCheckListVerificationReportService
		                .updateApplicationMastrRejection(applicationDetails.getApmApplicationId(), orgid);
				 LOGGER.info("End-->  updateApplicationMastrRejection() method");
				}
				LOGGER.info("Start-->  sendSmsAndEmail() method");
				//LOGGER.info("Value of applicationDetails-->  "+applicationDetails);
				LOGGER.info("Value of orgid.getOrgid()--> "+orgid.getOrgid());
				LOGGER.info("Value of getStatusVariable()--> "+getStatusVariable());
				LOGGER.info("Value of empId.getEmpId()--> "+empId.getEmpId());
				sendSmsAndEmail(applicationDetails, orgid.getOrgid(), getStatusVariable(), empId.getEmpId());
				LOGGER.info("End-->  sendSmsAndEmail() method");
				LOGGER.info("Start-->  setSuccessMessage() method");
				if(getStatusVariable().equals(MainetConstants.FlagR)) {
					setSuccessMessage(getAppSession().getMessage("checklistVerification.rejectSuccessMsg"));
				}else {
					setSuccessMessage(getAppSession().getMessage("checklistVerification.saveSuccessMsg"));
				}
				LOGGER.info("End-->  setSuccessMessage() method-->"+getAppSession().getMessage("checklistVerification.saveSuccessMsg"));
				LOGGER.info("End-->  getSuccessMessage() method-->"+getSuccessMessage());
				LOGGER.info("End--> " + this.getClass().getSimpleName() + " saveForm() method");
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * @param apmApplicationId
	 * @param orgid
	 * @param statusVariable2
	 */
	@SuppressWarnings("unchecked")
	private void sendSmsAndEmail(ChecklistStatusView checklistDetails, Long orgid, String statusVariable2, Long empId) {

		LOGGER.info("Begin--> " + this.getClass().getSimpleName() + " sendSmsAndEmail() method");
		LOGGER.info("Start--> getCFCApplicationByApplicationId() method");
		TbCfcApplicationMstEntity applicant = applicationMstService
				.getCFCApplicationByApplicationId(checklistDetails.getApmApplicationId(), orgid);
		LOGGER.info("End--> getCFCApplicationByApplicationId() method-->"+applicant);
		LOGGER.info("Start--> getApplicationAddressByAppId() method");
		CFCApplicationAddressEntity appAddress = iCFCApplicationAddressService
				.getApplicationAddressByAppId(checklistDetails.getApmApplicationId(), orgid);
		LOGGER.info("End--> getApplicationAddressByAppId() method-->"+appAddress);
		if (applicant != null) {
			LOGGER.info("Applicant found-->"+applicant.getApmFname());
			final SMSAndEmailDTO dto = new SMSAndEmailDTO();
			String userName = applicant.getApmFname() == null ? MainetConstants.BLANK
					: applicant.getApmFname() + MainetConstants.WHITE_SPACE;
			userName += applicant.getApmMname() == null ? MainetConstants.BLANK
					: applicant.getApmMname() + MainetConstants.WHITE_SPACE;
			userName += applicant.getApmLname() == null ? MainetConstants.BLANK : applicant.getApmLname();

			dto.setAppName(userName);
			dto.setAppNo(checklistDetails.getApmApplicationId().toString());
			Set<File> fileDetails = new LinkedHashSet<>();
			List<File> filesForAttach = new ArrayList<File>();
			String filePath=null;
			LOGGER.info("Value of getStatusVariable-->"+getStatusVariable());
			if (getStatusVariable().equals(MainetConstants.FlagA)) {
				dto.setDecision(PrefixConstants.SMS_EMAIL_ALERT_TYPE.APPROVAL);
			} else if (getStatusVariable().equals(MainetConstants.FlagR)) {
				dto.setDecision(PrefixConstants.SMS_EMAIL_ALERT_TYPE.REJECTED_MSG);
				LOGGER.info("Reject report call started ------------------------------------->");
				String URL = ServiceEndpoints.BIRT_REPORT_URL+"="+ MainetConstants.SMS_EMAIL_URL.RejectionLetterReport+"&__format=pdf&OrgId="
						+ orgid + "&AppNo=" + checklistDetails.getApmApplicationId();
				LOGGER.info("Birth Report URL for Reject------------------------->"+URL);
				 filePath = Utility.getFilePathForPdfBirt(URL,checklistDetails.getApmApplicationId());
			} else if (getStatusVariable().equals(MainetConstants.FlagH)) {
				LOGGER.info("calling BIRT_REPORT_URL");
				dto.setDecision(PrefixConstants.SMS_EMAIL_ALERT_TYPE.HOLD);
				String URL = ServiceEndpoints.BIRT_REPORT_URL+"="+MainetConstants.SMS_EMAIL_URL.HoldOfDocumentLetterReport+"&__format=pdf&OrgId="
						+ orgid + "&AppNo=" + checklistDetails.getApmApplicationId();
				LOGGER.info("called BIRT_REPORT_URL-->"+URL +" Decision-->"+dto.getDecision());
				 filePath = Utility.getFilePathForPdfBirt(URL,checklistDetails.getApmApplicationId());
				 LOGGER.info("Value of filePath-->"+filePath);
			}
			if(filePath!=null) {
				final File file = new File(filePath);
				fileDetails.add(file);
				filesForAttach.addAll(fileDetails);
			    dto.setFilesForAttach(filesForAttach);
				}
			if (checklistDetails.getServiceName() != null)
			LOGGER.info("Value of checklistDetails.getServiceName()-->"+checklistDetails.getServiceName());
				dto.setServName(checklistDetails.getServiceName());
			dto.setEmail(appAddress.getApaEmail());
			dto.setMobnumber(appAddress.getApaMobilno());
			if (applicant.getTbOrganisation() != null)
				dto.setOrganizationName(applicant.getTbOrganisation().getONlsOrgname());
			//to sent smsAndEmail in both language English and regional
			int langId = UserSession.getCurrent().getLanguageId();
			// Added Changes As per told by Rajesh Sir For Sms and Email
			dto.setUserId(empId);
			LOGGER.info("Value of Email-->"+appAddress.getApaEmail()
			+", OrganizationName-->"+dto.getOrganizationName()+" and langId-->"+langId);
			LOGGER.info("Before calling--> deptCode-->"+MainetConstants.DEPT_SHORT_NAME.CFC_CENTER+", menuURL-->"
					+MainetConstants.SMS_EMAIL_URL.CHECKLIST_VERIF+", Decision-->"+dto.getDecision());
			ismsAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.CFC_CENTER,
					MainetConstants.SMS_EMAIL_URL.CHECKLIST_VERIF, dto.getDecision(), dto,
					applicant.getTbOrganisation(), langId);
			LOGGER.info("End--> " + this.getClass().getSimpleName() + " sendSmsAndEmail() method");
		}

	}

	private boolean isChecked(final String index, final String[] listOfChkboxStatus1) {
		for (final String string : listOfChkboxStatus1) {
			if (!(string == null) && string.equals(index + "")) {
				return true;
			}

		}

		return false;
	}

	/**
	 * @return the attachmentList
	 */
	public List<CFCAttachment> getAttachmentList() {
		return attachmentList;
	}

	/**
	 * @param attachmentList the attachmentList to set
	 */
	public void setAttachmentList(final List<CFCAttachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

	/**
	 * @return the fileSize
	 */
	public int getFileSize() {
		return fileSize;
	}

	/**
	 * @param fileSize the fileSize to set
	 */
	public void setFileSize(final int fileSize) {
		this.fileSize = fileSize;
	}

	private void updateCFCApplicationStatus() {
		applicationMstService.updateCFCApplicationStatus(getStatusVariable(), applicationDetails.getApmApplicationId());
	}

	/**
	 * @return the statusVariable
	 */
	public String getStatusVariable() {
		return statusVariable;
	}

	/**
	 * @param statusVariable the statusVariable to set
	 */
	public void setStatusVariable(final String statusVariable) {
		this.statusVariable = statusVariable;
	}

	public void updateCFCAttachment(final List<CFCAttachment> list, final String status) {
		iChecklistVerificationService.updateCFCAttachment(list, status);
	}

	/**
	 * @return the listOfChkboxStatus
	 */
	public String[] getListOfChkboxStatus() {
		return listOfChkboxStatus;
	}

	/**
	 * @param listOfChkboxStatus the listOfChkboxStatus to set
	 */
	public void setListOfChkboxStatus(final String[] listOfChkboxStatus) {
		this.listOfChkboxStatus = listOfChkboxStatus;
	}

	/**
	 * @return the rejectionArray
	 */
	public List<CFCAttachment> getRejectionArray() {
		return rejectionArray;
	}

	/**
	 * @param rejectionArray the rejectionArray to set
	 */
	public void setRejectionArray(final List<CFCAttachment> rejectionArray) {
		this.rejectionArray = rejectionArray;
	}

	/**
	 * @return the serviceName
	 */
	@Override
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * @param serviceName the serviceName to set
	 */
	@Override
	public void setServiceName(final String serviceName) {

		this.serviceName = serviceName;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(final String path) {
		this.path = path;
	}

	/**
	 * @return the currentServiceId
	 */
	public long getCurrentServiceId() {
		return currentServiceId;
	}

	/**
	 * @param currentServiceId the currentServiceId to set
	 */
	public void setCurrentServiceId(final long currentServiceId) {
		this.currentServiceId = currentServiceId;
	}

	public List<CFCAttachment> getFinalattachmentList() {
		return finalattachmentList;
	}

	public void setFinalattachmentList(final List<CFCAttachment> finalattachmentList) {
		this.finalattachmentList = finalattachmentList;
	}

	private CheckListReportEntity checkListReportEntity = new CheckListReportEntity();
	private List<ViewCheckList> viewCheckList = Collections.emptyList();

	public List<CheckListReportEntity> getCheckListReportEntitylist() {
		return checkListReportEntitylist;
	}

	public void setCheckListReportEntitylist(final List<CheckListReportEntity> checkListReportEntitylist) {
		this.checkListReportEntitylist = checkListReportEntitylist;
	}

	public CheckListReportEntity getCheckListReportEntity() {
		return checkListReportEntity;
	}

	public void setCheckListReportEntity(final CheckListReportEntity checkListReportEntity) {
		this.checkListReportEntity = checkListReportEntity;
	}

	public List<ViewCheckList> getViewCheckList() {
		return viewCheckList;
	}

	public void setViewCheckList(final List<ViewCheckList> viewCheckList) {
		this.viewCheckList = viewCheckList;
	}

	@Override
	public void editForm(final long rowId) {
		super.editForm(rowId);
		fileUploadService.sessionCleanUpForFileUpload();
		final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		applicationDetails = checklistSearchService.getCheckListDataByApplication(orgId, rowId);
		if(null == applicationDetails.getRef_no()){
			applicationDetails.setRef_no("");
		}
		setCurrentServiceId(applicationDetails.getSmServiceId());
		ServiceMaster serviceMaster = serviceMasterService.getServiceMaster(getCurrentServiceId(), UserSession.getCurrent().getOrganisation().getOrgid());
		if(serviceMaster!=null) {
		setServShortCode(serviceMaster.getSmShortdesc());
		}
		if (MainetConstants.ApplicationStatus.RESUBMIT.equals(applicationDetails.getApmChklstVrfyFlag())) {
			setAttachmentList(iChecklistVerificationService.getAttachDocumentByDocumentStatus(rowId,
					MainetConstants.BLANK, orgId));
		} else {
			setAttachmentList(iChecklistVerificationService.findAttachmentsForAppId(rowId, null,
					UserSession.getCurrent().getOrganisation().getOrgid()));
		}

		if (!getAttachmentList().isEmpty()) {
			newApplication = ((null == applicationDetails.getApmChklstVrfyFlag())
					|| MainetConstants.ApplicationStatus.RESUBMIT.equals(applicationDetails.getApmChklstVrfyFlag())
					|| MainetConstants.ApplicationStatus.PENDING.equals(applicationDetails.getApmChklstVrfyFlag())
							? true
							: false);

		}
		
		listOfChkboxStatus = new String[getAttachmentList().size()];
		// code added for set email and mobile no for Defect#121682
		final CFCApplicationAddressEntity address = iCFCApplicationAddressService.getApplicationAddressByAppId(rowId,
				orgId);
		if (address != null) {
			this.setMobNo(address.getApaMobilno());
			this.setEmail(address.getApaEmail());
			this.setAppAddress(address.getApaAreanm());
		}

	}

	public boolean isNewApplication() {
		return newApplication;
	}

	public void setNewApplication(final boolean newApplication) {
		this.newApplication = newApplication;
	}

	public ChecklistStatusView getApplicationDetails() {
		return applicationDetails;
	}

	public void setApplicationDetails(final ChecklistStatusView applicationDetails) {
		this.applicationDetails = applicationDetails;
	}

	/**
	 * @return the attechEntity
	 */
	public CFCAttachment getAttechEntity() {
		return attechEntity;
	}

	/**
	 * @param attechEntity the attechEntity to set
	 */
	public void setAttechEntity(final CFCAttachment attechEntity) {
		this.attechEntity = attechEntity;
	}

	public String getRedirectFlag() {
		return redirectFlag;
	}

	public void setRedirectFlag(final String redirectFlag) {
		this.redirectFlag = redirectFlag;
	}

	public String getMobNo() {
		return mobNo;
	}

	public void setMobNo(String mobNo) {
		this.mobNo = mobNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAppAddress() {
		return appAddress;
	}

	public void setAppAddress(String appAddress) {
		this.appAddress = appAddress;
	}

	public String getServShortCode() {
		return servShortCode;
	}

	public void setServShortCode(String servShortCode) {
		this.servShortCode = servShortCode;
	}

	
}