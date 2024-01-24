package com.abm.mainet.rts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.rts.domain.DrainageConnectionRoadDetEntity;


@Repository
public interface DrainageConnectionDetailRepository extends JpaRepository<DrainageConnectionRoadDetEntity, Long> {

   
}