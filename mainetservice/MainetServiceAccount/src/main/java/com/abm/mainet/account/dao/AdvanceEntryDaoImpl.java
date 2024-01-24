package com.abm.mainet.account.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AdvanceEntry;
import com.abm.mainet.common.constant.QueryConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.integration.acccount.domain.AdvanceEntryEntity;

@Repository
public class AdvanceEntryDaoImpl extends AbstractDAO<AdvanceEntryEntity> implements AdvanceEntryDao {

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<AdvanceEntryEntity> findByGridAllData(final Long advanceNumber, final Date advDate, final String name,
            final BigDecimal advAmount,
            final Long advanceType, final String cpdIdStatus, final Long orgId,Long deptId) {

        String queryString = QueryConstants.MASTERS.AdvanceEntryMaster.QUERY_TO_GET_ALL_GRID_DATA;
        Long advName = null;
        if ((name != null) && !name.isEmpty()) {
            advName = Long.valueOf(name);
        }
        if (advanceNumber != null) {
            queryString += " and te.prAdvEntryNo =:advanceNumber";
        }

        if (advDate != null) {
            queryString += " and te.prAdvEntryDate =:advDate";
        }

        if (advName != null) {
            queryString += " and te.vendorId =:advName";
        }

        if (advAmount != null) {
            queryString += " and te.advanceAmount =:advAmount";
        }
        if (advanceType != null) {
            queryString += " and te.advanceTypeId =:advanceType";
        }
        if ((cpdIdStatus != null) && !cpdIdStatus.isEmpty()) {
            queryString += " and te.cpdIdStatus =:cpdIdStatus";
        }
        if(deptId!=null) {
        	queryString += " and te.deptId =:deptId";
        }

        queryString += " order by 1 desc";

        final Query query = createQuery(queryString);

        query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.ORGID,
                orgId);

        if (advanceNumber != null) {
            query.setParameter(AdvanceEntry.ADVANCE_NUMBER,
                    advanceNumber);
        }
        if (advDate != null) {
            query.setParameter(AdvanceEntry.ADV_DATE,
                    advDate);
        }
        if (advName != null) {
            query.setParameter(AdvanceEntry.ADV_NAME,
                    advName);
        }
        if (advAmount != null) {
            query.setParameter(AdvanceEntry.ADV_AMOUNT,
                    advAmount);
        }
        if (advanceType != null) {
            query.setParameter(AdvanceEntry.ADVNCE_TYPE,
                    advanceType);
        }
        if ((cpdIdStatus != null) && !cpdIdStatus.isEmpty()) {
            query.setParameter(AdvanceEntry.CPD_ID_STATUS,
                    cpdIdStatus);
        }
        if(deptId!=null) {
        	 query.setParameter("deptId",deptId);
        }
        List<AdvanceEntryEntity> result = null;
        result = query.getResultList();
        return result;
    }
}