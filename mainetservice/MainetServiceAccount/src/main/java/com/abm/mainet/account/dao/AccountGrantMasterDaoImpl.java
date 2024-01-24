package com.abm.mainet.account.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.account.domain.AccountGrantMasterEntity;
import com.abm.mainet.common.dao.AbstractDAO;

@Repository
public class AccountGrantMasterDaoImpl extends AbstractDAO<AccountGrantMasterEntity> implements AccountGrantMasterDao {
	@SuppressWarnings("unchecked")
	@Override
	public List<AccountGrantMasterEntity> findByNameAndNature(String grtType, String grtName,String grtNo,Long fundId,Date fromDate, Date toDate,Long orgId) {
		// TODO Auto-generated method stub
		Query jpaQuery = this.createQuery(buildQuery(grtType, grtName, grtNo,fundId,fromDate, toDate,orgId));
		jpaQuery.setParameter("orgId", orgId);
		if (null != grtType) {
			jpaQuery.setParameter("grtType", grtType);
		}
		if (null != grtName && !grtName.isEmpty()) {
			jpaQuery.setParameter("grtName", grtName);
		}
		if (null != grtNo && !grtNo.isEmpty()) {
			jpaQuery.setParameter("grtNo", grtNo);
		}
		if (null != fundId) {
			jpaQuery.setParameter("fundId", fundId);
		}
		if (null != fromDate) {
			jpaQuery.setParameter("fromDate", fromDate);
		}
		if (null != toDate) {
			jpaQuery.setParameter("toDate", toDate);
		}
		return jpaQuery.getResultList();
	}

	private String buildQuery(String grtType, String grtName,String grtNo,Long fundId,Date fromDate, Date toDate,Long orgId) {
		// TODO Auto-generated method stub
		StringBuilder query = new StringBuilder(
				" SELECT ai FROM  AccountGrantMasterEntity  ai WHERE ai.orgId = :orgId ");
		if (null != grtType) {
			query.append(" AND ai.grtType = :grtType");
		}
		if (null != grtName && !grtName.isEmpty()) {
			query.append(" AND ai.grtName = :grtName");
		}
		if (null != grtNo && !grtNo.isEmpty() ) {
			query.append(" AND ai.grtNo = :grtNo");
		}
		if (null != fundId) {
			query.append(" AND ai.fundId = :fundId");
		}
		if(null != fromDate)
		{
			query.append(" AND ai.grtDate BETWEEN :fromDate");
		}
		if(null != toDate)
		{
			query.append(" AND :toDate ORDER BY grtDate ASC");
		}
	/*	if(null != fromDate)
		{
			query.append(" ORDER BY grtDate ASC ");
		}*/
		else
		query.append(" ORDER BY grntId DESC ");
		return query.toString();
	}

}
