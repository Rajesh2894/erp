package com.abm.mainet.property.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.property.domain.AssesmentMastEntity;

public interface SelfAssessmentMasJpaRepository extends
        CrudRepository<AssesmentMastEntity, Long> {

    @Query("SELECT tm FROM  TbTaxMasEntity tm WHERE tm.orgid=:orgId"
            + " AND tm.taxApplicable=:applicableTax AND tm.department.dpDeptid=:deptid "
            + "AND tm.taxCategory1 not like 'Addition%' ORDER BY tm.taxDisplaySeq ASC")
    List<TbTaxMasEntity> getTaxMaster(@Param("orgId") Long orgId, @Param("deptid") Long deptId,
            @Param("applicableTax") long taxApplicableAt);

    @Query("SELECT tm FROM  TbTaxMasEntity tm  WHERE tm.orgid=:orgId"
            + " AND tm.taxApplicable=:applicableTax AND tm.department.dpDeptid=:deptid "
            + "AND tm.taxCategory1 like 'Addition%' ORDER BY tm.taxDisplaySeq ASC")
    List<TbTaxMasEntity> getAdditionalTax(@Param("orgId") Long orgId, @Param("deptid") Long deptId,
            @Param("applicableTax") long taxApplicableAt);

    @Query("SELECT DISTINCT tm.taxDescId FROM TbTaxMasEntity tm,TbComparamMasEntity tcm,TbComparamDetEntity tcd WHERE tcm.cpmPrefix='TXN'"
            + " AND tcd.cpdValue='EPD' and tm.orgid=:orgId AND tm.department.dpDeptid=:deptId AND tm.taxDescId=:taxDescId")
    Long findTaxDescIdByDeptIdAndOrgId(@Param("orgId") Long orgId, @Param("deptId") Long deptId,
            @Param("taxDescId") Long taxDescId);
}
