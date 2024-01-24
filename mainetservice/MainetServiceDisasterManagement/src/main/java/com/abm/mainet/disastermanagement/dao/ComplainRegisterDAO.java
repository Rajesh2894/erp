package com.abm.mainet.disastermanagement.dao;


import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.disastermanagement.domain.ComplainRegister;
import com.abm.mainet.disastermanagement.domain.ComplainScrutiny;
import com.abm.mainet.disastermanagement.dto.ComplainRegisterDTO;
import com.ibm.icu.text.SimpleDateFormat;

@Repository
public class ComplainRegisterDAO extends AbstractDAO<ComplainRegister> implements IComplainRegisterDAO {

	@Override
	@Transactional(readOnly=true)
	public ComplainRegister getComplainRegData(String complainId, Long orgId,String complainStatus) {

		Query query = this.createQuery(buildQuery(complainId, orgId,complainStatus));
		query.setParameter("complainId", Long.valueOf(complainId));
        query.setParameter("orgid", orgId);
		return (ComplainRegister) query.getSingleResult();
	}

	private String buildQuery(String complainId, Long orgId,String complainStatus) {
		StringBuilder searchQuery = new StringBuilder(" SELECT c FROM ComplainRegister c WHERE c.complainId = :complainId AND c.orgid = :orgid ");
		return searchQuery.toString();
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Object[]> findByCompNoFrmDtToDate(String complainNo, Date frmDate, Date toDate, Long orgid, String formStatusStr, Long userId, Long deptId) {
		 Query query = this.createQuery(buildQuery(complainNo, frmDate, toDate, orgid, formStatusStr));
	     	query.setParameter("orgid", orgid);
	     	query.setParameter("complainStatus", formStatusStr);
	     	query.setParameter("userId", userId);
	     if (StringUtils.isNotEmpty(complainNo)) {
	        query.setParameter("complainNo", complainNo);
	     }
	     return query.getResultList();
	}

	private String buildQuery(String complainNo, Date frmDate, Date toDate, Long orgid, String formStatusStr) {
		StringBuilder searchQuery = new StringBuilder(" SELECT  r.complainNo, r.createdDate, r.complaintType1, r.complaintDescription, r.complainId  FROM ComplainScrutiny c, ComplainRegister r  WHERE c.complainId = r.complainId  and c.orgid =:orgid  and c.userId =:userId  and c.complainScrutinyStatus =:complainStatus and r.complainStatus is not null");    
            
			SimpleDateFormat sdf   = new SimpleDateFormat("yyyy/MM/dd");
            if(frmDate != null && toDate != null) {
            	String frmDate1 = sdf.format(frmDate);
            	String toDate1 = sdf.format(toDate);
		    
            	if (frmDate1 != null && toDate1 != null) {
            		searchQuery.append(" AND cast(c.createdDate AS date) BETWEEN '"+frmDate1+"' AND '"+toDate1+"' ");
            	}
            }
            
        	if (StringUtils.isNotEmpty(complainNo)) {
	            searchQuery.append(" AND c.complainNo = :complainNo ");
	        }
	        
        return searchQuery.toString();
    }
	

	@Override
	public void updateComplainRegData(String complainNo, String status, String remark, Long orgid,Long userId,ComplainRegisterDTO complainRegisterDTO) {
		 StringBuilder updateQuery = new StringBuilder("update ComplainScrutiny as b set b.complainScrutinyStatus =:complainStatus, b.complaintRemark =:complaintRemark, b.complainScrutinyDate =:complainScrutinyDate");
		
		 if(complainRegisterDTO.getCallAttendDate() != null  ) {
			 updateQuery.append(",b.callAttendDate =:callAttendDate");
		 }
         if(complainRegisterDTO.getCallAttendTime() != null  ) {
        	 updateQuery.append(",b.callAttendTime =:callAttendTime");
		 }
         if(complainRegisterDTO.getCallAttendEmployee() != null  ) {
        	 updateQuery.append(",b.callAttendEmployee =:callAttendEmployee");
		 }
        if(complainRegisterDTO.getReasonForDelay() != null  ) {
        	 updateQuery.append(",b.reasonForDelay =:reasonForDelay");
		 }
		
		 updateQuery.append(" where b.complainNo =:complainNo and b.orgid =:orgid and b.userId =:userId");
		Query query = createQuery(updateQuery.toString());
		query.setParameter("complainNo", complainNo);
        query.setParameter("complainStatus", status);   
        query.setParameter("complaintRemark", remark);          
        query.setParameter("complainScrutinyDate",new Date());  
        query.setParameter("orgid", orgid);
        query.setParameter("userId", userId);
        if(complainRegisterDTO.getCallAttendDate() != null  ) {
        	query.setParameter("callAttendDate", complainRegisterDTO.getCallAttendDate());
		 }
        if(complainRegisterDTO.getCallAttendTime() != null  ) {
        	query.setParameter("callAttendTime", complainRegisterDTO.getCallAttendTime());
		 }
        if(complainRegisterDTO.getCallAttendEmployee() != null  ) {
        	query.setParameter("callAttendEmployee",complainRegisterDTO.getCallAttendEmployee());
		 }
       if(complainRegisterDTO.getReasonForDelay() != null  ) {
    	   query.setParameter("reasonForDelay", complainRegisterDTO.getReasonForDelay());
		 }
        query.executeUpdate();
	}

	@Override
	public List<Object[]> getcomplaintData(Long orgId, Long userId, Long deptId, String formStatusStr) {
		Query query = this.createQuery(buildQuery(orgId, userId, deptId, formStatusStr));
     	query.setParameter("orgid", orgId);
     	query.setParameter("userId", userId);
     	query.setParameter("deptId", deptId);
     	query.setParameter("complainStatus", formStatusStr);
    return query.getResultList();
	}

	private String buildQuery(Long orgId, Long userId, Long deptId, String formStatusStr) {
		 StringBuilder searchQuery = new StringBuilder("SELECT c.complainNo, c.createdDate, c.complaintType1, c.complaintDescription, c.complainId  FROM ComplainRegister c, ComplainScrutiny s WHERE c.complainId = s.complainId  and s.userId =:userId  and s.deptId =:deptId  and c.orgid =:orgid  and s.complainScrutinyStatus =:complainStatus ");
    return searchQuery.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComplainRegister> searchInjuryDetails(Long complaintType1, Long complaintType2, Long location,String complainNo, Long orgId,String complainStatus) {

		Query query = this.createQuery(buildQuery(complaintType1, complaintType2, location, complainNo, orgId, complainStatus));
		
        query.setParameter("orgid", orgId);

        if (complaintType1 != null && complaintType1!=0) {
            query.setParameter("complaintType1", complaintType1);
        }
        if (complaintType2 != null && complaintType2!=0) {
            query.setParameter("complaintType2", complaintType2);
        }
        if (location != null && location != 0) { 
            query.setParameter("location", location);
        }
        if (StringUtils.isNotEmpty(complainNo)) {
            query.setParameter("complainNo", complainNo);
        }
        
        if (StringUtils.isNotEmpty(complainStatus)) {
            query.setParameter("complainStatus", complainStatus);
        }
        return query.getResultList();
        
	}

	private String buildQuery(Long complaintType1, Long complaintType2, Long location,String complainNo, Long orgId,String complainStatus) {
		StringBuilder searchQuery = new StringBuilder(buildQuery(orgId, complainStatus));
		
		if (complaintType1 != null && complaintType1!=0) {
            searchQuery.append(" AND complaintType1 = :complaintType1 ");
        }
		if (complaintType2 != null && complaintType2!=0) {
			searchQuery.append(" AND complaintType2 = :complaintType2 ");
		}
		if (location != null && location!=0 ) {
            searchQuery.append(" AND location = :location ");
        }
		if (StringUtils.isNotEmpty(complainNo)) {
			searchQuery.append(" AND complainNo = :complainNo ");
		}
		
		
		return searchQuery.toString();
	}
	@Override
	public List<ComplainRegister> getComplainData(Long orgId, String complainStatus) {
		Query query = this.createQuery(buildQuery( orgId, complainStatus));
        query.setParameter("orgid", orgId);
        query.setParameter("complainStatus", complainStatus);
		return query.getResultList();
	}

	private String buildQuery(Long orgId, String complainStatus) {
        StringBuilder searchQuery = new StringBuilder(" select c from ComplainRegister c where c.orgid=:orgid and c.complainStatus=:complainStatus and c.complainStatus is not null");
		return searchQuery.toString();
	}
	
	@Override
	public List<ComplainScrutiny> getComplainScrutinyData(Long orgId, String complainNo) {
		Query query = this.createQuery(buildCSQuery( orgId, complainNo));
        query.setParameter("orgid", orgId);
        query.setParameter("complainNo", complainNo);
		return query.getResultList();
	}
	
	private String buildCSQuery(Long orgId, String complainNo) {
        StringBuilder searchQuery = new StringBuilder(" select c from ComplainScrutiny c where c.orgid=:orgid and c.complainNo=:complainNo");
		return searchQuery.toString();
	}
	
	
	
	

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> getComplaintClosureSummaryList(Long complaintType1, Long complaintType2, Long location,
	        String complainNo, Long orgId, Long scrutinyUser, String scrutinyStatus ) {
	    StringBuilder builder = new StringBuilder("select c.complainId, c.complainNo, c.complaintType1, c.complaintType2, "
	    		+ " c.complaintDescription, cs.complainScrutinyStatus FROM ComplainRegister c JOIN c.complainScrutiny cs "
	    		+ " where c.orgid=:orgId and cs.userId=:scrutinyUser ");
	    if (complaintType1 != null && complaintType1 != 0)
			builder.append(" and c.complaintType1 =:complaintType1");
		if (complaintType2 != null && complaintType2 != 0)
			builder.append(" and c.complaintType2 =:complaintType2");
		if (location != null && location!=0)
			builder.append(" and c.location=:location");
		if (StringUtils.isNotBlank(complainNo))
			builder.append(" and c.complainNo =:complainNo");
		if (StringUtils.isNotBlank(scrutinyStatus))
			builder.append(" and cs.complainScrutinyStatus =:scrutinyStatus");
		builder.append(" order by c.complainNo");
		final Query query = createQuery(builder.toString());
		query.setParameter("orgId", orgId);
		query.setParameter("scrutinyUser", scrutinyUser);
		if (complaintType1 != null && complaintType1 != 0)
			query.setParameter("complaintType1", complaintType1);
		if (complaintType2 != null && complaintType2 != 0)
			query.setParameter("complaintType2", complaintType2);
		if (location != null && location!=0)
			query.setParameter("location", location);
		if (StringUtils.isNotBlank(complainNo))
			query.setParameter("complainNo", complainNo);
		if (StringUtils.isNotBlank(scrutinyStatus))
			query.setParameter("scrutinyStatus", scrutinyStatus);
		return query.getResultList();
	}
	

	
}