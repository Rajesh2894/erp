package com.abm.mainet.account.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.account.constants.AccountMasterQueryConstant;
import com.abm.mainet.account.domain.AccountTDSTaxHeadsEntity;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadSecondaryAccountCodeMasterEntity;

/**
 * Repository : TbAcTdsTaxheads.
 */
public interface AccountTDSTaxHeadsJpaRepository extends PagingAndSortingRepository<AccountTDSTaxHeadsEntity, Long> {

    /**
     * @param orgId
     * @return
     */
    @Query(AccountMasterQueryConstant.MASTERS.TAX_HEADS_MASER.QUERY_TO_GET_ALL_TAX_HEAD_DETAILS)
    Iterable<AccountTDSTaxHeadsEntity> findAll(
            @Param(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.ORGID) Long orgId);

    /**
     * @param pacHeadId
     * @param orgId
     * @return
     */
    @Query("select s from AccountHeadSecondaryAccountCodeMasterEntity s where s.tbAcPrimaryheadMaster.primaryAcHeadId =:pacHeadId and s.orgid =:orgId")
    List<AccountHeadSecondaryAccountCodeMasterEntity> getSaHeadItemsList(
            @Param("pacHeadId") Long pacHeadId,
            @Param("orgId") Long orgId);

    /**
     * @param tdsId
     * @param orgId
     * @return
     */
    @Query("select te from AccountTDSTaxHeadsEntity te where te.tdsId =:tdsId and te.orgid =:orgId")
    AccountTDSTaxHeadsEntity findOne(@Param(MainetConstants.TAX_HEAD_MAPPING_MASTER.TDS_ID) Long tdsId,
            @Param(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.ORGID) Long orgId);

    @Query("select count(t) from AccountTDSTaxHeadsEntity t where t.tbComparamDet.cpdId =:tdsTypeCpdId")
    Long getTdsTypeCount(@Param("tdsTypeCpdId") Long tdsTypeCpdId);

}
