/*
 * Created on 4 May 2016 ( Time 15:33:38 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.abm.mainet.water.service;

import java.util.List;

import com.abm.mainet.water.dto.TbWtBillScheduleDetail;

/**
 * Business Service Interface for entity TbWtBillScheduleDetail.
 */
public interface TbWtBillScheduleDetailService {

    /**
     * Loads an entity from the database using its Primary Key
     * @param detId
     * @return entity
     */
    TbWtBillScheduleDetail findById(Long detId);

    /**
     * Loads all entities.
     * @return all entities
     */
    List<TbWtBillScheduleDetail> findAll();

    /**
     * Saves the given entity in the database (create or update)
     * @param entity
     * @return entity
     */
    TbWtBillScheduleDetail save(TbWtBillScheduleDetail entity);

    /**
     * Updates the given entity in the database
     * @param entity
     * @return
     */
    TbWtBillScheduleDetail update(TbWtBillScheduleDetail entity);

    /**
     * Creates the given entity in the database
     * @param entity
     * @return
     */
    TbWtBillScheduleDetail create(TbWtBillScheduleDetail entity);

    /**
     * Deletes an entity using its Primary Key
     * @param detId
     */
    void delete(Long detId);

}
