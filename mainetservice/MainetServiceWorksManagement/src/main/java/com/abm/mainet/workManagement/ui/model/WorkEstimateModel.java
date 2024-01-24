package com.abm.mainet.workManagement.ui.model;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.workManagement.dto.ScheduleOfRateDetDto;
import com.abm.mainet.workManagement.dto.ScheduleOfRateMstDto;
import com.abm.mainet.workManagement.dto.WmsLeadLiftMasterDto;
import com.abm.mainet.workManagement.dto.WmsMaterialMasterDto;
import com.abm.mainet.workManagement.dto.WmsProjectMasterDto;
import com.abm.mainet.workManagement.dto.WorkDefinitionDto;
import com.abm.mainet.workManagement.dto.WorkEstimOverHeadDetDto;
import com.abm.mainet.workManagement.dto.WorkEstimateMasterDto;
import com.abm.mainet.workManagement.dto.WorkEstimateMeasureDetailsDto;
import com.abm.mainet.workManagement.service.WorkDefinitionService;
import com.abm.mainet.workManagement.service.WorkEstimateService;
import com.abm.mainet.workManagement.service.WorksMeasurementSheetService;

/**
 * @author vishwajeet.kumar
 * @since 5 feb 2018
 */
@Component
@Scope("session")
public class WorkEstimateModel extends AbstractFormModel {

    private static final long serialVersionUID = 6533638986406926300L;

    @Autowired
    private WorkEstimateService estimateService;

    @Autowired
    private WorksMeasurementSheetService measurementSheetService;

    @Autowired
    private IFileUploadService fileUpload;

    @Autowired
    private WorkDefinitionService workDefinitionService;

    private String saveMode;
    private List<WorkEstimateMasterDto> workEstimateList = new ArrayList<WorkEstimateMasterDto>();
    private List<WorkEstimateMasterDto> savedEstimateList = new ArrayList<WorkEstimateMasterDto>();
    private List<WorkDefinitionDto> workDefinitionDto = new ArrayList<WorkDefinitionDto>();
    private List<ScheduleOfRateMstDto> scheduleOfrateMstList;
    private List<ScheduleOfRateDetDto> sorDetailsList = new ArrayList<>();
    private ScheduleOfRateDetDto sorDetailsDto = new ScheduleOfRateDetDto();
    private ScheduleOfRateMstDto scheduleOfRatemstDto = new ScheduleOfRateMstDto();
    private String esimateType;
    private WorkEstimateMasterDto estimateMasterDto;
    private List<WmsProjectMasterDto> projectMasterList = new ArrayList<>();

    private Long projectId;
    private Long workId;
    private Long workpId;
    private Long newWorkId;
    private String sorName;
    private ScheduleOfRateDetDto sorDetailsDtos = new ScheduleOfRateDetDto();
    private List<WorkEstimateMeasureDetailsDto> measureDetailsList = new ArrayList<>();

    // for Data binding
    private List<WorkEstimateMasterDto> rateAnalysisMaterialList = new ArrayList<>();
    private List<WorkEstimateMasterDto> rateAnalysisRoyaltyList = new ArrayList<>();
    private List<WorkEstimateMasterDto> rateAnalysisLoadList = new ArrayList<>();
    private List<WorkEstimateMasterDto> rateAnalysisUnLoadList = new ArrayList<>();
    private List<WorkEstimateMasterDto> rateAnalysisLeadList = new ArrayList<>();
    private List<WorkEstimateMasterDto> rateAnalysisLiftList = new ArrayList<>();
    private List<WorkEstimateMasterDto> rateAnalysisStackingList = new ArrayList<>();

    // for dropDouwn List in select option (All Rate Type including Lead and Lift)
    private List<WmsMaterialMasterDto> materialMasterList = new ArrayList<>();
    private List<WmsMaterialMasterDto> royaltyMasterList = new ArrayList<>();
    private List<WmsMaterialMasterDto> loadtMasterList = new ArrayList<>();
    private List<WmsMaterialMasterDto> unLoadtMasterList = new ArrayList<>();
    private List<WmsMaterialMasterDto> stackingMasterList = new ArrayList<>();
    private List<WmsLeadLiftMasterDto> leadMasterList = new ArrayList<>();
    private List<WmsLeadLiftMasterDto> liftMasterList = new ArrayList<>();
    private List<WmsMaterialMasterDto> labourMasterList = new ArrayList<>();
    private List<WmsMaterialMasterDto> machineryMasterList = new ArrayList<>();

    // add Labour and Machinary List
    private List<WorkEstimateMasterDto> addLabourList = new ArrayList<>();
    private List<WorkEstimateMasterDto> addMachinaryList = new ArrayList<>();

    // multiple List for(All Rate Type) under single material data
    private List<WorkEstimateMasterDto> addAllRatetypeEntity = new ArrayList<>();

    private String removeChildIds;
    private List<ScheduleOfRateMstDto> activeScheduleRateList;
    private Long sorId;
    private String fromDate;
    private String toDate;
    private String workProjCode;
    private String newWorkCode;
    private String estimateTypeId;
    private String totalOverheadsValue;

    private List<Long> WorkUsedInEstimation;
    private List<WorkEstimateMasterDto> measurementsheetViewData = new ArrayList<>();
    private Long sorCommonId;

    private Long measurementWorkId;
    private Long rateAnalysisWorkId;
    private Long labourWorkId;
    private Long machinaryWorkId;

    // for estimate Id forAll Master type
    private Long rateAnalysisSorId;
    private Long labourSorId;
    private Long machinarySorId;
    private Long rateAnalysisSorDId;
    private Long labourSorDId;
    private Long machinarySorDId;

    // other than work Estimate form
    private List<LookUp> unitLookUpList = new ArrayList<>();

    private List<LookUp> allRateType = new ArrayList<>();

    private List<LookUp> overHeadPercentLookUp = new ArrayList<>();
    private List<WorkEstimOverHeadDetDto> estimOverHeadDetDto = new ArrayList<>();
    private List<LookUp> overHeadLookUp = new ArrayList<>();

    private String removeEnclosureFileById;

    private String removeLabourById;
    private String removeMachineryById;
    private String removeLabourFormById;
    private String removeNonSorById;
    private String removeOverHeadById;
    private String removeMaterialById;
    private String removeDirectAbstract;

    private List<Long> fileCountUpload;
    private List<DocumentDetailsVO> attachments = new ArrayList<>();
    private List<AttachDocs> attachDocsList = new ArrayList<>();

    // for all rate type used in rateType in Rate Anlysis
    private WmsMaterialMasterDto rateTypeAllMaster;

    // non Sor List
    private List<WorkEstimateMasterDto> workEstimateNonSorList = new ArrayList<WorkEstimateMasterDto>();

    private Map<String, String> ratePrifixMap = new HashMap<>();
    private Map<Long, List<WorkEstimateMasterDto>> materialSubList = new HashMap<>();
    private Long materialMapKey;

    private String requestFormFlag;

    private Long parentOrgId; // used for cross organization

    private String excelFilePathDirects;

    private BigDecimal totalEstimateAmount;

    private Long mbContractId;

    private String modeCpd;
    
