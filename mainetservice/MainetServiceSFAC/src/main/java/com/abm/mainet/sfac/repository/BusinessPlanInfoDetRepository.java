package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.BusinessPlanInfoDetailEntity;
import com.abm.mainet.sfac.domain.FPOProfileManagementMaster;

@Repository
public interface BusinessPlanInfoDetRepository extends JpaRepository<BusinessPlanInfoDetailEntity, Long>{

	List<BusinessPlanInfoDetailEntity> findByFpoProfileMgmtMaster(FPOProfileManagementMaster fpoProfileManagemntMaster);

	List<BusinessPlanInfoDetailEntity> findByFpoProfileMgmtMasterAndDocumentName(
			FPOProfileManagementMaster fpoProfileManagemntMaster, String status);

	@Modifying
	@Query("UPDATE BusinessPlanInfoDetailEntity Bp SET Bp.documentName ='D', Bp.updatedBy =:updatedBy, Bp.updatedDate = CURRENT_DATE "
			+ "WHERE Bp.bpID in (:removeIds) ")
	void deActiveBPInfo(@Param("removeIds") List<Long> removeIds, @Param("updatedBy") Long updatedBy);

}
