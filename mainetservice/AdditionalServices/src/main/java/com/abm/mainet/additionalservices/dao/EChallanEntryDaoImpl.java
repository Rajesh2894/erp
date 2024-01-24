/**
 * 
 */
package com.abm.mainet.additionalservices.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.additionalservices.domain.EChallanMasterEntity;
import com.abm.mainet.common.dao.AbstractDAO;

/**
 * @author divya.marshettiwar
 *
 */
@Repository
public class EChallanEntryDaoImpl extends AbstractDAO<EChallanMasterEntity> implements EChallanEntryDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<EChallanMasterEntity> searchData(String challanNo, String raidNo, 
			String offenderName,String offenderMobNo, String challanType, Long orgid) {
		
		final StringBuilder builder = new StringBuilder();
		builder.append("select i from EChallanMasterEntity i where i.challanType=:challanType and i.orgid=:orgid");
		
		if(challanNo != null && !challanNo.equals("")) {
			builder.append(" and i.challanNo=:challanNo");
		}
		if(raidNo != null && !raidNo.equals("")) {
			builder.append(" and i.raidNo=:raidNo");
		}
		if(offenderName != null && !offenderName.equals("")) {
			builder.append(" and i.offenderName LIKE :offenderName");
		}
		if(offenderMobNo != null && !offenderMobNo.equals("")) {
			builder.append(" and i.offenderMobNo=:offenderMobNo");
		}
		
		final Query query = createQuery(builder.toString());
		query.setParameter("challanType", challanType);
		query.setParameter("orgid", orgid);
		
		if(challanNo != null && !challanNo.equals("")) {
			query.setParameter("challanNo", challanNo);
		}
		if(raidNo != null && !raidNo.equals("")) {
			query.setParameter("raidNo", raidNo);
		}
		if(offenderName != null && !offenderName.equals("")) {
			query.setParameter("offenderName", offenderName+"%");
		}
		if(offenderMobNo != null && !offenderMobNo.equals("")) {
			query.setParameter("offenderMobNo", offenderMobNo);
		}
		
		List<EChallanMasterEntity> challanData = (List<EChallanMasterEntity>)query.getResultList();
		
		return challanData;
	}
	
	 @Override
	 @Transactional
	 public List<EChallanMasterEntity> challanDetailsFromDates(Date challanFromDate, Date challanToDate, String challanType) {
		 final Query query = createQuery(
				 "SELECT i FROM EChallanMasterEntity i Where  i.challanDate >= :challanFromDate and i.challanDate <= :challanToDate"
				 + " and i.challanType=:challanType");
		 query.setParameter("challanFromDate",challanFromDate);
		 query.setParameter("challanToDate",challanToDate);
		 query.setParameter("challanType",challanType);
		 List<EChallanMasterEntity> challanDateList = (List<EChallanMasterEntity>)query.getResultList();			
		 return challanDateList;
	 }
	 
	 @Override
	 public List<EChallanMasterEntity> raidDetailsFromDates(Date challanFromDate, Date challanToDate, String challanType) {
		 final Query query = createQuery(
				 "SELECT i FROM EChallanMasterEntity i Where  i.challanDate >= :challanFromDate and i.challanDate <= :challanToDate"
				 + " and i.challanType=:challanType");
		 query.setParameter("challanFromDate",challanFromDate);
		 query.setParameter("challanToDate",challanToDate);
		 query.setParameter("challanType",challanType);
		 List<EChallanMasterEntity> challanDateList = (List<EChallanMasterEntity>)query.getResultList();			
		 return challanDateList;
	 }

	@Override
	public List<EChallanMasterEntity> searchRaidData(String raidNo, String offenderName, String offenderMobNo, String challanType,
			Long orgid) {
		
		final StringBuilder builder = new StringBuilder();
		builder.append("select i from EChallanMasterEntity i where i.challanType=:challanType and i.orgid=:orgid");
		
		if(raidNo != null && !raidNo.equals("")) {
			builder.append(" and i.raidNo=:raidNo");
		}
		if(offenderName != null && !offenderName.equals("")) {
			builder.append(" and i.offenderName LIKE :offenderName");
		}
		if(offenderMobNo != null && !offenderMobNo.equals("")) {
			builder.append(" and i.offenderMobNo=:offenderMobNo");
		}
		
		final Query query = createQuery(builder.toString());
		query.setParameter("challanType", challanType);
		query.setParameter("orgid", orgid);
		
		if(raidNo != null && !raidNo.equals("")) {
			query.setParameter("raidNo", raidNo);
		}
		if(offenderName != null && !offenderName.equals("")) {
			query.setParameter("offenderName", offenderName+"%");
		}
		if(offenderMobNo != null && !offenderMobNo.equals("")) {
			query.setParameter("offenderMobNo", offenderMobNo);
		}
		
		List<EChallanMasterEntity> raidData = (List<EChallanMasterEntity>)query.getResultList();	
		return raidData;
	}

}
