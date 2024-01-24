package com.abm.mainet.workManagement.ui.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.workManagement.dto.ScheduleOfRateDetDto;
import com.abm.mainet.workManagement.dto.WorkDefinitionDto;
import com.abm.mainet.workManagement.dto.WorkEstimateMasterDto;
import com.abm.mainet.workManagement.dto.WorkEstimateMeasureDetailsDto;
import com.abm.mainet.workManagement.dto.WorkOrderContractDetailsDto;
import com.abm.mainet.workManagement.service.WorkDefinitionService;
import com.abm.mainet.workManagement.service.WorkEstimateService;

/**
 * @author vishwajeet.kumar
 * @since 10 April 2019
 */
@Component
@Scope("session")
public class RevisedEstimateModel extends AbstractFormModel {

    private static final long serialVersionUID = -8696276809999484503L;

    @Autowired
    private WorkEstimateService estimateService;

    private String cpdModeHideSor;
    private Long newContractId;
    private String saveMode;
    private String requestFormFlag;
    private String workeReviseFlag;
    private String estimateRadioFlag;
    private Long reviseSorId;
    private Long revisedEstimateKey;
    private String removeREMsheet;
    private Long sorId;
    private Long newWorkId;
    private String removeNonSorById;
    private String ContractNo;
    private String removeEstimateDetails;
    private String removeSorDId;

    private ScheduleOfRateDetDto sorDetailsDto = new ScheduleOfRateDetDto();
    private List<LookUp> unitLookUpList = new ArrayList<>();
    private List<WorkOrderContractDetailsDto> workOrderContractDetailsDto = new ArrayList<>();
    private List<WorkEstimateMasterDto> revisedEstimateSorList = new ArrayList<WorkEstimateMasterDto>();
    private List<WorkEstimateMeasureDetailsDto> measureDetailsREList = new ArrayList<>();
    private Map<Long, List<WorkEstimateMeasureDetailsDto>> measureMentSubList = new HashMap<>();
    private List<WorkEstimateMasterDto> workEstimateNonSorFormList = new ArrayList<>();
    List<TbAcVendormaster> vendorList;
    private List chaperList;
    
    public List<TbAcVendormaster> getVendorList() {
		return vendorList;
	}

	public void setVendorList(List<TbAcVendormaster> vendorList) {
		this.vendorList = vendorList;
	}

