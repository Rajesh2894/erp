package com.abm.mainet.adh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.abm.mainet.adh.domain.NewAdvertisementApplication;

/**
 * @author vishwajeet.kumar
 * @since 24 Sept 2019
 */
@Repository
public interface NewAdvertisementApplicationRepository extends JpaRepository<NewAdvertisementApplication, Long> {

    @Query(" SELECT a From NewAdvertisementApplication a WHERE a.apmApplicationId =:applicationId and a.orgId = :orgId")
    NewAdvertisementApplication findAdvertisementDetailsByAppId(@Param("applicationId") long applicationId,
            @Param("orgId") Long orgId);

    @Query("SELECT a FROM NewAdvertisementApplication a WHERE a.orgId =:orgId")
    List<NewAdvertisementApplication> findAdvertisementDetailsByOrgId(@Param("orgId") Long orgId);

    @Query("SELECT a.licenseNo FROM  NewAdvertisementApplication a WHERE a.orgId=:orgId ")
    List<String> findLicenseNoByOrgId(@Param("orgId") Long orgId);

    @Query(" SELECT a FROM NewAdvertisementApplication a WHERE a.licenseNo=:licenseNo AND a.orgId=:orgId")
    NewAdvertisementApplication findAdvertisementDetailsByLicNoAndOrgId(@Param("licenseNo") String licenseNo,
            @Param("orgId") Long orgId);

    @Modifying
    @Query("UPDATE NewAdvertisementApplication set licenseNo =:advertisementLicenseNo where apmApplicationId =:applicationId and orgId =:orgId ")
    void updateAdvertisementLiceNo(@Param("applicationId") Long applicationId,
            @Param("advertisementLicenseNo") String advertisementLicenseNo, @Param("orgId") Long orgId);

    @Query("SELECT a.licenseNo , app.apmFname , app.apmLname, add.apaMobilno from NewAdvertisementApplication a,TbCfcApplicationMstEntity app, CFCApplicationAddressEntity add WHERE a.orgId =:orgId and a.licenseNo IS NOT NULL and a.apmApplicationId = app.apmApplicationId and a.apmApplicationId = add.apmApplicationId")
    List<Object[]> getLicenseNoByOrgId(@Param("orgId") Long orgId);

    @Query(" SELECT a FROM NewAdvertisementApplication a WHERE a.adhId=:adhId AND a.orgId=:orgId")
    NewAdvertisementApplication findAdvertisementDetailsByOrgIdAndAdhId(@Param("adhId") Long adhId,
            @Param("orgId") Long orgId);

    @Modifying
    @Query("delete from NewAdvertisementApplicationDet ad where ad.adhHrdDetId in(:removeAdverIds)")
    void deleteAdvetisementdetailByIds(@Param("removeAdverIds") List<Long> removeAdverIds);
}
