package com.abm.mainet.workManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.workManagement.domain.MileStoneEntryEntity;
import com.abm.mainet.workManagement.domain.MilestoneDetail;

@Repository
public interface MilestoneEntryRepository extends CrudRepository<MileStoneEntryEntity, Long> {

	@Query("select a from MileStoneEntryEntity a where  a.projId=:projId and a.workId=:workId and a.orgId=:orgId")
	List<MileStoneEntryEntity> getAllMilestoneEntries(@Param("projId") Long projId, @Param("workId") Long workId,
			@Param("orgId") Long orgId);

	@Query("select a from MileStoneEntryEntity a where  a.projId=:projId and a.workId=:workId and a.orgId=:orgId and a.milestoneName=:milestoneName")
	List<MileStoneEntryEntity> checkMilestone(@Param("projId") Long projId, @Param("workId") Long workId,
			@Param("orgId") Long orgId, @Param("milestoneName") String milestoneName);

}
