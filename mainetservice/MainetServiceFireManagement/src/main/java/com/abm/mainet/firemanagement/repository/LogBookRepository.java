package com.abm.mainet.firemanagement.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.firemanagement.domain.FmVehicleLogBook;

@Repository
public interface LogBookRepository extends JpaRepository<FmVehicleLogBook, Long> {

	@Query("SELECT DIR FROM FmVehicleLogBook DIR WHERE DIR.outDate BETWEEN :fromDate AND :toDate AND DIR.orgid=:orgid")
	List<FmVehicleLogBook> searchIncidentRegister(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("orgid") Long orgid);
	
	List<FmVehicleLogBook> findByOrgid(Long orgid);
      
	@Query("select  vm.veNo,vm.driverId,e.empname,e.emplname from FmVehicleLogBook vm, Employee e where  vm.driverId=e.empId AND  vm.orgid =:orgId")
	List<Object[]> vehicleData(@Param("orgId")Long orgId);

	@Query("SELECT distinct fr.veNo FROM FmVehicleLogBook fr WHERE fr.orgid=:orgid "
			+ " AND fr.outDate>=:fromDate AND fr.outDate<=:toDate ORDER BY fr.veNo")
	List<String> getVehicleNoList(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("orgid") Long orgid);
	
}
