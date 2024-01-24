package com.abm.mainet.workManagement.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.workManagement.domain.WorkEstimOverHeadDetails;
import com.abm.mainet.workManagement.domain.WorkEstimateMaster;
import com.abm.mainet.workManagement.domain.WorkEstimateMasterHistory;

/**
 * 
 * @author Jeetendra.Pal
 *
 */

@Repository
public class WorkEstimateDaoImpl extends AbstractDAO<Long> implements WorkEstimateDao {

	@Override
	public WorkEstimateMaster saveWorkEstimateList(WorkEstimateMaster entity) {
		WorkEstimateMaster master = entityManager.merge(entity);
		return master;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WorkEstimateMaster> getWorkEstimateByWorkId(Long workId, long orgId) {
		StringBuilder hql = new StringBuilder(
				"SELECT pm FROM WorkEstimateMaster pm  where pm.orgId = :orgId and workId= :workId and pm.workEstimActive= 'Y' and workEstimPId IS NULL");
		final Query query = createQuery(hql.toString());
		query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);
		query.setParameter(MainetConstants.WorksManagement.WORK_ID, workId);
		return (List<WorkEstimateMaster>) query.getResultList();
	}

	@Override
	public void setInActiveWorkEstimateByWorkId(List<Long> workIDList) {
		final StringBuilder hql = new StringBuilder(
				"update WorkEstimateMaster set workEstimActive = :workEstimActive  where workId in :workIDList");
		final Query query = createQuery(hql.toString());
		query.setParameter(MainetConstants.WorksManagement.WORK_ESTIM_ACTIVE, MainetConstants.IsDeleted.NOT_DELETE);
		query.setParameter(MainetConstants.WorksManagement.WORKIDLIST, workIDList);
		query.executeUpdate();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getAllActiveDistinctWorkId(long orgid) {
		StringBuilder hql = new StringBuilder(
				"SELECT DISTINCT workId FROM WorkEstimateMaster pm  where pm.orgId = :orgId and pm.workId is not null");
		final Query query = createQuery(hql.toString());
		query.setParameter(MainetConstants.Common_Constant.ORGID, orgid);
		return (List<Long>) query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WorkEstimateMaster> getEstimationByWorkIdAndType(Long workId, String workType) {
		StringBuilder hql = new StringBuilder(
				"SELECT pm FROM WorkEstimateMaster pm  where workEstimActive = :workEstimActive and pm.workId = :workId and workEstimFlag= :workEstimFlag");
		final Query query = createQuery(hql.toString());
		query.setParameter(MainetConstants.WorksManagement.WORK_ID, workId);
		query.setParameter(MainetConstants.WorksManagement.WORK_ESTIM_FLAG, workType);
		query.setParameter(MainetConstants.WorksManagement.WORK_ESTIM_ACTIVE, MainetConstants.IsDeleted.DELETE);
		return (List<WorkEstimateMaster>) query.getResultList();
	}

	@Override
	public BigDecimal calculateTotalEstimatedAmountByWorkId(Long workEstemateId) {
		final StringBuilder hql = new StringBuilder(
				"select Sum(workEstimAmount) from WorkEstimateMaster where workEstimActive = :workEstimActive  and workEstimPId =:workEId and workEstimFlag in ('M','RO','LO','UN','LE','LF','ST','L','C','A')");
		final Query query = createQuery(hql.toString());
		query.setParameter(MainetConstants.WorksManagement.WORK_ESTIM_ACTIVE, MainetConstants.IsDeleted.DELETE);
		query.setParameter(MainetConstants.WorksManagement.WORK_EID, workEstemateId);
		BigDecimal totalEstimate = (BigDecimal) query.getSingleResult();
		return totalEstimate;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WorkEstimateMaster> getRateAnalysisListByEstimateId(Long workEstemateId, String flag) {
		StringBuilder hql = new StringBuilder(
				"SELECT pm FROM WorkEstimateMaster pm  where pm.workEstimPId = :workId and pm.workEstimActive = :workEstimActive and workEstimFlag =:workEstimFlag");
		final Query query = createQuery(hql.toString());
		query.setParameter(MainetConstants.WorksManagement.WORK_ID, workEstemateId);
		query.setParameter(MainetConstants.WorksManagement.WORK_ESTIM_ACTIVE, MainetConstants.IsDeleted.DELETE);
		query.setParameter(MainetConstants.WorksManagement.WORK_ESTIM_FLAG, flag);
		return (List<WorkEstimateMaster>) query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WorkEstimateMaster> getChildRateAnalysisListByMaterialId(Long materialId, Long workId) {
		StringBuilder hql = new StringBuilder(
				"SELECT pm FROM WorkEstimateMaster pm  where pm.maPId = :maId and pm.workEstimPId =:workId and pm.workEstimActive = :workEstimActive");
		final Query query = createQuery(hql.toString());
		query.setParameter(MainetConstants.WorksManagement.MA_ID, materialId);
		query.setParameter(MainetConstants.WorksManagement.WORK_ID, workId);
		query.setParameter(MainetConstants.WorksManagement.WORK_ESTIM_ACTIVE, MainetConstants.IsDeleted.DELETE);
		return (List<WorkEstimateMaster>) query.getResultList();
	}

	@Override
	public void saveOverHeadList(WorkEstimOverHeadDetails entity) {
		entityManager.merge(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WorkEstimOverHeadDetails> getEstimateOverHeadDetByWorkId(Long workId) {
		StringBuilder hql = new StringBuilder(
				"SELECT od FROM WorkEstimOverHeadDetails od  where od.workId = :workId and active = :active");
		final Query query = createQuery(hql.toString());
		query.setParameter(MainetConstants.WorksManagement.WORK_ID, workId);
		query.setParameter(MainetConstants.WorksManagement.ACTIVE, MainetConstants.IsDeleted.DELETE);
		return (List<WorkEstimOverHeadDetails>) query.getResultList();
	}

	@Override
	public void updateDeletedFlag(List<Long> removeWorkIdList) {
		final StringBuilder hql = new StringBuilder(
				"update WorkEstimateMaster set workEstimActive = :projActive  where workEstemateId in :removeWorkIdList");
		final Query query = createQuery(hql.toString());
		query.setParameter(MainetConstants.WorksManagement.PROJ_ACTIVE, MainetConstants.IsDeleted.NOT_DELETE);
		query.setParameter(MainetConstants.WorksManagement.REMOVED_WORKID_LIST, removeWorkIdList);
		query.executeUpdate();

	}

	@Override
	public void updateWorkEsimateLbhQunatity(Long measurementWorkId, BigDecimal totalAmount) {
		final StringBuilder hql = new StringBuilder(
				"update WorkEstimateMaster set workEstimQuantity = :totalAmount  where workEstemateId =:measurementWorkId");
		final Query query = createQuery(hql.toString());
		query.setParameter(MainetConstants.WorksManagement.TOTAL_AMT, totalAmount);
		query.setParameter(MainetConstants.WorksManagement.MEASURE_WORK_ID, measurementWorkId);
		query.executeUpdate();
	}

	@Override
	public void updateDeletedFlagForOverHeads(List<Long> overHeadRemoveById) {
		final StringBuilder hql = new StringBuilder(
				"update WorkEstimOverHeadDetails set active = :active  where overHeadId in :removeWorkIdList");
		final Query query = createQuery(hql.toString());
		query.setParameter(MainetConstants.WorksManagement.ACTIVE, MainetConstants.IsDeleted.NOT_DELETE);
		query.setParameter(MainetConstants.WorksManagement.REMOVED_WORKID_LIST, overHeadRemoveById);
		query.executeUpdate();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WorkEstimateMaster> getAllTenderEstimates(List<Long> workId) {
		StringBuilder hql = new StringBuilder(
				"SELECT pm FROM WorkEstimateMaster pm  where workId  in (:workIDList) and pm.workEstimActive = :workEstimActive and pm.workEstimFlag in ('S','N')");
		final Query query = createQuery(hql.toString());
		query.setParameter(MainetConstants.WorksManagement.WORKIDLIST, workId);
		query.setParameter(MainetConstants.WorksManagement.WORK_ESTIM_ACTIVE, MainetConstants.IsDeleted.DELETE);
		return (List<WorkEstimateMaster>) query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WorkEstimateMaster> getAllRateTypeMBByEstimateNo(Long estmiateId) {
		StringBuilder hql = new StringBuilder(
				"SELECT pm FROM WorkEstimateMaster pm  where  pm.workEstimPId =:workId and pm.workEstimActive = :workEstimActive");
		final Query query = createQuery(hql.toString());
		query.setParameter(MainetConstants.WorksManagement.WORK_ID, estmiateId);
		query.setParameter(MainetConstants.WorksManagement.WORK_ESTIM_ACTIVE, MainetConstants.IsDeleted.DELETE);
		return (List<WorkEstimateMaster>) query.getResultList();
	}

	@Override
	public void updateWorkEstimateNo(Long workId, String mbNumber) {
		final StringBuilder hql = new StringBuilder(
				"Update WorkEstimateMaster we set we.workeEstimateNo =:mbNumber where we.workId =:workId");
		final Query query = createQuery(hql.toString());
		query.setParameter(MainetConstants.WorksManagement.WORK_ID, workId);
		query.setParameter(MainetConstants.WorksManagement.MB_NO, mbNumber);
		query.executeUpdate();
	}

	@Override
	public void updateRateValues(Long workEstemateId, BigDecimal workActualAmt, BigDecimal workActualQty) {
		final StringBuilder hql = new StringBuilder(
				"Update WorkEstimateMaster we set we.workEstimQuantityUtl =:workEstimQuantityUtl ,we.workEstimAmountUtl =:workEstimAmountUtl where we.workEstemateId =:workEstemateId");
		final Query query = createQuery(hql.toString());
		query.setParameter(MainetConstants.WorksManagement.WORKESTIMID, workEstemateId);
		query.setParameter(MainetConstants.WorksManagement.WORK_ESTIM_QNTY_UTL, workActualQty);
		query.setParameter(MainetConstants.WorksManagement.WORK_ESTIM_AMT_UTL, workActualAmt);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WorkEstimateMasterHistory> getAuditRateByEstimateId(Long workEstemateId) {
		StringBuilder hql = new StringBuilder(
				"SELECT we FROM WorkEstimateMasterHistory we  where we.workEstimPId = :workId");
		final Query query = createQuery(hql.toString());
		query.setParameter(MainetConstants.WorksManagement.WORK_ID, workEstemateId);
		return (List<WorkEstimateMasterHistory>) query.getResultList();
	}

	@Override
	public void updateContractId(Long workId, Long contId, Long empId) {
		final StringBuilder hql = new StringBuilder(
				"update WorkEstimateMaster c set c.contractId =:contId, c.updatedBy =:empId,c.updatedDate =:CURRENT_DATE where c.workId =:workId");
		final Query query = createQuery(hql.toString());
		query.setParameter(MainetConstants.WorksManagement.WORK_ID, workId);
		query.setParameter(MainetConstants.WorksManagement.CONTRACT_ID, contId);
		query.setParameter(MainetConstants.WorksManagement.EMP_ID, empId);
		query.setParameter(MainetConstants.WorksManagement.CURRENT_DATE, new Date());
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WorkEstimateMaster> getAllRevisedContarctEstimateDetailsByContrcatId(Long contractId, Long orgId,
			String workEstimateType) {
		StringBuilder hql = null;
		hql = new StringBuilder(
				"SELECT we FROM WorkEstimateMaster we  where workEstimActive = :workEstimActive and we.contractId = :contractId and we.workeReviseFlag is not null");
		if (workEstimateType != null)
			hql.append(" and estimateType= :workEstimateType");
		final Query query = createQuery(hql.toString());
		query.setParameter(MainetConstants.WorksManagement.CONTRACTID, contractId);
		if (workEstimateType != null)
			query.setParameter(MainetConstants.WorksManagement.WORK_ESTIM_TYPE, workEstimateType);
		query.setParameter(MainetConstants.WorksManagement.WORK_ESTIM_ACTIVE, MainetConstants.IsDeleted.DELETE);
		return (List<WorkEstimateMaster>) query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WorkEstimateMaster> getPreviousEstimateByContractId(Long contractId, Long orgId,
			String workEstimateType) {
		StringBuilder hql = null;
		hql = new StringBuilder(
				"SELECT we FROM WorkEstimateMaster we  where workEstimActive = :workEstimActive and we.contractId = :contractId");
		if (workEstimateType != null)
			hql.append(" and workEstimFlag= :workEstimateType");
		final Query query = createQuery(hql.toString());
		query.setParameter(MainetConstants.WorksManagement.CONTRACTID, contractId);
		if (workEstimateType != null)
			query.setParameter(MainetConstants.WorksManagement.WORK_ESTIM_TYPE, workEstimateType);
		query.setParameter(MainetConstants.WorksManagement.WORK_ESTIM_ACTIVE, MainetConstants.IsDeleted.DELETE);
		return (List<WorkEstimateMaster>) query.getResultList();
	}

	@Override
	public void updateParentWorkEstimationAmount(Long workEId, BigDecimal amount) {
		final StringBuilder hql = new StringBuilder(
				"Update WorkEstimateMaster we set we.workEstimAmount =:workEstimAmount where we.workEstemateId =:workEstemateId");
		final Query query = createQuery(hql.toString());
		query.setParameter(MainetConstants.WorksManagement.WORK_ESTIM_AMT, amount);
		query.setParameter(MainetConstants.WorksManagement.WORKESTIMID, workEId);
		query.executeUpdate();
	}

	@Override
	public BigDecimal getSorAndNonSorAmount(Long workId) {
		final StringBuilder hql = new StringBuilder(
				"select Sum(workEstimAmount) from WorkEstimateMaster where workEstimActive = :workEstimActive  and workId =:workId and workEstimFlag in ('S','N','MS','MN')");
		final Query query = createQuery(hql.toString());
		query.setParameter(MainetConstants.WorksManagement.WORK_ESTIM_ACTIVE, MainetConstants.IsDeleted.DELETE);
		query.setParameter(MainetConstants.WorksManagement.WORK_ID, workId);
		BigDecimal totalEstimate = (BigDecimal) query.getSingleResult();
		return totalEstimate;
	}

	@Override
	public BigDecimal calculateTotalEstimatedAmountByContId(Long contId, Long orgId) {
		final StringBuilder hql = new StringBuilder(
				"select Sum(m.workEstimAmount) from WorkEstimateMaster m where m.workeReviseFlag in ('N','E')  and m.contractId =:contId and m.orgId =:orgId");
		final Query query = createQuery(hql.toString());
		query.setParameter(MainetConstants.WorksManagement.CONTRACT_ID, contId);
		query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);

		BigDecimal totalEstimate = (BigDecimal) query.getSingleResult();
		return totalEstimate;
	}

	@Override
	public String findWorkEstimateNoByWorkId(Long workId, Long orgId) {
		final StringBuilder hql = new StringBuilder(
				"SELECT DISTINCT ms.workeEstimateNo from WorkEstimateMaster ms where ms.workId =:workId and ms.orgId =:orgId ");

		final Query query = createQuery(hql.toString());
		query.setParameter(MainetConstants.WorksManagement.WORK_ID, workId);
		query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);

		return (String) query.getSingleResult();
	}

	@Override
	public BigDecimal getOverheadAmount(Long workId) {
		final StringBuilder hql = new StringBuilder(
				"select Sum(w.overHeadValue) from WorkEstimOverHeadDetails w where w.active = :active  and w.workId =:workId");
		final Query query = createQuery(hql.toString());
		query.setParameter(MainetConstants.WorksManagement.ACTIVE, MainetConstants.IsDeleted.DELETE);
		query.setParameter(MainetConstants.WorksManagement.WORK_ID, workId);
		BigDecimal totalEstimate = (BigDecimal) query.getSingleResult();
		return totalEstimate;
	}
	
	@Override
	public WorkEstimateMaster findById(Long orgId, Long workEstimateId) {
		StringBuilder hql = new StringBuilder(
				"SELECT pm FROM WorkEstimateMaster pm  where pm.orgId = :orgId and pm.workEstemateId= :workEstimateId");
		final Query query = createQuery(hql.toString());
		query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);
		query.setParameter(MainetConstants.WorksManagement.WORK_ESTIM_ID, workEstimateId);
		return (WorkEstimateMaster) query.getSingleResult();
	}

	@Override
	public void updateWorkEsimateLbhUtlQunatity(Long measurementWorkId, BigDecimal utlQuantity) {
		final StringBuilder hql = new StringBuilder(
				"Update WorkEstimateMaster we set we.workEstimQuantityUtl =:utlQuantity where we.workId =:measurementWorkId");
		final Query query = createQuery(hql.toString());
		query.setParameter(MainetConstants.WorksManagement.UTL_QUANTITY, utlQuantity);
		query.setParameter(MainetConstants.WorksManagement.MEASURE_WORK_ID, measurementWorkId);
		query.executeUpdate();
		
	}

}
