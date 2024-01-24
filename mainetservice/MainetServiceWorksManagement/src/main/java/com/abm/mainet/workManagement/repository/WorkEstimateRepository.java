package com.abm.mainet.workManagement.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.workManagement.domain.WorkEstimOverHeadDetails;
import com.abm.mainet.workManagement.domain.WorkEstimateMaster;
import com.abm.mainet.workManagement.domain.WorkEstimateMasterHistory;

@Repository
public interface WorkEstimateRepository extends CrudRepository<WorkEstimateMaster, Long> {

    @Modifying
    @Query("update WorkEstimateMaster set workEstimActive = 'N'  where workEstemateId in :removeWorkIdList")
    void updateDeletedFlag(@Param("removeWorkIdList") List<Long> removeWorkIdList);

    @Query("SELECT pm FROM WorkEstimateMaster pm  where pm.orgId = :orgId and workId= :workId and pm.workEstimActive= 'Y' and workEstimPId IS NULL")
    List<WorkEstimateMaster> getWorkEstimateByWorkId(@Param("workId") Long workId, @Param("orgId") long orgid);

    @Modifying
    @Query("update WorkEstimateMaster set workEstimActive = 'N'  where workId in :workIDList")
    void setInActiveWorkEstimateByWorkId(@Param("workIDList") List<Long> workIDList);

    @Query("SELECT DISTINCT workId FROM WorkEstimateMaster pm  where pm.orgId = :orgId and pm.workId is not null")
    List<Long> getAllActiveDistinctWorkId(@Param("orgId") long orgid);

    @Query("SELECT pm FROM WorkEstimateMaster pm  where workEstimActive = 'Y' and pm.workId = :workId and workEstimFlag= :workEstimFlag")
    List<WorkEstimateMaster> getEstimationByWorkIdAndType(@Param("workId") Long workId,
            @Param("workEstimFlag") String workType);

    @Query("select Sum(workEstimAmount) from WorkEstimateMaster where workEstimActive = 'Y' and workEstimPId =:workEId and workEstimFlag in ('M','RO','LO','UN','LE','LF','ST','L','C','A')")
    BigDecimal calculateTotalEstimatedAmountByWorkId(@Param("workEId") Long workEstemateId);

    @Query("SELECT pm FROM WorkEstimateMaster pm  where pm.workEstimPId = :workId and pm.workEstimActive = 'Y' and workEstimFlag =:workEstimFlag")
    List<WorkEstimateMaster> getRateAnalysisListByEstimateId(@Param("workId") Long workEstemateId,
            @Param("workEstimFlag") String flag);

    @Query("SELECT pm FROM WorkEstimateMaster pm  where pm.maPId = :maId and pm.workEstimPId =:workId and pm.workEstimActive = 'Y'")
    List<WorkEstimateMaster> getChildRateAnalysisListByMaterialId(@Param("maId") Long materialId,
            @Param("workId") Long workId);

    @Query("select Sum(w.overHeadValue) from WorkEstimOverHeadDetails w where w.active = 'Y'  and w.workId =:workId")
    BigDecimal getOverheadAmount(@Param("workId") Long workId);

    @Query("SELECT od FROM WorkEstimOverHeadDetails od  where od.workId = :workId and active = 'Y'")
    List<WorkEstimOverHeadDetails> getEstimateOverHeadDetByWorkId(@Param("workId") Long workId);

    @Modifying
    @Query("update WorkEstimateMaster set workEstimQuantity = :totalAmount  where workEstemateId =:measurementWorkId")
    void updateWorkEsimateLbhQunatity(@Param("measurementWorkId") Long measurementWorkId,
            @Param("totalAmount") BigDecimal totalAmount);

    @Query("SELECT pm FROM WorkEstimateMaster pm  where workId  in (:workIDList) and pm.workEstimActive = 'Y' and pm.workEstimFlag in ('S','N')")
    List<WorkEstimateMaster> getAllTenderEstimates(@Param("workIDList") List<Long> workId);

    @Query("SELECT pm FROM WorkEstimateMaster pm  where  pm.workEstimPId =:workId and pm.workEstimActive = 'Y'")
    List<WorkEstimateMaster> getAllRateTypeMBByEstimateNo(@Param("workId") Long estmiateId);

    @Modifying
    @Query("Update WorkEstimateMaster we set we.workeEstimateNo =:mbNumber where we.workId =:workId")
    void updateWorkEstimateNo(@Param("workId") Long workId, @Param("mbNumber") String mbNumber);

    @Query("SELECT we FROM WorkEstimateMasterHistory we  where we.workEstimPId = :workId")
    List<WorkEstimateMasterHistory> getAuditRateByEstimateId(@Param("workId") Long workEstemateId);

