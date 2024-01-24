package com.abm.mainet.workManagement.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.master.dto.ContractAgreementSummaryDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.workManagement.dto.WorkEstimateMasterDto;
import com.abm.mainet.workManagement.dto.WorkOrderContractDetailsDto;
import com.abm.mainet.workManagement.service.WorkEstimateService;

@Component
@Scope("session")
public class WorkContractVariationFormModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;

	@Autowired
	WorkEstimateService estimateService;

	private Long newWorkId;
	private String workeReviseFlag;
	private Long newVendorId;
	private String newWorkCode;
	private Long newContractId;
	private String saveMode;
	private String estimateTypeId;
	private String requestFormFlag;
	private String esimateType;
	private Long rateAnalysisWorkId;
	private String removeNonSorById;
	private String removeChildIds;
	private List<WorkEstimateMasterDto> workEstimateNonSorFormList = new ArrayList<WorkEstimateMasterDto>();
	private List<WorkEstimateMasterDto> measurementsheetViewDataVariation = new ArrayList<WorkEstimateMasterDto>();
	private List<WorkEstimateMasterDto> workEstimateBillQuantityList = new ArrayList<WorkEstimateMasterDto>();
	private List<ContractAgreementSummaryDTO> contractAgreementSummaryDTO = new ArrayList<ContractAgreementSummaryDTO>();
	private List<WorkOrderContractDetailsDto> workOrderContractDetailsDto = new ArrayList<WorkOrderContractDetailsDto>();
	private WorkEstimateMasterDto workestimateMasterDto;
	private String estimateRadioFlag;
	private List<LookUp> unitLookUpList = new ArrayList<>();
	private String ContractNo;
	private String cpdModeHideSor;

	public void saveSORData(List<WorkEstimateMasterDto> workestimateMasterDto) {

		Date todayDate = new Date();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long empId = UserSession.getCurrent().getEmployee().getEmpId();

		for (WorkEstimateMasterDto measureDetailsDto : workestimateMasterDto) {
			measureDetailsDto.setOrgId(orgId);
			if (measureDetailsDto.getCreatedBy() == null) {
				measureDetailsDto.setCreatedBy(empId);
				measureDetailsDto.setWorkId(this.getNewWorkId());
				measureDetailsDto.setWorkEstimPId(getRateAnalysisWorkId());
				measureDetailsDto.setContractId(this.getNewContractId());
				measureDetailsDto.setEstimateType(MainetConstants.FlagS);
				measureDetailsDto.setCreatedDate(todayDate);
				measureDetailsDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
				measureDetailsDto.setWorkEstimActive(MainetConstants.IsDeleted.DELETE);
				measureDetailsDto.setWorkEstimFlag(MainetConstants.FlagC);
				measureDetailsDto.setWorkeReviseFlag(MainetConstants.FlagN);

			} else {
				measureDetailsDto.setUpdatedBy(empId);
				measureDetailsDto.setUpdatedDate(todayDate);
				measureDetailsDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
			}
		}
		List<Long> nonSorRemoveById = null;
		String fileId = getRemoveNonSorById();
		if (fileId != null && !fileId.isEmpty()) {
			nonSorRemoveById = new ArrayList<>();
			String fileArray[] = fileId.split(MainetConstants.operator.COMMA);
			for (String fields : fileArray) {
				nonSorRemoveById.add(Long.valueOf(fields));
			}
		}

		estimateService.saveWorkEstimateList(getMeasurementsheetViewDataVariation(), nonSorRemoveById, getSaveMode());

	}

	public void saveNonSORData(List<WorkEstimateMasterDto> workestimateMasterDto) {

		Date todayDate = new Date();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long empId = UserSession.getCurrent().getEmployee().getEmpId();

		for (WorkEstimateMasterDto measureDetailsDto : workestimateMasterDto) {
			measureDetailsDto.setOrgId(orgId);
			if (measureDetailsDto.getCreatedBy() == null) {
				measureDetailsDto.setCreatedBy(empId);
				measureDetailsDto.setWorkId(this.getNewWorkId());
				measureDetailsDto.setWorkEstimPId(getRateAnalysisWorkId());
				measureDetailsDto.setContractId(this.getNewContractId());
				measureDetailsDto.setEstimateType(MainetConstants.FlagN);
				measureDetailsDto.setCreatedDate(todayDate);
				measureDetailsDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
				measureDetailsDto.setWorkEstimActive(MainetConstants.IsDeleted.DELETE);
				measureDetailsDto.setWorkEstimFlag(MainetConstants.FlagC);
				measureDetailsDto.setWorkeReviseFlag(MainetConstants.FlagN);

			} else {
				measureDetailsDto.setUpdatedBy(empId);
				measureDetailsDto.setUpdatedDate(todayDate);
				measureDetailsDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
			}
		}
		List<Long> nonSorRemoveById = null;
		String fileId = getRemoveNonSorById();
		if (fileId != null && !fileId.isEmpty()) {
			nonSorRemoveById = new ArrayList<>();
			String fileArray[] = fileId.split(MainetConstants.operator.COMMA);
			for (String fields : fileArray) {
				nonSorRemoveById.add(Long.valueOf(fields));
			}
		}

		estimateService.saveWorkEstimateList(getWorkEstimateNonSorFormList(), nonSorRemoveById, getSaveMode());

	}

	public void saveBillQuantityData(List<WorkEstimateMasterDto> workestimateMasterDto) {

		Date todayDate = new Date();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long empId = UserSession.getCurrent().getEmployee().getEmpId();

		for (WorkEstimateMasterDto measureDetailsDto : workestimateMasterDto) {
			measureDetailsDto.setOrgId(orgId);
			if (measureDetailsDto.getCreatedBy() == null) {
				measureDetailsDto.setCreatedBy(empId);
				measureDetailsDto.setWorkId(this.getNewWorkId());
				measureDetailsDto.setWorkEstimPId(getRateAnalysisWorkId());
				measureDetailsDto.setContractId(this.getNewContractId());
				measureDetailsDto.setEstimateType(MainetConstants.FlagB);
				measureDetailsDto.setCreatedDate(todayDate);
				measureDetailsDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
				measureDetailsDto.setWorkEstimActive(MainetConstants.IsDeleted.DELETE);
				measureDetailsDto.setWorkEstimFlag(MainetConstants.FlagC);
				measureDetailsDto.setWorkeReviseFlag(MainetConstants.FlagN);

			} else {
				measureDetailsDto.setUpdatedBy(empId);
				measureDetailsDto.setUpdatedDate(todayDate);
				measureDetailsDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
			}
		}
		List<Long> nonSorRemoveById = null;
		String fileId = getRemoveNonSorById();
		if (fileId != null && !fileId.isEmpty()) {
			nonSorRemoveById = new ArrayList<>();
			String fileArray[] = fileId.split(MainetConstants.operator.COMMA);
			for (String fields : fileArray) {
				nonSorRemoveById.add(Long.valueOf(fields));
			}
		}

		estimateService.saveWorkEstimateList(getWorkEstimateBillQuantityList(), nonSorRemoveById, getSaveMode());

	}

	public void setAllContractVariationData(String estimateType, Long contId, Long orgId) {
		if (estimateType.equals(MainetConstants.FlagS)) {
			this.setMeasurementsheetViewDataVariation(
					estimateService.getAllRevisedContarctEstimateDetailsByContrcatId(contId, orgId, estimateType));
			this.setWorkeReviseFlag(MainetConstants.FlagN);
		} else if (estimateType.equals(MainetConstants.FlagN)) {
			this.setWorkEstimateNonSorFormList(
					estimateService.getAllRevisedContarctEstimateDetailsByContrcatId(contId, orgId, estimateType));
			this.setWorkeReviseFlag(MainetConstants.FlagN);
		} else if (estimateType.equals(MainetConstants.FlagB)) {

			this.setWorkEstimateBillQuantityList(
					estimateService.getAllRevisedContarctEstimateDetailsByContrcatId(contId, orgId, estimateType));
			this.setWorkeReviseFlag(MainetConstants.FlagN);
		}
	}

	public void setAllExistingContractVariationData(String estimateType, Long contId, Long orgId) {
		if (estimateType.equals(MainetConstants.FlagS)) {
			this.setMeasurementsheetViewDataVariation(
					estimateService.getPreviousEstimateByContractId(contId, orgId, estimateType));
			this.setWorkeReviseFlag(MainetConstants.MODE_EDIT);
			// this.setWorkeReviseFlag(this.getMeasurementsheetViewDataVariation().get(0).getWorkeReviseFlag());
		} else if (estimateType.equals(MainetConstants.FlagN)) {
			this.setWorkEstimateNonSorFormList(
					estimateService.getPreviousEstimateByContractId(contId, orgId, estimateType));
			this.setWorkeReviseFlag(MainetConstants.MODE_EDIT);
			// this.setWorkeReviseFlag(this.getWorkEstimateNonSorFormList().get(0).getWorkeReviseFlag());
		} else if (estimateType.equals(MainetConstants.FlagB)) {
			this.setWorkEstimateBillQuantityList(
					estimateService.getPreviousEstimateByContractId(contId, orgId, estimateType));
			this.setWorkeReviseFlag(MainetConstants.MODE_EDIT);
			/// this.setWorkeReviseFlag(this.getWorkEstimateBillQuantityList().get(0).getWorkeReviseFlag());
		}
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
		taskAction.setReferenceId(getContractNo());
		taskAction.setPaymentMode(MainetConstants.FlagF);
		return taskAction;
	}

	public List<WorkEstimateMasterDto> getWorkEstimateNonSorFormList() {
		return workEstimateNonSorFormList;
	}

	public void setWorkEstimateNonSorFormList(List<WorkEstimateMasterDto> workEstimateNonSorFormList) {
		this.workEstimateNonSorFormList = workEstimateNonSorFormList;
	}

	public List<LookUp> getUnitLookUpList() {
		return unitLookUpList;
	}

	public void setUnitLookUpList(List<LookUp> unitLookUpList) {
		this.unitLookUpList = unitLookUpList;
	}

	public Long getNewWorkId() {
		return newWorkId;
	}

	public void setNewWorkId(Long newWorkId) {
		this.newWorkId = newWorkId;
	}

	public String getEstimateTypeId() {
		return estimateTypeId;
	}

	public void setEstimateTypeId(String estimateTypeId) {
		this.estimateTypeId = estimateTypeId;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public String getRequestFormFlag() {
		return requestFormFlag;
	}

	public void setRequestFormFlag(String requestFormFlag) {
		this.requestFormFlag = requestFormFlag;
	}

	public List<WorkEstimateMasterDto> getMeasurementsheetViewDataVariation() {
		return measurementsheetViewDataVariation;
	}

	public void setMeasurementsheetViewDataVariation(List<WorkEstimateMasterDto> measurementsheetViewDataVariation) {
		this.measurementsheetViewDataVariation = measurementsheetViewDataVariation;
	}

	public List<WorkEstimateMasterDto> getWorkEstimateBillQuantityList() {
		return workEstimateBillQuantityList;
	}

	public void setWorkEstimateBillQuantityList(List<WorkEstimateMasterDto> workEstimateBillQuantityList) {
		this.workEstimateBillQuantityList = workEstimateBillQuantityList;
	}

	public String getNewWorkCode() {
		return newWorkCode;
	}

	public void setNewWorkCode(String newWorkCode) {
		this.newWorkCode = newWorkCode;
	}

	public List<ContractAgreementSummaryDTO> getContractAgreementSummaryDTO() {
		return contractAgreementSummaryDTO;
	}

	public void setContractAgreementSummaryDTO(List<ContractAgreementSummaryDTO> contractAgreementSummaryDTO) {
		this.contractAgreementSummaryDTO = contractAgreementSummaryDTO;
	}

	public WorkEstimateMasterDto getWorkestimateMasterDto() {
		return workestimateMasterDto;
	}

	public void setWorkestimateMasterDto(WorkEstimateMasterDto workestimateMasterDto) {
		this.workestimateMasterDto = workestimateMasterDto;
	}

	public String getEsimateType() {
		return esimateType;
	}

	public void setEsimateType(String esimateType) {
		this.esimateType = esimateType;
	}

	public Long getRateAnalysisWorkId() {
		return rateAnalysisWorkId;
	}

	public void setRateAnalysisWorkId(Long rateAnalysisWorkId) {
		this.rateAnalysisWorkId = rateAnalysisWorkId;
	}

	public String getRemoveNonSorById() {
		return removeNonSorById;
	}

	public void setRemoveNonSorById(String removeNonSorById) {
		this.removeNonSorById = removeNonSorById;
	}

	public Long getNewContractId() {
		return newContractId;
	}

	public void setNewContractId(Long newContractId) {
		this.newContractId = newContractId;
	}

	public String getRemoveChildIds() {
		return removeChildIds;
	}

	public void setRemoveChildIds(String removeChildIds) {
		this.removeChildIds = removeChildIds;
	}

	public Long getNewVendorId() {
		return newVendorId;
	}

	public void setNewVendorId(Long newVendorId) {
		this.newVendorId = newVendorId;
	}

	public String getWorkeReviseFlag() {
		return workeReviseFlag;
	}

	public void setWorkeReviseFlag(String workeReviseFlag) {
		this.workeReviseFlag = workeReviseFlag;
	}

	public String getEstimateRadioFlag() {
		return estimateRadioFlag;
	}

	public void setEstimateRadioFlag(String estimateRadioFlag) {
		this.estimateRadioFlag = estimateRadioFlag;
	}

	public String getContractNo() {
		return ContractNo;
	}

	public void setContractNo(String contractNo) {
		ContractNo = contractNo;
	}

	public List<WorkOrderContractDetailsDto> getWorkOrderContractDetailsDto() {
		return workOrderContractDetailsDto;
	}

	public void setWorkOrderContractDetailsDto(List<WorkOrderContractDetailsDto> workOrderContractDetailsDto) {
		this.workOrderContractDetailsDto = workOrderContractDetailsDto;
	}

	public String getCpdModeHideSor() {
		return cpdModeHideSor;
	}

	public void setCpdModeHideSor(String cpdModeHideSor) {
		this.cpdModeHideSor = cpdModeHideSor;
	}

}
