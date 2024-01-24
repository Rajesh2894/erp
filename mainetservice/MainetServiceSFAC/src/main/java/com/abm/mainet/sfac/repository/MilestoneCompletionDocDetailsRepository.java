package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.MilestoneCompletionDocDetailsEntity;
import com.abm.mainet.sfac.domain.MilestoneCompletionMasterEntity;

@Repository
public interface MilestoneCompletionDocDetailsRepository extends JpaRepository<MilestoneCompletionDocDetailsEntity, Long>{

	List<MilestoneCompletionDocDetailsEntity> findByMilestoneCompletionMasterEntity(MilestoneCompletionMasterEntity milestoneCompletionMasterEntity);

}
