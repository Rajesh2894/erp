/**
 * 
 */
package com.abm.mainet.workManagement.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.exception.FrameworkException;


@Repository
public class WorksBudgetReportDaoImpl extends AbstractDAO<Long> implements WorksBudgetReportDao {

	
	@SuppressWarnings("unchecked")
	public List<Object[]> getBudgetVsProjExpensesReport(Date fromDate, Date toDate, Long orgId) {
			List<Object[]> entityList = null;
			try {
				
				StringBuilder hql = new StringBuilder(
						//Defect #92549-modified
						"Select distinct e.projId,e.projCode,e.projNameEng,e.projNameReg,e.projStartDate,e.projEndDate,d.workId,d.workcode,d.workName,c.contFromDate as WorkStartDate,  " 
						+ "c.contToDate as WorkEndDate,a.sacHeadId,(select acHeadCode from AccountHeadSecondaryAccountCodeMasterEntity where sacHeadId=a.sacHeadId) as BudgetHead, "
						+ "a.financeCodeDesc as BudgetheadNonAc,a.yeBugAmount as ApprovedAmount,(SELECT SUM(r.raMbAmount) FROM WorksRABill r, MeasurementBookMaster u, "
						+ "WorkOrder v,TenderWorkEntity w WHERE r.raMbIds=u.workMbId AND u.workOrder.workId=v.workId AND v.contractMastEntity.contId=w.contractId AND r.raStatus='A' "
						+ "AND w.workDefinationEntity.workId=b.workDefinationEntity.workId) as Actual_Expenses,"
						+"(SELECT SUM(r.raMbAmount) FROM WorksRABill r,MeasurementBookMaster u, "
						+ "WorkOrder v,TenderWorkEntity w WHERE r.raMbIds=u.workMbId AND u.workOrder.workId =v.workId AND v.contractMastEntity.contId=w.contractId " 
						+ "AND r.raStatus='P' AND w.workDefinationEntity.workId=b.workDefinationEntity.workId) as Actual_Expenses,"
						+"t.taxId ,t.raTaxValue "
						+ "from WorkDefinationYearDetEntity a,TenderWorkEntity b,ContractDetailEntity c,WorkDefinationEntity d,TbWmsProjectMaster e,"
						+ "WmsRaBillTaxDetails t, WorksRABill rb where "
						+ "a.workDefEntity.workId=b.workDefinationEntity.workId and b.contractId=c.contdId and b.workDefinationEntity.workId=d.workId and "
						+ "d.projMasEntity.projId=e.projId and b.workDefinationEntity.workId = rb.workId and t.worksRABill.raId = rb.raId and c.contdActive='Y' and a.yeActive='Y' and "
						+ "a.orgId=:orgId and  t.raTaxFact is null and t.raTaxPercent is null and t.taxId is not null and "
						+ "e.projStartDate BETWEEN :fromDate and :toDate "					
						);
						
				final Query query = createQuery(hql.toString());
				query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);
				query.setParameter(MainetConstants.WorksManagement.FROM_DATE, fromDate);
				query.setParameter(MainetConstants.WorksManagement.TO_DATE, toDate);
				entityList = (List<Object[]>) query.getResultList();
			} catch (Exception ex) {
				throw new FrameworkException("Exception while fetching findAllApprovedNotInitiatedWork : " + ex);
			}
			return entityList;
		}
	
}
