package com.abm.mainet.account.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.account.domain.AccountLiabilityBookingDetEntity;
import com.abm.mainet.account.domain.AccountLiabilityBookingEntity;
import com.abm.mainet.account.domain.AccountTenderEntryEntity;

/** @author tejas.kotekar */
public interface AccountLiabilityBookingJpaRepository extends PagingAndSortingRepository<AccountLiabilityBookingEntity, Long> {

    /**
     * @param orgId
     * @return
     */
    @Query("select t from AccountTenderEntryEntity t where t.orgid=:orgId")
    List<AccountTenderEntryEntity> getListOfTenderDetails(@Param("orgId") Long orgId);

    /**
     * @param tenderId
     * @param orgId
     * @return
     */
    @Query("select t from AccountTenderEntryEntity t where t.trTenderId =:tenderId and t.orgid =:orgId")
    List<AccountTenderEntryEntity> getTenderDetailsByTenderId(@Param("tenderId") Long tenderId, @Param("orgId") Long orgId);

    @Query("select lb from AccountLiabilityBookingEntity lb where lb.tbAcTenderMaster.trTenderId =:tenderId and lb.orgid =:orgId")
    AccountLiabilityBookingEntity getLiabilityNoByTenderId(@Param("tenderId") Long tenderId, @Param("orgId") Long orgId);

    @Query("select d from AccountLiabilityBookingDetEntity d where d.tbAcLiabilityBooking.lbLiabilityId =:liabilityId and d.orgid =:orgId")
    List<AccountLiabilityBookingDetEntity> getLiabilityDetailsByLiabilityId(@Param("liabilityId") Long liabilityId,
            @Param("orgId") Long orgId);

}
