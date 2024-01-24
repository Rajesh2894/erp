package com.abm.mainet.workManagement.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.workManagement.domain.WorkDefinationEntity;
import com.abm.mainet.workManagement.domain.WorkDefinitionSancDet;
import com.abm.mainet.workManagement.dto.WorkDefinitionDto;

/**
 * @author hiren.poriya
 * @Since 12-Feb-2018
 */
@Repository
public class WorkDefinitionDaoImpl extends AbstractDAO<Long> implements WorkDefinitionDao {

	private static final Logger LOGGER = Logger.getLogger(WorkDefinitionDaoImpl.class);

	@Override
	public WorkDefinationEntity createWorkDefination(WorkDefinationEntity workDefEntity) {
		return entityManager.merge(workDefEntity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WorkDefinationEntity> findAllWorkDefinitionsByOrgId(Long orgId) {
		StringBuilder hql = new StringBuilder(
				"SELECT wd FROM WorkDefinationEntity wd WHERE wd.orgId =:orgId ORDER BY workId DESC");
		final Query query = createQuery(hql.toString());
		query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WorkDefinationEntity> filterWorkDefRecords(Long orgId, WorkDefinitionDto workDefDto) {
		final Query query = createQuery(buildDynamicQuery(workDefDto));
		query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);

		if (workDefDto.getWorkcode() != null && !workDefDto.getWorkcode().isEmpty()) {
			query.setParameter(MainetConstants.WorksManagement.WORKCODE, workDefDto.getWorkcode());
		}
		if (workDefDto.getWorkName() != null && !workDefDto.getWorkName().isEmpty()) {
			query.setParameter(MainetConstants.WorksManagement.WORK_NAME, workDefDto.getWorkName());
		}
		if (workDefDto.getWorkStartDate() != null && workDefDto.getWorkEndDate() != null) {
			query.setParameter(MainetConstants.WorksManagement.WORK_STARTDATE, workDefDto.getWorkStartDate());
			query.setParameter(MainetConstants.WorksManagement.WORK_ENDDATE, workDefDto.getWorkEndDate());
		}
		if (workDefDto.getWorkStartDate() != null && workDefDto.getWorkStartDate() == null) {
			query.setParameter(MainetConstants.WorksManagement.WORK_STARTDATE, workDefDto.getWorkStartDate());
		}
		if (workDefDto.getWorkStartDate() == null && workDefDto.getWorkEndDate() != null) {
			query.setParameter(MainetConstants.WorksManagement.WORK_ENDDATE, workDefDto.getWorkEndDate());
		}
		if (workDefDto.getProjId() != null) {
			query.setParameter(MainetConstants.WorksManagement.PROJ_ID, workDefDto.getProjId());
		}
		if (workDefDto.getWorkType() != null && workDefDto.getWorkType() != 0) {
			query.setParameter(MainetConstants.WorksManagement.WORK_TYPE, workDefDto.getWorkType());
		}
		if (workDefDto.getWorkProjPhase() != null && workDefDto.getWorkProjPhase() != 0) {
			query.setParameter(MainetConstants.WorksManagement.WORK_PROJ_PHASE, workDefDto.getWorkProjPhase());
		}

		return query.getResultList();
	}

	private String buildDynamicQuery(WorkDefinitionDto workDefDto) {
		StringBuilder hql = new StringBuilder("SELECT wd FROM WorkDefinationEntity wd WHERE wd.orgId =:orgId ");
		if (workDefDto.getWorkcode() != null && !workDefDto.getWorkcode().isEmpty()) {
			hql.append(" AND wd.workcode =:workcode ");
		}
		if (workDefDto.getWorkName() != null && !workDefDto.getWorkName().isEmpty()) {
			hql.append(" AND wd.workName =:workName ");
		}
		if (workDefDto.getWorkStartDate() != null && workDefDto.getWorkEndDate() != null) {
			hql.append(" AND wd.workStartDate >= :workStartDate AND wd.workEndDate <= :workEndDate  ");
		}
		if (workDefDto.getWorkStartDate() != null && workDefDto.getWorkEndDate() == null) {
			hql.append(" AND wd.workStartDate =:workStartDate ");
		}
		if (workDefDto.getWorkStartDate() == null && workDefDto.getWorkEndDate() != null) {
			hql.append(" AND wd.workEndDate =:workEndDate ");
		}
		if (workDefDto.getProjId() != null) {
			hql.append(" AND wd.projMasEntity.projId =:projId ");
		}
		if (workDefDto.getWorkType() != null && workDefDto.getWorkType() != 0) {
			hql.append(" AND wd.workType =:workType ");
		}
		if (workDefDto.getWorkProjPhase() != null && workDefDto.getWorkProjPhase() != 0) {
			hql.append(" AND wd.workProjPhase =:workProjPhase ");
		}
		hql.append(" ORDER BY workId DESC ");
		return hql.toString();
	}

	@Override
	public WorkDefinationEntity findAllWorkDefinitionById(Long workId) {
		return entityManager.find(WorkDefinationEntity.class, workId);
	}

	@Override
	@Transactional
	public void iactiveAssetsByIds(List<Long> removeAssetIdList, Long updatedBy) {
		final Query query = createQuery(
				"UPDATE WorkDefinationAssetInfoEntity a SET a.assetRecStatus ='N', a.updatedDate=CURRENT_DATE,a.updatedBy=?1 where a.workAssetId in ?2");
		query.setParameter(1, updatedBy);
		query.setParameter(2, removeAssetIdList);
		query.executeUpdate();
	}

	@Override
	@Transactional
	public void iactiveYearsByIds(List<Long> removeYearIdList, Long updatedBy) {
		final Query query = createQuery(
				"UPDATE WorkDefinationYearDetEntity y SET y.yeActive ='N',y.updatedDate=CURRENT_DATE, y.updatedBy=?1 where y.yearId in ?2");
		query.setParameter(1, updatedBy);
		query.setParameter(2, removeYearIdList);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WorkDefinationEntity> findAllWorkDefinitionsExcludedWork(long orgid, List<Long> workIdList,
			Long projId) {
		StringBuilder hql = new StringBuilder(
				"SELECT wd FROM WorkDefinationEntity wd WHERE wd.orgId =:orgId and wd.workId not in :workIdList ");
		if (projId != null)
			hql.append(" and wd.projMasEntity.projId =:projId");
		final Query query = createQuery(hql.toString());
		query.setParameter(MainetConstants.Common_Constant.ORGID, orgid);
		query.setParameter(MainetConstants.WorksManagement.WORK_IDLIST, workIdList);
		if (projId != null) {
			query.setParameter(MainetConstants.WorksManagement.PROJ_ID, projId);
		}
		return query.getResultList();
	}

	@Override
	public void updateWorkDefinationMode(Long workDefination, String flag) {
		final Query query = createQuery("UPDATE WorkDefinationEntity a SET a.workStatus =?1 where a.workId =?2");
		query.setParameter(1, flag);
		query.setParameter(2, workDefination);
		query.executeUpdate();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WorkDefinationEntity> getWorkDefinationBySearch(Long orgId, String estimateNo, Long projectId,
			Long workName, String status, Date fromDate, Date toDate) {

		StringBuilder hql = new StringBuilder("SELECT wd FROM WorkDefinationEntity wd WHERE wd.orgId =:orgId ");
		if (estimateNo != null && !estimateNo.isEmpty()) {
			hql.append(" AND wd.workcode =:workcode ");
		}
		if (workName != null) {
			hql.append(" AND wd.workId =:workId ");
		}
		if (fromDate != null && toDate != null) {
			hql.append(" AND wd.workStartDate >= :workStartDate AND wd.workEndDate <= :workEndDate  ");
		}
		if (fromDate != null && toDate == null) {
			hql.append(" AND wd.workStartDate =:workStartDate ");
		}
		if (fromDate == null && toDate != null) {
			hql.append(" AND wd.workEndDate =:workEndDate ");
		}
		if (projectId != null) {
			hql.append(" AND wd.projMasEntity.projId =:projId ");
		}
		if (status != null && !status.isEmpty()) {
			hql.append(" AND wd.workStatus =:workStatus ");
		}

		hql.append(" AND wd.workStatus is not null ORDER BY workId DESC ");

		final Query query = createQuery((hql.toString()));

		query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);

		if (estimateNo != null && !estimateNo.isEmpty()) {
			query.setParameter(MainetConstants.WorksManagement.WORKCODE, estimateNo);
		}
		if (workName != null) {
			query.setParameter(MainetConstants.WorksManagement.WORK_ID, workName);
		}
		if (fromDate != null && toDate != null) {
			query.setParameter(MainetConstants.WorksManagement.WORK_STARTDATE, fromDate);
			query.setParameter(MainetConstants.WorksManagement.WORK_ENDDATE, toDate);
		}
		if (fromDate != null && toDate == null) {
			query.setParameter(MainetConstants.WorksManagement.WORK_STARTDATE, fromDate);
		}
		if (fromDate == null && toDate != null) {
			query.setParameter(MainetConstants.WorksManagement.WORK_ENDDATE, toDate);
		}
		if (projectId != null) {
			query.setParameter(MainetConstants.WorksManagement.PROJ_ID, projectId);
		}
		if (status != null && !status.isEmpty()) {
			query.setParameter(MainetConstants.WorksManagement.WORK_STATUS, status);
		}

		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<WorkDefinationEntity> filterWorkDefRecordsByProjId(Long orgId, Long projId) {
		List<WorkDefinationEntity> entityList = null;
		try {
			StringBuilder hql = new StringBuilder(
					"SELECT wd FROM WorkDefinationEntity wd WHERE wd.orgId =:orgId and wd.projMasEntity.projId =:projId");
			final Query query = createQuery(hql.toString());
			query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);
			query.setParameter(MainetConstants.WorksManagement.PROJ_ID, projId);
			entityList = (List<WorkDefinationEntity>) query.getResultList();
		} catch (Exception e) {
			throw new FrameworkException(e);
		}
		return entityList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WorkDefinationEntity> findAllWorkByWorkList(long orgId, List<Long> workId) {
		List<WorkDefinationEntity> entityList = null;
		try {
			StringBuilder hql = new StringBuilder(
					"SELECT wd FROM WorkDefinationEntity wd WHERE wd.orgId =:orgId and wd.workId in :workId ORDER BY workId DESC");
			final Query query = createQuery(hql.toString());
			query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);
			query.setParameter(MainetConstants.WorksManagement.WORK_ID, workId);
			entityList = (List<WorkDefinationEntity>) query.getResultList();
		} catch (Exception e) {
			throw new FrameworkException(e);
		}
		return entityList;
	}

	@Override
	public WorkDefinationEntity findWorkDefinitionByWorkCode(String workcode, Long orgId) {
		WorkDefinationEntity entity = null;
		try {
			StringBuilder hql = new StringBuilder(
					"SELECT wd FROM WorkDefinationEntity wd WHERE wd.orgId =:orgId and wd.workcode=:workcode");
			final Query query = createQuery(hql.toString());
			query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);
			query.setParameter(MainetConstants.WorksManagement.WORKCODE, workcode);
			entity = (WorkDefinationEntity) query.getSingleResult();
		} catch (Exception e) {
			throw new FrameworkException(e);
		}
		return entity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WorkDefinationEntity> findAllApprovedNotUsedInOtherTenderWorkByTenderIdAndProjId(Long projId,
			Long orgId, Long tenderId) {
		List<WorkDefinationEntity> entityList = null;
		try {
			StringBuilder hql = new StringBuilder(
					"SELECT wd FROM WorkDefinationEntity wd WHERE(wd.workId in (select tw.workDefinationEntity.workId from TenderWorkEntity tw "
							+ " where tw.tenderMasEntity.tndId =:tndId))"
							+ " and wd.orgId =:orgId and wd.workStatus='A' OR (wd.workId not in (select ntw.workDefinationEntity.workId from TenderWorkEntity ntw "
							+ " where ntw.tenderMasEntity.tndId  <>:tndId))"
							+ " and wd.orgId =:orgId and wd.projMasEntity.projId =:projId and wd.workStatus='A' ORDER BY workId DESC");
			final Query query = createQuery(hql.toString());
			query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);
			query.setParameter(MainetConstants.WorksManagement.PROJ_ID, projId);
			query.setParameter(MainetConstants.WorksManagement.TND_ID, tenderId);
			entityList = (List<WorkDefinationEntity>) query.getResultList();
		} catch (Exception ex) {
			throw new FrameworkException(ex);
		}
		return entityList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WorkDefinationEntity> findAllApprovedNotInitiatedWorkByProjIdAndOrgId(Long projId, Long orgId) {
		List<WorkDefinationEntity> entityList = null;
		try {
			StringBuilder hql = new StringBuilder(
					"SELECT wd FROM WorkDefinationEntity wd WHERE wd.orgId =:orgId and wd.projMasEntity.projId =:projId and wd.workStatus='A' and wd.workId"
							+ " not in (select twork.workDefinationEntity.workId from TenderWorkEntity twork ) ORDER BY workId DESC");
			final Query query = createQuery(hql.toString());
			query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);
			query.setParameter(MainetConstants.WorksManagement.PROJ_ID, projId);
			entityList = (List<WorkDefinationEntity>) query.getResultList();
		} catch (Exception ex) {
			throw new FrameworkException(ex);
		}
		return entityList;
	}

	@Override
	@Transactional
	public void updateWorksStatusToInitiated(List<Long> worksId) {
		StringBuilder sb = new StringBuilder(
				"UPDATE WorkDefinationEntity a SET a.workStatus = 'I' where a.workId in ?1");
		final Query query = createQuery(sb.toString());
		query.setParameter(1, worksId);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WorkDefinationEntity> findAllApprovedNotInitiatedWork(Long tndId, Long projId, Long orgId) {
		List<WorkDefinationEntity> entityList = null;
		try {
			StringBuilder hql = new StringBuilder(
					"SELECT wd FROM WorkDefinationEntity wd WHERE wd.workId IN (SELECT a.workDefinationEntity.workId FROM TenderWorkEntity a, TenderMasterEntity b WHERE "
							+ " a.tenderMasEntity.tndId=b.tndId AND b.projMasEntity.projId =:projId AND b.tndId=:tndId) OR wd.workId NOT IN "
							+ " (SELECT work.workDefinationEntity.workId FROM TenderWorkEntity work WHERE work.orgId=:orgId)");
			final Query query = createQuery(hql.toString());
			query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);
			query.setParameter(MainetConstants.WorksManagement.PROJ_ID, projId);
			query.setParameter(MainetConstants.WorksManagement.TND_ID, tndId);
			entityList = (List<WorkDefinationEntity>) query.getResultList();
		} catch (Exception ex) {
			throw new FrameworkException("Exception while fetching findAllApprovedNotInitiatedWork : " + ex);
		}
		return entityList;
	}

	@Override
	@Transactional
	public void updateWorkStatusAsTendered(List<Long> workIds) {
		StringBuilder sb = new StringBuilder(
				"UPDATE WorkDefinationEntity a SET a.workStatus = 'T' where a.workId in ?1");
		final Query query = createQuery(sb.toString());
		query.setParameter(1, workIds);
		query.executeUpdate();
	}

	@Override
	public void deleteSanctionDetails(List<Long> removeScanIdList) {

		StringBuilder hql = new StringBuilder("DELETE from  WorkDefinitionSancDet a where a.workSancId in:workSancId");
		final Query query = createQuery(hql.toString());
		query.setParameter(MainetConstants.WorksManagement.WORK_SAN_ID, removeScanIdList);
		query.executeUpdate();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WorkDefinitionSancDet> finadAllSanctionDetailsByWorkId(Long workId) {

		List<WorkDefinitionSancDet> sanctionDetailsList = null;
		try {
			StringBuilder hql = new StringBuilder(
					"SELECT ws from WorkDefinitionSancDet ws  where ws.workDefEntity.workId =:workId");
			final Query query = createQuery(hql.toString());
			query.setParameter(MainetConstants.WorksManagement.WORK_ID, workId);
			sanctionDetailsList = (List<WorkDefinitionSancDet>) query.getResultList();
		} catch (Exception exception) {
			throw new FrameworkException("Exception while getting all sanction details to given workId :" + exception);
		}
		return sanctionDetailsList;
	}

	@Override
	@Transactional
	public void updateWorkSanctionNumber(String workSancNo, Long workId, Long orgid, Long deptId, String workSancBy,
			String workDesignBy) {

		StringBuilder hql = new StringBuilder(
				"UPDATE WorkDefinitionSancDet s SET s.workSancNo=:workSancNo ,s.workSancBy=:workSancBy , s.workDesignBy=:workDesignBy , s.workSancDate =:date "
						+ "where s.workDefEntity.workId=:workId and s.orgid=:orgid and s.deptId=:deptId ");

		final Query query = createQuery(hql.toString());
		query.setParameter(MainetConstants.WorksManagement.WORK_SANC_NO, workSancNo);
		query.setParameter(MainetConstants.WorksManagement.WORK_ID, workId);
		query.setParameter(MainetConstants.WorksManagement.ORGID, orgid);
		query.setParameter(MainetConstants.Common_Constant.DEPTID, deptId);
		query.setParameter(MainetConstants.WorksManagement.WORK_SAN_BY, workSancBy);
		query.setParameter(MainetConstants.WorksManagement.WORK_DESIGN_BY, workDesignBy);
		query.setParameter(MainetConstants.DATE, new Date());
		query.executeUpdate();
	}

	@Override
	public void updateTotalEstimatedAmount(Long workId, BigDecimal totalAmount) {
		final Query query = createQuery("UPDATE WorkDefinationEntity a SET a.workEstAmt =?1 where a.workId =?2");
		query.setParameter(1, totalAmount);
		query.setParameter(2, workId);
		query.executeUpdate();
	}

	@Override
	public void updateWorkCompletionDate(Long workId, Date workCompletionDate, String completionNo) {
		final Query query = createQuery(
				"UPDATE WorkDefinationEntity a SET a.workCompletionDate =?1 and a.workCompletionNo=?3 where a.workId =?2");
		query.setParameter(1, workCompletionDate);
		query.setParameter(2, workId);
		query.setParameter(3, completionNo);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<WorkDefinitionSancDet> filterWorkDefSanctionRecordsByProjId(Long orgId, Long projId,
			String workSancNo) {
		List<WorkDefinitionSancDet> entityList = null;
		try {
			StringBuilder hql = new StringBuilder(
					"SELECT wd FROM WorkDefinitionSancDet wd WHERE wd.workSancNo =:workSancNo and wd.orgid =:orgid and wd.workDefEntity.projMasEntity.projId =:projId ");

			final Query query = createQuery(hql.toString());
			query.setParameter(MainetConstants.WorksManagement.ORGID, orgId);
			query.setParameter(MainetConstants.WorksManagement.PROJ_ID, projId);
			query.setParameter(MainetConstants.WorksManagement.WORK_SANC_NO, workSancNo);
			entityList = (List<WorkDefinitionSancDet>) query.getResultList();
		} catch (Exception e) {
			throw new FrameworkException(e);
		}
		return entityList;
	}

	/*
	 * @Override public List<WorkDefinationEntity>
	 * filterWorkDefRecordsByProjIdAndStatus(Long orgId, Long projId, String status)
	 * { List<WorkDefinationEntity> entityList = null; try { StringBuilder hql = new
	 * StringBuilder(
	 * "SELECT wd FROM WorkDefinationEntity wd WHERE wd.orgId =:orgId and wd.projMasEntity.projId =:projId and wd.workStatus= 'A' "
	 * ); final Query query = createQuery(hql.toString());
	 * query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);
	 * query.setParameter("projId", projId); entityList =
	 * (List<WorkDefinationEntity>) query.getResultList(); } catch (Exception e) {
	 * throw new FrameworkException(); } return entityList; }
	 */

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> filterWorkDefRecordsByProjIdAndStatus(Long orgId, Long projId) {

		List<Object[]> entityList = null;
		try {
			StringBuilder hql = new StringBuilder(
					"SELECT DISTINCT wd.workId, wd.workName FROM WorkDefinationEntity wd,WorkDefinitionSancDet ws WHERE wd.workId=ws.workDefEntity.workId and ws.workSancNo is not null and ws.workSancNo!='' and "
							+ "wd.orgId =:orgId and wd.projMasEntity.projId =:projId");

			final Query query = createQuery(hql.toString());
			query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);
			query.setParameter(MainetConstants.WorksManagement.PROJ_ID, projId);
			entityList = (List<Object[]>) query.getResultList();
		} catch (Exception e) {
			throw new FrameworkException(e);
		}
		return entityList;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WorkDefinitionSancDet> filterSanctionNoRecordsByWrojId(Long orgId, Long workId) {
		List<WorkDefinitionSancDet> entityList = null;
		try {
			StringBuilder hql = new StringBuilder(
					"SELECT wd FROM WorkDefinitionSancDet wd WHERE wd.orgid =:orgid and wd.workDefEntity.workId =:workId");
			final Query query = createQuery(hql.toString());
			query.setParameter(MainetConstants.WorksManagement.ORGID, orgId);
			query.setParameter(MainetConstants.WorksManagement.WORK_ID, workId);
			entityList = (List<WorkDefinitionSancDet>) query.getResultList();
		} catch (Exception e) {
			throw new FrameworkException(e);
		}
		return entityList;

	}

	@Override
	public List<WorkDefinationEntity> findAllCompletedWorks(Long orgId) {
		StringBuilder hql = new StringBuilder(
				"SELECT wd FROM WorkDefinationEntity wd WHERE wd.orgId =:orgId and wd.workStatus ='C' ORDER BY workId DESC");
		final Query query = createQuery(hql.toString());
		query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findAllCompletedWorksByProjId(Long projId, String flag, Long orgId) {
		if (flag.equals(MainetConstants.FlagC)) {
			StringBuilder hql = new StringBuilder(
					"select c.workId,c.workName from WorkDefinationEntity c,WorkEstimateMaster a ,MeasurementBookDetails b left  join  b.workEstimateMaster x  where x.workEstemateId=a.workEstemateId and a.workId=c.workId  and a.orgId=:orgId and c.workStatus='C' and c.projMasEntity.projId=:projId group by c.workId,c.workName,c.deviationPercent having ((sum(a.workEstimQuantity)- sum(b.workActualQty))=0) or ((sum(b.workActualQty)+((sum(a.workEstimQuantity)*(c.deviationPercent))/100))- sum(b.workActualQty)=0)");
			final Query query = createQuery(hql.toString());

			query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);
			query.setParameter(MainetConstants.WorksManagement.PROJ_ID, projId);
			return query.getResultList();
		} else {
			//Modified for #38617
			final Query query=createNativeQuery(createQuery());
			query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);
			query.setParameter(MainetConstants.WorksManagement.PROJ_ID, projId);
			return query.getResultList();
		}
	}
	
	//Defect #88775
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findAllCompletedWorkBasedOnEnv(Long projId, String flag, Long orgId) {
		if (flag.equals(MainetConstants.FlagC)) {
			StringBuilder hql = new StringBuilder(
					"select c.workId,c.workName from WorkDefinationEntity c,WorkEstimateMaster a ,MeasurementBookDetails b left  join  b.workEstimateMaster x  where x.workEstemateId=a.workEstemateId and a.workId=c.workId  and a.orgId=:orgId and c.workStatus='C' and c.projMasEntity.projId=:projId group by c.workId,c.workName,c.deviationPercent having ((sum(a.workEstimQuantity)- sum(b.workActualQty))=0) or ((sum(b.workActualQty)+((sum(a.workEstimQuantity)*(c.deviationPercent))/100))- sum(b.workActualQty)=0)");
			final Query query = createQuery(hql.toString());

			query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);
			query.setParameter(MainetConstants.WorksManagement.PROJ_ID, projId);
			return query.getResultList();
		} else {
			final Query query=createNativeQuery(createQueryBasedOnEnv());
			query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);
			query.setParameter(MainetConstants.WorksManagement.PROJ_ID, projId);
			return query.getResultList();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WorkDefinationEntity> filterCompletionRecords(Long projId, Long workId, String completionNo,
			Long orgId) {
		List<WorkDefinationEntity> entity = null;
		try {
			StringBuilder hql = new StringBuilder(
					"SELECT pm FROM WorkDefinationEntity pm  where pm.orgId = :orgId and workStatus= 'C' ");

			if (completionNo != null && !completionNo.isEmpty()) {
				hql.append(" and pm.workCompletionNo=:completionNo");
			}
			if (projId != null) {
				hql.append(" and pm.projMasEntity.projId=:projId ");
			}
			if (workId != null) {
				hql.append(" and pm.workId =:workId ");
			}

			final Query query = createQuery(hql.toString());
			query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);

			if (completionNo != null && !completionNo.isEmpty()) {
				query.setParameter(MainetConstants.WorksManagement.COMPLETION_NO, completionNo);
			}
			if (projId != null) {
				query.setParameter(MainetConstants.WorksManagement.PROJ_ID, projId);
			}
			if (workId != null) {
				query.setParameter(MainetConstants.WorksManagement.WORK_ID, workId);
			}

			entity = (List<WorkDefinationEntity>) query.getResultList();

		} catch (final Exception exception) {
			LOGGER.error("Exception occur in  filterCompletionRecords() ", exception);

		}
		return entity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAllBudgetHeadByWorkId(Long workId) {
		List<Object[]> list = new ArrayList<>();
		try {
			StringBuilder builder = new StringBuilder("SELECT yearDet.financeCodeDesc, "
					+ "(select acMaster.acHeadCode from AccountHeadSecondaryAccountCodeMasterEntity acMaster where acMaster.sacHeadId=yearDet.sacHeadId) AS BudgetHead,"
					+ "f.faFromDate,f.faToDate from WorkDefinationYearDetEntity yearDet, "
					+ "FinancialYear f where yearDet.faYearId=f.faYear and yearDet.workDefEntity.workId =:workId ");

			final Query query = createQuery(builder.toString());
			query.setParameter(MainetConstants.WorksManagement.WORK_ID, workId);
			list = (List<Object[]>) query.getResultList();
		} catch (Exception exception) {
			LOGGER.error("Exception occur in getAllBudgetHeadByWorkId  ", exception);
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getWorkCompletionRegisterByWorkId(Long workId, String raCode) {
		List<Object[]> list = new ArrayList<>();

		try {
			StringBuilder builder = new StringBuilder(
					"select a.workId,a.workName,a.locIdSt,c.contractFromDate,c.contractToDate,c.startDate,d.raCode,"
							+ "(select vmVendorname from TbAcVendormasterEntity where vmVendorid=b.vendorMaster.vmVendorid) as VendorName from WorkDefinationEntity a,TenderWorkEntity b,WorkOrder c,WorksRABill d where a.workId=b.workDefinationEntity.workId and b.contractId=c.contractMastEntity.contId and a.workId=d.workId and d.raCode=:raCode and a.workId=:workId and d.raMbIds in "
							+ "(select workMbId from MeasurementBookMaster) ");

			final Query query = createQuery(builder.toString());
			query.setParameter(MainetConstants.WorksManagement.WORK_ID, workId);
			query.setParameter(MainetConstants.WorksManagement.RACODE, raCode);
			list = (List<Object[]>) query.getResultList();
		} catch (Exception exception) {
			LOGGER.error("Exception occur in getWorkCompletionRegisterByWorkId  ", exception);
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getRegisterDetailsByWorkId(Long workId, String raCode) {
		List<Object[]> list = new ArrayList<>();

		try {
			StringBuilder builder = new StringBuilder(
					"select c.workId,c.sorDIteamNo,c.sorDDescription,c.sorIteamUnit,c.workEstimQuantity,c.sorBasicRate,c.workEstimAmount,b.workActualQty,b.workActualAmt from MeasurementBookMaster a,MeasurementBookDetails b,WorkEstimateMaster c,WorksRABill d where a.workMbId=b.mbMaster.workMbId and b.workEstimateMaster.workEstemateId=c.workEstemateId and c.workId=:workId and d.raCode=:raCode and d.raMbIds in (SELECT workMbId from MeasurementBookMaster)");

			final Query query = createQuery(builder.toString());
			query.setParameter(MainetConstants.WorksManagement.WORK_ID, workId);
			query.setParameter(MainetConstants.WorksManagement.RACODE, raCode);
			list = (List<Object[]>) query.getResultList();
		} catch (Exception exception) {
			LOGGER.error("Exception occur in getWorkCompletionRegisterByWorkId  ", exception);
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WorkDefinationEntity> findAllWorkOrderGeneratedWorks(Long orgId) {
		List<WorkDefinationEntity> entity = null;
		try {
			StringBuilder hql = new StringBuilder(
					"SELECT c FROM WorkOrder a,TenderWorkEntity b,WorkDefinationEntity c where a.contractMastEntity.contId=b.contractId and b.workDefinationEntity.workId=c.workId and c.orgId = :orgId");

			final Query query = createQuery(hql.toString());
			query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);
			entity = (List<WorkDefinationEntity>) query.getResultList();

		} catch (final Exception exception) {
			LOGGER.error("Exception occur in  findAllWorkOrderGeneratedWorks() ", exception);

		}
		return entity;
	}

	@Override
	public void updateOverHeadAmount(Long workId, BigDecimal overheadAmount) {
		final Query query = createQuery("UPDATE WorkDefinationEntity a SET a.workOverHeadAmt =?1 where a.workId =?2");
		query.setParameter(1, overheadAmount);
		query.setParameter(2, workId);
		query.executeUpdate();
	}

	@Override
	public void saveSanctionDetails(WorkDefinitionSancDet definitionSancDet) {
		entityManager.merge(definitionSancDet);
	}

	// defect #38617  #88783-modified
	private String createQuery() {
		final StringBuilder builder = new StringBuilder();

		builder.append("SELECT \r\n" + "\r\n" + "workdefina0_.WORK_ID,\r\n" + "\r\n" + "  workdefina0_.WORK_NAME  \r\n"
				+ "\r\n" + " FROM TB_WMS_WORKDEFINATION workdefina0_\r\n" + "\r\n"
				+ "      CROSS JOIN TB_WMS_WORKESTIMATE_MAS workestima1_\r\n" + "\r\n" + "      CROSS JOIN \r\n"
				+ "\r\n" + "      (\r\n" + "\r\n"
				+ "       SELECT WORKE_ID,sum(WORKE_ACTUAL_QTY) as WORKE_ACTUAL_QTY\r\n" + "\r\n"
				+ "       FROM TB_WMS_MEASUREMENTBOOK_DET \r\n" + "\r\n" + "       group by worke_id\r\n" + "\r\n"
				+ "      ) measuremen2_\r\n" + "\r\n" + "      LEFT OUTER JOIN TB_WMS_WORKESTIMATE_MAS workestima3_\r\n"
				+ "\r\n" + "         ON measuremen2_.WORKE_ID = workestima3_.WORKE_ID\r\n" + "\r\n"
				+ "WHERE     workestima3_.WORKE_ID = workestima1_.WORKE_ID\r\n" + "\r\n"
				+ "      AND workestima1_.WORK_ID = workdefina0_.WORK_ID\r\n" + "\r\n"
				+ "      AND workestima1_.ORGID = :orgId\r\n" + "\r\n" + "      AND workdefina0_.WORK_STATUS <> 'C'\r\n"
				+ "\r\n" + "      AND workdefina0_.PROJ_ID = :projId\r\n" + "\r\n" + "GROUP BY workdefina0_.WORK_ID,\r\n"
				+ "\r\n" + "        workdefina0_.WORK_NAME,\r\n" + "\r\n" + "        workdefina0_.WORK_DEVPER \r\n"
				+ "\r\n" + "HAVING      sum(workestima1_.WORKE_QUANTITY)   -\r\n"
				+ "sum(measuremen2_.WORKE_ACTUAL_QTY) = 0\r\n" + "\r\n"
				+ "      OR   (sum(measuremen2_.WORKE_ACTUAL_QTY) > sum(workestima1_.WORKE_QUANTITY) );");
		return builder.toString();
	}
	
	
    	// Defect #88775
		private String createQueryBasedOnEnv() {
			final StringBuilder builder = new StringBuilder();

			builder.append("SELECT \r\n" + "\r\n" + "workdefina0_.WORK_ID,\r\n" + "\r\n" + "  workdefina0_.WORK_NAME  \r\n"
					+ "\r\n" + " FROM TB_WMS_WORKDEFINATION workdefina0_\r\n" + "\r\n"
					+ "      CROSS JOIN TB_WMS_WORKESTIMATE_MAS workestima1_\r\n" + "\r\n" + "      CROSS JOIN \r\n"
					+ "\r\n" + "      (\r\n" + "\r\n"
					+ "       SELECT WORKE_ID,sum(WORKE_ACTUAL_QTY) as WORKE_ACTUAL_QTY\r\n" + "\r\n"
					+ "       FROM TB_WMS_MEASUREMENTBOOK_DET \r\n" + "\r\n" + "       group by worke_id\r\n" + "\r\n"
					+ "      ) measuremen2_\r\n" + "\r\n" + "      LEFT OUTER JOIN TB_WMS_WORKESTIMATE_MAS workestima3_\r\n"
					+ "\r\n" + "         ON measuremen2_.WORKE_ID = workestima3_.WORKE_ID\r\n" + "\r\n"
					+ "WHERE     workestima3_.WORKE_ID = workestima1_.WORKE_ID\r\n" + "\r\n"
					+ "      AND workestima1_.WORK_ID = workdefina0_.WORK_ID\r\n" + "\r\n"
					+ "      AND workestima1_.ORGID = :orgId\r\n" + "\r\n" + "      AND workdefina0_.WORK_STATUS <> 'C'\r\n"
					+ "\r\n" + "      AND workdefina0_.PROJ_ID = :projId\r\n" + "\r\n" + "GROUP BY workdefina0_.WORK_ID,\r\n"
					+ "\r\n" + "        workdefina0_.WORK_NAME,\r\n" + "\r\n" + "        workdefina0_.WORK_DEVPER \r\n"
					+ "\r\n" + "HAVING      sum(workestima1_.WORKE_QUANTITY) > sum(measuremen2_.WORKE_ACTUAL_QTY) \r\n" + "\r\n"
					+ "      OR   (sum(measuremen2_.WORKE_ACTUAL_QTY) +\r\n"
					+ "sum(workestima1_.WORKE_QUANTITY) * (workdefina0_.WORK_DEVPER) / 100\r\n" + "\r\n"
					+ "\r\n" + "        - sum(measuremen2_.WORKE_ACTUAL_QTY) = 0);");
			return builder.toString();
		}
}