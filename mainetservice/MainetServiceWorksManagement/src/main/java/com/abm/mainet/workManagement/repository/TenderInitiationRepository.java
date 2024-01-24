package com.abm.mainet.workManagement.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.workManagement.domain.BIDMasterEntity;
import com.abm.mainet.workManagement.domain.TenderMasterEntity;
import com.abm.mainet.workManagement.domain.TenderWorkEntity;

/**
 * @author hiren.poriya
 * @Since 20-Apr-2018
 */
@Repository
public interface TenderInitiationRepository
        extends CrudRepository<TenderMasterEntity, Long>, TenderInitiationRepositoryCustom {

    /**
     * to inactive prepared tender details
     * 
     * @param tndId : tender Id
     * @param empId : updated user Id
     */
    @Modifying
    @Query("UPDATE TenderMasterEntity tnd SET tnd.status ='N', tnd.updatedBy=:updatedBy, tnd.updatedDate=CURRENT_DATE  WHERE tnd.tndId=:tndId ")
    void inactivePreparedTender(@Param("tndId") Long tndId, @Param("updatedBy") Long updatedBy);

    /**
     * used to get all created tender.
     * 
     * @param orgId
     * @return List<TenderMasterEntity>
     */
    List<TenderMasterEntity> findByOrgId(Long orgId);

    /**
     * get All active projects object which used for tender initiation
     * 
     * @param orgId
     * @return project id, project description and project code.
     */
    @Query("select DISTINCT tnd.projMasEntity.projId, tnd.projMasEntity.projNameEng,tnd.projMasEntity.projCode,tnd.projMasEntity.projNameReg from TenderMasterEntity tnd Where tnd.status ='A' and tnd.orgId=:orgId")
    List<Object[]> getAllActiveTenderProjects(@Param("orgId") Long orgId);

    /**
     * delete multiple tender works ids.
     * 
     * @param removedWorkIds
     */

    @Modifying
    @Query("DELETE TenderWorkEntity work WHERE work.tndWId in (:tndWId)")
    void deleteWorksbyIds(@Param("tndWId") List<Long> removedWorkIds);

    /**
     * get tender master entity by tender number
     * 
     * @param tenderNo
     * @return TenderMasterEntity
     *//*
        * @Query("select td from TenderMasterEntity td where td.tenderNo=:tenderNo") TenderMasterEntity
        * getTenderByNumber(@Param("tenderNo") String tenderNo);
        */

    /**
     * get only initiated tender
     * 
     * @param orgId
     * @return List<TenderMasterEntity>
     */
    @Query("select td from TenderMasterEntity td where td.orgId=:orgId and td.tenderNo is not null")
    List<TenderMasterEntity> getAllInitiatedTenders(@Param("orgId") Long orgId);

    /**
     * update tender work assignee
     * 
     * @param workId
     * @param assignee
     * @param assignedDate
     */
    @Modifying
    @Query("UPDATE TenderWorkEntity tw SET tw.employee.empId =:assignee, tw.workAssignedDate=:assignedDate WHERE tw.workDefinationEntity.workId in(:workId)")
    void updateTenderWorkAssignee(@Param("workId") List<Long> workId, @Param("assignee") Long assignee,
            @Param("assignedDate") Date assignedDate);

    /**
     * remove tender work assignee and set it null
     * 
     * @param workId
     */
    @Modifying
    @Query("UPDATE TenderWorkEntity tw SET tw.employee.empId = null WHERE tw.workDefinationEntity.workId in(:workId)")
    void deleteTenderWorkAssignee(@Param("workId") List<Long> workId);

    /**
     * get tender master entity by tender Id
     * 
     * @param tenderId
     * @return TenderMasterEntity
     */
    @Query("select td from TenderMasterEntity td JOIN FETCH td.tenderWorkList tw where td.tndId=:tenderId")
    TenderMasterEntity getTenderById(@Param("tenderId") Long tenderId);

    /**
     * used to generate LOA report by Tender Work Details Id
     * 
     * @param tndId
     * @param orgid
     * @return
     */
    @Query("select tw from TenderWorkEntity tw where tw.tenderMasEntity.tndId=:tndId and tw.orgId=:orgId")
    TenderWorkEntity generateLOA(@Param("tndId") Long tndId, @Param("orgId") Long orgId);

    /**
     * used to get tender details by loa number
     * 
     * @param tndLOANo
     * @param orgid
     * @return
     */
    @Query("select tw from TenderWorkEntity tw where tw.tndLOANo=:tndLOANo and tw.orgId=:orgId")
    TenderWorkEntity getTenderDetailsByLoaNumber(@Param("tndLOANo") String tndLOANo, @Param("orgId") Long orgId);

    /**
     * 
     * @param contId
     * @return TenderWorkEntity
     */
    @Query("select tw from TenderWorkEntity tw where tw.contractId=:contId ")
    TenderWorkEntity getWorkNameByConId(@Param("contId") Long contId);

    /**
     * used to UPDATE Tender Work by work id
     * 
     * @param workId
     * @param contId
     * @param empId
     */
    @Modifying
    @Query("UPDATE TenderWorkEntity a set a.contractId=:contractId, a.updatedBy=:empId, a.updatedDate = CURRENT_DATE WHERE a.workDefinationEntity.workId=:workId")
    void updateContractId(@Param("contractId") Long contractId, @Param("empId") Long empId,
            @Param("workId") Long workId);

    /**
     * get Contract Details By WorkId
     * 
     * @param workId
     * @return
     */
    @Query("select tw from TenderWorkEntity tw where tw.workDefinationEntity.workId=:workId")
    TenderWorkEntity getContractDetailsByWorkId(@Param("workId") Long workId);

    /**
     * find Tender rBy Vender Id
     * 
     * @param venderId
     * @return List<TenderWorkEntity>
     */
    @Query("select tw from TenderWorkEntity tw where tw.vendorMaster.vmVendorid=:venderId")
    List<TenderWorkEntity> findTenderByVenderId(@Param("venderId") Long venderId);

    /**
     * find NIT And Pq Doc Form Details By WorkId
     * 
     * @param workId
     * @param orgId
     * @return TenderWorkEntity
     */
    @Query("Select tw from TenderWorkEntity tw where tw.workDefinationEntity.workId=:workId and  tw.orgId =:orgId ")
    TenderWorkEntity findNITAndPqDocFormDetailsByWorkId(@Param("workId") Long workId, @Param("orgId") Long orgId);

    /**
     * update Tender Status
     * @param orgId
     * @param initiationNo
     * @param updateFlag
     */
    @Modifying
    @Query("UPDATE TenderMasterEntity  tm  SET tm.status =:updateFlag WHERE tm.orgId =:orgId and tm.initiationNo =:initiationNo ")
    void updateTenderStatus(@Param("orgId") Long orgId, @Param("initiationNo") String initiationNo,
            @Param("updateFlag") String updateFlag);

    /**
     * get Tender Id By Initiation Number
     * @param tenderInitiationNo
     * @param orgId
     * @return tendId
     */
    @Query("Select tm.tndId from TenderMasterEntity tm where tm.initiationNo =:tenderInitiationNo and tm.orgId =:orgId ")
    Long getTenderIdByInitiationNumber(@Param("tenderInitiationNo") String tenderInitiationNo, @Param("orgId") Long orgId);

    /***
     * find All Tender List
     * @param orgId
     * @return
     */
    @Query("select td from TenderMasterEntity td where td.orgId=:orgId and td.status NOT IN('Y' ,'N')")
    List<TenderMasterEntity> findAllTenderList(@Param("orgId") Long orgId);
    
    @Query("select td from TenderMasterEntity td where td.orgId=:orgId and td.status ='A' ")
    List<TenderMasterEntity> findAllApprovedTender(@Param("orgId") Long orgId);

    /**
     * get All active projects object which used for tender initiation for Tender Department Approval
     * 
     * @param orgId
     * @return project id, project description and project code.
     */
    @Query("select DISTINCT tnd.projMasEntity.projId, tnd.projMasEntity.projNameEng,tnd.projMasEntity.projCode from TenderMasterEntity tnd Where tnd.status !='N' and tnd.orgId=:orgId")
    List<Object[]> getActiveProjectTenderServices(@Param("orgId") Long orgId);
    
    @Query("select tm from TenderWorkEntity tm where tm.tenderMasEntity.tenderNo =:tenderNo and tm.orgId =:orgId and tm.tndSubmitDate=:tndSubmitDate")
    List<TenderWorkEntity> searchTenderByTendernoAndDate(@Param("orgId") Long orgid, @Param("tenderNo") String tenderNo, @Param("tndSubmitDate") Date tndSubmitDate);
    
    @Query("select bm from BIDMasterEntity bm where bm.orgId=:orgId and bm.tenderMasterEntity.tndId=:tndId")
    List<BIDMasterEntity> getBidMAsterByTndId(@Param("orgId") Long orgId,@Param("tndId") Long tndId);
    
    @Query("select bm from BIDMasterEntity bm where bm.orgId=:orgId and bm.bidId=:bidId")
    BIDMasterEntity getBidMAsterByBIDId(@Param("orgId") Long orgId,@Param("bidId") Long bidId);
    
    //D80043
    @Query("select tw.workDefinationEntity.workId from TenderWorkEntity tw where tw.tndLOANo=:tndLOANo and tw.orgId=:orgId")
    Long getWorkIdByLoaNumber(@Param("tndLOANo") String tndLOANo, @Param("orgId") Long orgId);
    
    @Query("select tw.vendorMaster.vmVendorid from TenderWorkEntity tw where tw.workDefinationEntity.workId=:workId")
    Long getVendorByWorkId(@Param("workId") Long workId);
    
    @Query("select td from TenderMasterEntity td where td.orgId=:orgId and td.projMasEntity.projId=:projId")
    List<TenderMasterEntity> getAllInitiatedTenders(@Param("projId") Long projId,@Param("orgId") Long orgId);
    
    @Query("select bm from BIDMasterEntity bm where bm.orgId=:orgId and bm.projectid=:projId and bm.tndNo=:tndNo")
    List<BIDMasterEntity> getBidMAster(@Param("orgId") Long orgId,@Param("projId") Long projId,@Param("tndNo") String tndNo);
    
    @Query("Select tm from TenderMasterEntity tm where tm.initiationNo =:tenderInitiationNo and tm.orgId =:orgId ")
    TenderMasterEntity getTenderByInitiationNumber(@Param("tenderInitiationNo") String tenderInitiationNo, @Param("orgId") Long orgId);

    @Query("select bm from BIDMasterEntity bm where bm.orgId=:orgId ")
    List<BIDMasterEntity> getBidMAsterByOrgId(@Param("orgId") Long orgId );
    
    @Query("select tw from TenderWorkEntity tw where tw.tndWId=:tndId and tw.orgId=:orgId")
    TenderWorkEntity generateLOANo(@Param("tndId") Long tndId, @Param("orgId") Long orgId);
 
    @Query("select tw.tenderType from TenderWorkEntity tw where tw.tenderMasEntity.tndId=:tndId and tw.orgId=:orgId")
    List<Long> getTenderTypeByTenderId(@Param("tndId") Long tndId, @Param("orgId") Long orgId);
	

}
