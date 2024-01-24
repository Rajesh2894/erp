package com.abm.mainet.bnd.dao;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.abm.mainet.bnd.constant.BndConstants;
import com.abm.mainet.bnd.domain.DeceasedMasterCorrection;
import com.abm.mainet.bnd.domain.MedicalMaster;
import com.abm.mainet.bnd.domain.MedicalMasterCorrection;
import com.abm.mainet.bnd.domain.TbBdDeathregCorr;
import com.abm.mainet.bnd.domain.TbDeathreg;
import com.abm.mainet.bnd.domain.TbDeathregTemp;
import com.abm.mainet.bnd.dto.TbDeathregDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.utility.Utility;
@Repository
public class DeathRegCorrDaoImpl extends AbstractDAO<TbDeathreg> implements IDeathRegCorrDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<TbDeathreg> getDeathRegisteredAppliDetail(String drCertNo, Long applnId, String year, String drRegno,
			Date drDod, String drDeceasedname,Long orgId,Long smServiceId, String DeathWFStatus) {
		final StringBuilder builder = new StringBuilder();
		
		String s1 = BndConstants.STRART_DATE+year;
		String s2 = BndConstants.TO_DATE+year;
		Date d1 = Utility.stringToDate(s1);
		Date d2 = Utility.stringToDate(s2);
		
		builder.append("select dr from TbDeathreg dr where dr.orgId=:orgId");
		
		
		Long BrIdOrDrID = null;
		if ((drCertNo != null) && !(MainetConstants.BLANK.equals(drCertNo))) {
			builder.append(" and dr.drCertNo:=drCertNo ");
		}
		if ((drRegno != null) && !((MainetConstants.BLANK).equals(drRegno))) {
			builder.append(" and dr.drRegno=:drRegno ");
			//builder.append(" and to_char(dr_regdate,'YYYY') =:drRegno");
		}
		if((year != null) && !((MainetConstants.BLANK).equals(year))) {
			builder.append(" and dr.drRegdate between :fromdate and :todate");
		}
		
		if ((applnId != null) && !((MainetConstants.BLANK).equals(applnId.toString()))) {
			String querybuilder = "select bdcfc.bdRequestId from BirthDeathCFCInterface bdcfc where bdcfc.apmApplicationId=:apmApplicationId and bdcfc.orgId=:orgId  and bdcfc.smServiceId=:smServiceId";
			final Query query1 = entityManager.createQuery(querybuilder.toString());
			query1.setParameter("apmApplicationId", Long.valueOf(applnId));
			query1.setParameter("orgId", orgId);
			query1.setParameter("smServiceId", smServiceId);
			//query.setParameter("DeathWFStatus", deathWFStatus);
			Long BirthDeathRegDetail = (Long) query1.getSingleResult();
			if (BirthDeathRegDetail != null) {
				BrIdOrDrID = BirthDeathRegDetail;
			}
			builder.append(" and dr.drId=:drId ");
		}
		if((DeathWFStatus != null) && !(MainetConstants.BLANK.equals(DeathWFStatus))) {
			builder.append(" and dr.DeathWFStatus=:DeathWFStatus ");
		}
		
		if((drDeceasedname != null) && !(MainetConstants.BLANK.equals(drDeceasedname))) {
			builder.append(" and dr.drDeceasedname Like :drDeceasedname ");
		}
		
		
		if((drDod != null)) {// && !(MainetConstants.BLANK.equals(drDod))
			builder.append(" and dr.drDod =:drDod");
		}
		
		
		final Query query = createQuery(builder.toString());
		query.setParameter("orgId", orgId);
		
		
		if ((drCertNo != null) && !(MainetConstants.BLANK.equals(drCertNo))) {
			query.setParameter("drCertNo", drCertNo);
		}
		if ((BrIdOrDrID != null) && !((MainetConstants.BLANK).equals(BrIdOrDrID.toString()))) {
			query.setParameter("drId", BrIdOrDrID);
		}
		if ((drRegno != null) && !((MainetConstants.BLANK).equals(drRegno))) {
			query.setParameter("drRegno", drRegno);
			//query.setParameter("year", year);
		}
		if ((drDeceasedname != null) && !(MainetConstants.BLANK.equals(drDeceasedname))) {  
			query.setParameter("drDeceasedname", drDeceasedname+"%");
		}
		
		if((drDod != null) ) {
			query.setParameter("drDod", drDod);
		}
		if ((DeathWFStatus != null) && !((MainetConstants.BLANK).equals(DeathWFStatus))) {
			query.setParameter("DeathWFStatus",DeathWFStatus);
		}

		if((year != null) && !((MainetConstants.BLANK).equals(year))) {
			query.setParameter("fromdate", d1);
			query.setParameter("todate", d2);
		}
		List<TbDeathreg> tbDeathreg =query.getResultList();
		/*
		 * Long drids[]=new Long[tbDeathreg.size()];; for(int i=0;i<
		 * tbDeathreg.size();i++) { drids[i]= tbDeathreg.get(i).getDrId(); } if
		 * (((drDeceasedname != null) &&
		 * !(MainetConstants.BLANK.equals(drDeceasedname))) || ((applnId != null) &&
		 * !((MainetConstants.BLANK).equals(applnId.toString()))) ||(drDod !=
		 * null)||((drRegno != null) && !((MainetConstants.BLANK).equals(drRegno)))) {
		 * StringBuilder querybuilderforApplition=new StringBuilder();
		 * querybuilderforApplition.
		 * append("select bdcfc.apmApplicationId ,bdcfc.bdRequestId from BirthDeathCFCInterface bdcfc where bdcfc.orgId=:orgId and bdcfc.smServiceId=:smServiceId"
		 * ); //query.setParameter("orgId", orgId); //query.setParameter("smServiceId",
		 * smServiceId);
		 * 
		 * StringBuilder str=new StringBuilder(); if(drids.length>0) { for(int
		 * i=0;i<drids.length;i++){ str=str.append("'"+drids[i]+"'");
		 * if(!(i==(drids.length-1))) str=str.append(",");
		 * 
		 * } querybuilderforApplition.append(" and bdcfc.bdRequestId in("+str+")");
		 * 
		 * } final Query queryForapplicationID =
		 * entityManager.createQuery(querybuilderforApplition.toString());
		 * queryForapplicationID.setParameter("orgId", orgId);
		 * queryForapplicationID.setParameter("smServiceId", smServiceId);
		 * //queryForapplicationID.setParameter("DeathWFStatus", deathWFStatus);
		 * List<Object[]> BirthDeathRegDetail = queryForapplicationID.getResultList();
		 * for(Object[] Object:BirthDeathRegDetail) { for(TbDeathreg obj: tbDeathreg) {
		 * if(obj.getDrId().equals(Object[1])) {
		 * obj.setApplnId(Long.valueOf(Object[0].toString())); } } }
		 * 
		 * }
		 */			
		return tbDeathreg;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TbDeathreg> getDeathRegisteredCorrAppliDetail(Long drId, Long orgId) {
		final StringBuilder builder = new StringBuilder();
		builder.append("select dr from TbDeathreg dr where dr.orgId=:orgId");
		
		if(drId != null) {
			builder.append(" and dr.drId=:drId ");
		}
		
		final Query query = createQuery(builder.toString());
		query.setParameter("orgId", orgId);
		if(drId != null) {
			query.setParameter("drId", drId);
		}
	
		List<TbDeathreg> tbDeathreg = query.getResultList();
		return tbDeathreg;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<TbBdDeathregCorr> getDeathRegisteredAppliDetailFromApplnId(Long applnId, Long orgId) {
		
		final StringBuilder builder = new StringBuilder();
		builder.append("select dr from TbBdDeathregCorr dr where dr.orgId=:orgId ");
		
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
			builder.append(" and dr.drId=:drId  order by dr.drCorrId desc ");
		}
				
		final Query query = createQuery(builder.toString());
		query.setParameter("orgId", orgId);
		
		if ((BrIdOrDrID != null) && !((MainetConstants.BLANK).equals(BrIdOrDrID.toString()))) {
			query.setParameter("drId", BrIdOrDrID);
		}
		
		List<TbBdDeathregCorr> tbDeathregcorr = query.getResultList();
		
		Long drids[] = new Long[tbDeathregcorr.size()];
		for(int i=0;i< tbDeathregcorr.size();i++) {
			drids[i]= tbDeathregcorr.get(i).getDrId();
		}
		return tbDeathregcorr;
	}

	@Override
	public List<MedicalMaster> getDeathRegApplnDataFromMedicalCorr(Long drId, Long orgId) {

		final StringBuilder builder = new StringBuilder();
		builder.append("select dr from MedicalMaster dr where dr.orgId=:orgId");
		
		if(drId != null) {
			builder.append(" and dr.drId=:drId ");
		}
		
		final Query query = createQuery(builder.toString());
		query.setParameter("orgId", orgId);
		if(drId != null) {
			query.setParameter("drId", drId);
		}
	
		List<MedicalMaster> tbDeathregMedicalMas = query.getResultList();
		return tbDeathregMedicalMas;
		
	}
	
	@Override
	public List<MedicalMasterCorrection> getDeathRegApplnDataFromMedicalMasCorr(Long drCorrId, Long orgId) {

		final StringBuilder builder = new StringBuilder();
		builder.append("select dr from MedicalMasterCorrection dr where dr.orgId=:orgId");
		if(drCorrId != null) {
			builder.append(" and dr.drCorrId=:drCorrId ");
		}
		
		final Query query = createQuery(builder.toString());
		query.setParameter("orgId", orgId);
		if(drCorrId != null) {
			query.setParameter("drCorrId", drCorrId);
		}
	
		List<MedicalMasterCorrection> tbDeathregMedicalMasCorr = query.getResultList();
		return tbDeathregMedicalMasCorr;
		
	}

	@Override
	public List<DeceasedMasterCorrection> getDeathRegApplnDataFromDecaseCorr(Long drCorrId, Long orgId) {
		final StringBuilder builder = new StringBuilder();
		builder.append("select dr from DeceasedMasterCorrection dr where dr.orgId=:orgId");
		if(drCorrId != null) {
			builder.append(" and dr.drCorrId=:drCorrId ");
		}
		
		final Query query = createQuery(builder.toString());
		query.setParameter("orgId", orgId);
		if(drCorrId != null) {
			query.setParameter("drCorrId", drCorrId);
		}
	
		List<DeceasedMasterCorrection> tbDeathregDecasedMasCorr = query.getResultList();
		return tbDeathregDecasedMasCorr;
	}

	@Override
	public List<TbDeathreg> getDeathDataForCorr(TbDeathregDTO tbDeathregDTO) {
    final StringBuilder builder = new StringBuilder();
		
		String s1 = BndConstants.STRART_DATE+tbDeathregDTO.getYear();
		String s2 = BndConstants.TO_DATE+tbDeathregDTO.getYear();
		Date d1 = Utility.stringToDate(s1);
		Date d2 = Utility.stringToDate(s2);
		
		builder.append("select dr from TbDeathreg dr where dr.orgId=:orgId");
		
		
		Long BrIdOrDrID = null;
		if ((tbDeathregDTO.getDrCertNo() != null) && !(MainetConstants.BLANK.equals(tbDeathregDTO.getDrCertNo()))) {
			builder.append(" and dr.drCertNo:=drCertNo ");
		}
		if ((tbDeathregDTO.getDrRegno() != null) && !((MainetConstants.BLANK).equals(tbDeathregDTO.getDrRegno()))) {
			builder.append(" and dr.drRegno=:drRegno ");
		}
		if((tbDeathregDTO.getYear() != null) && !((MainetConstants.BLANK).equals(tbDeathregDTO.getYear()))) {
			builder.append(" and dr.drRegdate between :fromdate and :todate");
		}
		
		if ((tbDeathregDTO.getApplnId() != null) && !((MainetConstants.BLANK).equals(String.valueOf(tbDeathregDTO.getApplnId())))) {
			String querybuilder = "select bdcfc.bdRequestId from BirthDeathCFCInterface bdcfc where bdcfc.apmApplicationId=:apmApplicationId and bdcfc.orgId=:orgId  and bdcfc.smServiceId=:smServiceId";
			final Query query1 = entityManager.createQuery(querybuilder.toString());
			query1.setParameter("apmApplicationId", Long.valueOf(tbDeathregDTO.getApplnId()));
			query1.setParameter("orgId", tbDeathregDTO.getOrgId());
			query1.setParameter("smServiceId", tbDeathregDTO.getServiceId());
			//query.setParameter("DeathWFStatus", deathWFStatus);
			Long BirthDeathRegDetail = (Long) query1.getSingleResult();
			if (BirthDeathRegDetail != null) {
				BrIdOrDrID = BirthDeathRegDetail;
			}
			builder.append(" and dr.drId=:drId ");
		}
		if (StringUtils.isNotBlank(tbDeathregDTO.getDeathWFStatus())) {
			builder.append(" and dr.DeathWFStatus=:DeathWFStatus ");
		}
		
		if (StringUtils.isNotBlank(tbDeathregDTO.getDrDeceasedname())) {
			builder.append(" and dr.drDeceasedname Like :drDeceasedname ");
		}
		
		
		if((tbDeathregDTO.getDrDod() != null)) {
			builder.append(" and dr.drDod =:drDod");
		}
		if(StringUtils.isNotBlank(tbDeathregDTO.getDrSex())&& !(tbDeathregDTO.getDrSex().equals("0"))) {
			builder.append(" and dr.drSex =:drSex");
			}
		if(tbDeathregDTO.getCpdDeathcauseId()!=null && tbDeathregDTO.getCpdDeathcauseId()!=0) {
			builder.append(" and dr.cpdDeathcauseId =:cpdDeathcauseId");
			}
		
		final Query query = createQuery(builder.toString());
		query.setParameter("orgId", tbDeathregDTO.getOrgId());
		
		
		if (StringUtils.isNotBlank(tbDeathregDTO.getDrCertNo())) {
			query.setParameter("drCertNo", tbDeathregDTO.getDrCertNo());
		}
		if ((BrIdOrDrID!=null) && !((MainetConstants.BLANK).equals(String.valueOf(BrIdOrDrID)))) {
			query.setParameter("drId", BrIdOrDrID);
		}
		if ((tbDeathregDTO.getDrRegno() != null) && !((MainetConstants.BLANK).equals(tbDeathregDTO.getDrRegno()))) {
			query.setParameter("drRegno", tbDeathregDTO.getDrRegno());
		}
		if (StringUtils.isNotBlank(tbDeathregDTO.getDrDeceasedname())) {  
			query.setParameter("drDeceasedname", tbDeathregDTO.getDrDeceasedname()+"%");
		}
		
		if((tbDeathregDTO.getDrDod() != null) ) {
			query.setParameter("drDod", tbDeathregDTO.getDrDod());
		}
		if ((tbDeathregDTO.getDeathWFStatus() != null) && !((MainetConstants.BLANK).equals(tbDeathregDTO.getDeathWFStatus()))) {
			query.setParameter("DeathWFStatus",tbDeathregDTO.getDeathWFStatus());
		}

		if(StringUtils.isNotBlank(tbDeathregDTO.getYear())) {
			query.setParameter("fromdate", d1);
			query.setParameter("todate", d2);
		}
		if(StringUtils.isNotBlank(tbDeathregDTO.getDrSex())&& !(tbDeathregDTO.getDrSex().equals("0"))) {
			query.setParameter("drSex", tbDeathregDTO.getDrSex());
			}
		if(tbDeathregDTO.getCpdDeathcauseId()!=null && tbDeathregDTO.getCpdDeathcauseId()!=0) {
			query.setParameter("cpdDeathcauseId", tbDeathregDTO.getCpdDeathcauseId());
			}
		List<TbDeathreg> tbDeathreg =query.getResultList();
					
		return tbDeathreg;
	}
	
	/*
	@Override
	public List<CemeteryMaster> getDeathRegApplnDataFromCemetryCorr(Long drId, Long orgId) {
		final StringBuilder builder = new StringBuilder();
		builder.append("select dr from CemeteryMaster dr where dr.orgId=:orgId");
		if(drId != null) {
			builder.append(" and dr.drId=:drId ");
		}
		
		final Query query = createQuery(builder.toString());
		query.setParameter("orgId", orgId);
		if(drId != null) {
			query.setParameter("drId", drId);
		}
	
		List<CemeteryMaster> tbDeathregCemetryMas = query.getResultList();
		return tbDeathregCemetryMas;
	} 
	*/
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TbDeathregTemp> getDeathRegisteredAppliDetailTemp(String drCertNo, Long applnId, String year, String drRegno,
			Date drDod, String drDeceasedname,Long orgId,Long smServiceId, String DeathWFStatus) {
		final StringBuilder builder = new StringBuilder();
		
		String s1 = BndConstants.STRART_DATE+year;
		String s2 = BndConstants.TO_DATE+year;
		Date d1 = Utility.stringToDate(s1);
		Date d2 = Utility.stringToDate(s2);
		
		builder.append("select dr from TbDeathregTemp dr where dr.orgId=:orgId");
		
		
		Long BrIdOrDrID = null;
		if ((drCertNo != null) && !(MainetConstants.BLANK.equals(drCertNo))) {
			builder.append(" and dr.drCertNo:=drCertNo ");
		}
		if ((drRegno != null) && !((MainetConstants.BLANK).equals(drRegno))) {
			builder.append(" and dr.drRegno=:drRegno ");
			//builder.append(" and to_char(dr_regdate,'YYYY') =:drRegno");
		}
		if((year != null) && !((MainetConstants.BLANK).equals(year))) {
			builder.append(" and dr.drRegdate between :fromdate and :todate");
		}
		
		if ((applnId != null) && !((MainetConstants.BLANK).equals(applnId.toString()))) {
			String querybuilder = "select bdcfc.bdRequestId from BirthDeathCFCInterface bdcfc where bdcfc.apmApplicationId=:apmApplicationId and bdcfc.orgId=:orgId  and bdcfc.smServiceId=:smServiceId";
			final Query query1 = entityManager.createQuery(querybuilder.toString());
			query1.setParameter("apmApplicationId", Long.valueOf(applnId));
			query1.setParameter("orgId", orgId);
			query1.setParameter("smServiceId", smServiceId);
			//query.setParameter("DeathWFStatus", deathWFStatus);
			Long BirthDeathRegDetail = (Long) query1.getSingleResult();
			if (BirthDeathRegDetail != null) {
				BrIdOrDrID = BirthDeathRegDetail;
			}
			builder.append(" and dr.drId=:drId ");
		}
		if((DeathWFStatus != null) && !(MainetConstants.BLANK.equals(DeathWFStatus))) {
			builder.append(" and dr.DeathWFStatus=:DeathWFStatus ");
		}
		
		if((drDeceasedname != null) && !(MainetConstants.BLANK.equals(drDeceasedname))) {
			builder.append(" and dr.drDeceasedname Like :drDeceasedname ");
		}
		
		
		if((drDod != null)) {// && !(MainetConstants.BLANK.equals(drDod))
			builder.append(" and dr.drDod =:drDod");
		}
		
		
		final Query query = createQuery(builder.toString());
		query.setParameter("orgId", orgId);
		
		
		if ((drCertNo != null) && !(MainetConstants.BLANK.equals(drCertNo))) {
			query.setParameter("drCertNo", drCertNo);
		}
		if ((BrIdOrDrID != null) && !((MainetConstants.BLANK).equals(BrIdOrDrID.toString()))) {
			query.setParameter("drId", BrIdOrDrID);
		}
		if ((drRegno != null) && !((MainetConstants.BLANK).equals(drRegno))) {
			query.setParameter("drRegno", drRegno);
			//query.setParameter("year", year);
		}
		if ((drDeceasedname != null) && !(MainetConstants.BLANK.equals(drDeceasedname))) {  
			query.setParameter("drDeceasedname", drDeceasedname+"%");
		}
		
		if((drDod != null) ) {
			query.setParameter("drDod", drDod);
		}
		if ((DeathWFStatus != null) && !((MainetConstants.BLANK).equals(DeathWFStatus))) {
			query.setParameter("DeathWFStatus",DeathWFStatus);
		}

		if((year != null) && !((MainetConstants.BLANK).equals(year))) {
			query.setParameter("fromdate", d1);
			query.setParameter("todate", d2);
		}
		List<TbDeathregTemp> tbDeathreg =query.getResultList();
			
		return tbDeathreg;
	}
	
}


