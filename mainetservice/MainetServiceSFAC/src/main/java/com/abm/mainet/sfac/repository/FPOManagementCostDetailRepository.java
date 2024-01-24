package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.FPOManagementCostDetailEntity;
import com.abm.mainet.sfac.domain.FPOManagementCostMasterEntity;

@Repository
public interface FPOManagementCostDetailRepository extends JpaRepository<FPOManagementCostDetailEntity, Long>{

	List<FPOManagementCostDetailEntity> findByFpoManagementCostMasterEntity(
			FPOManagementCostMasterEntity fpoManagementCostMasterEntity);

}
