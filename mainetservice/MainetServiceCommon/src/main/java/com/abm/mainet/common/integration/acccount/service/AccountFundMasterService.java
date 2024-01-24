
package com.abm.mainet.common.integration.acccount.service;

import java.util.List;
import java.util.Map;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.domain.TbAcCodingstructureDetEntity;
import com.abm.mainet.common.integration.acccount.dto.AccountFundMasterBean;

/**
 * Business Service Interface for entity TbAcFundMaster.
 */
public interface AccountFundMasterService {

    /**
     * Creates the given entity in the database
     * @param entity
     * @param empId
     * @param langId
     * @param orgId
     * @return
     */
    void create(AccountFundMasterBean entity, TbAcCodingstructureDetEntity detailEntity, Long orgId, int langId, Long empId)
            throws Exception;

    /**
     * @param orgId
     * @param userid
     * @return
     */
    List<AccountFundMasterBean> findAllParentFunds(Long orgid);

    /**
     * @param tbAcFundMaster
     * @return
     */
    AccountFundMasterBean getDetailsUsingFundId(AccountFundMasterBean tbAcFundMaster);

    /**
     * @param fundCode
     * @return
     */
    Boolean isExist(String fundCode, Long orgId);

    /**
     * @param fieldCode
     * @return
     */

    public Map<Long, String> getFundMasterLastLevels(Long orgId) throws Exception;

    public void saveEditedData(AccountFundMasterBean fundMasterBean, Long orgId, int langId, Long empId) throws Exception;

    public String getFundCode(Long fundId);

    public Map<Long, String> getFundMasterStatusLastLevels(Long orgId, Organisation organisation, int langId) throws Exception;

    public String getFundCodeDesc(Long fundId);
    
}
