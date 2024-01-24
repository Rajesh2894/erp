/**
 * 
 */
package com.abm.mainet.adh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.adh.domain.HoardingMasterEntity;

/**
 * @author anwarul.hassan
 * @since 17-Oct-2019
 */
@Repository
public interface NewHoardingApplicationRepository extends JpaRepository<HoardingMasterEntity, Long> {

    @Query("SELECT am.hoardingNumber FROM  HoardingMasterEntity am WHERE am.orgId=:orgId ")
    List<String> findHoardingNumberByOrgId(@Param("orgId") Long orgId);

    @Query("SELECT am FROM HoardingMasterEntity am WHERE am.hoardingNumber=:hoardingNumber AND am.orgId=:orgId")
    HoardingMasterEntity findHoardingDetailsByHoardingNumberAndOrgId(@Param("hoardingNumber") String hoardingNumber,
            @Param("orgId") Long orgId);
    
    @Query("SELECT am FROM HoardingMasterEntity am WHERE am.orgId=:orgId")
    List<HoardingMasterEntity> findHoardingListByOrgId(@Param("orgId") Long orgId);
}
