package com.abm.mainet.water.dao;

import java.util.List;

import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.water.constant.QueryConstants;
import com.abm.mainet.water.domain.AdditionalOwnerInfo;
import com.abm.mainet.water.domain.ChangeOfOwnerMas;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;

/**
 * @author Rahul.Yadav
 *
 */
@Repository
public class ChangeOfOwnershipRepositoryImpl extends AbstractDAO<ChangeOfOwnerMas>
		implements ChangeOfOwnershipRepository {

	private static final Logger lOGGER = LoggerFactory.getLogger(ChangeOfOwnershipRepositoryImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainetservice.rest.water.dao.ChangeOfOwnershipRepository#
	 * getConnectionData(long)
	 */
	@Override
	public List<TbKCsmrInfoMH> getConnectionData(final long orgnId, final Long empId) {
		final Query query = entityManager
				.createQuery("select m from TbKCsmrInfoMH m where m.orgId=?1 and m.userId.empId=?2");
		query.setParameter(1, orgnId);
		query.setParameter(2, empId);
		return query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainetservice.rest.water.dao.ChangeOfOwnershipRepository#saveNewData(
	 * com.abm.mainetservice.rest.water.entity. TBChangeOfOwnershipMas)
	 */
	@Override
	public void saveNewData(final ChangeOfOwnerMas master) {
		entityManager.persist(master);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainetservice.rest.water.dao.ChangeOfOwnershipRepository#
	 * findWardZoneFromConnectionDetail(long, long, long)
	 */
	@Override
	public long findCsidnFromChangeOfOwner(final long applicationId) {

		final Query query = entityManager.createQuery(QueryConstants.ChangeOwnerQuery);
		query.setParameter(1, applicationId);
		final Object csIdn = query.getSingleResult();

		return (long) csIdn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainetservice.rest.water.dao.ChangeOfOwnershipRepository#
	 * findWardZoneBlockByCsidn(long)
	 */
	@Override
	public TbKCsmrInfoMH findOldOwnerConnectionInfoByCsidn(final long csidn, final long orgId) {
		TbKCsmrInfoMH entity = new TbKCsmrInfoMH();
		try {
			final Query query = entityManager.createQuery("FROM TbKCsmrInfoMH m WHERE m.csIdn=?1 AND m.orgId=?2");
			query.setParameter(1, csidn);
			query.setParameter(2, orgId);
			entity = (TbKCsmrInfoMH) query.getSingleResult();

		} catch (Exception e) {

		}

		return entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.water.dao.ChangeOfOwnershipRepository#
	 * fetchWaterConnectionOwnerDetail(long)
	 */
	@Override
	public ChangeOfOwnerMas fetchWaterConnectionOwnerDetail(final long applicationId) {

		final Query query = entityManager.createQuery("FROM ChangeOfOwnerMas m WHERE m.apmApplicationId=?1");
		query.setParameter(1, applicationId);

		return (ChangeOfOwnerMas) query.getSingleResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.water.dao.ChangeOfOwnershipRepository#getConnectionData(java.
	 * lang.String, long)
	 */
	@Override
	public TbKCsmrInfoMH getConnectionData(final String connectionNo, final long orgnId) {
		TbKCsmrInfoMH master = null;
		final Query query = entityManager.createQuery("select m from TbKCsmrInfoMH m where m.csCcn=?1 and m.orgId=?2");
		query.setParameter(1, connectionNo);
		query.setParameter(2, orgnId);
		try {
			master = (TbKCsmrInfoMH) query.getSingleResult();
		} catch (Exception ex) {
			lOGGER.warn("Error in getConnectionData() ", ex);
		}
		return master;
	}

	@Override
	public void updateChangeOfOwnershipMas(final ChangeOfOwnerMas mas) {

		entityManager.merge(mas);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.water.dao.ChangeOfOwnershipRepository#saveAdditionalOwners(
	 * java.util.List)
	 */
	@Override
	public void saveAdditionalOwners(final List<AdditionalOwnerInfo> additionalOwnerList) {
		/* final EntityManager em = entityManager; */
		for (final AdditionalOwnerInfo additionalOwnerInfo : additionalOwnerList) {
			entityManager.merge(additionalOwnerInfo);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.water.dao.ChangeOfOwnershipRepository#fetchAdditionalOwners(
	 * long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AdditionalOwnerInfo> fetchAdditionalOwners(final long applicationId) {

		final Query query = entityManager.createQuery("FROM AdditionalOwnerInfo info WHERE info.apmApplicationId=?1");
		query.setParameter(1, applicationId);

		return query.getResultList();
	}

	@Override
	@Transactional(readOnly = true)
	public String enquiryConnectionNo(final String connectionNo) {
		final ApplicationSession appSession = ApplicationSession.getInstance();
		String enquiryMsg = MainetConstants.BLANK;
		List<?> csCcn = null;
		final Query query1 = entityManager.createQuery("SELECT m.csCcn FROM TbKCsmrInfoMH m  WHERE m.csCcn=:csCcn");
		query1.setParameter("csCcn", connectionNo);
		csCcn = query1.getResultList();
		// if connection no exist
		if ((csCcn != null) && !csCcn.isEmpty()) {
			final Query query2 = entityManager.createQuery(
					"SELECT m.csCcn FROM TbKCsmrInfoMH m  WHERE m.csCcn=:csCcn AND m.csIdn NOT IN (SELECT cm.csIdn FROM ChangeOfOwnerMas cm WHERE cm.csIdn=m.csIdn)");
			query2.setParameter("csCcn", connectionNo);
			csCcn = query2.getResultList();
			// if change for ownership request made first time
			if ((csCcn != null) && !csCcn.isEmpty()) {
				enquiryMsg = MainetConstants.FlagY;
			} else {
				// already request made for ownership change then check status of application
				final Query query3 = entityManager.createQuery(
						"SELECT a.csCcn FROM TbKCsmrInfoMH a, ChangeOfOwnerMas b, TbCfcApplicationMstEntity c "
								+ "WHERE a.csCcn=:csCcn AND a.csIdn=b.csIdn AND c.apmApplicationId=b.apmApplicationId  AND c.apmApplClosedFlag=:apmApplClosedFlag "
								+ "AND c.apmApprovedBy IS NOT NULL");
				query3.setParameter("csCcn", connectionNo);
				query3.setParameter("apmApplClosedFlag", MainetConstants.Common_Constant.YES);
				csCcn = query3.getResultList();
				if ((csCcn != null) && !csCcn.isEmpty()) {
					enquiryMsg = MainetConstants.Common_Constant.YES;
				} else {
					enquiryMsg = appSession.getMessage("changeofowner.searchCriteria") + connectionNo+" "
							+ appSession.getMessage("changeofowner.searchCriteria.append");
				}

			}
		} else {
			enquiryMsg = appSession.getMessage("water.DeptCOO.ConNo") + MainetConstants.WHITE_SPACE + connectionNo+" "
					+ appSession.getMessage("changeofowner.searchCriteria.noValid");
		}

		return enquiryMsg;
	}
}
