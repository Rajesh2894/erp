package com.abm.mainet.buildingplan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.buildingplan.domain.LicenseApplicationLandAcquisitionDetEntity;
import com.abm.mainet.buildingplan.domain.LicenseApplicationLandAcquisitionDetHistEntity;

public interface LicenseApplicationLandAcquisitionDetRepository extends JpaRepository<LicenseApplicationLandAcquisitionDetEntity, Long> {
	
	@Query("select s from LicenseApplicationLandAcquisitionDetHistEntity s where s.taskId=:taskId and s.scrnFlag=:scrnFlag")
	List<LicenseApplicationLandAcquisitionDetHistEntity> getDataByTaskId(@Param("taskId") Long taskId,@Param("scrnFlag") String scrnFlag);

}
