package com.abm.mainet.materialmgmt.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.materialmgmt.domain.ItemOpeningBalanceEntity;

@Repository
public class ItemOpeningBalanceDaoImpl extends AbstractDAO<ItemOpeningBalanceEntity> implements ItemOpeningBalanceDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<ItemOpeningBalanceEntity> searchBalaneData(Long storeId, Long itemId, Long orgId) {
		Query query = this.createQuery(buildQuery(storeId, itemId, orgId));
		query.setParameter("orgId", orgId);
		if (null != storeId && 0L != storeId)
			query.setParameter("storeId", storeId);
		if (null != itemId && 0L != itemId)
			query.setParameter("itemId", itemId);
		return query.getResultList();
	}

	private String buildQuery(Long storeId, Long itemId, Long orgId) {
		StringBuilder query = new StringBuilder(" SELECT io FROM ItemOpeningBalanceEntity io WHERE io.orgId = :orgId ");
		if (null != storeId && 0L != storeId) 
			query.append(" AND io.storeMaster.storeId = :storeId");
		if (null != itemId && 0L != itemId)
			query.append(" AND io.itemMasterEntity.itemId = :itemId ");
		query.append(" ORDER BY io.openBalId DESC ");
		return query.toString();

	}

}
