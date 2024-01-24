package com.abm.mainet.vehiclemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.vehiclemanagement.domain.EmployeeDetailsView;


public interface EmployeeDetailsViewRepository extends JpaRepository<EmployeeDetailsView, Long>{
	
    @Query("select ca from EmployeeDetailsView ca")
    List<EmployeeDetailsView> getAllEmployee();
    
    @Query("select ca from EmployeeDetailsView ca where ca.empId =:empId")
    EmployeeDetailsView getEmpDetails( @Param("empId") Long empId);

}
