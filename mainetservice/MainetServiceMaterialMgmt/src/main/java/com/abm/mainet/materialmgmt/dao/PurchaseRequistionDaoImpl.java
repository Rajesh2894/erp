package com.abm.mainet.materialmgmt.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.materialmgmt.domain.PurchaseRequistionEntity;


@Repository
public class PurchaseRequistionDaoImpl extends AbstractDAO<PurchaseRequistionEntity> implements PurchaseRequistionDao{

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> searchPurchaseRequisitionDataByAll(Long storeId, String prno, Date fromDate, Date toDate,
			Long orgId) {
		Query query = this.createQuery(buildQuery(storeId, prno, fromDate, toDate, orgId));
		query.setParameter("orgId", orgId);
		if (null != storeId)
			query.setParameter("storeId", storeId);
		if (StringUtils.isNotEmpty(prno))
			query.setParameter("prno", prno);
		if (null != fromDate)
			query.setParameter("fromDate", fromDate);
		if (null != toDate)
			query.setParameter("toDate", toDate);
		return query.getResultList();
	}

	private String buildQuery(Long storeId, String prno, Date fromDate, Date toDate, Long orgId) {
		StringBuilder purchaseRequisitionSearchQuery = new StringBuilder(" SELECT pr.prId, pr.prNo, pr.prDate, "
				+ " pr.storeId, pr.status FROM PurchaseRequistionEntity pr WHERE pr.orgId = :orgId ");

		if (null != storeId)
			purchaseRequisitionSearchQuery.append(" AND pr.storeId = :storeId");
		if (StringUtils.isNotEmpty(prno))
			purchaseRequisitionSearchQuery.append(" AND pr.prNo = :prno ");
		if (null != fromDate)
			purchaseRequisitionSearchQuery.append(" AND pr.prDate >= :fromDate ");
		if (null != toDate)
			purchaseRequisitionSearchQuery.append(" AND pr.prDate <= :toDate ");
		purchaseRequisitionSearchQuery.append(" ORDER BY pr.prId DESC ");
		return purchaseRequisitionSearchQuery.toString();
	}

}
