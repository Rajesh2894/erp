/**
 *
 */
package com.abm.mainet.common.master.service;

import java.util.List;

import com.abm.mainet.common.master.dto.TbTaxAcMappingBean;

/**
 * @author Harsha.Ramachandran
 *
 */
public interface TbTaxAcMappingService {

    /**
     * Creates the given entity in the database
     *
     * @param entity
     * @return
     */
    TbTaxAcMappingBean create(TbTaxAcMappingBean bean);

    List<TbTaxAcMappingBean> getByTaxIdOrgId(Long orgId, Long taxId);

    List<Object> getbudgetCodes(List<Long> budgetCodeList);

    TbTaxAcMappingBean update(TbTaxAcMappingBean bean);

    TbTaxAcMappingBean findTbTaxBudgetCode(Long taxId, Long orgId);

    void delete(Long taxbId);

    TbTaxAcMappingBean findById(Long taxbId);

    TbTaxAcMappingBean getBySacHeadId(Long sacHeadId, Long dmdClass, Long orgId);

}
