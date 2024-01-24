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
import com.abm.mainet.sfac.domain.FPOAssessmentMasterEntity;

/**
 * @author pooja.maske
 *
 */
@Repository
public class FpoAssessmentMasterDaoImpl extends AbstractDAO<FPOAssessmentMasterEntity>
		implements FpoAssessmentMasterDao {

	private static final Logger logger = Logger.getLogger(FpoAssessmentMasterDaoImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.dao.FpoAssessmentMasterDao#findByFpoIdAndAssStatus(java.
	 * lang.Long, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<FPOAssessmentMasterEntity> findByFpoIdAndAssStatus(Long fpoId, String assStatus) {

		List<FPOAssessmentMasterEntity> entityList = new ArrayList<>();
		try {
			StringBuilder hql = new StringBuilder("Select f from FPOAssessmentMasterEntity f  where ");

			if (fpoId != null && fpoId != 0)
				hql.append(" f.fpoId=:fpoId ");

			if (StringUtils.isNotEmpty(assStatus)) {
				if(fpoId != null && fpoId != 0) {
					hql.append("and ");
				}
				hql.append(" f.assStatus=:assStatus");
			}

			logger.info("QUERY  " + hql.toString());

			final Query query = createQuery(hql.toString());

			if (fpoId != null && fpoId != 0)
				query.setParameter("fpoId", fpoId);
			if (StringUtils.isNotEmpty(assStatus))
				query.setParameter("assStatus", assStatus);

			entityList = query.getResultList();
		} catch (Exception ex) {
			logger.error("Issue at the time of fetchinfg block details by  id's" + ex);
			return entityList;
		}
		return entityList;

	}

}
