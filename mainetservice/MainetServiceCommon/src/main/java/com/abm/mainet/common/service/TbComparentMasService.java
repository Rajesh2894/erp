/*
 * Created on 6 Aug 2015 ( Time 16:35:26 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.abm.mainet.common.service;

import java.util.List;

import com.abm.mainet.common.domain.TbComparentMasEntity;
import com.abm.mainet.common.master.dto.TbComparentMas;

/**
 * Business Service Interface for entity TbComparentMas.
 */
public interface TbComparentMasService {

    /**
     * Loads an entity from the database using its Primary Key
     * @param comId
     * @return entity
     */
    TbComparentMas findById(Long comId);

    /**
     * Updates the given entity in the database
     * @param entity
     * @return
     */
    TbComparentMas update(TbComparentMas entity);

    /**
     *
     * @param cpmId
     * @return
     */
    List<TbComparentMas> findComparentMasDataById(Long cpmId, Long orgId);

    /**
     * @param cpmId
     * @param orgid
     * @return
     */
    TbComparentMas findDataByCpmIdLevel(Long cpmId, long orgid);

    TbComparentMasEntity create(TbComparentMasEntity entity);

    List<TbComparentMas> findComparentMasDataByCpmId(Long cpmId);
}
