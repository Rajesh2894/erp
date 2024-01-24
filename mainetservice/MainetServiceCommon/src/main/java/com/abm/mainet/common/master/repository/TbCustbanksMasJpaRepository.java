package com.abm.mainet.common.master.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.domain.BankMasterEntity;

/**
 * Repository : TbCustbanksMas.
 */
@Repository
public interface TbCustbanksMasJpaRepository extends PagingAndSortingRepository<BankMasterEntity, Long> {

}
