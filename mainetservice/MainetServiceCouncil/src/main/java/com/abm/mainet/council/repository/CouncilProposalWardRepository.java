package com.abm.mainet.council.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.council.domain.CouncilProposalWardEntity;

@Repository
public interface CouncilProposalWardRepository extends JpaRepository<CouncilProposalWardEntity, Long>{
	
	@Query("select w from CouncilProposalWardEntity w where w.wardId=:wardId and w.orgId=:orgId")
	List<CouncilProposalWardEntity> fetchAllProposalId(@Param("wardId") Long wardId, @Param("orgId") Long orgId);
	
}
