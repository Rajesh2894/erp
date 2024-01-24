package com.abm.mainet.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.domain.BankMasterEntity;

/**
 * Repository : BankMaster Repository.
 */
public interface BankMasterJpaRepository extends PagingAndSortingRepository<BankMasterEntity, Long> {

    /**
     * @return return all bank distinct names in list
     */

    @Query("SELECT distinct  bank from BankMasterEntity order by bank ASC")
    List<String> getAllBankName();

    /**
     * @return return all bank branch and bankId by bank name
     */

    @Query("select c.bankId,c.branch,c.ifsc from BankMasterEntity c where c.bank=:bank order by c.branch ASC")
    List<Object[]> getAllBranchNames(@Param("bank") String bank);

    @Query("SELECT d.bank FROM BankMasterEntity d WHERE d.bankId=:bankId")
    String fetchBankNameDescById(@Param("bankId") Long bankId);

    @Query("SELECT distinct bm.bank from BankMasterEntity bm where bm.bankStatus=:status order by bm.bank ASC")
    List<String> getAllBankNameStatus(@Param("status") String status);

}
