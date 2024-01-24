package com.abm.mainet.vehiclemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.vehiclemanagement.domain.VehicleEmployeeMaster;

@Repository
public interface SLRMEmployeeMastRepository extends JpaRepository<VehicleEmployeeMaster, Long> {
    
    @Query("select wst from VehicleEmployeeMaster wst where wst.orgid =:orgid and wst.empId =:empId")
    VehicleEmployeeMaster getEmployeeDetails(@Param("orgid") Long orgid , @Param("empId") Long empId); 
    
    @Query(value ="SELECT count(*) FROM VehicleEmployeeMaster c WHERE c.orgid =:orgid AND c.empMobNo =:empMobNo AND c.empMobNo not like '0000000000'")
    int checkDuplicateMobileNo(@Param("orgid") Long orgid , @Param("empMobNo") String empMobNo);
    
    @Query("select count(*) from VehicleEmployeeMaster b where b.empUId=:empUId and b.orgid=:orgid")
	Long checkDuplicateEmpCodeByEmpCode(@Param("empUId")String empUId,@Param("orgid") Long orgid);
    
    @Query("SELECT c FROM VehicleEmployeeMaster c WHERE c.orgid =:orgid AND c.empMobNo =:empMobNo ")
    List<VehicleEmployeeMaster> checkDuplicate(@Param("orgid") Long orgid , @Param("empMobNo") String empMobNo);
    
	@Query("select e.empName, e.empLName from VehicleEmployeeMaster e where e.empId=:empId ")
	Object[] getEmpFullNameByEmpId(@Param("empId") Long empId);
	
	@Query("SELECT e.empId, e.empname, e.emplname FROM Employee e WHERE e.organisation.orgid=:orgid and "
			+ " e.designation.dsgname like :desgDriver and e.isDeleted = '0' and e.empId not in "
			+ " (select empUId from VehicleEmployeeMaster where orgid=:orgid) order by e.empname asc")
	List<Object[]> getEmployeesForVehicleDriverMas(@Param("orgid") Long orgid, @Param("desgDriver") String desgDriver);
    
}
