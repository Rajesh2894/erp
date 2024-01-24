package com.abm.mainet.additionalservices.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.additionalservices.domain.CFCNursingHomeInfoEntity;


@Repository
public interface CFCNursingHomeRepository extends JpaRepository<CFCNursingHomeInfoEntity, Long>{
	
	@Query("from CFCNursingHomeInfoEntity c where c.apmApplicationId=:apmApplicationId")
	public CFCNursingHomeInfoEntity findbyApmApplicationId(@Param("apmApplicationId") Long apmApplicationId);
}
