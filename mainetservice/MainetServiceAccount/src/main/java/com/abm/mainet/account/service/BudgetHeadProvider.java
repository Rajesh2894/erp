
package com.abm.mainet.account.service;

import java.util.List;

import com.abm.mainet.account.dto.BudgetHeadDTO;

/**
 * A Service to provide Budget Head related information where ever Budget Head related things required, use this service or add
 * your requirement related method here
 *
 * @author Vivek.Kumar
 *
 */
public interface BudgetHeadProvider {

    /**
     * use this method to find budget code
     * @param orgId : pass orgId
     * @param mode : pass Pay Mode
     * @return {@code List<BudgetCodeDTO>} containing all required data as well contain combined budget code
     * @throws IllegalArgumentException Either due to invalid parameter passed OR No record found for provided input parameter
     */
    List<BudgetHeadDTO> findBudgetHeadsByMode(long payMode, long orgId);

    /**
     * use this method in case to get all budget code by primary key
     * @param budgetCodeId : pass primary key of BudegtCode
     * @param orgId : pass orgId
     * @return {@code List<BudgetCodeDTO>} containing all required data as well contain combined budget code
     * @throws IllegalArgumentException Either due to invalid parameter passed OR No record found for provided input parameter
     */
    List<BudgetHeadDTO> findAllBudgetHeadsById(long budgetCodeId, long orgId);

    /**
     * use this method to find All Budget Heads info by passing HeadType
     * @param superOrgId : pass super organization Id
     * @param orgId : pass current orgId
     * @param headTypeId : pass Account head type id
     * @return {@code List<BudgetCodeDTO>} containing all required data as well contain combined budget code
     * @throws IllegalArgumentException Either due to invalid parameter passed OR No record found for provided input parameter
     */
    List<BudgetHeadDTO> findAllBudgetHeadsByHeadType(long superOrgId, long orgId, long headTypeId);

}
