package com.abm.mainet.common.integration.acccount.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.domain.BankAccountMasterEntity;
import com.abm.mainet.common.domain.BankMasterEntity;

/**
 * Repository : TbBankmaster.
 */
public interface TbBankmasterJpaRepository extends PagingAndSortingRepository<BankMasterEntity, Long> {

    /**
     * @param orgId
     * @return
     */
    @Query("select distinct c.baAccountId, c.baAccountNo, c.baAccountName, a.bank, c.fundId from BankMasterEntity a, BankAccountMasterEntity c, "
            + "AccountHeadSecondaryAccountCodeMasterEntity sm "
            + "where a.bankId=c.bankId.bankId and c.baAccountId=sm.tbBankaccount.baAccountId "
            + "and c.orgId=sm.orgid  and c.orgId =:orgId and c.acCpdIdStatus =:statusId")
    List<Object[]> getActiveBankAccountList(@Param("orgId") Long orgId, @Param("statusId") Long statusId);

    @Query("select c.baAccountId, c.baAccountNo, c.baAccountName from BankAccountMasterEntity c where c.baAccountId=:bankAccountId")
    List<Object[]> getBankAccountNames(@Param("bankAccountId") Long bankAccountId);

    @Query("select distinct c.baAccountId, c.baAccountNo, c.baAccountName, a.bank, c.fundId, a.branch,a.bankId from BankMasterEntity a, BankAccountMasterEntity c, "
            + "AccountHeadSecondaryAccountCodeMasterEntity sm "
            + "where a.bankId=c.bankId.bankId and c.baAccountId=sm.tbBankaccount.baAccountId "
            + "and c.orgId=sm.orgid  and c.orgId =:orgId and c.baAccountId=:bankAccountId")
    List<Object[]> getActiveBankAccount(@Param("orgId") Long orgId, @Param("bankAccountId") Long bankAccountId);

    @Query("SELECT bm.bank FROM BankMasterEntity bm WHERE bm.bankId=:bankId")
    String getBankNameByBankId(@Param("bankId") Long bankId);

    /*
     * @Query("select b.ulbBankId from BankMasterEntity a, UlbBankMasterEntity b, BankAccountMasterEntity c, " +
     * "AccountHeadSecondaryAccountCodeMasterEntity sm " +
     * "where a.bankId = b.bankId   and b.ulbBankId = c.ulbBankId.ulbBankId and c.baAccountId =:bmBankid " +
     * "and b.orgId=sm.orgid  and b.orgId =:orgId and c.acCpdIdStatus =:statusId") Long getActiveUlbBankAccountId(@Param("orgId")
     * Long orgId, @Param("statusId") Long statusId,
     * @Param("bmBankid") Long bmBankid);
     */

    @Query("select distinct a.bank,a.branch,c.baAccountNo,c.cpdAccountType,c.baAccountName from BankMasterEntity a, BankAccountMasterEntity c, "
            + "AccountHeadSecondaryAccountCodeMasterEntity sm "
            + "where a.bankId=c.bankId.bankId and c.baAccountId=sm.tbBankaccount.baAccountId "
            + "and c.orgId=sm.orgid  and c.orgId =:orgId and c.baAccountId=:bankAccountId")
    List<Object[]> getBankBranchOrg(@Param("bankAccountId") Long bankAccountId, @Param("orgId") Long orgId);

    @Query("select ba  from BankMasterEntity bm , BankAccountMasterEntity ba  where "
            + "bm.bankId=ba.bankId.bankId AND "
            + "ba.orgId =:orgId AND "
            + "bm.bankId=:bmBankid  )")
    List<BankAccountMasterEntity> getBankList(@Param("bmBankid") Long bmBankid, @Param("orgId") Long orgId);

    @Query("select distinct b.bankId,b.bank,b.branch,b.ifsc from BankMasterEntity b where b.bankStatus='A'")
    List<Object[]> getActiveBankList();

    @Query("select c from BankAccountMasterEntity c where c.orgId=:orgId and c.acCpdIdStatus=:activeStatusId order by 1 desc")
	List<BankAccountMasterEntity> getActiveBankAccountDetailsByOrgId(@Param("orgId") Long orgId, @Param("activeStatusId") Long activeStatusId);
    
}
