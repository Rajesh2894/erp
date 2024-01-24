package com.abm.mainet.bnd.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.bnd.domain.BirthCertificateEntity;
import com.abm.mainet.bnd.domain.DeathCertEntity;


@Repository("bndDeathCertificate")
public interface DeathCertificateRepository extends JpaRepository<DeathCertEntity, Long>{

	
	@Query("select b from DeathCertEntity b where b.applicationNo=:applicationNo and b.orgId=:orgId")
	List<DeathCertEntity> FinddeathCertDatabyApplicationNO(@Param("applicationNo")Long applicationNo, @Param("orgId") Long orgId);

	@Modifying
	@Query("update DeathCertEntity d set d.wfStatus=:wfStatus,d.drStatus=:drstatus  where d.drRId=:drRId and d.orgId=:orgId")
	void updateWorkFlowStatus(@Param("drRId")Long drRId,@Param("orgId") Long orgId,@Param("wfStatus") String wfStatus,@Param("drstatus") String drstatus);
	
	@Query("select d from DeathCertEntity d where d.drRId=:drRId")
	public List<DeathCertEntity>  findData(@Param("drRId") Long drRId);
	
	
	@Query("select b from DeathCertEntity b where b.applicationNo=:applicationNo and b.orgId=:orgId")
	public DeathCertEntity findNacDeathData(@Param("applicationNo")Long applicationNo, @Param("orgId") Long orgId);
	
	public List<DeathCertEntity> findByApplicationNoAndDrDodAndOrgIdAndDrStatus(Long applicationNo, Date drDod, Long orgId, String drStatus);
	
	public List<DeathCertEntity> findByDrDodAndOrgIdAndDrStatus(Date drDod,Long orgId, String drStatus);
	
	public DeathCertEntity findByApplicationNoAndOrgIdAndDrStatus(Long applicationNo, Long orgId, String drStatus);

	
	
	
}
