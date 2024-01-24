package com.abm.mainet.intranet.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.intranet.domain.PollView;

@Repository
public class IPollViewDao extends AbstractDAO<PollView> implements PollViewDao {

	private static final Logger logger = Logger.getLogger(IPollViewDao.class);

	@Override
	public List<PollView> getPollViewData(Long orgId) {
		final StringBuilder builder = new StringBuilder();
		builder.append("select i from PollView i where i.orgid=:orgid order by 1 desc");	
		final Query query = createQuery(builder.toString());
		query.setParameter("orgid", orgId);
		//query.setParameter("id", pDetId); // ashish
		List<PollView> PollViewMaster = query.getResultList();
		return PollViewMaster;
	}

	@Override
	public List<PollView> getPollViewCount(Long orgId) {
		final StringBuilder builder = new StringBuilder();
		
		builder.append("select p.question, c.text, count(choiceDescVal) as cnt from Poll p, Choice c left outer join PollView v on c.id =: v.choiceId where p.pollid =: c.poll group by p.question, c.text");	
		final Query query = createQuery(builder.toString());
		//query.setParameter("orgid", orgId);
		List<PollView> PollViewMasterCount = query.getResultList();
		return PollViewMasterCount;
	}

	@Override
	public List<PollView> getPollViewDet(Long pollid, Long userId) {
		final StringBuilder builder = new StringBuilder();
		builder.append("select i from PollView i where i.pollId=:pollId and i.loggedinEmpId=:loggedinEmpId order by 1 desc");	
		final Query query = createQuery(builder.toString());
		query.setParameter("pollId", pollid);
		query.setParameter("loggedinEmpId", userId);
		List<PollView> PollViewEntity = query.getResultList();
		return PollViewEntity;
	}
	
	
	
	

	
		
}

