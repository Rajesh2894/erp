package com.abm.mainet.intranet.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.intranet.domain.Poll;

@Repository
public class IPollDao extends AbstractDAO<Poll> implements PollDao {

	private static final Logger logger = Logger.getLogger(IPollDao.class);
	
	public List<Poll> getPollData1(Long orgId, Long objPollId) {
		final StringBuilder builder = new StringBuilder();
		builder.append("select i from Poll i where i.orgid=:orgid order by i.pollid desc");	
		/*if(objPollId != null) {
			builder.append(" and i.id=:id  ");
		}*/
		final Query query = createQuery(builder.toString());
		query.setParameter("orgid", orgId);
		/*if(objPollId != null) {
			query.setParameter("id", objPollId);
		}*/
		List<Poll> PollMaster = query.getResultList();
		return PollMaster;
	}
	
	public List<Poll> getPollData(Long orgId) {
		final StringBuilder builder = new StringBuilder();
		builder.append("select i from Poll i where i.orgid=:orgid order by i.pollid desc");	
		final Query query = createQuery(builder.toString());
		query.setParameter("orgid", orgId);
		List<Poll> PollMaster = query.getResultList();
		return PollMaster;
	}

	@Override
	public List<Poll> getByPollDetId(Long orgId, Long id) {
			final StringBuilder builder = new StringBuilder();
			builder.append("select i from Poll i where i.orgid=:orgid  order by 1 desc");	
			final Query query = createQuery(builder.toString());
			query.setParameter("orgid", orgId);
			//query.setParameter("id", id); and i.id=:id
			List<Poll> PollMaster = query.getResultList();
			return PollMaster;
		
	}

	@Override
	public List<Poll> getPollDataByStatus(Long orgId) {
		final StringBuilder builder = new StringBuilder();
		builder.append("select i from Poll i where i.orgid=:orgid and i.pollStatus=:pollStatus order by 1 desc");	
		final Query query = createQuery(builder.toString());
		query.setParameter("orgid", orgId);
		query.setParameter("pollStatus", "P");
		//query.setParameter("id", id); and i.id=:id
		List<Poll> PollMaster = query.getResultList();
		return PollMaster;

	}

	@Override
	public List<Poll> getPollIdByPollDetId(Long pDetId, Long orgId) {
		final StringBuilder builder = new StringBuilder();
		builder.append("select i from Poll i where i.orgid=:orgid and i.id.id=:id order by 1 desc");	
		final Query query = createQuery(builder.toString());
		query.setParameter("orgid", orgId);
		query.setParameter("id", pDetId); // ashish
		List<Poll> PollMaster = query.getResultList();
		return PollMaster;
	}

	
	
}

