
package com.abm.mainet.account.repository;

import java.util.List;

import com.abm.mainet.common.integration.acccount.domain.AccountHeadPrimaryAccountCodeMasterEntity;

/**
 * Common Repository being used to handle dynamic query for Budget Head related. add method here and provide their implementation
 * in {@code BudgetHeadRepositoryImpl} class
 *
 * @author Hiren.Poriya
 *
 */
public interface StandardAccountHeadMappingRepositoryCustom {

    /**
     * use this method in case of find Duplicate combination on given parameters required anywhere
     * @param baAccountNo : pass Account No
     * @param pacHeadId : pass Account head type Id or {@code null} if not necessary
     * @param fieldId : pass primary key of BudgetCode or {@code null} if not necessary
     * @param fundId : pass super organization id or {@code null} if not necessary
     * @return count for provided input
     */

    void updateStandardMappingData(Long pacHeadId, Long accountTypeId, Long statusId, Long accountSubType);// Long bankType,

    List<AccountHeadPrimaryAccountCodeMasterEntity> findAllByAccountTypeId(Long accountType, Long accountSubType,
            Long defaultOrgId);

}