    private List<WorkDefinitionDto> workDefinationList = new ArrayList<>();
    
    private List chaperList;

    public List getChaperList() {
		return chaperList;
	}

	public void setChaperList(List chaperList) {
		this.chaperList = chaperList;
	}

	public List<String> saveEstimateData() {

        List<String> statusList = validateEstimateForm();
        if (!statusList.isEmpty()) {
            return statusList;
        }

        measurementsheetViewData.clear();
        List<Long> deletedEstimate = new ArrayList<>();
        List<WorkEstimateMasterDto> workEstimatetypeList = new ArrayList<WorkEstimateMasterDto>();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
        String macAddres = UserSession.getCurrent().getEmployee().getEmppiservername();
        Date newDate = new Date();
        if (getEsimateType() != null
                && (getEsimateType().equals(MainetConstants.FlagS) || getEsimateType().equals(MainetConstants.FlagP)))
            this.getSorDetailsList().parallelStream().forEach(listDto -> {
                //if (listDto.getWorkEstimateId() == null) {
                    WorkEstimateMasterDto entity = new WorkEstimateMasterDto();
                    entity.setEstimateType(getEsimateType());
                    if (!getSaveMode().equals(MainetConstants.FlagM)) {
                        entity.setWorkEstimFlag(MainetConstants.FlagS);
                    } else {
                        entity.setWorkEstimFlag(MainetConstants.WorksManagement.MS);
                        entity.setContractId(this.getMbContractId());
                    }
                    // entity.setWorkEstimPId(getWorkId());
                    entity.setWorkPreviousId(getWorkId());
                    setWorkpId(getWorkId());
                    if (getEsimateType().equals(MainetConstants.FlagS)) {
                        entity.setSorId(getSorCommonId());
                        entity.setWorkId(getNewWorkId());
                    } else {
                        // entity.setSorId(listDto.getSorId());
                        entity.setSorId(getSorCommonId());
                        entity.setWorkId(getNewWorkId());
                    }
                    if(null!=listDto.getWorkEstimateId())
                    	entity.setWorkEstemateId(listDto.getWorkEstimateId());
                    entity.setSordId(listDto.getSordId());
                    entity.setSordCategory(listDto.getSordCategory());
                    entity.setSordSubCategory(listDto.getSordSubCategory());
                    entity.setSorDIteamNo(listDto.getSorDIteamNo());
                    entity.setSorDDescription(listDto.getSorDDescription());
                    entity.setSorIteamUnit(listDto.getSorIteamUnit());
                    entity.setSorBasicRate(listDto.getSorBasicRate());
                    entity.setSorLabourRate(listDto.getSorLabourRate());
                    entity.setWorkEstimActive(MainetConstants.Common_Constant.YES);
                    entity.setOrgId(orgId);
                    entity.setCreatedBy(createdBy);
                    entity.setCreatedDate(newDate);
                    entity.setLgIpMac(macAddres);
                    entity.setProjId(getProjectId());
                    workEstimatetypeList.add(entity);
                //}
            });
        else {
            this.getWorkEstimateList().parallelStream().forEach(listDto -> {
                listDto.setProjId(getProjectId());
                if (listDto.getWorkEstemateId() == null) {
                    listDto.setEstimateType(getEsimateType());
                    listDto.setWorkEstimFlag(MainetConstants.FlagS);
                    listDto.setWorkEstimActive(MainetConstants.Common_Constant.YES);
                    listDto.setWorkId(getNewWorkId());
                    listDto.setOrgId(orgId);
                    listDto.setCreatedBy(createdBy);
                    listDto.setCreatedDate(newDate);
                    listDto.setLgIpMac(macAddres);
                    listDto.setWorkEstimPId(getWorkpId());
                } else {
                    listDto.setUpdatedBy(createdBy);
                    listDto.setUpdatedDate(newDate);
                    listDto.setLgIpMacUpd(macAddres);
                }
            });
            workEstimatetypeList.addAll(this.getWorkEstimateList());
        }

        String ids = getRemoveDirectAbstract();
        if (ids != null && !ids.isEmpty()) {
            String array[] = ids.split(MainetConstants.operator.COMMA);
            for (String id : array) {
                deletedEstimate.add(Long.valueOf(id));
            }
        }

        estimateService.saveWorkEstimateList(workEstimatetypeList, deletedEstimate, getSaveMode());
        workDefinitionService.updateWorkDefinationMode(getNewWorkId(), MainetConstants.FlagD);
        measurementsheetViewData.addAll(workEstimatetypeList);

        return statusList;
    }

    public boolean SaveLbhForm() {
        prepareWorkEstimateEntity();
        List<Long> removeIds = null;
        String ids = getRemoveChildIds();
        if (ids != null && !ids.isEmpty()) {
            removeIds = new ArrayList<>();
            String array[] = ids.split(MainetConstants.operator.COMMA);
            for (String id : array) {
                removeIds.add(Long.valueOf(id));
            }
        }
        measurementSheetService.saveUpdateEstimateMeasureDetails(getMeasureDetailsList(), removeIds, getSaveMode());
        estimateService.updateWorkEsimateLbhQunatity(getMeasurementWorkId(),
                measurementSheetService.calculateTotalEstimatedAmountByWorkId(getMeasurementWorkId()));

        return true;
    }

    // used to set all others required database field
    public void prepareWorkEstimateEntity() {

        Date todayDate = new Date();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        Long empId = UserSession.getCurrent().getEmployee().getEmpId();

        for (WorkEstimateMeasureDetailsDto measureDetailsDto : getMeasureDetailsList()) {
            measureDetailsDto.setOrgId(orgId);
            if (measureDetailsDto.getCreatedBy() == null) {
                measureDetailsDto.setCreatedBy(empId);
                measureDetailsDto.setWorkEstemateId(getMeasurementWorkId());
                measureDetailsDto.setCreatedDate(todayDate);
                measureDetailsDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
                measureDetailsDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
                measureDetailsDto.setMeMentActive(MainetConstants.IsDeleted.DELETE);

            } else {
                measureDetailsDto.setUpdatedBy(empId);
                measureDetailsDto.setUpdatedDate(todayDate);
                measureDetailsDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
            }
        }
    }

    public void prepareRateAnalysisData(List<WmsMaterialMasterDto> materialListBySorId) {

        materialMasterList.clear();
        loadtMasterList.clear();
        unLoadtMasterList.clear();
        stackingMasterList.clear();
        labourMasterList.clear();
        machineryMasterList.clear();
        List<LookUp> lookups = getLevelData(MainetConstants.WorksManagement.MTY);
        lookups.forEach(lookup -> {
            materialListBySorId.forEach(listData -> {
                if (lookup.getLookUpCode().equals(MainetConstants.FlagM)
                        && listData.getMaTypeId() == lookup.getLookUpId()) {
                    materialMasterList.add(listData);
                } else if (lookup.getLookUpCode().equals(MainetConstants.FlagL)
                        && listData.getMaTypeId() == lookup.getLookUpId()) {
                    loadtMasterList.add(listData);
                } else if (lookup.getLookUpCode().equals(MainetConstants.FlagU)
                        && listData.getMaTypeId() == lookup.getLookUpId()) {
                    unLoadtMasterList.add(listData);
                } else if (lookup.getLookUpCode().equals(MainetConstants.FlagS)
                        && listData.getMaTypeId() == lookup.getLookUpId()) {
                    stackingMasterList.add(listData);
                } else if (lookup.getLookUpCode().equals(MainetConstants.FlagA)
                        && listData.getMaTypeId() == lookup.getLookUpId()) {
                    labourMasterList.add(listData);
                } else if (lookup.getLookUpCode().equals(MainetConstants.FlagC)
                        && listData.getMaTypeId() == lookup.getLookUpId()) {
                    machineryMasterList.add(listData);
                }
            });
        });
    }

