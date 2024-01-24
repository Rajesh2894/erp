package com.abm.mainet.common.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.domain.DeptOrgMap;

/**
 * Repository : TbDeporgMap.
 */
public interface TbDeporgMapJpaRepository extends PagingAndSortingRepository<DeptOrgMap, Long> {

    @Query("select tbDeporgMapEntity from DeptOrgMap tbDeporgMapEntity where "
            + "tbDeporgMapEntity.department.dpDeptid = :dpDeptid")
    List<DeptOrgMap> findDepOrgById(@Param("dpDeptid") Long dpDeptid);

    @Query("select department, deporgMap from Department department, DeptOrgMap deporgMap where department.dpDeptid = deporgMap.department.dpDeptid"
            + " and deporgMap.orgid = :orgId and deporgMap.mapStatus='A' order by department.dpDeptdesc asc")
    List<Object> getDepartmentData(@Param("orgId") Long orgId);

    @Query("select deporgMap from DeptOrgMap deporgMap where deporgMap.orgid = :orgId and deporgMap.department.dpDeptid=:dpDeptId")
    DeptOrgMap findByOrgIdDeptId(@Param("orgId") Long orgId, @Param("dpDeptId") Long dpDeptId);

    @Query("select count(*) from DeptLocationEntity a,LocationMasEntity b where a.locId = b.locId and b.deptLoc='Y' and a.tbDepartment.dpDeptid=:dpDeptId")
    int findActualDeptMapping(@Param("dpDeptId") Long dpDeptId);

    @Modifying
    @Query("delete from DeptOrgMap d where d.mapId=:mapId ")
    int deleteMapping(@Param("mapId") Long mapId);

}
