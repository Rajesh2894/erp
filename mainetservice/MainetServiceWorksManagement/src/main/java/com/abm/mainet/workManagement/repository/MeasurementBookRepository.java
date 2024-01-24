package com.abm.mainet.workManagement.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.workManagement.domain.MeasurementBookDetails;
import com.abm.mainet.workManagement.domain.MeasurementBookDetailsHistory;
import com.abm.mainet.workManagement.domain.MeasurementBookMaster;

@Repository
public interface MeasurementBookRepository extends CrudRepository<MeasurementBookMaster, Long> {

    /**
     * used to delete MB details by ID
     * 
     * @param deletedMbId
     */
    @Modifying
    @Query("DELETE from  MeasurementBookDetails a where a.mbdId in (:mbId)")
    void deleteMBDetailsById(@Param("mbId") List<Long> mbId);

    /**
     * used to get MB by mb ID
     * 
     * @param mbId
     * @return
     */
    @Query("select mb from MeasurementBookMaster mb where mb.workMbId = :mbId")
    MeasurementBookMaster getMBById(@Param("mbId") Long mbId);

    /**
     * used to get MB by work Order ID
     * 
     * @param workOrderId
     * @return
     */
    @Query("select mb from MeasurementBookMaster mb where mb.workOrder.workId = :workId and mbStatus<>'RA'")
    MeasurementBookMaster getMBByWorkOrderId(@Param("workId") Long workId);

    /**
     * used to get MB Status by work Order ID
     * 
     * @param workOrderId
     * @return
     */
    @Query("select mb from MeasurementBookMaster mb where mb.workOrder.workId = :workId ")
    MeasurementBookMaster getMBStatusByWorkOrderId(@Param("workId") Long workId);

    /**
     * used to get all measurement book by org Id
     * 
     * @param orgId
     * @return
     */
    @Query("select mb from MeasurementBookMaster mb where mb.orgId = :orgId")
    List<MeasurementBookMaster> getAllMBListByOrgId(@Param("orgId") Long orgId);

    /**
     * used to get List of MB Details by measurement book Id
     * 
     * @param mbId
     * @return
     */
    @Query("select mb from MeasurementBookDetails mb where mb.mbMaster.workMbId = :mbId")
    List<MeasurementBookDetails> getMBDetailsListByMBId(@Param("mbId") Long mbId);

    /**
     * used to get MB Details by measurement book detils Id
     * 
     * @param mbDId
     * @return
     */
    @Query("select mb from MeasurementBookDetails mb where mb.mbdId = :mbdId")
    MeasurementBookDetails getMBDetailsByDetailsId(@Param("mbdId") Long mbDId);

    /**
     * used to update MB number
     * 
     * @param workOrderNo
     * @param mbNumber
     */
    @Query("Update MeasurementBookMaster mb set mb.workMbNo =:workMbNo where mb.workOrder.workId =:workId")
    void updateMbnumberByWorkOrderId(@Param("workId") Long workOrderNo, @Param("workMbNo") String mbNumber);

    /**
     * used to get List of MB Details by measurement book Parent Id
     * 
     * @param mbPId
     * @return
     */
    @Query("select mb from MeasurementBookDetails mb where mb.mbDetPId = :mbPId")
    List<MeasurementBookDetails> getMBByMbdetParentId(@Param("mbPId") Long mbPId);

    /**
     * used to get List of MB Details by Estimate Id
     * 
     * @param estimateId
     * @return
     */
    @Query("select mb from MeasurementBookDetails mb where mb.workEstimateMaster.workEstemateId = :estimateId and mb.mbDetPId =:mbDetId")
    MeasurementBookDetails getMBDetailsByEstimateId(@Param("estimateId") Long estimateId,
            @Param("mbDetId") Long mbDetId);

    @Modifying
    @Query("DELETE from  MeasurementBookDetails a where a.mbdId in (:mbdId)")
    void deleteMBDetailsByDetpId(@Param("mbdId") List<Long> mbdId);

    /**
     * used to get MB master by MB Code and orgId
     * 
     * @param workMBCode
     * @param currentOrgId
     * @return
     */
    @Query("select mb from MeasurementBookMaster mb where mb.workMbNo=:workMBCode and mb.orgId = :orgId")
    MeasurementBookMaster getWorkMbByMBCode(@Param("workMBCode") String workMBCode, @Param("orgId") Long currentOrgId);

