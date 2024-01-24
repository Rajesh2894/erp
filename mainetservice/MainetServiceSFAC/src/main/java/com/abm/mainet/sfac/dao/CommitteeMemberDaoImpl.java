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
import com.abm.mainet.sfac.domain.CommitteeMemberMasterEntity;

/**
 * @author pooja.maske
 *
 */
@Repository
public class CommitteeMemberDaoImpl extends AbstractDAO<CommitteeMemberMasterEntity> implements CommitteeMemberDao {

	private static final Logger logger = Logger.getLogger(CommitteeMemberDaoImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.dao.CommitteeMemberDao#getDetByCommiteeIdAndName(java.
	 * lang.Long, java.lang.Long, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CommitteeMemberMasterEntity> getDetByCommiteeIdAndName(Long committeeTypeId, Long comMemberId,
			Long orgId) {
		List<CommitteeMemberMasterEntity> entityList = new ArrayList<>();
		try {
			StringBuilder hql = new StringBuilder("Select c from CommitteeMemberMasterEntity c where c.orgId=:orgId ");
			if (committeeTypeId != null && committeeTypeId != 0) {
				hql.append(" and c.committeeTypeId=:committeeTypeId ");
			}
			if (comMemberId != null && comMemberId != 0) {
				/*if (committeeTypeId != null && committeeTypeId != 0) {
					hql.append(" and ");
				}*/
				hql.append(" and c.comMemberId=:comMemberId");
			}

			logger.info("QUERY  " + hql.toString());

			final Query query = createQuery(hql.toString());

			if (orgId != null && orgId != 0)
				query.setParameter("orgId", orgId);

			if (committeeTypeId != null && committeeTypeId != 0)
				query.setParameter("committeeTypeId", committeeTypeId);

			if (comMemberId != null && comMemberId != 0)
				query.setParameter("comMemberId", comMemberId);

			entityList = query.getResultList();
		} catch (Exception e) {
			logger.error("Issue at the time of fetchinfg committee details by  id's" + e);
			return entityList;
		}
		return entityList;
	}

}
