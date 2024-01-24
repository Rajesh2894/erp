package com.abm.mainet.account.service;

import java.util.List;

import com.abm.mainet.account.dto.AccountTDSTaxHeadsBean;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadSecondaryAccountCodeMasterEntity;

/**
 * Business Service Interface for entity TbAcTdsTaxheads.
 */
public interface AccountTDSTaxHeadsService {

    /**
     * Loads an entity from the database using its Primary Key
     *
     * @param tdsId
     * @return entity
     */
    AccountTDSTaxHeadsBean findById(Long tdsId, Long orgId);

    /**
     * Loads all entities.
     *
     * @return all entities
     */
    List<AccountTDSTaxHeadsBean> findAll(Long orgId);

    /**
     * Saves the given entity in the database (create or update)
     *
     * @param entity
     * @return entity
     */
    AccountTDSTaxHeadsBean save(
            AccountTDSTaxHeadsBean entity);

    /**
     * Updates the given entity in the database
     *
     * @param entity
     * @return
     */
    AccountTDSTaxHeadsBean update(
            AccountTDSTaxHeadsBean entity);

    /**
     * Creates the given entity in the database
     *
     * @param entity
     * @return
     */
    AccountTDSTaxHeadsBean create(AccountTDSTaxHeadsBean entity, Organisation org);

    AccountTDSTaxHeadsBean getDetailsUsingTdsID(
            AccountTDSTaxHeadsBean tdsTaxHeadsMasterBean);

    /**
     * @param pacHeadId
     * @param orgId
     * @return
     */
    List<AccountHeadSecondaryAccountCodeMasterEntity> getSaHeadItemsList(
            Long pacHeadId, Long orgId);

    /**
     * @param orgId
     * @param accountHeadId
     * @param tdsTypeId
     * @return
     */
    List<AccountTDSTaxHeadsBean> getTdsMappingData(Long orgId, Long accountHeadId, Long tdsTypeId, String status);

    /**
     * @param tdsTypeCpdId
     * @return
     */
    Long getTdsTypeCount(Long tdsTypeCpdId);

    String getBudgetHeadDescription(Long headTypeId, Long budgetCodeId, Long superOrgId, Long orgId);
}
