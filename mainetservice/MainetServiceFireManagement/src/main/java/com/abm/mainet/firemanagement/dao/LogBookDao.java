package com.abm.mainet.firemanagement.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.firemanagement.domain.FmVehicleLogBook;

@Repository
public class LogBookDao extends AbstractDAO<FmVehicleLogBook> implements ILogBookDao {

	
	@SuppressWarnings("unchecked")
	@Override
	public List<FmVehicleLogBook> searchFireCallRegisterwithDate(Date fromDate, Date toDate,String veNo,
			Long orgid) {

		Query query = this.createQuery(buildQuery(fromDate, toDate,veNo, orgid));
		query.setParameter("orgid", orgid);

		  if (fromDate != null){
	            query.setParameter("fromDate", fromDate);
	        }
		  if (toDate != null){
	            query.setParameter("toDate", toDate);
	        }
		  if (StringUtils.isNotEmpty(veNo)) {
	            query.setParameter("veNo", veNo);
	        }
		
		

		return query.getResultList();
	}
	private String buildQuery(Date fromDate, Date toDate,String veNo,
			Long orgid) {

		StringBuilder searchQuery = new StringBuilder(
				" SELECT fcr FROM FmVehicleLogBook fcr WHERE fcr.orgid = :orgid ");

		  if (fromDate != null){
			searchQuery.append(" AND fcr.outDate >= :fromDate ");
		}
		  if (toDate != null) {
			searchQuery.append(" AND fcr.outDate <= :toDate ");
		}
		  if (StringUtils.isNotEmpty(veNo)) {
			  searchQuery.append(" AND fcr.veNo = :veNo ");
	        }
	
		searchQuery.append(" ORDER BY fcr.outDate DESC");

		return searchQuery.toString();

	}
	}



























