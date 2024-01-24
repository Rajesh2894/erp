package com.abm.mainet.eip.dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.domain.PageMaster;

@Repository
public class HitCounterDao extends AbstractDAO<PageMaster> implements IHitCounterDao {

	private static final Logger LOGGER = Logger.getLogger(HitCounterDao.class);

	private static final PageMaster PageMaster = null;

	@Override
	public PageMaster getPageMaster(final Long pageId) {

		try {
			final Query query = createQuery("select distinct m  from  PageMaster m  where m.org = :pageId ");
			query.setFirstResult(0);
			query.setMaxResults(1);
			query.setParameter("pageId", pageId);

			return (PageMaster) query.getSingleResult();
		}

		catch (final NoResultException nre) {
			LOGGER.error(MainetConstants.ERROR_OCCURED, nre);
			return PageMaster;
		}
	}

	@Override
	public void updateCount(final PageMaster counter) {
		try {
			entityManager.merge(counter);
		} catch (final Exception e) {
			LOGGER.error("Error occurred while updating page count", e);
		}
	}

	@Override
	public Long getFinalCountOfHits(final Long pageId) {
		Long count = 0L;
		final Query query = createQuery("select phm.totalCount from PageMaster phm where phm.org=:pageId ");
		query.setFirstResult(0);
		query.setMaxResults(1);
		query.setParameter("pageId", pageId);
		try {
			count = (Long) query.getSingleResult();
		} catch (Exception ex) {
			LOGGER.warn("Error occurred whilegetFinalCountOfHits count", ex);
		}
		return count;
	}

	@Override
	public boolean pageIdExist(final Long pageId) {
		final Query query = createQuery("select count(*) from PageMaster pm  where pm.org=:pageId");
		query.setParameter("pageId", pageId);
		final Long count = (Long) query.getSingleResult();
		return count > 0L ? true : false;
	}

	@Override
	public boolean updateMyMarathiCount(Long orgId) {

		int count = 0;
		final Query query = createQuery(
				"update PageMaster set totalCount=totalCount+1 where pageName='MarathiCount' AND org=:orgId");
		query.setParameter("orgId", orgId);
		try {
			count = query.executeUpdate();
			
		} catch (Exception ex) {
			LOGGER.warn("Error occurred updateMyMarathiCount count", ex);
		}
		return count > 0L ? true : false;
	}
	@Override
	public Long getMarathiCount(Long orgId) {
		Long count = 0L;
		try {
			final Query query = createQuery("select  m  from  PageMaster m  where m.org = :pageId and pageName='MarathiCount'");
			query.setFirstResult(0);
			query.setMaxResults(1);
			query.setParameter("pageId", orgId);

			PageMaster master= (PageMaster) query.getSingleResult();
			return master.getTotalCount();
		}

		catch (final NoResultException nre) {
			LOGGER.error(MainetConstants.ERROR_OCCURED, nre);
			
		}return count;
	}

	
}
