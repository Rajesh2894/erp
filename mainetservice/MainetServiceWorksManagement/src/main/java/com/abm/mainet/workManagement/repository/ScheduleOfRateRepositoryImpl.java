package com.abm.mainet.workManagement.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.workManagement.domain.ScheduleOfRateMstEntity;

/**
 * @author hiren.poriya
 * @Since 05-Dec-2017
 */
@Repository
public class ScheduleOfRateRepositoryImpl extends AbstractDAO<Long> implements ScheduleOfRateRepositoryCustom {

    @SuppressWarnings("unchecked")
    @Override
    public List<ScheduleOfRateMstEntity> searchSorRecords(final Long orgId, final Long sorCpdId,
            final Date sorFromDate,
            final Date sorToDate) {
        final Query query = entityManager.createQuery(buildDynamicQuery(sorCpdId, sorFromDate, sorToDate));
        query.setParameter("orgId", orgId);
        if (sorCpdId != null && sorCpdId != 0) {
            query.setParameter("sorCpdId", sorCpdId);
        }
        if (sorFromDate != null && sorToDate != null) {
            query.setParameter("sorFromDate", sorFromDate);
            query.setParameter("sorToDate", sorToDate);
        }
        if (sorFromDate != null && sorToDate == null) {
            query.setParameter("sorFromDate", sorFromDate);
        }
        if (sorFromDate == null && sorToDate != null) {
            query.setParameter("sorToDate", sorToDate);
        }
        return query.getResultList();
    }

    private String buildDynamicQuery(Long sorCpdId, Date sorFromDate, Date sorToDate) {
        final StringBuilder builder = new StringBuilder();
        builder.append("SELECT sor FROM ScheduleOfRateMstEntity sor WHERE sor.orgId =:orgId ");
        if (sorCpdId != null && sorCpdId != 0) {
            builder.append(" AND sor.sorCpdId =:sorCpdId ");
        }
        if (sorFromDate != null && sorToDate != null) {
            builder.append(" AND sor.sorFromDate >= :sorFromDate AND sor.sorToDate <= :sorToDate");
        }
        if (sorFromDate != null && sorToDate == null) {
            builder.append(" AND sor.sorFromDate >= :sorFromDate ");
        }
        if (sorFromDate == null && sorToDate != null) {
            builder.append(" AND sor.sorToDate <= :sorToDate ");
        }
        builder.append(" order by sor.sorId desc ");
        return builder.toString();
    }

}
