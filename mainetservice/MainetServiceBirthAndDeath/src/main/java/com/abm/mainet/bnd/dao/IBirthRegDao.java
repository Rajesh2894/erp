package com.abm.mainet.bnd.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.abm.mainet.bnd.constant.BndConstants;
import com.abm.mainet.bnd.domain.BirthRegistrationCorrection;
import com.abm.mainet.bnd.domain.BirthRegistrationEntity;
import com.abm.mainet.bnd.domain.ParentDetail;
import com.abm.mainet.bnd.dto.BirthRegistrationDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.utility.Utility;

@Repository
public class IBirthRegDao extends AbstractDAO<BirthRegistrationEntity> implements BirthRegDao {

	private static final Logger logger = Logger.getLogger(IBirthRegDao.class);

	@Override
	public BirthRegistrationEntity getBirthRegApplnDetails(String certno, Long regNo, String year,
			String brApplicationId, Long orgId) {

		final StringBuilder builder = new StringBuilder();
		builder.append("select br from BirthRegistrationEntity br where br.orgId=:orgId ");

		if ((certno != null) && !((MainetConstants.BLANK).equals(certno))) {
			builder.append(" and br.brCertNo=:brCertNo ");
		}

		if ((regNo != null) && regNo != 0) {
			builder.append(" and br.brRegNo=:brRegNo ");
		}

		if ((year != null) && !((MainetConstants.BLANK).equals(year))) {
			builder.append(" and br.brRegDate=:brRegDate ");
		}

		Long BrIdOrDrID = 0L;
		if ((brApplicationId != null) && !((MainetConstants.BLANK).equals(brApplicationId.toString()))) {
			String querybuilder = "select bdcfc.bdRequestId from BirthDeathCFCInterface bdcfc where bdcfc.apmApplicationId=:apmApplicationId and bdcfc.orgId=:orgId ";
			final Query query = entityManager.createQuery(querybuilder.toString());
			query.setParameter("apmApplicationId", Long.valueOf(brApplicationId));
			query.setParameter("orgId", orgId);
			Long BirthDeathRegDetail = null;
			try {
				BirthDeathRegDetail = (Long) query.getSingleResult();
			} catch (Exception e) {
				logger.error("Error Occurred while fetching result from BirthDeathCFCInterface", e);
			}
			if (BirthDeathRegDetail != null) {
				BrIdOrDrID = BirthDeathRegDetail;
			}
			builder.append(" and br.brId=:brId ");
		}

		final Query query = createQuery(builder.toString());
		if ((regNo != null) && regNo != 0) {
			query.setParameter("brId", regNo);
		}

		if ((brApplicationId != null) && !((MainetConstants.BLANK).equals(brApplicationId.toString()))) {
			query.setParameter("brId", BrIdOrDrID);
		}

		if ((orgId != null) && orgId != 0) {
			query.setParameter("orgId", orgId);
		}
		BirthRegistrationEntity birthRegDetail = null;
		try {
			birthRegDetail = (BirthRegistrationEntity) query.getSingleResult();
		} catch (Exception e) {
			logger.error("Error Occurred while fetching result from BirthRegistrationEntity", e);
		}
		return birthRegDetail;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BirthRegistrationEntity> getBirthRegisteredApplicantList(final String certNo, final String brRegNo,
			final String year,final Date brDob,final String brChildName,Long smServiceId, final String applnId, Long orgId) {
		final StringBuilder builder = new StringBuilder();
		//BirthRegistrationEntity birthRegDetail = null;
		builder.append("select br from BirthRegistrationEntity br where br.orgId=:orgId");
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
			
			List<BirthRegistrationEntity> birthRegDetail =query.getResultList();
		/*
		 * Long brids[]=new Long[birthRegDetail.size()];; for(int i=0;i<
		 * birthRegDetail.size();i++) {
		 * 
		 * brids[i]= birthRegDetail.get(i).getBrId();
		 * 
		 * }
		 * 
		 * if (((brChildName != null) && !(MainetConstants.BLANK.equals(brChildName)))
		 * || ((applnId != null) &&
		 * !((MainetConstants.BLANK).equals(applnId.toString()))) ||(brDob !=
		 * null)||((brRegNo != null) && !((MainetConstants.BLANK).equals(brRegNo)))) {
		 * StringBuilder querybuilderforApplition=new StringBuilder();
		 * querybuilderforApplition.
		 * append("select bdcfc.apmApplicationId ,bdcfc.bdRequestId from BirthDeathCFCInterface bdcfc where bdcfc.orgId=:orgId and bdcfc.smServiceId=:smServiceId"
		 * ); query.setParameter("orgId", orgId); StringBuilder str=new StringBuilder();
		 * if(brids.length>0) { for(int i=0;i<brids.length;i++){
		 * str=str.append("'"+brids[i]+"'"); if(!(i==(brids.length-1)))
		 * str=str.append(","); }
		 * querybuilderforApplition.append(" and bdcfc.bdRequestId in("+str+")"); }
		 * final Query queryForapplicationID =
		 * entityManager.createQuery(querybuilderforApplition.toString());
		 * queryForapplicationID.setParameter("orgId", orgId);
		 * queryForapplicationID.setParameter("smServiceId", smServiceId);
		 * List<Object[]> BirthDeathRegDetail = queryForapplicationID.getResultList();
		 * for(Object[] Object:BirthDeathRegDetail) { for(BirthRegistrationEntity obj:
		 * birthRegDetail) { if(obj.getBrId().equals(Object[1])) {
		 * obj.setApplnId(Long.valueOf(Object[0].toString())); } } } }
		 */

			return birthRegDetail;
			}
	

	@Override
	public List<BirthRegistrationCorrection> getBirthRegisteredAppliDetailFromApplnId (Long applnId, Long orgId) {

		final StringBuilder builder = new StringBuilder();
		builder.append("select dr from BirthRegistrationCorrection dr where dr.orgId=:orgId");
		
		if(applnId != null) {
			builder.append(" and dr.apmApplicationId=:apmApplicationId order by dr.brCorrId desc ");
		}
		
		final Query query = createQuery(builder.toString());
		query.setParameter("orgId", orgId);
		
		if(applnId != null) {
			query.setParameter("apmApplicationId", applnId);
		}
		List<BirthRegistrationCorrection> tbBirthregCorrMas = query.getResultList();
		return tbBirthregCorrMas;
		
	}

	
	@Override
	public List<BirthRegistrationEntity> getBirthRegApplnData(Long brId, Long orgId) {

		final StringBuilder builder = new StringBuilder();
		builder.append("select dr from BirthRegistrationEntity dr where dr.orgId=:orgId");
		
		if(brId != null) {
			builder.append(" and dr.brId=:brId ");
		}
		
		final Query query = createQuery(builder.toString());
		query.setParameter("orgId", orgId);
		
		if(brId != null) {
			query.setParameter("brId", brId);
		}
		List<BirthRegistrationEntity> tbBirthregEntity = query.getResultList();
		return tbBirthregEntity;
		
	}

	@Override
	public List<ParentDetail> getParentDtlApplnData(Long brId, Long orgId) {
		
		final StringBuilder builder = new StringBuilder();
		builder.append("select dr from ParentDetail dr where dr.orgId=:orgId");
		
		if(brId != null) {
			builder.append(" and dr.brId=:brId ");
		}
		
		final Query query = createQuery(builder.toString());
		query.setParameter("orgId", orgId);
		
		if(brId != null) {
			query.setParameter("brId", brId);
		}
		List<ParentDetail> parentDtlEntity = query.getResultList();
		return parentDtlEntity;
		
	}

	@Override
	public List<BirthRegistrationEntity> getBirthRegCorrDetList(BirthRegistrationDTO birthRegDto) {
			final StringBuilder builder = new StringBuilder();
			builder.append("select br from BirthRegistrationEntity br where br.orgId=:orgId");
			Long BrIdOrDrID = null;
			String s1 = BndConstants.STRART_DATE+birthRegDto.getYear();
			String s2 = BndConstants.TO_DATE+birthRegDto.getYear();
			Date d1 = Utility.stringToDate(s1);
			Date d2 = Utility.stringToDate(s2);
			
			
			if ((birthRegDto.getBrCertNo() != null) && !(MainetConstants.BLANK.equals(birthRegDto.getBrCertNo()))) {
				builder.append(" and br.brCertNo:=brCertNo ");
			}
			if ((birthRegDto.getBrRegNo() != null) && !((MainetConstants.BLANK).equals(birthRegDto.getBrRegNo()))) {
				builder.append(" and br.brRegNo=:brRegNo ");
			}
			if((birthRegDto.getYear() != null) && !((MainetConstants.BLANK).equals(birthRegDto.getYear()))) {
				builder.append(" and br.brRegDate between :fromdate and :todate");
			}
			if (birthRegDto.getApplnId() != null ) {
				String querybuilder = "select bdcfc.bdRequestId from BirthDeathCFCInterface bdcfc where bdcfc.apmApplicationId=:apmApplicationId and bdcfc.orgId=:orgId and bdcfc.smServiceId=:smServiceId";
				final Query query = entityManager.createQuery(querybuilder.toString());
				query.setParameter("apmApplicationId", Long.valueOf(birthRegDto.getApplnId()));
				query.setParameter("orgId", birthRegDto.getOrgId());
				query.setParameter("smServiceId", birthRegDto.getServiceId());
				Long BirthDeathRegDetail = (Long) query.getSingleResult();
				if (BirthDeathRegDetail != null) {
					BrIdOrDrID = BirthDeathRegDetail;
				}
				builder.append(" and br.brId=:brId ");
			}
			
			
			if((birthRegDto.getBrChildName() != null) && !(MainetConstants.BLANK.equals(birthRegDto.getBrChildName()))) {
				builder.append(" and br.brChildName Like :brChildName ");
				}

				if((birthRegDto.getBrDob() != null)) {
				builder.append(" and br.brDob =:brDob");
				}
				
				if(StringUtils.isNotBlank(birthRegDto.getBrSex())&& !(birthRegDto.getBrSex().equals("0"))) {
					builder.append(" and br.brSex =:brSex");
					}
				if((birthRegDto.getHiId() != null) && (birthRegDto.getHiId() != 0L)) {
					builder.append(" and br.hiId =:hiId");
					}
				
				if(StringUtils.isNotBlank(birthRegDto.getParentDetailDTO().getPdFathername())) {
					builder.append(" and br.parentDetail.pdFathername Like :pdFathername");
					}
				
				if(StringUtils.isNotBlank(birthRegDto.getParentDetailDTO().getPdMothername())) {
					builder.append(" and br.parentDetail.pdMothername Like :pdMothername");
					}
			
			final Query query = createQuery(builder.toString());
			query.setParameter("orgId", birthRegDto.getOrgId());

			if ((birthRegDto.getBrCertNo() != null) && !(MainetConstants.BLANK.equals(birthRegDto.getBrCertNo()))) {
				query.setParameter("brCertNo", birthRegDto.getBrCertNo());
			}
			if ((BrIdOrDrID != null) && !((MainetConstants.BLANK).equals(BrIdOrDrID.toString()))) {
				query.setParameter("brId", BrIdOrDrID);
			}
			if ((birthRegDto.getBrRegNo() != null) && !((MainetConstants.BLANK).equals(birthRegDto.getBrRegNo()))) {
				query.setParameter("brRegNo", birthRegDto.getBrRegNo());
			}
			if ((birthRegDto.getBrChildName() != null) && !(MainetConstants.BLANK.equals(birthRegDto.getBrChildName()))) {  
				query.setParameter("brChildName", birthRegDto.getBrChildName()+"%");
				}

		   if((birthRegDto.getBrDob() != null) ) {
				query.setParameter("brDob", birthRegDto.getBrDob());
				}
			
		   if((birthRegDto.getYear() != null) && !((MainetConstants.BLANK).equals(birthRegDto.getYear()))) {
				query.setParameter("fromdate", d1);
				query.setParameter("todate", d2);
			}
		   
		   if(StringUtils.isNotBlank(birthRegDto.getBrSex())&& !(birthRegDto.getBrSex().equals("0"))) {
				query.setParameter("brSex", birthRegDto.getBrSex());
				}
			if((birthRegDto.getHiId() != null) && (birthRegDto.getHiId() != 0L)) {
				query.setParameter("hiId", birthRegDto.getHiId());
				}
				
			if(StringUtils.isNotBlank(birthRegDto.getParentDetailDTO().getPdFathername())) {
				query.setParameter("pdFathername", "%"+birthRegDto.getParentDetailDTO().getPdFathername()+"%");
				}
			
			if(StringUtils.isNotBlank(birthRegDto.getParentDetailDTO().getPdMothername())) {
				query.setParameter("pdMothername", "%"+birthRegDto.getParentDetailDTO().getPdMothername()+"%");
				}
				List<BirthRegistrationEntity> birthRegDetail =query.getResultList();

				return birthRegDetail;
	}

	@Override
	public List<Object[]> getApplicantDetailsByApplNoAndOrgId(Long applnNO, Long orgId) {
		final StringBuilder queryString = new StringBuilder();
		queryString.append("select ad.apaMobilno,ad.apaEmail,am.apmFname,am.apmMname,am.apmLname,am.apmApplicationDate from CFCApplicationAddressEntity ad,TbCfcApplicationMstEntity am where ad.apmApplicationId=:apmApplicationId"
				+ " and ad.apmApplicationId=am.apmApplicationId and ad.orgId.orgid=:orgId" );
		final Query query = createQuery(queryString.toString());
		 query.setParameter("apmApplicationId", applnNO);
		 query.setParameter("orgId", orgId);
		 List<Object[]> objList= query.getResultList();
		return objList;
	}


	
	
	
}