    public void saveRateAnalysisEntity() {

        List<WorkEstimateMasterDto> rateAnalysisEntity = new ArrayList<>();

        getRateAnalysisMaterialList().forEach(parentMaterialObj -> {
            parentMaterialObj.setWorkEstimFlag(MainetConstants.FlagM);
            rateAnalysisEntity.add(parentMaterialObj);
            Long materialId = parentMaterialObj.getgRateMastId();
            List<WorkEstimateMasterDto> otherRateTypeList = getMaterialSubList().get(materialId);

            if (otherRateTypeList != null && !otherRateTypeList.isEmpty()) {
                otherRateTypeList.forEach(obj -> {
                    obj.setMaPId(materialId);
                    if (obj.getWorkEstimFlag().equals(MainetConstants.FlagL))
                        obj.setWorkEstimFlag(MainetConstants.WorksManagement.LO);
                    else if (obj.getWorkEstimFlag().equals(MainetConstants.FlagU))
                        obj.setWorkEstimFlag(MainetConstants.WorksManagement.UN);
                    else if (obj.getWorkEstimFlag().equals(MainetConstants.FlagS))
                        obj.setWorkEstimFlag(MainetConstants.WorksManagement.ST);
                    else if (obj.getWorkEstimFlag().equals(MainetConstants.FlagA))
                        obj.setWorkEstimFlag(MainetConstants.FlagA);
                    else if (obj.getWorkEstimFlag().equals(MainetConstants.FlagC))
                        obj.setWorkEstimFlag(MainetConstants.FlagC);
                    else if (obj.getWorkEstimFlag().equals(MainetConstants.WorksManagement.L_E))
                        obj.setWorkEstimFlag(MainetConstants.WorksManagement.LE);
                    else if (obj.getWorkEstimFlag().equals(MainetConstants.WorksManagement.L_I))
                        obj.setWorkEstimFlag(MainetConstants.WorksManagement.LF);
                    else if (obj.getWorkEstimFlag().equals(MainetConstants.FlagR))
                        obj.setWorkEstimFlag(MainetConstants.WorksManagement.RO);
                    rateAnalysisEntity.add(obj);
                });
            }
        });
        prepareRateMasterEntity(rateAnalysisEntity);
    }

    public void prepareRateMasterEntity(List<WorkEstimateMasterDto> rateAnalysisEntity) {

        Date todayDate = new Date();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        Long empId = UserSession.getCurrent().getEmployee().getEmpId();

        for (WorkEstimateMasterDto measureDetailsDto : rateAnalysisEntity) {
            measureDetailsDto.setProjId(getProjectId());
            measureDetailsDto.setOrgId(orgId);
            if (measureDetailsDto.getCreatedBy() == null) {
                measureDetailsDto.setCreatedBy(empId);
                measureDetailsDto.setWorkId(this.getNewWorkId());
                measureDetailsDto.setWorkEstimPId(getRateAnalysisWorkId());
                measureDetailsDto.setEstimateType(this.getEsimateType());
                measureDetailsDto.setCreatedDate(todayDate);
                measureDetailsDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
                measureDetailsDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
                measureDetailsDto.setWorkEstimActive(MainetConstants.IsDeleted.DELETE);
                measureDetailsDto.setSordId(getRateAnalysisSorDId());
                measureDetailsDto.setSorId(getRateAnalysisSorId());
            } else {
                measureDetailsDto.setUpdatedBy(empId);
                measureDetailsDto.setUpdatedDate(todayDate);
                measureDetailsDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
            }
        }

        List<Long> removeIds = new ArrayList<>();

        String ids = getRemoveMaterialById();
        if (ids != null && !ids.isEmpty()) {
            String array[] = ids.split(MainetConstants.operator.COMMA);
            for (String id : array) {
                removeIds.add(Long.valueOf(id));
            }
        }
        String allRateIds = getRemoveLabourById();
        if (allRateIds != null && !allRateIds.isEmpty()) {
            String array[] = allRateIds.split(MainetConstants.operator.COMMA);
            for (String id : array) {
                removeIds.add(Long.valueOf(id));
            }
        }
        estimateService.saveWorkEstimateList(rateAnalysisEntity, removeIds, getSaveMode());
    }

    public void calculateTotalEstimatedAmount() {

        Map<Long, BigDecimal> estimatedAmt = new HashMap<>();
        this.getMeasurementsheetViewData().forEach(dto -> {

            BigDecimal estimateQuantity = measurementSheetService
                    .calculateTotalEstimatedAmountByWorkId(dto.getWorkEstemateId());
            dto.setWorkEstimQuantity(estimateQuantity);

            BigDecimal estimateAmount = estimateService.calculateTotalEstimatedAmountByWorkId(dto.getWorkEstemateId());
            dto.setWorkEstimAmount(estimateAmount);
            BigDecimal q = null;
            if (dto.getSorBasicRate() != null && estimateQuantity != null)
                q = estimateQuantity.multiply(dto.getSorBasicRate());

            BigDecimal totalValue = null;
            if (dto.getWorkEstimAmount() != null && q != null) {
                totalValue = dto.getWorkEstimAmount().add(q);
            } else if (dto.getWorkEstimAmount() != null && q == null) {
                totalValue = dto.getWorkEstimAmount();
            } else if (dto.getWorkEstimAmount() == null && q != null) {
                totalValue = q;
            }
            if (estimateQuantity != null && dto.getSorBasicRate() != null && dto.getSorLabourRate() != null) {
                totalValue = totalValue.add(dto.getSorLabourRate());
            }

            if (totalValue != null) {
                dto.setTotalEsimateAmount(totalValue.setScale(2, RoundingMode.HALF_EVEN));
            }

            estimatedAmt.put(dto.getWorkEstemateId(), totalValue);
        });

        if (!this.getMeasurementsheetViewData().isEmpty())
            estimateService.updateParentWorkEstimationAmount(estimatedAmt, this.getMeasurementsheetViewData().get(0).getWorkId(),
                    UserSession.getCurrent().getOrganisation().getOrgid());
    }

    public void prepareWorkEnclosuresData(WorkEstimateMasterDto addWorkEnclosures) {

        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        requestDTO.setStatus(MainetConstants.FlagA);
        requestDTO.setDepartmentName(MainetConstants.WorksManagement.WORKS_MANAGEMENT);
        requestDTO.setIdfId(getNewWorkCode());
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
        if (enclosureRemoveById != null && !enclosureRemoveById.isEmpty())
            estimateService.deleteEnclosureFileById(enclosureRemoveById,
                    UserSession.getCurrent().getEmployee().getEmpId());
    }

