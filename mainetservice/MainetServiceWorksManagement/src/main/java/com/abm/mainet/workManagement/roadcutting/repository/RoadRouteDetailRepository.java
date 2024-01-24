package com.abm.mainet.workManagement.roadcutting.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.workManagement.roadcutting.domain.RoadCuttingEntity;
import com.abm.mainet.workManagement.roadcutting.domain.RoadRouteDetailEntity;

/**
 * JPA specific extension of {@link org.springframework.data.jpa.repository.JpaRepository}.
 * 
 * @author Lalit.Prusti
 *
 */

@Repository
public interface RoadRouteDetailRepository extends JpaRepository<RoadRouteDetailEntity, Long> {

	    @Modifying
	    @Query("update RoadRouteDetailEntity r set r.refId = :refId, r.apmAppStatus = :apmAppStatus where r.rcdId = :rcdId")
		public void updateRefId(@Param("refId") String refId,@Param("rcdId") Long rcdId,@Param("apmAppStatus") String apmAppStatus);
}
