package com.abm.mainet.rts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.rts.domain.RtsExternalServicesEntity;

@Repository
public interface RtsExternalServicesRepository extends JpaRepository<RtsExternalServicesEntity, Long>{
	
	@Modifying
	   @Query("update RtsExternalServicesEntity d set d.status=:status where d.applicationId=:applicationId and d.orgId=:orgId")
	   void updateStatus(@Param("applicationId")Long applicationId,@Param("orgId")Long orgId,@Param("status")String status);
	
	@Query("select b from RtsExternalServicesEntity b where b.applicationId=:applicationId and b.orgId=:orgId")
	public RtsExternalServicesEntity findData(@Param("applicationId") Long applicationId , @Param("orgId")Long orgId );

}
