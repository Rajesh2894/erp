package com.abm.mainet.vehiclemanagement.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.vehiclemanagement.domain.VehicleLogBookDetails;

@Repository
public class LogBookDaoImpl extends AbstractDAO<VehicleLogBookDetails> implements ILogBookDao {

	
	@SuppressWarnings("unchecked")

	public List<VehicleLogBookDetails> searchVehicleLogBook(Date fromDate, Date toDate,Long veNo,
			Long orgid) {

		Query query = this.createQuery(buildQuery(fromDate, toDate,veNo, orgid));
		query.setParameter("orgid", orgid);

		  if (fromDate != null){
	            query.setParameter("fromDate", fromDate);
	        }
		  if (toDate != null){
	            query.setParameter("toDate", toDate);
	        }
		  if (null != veNo){
	            query.setParameter("veNo", veNo);
	        }
		
		

		return query.getResultList();
	}
	private String buildQuery(Date fromDate, Date toDate,Long veNo,
			Long orgid) {

		StringBuilder searchQuery = new StringBuilder(
				" SELECT fcr FROM VehicleLogBookDetails fcr WHERE fcr.orgid = :orgid ");

		  if (fromDate != null){
			searchQuery.append(" AND fcr.outDate >= :fromDate ");
		}
		  if (toDate != null) {
			searchQuery.append(" AND fcr.outDate <= :toDate ");
		}
		  if (null != veNo){
			  searchQuery.append(" AND fcr.veNo = :veNo ");
	        }
	
		searchQuery.append(" ORDER BY fcr.veID DESC");

		return searchQuery.toString();

	}

	}


