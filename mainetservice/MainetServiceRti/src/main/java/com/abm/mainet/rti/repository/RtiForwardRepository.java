package com.abm.mainet.rti.repository;

import java.util.List;

import javax.ws.rs.QueryParam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.rti.domain.TbRtiFwdEmployeeEntity;
@Repository
public interface RtiForwardRepository  extends JpaRepository<TbRtiFwdEmployeeEntity, Long>{
	
	
	@Query("select rti from TbRtiFwdEmployeeEntity rti where rti.applicationId=:applicationId")
List<TbRtiFwdEmployeeEntity> getRtiFOrwardedEmployeeDetails(@Param("applicationId")Long apmApplicationId);
}
