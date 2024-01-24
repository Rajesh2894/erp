package com.abm.mainet.common.master.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.domain.EmployeeHistory;

public interface EmployeeHistoryRepository extends JpaRepository<EmployeeHistory, Long> {

	@Query("select e  from EmployeeHistory e where e.empHistId =(select max(eh.empHistId) from EmployeeHistory eh where\r\n"
			+ "			 eh.empHistId < (select max(he.empHistId) from EmployeeHistory he  where he.empId=:empId and he.organisation.orgid=:orgid)  and eh.empId=:empId and eh.organisation.orgid=:orgid and eh.updatedDate>=:date)")
	EmployeeHistory findEmployeelatestData(@Param("empId") Long empId, @Param("orgid") Long orgid,@Param("date") Date date);
	//@Query("select e  from EmployeeHistory e where   e.empId=:empId and e.organisation.orgid=:orgid and e.updatedDate>=:date order by 1 asc")
	
	
	
	@Query(value = "select a.empid_h,a.empid,empname,empmname,emplname,a.CREATED_DATE,a.UPDATED_DATE,a.emploginname from employee_hist a\r\n"
			+ "INNER JOIN tb_workflow_action b on a.EMPID = b.EMPID\r\n"
			+ "where b.APM_APPLICATION_ID=:applicationId and TASK_ID=:taskId and a.EMPID=:empId and a.ORGID=:orgid \r\n"
			+ " and (a.Created_date)\r\n" + "    BETWEEN\r\n"
			+ "    (SELECT min(t2.created_date) from employee_hist t2 WHERE t2.empid=a.empid)\r\n"
			+ "    AND (SELECT max(tt2.updated_Date) from employee_hist tt2 WHERE tt2.empid=a.empid)\r\n"
			+ "    order by empid_h desc", nativeQuery = true)
	List<Object[]> findEmployeeHistData(@Param("empId") Long empId, @Param("orgid") Long orgid,@Param("applicationId") String applicationId, @Param("taskId") String taskId);
}
