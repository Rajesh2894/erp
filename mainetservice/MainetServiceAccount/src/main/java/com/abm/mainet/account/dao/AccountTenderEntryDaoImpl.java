
package com.abm.mainet.account.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.domain.AccountTenderEntryEntity;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.TENDER_ENTRY;
import com.abm.mainet.common.constant.QueryConstants;
import com.abm.mainet.common.dao.AbstractDAO;

/**
 * @author niranjan.rane
 *
 */
@Repository
public class AccountTenderEntryDaoImpl extends AbstractDAO<AccountTenderEntryEntity> implements AccountTenderEntryDao {

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.account.dao.AccountTenderEntryDao#isCombinationExists(java.lang.Long, java.lang.Long, java.lang.Long,
     * java.lang.Long, java.lang.Long)
     */
    @Override
    @Transactional
    public Boolean isCombinationExists(final Long fundId, final Long functionId,
            final Long fieldId, final Long pacId, final Long sacId) {

        final Query query = createQuery(QueryConstants.MASTERS.TENDER_ENTRY.QUERY_TO_CHEK_DUPLICATE_ENTRY);

        query.setParameter(
                MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.FUND_ID,
                fundId);
        query.setParameter(
                MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.FUNCTION_ID,
                functionId);
        query.setParameter(
                MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.FIELD_ID,
                fieldId);
        query.setParameter(
                MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.PAC_HEAD_ID,
                pacId);
        query.setParameter(
                MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.SAC_HEAD_ID,
                sacId);

        if (query.getResultList().isEmpty()) {
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<AccountTenderEntryEntity> findByAllGridSearchData(final String trTenderNo, final Long vmVendorid,
            final Long trTypeCpdId,
            final Long sacHeadId, final String trTenderAmount, final String statusId, final Long orgId) {
        BigDecimal tenderAmount = null;
        String status = MainetConstants.BLANK;
        if ((trTenderAmount != null) && !trTenderAmount.isEmpty()) {
            tenderAmount = new BigDecimal(trTenderAmount);
        }
        if ((statusId != null) && !statusId.isEmpty()) {
            if (statusId.equals(MainetConstants.MENU.A)) {
                status = MainetConstants.MENU.N;
            } else {
                status = MainetConstants.MENU.Y;
            }
        }
        String queryString = QueryConstants.MASTERS.TENDER_ENTRY.QUERY_TO_GET_DISTINCT_TENDER_ENTRY;
        if ((trTenderNo != null) && !trTenderNo.isEmpty()) {
            queryString += " and te.trTenderNo =:trTenderNo";
        }
        if (vmVendorid != null) {
            queryString += " and te.tbVendormaster.vmVendorid =:vmVendorid";
        }
        if (trTypeCpdId != null) {
            queryString += " and te.tbComparamDet.cpdId =:trTypeCpdId";
        }
        if (sacHeadId != null) {
            queryString += " and td.sacHeadId =:sacHeadId";
        }

        if (tenderAmount != null) {
            queryString += " and td.tenderDetailAmt =:tenderAmount";
        }

        if ((status != null) && !status.isEmpty()) {
            queryString += " and te.authStatus =:status";
        }
        queryString += " order by 1 desc";

        final Query query = createQuery(queryString);

        query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.ORGID,
                orgId);

        if ((trTenderNo != null) && !trTenderNo.isEmpty()) {
            query.setParameter(TENDER_ENTRY.TR_TENDER_NO,
                    trTenderNo);
        }
        if (vmVendorid != null) {
            query.setParameter(MainetConstants.AccountDeposit.VM_VENDORID,
                    vmVendorid);
        }
        if (trTypeCpdId != null) {
            query.setParameter(TENDER_ENTRY.TR_TYPE_CPD_ID,
                    trTypeCpdId);
        }
        if (sacHeadId != null) {
            query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.SAC_HEAD_ID,
                    sacHeadId);
        }
        if (tenderAmount != null) {
            query.setParameter(TENDER_ENTRY.TENDER_AMOUNT,
                    tenderAmount);
        }
        if ((status != null) && !status.isEmpty()) {
            query.setParameter(MainetConstants.REQUIRED_PG_PARAM.STATUS,
                    status);
        }
        List<AccountTenderEntryEntity> result = null;
        result = query.getResultList();
        return result;
    }

}
