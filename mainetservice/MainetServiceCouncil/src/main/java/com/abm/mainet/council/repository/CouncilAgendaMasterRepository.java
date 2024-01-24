package com.abm.mainet.council.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.council.domain.CouncilAgendaMasterEntity;

@Repository
public interface CouncilAgendaMasterRepository extends JpaRepository<CouncilAgendaMasterEntity, Long> {

    Long countByOrgId(Long orgId);

}
