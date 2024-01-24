package com.abm.mainet.materialmgmt.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Predicate;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.materialmgmt.domain.InvoiceEntryEntity;

@Repository
public class InvoiceEntryDaoImpl extends AbstractDAO<InvoiceEntryEntity> implements IInvoiceEntryDao {

	@Override
	public List<Object[]> searchInvoiceEntrySummaryData(Long invoiceId, Long poId, Date fromDate, Date toDate,
			Long storeId, Long vendorId, Long orgId) {

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<InvoiceEntryEntity> invoiceEntry = criteriaQuery.from(InvoiceEntryEntity.class);
		ArrayList<Predicate> whereClause = new ArrayList<Predicate>();

		Expression<Long> invoiceIds = invoiceEntry.get("invoiceId").as(Long.class);
		Expression<String> invoiceNo = invoiceEntry.get("invoiceNo").as(String.class);
		Expression<Date> invoiceDate = invoiceEntry.get("invoiceDate").as(Date.class);
		Expression<BigDecimal> invoiceAmt = invoiceEntry.get("invoiceAmt").as(BigDecimal.class);
		Expression<Long> poIds = invoiceEntry.get("poId").as(Long.class);
		Expression<Long> storeIds = invoiceEntry.get("storeId").as(Long.class);
		Expression<Long> vendorIds = invoiceEntry.get("vendorId").as(Long.class);
		Expression<String> invoiceStatus = invoiceEntry.get("invoiceStatus").as(String.class);

		if (null != orgId && orgId > 0)
			whereClause.add(criteriaBuilder.equal(invoiceEntry.get("orgId"), orgId));
		if (null != invoiceId && invoiceId != 0)
			whereClause.add(criteriaBuilder.equal(invoiceEntry.get("invoiceId"), invoiceId));
		if (null != poId && poId != 0)
			whereClause.add(criteriaBuilder.equal(invoiceEntry.get("poId"), poId));
		if (null != fromDate)
			whereClause.add(criteriaBuilder.greaterThanOrEqualTo(invoiceEntry.get("invoiceDate"), fromDate));
		if (null != toDate)
			whereClause.add(criteriaBuilder.lessThanOrEqualTo(invoiceEntry.get("invoiceDate"), toDate));
		if (null != storeId && storeId != 0)
			whereClause.add(criteriaBuilder.equal(invoiceEntry.get("storeId"), storeId));
		if (null != vendorId && vendorId != 0)
			whereClause.add(criteriaBuilder.equal(invoiceEntry.get("vendorId"), vendorId));

		criteriaQuery.orderBy(criteriaBuilder.desc(invoiceEntry.get("invoiceId")));
		
		criteriaQuery.where(whereClause.toArray(new Predicate[0]));
		CriteriaQuery<Object[]> query = criteriaQuery.multiselect(invoiceIds, invoiceNo, invoiceDate, invoiceAmt, poIds,
				storeIds, vendorIds, invoiceStatus);
		List<Object[]> resultList = entityManager.createQuery(query).getResultList();

		return resultList;
	}

	
}
