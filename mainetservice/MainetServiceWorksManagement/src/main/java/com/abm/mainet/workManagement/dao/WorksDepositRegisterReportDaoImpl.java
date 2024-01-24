package com.abm.mainet.workManagement.dao;

import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.exception.FrameworkException;


@Repository
public class WorksDepositRegisterReportDaoImpl extends AbstractDAO<Long> implements WorksDepositRegisterReportDao {

	
	@SuppressWarnings("unchecked")
	public List<Object[]> getWorksDepositRegisterReport(Long orgId, Date  fromDate, Date  toDate) {
		
			List<Object[]> entityList = null;
			try {
				
				StringBuilder hql = new StringBuilder(
						
						"SELECT DISTINCT c.vendorMaster.vmVendorid, e.vmVendorname, b.projMasEntity.projId, d.projNameEng, d.projNameReg, d.projStartDate, d.projEndDate, b.workId, b.workcode, "
								+ "b.workName, b.workStartDate, b.workEndDate, "
								+ "CASE WHEN b.workStatus = 'D' THEN 'Draft' WHEN b.workStatus = 'P' THEN 'Pending' "
								+ "WHEN b.workStatus = 'A' THEN 'Approved' WHEN b.workStatus = 'AA' THEN 'Administrator Approval' "
								+ "WHEN b.workStatus = 'TA' THEN 'Technical Approval' "
								+ "WHEN b.workStatus = 'C' THEN 'Completed' " 
								+ "WHEN b.workStatus = 'T' THEN 'Tender Generated' END AS Status, "
								+ "(SELECT cpdDesc FROM TbComparamDetEntity WHERE cpdId = a.depType) AS DepositTypeEng, " 
								+ "(SELECT cpdDescMar FROM TbComparamDetEntity WHERE cpdId = a.depType) AS DepositTypeReg, "
								+ "a.depDate, a.depAmount FROM TbDepositEntity a, "
								+ "WorkDefinationEntity b, TenderWorkEntity c, TbWmsProjectMaster d, TbAcVendormasterEntity e "
								+ "WHERE a.depRefId = b.workId AND b.workId = c.workDefinationEntity.workId AND d.projId = b.projMasEntity.projId AND c.vendorMaster.vmVendorid = e.vmVendorid AND "
								+ "a.orgId = :orgId AND "
								+ "d.projStartDate BETWEEN :fromDate AND :toDate"
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