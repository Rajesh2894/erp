package com.abm.mainet.account.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.account.domain.AccountLoanMasterEntity;
import com.abm.mainet.common.dao.AbstractDAO;

@Repository
public class AccountLoanMasterDaoImpl extends AbstractDAO<AccountLoanMasterEntity> implements AccountLoanMasterDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<AccountLoanMasterEntity> searchByDeptAndPurpose(Long loanId, String lnDeptname, String lnPurpose,
			Long orgId,String loanCode) {
		// TODO Auto-generated method stub
		Query jpaQuery = this.createQuery(buildQuery(loanId, lnDeptname, lnPurpose, orgId,loanCode));
		jpaQuery.setParameter("orgId", orgId);
		if (null != lnDeptname) {
			jpaQuery.setParameter("lnDeptname", lnDeptname);
		}
		if (null != lnPurpose) {
			jpaQuery.setParameter("lnPurpose", lnPurpose);
		}
		if (null != loanId) {
			jpaQuery.setParameter("loanId", loanId);
		}
		if ( null !=loanCode) {
			jpaQuery.setParameter("lnNo", loanCode);
		}
		return jpaQuery.getResultList();
	}
//lnNo
	private String buildQuery(Long loanId, String lnDeptname, String lnPurpose, Long orgId, String lnNo) {
		// TODO Auto-generated method stub
		StringBuilder query = new StringBuilder(
				" SELECT ai FROM  AccountLoanMasterEntity  ai WHERE ai.orgId = :orgId ");
		if (null != lnDeptname) {
			query.append(" AND lnDeptname = :lnDeptname");
		}
		if (null != lnPurpose) {
			query.append(" AND lnPurpose = :lnPurpose");
		}
		if (null != loanId) {
			query.append(" AND loanId = :loanId");
		}
		if (lnNo!=null ) {
			query.append(" AND lnNo = :lnNo");
		}
		query.append(" ORDER BY loanId ASC ");
		return query.toString();
	}

}
