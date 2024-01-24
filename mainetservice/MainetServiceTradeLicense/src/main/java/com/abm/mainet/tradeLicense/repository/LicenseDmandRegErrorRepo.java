package com.abm.mainet.tradeLicense.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.tradeLicense.domain.LicenseDmandRegErrorEntity;

@Repository
public interface LicenseDmandRegErrorRepo extends CrudRepository<LicenseDmandRegErrorEntity, Long> {
	@Query("select  p from LicenseDmandRegErrorEntity p where p.licDemandErrId =(select  max(l.licDemandErrId) from LicenseDmandRegErrorEntity l where l.trdLicNo =:oldLicenseNo)")
	LicenseDmandRegErrorEntity getDemandLicenseRegErrByLiscenseNo(@Param("oldLicenseNo") String oldLicenseNo);
}
