package com.abm.mainet.account.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.integration.acccount.domain.AccountHeadPrimaryAccountCodeMasterEntity;

/**
 * Repository : StandardAccountHeadMappingRepository
 */
public interface StandardAccountHeadMappingRepository extends
        PagingAndSortingRepository<AccountHeadPrimaryAccountCodeMasterEntity, Long>, StandardAccountHeadMappingRepositoryCustom {

    @Query("select d from AccountHeadPrimaryAccountCodeMasterEntity d where d.primaryAcHeadId=:primaryAcHeadId and d.cpdIdAccountType=:accountType and d.cpdIdPayMode=:accountSubType and d.cpdIdBanktype=:lookUpStatusId and d.orgid=:orgid")
    AccountHeadPrimaryAccountCodeMasterEntity findEntity(@Param("primaryAcHeadId") Long primaryAcHeadId,
            @Param("accountType") Long accountType, @Param("accountSubType") Long accountSubType,
            @Param("lookUpStatusId") Long lookUpStatusId, @Param("orgid") Long orgId);

    @Modifying
    @Query("update AccountHeadPrimaryAccountCodeMasterEntity SET cpdIdBanktype=:pacStatusCpdId WHERE primaryAcHeadId=:primaryAcHeadId")
    int updateData(@Param("pacStatusCpdId") Long pacStatusCpdId, @Param("primaryAcHeadId") Long primaryAcHeadId);

    @Query("select d from AccountHeadPrimaryAccountCodeMasterEntity d where d.cpdIdAccountType=:accountType and d.cpdIdPayMode=:payModeId and d.cpdIdBanktype=:lookUpStatusId and d.orgid=:orgid")
    AccountHeadPrimaryAccountCodeMasterEntity findStatusPaymode(@Param("accountType") Long accountType,
            @Param("payModeId") Long payModeId, @Param("lookUpStatusId") Long lookUpStatusId, @Param("orgid") Long orgId);
}
