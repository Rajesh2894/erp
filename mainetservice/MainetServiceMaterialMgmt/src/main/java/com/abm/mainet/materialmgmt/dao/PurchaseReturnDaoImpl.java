package com.abm.mainet.materialmgmt.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Predicate;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.materialmgmt.domain.PurchaseReturnEntity;

@Repository
public class PurchaseReturnDaoImpl extends AbstractDAO<PurchaseReturnEntity> implements IPurchaseReturnDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<PurchaseReturnEntity> searchPurchaseReturnAllData(Long returnId, Long grnId, Date fromDate,
			Date toDate, Long storeId, Long vendorId, Long orgId) {
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<PurchaseReturnEntity> criteriaQuery =  criteriaBuilder.createQuery(PurchaseReturnEntity.class);
		Root<PurchaseReturnEntity> purchaseReturn = criteriaQuery.from(PurchaseReturnEntity.class);
		
        // Create a list of predicates to dynamically add conditions
        List<Predicate> predicates = new ArrayList<>();
                
        // Add predicates based on the provided parameters
        predicates.add(criteriaBuilder.equal(purchaseReturn.get("orgId"), orgId));
        if (returnId != null)
            predicates.add(criteriaBuilder.equal(purchaseReturn.get("returnId"), returnId));
        if (grnId != null) 
            predicates.add(criteriaBuilder.equal(purchaseReturn.get("grnId"), grnId));
        if (fromDate != null) 
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(purchaseReturn.get("returnDate"), fromDate));
        if (toDate != null) 
            predicates.add(criteriaBuilder.lessThanOrEqualTo(purchaseReturn.get("returnDate"), toDate));
        if (storeId != null) 
            predicates.add(criteriaBuilder.equal(purchaseReturn.get("storeId"), storeId));
        if (vendorId != null) 
            predicates.add(criteriaBuilder.equal(purchaseReturn.get("vendorId"), vendorId));
        
		criteriaQuery.orderBy(criteriaBuilder.desc(purchaseReturn.get("returnId")));
        
		Query query = entityManager.createQuery(criteriaQuery);
		List<PurchaseReturnEntity> resultList = query.getResultList();
        return resultList;
		
	}

	
	@Override
	public List<Object[]> searchPurchaseReturnSummaryData(Long returnId, Long grnId, Date fromDate,
			Date toDate, Long storeId, Long vendorId, Long orgId) {

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<PurchaseReturnEntity> purchaseReturn = criteriaQuery.from(PurchaseReturnEntity.class);
		ArrayList<Predicate> whereClause = new ArrayList<Predicate>();

		Expression<Long> returnIds = purchaseReturn.get("returnId").as(Long.class);
		Expression<String> returnNo = purchaseReturn.get("returnNo").as(String.class);
		Expression<Date> returnDate = purchaseReturn.get("returnDate").as(Date.class);
		Expression<Long> grnIds = purchaseReturn.get("grnId").as(Long.class);
		Expression<Long> poId = purchaseReturn.get("poId").as(Long.class);
		Expression<Long> storeIds = purchaseReturn.get("storeId").as(Long.class);
		Expression<Long> vendorIds = purchaseReturn.get("vendorId").as(Long.class);
		
		if (orgId != null && orgId > 0)
			whereClause.add(criteriaBuilder.equal(purchaseReturn.get("orgId"), orgId));
		if (returnId != null)
			whereClause.add(criteriaBuilder.equal(purchaseReturn.get("returnId"), returnId));
		if (grnId != null)
			whereClause.add(criteriaBuilder.equal(purchaseReturn.get("grnId"), grnId));
		if (fromDate != null)
			whereClause.add(criteriaBuilder.greaterThanOrEqualTo(purchaseReturn.get("returnDate"), fromDate));
		if (toDate != null)
			whereClause.add(criteriaBuilder.lessThanOrEqualTo(purchaseReturn.get("returnDate"), toDate));
		if (storeId != null)
			whereClause.add(criteriaBuilder.equal(purchaseReturn.get("storeId"), storeId));
		if (vendorId != null)
			whereClause.add(criteriaBuilder.equal(purchaseReturn.get("vendorId"), vendorId));

		criteriaQuery.orderBy(criteriaBuilder.desc(purchaseReturn.get("returnId")));
		
		criteriaQuery.where(whereClause.toArray(new Predicate[0]));
		CriteriaQuery<Object[]> query = criteriaQuery.multiselect(returnIds, returnNo, returnDate, grnIds, poId, storeIds, vendorIds);
		List<Object[]> resultList = entityManager.createQuery(query).getResultList();

		return resultList;
	}
	
	
}