	/**
     * save revised estimate Data
     * @param revisedEstimateDto
     */
    public void saveRevisedEstimateSORData(List<WorkEstimateMasterDto> revisedEstimateDto) {

        List<WorkEstimateMasterDto> revisedEstimateMasterList = new ArrayList<>();

        if (this.getWorkeReviseFlag().equals("N")) {
            WorkDefinitionDto definitionDto = ApplicationContextProvider.getApplicationContext()
                    .getBean(WorkDefinitionService.class)
                    .findAllWorkDefinitionById(this.getNewWorkId());
            revisedEstimateDto.forEach(estimateDto -> {
                estimateDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
                if (getMeasureMentSubList().containsKey(estimateDto.getSordId())) {
                    if (estimateDto.getCreatedBy() == null) {
                        estimateDto.setEstimateType("S");
                        estimateDto.setWorkEstimActive("Y");
                        // estimateDto.setWorkeReviseFlag(this.getEstimateRadioFlag());
                        estimateDto.setWorkeReviseFlag(this.getWorkeReviseFlag());
                        estimateDto.setWorkEstimFlag("S");
                        estimateDto.setWorkId(definitionDto.getWorkId());
                        estimateDto.setProjId(definitionDto.getProjId());
                        estimateDto.setContractId(this.getNewContractId());
                        estimateDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                        estimateDto.setCreatedDate(new Date());
                        estimateDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
                    } else {
                        estimateDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                        estimateDto.setUpdatedDate(new Date());
                        estimateDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
                    }
                    revisedEstimateMasterList.add(estimateDto);
                }
            });

        } else {
            revisedEstimateDto.forEach(estimateDto -> {
                estimateDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
                if (getMeasureMentSubList().containsKey(estimateDto.getSordId())) {
                    if (estimateDto.getCreatedBy() == null) {
                        estimateDto.setEstimateType("S");
                        estimateDto.setWorkEstimActive("Y");
                        estimateDto.setWorkeReviseFlag(this.getWorkeReviseFlag());
                        estimateDto.setWorkEstimFlag("S");
                        estimateDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                        estimateDto.setCreatedDate(new Date());
                        estimateDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
                        estimateDto.setWorkEstimQuantity(estimateDto.getReviseEstimQty());
                        estimateDto.setWorkEstimAmount(estimateDto.getWorkRevisedEstimAmount());
                    } else {
                        estimateDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                        estimateDto.setUpdatedDate(new Date());
                        estimateDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
                    }
                    revisedEstimateMasterList.add(estimateDto);
                }
            });
        }
        List<Long> MesureRemoveById = null;
        String fileId = getRemoveREMsheet();
        if (getRemoveREMsheet() != null && !getRemoveREMsheet().isEmpty()) {
            MesureRemoveById = new ArrayList<>();
            String fileArray[] = fileId.split(MainetConstants.operator.COMMA);
            for (String fields : fileArray) {
                MesureRemoveById.add(Long.valueOf(fields));
            }
        }

        List<Long> removeEstimateById = null;
        String id = getRemoveEstimateDetails();
        if (getRemoveEstimateDetails() != null && !getRemoveEstimateDetails().isEmpty()) {
            removeEstimateById = new ArrayList<>();
            String fileArray[] = id.split(MainetConstants.operator.COMMA);
            for (String fields : fileArray) {
                removeEstimateById.add(Long.valueOf(fields));
            }
        }

        estimateService.saveRevisedEstimate(revisedEstimateMasterList, getMeasureMentSubList(), MesureRemoveById,
                removeEstimateById);
    }

    /**
     * Save Revised Estimate LBH Data
     */
    public void saveRevisedEstimateLBHform() {
        List<WorkEstimateMeasureDetailsDto> listMS = new ArrayList<>();
        getMeasureDetailsREList().forEach(msReDetails -> {
            if (msReDetails.getCreatedBy() == null) {
                msReDetails.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
                msReDetails.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                msReDetails.setCreatedDate(new Date());
                msReDetails.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
            } else {
                msReDetails.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                msReDetails.setUpdatedDate(new Date());
                msReDetails.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
            }
            listMS.add(msReDetails);
        });

        this.getRevisedEstimateSorList().forEach(sorEstm -> {
            BigDecimal totalQuantityAmount = null;
            if (sorEstm.getSordId().equals(getRevisedEstimateKey())) {
                for (WorkEstimateMeasureDetailsDto worDetailsDto : listMS) {
                    if (worDetailsDto.getMeMentToltal() != null && totalQuantityAmount == null) {
                        totalQuantityAmount = worDetailsDto.getMeMentToltal();
                    } else {
                        totalQuantityAmount = totalQuantityAmount.add(worDetailsDto.getMeMentToltal());
                    }
                }
                if (this.getWorkeReviseFlag().equals("N")) {
                    sorEstm.setWorkEstimQuantity(totalQuantityAmount);
                    sorEstm.setWorkEstimAmount(sorEstm.getSorBasicRate().multiply(totalQuantityAmount));
                } else {
                    sorEstm.setReviseEstimQty(totalQuantityAmount);
                    sorEstm.setWorkRevisedEstimAmount(
                            sorEstm.getSorBasicRate().multiply(totalQuantityAmount).setScale(2, RoundingMode.HALF_EVEN));
                }
            }
        });

        measureMentSubList.put(getRevisedEstimateKey(), listMS);

    }

