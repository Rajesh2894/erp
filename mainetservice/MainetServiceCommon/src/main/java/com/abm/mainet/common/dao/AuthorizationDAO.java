package com.abm.mainet.common.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.master.dto.Sysmodfunction;

/**
 * 
 * @author Jugnu Pandey
 *
 */
@Repository
public class AuthorizationDAO extends AbstractDAO<Sysmodfunction> implements IAuthorizationDAO{

	public boolean isAuthUrl(String url, Long gmid, Long orgid) {
		 final Query query = createNativeQuery(
	                "select tb.* from  TB_SYSMODFUNCTION tb, ROLE_ENTITLEMENT RR where tb.smfaction like :URL and RR.Role_Id =:gmid and RR.IS_ACTIVE=0 and RR.ORG_ID=:orgid and tb.smfid = RR.SMFID ");
		 query.setParameter("URL", "%"+url+"%");
		 query.setParameter("orgid", orgid);
	     query.setParameter("gmid", gmid);

	     List<Sysmodfunction> list=query.getResultList();
	     if(list.size()>0)
	     {
	    	 return true;
	     }
	     return false;
	}
}
