package com.abm.mainet.common.integration.acccount.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.integration.acccount.domain.TbSacheadAccMapEntity;

/**
 * Repository : TbSacheadAccMap.
 */
public interface TbSacheadAccMapJpaRepository
        extends PagingAndSortingRepository<TbSacheadAccMapEntity, Long> {

	@Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM TB_AC_RCPTHD_LINK WHERE SAC_HEAD_ID = :sacHeadId AND BA_ACCOUNT_ID = :baAccountId", nativeQuery = true)
    Long doesCombinationExist(@Param("sacHeadId") Long sacHeadId, @Param("baAccountId") Long baAccountId);
	
	
}
