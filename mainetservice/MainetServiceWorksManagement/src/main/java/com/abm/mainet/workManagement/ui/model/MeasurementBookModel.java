/**
 * 
 */
package com.abm.mainet.workManagement.ui.model;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.TbScrutinyLabels;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.workManagement.dto.MbOverHeadDetDto;
import com.abm.mainet.workManagement.dto.MeasurementBookCheckListDetailDto;
import com.abm.mainet.workManagement.dto.MeasurementBookCheckListDto;
import com.abm.mainet.workManagement.dto.MeasurementBookDetailsDto;
import com.abm.mainet.workManagement.dto.MeasurementBookLbhDto;
import com.abm.mainet.workManagement.dto.MeasurementBookMasterDto;
import com.abm.mainet.workManagement.dto.MeasurementBookTaxDetailsDto;
import com.abm.mainet.workManagement.dto.TenderMasterDto;
import com.abm.mainet.workManagement.dto.TenderWorkDto;
import com.abm.mainet.workManagement.dto.WmsLeadLiftMasterDto;
import com.abm.mainet.workManagement.dto.WmsMaterialMasterDto;
import com.abm.mainet.workManagement.dto.WmsProjectMasterDto;
import com.abm.mainet.workManagement.dto.WorkDefinitionDto;
import com.abm.mainet.workManagement.dto.WorkEstimOverHeadDetDto;
import com.abm.mainet.workManagement.dto.WorkEstimateMasterDto;
import com.abm.mainet.workManagement.dto.WorkEstimateMeasureDetailsDto;
import com.abm.mainet.workManagement.dto.WorkOrderContractDetailsDto;
import com.abm.mainet.workManagement.dto.WorkOrderDto;
import com.abm.mainet.workManagement.service.MeasurementBookLbhService;
import com.abm.mainet.workManagement.service.MeasurementBookService;
import com.abm.mainet.workManagement.service.MeasurementBookTaxDetailsService;
import com.abm.mainet.workManagement.service.TenderInitiationService;
import com.abm.mainet.workManagement.service.WorkEstimateService;

/**
 * @author Saiprasad.Vengurlekar
 *
 */

