package com.abm.mainet.buildingplan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.buildingplan.domain.LicenseGrantedEntity;

public interface LicenseGrantedRepository extends JpaRepository<LicenseGrantedEntity, Long> {

	@Query("select s from LicenseGrantedEntity s where s.cfcApplicationId =:cfcApplicationId")
	List<LicenseGrantedEntity> getLicenseDetailsByApplicationId(@Param("cfcApplicationId") Long cfcApplicationId);
	
	@Query("select s from LicenseGrantedEntity s where s.cfcApplicationId =:cfcApplicationId and s.level=:level")
	List<LicenseGrantedEntity> getApplicationNotingDetailLicense(@Param("cfcApplicationId") Long cfcApplicationId,@Param("level") Long level);

}
