package com.abm.mainet.common.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.domain.EmployeeSession;

@Repository
public interface EmployeeSessionRepository extends PagingAndSortingRepository<EmployeeSession, Long> {

	@Query("SELECT m FROM EmployeeSession m WHERE m.sessionId = (select max(emp1.sessionId) from EmployeeSession emp1 where emp1.empId=:empId)")
	EmployeeSession getEmployeeSessionDataBy(@Param(value = "empId") Long empId);
}
