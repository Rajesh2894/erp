/**
 * 
 */
package com.abm.mainet.sfac.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.sfac.domain.StateInformationEntity;

/**
 * @author pooja.maske
 *
 */
@Repository
public class StateInformationDaoImpl extends AbstractDAO<StateInformationEntity> implements StateInformationDao {

	private static final Logger LOGGER = Logger.getLogger(StateInformationDaoImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.dao.StateInformationDao#getStateInfoDetailsByIds(java.
	 * lang.Long, java.lang.Long, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StateInformationEntity> getStateInfoDetailsByIds(Long state, Long district, Long orgId) {
		List<StateInformationEntity> entityList = new ArrayList<>();
		try {
			StringBuilder hql = new StringBuilder("Select s from StateInformationEntity s where");
			if (state != null && state != 0)
				hql.append(" s.state=:state");

			if (district != null && district != 0) {
				if (state != null && state != 0) {
					hql.append(" and ");
				}
				hql.append(" s.district=:district");
			}
			LOGGER.info("QUERY  " + hql.toString());

			final Query query = createQuery(hql.toString());
			if (state != null && state != 0)
				query.setParameter("state", state);
			if (district != null && district != 0)
				query.setParameter("district", district);
			/*if (orgId != null && orgId != 0)
				query.setParameter("orgId", orgId);*/
			entityList = query.getResultList();
		} catch (Exception e) {
			LOGGER.error("Issue at the time of fetchinfg State  details by  id's" + e);
			return entityList;
		}
		return entityList;
	}

}
