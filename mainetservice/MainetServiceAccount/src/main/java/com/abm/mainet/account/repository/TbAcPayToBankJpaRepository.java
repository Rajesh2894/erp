package com.abm.mainet.account.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.account.domain.TbAcPayToBankEntity;

/**
 * Repository : TbAcPayToBank.
 */
public interface TbAcPayToBankJpaRepository
        extends
        PagingAndSortingRepository<TbAcPayToBankEntity, Long> {

    /**
     * @param orgid
     * @return
     */
    @Query("select em from TbAcPayToBankEntity em  where em.orgid=:orgid")
    List<TbAcPayToBankEntity> getBankTDSData(
            @Param("orgid") Long orgid);

    @Query("select em from TbAcPayToBankEntity em  where em.ptbTdsType=:ptbTdsType and em.orgid=:orgid and em.ptbStatus=:status")
    List<TbAcPayToBankEntity> isCombinationExists(@Param("ptbTdsType") Long ptbTdsType, @Param("orgid") Long orgId,
            @Param("status") String status);

    @Query("select em from TbAcPayToBankEntity em  where em.ptbTdsType=:ptbTdsType and em.orgid=:orgid")
    List<TbAcPayToBankEntity> getTdsTypeData(@Param("ptbTdsType") Long tdsType, @Param("orgid") Long orgid);

    @Query("select em from TbAcPayToBankEntity em  where em.bankMaster.bankId=:bankId and em.ptbBsrcode=:ptbBsrcode")
    List<TbAcPayToBankEntity> isBSRCodeCombinationExists(@Param("bankId") Long bankId, @Param("ptbBsrcode") String ptbBsrcode);

}
