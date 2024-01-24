package com.abm.mainet.firemanagement.dao;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.firemanagement.Constants.Constants;
import com.abm.mainet.firemanagement.domain.FireCallRegister;
import com.abm.mainet.firemanagement.domain.TbFmComplainClosure;

@Repository
public class FireCallRegisterDAO extends AbstractDAO<FireCallRegister> implements IFireCallRegisterDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<FireCallRegister> searchFireCallRegister(String cmplntNo, String complaintStatus, String fireStation, Long orgid) {

		Query query = this.createQuery(buildQuery(cmplntNo, complaintStatus,fireStation, orgid));
		query.setParameter("orgid", orgid);

		if (StringUtils.isNotEmpty(cmplntNo)) {
			query.setParameter("cmplntNo", '%'+cmplntNo+'%');
		}

		if (StringUtils.isNotEmpty(complaintStatus)) {
			query.setParameter("complaintStatus", Arrays.asList(complaintStatus.split(",")));
		}
		
		if(fireStation != null && !fireStation.equals(Constants.ZERO)) {
			  query.setParameter("cpdFireStation", fireStation);
		  }

		return query.getResultList();
	}

	private String buildQuery(String cmplntNo, String complaintStatus,String fireStation, Long orgid) {

		StringBuilder searchQuery = new StringBuilder(
				" SELECT fcr FROM FireCallRegister fcr WHERE fcr.orgid = :orgid ");

		if (StringUtils.isNotEmpty(cmplntNo)) {
			searchQuery.append(" AND fcr.cmplntNo like :cmplntNo ");
		}
		if (StringUtils.isNotEmpty(complaintStatus)) {
			searchQuery.append(" AND fcr.complaintStatus in :complaintStatus ");
		}
		 if ( fireStation != null && !fireStation.equals(Constants.ZERO)) {
			  searchQuery.append(" AND fcr.cpdFireStation like concat('%',:cpdFireStation,'%')");
			}
		searchQuery.append(" ORDER BY fcr.cmplntId DESC ");

		return searchQuery.toString();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FireCallRegister> searchFireCallRegisterwithDate(Date fromDate, Date toDate, String fireStation,
			Long orgid) {
		String status=Constants.OPEN_STATUS;
		Query query = this.createQuery(buildQuery(fromDate, toDate,fireStation, orgid));
		query.setParameter("orgid", orgid);
		query.setParameter("status", status);
		query.setParameter("complaintStatus",  Constants.SB);

		  if (fromDate != null){
	            query.setParameter("fromDate", fromDate);
	        }
		  if (toDate != null){
	            query.setParameter("toDate", toDate);
	        }
		  
		  

		   if(fireStation != null && !fireStation.equals(Constants.ZERO)) {
			  query.setParameter("cpdFireStation", fireStation);
		  }

		return query.getResultList();
	}
	private String buildQuery(Date fromDate, Date toDate, String fireStation,
			Long orgid) {

		StringBuilder searchQuery = new StringBuilder(
				" SELECT fcr FROM FireCallRegister fcr WHERE fcr.orgid = :orgid AND (fcr.complaintStatus =:status or fcr.complaintStatus =:complaintStatus)");

		  if (fromDate != null){
			searchQuery.append(" AND fcr.date >= :fromDate ");
		}
		  if (toDate != null) {
			searchQuery.append(" AND fcr.date <= :toDate ");
		}
		  
		  if ( fireStation != null && !fireStation.equals(Constants.ZERO)) {
			  searchQuery.append(" AND fcr.cpdFireStation like concat('%',:cpdFireStation,'%')");
			} 

		  searchQuery.append(" ORDER BY fcr.date DESC");

		return searchQuery.toString();

	}

	@Override
	public List<FireCallRegister> searchFireData(Long orgId, String status) {
		Query query = this.createQuery(buildQuery(orgId, status));
		query.setParameter("orgid", orgId);
		query.setParameter("complaintStatus", status);
		query.setParameter("status", Constants.SB);
		return query.getResultList();
	}

	private String buildQuery(Long orgId, String status) {
		StringBuilder searchQuery = new StringBuilder(" SELECT fcr FROM FireCallRegister fcr WHERE fcr.orgid =:orgid and (fcr.complaintStatus =:complaintStatus or fcr.complaintStatus =:status) ORDER BY fcr.date DESC");

		return searchQuery.toString();
	}

	
	
	@Override
	public List<FireCallRegister> searchFireCallRegisterReg(Date fromDate, Date toDate, String fireStation,
			String cmplntNo, Long orgid, String status) {
		
		
		Query query = this.createQuery(buildQuery(fromDate, toDate,fireStation,cmplntNo, orgid, status));
		query.setParameter("orgid", orgid);
	
			  
		  if (fromDate != null){
	            query.setParameter("fromDate", fromDate);
	        }
		  if (toDate != null){
	            query.setParameter("toDate", toDate);
	        } 
		 
		 
		   if(fireStation != null && !fireStation.equals(Constants.ZERO) ) {
			  query.setParameter("cpdFireStation", fireStation);
		  } 
		  
		  if (StringUtils.isNotEmpty(cmplntNo)) {
				query.setParameter("cmplntNo", cmplntNo);
			}
	
		  
		  if (StringUtils.isNotEmpty(status)) {
				query.setParameter("complaintStatus", status);
			}

		  List<FireCallRegister> firecall= query.getResultList();
		  return firecall;
	}
	
	private String buildQuery(Date fromDate, Date toDate, String fireStation,
			String cmplntNo,Long orgid, String status) {

		StringBuilder searchQuery = new StringBuilder(" SELECT fcr FROM FireCallRegister fcr WHERE fcr.orgid =:orgid ");
	  
		
		 if (fromDate != null){
				searchQuery.append(" AND fcr.date >= :fromDate ");
			}
			  if (toDate != null) {
				searchQuery.append(" AND fcr.date <= :toDate ");
			}
		  if ( fireStation != null && !fireStation.equals(Constants.ZERO) ) {
			  searchQuery.append(" AND fcr.cpdFireStation like concat('%',:cpdFireStation,'%')");
			} 
		  
 		  if (StringUtils.isNotEmpty(cmplntNo)) {
				searchQuery.append("  AND fcr.cmplntNo = :cmplntNo  ");
			}
		  
		  if (StringUtils.isNotEmpty(status)) {
				searchQuery.append(" AND fcr.complaintStatus = :complaintStatus "); 
			}

		  return searchQuery.toString();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TbFmComplainClosure> searchFireCallCloser(String cmplntNo, String complaintStatus, String fireStation, Long orgid) {

		Query query = this.createQuery(buildQuery1(cmplntNo, complaintStatus,fireStation, orgid));
		query.setParameter("orgid", orgid);

		if (StringUtils.isNotEmpty(cmplntNo)) {
			query.setParameter("cmplntNo", '%'+cmplntNo+'%');
		}

		if (StringUtils.isNotEmpty(complaintStatus)) {
			query.setParameter("complaintStatus", Arrays.asList(complaintStatus.split(",")));
		}
		
		if (null != fireStation) {
			query.setParameter("fireStation", fireStation);
		}

		return query.getResultList();
	}
	
	private String buildQuery1(String cmplntNo, String complaintStatus,String fireStation, Long orgid) {

		StringBuilder searchQuery = new StringBuilder(
				" SELECT fcr FROM TbFmComplainClosure fcr WHERE fcr.orgid = :orgid ");

		if (StringUtils.isNotEmpty(cmplntNo)) {
			searchQuery.append(" AND fcr.cmplntNo like :cmplntNo ");
		}
		if (StringUtils.isNotEmpty(complaintStatus)) {
			searchQuery.append(" AND fcr.complaintStatus in :complaintStatus ");
		}
		if (null != fireStation) {
			searchQuery.append(" AND fcr.cpdFireStation = :fireStation ");
		}
		searchQuery.append(" ORDER BY fcr.cmplntId DESC ");

		return searchQuery.toString();

	}

}