    public WorkEstimateMasterDto prepareAddLabourData(WorkEstimateMasterDto addLabourData) {
        List<Long> labourRemoveById = null;
        String fileId = getRemoveLabourById();
        if (fileId != null && !fileId.isEmpty()) {
            labourRemoveById = new ArrayList<>();
            String fileArray[] = fileId.split(MainetConstants.operator.COMMA);
            for (String fields : fileArray) {
                labourRemoveById.add(Long.valueOf(fields));
            }
        }
        materialSubList.put(getMaterialMapKey(), getAddAllRatetypeEntity());
        return estimateMasterDto;
    }

    public WorkEstimateMasterDto prepareMachineryData(WorkEstimateMasterDto addMachineryData) {

        List<Long> machineryRemoveById = null;
        String fileId = getRemoveMachineryById();
        if (fileId != null && !fileId.isEmpty()) {
            machineryRemoveById = new ArrayList<>();
            String fileArray[] = fileId.split(MainetConstants.operator.COMMA);
            for (String fields : fileArray) {
                machineryRemoveById.add(Long.valueOf(fields));
            }
        }
        Date todayDate = new Date();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        Long empId = UserSession.getCurrent().getEmployee().getEmpId();

        for (WorkEstimateMasterDto measureDetailsDto : getAddMachinaryList()) {
            measureDetailsDto.setProjId(getProjectId());
            measureDetailsDto.setOrgId(orgId);
            if (measureDetailsDto.getCreatedBy() == null) {
                measureDetailsDto.setCreatedBy(empId);
                measureDetailsDto.setWorkId(this.getNewWorkId());
                measureDetailsDto.setWorkEstimPId(getMachinaryWorkId());
                measureDetailsDto.setEstimateType(this.getEsimateType());
                measureDetailsDto.setCreatedDate(todayDate);
                measureDetailsDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
                measureDetailsDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
                measureDetailsDto.setWorkEstimActive(MainetConstants.IsDeleted.DELETE);
                measureDetailsDto.setSordId(getMachinarySorDId());
                measureDetailsDto.setSorId(getMachinarySorId());
                measureDetailsDto.setWorkEstimFlag(MainetConstants.FlagC);
            } else {
                measureDetailsDto.setUpdatedBy(empId);
                measureDetailsDto.setUpdatedDate(todayDate);
                measureDetailsDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
            }
        }
        estimateService.saveWorkEstimateList(getAddMachinaryList(), machineryRemoveById, getSaveMode());
        return estimateMasterDto;
    }

    public WorkEstimateMasterDto prepareLabourFormData(WorkEstimateMasterDto addMachineryData) {

        List<Long> labourRemoveById = null;
        String fileId = getRemoveLabourFormById();
        if (fileId != null && !fileId.isEmpty()) {
            labourRemoveById = new ArrayList<>();
            String fileArray[] = fileId.split(MainetConstants.operator.COMMA);
            for (String fields : fileArray) {
                labourRemoveById.add(Long.valueOf(fields));
            }
        }
        Date todayDate = new Date();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        Long empId = UserSession.getCurrent().getEmployee().getEmpId();

        for (WorkEstimateMasterDto measureDetailsDto : getAddLabourList()) {
            measureDetailsDto.setProjId(getProjectId());
            measureDetailsDto.setOrgId(orgId);
            if (measureDetailsDto.getCreatedBy() == null) {
                measureDetailsDto.setCreatedBy(empId);
                measureDetailsDto.setWorkId(this.getNewWorkId());
                measureDetailsDto.setWorkEstimPId(getLabourWorkId());
                measureDetailsDto.setEstimateType(this.getEsimateType());
                measureDetailsDto.setCreatedDate(todayDate);
                measureDetailsDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
                measureDetailsDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
                measureDetailsDto.setWorkEstimActive(MainetConstants.IsDeleted.DELETE);
                measureDetailsDto.setSordId(getLabourSorDId());
                measureDetailsDto.setSorId(getLabourSorId());
                measureDetailsDto.setWorkEstimFlag(MainetConstants.FlagA);
            } else {
                measureDetailsDto.setUpdatedBy(empId);
                measureDetailsDto.setUpdatedDate(todayDate);
                measureDetailsDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
                measureDetailsDto.setWorkEstimFlag(MainetConstants.FlagA);
            }
        }
        estimateService.saveWorkEstimateList(getAddLabourList(), labourRemoveById, getSaveMode());
        return estimateMasterDto;
    }

    public void prepareNonSORData(WorkEstimateMasterDto addNonSorData) {

        List<Long> nonSorRemoveById = null;
        String fileId = getRemoveNonSorById();
        if (fileId != null && !fileId.isEmpty()) {
            nonSorRemoveById = new ArrayList<>();
            String fileArray[] = fileId.split(MainetConstants.operator.COMMA);
            for (String fields : fileArray) {
                nonSorRemoveById.add(Long.valueOf(fields));
            }
        }
        Date todayDate = new Date();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        Long empId = UserSession.getCurrent().getEmployee().getEmpId();

        for (WorkEstimateMasterDto measureDetailsDto : getWorkEstimateNonSorList()) {
            measureDetailsDto.setProjId(getProjectId());
            measureDetailsDto.setOrgId(orgId);
            if (measureDetailsDto.getCreatedBy() == null) {
                measureDetailsDto.setCreatedBy(empId);
                measureDetailsDto.setWorkId(this.getNewWorkId());
                // measureDetailsDto.setWorkEstimPId(getWorkpId());
                measureDetailsDto.setWorkPreviousId(getWorkpId());
                measureDetailsDto.setEstimateType(this.getEsimateType());
                measureDetailsDto.setCreatedDate(todayDate);
                measureDetailsDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
                measureDetailsDto.setWorkEstimActive(MainetConstants.IsDeleted.DELETE);
                if (!getSaveMode().equals(MainetConstants.FlagM)) {
                    measureDetailsDto.setWorkEstimFlag(MainetConstants.FlagN);
                } else {
                    measureDetailsDto.setWorkEstimFlag(MainetConstants.WorksManagement.MN);
                }
            } else {
                measureDetailsDto.setUpdatedBy(empId);
                measureDetailsDto.setUpdatedDate(todayDate);
                measureDetailsDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
            }
        }

        Iterator nonSorList = getWorkEstimateNonSorList().iterator();
        while (nonSorList.hasNext()) {
            WorkEstimateMasterDto estimateObj = (WorkEstimateMasterDto) nonSorList.next();
            if (estimateObj.getSorDIteamNo() == null || estimateObj.getSorDIteamNo().isEmpty()) {
                nonSorList.remove();
            }
        }

        estimateService.saveWorkEstimateList(getWorkEstimateNonSorList(), nonSorRemoveById, getSaveMode());
        setRemoveNonSorById(null);
    }

