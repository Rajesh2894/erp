package com.abm.mainet.property.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abm.mainet.property.domain.ProvisionalAssesmentOwnerDtlEntity;

public interface ProvisionalAssessOwnerRepository extends JpaRepository<ProvisionalAssesmentOwnerDtlEntity, Long> {

}
