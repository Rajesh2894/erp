package com.abm.mainet.buildingplan.ui.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.buildingplan.dto.ProfessionalRegistrationDTO;
import com.abm.mainet.buildingplan.service.ProfessionalRegistrationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.UserTaskDTO;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.common.workflow.service.IWorkflowTaskService;

@Component
@Scope("session")
public class ProfessionalRegistrationFormModel extends AbstractFormModel {

	private static final long serialVersionUID = -8421991602979312945L;

	@Autowired
	private IFileUploadService fileUpload;

	@Autowired
	private IWorkflowTaskService iWorkflowTaskService;

	@Autowired
	private ProfessionalRegistrationService professionalRegService;

	private ProfessionalRegistrationDTO professionalRegDTO = new ProfessionalRegistrationDTO();

	private List<DocumentDetailsVO> checkList = new ArrayList<>();

	private List<CFCAttachment> documentList = new ArrayList<>();

	private String modeType;

	List<LookUp> officeCircle = new ArrayList<LookUp>();

	private ServiceMaster serviceMst = new ServiceMaster();

	private Long currentLevel;
	
	List<LookUp> district = new ArrayList<LookUp>();

	@Override
	public boolean saveForm() {
		try {
			validatedForm(getProfessionalRegDTO());
			List<DocumentDetailsVO> docs = getCheckList();
			docs = prepareFileUpload(docs);
			professionalRegDTO.setDocs(docs);
			if ((professionalRegDTO.getDocs() != null) && !professionalRegDTO.getDocs().isEmpty()) {
				for (final DocumentDetailsVO doc : professionalRegDTO.getDocs()) {
					if (doc.getCheckkMANDATORY().equals(MainetConstants.MENU.Y) && doc.getDocumentByteCode() == null) {
						addValidationError(ApplicationSession.getInstance().getMessage("professional.val.man.doc"));
					}
				}
			}

			if (hasValidationErrors()) {
				return false;
			}
			if (professionalRegDTO.getId() != null && professionalRegDTO.getId() > 0) {
				professionalRegDTO.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
				professionalRegDTO.setUpdatedDate(new Date());
				professionalRegDTO.setLgIpMacUp(UserSession.getCurrent().getEmployee().getEmppiservername());
			} else {
				professionalRegDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
				professionalRegDTO.setLangId(UserSession.getCurrent().getLanguageId());
				professionalRegDTO.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
				professionalRegDTO.setCreatedDate(new Date());
				professionalRegDTO.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());

			}

			professionalRegService.saveRegForm(getProfessionalRegDTO());
			setSuccessMessage(ApplicationSession.getInstance().getMessage("professional.success.msg")
					+ professionalRegDTO.getApplicationId());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	public List<DocumentDetailsVO> prepareFileUpload(final List<DocumentDetailsVO> docs) {
		final Map<Long, String> listOfString = new HashMap<>();
		final Map<Long, String> fileName = new HashMap<>();
		if ((FileUploadUtility.getCurrent().getFileMap() != null)
				&& !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				final List<File> list = new ArrayList<>(entry.getValue());
				for (final File file : list) {
					try {
						final Base64 base64 = new Base64();
						final String bytestring = base64.encodeToString(FileUtils.readFileToByteArray(file));
						fileName.put(entry.getKey(), file.getName());
						listOfString.put(entry.getKey(), bytestring);
					} catch (final IOException e) {
					}
				}
			}
		}
		if (docs != null && !docs.isEmpty() && !listOfString.isEmpty()) {
			for (final DocumentDetailsVO d : docs) {
				if (listOfString.containsKey(d.getDocumentId()) && fileName.containsKey(d.getDocumentId())) {
					d.setDocumentByteCode(listOfString.get(d.getDocumentId()));
					d.setDocumentName(fileName.get(d.getDocumentId()));
				}
			}
		}
		return docs;
	}