    public void prepareOverHeadData(List<WorkEstimOverHeadDetDto> headDetDtos) {

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
        for (WorkEstimOverHeadDetDto measureDetailsDto : getEstimOverHeadDetDto()) {
            measureDetailsDto.setOrgId(orgId);
            if (measureDetailsDto.getCreatedBy() == null) {
                measureDetailsDto.setCreatedBy(empId);
                measureDetailsDto.setWorkId(this.getNewWorkId());
                measureDetailsDto.setCreatedDate(todayDate);
                measureDetailsDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
                measureDetailsDto.setActive(MainetConstants.FlagY);
            } else {
                measureDetailsDto.setUpdatedBy(empId);
                measureDetailsDto.setUpdatedDate(todayDate);
                measureDetailsDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
            }
        }
        estimateService.saveOverHeadList(getEstimOverHeadDetDto(), overHeadRemoveById);
        setRemoveOverHeadById(null);
    }

    public void preapereEditModeData(List<WorkEstimateMasterDto> estimationByWorkIdAndType) {

        sorDetailsList = new ArrayList<>();

        WorkEstimateMasterDto dto = null;
        if (!estimationByWorkIdAndType.isEmpty()) {
            dto = estimationByWorkIdAndType.get(0);
            // setWorkpId(dto.getWorkEstimPId());
            setWorkpId(dto.getWorkPreviousId());
            setNewWorkId(dto.getWorkId());
            setProjectId(workDefinitionService.findAllWorkDefinitionById(getNewWorkId()).getProjId());
            setNewWorkCode(newWorkCode);
            setEstimateTypeId(dto.getEstimateType());
            setEsimateType(dto.getEstimateType());
            setSorCommonId(dto.getSorId());
            setWorkId(dto.getWorkId());
            // setWorkId(dto.getWorkEstimPId());
            setWorkId(dto.getWorkPreviousId());
            setMbContractId(dto.getContractId());
        }

        if (getEsimateType() != null && !getEsimateType().equals(MainetConstants.FlagU)) {
            estimationByWorkIdAndType.parallelStream().forEach(estimatData -> {
                ScheduleOfRateDetDto detDto = new ScheduleOfRateDetDto();
                detDto.setWorkEstimateId(estimatData.getWorkEstemateId());
                detDto.setSorLabourRate(estimatData.getSorLabourRate());
                detDto.setSorBasicRate(estimatData.getSorBasicRate());
                detDto.setSorIteamUnit(estimatData.getSorIteamUnit());
                detDto.setSorIteamUnitDesc(estimatData.getSorIteamUnitDesc());
                detDto.setSordCategory(estimatData.getSordCategory());
                detDto.setSordCategoryDesc(estimatData.getSordCategoryStr());
                detDto.setSorDIteamNo(estimatData.getSorDIteamNo());
                detDto.setSordSubCategory(estimatData.getSordSubCategory());
                detDto.setSorDDescription(estimatData.getSorDDescription());
                detDto.setSorId(estimatData.getSorId());
                detDto.setSordId(estimatData.getSordId());
                detDto.setCheckBox(true);
                detDto.setWorkEFlag(estimatData.getWorkEstimFlag());
                if (estimatData.getWorkEstimFlag().equals("S")) {
                    detDto.setFlag("SOR");
                }
                detDto.setEstimatechildAvailble(estimatData.isChildAvailable());
                if (saveMode.equals(MainetConstants.FlagM)
                        && (estimatData.getWorkEstimFlag().equals(MainetConstants.WorksManagement.MS))) {
                    sorDetailsList.add(detDto);
                } else if (!saveMode.equals(MainetConstants.FlagM)) {
                    sorDetailsList.add(detDto);
                }
            });
        } else {
            workEstimateList = estimationByWorkIdAndType;
        }

    }

    public void prepareAllMasterTypeList() {

        List<LookUp> rateType = getLevelData(MainetConstants.WorksManagement.MTY);
        rateType.forEach(lookup -> {
            if (lookup.getLookUpCode() != null && !lookup.getLookUpCode().equals(MainetConstants.FlagM))
                ratePrifixMap.put(lookup.getLookUpCode(), lookup.getLookUpDesc());
        });
        ratePrifixMap.put(MainetConstants.FlagR, MainetConstants.WorksManagement.ROYALITY);
        ratePrifixMap.put(MainetConstants.WorksManagement.L_E, MainetConstants.LEAD);
        ratePrifixMap.put(MainetConstants.WorksManagement.L_I, MainetConstants.LIFT);
    }

    public void setAddAllRatetypeEntity(List<WorkEstimateMasterDto> addAllRatetypeEntity) {
        if (addAllRatetypeEntity != null) {
            addAllRatetypeEntity.forEach(obj -> {
                if (obj.getWorkEstimFlag().equals(MainetConstants.WorksManagement.L_E)) {
                    obj.setRateList(getLeadRateList());
                } else if (obj.getWorkEstimFlag().equals(MainetConstants.WorksManagement.L_I)) {
                    obj.setRateList(getLiftRateList());
                } else if (obj.getWorkEstimFlag().equals(MainetConstants.FlagL)) {
                    obj.setRateList(getLoadtMasterList());
                } else if (obj.getWorkEstimFlag().equals(MainetConstants.FlagS)) {
                    obj.setRateList(getStackingMasterList());
                } else if (obj.getWorkEstimFlag().equals(MainetConstants.FlagU)) {
                    obj.setRateList(getUnLoadtMasterList());
                } else if (obj.getWorkEstimFlag().equals(MainetConstants.FlagA)) {
                    obj.setRateList(getLabourMasterList());
                } else if (obj.getWorkEstimFlag().equals(MainetConstants.FlagC)) {
                    obj.setRateList(getMachineryMasterList());
                }
            });
        }
        this.addAllRatetypeEntity = addAllRatetypeEntity;
    }

