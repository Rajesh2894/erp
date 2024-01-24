package com.abm.mainet.property.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abm.mainet.property.domain.ProvisionalAssesmentFactorDtlEntity;

public interface ProvisionalAssesmentFactRepository extends JpaRepository<ProvisionalAssesmentFactorDtlEntity, Long> {

}
