package com.abm.mainet.additionalservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.additionalservices.domain.CFCSonographyMasterEntity;

@Repository
public interface CFCSonographyRepository extends JpaRepository<CFCSonographyMasterEntity, Long>{
	
	@Query("from CFCSonographyMasterEntity c where c.apmApplicationId=:apmApplicationId")
	public CFCSonographyMasterEntity findbyApmApplId(@Param("apmApplicationId") Long apmApplicationId);
}
