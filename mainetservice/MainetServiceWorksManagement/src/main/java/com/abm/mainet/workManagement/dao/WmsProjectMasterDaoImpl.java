package com.abm.mainet.workManagement.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.workManagement.domain.TbWmsProjectMaster;

@Repository
public class WmsProjectMasterDaoImpl extends AbstractDAO<Long> implements WmsProjectMasterDao {

	private static final Logger LOGGER = Logger.getLogger(WmsProjectMasterDaoImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<TbWmsProjectMaster> getProjectMasterList(Long sourceCode, Long sourceName, String projectName,
			String projCode, long orgId, Long dpDeptId, Long projStatus) {

		List<TbWmsProjectMaster> entity = null;
		try {
			StringBuilder hql = new StringBuilder(
					"SELECT pm FROM TbWmsProjectMaster pm  where pm.orgId = :orgId and projActive= :projActive ");

			if (sourceCode != null) {
				hql.append(" and pm.schId.wmSchCodeId1 = :sourceCode ");
			}
			if (sourceName != null) {
				hql.append(" and pm.schId.wmSchCodeId2 = :sourceName ");
			}
			if (dpDeptId != null) {
				hql.append(" and pm.dpDeptId = :dpDeptId ");
			}
			if (projStatus != null) {
				hql.append(" and pm.projStatus = :projStatus ");
			}
			if (projectName != null && !projectName.isEmpty()) {
				hql.append(" and pm.projNameEng like :projNameEng");
			}
			if (projCode != null && !projCode.isEmpty()) {
				hql.append(" and pm.projCode like :projCode");
			}

			final Query query = createQuery(hql.toString());
			query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);
			query.setParameter(MainetConstants.WorksManagement.PROJ_ACTIVE, MainetConstants.IsDeleted.DELETE);

			if (sourceCode != null) {
				query.setParameter(MainetConstants.WorksManagement.SOURCE_CODE, sourceCode);

			}
			if (sourceName != null) {
				query.setParameter(MainetConstants.WorksManagement.SOURCE_NAME, sourceName);

			}
			if (dpDeptId != null) {
				query.setParameter(MainetConstants.WorksManagement.DEPT_ID, dpDeptId);

			}
			if (projStatus != null) {
				query.setParameter(MainetConstants.WorksManagement.PROJ_STATUS, projStatus);

			}
			if (projectName != null && !projectName.isEmpty()) {
				query.setParameter(MainetConstants.WorksManagement.PROJ_NAMEENG,
						MainetConstants.operator.PERCENTILE + projectName + MainetConstants.operator.PERCENTILE);
			}
			if (projCode != null && !projCode.isEmpty()) {
				query.setParameter(MainetConstants.WorksManagement.PROJ_CODE,
						MainetConstants.operator.PERCENTILE + projCode + MainetConstants.operator.PERCENTILE);
			}

			entity = query.getResultList();

		} catch (final Exception exception) {
			throw new FrameworkException("Exception occur in  getProjectMasterList() ", exception);

		}
		return entity;
	}

	@Override
	public List<Object[]> getAllProjectAssociationByMileStone(Long orgId, String mileStoneFlag) {
		StringBuilder stringBuilder = new StringBuilder(
				"select a.projId from TbWmsProjectMaster a where a.projId not in "
						+ " (select distinct ms.projectMaster.projId from MileStone ms where ms.mastDetailsEntity.workId is null "
						+ " and ms.mileStoneType=:mileStoneFlag and ms.orgId=:orgId) and a.orgId=:orgId ");

		final Query query = createQuery(stringBuilder.toString());
		query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);
		query.setParameter(MainetConstants.WorksManagement.mileStoneFlag, mileStoneFlag);
		@SuppressWarnings("unchecked")
		List<Object> projId = query.getResultList();

		List<Object[]> id = new ArrayList<>();
		for (Object lId : projId) {

			Object[] obj = new Object[] { lId, null };
			id.add(obj);
		}

		StringBuilder stringBuilder1 = new StringBuilder(
				" select a.projId, b.workId from TbWmsProjectMaster a, WorkDefinationEntity b "
						+ " where a.projId = b.projMasEntity.projId and (a.projId,b.workId) not in "
						+ " (select distinct ms.projectMaster.projId,ms.mastDetailsEntity.workId from MileStone ms where "
						+ " ms.mastDetailsEntity.workId is not null and "
						+ " ms.mileStoneType=:mileStoneFlag and ms.orgId=:orgId) and a.orgId=:orgId ");

		final Query query1 = createQuery(stringBuilder1.toString());
		query1.setParameter(MainetConstants.Common_Constant.ORGID, orgId);
		query1.setParameter(MainetConstants.WorksManagement.mileStoneFlag, mileStoneFlag);
		@SuppressWarnings("unchecked")
		List<Object[]> projId1 = query1.getResultList();

		id.addAll(projId1);
		return id;
	}

}
