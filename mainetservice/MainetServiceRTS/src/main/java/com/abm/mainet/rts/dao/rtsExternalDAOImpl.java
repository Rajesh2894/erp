package com.abm.mainet.rts.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.dto.CitizenDashBoardReqDTO;
import com.abm.mainet.rts.domain.RtsExternalServicesEntity;

@Repository
public class rtsExternalDAOImpl extends AbstractDAO<RtsExternalServicesEntity> implements IrtsExternalDAO{

	@Override
	public List<Object[]> getIntegratedServiceApplications(CitizenDashBoardReqDTO request) {
		final StringBuilder queryString = new StringBuilder();
		queryString.append("select am.apmApplicationDate,am.apmApplicationId,ad.apaMobilno,am.tbServicesMst,a.status from CFCApplicationAddressEntity ad,TbCfcApplicationMstEntity am, RtsExternalServicesEntity a where ad.apaMobilno=:mobileNo"
				+ " and ad.userId=:userId and ad.apmApplicationId=am.apmApplicationId and a.orgId=:orgId"
				+" and a.applicationId=am.apmApplicationId");
		
		//ad.apmApplicationId in(select a.applicationId from RtsExternalServicesEntity a where a.orgId=:orgId)
		
		 final Query query = createQuery(queryString.toString());
		 query.setParameter("mobileNo", request.getMobileNo());
		 query.setParameter("userId", request.getEmpId());
		 query.setParameter("orgId", request.getOrgId());
		
		
		 List<Object[]> objList= query.getResultList();
		return objList;
		
	}

}
