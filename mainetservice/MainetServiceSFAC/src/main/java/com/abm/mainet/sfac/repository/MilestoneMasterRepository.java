package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.MilestoneCBBODetEntity;
import com.abm.mainet.sfac.domain.MilestoneMasterEntity;

@Repository
public interface MilestoneMasterRepository extends JpaRepository<MilestoneMasterEntity, Long> {

	List<MilestoneMasterEntity> findByIaId(Long iaId);

	List<MilestoneMasterEntity> findByIaIdAndMilestoneId(Long iaId, String milestoneId);

	List<MilestoneMasterEntity> findByMsIdNotInAndMilestoneCBBODetEntitiesIn(List<Long> msIdList,
			List<MilestoneCBBODetEntity> milestoneCBBODetEntities);

	List<MilestoneMasterEntity> findByMilestoneCBBODetEntitiesIn(List<MilestoneCBBODetEntity> milestoneCBBODetEntities);

}
