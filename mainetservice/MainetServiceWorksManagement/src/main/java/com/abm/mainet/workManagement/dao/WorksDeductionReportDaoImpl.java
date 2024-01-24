package com.abm.mainet.workManagement.dao;

import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.exception.FrameworkException;


@Repository
public class WorksDeductionReportDaoImpl extends AbstractDAO<Long> implements WorksDeductionReportDao {

	
	@SuppressWarnings("unchecked")
	public List<Object[]> getWorksDeductionReport(Long orgId, Date  fromDate, Date  toDate) {
		
			List<Object[]> entityList = null;
			try {
				
				StringBuilder hql = new StringBuilder(
						
						"select d.vendorMaster.vmVendorid,g.vmVendorname,e.projMasEntity.projId,f.projNameEng,f.projNameReg,f.projStartDate,f.projEndDate,e.workcode,e.workName, "
						+"c.contractFromDate,c.contractToDate,t.taxId ,(select taxDesc from TbTaxMasEntity where taxId=t.taxId) as TaxName,r.raBillDate ,t.raTaxValue , r.raCode "
						+"from WorksRABill r,WmsRaBillTaxDetails t,MeasurementBookMaster b,WorkOrder c,TenderWorkEntity d,WorkDefinationEntity e, "
						+"TbWmsProjectMaster f,TbAcVendormasterEntity g "
						+"where b.workMbId=r.raMbIds and r.raId=t.worksRABill.raId  and "
						+ "b.workOrder.workId=c.workId and "
						+"c.contractMastEntity.contId=d.contractId and "
						+"d.workDefinationEntity.workId=e.workId and "
						+"e.projMasEntity.projId=f.projId and "
						+"d.vendorMaster.vmVendorid=g.vmVendorid and "
						+"t.orgId=:orgId and t.raTaxValue!=0 and "
						+"c.contractFromDate BETWEEN :fromDate and :toDate "
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