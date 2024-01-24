package com.abm.mainet.validitymaster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.validitymaster.domain.LicenseValidityMasterEntity;

/**
 * @author cherupelli.srikanth
 * @since 17 september 2019
 */
@Repository
public interface LicenseValidityMasterRepository extends JpaRepository<LicenseValidityMasterEntity, Long> {

	@Query("SELECT lm FROM  LicenseValidityMasterEntity lm WHERE lm.licId=:licId AND lm.orgId=:orgId")
	LicenseValidityMasterEntity findByLicIdAndOrgId(@Param("licId") Long licId, @Param("orgId") Long orgId);

	@Query("SELECT lm FROM  LicenseValidityMasterEntity lm WHERE lm.deptId=:deptId AND lm.serviceId=:serviceId AND lm.orgId=:orgId AND lm.licType=:licenceTypeId")
	LicenseValidityMasterEntity findByDeptIdAndServiceId(@Param("deptId") Long deptId,
			@Param("serviceId") Long serviceId, @Param("orgId") Long orgId, @Param("licenceTypeId") Long licenceTypeId);

	@Query("SELECT lm FROM  LicenseValidityMasterEntity lm WHERE lm.deptId=:deptId AND lm.serviceId=:serviceId AND lm.orgId=:orgId AND lm.licType=:licenceTypeId AND lm.triCod1=:triCod1 AND lm.triCod2=:triCod2")
	LicenseValidityMasterEntity findByCategoryAndSubCategory(@Param("deptId") Long deptId,
			@Param("serviceId") Long serviceId, @Param("orgId") Long orgId, @Param("licenceTypeId") Long licenceTypeId,
			@Param("triCod1") Long triCod1, @Param("triCod2") Long triCod2);
}
