package com.abm.mainet.common.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.domain.TbTaxDetMasEntity;
import com.abm.mainet.common.domain.TbTaxDetMasEntityKey;

/**
 * Repository : TbTaxDetMas.
 */
public interface TbTaxDetMasJpaRepository extends PagingAndSortingRepository<TbTaxDetMasEntity, TbTaxDetMasEntityKey> {

    @Query("select taxDetMasEntity from TbTaxDetMasEntity taxDetMasEntity"
            + " where taxDetMasEntity.tbTaxMas.taxId=:taxId and taxDetMasEntity.orgid=:orgid"
            + " and taxDetMasEntity.status='A'")
    List<TbTaxDetMasEntity> findByTaxOrgId(@Param("taxId") Long taxId, @Param("orgid") Long orgid);

}
