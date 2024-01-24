package com.abm.mainet.common.integration.dms.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.CommonSequenceConfigDto;
import com.abm.mainet.common.dto.SequenceConfigMasterDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.domain.DmsDocsMetadata;
import com.abm.mainet.common.integration.dms.dto.DmsDocsMetadataDetDto;
import com.abm.mainet.common.integration.dms.dto.DmsDocsMetadataDto;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.service.IDmsMetadataService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dms.ui.validator.DmsMetadataValidator;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractEntryFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class DmsMetadataModel extends AbstractEntryFormModel<DmsDocsMetadata> {

	private static final Logger LOGGER = Logger.getLogger(DmsMetadataModel.class);
	private static final long serialVersionUID = 1L;

	private List<LookUp> departmentList = new ArrayList<>();
	private List<LookUp> metadataList = new ArrayList<>();
	private List<DocumentDetailsVO> attachments = new ArrayList<>();
	private List<AttachDocs> attachDocsList = new ArrayList<>();
	private List<LookUp> lookUpList = new ArrayList<>();
	private List<LookUp> docTypeList = new ArrayList<>();
	private LookUp documentType = new LookUp();
	private Long deptId;
	private String attachDocsPath;
	private List<String> attachNameList = new ArrayList<>(0);
	private DmsDocsMetadata entity = new DmsDocsMetadata();
	private List<Long> fileCountUpload;
	List<EmployeeBean> employeeList = new ArrayList<>();
	Map<Long, String> roleList = new HashMap<Long, String>();
	List<TbDepartment> assignedDeptList = new ArrayList<>();
	private String kms;
	private String employeeIds;
	private String roleIds;
	private String assignDeptIds;
	private String knowledgeSharing;
	private Long ward1;
	private Long ward2;
	private Long ward3;
	private String docRefNo;
	private String deptCode;
	private String seqNo;
	private Long dmsId;
	@Autowired
	private IFileUploadService fileUploadService;

	@Autowired
	private IDmsMetadataService dmsService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private SeqGenFunctionUtility seqGenFunctionUtility;

	@Autowired
	private ServiceMasterService serviceMaster;

	public boolean saveForm() {
		Organisation org=UserSession.getCurrent().getOrganisation();
		boolean status = false;
		
		StringBuilder docType = new StringBuilder();
		for (int i = 0; i < getAttachments().size(); i++) {
			if (getAttachments().get(i).getDoc_DESC_ENGL() != null) {
				docType.append(getAttachments().get(i).getDoc_DESC_ENGL() + MainetConstants.operator.COMMA);
			}
		}
		docType = docType.deleteCharAt((docType.length() - 1));
		setAttachments(fileUploadService.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
		validateBean(this, DmsMetadataValidator.class);

		if (hasValidationErrors()) {
			return status;
		}
					
		String[] arr = docType.toString().split(MainetConstants.operator.COMMA);
		//#106603 By Arun
		for(int i = 0; i < getAttachments().size(); i++) {
			LookUp lookup=CommonMasterUtility.getNonHierarchicalLookUpObject(Long.valueOf(arr[i]), org);
			getAttachments().get(i).setDescriptionType(lookup.getLookUpDesc());	// setting description of document type		
		}
		
		DmsDocsMetadataDto dmsDocsDto = new DmsDocsMetadataDto();
		List<DmsDocsMetadataDetDto> dmsDocsMetadataDetList = new ArrayList<>();
		FileUploadDTO uploadDTO = new FileUploadDTO();
		dmsDocsDto.setDeptId(String.valueOf(getDeptId()));
		dmsDocsDto.setDocType(docType.toString());
		dmsDocsDto.setIsActive(MainetConstants.FlagA);
		dmsDocsDto.setDmsId(getDmsId());
		dmsDocsDto.setStatus(MainetConstants.AssetManagement.STATUS.SUBMITTED);

		Map<String, String> dmsServiceMap = new HashMap<String, String>();
		getLookUpList().forEach(data -> {
			//Add only those records which having data in metadata column.
			if(StringUtils.isNotBlank(data.getOtherField())) {
			DmsDocsMetadataDetDto dmsDto = new DmsDocsMetadataDetDto();
			dmsDto.setMtKey(String.valueOf(data.getLookUpId()));
			dmsDto.setMtVal(data.getOtherField());	
			//#106603  By Arun
			dmsServiceMap.put(CommonMasterUtility.getHierarchicalLookUp(data.getLookUpId(), org.getOrgid()).getLookUpCode(),data.getOtherField());
			dmsDocsMetadataDetList.add(dmsDto);
			}
		});
		dmsDocsDto.setDmsServiceMap(dmsServiceMap);
				
		if (getKnowledgeSharing() != null) {
			if (getKnowledgeSharing().equals(MainetConstants.CommonConstants.E)) {
				if (getEmployeeIds() != null) {
					dmsDocsDto.setAssignedTo(getEmployeeIds());
				} else {
					dmsDocsDto.setAssignedTo(String.valueOf(MainetConstants.NUMBERS.ZERO));
				}
			} else if (getKnowledgeSharing().equals(MainetConstants.Common_Constant.ROLE)) {
				if (getRoleIds() != null) {
					dmsDocsDto.setUserRoleId(getRoleIds());
				} else {
					dmsDocsDto.setUserRoleId(String.valueOf(MainetConstants.NUMBERS.ZERO));
				}
			} else if (getKnowledgeSharing().equals(MainetConstants.FlagD)) {
				if (getAssignDeptIds() != null) {
					dmsDocsDto.setAssignedDept(getAssignDeptIds());
				} else {
					dmsDocsDto.setAssignedDept(String.valueOf(MainetConstants.NUMBERS.ZERO));
				}
			}
		}
		dmsDocsDto.setZone(getWard1());
		dmsDocsDto.setWard(getWard2());
		dmsDocsDto.setMohalla(getWard3());
		dmsDocsDto.setDocRefNo(getDocRefNo());
		dmsDocsDto.setDmsDocsMetadataDetList(dmsDocsMetadataDetList);
		Long count = dmsService.getMaxCount(UserSession.getCurrent().getOrganisation().getOrgid());
		if (getKms() != null && getKms().equals(MainetConstants.Dms.KMS)) {
			dmsDocsDto.setStorageType(MainetConstants.Dms.KMS);
			uploadDTO.setIdfId(count.toString());
		} else {
			dmsDocsDto.setStorageType(MainetConstants.Dms.DMS);
			uploadDTO.setIdfId(dmsDocsDto.getStorageType() + MainetConstants.WINDOWS_SLASH + count);
		}

		uploadDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		uploadDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		uploadDTO.setStatus(MainetConstants.FlagA);
		uploadDTO.setDmsDocsDto(dmsDocsDto);
		this.getDepartmentList().forEach(data -> {
			if (data.getLookUpId() == getDeptId()) {
				uploadDTO.setDepartmentName(data.getLookUpCode());
				uploadDTO.setDepartmentFullName(data.getLookUpDesc());				
			}
		});

		LookUp lookUp = null;
		try {
			ServiceMaster servMaster = serviceMaster.getServiceByShortName(MainetConstants.Dms.MTD,
					UserSession.getCurrent().getOrganisation().getOrgid());
			lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(servMaster.getSmProcessId(),
					UserSession.getCurrent().getOrganisation());
			status = true;
		} catch (Exception e1) {
			addValidationError(getAppSession().getMessage("dmsDto.valid.serviceMaster"));
		}
		String flag = MainetConstants.FlagN;
		if (lookUp != null && !lookUp.getLookUpCode().equals(MainetConstants.CommonConstants.NA)) {
			flag = MainetConstants.FlagY;
			SequenceConfigMasterDTO configMasterDTO = null; // #96133
			try {
				// Sequence no
				status = false;
				Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.CommonConstants.COM,
						PrefixConstants.STATUS_ACTIVE_PREFIX);
				configMasterDTO = seqGenFunctionUtility.loadSequenceData(
						UserSession.getCurrent().getOrganisation().getOrgid(), deptId, MainetConstants.DMS_TABLE,
						MainetConstants.SEQ_NO);
				CommonSequenceConfigDto commonSequenceConfigDto = new CommonSequenceConfigDto();
				String seqNo = seqGenFunctionUtility.generateNewSequenceNo(configMasterDTO, commonSequenceConfigDto);
				dmsDocsDto.setSeqNo(seqNo);
				dmsDocsDto.setWfStatus(MainetConstants.LQP.STATUS.OPEN);
				this.setSeqNo(seqNo);
				status = true;
			} catch (Exception e) {
				addValidationError(getAppSession().getMessage("dms.seqNotConfigured"));
				LOGGER.error("Sequence No. is not configured in Sequence Master ");
			}
			if (status) {
				try {
					// workflow start
					status = false;
					getWorkflowActionDto().setReferenceId(seqNo);
					RequestDTO requestDTO = new RequestDTO();
					requestDTO.setReferenceId(getWorkflowActionDto().getReferenceId());
					requestDTO.setApplicationId(getWorkflowActionDto().getApplicationId());
					ServiceMaster service = ApplicationContextProvider.getApplicationContext()
							.getBean(ServiceMasterService.class).getServiceMasterByShortCode(MainetConstants.Dms.MTD,
									UserSession.getCurrent().getOrganisation().getOrgid());
					WorkflowMas workFlowMas = ApplicationContextProvider.getApplicationContext()
							.getBean(IWorkflowTyepResolverService.class)
							.resolveServiceWorkflowType(UserSession.getCurrent().getOrganisation().getOrgid(),
									service.getTbDepartment().getDpDeptid(), service.getSmServiceId(), null, null, null,
									null, null);
					dmsService.initiateWorkFlowWorksService(this.prepareWorkFlowTaskAction(), workFlowMas,
							"DmsMetadata.html", MainetConstants.FlagA);
					status = true;
				} catch (Exception e) {
					addValidationError(getAppSession().getMessage("dms.workflowNotInitiated"));
				}
			}
		}
		if (status) {
			if (flag.equals(MainetConstants.FlagN)) {
				dmsDocsDto.setWfStatus(MainetConstants.WorkFlow.Decision.APPROVED);
				dmsDocsDto.setRemark(MainetConstants.MAKER_CHECKER_NA);
			}
			try {
				fileUploadService.doMasterFileUpload(getAttachments(), uploadDTO); // save data
				if (this.getSeqNo() != null) {
					this.setSuccessMessage(ApplicationSession.getInstance().getMessage("dms.saveRecord" + "",
							new Object[] { this.getSeqNo() }));
				} else {
					this.setSuccessMessage(ApplicationSession.getInstance().getMessage("dms.saveRecordSuccess"));
				}
				status = true;
			} catch (FrameworkException fe) {//#106603  By Arun
				addValidationError(getAppSession().getMessage(fe.getMessage().split(";")[0]));
				return false;
			} catch (Exception e) {
				addValidationError(getAppSession().getMessage("dms.unableToUpdate"));
				return false;
			}
		}
		return status;
	}

	public WorkflowTaskAction prepareWorkFlowTaskAction() {
		WorkflowTaskAction taskAction = new WorkflowTaskAction();
		taskAction.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		taskAction.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
		taskAction.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		taskAction.setDateOfAction(new Date());
		taskAction.setCreatedDate(new Date());
		taskAction.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		taskAction.setEmpName(UserSession.getCurrent().getEmployee().getEmplname());
		taskAction.setEmpEmail(UserSession.getCurrent().getEmployee().getEmpemail());
		taskAction.setReferenceId(this.getSeqNo());
		taskAction.setPaymentMode(MainetConstants.FlagF);
		taskAction.setDecision("SUBMITTED");
		return taskAction;
	}

	public List<DocumentDetailsVO> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<DocumentDetailsVO> attachments) {
		this.attachments = attachments;
	}

	public List<AttachDocs> getAttachDocsList() {
		return attachDocsList;
	}

	public void setAttachDocsList(List<AttachDocs> attachDocsList) {
		this.attachDocsList = attachDocsList;
	}

	public List<LookUp> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<LookUp> departmentList) {
		this.departmentList = departmentList;
	}

	public LookUp getDocumentType() {
		return documentType;
	}

	public void setDocumentType(LookUp documentType) {
		this.documentType = documentType;
	}

	public List<LookUp> getMetadataList() {
		return metadataList;
	}

	public void setMetadataList(List<LookUp> metadataList) {
		this.metadataList = metadataList;
	}

	public List<LookUp> getLookUpList() {
		return lookUpList;
	}

	public void setLookUpList(List<LookUp> lookUpList) {
		this.lookUpList = lookUpList;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getAttachDocsPath() {
		return attachDocsPath;
	}

	public void setAttachDocsPath(String attachDocsPath) {
		this.attachDocsPath = attachDocsPath;
	}

	public List<String> getAttachNameList() {
		return attachNameList;
	}

	public void setAttachNameList(List<String> attachNameList) {
		this.attachNameList = attachNameList;
	}

	public DmsDocsMetadata getEntity() {
		return entity;
	}

	public void setEntity(DmsDocsMetadata entity) {
		this.entity = entity;
	}

	public List<Long> getFileCountUpload() {
		return fileCountUpload;
	}

	public void setFileCountUpload(List<Long> fileCountUpload) {
		this.fileCountUpload = fileCountUpload;
	}

	public String getKnowledgeSharing() {
		return knowledgeSharing;
	}

	public void setKnowledgeSharing(String knowledgeSharing) {
		this.knowledgeSharing = knowledgeSharing;
	}

	public List<EmployeeBean> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<EmployeeBean> employeeList) {
		this.employeeList = employeeList;
	}

	public Map<Long, String> getRoleList() {
		return roleList;
	}

	public void setRoleList(Map<Long, String> roleList) {
		this.roleList = roleList;
	}

	public String getKms() {
		return kms;
	}

	public void setKms(String kms) {
		this.kms = kms;
	}

	public String getEmployeeIds() {
		return employeeIds;
	}

	public void setEmployeeIds(String employeeIds) {
		this.employeeIds = employeeIds;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public List<LookUp> getDocTypeList() {
		return docTypeList;
	}

	public void setDocTypeList(List<LookUp> docTypeList) {
		this.docTypeList = docTypeList;
	}

	public Long getWard1() {
		return ward1;
	}

	public void setWard1(Long ward1) {
		this.ward1 = ward1;
	}

	public Long getWard2() {
		return ward2;
	}

	public void setWard2(Long ward2) {
		this.ward2 = ward2;
	}

	public Long getWard3() {
		return ward3;
	}

	public void setWard3(Long ward3) {
		this.ward3 = ward3;
	}

	public String getDocRefNo() {
		return docRefNo;
	}

	public void setDocRefNo(String docRefNo) {
		this.docRefNo = docRefNo;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public List<TbDepartment> getAssignedDeptList() {
		return assignedDeptList;
	}

	public void setAssignedDeptList(List<TbDepartment> assignedDeptList) {
		this.assignedDeptList = assignedDeptList;
	}

	public String getAssignDeptIds() {
		return assignDeptIds;
	}

	public void setAssignDeptIds(String assignDeptIds) {
		this.assignDeptIds = assignDeptIds;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public Long getDmsId() {
		return dmsId;
	}

	public void setDmsId(Long dmsId) {
		this.dmsId = dmsId;
	}

}
