package com.abm.mainet.mrm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.mrm.domain.Marriage;

@Repository
public interface MarriageRepository extends JpaRepository<Marriage, Long> {

    @Modifying
    @Query("UPDATE Marriage  m SET m.status =:status, m.updatedBy =:updatedBy, m.lgIpMacUpd =:lgIpMacUpd, m.updatedDate = CURRENT_TIMESTAMP "
            + "WHERE m.marId =:marId ")
    void updateMarriageRegStatus(@Param("status") String status, @Param("updatedBy") Long updatedBy,
            @Param("lgIpMacUpd") String lgIpMacUpd,
            @Param("marId") Long marId);

    List<Marriage> findByApplicationIdAndOrgIdOrderByMarIdDesc(Long applicationId, Long orgId);

    Marriage findBySerialNoAndOrgId(String serialNo, Long orgId);

    @Query("select case when count(m)>0 THEN true ELSE false END from Marriage m where m.applicationId =:applicationId AND m.orgId =:orgId")
    Boolean checkApplicationIdExist(@Param("applicationId") Long applicationId, @Param("orgId") Long orgId);

    Marriage findByApplicationIdAndOrgId(Long applicationId, Long orgId);

}
