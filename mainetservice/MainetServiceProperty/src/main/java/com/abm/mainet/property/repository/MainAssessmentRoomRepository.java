package com.abm.mainet.property.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.property.domain.AssessmentRoomDetailEntity;

@Repository
public interface MainAssessmentRoomRepository extends JpaRepository<AssessmentRoomDetailEntity, Long> {

}
