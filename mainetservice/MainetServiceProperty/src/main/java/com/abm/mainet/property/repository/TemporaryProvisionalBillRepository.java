package com.abm.mainet.property.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.property.domain.TemporaryProvisionalBillMasEntity;

@Repository
public interface TemporaryProvisionalBillRepository extends JpaRepository<TemporaryProvisionalBillMasEntity, Long> {

    @Modifying
    @Query("DELETE from TemporaryProvisionalBillMasEntity a where a.userId=:empId and a.orgid=:orgId")
    void deleteRecords(@Param("empId") Long empId, @Param("orgId") Long orgId);

    @Modifying
    @Query("DELETE from TemporaryProvisionalBillDetEntity a where a.userId=:empId and a.orgid=:orgId")
    void deleteRecordFromTempProDet(@Param("empId") Long empId, @Param("orgId") Long orgId);

}
