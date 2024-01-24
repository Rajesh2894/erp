package com.abm.mainet.legal.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.legal.dto.CaseEntryDTO;
import com.abm.mainet.legal.dto.LegalOpinionDetailDTO;
import com.abm.mainet.legal.service.ILegalOpinionService;

@Component
@Scope("session")
public class LegalOpinionModel extends AbstractFormModel {

	private static final long serialVersionUID = -815361183624618895L;

	private List<CaseEntryDTO> caseEntryDTOList;

	private String saveMode;

	private CaseEntryDTO caseEntryDTO = new CaseEntryDTO();

	private List<AttachDocs> caseAttachDocsList = new ArrayList<>();

	private LegalOpinionDetailDTO legalOpinionDetailDTO = new LegalOpinionDetailDTO();

	private List<DocumentDetailsVO> uploadFileList = new ArrayList<>();

	private WorkflowTaskAction WorkflowTaskActionDTO = new WorkflowTaskAction();

	private List<LegalOpinionDetailDTO> legalOpinionDetailDTOList = new ArrayList<>();

	private String mode;
	private List<CFCAttachment> cfcAttachment = new ArrayList<>();
	
	private List<DocumentDetailsVO> documentDetailsList = new ArrayList<>();
	
	private List<CFCAttachment> cfcAttachmentList = new ArrayList<>();

	@Autowired
	ILegalOpinionService iLegalOpinionService;
	@Autowired
	ServiceMasterService serviceMasterService;
	@Resource
	private ApplicationService applicationService;
	@Resource
	private CommonService commonService;
	@Autowired
	private DepartmentService departmentService;
	@Resource
	IFileUploadService fileUpload;
	private String viewCommentAndDecision;

	@Override
	public boolean saveForm() {

		UserSession session = UserSession.getCurrent();

		if (mode.equalsIgnoreCase("I")) {

			legalOpinionDetailDTO.setCseId(caseEntryDTO.getCseId());
            //#143311
			if (caseEntryDTO.getLocId() != null)
			legalOpinionDetailDTO.setLocId(caseEntryDTO.getLocId());
			legalOpinionDetailDTO.setOpniondeptId(caseEntryDTO.getCseDeptid());

		}

		if (legalOpinionDetailDTO.getId() != null) {
			legalOpinionDetailDTO.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
			legalOpinionDetailDTO.setUpdateddate(new Date());
			legalOpinionDetailDTO.setUpdatedIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			setSuccessMessage(ApplicationSession.getInstance().getMessage("lgl.saveLegalOpinion"));
		} else {

			final ServiceMaster service = serviceMasterService.getServiceByShortName("LPN",
					UserSession.getCurrent().getOrganisation().getOrgid());

			//legalOpinionDetailDTO.setDeptId(departmentService.getDepartmentIdByDeptCode("LGL"));

			legalOpinionDetailDTO.setSmServiceId(service.getSmServiceId());
			legalOpinionDetailDTO.setServiceId(service.getSmServiceId());

			legalOpinionDetailDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			legalOpinionDetailDTO.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
			legalOpinionDetailDTO.setCreatedDate(new Date());
			legalOpinionDetailDTO.setIpMacAdrress(UserSession.getCurrent().getEmployee().getEmppiservername());
			final Date sysDate = UtilityService.getSQLDate(new Date());
			legalOpinionDetailDTO.setUserId(session.getEmployee().getEmpId());
			legalOpinionDetailDTO.setLangId((long) session.getLanguageId());
			legalOpinionDetailDTO.setOrgId(session.getOrganisation().getOrgid());
			legalOpinionDetailDTO.setUpdatedBy(session.getEmployee().getEmpId());
			legalOpinionDetailDTO.setApplicationDate(sysDate);
			legalOpinionDetailDTO.setfName("Legal Opinion");
			legalOpinionDetailDTO.setDeptId(departmentService.getDepartmentIdByDeptCode("LEGL"));
			List<DocumentDetailsVO> docList = getUploadFileList();
			if (docList != null) {
				docList = fileUpload.prepareFileUpload(docList);
			}
			legalOpinionDetailDTO.setDocList(docList);

			  legalOpinionDetailDTO =	iLegalOpinionService.saveLegalOpinionEntry(legalOpinionDetailDTO);
			setSuccessMessage(ApplicationSession.getInstance().getMessage("lgl.legalOpinion.sucMsg") + " " + legalOpinionDetailDTO.getApplicationId());
		}
		return true;
	}

	public List<DocumentDetailsVO> getUploadFileList() {
		return uploadFileList;
	}

	public void setUploadFileList(List<DocumentDetailsVO> uploadFileList) {
		this.uploadFileList = uploadFileList;
	}

	public LegalOpinionDetailDTO getLegalOpinionDetailDTO() {
		return legalOpinionDetailDTO;
	}

	public void setLegalOpinionDetailDTO(LegalOpinionDetailDTO legalOpinionDetailDTO) {
		this.legalOpinionDetailDTO = legalOpinionDetailDTO;
	}

	public List<CaseEntryDTO> getCaseEntryDTOList() {
		return caseEntryDTOList;
	}

	public void setCaseEntryDTOList(List<CaseEntryDTO> caseEntryDTOList) {
		this.caseEntryDTOList = caseEntryDTOList;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public CaseEntryDTO getCaseEntryDTO() {
		return caseEntryDTO;
	}

	public void setCaseEntryDTO(CaseEntryDTO caseEntryDTO) {
		this.caseEntryDTO = caseEntryDTO;
	}

	public List<AttachDocs> getCaseAttachDocsList() {
		return caseAttachDocsList;
	}

	public void setCaseAttachDocsList(List<AttachDocs> caseAttachDocsList) {
		this.caseAttachDocsList = caseAttachDocsList;
	}

	public WorkflowTaskAction getWorkflowTaskActionDTO() {
		return WorkflowTaskActionDTO;
	}

	public void setWorkflowTaskActionDTO(WorkflowTaskAction workflowTaskActionDTO) {
		WorkflowTaskActionDTO = workflowTaskActionDTO;
	}

	public List<LegalOpinionDetailDTO> getLegalOpinionDetailDTOList() {
		return legalOpinionDetailDTOList;
	}

	public void setLegalOpinionDetailDTOList(List<LegalOpinionDetailDTO> legalOpinionDetailDTOList) {
		this.legalOpinionDetailDTOList = legalOpinionDetailDTOList;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public List<CFCAttachment> getCfcAttachment() {
		return cfcAttachment;
	}

	public void setCfcAttachment(List<CFCAttachment> cfcAttachment) {
		this.cfcAttachment = cfcAttachment;
	}

	public List<DocumentDetailsVO> getDocumentDetailsList() {
		return documentDetailsList;
	}

	public void setDocumentDetailsList(List<DocumentDetailsVO> documentDetailsList) {
		this.documentDetailsList = documentDetailsList;
	}

	public List<CFCAttachment> getCfcAttachmentList() {
		return cfcAttachmentList;
	}

	public void setCfcAttachmentList(List<CFCAttachment> cfcAttachmentList) {
		this.cfcAttachmentList = cfcAttachmentList;
	}

	public String getViewCommentAndDecision() {
		return viewCommentAndDecision;
	}

	public void setViewCommentAndDecision(String viewCommentAndDecision) {
		this.viewCommentAndDecision = viewCommentAndDecision;
	}
	
}
