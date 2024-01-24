/**
 * 
 */
package com.abm.mainet.sfac.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.sfac.domain.IAMasterEntity;

/**
 * @author pooja.maske
 *
 */
@Repository
public class IAMasterDaoImpl extends AbstractDAO<IAMasterEntity> implements IAMasterDao {

	private static final Logger LOGGER = Logger.getLogger(IAMasterDaoImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.sfac.dao.IAMasterDao#getIaDetailsByIds(java.lang.Long,
	 * java.lang.Long, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<IAMasterEntity> getIaDetailsByIds(Long IAName, Long allocationYear, Long orgId) {

		List<IAMasterEntity> entityList = new ArrayList<>();
		try {
			StringBuilder hql = new StringBuilder("Select b from IAMasterEntity b  where ");

			if (IAName != null && IAName != 0)
				hql.append(" b.IAId=:IAName ");

			if (allocationYear != null && allocationYear != 0) {
				if (IAName != null && IAName != 0) {
					hql.append(" and ");
				}
				hql.append(" b.alcYear=:allocationYear");
			}

			LOGGER.info("QUERY  " + hql.toString());

			final Query query = createQuery(hql.toString());
			
			if (IAName !=null && IAName != 0)
				query.setParameter("IAName", IAName);
			if (allocationYear != null && allocationYear != 0)
				query.setParameter("allocationYear", allocationYear);

			entityList = query.getResultList();
		} catch (Exception ex) {
			LOGGER.error("Issue at the time of fetchinfg block details by  id's" + ex);
			return entityList;
		}
		return entityList;

	}
}
