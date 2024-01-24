/*
 * Created on 6 Jun 2016 ( Time 16:22:31 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.abm.mainet.common.integration.acccount.service;

import java.util.List;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.domain.TbAcCodingstructureMasEntity;
import com.abm.mainet.common.integration.acccount.dto.AccountFundMasterBean;
import com.abm.mainet.common.integration.acccount.dto.TbAcCodingstructureMas;
import com.abm.mainet.common.utility.LookUp;

/**
 * Business Service Interface for entity TbAcCodingstructureMas.
 */
public interface TbAcCodingstructureMasService {

    /**
     * Loads an entity from the database using its Primary Key
     * @param codcofId
     * @return entity
     */
    TbAcCodingstructureMas findById(Long codcofId);

    /**
     * Loads all entities.
     * @return all entities
     */
    List<TbAcCodingstructureMas> findAll();

    /**
     * Saves the given entity in the database (create or update)
     * @param entity
     * @return entity
     */
    TbAcCodingstructureMas save(TbAcCodingstructureMas entity);

    /**
     * Updates the given entity in the database
     * @param entity
     * @return
     */
    TbAcCodingstructureMas update(TbAcCodingstructureMas entity);

    /**
     * Creates the given entity in the database
     * @param entity
     * @return
     */
    TbAcCodingstructureMas create(TbAcCodingstructureMas entity);

    /**
     * Deletes an entity using its Primary Key
     * @param codcofId
     */
    void delete(Long codcofId);

    // public TbAcCodingstructureMasEntity findConfigurationMasterEntiry(String compDet,String desc);

    public TbAcCodingstructureMasEntity findConfigurationMasterEntiry(Long compDet, Long orgId, String activeStatusCode);

    public List<AccountFundMasterBean> getFundMasterList(Long cpdFundId);

    /**
     * @param orgId
     * @return
     */
    List<TbAcCodingstructureMas> findAllWithOrgId(Long orgId);

    /**
     * to get last level active fund list
     * @param orgId
     * @return
     */
    public List<AccountFundMasterBean> getActiveFundMasterList(Long cpdFundId);

    public List<TbAcCodingstructureMas> findByAllGridSearchData(Long comCpdId, Long orgId);

    boolean checkDefaultFlagIsExists(String comCpdIdCode, Long orgId, String defaultFlag);

    public List<AccountFundMasterBean> getFundMasterActiveStatusList(Long orgid, Organisation org, Long cpdFundId, int langId);

    /**
     * being used in Trail balance report to show A/C head drop down
     * @param param1 : cpdId of Primary Account Head from CMD Prefix
     * @param param2 : cpdId of Secondary Account Head from CMD prefix
     * @param orgId
     * @return
     */
    List<LookUp> queryAccountHeadByChartOfAccount(Long param1, Long param2, Long orgId);

}
