package com.abm.mainet.legal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.legal.domain.LegalOpinionEntity;

public interface LglOpinionRepository  extends JpaRepository<LegalOpinionEntity, Long>
{

	LegalOpinionEntity findByOrgIdAndApmApplicationId(Long orgId,Long apmApplicationId);
	
	List<LegalOpinionEntity> findByOrgId(Long orgId);

	List<LegalOpinionEntity> findByOrgIdAndOpniondeptId(Long orgId, Long opniondeptId);

	@Query("Select lo from LegalOpinionEntity lo where lo.id=:id and lo.orgId=:orgId")
	LegalOpinionEntity findByIds(@Param("id") Long id, @Param("orgId") Long orgId);
	
	
}
