package com.abm.mainet.common.integration.acccount.dao;

import java.util.List;

import com.abm.mainet.common.integration.acccount.domain.AccountFieldMasterEntity;
import com.abm.mainet.common.integration.acccount.dto.AccountFieldMasterBean;

/**
 * @author tejas.kotekar
 *
 */
public interface AccountFieldMasterDao {

    public List<AccountFieldMasterEntity> findAllParentFields(
            Long orgId);

    /**
     * @param i
     * @return
     */
    public List<Object[]> getCodCofigurationDetIdUsingLevel(Long i);

    /**
     * @param tbAcFieldMaster
     * @return
     */
    AccountFieldMasterEntity getParentDetailsUsingFieldId(
            AccountFieldMasterBean tbAcFieldMaster);

    /**
     * @param fieldId
     * @return
     */
    List<AccountFieldMasterEntity> getChildDetailsUsingParentFieldId(
            Long fieldId);

    /**
     * @param fieldCode
     * @return
     */
    public Boolean isParentExists(String fieldCode, Long orgId);

    /**
     * @param orgId
     * @param string
     * @return
     */
    public List<AccountFieldMasterEntity> getLastLevels(
            Long orgId, long prefixId);

    List<AccountFieldMasterEntity> getLastLevels(Long orgId);

    /**
     * @param chieldFieldCode
     * @return
     */
    public boolean isChildFieldCompositeCodeExists(String compositeCode, Long defaultOrgId);

}
