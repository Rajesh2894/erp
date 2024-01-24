package com.abm.mainet.property.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.property.domain.ProvisionalAssesmentMstEntity;
import com.abm.mainet.property.domain.ProvisionalAssesmentOwnerDtlEntity;

@Repository
public interface ProvisionalAssesmentOwnerRepository extends JpaRepository<ProvisionalAssesmentOwnerDtlEntity, Long> {

    @Query("SELECT c FROM ProvisionalAssesmentOwnerDtlEntity c WHERE c.tbAsAssesmentOwnerMast =:tbAsAssesmentOwnerMast and c.orgId=:orgId")
    List<ProvisionalAssesmentOwnerDtlEntity> fetchOwnerDetailListByProAssMast(
            @Param("tbAsAssesmentOwnerMast") ProvisionalAssesmentMstEntity tbAsAssesmentOwnerMast,
            @Param("orgId") Long orgId);

    @Modifying(clearAutomatically = true)
    @Query("update ProvisionalAssesmentOwnerDtlEntity a set a.assoOwnerName=:assoOwnerName, a.assoMobileno=:assoMobileno,a.assoAddharno=:assoAddharno,"
            + "a.assoGuardianName=:assoGuardianName, a.eMail=:eMail, a.assoPanno=:assoPanno, "
            + "a.updatedBy=:empId,a.lgIpMacUpd=:clientIpAddress,a.updatedDate = CURRENT_DATE where a.orgId=:orgId and  a.proAssoId =:proAssoId")
    int updateOwnerDetailByProAssoId(@Param("orgId") Long orgId,
            @Param("proAssoId") Long proAssoId,
            @Param("assoOwnerName") String assoOwnerName, @Param("assoMobileno") String assoMobileno,
            @Param("assoAddharno") Long assoAddharno, @Param("assoGuardianName") String assoGuardianName,
            @Param("eMail") String eMail, @Param("assoPanno") String assoPanno, @Param("empId") Long empId,
            @Param("clientIpAddress") String clientIpAddress);

    @Modifying(clearAutomatically = true)
    @Query("update ProvisionalAssesmentOwnerDtlEntity own set own.assoMobileno=:mobileNo, own.eMail=:emailId, own.updatedDate=:updatedDate where own.proAssoId=:ownerId and own.assoOType='P'")
    int updateMobileAndEmailForMobile(@Param("mobileNo") String mobileNo, @Param("emailId") String emailId,
            @Param("ownerId") Long ownerId, @Param("updatedDate") Date updatedDate);

    @Query("SELECT p.assNo FROM  ProvisionalAssesmentOwnerDtlEntity p  where  p.assoMobileno=:mobileNo")
    List<String> fetchPropertyOwnerByMobileNo(@Param("mobileNo") String mobileNo);

    @Query("SELECT c FROM ProvisionalAssesmentOwnerDtlEntity c WHERE c.assNo =:assNo")
    List<ProvisionalAssesmentOwnerDtlEntity> fetchOwnerDetailListByPropNo(@Param("assNo") String assNo);

    @Modifying(clearAutomatically = true)
    @Query("update ProvisionalAssesmentOwnerDtlEntity a set a.assoMobileNoOtp=:otp, a.updatedDate=:updatedDate where a.proAssoId=:proAssoId")
    int updateOTPByProAssoId(@Param("proAssoId") Long proAssoId, @Param("otp") Long otp, @Param("updatedDate") Date updatedDate);

}
