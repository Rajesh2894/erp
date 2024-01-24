package com.abm.mainet.bnd.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.bnd.constant.BndConstants;
import com.abm.mainet.bnd.domain.BirthRegistrationEntity;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.utility.Utility;

/**
 * 
 * @author vishwanath.s
 *
 */
@Repository
public class InclusionOfChildDaoImpl extends AbstractDAO<BirthRegistrationEntity>
		implements InclusionOfChildDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<BirthRegistrationEntity> getBirthRegisteredApplicantList(final String certNo, final String regNo,
			final String year, final Date brDob,final String applicnId,Long smServiceId,Long orgId) {
		final StringBuilder builder = new StringBuilder();
		String s1 = BndConstants.STRART_DATE + year;
		String s2 = BndConstants.TO_DATE + year;
		Date d1 = Utility.stringToDate(s1);
		Date d2 = Utility.stringToDate(s2);
		builder.append("select br from BirthRegistrationEntity br where br.orgId=:orgId"); 
		Long BrIdOrDrID = null;
		if ((certNo != null) && !(MainetConstants.BLANK.equals(certNo))) {
			builder.append(" and br.brCertNo:=brCertNo ");
		}
		if ((regNo != null) && !((MainetConstants.BLANK).equals(regNo))) {
			builder.append(" and br.brRegNo=:brRegNo ");
			//builder.append(" and to_char(br_regdate,'YYYY') =:regDate");
		}
		if((year != null) && !((MainetConstants.BLANK).equals(year))) {
			builder.append(" and br.brRegDate between :fromdate and :todate");
		}
		if ((applicnId != null) && !((MainetConstants.BLANK).equals(applicnId.toString()))) {
			String querybuilder = "select bdcfc.bdRequestId from BirthDeathCFCInterface bdcfc where bdcfc.apmApplicationId=:apmApplicationId and bdcfc.orgId=:orgId and bdcfc.smServiceId=:smServiceId ";
			final Query query = entityManager.createQuery(querybuilder.toString());
			query.setParameter("apmApplicationId", Long.valueOf(applicnId));
			query.setParameter("orgId",orgId);
			query.setParameter("smServiceId", smServiceId);
			Long BirthDeathRegDetail = (Long) query.getSingleResult();
			if (BirthDeathRegDetail != null) {
				BrIdOrDrID = BirthDeathRegDetail;
			}
			builder.append(" and br.brId=:brId ");
		}
		
		if((brDob != null)) {
			builder.append(" and br.brDob =:brDob");
			}
		
		final Query query = createQuery(builder.toString()); 

	    query.setParameter("orgId",orgId);
	    
		if ((certNo != null) && !(MainetConstants.BLANK.equals(certNo))) {
			query.setParameter("brCertNo", certNo);
		}
		if ((BrIdOrDrID != null) && !((MainetConstants.BLANK).equals(BrIdOrDrID.toString()))) {

			query.setParameter("brId", BrIdOrDrID);
		}
	   if ((regNo != null) && !((MainetConstants.BLANK).equals(regNo))) {
			query.setParameter("brRegNo", regNo);
			//query.setParameter("year", year);
		}
	   if((brDob != null) ) {
			query.setParameter("brDob", brDob);
			}
	   if((year != null) && !((MainetConstants.BLANK).equals(year))) {
			query.setParameter("fromdate", d1);
			query.setParameter("todate", d2);
		}
	   List<BirthRegistrationEntity> birthRegDetail=null;
		  //query.setParameter("authFlag", "A")
		  try {
	      birthRegDetail = query.getResultList();
		  }
		  catch(Exception e) {
			  
		  }
		return birthRegDetail;
	}

	@Override
	@Transactional
	public void updateBirthRegEntity(Long brId, String childName, String childNameMar, Long orgId) {
        Query query = createQuery("update BirthRegistrationEntity as b set b.brChildName =:brChildName, b.brChildNameMar =:brChildNameMar where b.brId =:brId and b.orgId =:orgId");
        query.setParameter("brId", brId);
        query.setParameter("brChildName", childName);   
        query.setParameter("brChildNameMar", childNameMar);          
        query.setParameter("orgId",orgId);     
        query.executeUpdate();  
		
	}
	
	@Override
	@Transactional
	public void updateBirthRegEntityOnApproval(Long brId, String brChildName, String brChildNameMar,
			String birthWFStatus, String brStatus, String brCorrectionFlg, Date brCorrnDate, Long orgId) {
		
        Query query = createQuery("update BirthRegistrationEntity as b set b.brChildName =:brChildName, b.brChildNameMar =:brChildNameMar, b.birthWFStatus =:birthWFStatus, b.brStatus =:brStatus, b.brCorrectionFlg =:brCorrectionFlg, b.brCorrnDate =:brCorrnDate where b.brId =:brId and b.orgId =:orgId");
	        query.setParameter("brId", brId);
	        query.setParameter("brChildName", brChildName);   
	        query.setParameter("brChildNameMar", brChildNameMar);   
	        query.setParameter("birthWFStatus", birthWFStatus);
	        query.setParameter("brStatus", brStatus);
	        query.setParameter("brCorrectionFlg", brCorrectionFlg);
	        query.setParameter("brCorrnDate", brCorrnDate); 
	        query.setParameter("orgId",orgId);     
        query.executeUpdate();  
		
	}

	
}