    @Modifying
    @Query("update WorkEstimateMaster c set c.contractId =:contId, c.updatedBy =:empId,c.updatedDate =CURRENT_DATE where c.workId =:workId")
    void updateContractId(@Param("workId") Long workId, @Param("contId") Long contId, @Param("empId") Long empId);

    @Modifying
    @Query("Update WorkEstimateMaster we set we.workEstimAmount =:workEstimAmount where we.workEstemateId =:workEstemateId")
    void updateParentWorkEstimationAmount(@Param("workEstemateId") Long workEId,
            @Param("workEstimAmount") BigDecimal amount);

    @Query("select Sum(workEstimAmount) from WorkEstimateMaster where workEstimActive = 'Y'  and workId =:workId and workEstimFlag in ('S','N','MS','MN')")
    BigDecimal getSorAndNonSorAmount(@Param("workId") Long workId);

    @Query("select Sum(m.workEstimAmount) from WorkEstimateMaster m where m.workeReviseFlag in ('N','E')  and m.contractId =:contId and m.orgId =:orgId")
    BigDecimal calculateTotalEstimatedAmountByContId(@Param("contId") Long contId, @Param("orgId") Long orgId);

    @Query("SELECT DISTINCT ms.workeEstimateNo from WorkEstimateMaster ms where ms.workId =:workId and ms.orgId =:orgId")
    String findWorkEstimateNoByWorkId(@Param("workId") Long workId, @Param("orgId") Long orgId);

    @Query("SELECT pm FROM WorkEstimateMaster pm  where pm.orgId = :orgId and pm.workEstemateId= :workEstimateId")
    WorkEstimateMaster findById(@Param("orgId") Long orgId, @Param("workEstimateId") Long workEstimateId);

    @Modifying
    @Query("Update WorkEstimateMaster we set we.workEstimQuantityUtl =:utlQuantity where we.workId =:measurementWorkId")
    void updateWorkEsimateLbhUtlQunatity(@Param("measurementWorkId") Long measurementWorkId,
            @Param("utlQuantity") BigDecimal utlQuantity);

    @Query("SELECT we FROM WorkEstimateMaster we  where workEstimActive = 'Y' and we.contractId = :contractId and we.workeReviseFlag is not null and estimateType= :workEstimateType")
    List<WorkEstimateMaster> getAllRevisedContarctEstimateDetailsByContrcatIdWithEstimateType(
            @Param("contractId") Long contractId, @Param("workEstimateType") String workEstimateType);

    @Query("SELECT we FROM WorkEstimateMaster we  where workEstimActive = 'Y' and we.contractId = :contractId and we.workeReviseFlag is not null")
    List<WorkEstimateMaster> getAllRevisedContarctEstimateDetailsByContrcatId(@Param("contractId") Long contractId);

    @Query("SELECT we FROM WorkEstimateMaster we  where workEstimActive = 'Y' and we.contractId = :contractId and workEstimFlag= :workEstimateType")
    List<WorkEstimateMaster> getPreviousEstimateByContractIdWithEstimateType(@Param("contractId") Long contractId,
            @Param("workEstimateType") String workEstimateType);

    @Query("SELECT we FROM WorkEstimateMaster we  where workEstimActive = 'Y' and we.contractId = :contractId")
    List<WorkEstimateMaster> getPreviousEstimateByContractId(@Param("contractId") Long contractId);

    @Query("SELECT DISTINCT pm.sorId ,pm.workId from WorkEstimateMaster pm where pm.orgId =:orgId and pm.contractId =:contractId and pm.sorId is not null ")
    List<Object[]> findDistictSorIdWithContractId(@Param("contractId") Long contractId, @Param("orgId") Long orgId);

    @Query("SELECT we FROM WorkEstimateMaster we  where workEstimActive = 'Y' and we.contractId = :contId and we.workeReviseFlag =:workeReviseFlag and we.workEstimFlag= :estimateType and we.orgId = :orgId ")
    List<WorkEstimateMaster> getAllDataByRevisedFlag(@Param("contId") Long contId, @Param("estimateType") String estimateType,
            @Param("workeReviseFlag") String workeReviseFlag, @Param("orgId") Long orgId);

    @Query("SELECT we FROM WorkEstimateMaster we  where workEstimActive = 'Y' and we.contractId = :contId and we.workEstimFlag= :estimateType and we.orgId = :orgId and we.workeReviseFlag is null ")
    List<WorkEstimateMaster> getPreviousEstimate(@Param("contId") Long contId, @Param("orgId") Long orgId,
            @Param("estimateType") String estimateType);
    
    @Modifying
    @Query("update WorkEstimateMasterHistory set workEstimStatus = 'D'  where workEstemateId in :removeWorkIdList")
    void updateDeleteflag(@Param("removeWorkIdList") List<Long> removeWorkIdList);
}
