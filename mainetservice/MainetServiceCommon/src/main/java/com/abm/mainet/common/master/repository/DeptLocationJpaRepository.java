package com.abm.mainet.common.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.domain.DeptLocationEntity;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.master.dto.TbDepartment;

/**
 * Repository : TbDeptLocation.
 */
public interface DeptLocationJpaRepository extends PagingAndSortingRepository<DeptLocationEntity, Long> {

    /**
     * @return
     */
    @Query("select locationMas from LocationMasEntity locationMas where locationMas.organisation.orgid = :orgId and locationMas.locActive='Y'")
    List<LocationMasEntity> getLocationList(@Param("orgId") Long orgId);

    @Query("select count(*) from Employee emp where emp.organisation.orgid = :orgId and emp.tbDepartment.dpDeptid=:deptId and emp.tbLocationMas.locId=:locId")
    long getEmpCount(@Param("orgId") Long orgId, @Param("deptId") Long deptId, @Param("locId") Long locId);

    @Query("select a from Department a,DeptOrgMap b where a.status in ('A') and "
            + "a.dpDeptid=b.department.dpDeptid and b.orgid=:orgId and "
            + "a.dpDeptid not in (select k.tbDepartment.dpDeptid from DeptLocationEntity k  "
            + "where k.isdeleted=0 and k.locId=:locId) order by a.dpDeptdesc asc")
    List<TbDepartment> getAllDept(@Param("orgId") Long orgId, @Param("locId") Long locId);

    @Query("select count(*) from Department a,DeptOrgMap b where a.status in ('A') and a.dpDeptid=b.department.dpDeptid and b.orgid=:orgId and a.dpDeptid not in (select k.tbDepartment.dpDeptid from DeptLocationEntity k  where k.isdeleted=0 and k.locId=:locId) order by a.dpDeptdesc asc")
    long getAllDeptCount(@Param("orgId") Long orgId, @Param("locId") Long locId);

    @Query("select count(*) from DeptLocationEntity k WHERE k.locId=:locId AND k.tbOrganisation.orgid=:orgId AND k.isdeleted=0 ")
    int getDeptLocationCount(@Param("orgId") Long orgId, @Param("locId") Long locId);

    /*
     * @Query("select l from LocationMasEntity l, DeptLocationEntity dl LEFT JOIN dl.tbDepartment d where " +
     * "l.locId = dl.locId " + "and d.dpDeptid =:deptId " + "and dl.tbOrganisation.orgid=:orgId " + "and l.locActive='Y'")
     */
    @Query("select l from LocationMasEntity l, DeptLocationEntity d where l.locId = d.locId and d.tbDepartment.dpDeptid =:deptId and d.tbOrganisation.orgid=:orgId and l.locActive='Y'")
    List<LocationMasEntity> findByOrgIdAndDeptId(@Param("orgId") Long orgId, @Param("deptId") Long deptId);

}