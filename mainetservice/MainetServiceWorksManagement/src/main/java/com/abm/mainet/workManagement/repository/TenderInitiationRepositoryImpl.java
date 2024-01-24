package com.abm.mainet.workManagement.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.workManagement.domain.TenderMasterEntity;

/**
 * @author hiren.poriya
 * @Since 05-Dec-2017
 */
@Repository
public class TenderInitiationRepositoryImpl extends AbstractDAO<Long> implements TenderInitiationRepositoryCustom {

    @SuppressWarnings("unchecked")
    @Override
    public List<TenderMasterEntity> searchTenderDetails(Long orgId, Long projId, String initiationNo, Date initiationDate,
            Long tenderCategory, String flag) {
        final Query query = entityManager
                .createQuery(buildDynamicQuery(projId, initiationNo, initiationDate, tenderCategory, flag));
        query.setParameter("orgId", orgId);

        if (projId != null && projId != 0) {
            query.setParameter("projId", projId);
        }
        if (initiationNo != null && !initiationNo.isEmpty()) {
            query.setParameter("initiationNo", initiationNo);
        }
        if (initiationDate != null) {
            query.setParameter("initiationDate", initiationDate);
        }
        if (tenderCategory != null && tenderCategory != 0) {
            query.setParameter("tenderCategory", tenderCategory);
        }

        return query.getResultList();
    }

    private String buildDynamicQuery(Long projId, String initiationNo, Date initiationDate, Long tenderCategory, String flag) {
        final StringBuilder builder = new StringBuilder();
        builder.append("SELECT td FROM TenderMasterEntity td WHERE td.orgId =:orgId ");
        if (projId != null && projId != 0) {
            builder.append(" AND td.projMasEntity.projId =:projId ");
        }
        if (initiationNo != null && !initiationNo.isEmpty()) {
            builder.append(" AND td.initiationNo =:initiationNo ");
        }
        if (initiationDate != null) {
            builder.append(" AND td.initiationDate >= :initiationDate ");
        }
        if (tenderCategory != null && tenderCategory != 0) {
            builder.append(" AND td.tenderCategory =:tenderCategory ");
        }
        if (flag != null && !flag.isEmpty()) {
            builder.append("AND td.status NOT IN('Y','N') ");
        }
        builder.append(" order by td.tndId desc ");
        return builder.toString();
    }

}
