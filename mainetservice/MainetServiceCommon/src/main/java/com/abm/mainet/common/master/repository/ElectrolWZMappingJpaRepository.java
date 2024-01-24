
package com.abm.mainet.common.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.abm.mainet.common.domain.LocationElectrolWZMapping;

public interface ElectrolWZMappingJpaRepository extends PagingAndSortingRepository<LocationElectrolWZMapping, Long> {

    @Query("select a from LocationElectrolWZMapping a where a.locationMasEntity.locId= ?1")
    List<LocationElectrolWZMapping> findByLocationId(Long locId);
}
