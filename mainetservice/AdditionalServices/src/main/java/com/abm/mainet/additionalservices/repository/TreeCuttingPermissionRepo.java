package com.abm.mainet.additionalservices.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.additionalservices.domain.CFCNursingHomeInfoEntity;
import com.abm.mainet.additionalservices.domain.TreeCuttingInfoEntity;
@Repository
public interface TreeCuttingPermissionRepo extends CrudRepository<TreeCuttingInfoEntity, Long>{

	@Query("from TreeCuttingInfoEntity c where c.apmApplicationId=:apmApplicationId")
	public TreeCuttingInfoEntity findbyApmApplicationId(@Param("apmApplicationId") Long apmApplicationId);
}
