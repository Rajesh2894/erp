package com.abm.mainet.buildingplan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.buildingplan.domain.TbLicPerfSiteAffecEntity;

@Repository
public interface SiteAffectedRepository extends JpaRepository<TbLicPerfSiteAffecEntity, Long> {

	@Query("select s from TbLicPerfSiteAffecEntity s where s.cfcApplicationId =:cfcApplicationId and s.mainLine=:mainLine")
	List<TbLicPerfSiteAffecEntity> getSiteDetailsByApplicationId(@Param("cfcApplicationId") Long cfcApplicationId,
			@Param("mainLine") String mainLine);
	
	@Query("select s from TbLicPerfSiteAffecEntity s where s.cfcApplicationId =:cfcApplicationId and s.level=:level")
	List<TbLicPerfSiteAffecEntity> getApplicationNotingDetail(@Param("cfcApplicationId") Long cfcApplicationId,
			@Param("level") Long level);

}
