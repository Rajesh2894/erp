package com.abm.mainet.socialsecurity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.socialsecurity.domain.SocialSecuritySchemeEligibilty;



/**
 * 
 * @author rajesh.das
 *
 */
@Repository
public interface SchemeEligibilityDetailsRepository extends PagingAndSortingRepository<SocialSecuritySchemeEligibilty, Long> {

	@Query(value="select sse.SDSCHE_RENGFROM,sse.SDSCHE_RENGTO,sse.SDSCHE_FACT_APL,sse.SDSCHE_CRITERIA from tb_swd_scheme_eligibility sse  where  sse.SDSCH_ID=:sosecuritySchmMasEligibility and sse.orgId=:orgId",nativeQuery = true)
	List<Object[]> findAllEligiblityById(
			@Param("sosecuritySchmMasEligibility") Long sosecuritySchmMasEligibility, @Param("orgId") Long orgId);
	
	
	@Query(value="select schm from SocialSecuritySchemeEligibilty schm where schm.sosecuritySchmMasEligibility.schemeMstId=:schemeMstId "
			+ " and schm.groupID = (select max(cast(schm1.groupID as long)) from SocialSecuritySchemeEligibilty schm1 where schm1.sosecuritySchmMasEligibility.schemeMstId=:schemeMstId) ")
	List<SocialSecuritySchemeEligibilty> findLatestEligibiltyRecord(@Param("schemeMstId") Long schemeMstId);
	
	@Query(value="select schm from SocialSecuritySchemeEligibilty schm where schm.sosecuritySchmMasEligibility.schemeMstId=:schemeMstId ")
	List<SocialSecuritySchemeEligibilty> findEligibiltyRecord(@Param("schemeMstId") Long schemeMstId);
}
