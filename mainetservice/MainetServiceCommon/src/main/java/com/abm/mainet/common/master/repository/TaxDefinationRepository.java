package com.abm.mainet.common.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.integration.acccount.domain.TaxDefinationEntity;

/**
 * 
 * @author Jeetendra.Pal
 *
 */
@Repository
public interface TaxDefinationRepository extends PagingAndSortingRepository<TaxDefinationEntity, Long> {

	@Query("select a from TaxDefinationEntity a where a.orgId =:orgId")
	List<TaxDefinationEntity> getTaxDefinationList(@Param("orgId") Long orgId);

}
