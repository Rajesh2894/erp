package com.abm.mainet.property.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.property.domain.ProvisionalAssesmentDetailEntity;
import com.abm.mainet.property.domain.ProvisionalAssesmentMstEntity;

@Repository
public interface ProvisionalAssesmentDetRepository extends JpaRepository<ProvisionalAssesmentDetailEntity, Long> {

    @Query("SELECT c.proAssdId FROM ProvisionalAssesmentDetailEntity c WHERE c.tbAsAssesmentMast =:mstEntity")
    List<Long> fetchProAssDetEntity(@Param("mstEntity") ProvisionalAssesmentMstEntity mstEntity);

    @Query("SELECT c FROM ProvisionalAssesmentDetailEntity c WHERE c.tbAsAssesmentMast =:findOne")
    List<ProvisionalAssesmentDetailEntity> fetchProAssDetailsByProAssMst(@Param("findOne") ProvisionalAssesmentMstEntity findOne);

    @Modifying(clearAutomatically = true)
    @Query("update ProvisionalAssesmentDetailEntity a set a.occupierName=:occupierName,a.updatedBy=:empId,"
            + "a.lgIpMacUpd=:clientIpAddress,a.updatedDate = CURRENT_DATE where a.orgId=:orgId and  a.proAssdId =:proAssdId ")
    int updateOccupierName(@Param("orgId") Long orgId,
            @Param("proAssdId") Long proAssdId,
            @Param("occupierName") String occupierName, @Param("empId") Long empId,
            @Param("clientIpAddress") String clientIpAddress);
}