    /**
     * save Non Sor Data For Revised Estimate
     */
    public void saveNonSorDataForRevisedEstimate(List<WorkEstimateMasterDto> workEstimateMasterDtosList) {

        workEstimateMasterDtosList.forEach(nonSorDto -> {
            nonSorDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            if (nonSorDto.getCreatedBy() == null) {
                nonSorDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                nonSorDto.setWorkId(this.getNewWorkId());
                // nonSorDto.setWorkEstimPId(getRateAnalysisWorkId());
                nonSorDto.setContractId(this.getNewContractId());
                nonSorDto.setEstimateType("S");
                nonSorDto.setCreatedDate(new Date());
                nonSorDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
                nonSorDto.setWorkEstimActive(MainetConstants.IsDeleted.DELETE);
                nonSorDto.setWorkEstimFlag("N");
                nonSorDto.setWorkeReviseFlag(this.getWorkeReviseFlag());
            } else {
                nonSorDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                nonSorDto.setUpdatedDate(new Date());
                nonSorDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
            }
        });

        List<Long> nonSorRemoveById = null;
        String fileId = getRemoveNonSorById();
        if (fileId != null && !fileId.isEmpty()) {
            nonSorRemoveById = new ArrayList<>();
            String fileArray[] = fileId.split(MainetConstants.operator.COMMA);
            for (String fields : fileArray) {
                nonSorRemoveById.add(Long.valueOf(fields));
            }
        }
        estimateService.saveRevisedEstimate(getWorkEstimateNonSorFormList(), null, nonSorRemoveById, null);
    }

    // add Existing Estimate Revised Data
    public void addExistingRevisedEstimateData(String estimateType, Long contId, Long orgId) {
        if (estimateType.equals(MainetConstants.FlagS)) {
            /*
             * List<WorkEstimateMasterDto> SorDtosList = estimateService.getPreviousEstimateByContractId(contId, orgId,
             * estimateType);
             */
            List<WorkEstimateMasterDto> SorDtosList = estimateService.getPreviousEstimateByContractAndRevisedFlag(contId,
                    estimateType, orgId);
            List<WorkEstimateMasterDto> newRevisedEstimateSorList = new ArrayList<>();
            if (!SorDtosList.isEmpty()) {
                this.setNewWorkId(SorDtosList.get(0).getWorkId());
                SorDtosList.forEach(workReviseSor -> {
                    WorkEstimateMasterDto dto = new WorkEstimateMasterDto();
                    dto.setContractId(workReviseSor.getContractId());
                    dto.setEstimateType(workReviseSor.getEstimateType());
                    dto.setSorBasicRate(workReviseSor.getSorBasicRate());
                    dto.setSorDDescription(workReviseSor.getSorDDescription());
                    dto.setSorDIteamNo(workReviseSor.getSorDIteamNo());
                    dto.setSorIteamUnitDesc(workReviseSor.getSorIteamUnitDesc());
                    dto.setWorkEstimAmount(workReviseSor.getWorkEstimAmount());
                    dto.setWorkEstimFlag(workReviseSor.getWorkEstimFlag());
                    dto.setWorkEstimQuantity(workReviseSor.getWorkEstimQuantity());
                    dto.setWorkId(workReviseSor.getWorkId());
                    dto.setWorkPreviousId(workReviseSor.getWorkPreviousId());
                    dto.setProjId(workReviseSor.getProjId());
                    dto.setEstimateType("S");
                    dto.setSorId(workReviseSor.getSorId());
                    dto.setSordId(workReviseSor.getSordId());
                    dto.setSordCategory(workReviseSor.getSordCategory());
                    dto.setSorIteamUnit(workReviseSor.getSorIteamUnit());
                    // dto.setWorkeEstimateNo(workReviseSor.getWorkeEstimateNo());
                    // dto.setWorkeReviseFlag(MainetConstants.MODE_EDIT);
                    dto.setWorkEstimPId(workReviseSor.getWorkEstemateId());
                    newRevisedEstimateSorList.add(dto);
                });
                this.setRevisedEstimateSorList(newRevisedEstimateSorList);
                this.setWorkeReviseFlag(this.getWorkeReviseFlag());
            }
        } else if (estimateType.equals(MainetConstants.FlagN)) {
            List<WorkEstimateMasterDto> nonSorListAdd = estimateService.getPreviousEstimateByContractId(contId, orgId,
                    estimateType);
            List<WorkEstimateMasterDto> newNonSorRevisedEstimateList = new ArrayList<>();
            if (!nonSorListAdd.isEmpty()) {
                this.setNewWorkId(nonSorListAdd.get(0).getWorkId());
                nonSorListAdd.forEach(workReviseNonSor -> {
                    WorkEstimateMasterDto dto = new WorkEstimateMasterDto();
                    dto.setContractId(workReviseNonSor.getContractId());
                    dto.setEstimateType(workReviseNonSor.getEstimateType());
                    dto.setSorBasicRate(workReviseNonSor.getSorBasicRate());
                    dto.setSorDDescription(workReviseNonSor.getSorDDescription());
                    dto.setSorDIteamNo(workReviseNonSor.getSorDIteamNo());
                    dto.setSorDIteamNo(workReviseNonSor.getSorDIteamNo());
                    dto.setSorIteamUnitDesc(workReviseNonSor.getSorIteamUnitDesc());
                    dto.setSorIteamUnit(workReviseNonSor.getSorIteamUnit());
                    dto.setWorkEstimAmount(workReviseNonSor.getWorkEstimAmount());
                    dto.setWorkEstimFlag(workReviseNonSor.getWorkEstimFlag());
                    dto.setWorkEstimQuantity(workReviseNonSor.getWorkEstimQuantity());
                    dto.setWorkId(workReviseNonSor.getWorkId());
                    dto.setWorkPreviousId(workReviseNonSor.getWorkPreviousId());
                    newNonSorRevisedEstimateList.add(dto);
                });
            }
            this.setWorkEstimateNonSorFormList(newNonSorRevisedEstimateList);
        }
    }

