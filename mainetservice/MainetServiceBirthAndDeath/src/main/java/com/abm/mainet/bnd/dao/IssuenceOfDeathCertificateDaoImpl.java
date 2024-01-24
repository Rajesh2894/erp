package com.abm.mainet.bnd.dao;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.abm.mainet.bnd.domain.BirthDeathCFCInterface;
import com.abm.mainet.bnd.domain.TbDeathreg;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;

/**
 * 
 * @author vishwanath.s
 *
 */
@Repository
public class IssuenceOfDeathCertificateDaoImpl extends AbstractDAO<TbDeathreg>
		implements IssuenceOfDeathCertificateDao {

	Logger logger=Logger.getLogger(IssuenceOfDeathCertificateDaoImpl.class);
	@Override
	public TbDeathreg getDeathRegisteredApplicantList(String certNo, String regNo, String regDate, String applicnId,
			Long orgId,String DeathWFStatus) {
		final StringBuilder builder = new StringBuilder();
		TbDeathreg birthRegDetail = null;
		builder.append("select dr from TbDeathreg dr where dr.orgId=:orgId ");
		Long BrIdOrDrID = null;
		if ((certNo != null) && !(MainetConstants.BLANK.equals(certNo))) {
			builder.append(" and dr.drCertNo=:drCertNo ");
		}
		/*if ((regNo != null) && !((MainetConstants.BLANK).equals(regNo))) {
			builder.append(" and dr.drRegno=:drRegno ");
			builder.append(" and to_char(dr.drRegdate,'YYYY') =:regDate");
		}*/
		if ((applicnId != null) && !((MainetConstants.BLANK).equals(applicnId.toString()))) {
			String querybuilder = "select bdcfc.bdRequestId from BirthDeathCFCInterface bdcfc where bdcfc.apmApplicationId=:apmApplicationId and  bdcfc.orgId=:orgId";
			final Query query = createQuery(querybuilder.toString());
			query.setParameter("apmApplicationId", Long.valueOf(applicnId));
			query.setParameter("orgId", orgId);
			Long  BirthDeathRegDetail=null;
			try {
			 BirthDeathRegDetail = (Long) query.getSingleResult();
			 }
			 catch(Exception e) {
				 logger.error("No record found for given criteria",e);
			 }
			 
			if (BirthDeathRegDetail != null) {
				BrIdOrDrID = BirthDeathRegDetail;
			}
			builder.append(" and dr.drId=:drId ");
		}
		if((DeathWFStatus != null) && !(MainetConstants.BLANK.equals(DeathWFStatus))) {
			builder.append(" and dr.DeathWFStatus=:DeathWFStatus ");
		}

		final Query query = createQuery(builder.toString());
		query.setParameter("orgId", orgId);
		
		if ((certNo != null) && !(MainetConstants.BLANK.equals(certNo))) {
			query.setParameter("drCertNo", certNo);
		}
		if (BrIdOrDrID != null) {
			query.setParameter("drId", BrIdOrDrID);
		}
		/*if ((regNo != null) && !((MainetConstants.BLANK).equals(regNo))) {
			query.setParameter("brRegNo", regNo);
			query.setParameter("regDate", regDate);
		}*/
		// query.setParameter("authFlag", "A");
		if ((DeathWFStatus != null) && !((MainetConstants.BLANK).equals(DeathWFStatus))) {
			query.setParameter("DeathWFStatus",DeathWFStatus);
		}
		try {
			birthRegDetail = (TbDeathreg) query.getSingleResult();
		} catch (Exception e) {
		logger.error("No record found for given criteria",e);
		}
		return birthRegDetail;
	}
	@Override
	public Long getNoOfCopies(String applicnId, Long orgId) {
		BirthDeathCFCInterface birthDeathCFCInterface=null;
		if ((applicnId != null) && !((MainetConstants.BLANK).equals(applicnId.toString()))) {
			String querybuilder = "from BirthDeathCFCInterface bdcfc where bdcfc.apmApplicationId=:apmApplicationId and  bdcfc.orgId=:orgId";
			final Query query = createQuery(querybuilder.toString());
			query.setParameter("apmApplicationId", Long.valueOf(applicnId));
			query.setParameter("orgId", orgId);
			birthDeathCFCInterface = (BirthDeathCFCInterface) query.getSingleResult();
		}
		
		return birthDeathCFCInterface.getCopies();
	}
}