    public void setAllRateByPrifixType(List<WorkEstimateMasterDto> addAllRatetypeEntity) {
        if (addAllRatetypeEntity != null) {
            addAllRatetypeEntity.forEach(obj -> {
                if (obj.getWorkEstimFlag().equals(MainetConstants.WorksManagement.LE)) {
                    obj.setRateList(getLeadRateList());
                    obj.setWorkEstimFlag(MainetConstants.WorksManagement.L_E);
                } else if (obj.getWorkEstimFlag().equals(MainetConstants.WorksManagement.LF)) {
                    obj.setRateList(getLiftRateList());
                    obj.setWorkEstimFlag(MainetConstants.WorksManagement.L_I);
                } else if (obj.getWorkEstimFlag().equals(MainetConstants.WorksManagement.LO)) {
                    obj.setRateList(getLoadtMasterList());
                    obj.setWorkEstimFlag(MainetConstants.FlagL);
                } else if (obj.getWorkEstimFlag().equals(MainetConstants.WorksManagement.ST)) {
                    obj.setRateList(getStackingMasterList());
                    obj.setWorkEstimFlag(MainetConstants.FlagS);
                } else if (obj.getWorkEstimFlag().equals(MainetConstants.WorksManagement.UN)) {
                    obj.setRateList(getUnLoadtMasterList());
                    obj.setWorkEstimFlag(MainetConstants.FlagU);
                } else if (obj.getWorkEstimFlag().equals(MainetConstants.FlagA)) {
                    obj.setRateList(getLabourMasterList());
                    obj.setWorkEstimFlag(MainetConstants.FlagA);
                } else if (obj.getWorkEstimFlag().equals(MainetConstants.FlagC)) {
                    obj.setRateList(getMachineryMasterList());
                    obj.setWorkEstimFlag(MainetConstants.FlagC);
                } else if (obj.getWorkEstimFlag().equals(MainetConstants.WorksManagement.RO)) {
                    obj.setWorkEstimFlag(MainetConstants.FlagR);
                }
            });
        }
        this.addAllRatetypeEntity = addAllRatetypeEntity;
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

    // set all relevant Work flow Task Action Data For initiating Work Flow
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

    public List<String> validateEstimateForm() {
        List<String> errorList = new ArrayList<>();
        int count = 1;
        if (getEsimateType() != null
                && (getEsimateType().equals(MainetConstants.FlagS) || getEsimateType().equals(MainetConstants.FlagP))
                && this.getSorDetailsList() != null)
            for (ScheduleOfRateDetDto listDto : this.getSorDetailsList()) {
                if (listDto.isCheckBox() && (listDto.getSordCategory() == null || listDto.getSorIteamUnit() == null)) {
                    errorList.add("Chapter or Item code Must be Selected " + count);
                }
                count++;
            }
        if (getEstimateTypeId().endsWith(MainetConstants.FlagS) && getSorCommonId() == null) {
            errorList.add(ApplicationSession.getInstance().getMessage("work.estimate.sor.name.vldn"));
        }
        if (getEstimateTypeId().endsWith(MainetConstants.FlagP) && getWorkId() == null) {
            errorList.add(ApplicationSession.getInstance().getMessage("work.estimate.sor.workname.vldn"));
        }
        return errorList;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public List<WorkEstimateMasterDto> getWorkEstimateList() {
        return workEstimateList;
    }

    public void setWorkEstimateList(List<WorkEstimateMasterDto> workEstimateList) {
        this.workEstimateList = workEstimateList;
    }

    public List<ScheduleOfRateMstDto> getScheduleOfrateMstList() {
        return scheduleOfrateMstList;
    }

    public void setScheduleOfrateMstList(List<ScheduleOfRateMstDto> scheduleOfrateMstList) {
        this.scheduleOfrateMstList = scheduleOfrateMstList;
    }

    public WorkEstimateMasterDto getEstimateMasterDto() {
        return estimateMasterDto;
    }

    public void setEstimateMasterDto(WorkEstimateMasterDto estimateMasterDto) {
        this.estimateMasterDto = estimateMasterDto;
    }

    public ScheduleOfRateMstDto getScheduleOfRatemstDto() {
        return scheduleOfRatemstDto;
    }

    public void setScheduleOfRatemstDto(ScheduleOfRateMstDto scheduleOfRatemstDto) {
        this.scheduleOfRatemstDto = scheduleOfRatemstDto;
    }

    public String getSorName() {
        return sorName;
    }

    public void setSorName(String sorName) {
        this.sorName = sorName;
    }

    public ScheduleOfRateDetDto getSorDetailsDtos() {
        return sorDetailsDtos;
    }

    public void setSorDetailsDtos(ScheduleOfRateDetDto sorDetailsDtos) {
        this.sorDetailsDtos = sorDetailsDtos;
    }

    public List<ScheduleOfRateDetDto> getSorDetailsList() {
        return sorDetailsList;
    }

    public void setSorDetailsList(List<ScheduleOfRateDetDto> sorDetailsList) {
        this.sorDetailsList = sorDetailsList;
    }

    public String getEsimateType() {
        return esimateType;
    }

    public void setEsimateType(String esimateType) {
        this.esimateType = esimateType;
    }

    public List<WmsProjectMasterDto> getProjectMasterList() {
        return projectMasterList;
    }

    public void setProjectMasterList(List<WmsProjectMasterDto> projectMasterList) {
        this.projectMasterList = projectMasterList;
    }

    public List<WorkEstimateMasterDto> getSavedEstimateList() {
        return savedEstimateList;
    }

    public void setSavedEstimateList(List<WorkEstimateMasterDto> savedEstimateList) {
        this.savedEstimateList = savedEstimateList;
    }

    public List<WorkDefinitionDto> getWorkDefinitionDto() {
        return workDefinitionDto;
    }

    public void setWorkDefinitionDto(List<WorkDefinitionDto> workDefinitionDto) {
        this.workDefinitionDto = workDefinitionDto;
    }

    public Long getWorkId() {
        return workId;
    }

    public void setWorkId(Long workId) {
        this.workId = workId;
    }

    public Long getNewWorkId() {
        return newWorkId;
    }

    public void setNewWorkId(Long newWorkId) {
        this.newWorkId = newWorkId;
    }

    public Long getWorkpId() {
        return workpId;
    }

    public void setWorkpId(Long workpId) {
        this.workpId = workpId;
    }

    public ScheduleOfRateDetDto getSorDetailsDto() {
        return sorDetailsDto;
    }

    public void setSorDetailsDto(ScheduleOfRateDetDto sorDetailsDto) {
        sorDetailsDto.setSorIteamUnitDesc(CommonMasterUtility.getCPDDescription(sorDetailsDto.getSorIteamUnit().longValue(),
                MainetConstants.WorksManagement.EDIT));
        this.sorDetailsDto = sorDetailsDto;
    }

    public List<WorkEstimateMeasureDetailsDto> getMeasureDetailsList() {
        return measureDetailsList;
    }

    public void setMeasureDetailsList(List<WorkEstimateMeasureDetailsDto> measureDetailsList) {
        this.measureDetailsList = measureDetailsList;
    }

    public List<WmsMaterialMasterDto> getMaterialMasterList() {
        for (WmsMaterialMasterDto rateType : materialMasterList) {
            rateType.setUnitName(CommonMasterUtility.getCPDDescription(rateType.getMaItemUnit().longValue(),
                    MainetConstants.WorksManagement.EDIT));
        }
        return materialMasterList;
    }

    public void setMaterialMasterList(List<WmsMaterialMasterDto> materialMasterList) {
        this.materialMasterList = materialMasterList;
    }

    public List<WmsMaterialMasterDto> getRoyaltyMasterList() {
        return royaltyMasterList;
    }

    public void setRoyaltyMasterList(List<WmsMaterialMasterDto> royaltyMasterList) {
        this.royaltyMasterList = royaltyMasterList;
    }

    public List<WmsMaterialMasterDto> getLoadtMasterList() {
        return loadtMasterList;
    }

    public void setLoadtMasterList(List<WmsMaterialMasterDto> loadtMasterList) {
        this.loadtMasterList = loadtMasterList;
    }

    public List<WmsMaterialMasterDto> getUnLoadtMasterList() {
        return unLoadtMasterList;
    }

    public void setUnLoadtMasterList(List<WmsMaterialMasterDto> unLoadtMasterList) {
        this.unLoadtMasterList = unLoadtMasterList;
    }

    public List<WmsMaterialMasterDto> getStackingMasterList() {
        return stackingMasterList;
    }

    public void setStackingMasterList(List<WmsMaterialMasterDto> stackingMasterList) {
        this.stackingMasterList = stackingMasterList;
    }

    public List<WmsLeadLiftMasterDto> getLeadMasterList() {
        for (WmsLeadLiftMasterDto rateType : leadMasterList) {
            rateType.setUnitName(CommonMasterUtility.getCPDDescription(rateType.getLeLiUnit().longValue(),
                    MainetConstants.WorksManagement.EDIT));
        }
        return leadMasterList;
    }

    public void setLeadMasterList(List<WmsLeadLiftMasterDto> leadMasterList) {
        this.leadMasterList = leadMasterList;
    }

    public List<WmsLeadLiftMasterDto> getLiftMasterList() {
        for (WmsLeadLiftMasterDto rateType : liftMasterList) {
            rateType.setUnitName(CommonMasterUtility.getCPDDescription(rateType.getLeLiUnit().longValue(),
                    MainetConstants.WorksManagement.EDIT));
        }
        return liftMasterList;
    }

    public void setLiftMasterList(List<WmsLeadLiftMasterDto> liftMasterList) {
        this.liftMasterList = liftMasterList;
    }

    public String getRemoveChildIds() {
        return removeChildIds;
    }

    public void setRemoveChildIds(String removeChildIds) {
        this.removeChildIds = removeChildIds;
    }

    public List<ScheduleOfRateMstDto> getActiveScheduleRateList() {
        return activeScheduleRateList;
    }

    public void setActiveScheduleRateList(List<ScheduleOfRateMstDto> activeScheduleRateList) {
        this.activeScheduleRateList = activeScheduleRateList;
    }

    public Long getSorId() {
        return sorId;
    }

    public void setSorId(Long sorId) {
        this.sorId = sorId;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public List<WorkEstimateMasterDto> getRateAnalysisMaterialList() {
        return rateAnalysisMaterialList;
    }

    public void setRateAnalysisMaterialList(List<WorkEstimateMasterDto> rateAnalysisMaterialList) {
        this.rateAnalysisMaterialList = rateAnalysisMaterialList;
    }

    public List<WorkEstimateMasterDto> getRateAnalysisRoyaltyList() {
        return rateAnalysisRoyaltyList;
    }

    public void setRateAnalysisRoyaltyList(List<WorkEstimateMasterDto> rateAnalysisRoyaltyList) {
        this.rateAnalysisRoyaltyList = rateAnalysisRoyaltyList;
    }

    public List<WorkEstimateMasterDto> getRateAnalysisLoadList() {
        return rateAnalysisLoadList;
    }

    public void setRateAnalysisLoadList(List<WorkEstimateMasterDto> rateAnalysisLoadList) {
        this.rateAnalysisLoadList = rateAnalysisLoadList;
    }

    public List<WorkEstimateMasterDto> getRateAnalysisUnLoadList() {
        return rateAnalysisUnLoadList;
    }

    public void setRateAnalysisUnLoadList(List<WorkEstimateMasterDto> rateAnalysisUnLoadList) {
        this.rateAnalysisUnLoadList = rateAnalysisUnLoadList;
    }

    public List<WorkEstimateMasterDto> getRateAnalysisLeadList() {
        return rateAnalysisLeadList;
    }

    public void setRateAnalysisLeadList(List<WorkEstimateMasterDto> rateAnalysisLeadList) {
        this.rateAnalysisLeadList = rateAnalysisLeadList;
    }

    public List<WorkEstimateMasterDto> getRateAnalysisLiftList() {
        return rateAnalysisLiftList;
    }

    public void setRateAnalysisLiftList(List<WorkEstimateMasterDto> rateAnalysisLiftList) {
        this.rateAnalysisLiftList = rateAnalysisLiftList;
    }

    public List<WorkEstimateMasterDto> getRateAnalysisStackingList() {
        return rateAnalysisStackingList;
    }

    public void setRateAnalysisStackingList(List<WorkEstimateMasterDto> rateAnalysisStackingList) {
        this.rateAnalysisStackingList = rateAnalysisStackingList;
    }

    public String getWorkProjCode() {
        return workProjCode;
    }

    public void setWorkProjCode(String workProjCode) {
        this.workProjCode = workProjCode;
    }

    public String getNewWorkCode() {
        return newWorkCode;
    }

    public void setNewWorkCode(String newWorkCode) {
        this.newWorkCode = newWorkCode;
    }

    public String getEstimateTypeId() {
        return estimateTypeId;
    }

    public void setEstimateTypeId(String estimateTypeId) {
        this.estimateTypeId = estimateTypeId;
    }

    public String getTotalOverheadsValue() {
        return totalOverheadsValue;
    }

    public void setTotalOverheadsValue(String totalOverheadsValue) {
        this.totalOverheadsValue = totalOverheadsValue;
    }

    public List<Long> getWorkUsedInEstimation() {
        return WorkUsedInEstimation;
    }

    public void setWorkUsedInEstimation(List<Long> workUsedInEstimation) {
        WorkUsedInEstimation = workUsedInEstimation;
    }

    public List<WorkEstimateMasterDto> getMeasurementsheetViewData() {
        return measurementsheetViewData;
    }

    public void setMeasurementsheetViewData(List<WorkEstimateMasterDto> measurementsheetViewData) {
        this.measurementsheetViewData = measurementsheetViewData;
    }

    public Long getSorCommonId() {
        return sorCommonId;
    }

    public void setSorCommonId(Long sorCommonId) {
        this.sorCommonId = sorCommonId;
    }

    public Long getMeasurementWorkId() {
        return measurementWorkId;
    }

    public void setMeasurementWorkId(Long measurementWorkId) {
        this.measurementWorkId = measurementWorkId;
    }

    public Long getRateAnalysisWorkId() {
        return rateAnalysisWorkId;
    }

    public void setRateAnalysisWorkId(Long rateAnalysisWorkId) {
        this.rateAnalysisWorkId = rateAnalysisWorkId;
    }

    public Long getLabourWorkId() {
        return labourWorkId;
    }

    public void setLabourWorkId(Long labourWorkId) {
        this.labourWorkId = labourWorkId;
    }

    public Long getMachinaryWorkId() {
        return machinaryWorkId;
    }

    public void setMachinaryWorkId(Long machinaryWorkId) {
        this.machinaryWorkId = machinaryWorkId;
    }

    public Long getRateAnalysisSorId() {
        return rateAnalysisSorId;
    }

    public void setRateAnalysisSorId(Long rateAnalysisSorId) {
        this.rateAnalysisSorId = rateAnalysisSorId;
    }

    public Long getLabourSorId() {
        return labourSorId;
    }

    public void setLabourSorId(Long labourSorId) {
        this.labourSorId = labourSorId;
    }

    public Long getMachinarySorId() {
        return machinarySorId;
    }

    public void setMachinarySorId(Long machinarySorId) {
        this.machinarySorId = machinarySorId;
    }

    public Long getRateAnalysisSorDId() {
        return rateAnalysisSorDId;
    }

    public void setRateAnalysisSorDId(Long rateAnalysisSorDId) {
        this.rateAnalysisSorDId = rateAnalysisSorDId;
    }

    public Long getLabourSorDId() {
        return labourSorDId;
    }

    public void setLabourSorDId(Long labourSorDId) {
        this.labourSorDId = labourSorDId;
    }

    public Long getMachinarySorDId() {
        return machinarySorDId;
    }

    public void setMachinarySorDId(Long machinarySorDId) {
        this.machinarySorDId = machinarySorDId;
    }

    public List<LookUp> getUnitLookUpList() {
        return unitLookUpList;
    }

    public void setUnitLookUpList(List<LookUp> unitLookUpList) {
        this.unitLookUpList = unitLookUpList;
    }

    public List<LookUp> getOverHeadPercentLookUp() {
        return overHeadPercentLookUp;
    }

    public void setOverHeadPercentLookUp(List<LookUp> overHeadPercentLookUp) {
        this.overHeadPercentLookUp = overHeadPercentLookUp;
    }

    public String getRemoveEnclosureFileById() {
        return removeEnclosureFileById;
    }

    public void setRemoveEnclosureFileById(String removeEnclosureFileById) {
        this.removeEnclosureFileById = removeEnclosureFileById;
    }

    public String getRemoveLabourById() {
        return removeLabourById;
    }

    public void setRemoveLabourById(String removeLabourById) {
        this.removeLabourById = removeLabourById;
    }

    public String getRemoveMachineryById() {
        return removeMachineryById;
    }

    public void setRemoveMachineryById(String removeMachineryById) {
        this.removeMachineryById = removeMachineryById;
    }

    public String getRemoveNonSorById() {
        return removeNonSorById;
    }

    public void setRemoveNonSorById(String removeNonSorById) {
        this.removeNonSorById = removeNonSorById;
    }

    public String getRemoveOverHeadById() {
        return removeOverHeadById;
    }

    public void setRemoveOverHeadById(String removeOverHeadById) {
        this.removeOverHeadById = removeOverHeadById;
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

    public List<WorkEstimateMasterDto> getWorkEstimateNonSorList() {
        return workEstimateNonSorList;
    }

    public void setWorkEstimateNonSorList(List<WorkEstimateMasterDto> workEstimateNonSorList) {
        this.workEstimateNonSorList = workEstimateNonSorList;
    }

    public List<WorkEstimOverHeadDetDto> getEstimOverHeadDetDto() {
        return estimOverHeadDetDto;
    }

    public void setEstimOverHeadDetDto(List<WorkEstimOverHeadDetDto> estimOverHeadDetDto) {
        this.estimOverHeadDetDto = estimOverHeadDetDto;
    }

    public List<WorkEstimateMasterDto> getAddLabourList() {
        return addLabourList;
    }

    public void setAddLabourList(List<WorkEstimateMasterDto> addLabourList) {
        this.addLabourList = addLabourList;
    }

    public List<WorkEstimateMasterDto> getAddMachinaryList() {
        return addMachinaryList;
    }

    public void setAddMachinaryList(List<WorkEstimateMasterDto> addMachinaryList) {
        this.addMachinaryList = addMachinaryList;
    }

    public List<WmsMaterialMasterDto> getLabourMasterList() {
        for (WmsMaterialMasterDto rateType : labourMasterList) {
            rateType.setUnitName(CommonMasterUtility.getCPDDescription(rateType.getMaItemUnit().longValue(),
                    MainetConstants.WorksManagement.EDIT));
        }
        return labourMasterList;
    }

    public void setLabourMasterList(List<WmsMaterialMasterDto> labourMasterList) {
        this.labourMasterList = labourMasterList;
    }

    public List<WmsMaterialMasterDto> getMachineryMasterList() {
        for (WmsMaterialMasterDto rateType : machineryMasterList) {
            rateType.setUnitName(CommonMasterUtility.getCPDDescription(rateType.getMaItemUnit().longValue(),
                    MainetConstants.WorksManagement.EDIT));
        }
        return machineryMasterList;
    }

    public void setMachineryMasterList(List<WmsMaterialMasterDto> machineryMasterList) {
        this.machineryMasterList = machineryMasterList;
    }

    public String getRemoveMaterialById() {
        return removeMaterialById;
    }

    public void setRemoveMaterialById(String removeMaterialById) {
        this.removeMaterialById = removeMaterialById;
    }

    public List<LookUp> getAllRateType() {
        return allRateType;
    }

    public void setAllRateType(List<LookUp> allRateType) {
        this.allRateType = allRateType;
    }

    public List<WorkEstimateMasterDto> getAddAllRatetypeEntity() {
        return addAllRatetypeEntity;
    }

    public WmsMaterialMasterDto getRateTypeAllMaster() {
        return rateTypeAllMaster;
    }

    public void setRateTypeAllMaster(WmsMaterialMasterDto rateTypeAllMaster) {
        this.rateTypeAllMaster = rateTypeAllMaster;
    }

    public Map<String, String> getRatePrifixMap() {
        return ratePrifixMap;
    }

    public void setRatePrifixMap(Map<String, String> ratePrifixMap) {
        this.ratePrifixMap = ratePrifixMap;
    }

    public Map<Long, List<WorkEstimateMasterDto>> getMaterialSubList() {
        return materialSubList;
    }

    public void setMaterialSubList(Map<Long, List<WorkEstimateMasterDto>> materialSubList) {
        this.materialSubList = materialSubList;
    }

    public Long getMaterialMapKey() {
        return materialMapKey;
    }

    public void setMaterialMapKey(Long materialMapKey) {
        this.materialMapKey = materialMapKey;
    }

    public String getRemoveDirectAbstract() {
        return removeDirectAbstract;
    }

    public void setRemoveDirectAbstract(String removeDirectAbstract) {
        this.removeDirectAbstract = removeDirectAbstract;
    }

    public String getRemoveLabourFormById() {
        return removeLabourFormById;
    }

    public void setRemoveLabourFormById(String removeLabourFormById) {
        this.removeLabourFormById = removeLabourFormById;
    }

    public String getRequestFormFlag() {
        return requestFormFlag;
    }

    public void setRequestFormFlag(String requestFormFlag) {
        this.requestFormFlag = requestFormFlag;
    }

    public Long getParentOrgId() {
        return parentOrgId;
    }

    public void setParentOrgId(Long parentOrgId) {
        this.parentOrgId = parentOrgId;
    }

    public String getExcelFilePathDirects() {
        return excelFilePathDirects;
    }

    public void setExcelFilePathDirects(String excelFilePathDirects) {
        this.excelFilePathDirects = excelFilePathDirects;
    }

    public BigDecimal getTotalEstimateAmount() {
        return totalEstimateAmount;
    }

    public void setTotalEstimateAmount(BigDecimal totalEstimateAmount) {
        this.totalEstimateAmount = totalEstimateAmount;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getMbContractId() {
        return mbContractId;
    }

    public void setMbContractId(Long mbContractId) {
        this.mbContractId = mbContractId;
    }

    public String getModeCpd() {
        return modeCpd;
    }

    public void setModeCpd(String modeCpd) {
        this.modeCpd = modeCpd;
    }

    public List<LookUp> getOverHeadLookUp() {
        return overHeadLookUp;
    }

    public void setOverHeadLookUp(List<LookUp> overHeadLookUp) {
        this.overHeadLookUp = overHeadLookUp;
    }

	public List<WorkDefinitionDto> getWorkDefinationList() {
		return workDefinationList;
	}

	public void setWorkDefinationList(List<WorkDefinitionDto> workDefinationList) {
		this.workDefinationList = workDefinationList;
	}
  
}