    // edit And View Revised Estimate
    public void editAndViewRevisedEstimate(String estimateType, Long contId, Long orgId) {

        if (this.getWorkeReviseFlag().equals("N")) {
            if (estimateType.equals("S")) {
                if (getSaveMode().equals("V")) {
                    List<WorkEstimateMasterDto> masterDtos = estimateService.getAllRevEstmtByReviseFlag(contId, orgId,
                            estimateType,
                            this.getWorkeReviseFlag());
                    this.setRevisedEstimateSorList(masterDtos);
                }
                if (getSaveMode().equals("E")) {
                    List<WorkEstimateMasterDto> masterDtos = estimateService.getAllRevEstmtByReviseFlag(contId, orgId,
                            estimateType, this.getWorkeReviseFlag());
                    this.setRevisedEstimateSorList(masterDtos);
                    if (!masterDtos.isEmpty())
                        this.setReviseSorId(masterDtos.get(0).getSorId());
                }
            }
            if (estimateType.equals("N")) {
                if (getSaveMode().equals("V")) {
                    List<WorkEstimateMasterDto> masterDtos = estimateService.getAllRevEstmtByReviseFlag(contId, orgId,
                            estimateType,
                            this.getWorkeReviseFlag());
                    this.setWorkEstimateNonSorFormList(masterDtos);
                }
                if (getSaveMode().equals("E")) {
                    List<WorkEstimateMasterDto> masterDtos = estimateService.getAllRevEstmtByReviseFlag(contId, orgId,
                            estimateType,
                            this.getWorkeReviseFlag());
                    this.setWorkEstimateNonSorFormList(masterDtos);
                }
            }
        }
        if (this.getWorkeReviseFlag().equals("E")) {
            if (estimateType.equals(MainetConstants.FlagS)) {
                if (getSaveMode().equals(MainetConstants.FlagV)) {

                    List<WorkEstimateMasterDto> SorDtosList = estimateService.getPreviousEstimateByContractId(contId, orgId,
                            estimateType);
                    List<WorkEstimateMasterDto> newRevisedList = SorDtosList.stream()
                            .collect(Collectors.collectingAndThen(
                                    Collectors.toCollection(
                                            () -> new TreeSet<>(Comparator.comparingLong(WorkEstimateMasterDto::getSordId))),
                                    ArrayList::new));
                    List<WorkEstimateMasterDto> dtos = estimateService.getAllRevisedContarctEstimateDetailsByContrcatId(contId,
                            orgId, estimateType);

                    List<WorkEstimateMasterDto> finalList = new ArrayList<WorkEstimateMasterDto>();
                    for (WorkEstimateMasterDto uniq : newRevisedList) {
                        for (WorkEstimateMasterDto all : dtos) {
                            if (uniq.getSordId().equals(all.getSordId())) {
                                if ((all.getWorkEstemateId() != null)) {
                                    BigDecimal quantity = all.getWorkEstimQuantity();
                                    BigDecimal totalRevised = all.getWorkEstimAmount();
                                    all.setWorkEstimQuantity(uniq.getWorkEstimQuantity());
                                    all.setWorkEstimAmount(uniq.getWorkEstimAmount());
                                    all.setReviseEstimQty(quantity);
                                    all.setWorkRevisedEstimAmount(totalRevised);
                                    finalList.add(all);
                                }
                            }
                        }
                    }
                    this.setRevisedEstimateSorList(finalList);
                    this.setWorkeReviseFlag(dtos.get(0).getWorkeReviseFlag());
                } else if (getSaveMode().equals(MainetConstants.FlagE)) {
                    List<WorkEstimateMasterDto> SorDtosList = estimateService.getPreviousEstimateByContractId(contId, orgId,
                            estimateType);
                    List<WorkEstimateMasterDto> newRevisedEstimateSorList = new ArrayList<>();
                    if (!SorDtosList.isEmpty()) {
                        this.setNewWorkId(SorDtosList.get(0).getWorkId());
                        // this.setWorkeReviseFlag(SorDtosList.get(0).getWorkeReviseFlag());
                        SorDtosList.forEach(workReviseSor -> {
                            if (StringUtils.isNotEmpty(workReviseSor.getWorkeReviseFlag())) {
                                newRevisedEstimateSorList.add(workReviseSor);
                            } else {
                                WorkEstimateMasterDto dto = new WorkEstimateMasterDto();
                                dto.setContractId(workReviseSor.getContractId());
                                dto.setEstimateType(workReviseSor.getEstimateType());
                                dto.setSorBasicRate(workReviseSor.getSorBasicRate());
                                dto.setSorDDescription(workReviseSor.getSorDDescription());
                                dto.setSorDIteamNo(workReviseSor.getSorDIteamNo());
                                dto.setSorIteamUnitDesc(workReviseSor.getSorIteamUnitDesc());
                                dto.setWorkEstimAmount(workReviseSor.getWorkEstimAmount());
                                dto.setWorkEstimFlag(workReviseSor.getWorkEstimFlag());
                                dto.setWorkEstimQuantity(workReviseSor.getWorkEstimQuantity());
                                dto.setWorkId(workReviseSor.getWorkId());
                                dto.setWorkPreviousId(workReviseSor.getWorkPreviousId());
                                dto.setProjId(workReviseSor.getProjId());
                                dto.setEstimateType("S");
                                dto.setSorId(workReviseSor.getSorId());
                                dto.setSordId(workReviseSor.getSordId());
                                dto.setSordCategory(workReviseSor.getSordCategory());
                                dto.setSorIteamUnit(workReviseSor.getSorIteamUnit());
                                // dto.setWorkeEstimateNo(workReviseSor.getWorkeEstimateNo());
                                dto.setWorkEstimPId(workReviseSor.getWorkEstemateId());
                                newRevisedEstimateSorList.add(dto);
                            }
                        });

                        /**
                         * Unique Data In our List
                         * @newRevisedEstimateSorList is Orginal Data It Consist All Data
                         */
                        List<WorkEstimateMasterDto> newRevisedList1 = newRevisedEstimateSorList.stream()
                                .collect(Collectors.collectingAndThen(
                                        Collectors.toCollection(
                                                () -> new TreeSet<>(Comparator.comparingLong(WorkEstimateMasterDto::getSordId))),
                                        ArrayList::new));
                        int count = 0;
                        List<WorkEstimateMasterDto> finalList = new ArrayList<WorkEstimateMasterDto>();
                        for (WorkEstimateMasterDto uniq : newRevisedList1) {
                            for (WorkEstimateMasterDto all : newRevisedEstimateSorList) {
                                if (uniq.getSordId().equals(all.getSordId())) {
                                    count++;
                                    if ((all.getWorkEstemateId() != null)) {
                                        BigDecimal quantity = all.getWorkEstimQuantity();
                                        BigDecimal totalRevised = all.getWorkEstimAmount();
                                        all.setWorkEstimQuantity(uniq.getWorkEstimQuantity());
                                        all.setWorkEstimAmount(uniq.getWorkEstimAmount());
                                        all.setReviseEstimQty(quantity);
                                        all.setWorkRevisedEstimAmount(totalRevised);
                                        finalList.add(all);
                                    }
                                }
                            }
                            if (count == 1) {
                                finalList.add(uniq);
                            }
                        }
                        this.setRevisedEstimateSorList(finalList);
                        this.setWorkeReviseFlag(MainetConstants.FlagE);
                    }
                }
            } else if (estimateType.equals(MainetConstants.FlagN)) {
                if (getSaveMode().equals(MainetConstants.FlagV)) {
                    List<WorkEstimateMasterDto> nonSorList = estimateService.getAllRevisedContarctEstimateDetailsByContrcatId(
                            contId,
                            orgId, estimateType);
                    this.setWorkEstimateNonSorFormList(nonSorList);
                } else {
                    List<WorkEstimateMasterDto> nonSorListAdd = estimateService.getPreviousEstimateByContractId(contId, orgId,
                            estimateType);
                    List<WorkEstimateMasterDto> newRevisedEstimateList = new ArrayList<>();
                    if (!nonSorListAdd.isEmpty()) {
                        this.setNewWorkId(nonSorListAdd.get(0).getWorkId());
                        nonSorListAdd.forEach(workReviseNonSor -> {
                            if (StringUtils.isNotEmpty(workReviseNonSor.getWorkeReviseFlag())) {
                                newRevisedEstimateList.add(workReviseNonSor);
                            } else {
                                WorkEstimateMasterDto dto = new WorkEstimateMasterDto();
                                dto.setContractId(workReviseNonSor.getContractId());
                                dto.setEstimateType(workReviseNonSor.getEstimateType());
                                dto.setSorBasicRate(workReviseNonSor.getSorBasicRate());
                                dto.setSorDDescription(workReviseNonSor.getSorDDescription());
                                // dto.setSorDIteamNo(workReviseNonSor.getSorDIteamNo());
                                dto.setSorDIteamNo(workReviseNonSor.getSorDIteamNo());
                                dto.setSorIteamUnit(workReviseNonSor.getSorIteamUnit());
                                dto.setSorIteamUnitDesc(workReviseNonSor.getSorIteamUnitDesc());
                                dto.setWorkEstimAmount(workReviseNonSor.getWorkEstimAmount());
                                dto.setWorkEstimFlag(workReviseNonSor.getWorkEstimFlag());
                                dto.setWorkEstimQuantity(workReviseNonSor.getWorkEstimQuantity());
                                dto.setWorkId(workReviseNonSor.getWorkId());
                                dto.setWorkPreviousId(workReviseNonSor.getWorkPreviousId());
                                newRevisedEstimateList.add(dto);
                            }
                        });
                    }
                    this.setWorkEstimateNonSorFormList(newRevisedEstimateList);
                }
            }
        }

    }

