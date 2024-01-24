package com.abm.mainet.common.master.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.domain.EmployeeAttendanceEntity;
@Repository
public interface EmployeeAttendanceRepositary extends JpaRepository<EmployeeAttendanceEntity, Long> {

	@Query("select eatt.empAttndId from EmployeeAttendanceEntity eatt where eatt.empId=:empId")
	public Long getEMployeeAttendanceId(@Param("empId") Long empId);
	
	@Query("select eatt from EmployeeAttendanceEntity eatt where eatt.empId=:empId and date(eatt.atendanceDate) = current_date()")
	public EmployeeAttendanceEntity getEMployeeAttendanceDetailsByEmpId(@Param("empId") Long empId);
}
