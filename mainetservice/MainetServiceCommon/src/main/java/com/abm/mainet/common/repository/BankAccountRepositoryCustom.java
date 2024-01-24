package com.abm.mainet.common.repository;

import java.util.List;

import com.abm.mainet.common.domain.BankAccountMasterEntity;

/**
 * Common Repository being used to handle dynamic query for Budget Head related. add method here and provide their implementation
 * in {@code BudgetHeadRepositoryImpl} class
 *
 * @author Hiren.Poriya
 *
 */
public interface BankAccountRepositoryCustom {

    /**
     * use this method in case of find Duplicate combination on given parameters required anywhere
     * @param baAccountNo : pass Account No
     * @param pacHeadId : pass Account head type Id or {@code null} if not necessary
     * @param fieldId : pass primary key of BudgetCode or {@code null} if not necessary
     * @param fundId : pass super organization id or {@code null} if not necessary
     * @return count for provided input
     */
    int findDuplicateCombination(String baAccountNo, Long functionId, Long pacHeadId, Long fieldId, Long fundId);

    int isCombinationExists(String bankName, String baAccountNo, Long orgid);

    List<BankAccountMasterEntity> findByAllGridSearchData(String accountNo, Long accountNameId, Long bankId, Long orgId);
}
