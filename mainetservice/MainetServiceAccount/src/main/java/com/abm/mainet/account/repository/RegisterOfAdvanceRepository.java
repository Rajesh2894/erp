
package com.abm.mainet.account.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.account.domain.AccountBillEntryMasterEnitity;
import com.abm.mainet.common.integration.acccount.domain.AdvanceEntryEntity;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;

/**
 * @author satish.rathore
 *
 */
@Repository
public interface RegisterOfAdvanceRepository extends CrudRepository<AdvanceEntryEntity, Long> {

    @Query("from  AdvanceEntryEntity aee  where aee.prAdvEntryDate <= :asOnDates and aee.orgid=:orgId order by aee.prAdvEntryDate")
    List<AdvanceEntryEntity> findAdvanceLadgerReport(@Param("asOnDates") Date asOnDates,
            @Param("orgId") Long orgId);

    @Query(" from  TbServiceReceiptMasEntity sm  where sm.refId = :prAdvEntryId  and  sm.orgId=:orgId and sm.receiptTypeFlag='A'")
    List<TbServiceReceiptMasEntity> findReceiptForAdvanceLedger(@Param("prAdvEntryId") Long prAdvEntryId, @Param("orgId") Long orgId);

    @Query("from  AccountBillEntryMasterEnitity bm where bm.advanceTypeId =:prAdvEntryId and bm.orgId=:orgId and bm.checkerAuthorization = 'Y' ")
    List<AccountBillEntryMasterEnitity> findbillNoAndDateForAdvanceLadger(@Param("prAdvEntryId") Long prAdvEntryId,
            @Param("orgId") Long orgId);

}
