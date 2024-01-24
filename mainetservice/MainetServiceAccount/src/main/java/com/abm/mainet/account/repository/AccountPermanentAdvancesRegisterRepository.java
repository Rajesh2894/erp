package com.abm.mainet.account.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.account.domain.AccountBillEntryMasterEnitity;
import com.abm.mainet.common.integration.acccount.domain.AdvanceEntryEntity;

@Repository
public interface AccountPermanentAdvancesRegisterRepository extends CrudRepository<AdvanceEntryEntity, Long> {

    @Query("from  AdvanceEntryEntity aee  where aee.prAdvEntryDate <= :asOnDates and aee.orgid=:currentOrgId")
    List<AdvanceEntryEntity> findPermanentAdvanceLadger(@Param("asOnDates") Date asOnDates,
            @Param("currentOrgId") Long currentOrgId);

    @Query("from  AccountBillEntryMasterEnitity bm where bm.advanceTypeId =:prAdvEntryId and bm.orgId=:currentOrgId and bm.checkerAuthorization = 'Y' ")
    List<AccountBillEntryMasterEnitity> findAdvanceBill(@Param("prAdvEntryId") Long prAdvEntryId,
            @Param("currentOrgId") Long currentOrgId);

}
