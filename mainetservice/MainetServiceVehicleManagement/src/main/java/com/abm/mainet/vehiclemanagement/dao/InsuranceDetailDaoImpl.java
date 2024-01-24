package com.abm.mainet.vehiclemanagement.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.vehiclemanagement.domain.InsuranceDetail;
import com.ibm.icu.text.DateFormat;

@Repository
public class InsuranceDetailDaoImpl extends AbstractDAO<InsuranceDetail> implements IInsuranceDetaildao {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<InsuranceDetail> searchInsuranceDetails(Long department, Long vehicleType, Long veid, Long orgid) {
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
				" SELECT fcr FROM InsuranceDetail fcr WHERE fcr.orgid = :orgid ");

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
	public List<InsuranceDetail> insuranceDetails(Date issueDate, Date endDate, Long veid, Long orgid) {
 		Query query = this.createQuery(buildQuery1(issueDate, endDate, veid, orgid));
		query.setParameter("orgid", orgid);
		
		if ((issueDate != null) && (endDate != null)) {
            query.setParameter("issueDate", issueDate);
            query.setParameter("endDate", endDate);
        }
		
		if (veid != null) {
			query.setParameter("veid", veid);
		}
		return query.getResultList();
	}
	
	private String buildQuery1(Date issueDate, Date endDate, Long veid, Long orgid) {
		StringBuilder searchQuery = new StringBuilder(" SELECT fcr FROM InsuranceDetail fcr WHERE fcr.orgid = :orgid ");
		if (null != issueDate && null != endDate) {
			searchQuery.append("AND ((fcr.endDate between :issueDate and :endDate) or (fcr.issueDate between :issueDate and :endDate) or (:issueDate between fcr.issueDate and fcr.endDate) or (:endDate between fcr.issueDate and fcr.endDate))"); 
			//searchQuery.append("AND fcr.endDate between :issueDate and :endDate");
		}
		if (null != veid) {
			searchQuery.append(" AND fcr.veId = :veid ");
		}
		searchQuery.append(" ORDER BY fcr.orgid DESC");
	return searchQuery.toString();

}
	

}
