package com.abm.mainet.account.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.abm.mainet.common.domain.UlbBankMasterEntity;

/**
 * Repository : TbBankmaster.
 */
public interface UlbBankMasterJpaRepository extends PagingAndSortingRepository<UlbBankMasterEntity, Long> {

    /**
     * @param bankId
     * @return
     */
    /*
     * @Query("select ulb from UlbBankMasterEntity ulb where ulb.bankId=:bankId and ulb.cpdIdBankType = :cpdIdBankType and ulb.orgId =:orgid"
     * ) UlbBankMasterEntity getUlbBankByBankIdAndBankType(@Param("bankId") Long bankId, @Param("cpdIdBankType") Long
     * cpdIdBankType,
     * @Param("orgid") Long orgid);
     */

    /**
     * @param branchId
     * @return
     */
    /*
     * @Query("select ulb.cpdIdBankType from UlbBankMasterEntity ulb where ulb.ulbBankId=:ulbBankId and ulb.orgId = :orgId") Long
     * getBankTypeByULBId(@Param("ulbBankId") Long ulbBankId, @Param("orgId") Long orgId);
     */

    /*
     * @Query("select bm from UlbBankMasterEntity bm where bm.ulbBankId =:bmBankid and bm.orgId =:orgId and bm.bmStatus =:status")
     * List<UlbBankMasterEntity> getBankList(@Param("bmBankid") Long bankId, @Param("orgId") Long orgId,
     * @Param("status") String status);
     */

    /*
     * @Query("select bm  from BankMasterEntity bm , UlbBankMasterEntity ub,BankAccountMasterEntity ba  where " +
     * "bm.bankId=ub.bankId AND ub.ulbBankId=ba.ulbBankId AND " + "ub.orgId =:orgId)") List<UlbBankMasterEntity>
     * getBankListEdit(@Param("orgId") Long orgId);
     */

}
