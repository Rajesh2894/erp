package com.abm.mainet.common.repository;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.domain.BankAccountMasterEntity;

/**
 * @author Hiren.Poriya
 *
 */
@Repository
public class BankAccountJpaRepositoryImpl extends AbstractDAO<BankAccountMasterEntity> implements BankAccountRepositoryCustom {

    //@PersistenceContext
    //private EntityManager entityManager;

    @Override
    public int findDuplicateCombination(final String baAccountNo, final Long functionId, final Long pacHeadId, final Long fieldId,
            final Long fundId) {

        final Query query = entityManager.createQuery(buildDynamicQuery(baAccountNo, functionId, pacHeadId, fieldId, fundId));
        query.setParameter(MainetConstants.CommonMasterUi.BA_ACCOUNT_NO, baAccountNo);
        if ((functionId != null) && (functionId != 0l)) {
            query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.FUNCTION_ID, functionId);
        }
        if ((pacHeadId != null) && (pacHeadId != 0l)) {
            query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.PAC_HEAD_ID, pacHeadId);
        }
        if ((fieldId != null) && (fieldId != 0l)) {
            query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.FIELD_ID, fieldId);
        }
        if ((fundId != null) && (fundId != 0l)) {
            query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.FUND_ID, fundId);
        }
        return ((Long) query.getSingleResult()).intValue();
    }

    private String buildDynamicQuery(final String baAccountNo, final Long functionId, final Long pacHeadId, final Long fieldId,
            final Long fundId) {

        final StringBuilder builder = new StringBuilder();
        builder.append("select count(*) FROM BankAccountMasterEntity d where d.baAccountNo=:baAccountNo");
        if ((functionId != null) && (functionId != 0l)) {
            builder.append(" AND d.functionId=:functionId");
        }
        if ((pacHeadId != null) && (pacHeadId != 0l)) {
            builder.append(" AND d.pacHeadId=:pacHeadId");
        }
        if ((fieldId != null) && (fieldId != 0l)) {
            builder.append(" AND d.fieldId=:fieldId");
        }
        if ((fundId != null) && (fundId != 0l)) {
            builder.append(" AND d.fundId =:fundId");
        }

        return builder.toString();
    }

    @Override
    public int isCombinationExists(final String bankName, final String baAccountNo, final Long orgid) {

        Long bankId = Long.valueOf(bankName.toString());
        final Query query = entityManager.createQuery(buildDynamicDuplicateExistQuery(bankId, baAccountNo, orgid));
        query.setParameter(MainetConstants.CommonMasterUi.BA_ACCOUNT_NO, baAccountNo);
        if ((bankId != null)) {
            query.setParameter("bankId", bankId);
        }
        query.setParameter(MainetConstants.VENDOR_MASTER.ORGID2, orgid);

        return ((Long) query.getSingleResult()).intValue();
    }

    private String buildDynamicDuplicateExistQuery(final Long bankId, final String baAccountNo, final Long orgid) {

        final StringBuilder builder = new StringBuilder();
        builder.append(
                "select count(*) FROM BankAccountMasterEntity d where d.baAccountNo=:baAccountNo and d.orgId=:orgid");
        if ((bankId != null)) {
            builder.append(" AND d.bankId.bankId=:bankId");
        }
        return builder.toString();
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<BankAccountMasterEntity> findByAllGridSearchData(String accountNo, Long accountNameId, Long bankId, Long orgId) {

        final Query query = entityManager.createQuery(buildGridSearchDataQuery(accountNo, accountNameId, bankId));

        if (accountNo != null && !accountNo.isEmpty()) {
            query.setParameter("accountNo", accountNo);
        }
        if (accountNameId != null && accountNameId != 0) {
            query.setParameter("accountNameId", accountNameId);
        }
        if (bankId != null && bankId != 0) {
            query.setParameter("bankId", bankId);
        }
        query.setParameter(MainetConstants.VENDOR_MASTER.ORGID2, orgId);
        List<BankAccountMasterEntity> result = null;
        result = query.getResultList();
        return result;
    }

    private String buildGridSearchDataQuery(String accountNo, Long accountNameId, Long bankId) {

        final StringBuilder builder = new StringBuilder();
        builder.append(
                "select d FROM BankAccountMasterEntity d where d.orgId=:orgid");
        if ((accountNo != null) && !accountNo.isEmpty()) {
            builder.append(" AND d.baAccountNo=:accountNo");
        }
        if (accountNameId != null && accountNameId != 0) {
            builder.append(" AND d.baAccountId=:accountNameId");
        }
        if (bankId != null && bankId != 0) {
            builder.append(" AND d.bankId.bankId=:bankId");
        }
        return builder.toString();
    }

}
