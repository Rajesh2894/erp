package com.abm.mainet.vehiclemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.vehiclemanagement.domain.InsuranceDetail;

@Repository
public interface InsuranceDetailRepository extends JpaRepository<InsuranceDetail, Long> {

}
