package com.abm.mainet.workManagement.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.workManagement.domain.WorkDefinationEntity;
import com.abm.mainet.workManagement.domain.WorkDefinitionSancDet;

@Repository
public interface WorkDefinitionRepository extends CrudRepository<WorkDefinationEntity, Long> {

    /**
     * used to find All Work Definitions By Org Id
     * 
     * @param orgId
     * @return
     */
    @Query("SELECT wd FROM WorkDefinationEntity wd WHERE wd.orgId =:orgId")
    List<WorkDefinationEntity> findAllWorkDefinitionsByOrgId(@Param("orgId") Long orgId);

    /**
     * used to inActive assests by id
     * 
     * @param removeAssetIdList
     * @param updatedBy
     */
    @Modifying
    @Query("UPDATE WorkDefinationAssetInfoEntity a SET a.assetRecStatus ='N', a.updatedDate=CURRENT_DATE,a.updatedBy=:updatedBy where a.workAssetId in (:removeAssetIdList)")
    void iactiveAssetsByIds(@Param("removeAssetIdList") List<Long> removeAssetIdList,
            @Param("updatedBy") Long updatedBy);

    /**
     * used to inactive Years By Ids
     * 
     * @param removeYearIdList
     * @param updatedBy
     */
    @Modifying
    @Query("UPDATE WorkDefinationYearDetEntity y SET y.yeActive ='N',y.updatedDate=CURRENT_DATE, y.updatedBy=:updatedBy where y.yearId in (:removeYearIdList)")
    void iactiveYearsByIds(@Param("removeYearIdList") List<Long> removeYearIdList, @Param("updatedBy") Long updatedBy);

    /**
     * used to update Work Defination Mode
     * 
     * @param workDefination
     * @param flag
     */
    @Modifying
    @Query("UPDATE WorkDefinationEntity a SET a.workStatus =:flag where a.workId =:workId")
    void updateWorkDefinationMode(@Param("workId") Long workDefination, @Param("flag") String flag);

    /**
     * used to filter Work Definition Records By ProjId
     * 
     * @param orgId
     * @param projId
     * @return
     */
    @Query("SELECT wd FROM WorkDefinationEntity wd WHERE wd.orgId =:orgId and wd.projMasEntity.projId =:projId")
    List<WorkDefinationEntity> filterWorkDefRecordsByProjId(@Param("orgId") Long orgId, @Param("projId") Long projId);
    
    

    @Query("SELECT wd FROM WorkDefinationEntity wd WHERE wd.orgId =:orgId and wd.projMasEntity.projId =:projId and wd.workType =:workType")
    List<WorkDefinationEntity> filterWorkDefDetByProjIdAndWorkType(@Param("orgId") Long orgId, @Param("projId") Long projId, @Param("workType") Long workType);

    /**
     * used to find All Work By Work List
     * 
     * @param orgId
     * @param workId
     * @return
     */
    @Query("SELECT wd FROM WorkDefinationEntity wd WHERE wd.orgId =:orgId and wd.workId in :workId ORDER BY workId DESC")
    public List<WorkDefinationEntity> findAllWorkByWorkList(@Param("orgId") long orgId,
            @Param("workId") List<Long> workId);

    /**
     * used to find Work Definition By Work Code
     * 
     * @param workcode
     * @param orgId
     * @return
     */
    @Query("SELECT wd FROM WorkDefinationEntity wd WHERE wd.orgId =:orgId and wd.workcode=:workcode")
    WorkDefinationEntity findWorkDefinitionByWorkCode(@Param("workcode") String workcode, @Param("orgId") Long orgId);

    /**
     * used to find All Approved Not Used In Other Tender Work By Tender Id And ProjId
     * 
     * @param projId
     * @param orgId
     * @param tenderId
     * @return
     */
    @Query("SELECT wd FROM WorkDefinationEntity wd WHERE(wd.workId in (select tw.workDefinationEntity.workId from TenderWorkEntity tw "
            + " where tw.tenderMasEntity.tndId =:tndId))"
            + " and wd.orgId =:orgId and wd.workStatus='A' OR (wd.workId not in (select ntw.workDefinationEntity.workId from TenderWorkEntity ntw "
            + " where ntw.tenderMasEntity.tndId  <>:tndId))"
            + " and wd.orgId =:orgId and wd.projMasEntity.projId =:projId and wd.workStatus='A' ORDER BY workId DESC")
    List<WorkDefinationEntity> findAllApprovedNotUsedInOtherTenderWorkByTenderIdAndProjId(@Param("projId") Long projId,
            @Param("orgId") Long orgId, @Param("tndId") Long tenderId);

