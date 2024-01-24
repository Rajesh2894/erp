/**
 * 
 */
package com.abm.mainet.water.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.water.domain.PlumberMaster;

/**
 * @author Saiprasad.Vengurlekar
 *
 */
@Repository
public class DuplicatePlumberLicenseRepositoryImpl extends AbstractDAO<PlumberMaster>
		implements DuplicatePlumberLicenseRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(DuplicatePlumberLicenseRepositoryImpl.class);

	/*@Override
	public TbDuplicatePlumberLicenseEntity saveDuplicatePlumberLicenseDetails(TbDuplicatePlumberLicenseEntity entity) {
		entityManager.persist(entity);
		return entity;
	}*/

	@Override
	public Long findDuplicatePlumberIdByApplicationNumber(Long applicationNumber, Long orgId) {
		Long data = 0l;
		final Query query = createQuery(
				"select p.plumId from TbDuplicatePlumberLicenseEntity p WHERE p.apmApplicationId=?1 and p.orgId=?2");
		query.setParameter(1, applicationNumber);
		query.setParameter(2, orgId);
		try {
			String resultList = query.getSingleResult().toString();
			data = Long.parseLong(resultList);
		} catch (final Exception e) {
			LOGGER.error("Exception in Serching Duplicate Plumber Detail Application Number:" + applicationNumber, e);
		}
		return data;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PlumberMaster> getPlumberDetailsByPlumId(Long plumId, Long orgid) {
		List<PlumberMaster> result = null;
		final Query query = createQuery("select p from PlumberMaster p WHERE p.plumId=?1 and p.orgid=?2");
		query.setParameter(1, plumId);
		query.setParameter(2, orgid);

		try {
			result = query.getResultList();
		} catch (final Exception e) {
			LOGGER.error("Exception in Serching  Plumber Detail Plumber Number:" + plumId, e);
		}

		return result;
	}

	@Override
	public void updateApprovalStatus(Long applicationNumber, String appovalStatus, Date date) {
		try {
			final Query query = entityManager
					.createQuery("UPDATE TbDuplicatePlumberLicenseEntity t set t.approvalFlag=? , t.approvalDate=?"
							+ "WHERE t.apmApplicationId = ?");

			query.setParameter(1, appovalStatus);
			query.setParameter(2, date);
			query.setParameter(3, applicationNumber);

			query.executeUpdate();
		} catch (final Exception e) {
			LOGGER.error("Exception in Updating Duplicate Plumber Detail" + applicationNumber, e);
		}

	}

	/*@Override
	public TbDuplicatePlumberLicenseEntity findLatestDuplicateLicenseByPlumId(Long plumId, Long orgid) {
		final Query query = createQuery(
				"select p from TbDuplicatePlumberLicenseEntity p WHERE p.plumId=?1 and p.orgId=?2 order by p.appPlumId DESC");
		query.setParameter(1, plumId);
		query.setParameter(2, orgid);
		query.setMaxResults(1);
		List<TbDuplicatePlumberLicenseEntity> result = query.getResultList();
		if (result != null && !result.isEmpty()) {
			return result.get(0);
		} else {
			return null;
		}

	}*/

	@SuppressWarnings("unchecked")
	@Override
	public List<PlumberMaster> getPlumberDetailsByApplicationId(Long applicationNumber, Long orgId) {
		List<PlumberMaster> result = null;
		final Query query = createQuery(
				"select p from PlumberMaster p  JOIN TbDuplicatePlumberLicenseEntity p1 ON p.plumId=p1.plumId where p1.apmApplicationId=?1 and p1.orgId=?2");
		query.setParameter(1, applicationNumber);
		query.setParameter(2, orgId);
		try {
			result = query.getResultList();
		} catch (final Exception e) {
			LOGGER.error("Exception in Serching  Plumber Detail application Number:" + applicationNumber, e);
		}
		return result;
	}

	@Override
	public Object getchecklistStatusInApplicationMaster(Long apmApplicationId, Long orgid) {

		Object results = null;
		String qlString = "SELECT a.apmChklstVrfyFlag FROM TbCfcApplicationMstEntity a WHERE a.apmApplicationId=?";
		final Query query = createQuery(qlString);
		query.setParameter(1, apmApplicationId);
		/* query.setParameter(2, orgid); */
		try {
			results = (Object) query.getSingleResult();
		} catch (final Exception e) {
			LOGGER.error("Exception in Searching Application Number In Application Master :" + apmApplicationId, e);
		}
		return results;
	}

}
