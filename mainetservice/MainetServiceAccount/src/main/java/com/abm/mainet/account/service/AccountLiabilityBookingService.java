package com.abm.mainet.account.service;

import java.util.List;

import com.abm.mainet.account.domain.AccountLiabilityBookingDetEntity;
import com.abm.mainet.account.domain.AccountLiabilityBookingEntity;
import com.abm.mainet.account.domain.AccountTenderEntryEntity;
import com.abm.mainet.account.dto.AccountLiabilityBookingBean;

/**
 * @author tejas.kotekar
 *
 */
public interface AccountLiabilityBookingService {

    List<AccountTenderEntryEntity> getListOfTenderDetails(Long orgId);

    List<AccountTenderEntryEntity> getTenderDetailsByTenderId(Long tenderId, Long orgId);

    AccountLiabilityBookingBean createLiabilityBookingEntry(AccountLiabilityBookingBean liabilityBookingBean);

    Boolean isLiabilityExists(Long tenderId, Long orgId);

    AccountLiabilityBookingEntity getLiabilityNoByTenderId(Long tenderId, Long orgId);

    List<AccountLiabilityBookingDetEntity> getLiabilityDetailsByLiabilityId(Long liabilityId, Long orgId);

}
