package com.abm.mainet.common.integration.acccount.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadSecondaryAccountCodeMasterEntity;

@Repository
public class SecondaryheadMasterDaoImpl extends AbstractDAO<AccountHeadSecondaryAccountCodeMasterEntity>
        implements SecondaryheadMasterDao {

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<AccountHeadSecondaryAccountCodeMasterEntity> findByAllGridSearchData(final Long fundId, final Long fieldId,
            final Long pacHeadId,
            final Long functionId, final Long sacHeadId,
            final Long ledgerTypeId, final Long defaultOrgId) {

        String queryString = "select te from AccountHeadSecondaryAccountCodeMasterEntity te where te.orgid =:defaultOrgId";
        if (fundId != null) {
            queryString += " and te.tbAcFundMaster.fundId =:fundId";
        }
        if (fieldId != null) {
            queryString += " and te.tbAcFieldMaster.fieldId =:fieldId";
        }
        if (pacHeadId != null) {
            queryString += " and te.tbAcPrimaryheadMaster.primaryAcHeadId =:pacHeadId";
        }
        if (functionId != null) {
            queryString += " and te.tbAcFunctionMaster.functionId =:functionId";
        }
        if (sacHeadId != null) {
            queryString += " and te.sacHeadId =:sacHeadId";
        }
        if (ledgerTypeId != null) {
            queryString += " and te.sacLeddgerTypeCpdId =:ledgerTypeId";
        }
        queryString += " order by 1 desc";

        final Query query = createQuery(queryString);

        query.setParameter("defaultOrgId",
                defaultOrgId);

        if (fundId != null) {
            query.setParameter("fundId",
                    fundId);
        }
        if (fieldId != null) {
            query.setParameter("fieldId",
                    fieldId);
        }
        if (pacHeadId != null) {
            query.setParameter("pacHeadId",
                    pacHeadId);
        }
        if (functionId != null) {
            query.setParameter("functionId",
                    functionId);
        }
        if (sacHeadId != null) {
            query.setParameter("sacHeadId",
                    sacHeadId);
        }
        if (ledgerTypeId != null) {
            query.setParameter("ledgerTypeId",
                    ledgerTypeId);
        }
        List<AccountHeadSecondaryAccountCodeMasterEntity> result = null;
        result = query.getResultList();
        return result;
    }
}
