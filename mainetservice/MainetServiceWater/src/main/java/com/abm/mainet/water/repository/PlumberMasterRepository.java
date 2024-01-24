package com.abm.mainet.water.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.water.domain.PlumberMaster;

/**
 * Repository : PlumberMaster.
 */
@Repository
public interface PlumberMasterRepository extends CrudRepository<PlumberMaster, Long> {
	@Query("SELECT m.plumId FROM PlumberMaster m "
			+ " WHERE m.plumLicNo =:plumLicNo AND ( :today  BETWEEN m.plumLicFromDate AND m.plumLicToDate OR "
			+ " m.plumId in ( select r.plum_id from PlumberRenewalRegisterMaster r where :today between r.rn_fromdate AND r.rn_todate )) ")
	Long validatePlumberLicenceOutSideULB(@Param("plumLicNo") String plumLicNo, @Param("today") Date today);

	@Query("SELECT m.plumLicNo FROM PlumberMaster m  WHERE m.plumId=:plumbId ")
	String getPlumberLicenceNumber(@Param("plumbId") Long plumbId);
	
	@Query("SELECT m.plumFname,m.plumMname,m.plumLname FROM PlumberMaster m  WHERE m.plumId=:plumbId ")
	List<Object[]> getPlumberLicenceName(@Param("plumbId") Long plumbId);

	@Query("SELECT m  FROM PlumberMaster m  WHERE m.orgid=:orgid and m.plumLicNo IS NOT NULL")
	List<PlumberMaster> getAllPlumber(@Param("orgid") Long orgid);
	
	@Query("SELECT m.plum_id FROM PlumberRenewalRegisterMaster m  WHERE m.apm_application_id=:apm_application_id and m.orgid=:orgid ")
	Long getPlumberId(@Param("apm_application_id") Long apm_application_id, @Param("orgid") Long orgid);

}
