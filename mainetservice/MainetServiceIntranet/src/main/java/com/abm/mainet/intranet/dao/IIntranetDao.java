package com.abm.mainet.intranet.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.intranet.domain.IntranetMaster;

@Repository
public class IIntranetDao extends AbstractDAO<IntranetMaster> implements IntranetDao {

	private static final Logger logger = Logger.getLogger(IIntranetDao.class);

	
	@Override
	public List<IntranetMaster> getIntranetData(Long orgId, Long docCateType) {
		final StringBuilder builder = new StringBuilder();
		builder.append("select i from IntranetMaster i where i.orgid=:orgid and i.docCateType=:docCateType order by 1 desc");
		final Query query = createQuery(builder.toString());
		query.setParameter("orgid", orgId);
		query.setParameter("docCateType", docCateType);
		List<IntranetMaster> IntranetEntity = query.getResultList();
		return IntranetEntity;
	}
	
	@Override
	public IntranetMaster getIntranetDataByInId(Long inId, Long orgId) {

		final StringBuilder builder = new StringBuilder();
		builder.append("select i from IntranetMaster i where i.orgid=:orgid and i.inId=:inId order by i.inId desc");
		final Query query = createQuery(builder.toString());
		query.setParameter("inId", inId);
		query.setParameter("orgid", orgId);
		IntranetMaster IntranetMaster = (com.abm.mainet.intranet.domain.IntranetMaster) query.getSingleResult();
		return IntranetMaster;
		
	}

	@Override
	public List<IntranetMaster> getAllIntranetData(Long orgId) {
		final StringBuilder builder = new StringBuilder();
		builder.append("select i from IntranetMaster i where i.orgid=:orgid order by 1 desc");
		final Query query = createQuery(builder.toString());
		query.setParameter("orgid", orgId);
		List<IntranetMaster> IntranetEntity = query.getResultList();
		return IntranetEntity;
	}

	
	
}
