/*package com.abm.mainet.vehicle.management.repository;



import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.vehicle.management.domain.FmVehicleMaster;
import com.abm.mainet.vehicle.management.dto.GenVehicleMasterDTO;

*//**
 * The Interface VehicleMasterRepository.
 *
 * @author Lalit.Prusti Created Date : 04-May-2018
 * 
 *//*
@Repository
public interface GenVehicleMasterRepository extends JpaRepository<FmVehicleMaster, Long> {

	Collection<GenVehicleMasterDTO> findByOrgid(Long orgid);

	@Query("select distinct vm.veId,vm.veNo,vm.driverName,e.empname,e.emplname from FmVehicleMaster vm, Employee e where  vm.driverName=e.empId AND  vm.orgid =:orgId and vm.veActive ='Y' ")
	List<Object[]> vehicleData(@Param("orgId")Long orgId);
	
	 * @Query("select distinct vm.veId,e.empname,e.emplname from FmVehicleMaster vm.orgid =:orgId"
	 * ) List<Object[]> vehicleDataByName(@Param("orgId")Long orgId);
	 
	@Query("select h.deptId,h.veVetype,h.veNo from FmVehicleMaster h  where h.orgid =:orgid  group by h.deptId,h.veVetype,h.veNo ")
	List<Object[]> vehicleDetail(@Param("orgid")Long orgid);
		
}





*/