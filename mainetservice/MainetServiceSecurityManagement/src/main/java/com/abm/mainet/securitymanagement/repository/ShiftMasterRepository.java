package com.abm.mainet.securitymanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.securitymanagement.domain.ShiftMaster;

@Repository
public interface ShiftMasterRepository extends JpaRepository< ShiftMaster,Long>{
	
	@Query("select sm from ShiftMaster sm where sm.orgid=:orgid  and sm.status='A' ")
	List<ShiftMaster> findAll(@Param("orgid")Long orgid);
	
	 @Query("SELECT  sm.shiftId from ShiftMaster sm where  sm.orgid=:orgid  and sm.status='A' ")
	 List<Long> getActiveShiftInfo(@Param("orgid") Long orgid);
	
	
}
