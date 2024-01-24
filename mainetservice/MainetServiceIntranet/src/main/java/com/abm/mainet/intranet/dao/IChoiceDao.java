package com.abm.mainet.intranet.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.intranet.domain.Choice;

@Repository
public class IChoiceDao extends AbstractDAO<Choice> implements ChoiceDao {

	private static final Logger logger = Logger.getLogger(IPollDao.class);

	@Override
	public List<Choice> getChoiceData(Long orgid, Long pollID) {
		final StringBuilder builder = new StringBuilder();
		builder.append("select i from Choice i where i.orgid=:orgid  order by 1 desc");	
		final Query query = createQuery(builder.toString());
		query.setParameter("orgid", orgid);
		//query.setParameter("poll", pollID);
		List<Choice> ChoiceMaster = query.getResultList();
		return ChoiceMaster;
	}

	@Override
	public Choice getChoiceDescData(Long orgid, Long id) {
		final StringBuilder builder = new StringBuilder();
		builder.append("select i from Choice i where i.orgid=:orgid and i.id=:id");	
		final Query query = createQuery(builder.toString());
		query.setParameter("orgid", orgid);
		query.setParameter("id", id);
		Choice ChoiceMaster = (Choice) query.getSingleResult();
		return ChoiceMaster;
	}
	
}