@Component
@Scope("session")
public class MeasurementBookModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;

	@Autowired
	MeasurementBookService mbService;

	@Autowired
	IFileUploadService fileUpload;

	@Autowired
	WorkEstimateService workEstimateService;

	@Autowired
	private TenderInitiationService tenderService;

	private MeasurementBookMasterDto mbMasDto = new MeasurementBookMasterDto();
	private List<WorkOrderDto> workOrderDtoList = new ArrayList<>();
	private WorkOrderDto workOrderDto = new WorkOrderDto();

	private List<WorkEstimateMasterDto> estimateMasDtoList = new ArrayList<>();
	private List<WorkEstimateMasterDto> savedEstimateDtoList = new ArrayList<>();
	private String saveMode;
	private List<WorkEstimateMeasureDetailsDto> measureDetailsList = new ArrayList<>();
	private List<MeasurementBookLbhDto> lbhDtosList = new ArrayList<>();

	private List<WorkEstimateMasterDto> rateAnalysisMaterialList = new ArrayList<>();
	private WorkEstimateMasterDto estimateMasterDto = new WorkEstimateMasterDto();
	private Map<String, String> ratePrifixMap = new HashMap<>();
	// multiple List for(All Rate Type) under single material data
	private List<WorkEstimateMasterDto> addAllRatetypeEntity = new ArrayList<>();
	private List<WmsMaterialMasterDto> rateList = new ArrayList<>();
	private List<WmsLeadLiftMasterDto> leadMasterList = new ArrayList<>();
	private List<WmsLeadLiftMasterDto> liftMasterList = new ArrayList<>();

	private List<MeasurementBookDetailsDto> mbDetRateDtoList = new ArrayList<>();

	private List<MeasurementBookDetailsDto> mbDetNonSorDtoList = new ArrayList<>();

	private String removeEnclosureFileById;
	private String removeMbTaxDetIds;
	private String removeOverHeadById;
	private List<Long> fileCountUpload;
	private List<DocumentDetailsVO> attachments = new ArrayList<>();
	private List<AttachDocs> attachList = new ArrayList<>();
	private Map<Long, List<String>> fileNames = new HashMap<>();
	private WorkEstimateMeasureDetailsDto measureDto = new WorkEstimateMeasureDetailsDto();
	private MeasurementBookCheckListDto checkListDto = new MeasurementBookCheckListDto();
	private String docDescription;
	private List<TbScrutinyLabels> classACheckList = new ArrayList<>();
	private List<TbScrutinyLabels> checkListColumnBeams = new ArrayList<>();
	private List<TbScrutinyLabels> checkListBrickWork = new ArrayList<>();
	private List<TbScrutinyLabels> checkListPlastering = new ArrayList<>();
	private List<TbScrutinyLabels> checkListWaterSupply = new ArrayList<>();
	private Long measurementId;
	private String uploadMode;
	private BigDecimal overHeadAmt;
	private String removeSorIds;
	private String removeNonSorIds;
	private MeasurementBookLbhDto  measurementBookLbhDto = new MeasurementBookLbhDto();

	public List<AttachDocs> getAttachList() {
		return attachList;
	}

	public void setAttachList(List<AttachDocs> attachList) {
		this.attachList = attachList;
	}

	private List<MeasurementBookTaxDetailsDto> mbTaxDetailsDto = new ArrayList<>();
	private List<LookUp> valueTypeList = new ArrayList<LookUp>();
	private List<LookUp> taxList = new ArrayList<LookUp>();
	private List<Long> deletedEstimate = new ArrayList<>();
	private List<Long> deletedLbh = new ArrayList<>();
	private List<Long> deletedRate = new ArrayList<>();
	private List<DocumentDetailsVO> attachDocs = new ArrayList<>();

	private String mbReferenceNo = null;

	private String totalMbAmount;

	private BigDecimal mbTotalAmt;

	private String requestFormFlag;

	private List<WorkEstimateMasterDto> directEstimateList = new ArrayList<>();

	private String cpdModeCatSor;

	private List<Employee> employeeList = new ArrayList<>();

	private Long deptId;
	
	private String newWorkCode;

	private List<WorkEstimateMasterDto> nonSorEstimateDtoList = new ArrayList<>();

	private Long parentLbhEstimatedId;
	private List<AttachDocs> attachDocsList = new ArrayList<>();
	private List<MeasurementBookMasterDto> mbList = new ArrayList<>();
	private List<WorkOrderContractDetailsDto> vendorDetail = new ArrayList<>();
	private List<WorkDefinitionDto> workList = new ArrayList<>();
	private List<LookUp> checkList = new ArrayList<LookUp>();
	private List<TbLocationMas> locList = new ArrayList<>();
	private List<MeasurementBookCheckListDetailDto> checkListDetList = new ArrayList<>();
	private List<Long> checkListIds = new ArrayList<>();
	private List<WmsProjectMasterDto> projectMasterList = new ArrayList<>();

	private List<LookUp> overHeadPercentLookUp = new ArrayList<>();
	private List<WorkEstimOverHeadDetDto> estimOverHeadDetDto = new ArrayList<>();
	private List<MbOverHeadDetDto> mbeOverHeadDetDtoList = new ArrayList<>();
	private List<LookUp> overHeadLookUp = new ArrayList<>();
	private List<WorkDefinitionDto> workDefinitionDto = new ArrayList<WorkDefinitionDto>();
	private Long newWorkId;
	private String preMbStatus;
	private String uploadFileName;
	
	public Map<Long, List<String>> getFileNames() {
		return fileNames;
	}

	public void setFileNames(Map<Long, List<String>> fileNames) {
		this.fileNames = fileNames;
	}

	public List<DocumentDetailsVO> getAttachDocs() {
		return attachDocs;
	}

	public void setAttachDocs(List<DocumentDetailsVO> attachDocs) {
		this.attachDocs = attachDocs;
	}

	public List<WorkEstimateMasterDto> getNonSorEstimateDtoList() {
		return nonSorEstimateDtoList;
	}

	public void setNonSorEstimateDtoList(List<WorkEstimateMasterDto> nonSorEstimateDtoList) {
		this.nonSorEstimateDtoList = nonSorEstimateDtoList;
	}

	public List<LookUp> getTaxList() {
		return taxList;
	}

	public void setTaxList(List<LookUp> taxList) {
		this.taxList = taxList;
	}

	public List<LookUp> getValueTypeList() {
		return valueTypeList;
	}

	public void setValueTypeList(List<LookUp> valueTypeList) {
		this.valueTypeList = valueTypeList;
	}

	public List<Long> getDeletedRate() {
		return deletedRate;
	}

	public void setDeletedRate(List<Long> deletedRate) {
		this.deletedRate = deletedRate;
	}

	public List<Long> getDeletedLbh() {
		return deletedLbh;
	}

	public void setDeletedLbh(List<Long> deletedLbh) {
		this.deletedLbh = deletedLbh;
	}

	public MeasurementBookMasterDto getMbMasDto() {
		return mbMasDto;
	}

	public void setMbMasDto(MeasurementBookMasterDto mbMasDto) {
		this.mbMasDto = mbMasDto;
	}

	public List<WorkOrderDto> getWorkOrderDtoList() {
		return workOrderDtoList;
	}

	public void setWorkOrderDtoList(List<WorkOrderDto> workOrderDtoList) {
		this.workOrderDtoList = workOrderDtoList;
	}

	public WorkOrderDto getWorkOrderDto() {
		return workOrderDto;
	}

	public void setWorkOrderDto(WorkOrderDto workOrderDto) {
		this.workOrderDto = workOrderDto;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public String getRemoveEnclosureFileById() {
		return removeEnclosureFileById;
	}

	public void setRemoveEnclosureFileById(String removeEnclosureFileById) {
		this.removeEnclosureFileById = removeEnclosureFileById;
	}

	public List<Long> getFileCountUpload() {
		return fileCountUpload;
	}

	public void setFileCountUpload(List<Long> fileCountUpload) {
		this.fileCountUpload = fileCountUpload;
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

	public List<WorkEstimateMasterDto> getEstimateMasDtoList() {
		return estimateMasDtoList;
	}

	public void setEstimateMasDtoList(List<WorkEstimateMasterDto> estimateMasDtoList) {
		this.estimateMasDtoList = estimateMasDtoList;
	}

	public List<WorkEstimateMeasureDetailsDto> getMeasureDetailsList() {
		return measureDetailsList;
	}

	public void setMeasureDetailsList(List<WorkEstimateMeasureDetailsDto> measureDetailsList) {
		this.measureDetailsList = measureDetailsList;
	}

	public List<MeasurementBookLbhDto> getLbhDtosList() {
		return lbhDtosList;
	}

	public void setLbhDtosList(List<MeasurementBookLbhDto> lbhDtosList) {
		this.lbhDtosList = lbhDtosList;
	}

	public WorkEstimateMasterDto getEstimateMasterDto() {
		return estimateMasterDto;
	}

	public void setEstimateMasterDto(WorkEstimateMasterDto estimateMasterDto) {
		this.estimateMasterDto = estimateMasterDto;
	}

	public List<WorkEstimateMasterDto> getRateAnalysisMaterialList() {
		return rateAnalysisMaterialList;
	}

	public void setRateAnalysisMaterialList(List<WorkEstimateMasterDto> rateAnalysisMaterialList) {
		this.rateAnalysisMaterialList = rateAnalysisMaterialList;
	}

	public List<WorkEstimateMasterDto> getSavedEstimateDtoList() {
		return savedEstimateDtoList;
	}

	public void setSavedEstimateDtoList(List<WorkEstimateMasterDto> savedEstimateDtoList) {
		this.savedEstimateDtoList = savedEstimateDtoList;
	}

	public Map<String, String> getRatePrifixMap() {
		return ratePrifixMap;
	}

	public void setRatePrifixMap(Map<String, String> ratePrifixMap) {
		this.ratePrifixMap = ratePrifixMap;
	}

	public List<WmsLeadLiftMasterDto> getLeadMasterList() {
		return leadMasterList;
	}

	public void setLeadMasterList(List<WmsLeadLiftMasterDto> leadMasterList) {
		this.leadMasterList = leadMasterList;
	}

	public List<WmsLeadLiftMasterDto> getLiftMasterList() {
		return liftMasterList;
	}

	public void setLiftMasterList(List<WmsLeadLiftMasterDto> liftMasterList) {
		this.liftMasterList = liftMasterList;
	}

	public List<MeasurementBookDetailsDto> getMbDetRateDtoList() {
		return mbDetRateDtoList;
	}

	public void setMbDetRateDtoList(List<MeasurementBookDetailsDto> mbDetRateDtoList) {
		this.mbDetRateDtoList = mbDetRateDtoList;
	}

	public List<WmsMaterialMasterDto> getRateList() {
		return rateList;
	}

	public void setRateList(List<WmsMaterialMasterDto> rateList) {
		this.rateList = rateList;
	}

	public List<Long> getDeletedEstimate() {
		return deletedEstimate;
	}

	public void setDeletedEstimate(List<Long> deletedEstimate) {
		this.deletedEstimate = deletedEstimate;
	}

	public List<WorkEstimateMasterDto> getAddAllRatetypeEntity() {
		return addAllRatetypeEntity;
	}

	public void setAddAllRatetypeEntity(List<WorkEstimateMasterDto> addAllRatetypeEntity) {
		this.addAllRatetypeEntity = addAllRatetypeEntity;
	}

	public String getMbReferenceNo() {
		return mbReferenceNo;
	}

	public void setMbReferenceNo(String mbReferenceNo) {
		this.mbReferenceNo = mbReferenceNo;
	}

	public List<MeasurementBookTaxDetailsDto> getMbTaxDetailsDto() {
		return mbTaxDetailsDto;
	}

	public void setMbTaxDetailsDto(List<MeasurementBookTaxDetailsDto> mbTaxDetailsDto) {
		this.mbTaxDetailsDto = mbTaxDetailsDto;
	}

	public String getRemoveMbTaxDetIds() {
		return removeMbTaxDetIds;
	}

	public void setRemoveMbTaxDetIds(String removeMbTaxDetIds) {
		this.removeMbTaxDetIds = removeMbTaxDetIds;
	}

	public String getTotalMbAmount() {
		return totalMbAmount;
	}

	public void setTotalMbAmount(String totalMbAmount) {
		this.totalMbAmount = totalMbAmount;
	}

	public List<MeasurementBookDetailsDto> getMbDetNonSorDtoList() {
		return mbDetNonSorDtoList;
	}

	public void setMbDetNonSorDtoList(List<MeasurementBookDetailsDto> mbDetNonSorDtoList) {
		this.mbDetNonSorDtoList = mbDetNonSorDtoList;
	}

	public String getRequestFormFlag() {
		return requestFormFlag;
	}

	public void setRequestFormFlag(String requestFormFlag) {
		this.requestFormFlag = requestFormFlag;
	}

	public List<WorkEstimateMasterDto> getDirectEstimateList() {
		return directEstimateList;
	}

	public void setDirectEstimateList(List<WorkEstimateMasterDto> directEstimateList) {
		this.directEstimateList = directEstimateList;
	}

	public String getCpdModeCatSor() {
		return cpdModeCatSor;
	}

	public void setCpdModeCatSor(String cpdModeCatSor) {
		this.cpdModeCatSor = cpdModeCatSor;
	}

	public List<Employee> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<Employee> employeeList) {
		this.employeeList = employeeList;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Long getParentLbhEstimatedId() {
		return parentLbhEstimatedId;
	}

	public void setParentLbhEstimatedId(Long parentLbhEstimatedId) {
		this.parentLbhEstimatedId = parentLbhEstimatedId;
	}

	public List<WmsMaterialMasterDto> getLeadRateList() {
		List<WmsMaterialMasterDto> list = new ArrayList<>();
		WmsMaterialMasterDto dto = null;
		for (WmsLeadLiftMasterDto obj : getLeadMasterList()) {
			dto = new WmsMaterialMasterDto();
			dto.setMaId(obj.getLeLiId());
			dto.setUnitName(obj.getUnitName());
			dto.setMaItemUnit(obj.getLeLiUnit());
			dto.setMaRate(obj.getLeLiRate());
			dto.setMaDescription(MainetConstants.WorksManagement.From_Lead_Lift + obj.getLeLiFrom()
					+ MainetConstants.WorksManagement.To_Lead_Lift + obj.getLeLiTo());
			list.add(dto);
		}
		return list;
	}

	public List<WmsMaterialMasterDto> getLiftRateList() {
		List<WmsMaterialMasterDto> list = new ArrayList<>();
		WmsMaterialMasterDto dto = null;
		for (WmsLeadLiftMasterDto obj : getLiftMasterList()) {
			dto = new WmsMaterialMasterDto();
			dto.setMaId(obj.getLeLiId());
			dto.setUnitName(obj.getUnitName());
			dto.setMaItemUnit(obj.getLeLiUnit());
			dto.setMaRate(obj.getLeLiRate());
			dto.setMaDescription(MainetConstants.WorksManagement.From_Lead_Lift + obj.getLeLiFrom()
					+ MainetConstants.WorksManagement.To_Lead_Lift + obj.getLeLiTo());
			list.add(dto);
		}
		return list;
	}

	public void prepareAllMasterTypeList() {

		List<LookUp> rateType = getLevelData(MainetConstants.WorksManagement.MTY);
		rateType.forEach(lookup -> {
			ratePrifixMap.put(lookup.getLookUpCode(), lookup.getLookUpDesc());
		});
		ratePrifixMap.put(MainetConstants.FlagR, MainetConstants.WorksManagement.ROYALITY);
		ratePrifixMap.put(MainetConstants.WorksManagement.L_E, MainetConstants.LEAD);
		ratePrifixMap.put(MainetConstants.WorksManagement.L_I, MainetConstants.LIFT);
	}

	public void saveMbEstimationData() {
		getDeletedEstimate().clear();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		Date newDate = new Date();
		List<MeasurementBookDetailsDto> mbDetdtoList = new ArrayList<>();
		MeasurementBookDetailsDto mbDetDto;
		MeasurementBookMasterDto mbMasterDto = getMbMasDto();
		if (mbMasterDto.getCreatedBy() == null) {
			Long contractId = getWorkOrderDto().getContractMastDTO().getContId();
			TenderWorkDto tenderWorkDto = tenderService.findWorkByWorkId(contractId);

			TenderMasterDto tenderMasterDto = tenderService.getPreparedTenderDetails(tenderWorkDto.getTndId());
			mbMasterDto.setWorkMbNo(mbService.generateAndUpdateMbNumber(getWorkOrderDto().getWorkId(),
					UserSession.getCurrent().getOrganisation().getOrgid(), tenderMasterDto.getDeptId()));
			mbMasterDto.setWorkOrId(getWorkOrderDto().getWorkId());
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL) && (UserSession.getCurrent().getOrganisation().getDefaultStatus().equals("Y"))) {
				mbMasterDto.setMbStatus(MainetConstants.FlagC);
			} else {
				mbMasterDto.setMbStatus(MainetConstants.FlagD);
			}
			mbMasterDto.setCreatedBy(createdBy);
			mbMasterDto.setCreatedDate(newDate);
			mbMasterDto.setOrgId(orgId);
			mbMasterDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			for (WorkEstimateMasterDto workEstimateMasterDto : getEstimateMasDtoList()) {
				if (workEstimateMasterDto.isCheckBox()) {
					mbDetDto = new MeasurementBookDetailsDto();
					mbDetDto.setWorkEstimateMaster(workEstimateMasterDto);
					mbDetDto.setCreatedBy(createdBy);
					mbDetDto.setCreatedDate(newDate);
					mbDetDto.setOrgId(orgId);
					mbDetDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
					mbDetdtoList.add(mbDetDto);
				}
			}
			mbMasterDto.setMbDetails(mbDetdtoList);
		} else {
			mbMasterDto.setWorkOrId(getWorkOrderDto().getWorkId());
			mbMasterDto.setMbStatus(MainetConstants.FlagD);
			mbMasterDto.setUpdatedBy(createdBy);
			mbMasterDto.setUpdatedDate(newDate);
			mbMasterDto.setOrgId(orgId);
			mbMasterDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			for (WorkEstimateMasterDto workEstimateMasterDto : getEstimateMasDtoList()) {
				if (workEstimateMasterDto.isCheckBox() && workEstimateMasterDto.getMbDetId() == null) {
					mbDetDto = new MeasurementBookDetailsDto();
					mbDetDto.setWorkEstimateMaster(workEstimateMasterDto);
					mbDetDto.setCreatedBy(createdBy);
					mbDetDto.setCreatedDate(newDate);
					mbDetDto.setOrgId(orgId);
					mbDetDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
					mbDetdtoList.add(mbDetDto);
				}
				if (!workEstimateMasterDto.isCheckBox() && workEstimateMasterDto.getMbDetId() != null) {
					getDeletedEstimate().add(workEstimateMasterDto.getMbDetId());
				}
			}
			mbMasterDto.setMbDetails(mbDetdtoList);
		}
		mbService.saveAndUpdateMB(mbMasterDto, getDeletedEstimate());
	}

	public void prepareMbEnclosuersData(String mbNumber) {
		RequestDTO requestDTO = new RequestDTO();
		requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		requestDTO.setStatus(MainetConstants.FlagA);
		requestDTO.setDepartmentName(MainetConstants.WorksManagement.WORKS_MANAGEMENT);
		requestDTO.setIdfId(mbNumber);
		requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		List<DocumentDetailsVO> dto = getAttachments();
		setAttachments(fileUpload.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
		int i = 0;
		for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
			getAttachments().get(i).setDoc_DESC_ENGL(dto.get(entry.getKey().intValue()).getDoc_DESC_ENGL());
			i++;
		}

		fileUpload.doMasterFileUpload(getAttachments(), requestDTO);
		List<Long> enclosureRemoveById = null;
		String fileId = getRemoveEnclosureFileById();
		if (fileId != null && !fileId.isEmpty()) {
			enclosureRemoveById = new ArrayList<>();
			String fileArray[] = fileId.split(MainetConstants.operator.COMMA);
			for (String fields : fileArray) {
				enclosureRemoveById.add(Long.valueOf(fields));
			}
		}
		if (enclosureRemoveById != null && !enclosureRemoveById.isEmpty()) {
			mbService.deleteEnclosureFileById(enclosureRemoveById, UserSession.getCurrent().getEmployee().getEmpId());
		}
	}

	public boolean saveLbhForm() {
		Date todayDate = new Date();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long empId = UserSession.getCurrent().getEmployee().getEmpId();
		Long id=null;	
		List<MeasurementBookLbhDto> updatedMbLbhDto = getUpdatedMbLbhDto(getLbhDtosList());
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
			if(cpdModeCatSor.equals("Y")) {
				List<MeasurementBookLbhDto> updatedList = new ArrayList<>();
				for (MeasurementBookLbhDto dto : updatedMbLbhDto) {
					if (dto.getMbType() != null) {
						String[] active = dto.getMbType().split(",");
						if (active.length > 0) {
							int lastIndex = active.length - 1;
							if (!active[lastIndex].isEmpty()) {
								dto.setMbType(active[lastIndex]);
							}
						}
						updatedList.add(dto);

					}
				}

				updatedMbLbhDto.clear();

				updatedMbLbhDto.addAll(updatedList);
					
				}
			}
		if (CollectionUtils.isNotEmpty(directEstimateList)) {
	    id=directEstimateList.get(0).getMbDetId();
		}
		if (updatedMbLbhDto != null && !updatedMbLbhDto.isEmpty()) {
			for (MeasurementBookLbhDto mbLbhDto : updatedMbLbhDto) {
				if (mbLbhDto.getCreatedBy() == null) {
					mbLbhDto.setOrgId(orgId);
					mbLbhDto.setCreatedBy(empId);
					mbLbhDto.setCreatedDate(todayDate);
					mbLbhDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
					mbLbhDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
					if(id!=null)
					mbLbhDto.setMbdId(id);

				} else {
					mbLbhDto.setUpdatedBy(empId);
					mbLbhDto.setUpdatedDate(todayDate);
					mbLbhDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
				}
			}

		}
		
		List<Long> removeIds=null;
		String withId = getRemoveSorIds();
		if (withId != null && !withId.isEmpty()) {
		    removeIds = new ArrayList<>();
			String array1[] = withId.split(MainetConstants.operator.COMMA);
			for (String ids : array1) {
				removeIds.add(Long.valueOf(ids));
			}
		}
		ApplicationContextProvider.getApplicationContext().getBean(MeasurementBookLbhService.class)
				.saveUpdateMbLbhList(updatedMbLbhDto, removeIds, getParentLbhEstimatedId());
		
		return true;
	}

	public void prepareMeasureDtailsDocData() {
		getAttachDocs().clear();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		List<AttachDocs> removedDocs = new ArrayList<>();
		List<DocumentDetailsVO> images = new ArrayList<>();
		setAttachDocs(fileUpload.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
		RequestDTO requestDTO = new RequestDTO();
		requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		requestDTO.setStatus(MainetConstants.FlagA);
		requestDTO.setDepartmentName(MainetConstants.WorksManagement.WORKS_MANAGEMENT);
		if (getEstimateMasterDto().getEstimateType().equals(MainetConstants.FlagU)) {
			requestDTO.setIdfId(getMbMasDto().getWorkMbNo() + MainetConstants.WINDOWS_SLASH + orgId
					+ MainetConstants.WINDOWS_SLASH + MainetConstants.FlagU + getEstimateMasterDto().getMbDetId());
		} else {
			requestDTO.setIdfId(getMbMasDto().getWorkMbNo() + MainetConstants.WINDOWS_SLASH + orgId
					+ MainetConstants.WINDOWS_SLASH + MainetConstants.FlagS + getMeasureDto().getMeMentId());
		}
		requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		setAttachDocs(fileUpload.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));

		for (DocumentDetailsVO doc : getAttachDocs()) {
			boolean flag = false;
			if (!getAttachList().isEmpty()) {
				for (AttachDocs docs : getAttachList()) {
					if (doc.getDocumentName().equals(docs.getAttFname())) {
						flag = true;
						break;
					}
				}
				if (!flag) {
					doc.setDoc_DESC_ENGL(docDescription);
					images.add(doc);
				}
			} else {
				doc.setDoc_DESC_ENGL(docDescription);
				images.add(doc);
			}
		}
		fileUpload.doMasterFileUpload(images, requestDTO);
		List<String> filenames = new ArrayList<>();
		for (DocumentDetailsVO documentDetailsVO : getAttachDocs()) {
			filenames.add(documentDetailsVO.getDocumentName());
		}
		for (AttachDocs docs : getAttachList()) {
			if (!filenames.isEmpty()) {
				if (!filenames.contains(docs.getAttFname())) {
					removedDocs.add(docs);
				}
			} else {
				removedDocs.add(docs);
			}
		}
		if (!removedDocs.isEmpty()) {
			mbService.deleteMbDocuments(removedDocs, UserSession.getCurrent().getEmployee().getEmpId());
		}

	}

	public List<MeasurementBookLbhDto> getUpdatedMbLbhDto(List<MeasurementBookLbhDto> mbLbhDtoList) {
		getDeletedLbh().clear();
		List<MeasurementBookLbhDto> updatedMbLbhDto = new ArrayList<>();
		for (MeasurementBookLbhDto measurementBookLbhDto : mbLbhDtoList) {
			if (measurementBookLbhDto.getMbLbhId() != null) {
				if (measurementBookLbhDto.getMbTotal().compareTo(new BigDecimal(0)) == 0
						|| measurementBookLbhDto.getMbTotal() == null) {
					getDeletedLbh().add(measurementBookLbhDto.getMbLbhId());
				} else {
					updatedMbLbhDto.add(measurementBookLbhDto);
				}
			} else {
				if (measurementBookLbhDto.getMbTotal() != null
						&& measurementBookLbhDto.getMbTotal().compareTo(new BigDecimal(0)) != 0) {
					updatedMbLbhDto.add(measurementBookLbhDto);
				}
			}

		}
		return updatedMbLbhDto;
	}

	public void saveMbRateEntity(List<MeasurementBookDetailsDto> mbRateEntity) {

		Date todayDate = new Date();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long empId = UserSession.getCurrent().getEmployee().getEmpId();
		getDeletedRate().clear();
		List<MeasurementBookDetailsDto> mbRateDtoList = getUpdatedMbDetailsDto(mbRateEntity);
		for (MeasurementBookDetailsDto mbDetDto : mbRateDtoList) {
			mbDetDto.setOrgId(orgId);
			if (mbDetDto.getCreatedBy() == null) {
				mbDetDto.setCreatedBy(empId);
				mbDetDto.setCreatedDate(todayDate);
				mbDetDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
				mbDetDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			} else {
				mbDetDto.setUpdatedBy(empId);
				mbDetDto.setUpdatedDate(todayDate);
				mbDetDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
			}
		}
		mbService.saveMbRateDetails(mbRateDtoList, deletedRate);

	}

	public void saveMbNonSorEntity(List<MeasurementBookDetailsDto> mbRateEntity) {
		Date todayDate = new Date();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long empId = UserSession.getCurrent().getEmployee().getEmpId();
		getDeletedRate().clear();
		List<MeasurementBookDetailsDto> mbRateDtoList = getUpdatedMbDetailsDto(mbRateEntity);
		for (MeasurementBookDetailsDto mbDetDto : mbRateDtoList) {
			mbDetDto.setOrgId(orgId);
			if (mbDetDto.getCreatedBy() == null) {
				mbDetDto.setCreatedBy(empId);
				mbDetDto.setCreatedDate(todayDate);
				mbDetDto.setMbId(mbMasDto.getWorkMbId());
				mbDetDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
				mbDetDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			} else {
				mbDetDto.setUpdatedBy(empId);
				mbDetDto.setUpdatedDate(todayDate);
				mbDetDto.setMbId(mbMasDto.getWorkMbId());
				mbDetDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
			}
		}
		List<Long> removeIds=null;
		String withId = getRemoveNonSorIds();
		if (withId != null && !withId.isEmpty()) {
		    removeIds = new ArrayList<>();
			String array1[] = withId.split(MainetConstants.operator.COMMA);
			for (String id : array1) {
				removeIds.add(Long.valueOf(id));
			}
		}
		mbService.saveMbNonSorDetails(mbRateDtoList);
		mbService.updateAmountNonSor(mbRateDtoList,removeIds);
	}

	public List<MeasurementBookDetailsDto> getUpdatedMbDetailsDto(List<MeasurementBookDetailsDto> mbRateDetList) {
		getDeletedRate().clear();
		List<MeasurementBookDetailsDto> updatedMbRateDto = new ArrayList<>();
		for (MeasurementBookDetailsDto mbDetailsDto : mbRateDetList) {

			if (mbDetailsDto.getMbdId() != null) {
				if (mbDetailsDto.getWorkActualAmt() == null) {
					getDeletedRate().add(mbDetailsDto.getMbdId());
				} else {
					updatedMbRateDto.add(mbDetailsDto);
				}

			} else {
				if (mbDetailsDto.getWorkActualAmt() != null) {
					updatedMbRateDto.add(mbDetailsDto);
				}

			}
		}
		return updatedMbRateDto;
	}

	public void saveMbTaxDetailsForm() {
		Date todayDate = new Date();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long empId = UserSession.getCurrent().getEmployee().getEmpId();
		for (MeasurementBookTaxDetailsDto mbTaxDto : mbTaxDetailsDto) {
			if (mbTaxDto.getCreatedBy() == null) {
				mbTaxDto.setMbId(mbMasDto.getWorkMbId());
				mbTaxDto.setOrgId(orgId);
				mbTaxDto.setCreatedBy(empId);
				mbTaxDto.setCreatedDate(todayDate);
				mbTaxDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
				mbTaxDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());

			} else {
				mbTaxDto.setUpdatedBy(empId);
				mbTaxDto.setUpdatedDate(todayDate);
				mbTaxDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
			}
		}

		List<Long> removeMbTaxDetIds = null;
		String fileId = getRemoveMbTaxDetIds();
		if (fileId != null && !fileId.isEmpty()) {
			removeMbTaxDetIds = new ArrayList<>();
			String fileArray[] = fileId.split(MainetConstants.operator.COMMA);
			for (String fields : fileArray) {
				removeMbTaxDetIds.add(Long.valueOf(fields));
			}
		}

		ApplicationContextProvider.getApplicationContext().getBean(MeasurementBookTaxDetailsService.class)
				.saveUpdateMbTaxDetails(mbTaxDetailsDto, removeMbTaxDetIds);

	}

	public void getdataOfUploadedImage() {
		getFileNames().clear();
		List<String> fileNameList = null;
		Long count = 0L;
		Map<Long, List<String>> fileNames = new HashMap<>();
		if ((FileUploadUtility.getCurrent().getFileMap() != null)
				&& !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				fileNameList = new ArrayList<>();
				for (final File file : entry.getValue()) {
					String fileName = null;

					try {
						final String path = file.getPath().replace(MainetConstants.DOUBLE_BACK_SLACE,
								MainetConstants.operator.FORWARD_SLACE);
						fileName = path.replace(Filepaths.getfilepath(), StringUtils.EMPTY);
					} catch (final Exception e) {
						e.printStackTrace();
					}

					fileNameList.add(fileName);
				}
				fileNames.put(count, fileNameList);
				count++;
			}
		}
		setFileNames(fileNames);
	}

	public void saveMbCheckListData() {
		Date todayDate = new Date();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long empId = UserSession.getCurrent().getEmployee().getEmpId();
		getCheckListDetList().clear();
		getCheckListIds().clear();
		MeasurementBookCheckListDto dto = getCheckListDto();
		if (dto.getMbcId() != null) {
			dto.setUpdatedBy(empId);
			dto.setUpdatedDate(todayDate);
			dto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
		} else {
			dto.setMbId(mbMasDto.getWorkMbId());
			dto.setOrgId(orgId);
			dto.setCreatedBy(empId);
			dto.setCreatedDate(todayDate);
			dto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			dto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
		}

		prepareCheckListData(dto.getClassACheckList(), dto);
		prepareCheckListData(dto.getCheckListBrickWork(), dto);
		prepareCheckListData(dto.getCheckListColumnBeams(), dto);
		prepareCheckListData(dto.getCheckListPlastering(), dto);
		prepareCheckListData(dto.getCheckListWaterSupply(), dto);

		dto.setMbChkListDetails(getCheckListDetList());
		mbService.saveAndUpdateMBCheckList(dto, getCheckListIds());

	}

	public void prepareCheckListData(List<TbScrutinyLabels> labelsList, MeasurementBookCheckListDto dto) {
		Date todayDate = new Date();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long empId = UserSession.getCurrent().getEmployee().getEmpId();
		MeasurementBookCheckListDetailDto chkDetDto = null;
		for (TbScrutinyLabels labDto : labelsList) {
			if (!labDto.getSlValidationText().isEmpty() && labDto.getComN5() == null) {
				chkDetDto = new MeasurementBookCheckListDetailDto();
				BeanUtils.copyProperties(labDto, chkDetDto);
				chkDetDto.setMbcdValue(labDto.getSlValidationText());
				chkDetDto.setCreatedBy(empId);
				chkDetDto.setCreatedDate(todayDate);
				chkDetDto.setOrgId(orgId);
				chkDetDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
				getCheckListDetList().add(chkDetDto);
			}
			if (labDto.getSlValidationText().isEmpty() && labDto.getComN5() != null) {
				getCheckListIds().add(labDto.getComN5());
			}
			if (!labDto.getSlValidationText().isEmpty() && labDto.getComN5() != null) {
				for (MeasurementBookCheckListDetailDto checkDetDto : dto.getMbChkListDetails()) {
					if (checkDetDto.getMbcdId().longValue() == labDto.getComN5().longValue()) {
						checkDetDto.setUpdatedBy(empId);
						checkDetDto.setUpdatedDate(todayDate);
						checkDetDto.setMbcdValue(labDto.getSlValidationText());
						checkDetDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
						getCheckListDetList().add(checkDetDto);
						break;
					}
				}

			}
		}
	}

	public void prepareOverHeadData(List<MbOverHeadDetDto> headDetDtos, MeasurementBookModel model) {

		List<Long> overHeadRemoveById = null;
		String fileId = getRemoveOverHeadById();
		if (fileId != null && !fileId.isEmpty()) {
			overHeadRemoveById = new ArrayList<>();
			String fileArray[] = fileId.split(MainetConstants.operator.COMMA);
			for (String fields : fileArray) {
				overHeadRemoveById.add(Long.valueOf(fields));
			}
		}
		Date todayDate = new Date();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long empId = UserSession.getCurrent().getEmployee().getEmpId();

		BigDecimal overheadAmt = new BigDecimal(0);
		BigDecimal totalMbAmount = new BigDecimal(0);
		if ((model.getTotalMbOverheadValue().compareTo(model.getPreviousMbOverHeadAmt())) >= 0) {
			overheadAmt = model.getTotalMbOverheadValue().subtract(model.getPreviousMbOverHeadAmt());
			totalMbAmount = model.getOverHeadAmt().add(overheadAmt);
		} else {
			overheadAmt = model.getPreviousMbOverHeadAmt().subtract(model.getTotalMbOverheadValue());
			totalMbAmount = model.getOverHeadAmt().subtract(overheadAmt);
		}

		List<MbOverHeadDetDto> overheadDtoList = new ArrayList<MbOverHeadDetDto>();
		for (MbOverHeadDetDto measureDetailsDto : getMbeOverHeadDetDtoList()) {
			measureDetailsDto.setOrgId(orgId);

			if (measureDetailsDto.getMbOvhId() == null) {

				measureDetailsDto.setCreatedBy(empId);
				measureDetailsDto.setWorkId(this.getNewWorkId());
				measureDetailsDto.setCreatedDate(todayDate);
				measureDetailsDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());

			} else {

				measureDetailsDto.setWorkId(this.getNewWorkId());

				measureDetailsDto.setUpdatedBy(empId);
				measureDetailsDto.setUpdatedDate(todayDate);
				measureDetailsDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
			}
			overheadDtoList.add(measureDetailsDto);
		}
		mbService.saveOverHeadList(overheadDtoList, overHeadRemoveById, totalMbAmount,
				model.getMbMasDto().getWorkMbId());
		setRemoveOverHeadById(null);
	}

	public WorkEstimateMeasureDetailsDto getMeasureDto() {
		return measureDto;
	}

	public void setMeasureDto(WorkEstimateMeasureDetailsDto measureDto) {
		this.measureDto = measureDto;
	}

	public String getDocDescription() {
		return docDescription;
	}

	public void setDocDescription(String docDescription) {
		this.docDescription = docDescription;
	}

	public List<MeasurementBookMasterDto> getMbList() {
		return mbList;
	}

	public void setMbList(List<MeasurementBookMasterDto> mbList) {
		this.mbList = mbList;
	}

	public List<WorkOrderContractDetailsDto> getVendorDetail() {
		return vendorDetail;
	}

	public void setVendorDetail(List<WorkOrderContractDetailsDto> vendorDetail) {
		this.vendorDetail = vendorDetail;
	}

	public List<WorkDefinitionDto> getWorkList() {
		return workList;
	}

	public void setWorkList(List<WorkDefinitionDto> workList) {
		this.workList = workList;
	}

	public List<LookUp> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<LookUp> checkList) {
		this.checkList = checkList;
	}

	public List<TbLocationMas> getLocList() {
		return locList;
	}

	public void setLocList(List<TbLocationMas> locList) {
		this.locList = locList;
	}

	public MeasurementBookCheckListDto getCheckListDto() {
		return checkListDto;
	}

	public void setCheckListDto(MeasurementBookCheckListDto checkListDto) {
		this.checkListDto = checkListDto;
	}

	public List<TbScrutinyLabels> getClassACheckList() {
		return classACheckList;
	}

	public void setClassACheckList(List<TbScrutinyLabels> classACheckList) {
		this.classACheckList = classACheckList;
	}

	public List<TbScrutinyLabels> getCheckListColumnBeams() {
		return checkListColumnBeams;
	}

	public void setCheckListColumnBeams(List<TbScrutinyLabels> checkListColumnBeams) {
		this.checkListColumnBeams = checkListColumnBeams;
	}

	public List<TbScrutinyLabels> getCheckListBrickWork() {
		return checkListBrickWork;
	}

	public void setCheckListBrickWork(List<TbScrutinyLabels> checkListBrickWork) {
		this.checkListBrickWork = checkListBrickWork;
	}

	public List<TbScrutinyLabels> getCheckListPlastering() {
		return checkListPlastering;
	}

	public void setCheckListPlastering(List<TbScrutinyLabels> checkListPlastering) {
		this.checkListPlastering = checkListPlastering;
	}

	public List<TbScrutinyLabels> getCheckListWaterSupply() {
		return checkListWaterSupply;
	}

	public void setCheckListWaterSupply(List<TbScrutinyLabels> checkListWaterSupply) {
		this.checkListWaterSupply = checkListWaterSupply;
	}

	public Long getMeasurementId() {
		return measurementId;
	}

	public void setMeasurementId(Long measurementId) {
		this.measurementId = measurementId;
	}

	public String getUploadMode() {
		return uploadMode;
	}

	public void setUploadMode(String uploadMode) {
		this.uploadMode = uploadMode;
	}

	public List<MeasurementBookCheckListDetailDto> getCheckListDetList() {
		return checkListDetList;
	}

	public void setCheckListDetList(List<MeasurementBookCheckListDetailDto> checkListDetList) {
		this.checkListDetList = checkListDetList;
	}

	public List<Long> getCheckListIds() {
		return checkListIds;
	}

	public void setCheckListIds(List<Long> checkListIds) {
		this.checkListIds = checkListIds;
	}

	public List<WmsProjectMasterDto> getProjectMasterList() {
		return projectMasterList;
	}

	public void setProjectMasterList(List<WmsProjectMasterDto> projectMasterList) {
		this.projectMasterList = projectMasterList;
	}

	public List<LookUp> getOverHeadPercentLookUp() {
		return overHeadPercentLookUp;
	}

	public void setOverHeadPercentLookUp(List<LookUp> overHeadPercentLookUp) {
		this.overHeadPercentLookUp = overHeadPercentLookUp;
	}

	public List<WorkEstimOverHeadDetDto> getEstimOverHeadDetDto() {
		return estimOverHeadDetDto;
	}

	public void setEstimOverHeadDetDto(List<WorkEstimOverHeadDetDto> estimOverHeadDetDto) {
		this.estimOverHeadDetDto = estimOverHeadDetDto;
	}

	public List<LookUp> getOverHeadLookUp() {
		return overHeadLookUp;
	}

	public void setOverHeadLookUp(List<LookUp> overHeadLookUp) {
		this.overHeadLookUp = overHeadLookUp;
	}

	public String getRemoveOverHeadById() {
		return removeOverHeadById;
	}

	public void setRemoveOverHeadById(String removeOverHeadById) {
		this.removeOverHeadById = removeOverHeadById;
	}

	public BigDecimal getMbTotalAmt() {
		return mbTotalAmt;
	}

	public void setMbTotalAmt(BigDecimal mbTotalAmt) {
		this.mbTotalAmt = mbTotalAmt;
	}

	public List<WorkDefinitionDto> getWorkDefinitionDto() {
		return workDefinitionDto;
	}

	public void setWorkDefinitionDto(List<WorkDefinitionDto> workDefinitionDto) {
		this.workDefinitionDto = workDefinitionDto;
	}

	public Long getNewWorkId() {
		return newWorkId;
	}

	public void setNewWorkId(Long newWorkId) {
		this.newWorkId = newWorkId;
	}

	public List<MbOverHeadDetDto> getMbeOverHeadDetDtoList() {
		return mbeOverHeadDetDtoList;
	}

	public void setMbeOverHeadDetDtoList(List<MbOverHeadDetDto> mbeOverHeadDetDtoList) {
		this.mbeOverHeadDetDtoList = mbeOverHeadDetDtoList;
	}

	public String getPreMbStatus() {
		return preMbStatus;
	}

	public void setPreMbStatus(String preMbStatus) {
		this.preMbStatus = preMbStatus;
	}

	public BigDecimal getOverHeadAmt() {
		return overHeadAmt;
	}

	public void setOverHeadAmt(BigDecimal overHeadAmt) {
		this.overHeadAmt = overHeadAmt;
	}

	private BigDecimal totalMbOverheadValue;

	public BigDecimal getTotalMbOverheadValue() {
		return totalMbOverheadValue;
	}

	public void setTotalMbOverheadValue(BigDecimal totalMbOverheadValue) {
		this.totalMbOverheadValue = totalMbOverheadValue;
	}

	private BigDecimal previousMbOverHeadAmt;

	public BigDecimal getPreviousMbOverHeadAmt() {
		return previousMbOverHeadAmt;
	}

	public void setPreviousMbOverHeadAmt(BigDecimal previousMbOverHeadAmt) {
		this.previousMbOverHeadAmt = previousMbOverHeadAmt;
	}

	public String getRemoveSorIds() {
		return removeSorIds;
	}

	public void setRemoveSorIds(String removeSorIds) {
		this.removeSorIds = removeSorIds;
	}

	public String getRemoveNonSorIds() {
		return removeNonSorIds;
	}

	public void setRemoveNonSorIds(String removeNonSorIds) {
		this.removeNonSorIds = removeNonSorIds;
	}
	
	
	 public String getNewWorkCode() {
		return newWorkCode;
	}

	public void setNewWorkCode(String newWorkCode) {
		this.newWorkCode = newWorkCode;
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
	        taskAction.setReferenceId(getNewWorkCode());
	        taskAction.setPaymentMode(MainetConstants.FlagF);
	        taskAction.setDecision("SUBMITED");
	        return taskAction;
	    }

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public MeasurementBookLbhDto getMeasurementBookLbhDto() {
		return measurementBookLbhDto;
	}

	public void setMeasurementBookLbhDto(MeasurementBookLbhDto measurementBookLbhDto) {
		this.measurementBookLbhDto = measurementBookLbhDto;
	}
}
