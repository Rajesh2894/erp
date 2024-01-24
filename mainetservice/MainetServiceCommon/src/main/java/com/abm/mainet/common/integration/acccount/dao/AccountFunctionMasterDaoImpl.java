/**
 *
 */
package com.abm.mainet.common.integration.acccount.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.QueryConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.integration.acccount.domain.AccountFunctionMasterEntity;
import com.abm.mainet.common.integration.acccount.dto.AccountFunctionMasterBean;

/**
 * @author prasant.sahu
 *
 */
@Repository
public class AccountFunctionMasterDaoImpl extends AbstractDAO<AccountFunctionMasterEntity> implements AccountFunctionMasterDao {

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.account.dao.AccountFunctionMasterDao# findAllParentFunctions()
     */
    @Override
    public List<AccountFunctionMasterEntity> findAllParentFunctions(
            final Long orgId) {

        final Query query = createQuery(QueryConstants.FUNCTION_MASTER.QUERY_TO_FIND_PARENT_FUN);
        query.setParameter("orgId", orgId);

        final List<AccountFunctionMasterEntity> listOfEntity = query
                .getResultList();
        return listOfEntity;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.account.dao.AccountFunctionMasterDao# getParentDetailsUsingFunctionId
     * (com.abm.mainet.account.dto.AccountFunctionMasterBean)
     */
    @Override
    public AccountFunctionMasterEntity getParentDetailsUsingFunctionId(
            final AccountFunctionMasterBean tbAcFunctionMaster) {
        final Query query = createQuery(QueryConstants.FUNCTION_MASTER.QUERY_TO_FIND_PARENT_DET_USING_FUN_ID);
        query.setParameter("functionId", tbAcFunctionMaster.getFunctionId());
        return (AccountFunctionMasterEntity) query.getSingleResult();
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.account.dao.AccountFunctionMasterDao# getChildDetailsUsingParentFunctionId(java.lang.Long)
     */
    @Override
    public List<AccountFunctionMasterEntity> getChildDetailsUsingParentFunctionId(
            final Long functionId) {
        final Query query = createQuery(QueryConstants.FUNCTION_MASTER.QUERY_TO_GET_CHILD_DET_USING_PARENT_FUN_ID);
        query.setParameter("parentId", functionId);
        return query.getResultList();
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.account.dao.AccountFunctionMasterDao# findEntityUsingFuncCode(java.lang.String)
     */
    @Override
    public AccountFunctionMasterEntity findEntityUsingFuncCode(
            final String childParentCode) {
        final Query query = createQuery(QueryConstants.FUNCTION_MASTER.QUERY_TO_GET_ENTITY_USING_FUNCODE);
        query.setParameter("functionCode", childParentCode);
        return (AccountFunctionMasterEntity) query.getSingleResult();
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.account.dao.AccountFunctionMasterDao# getCodCofigurationDetIdUsingLevel(int)
     */
    @Override
    public List<Object[]> getCodCofigurationDetIdUsingLevel(final Long i) {
        final Query query = createQuery(QueryConstants.FUNCTION_MASTER.QUERY_TO_GET_CONFIG_DET_USING_LVL);
        query.setParameter("id", i);
        return query.getResultList();
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.account.dao.AccountFunctionMasterDao# getAllParentLevelCodes()
     */
    @Override
    public List<Integer> getAllParentLevelCodes(final Long orgId) {
        final Query query = createQuery(QueryConstants.FUNCTION_MASTER.QUERY_TO_GET_PARENT_LVL_CODES);
        query.setParameter("orgId", orgId);
        return query.getResultList();
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.account.dao.AccountFunctionMasterDao#findAllWithOrgId (long)
     */
    @Override
    public List<AccountFunctionMasterEntity> findAllWithOrgId(
            final long orgid) {
        final Query query = createQuery(
                "select am from AccountFunctionMasterEntity am  WHERE am.orgid= :orgId and am.functionParentId.functionId is null");
        query.setParameter("orgId", orgid);

        return query.getResultList();
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.account.dao.AccountFunctionMasterDao#getLastLevels (java.lang.Long, java.lang.String)
     */
    @Override
    public List<AccountFunctionMasterEntity> getLastLevels(
            final Long orgId, final long prefixId) {

        List<AccountFunctionMasterEntity> list = null;
        final Query query = createQuery(QueryConstants.FUNCTION_MASTER.QEURY_TO_GET_LAST_FUNCTION_LEVEL);
        query.setParameter("orgId", orgId);
        query.setParameter("prefixId", prefixId);
        list = query.getResultList();
        return list;
    }

    @Override
    public List<AccountFunctionMasterEntity> getLastLevels(final Long orgId, final String prefixId,
            final String AccountMasterConstantsCMD) {

        AccountFunctionMasterEntity accountFunctionMasterEntitydto;
        final List<AccountFunctionMasterEntity> accountFunctionMasterEntity = new ArrayList<>();
        Long primaryheadvalue = null;
        String primaryheaddesicription = null;
        String primaryheadcompocode = null;
        final Query query = createQuery(QueryConstants.FUNCTION_MASTER.QEURY_TO_GET_LAST_FUNCTION_LEVEL2);
        query.setParameter("prefixId", prefixId);
        query.setParameter("AccountMasterConstantsCMD", AccountMasterConstantsCMD);
        final List<Object[]> List = query.getResultList();
        for (final Object[] tp : List) {
            primaryheadvalue = Long.valueOf(tp[0].toString());
            primaryheaddesicription = tp[1].toString();
            primaryheadcompocode = tp[2].toString();
            accountFunctionMasterEntitydto = new AccountFunctionMasterEntity();

            accountFunctionMasterEntitydto.setFunctionId(primaryheadvalue);
            accountFunctionMasterEntitydto.setFunctionCode(primaryheadcompocode);
            accountFunctionMasterEntitydto.setFunctionDesc(primaryheaddesicription);
            accountFunctionMasterEntity.add(accountFunctionMasterEntitydto);
        }
        return accountFunctionMasterEntity;
    }

}
