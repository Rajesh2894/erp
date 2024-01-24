package com.abm.mainet.vehiclemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abm.mainet.vehiclemanagement.domain.OEMWarranty;

public interface OEMWarrantyServiceRepository extends JpaRepository<OEMWarranty, Long>{
	
	OEMWarranty findByRefNoAndOrgid(String refNo,long orgId);

}
