/**
 * 
 */
package com.abm.mainet.validitymaster.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.validitymaster.domain.EmployeeWardZoneMappingDetailEntity;

/**
 * @author cherupelli.srikanth
 * @since 30 Nov 2021
 */
@Repository
public interface EmployeeWardZoneMappingRepository extends JpaRepository<EmployeeWardZoneMappingDetailEntity, Long>{

	
	@Query("SELECT wz FROM  EmployeeWardZoneMappingDetailEntity wz WHERE wz.empLocId=:empId AND wz.orgId=:orgId")
	List<EmployeeWardZoneMappingDetailEntity> getWardZoneMappingByEmpId(@Param("empId") Long empId, @Param("orgId") Long orgId);
	
	@Query("SELECT wz FROM  EmployeeWardZoneMappingDetailEntity wz WHERE wz.orgId=:orgId")
	List<EmployeeWardZoneMappingDetailEntity> getWardZoneMappingByOrgId(@Param("orgId") Long orgId);
	
	
	@Query("SELECT wz FROM  EmployeeWardZoneMappingDetailEntity wz WHERE wz.orgId=:orgId and wz.empLocId=:empId")
	List<EmployeeWardZoneMappingDetailEntity> getWardZoneMappingByOrgIdAndEmpId(@Param("orgId") Long orgId, @Param("empId") Long empId);
}
