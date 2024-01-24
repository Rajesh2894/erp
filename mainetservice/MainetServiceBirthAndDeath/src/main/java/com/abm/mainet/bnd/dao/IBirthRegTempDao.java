package com.abm.mainet.bnd.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.bnd.constant.BndConstants;
import com.abm.mainet.bnd.domain.BirthRegistrationEntityTemp;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.utility.Utility;

@Repository
public class IBirthRegTempDao extends AbstractDAO<BirthRegistrationEntityTemp> implements IBirthRegDraftTempDao{

	
	@SuppressWarnings("unchecked")
	@Override
	public List<BirthRegistrationEntityTemp> getBirthRegisteredApplicantListTemp(final String certNo, final String brRegNo,
			final String year,final Date brDob,final String brChildName,Long smServiceId, final String applnId, Long orgId) {
		final StringBuilder builder = new StringBuilder();
		//BirthRegistrationEntity birthRegDetail = null;
		builder.append("select br from BirthRegistrationEntityTemp br where br.orgId=:orgId");
		Long BrIdOrDrID = null;
		String s1 = BndConstants.STRART_DATE+year;
		String s2 = BndConstants.TO_DATE+year;
		Date d1 = Utility.stringToDate(s1);
		Date d2 = Utility.stringToDate(s2);
		
		
		if ((certNo != null) && !(MainetConstants.BLANK.equals(certNo))) {
			builder.append(" and br.brCertNo:=brCertNo ");
		}
		if ((brRegNo != null) && !((MainetConstants.BLANK).equals(brRegNo))) {
			builder.append(" and br.brRegNo=:brRegNo ");
			//builder.append(" and to_char(br_regdate,'YYYY') =:brRegDate");
		}
		if((year != null) && !((MainetConstants.BLANK).equals(year))) {
			builder.append(" and br.brRegDate between :fromdate and :todate");
		}
		if ((applnId != null) && !((MainetConstants.BLANK).equals(applnId.toString()))) {
			String querybuilder = "select bdcfc.bdRequestId from BirthDeathCFCInterface bdcfc where bdcfc.apmApplicationId=:apmApplicationId and bdcfc.orgId=:orgId and bdcfc.smServiceId=:smServiceId";
			final Query query = entityManager.createQuery(querybuilder.toString());
			query.setParameter("apmApplicationId", Long.valueOf(applnId));
			query.setParameter("orgId", orgId);
			query.setParameter("smServiceId", smServiceId);
			Long BirthDeathRegDetail = (Long) query.getSingleResult();
			if (BirthDeathRegDetail != null) {
				BrIdOrDrID = BirthDeathRegDetail;
			}
			builder.append(" and br.brId=:brId ");
		}
		
		
		if((brChildName != null) && !(MainetConstants.BLANK.equals(brChildName))) {
			builder.append(" and br.brChildName Like :brChildName ");
			}

			if((brDob != null)) {
			builder.append(" and br.brDob =:brDob");
			}
		
		final Query query = createQuery(builder.toString());
		query.setParameter("orgId", orgId);

		if ((certNo != null) && !(MainetConstants.BLANK.equals(certNo))) {
			query.setParameter("brCertNo", certNo);
		}
		if ((BrIdOrDrID != null) && !((MainetConstants.BLANK).equals(BrIdOrDrID.toString()))) {
			query.setParameter("brId", BrIdOrDrID);
		}
		if ((brRegNo != null) && !((MainetConstants.BLANK).equals(brRegNo))) {
			query.setParameter("brRegNo", brRegNo);
			//query.setParameter("regDate", year);
		}
		if ((brChildName != null) && !(MainetConstants.BLANK.equals(brChildName))) {  
			query.setParameter("brChildName", brChildName+"%");
			}

	   if((brDob != null) ) {
			query.setParameter("brDob", brDob);
			}
		
	   if((year != null) && !((MainetConstants.BLANK).equals(year))) {
			query.setParameter("fromdate", d1);
			query.setParameter("todate", d2);
		}
			
			List<BirthRegistrationEntityTemp> birthRegDetail =query.getResultList();
		
			return birthRegDetail;
			}
}
