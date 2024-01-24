/**
 *
 */
package com.abm.mainet.common.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.domain.LocationRevenueWZMapping;

public interface RevenueWZMappingJpaRepository extends PagingAndSortingRepository<LocationRevenueWZMapping, Long> {

    @Query("select a from LocationRevenueWZMapping a where a.locationMasEntity.locId= ?1")
    List<LocationRevenueWZMapping> findByLocationId(Long locId);

    @Query("select a.locationMasEntity.locId from LocationRevenueWZMapping a where a.codIdRevLevel1= ?1 and a.orgId=?2")
    Long getRevenueLocationId(long areaDivision1, Long orgId);

    @Query("select l.codIdRevLevel1 from LocationRevenueWZMapping l where l.locationMasEntity.locId =:locId and  l.orgId =:orgId")
    Long getcodIdRevLevel1ByLocId(@Param("locId") Long locId, @Param("orgId") Long orgId);
    
    @Query("select a.locationMasEntity.locId from LocationRevenueWZMapping a where a.codIdRevLevel1= ?1 and a.orgId= ?2 order by a.locrwzmpId desc")
    List<Long> getlocationListByFieldIdAndOrgId(long areaDivision1,Long orgId);

}
