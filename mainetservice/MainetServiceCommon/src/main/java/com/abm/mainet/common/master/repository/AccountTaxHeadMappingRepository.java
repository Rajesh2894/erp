package com.abm.mainet.common.master.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.domain.TbTaxMasEntity;

/**
 * Repository : TbTaxMas.
 */
public interface AccountTaxHeadMappingRepository extends CrudRepository<TbTaxMasEntity, Long> {

    /**
     * @param taxId
     * @return
     */
    @Query("FROM TbTaxMasEntity m WHERE m.taxId =:taxId")
    TbTaxMasEntity findByTaxId(@Param("taxId") Long taxId);

}
