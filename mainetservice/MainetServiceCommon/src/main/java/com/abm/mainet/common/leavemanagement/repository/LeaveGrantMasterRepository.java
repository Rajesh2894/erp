package com.abm.mainet.common.leavemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.leavemanagement.domain.TbLeaveGrantMaster;

@Repository
public interface LeaveGrantMasterRepository  extends JpaRepository<TbLeaveGrantMaster, Long> {

	 @Query(" SELECT leave FROM TbLeaveGrantMaster leave WHERE leave.orgId= :orgId AND leave.empId= :empId  order by leave.leaveGrantId desc ")
	    List<TbLeaveGrantMaster> getAllLeaveMasterBasedOnEmpAndOrgId(@Param("orgId") Long orgId, @Param("empId") Long empId);

}
