package com.abm.mainet.common.integration.acccount.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.integration.acccount.domain.TbAcCodingstructureDetEntity;

/**
 * Repository : TbAcCodingstructureDet.
 */
public interface TbAcCodingstructureDetJpaRepository extends PagingAndSortingRepository<TbAcCodingstructureDetEntity, Long> {

    @Query("select d from TbAcCodingstructureDetEntity d where d.tbAcCodingstructureMasEntity=:tbAcCodingstructureMasEntity and d.orgId =:orgid ")
    TbAcCodingstructureDetEntity findOneCodCofId(@Param("tbAcCodingstructureMasEntity") Long tbAcCodingstructureMasEntity,
            @Param("orgid") Long orgid);
}
