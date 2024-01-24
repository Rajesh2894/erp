/**
 *
 */
package com.abm.mainet.common.integration.acccount.dao;

import java.util.List;

import com.abm.mainet.common.integration.acccount.domain.AccountFunctionMasterEntity;
import com.abm.mainet.common.integration.acccount.dto.AccountFunctionMasterBean;

/**
 * @author prasant.sahu
 *
 */

public interface AccountFunctionMasterDao {

    /**
     * @return
     */

    public List<AccountFunctionMasterEntity> findAllParentFunctions(
            Long orgId);

    /**
     * @param tbAcFunctionMaster
     * @return
     */
    public AccountFunctionMasterEntity getParentDetailsUsingFunctionId(
            AccountFunctionMasterBean tbAcFunctionMaster);

    /**
     * @param functionId
     * @return
     */
    public List<AccountFunctionMasterEntity> getChildDetailsUsingParentFunctionId(
            Long functionId);

    /**
     * @param childParentCode
     * @return
     */
    public AccountFunctionMasterEntity findEntityUsingFuncCode(
            String childParentCode);

    /**
     * @param i
     * @return
     */
    public List<Object[]> getCodCofigurationDetIdUsingLevel(Long i);

    /**
     * @return
     */
    public List<Integer> getAllParentLevelCodes(Long orgId);

    /**
     * @param orgid
     * @return
     */
    public List<AccountFunctionMasterEntity> findAllWithOrgId(
            long orgid);

    /**
     * @param orgId
     * @return
     */
    public List<AccountFunctionMasterEntity> getLastLevels(
            Long orgId, long prefixId);

    public List<AccountFunctionMasterEntity> getLastLevels(Long orgId, String prefixId, String AccountMasterConstantsCMD);

}
