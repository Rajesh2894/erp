package com.abm.mainet.materialmgmt.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.materialmgmt.domain.PurchaseRequistionEntity;

@Repository
public class PurchaseOrderDaoImpl extends AbstractDAO<PurchaseRequistionEntity> implements PurchaseOrderDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> purchaseOrderSearchSummaryData(Long storeId, Long vendorId, Date fromDate,
			Date toDate, Long orgId) {

		Query query = this.createQuery(buildQuery(storeId, vendorId, fromDate, toDate, orgId));
		query.setParameter("orgId", orgId);
		if (null != storeId && 0 != storeId)
			query.setParameter("storeId", storeId);
		if (null != vendorId && 0 != vendorId)
			query.setParameter("vendorId", vendorId);
		if (null != fromDate)
			query.setParameter("fromDate", fromDate);
		if (null != toDate)
			query.setParameter("toDate", toDate);
		return query.getResultList();
	}

	private String buildQuery(Long storeId, Long vendorId, Date fromDate, Date toDate, Long orgId) {
		StringBuilder purchaseOrderSearchQuery = new StringBuilder("SELECT po.poId, po.poNo, po.poDate, po.storeId, "
				+ " po.vendorId, po.status FROM PurchaseOrderEntity po WHERE po.orgId=:orgId AND status <> 'N' ");
		if (null != storeId && 0 != storeId)
			purchaseOrderSearchQuery.append(" AND po.storeId = :storeId");
		if (null != vendorId && 0 != vendorId)
			purchaseOrderSearchQuery.append(" AND po.vendorId = :vendorId");
		if (null != fromDate)
			purchaseOrderSearchQuery.append(" AND po.poDate >= :fromDate ");
		if (null != toDate)
			purchaseOrderSearchQuery.append(" AND po.poDate <= :toDate ");
		purchaseOrderSearchQuery.append(" ORDER BY po.poId DESC ");
		return purchaseOrderSearchQuery.toString();
	}

}
