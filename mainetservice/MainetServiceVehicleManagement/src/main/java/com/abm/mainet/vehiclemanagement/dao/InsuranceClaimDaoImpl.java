package com.abm.mainet.vehiclemanagement.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.vehiclemanagement.domain.InsuranceClaim;
import com.abm.mainet.vehiclemanagement.domain.InsuranceDetail;

@Repository
public class InsuranceClaimDaoImpl extends AbstractDAO<InsuranceClaim> implements IInsuranceClaimdao {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<InsuranceClaim> searchInsuranceClaim(Long department, Long vehicleType, Long veid, Long orgid) {
 		Query query = this.createQuery(buildQuery(department, vehicleType, veid, orgid));
		query.setParameter("orgid", orgid);

	  if (department != null){
            query.setParameter("department", department);
        }
		  if (vehicleType != null){
	            query.setParameter("vehicleType", vehicleType);
	        }
		  if (veid != null) {
	            query.setParameter("veid", veid);
	        }
		
		return query.getResultList();
	}

	private String buildQuery(Long department, Long vehicleType, Long veid, Long orgid) {
	          StringBuilder searchQuery = new StringBuilder(
				" SELECT fcr FROM InsuranceClaim fcr WHERE fcr.orgid = :orgid ");

	  if (null != department) {
		  searchQuery.append("AND fcr.department = :department");
		}
		  
		  if (null != vehicleType) {
			  searchQuery.append(" AND fcr.vehicleType = :vehicleType ");
	        }
		  
		  if (null != veid) {
			  searchQuery.append(" AND fcr.veId = :veid ");
	        }
		  

		searchQuery.append(" ORDER BY fcr.orgid DESC");

		return searchQuery.toString();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InsuranceClaim> insuranceClaim(Date issueDate, Date endDate, Long veid, Long orgid) {
		Query query = this.createQuery(buildQuery1(issueDate, endDate, veid, orgid));
		query.setParameter("orgid", orgid);

		if (issueDate != null) {
			query.setParameter("issueDate", issueDate);
		}
		if (endDate != null) {
			query.setParameter("endDate", endDate);
		}
		if (veid != null) {
			query.setParameter("veid", veid);
		}
		return query.getResultList();
	}

	private String buildQuery1(Date issueDate, Date endDate, Long veid, Long orgid) {
		StringBuilder searchQuery = new StringBuilder(" SELECT fcr FROM InsuranceClaim fcr WHERE fcr.orgid = :orgid ");
		if (null != issueDate) {
			searchQuery.append("AND fcr.issueDate = :issueDate ");
		}
		if (null != endDate) {
			searchQuery.append("AND fcr.endDate = :endDate");
		}
		if (null != veid) {
			searchQuery.append(" AND fcr.veId = :veid ");
		}
		searchQuery.append(" ORDER BY fcr.orgid DESC");
		return searchQuery.toString();

	}

}
