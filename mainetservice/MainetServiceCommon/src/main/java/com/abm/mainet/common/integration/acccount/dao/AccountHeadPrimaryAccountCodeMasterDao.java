/**
 *
 */
package com.abm.mainet.common.integration.acccount.dao;

import java.util.List;
import java.util.Set;

import com.abm.mainet.common.integration.acccount.domain.AccountHeadPrimaryAccountCodeMasterEntity;
import com.abm.mainet.common.integration.acccount.dto.AccountHeadPrimaryAccountCodeMasterBean;

/**
 * @author prasant.sahu
 *
 */

public interface AccountHeadPrimaryAccountCodeMasterDao {

    /**
     * @param tbAcFunctionMaster
     * @return
     */
    public AccountHeadPrimaryAccountCodeMasterEntity getParentDetailsUsingPrimaryHeadId(
            AccountHeadPrimaryAccountCodeMasterBean tbAcFunctionMaster);

    /**
     * @param i
     * @return
     */
    public List<Object[]> getCodCofigurationDetIdUsingLevel(Long level);

    /**
     * @return
     */
    public List<Integer> getAllParentLevelCodes(Long orgId);

    public List<AccountHeadPrimaryAccountCodeMasterEntity> findAllWithOrgId(Long orgid);

    public List<AccountHeadPrimaryAccountCodeMasterEntity> findAllCopositeCodeOrgId(Long orgid);

    /**
     * @param orgId
     * @param string
     * @return
     */
    public List<AccountHeadPrimaryAccountCodeMasterEntity> getLastLevels(Long orgId, Long string);

    /**
     * @param orgId
     * @param string
     * @param active Status Id
     * @return
     */
    public List<AccountHeadPrimaryAccountCodeMasterEntity> getLastActiveLevels(Long orgId, Long string, Long activeStatus);

    public List<Object[]> getPrimaryHeadCodeAllLastLevelsAsset(Long orgId);

    public List<Object[]> getPrimaryHeadCodeAllLastLevelsLiability(Long orgId);

    public List<AccountHeadPrimaryAccountCodeMasterEntity> getLastLevelsWithOutbankAndVendor(Long orgId, Long string);

    public Set<AccountHeadPrimaryAccountCodeMasterEntity> getParentDetails(Long orgId);

}
