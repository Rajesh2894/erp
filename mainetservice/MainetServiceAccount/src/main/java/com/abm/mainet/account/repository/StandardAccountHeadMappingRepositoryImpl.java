
package com.abm.mainet.account.repository;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadPrimaryAccountCodeMasterEntity;

/**
 * @author Hiren.Poriya
 *
 */
@Repository
public class StandardAccountHeadMappingRepositoryImpl extends AbstractDAO<AccountHeadPrimaryAccountCodeMasterEntity>
        implements StandardAccountHeadMappingRepositoryCustom {

    // @PersistenceContext
    // private EntityManager entityManager;

    /*
     * (non-Javadoc)
     * @see
     * com.abm.mainetservice.account.repository.jpa.StandardAccountHeadMappingRepositoryCustom#updateStandardMappingData(java.lang
     * .Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long)
     */
    @Override
    public void updateStandardMappingData(final Long primaryAcHeadId, final Long cpdIdAccountType, final Long pacStatusCpdId,
            final Long accountSubType) {// Long cpdIdBanktype,
        final Query query = entityManager
                .createQuery(buildDynamicQuery(primaryAcHeadId, cpdIdAccountType, pacStatusCpdId, accountSubType));// cpdIdBanktype,

        if (cpdIdAccountType != null) {
            query.setParameter("cpdIdAccountType", cpdIdAccountType);
        }
        if (pacStatusCpdId != null) {
            query.setParameter("pacStatusCpdId", pacStatusCpdId);
        }

        if ((accountSubType != null) && (accountSubType != 0l)) {
            query.setParameter("accountSubType", accountSubType);
        }
        query.setParameter("primaryAcHeadId", primaryAcHeadId);
        query.executeUpdate();

    }

    private String buildDynamicQuery(final Long primaryAcHeadId, final Long cpdIdAccountType, final Long pacStatusCpdId,
            final Long accountSubType) {

        final StringBuilder builder = new StringBuilder();
        builder.append(
                "update AccountHeadPrimaryAccountCodeMasterEntity SET cpdIdAccountType=:cpdIdAccountType, cpdIdBanktype=:pacStatusCpdId ");
        if ((accountSubType != null) && (accountSubType != 0l)) {
            builder.append(", cpdIdPayMode=:accountSubType ");
        }
        builder.append(" WHERE primaryAcHeadId=:primaryAcHeadId");
        return builder.toString();
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<AccountHeadPrimaryAccountCodeMasterEntity> findAllByAccountTypeId(final Long accountType,
            final Long accountSubType,
            final Long defaultOrgId) {

        String queryString = "select te from AccountHeadPrimaryAccountCodeMasterEntity te where te.orgid =:defaultOrgId";

        if ((accountType != null) && (accountType != 0)) {
            queryString += " and te.cpdIdAccountType =:accountType";
        }
        if ((accountSubType != null) && (accountSubType != 0)) {
            queryString += " and te.cpdIdPayMode =:accountSubType";
        }
        queryString += " order by 1 desc";

        final Query query = createQuery(queryString);

        query.setParameter("defaultOrgId",
                defaultOrgId);

        if ((accountType != null) && (accountType != 0)) {
            query.setParameter("accountType",
                    accountType);
        }
        if ((accountSubType != null) && (accountSubType != 0)) {
            query.setParameter("accountSubType",
                    accountSubType);
        }
        List<AccountHeadPrimaryAccountCodeMasterEntity> result = null;
        result = query.getResultList();
        return result;
    }

}
