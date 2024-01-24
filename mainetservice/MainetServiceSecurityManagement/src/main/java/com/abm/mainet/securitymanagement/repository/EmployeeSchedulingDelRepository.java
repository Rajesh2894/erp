package com.abm.mainet.securitymanagement.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.securitymanagement.domain.EmployeeSchedulingDet;

@Repository
public interface EmployeeSchedulingDelRepository  extends JpaRepository<EmployeeSchedulingDet, Long>{
	
	@Modifying
	   @Query(value="update EmployeeSchedulingDet as br set br.attStatus=:attStatus, br.updatedBy=:updatedBy ,br.updatedDate=:updatedDate ,br.lgIpMacUpd=:lgIpMacUpd where br.emplScdlDetId=:emplScdlDetId and br.orgid=:orgid")
	   void updateData(@Param("emplScdlDetId")Long emplScdlDetId,@Param("orgid") Long orgid,@Param("attStatus") String attStatus,@Param("updatedBy") Long updatedBy,@Param("updatedDate") Date updatedDate,@Param("lgIpMacUpd") String lgIpMacUpd);
	
	
	@Query("SELECT esd.locId " +
	           "FROM EmployeeSchedulingDet esd " +
	           "WHERE esd.contStaffIdNo = :staffId " +
	           "AND esd.orgid = :orgId " +
	           "AND esd.createdDate = ( " +
	           "    SELECT MAX(esd2.createdDate) " +
	           "    FROM EmployeeSchedulingDet esd2 " +
	           "    WHERE esd2.contStaffIdNo = :staffId " +
	           "    AND esd2.orgid = :orgId " +
	           ")")
	    Long findLatestLocIdByStaffIdAndOrgId(
	        @Param("staffId") String staffId,
	        @Param("orgId") Long orgId
	    );

}