	private void validatedForm(ProfessionalRegistrationDTO professionalRegDTO) {
		if (professionalRegDTO.getUserType() == null) {
			addValidationError(ApplicationSession.getInstance().getMessage("professional.val.user.type"));
		}
		if (StringUtils.isBlank(professionalRegDTO.getFirstName())) {
			addValidationError(ApplicationSession.getInstance().getMessage("professional.val.first.name"));
		}
		if (StringUtils.isBlank(professionalRegDTO.getLastName())) {
			addValidationError(ApplicationSession.getInstance().getMessage("professional.val.last.name"));
		}
		if (StringUtils.isBlank(professionalRegDTO.getEmailId())) {
			addValidationError(ApplicationSession.getInstance().getMessage("professional.val.email.id"));
		}
		if (StringUtils.isBlank(professionalRegDTO.getMobileNo())) {
			addValidationError(ApplicationSession.getInstance().getMessage("professional.val.mobile.no"));
		}
		if (professionalRegDTO.getState() == null) {
			addValidationError(ApplicationSession.getInstance().getMessage("professional.val.state"));
		}
		if (professionalRegDTO.getDistrict() == null) {
			addValidationError(ApplicationSession.getInstance().getMessage("professional.val.district"));
		}
		if (StringUtils.isBlank(professionalRegDTO.getPincode())) {
			addValidationError(ApplicationSession.getInstance().getMessage("professional.val.pincode"));
		}
		if (StringUtils.isBlank(professionalRegDTO.getAddress())) {
			addValidationError(ApplicationSession.getInstance().getMessage("professional.val.address"));
		}
		if (professionalRegDTO.getOfficeCircle() == null) {
			addValidationError(ApplicationSession.getInstance().getMessage("professional.val.office.circle"));
		}

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
		taskAction.setApplicationId(professionalRegDTO.getApplicationId());
		taskAction.setDecision(MainetConstants.WorkFlow.Decision.APPROVED);
		taskAction.setTaskId(getWorkflowActionDto().getTaskId());
		taskAction.setComments(getWorkflowActionDto().getComments());
		taskAction.setCodIdOperLevel1(professionalRegDTO.getDistrict());
		WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();
		LookUp workProcessLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(getServiceMst().getSmProcessId(),
				UserSession.getCurrent().getOrganisation());
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

	public boolean ProfessioanlRegApprovalAction() {
		WorkflowTaskAction taskAction = getWorkflowActionDto();
		UserTaskDTO userTaskdto = iWorkflowTaskService.findByTaskId(getWorkflowActionDto().getTaskId());
		taskAction.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		taskAction.setEmpName(UserSession.getCurrent().getEmployee().getEmpname());
		taskAction.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		taskAction.setCreatedDate(new Date());
		taskAction.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
		taskAction.setDateOfAction(new Date());
		taskAction.setIsFinalApproval(MainetConstants.FAILED);
		taskAction.setIsObjectionAppealApplicable(MainetConstants.FAILED);
		taskAction.setDecision(getWorkflowActionDto().getDecision());
		if (StringUtils.equalsIgnoreCase(getWorkflowActionDto().getDecision(),
				MainetConstants.WorkFlow.Decision.SEND_BACK)) {
			taskAction.setSendBackToGroup(1);
			taskAction.setSendBackToLevel((int) (userTaskdto.getCurentCheckerLevel() - 1));
		}
		if (StringUtils.equalsIgnoreCase(getWorkflowActionDto().getDecision(),
				MainetConstants.WorkFlow.Decision.SEND_BACK_TO_APPLICANT)) {
			taskAction.setSendBackToGroup(0);
			taskAction.setSendBackToLevel(0);
			taskAction.setDecision(MainetConstants.WorkFlow.Decision.SEND_BACK);
		}
		taskAction.setComments(getWorkflowActionDto().getComments());

		taskAction.setApplicationId(professionalRegDTO.getApplicationId());
		taskAction.setTaskId(getWorkflowActionDto().getTaskId());
		taskAction.setCodIdOperLevel1(professionalRegDTO.getDistrict());

		return professionalRegService.executeApprovalWorkflowAction(taskAction, getServiceMst().getSmShortdesc(),
				getServiceMst().getSmServiceId(), getServiceMst().getSmShortdesc());

	}

	public ProfessionalRegistrationDTO getProfessionalRegDTO() {
		return professionalRegDTO;
	}

	public void setProfessionalRegDTO(ProfessionalRegistrationDTO professionalRegDTO) {
		this.professionalRegDTO = professionalRegDTO;
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

	public String getModeType() {
		return modeType;
	}

	public void setModeType(String modeType) {
		this.modeType = modeType;
	}

	public List<LookUp> getOfficeCircle() {
		return officeCircle;
	}

	public void setOfficeCircle(List<LookUp> officeCircle) {
		this.officeCircle = officeCircle;
	}

	public ServiceMaster getServiceMst() {
		return serviceMst;
	}

	public void setServiceMst(ServiceMaster serviceMst) {
		this.serviceMst = serviceMst;
	}

	public Long getCurrentLevel() {
		return currentLevel;
	}

	public void setCurrentLevel(Long currentLevel) {
		this.currentLevel = currentLevel;
	}

	public List<LookUp> getDistrict() {
		return district;
	}

	public void setDistrict(List<LookUp> district) {
		this.district = district;
	}
	
	

}
