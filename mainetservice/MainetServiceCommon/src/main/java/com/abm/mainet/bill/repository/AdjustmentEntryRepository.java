package com.abm.mainet.bill.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.cfc.challan.domain.AdjustmentMasterEntity;

public interface AdjustmentEntryRepository extends JpaRepository<AdjustmentMasterEntity, Long> {

    @Query("select a from AdjustmentMasterEntity a where  a.adjRefNo=:refNo and a.dpDeptId=:dpDeptId")
    List<AdjustmentMasterEntity> fetchHistory(@Param("refNo") String refNo, @Param("dpDeptId") Long dpDeptId);

    @Query("select a from AdjustmentMasterEntity a where a.adjRefNo in (:refNo) and a.dpDeptId=:dpDeptId and "
            + " a.orgId=:orgId ")
    List<AdjustmentMasterEntity> fetchModuleWiseAdjustmentByUniqueIds(@Param("dpDeptId") Long deptId,
            @Param("refNo") List<String> uniqueIds, @Param("orgId") long orgid);

    @Query("select a from AdjustmentMasterEntity a where a.adjRefNo =:refNo and a.dpDeptId=:dpDeptId and "
            + " a.orgId=:orgId")
    List<AdjustmentMasterEntity> fetchAdjustmentByForPaidBill(@Param("dpDeptId") Long deptId,
            @Param("refNo") String csIdn, @Param("orgId") long orgid);
}
