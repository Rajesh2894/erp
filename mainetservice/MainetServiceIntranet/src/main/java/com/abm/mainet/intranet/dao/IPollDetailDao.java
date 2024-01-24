package com.abm.mainet.intranet.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.intranet.domain.PollDetails;

@Repository
public class IPollDetailDao extends AbstractDAO<PollDetails> implements PollDetailDao {

	private static final Logger logger = Logger.getLogger(IPollDetailDao.class);
	
	public List<PollDetails> getPollDetailData(Long orgId) {
		final StringBuilder builder = new StringBuilder();
		builder.append("select i from PollDetails i where i.orgid=:orgid order by 1 desc");	
		final Query query = createQuery(builder.toString());
		query.setParameter("orgid", orgId);
		List<PollDetails> PollDetMaster = query.getResultList();
		return PollDetMaster;
	}
	
	@Override
	public List<PollDetails> getPollByIdFromPollDet(Long orgId) {
		final StringBuilder builder = new StringBuilder();
		builder.append("select i from PollDetails i where i.orgid=:orgid order by 1 desc");	
		final Query query = createQuery(builder.toString());
		query.setParameter("orgid", orgId);
		List<PollDetails> PollDet = query.getResultList();
		return PollDet;
	}
	
	
}

