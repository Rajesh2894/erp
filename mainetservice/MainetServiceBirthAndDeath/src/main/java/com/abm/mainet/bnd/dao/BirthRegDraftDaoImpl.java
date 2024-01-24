package com.abm.mainet.bnd.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import com.abm.mainet.bnd.domain.BirthRegdraftEntity;
import com.abm.mainet.bnd.domain.TbDeathRegdraft;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;

@Repository
public class BirthRegDraftDaoImpl extends AbstractDAO<BirthRegdraftEntity> implements IBirthRegDraftDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<BirthRegdraftEntity> getDraftDetail(Long orgId) {
		final StringBuilder builder = new StringBuilder();
		builder.append("select br from BirthRegdraftEntity br where br.orgId=:orgId");
		final Query query = createQuery(builder.toString());
		query.setParameter("orgId", orgId);
		List<BirthRegdraftEntity> birthRegdraftEntityList =query.getResultList();
		Long brdraftids[]=new Long[birthRegdraftEntityList.size()];;
 		for(int i=0;i< birthRegdraftEntityList.size();i++) {
			brdraftids[i]= birthRegdraftEntityList.get(i).getBrDraftId();
		}
 		StringBuilder querybuilderforApplition=new StringBuilder();
		querybuilderforApplition.append("select bdcfc.apmApplicationId ,bdcfc.bdRequestId from BirthDeathCFCInterface bdcfc where bdcfc.orgId=:orgId");
		//query.setParameter("orgId", orgId);
		StringBuilder str=new StringBuilder();
		if(brdraftids.length>0) {
			for(int i=0;i<brdraftids.length;i++){
				str=str.append("'"+brdraftids[i]+"'");
				if(!(i==(brdraftids.length-1)))
				str=str.append(",");
			}
		}
		if (str != null && !(str.toString().isEmpty())) {
			querybuilderforApplition.append(" and bdcfc.bdRequestId in(" + str + ")");
		}

		final Query queryForapplicationID = entityManager.createQuery(querybuilderforApplition.toString());
		queryForapplicationID.setParameter("orgId", orgId);
		List<Object[]> birthDraftRegDetail = queryForapplicationID.getResultList();
		if (!birthRegdraftEntityList.isEmpty()) {
			for (Object[] Object : birthDraftRegDetail) {
				for (BirthRegdraftEntity obj : birthRegdraftEntityList) {
					if (obj.getBrDraftId().equals(Object[1])) {
						if (Object[0] != null) {
							obj.setApplnId(Long.valueOf(Object[0].toString()));
						}
					}
				}
			}
		}
		List<BirthRegdraftEntity> list = new ArrayList<BirthRegdraftEntity>();
		if (!birthRegdraftEntityList.isEmpty()) {
			birthRegdraftEntityList.forEach(entity -> {
				if (entity.getApplnId() != null) {
					list.add(entity);
				}

			});

		}

		return list;
 	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BirthRegdraftEntity> getBirthRegDraftAppliDetail(Long applnId, Date brDob, Long orgId) {
		final StringBuilder builder = new StringBuilder();

		builder.append("select br from BirthRegdraftEntity br where br.orgId=:orgId");

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
			builder.append(" and br.brDraftId=:brDraftId ");
		}

		if ((brDob != null)) {// && !(MainetConstants.BLANK.equals(drDod))
			builder.append(" and br.brDob =:brDob");
		}

		final Query query = createQuery(builder.toString());
		query.setParameter("orgId", orgId);

		if ((BrIdOrDrID != null) && !((MainetConstants.BLANK).equals(BrIdOrDrID.toString()))) {
			query.setParameter("brDraftId", BrIdOrDrID);
		}

		if ((brDob != null)) {
			query.setParameter("brDob", brDob);
		}

		List<BirthRegdraftEntity> birthregDraft =query.getResultList();
		Long brDraftids[]=new Long[birthregDraft.size()];
		for(int i=0;i< birthregDraft.size();i++) {
			brDraftids[i]= birthregDraft.get(i).getBrDraftId();
		}
		if (((applnId != null) && !((MainetConstants.BLANK).equals(applnId.toString())))||(brDob != null)) {  
			StringBuilder querybuilderforApplition=new StringBuilder();
			querybuilderforApplition.append("select bdcfc.apmApplicationId ,bdcfc.bdRequestId from BirthDeathCFCInterface bdcfc where bdcfc.orgId=:orgId");
			//query.setParameter("orgId", orgId);
			StringBuilder str=new StringBuilder();
			if(brDraftids.length>0) {
				for(int i=0;i<brDraftids.length;i++){
					str=str.append("'"+brDraftids[i]+"'");
					if(!(i==(brDraftids.length-1)))
					str=str.append(",");
					
			}
				querybuilderforApplition.append(" and bdcfc.bdRequestId in("+str+")");

			}
			final Query queryForapplicationID = entityManager.createQuery(querybuilderforApplition.toString());
			queryForapplicationID.setParameter("orgId", orgId);
			List<Object[]> birthDraftRegDetail = queryForapplicationID.getResultList();
			for(Object[] Object:birthDraftRegDetail) {
	               for(BirthRegdraftEntity obj: birthregDraft) {
	            	   if(obj.getBrDraftId().equals(Object[1])) {
	            		   if(Object[0]!=null) {
	            		   obj.setApplnId(Long.valueOf(Object[0].toString()));
	            		   }
	            	   }
	               }
			}
	
		}
		return birthregDraft;
	}

}
