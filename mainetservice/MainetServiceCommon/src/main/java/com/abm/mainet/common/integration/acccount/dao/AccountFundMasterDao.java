/**
 *
 */
package com.abm.mainet.common.integration.acccount.dao;

import java.util.List;

import com.abm.mainet.common.integration.acccount.domain.AccountFundMasterEntity;
import com.abm.mainet.common.integration.acccount.dto.AccountFundMasterBean;

/**
 * @author prasant.sahu
 *
 */

public interface AccountFundMasterDao {

    /**
     * @return
     */

    public List<AccountFundMasterEntity> findAllParentFunds(
            Long orgId);

    /**
     * @param tbAcFundMaster
     * @return
     */
    public AccountFundMasterEntity getParentDetailsUsingFundId(AccountFundMasterBean tbAcFundMaster);

    /**
     * @param functionId
     * @return
     */
    public List<AccountFundMasterEntity> getChildDetailsUsingParentFundId(
            Long fundId);

    /**
     * @param valueOf
     * @return
     */
    public List<Object[]> getCodCofigurationDetIdUsingLevel(Long i);

    /**
     * @param orgid
     * @param userId
     * @return
     */
    public AccountFundMasterEntity findEntityUsingFuncCode(
            String childParentCode);

    /**
     * @param tbAcFundMaster
     * @return
     */
    public List<Integer> getAllParentLevelCodes();

    /**
     * @param fundCode
     * @return
     */
    public Boolean isExist(String fundCode, Long orgId);

    public List<AccountFundMasterEntity> getLastLevels(
            Long orgId, long prefixId);

}
