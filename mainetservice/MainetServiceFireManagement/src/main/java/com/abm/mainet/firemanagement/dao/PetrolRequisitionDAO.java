package com.abm.mainet.firemanagement.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.firemanagement.domain.FmPetrolRequisition;

@Repository
public class PetrolRequisitionDAO extends AbstractDAO<FmPetrolRequisition> implements IPetrolRequisitionDAO {

	
	@SuppressWarnings("unchecked")
	@Override
	public List<FmPetrolRequisition> searchPetrolRequestForm(Date fromDate, Date toDate, Long department, String veNo,
			Long orgid) {
		Query query = this.createQuery(buildQuery(fromDate, toDate, department, veNo, orgid));
		query.setParameter("orgid", orgid);

		  if (fromDate != null){
	            query.setParameter("fromDate", fromDate);
	        }
		  if (toDate != null){
	            query.setParameter("toDate", toDate);
	        }
			if (null != department) {
				query.setParameter("department", department);
			}
		  if (StringUtils.isNotEmpty(veNo)) {
	            query.setParameter("veNo", veNo);
	        }
		
		return query.getResultList();
	}

	private String buildQuery(Date fromDate, Date toDate,Long department ,String veNo,Long orgid) {
              StringBuilder searchQuery = new StringBuilder(
				" SELECT fcr FROM FmPetrolRequisition fcr WHERE fcr.orgid = :orgid ");

		  if (fromDate != null){
			searchQuery.append(" AND fcr.date >= :fromDate ");
		}
		  if (toDate != null) {
			searchQuery.append(" AND fcr.date <= :toDate ");
		}
		  if (null != department) {
			  searchQuery.append("AND fcr.department = :department");
			}
		  
		  if (StringUtils.isNotEmpty(veNo)) {
			  searchQuery.append(" AND fcr.veNo = :veNo ");
	        }
	
		searchQuery.append(" ORDER BY fcr.date DESC");

		return searchQuery.toString();

	}
/*
	@Override
	public List<PetrolRequisition> getDetailByVehNo(String complainNo,Long orgid) {
		Query query = this.createQuery(buildQuery(complainNo));
		query.setParameter("orgid", orgid);

		  if (StringUtils.isNotEmpty(complainNo)) {
	            query.setParameter("veNo", veNo);
	        }
		
		return query.getResultList();
		
		
		private String buildQuery(String complainNo,Long orgid) {
            StringBuilder searchQuery = new StringBuilder(
				" SELECT fcr FROM PetrolRequisition fcr WHERE fcr.orgid = :orgid ");

		  if (fromDate != null){
			searchQuery.append(" AND fcr.date >= :fromDate ");
		}
		  if (toDate != null) {
			searchQuery.append(" AND fcr.date <= :toDate ");
		}
		  if (null != department) {
			  searchQuery.append("AND fcr.department = :department");
			}
		  
		  if (StringUtils.isNotEmpty(veNo)) {
			  searchQuery.append(" AND fcr.veNo = :veNo ");
	        }
	
		searchQuery.append(" ORDER BY fcr.date DESC");

		return searchQuery.toString();

	}
		*/

	@Override
	@Transactional
	public List<FmPetrolRequisition> getDetailByVehNo(Long complainNo, Long orgid) {
		Query query = this.createQuery(buildQuery(complainNo, orgid));
		query.setParameter("orgid", orgid);
		
		/*
		if (StringUtils.isNotEmpty(complainNo)) {
			query.setParameter("veNo", complainNo);
	    }
	    */
		//if (StringUtils.isNotEmpty(complainNo)) {
		if(complainNo != null) {
			query.setParameter("requestId", complainNo);
	    }
		
		return query.getResultList();
		
	}
		
	private String buildQuery(Long complainNo,Long orgid) {
        StringBuilder searchQuery = new StringBuilder(" SELECT fcr FROM FmPetrolRequisition fcr WHERE fcr.orgid = :orgid ");
	  
      /*
	  if (StringUtils.isNotEmpty(complainNo)) {
		  searchQuery.append(" AND fcr.veNo = :veNo ");
      }
	  */
	  
	  //if (StringUtils.isNotEmpty(complainNo)) {
      if(complainNo != null) {  
		  searchQuery.append(" AND fcr.requestId = :requestId ");
      }
	  

	searchQuery.append(" ORDER BY fcr.date DESC");

	return searchQuery.toString();

}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/*
		StringBuilder searchQuery = new StringBuilder(" SELECT fcr FROM PetrolRequisition fcr WHERE fcr.orgid = :orgid ");

			if (complainNo != null && !(complainNo.isEmpty())){
				searchQuery.append(" AND fcr.veNo = :veNo ");
			}
			  
			if (StringUtils.isNotEmpty(complainNo)) {
			   searchQuery.append(" AND fcr.veNo = :veNo ");
		    }
		
			searchQuery.append(" ORDER BY fcr.date DESC");

		return searchQuery.toString();
		*/
	}
	
	
	




