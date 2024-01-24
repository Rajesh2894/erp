package com.abm.mainet.vehiclemanagement.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.vehiclemanagement.domain.VeVehicleMaster;
import com.abm.mainet.vehiclemanagement.dto.GenVehicleMasterDTO;

/**
 * The Interface VehicleMasterRepository.
 *
 * @author Lalit.Prusti Created Date : 04-May-2018
 * 
 */
@Repository
public interface GeVehicleMasterRepository extends JpaRepository<VeVehicleMaster, Long> {

	Collection<GenVehicleMasterDTO> findByOrgid(Long orgid);

	@Query("select distinct vm.veId,vm.veNo,vm.driverName,e.empname,e.emplname,vm.deptId,vm.veFlag,vm.veRentFromdate,vm.veRentTodate from VeVehicleMaster vm, Employee e where vm.veActive='Y' AND vm.orgid =:orgId")
	List<Object[]> vehicleData(@Param("orgId") Long orgId);

	@Query("SELECT d.veNo FROM VeVehicleMaster d WHERE d.veId=:veId")
	String fetchVeNoByVeId(@Param("veId") Long veId);

	@Query("SELECT d.veChasisSrno FROM VeVehicleMaster d WHERE d.veId=:veId and d.orgid =:orgid")
	String fetchveChasisSrnoByVeIdAndOrgid(@Param("veId") Long veId, @Param("orgid") Long orgid);

	@Query("SELECT d.veId, d.veNo,d.fuelType FROM VeVehicleMaster d where d.veId=:veId and d.orgid =:orgid ")
	List<Object[]> getVehicleByNumber(@Param("veId") Long veId, @Param("orgid") Long orgid);

	@Query("SELECT d.veId, d.veNo FROM VeVehicleMaster d where d.veId=:veId and d.orgid =:orgid ")
	List<Object[]> getVehicleByNo(@Param("veId") Long veId, @Param("orgid") Long orgid);

	@Query("select distinct vm.veId,vm.veNo,vm.driverName,vm.deptId,vm.veFlag,vm.veRentFromdate,vm.veRentTodate,vm.veChasisSrno from VeVehicleMaster vm where vm.veActive='Y' AND vm.orgid =:orgId")
	List<Object[]> vehicleDataWithoutEmp(@Param("orgId") Long orgId);

	@Query("select vm.veId,vm.veNo from VeVehicleMaster vm  where vm.veActive='Y' AND vm.orgid =:orgId and vm.deptId = :deptId")
	List<Object[]> vehicleDataForDept(@Param("orgId") Long orgId, @Param("orgId") Long deptId);

	@Query("select v.veId, v.veNo, v.veVetype from VeVehicleMaster v where v.orgid=:orgid and v.veActive=:flagY and "
			+ " v.veFlag=:flagY and v.veId not in (select veId from VehicleMaintenanceMast where orgid=:orgid and veId is not null)")
	List<Object[]> getUlbActiveVehiclesForMaintMasterAdd(@Param("orgid") Long orgid, @Param("flagY") String flagY);

	@Query("select vm.veId, vm.veNo, vm.veVetype from VeVehicleMaster vm where vm.orgid =:orgId")
	List<Object[]> getAllVehicleIdNumberObjectList(@Param("orgId") Long orgId);

	@Query("select vm from VeVehicleMaster vm where vm.veId in (:activeMaintMasVehicleIdList) and vm.veActive=:flagY and vm.orgid =:orgId")
	List<VeVehicleMaster> getActiveVehiclesForMaintenanceAlert(@Param("activeMaintMasVehicleIdList") List<Long> activeMaintMasVehicleIdList, 
			@Param("orgId") Long orgId, @Param("flagY") String flagY);

}
