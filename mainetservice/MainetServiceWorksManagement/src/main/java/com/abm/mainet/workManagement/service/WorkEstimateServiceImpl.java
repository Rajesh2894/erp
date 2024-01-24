package com.abm.mainet.workManagement.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.dao.IAttachDocsDao;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.IContractAgreementService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.workManagement.domain.MbOverHeadDetails;
import com.abm.mainet.workManagement.domain.WorkEstimOverHeadDetails;
import com.abm.mainet.workManagement.domain.WorkEstimateMaster;
import com.abm.mainet.workManagement.domain.WorkEstimateMasterHistory;
import com.abm.mainet.workManagement.dto.MbOverHeadDetDto;
import com.abm.mainet.workManagement.dto.TenderMasterDto;
import com.abm.mainet.workManagement.dto.TenderWorkDto;
import com.abm.mainet.workManagement.dto.WorkEstimOverHeadDetDto;
import com.abm.mainet.workManagement.dto.WorkEstimateMasterDto;
import com.abm.mainet.workManagement.dto.WorkEstimateMeasureDetailsDto;
import com.abm.mainet.workManagement.repository.WorkEstimOverHeadRepository;
import com.abm.mainet.workManagement.repository.WorkEstimateRepository;

@Service
public class WorkEstimateServiceImpl implements WorkEstimateService {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private WorksMeasurementSheetService measurementSheetService;

    @Autowired
    private WorkEstimateRepository workEstimateRepository;

    @Autowired
    private WorkEstimOverHeadRepository workEstimOverHeadDetailsRepository;

    private static final Logger LOGGER = Logger.getLogger(WorkEstimateServiceImpl.class);

