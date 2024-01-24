
package com.abm.mainet.account.dao;

import java.util.List;

import com.abm.mainet.account.domain.AccountDepositeAndAdvnMasterEntity;

/**
 * @author prasant.sahu
 *
 */
public interface AccountAdvnAndDepostieMasterDao {
    public List<AccountDepositeAndAdvnMasterEntity> getDepositeAndAdvncMappingEntityList(long orgid, Long mappingType,
            Long advncOrDepType, Long deptId);

}
