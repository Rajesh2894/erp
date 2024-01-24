package com.abm.mainet.tradeLicense.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.tradeLicense.domain.LicenseDemandRegisterEntity;


@Repository
public interface LicenseDemandRegRepo extends CrudRepository<LicenseDemandRegisterEntity, Long> {
	@Query("select  p from LicenseDemandRegisterEntity p where p.licDemandId=(select max(l.licDemandId) from LicenseDemandRegisterEntity l where  l.trdOldlicno =:oldLicenseNo)")
	LicenseDemandRegisterEntity getDemandLicenseRegByLiscenseNo(@Param("oldLicenseNo") String oldLicenseNo);
}
