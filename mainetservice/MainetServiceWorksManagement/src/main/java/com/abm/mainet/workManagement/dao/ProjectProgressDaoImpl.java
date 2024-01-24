/**
 * 
 */
package com.abm.mainet.workManagement.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * @author Saiprasad.Vengurekar
 *
 */
@Repository
public class ProjectProgressDaoImpl extends AbstractDAO<Long> implements ProjectProgressDao {

	@Override
	public List<Object[]> getProjectProgressWithWorkId(Long orgId, Date fromDate, Date toDate) {
		String queryString="";
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)) {

		 queryString = "select c.projId,c.projNameEng,c.projStartDate,c.projEndDate,b.workId, b.workcode,b.workName,b.workStartDate,b.workEndDate,a.mileStoneDesc,a.mileStoneWeight,a.msPercent,d.wmSchNameEng,d.schFundName,f.vmVendorname from "
				+ " TbWmsProjectMaster c ,WorkDefinationEntity b,MileStone a,SchemeMaster d,TenderWorkEntity e,TbAcVendormasterEntity f"
				+ " where a.projectMaster.projId=c.projId and a.mastDetailsEntity.workId=b.workId and e.workDefinationEntity.workId=b.workId and e.vendorMaster.vmVendorid=f.vmVendorid and c.projId=b.projMasEntity.projId and a.mastDetailsEntity.workId is not null and a.orgId=:orgId and c.projStartDate BETWEEN :fromDate AND :toDate";
			}
		
		else {
			 queryString = "select c.projId,c.projNameEng,c.projStartDate,c.projEndDate,b.workId, b.workcode,b.workName,b.workStartDate,b.workEndDate,a.mileStoneDesc,a.mileStoneWeight,a.msPercent from "
					+ " TbWmsProjectMaster c ,WorkDefinationEntity b,MileStone a"
					+ " where a.projectMaster.projId=c.projId and a.mastDetailsEntity.workId=b.workId and c.projId=b.projMasEntity.projId and a.mastDetailsEntity.workId is not null and a.orgId=:orgId and c.projStartDate BETWEEN :fromDate AND :toDate";
			}

		final Query query = createQuery(queryString);
		
		query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);
		
		query.setParameter(MainetConstants.WorksManagement.FROM_DATE, fromDate,TemporalType.DATE);
		
		query.setParameter(MainetConstants.WorksManagement.TO_DATE, toDate,TemporalType.DATE);
		
		final List<Object[]> list = query.getResultList();

		return list;
	}

	@Override
	public List<Object[]> getProjectProgress(Long orgId, Date fromDate, Date toDate) {
		
		String queryString = "select c.projId,c.projNameEng,c.projStartDate,c.projEndDate,a.mileStoneDesc,a.mileStoneWeight,a.msPercent from "
				+ " TbWmsProjectMaster c ,MileStone a"
				+ " where a.projectMaster.projId=c.projId and a.mastDetailsEntity.workId is null and a.orgId=:orgId and c.projStartDate BETWEEN :fromDate AND :toDate";
		final Query query = createQuery(queryString);
		
		query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);

		query.setParameter(MainetConstants.WorksManagement.FROM_DATE, fromDate,TemporalType.DATE);

		query.setParameter(MainetConstants.WorksManagement.TO_DATE, toDate,TemporalType.DATE);

		final List<Object[]> list = query.getResultList();

		return list;
	}

}