    @Override
    @Transactional
    public void saveWorkEstimateList(List<WorkEstimateMasterDto> workEstimateList, List<Long> removeWorkIdList,
            String approvalFlag) {

        String workEstimateNo = null;
        WorkEstimateMaster entity = null;
        List<WorkEstimateMaster> entityList = new ArrayList<>();
        List<Object> historyList = new ArrayList<>();
        List<WorkEstimateMasterDto> dtos = null;
        Long deptId = null;
        if (workEstimateList != null && !workEstimateList.isEmpty()) {
            Long orgId = workEstimateList.get(0).getOrgId();
            Long workId = workEstimateList.get(0).getWorkId();

            dtos = getWorkEstimateByWorkId(workId, orgId);
            if (workEstimateList.get(0).getContractId() != null)
                deptId = ApplicationContextProvider.getApplicationContext().getBean(IContractAgreementService.class)
                        .findById(workEstimateList.get(0).getContractId(), orgId).getContDept();
            else {
                deptId = ApplicationContextProvider.getApplicationContext().getBean(WmsProjectMasterService.class)
                        .getProjectMasterByProjId(workEstimateList.get(0).getProjId()).getDpDeptId();
            }
            if (dtos != null && !dtos.isEmpty()) {
                workEstimateNo = dtos.get(0).getWorkeEstimateNo();
            }

            for (WorkEstimateMasterDto dto : workEstimateList) {
                entity = new WorkEstimateMaster();
                BeanUtils.copyProperties(dto, entity);
                if (workEstimateNo != null) {
                    entity.setWorkeEstimateNo(workEstimateNo);
                } else {
                    workEstimateNo = updateWorkEstimateNo(new Date(), orgId, deptId);
                    entity.setWorkeEstimateNo(workEstimateNo);
                }
                entityList.add(entity);
            }
            workEstimateRepository.save(entityList);

           // if (approvalFlag != null && approvalFlag.equals(MainetConstants.WorksManagement.APPROVAL)) {
                entityList.forEach(e -> {
                    WorkEstimateMasterHistory entityHist = new WorkEstimateMasterHistory();
                    BeanUtils.copyProperties(e, entityHist);
                    entityHist.setWorkEstimStatus(approvalFlag);
                    
                    if (removeWorkIdList!=null && !removeWorkIdList.isEmpty() && removeWorkIdList.contains(entityHist.getWorkEstemateId())) {
                    	entityHist.setWorkEstimStatus(MainetConstants.FlagD); 
                    }

                    historyList.add(entityHist);
                });
                try {
                    ApplicationContextProvider.getApplicationContext().getBean(AuditService.class)
                            .createHistoryForListObj(historyList);
                } catch (Exception exception) {
                	 throw new FrameworkException("Exception occured when create history of work Estimate Service  ", exception);
                }
          //  }
            updateTotalEstimateAmount(workId);
        }
        if (removeWorkIdList != null && !removeWorkIdList.isEmpty()) {
            workEstimateRepository.updateDeletedFlag(removeWorkIdList);
        	workEstimateRepository.updateDeleteflag(removeWorkIdList);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkEstimateMasterDto> getWorkEstimateByWorkId(Long workId, long orgid) {
        List<WorkEstimateMaster> listEntity = workEstimateRepository.getWorkEstimateByWorkId(workId, orgid);
        List<WorkEstimateMasterDto> dtoList = new ArrayList<>();
        Organisation organisation = new Organisation();
        organisation.setOrgid(orgid);
        listEntity.forEach(entity -> {
            WorkEstimateMasterDto obj = new WorkEstimateMasterDto();
            BeanUtils.copyProperties(entity, obj);
            if (obj.getWorkEstimFlag().equals(MainetConstants.FlagN)) {
                obj.setWorkMbFlag(MainetConstants.WorksManagement.NonSOR);
            } else if (obj.getWorkEstimFlag().equals(MainetConstants.FlagD)) {
                obj.setWorkMbFlag(MainetConstants.WorksManagement.DirectSOR);
            } else if (obj.getWorkEstimFlag().equals(MainetConstants.WorksManagement.MN)) {
                obj.setWorkMbFlag(MainetConstants.WorksManagement.NONSOR_ADDITIONAL);
            } else if (obj.getWorkEstimFlag().equals(MainetConstants.WorksManagement.MS)) {
                obj.setWorkMbFlag(MainetConstants.WorksManagement.SOR_ADDITIONAL);
            } else {
                obj.setWorkMbFlag(MainetConstants.WorksManagement.SOR_TYPE);
            }
            if (obj.getSorIteamUnit() != null) {
                obj.setSorIteamUnitDesc(CommonMasterUtility
                        .getNonHierarchicalLookUpObject(obj.getSorIteamUnit(), organisation).getDescLangFirst());
            }

            if (obj.getSordCategory() != null) {
                obj.setSordCategoryStr(
                        CommonMasterUtility.getCPDDescription(obj.getSordCategory().longValue(), MainetConstants.MENU.E));
            }
            dtoList.add(obj);
        });
        return dtoList;
    }

    @Override
    @Transactional
    public void setInActiveWorkEstimateByWorkId(List<Long> workIDList) {
        workEstimateRepository.setInActiveWorkEstimateByWorkId(workIDList);

    }

    @Override
    @Transactional(readOnly = true)
    public List<Long> getAllActiveDistinctWorkId(long orgid) {
        return workEstimateRepository.getAllActiveDistinctWorkId(orgid);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkEstimateMasterDto> getEstimationByWorkIdAndType(Long workId, String workType) {
        List<WorkEstimateMaster> listEntity = workEstimateRepository.getEstimationByWorkIdAndType(workId, workType);
        List<WorkEstimateMasterDto> dtoList = new ArrayList<>();
        listEntity.forEach(entity -> {
            WorkEstimateMasterDto obj = new WorkEstimateMasterDto();
            BeanUtils.copyProperties(entity, obj);
            if (entity.getSorIteamUnit() != null) {
                obj.setSorIteamUnitDesc(
                        CommonMasterUtility.getCPDDescription(entity.getSorIteamUnit().longValue(), MainetConstants.MENU.E));
            }
            if (entity.getSordCategory() != null) {
                obj.setSordCategoryStr(
                        CommonMasterUtility.getCPDDescription(entity.getSordCategory().longValue(), MainetConstants.MENU.E));
            }
            List<WorkEstimateMasterDto> dtos = getRateAnalysisListByEstimateId(obj.getWorkEstemateId(), workType);
            List<WorkEstimateMeasureDetailsDto> detailsDtos = measurementSheetService
                    .getWorkEstimateDetailsByWorkEId(obj.getWorkEstemateId());
            if (!(dtos.isEmpty() && detailsDtos.isEmpty()))
                obj.setChildAvailable(true);
            dtoList.add(obj);
        });
        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calculateTotalEstimatedAmountByWorkId(Long workEstemateId) {
        return workEstimateRepository.calculateTotalEstimatedAmountByWorkId(workEstemateId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkEstimateMasterDto> getRateAnalysisListByEstimateId(Long workEstemateId, String flag) {
        List<WorkEstimateMaster> listEntity = workEstimateRepository.getRateAnalysisListByEstimateId(workEstemateId,
                flag);
        List<WorkEstimateMasterDto> dtoList = new ArrayList<>();
        listEntity.forEach(entity -> {
            WorkEstimateMasterDto obj = new WorkEstimateMasterDto();
            BeanUtils.copyProperties(entity, obj);
            dtoList.add(obj);
        });
        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkEstimateMasterDto> getChildRateAnalysisListByMaterialId(Long materialId, Long workId) {
        List<WorkEstimateMaster> listEntity = workEstimateRepository.getChildRateAnalysisListByMaterialId(materialId,
                workId);
        List<WorkEstimateMasterDto> dtoList = new ArrayList<>();
        listEntity.forEach(entity -> {
            WorkEstimateMasterDto obj = new WorkEstimateMasterDto();
            BeanUtils.copyProperties(entity, obj);
            dtoList.add(obj);
        });
        return dtoList;
    }

    @Override
    @Transactional
    public void saveOverHeadList(List<WorkEstimOverHeadDetDto> estimOverHeadDetDto, List<Long> overHeadRemoveById) {
        List<WorkEstimOverHeadDetails> overHeadList = new ArrayList<>();
        estimOverHeadDetDto.forEach(dto -> {
            WorkEstimOverHeadDetails entity = new WorkEstimOverHeadDetails();
            BeanUtils.copyProperties(dto, entity);
            if (dto.getOverHeadCode() != null && !dto.getOverHeadCode().isEmpty())
                overHeadList.add(entity);
        });

        workEstimOverHeadDetailsRepository.save(overHeadList);

        if (overHeadRemoveById != null && !overHeadRemoveById.isEmpty())
            workEstimOverHeadDetailsRepository.updateDeletedFlagForOverHeads(overHeadRemoveById);

        if (estimOverHeadDetDto != null && !estimOverHeadDetDto.isEmpty()) {
            BigDecimal overheadAmount = workEstimateRepository
                    .getOverheadAmount(estimOverHeadDetDto.get(0).getWorkId());

            ApplicationContextProvider.getApplicationContext().getBean(WorkDefinitionService.class)
                    .updateOverHeadAmount(estimOverHeadDetDto.get(0).getWorkId(), overheadAmount);
            updateTotalEstimateAmount(estimOverHeadDetDto.get(0).getWorkId());
        }
    }

    @Override
    @Transactional

    public void deleteEnclosureFileById(List<Long> enclosureRemoveById, Long empId) {

        ApplicationContextProvider.getApplicationContext().getBean(IAttachDocsDao.class).updateRecord(enclosureRemoveById, empId,
                MainetConstants.FlagD);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkEstimOverHeadDetDto> getEstimateOverHeadDetByWorkId(Long workId) {
        List<WorkEstimOverHeadDetails> listEntity = workEstimateRepository.getEstimateOverHeadDetByWorkId(workId);
        List<WorkEstimOverHeadDetDto> dtoList = new ArrayList<>();
        listEntity.forEach(entity -> {
            WorkEstimOverHeadDetDto obj = new WorkEstimOverHeadDetDto();
            BeanUtils.copyProperties(entity, obj);
			
			  if(entity.getMbOverheadDetails() != null) {
			  
			  MbOverHeadDetDto detDto=new MbOverHeadDetDto();
			  BeanUtils.copyProperties(entity.getMbOverheadDetails(), detDto);
			  obj.setMbOverheadDetails(detDto); }
			 
            dtoList.add(obj);
        });
        return dtoList;
    }

    @Override
    @Transactional
    public void updateWorkEsimateLbhQunatity(Long measurementWorkId, BigDecimal totalAmount) {
        workEstimateRepository.updateWorkEsimateLbhQunatity(measurementWorkId, totalAmount);

    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkEstimateMasterDto> getAllRateTypeMBByEstimateNo(Long estmiateId, Long mbId, Long mbDetId) {
        WorkEstimateMasterDto obj = null;

        List<WorkEstimateMaster> listEntity = workEstimateRepository.getAllRateTypeMBByEstimateNo(estmiateId);
        List<WorkEstimateMasterDto> dtoList = new ArrayList<>();
        for (WorkEstimateMaster entity : listEntity) {
            obj = new WorkEstimateMasterDto();
            BeanUtils.copyProperties(entity, obj);
            obj.setMbId(mbId);
            obj.setMbDetId(mbDetId);
            if (entity.getSorIteamUnit() != null)
                obj.setSorIteamUnitDesc(
                        CommonMasterUtility.getCPDDescription(entity.getSorIteamUnit().longValue(), MainetConstants.MENU.E));
            dtoList.add(obj);
        }
        return dtoList;
    }

    public String updateWorkEstimateNo(Date estimateDate, Long orgId, Long deptId) {

        // get financial by date
        FinancialYear financiaYear = ApplicationContextProvider.getApplicationContext().getBean(TbFinancialyearService.class)
                .getFinanciaYearByDate(estimateDate);

        // get financial year formate
        String finacialYear = Utility.getFinancialYear(financiaYear.getFaFromDate(), financiaYear.getFaToDate());

        // gerenerate sequence
        final Long sequence = ApplicationContextProvider.getApplicationContext().getBean(SeqGenFunctionUtility.class)
                .generateSequenceNo(
                        MainetConstants.WorksManagement.WORKS_MANAGEMENT,
                        MainetConstants.WorksManagement.TB_WMS_WORKESTIMATE_MAS,
                        MainetConstants.WorksManagement.WORKE_ESTIMATE_NO, orgId, MainetConstants.FlagC,
                        financiaYear.getFaYear());
        String deptCode = departmentService.getDeptCode(deptId);

        String mbNumber = deptCode + MainetConstants.WINDOWS_SLASH + finacialYear + MainetConstants.WINDOWS_SLASH
                + String.format(MainetConstants.CommonMasterUi.PADDING_SIX, sequence);

        return mbNumber;
    }

    @Override
    @Transactional
    public void updateRateValues(List<WorkEstimateMasterDto> WorkEstimateMasterDto) {
        List<WorkEstimateMaster> listEntity = new ArrayList<>();
        WorkEstimateMasterDto.forEach(dto -> {
            WorkEstimateMaster entity = new WorkEstimateMaster();
            BeanUtils.copyProperties(dto, entity);
            listEntity.add(entity);
        });
        workEstimateRepository.save(listEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkEstimateMasterDto> getAuditRateByEstimateId(Long workEstemateId) {
        List<WorkEstimateMasterHistory> listEntity = workEstimateRepository.getAuditRateByEstimateId(workEstemateId);
        List<WorkEstimateMasterDto> dtoList = new ArrayList<>();
        listEntity.forEach(entity -> {
            WorkEstimateMasterDto obj = new WorkEstimateMasterDto();
            BeanUtils.copyProperties(entity, obj);
            dtoList.add(obj);
        });
        return dtoList;
    }

    @Override
    @Transactional
    public List<WorkEstimateMasterDto> getAllEstimateByTenderId(Long tenderId) {

        List<Long> workId = new ArrayList<>();
        List<WorkEstimateMasterDto> dtoList = new ArrayList<>();
        List<WorkEstimateMaster> listEntity = null;
        WorkEstimateMasterDto obj = null;

        TenderMasterDto tendermstdto = ApplicationContextProvider.getApplicationContext().getBean(TenderInitiationService.class)
                .getTenderDetailsByTenderId(tenderId);
        for (TenderWorkDto tenderWorkDto : tendermstdto.getWorkDto()) {
            workId.add(tenderWorkDto.getWorkId());
        }

        if (!workId.isEmpty()) {
            listEntity = workEstimateRepository.getAllTenderEstimates(workId);
            for (WorkEstimateMaster entity : listEntity) {
                obj = new WorkEstimateMasterDto();
                BeanUtils.copyProperties(entity, obj);
                if (obj.getWorkEstimFlag().equals(MainetConstants.FlagS)) {
                    obj.setWorkMbFlag(MainetConstants.WorksManagement.SOR_TYPE);
                } else {
                    obj.setWorkMbFlag(MainetConstants.WorksManagement.NonSOR);
                }
                obj.setSorIteamUnitDesc(
                        CommonMasterUtility.getCPDDescription(obj.getSorIteamUnit().longValue(), MainetConstants.MENU.E));
                dtoList.add(obj);
            }
        }
        return dtoList;
    }

    @Override
    @Transactional
    public void updateContractId(Long workId, Long contId, Long empId) {
        workEstimateRepository.updateContractId(workId, contId, empId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkEstimateMasterDto> getAllRevisedContarctEstimateDetailsByContrcatId(Long contractId, Long orgId,
            String workEstimateType) {

        List<WorkEstimateMaster> listEntity = new ArrayList<>();

        if (workEstimateType != null) {
            listEntity = workEstimateRepository
                    .getAllRevisedContarctEstimateDetailsByContrcatIdWithEstimateType(contractId, workEstimateType);
        } else {
            listEntity = workEstimateRepository.getAllRevisedContarctEstimateDetailsByContrcatId(contractId);
        }

        List<WorkEstimateMasterDto> dtoList = new ArrayList<>();
        Organisation organisation = new Organisation();
        organisation.setOrgid(orgId);
        listEntity.forEach(entity -> {
            WorkEstimateMasterDto obj = new WorkEstimateMasterDto();
            BeanUtils.copyProperties(entity, obj);

            if (obj.getSorIteamUnit() != null)
                obj.setSorIteamUnitDesc(CommonMasterUtility
                        .getNonHierarchicalLookUpObject(obj.getSorIteamUnit(), organisation).getDescLangFirst());

            dtoList.add(obj);
        });
        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkEstimateMasterDto> getPreviousEstimateByContractId(Long contractId, Long orgId,
            String workEstimateType) {

        List<WorkEstimateMaster> listEntity = new ArrayList<>();
        if (workEstimateType != null) {
            listEntity = workEstimateRepository.getPreviousEstimateByContractIdWithEstimateType(contractId,
                    workEstimateType);
        } else {
            listEntity = workEstimateRepository.getPreviousEstimateByContractId(contractId);
        }

        List<WorkEstimateMasterDto> dtoList = new ArrayList<>();
        if (listEntity != null && !listEntity.isEmpty()) {
            Organisation organisation = new Organisation();
            organisation.setOrgid(orgId);
            listEntity.forEach(entity -> {
                WorkEstimateMasterDto obj = new WorkEstimateMasterDto();
                BeanUtils.copyProperties(entity, obj);
                // obj.setWorkeReviseFlag(MainetConstants.MODE_EDIT);

                if (obj.getSorIteamUnit() != null)
                    obj.setSorIteamUnitDesc(CommonMasterUtility
                            .getNonHierarchicalLookUpObject(obj.getSorIteamUnit(), organisation).getDescLangFirst());
                // obj.setWorkEstemateId(null);
                dtoList.add(obj);
            });
        }
        return dtoList;
    }

    @Override
    @Transactional
    public void updateParentWorkEstimationAmount(Map<Long, BigDecimal> estimatedAmtAndId, Long WorkId, Long orgId) {

        List<Long> estimatedIds = new ArrayList<>();
        estimatedAmtAndId.forEach((key, value) -> {
            estimatedIds.add(key);
        });
        List<WorkEstimateMaster> estimateList = (List<WorkEstimateMaster>) workEstimateRepository.findAll(estimatedIds);

        estimatedAmtAndId.forEach((key, value) -> {
            estimateList.forEach(e -> {
                if (key.longValue() == e.getWorkEstemateId().longValue()) {
                    e.setWorkEstimAmount(value);
                }
            });
        });
        workEstimateRepository.save(estimateList);
        updateTotalEstimateAmount(WorkId);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getTotalEstimateAmount(Long workId) {
        BigDecimal sorAndNonSorAmount = workEstimateRepository.getSorAndNonSorAmount(workId);
        BigDecimal overheadAmount = workEstimateRepository.getOverheadAmount(workId);
        if (overheadAmount != null && sorAndNonSorAmount != null) {
            sorAndNonSorAmount = sorAndNonSorAmount.add(overheadAmount);
        }
        return sorAndNonSorAmount;
    }

    public void updateTotalEstimateAmount(Long workId) {
        BigDecimal sorAndNonSorAmount = workEstimateRepository.getSorAndNonSorAmount(workId);
        BigDecimal overheadAmount = workEstimateRepository.getOverheadAmount(workId);
        if (overheadAmount != null && sorAndNonSorAmount != null) {
            sorAndNonSorAmount = sorAndNonSorAmount.add(overheadAmount);
        }

        ApplicationContextProvider.getApplicationContext().getBean(WorkDefinitionService.class).updateTotalEstimatedAmount(workId,
                sorAndNonSorAmount);
    }

    @Override
    @Transactional
    public BigDecimal calculateTotalWorkEstimate(Long contId, Long orgId) {

        return workEstimateRepository.calculateTotalEstimatedAmountByContId(contId, orgId);
    }

    @Override
    @Transactional
    public String findWorkEstimateNoByWorkId(Long workId, Long orgId) {

        return workEstimateRepository.findWorkEstimateNoByWorkId(workId, orgId);
    }

    @Override
    public WorkEstimateMasterDto findById(Long orgId, Long workEstimateId) {
        WorkEstimateMasterDto dto = null;
        WorkEstimateMaster entity = workEstimateRepository.findById(orgId, workEstimateId);
        if (entity != null) {
            dto = new WorkEstimateMasterDto();
            BeanUtils.copyProperties(entity, dto);
        }
        return dto;
    }

    @Override
    @Transactional
    public void updateWorkEsimateLbhUtlQunatity(Long measurementWorkId, BigDecimal utlQuantity) {
        workEstimateRepository.updateWorkEsimateLbhUtlQunatity(measurementWorkId, utlQuantity);
    }

    @Override
    @Transactional
    public void saveRevisedEstimate(List<WorkEstimateMasterDto> revisedEstimateMasterList,
            Map<Long, List<WorkEstimateMeasureDetailsDto>> measureMentSubList, List<Long> mesureRemoveByIdList,
            List<Long> removeWorkIdList) {
        String workEstimateNo = null;
        WorkEstimateMaster entity = null;
        List<WorkEstimateMaster> entityList = new ArrayList<>();
        List<WorkEstimateMasterDto> dtos = null;
        Long deptId = null;

        if (revisedEstimateMasterList != null && !revisedEstimateMasterList.isEmpty()) {
            Long orgId = revisedEstimateMasterList.get(0).getOrgId();
            Long workId = revisedEstimateMasterList.get(0).getWorkId();

            dtos = getWorkEstimateByWorkId(workId, orgId);
            if (revisedEstimateMasterList.get(0).getContractId() != null)
                deptId = ApplicationContextProvider.getApplicationContext().getBean(IContractAgreementService.class)
                        .findById(revisedEstimateMasterList.get(0).getContractId(), orgId).getContDept();
            else {
                deptId = ApplicationContextProvider.getApplicationContext().getBean(WmsProjectMasterService.class)
                        .getProjectMasterByProjId(revisedEstimateMasterList.get(0).getProjId()).getDpDeptId();
            }
            if (dtos != null && !dtos.isEmpty()) {
                workEstimateNo = dtos.get(0).getWorkeEstimateNo();
            }

            for (WorkEstimateMasterDto dto : revisedEstimateMasterList) {
                entity = new WorkEstimateMaster();
                BeanUtils.copyProperties(dto, entity);
                if (workEstimateNo != null) {
                    entity.setWorkeEstimateNo(workEstimateNo);
                } else {
                    workEstimateNo = updateWorkEstimateNo(new Date(), orgId, deptId);
                    entity.setWorkeEstimateNo(workEstimateNo);
                }
                entityList.add(entity);
            }
            Iterable<WorkEstimateMaster> workRevisedEstimationList = workEstimateRepository.save(entityList);
            if (measureMentSubList != null) {
                workRevisedEstimationList.forEach(reviseEstimate -> {
                    if (measureMentSubList.containsKey(reviseEstimate.getSordId())) {
                        measureMentSubList.get(reviseEstimate.getSordId()).forEach(rev -> {
                            rev.setWorkEstemateId(reviseEstimate.getWorkEstemateId());
                            rev.setMeMentActive("Y");
                        });
                        measurementSheetService.saveUpdateEstimateMeasureDetails(
                                measureMentSubList.get(reviseEstimate.getSordId()),
                                mesureRemoveByIdList, "APZX");
                        updateWorkEsimateLbhQunatity(reviseEstimate.getWorkEstemateId(),
                                measurementSheetService
                                        .calculateTotalEstimatedAmountByWorkId(reviseEstimate.getWorkEstemateId()));
                    }
                });
            }
            updateTotalEstimateAmount(workId);
        }
        if (removeWorkIdList != null && !removeWorkIdList.isEmpty()) {
            workEstimateRepository.updateDeletedFlag(removeWorkIdList);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkEstimateMasterDto> getSorIdWithContractId(Long ContractId, Long orgId) {
        WorkEstimateMasterDto dto = new WorkEstimateMasterDto();
        List<WorkEstimateMasterDto> listOfId = new ArrayList<WorkEstimateMasterDto>();
        List<Object[]> objects = workEstimateRepository.findDistictSorIdWithContractId(ContractId, orgId);
        objects.forEach(wdto -> {
            if (wdto[0] != null) {
                dto.setSorId((Long) wdto[0]);
            }
            if (wdto[1] != null) {
                dto.setWorkId((Long) wdto[1]);
            }
            listOfId.add(dto);
        });
        return listOfId;
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkEstimateMasterDto> getAllRevEstmtByReviseFlag(Long contId, Long orgId, String estimateType,
            String workeReviseFlag) {

        List<WorkEstimateMaster> listEntity = new ArrayList<>();
        listEntity = workEstimateRepository.getAllDataByRevisedFlag(contId,
                estimateType, workeReviseFlag, orgId);
        List<WorkEstimateMasterDto> dtoList = new ArrayList<>();
        if (listEntity != null && !listEntity.isEmpty()) {
            Organisation organisation = new Organisation();
            organisation.setOrgid(orgId);
            listEntity.forEach(entity -> {
                WorkEstimateMasterDto obj = new WorkEstimateMasterDto();
                BeanUtils.copyProperties(entity, obj);
                if (obj.getSorIteamUnit() != null)
                    obj.setSorIteamUnitDesc(CommonMasterUtility
                            .getNonHierarchicalLookUpObject(obj.getSorIteamUnit(), organisation).getDescLangFirst());
                dtoList.add(obj);
            });
        }
        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkEstimateMasterDto> getPreviousEstimateByContractAndRevisedFlag(Long contId, String estimateType, Long orgId) {

        List<WorkEstimateMaster> listEntity = new ArrayList<>();
        listEntity = workEstimateRepository.getPreviousEstimate(contId, orgId, estimateType);
        List<WorkEstimateMasterDto> dtoList = new ArrayList<>();
        if (listEntity != null && !listEntity.isEmpty()) {
            Organisation organisation = new Organisation();
            organisation.setOrgid(orgId);
            listEntity.forEach(entity -> {
                WorkEstimateMasterDto obj = new WorkEstimateMasterDto();
                BeanUtils.copyProperties(entity, obj);
                if (obj.getSorIteamUnit() != null)
                    obj.setSorIteamUnitDesc(CommonMasterUtility
                            .getNonHierarchicalLookUpObject(obj.getSorIteamUnit(), organisation).getDescLangFirst());
                dtoList.add(obj);
            });
        }
        return dtoList;
    }

}
