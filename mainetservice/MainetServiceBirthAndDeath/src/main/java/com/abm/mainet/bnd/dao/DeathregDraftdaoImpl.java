package com.abm.mainet.bnd.dao;

import static com.abm.mainet.common.constant.PrefixConstants.MobilePreFix.GENDER;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import com.abm.mainet.bnd.domain.TbDeathRegdraft;
import com.abm.mainet.bnd.domain.TbDeathreg;
import com.abm.mainet.bnd.dto.TbDeathRegdraftDto;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;

@Repository
public class DeathregDraftdaoImpl extends AbstractDAO<TbDeathRegdraft> implements IDeathRegDraftDao {

	@Override
	@SuppressWarnings("unchecked")
	public List<TbDeathRegdraft> getDeathRegisteredAppliDetail(Long applnId, Date drDod, Long orgId) {
		final StringBuilder builder = new StringBuilder();

		builder.append("select dr from TbDeathRegdraft dr where dr.orgId=:orgId");

		Long BrIdOrDrID = null;
		if ((applnId != null) && !((MainetConstants.BLANK).equals(applnId.toString()))) {
			String querybuilder = "select bdcfc.bdRequestId from BirthDeathCFCInterface bdcfc where bdcfc.apmApplicationId=:apmApplicationId and bdcfc.orgId=:orgId";
			final Query query = entityManager.createQuery(querybuilder.toString());
			query.setParameter("apmApplicationId", Long.valueOf(applnId));
			query.setParameter("orgId", orgId);
			Long BirthDeathRegDetail = (Long) query.getSingleResult();
			if (BirthDeathRegDetail != null) {
				BrIdOrDrID = BirthDeathRegDetail;
			}
			builder.append(" and dr.drDraftId=:drDraftId ");
		}

		if ((drDod != null)) {// && !(MainetConstants.BLANK.equals(drDod))
			builder.append(" and dr.drDod =:drDod");
		}

		final Query query = createQuery(builder.toString());
		query.setParameter("orgId", orgId);

		if ((BrIdOrDrID != null) && !((MainetConstants.BLANK).equals(BrIdOrDrID.toString()))) {
			query.setParameter("drDraftId", BrIdOrDrID);
		}

		if ((drDod != null)) {
			query.setParameter("drDod", drDod);
		}

		List<TbDeathRegdraft> tbDeathreg =query.getResultList();
		Long drDraftids[]=new Long[tbDeathreg.size()];
		for(int i=0;i< tbDeathreg.size();i++) {
			drDraftids[i]= tbDeathreg.get(i).getDrDraftId();
		}
		if (((applnId != null) && !((MainetConstants.BLANK).equals(applnId.toString())))||(drDod != null)) {  
			StringBuilder querybuilderforApplition=new StringBuilder();
			querybuilderforApplition.append("select bdcfc.apmApplicationId ,bdcfc.bdRequestId from BirthDeathCFCInterface bdcfc where bdcfc.orgId=:orgId");
			//query.setParameter("orgId", orgId);
			StringBuilder str=new StringBuilder();
			if(drDraftids.length>0) {
				for(int i=0;i<drDraftids.length;i++){
					str=str.append("'"+drDraftids[i]+"'");
					if(!(i==(drDraftids.length-1)))
					str=str.append(",");
					
			   }
			}
			
			if(str!=null && !(str.toString().isEmpty())){
				querybuilderforApplition.append(" and bdcfc.bdRequestId in("+str+")");
			}
			
			final Query queryForapplicationID = entityManager.createQuery(querybuilderforApplition.toString());
			queryForapplicationID.setParameter("orgId", orgId);
			List<Object[]> BirthDeathRegDetail = queryForapplicationID.getResultList();
			for(Object[] Object:BirthDeathRegDetail) {
	               for(TbDeathRegdraft obj: tbDeathreg) {
	            	   if(obj.getDrDraftId().equals(Object[1])) {
	            		   obj.setApplnId(Long.valueOf(Object[0].toString()));
	            	   }
	               }
			}
	
		}
		return tbDeathreg;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TbDeathRegdraft> getDraftDetail(Long orgId) {
	
		final StringBuilder builder = new StringBuilder();

		builder.append("select dr from TbDeathRegdraft dr where dr.orgId=:orgId");
		final Query query = createQuery(builder.toString());
		query.setParameter("orgId", orgId);
		List<TbDeathRegdraft> tbDeathRegdraftList =query.getResultList();
		Long drdraftids[]=new Long[tbDeathRegdraftList.size()];
 		for(int i=0;i< tbDeathRegdraftList.size();i++) {
			drdraftids[i]= tbDeathRegdraftList.get(i).getDrDraftId();
		}
		
		StringBuilder querybuilderforApplition=new StringBuilder();
		querybuilderforApplition.append("select bdcfc.apmApplicationId ,bdcfc.bdRequestId from BirthDeathCFCInterface bdcfc where bdcfc.orgId=:orgId");
		//query.setParameter("orgId", orgId);
		StringBuilder str=new StringBuilder();
		if(drdraftids.length>0) {
			for(int i=0;i<drdraftids.length;i++){
				str=str.append("'"+drdraftids[i]+"'");
				if(!(i==(drdraftids.length-1)))
				str=str.append(",");
			}
		}
		
		     if(str!=null && !(str.toString().isEmpty())){
				querybuilderforApplition.append(" and bdcfc.bdRequestId in("+str+")");
		     }
				
				final Query queryForapplicationID = entityManager.createQuery(querybuilderforApplition.toString());
				queryForapplicationID.setParameter("orgId", orgId);
				List<Object[]> DeathDraftRegDetail = queryForapplicationID.getResultList();
				if(!tbDeathRegdraftList.isEmpty()) {
				for(Object[] Object:DeathDraftRegDetail) {
		               for(TbDeathRegdraft obj: tbDeathRegdraftList) {
		            	   if(obj.getDrDraftId().equals(Object[1])) {
		            		   if(Object[0]!=null) {
			            		    obj.setApplnId(Long.valueOf(String.valueOf(Object[0])));
			            		    }
		            	   }
		               }
			    	}
				}
				
				List<TbDeathRegdraft> list = new ArrayList<TbDeathRegdraft>();
				if (!tbDeathRegdraftList.isEmpty()) {
					tbDeathRegdraftList.forEach(entity ->{
					if(entity.getApplnId()!=null) {
						list.add(entity);
					}
	
				});	
						
				}
		return list;

	}
}
