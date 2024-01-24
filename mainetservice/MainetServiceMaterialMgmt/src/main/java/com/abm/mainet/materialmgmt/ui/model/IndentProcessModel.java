package com.abm.mainet.materialmgmt.ui.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonUtility;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.common.workflow.ui.validator.CheckerActionValidator;
import com.abm.mainet.materialmgmt.dto.BinLocMasDto;
import com.abm.mainet.materialmgmt.dto.IndentIssueDto;
import com.abm.mainet.materialmgmt.dto.IndentProcessDTO;
import com.abm.mainet.materialmgmt.dto.IndentProcessItemDto;
import com.abm.mainet.materialmgmt.service.IndentProcessService;
import com.abm.mainet.materialmgmt.utility.MainetMMConstants;

@Component
@Scope("session")
public class IndentProcessModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;

	private IndentProcessDTO indentProcessDTO = new IndentProcessDTO();

	private IndentProcessItemDto indentProcessItemDTO = new IndentProcessItemDto();

	private List<IndentProcessDTO> listIndentProcessDTO = new ArrayList<>();

    List<Department> departmentList =new ArrayList<>();
	
	private boolean lastChecker;

	private long levelcheck;

	private String saveMode;

	private int indexCount;
	
	private String managementCode;
	
	private WorkflowTaskAction workflowActionDto = new WorkflowTaskAction();

	private List<BinLocMasDto> binLocList = new ArrayList<>();

	private Map<Long, List<IndentIssueDto>> indentIssueMap = new LinkedHashMap<>();

	private List<IndentIssueDto> listIndentIssueDtos = new ArrayList<>();

	private IndentIssueDto indentIssueDto = new IndentIssueDto();

	private List<String> itemNumberList = new ArrayList<>();
	
	private Map<String, List<String>> itemNumbersMap = new LinkedHashMap<>();

	private List<DocumentDetailsVO> documents = new ArrayList<>();

	private List<DocumentDetailsVO> attachments = new ArrayList<>();

	private List<AttachDocs> attachDocsList = new ArrayList<>();

	private List<Object[]> employees;
	
	private List<Object[]> storeIdNameList;

	private List<Object[]> itemIdNameList;
	
	private List<Object[]> departmentIdNameList;
		
	@Autowired
	private IndentProcessService indentProcessService;

	@Autowired
	private SeqGenFunctionUtility seqGenFunctionUtility;

	@Autowired
	private IOrganisationService iOrganisationService;

	@Resource
	private TbAcVendormasterService tbAcVendormasterService;

	@Resource
	private ServiceMasterService serviceMasterService;
	
	@Resource
	private IWorkflowRequestService iWorkflowRequestService;
	
	@Autowired
	IFileUploadService fileUpload;

	
	@Override
	public boolean saveForm() {
		boolean status = false;

		if ( 0L != this.levelcheck && !this.lastChecker )
			validateBean(this.getWorkflowActionDto(), CheckerActionValidator.class);
		if (hasValidationErrors())
			return false;
		else {
			Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
			Long empID = UserSession.getCurrent().getEmployee().getEmpId();
			Date sysDate = new Date();
			Long langId = Long.valueOf(UserSession.getCurrent().getLanguageId());
			String macAddr = UserSession.getCurrent().getEmployee().getEmppiservername();
			
			if (null == indentProcessDTO.getIndentid()) {
				indentProcessDTO.setOrgid(orgId);
				indentProcessDTO.setLgIpMac(macAddr);
				indentProcessDTO.setLangId(langId);
				indentProcessDTO.setUser_id(empID);
				indentProcessDTO.setLmoddate(sysDate);
				indentProcessDTO.setStatus(MainetConstants.FlagN); /** N==Pending For Approval, Prefix: IDS */
				indentProcessDTO.setWf_flag( MainetConstants.WorkFlow.Status.PENDING);
				if (indentProcessDTO.getItem() != null) {
					indentProcessDTO.getItem().forEach(processItemDto -> {
						processItemDto.setOrgid(orgId);
						processItemDto.setUser_id(empID);
						processItemDto.setLgIpMac(macAddr);
						processItemDto.setLangId(langId);
						processItemDto.setStatus(MainetConstants.FlagY);
					});
				}
				
				genrateIndentNo();
				
			} else {
				indentProcessDTO.setUpdatedBy(empID);
				indentProcessDTO.setUpdatedDate(sysDate);
				indentProcessDTO.setLgIpMacUpd(macAddr);
				
				if (indentProcessDTO.getItem() != null) {
					indentProcessDTO.getItem().forEach(processItemDto -> {
						processItemDto.setUpdatedBy(empID);
						processItemDto.setUpdatedDate(sysDate);
						processItemDto.setLgIpMac(macAddr);
						processItemDto.setLangId(langId);

						if ( this.lastChecker ) {
							this.getIndentIssueMap().get(processItemDto.getInditemid()).forEach(indentIssueDto -> {
								if (indentIssueDto.getInditemid().equals(processItemDto.getInditemid())) {
									indentIssueDto.setORGID(orgId);
									indentIssueDto.setUSER_ID(empID);
									indentIssueDto.setLmoddate(sysDate);
									indentIssueDto.setLANGID(langId);
									indentIssueDto.setLG_IP_MAC(macAddr);
									indentIssueDto.setStatus(MainetConstants.FlagY);
									processItemDto.getIndentIssueDtoList().add(indentIssueDto);
								}
							});
							this.getWorkflowActionDto().setDecision(MainetConstants.WorkFlow.Decision.APPROVED);
							this.getWorkflowActionDto().setComments("AUTO APPROVED");
						}
					});
				}
			}
			
			if (this.getLevelcheck() >= 1L) {
				prepareWorkFlowTaskAction(this.getWorkflowActionDto());
				if (MainetConstants.WorkFlow.Decision.REJECTED.equals(this.getWorkflowActionDto().getDecision()))
					setSuccessMessage(ApplicationSession.getInstance().getMessage("material.management.application.rejected.success"));
				else
					if(this.lastChecker)
						this.setSuccessMessage(ApplicationSession.getInstance().getMessage("department.indent.issued.success"));					
					else
						this.setSuccessMessage(ApplicationSession.getInstance().getMessage("application.Approved.Successfully"));
			} 

			indentProcessService.saveIndentProcess(indentProcessDTO,  this.levelcheck, this.getWorkflowActionDto());

			if(0L != this.levelcheck)
				setWorkFlowStatusOnVendor();
			
			status = true;	
			
			/*********** File Upload *************/
			List<Long> removeFileById = null;
			String fileId = indentProcessDTO.getRemoveFileById();
			if (fileId != null && !fileId.isEmpty()) {
				removeFileById = new ArrayList<>();
				String fileArray[] = fileId.split(MainetConstants.operator.COMMA);
				for (String fields : fileArray)
					removeFileById.add(Long.valueOf(fields));
			}
			if (removeFileById != null && !removeFileById.isEmpty())
				tbAcVendormasterService.updateUploadedFileDeleteRecords(removeFileById, indentProcessDTO.getUser_id());

			FileUploadDTO requestDTO = new FileUploadDTO();
			requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			requestDTO.setStatus(MainetConstants.FlagA);
			requestDTO.setIdfId(MainetConstants.DEPT_SHORT_NAME.STORE + MainetConstants.WINDOWS_SLASH + indentProcessDTO.getIndentno());
			requestDTO.setDepartmentName(MainetConstants.DEPT_SHORT_NAME.STORE);
			requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
			List<DocumentDetailsVO> dto = getDocuments();
			setDocuments(fileUpload.setFileUploadMethod(getDocuments()));
			setAttachments(fileUpload.setFileUploadMethod(getAttachments()));
			fileUpload.doMasterFileUpload(getAttachments(), requestDTO);
			int i = 0;
			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				getDocuments().get(i).setDoc_DESC_ENGL(dto.get(entry.getKey().intValue()).getDoc_DESC_ENGL());
				i++;
			}
		}
		return status;
	}
	
	
	private WorkflowTaskAction prepareWorkFlowTaskAction(WorkflowTaskAction workflowActionDto) {
		this.getWorkflowActionDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		workflowActionDto.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
		workflowActionDto.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		workflowActionDto.setEmpName(UserSession.getCurrent().getEmployee().getEmpname() + MainetConstants.WHITE_SPACE 
				+ UserSession.getCurrent().getEmployee().getEmplname());
		workflowActionDto.setEmpEmail(UserSession.getCurrent().getEmployee().getEmpemail());
		workflowActionDto.setDateOfAction(new Date());
		workflowActionDto.setCreatedDate(new Date());
		workflowActionDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		workflowActionDto.setReferenceId(this.getWorkflowActionDto().getReferenceId());
		workflowActionDto.setPaymentMode(MainetConstants.FlagF);
		workflowActionDto.setIsFinalApproval(false);
		return workflowActionDto;
	}
	
	private void setWorkFlowStatusOnVendor() {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getWorkflowActionDto().setReferenceId(this.indentProcessDTO.getIndentno());		
		WorkflowRequest workflowRequest = iWorkflowRequestService.getWorkflowRequestByAppIdOrRefId(null,
				this.getWorkflowActionDto().getReferenceId(), orgId);

		if (workflowRequest != null && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.REJECTED))
			indentProcessService.updateIndentStatus(orgId, this.getWorkflowActionDto().getReferenceId(), MainetConstants.FlagR, MainetConstants.WorkFlow.Status.CLOSED);  /** R==Rejected, Prefix: IDS */

		if (workflowRequest != null && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED))
			indentProcessService.updateIndentStatus(orgId, this.getWorkflowActionDto().getReferenceId(), MainetConstants.FlagP, MainetConstants.WorkFlow.Status.PENDING); /** P==Pending For Issuance, Prefix: IDS */

		if (workflowRequest != null && workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Status.CLOSED)
				&& workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)) //on issue complete WF_flag
			indentProcessService.updateIndentStatus(orgId, this.getWorkflowActionDto().getReferenceId(), MainetConstants.FlagI,  MainetConstants.WorkFlow.Status.CLOSED); /** I==Issued, Prefix: IDS */
	}
	
	private void genrateIndentNo() {
		String date = CommonUtility.dateToString(new Date());
		final Organisation organisation = iOrganisationService.getOrganisationById(indentProcessDTO.getOrgid());
		final Long sequence = seqGenFunctionUtility.generateSequenceNo("ITP", "MM_INDENT", "indentid", 
									UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.FlagF, null);
		String indentNo = MainetMMConstants.MmItemMaster.IND +  MainetConstants.SLASH + organisation.getOrgShortNm() + MainetConstants.SLASH + MainetMMConstants.MmItemMaster.STR 
				+ MainetConstants.SLASH + date + MainetConstants.SLASH + String.format(MainetConstants.CommonMasterUi.PADDING_THREE, sequence);
		indentProcessDTO.setIndentno(indentNo);
		setSuccessMessage(ApplicationSession.getInstance().getMessage("department.indent.submit.success") + MainetConstants.WHITE_SPACE
				+ indentProcessDTO.getIndentno());	
	}
	
	
	public IndentProcessDTO getIndentProcessDTO() {
		return indentProcessDTO;
	}

	public void setIndentProcessDTO(IndentProcessDTO indentProcessDTO) {
		this.indentProcessDTO = indentProcessDTO;
	}

	public List<IndentProcessDTO> getListIndentProcessDTO() {
		return listIndentProcessDTO;
	}

	public void setListIndentProcessDTO(List<IndentProcessDTO> listIndentProcessDTO) {
		this.listIndentProcessDTO = listIndentProcessDTO;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public WorkflowTaskAction getWorkflowActionDto() {
		return workflowActionDto;
	}

	public void setWorkflowActionDto(WorkflowTaskAction workflowActionDto) {
		this.workflowActionDto = workflowActionDto;
	}

	public IndentProcessItemDto getIndentProcessItemDTO() {
		return indentProcessItemDTO;
	}

	public void setIndentProcessItemDTO(IndentProcessItemDto indentProcessItemDTO) {
		this.indentProcessItemDTO = indentProcessItemDTO;
	}

	public boolean isLastChecker() {
		return lastChecker;
	}

	public void setLastChecker(boolean lastChecker) {
		this.lastChecker = lastChecker;
	}

	public long getLevelcheck() {
		return levelcheck;
	}

	public void setLevelcheck(long levelcheck) {
		this.levelcheck = levelcheck;
	}

	public List<BinLocMasDto> getBinLocList() {
		return binLocList;
	}

	public void setBinLocList(List<BinLocMasDto> binLocList) {
		this.binLocList = binLocList;
	}

	public Map<Long, List<IndentIssueDto>> getIndentIssueMap() {
		return indentIssueMap;
	}

	public void setIndentIssueMap(Map<Long, List<IndentIssueDto>> indentIssueMap) {
		this.indentIssueMap = indentIssueMap;
	}

	public List<IndentIssueDto> getListIndentIssueDtos() {
		return listIndentIssueDtos;
	}

	public void setListIndentIssueDtos(List<IndentIssueDto> listIndentIssueDtos) {
		this.listIndentIssueDtos = listIndentIssueDtos;
	}

	public IndentIssueDto getIndentIssueDto() {
		return indentIssueDto;
	}

	public void setIndentIssueDto(IndentIssueDto indentIssueDto) {
		this.indentIssueDto = indentIssueDto;
	}

	public int getIndexCount() {
		return indexCount;
	}

	public void setIndexCount(int indexCount) {
		this.indexCount = indexCount;
	}

	public String getManagementCode() {
		return managementCode;
	}

	public void setManagementCode(String managementCode) {
		this.managementCode = managementCode;
	}

	public List<String> getItemNumberList() {
		return itemNumberList;
	}

	public void setItemNumberList(List<String> itemNumberList) {
		this.itemNumberList = itemNumberList;
	}

	public List<Department> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<Department> departmentList) {
		this.departmentList = departmentList;
	}

	public Map<String, List<String>> getItemNumbersMap() {
		return itemNumbersMap;
	}

	public void setItemNumbersMap(Map<String, List<String>> itemNumbersMap) {
		this.itemNumbersMap = itemNumbersMap;
	}

	public List<DocumentDetailsVO> getDocuments() {
		return documents;
	}

	public void setDocuments(List<DocumentDetailsVO> documents) {
		this.documents = documents;
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

	public List<Object[]> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Object[]> employees) {
		this.employees = employees;
	}

	public List<Object[]> getStoreIdNameList() {
		return storeIdNameList;
	}

	public void setStoreIdNameList(List<Object[]> storeIdNameList) {
		this.storeIdNameList = storeIdNameList;
	}

	public List<Object[]> getItemIdNameList() {
		return itemIdNameList;
	}

	public void setItemIdNameList(List<Object[]> itemIdNameList) {
		this.itemIdNameList = itemIdNameList;
	}

	public List<Object[]> getDepartmentIdNameList() {
		return departmentIdNameList;
	}

	public void setDepartmentIdNameList(List<Object[]> departmentIdNameList) {
		this.departmentIdNameList = departmentIdNameList;
	}

}
