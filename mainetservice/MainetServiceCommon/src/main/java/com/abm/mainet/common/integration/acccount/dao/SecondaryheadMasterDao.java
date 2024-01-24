package com.abm.mainet.common.integration.acccount.dao;

import java.util.List;

import com.abm.mainet.common.integration.acccount.domain.AccountHeadSecondaryAccountCodeMasterEntity;

public interface SecondaryheadMasterDao {

    List<AccountHeadSecondaryAccountCodeMasterEntity> findByAllGridSearchData(Long fundId, Long fieldId, Long pacHeadId,
            Long functionId, Long sacHeadId,
            Long ledgerTypeId, Long defaultOrgId);
}
