/**
 *
 */
package com.abm.mainet.common.integration.acccount.dao;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.QueryConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadPrimaryAccountCodeMasterEntity;
import com.abm.mainet.common.integration.acccount.dto.AccountHeadPrimaryAccountCodeMasterBean;

/**
 * @author prasant.sahu
 *
 */
@Repository
public class AccountHeadPrimaryAccountCodeMasterDaoImpl extends AbstractDAO<AccountHeadPrimaryAccountCodeMasterEntity>
        implements AccountHeadPrimaryAccountCodeMasterDao {

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.dao.AccountHeadPrimaryAccountCodeMasterDao#getParentDetailsUsingFunctionId(com.abm.
     * mainetservice.account.bean.AccountHeadPrimaryAccountCodeMasterBean)
     */
    @Override
    public AccountHeadPrimaryAccountCodeMasterEntity getParentDetailsUsingPrimaryHeadId(
            final AccountHeadPrimaryAccountCodeMasterBean tbAcFunctionMaster) {
        final Query query = createQuery(QueryConstants.PRIMARY_CODE_MASTER.QUERY_PRIMARY_CODE_ENTITY);
        query.setParameter("primaryAcHeadId", tbAcFunctionMaster.getPrimaryAcHeadId());
        final AccountHeadPrimaryAccountCodeMasterEntity entity = (AccountHeadPrimaryAccountCodeMasterEntity) query
                .getSingleResult();
        return entity;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.dao.AccountHeadPrimaryAccountCodeMasterDao#getCodCofigurationDetIdUsingLevel(int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> getCodCofigurationDetIdUsingLevel(final Long level) {
        final Query query = createQuery(QueryConstants.FUNCTION_MASTER.QUERY_TO_GET_CONFIG_DET_USING_LVL);
        query.setParameter("id", level);
        return query.getResultList();
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.dao.AccountHeadPrimaryAccountCodeMasterDao#getAllParentLevelCodes()
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> getAllParentLevelCodes(final Long orgId) {
        final Query query = createQuery(QueryConstants.PRIMARY_CODE_MASTER.QUERY_FOR_ID_NULL);
        query.setParameter("orgId", orgId);
        return query.getResultList();
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.dao.AccountHeadPrimaryAccountCodeMasterDao#findAllWithOrgId(long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<AccountHeadPrimaryAccountCodeMasterEntity> findAllWithOrgId(final Long orgid) {
        final Query query = createQuery(QueryConstants.PRIMARY_CODE_MASTER.QUERY_FOR_NULL_ID_WITH_ORG);
        query.setParameter("orgId", orgid);

        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AccountHeadPrimaryAccountCodeMasterEntity> getLastLevels(final Long orgId, final Long prefixId) {
        List<AccountHeadPrimaryAccountCodeMasterEntity> list = null;
        final Query query = createQuery(QueryConstants.PRIMARY_CODE_MASTER.QUERY_FOR_LAST_LEVEL);
        query.setParameter("orgId", orgId);
        query.setParameter("prefixId", prefixId);
        list = query.getResultList();
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AccountHeadPrimaryAccountCodeMasterEntity> getLastActiveLevels(final Long orgId, final Long prefixId,
            final Long pacStatusCpdId) {
        List<AccountHeadPrimaryAccountCodeMasterEntity> list = null;
        final Query query = createQuery(QueryConstants.PRIMARY_CODE_MASTER.QUERY_FOR_LAST_ACTIVE_LEVEL);
        query.setParameter("orgId", orgId);
        query.setParameter("prefixId", prefixId);
        query.setParameter("pacStatusCpdId", pacStatusCpdId);
        list = query.getResultList();
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> getPrimaryHeadCodeAllLastLevelsAsset(final Long orgId) {
        final Query query = createQuery(QueryConstants.BUGOPEN_BALANCE.QUERY_TO_FIND_ASSETS_DATA);
        query.setParameter("orgId", orgId);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> getPrimaryHeadCodeAllLastLevelsLiability(final Long orgId) {
        final Query query = createQuery(QueryConstants.BUGOPEN_BALANCE.QUERY_TO_FIND_LIABILITY_DATA);
        query.setParameter("orgId", orgId);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AccountHeadPrimaryAccountCodeMasterEntity> getLastLevelsWithOutbankAndVendor(final Long orgId,
            final Long prefixId) {
        List<AccountHeadPrimaryAccountCodeMasterEntity> list = null;
        final Query query = createQuery(QueryConstants.PRIMARY_CODE_MASTER.QUERY_FOR_LAST_LEVEL_WITHOUT_BANK_AND_VENDOR);
        query.setParameter("orgId", orgId);
        query.setParameter("prefixId", prefixId);
        list = query.getResultList();
        return list;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.dao.AccountHeadPrimaryAccountCodeMasterDao#getParentDetails()
     */
    @SuppressWarnings("unchecked")
    @Override
    @Transactional(readOnly = true)
    public Set<AccountHeadPrimaryAccountCodeMasterEntity> getParentDetails(final Long orgId) {
        Set<AccountHeadPrimaryAccountCodeMasterEntity> parentsList = null;
        final Query query = createQuery(QueryConstants.PRIMARY_CODE_MASTER.QUERY_FOR_NULL_PARENTS);
        query.setParameter("orgId", orgId);
        parentsList = new LinkedHashSet<AccountHeadPrimaryAccountCodeMasterEntity>(
                query.getResultList());
        return parentsList;

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.dao.AccountHeadPrimaryAccountCodeMasterDao#findAllCopositeCodeOrgId(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<AccountHeadPrimaryAccountCodeMasterEntity> findAllCopositeCodeOrgId(final Long orgid) {

        List<AccountHeadPrimaryAccountCodeMasterEntity> parentsList = null;
        final Query query = createQuery(QueryConstants.PRIMARY_CODE_MASTER.QUERY_FOR_ALL_COMPOSITECODE_ORG);
        query.setParameter("orgId", orgid);
        parentsList = new ArrayList<AccountHeadPrimaryAccountCodeMasterEntity>(
                query.getResultList());
        return parentsList;
    }
}
