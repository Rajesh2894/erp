/**
 * 
 */
package com.abm.mainet.sfac.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.sfac.domain.CBBOMasterEntity;

/**
 * @author pooja.maske
 *
 */
@Repository
public class CBBOMasterDaoImpl extends AbstractDAO<CBBOMasterEntity> implements CBBOMasterDao{

	private static final Logger logger = Logger.getLogger(CBBOMasterDaoImpl.class);
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CBBOMasterEntity> getCBBODetailsByIds(Long cbboId, Date alcYearToCBBO,Long iaId) {

	List<CBBOMasterEntity> entityList = new ArrayList<>();
	try {
		StringBuilder hql = new StringBuilder("Select b from CBBOMasterEntity b  where ");

		if (cbboId !=null && cbboId !=0)
			hql.append(" b.cbId=:cbboId ");

		if (alcYearToCBBO != null)
			hql.append(" and b.alcYearToCBBO=:alcYearToCBBO");
		
			if (iaId != null && iaId != 0) {
				if (alcYearToCBBO != null || (cbboId != null && cbboId != 0)) {
					hql.append(" and ");
				}
				hql.append(" b.iaId=:iaId");
			}

		logger.info("QUERY  " + hql.toString());

		final Query query = createQuery(hql.toString());
		/*if (orgId != null && orgId != 0)
			query.setParameter("orgId", orgId);*/
		if (cbboId != null && cbboId != 0)
			query.setParameter("cbboId", cbboId);
		if (alcYearToCBBO != null)
			query.setParameter("alcYearToCBBO", alcYearToCBBO);
		if (iaId != null && iaId != 0)
			query.setParameter("iaId", iaId);
			

		entityList = query.getResultList();
	} catch (Exception ex) {
		logger.error("Issue at the time of fetchinfg block details by  id's" + ex);
		return entityList;
	}
	return entityList;
	}

}