    /**
     * used to find Abstract Report Sheet
     * 
     * @param workMbId
     * @param orgId
     * @return
     */
    @Query("select d.workId,d.sordCategory, d.sordSubCategory,d.sorDIteamNo, d.sorDDescription, "
            + "d.sorIteamUnit, d.workEstimQuantity, b.workActualQty,b.workActualAmt , "
            + "(select sum(g.workActualQty) from MeasurementBookDetails g "
            + "  where (g.mbDetPId=b.mbdId) or (g.mbdId=b.mbdId)  group by COALESCE(g.mbDetPId,b.mbdId)) As ActualQty, "
            + "(select sum(c.sorBasicRate) from WorkEstimateMaster c "
            + " where (c.workEstimPId=d.workEstemateId) or (c.workEstemateId=d.workEstemateId)  group by COALESCE(c.workEstimPId,c.workEstemateId)) "
            + "As ActualRate, e.workName,f.projNameEng,b.workUtlQty,d.meRemark  "
            + "from  MeasurementBookMaster a,MeasurementBookDetails b,WorkEstimateMaster d,WorkDefinationEntity e, TbWmsProjectMaster f "
            + "where a.workMbId=b.mbMaster.workMbId and  "
            + " f.projId=e.projMasEntity.projId and b.mbDetPId IS NULL and d.workId=e.workId "
            + "and b.workEstimateMaster.workEstemateId=d.workEstemateId and a.workMbId =:workMbId ")
    List<Object[]> findAbstractReportSheet(@Param("workMbId") Long workMbId);

    /**
     * used to get Log details
     * 
     * @param mbParentId
     * @return
     */
    @Query("select mbh from MeasurementBookDetailsHistory mbh where mbh.mbDetPId = :mbParentId")
    List<MeasurementBookDetailsHistory> getAuditLogDetails(@Param("mbParentId") Long mbParentId);

    @Modifying
    @Query("Update MeasurementBookDetails mbd set mbd.workActualQty =:workActualQty where mbd.mbdId =:mbdId")
    void updateLbhQuantityByMbId(@Param("workActualQty") BigDecimal quantity, @Param("mbdId") Long workMbDId);

    @Modifying
    @Query("Update MeasurementBookMaster mb set mb.mbTotalAmt =:mbTotalAmt where mb.workMbId =:workMbId")
    void updateTotalMbAmountByMbId(@Param("mbTotalAmt") BigDecimal amount, @Param("workMbId") Long workMbId);
    
    @Modifying
    @Query("Update MeasurementBookDetails mb set mb.workActualAmt =:mbTotalAmt where mb.mbMaster.workMbId =:workMbId")
    void updateTotalMbAmountByMbIdDet(@Param("mbTotalAmt") BigDecimal amount, @Param("workMbId") Long workMbId);

    @Modifying
    @Query("Update MeasurementBookMaster mb set mb.billNumber =:billNumber where mb.workMbId =:workMbId")
    void updateBillNumberByMbId(@Param("billNumber") String amount, @Param("workMbId") Long workMbId);

    /**
     * get WorkId With MbNumber
     * 
     * @param workMbNo
     * @param orgId
     * @return
     */
    @Query("SELECT tw.workDefinationEntity.workId from MeasurementBookMaster mb ,WorkOrder wo ,TenderWorkEntity tw "
            + "where mb.workMbNo =:workMbNo and mb.workOrder.workId = wo.workId and wo.contractMastEntity.contId=tw.contractId "
            + "and mb.orgId =:orgId ")
    Long getWorkIdWithMbNumber(@Param("workMbNo") String workMbNo, @Param("orgId") Long orgId);

    /**
     * update Measure Ment Mode
     * 
     * @param workMbId
     * @param flag
     */
    @Modifying
    @Query("UPDATE MeasurementBookMaster mb SET mb.mbStatus =:flag ,mb.bmDate = CURRENT_DATE where mb.workMbId =:workMbId")
    void updateMeasureMentMode(@Param("workMbId") Long workMbId, @Param("flag") String flag);

    /**
     * used to get All MB by work Order ID
     * 
     * @param workOrderId
     * @return
     */
    @Query("select mb from MeasurementBookMaster mb where mb.workOrder.workId = :workId")
    List<MeasurementBookMaster> getAllMBByWorkOrderId(@Param("workId") Long workId);

    @Query("select mb from MeasurementBookDetails mb where mb.workEstimateMaster.workEstemateId = :estimateId and mb.mbdId =:mbDetId and mb.workFlag =:flag")
    MeasurementBookDetails getMBNonSorDetailsByEstimateId(@Param("estimateId") Long estimateId,
            @Param("mbDetId") Long mbDetId, @Param("flag") String flag);

    /**
     * Used to Update workOrder work Assignee Data
     * 
     * @param workOrId
     * @param workAssignee
     */
    @Modifying
    @Query("UPDATE WorkOrder wo SET wo.workAssignee =:workAssignee where wo.workId =:workOrderId ")
    void updateWorkAssigneeByworkOrderId(@Param("workOrderId") Long workOrderId,
            @Param("workAssignee") String workAssignee);

    /**
     * used to get Cummulative Details By Work Order Id and current running mb Id
     * 
     * @param workOrderId
     * @param currentMbId
     * @return
     */
    @Query("select b.workEstimateMaster.workEstemateId,b.workActualQty,b.workUtlQty , a.workMbId from MeasurementBookMaster a, MeasurementBookDetails b "
            + "where a.workMbId=b.mbMaster.workMbId  and a.workOrder.workId=:workOrderId "
            + "and a.workMbId IN (select max(workMbId) from MeasurementBookMaster mb where mb.workOrder.workId=:workOrderId and mb.workMbId < :currentMbId)")
    List<Object[]> getCummulativeDetailsByWorkOrderId(@Param("workOrderId") Long workOrderId,
            @Param("currentMbId") Long currentMbId);

