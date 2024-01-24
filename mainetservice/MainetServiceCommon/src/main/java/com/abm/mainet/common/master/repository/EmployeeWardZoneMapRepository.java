package com.abm.mainet.common.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.domain.EmployeeWardZoneMapping;

@Repository
public interface EmployeeWardZoneMapRepository extends JpaRepository<EmployeeWardZoneMapping, Long>{

	EmployeeWardZoneMapping findByEmpId(Long empid);

	List<EmployeeWardZoneMapping> findByEmpIdIn(List<Long> emps);

}
