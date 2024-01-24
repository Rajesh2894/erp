package com.abm.mainet.bnd.dao;

import javax.persistence.Query;
import org.springframework.stereotype.Repository;

import com.abm.mainet.bnd.domain.BirthDeathCFCInterface;
import com.abm.mainet.bnd.domain.BirthRegistrationEntity;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;

/**
 * 
 * @author vishwanath.s
 *
 */
@Repository
public class IssuenceOfBirthCertificateDaoImpl extends AbstractDAO<BirthRegistrationEntity>
		implements IssuenceOfBirthCertificateDao {

	@Override
	public BirthRegistrationEntity getBirthRegisteredApplicantList(final String certNo, final String regNo,
			final String regDate, final String applicnId,Long orgId) {
		final StringBuilder builder = new StringBuilder();
		BirthRegistrationEntity birthRegDetail=null;
		builder.append("select br from BirthRegistrationEntity br where br.orgId=:orgId"); 
		Long BrIdOrDrID = null;
		if ((certNo != null) && !(MainetConstants.BLANK.equals(certNo))) {
			builder.append(" and br.brCertNo:=brCertNo ");   //need to be change brManualCertNo
		}
		if ((regNo != null) && !((MainetConstants.BLANK).equals(regNo))) {
			builder.append(" and br.brRegNo=:brRegNo ");
			builder.append(" and to_char(br_regdate,'YYYY') =:regDate");
		}
		if ((applicnId != null) && !((MainetConstants.BLANK).equals(applicnId.toString()))) {
			String querybuilder = "select bdcfc.bdRequestId from BirthDeathCFCInterface bdcfc where bdcfc.apmApplicationId=:apmApplicationId and bdcfc.orgId=:orgId";
			final Query query = entityManager.createQuery(querybuilder.toString());
			query.setParameter("apmApplicationId", Long.valueOf(applicnId));
			query.setParameter("orgId",orgId);
			Long BirthDeathRegDetail = (Long) query.getSingleResult();
			if (BirthDeathRegDetail != null) {
				BrIdOrDrID = BirthDeathRegDetail;
			}
			builder.append(" and br.brId=:brId ");
		}
		
		final Query query = createQuery(builder.toString()); 

	    query.setParameter("orgId",orgId);
	    
		if ((certNo != null) && !(MainetConstants.BLANK.equals(certNo))) {
			query.setParameter("brCertNo", certNo);     //need to be change brManualCertNo
		}
		if ((BrIdOrDrID != null) && !((MainetConstants.BLANK).equals(BrIdOrDrID.toString()))) {

			query.setParameter("brId", BrIdOrDrID);
		}
	   if ((regNo != null) && !((MainetConstants.BLANK).equals(regNo))) {
			query.setParameter("brRegNo", regNo);
			query.setParameter("regDate", regDate);
		}
		
	 
		  //query.setParameter("authFlag", "A")
		  try {
	      birthRegDetail = (BirthRegistrationEntity) query.getSingleResult();
		  }
		  catch(Exception e) {
			  
		  }
		return birthRegDetail;
	}

	@Override
	public Long getNoOfRequestCopies(String applicnId, Long orgId) {
		BirthDeathCFCInterface  BirthDeathRegDetail=null;
		if ((applicnId != null) && !((MainetConstants.BLANK).equals(applicnId.toString()))) {
			String querybuilder = "from BirthDeathCFCInterface bdcfc where bdcfc.apmApplicationId=:apmApplicationId and bdcfc.orgId=:orgId";
			final Query query = entityManager.createQuery(querybuilder.toString());
			query.setParameter("apmApplicationId", Long.valueOf(applicnId));
			query.setParameter("orgId",orgId);
		   BirthDeathRegDetail =(BirthDeathCFCInterface)query.getSingleResult();
		   
	}
		return BirthDeathRegDetail.getCopies();
	}
	
	
}
