package com.abm.mainet.common.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.CFCApplicationStatusDto;
import com.abm.mainet.common.utility.Utility;

@Repository
public class CFCApplicationStatusDaoImpl extends AbstractDAO<TbCfcApplicationMstEntity>
		implements ICFCApplicationStatusDao {

	private static final Logger LOGGER = Logger.getLogger(CFCApplicationStatusDaoImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<TbCfcApplicationMstEntity> getCFCApplicationEtites(CFCApplicationStatusDto cfcApplicationStatusDto) {

		List<TbCfcApplicationMstEntity> applicationMstEntities = new ArrayList<TbCfcApplicationMstEntity>();
	
		try {

			StringBuilder hql = new StringBuilder("FROM TbCfcApplicationMstEntity sm WHERE sm.tbOrganisation.orgid ="
					+ cfcApplicationStatusDto.getOrgId());

			if (cfcApplicationStatusDto.getfName() != null && !cfcApplicationStatusDto.getfName().isEmpty()) {
				
				  hql.append(" and sm.apmFname like '%" + cfcApplicationStatusDto.getfName() + "%'");
			
			}
			if (cfcApplicationStatusDto.getAppNo() != null) {
				hql.append(" and sm.apmApplicationId =" + cfcApplicationStatusDto.getAppNo());
			
			}
			if (cfcApplicationStatusDto.getmName() != null && !cfcApplicationStatusDto.getmName().isEmpty()) {
				hql.append(" and sm.apmMname like '%" + cfcApplicationStatusDto.getmName() + "%'");
				 
			}
			if (cfcApplicationStatusDto.getlName() != null && !cfcApplicationStatusDto.getlName().isEmpty()) {
				  hql.append(" and sm.apmLname like '%" + cfcApplicationStatusDto.getlName() + "%'");
				 
				 
			}
			if (cfcApplicationStatusDto.getAppDate() != null) {
				String dateToString = Utility.dateToString(cfcApplicationStatusDto.getAppDate(), MainetConstants.DATE_FORMAT);
				Date afterDateBy = Utility.getAfterDateBy(dateToString);
				String afterDate = Utility.dateToString(afterDateBy, MainetConstants.DATE_FORMATS);
				String beforeDate = Utility.dateToString(cfcApplicationStatusDto.getAppDate(), MainetConstants.DATE_FORMATS);
				
				hql.append(" and sm.apmApplicationDate between '"+beforeDate+"' and '"+afterDate+"'");
			}

			if (cfcApplicationStatusDto.getDeptId() != null) {
				hql.append(" and sm.tbServicesMst.tbDepartment.dpDeptid =" + cfcApplicationStatusDto.getDeptId());
			}

			if (cfcApplicationStatusDto.getServiceId() != null) {
				hql.append(" and sm.tbServicesMst.smServiceId =" + cfcApplicationStatusDto.getServiceId());
			}

			final Query query = this.createQuery(hql.toString());

			applicationMstEntities = (List<TbCfcApplicationMstEntity>) query.getResultList();

		} catch (Exception e) {
			LOGGER.info("Error occure while fetching the data from method getCFCApplicationEtites " + e);
		}
		return applicationMstEntities;
	}

}
