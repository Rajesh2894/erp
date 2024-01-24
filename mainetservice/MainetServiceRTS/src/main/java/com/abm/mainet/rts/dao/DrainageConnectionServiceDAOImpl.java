package com.abm.mainet.rts.dao;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
@Repository
public class DrainageConnectionServiceDAOImpl extends AbstractDAO<TbCfcApplicationMstEntity>  implements DrainageConnectionServiceDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<TbCfcApplicationMstEntity> searchData(Long applicationId, Long serviceId, Long orgId) {
		Query query = this.createQuery(queryBuilder( applicationId,  serviceId,  orgId));
		
		query.setParameter("orgId", orgId);
		if(applicationId != null)
		{
			query.setParameter("applicationId", applicationId);
		}
		if(serviceId != null)
		{
			query.setParameter("serviceId", serviceId);
		}
		
		
		return query.getResultList();
	}
	
	String queryBuilder(Long applicationId, Long serviceId, Long orgId)
	{
		StringBuilder 	query = new StringBuilder("FROM TbCfcApplicationMstEntity am WHERE am.tbOrganisation.orgid =:orgId ");
		if(applicationId != null)
		{
			query.append("and am.apmApplicationId =:applicationId ");
		}
		if(serviceId != null)
		{
			query.append("and am.tbServicesMst.smServiceId=:serviceId ");
		}
		
		return query.toString();
	}

}
