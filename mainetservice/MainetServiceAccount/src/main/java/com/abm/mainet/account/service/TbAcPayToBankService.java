package com.abm.mainet.account.service;

import java.util.List;

import com.abm.mainet.account.dto.TbAcPayToBank;

/**
 * Business Service Interface for entity TbAcPayToBank.
 */
public interface TbAcPayToBankService {

    /**
     * Loads an entity from the database using its Primary Key
     *
     * @param ptbId
     * @return entity
     */
    TbAcPayToBank findById(Long ptbId);

    /**
     * Loads all entities.
     *
     * @return all entities
     */
    List<TbAcPayToBank> findAll();

    /**
     * Saves the given entity in the database (create or update)
     *
     * @param entity
     * @return entity
     */
    TbAcPayToBank save(TbAcPayToBank entity);

    /**
     * Updates the given entity in the database
     *
     * @param entity
     * @return
     */
    TbAcPayToBank update(TbAcPayToBank entity);

    /**
     * Creates the given entity in the database
     *
     * @param entity
     * @return
     */
    TbAcPayToBank create(TbAcPayToBank entity);

    /**
     * Deletes an entity using its Primary Key
     *
     * @param ptbId
     */
    void delete(Long ptbId);

    /**
     * @param orgid
     * @return
     */
    List<TbAcPayToBank> getBankTDSData(Long orgid);

    boolean isCombinationExists(Long ptbTdsType, Long orgId, String status);

    List<TbAcPayToBank> getTdsTypeData(Long tdsType, Long orgid, Long defaultOrgid);

    boolean isBSRCodeCombinationExists(Long bankId, String ptbBsrcode);

}
