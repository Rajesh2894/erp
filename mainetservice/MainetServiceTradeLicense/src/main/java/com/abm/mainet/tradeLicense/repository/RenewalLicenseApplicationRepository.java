package com.abm.mainet.tradeLicense.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.abm.mainet.tradeLicense.domain.TbMlRenewalMast;




/**
 * Created Date:18/02/2019
 * @author Gayatri.Kokane
 *
 */
@Repository
public interface RenewalLicenseApplicationRepository extends CrudRepository<TbMlRenewalMast, Long> {

	
	/**
	 * used to get Trade License With All Details By Application Id
	 * @param applicationId
	 */
	@Query("select p from TbMlRenewalMast p where p.apmApplicationId =:applicationId")
	TbMlRenewalMast getLicenseDetailsByApplicationId(@Param("applicationId") Long applicationId);
	
    @Query("select count(1) from TbMlRenewalMast a where a.trdId=:trdId and a.orgid=:orgid")
    int getRenewalCountByLicenseNo(@Param("trdId") Long trdId, @Param("orgid") long orgid);
}
