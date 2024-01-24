package com.abm.mainet.workManagement.ui.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.ContractAgreementSummaryDTO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.workManagement.dto.ScheduleOfRateMstDto;
import com.abm.mainet.workManagement.dto.TenderMasterDto;
import com.abm.mainet.workManagement.dto.TenderWorkDto;
import com.abm.mainet.workManagement.dto.WorkEstimateMasterDto;
import com.abm.mainet.workManagement.dto.WorkOrderContractDetailsDto;
import com.abm.mainet.workManagement.dto.WorkOrderDto;
import com.abm.mainet.workManagement.service.WorkOrderService;

@Component
@Scope("session")
public class WorkOrderGenerationModel extends AbstractFormModel {

	private static final long serialVersionUID = -8652785345319268040L;

	private String saveMode;
	private String removeFileById;
	private String removeTermsCondById;

	private List<Long> fileCountUpload;

	private WorkOrderDto workOrderDto;

	private List<AttachDocs> attachDocsList = new ArrayList<>();

	private List<DocumentDetailsVO> attachments = new ArrayList<>();

	private List<TenderMasterDto> tenderMasterDtosList = new ArrayList<>();

	private TenderMasterDto tenderMasterDto;

	private TenderWorkDto tenderWorkDto;

	private ScheduleOfRateMstDto scheduleOfRateMstDto;

	private List<WorkEstimateMasterDto> workEstimateMasterDtoList = new ArrayList<>();

	private List<ContractAgreementSummaryDTO> listContractAggrement = new ArrayList<>();

	private ContractAgreementSummaryDTO contractAgreementSummaryDTO;

	private Long DeptId;

	private String workName;

	private List<WorkOrderContractDetailsDto> contractDetailsDtosList;

	private List<Employee> employeeList = new ArrayList<>();

	private List<String> termsList;

	private String termFlag;
	
	private List<TbDepartment> departmentsList=new ArrayList<>();

	public String getTermFlag() {
		return termFlag;
	}

	public void setTermFlag(String termFlag) {
		this.termFlag = termFlag;
	}

	public List<String> getTermsList() {
		return termsList;
	}

	public void setTermsList(List<String> termsList) {
		this.termsList = termsList;
	}

	@Override
	public boolean saveForm() {

		boolean status = true;
		getWorkOrderDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());

		RequestDTO requestDTO = new RequestDTO();
		requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		requestDTO.setStatus(MainetConstants.FlagA);

		requestDTO.setDepartmentName(MainetConstants.WorksManagement.WORKS_MANAGEMENT);
		requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		List<DocumentDetailsVO> dto = getAttachments();
		setAttachments(ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
				.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
		//#71812
		if(getTermFlag() != null && getTermFlag().equals(MainetConstants.FlagY)) {
			if(!getWorkOrderDto().getWorkOrderTermsDtoList().isEmpty()){
				StringBuffer stringBuffer=new StringBuffer();
				stringBuffer.append(getWorkOrderDto().getWorkOrderTermsDtoList().get(0).getTermsDesc());
				stringBuffer.append("EnCrYpTed");
				getWorkOrderDto().getWorkOrderTermsDtoList().get(0).setTermsDesc((stringBuffer.toString()));
			}
		}
		
		int i = 0;
		for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
			getAttachments().get(i).setDoc_DESC_ENGL(dto.get(entry.getKey().intValue()).getDoc_DESC_ENGL());
			i++;
		}

		if (getSaveMode().equals(MainetConstants.WorksManagement.ADD)) {

			workOrderDto.setCreatedDate(new Date());
			workOrderDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
			workOrderDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			WorkOrderDto svedOrderDto = ApplicationContextProvider.getApplicationContext()
					.getBean(WorkOrderService.class)
					.createWorkOrderGeneration(workOrderDto, getAttachments(), requestDTO, getDeptId());

			setSuccessMessage(getAppSession().getMessage("work.order.creation.successfull") + " "
					+ getAppSession().getMessage("work.order.code") + " " + svedOrderDto.getWorkOrderNo());
		} else {

			List<Long> removeFileId = removeFileByIdAsList();
			List<Long> removeTermsById = removeTermsCondByIdAsList();
			workOrderDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
			workOrderDto.setUpdatedDate(new Date());
			workOrderDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());

