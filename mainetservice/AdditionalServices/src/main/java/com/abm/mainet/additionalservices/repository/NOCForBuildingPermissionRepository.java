package com.abm.mainet.additionalservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.additionalservices.domain.NOCForBuildingPermissionEntity;

@Repository
public interface NOCForBuildingPermissionRepository extends JpaRepository<NOCForBuildingPermissionEntity, Long>{
	
	@Query("select b from NOCForBuildingPermissionEntity b where b.apmApplicationId=:applicationId and b.orgId=:orgId")
	public NOCForBuildingPermissionEntity findData(@Param("applicationId") Long applicationId , @Param("orgId")Long orgId );
	
	@Modifying
	   @Query("update NOCForBuildingPermissionEntity d set d.wfStatus=:wfStatus,d.status=:status where d.bpId=:bpId and d.orgId=:orgId")
	   void updateWorkFlowStatus(@Param("bpId")Long bpId,@Param("orgId")Long orgId,@Param("wfStatus")String wfStatus,@Param("status")String status);

	
	NOCForBuildingPermissionEntity findByRefNo(String refNo);
}
