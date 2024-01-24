
package com.abm.mainet.account.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.account.constants.AccountMasterQueryConstant;
import com.abm.mainet.account.domain.AccountTenderEntryEntity;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.TbAcVendormasterEntity;

public interface AccountTenderEntryJpaRepository extends
        PagingAndSortingRepository<AccountTenderEntryEntity, Long> {

    @Query(AccountMasterQueryConstant.MASTERS.TENDER_ENTRY.QUERY_TO_GET_ALL_TENDER_ENTRY_DETAILS)
    List<AccountTenderEntryEntity> findAll(
            @Param(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.ORGID) Long orgId);

    @Query("select vm from TbAcVendormasterEntity vm where vm.orgid=:orgId")
    List<TbAcVendormasterEntity> getVendorMasterList(@Param(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.ORGID) Long orgId);

    @Query("select distinct de.depId,de.depNo from AccountDepositEntity de where de.tbVendormaster.vmVendorid =:vmVendorid and de.tbComparamDetEntity.cpdId =:emdId and de.orgid =:orgId order by 1 desc")
    List<Object[]> findDepositTypeEmdData(@Param("vmVendorid") Long vmVendorid, @Param("emdId") Long emdId,
            @Param("orgId") Long orgId);

    @Query("select distinct te from AccountTenderEntryEntity te where te.trTenderId =:trTenderId order by 1 desc")
    AccountTenderEntryEntity findById(@Param("trTenderId") Long trTenderId);

    @Modifying
    @Query("update AccountTenderEntryEntity as de set de.tbVoucherEntry.vouId =:billId where de.trTenderId =:trTenderId and de.orgid =:orgid")
    void forUpdateBillIdInToWorkOrderEntryTable(@Param("trTenderId") Long trTenderId, @Param("billId") Long billId,
            @Param("orgid") Long orgid);

    @Query("select distinct te from AccountTenderEntryEntity te where te.trTenderNo =:trTenderNo and te.orgid =:orgid order by 1 desc")
    AccountTenderEntryEntity findByTenderNo(@Param("trTenderNo") String trTenderNo, @Param("orgid") Long orgid);

}