    /**
     * used to find All Approved Not Initiated Work By ProjId And OrgId
     * 
     * @param projId
     * @param orgId
     * @return
     */
    @Query("SELECT wd FROM WorkDefinationEntity wd WHERE wd.orgId =:orgId and wd.projMasEntity.projId =:projId and wd.workStatus='A' and wd.workId"
            + " not in (select twork.workDefinationEntity.workId from TenderWorkEntity twork ) ORDER BY workId DESC")
    List<WorkDefinationEntity> findAllApprovedNotInitiatedWorkByProjIdAndOrgId(@Param("projId") Long projId,
            @Param("orgId") Long orgId);

    /**
     * used to update Works Status To Initiated
     * 
     * @param worksId
     */
    @Modifying
    @Query("UPDATE WorkDefinationEntity a SET a.workStatus = 'I' where a.workId in (:worksId)")
    void updateWorksStatusToInitiated(@Param("worksId") List<Long> worksId);

    /**
     * used to find All Approved Not Initiated Work
     * 
     * @param tndId
     * @param projId
     * @param orgId
     * @return
     */
    @Query("SELECT wd FROM WorkDefinationEntity wd WHERE wd.workId IN (SELECT a.workDefinationEntity.workId FROM TenderWorkEntity a, TenderMasterEntity b WHERE "
            + " a.tenderMasEntity.tndId=b.tndId AND b.projMasEntity.projId =:projId AND b.tndId=:tndId) OR wd.workId NOT IN "
            + " (SELECT work.workDefinationEntity.workId FROM TenderWorkEntity work WHERE work.orgId=:orgId)")
    List<WorkDefinationEntity> findAllApprovedNotInitiatedWork(@Param("tndId") Long tndId, @Param("projId") Long projId,
            @Param("orgId") Long orgId);

    /**
     * used to update Work Status As Tendered
     * 
     * @param workIds
     */
    @Modifying
    @Query("UPDATE WorkDefinationEntity a SET a.workStatus = 'T' where a.workId in (:workIds)")
    void updateWorkStatusAsTendered(@Param("workIds") List<Long> workIds);

    /**
     * used to delete Sanction Details
     * 
     * @param removeScanIdList
     */
    @Modifying
    @Query("DELETE from  WorkDefinitionSancDet a where a.workSancId in:workSancId")
    void deleteSanctionDetails(@Param("workSancId") List<Long> removeScanIdList);

    /**
     * used to find All Sanction Details By Work Id
     * 
     * @param workId
     * @return
     */
    @Query("SELECT ws from WorkDefinitionSancDet ws  where ws.workDefEntity.workId =:workId")
    List<WorkDefinitionSancDet> finadAllSanctionDetailsByWorkId(@Param("workId") Long workId);

    /**
     * used to update Work Sanction Number
     * 
     * @param workSancNo
     * @param workId
     * @param orgid
     * @param deptId
     * @param workSancBy
     * @param workDesignBy
     */
    @Modifying
    @Query("UPDATE WorkDefinitionSancDet s SET s.workSancNo=:workSancNo ,s.workSancBy=:workSancBy , s.workDesignBy=:workDesignBy , s.workSancDate = CURRENT_DATE "
            + "where s.workDefEntity.workId=:workId and s.orgid=:orgid and s.deptId=:deptId ")
    void updateWorkSanctionNumber(@Param("workSancNo") String workSancNo, @Param("workId") Long workId,
            @Param("orgid") Long orgid, @Param("deptId") Long deptId, @Param("workSancBy") String workSancBy,
            @Param("workDesignBy") String workDesignBy);

