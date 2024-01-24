package com.abm.mainet.vehiclemanagement.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.vehiclemanagement.domain.VehicleLogBookDetails;

@Repository
public interface VeLogBookRepository extends JpaRepository<VehicleLogBookDetails, Long> {

	@Query("SELECT DIR FROM VehicleLogBookDetails DIR WHERE DIR.outDate BETWEEN :fromDate AND :toDate AND DIR.orgid=:orgid")
	List<VehicleLogBookDetails> searchIncidentRegister(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("orgid") Long orgid);
	
	@Query("SELECT DIR FROM VehicleLogBookDetails DIR WHERE DIR.orgid=:orgid order by DIR.veID desc")
	List<VehicleLogBookDetails> findByOrgid(@Param("orgid")Long orgid);
      
	@Query("select  vm.veNo,vm.driverId,e.empname,e.emplname from VehicleLogBookDetails vm, Employee e where  vm.driverId=e.empId AND  vm.orgid =:orgId")
	List<Object[]> vehicleData(@Param("orgId")Long orgId);

	

	@Query("select  count(*)  from VeVehicleMaster vm,VehicleLogBookDetails vl " +
		    " where vm.veId = vl.veNo and vm.veVetype = vl.typeOfVehicle  \r\n" +
            " and vm.veId =:veId \r\n" +
            " and (:outDate between vl.outDate and  vl.inDate  \r\n" +
            " or :outDate between vl.inDate and  vl.outDate  \r\n" +
            " or :inDate between vl.outDate and  vl.inDate \r\n" +
            " or :inDate between vl.inDate and  vl.outDate ) \r\n" +
            " and (  vl.vehicleOutTime < :vehicleOutTime and vl.vehicleInTime > :vehicleInTime \r\n" +
            " or  vl.vehicleInTime < :vehicleOutTime and vl.vehicleOutTime > :vehicleInTime \r\n" +
            " or  vl.vehicleOutTime < :vehicleInTime and vl.vehicleInTime > :vehicleOutTime \r\n" +
            " or  vl.vehicleInTime < :vehicleInTime and vl.vehicleOutTime > :vehicleOutTime ) \r\n" +			
            /* * " and ( :vehicleOutTime between vl.vehicleOutTime and vl.vehicleInTime \r\n"
			 * + " or :vehicleOutTime between vl.vehicleInTime and vl.vehicleOutTime\r\n" +
			 * " or :vehicleInTime between vl.vehicleOutTime and vl.vehicleInTime \r\n" +
			 * " or :vehicleInTime between vl.vehicleInTime and vl.vehicleOutTime )\r\n" +
			 */
         //   " and vl.driverId = :driverId \r\n"+
            " and vl.veID <> :veID \r\n"+
            " and vm.orgid = :orgid\r\n")
	
		Long veLogBookDupCheck(@Param("orgid") Long orgid,
				 @Param("vehicleOutTime")  String vehicleOutTime,
				 @Param("vehicleInTime")  String vehicleInTime,
	            @Param("outDate") Date outDate,
	            @Param("inDate") Date inDate,
	            @Param("veId") Long veId, 
	          //  @Param("driverId") Long driverId,
	            @Param("veID") Long veID);


	@Query(value = "Select vb from VehicleLogBookDetails vb where vb.veNo in (:activeMaintMasVehicleIdList) and vb.orgid=:orgId "
			+ " and vb.createdDate=(SELECT MAX(vb2.createdDate) from VehicleLogBookDetails vb2 where vb2.veNo=vb.veNo)")
	List<VehicleLogBookDetails> getLogBookForMaintenanceAlert(@Param("activeMaintMasVehicleIdList") List<Long> activeMaintMasVehicleIdList,
			@Param("orgId")Long orgId);

}



