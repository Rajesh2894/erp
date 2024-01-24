package com.abm.mainet.legal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.legal.domain.CounterAffidavitEntity;

@Repository
public interface CounterAffidavitRepository  extends JpaRepository<CounterAffidavitEntity, Long> 
{

	CounterAffidavitEntity findByOrgIdAndCaseId(Long orgId, Long caseId);
}
