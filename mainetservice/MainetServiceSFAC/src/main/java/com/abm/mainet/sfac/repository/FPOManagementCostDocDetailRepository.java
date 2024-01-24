package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.FPOManagementCostDocDetailEntity;
import com.abm.mainet.sfac.domain.FPOManagementCostMasterEntity;

@Repository
public interface FPOManagementCostDocDetailRepository extends JpaRepository<FPOManagementCostDocDetailEntity, Long>{

	List<FPOManagementCostDocDetailEntity> findByFpoManagementCostMasterEntity(FPOManagementCostMasterEntity fpoManagementCostMasterEntity);

	@Modifying
	@Query("UPDATE FPOManagementCostDocDetailEntity d SET d.status ='D', d.updatedBy =:updatedBy, d.updatedDate = CURRENT_DATE "
			+ "WHERE d.docId in (:removeIds) ")
	void deActiveBPInfo(@Param("removeIds") List<Long> removeIds, @Param("updatedBy") Long updatedBy);

	List<FPOManagementCostDocDetailEntity> findByFpoManagementCostMasterEntityAndStatus(
			FPOManagementCostMasterEntity fpoManagementCostMasterEntity, String type);

}
