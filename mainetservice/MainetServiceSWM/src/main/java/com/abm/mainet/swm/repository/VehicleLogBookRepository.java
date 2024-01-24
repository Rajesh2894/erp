package com.abm.mainet.swm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.swm.domain.TripSheet;

@Repository
public interface VehicleLogBookRepository extends JpaRepository<TripSheet, Long>{
    
 
    
}
