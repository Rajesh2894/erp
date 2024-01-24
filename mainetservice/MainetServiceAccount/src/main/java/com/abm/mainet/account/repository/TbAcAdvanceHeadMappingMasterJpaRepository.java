package com.abm.mainet.account.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.account.domain.AccountDepositeAndAdvnMasterEntity;

/**
 * Repository :
 */
@Repository
public interface TbAcAdvanceHeadMappingMasterJpaRepository
        extends PagingAndSortingRepository<AccountDepositeAndAdvnMasterEntity, Long> {

}
