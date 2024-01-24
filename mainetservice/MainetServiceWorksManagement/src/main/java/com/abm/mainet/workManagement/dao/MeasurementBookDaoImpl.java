package com.abm.mainet.workManagement.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import javax.persistence.Query;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.workManagement.domain.MeasurementBookMaster;

/**
 * @author Saiprasad.Vengurlekar
 *
 */

@Repository
public class MeasurementBookDaoImpl extends AbstractDAO<Long> implements IMeasurementBookDao {
	private static final Logger LOGGER = Logger.getLogger(MeasurementBookDaoImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<MeasurementBookMaster> filterMeasurementBookData(Long workOrderId, String flag, Long orgId) {

		List<MeasurementBookMaster> entity = null;
		try {
			StringBuilder hql = new StringBuilder("SELECT pm FROM MeasurementBookMaster pm  where pm.orgId = :orgId");

			if (workOrderId != null) {
				hql.append(" and pm.workOrder.workId= :workOrderId");
			}

			if (flag != null && !flag.isEmpty()) {
				hql.append(" and pm.mbStatus= :flag");
			}

			final Query query = createQuery(hql.toString());
			query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);

			if (workOrderId != null) {
				query.setParameter(MainetConstants.WorksManagement.WORK_ORDER_ID, workOrderId);
			}

			if (flag != null && !flag.isEmpty()) {
				query.setParameter(MainetConstants.Common_Constant.FLAG, flag);
			}

			entity = (List<MeasurementBookMaster>) query.getResultList();

		} catch (final Exception exception) {
			throw new FrameworkException("Exception occur in  filterMeasurementBookData() ", exception);

		}
		return entity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MeasurementBookMaster> getAllMbDeatilsBySearch(Long workOrderId, String flag, String mbNo, Long workId,
			Long vendorId, Long orgId) {
		List<MeasurementBookMaster> entity = null;
		try {
			StringBuilder hql = new StringBuilder("SELECT pm FROM MeasurementBookMaster pm  where pm.orgId = :orgId");

			if (workOrderId == null) {
				if (mbNo.trim() != null && !mbNo.trim().isEmpty()) {
					if (workId != null && vendorId != null) {
						hql.append(
								" and pm.workOrder.workId in (select b.workId from TenderWorkEntity a,WorkOrder b where a.contractId=b.contractMastEntity.contId and a.vendorMaster.vmVendorid=(case when COALESCE(:vendorId,0)=0 then COALESCE(a.vendorMaster.vmVendorid,0) else COALESCE(:vendorId,0) end) and  a.workDefinationEntity.workId=(case when COALESCE(:workId,0)=0 then COALESCE(a.workDefinationEntity.workId,0) else COALESCE(:workId,0) end)) and pm.workMbNo= :mbNo");

						if (flag != null && !flag.isEmpty()) {
							hql.append(" and pm.mbStatus= :flag");
						}
					} else {
						if (workId == null)
							workId = 0l;
						if (vendorId == null)
							vendorId = 0l;
						hql.append(
								" and pm.workOrder.workId in (select b.workId from TenderWorkEntity a,WorkOrder b where a.contractId=b.contractMastEntity.contId and a.vendorMaster.vmVendorid=(case when COALESCE(:vendorId,0)=0 then COALESCE(a.vendorMaster.vmVendorid,0) else COALESCE(:vendorId,0) end) and  a.workDefinationEntity.workId=(case when COALESCE(:workId,0)=0 then COALESCE(a.workDefinationEntity.workId,0) else COALESCE(:workId,0) end))");

						hql.append(" and pm.workMbNo= :mbNo");
						if (flag != null && !flag.isEmpty()) {
							hql.append(" and pm.mbStatus= :flag");
						}
					}
				} else {
					if (workId == null)
						workId = 0l;
					if (vendorId == null)
						vendorId = 0l;
					hql.append(
							" and pm.workOrder.workId in (select b.workId from TenderWorkEntity a,WorkOrder b where a.contractId=b.contractMastEntity.contId and a.vendorMaster.vmVendorid=(case when COALESCE(:vendorId,0)=0 then COALESCE(a.vendorMaster.vmVendorid,0) else COALESCE(:vendorId,0) end) and  a.workDefinationEntity.workId=(case when COALESCE(:workId,0)=0 then COALESCE(a.workDefinationEntity.workId,0) else COALESCE(:workId,0) end))");
					if (flag != null && !flag.isEmpty()) {
						hql.append(" and pm.mbStatus= :flag");
					}
				}

			} else {
				if (workId == null)
					workId = 0l;
				if (vendorId == null)
					vendorId = 0l;
				hql.append(
						" and pm.workOrder.workId in (select b.workId from TenderWorkEntity a,WorkOrder b where a.contractId=b.contractMastEntity.contId and a.vendorMaster.vmVendorid=(case when COALESCE(:vendorId,0)=0 then COALESCE(a.vendorMaster.vmVendorid,0) else COALESCE(:vendorId,0) end) and  a.workDefinationEntity.workId=(case when COALESCE(:workId,0)=0 then COALESCE(a.workDefinationEntity.workId,0) else COALESCE(:workId,0) end)) and pm.workOrder.workId= :workOrderId");
				if (mbNo != null && !mbNo.isEmpty())
					hql.append(" and pm.workMbNo= :mbNo");
				if (flag != null && !flag.isEmpty()) {
					hql.append(" and pm.mbStatus= :flag");
				}
			}

			final Query query = createQuery(hql.toString());
			query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);

			if (workOrderId != null) {
				query.setParameter(MainetConstants.WorksManagement.WORK_ORDER_ID, workOrderId);
			}
			if (workId != null) {
				query.setParameter("workId", workId);
			}
			if (vendorId != null) {
				query.setParameter("vendorId", vendorId);
			}

			if (flag != null && !flag.isEmpty()) {
				query.setParameter(MainetConstants.Common_Constant.FLAG, flag);
			}

			if (mbNo != null && !mbNo.isEmpty()) {
				query.setParameter("mbNo", mbNo);
			}

			entity = (List<MeasurementBookMaster>) query.getResultList();

		} catch (final Exception exception) {
			throw new FrameworkException("Exception occur in  filterMeasurementBookData() ", exception);

		}
		return entity;
	}

}
