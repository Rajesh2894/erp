package com.abm.mainet.workManagement.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.workManagement.domain.SchemeMaster;

/**
 * @author vishwajeet.kumar
 * @since 5 dec 2017
 */
@Repository
public class SchemeMasterDaoImpl extends AbstractDAO<Long> implements SchemeMasterDao {

	/**
	 * save and update scheme master details in database
	 *
	 */

	@Override
	public SchemeMaster saveSchemeMaster(SchemeMaster masterEntity) {

		return entityManager.merge(masterEntity);

	}

	@Override
	public SchemeMaster getSchemeMasterBySchemeId(Long wmSchId) {

		return entityManager.find(SchemeMaster.class, wmSchId);
	}

	/**
	 * get all scheme master list on condition start date to end date and name
	 * 
	 * @param startDate
	 * @param endDate
	 * @param wmSchNameEng
	 * @param orgId
	 */

	@SuppressWarnings("unchecked")
	@Override
	public List<SchemeMaster> getSchemeMasterList(Long sourceCode, Long sourceName, String wmSchNameEng, long orgId) {

		List<SchemeMaster> entityList = null;
		try {

			StringBuilder jpaQuery = new StringBuilder(
					"SELECT sm FROM SchemeMaster sm  where sm.orgId = :orgId and sm.schemeActive = :schemeActive");

			if (wmSchNameEng != null && !wmSchNameEng.isEmpty()) {
				jpaQuery.append(" and sm.wmSchNameEng like :wmSchNameEng");

			}

			if (sourceCode != null) {
				jpaQuery.append(" and sm.wmSchCodeId1 = :sourceCode ");

			}

			if (sourceName != null) {
				jpaQuery.append(" and sm.wmSchCodeId2 = :sourceName ");

			}

			final Query hqlQuery = createQuery(jpaQuery.toString());

			hqlQuery.setParameter(MainetConstants.Common_Constant.ORGID, orgId);
			hqlQuery.setParameter(MainetConstants.WorksManagement.SCHEME_ACTIVE, MainetConstants.Common_Constant.YES);
			if (sourceCode != null) {
				hqlQuery.setParameter(MainetConstants.WorksManagement.SOURCE_CODE, sourceCode);

			}
			if (sourceName != null) {
				hqlQuery.setParameter(MainetConstants.WorksManagement.SOURCE_NAME, sourceName);

			}
			if (wmSchNameEng != null && !wmSchNameEng.isEmpty()) {
				hqlQuery.setParameter(MainetConstants.WorksManagement.SCH_NAME,
						MainetConstants.operator.PERCENTILE + wmSchNameEng + MainetConstants.operator.PERCENTILE);

			}

			entityList = hqlQuery.getResultList();

		} catch (Exception e) {
			throw new FrameworkException(e);
		}
		return entityList;
	}

	/**
	 * check for active duplicate scheme code
	 * 
	 * @param wmSchCode
	 */

	@SuppressWarnings("unchecked")
	@Override
	public String checkDuplicateSchemeCode(String wmSchCode, Long orgId) {

		String flag = MainetConstants.Common_Constant.NO;
		StringBuilder builder = new StringBuilder(
				"SELECT sm FROM SchemeMaster sm where UPPER(sm.wmSchCode) = :wmSchCode and sm.orgId= :orgId");
		final Query query = createQuery(builder.toString());
		query.setParameter(MainetConstants.WorksManagement.SCHEME_CODE, wmSchCode);
		query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);

		List<SchemeMaster> schemeMastersList = query.getResultList();
		if (!schemeMastersList.isEmpty()) {
			flag = MainetConstants.Common_Constant.YES;
		}
		return flag;

	}

	/**
	 * method used for inactive child records of scheme master details
	 * 
	 * @param updatedBy
	 * @param schDActive
	 * @param schDetId
	 */

	@Modifying
	@Override
	@Transactional
	public void inactiveSchemeMasterChildRecords(Long updatedBy, List<Long> removeChildIds) {
		StringBuilder builder = new StringBuilder(
				"UPDATE  SchemeMastDetails smd set smd.schDActive = :schDActive, smd.updatedBy = :updatedBy  WHERE smd.schDetId in (:schDetId) ");

		try {
			final Query query = createQuery(builder.toString());
			query.setParameter(MainetConstants.WorksManagement.schDActive, MainetConstants.Common_Constant.NO);
			query.setParameter(MainetConstants.WorksManagement.UPDATED_BY, updatedBy);
			query.setParameter(MainetConstants.WorksManagement.schDetId, removeChildIds);

			query.executeUpdate();
		} catch (Exception e) {
			System.out.println("Exception" + e);
			throw new FrameworkException(e);
		}
	}

}
