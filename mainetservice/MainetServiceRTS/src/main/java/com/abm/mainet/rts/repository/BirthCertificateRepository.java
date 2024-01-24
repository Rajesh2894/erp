package com.abm.mainet.rts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.rts.domain.RTSBirthCertificateEntity;

@Repository
public interface BirthCertificateRepository extends JpaRepository<RTSBirthCertificateEntity, Long>{
	
	
	@Query("select b from RTSBirthCertificateEntity b where b.apmApplicationId=:applicationId and b.orgId=:orgId")
	public RTSBirthCertificateEntity findData(@Param("applicationId") Long applicationId , @Param("orgId")Long orgId );
	
	@Modifying
	   @Query("update RTSBirthCertificateEntity d set d.birthWfStatus=:wfStatus,d.brStatus=:brStatus where d.brId=:brId and d.orgId=:orgId")
	   void updateWorkFlowStatus(@Param("brId")Long brId,@Param("orgId")Long orgId,@Param("wfStatus")String wfStatus,@Param("brStatus")String brStatus);

}
