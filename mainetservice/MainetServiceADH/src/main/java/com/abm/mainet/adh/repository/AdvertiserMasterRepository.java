package com.abm.mainet.adh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.adh.domain.AdvertiserMasterEntity;
import com.abm.mainet.adh.domain.AdvertiserMasterHistoryEntity;

/**
 * @author cherupelli.srikanth
 * @since 02 august 2019
 */
@Repository
public interface AdvertiserMasterRepository extends JpaRepository<AdvertiserMasterEntity, Long> {

    @Query("SELECT am FROM  AdvertiserMasterEntity am WHERE am.orgId=:orgId and agencyStatus='A'")
    List<AdvertiserMasterEntity> findByOrgId(@Param("orgId") Long orgId);

    @Query("SELECT am FROM  AdvertiserMasterEntity am WHERE am.orgId=:orgId and am.agencyStatus =:agencyStatus ")
    List<AdvertiserMasterEntity> findByOrgIdAndStatus(@Param("orgId") Long orgId, @Param("agencyStatus") String agencyStatus);

    @Query("SELECT am FROM  AdvertiserMasterEntity am WHERE am.orgId=:orgId AND am.agencyId=:agencyId ")
    AdvertiserMasterEntity findByOrgIdAndAgencyId(@Param("orgId") Long orgId, @Param("agencyId") Long agencyId);

    @Query("SELECT am FROM AdvertiserMasterEntity am WHERE am.apmApplicationId=:applicationId AND am.orgId=:orgId")
    List<AdvertiserMasterEntity> findByApplicationIdAndOrgId(@Param("applicationId") Long applicationId,
            @Param("orgId") Long orgId);

    @Modifying
    @Query("update TbCfcApplicationMstEntity am set am.apmAppRejFlag =:approvalStatus ,am.apmApprovedBy =:approvedBy where am.apmApplicationId =:applicationId")
    void updateAgencyApprovalWorkflow(@Param("approvalStatus") String approvalStatus,
            @Param("approvedBy") Long approvedBy, @Param("applicationId") Long applicationId);

    @Query("SELECT ah FROM AdvertiserMasterHistoryEntity ah WHERE ah.agencyId=:agencyId AND ah.orgId=:orgId")
    List<AdvertiserMasterHistoryEntity> findAllLicPeriodDetails(@Param("agencyId") Long agencyId,
            @Param("orgId") Long orgId);

    @Query("SELECT am FROM AdvertiserMasterEntity am WHERE am.agencyLicNo=:agencyLicNo AND am.orgId=:orgId")
    AdvertiserMasterEntity findAgencyByAgencyLicNoAndOrgId(@Param("agencyLicNo") String agencyLicNo,
            @Param("orgId") Long orgId);

    @Query("SELECT am From AdvertiserMasterEntity am where am.agencyCategory =:agencyCategoryId and am.orgId =:orgId and am.agencyStatus = 'A' ")
    List<AdvertiserMasterEntity> findByAgencyCategoryAndOrgId(@Param("agencyCategoryId") Long agencyCategoryId,
            @Param("orgId") Long orgId);
    //Defect #137750 apmApplicationId !=null condition added
    @Query("SELECT am.agencyLicNo, am.agencyName FROM  AdvertiserMasterEntity am WHERE am.orgId=:orgId AND am.agencyStatus=:agencyStatus AND am.apmApplicationId !=null")
    List<String[]> findLicNoByOrgId(@Param("orgId") Long orgId, @Param("agencyStatus") String agencyStatus);

    @Query("SELECT am.agencyLicNo,am.agencyStatus,am.agencyName, add.apaMobilno FROM AdvertiserMasterEntity am , CFCApplicationAddressEntity add WHERE am.orgId=:orgId and am.apmApplicationId = add.apmApplicationId  ")
    List<Object[]> findLicNoAndAgenNameStatusByorgId(@Param("orgId") Long orgId);

    @Query("SELECT am.agencyLicNo FROM AdvertiserMasterEntity am WHERE am.apmApplicationId=:applicationId AND am.orgId=:orgId")
    String findLicNoByOrgIdAndAppId(@Param("applicationId") Long applicationId, @Param("orgId") Long orgId);

    @Query("SELECT am.agencyName FROM AdvertiserMasterEntity am WHERE am.agencyId=:agencyId AND am.orgId=:orgId")
    String findAgencyNameByAgnIdAndOrgId(@Param("agencyId") Long agencyId, @Param("orgId") Long orgId);

    @Query("SELECT am.agencyName FROM AdvertiserMasterEntity am WHERE am.orgId=:orgId")
    List<String> findAgencyNameByOrgId(@Param("orgId") Long orgId);
    
    @Query("SELECT am FROM AdvertiserMasterEntity am WHERE am.apmApplicationId=:applicationId AND am.orgId=:orgId")
    AdvertiserMasterEntity findByApplicationIdOrgId(@Param("applicationId") Long applicationId,
            @Param("orgId") Long orgId);

}
