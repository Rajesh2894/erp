package com.abm.mainet.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.domain.BankAccountMasterEntity;

/**
 * Repository : BankAccountMaster Repository.
 */
public interface BankAccountJpaRepository
        extends PagingAndSortingRepository<BankAccountMasterEntity, Long>, BankAccountRepositoryCustom {

    @Query("select count(*) from BankAccountMasterEntity account where account.baAccountNo =:baAccountNo and account.fundId =:fundId")
    Long findAccounNoAndFund(@Param("baAccountNo") String baAccountNo, @Param("fundId") Long fundId);

    @Query("select h from BankAccountMasterEntity h where h.baAccountNo =:baAccountNo")
    List<BankAccountMasterEntity> findByAccountNo(@Param("baAccountNo") String baAccountNo);

    @Query("select d.sacHeadId from AccountHeadSecondaryAccountCodeMasterEntity d where d.tbBankaccount.baAccountId=:baAccountId")
    Long getSecHeadIdbyAccountId(@Param("baAccountId") Long baAccountId);

    /*
     * @Query("select c from BankMasterEntity b,UlbBankMasterEntity u,BankAccountMasterEntity c where b.bankId=u.bankId and " +
     * "c.ulbBankId.ulbBankId=u.ulbBankId and b.bankId =:bankId AND u.orgId=:orgId") List<BankAccountMasterEntity>
     * getAccountListByBankId(@Param("bankId") Long bankId, @Param("orgId") Long orgId);
     */

    @Query("select h from BankAccountMasterEntity h where h.baAccountId =:baAccountid")
    BankAccountMasterEntity findBankNames(@Param("baAccountid") Long baAccountid);

    @Query("SELECT ba FROM  BankAccountMasterEntity ba WHERE ba.orgId=:orgId ORDER BY ba.baAccountName ASC")
    List<BankAccountMasterEntity> findBankAccountsByOrgId(@Param("orgId") Long orgId);

    @Query("select ba from BankAccountMasterEntity ba where ba.orgId=:orgid")
	List<BankAccountMasterEntity> getBankAccountMasterDet(@Param("orgid") long orgid);

    @Query("select distinct ba.bankId.bankId,ba.bankId.bank,ba.bankId.branch,ba.bankId.ifsc from BankAccountMasterEntity ba where ba.orgId=:orgid")
	List<Object[]> findBankDeatilsInBankAccount(@Param("orgid") long orgid);

}