    /**
     * used to update Total Estimated Amount
     * 
     * @param workId
     * @param totalAmount
     */
    @Modifying
    @Query("UPDATE WorkDefinationEntity a SET a.workEstAmt =:totalAmount where a.workId =:workId")
    void updateTotalEstimatedAmount(@Param("workId") Long workId, @Param("totalAmount") BigDecimal totalAmount);

    /**
     * used to update Work Completion Date
     * 
     * @param workId
     * @param workCompletionDate
     * @param completionNo
     */
    @Modifying
    @Query("UPDATE WorkDefinationEntity a SET a.workCompletionDate =:workCompletionDate , a.workCompletionNo=:completionNo where a.workId =:workId")
    void updateWorkCompletionDate(@Param("workId") Long workId, @Param("workCompletionDate") Date workCompletionDate,
            @Param("completionNo") String completionNo);

    /**
     * used to filter Work Definition Sanction Records By ProjId
     * 
     * @param orgId
     * @param projId
     * @param workSancNo
     * @return
     */
    @Query("SELECT wd FROM WorkDefinitionSancDet wd WHERE wd.workSancNo =:workSancNo and wd.orgid =:orgid and wd.workDefEntity.projMasEntity.projId =:projId ")
    List<WorkDefinitionSancDet> filterWorkDefSanctionRecordsByProjId(@Param("orgid") Long orgId,
            @Param("projId") Long projId, @Param("workSancNo") String workSancNo);

    /**
     * used to filter Work Definition Records By ProjId And Status
     * 
     * @param orgId
     * @param projId
     * @return
     */
    @Query("SELECT DISTINCT wd.workId, wd.workName FROM WorkDefinationEntity wd,WorkDefinitionSancDet ws WHERE wd.workId=ws.workDefEntity.workId and ws.workSancNo is not null and ws.workSancNo!='' and "
            + "wd.orgId =:orgId and wd.projMasEntity.projId =:projId")
    List<Object[]> filterWorkDefRecordsByProjIdAndStatus(@Param("orgId") Long orgId, @Param("projId") Long projId);

    /**
     * used to filter Sanction No Records By workid
     * 
     * @param orgId
     * @param workId
     * @return
     */
    @Query("SELECT wd FROM WorkDefinitionSancDet wd WHERE wd.orgid =:orgid and wd.workDefEntity.workId =:workId")
    List<WorkDefinitionSancDet> filterSanctionNoRecordsByWrojId(@Param("orgid") Long orgId,
            @Param("workId") Long workId);

    /**
     * used to find All Completed Works
     * 
     * @param orgId
     * @return
     */
    @Query("SELECT wd FROM WorkDefinationEntity wd WHERE wd.orgId =:orgId and wd.workStatus ='C' ORDER BY workId DESC")
    List<WorkDefinationEntity> findAllCompletedWorks(@Param("orgId") Long orgId);

    /**
     * used to get All Budget Head By WorkId
     * 
     * @param workId
     * @return
     */
    @Query("SELECT yearDet.financeCodeDesc, "
            + "(select acMaster.acHeadCode from AccountHeadSecondaryAccountCodeMasterEntity acMaster where acMaster.sacHeadId=yearDet.sacHeadId) AS BudgetHead,"
            + "f.faFromDate,f.faToDate from WorkDefinationYearDetEntity yearDet, "
            + "FinancialYear f where yearDet.faYearId=f.faYear and yearDet.workDefEntity.workId =:workId and yearDet.yeActive <> 'N' ")
    List<Object[]> getAllBudgetHeadByWorkId(@Param("workId") Long workId);

    /**
     * used to get Work Completion Register By WorkId
     * 
     * @param workId
     * @param raCode
     * @return
     */
    @Query("select a.workId,a.workName,a.locIdSt,c.contractFromDate,c.contractToDate,c.startDate,d.raCode,"
            + "(select vmVendorname from TbAcVendormasterEntity where vmVendorid=b.vendorMaster.vmVendorid) as VendorName from WorkDefinationEntity a,TenderWorkEntity b,WorkOrder c,WorksRABill d where a.workId=b.workDefinationEntity.workId and b.contractId=c.contractMastEntity.contId and a.workId=d.workId and d.raCode=:raCode and a.workId=:workId and d.raMbIds in "
            + "(select workMbId from MeasurementBookMaster) ")
    List<Object[]> getWorkCompletionRegisterByWorkId(@Param("workId") Long workId, @Param("raCode") String raCode);

