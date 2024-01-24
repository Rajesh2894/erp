package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.MilestoneDeliverablesEntity;
import com.abm.mainet.sfac.domain.MilestoneMasterEntity;

@Repository
public interface MilestoneDeliverablesRepository extends JpaRepository<MilestoneDeliverablesEntity, Long>{

	List<MilestoneDeliverablesEntity> findByMilestoneMasterEntity(MilestoneMasterEntity milestoneMasterEntity);

}
