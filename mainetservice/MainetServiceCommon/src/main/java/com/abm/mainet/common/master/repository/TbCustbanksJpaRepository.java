package com.abm.mainet.common.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.domain.BankMasterEntity;

/**
 * Repository : TbCustbanks.
 */
public interface TbCustbanksJpaRepository extends PagingAndSortingRepository<BankMasterEntity, Long> {

    @Query("select bank from BankMasterEntity bank where bank.bankId = :cmBankid")
    List<BankMasterEntity> findAllByCmBankId(@Param("cmBankid") Long cmBankid);
}
