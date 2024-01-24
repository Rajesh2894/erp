
package com.abm.mainet.adh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.adh.domain.HoardingMasterEntity;

/**
 * @author Anwarul.Hassan
 * @since 16-Aug-2019
 */
@Repository
public interface HoardingMasterRepository extends JpaRepository<HoardingMasterEntity, Long> {

    @Query("SELECT hm FROM HoardingMasterEntity hm WHERE hm.orgId =:orgId AND hm.hoardingId =:hoardingId")
    HoardingMasterEntity findByOrgIdAndHoardingId(@Param("orgId") Long orgId, @Param("hoardingId") Long hoardingId);

    @Query("SELECT hm FROM HoardingMasterEntity hm WHERE hm.orgId =:orgId")
    List<HoardingMasterEntity> getAllHoardingNumberByOrgId(@Param("orgId") Long orgId);

    @Query("SELECT hm FROM HoardingMasterEntity hm WHERE hm.orgId =:orgId AND hm.hoardingNumber =:hoardingNumber")
    HoardingMasterEntity findDetailsByOrgIdAndHoardingNo(@Param("orgId") Long orgId,
            @Param("hoardingNumber") String hoardingNumber);

    @Query("SELECT am.hoardingNumber FROM  HoardingMasterEntity am WHERE am.orgId=:orgId ")
    List<String> findHoardingNumberByOrgId(@Param("orgId") Long orgId);

    @Query("SELECT am.hoardingId, am.hoardingNumber FROM  HoardingMasterEntity am WHERE am.orgId=:orgId ")
    List<String[]> findHoardingNumberAndIdByOrgId(@Param("orgId") Long orgId);

    @Query("SELECT hm FROM HoardingMasterEntity hm WHERE hm.orgId =:orgId AND hm.hoardingId =:hoardingId")
    List<HoardingMasterEntity> findByOrgIdAndHoardingIdList(@Param("orgId") Long orgId, @Param("hoardingId") Long hoardingId);

}
