/**
 *
 */
package com.abm.mainet.common.integration.acccount.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.QueryConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.integration.acccount.domain.AccountFundMasterEntity;
import com.abm.mainet.common.integration.acccount.dto.AccountFundMasterBean;

/**
 * @author prasant.sahu
 *
 */
@Repository
public class AccountFundMasterDaoImpl extends AbstractDAO<AccountFundMasterEntity> implements
        AccountFundMasterDao {

    @Override
    public List<AccountFundMasterEntity> findAllParentFunds(
            final Long orgId) {

        final Query query = createQuery(QueryConstants.FUND_MASTER.QUERY_TO_FIND_PARENT_FUND);
        query.setParameter(
                "orgId",
                orgId);
        @SuppressWarnings("unchecked")
        final List<AccountFundMasterEntity> listOfEntity = query
                .getResultList();
        return listOfEntity;
    }

    @Override
    public AccountFundMasterEntity getParentDetailsUsingFundId(
            final AccountFundMasterBean tbAcFundMaster) {
        final Query query = createQuery(QueryConstants.FUND_MASTER.QUERY_TO_FIND_PARENT_DET_USING_FUND_ID);
        query.setParameter(
                "fundId",
                tbAcFundMaster.getFundId());
        return (AccountFundMasterEntity) query
                .getSingleResult();
    }

    @Override
    public List<AccountFundMasterEntity> getChildDetailsUsingParentFundId(
            final Long fundId) {
        final Query query = createQuery(QueryConstants.FUND_MASTER.QUERY_TO_GET_CHILD_DET_USING_PARENT_FUND_ID);
        query.setParameter(
                "parentId",
                fundId);
        return query.getResultList();
    }

    @Override
    public List<Object[]> getCodCofigurationDetIdUsingLevel(final Long i) {
        final Query query = createQuery(QueryConstants.FUND_MASTER.QUERY_TO_GET_CONFIG_DET_USING_LVLS);
        query.setParameter("id", i);
        return query.getResultList();
    }

    @Override
    public List<Integer> getAllParentLevelCodes() {
        final Query query = createQuery(QueryConstants.FUND_MASTER.QUERY_TO_GET_PARENT_LVLS_CODES);
        query.setParameter(
                "parentId",
                0L);
        return query.getResultList();
    }

    @Override
    public AccountFundMasterEntity findEntityUsingFuncCode(
            final String childParentCode) {
        final Query query = createQuery(QueryConstants.FUND_MASTER.QUERY_TO_GET_ENTITY_USING_FUNDCODE);
        query.setParameter(
                "fundCode",
                childParentCode);
        return (AccountFundMasterEntity) query
                .getSingleResult();
    }

    @Override
    public Boolean isExist(final String fundCode, final Long orgId) {

        final Query query = createQuery(QueryConstants.FUND_MASTER.QUERY_TO_GET_ENTITY_USING_PARENTCODE);
        query.setParameter(
                "parentCode",
                fundCode);
        query.setParameter(
                "orgId",
                orgId);
        if (query.getResultList().isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public List<AccountFundMasterEntity> getLastLevels(
            final Long orgId, final long prefixId) {
        List<AccountFundMasterEntity> list = null;
        final Query query = createQuery(QueryConstants.FUND_MASTER.QEURY_TO_GET_LAST_FUND_LEVEL);
        query.setParameter(
                "orgId",
                orgId);
        query.setParameter(
                "prefixId",
                prefixId);
        list = query.getResultList();
        return list;
    }

}
