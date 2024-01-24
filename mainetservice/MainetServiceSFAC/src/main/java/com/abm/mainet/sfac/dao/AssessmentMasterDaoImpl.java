/**
 * 
 */
package com.abm.mainet.sfac.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.sfac.domain.AssessmentMasterEntity;

/**
 * @author pooja.maske
 *
 */
@Repository
public class AssessmentMasterDaoImpl extends AbstractDAO<AssessmentMasterEntity> implements AssessmentMasterDao {

	private static final Logger LOGGER = Logger.getLogger(AssessmentMasterDaoImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.sfac.dao.AssessmentMasterDao#findDetByIds(java.lang.Long,
	 * java.lang.String, java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AssessmentMasterEntity> findDetByIds(Long cbboId, String assStatus, Date assDate) {
		List<AssessmentMasterEntity> entityList = new ArrayList<>();
		try {

			StringBuilder hql = new StringBuilder("Select b from AssessmentMasterEntity b  where ");

			if (cbboId != null && cbboId != 0)
				hql.append(" b.cbboId=:cbboId");

			if (StringUtils.isNotEmpty(assStatus)) {
				if (cbboId != null && cbboId != 0) {
					hql.append(" and ");
				}
				hql.append(" b.assStatus=:assStatus");
			}
			if (assDate != null) {
				if (StringUtils.isNotEmpty(assStatus))
					hql.append(" and ");
				hql.append(" Date(b.createdDate)=:assDate");
			}
			LOGGER.info("QUERY  " + hql.toString());

			final Query query = createQuery(hql.toString());
			if (cbboId != null && cbboId != 0)
				query.setParameter("cbboId", cbboId);
			if (StringUtils.isNotEmpty(assStatus))
				query.setParameter("assStatus", assStatus);
			if (assDate != null)
				query.setParameter("assDate", assDate);
			entityList = query.getResultList();

		} catch (Exception ex) {
			LOGGER.error("Issue at the time of fetching asseement details by  id's" + ex);
			return entityList;
		}
		return entityList;
	}

}
