package com.abm.mainet.additionalservices.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.additionalservices.domain.CFCCounterScheduleEntity;

@Repository
public interface CFCCounterScheduleRepo extends JpaRepository<CFCCounterScheduleEntity, Long> {

	//121826  It will return only active records
	@Query("select cm from CFCCounterScheduleEntity cm where cm.csUserId=:empId and cm.orgId=:orgId and (cm.csStatus='A' or (csFromTime <= :date and "
			+ "csToTime >= :date))")
	public List<CFCCounterScheduleEntity> getcounterDetByempId(@Param("empId") Long empId, @Param("orgId") Long orgId, @Param("date") Date date);

	@Query("select case when count(cs)>0 THEN true ELSE false END  from CFCCounterScheduleEntity cs, CFCCounterMasterEntity cm, CFCCollectionMasterEntity cl where cs.cfcCounterMasterEntity.cuCounterid=cm.cuCounterid and cm.cfcCollectionMasterEntity.cmCollnid=cl.cmCollnid and cs.csStatus='A' "
			+ "And (:fromDate BETWEEN cs.csFromTime AND cs.csToTime OR :toTime BETWEEN cs.csFromTime AND cs.csToTime) and cs.cfcCounterMasterEntity.cuCountcentreno=:cuCountcentreno and"
			+ " cm.cfcCollectionMasterEntity.cmCollncentreno=:cmCollncentreno and cs.orgId=:orgid ")
	public Boolean getcounterDet(@Param("cuCountcentreno") String cuCountcentreno, @Param("cmCollncentreno") String cmCollncentreno,
			@Param("orgid") Long orgid, @Param("fromDate") Date fromDate, @Param("toTime") Date toTime);
	
}
