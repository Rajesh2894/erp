package com.abm.mainet.care.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.care.domain.LandInspection;

@Repository
public interface LandInspectionRepository extends JpaRepository<LandInspection, Long> {

}
