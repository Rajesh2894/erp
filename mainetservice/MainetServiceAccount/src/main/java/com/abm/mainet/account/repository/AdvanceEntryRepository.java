package com.abm.mainet.account.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.integration.acccount.domain.AdvanceEntryEntity;

public interface AdvanceEntryRepository
        extends org.springframework.data.repository.PagingAndSortingRepository<AdvanceEntryEntity, Long>,
        AdvanceEntryRepositoryCustom {

    @Query("select pm.cpdIdPayMode from "
            + "AccountHeadPrimaryAccountCodeMasterEntity pm, AccountHeadSecondaryAccountCodeMasterEntity sm "
            + "where pm.primaryAcHeadId = sm.tbAcPrimaryheadMaster.primaryAcHeadId  and pm.orgid =:superOrgId "
            + "and sm.orgid =:orgId and sm.sacHeadId =:accountHeadId")
    Long getAdvAccountTypeId( @Param("accountHeadId") Long accountHeadId,
            @Param("superOrgId") Long superOrgId, @Param("orgId") Long orgId);

    @Modifying
    @Query("update AdvanceEntryEntity as te set te.balanceAmount =:balanceAmount where te.prAdvEntryId =:advId and te.orgid =:orgid")
    void updateAdvanceBalanceAmountInAdvanceTable(@Param("advId") Long advId, @Param("balanceAmount") BigDecimal balanceAmount,
            @Param("orgid") Long orgid);

    @Query(" select sm.sacHeadId,sm.acHeadCode from "
            + " AccountHeadSecondaryAccountCodeMasterEntity sm "
            + " where sm.tbAcPrimaryheadMaster.cpdIdAccountType =:advanceTypeId and sm.tbAcPrimaryheadMaster.cpdIdPayMode =:acountSubType and sm.sacStatusCpdId =:statusId and sm.orgid =:orgId ")
    List<Object[]> getAdvBudgetHeadIds(@Param("statusId") Long statusId, @Param("advanceTypeId") Long advanceTypeId,
            @Param("acountSubType") Long acountSubType, @Param("orgId") Long orgId);

    @Query("select pm.cpdIdPayMode from  AccountHeadPrimaryAccountCodeMasterEntity pm,AccountHeadSecondaryAccountCodeMasterEntity sm \r\n"
            +
            "        where pm.primaryAcHeadId = sm.tbAcPrimaryheadMaster.primaryAcHeadId\r\n" +
            "        and sm.orgid=:orgId and sm.sacHeadId=:id")
    Long getAdvAccountTypeIdByOrgIdAndId(@Param("id") Long id, @Param("orgId") Long orgId);

}
