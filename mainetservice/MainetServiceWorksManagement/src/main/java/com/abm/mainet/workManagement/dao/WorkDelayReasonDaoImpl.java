package com.abm.mainet.workManagement.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.domain.SequenceConfigMasterEntity;
import com.abm.mainet.workManagement.domain.WorkDelayReasonEntity;

@Repository
public class WorkDelayReasonDaoImpl extends AbstractDAO<WorkDelayReasonEntity> implements IWorkDelayReasonDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<WorkDelayReasonEntity> getAllData(Long orgId, Long projId, Long workId, Date dateClearance,
			String status) {

		List<WorkDelayReasonEntity> delayReasonEntities = null;

		try {

			StringBuilder hql = new StringBuilder("FROM WorkDelayReasonEntity sm WHERE sm.orgId =:orgId");

			if (projId != null && projId != 0) {
				hql.append(" and sm.projId =:projId");
			}

			if (workId != null && workId != 0) {
				hql.append(" and sm.workId =:workId");
			}

			if (dateClearance != null) {
				hql.append(" and sm.dateOccurance =:dateOccurance");
			}

			/* StringUtils.isNotBlank(seqStatus) && seqStatus!="0" */
			if (status != null && !status.isEmpty()) {
				hql.append(" and sm.status =:status");
			}

			final Query query = this.createQuery(hql.toString());

			query.setParameter("orgId", orgId);

			if (projId != null && projId != 0) {
				query.setParameter("projId", projId);
			}

			if (workId != null && workId != 0) {
				query.setParameter("workId", workId);
			}

			if (dateClearance != null) {
				query.setParameter("dateOccurance", dateClearance);

			}

			if (status != null && !status.isEmpty()) {
				query.setParameter("status", status);
			}

			delayReasonEntities = (List<WorkDelayReasonEntity>) query.getResultList();

		} catch (Exception e) {
			System.out.println("exception occure while getting the data::::=======" + e);
		}

		return delayReasonEntities;

	}

	@Override
	public WorkDelayReasonEntity getDelayResById(Long orgId, Long delResId) {

		WorkDelayReasonEntity delayReasonEntity = null;

		try {

			StringBuilder hql = new StringBuilder("FROM WorkDelayReasonEntity sm WHERE sm.orgId =:orgId");

			if (delResId != null && delResId != 0) {
				hql.append(" and sm.delResId =:delResId");
			}

			final Query query = this.createQuery(hql.toString());

			query.setParameter("orgId", orgId);

			if (delResId != null && delResId != 0) {
				query.setParameter("delResId", delResId);
			}

			delayReasonEntity = (WorkDelayReasonEntity) query.getSingleResult();

		} catch (Exception e) {
			System.out.println("exception occure while getting the data::::=======" + e);
		}

		return delayReasonEntity;

	}

}
