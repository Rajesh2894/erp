package com.abm.mainet.account.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import com.abm.mainet.account.domain.AccountInvestmentMasterEntity;
import com.abm.mainet.common.dao.AbstractDAO;

@Repository
public class AccountInvestMentDaoImpl extends AbstractDAO<AccountInvestmentMasterEntity>
		implements AccountInvestMentDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<AccountInvestmentMasterEntity> searchByBankId(String invstNo,Long invstId, Long bankId, BigDecimal invstAmount,Long fundId,Date fromDate,Date toDate, Long orgId ) {
		Query jpaQuery = this.createQuery(buildQuery(invstNo,invstId, bankId, invstAmount, fundId,fromDate, toDate,orgId));
		jpaQuery.setParameter("orgId", orgId);

		if (null != invstNo && !invstNo.isEmpty()) {
			jpaQuery.setParameter("invstNo", invstNo);
		}
		if (null != bankId) {
			jpaQuery.setParameter("bankId", bankId);
		}
		if (null != fundId) {
			jpaQuery.setParameter("fundId", fundId);
		}
		if (null != invstAmount) {
			jpaQuery.setParameter("invstAmount", invstAmount);
		}
		if (null != invstId) {
			jpaQuery.setParameter("invstId", invstId);
		}
		if (null != fromDate) {
			jpaQuery.setParameter("fromDate", fromDate);
		}
		if (null != toDate) {
			jpaQuery.setParameter("toDate", toDate);
		}
		return jpaQuery.getResultList();
	}

	private String buildQuery(String invstNo,Long invstId, Long bankId, BigDecimal invstAmount, Long fundId,Date fromDate,Date toDate, Long orgId ) {
		// TODO Auto-generated method stub
		StringBuilder query = new StringBuilder(
				"SELECT ai FROM  AccountInvestmentMasterEntity  ai WHERE ai.orgId = :orgId ");
		if (null != bankId) {
			query.append(" AND ai.bankId = :bankId");
		}
		if (null != invstAmount) {
			query.append(" AND ai.invstAmount = :invstAmount");
		}

		if (null != fundId) {
			query.append(" AND ai.fundId = :fundId");
		}
		if (null != invstNo && !invstNo.isEmpty()) {
			query.append(" AND ai.invstNo = :invstNo");
		}
		if (null != invstId) {
			query.append(" AND ai.invstId = :invstId");
		}
		if(null != fromDate)
		{
			query.append(" AND ai.invstDate BETWEEN :fromDate");
		}
		if(null != toDate)
		{
			query.append(" AND :toDate ORDER BY invstDate ASC ");
		}
		/*if(null != fromDate)
		{
			query.append(" ORDER BY invstDate ASC ");
		}*/
		else
		query.append(" ORDER BY invstId ASC ");
		return query.toString();
	}

}//invstDate
