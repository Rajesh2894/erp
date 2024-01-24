package com.abm.mainet.materialmgmt.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.materialmgmt.domain.StoreIndentEntity;

@Repository
public class StoreIndentDaoImpl extends AbstractDAO<StoreIndentEntity> implements IStoreIndentDao {
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> getStoreIndentSummaryList(Long requestStore, Long storeIndentId, Long issueStore, String status,
			Long orgId) {
		final StringBuilder builder = new StringBuilder();
		builder.append("select i.storeIndentId, i.storeIndentNo, i.storeIndentdate, i.requestStore, i.issueStore, i.status "
				+ " from StoreIndentEntity i where i.orgId=:orgId");
		if (requestStore != null)
			builder.append(" and i.requestStore=:requestStore");
		if (storeIndentId != null)
			builder.append(" and i.storeIndentId=:storeIndentId");
		if (issueStore != null)
			builder.append(" and i.issueStore=:issueStore");
		if (status != null && !status.equals("0"))
			builder.append(" and i.status=:status");
		builder.append(" order by i.storeIndentId desc");

		final Query query = createQuery(builder.toString());
		query.setParameter("orgId", orgId);
		if (requestStore != null)
			query.setParameter("requestStore", requestStore);
		if (storeIndentId != null)
			query.setParameter("storeIndentId", storeIndentId);
		if (issueStore != null)
			query.setParameter("issueStore", issueStore);
		if (status != null && !status.equals("0"))
			query.setParameter("status", status);

		return query.getResultList();
	}

}