    // Set All Work Flow related Data
    public WorkflowTaskAction prepareWorkFlowRevisedEstimate() {

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
        taskAction.setDecision("SUBMITTED");
        return taskAction;
    }

    public String getCpdModeHideSor() {
        return cpdModeHideSor;
    }

    public void setCpdModeHideSor(String cpdModeHideSor) {
        this.cpdModeHideSor = cpdModeHideSor;
    }

    public Long getNewContractId() {
        return newContractId;
    }

    public void setNewContractId(Long newContractId) {
        this.newContractId = newContractId;
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

    public Long getReviseSorId() {
        return reviseSorId;
    }

    public void setReviseSorId(Long reviseSorId) {
        this.reviseSorId = reviseSorId;
    }

    public Long getRevisedEstimateKey() {
        return revisedEstimateKey;
    }

    public void setRevisedEstimateKey(Long revisedEstimateKey) {
        this.revisedEstimateKey = revisedEstimateKey;
    }

    public String getRemoveREMsheet() {
        return removeREMsheet;
    }

    public void setRemoveREMsheet(String removeREMsheet) {
        this.removeREMsheet = removeREMsheet;
    }

    public Long getSorId() {
        return sorId;
    }

    public void setSorId(Long sorId) {
        this.sorId = sorId;
    }

    public Long getNewWorkId() {
        return newWorkId;
    }

    public void setNewWorkId(Long newWorkId) {
        this.newWorkId = newWorkId;
    }

    public String getRemoveNonSorById() {
        return removeNonSorById;
    }

    public void setRemoveNonSorById(String removeNonSorById) {
        this.removeNonSorById = removeNonSorById;
    }

    public ScheduleOfRateDetDto getSorDetailsDto() {
        return sorDetailsDto;
    }

    public String getContractNo() {
        return ContractNo;
    }

    public void setContractNo(String contractNo) {
        ContractNo = contractNo;
    }

    public void setSorDetailsDto(ScheduleOfRateDetDto sorDetailsDto) {
        sorDetailsDto.setSorIteamUnitDesc(CommonMasterUtility.getCPDDescription(sorDetailsDto.getSorIteamUnit().longValue(),
                MainetConstants.WorksManagement.EDIT));
        this.sorDetailsDto = sorDetailsDto;
    }

    public String getRemoveEstimateDetails() {
        return removeEstimateDetails;
    }

    public void setRemoveEstimateDetails(String removeEstimateDetails) {
        this.removeEstimateDetails = removeEstimateDetails;
    }

    public String getRemoveSorDId() {
        return removeSorDId;
    }

    public void setRemoveSorDId(String removeSorDId) {
        this.removeSorDId = removeSorDId;
    }

    public List<LookUp> getUnitLookUpList() {
        return unitLookUpList;
    }

    public void setUnitLookUpList(List<LookUp> unitLookUpList) {
        this.unitLookUpList = unitLookUpList;
    }

    public List<WorkOrderContractDetailsDto> getWorkOrderContractDetailsDto() {
        return workOrderContractDetailsDto;
    }

    public void setWorkOrderContractDetailsDto(List<WorkOrderContractDetailsDto> workOrderContractDetailsDto) {
        this.workOrderContractDetailsDto = workOrderContractDetailsDto;
    }

    public List<WorkEstimateMasterDto> getRevisedEstimateSorList() {
        return revisedEstimateSorList;
    }

    public void setRevisedEstimateSorList(List<WorkEstimateMasterDto> revisedEstimateSorList) {
        this.revisedEstimateSorList = revisedEstimateSorList;
    }

    public List<WorkEstimateMeasureDetailsDto> getMeasureDetailsREList() {
        return measureDetailsREList;
    }

    public void setMeasureDetailsREList(List<WorkEstimateMeasureDetailsDto> measureDetailsREList) {
        this.measureDetailsREList = measureDetailsREList;
    }

    public Map<Long, List<WorkEstimateMeasureDetailsDto>> getMeasureMentSubList() {
        return measureMentSubList;
    }

    public void setMeasureMentSubList(Map<Long, List<WorkEstimateMeasureDetailsDto>> measureMentSubList) {
        this.measureMentSubList = measureMentSubList;
    }

    public List<WorkEstimateMasterDto> getWorkEstimateNonSorFormList() {
        return workEstimateNonSorFormList;
    }

    public void setWorkEstimateNonSorFormList(List<WorkEstimateMasterDto> workEstimateNonSorFormList) {
        this.workEstimateNonSorFormList = workEstimateNonSorFormList;
    }

	public List getChaperList() {
		return chaperList;
	}

	public void setChaperList(List chaperList) {
		this.chaperList = chaperList;
	}
    
}
