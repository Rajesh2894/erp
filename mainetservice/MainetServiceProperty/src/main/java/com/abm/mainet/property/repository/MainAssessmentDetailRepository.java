package com.abm.mainet.property.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.property.domain.AssesmentDetailEntity;
import com.abm.mainet.property.domain.AssesmentMastEntity;

@Repository
public interface MainAssessmentDetailRepository extends JpaRepository<AssesmentDetailEntity, Long> {

    @Query("SELECT c.proAssdId FROM AssesmentDetailEntity c WHERE c.mnAssId =:findOne and c.assdUnitNo =:unitNo")
    Long fetchAssDetailIdByAssIdAndUnitNo(@Param("findOne") AssesmentMastEntity findOne, @Param("unitNo") Long unitNo);

    @Query("SELECT c FROM AssesmentDetailEntity c WHERE c.mnAssId =:findOne")
    List<AssesmentDetailEntity> fetchAssdIdByAssId(@Param("findOne") AssesmentMastEntity findOne);

    @Modifying(clearAutomatically = true)
    @Query("update AssesmentDetailEntity a set a.occupierName=:occupierName,a.updatedBy=:empId,"
            + "a.lgIpMacUpd=:clientIpAddress,a.updatedDate = CURRENT_DATE where a.orgId=:orgId and  a.proAssdId =:proAssdId ")
    int updateOccupierName(@Param("orgId") Long orgId,
            @Param("proAssdId") Long proAssdId,
            @Param("occupierName") String occupierName, @Param("empId") Long empId,
            @Param("clientIpAddress") String clientIpAddress);
}
