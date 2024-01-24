package com.abm.mainet.workManagement.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.workManagement.domain.TbWmsError;
import com.abm.mainet.workManagement.domain.TbWmsMaterialMaster;

@Repository
public class WmsMaterialMasterDaoImpl extends AbstractDAO<Long> implements WmsMaterialMasterDao {

	@Override
	public void saveUpdateMaterialList(TbWmsMaterialMaster entity) {
		entityManager.merge(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TbWmsMaterialMaster> getMaterialListBySorId(Long sorId) {
		List<TbWmsMaterialMaster> entity = null;
		StringBuilder hql = new StringBuilder(
				"SELECT mm FROM TbWmsMaterialMaster mm  where mm.sorId.sorId = :sorId and maActive= :projActive ");

		final Query query = createQuery(hql.toString());
		query.setParameter(MainetConstants.WorksManagement.PROJ_ACTIVE, MainetConstants.IsDeleted.DELETE);
		query.setParameter(MainetConstants.WorksManagement.SOR_ID, sorId);
		entity = (List<TbWmsMaterialMaster>) query.getResultList();

		return entity;
	}

	@Override
	public void updateDeletedFlag(List<Long> deletedMaterialId) {

		final StringBuilder hql = new StringBuilder(
				"update TbWmsMaterialMaster set maActive = :projActive  where maId in :deletedMaterialId");
		final Query query = createQuery(hql.toString());
		query.setParameter(MainetConstants.WorksManagement.PROJ_ACTIVE, MainetConstants.IsDeleted.NOT_DELETE);
		query.setParameter("deletedMaterialId", deletedMaterialId);
		query.executeUpdate();

	}

	@Override
	public void deleteMaterialById(Long sorId) {
		final StringBuilder hql = new StringBuilder(
				"update TbWmsMaterialMaster set maActive = :projActive  where sorId.sorId =:sorId");
		final Query query = createQuery(hql.toString());
		query.setParameter(MainetConstants.WorksManagement.PROJ_ACTIVE, MainetConstants.IsDeleted.NOT_DELETE);
		query.setParameter(MainetConstants.WorksManagement.SOR_ID, sorId);
		query.executeUpdate();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TbWmsMaterialMaster> findAllActiveMaterialBySorMas(long orgId) {
		List<TbWmsMaterialMaster> entity = null;
		StringBuilder hql = new StringBuilder(
				"SELECT DISTINCT mm FROM TbWmsMaterialMaster mm  where  maActive= :projActive and  orgId= :orgId");
		final Query query = createQuery(hql.toString());
		query.setParameter(MainetConstants.WorksManagement.PROJ_ACTIVE, MainetConstants.IsDeleted.DELETE);
		query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);
		entity = (List<TbWmsMaterialMaster>) query.getResultList();
		return entity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String checkDuplicateExcelData(Long maTypeId, String maItemNo, Long sorId,long orgId) {
		List<TbWmsMaterialMaster> entity = null;
		String duplicateStatus = MainetConstants.IsDeleted.NOT_DELETE;
		StringBuilder hql = new StringBuilder(
				"SELECT DISTINCT mm FROM TbWmsMaterialMaster mm  where  maActive= :projActive and sorId.sorId= :sorId and orgId= :orgId and maTypeId=:maTypeId and maItemNo=:maItemNo");
		final Query query = createQuery(hql.toString());
		query.setParameter(MainetConstants.WorksManagement.PROJ_ACTIVE, MainetConstants.IsDeleted.DELETE);
		query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);
		query.setParameter(MainetConstants.WorksManagement.MA_TYPE_ID, maTypeId);
		query.setParameter(MainetConstants.WorksManagement.MA_ITEM_NO, maItemNo);
		query.setParameter(MainetConstants.WorksManagement.SOR_ID, sorId);
		entity = (List<TbWmsMaterialMaster>) query.getResultList();
		if (entity != null && !entity.isEmpty())
			duplicateStatus = MainetConstants.IsDeleted.DELETE;
		return duplicateStatus;
	}

	@Override
	public void saveErrorDetails(TbWmsError entity) {
		entityManager.merge(entity);

	}

	@Override
	public void deleteErrorLog(Long orgId, String masterType) {
		StringBuilder hql = new StringBuilder("DELETE FROM TbWmsError d WHERE d.errFlag= :errFlag and d.orgId= :orgId");
		final Query query = createQuery(hql.toString());
		query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);
		query.setParameter(MainetConstants.WorksManagement.ERR_FLAG, masterType);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TbWmsError> getErrorLog(Long orgId, String masterType) {
		List<TbWmsError> entity = null;
		StringBuilder hql = new StringBuilder(
				"SELECT mm FROM TbWmsError mm  where mm.orgId = :orgId and mm.errFlag= :errFlag");
		final Query query = createQuery(hql.toString());
		query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);
		query.setParameter(MainetConstants.WorksManagement.ERR_FLAG, masterType);
		entity = (List<TbWmsError>) query.getResultList();

		return entity;
	}

}
