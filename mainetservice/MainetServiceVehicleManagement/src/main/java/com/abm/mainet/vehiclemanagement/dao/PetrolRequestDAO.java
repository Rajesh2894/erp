package com.abm.mainet.vehiclemanagement.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.vehiclemanagement.domain.PetrolRequisitionDetails;


@Repository
public class PetrolRequestDAO extends AbstractDAO<PetrolRequisitionDetails> implements IPetrolRequisitionDAO {

	
	@SuppressWarnings("unchecked")
	@Override
	public List<PetrolRequisitionDetails> searchPetrolRequestForm(Date fromDate, Date toDate, Long department, Long veNo,
			Long orgid) {
		Query query = this.createQuery(buildQuery(fromDate, toDate, department, veNo, orgid));
		query.setParameter("orgid", orgid);

		  if (fromDate != null){
	            query.setParameter("fromDate", fromDate);
	        }
		  if (toDate != null){
	            query.setParameter("toDate", toDate);
	        }
			if ( department!=0) {
				query.setParameter("department", department);
			}
		  if (veNo!=0) {
	            query.setParameter("veNo", veNo);
	        }
		
		return query.getResultList();
	}

	private String buildQuery(Date fromDate, Date toDate,Long department ,Long veNo,Long orgid) {
              StringBuilder searchQuery = new StringBuilder(
				" SELECT fcr FROM PetrolRequisitionDetails fcr WHERE fcr.orgid = :orgid ");

		  if (fromDate != null){
			searchQuery.append(" AND fcr.date >= :fromDate ");
		}
		  if (toDate != null) {
			searchQuery.append(" AND fcr.date <= :toDate ");
		}
		  if ( department!=0) {
			  searchQuery.append("AND fcr.department = :department");
			}
		  
		  if ( veNo!=0) {
			  searchQuery.append(" AND fcr.veId = :veNo ");
	        }
	
		searchQuery.append(" ORDER BY fcr.date DESC");

		return searchQuery.toString();

	}


	@Override
	@Transactional
	public List<PetrolRequisitionDetails> getDetailByVehNo(String complainNo, Long orgid) {
		Query query = this.createQuery(buildQuery(complainNo, orgid));
		query.setParameter("orgid", orgid);
		

		if(complainNo != null) {
			query.setParameter("fuelReqNo", complainNo);
	    }
		
		return query.getResultList();
		
	}
		
	private String buildQuery(String complainNo,Long orgid) {
        StringBuilder searchQuery = new StringBuilder(" SELECT fcr FROM PetrolRequisitionDetails fcr WHERE fcr.orgid = :orgid ");
	  
 
      if(complainNo != null) {  
		  searchQuery.append(" AND fcr.fuelReqNo = :fuelReqNo ");
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
	
	
	




