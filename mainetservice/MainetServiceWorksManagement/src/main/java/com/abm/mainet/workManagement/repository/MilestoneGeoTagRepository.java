package com.abm.mainet.workManagement.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.workManagement.domain.MilestoneGeoTag;

@Repository
public interface MilestoneGeoTagRepository extends CrudRepository<MilestoneGeoTag, Long> {

	@Modifying
	@Query("update MilestoneGeoTag a set a.status = ?2,a.updatedDate = CURRENT_DATE where a.atdId = ?1")
	void updateGeoTagImageStatus(Long atdId, String status);
}
