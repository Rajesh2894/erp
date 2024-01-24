/*
 * Created on 6 Jun 2016 ( Time 15:25:35 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.abm.mainet.common.integration.acccount.service;

import java.util.List;
import java.util.Map;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.domain.AccountFunctionMasterEntity;
import com.abm.mainet.common.integration.acccount.domain.TbAcCodingstructureDetEntity;
import com.abm.mainet.common.integration.acccount.dto.AccountFunctionMasterBean;

/**
 * Business Service Interface for entity TbAcFunctionMaster.
 */
public interface AccountFunctionMasterService {

    /**
     * Creates the given entity in the database
     * @param functionMasterEntity
     * @return
     */
    void create(AccountFunctionMasterBean functionMasterBean, TbAcCodingstructureDetEntity detailEntity, Long o, int l, Long e)
            throws Exception;

    /**
     * @param tbAcFunctionMaster
     * @return
     */
    AccountFunctionMasterBean getDetailsUsingFunctionId(AccountFunctionMasterBean tbAcFunctionMaster) throws Exception;

    /**
     * @return
     */
    List<Integer> getAllParentLevelCodes(Long orgId);

    /**
     * @param orgid
     * @return
     */
    List<AccountFunctionMasterBean> findAllWithOrgId(long orgid);

    /**
     * @param orgId
     * @return
     */
    public Map<Long, String> getFunctionMasterLastLevels(Long orgId) throws Exception;

    public AccountFunctionMasterEntity saveEditedData(AccountFunctionMasterBean functionMasterBean, Long orgId, int langId, Long empId) throws Exception;

    public String getFunctionCode(Long functionId);

    /**
     * @param orgId
     * @return all composite codes
     */
    List<String> getAllCompositeCodeByOrgId(Long orgId);

    public Map<Long, String> getFunctionMasterStatusLastLevels(Long orgId, Organisation organisation, int langId)
            throws Exception;

    public AccountFunctionMasterEntity getFunctionById(Long functionId);

    public void saveUpdateFunctions(List<AccountFunctionMasterEntity> entities);

    public void saveUpdateFunction(AccountFunctionMasterEntity function);

	void insertEditedFunctionMasterDataIntoPropertyTaxTableByUsingSoapWS(AccountFunctionMasterEntity finalEntity);

	Map<Long, String> getFunctionCompositeCode(Organisation defaultOrg, int langId);

}
