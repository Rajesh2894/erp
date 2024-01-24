package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.CBBOMasterEntity;
import com.abm.mainet.sfac.domain.FPOManagementCostMasterEntity;
import com.abm.mainet.sfac.domain.FPOMasterEntity;
import com.abm.mainet.sfac.domain.IAMasterEntity;

@Repository
public interface FPOManagementCostMasterRepository extends JpaRepository<FPOManagementCostMasterEntity, Long>{
	
	List<FPOManagementCostMasterEntity> findByFpoMasterEntityAndCbboMasterEntityAndIaMasterEntityAndFinancialYear(FPOMasterEntity fpoMasterEntity, CBBOMasterEntity cbboMasterEntity, IAMasterEntity iaMasterEntity, Long fyId);

	List<FPOManagementCostMasterEntity> findByFpoMasterEntityAndCbboMasterEntity(FPOMasterEntity fpoMasterEntity, CBBOMasterEntity cbboMasterEntity);

	List<FPOManagementCostMasterEntity> findByFpoMasterEntityAndCbboMasterEntityAndFinancialYear(FPOMasterEntity fpoMasterEntity,
			CBBOMasterEntity cbboMasterEntity, Long fyId);

	List<FPOManagementCostMasterEntity> findByFpoMasterEntityAndCbboMasterEntityAndIaMasterEntity(FPOMasterEntity fpoMasterEntity,
			CBBOMasterEntity cbboMasterEntity, IAMasterEntity iaMasterEntity);

	FPOManagementCostMasterEntity findByApplicationNumber(Long appNumber);

}
