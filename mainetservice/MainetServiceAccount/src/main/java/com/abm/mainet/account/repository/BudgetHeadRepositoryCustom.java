
package com.abm.mainet.account.repository;

import java.util.List;

/**
 * Common Repository being used to handle dynamic query for Budget Head related. add method here and provide their implementation
 * in {@code BudgetHeadRepositoryImpl} class
 *
 * @author Vivek.Kumar
 *
 */
public interface BudgetHeadRepositoryCustom {

    /**
     * use this method in case of Budget Heads info required anywhere
     * @param modeId : pass Pay Mode id or {@code null} if not necessary
     * @param headTypeId : pass Account head type Id or {@code null} if not necessary
     * @param budgetCodeId : pass primary key of BudgetCode or {@code null} if not necessary
     * @param superOrgId : pass super organization id or {@code null} if not necessary
     * @param orgId : pass orgId or {@code null} if not necessary
     * @return {@code List<Object[]> } for provided input
     */
    List<Object[]> fetchBudgetHeads(Long payMode, Long headTypeId, Long budgetCodeId, Long superOrgId, Long orgId);
}