    /**
     * used to update Work Utilization Quantity
     * 
     * @param workEId
     * @param workUtlQty
     * @param workMbId
     */
    @Modifying
    @Query("UPDATE MeasurementBookDetails mbd SET mbd.workUtlQty =:workUtlQty where mbd.workEstimateMaster.workEstemateId =:workEId and mbd.mbMaster.workMbId=:workMbId")
    void updateWorkUtlQty(@Param("workEId") Long workEId, @Param("workUtlQty") BigDecimal workUtlQty,
            @Param("workMbId") Long workMbId);

    /**
     * update List Of MeasureMentMode
     * 
     * @param workMbId
     * @param flag
     *//*
        * @Modifying
        * @Query("UPDATE MeasurementBookMaster mb SET mb.mbStatus =:flag ,mb.bmDate = CURRENT_DATE where mb.workMbId in (:workMbId)"
        * ) void updateListOfMeasureMentMode(@Param("workMbId") List<Long> workMbId, @Param("flag") String flag);
        */
    /**
     * get All Mb Details By List Of WorkMbId
     * 
     * @param workMbId
     * @param orgId
     * @return List<MeasurementBookMaster>
     */
    @Query("SELECT mb from MeasurementBookMaster mb where mb.workMbId in (:workMbId) and mb.orgId =:orgId")
    List<MeasurementBookMaster> getAllMbDetailsByListOfWorkMbId(@Param("workMbId") List<Long> workMbId,
            @Param("orgId") Long orgId);

    /**
     * Use to get Previous MeasurementBookLbh Utilized Quantity
     * 
     * @param mbId
     * @param workOrderId
     * @return List<Object>
     */
    @Query("select a.details.meMentId,sum(a.mbNosAct) from MeasurementBookLbh a,MeasurementBookDetails b, MeasurementBookMaster c where c.workMbId=b.mbMaster.workMbId and a.mbdId=b.mbdId and c.workOrder.workId=?2 and c.workMbId < ?1 group by c.workOrder.workId,a.details.meMentId")
    List<Object[]> getPreviousMbUtilzedQuantity(Long mbId, Long workOrderId);

    /**
     * Use to get Previous NonSor Utilized Quantity
     * 
     * @param mbId
     * @param workOrderId
     * @return List<Object>
     */
    @Query("select d.workEstemateId,sum(b.workActualQty) from MeasurementBookDetails b, MeasurementBookMaster c, WorkEstimateMaster d where c.workMbId=b.mbMaster.workMbId and b.workEstimateMaster.workEstemateId=d.workEstemateId and c.workOrder.workId=?2 and c.workMbId < ?1 and d.workEstimFlag in ('N','MN') group by c.workOrder.workId,d.workEstemateId")
    List<Object[]> getPreviousNonSorUtilzedQuantity(Long mbId, Long workOrderId);

    /**
     * Use to get Previous Direct Utilized Quantity
     * 
     * @param mbId
     * @param workOrderId
     * @return List<Object>
     */
    @Query("select d.workEstemateId,sum(a.mbNosAct) from MeasurementBookLbh a,MeasurementBookDetails b, MeasurementBookMaster c, WorkEstimateMaster d where c.workMbId=b.mbMaster.workMbId and a.mbdId=b.mbdId and b.workEstimateMaster.workEstemateId=d.workEstemateId and c.workOrder.workId=?2 and c.workMbId < ?1 and d.estimateType='U' group by c.workOrder.workId,d.workEstemateId")
    List<Object[]> getPreviousDirectUtilzedQuantity(Long mbId, Long workOrderId);

    @Query("select sum(c.mbTotalAmt) from MeasurementBookMaster c,WorkOrder wo , TenderWorkEntity tw where c.workOrder.workId=wo.workId and tw.contractId=wo.contractMastEntity.contId and tw.orgId=:orgId and tw.workDefinationEntity.workId=:workId)")
    BigDecimal getTotalMbAmountByWorkId(@Param("workId") Long workId, @Param("orgId") Long orgId);
    
    @Query("select sum(c.mbTotalAmt) from MeasurementBookMaster c where c.workOrder.workId=:workId and c.orgId=:orgId)")
    BigDecimal getTotalMbAmountByWorkOrderId(@Param("workId") Long workId, @Param("orgId") Long orgId);
    
    @Query("select sum(c.mbTotalAmt) from MeasurementBookMaster c where c.workMbId=:workMbId and c.orgId=:orgId)")
    BigDecimal getTotalMbAmountByWorkMbId(@Param("workMbId") Long workMbId, @Param("orgId") Long orgId);
    
    @Modifying
	@Query("DELETE from  MeasurementBookDetails a where a.mbdId in (:mbdId)")
	void deleteNonSorMbDetailsId(@Param("mbdId") List<Long> mbdId);
    
    @Modifying
    @Query("Update MeasurementBookDetails mbd set mbd.workActualAmt =:workActualAmt where mbd.mbdId =:mbdId")
    void updateAmountByMbId(@Param("workActualAmt") BigDecimal workActualAmt, @Param("mbdId") Long mbDetId);
}
