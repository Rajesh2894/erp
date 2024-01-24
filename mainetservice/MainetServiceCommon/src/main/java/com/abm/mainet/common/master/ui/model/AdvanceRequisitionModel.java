package com.abm.mainet.common.master.ui.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.dto.AdvanceRequisitionDto;
import com.abm.mainet.common.integration.acccount.dto.VendorBillApprovalDTO;
import com.abm.mainet.common.integration.acccount.dto.VendorBillDedDetailDTO;
import com.abm.mainet.common.integration.acccount.dto.VendorBillExpDetailDTO;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.IAdvanceRequisitionService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;

/**
 * 
 * @author Jeetendra.Pal
 *
 */
@Component
@Scope("session")
public class AdvanceRequisitionModel extends AbstractFormModel {

	private static final long serialVersionUID = -8338723845082004659L;

	@Autowired
	private IFileUploadService fileUpload;
	@Autowired
	private IAdvanceRequisitionService advanceRequisitionService;
	@Autowired
	private IWorkflowRequestService workflowRequestService;
	@Autowired
	private ServiceMasterService serviceMaster;
	@Autowired
	private IChecklistVerificationService checkListService;
	@Autowired
	private ILocationMasService locMasService;
	@Autowired
	private DepartmentService departmentService;

	private String sliStatus;
	private AdvanceRequisitionDto advanceRequisitionDto = new AdvanceRequisitionDto();
	private List<LookUp> AdvanceType = new ArrayList<>();
	private List<TbDepartment> departmentsList = new ArrayList<>();
	private List<TbAcVendormaster> vendorList = new ArrayList<>();
	private List<DocumentDetailsVO> attachments = new ArrayList<>();
	private List<AttachDocs> attachDocsList = new ArrayList<>();
	private VendorBillApprovalDTO approvalDTO = new VendorBillApprovalDTO();
	private List<VendorBillExpDetailDTO> expDetListDto = new ArrayList<>();
	private VendorBillExpDetailDTO billExpDetailDTO = new VendorBillExpDetailDTO();
	private String removeFileById;
	private String saveMode;
	private Long actualTaskId;
	private Long serviceId;
	private boolean finalApproval;
	private Long workflowId;
	private Long parentOrgId;
	private String taskName;
	private boolean wokflowMode;
	private String workBillNumber;
	private boolean billEntry;

	@Override
	public boolean saveForm() {

		boolean status = true;

		if (!isWokflowMode()) {
			RequestDTO requestDTO = new RequestDTO();
			requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			requestDTO.setStatus(MainetConstants.FlagA);
			requestDTO.setDepartmentName(MainetConstants.CommonConstants.COM);
			requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
			List<DocumentDetailsVO> dto = getAttachments();

			setAttachments(fileUpload.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));

			int i = 0;
			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				getAttachments().get(i).setDoc_DESC_ENGL(dto.get(entry.getKey().intValue()).getDoc_DESC_ENGL());
				i++;
			}

			prepareAdvanceRequisitionEntity(getAdvanceRequisitionDto());
			AdvanceRequisitionDto dtoEntity = advanceRequisitionService
					.saveUpdateAdvanceRequisition(getAdvanceRequisitionDto(), getRemoveFileById());
			requestDTO.setIdfId(dtoEntity.getAdvNo());
			fileUpload.doMasterFileUpload(getAttachments(), requestDTO);

