package com.abm.mainet.workManagement.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.abm.mainet.workManagement.dto.WorkEstimOverHeadDetDto;
import com.abm.mainet.workManagement.dto.WorkEstimateMasterDto;
import com.abm.mainet.workManagement.dto.WorkEstimateMeasureDetailsDto;

public interface WorkEstimateService {

    /**
     * save Work Estimate List
     * @param workEstimateList
     * @param removeWorkIdList
     * @param approvalFlag
     */
    void saveWorkEstimateList(List<WorkEstimateMasterDto> workEstimateList, List<Long> removeWorkIdList,
            String approvalFlag);

    /**
     * get WorkEstimate By WorkId
     * @param workId
     * @param orgid
     * @return
     */
    List<WorkEstimateMasterDto> getWorkEstimateByWorkId(Long workId, long orgid);

    /**
     * set InActive Work Estimate By WorkId
     * @param workIDList
     */
    void setInActiveWorkEstimateByWorkId(List<Long> workIDList);

    /**
     * get All Active Distinct WorkId
     * @param orgid
     * @return
     */
    List<Long> getAllActiveDistinctWorkId(long orgid);

    /**
     * get Estimation By Work Id And Type
     * @param workId
     * @param workType
     * @return
     */
    List<WorkEstimateMasterDto> getEstimationByWorkIdAndType(Long workId, String workType);

    /**
     * calculate Total Estimated Amount By WorkId
     * @param workEstemateId
     * @return
     */
    BigDecimal calculateTotalEstimatedAmountByWorkId(Long workEstemateId);

    /**
     * get Rate Analysis List By Estimate Id
     * @param workEstemateId
     * @param flag
     * @return
     */
    List<WorkEstimateMasterDto> getRateAnalysisListByEstimateId(Long workEstemateId, String flag);

    /**
     * get Child Rate Analysis List By Material Id
     * @param materialId
     * @param workId
     * @return
     */
    List<WorkEstimateMasterDto> getChildRateAnalysisListByMaterialId(Long materialId, Long workId);

    /**
     * save Over Head List
     * @param estimOverHeadDetDto
     * @param overHeadRemoveById
     */
    void saveOverHeadList(List<WorkEstimOverHeadDetDto> estimOverHeadDetDto, List<Long> overHeadRemoveById);

    /**
     * get Estimate OverHeadDet By WorkId
     * @param workId
     * @return
     */
    List<WorkEstimOverHeadDetDto> getEstimateOverHeadDetByWorkId(Long workId);

    /**
     * delete Enclosure File ById
     * @param enclosureRemoveById
     * @param empId
     */
    void deleteEnclosureFileById(List<Long> enclosureRemoveById, Long empId);

    /**
     * update Work Esimate Lbh Qunatity
     * @param measurementWorkId
     * @param totalAmount
     */
    void updateWorkEsimateLbhQunatity(Long measurementWorkId, BigDecimal totalAmount);

    /**
     * used to get all estimate which is associated in Tender
     * 
     * @param tenderId
     * @return List<WorkEstimateMasterDto>
     */
    List<WorkEstimateMasterDto> getAllEstimateByTenderId(Long tenderId);

    /**
     * used to get all rate Type for measurement book by estimate measurement id
     * 
     * @param estmiateId
     * @return
     */
    List<WorkEstimateMasterDto> getAllRateTypeMBByEstimateNo(Long estmiateId, Long mbId, Long mbDetId);

    /**
     * used to update actual amount and quantity in work estimation table
     * 
     * @param WorkEstimateMasterDto
     */
    void updateRateValues(List<WorkEstimateMasterDto> WorkEstimateMasterDto);

    /**
     * used to get change log data
     * 
     * @param workEstemateId
     * @return
     */
    List<WorkEstimateMasterDto> getAuditRateByEstimateId(Long workEstemateId);

    void updateContractId(Long workId, Long contId, Long empId);

    /**
     * get All Revised Contarct Estimate Details By Contrcat Id (workEstimateType -SOR->'S',NON-SOR->'N','B'->Bill of Quantity
     * :for all set workEstimateType A )
     * 
     * @param contractId
     * @param orgId
     * @param workEstimateType
     * @return
     */
    List<WorkEstimateMasterDto> getAllRevisedContarctEstimateDetailsByContrcatId(Long contractId, Long orgId,
            String workEstimateType);

    /**
     * used to get get Previous Estimate By ContractId
     * 
     * @param contractId
     * @param orgId
     * @param workEstimateType
     * @return
     */
    List<WorkEstimateMasterDto> getPreviousEstimateByContractId(Long contractId, Long orgId, String workEstimateType);

    /**
     * update Parent Work Estimation Amount
     * 
     * @param estimatedAmt
     * @param workId
     * @param orgId
     */
    void updateParentWorkEstimationAmount(Map<Long, BigDecimal> estimatedAmt, Long workId, Long orgId);

    /**
     * get Total Estimate Amount
     * @param workId
     * @return
     */
    BigDecimal getTotalEstimateAmount(Long workId);

    /**
     * calculate Total Work Estimate
     * @param contId
     * @param orgId
     * @return
     */
    BigDecimal calculateTotalWorkEstimate(Long contId, Long orgId);

    /**
     * find Work Estimate No By WorkId
     * @param workId
     * @param orgId
     * @return
     */
    String findWorkEstimateNoByWorkId(Long workId, Long orgId);

    /**
     * find By Id
     * @param orgId
     * @param workEstimateId
     * @return
     */
    WorkEstimateMasterDto findById(Long orgId, Long workEstimateId);

    /**
     * used to update commulative quantity
     * @param measurementWorkId
     * @param utlQuantity
     */
    void updateWorkEsimateLbhUtlQunatity(Long measurementWorkId, BigDecimal utlQuantity);

    /**
     * method is used for save Revised Estimate
     * @param revisedEstimateMasterList
     * @param measureMentSubList
     * @param mesureRemoveById
     */

    void saveRevisedEstimate(List<WorkEstimateMasterDto> revisedEstimateMasterList,
            Map<Long, List<WorkEstimateMeasureDetailsDto>> measureMentSubList, List<Long> mesureRemoveByIdList,
            List<Long> removeWorkIdList);

    public List<WorkEstimateMasterDto> getSorIdWithContractId(Long ContractId, Long orgId);

    List<WorkEstimateMasterDto> getAllRevEstmtByReviseFlag(Long contId, Long orgId, String estimateType, String workeReviseFlag);

    List<WorkEstimateMasterDto> getPreviousEstimateByContractAndRevisedFlag(Long contId, String estimateType, Long orgId);
}
