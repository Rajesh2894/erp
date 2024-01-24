package com.abm.mainet.materialmgmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.materialmgmt.domain.BinDefMasEntity;

@Repository
public interface BinDefMasRepository extends JpaRepository<BinDefMasEntity, Long> {
    	
	List<BinDefMasEntity> findByOrgId(Long orgId);

	@Modifying
	@Query(value = "DELETE FROM MM_BIN_LOCATION_MAPPING WHERE BIN_LOC_ID is null", nativeQuery = true)
	void deleteByDefMappingId();
}
