package com.abm.mainet.council.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.council.domain.CouncilActionTakenEntity;

@Repository
public interface CouncilActionTakenRepository extends JpaRepository<CouncilActionTakenEntity, Long> {

    List<CouncilActionTakenEntity> findByProposalIdAndOrgId(@Param("proposalId") Long proposalId, @Param("orgId") Long orgId);

}