    /**
     * used to get Register Details By WorkId
     * 
     * @param workId
     * @param raCode
     * @return
     */
    @Query("select c.workId,c.sorDIteamNo,c.sorDDescription,c.sorIteamUnit,c.workEstimQuantity,c.sorBasicRate,c.workEstimAmount,b.workActualQty,b.workActualAmt from MeasurementBookMaster a,MeasurementBookDetails b,WorkEstimateMaster c,WorksRABill d where a.workMbId=b.mbMaster.workMbId and b.workEstimateMaster.workEstemateId=c.workEstemateId and c.workId=:workId and d.raCode=:raCode and d.raMbIds in (SELECT workMbId from MeasurementBookMaster)")
    List<Object[]> getRegisterDetailsByWorkId(@Param("workId") Long workId, @Param("raCode") String raCode);

    /**
     * used to find All Work Order Generated Works
     * 
     * @param orgId
     * @return
     */
    @Query("SELECT c FROM WorkOrder a,TenderWorkEntity b,WorkDefinationEntity c where a.contractMastEntity.contId=b.contractId and b.workDefinationEntity.workId=c.workId and c.orgId = :orgId")
    List<WorkDefinationEntity> findAllWorkOrderGeneratedWorks(@Param("orgId") Long orgId);

    /**
     * used to update OverHead Amount
     * 
     * @param workId
     * @param overheadAmount
     */
    @Modifying
    @Query("UPDATE WorkDefinationEntity a SET a.workOverHeadAmt =:overheadAmount where a.workId =:workId")
    void updateOverHeadAmount(@Param("workId") Long workId, @Param("overheadAmount") BigDecimal overheadAmount);

    /**
     * find All Work Id on WorkStatus excluding P And D and SOR Condition
     * @param orgId
     * @param workIdList
     * @return
     */

    @Query("Select wdef from WorkDefinationEntity wdef  where wdef.workStatus not in('P','D') and "
            + "wdef.workId in (Select Distinct wm.workId from  WorkEstimateMaster wm, ScheduleOfRateMstEntity sor where "
            + "wm.sorId = sor.sorId and sor.sorActive='Y' and wm.estimateType <> 'U' and wm.orgId =:orgId and "
            + "sor.sorToDate is null ) and wdef.orgId =:orgId ")
    List<WorkDefinationEntity> findAllWorkByWorkStatus(@Param("orgId") Long orgId);

    @Modifying
    @Query("Delete from WorkDefinationWardZoneDetails w where w.wardZoneId in (:removeWardZoneIdList)")
    void deleteWardZoneDetails(@Param("removeWardZoneIdList") List<Long> removeWardZoneIdList);

    
     @Query("select b from TbWmsProjectMaster a, WorkDefinationEntity b where a.projId=b.projMasEntity.projId and a.projCode=:projCode and b.workcode=:workCode")
     WorkDefinationEntity getProjCodeWorkCode(@Param("projCode") String projCode, @Param("workCode") String workCode);
    
     @Query("SELECT wd FROM WorkDefinationEntity wd WHERE wd.orgId =:orgId and wd.workId=:workId")
     WorkDefinationEntity findWorkDefinitionByWorkId(@Param("workId") Long workId, @Param("orgId") Long orgId);
 
     @Query("SELECT workId FROM WorkDefinationEntity wd WHERE wd.orgId =:orgId and wd.workcode=:workCode")
     Long findWorkIdByWorkCode(@Param("workCode")String workCode, @Param("orgId")Long orgId);
 
     @Query("SELECT wd FROM WorkDefinationEntity wd WHERE wd.orgId =:orgId and (wd.workStatus='TS' or wd.workStatus='P')")
     List<WorkDefinationEntity> findAllWorkDefinitionsforSchedularByOrgId(@Param("orgId") Long orgId);
     
     @Modifying
     @Query("UPDATE WorkDefinationEntity a SET a.latitude =:latitude, a.longitude =:longitude where a.workId =:workId")
     void updateWorkDefLatLong( @Param("latitude") String latitude, @Param("longitude") String longitude,@Param("workId") Long workId);

     
}
