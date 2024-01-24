package com.abm.mainet.account.dao;

import java.util.List;

import com.abm.mainet.account.domain.AccountTDSTaxHeadsEntity;

/**
 * @author tejas.kotekar
 *
 */
public interface AccountTDSTaxHeadsDao {

    List<AccountTDSTaxHeadsEntity> getTDSDetails(Long orgId, Long accountHeadId, Long tdsTypeId, String status);

}
