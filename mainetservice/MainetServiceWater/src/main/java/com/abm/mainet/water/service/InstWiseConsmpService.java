/*
 * Created on 20 May 2016 ( Time 15:06:00 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.abm.mainet.water.service;

import java.util.List;

import com.abm.mainet.water.dto.TbWtInstCsmp;

/**
 * Business Service Interface for entity TbWtInstCsmp.
 */
public interface InstWiseConsmpService {

    /**
     * Loads an entity from the database using its Primary Key
     * @param instId
     * @return entity
     */
    TbWtInstCsmp findById(Long instId);

    /**
     * Loads all entities.
     * @return all entities
     */
    List<TbWtInstCsmp> findAll();

    /**
     * Updates the given entity in the database
     * @param entity
     * @return
     */
    TbWtInstCsmp update(TbWtInstCsmp entity, Long orgId, int langId, Long empId) throws Exception;

    /**
     * Creates the given entity in the database
     * @param entity
     * @param empId
     * @param langId
     * @param orgId
     * @return
     */
    TbWtInstCsmp create(TbWtInstCsmp entity, Long orgId, int langId, Long empId);

    /**
     * Deletes an entity using its Primary Key
     * @param instId
     */
    void delete(Long instId);
}
