package com.abm.mainet.account.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.account.domain.AccountVoucherEntryEntity;

/**
 *
 * @author Vivek.Kumar
 * @since 02-Aug-2017
 */
@Repository
public interface AccountVoucherEntryRepository extends CrudRepository<AccountVoucherEntryEntity, Long> {

}
