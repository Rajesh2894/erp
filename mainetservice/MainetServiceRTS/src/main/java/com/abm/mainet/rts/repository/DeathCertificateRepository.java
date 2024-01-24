package com.abm.mainet.rts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.rts.domain.DeathCertificateEntity;

@Repository
public interface DeathCertificateRepository extends JpaRepository<DeathCertificateEntity, Long>{

	
	@Query("select b from DeathCertificateEntity b where b.applicationNo=:applicationNo and b.orgId=:orgId")
	List<DeathCertificateEntity> FinddeathCertDatabyApplicationNO(@Param("applicationNo")Long applicationNo, @Param("orgId") Long orgId);

	@Modifying
	  @Query("update DeathCertificateEntity d set d.wfStatus=:wfStatus,d.drStatus=:drstatus  where d.drRId=:drRId and d.orgId=:orgId")
	void updateWorkFlowStatus(@Param("drRId")Long drRId,@Param("orgId") Long orgId,@Param("wfStatus") String wfStatus,@Param("drstatus") String drstatus);

	
	
	
}
