/**
 *
 */
package com.abm.mainet.common.integration.acccount.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.QueryConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.integration.acccount.domain.AccountFieldMasterEntity;
import com.abm.mainet.common.integration.acccount.dto.AccountFieldMasterBean;

/**
 * @author tejas.kotekar
 *
 */
@Repository
public class AccountFieldMasterDaoImpl extends AbstractDAO<AccountFieldMasterEntity> implements AccountFieldMasterDao {

    @Override
    public List<AccountFieldMasterEntity> findAllParentFields(
            final Long orgId) {
        final Query query = createQuery(QueryConstants.FIELD_MASTER.QUERY_TO_FIND_PARENT_FIELD);
        query.setParameter("orgId", orgId);
        final List<AccountFieldMasterEntity> listOfEntity = query.getResultList();
        return listOfEntity;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.account.dao.AccountFieldMasterDao# getCodCofigurationDetIdUsingLevel(int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> getCodCofigurationDetIdUsingLevel(final Long i) {

        final Query query = createQuery(QueryConstants.FIELD_MASTER.QUERY_TO_GET_CODCOFDET_ID);
        query.setParameter("codCofId", i);
        return query.getResultList();

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.account.dao.AccountFieldMasterDao# getParentDetailsUsingFieldId
     * (com.abm.mainet.account.dto.AccountFieldMasterBean)
     */
    @Override
    public AccountFieldMasterEntity getParentDetailsUsingFieldId(
            final AccountFieldMasterBean tbAcFieldMaster) {

        final Query query = createQuery(QueryConstants.FIELD_MASTER.QUERY_TO_GET_PARENT_DET_USING_FIELD_ID);
        query.setParameter("fieldId", tbAcFieldMaster.getFieldId());
        return (AccountFieldMasterEntity) query
                .getSingleResult();
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.account.dao.AccountFieldMasterDao# getChildDetailsUsingParentFieldId(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<AccountFieldMasterEntity> getChildDetailsUsingParentFieldId(
            final Long fieldId) {

        final Query query = createQuery(QueryConstants.FIELD_MASTER.QUERY_TO_GET_CHILD_DET_USING_PARENT_FIELD_ID);
        query.setParameter("parentId", fieldId);
        return query.getResultList();
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.account.dao.AccountFieldMasterDao# getParentFieldCodeList(java.lang.String)
     */
    @Override
    public Boolean isParentExists(final String fieldCode, final Long orgId) {

        final Query query = createQuery(QueryConstants.FIELD_MASTER.QUERY_TO_CHECK_IF_PARENT_CODE_EXISTS);
        query.setParameter("parentCode", fieldCode);
        query.setParameter("orgId", orgId);
        if (query.getResultList().isEmpty()) {
            return false;
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.account.dao.AccountFieldMasterDao#getLastLevels (java.lang.Long, java.lang.String)
     */
    @Override
    public List<AccountFieldMasterEntity> getLastLevels(
            final Long orgId, final long prefixId) {
        List<AccountFieldMasterEntity> list = null;
        final Query query = createQuery(QueryConstants.FIELD_MASTER.QEURY_TO_GET_LAST_FIELD_LEVEL);
        query.setParameter("orgId", orgId);
        query.setParameter(
                "prefixId",
                prefixId);
        list = query.getResultList();
        return list;
    }

    @Override
    public List<AccountFieldMasterEntity> getLastLevels(final Long orgId) {
        List<AccountFieldMasterEntity> list = null;
        final Query query = createQuery(
                "select p from AccountFieldMasterEntity p WHERE p.fieldParentId.fieldId is not null and p.fieldId not in (select 1 from AccountFieldMasterEntity a where a.fieldParentId.fieldId = p.fieldId) and p.orgid=?1 order by p.fieldId");
        query.setParameter(1, orgId);
        list = query.getResultList();
        return list;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.account.dao.AccountFieldMasterDao# getChildFieldCodeList(java.lang.String)
     */
    @Override
    public boolean isChildFieldCompositeCodeExists(final String compositeCode, final Long defaultOrgId) {

        final Query query = createQuery(QueryConstants.FIELD_MASTER.QUERY_TO_CHECK_CHILD_COMPOSITE_CODE_EXISTS);
        query.setParameter("compositeCode", compositeCode);
        query.setParameter("orgId", defaultOrgId);

        if (query.getResultList().isEmpty()) {
            return false;
        }
        return true;
    }

}
