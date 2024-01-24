package com.abm.mainet.securitymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.securitymanagement.domain.DeploymentOfStaffHist;

@Repository
public interface DeploymentOfStaffHistoryRepository extends JpaRepository<DeploymentOfStaffHist,Long>{

}