			ApplicationContextProvider.getApplicationContext().getBean(WorkOrderService.class)
					.updateWorkOrderGeneration(workOrderDto, getAttachments(), requestDTO, removeFileId,
							removeTermsById);
			setSuccessMessage(getAppSession().getMessage("work.order.updation.successfull"));
		}

		return status;
	}

	// Deleted Terms And Condition with the help of Id's
	private List<Long> removeTermsCondByIdAsList() {
		List<Long> removeTermsList = null;
		String termsIdList = getRemoveTermsCondById();
		if (termsIdList != null && !termsIdList.isEmpty()) {
			removeTermsList = new ArrayList<>();
			String termsArray[] = termsIdList.split(MainetConstants.operator.COMMA);
			for (String termsCondId : termsArray) {
				removeTermsList.add(Long.valueOf(termsCondId));
			}
		}
		return removeTermsList;
	}

	// Deleted File Documents with the help of Id's
	private List<Long> removeFileByIdAsList() {
		List<Long> removeFileIdList = null;
		String fileIdList = getRemoveFileById();
		if (fileIdList != null && !fileIdList.isEmpty()) {
			removeFileIdList = new ArrayList<>();
			String fileArray[] = fileIdList.split(MainetConstants.operator.COMMA);
			for (String fileId : fileArray) {
				removeFileIdList.add(Long.valueOf(fileId));
			}
		}
		return removeFileIdList;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public String getRemoveFileById() {
		return removeFileById;
	}

	public void setRemoveFileById(String removeFileById) {
		this.removeFileById = removeFileById;
	}

	public String getRemoveTermsCondById() {
		return removeTermsCondById;
	}

	public void setRemoveTermsCondById(String removeTermsCondById) {
		this.removeTermsCondById = removeTermsCondById;
	}

	public List<Long> getFileCountUpload() {
		return fileCountUpload;
	}

	public void setFileCountUpload(List<Long> fileCountUpload) {
		this.fileCountUpload = fileCountUpload;
	}

	public WorkOrderDto getWorkOrderDto() {
		return workOrderDto;
	}

	public void setWorkOrderDto(WorkOrderDto workOrderDto) {
		this.workOrderDto = workOrderDto;
	}

	public List<AttachDocs> getAttachDocsList() {
		return attachDocsList;
	}

	public void setAttachDocsList(List<AttachDocs> attachDocsList) {
		this.attachDocsList = attachDocsList;
	}

	public List<DocumentDetailsVO> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<DocumentDetailsVO> attachments) {
		this.attachments = attachments;
	}

	public List<TenderMasterDto> getTenderMasterDtosList() {
		return tenderMasterDtosList;
	}

	public void setTenderMasterDtosList(List<TenderMasterDto> tenderMasterDtosList) {
		this.tenderMasterDtosList = tenderMasterDtosList;
	}

	public TenderMasterDto getTenderMasterDto() {
		return tenderMasterDto;
	}

	public void setTenderMasterDto(TenderMasterDto tenderMasterDto) {
		this.tenderMasterDto = tenderMasterDto;
	}

	public List<ContractAgreementSummaryDTO> getListContractAggrement() {
		return listContractAggrement;
	}

	public void setListContractAggrement(List<ContractAgreementSummaryDTO> listContractAggrement) {
		this.listContractAggrement = listContractAggrement;
	}

	public Long getDeptId() {
		return DeptId;
	}

	public void setDeptId(Long deptId) {
		DeptId = deptId;
	}

	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	public ContractAgreementSummaryDTO getContractAgreementSummaryDTO() {
		return contractAgreementSummaryDTO;
	}

	public void setContractAgreementSummaryDTO(ContractAgreementSummaryDTO contractAgreementSummaryDTO) {
		this.contractAgreementSummaryDTO = contractAgreementSummaryDTO;
	}

	public TenderWorkDto getTenderWorkDto() {
		return tenderWorkDto;
	}

	public void setTenderWorkDto(TenderWorkDto tenderWorkDto) {
		this.tenderWorkDto = tenderWorkDto;
	}

	public ScheduleOfRateMstDto getScheduleOfRateMstDto() {
		return scheduleOfRateMstDto;
	}

	public void setScheduleOfRateMstDto(ScheduleOfRateMstDto scheduleOfRateMstDto) {
		this.scheduleOfRateMstDto = scheduleOfRateMstDto;
	}

	public List<WorkEstimateMasterDto> getWorkEstimateMasterDtoList() {
		return workEstimateMasterDtoList;
	}

	public void setWorkEstimateMasterDtoList(List<WorkEstimateMasterDto> workEstimateMasterDtoList) {
		this.workEstimateMasterDtoList = workEstimateMasterDtoList;
	}

	public List<WorkOrderContractDetailsDto> getContractDetailsDtosList() {
		return contractDetailsDtosList;
	}

	public void setContractDetailsDtosList(List<WorkOrderContractDetailsDto> contractDetailsDtosList) {
		this.contractDetailsDtosList = contractDetailsDtosList;
	}

	public List<Employee> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<Employee> employeeList) {
		this.employeeList = employeeList;
	}

	public List<TbDepartment> getDepartmentsList() {
		return departmentsList;
	}

	public void setDepartmentsList(List<TbDepartment> departmentsList) {
		this.departmentsList = departmentsList;
	}

}
