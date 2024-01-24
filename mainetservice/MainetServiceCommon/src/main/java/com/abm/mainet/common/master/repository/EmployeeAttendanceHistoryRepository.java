package com.abm.mainet.common.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.domain.EmployeeAttendanceHistoryEntity;
@Repository
public interface EmployeeAttendanceHistoryRepository extends JpaRepository<EmployeeAttendanceHistoryEntity, Long>{

}
