/**
 *
 */
package com.abm.mainet.common.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.LocationOperationWZMapping;

public interface OperationWZMappingJpaRepository extends PagingAndSortingRepository<LocationOperationWZMapping, Long> {

    @Query("select a from LocationOperationWZMapping a where a.locationMasEntity.locId= ?1")
    LocationOperationWZMapping findByLocationId(Long locId);

    @Query("select a from LocationOperationWZMapping a where a.locationMasEntity.locId= ?1 and a.dpDeptId = ?2")
    LocationOperationWZMapping findByLocationId(Long locId, Long deptId);

    @Query("select a from LocationOperationWZMapping a where a.locationMasEntity.locId= ?1")
    List<LocationOperationWZMapping> findByLocationList(Long locId);

    /*
     * @Query("select a.locationMasEntity from LocationOperationWZMapping a, DeptLocationEntity dl LEFT JOIN dl.tbDepartment d where "
     * + "a.orgId=:orgId " + "and dl.tbOrganisation.orgid=:orgId " + "and a.locationMasEntity.locId = dl.locId " +
     * "and d.dpDeptid=:deptId " + "and a.locationMasEntity.locActive='Y'")
     */
    @Query("select a.locationMasEntity from LocationOperationWZMapping a where a.orgId=:orgId and a.dpDeptId=:deptId and a.locationMasEntity.locActive='Y' order by a.locationMasEntity.locNameEng")
    List<LocationMasEntity> findWZMappedLocationByOrgIdAndDeptId(@Param("orgId") Long orgId, @Param("deptId") Long deptId);
    
    @Query("select a.locationMasEntity.locId from LocationOperationWZMapping a where a.locationMasEntity.locId= ?1 and a.dpDeptId = ?2 and a.orgId= ?3 order by a.locowzmpId desc")
    List<Long> getOperLocationId(long locId,Long deptId,Long orgId);
}