			setSuccessMessage(ApplicationSession.getInstance().getMessage("advance.requisition.save")
					+ MainetConstants.BLANK_WITH_SPACE
					+ ApplicationSession.getInstance().getMessage("advance.requisition.arn")
					+ MainetConstants.operator.QUOTES + dtoEntity.getAdvNo());
		} else {

			RequestDTO requestDTO = new RequestDTO();

			requestDTO.setReferenceId(getWorkflowActionDto().getReferenceId());
			requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			requestDTO.setDepartmentName(MainetConstants.CommonConstants.COM);
			requestDTO.setServiceId(getServiceId());
			requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());

			ServiceMaster service = serviceMaster.getServiceMasterByShortCode(MainetConstants.CAR, getParentOrgId());
			requestDTO.setDeptId(service.getTbDepartment().getDpDeptid());
			List<DocumentDetailsVO> docs = new ArrayList<>();
			DocumentDetailsVO document = new DocumentDetailsVO();
			document.setDocumentSerialNo(1L);
			docs.add(document);
			fileUpload.doFileUpload(getAttachments(), requestDTO);
			List<Long> attacheMentIds = checkListService.fetchAllAttachIdByReferenceId(
					getWorkflowActionDto().getReferenceId(), UserSession.getCurrent().getOrganisation().getOrgid());

			prepareWorkFlowTaskAction(getWorkflowActionDto());
			getWorkflowActionDto().setAttachementId(attacheMentIds);
			advanceRequisitionService.initiateWorkFlow(getWorkflowActionDto(), actualTaskId,
					MainetConstants.ADVANCE_REQUISITION_URL, MainetConstants.FlagU);

			WorkflowRequest workflowRequest = workflowRequestService.getWorkflowRequestByAppIdOrRefId(null,
					getWorkflowActionDto().getReferenceId(), getParentOrgId());

			if (workflowRequest != null
					&& workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.REJECTED)) {
				advanceRequisitionService.updateAdvanceRequisitionMode(advanceRequisitionDto.getAdvId(),
						MainetConstants.FlagR);
			}

			if (workflowRequest != null && workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Status.CLOSED)
					&& workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)) {
				advanceRequisitionService.updateAdvanceRequisitionMode(advanceRequisitionDto.getAdvId(),
						MainetConstants.FlagA);
				if (sliStatus != null && sliStatus.equals(MainetConstants.FlagL)) {
					this.saveBillApprval();
					setBillEntry(true);
				}
			}

			if (isBillEntry()) {
				setSuccessMessage(ApplicationSession.getInstance().getMessage("advance.requisition.bill.save")
						+ getWorkBillNumber());
			} else {
				setSuccessMessage(ApplicationSession.getInstance().getMessage("advance.requisition.approval.save"));
			}

		}

		return status;
	}

	private void prepareAdvanceRequisitionEntity(AdvanceRequisitionDto advanceRequisitionDto) {
		Date todayDate = new Date();

		advanceRequisitionDto.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
		if (advanceRequisitionDto.getCreatedBy() == null) {
			advanceRequisitionDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
			advanceRequisitionDto.setCreatedDate(todayDate);
			advanceRequisitionDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			advanceRequisitionDto.setAdvStatus(MainetConstants.FlagD);

		} else {
			advanceRequisitionDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
			advanceRequisitionDto.setUpdatedDate(todayDate);
			advanceRequisitionDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
		}

	}

	public WorkflowTaskAction prepareWorkFlowTaskAction(WorkflowTaskAction workflowActionDto) {

		workflowActionDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		workflowActionDto.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
		workflowActionDto.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		workflowActionDto.setEmpName(UserSession.getCurrent().getEmployee().getEmplname());
		workflowActionDto.setEmpEmail(UserSession.getCurrent().getEmployee().getEmpemail());

		workflowActionDto.setDateOfAction(new Date());
		workflowActionDto.setCreatedDate(new Date());
		workflowActionDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		workflowActionDto.setReferenceId(advanceRequisitionDto.getAdvNo());
		workflowActionDto.setPaymentMode(MainetConstants.FlagF);
		workflowActionDto.setIsFinalApproval(finalApproval);
		return workflowActionDto;

	}

	public void saveBillApprval() {

		ResponseEntity<?> responseEntity = null;
		List<VendorBillDedDetailDTO> deductionDetList = new ArrayList<>();
		VendorBillDedDetailDTO billDedDetailDTO = new VendorBillDedDetailDTO();
		deductionDetList.add(billDedDetailDTO);

		approvalDTO.setBillEntryDate(Utility.dateToString(new Date()));
		approvalDTO.setBillTypeId(CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(MainetConstants.AD,
				MainetConstants.ABT, getParentOrgId()));
		approvalDTO.setOrgId(getParentOrgId());
		approvalDTO.setNarration("Advance Requisition no -" + advanceRequisitionDto.getAdvNo());
		approvalDTO.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		approvalDTO.setCreatedDate(Utility.dateToString(new Date()));
		approvalDTO.setLgIpMacAddress(UserSession.getCurrent().getEmployee().getEmppiservername());

		approvalDTO.setVendorId(advanceRequisitionDto.getVenderId());
		approvalDTO.setInvoiceAmount(advanceRequisitionDto.getAdvAmount());

		billExpDetailDTO.setBudgetCodeId(advanceRequisitionDto.getHeadId());

		billExpDetailDTO.setAmount(advanceRequisitionDto.getAdvAmount());
		billExpDetailDTO.setSanctionedAmount(advanceRequisitionDto.getAdvAmount());

		expDetListDto.add(billExpDetailDTO);

		approvalDTO.setExpDetListDto(expDetListDto);

		long fieldId = 0;
		if (UserSession.getCurrent().getLoggedLocId() != null) {
			final TbLocationMas locMas = locMasService.findById(UserSession.getCurrent().getLoggedLocId());
			if ((locMas.getLocRevenueWZMappingDto() != null) && !locMas.getLocRevenueWZMappingDto().isEmpty()) {
				fieldId = locMas.getLocRevenueWZMappingDto().get(0).getCodIdRevLevel1();
			}
		}
		if (fieldId == 0) {
			throw new NullPointerException("fieldId is not linked with Location Master for[locId="
					+ UserSession.getCurrent().getEmployee().getTbLocationMas().getLocId() + ",locName="
					+ UserSession.getCurrent().getEmployee().getTbLocationMas().getLocNameEng() + "]");
		}
		approvalDTO.setFieldId(fieldId);

		Department entities = departmentService.getDepartment("COM", MainetConstants.CommonConstants.ACTIVE);
		approvalDTO.setDepartmentId(entities.getDpDeptid());

		try {
			responseEntity = RestClient.callRestTemplateClient(approvalDTO, ServiceEndpoints.SALARY_POSTING);
			if (responseEntity != null && responseEntity.getStatusCode().equals(HttpStatus.OK)) {
				advanceRequisitionService.updateBillNumberByAdvId(responseEntity.getBody().toString(),
						advanceRequisitionDto.getAdvId());
				setWorkBillNumber(responseEntity.getBody().toString());
			}
		} catch (Exception exception) {

			throw new FrameworkException("error occured while bill posting to account module ", exception);
		}
	}

	public AdvanceRequisitionDto getAdvanceRequisitionDto() {
		return advanceRequisitionDto;
	}

	public void setAdvanceRequisitionDto(AdvanceRequisitionDto advanceRequisitionDto) {
		this.advanceRequisitionDto = advanceRequisitionDto;
	}

	public List<LookUp> getAdvanceType() {
		return AdvanceType;
	}

	public void setAdvanceType(List<LookUp> advanceType) {
		AdvanceType = advanceType;
	}

	public List<TbDepartment> getDepartmentsList() {
		return departmentsList;
	}

	public void setDepartmentsList(List<TbDepartment> departmentsList) {
		this.departmentsList = departmentsList;
	}

	public List<TbAcVendormaster> getVendorList() {
		return vendorList;
	}

	public void setVendorList(List<TbAcVendormaster> vendorList) {
		this.vendorList = vendorList;
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

	public VendorBillApprovalDTO getApprovalDTO() {
		return approvalDTO;
	}

	public void setApprovalDTO(VendorBillApprovalDTO approvalDTO) {
		this.approvalDTO = approvalDTO;
	}

	public List<VendorBillExpDetailDTO> getExpDetListDto() {
		return expDetListDto;
	}

	public void setExpDetListDto(List<VendorBillExpDetailDTO> expDetListDto) {
		this.expDetListDto = expDetListDto;
	}

	public VendorBillExpDetailDTO getBillExpDetailDTO() {
		return billExpDetailDTO;
	}

	public void setBillExpDetailDTO(VendorBillExpDetailDTO billExpDetailDTO) {
		this.billExpDetailDTO = billExpDetailDTO;
	}

	public String getRemoveFileById() {
		return removeFileById;
	}

	public void setRemoveFileById(String removeFileById) {
		this.removeFileById = removeFileById;
	}

	public String getSliStatus() {
		return sliStatus;
	}

	public void setSliStatus(String sliStatus) {
		this.sliStatus = sliStatus;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public Long getActualTaskId() {
		return actualTaskId;
	}

	public void setActualTaskId(Long actualTaskId) {
		this.actualTaskId = actualTaskId;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public boolean isFinalApproval() {
		return finalApproval;
	}

	public void setFinalApproval(boolean finalApproval) {
		this.finalApproval = finalApproval;
	}

	public Long getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(Long workflowId) {
		this.workflowId = workflowId;
	}

	public Long getParentOrgId() {
		return parentOrgId;
	}

	public void setParentOrgId(Long parentOrgId) {
		this.parentOrgId = parentOrgId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public boolean isWokflowMode() {
		return wokflowMode;
	}

	public void setWokflowMode(boolean wokflowMode) {
		this.wokflowMode = wokflowMode;
	}

	public String getWorkBillNumber() {
		return workBillNumber;
	}

	public void setWorkBillNumber(String workBillNumber) {
		this.workBillNumber = workBillNumber;
	}

	public boolean isBillEntry() {
		return billEntry;
	}

	public void setBillEntry(boolean billEntry) {
		this.billEntry = billEntry;
	}

}
