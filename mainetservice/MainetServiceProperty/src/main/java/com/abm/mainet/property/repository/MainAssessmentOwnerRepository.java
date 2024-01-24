package com.abm.mainet.property.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.property.domain.AssesmentMastEntity;
import com.abm.mainet.property.domain.AssesmentMastHistEntity;
import com.abm.mainet.property.domain.AssesmentOwnerDtlEntity;
import com.abm.mainet.property.domain.AssesmentOwnerDtlHistEntity;

@Repository
public interface MainAssessmentOwnerRepository extends JpaRepository<AssesmentOwnerDtlEntity, Long> {
    @Query("SELECT c.proAssoId FROM AssesmentOwnerDtlEntity c WHERE c.mnAssId =:mnAssId")
    Long fetchAssOwnerIdByAssId(@Param("mnAssId") AssesmentMastEntity assNo);

    @Query("SELECT c FROM AssesmentOwnerDtlEntity c WHERE c.assNo =:assNo")
    AssesmentOwnerDtlEntity fetchOwnerDetailByPropNo(@Param("assNo") String assNo);

    @Query("SELECT c FROM AssesmentOwnerDtlEntity c WHERE c.assNo =:assNo and c.orgId=:orgId")
    List<AssesmentOwnerDtlEntity> fetchOwnerDetailListByPropNo(@Param("assNo") String assNo, @Param("orgId") Long orgId);

    @Query("SELECT c FROM AssesmentOwnerDtlEntity c WHERE c.mnAssId =:mnAssId and c.orgId=:orgId")
    List<AssesmentOwnerDtlEntity> fetchOwnerDetailListByProAssId(@Param("mnAssId") AssesmentMastEntity mnAssId,
            @Param("orgId") Long orgId);

    @Modifying(clearAutomatically = true)
    @Query("update AssesmentOwnerDtlEntity a set a.assoOwnerName=:assoOwnerName, a.assoMobileno=:assoMobileno,a.assoAddharno=:assoAddharno,"
            + " a.genderId=:genderId, a.relationId=:relationId, a.assoGuardianName=:assoGuardianName, a.eMail=:eMail, a.assoPanno=:assoPanno, "
            + "a.updatedBy=:empId,a.lgIpMacUpd=:clientIpAddress,a.updatedDate = CURRENT_DATE where a.orgId=:orgId and  a.assNo =:propNo")
    int updateOwnerDetailByPropNo(@Param("orgId") Long orgId,
            @Param("propNo") String propNo,
            @Param("assoOwnerName") String assoOwnerName, @Param("assoMobileno") String assoMobileno,
            @Param("assoAddharno") Long assoAddharno, @Param("genderId") Long genderId, @Param("relationId") Long relationId,
            @Param("assoGuardianName") String assoGuardianName, @Param("eMail") String eMail,
            @Param("assoPanno") String assoPanno, @Param("empId") Long empId,
            @Param("clientIpAddress") String clientIpAddress);

    @Modifying(clearAutomatically = true)
    @Query("update AssesmentOwnerDtlEntity a set a.assoOwnerName=:assoOwnerName, a.assoMobileno=:assoMobileno,a.assoAddharno=:assoAddharno,"
            + " a.genderId=:genderId, a.relationId=:relationId, a.assoGuardianName=:assoGuardianName, a.eMail=:eMail, a.assoPanno=:assoPanno, "
            + "a.updatedBy=:empId,a.lgIpMacUpd=:clientIpAddress,a.updatedDate = CURRENT_DATE where a.orgId=:orgId and  a.proAssoId =:proAssoId")
    int updateOwnerDetailByProAssoId(@Param("orgId") Long orgId,
            @Param("proAssoId") Long proAssoId,
            @Param("assoOwnerName") String assoOwnerName, @Param("assoMobileno") String assoMobileno,
            @Param("assoAddharno") Long assoAddharno, @Param("genderId") Long genderId, @Param("relationId") Long relationId,
            @Param("assoGuardianName") String assoGuardianName, @Param("eMail") String eMail,
            @Param("assoPanno") String assoPanno, @Param("empId") Long empId,
            @Param("clientIpAddress") String clientIpAddress);

    @Query("SELECT c FROM AssesmentOwnerDtlHistEntity c WHERE c.apmApplicationId =:apmApplicationId")
    List<AssesmentOwnerDtlHistEntity> fetchAssessmentOwnerHistByAppId(@Param("apmApplicationId") Long apmApplicationId);

    @Query("SELECT c FROM AssesmentMastHistEntity c WHERE c.apmApplicationId =:apmApplicationId")
    AssesmentMastHistEntity fetchAssessmentMasterHistByAppId(@Param("apmApplicationId") Long apmApplicationId);

    @Modifying(clearAutomatically = true)
    @Query("update AssesmentOwnerDtlEntity a set a.assoOwnerName=:assoOwnerName, a.assoMobileno=:assoMobileno,a.assoAddharno=:assoAddharno,"
            + " a.genderId=:genderId, a.relationId=:relationId, a.assoGuardianName=:assoGuardianName, a.eMail=:eMail, a.assoPanno=:assoPanno, "
            + "a.updatedBy=:empId,a.lgIpMacUpd=:clientIpAddress,a.updatedDate = CURRENT_DATE where a.orgId=:orgId and  a.mnAssId =:mnAssId")
    int updateOwnerDetailByMnAssId(@Param("orgId") Long orgId,
            @Param("mnAssId") Long mnAssId,
            @Param("assoOwnerName") String assoOwnerName, @Param("assoMobileno") String assoMobileno,
            @Param("assoAddharno") Long assoAddharno, @Param("genderId") Long genderId, @Param("relationId") Long relationId,
            @Param("assoGuardianName") String assoGuardianName, @Param("eMail") String eMail,
            @Param("assoPanno") String assoPanno, @Param("empId") Long empId,
            @Param("clientIpAddress") String clientIpAddress);

    @Query("SELECT p.assNo FROM  AssesmentOwnerDtlEntity p  where  p.assoMobileno=:mobileNo")
    List<String> fetchPropertyOwnerByMobileNo(@Param("mobileNo") String mobileNo);

    @Query("SELECT c FROM AssesmentOwnerDtlEntity c WHERE c.assNo =:assNo")
    List<AssesmentOwnerDtlEntity> fetchOwnerDetailListByPropNo(@Param("assNo") String assNo);

    @Modifying(clearAutomatically = true)
    @Query("update AssesmentOwnerDtlEntity a set a.assoMobileNoOtp=:otp, a.updatedDate=:updatedDate where a.proAssoId=:proAssoId")
    int updateOTPByProAssoId(@Param("proAssoId") Long proAssoId, @Param("otp") Long otp, @Param("updatedDate") Date updatedDate);

    @Modifying(clearAutomatically = true)
    @Query("update AssesmentOwnerDtlEntity own set own.assoMobileno=:mobileNo, own.eMail=:emailId, own.updatedDate=:updatedDate where own.proAssoId=:ownerId and own.assoOType='P'")
    int updateMobileAndEmailForMobile(@Param("mobileNo") String mobileNo, @Param("emailId") String emailId,
            @Param("ownerId") Long ownerId, @Param("updatedDate") Date updatedDate);
    
    @Modifying(clearAutomatically = true)
    @Query("update AssesmentOwnerDtlEntity own set own.assoMobileno=:mobileNo where own.mnAssId.proAssId=:assId and own.assoOType=:primaryOwn")
    int updateMobileForMPOS(@Param("mobileNo") String mobileNo, @Param("assId") Long assId,@Param("primaryOwn") String primaryOwn);
}
