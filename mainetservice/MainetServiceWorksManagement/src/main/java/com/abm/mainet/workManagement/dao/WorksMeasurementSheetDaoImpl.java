package com.abm.mainet.workManagement.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.workManagement.domain.WorkEstimateMeasureDetails;
import com.abm.mainet.workManagement.domain.WorkEstimateMeasureDetailsHistory;

@Repository
public class WorksMeasurementSheetDaoImpl extends AbstractDAO<Long> implements WorksMeasurementSheetDao {

	@Override
	public WorkEstimateMeasureDetails saveUpdateEstimateMeasureDetails(WorkEstimateMeasureDetails entity) {
		WorkEstimateMeasureDetails details = entityManager.merge(entity);
		return details;
	}

	@Override
	public void updateDeletedFlag(List<Long> removeIds) {
		final StringBuilder hql = new StringBuilder(
				"update WorkEstimateMeasureDetails set meMentActive = :meMentActive  where meMentId in :removeIds");
		final Query query = createQuery(hql.toString());
		query.setParameter(MainetConstants.WorksManagement.MEASUREMENT_ACTIVE, MainetConstants.IsDeleted.NOT_DELETE);
		query.setParameter(MainetConstants.WorksManagement.REMOVED_IDS, removeIds);
		query.executeUpdate();

	}

	@Override
	public BigDecimal calculateTotalEstimatedAmountByWorkId(Long workEId) {
		final StringBuilder hql = new StringBuilder(
				"select Sum(we.meMentToltal) from WorkEstimateMeasureDetails we where meMentActive = :meMentActive  and workEstemateId =:workEId");
		final Query query = createQuery(hql.toString());
		query.setParameter(MainetConstants.WorksManagement.MEASUREMENT_ACTIVE, MainetConstants.IsDeleted.DELETE);
		query.setParameter(MainetConstants.WorksManagement.WORK_EID, workEId);
		BigDecimal totalEstimate = (BigDecimal) query.getSingleResult();
		return totalEstimate;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WorkEstimateMeasureDetails> getWorkEstimateDetailsByWorkEId(Long workEId) {
		StringBuilder hql = new StringBuilder(
				"SELECT pm FROM WorkEstimateMeasureDetails pm  where pm.workEstemateId = :workEId and meMentActive= :meMentActive");
		final Query query = createQuery(hql.toString());
		query.setParameter(MainetConstants.WorksManagement.WORK_EID, workEId);
		query.setParameter(MainetConstants.WorksManagement.MEASUREMENT_ACTIVE, MainetConstants.IsDeleted.DELETE);
		return (List<WorkEstimateMeasureDetails>) query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WorkEstimateMeasureDetailsHistory> getAuditMeasuremnetByWorkEId(Long workEId) {
		StringBuilder hql = new StringBuilder(
				"SELECT we FROM WorkEstimateMeasureDetailsHistory we  where we.workEstemateId = :workEId ");
		final Query query = createQuery(hql.toString());
		query.setParameter(MainetConstants.WorksManagement.WORK_EID, workEId);
		return (List<WorkEstimateMeasureDetailsHistory>) query.getResultList();
	}

	@Override
	public void updateUtilizationNoByMeId(Long nosUtilize, Long meMentId) {
		final StringBuilder hql = new StringBuilder(
				"update WorkEstimateMeasureDetails set meNoUtl = :nosUtilize  where meMentId = :meMentId");
		final Query query = createQuery(hql.toString());
		query.setParameter(MainetConstants.WorksManagement.NOS_UTL, nosUtilize);
		query.setParameter(MainetConstants.WorksManagement.MEASUREMENT_ID, meMentId);
		query.executeUpdate();
	}

	@Override
	public WorkEstimateMeasureDetails findMeasureDetailsById(Long meMentId) {
		final StringBuilder hql = new StringBuilder(
				"SELECT pm FROM WorkEstimateMeasureDetails pm  where meMentId = :meMentId and meMentActive= :meMentActive");
		final Query query = createQuery(hql.toString());
		query.setParameter(MainetConstants.WorksManagement.MEASUREMENT_ACTIVE, MainetConstants.IsDeleted.DELETE);
		query.setParameter(MainetConstants.WorksManagement.MEASUREMENT_ID, meMentId);
		return (WorkEstimateMeasureDetails) query.getSingleResult();
	}

}
