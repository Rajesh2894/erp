package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.CircularNotificationMasterEntity;

@Repository
public interface CircularNotificationMasterRepository extends JpaRepository<CircularNotificationMasterEntity, Long>{

	@Query("select c from CircularNotificationMasterEntity c where c.circularTitle like %:circularTitle% and c.circularNo like %:circularNo%")
	List<CircularNotificationMasterEntity> getDetails(@Param("circularTitle")String circularTitle, @Param("circularNo")String circularNo);
	
	@Query("select c from CircularNotificationMasterEntity c where c.circularTitle like %:circularTitle% ")
	List<CircularNotificationMasterEntity> getDetailsCircularTitle(@Param("circularTitle")String circularTitle);
	
	@Query("select c from CircularNotificationMasterEntity c where c.circularNo like %:circularNo% ")
	List<CircularNotificationMasterEntity> getDetailsCircularNo(@Param("circularNo")String circularNo);

}
