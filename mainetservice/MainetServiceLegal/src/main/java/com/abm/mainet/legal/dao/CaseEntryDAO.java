package com.abm.mainet.legal.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.legal.domain.AdvocateMaster;
import com.abm.mainet.legal.domain.CaseEntry;
import com.abm.mainet.legal.domain.CourtMaster;
import com.abm.mainet.legal.domain.JudgeMaster;

@Repository
public class CaseEntryDAO extends AbstractDAO<CaseEntry> implements ICaseEntryDAO {

    @SuppressWarnings("unchecked")
    @Override
    public List<CaseEntry> searchCaseEntry(String cseSuitNo, Long cseDeptid, Long cseCatId1, Long cseCatId2, Long cseTypId,
            Date cseDate,
            Long crtId, Long cseCaseStatusId, Long orgId, List<Long> cseId) {

        Query query = this.createQuery(
                buildQuery(cseSuitNo, cseDeptid, cseCatId1, cseCatId2, cseTypId, cseDate, crtId, cseCaseStatusId, cseId));
        query.setParameter("orgId", orgId);

        if (StringUtils.isNotEmpty(cseSuitNo)) {
            query.setParameter("cseSuitNo", cseSuitNo);
        }
        if (cseDeptid != null) {
            query.setParameter("cseDeptid", cseDeptid);
        }
        if (cseCatId1 != null) {
            query.setParameter("cseCatId1", cseCatId1);
        }
        if (cseCatId2 != null) {
            query.setParameter("cseCatId2", cseCatId2);
        }
        if (cseTypId != null) {
            query.setParameter("cseTypId", cseTypId);
        }
        if (cseDate != null) {
            query.setParameter("cseDate", cseDate);
        }
        if (crtId != null) {
            query.setParameter("crtId", crtId);
        }
        if (cseCaseStatusId != null) {
            query.setParameter("cseCaseStatusId", cseCaseStatusId);
        }

        if (cseId != null) {
            query.setParameter("cseId", cseId);
        }

        return query.getResultList();
    }

    private String buildQuery(String cseSuitNo, Long cseDeptid, Long cseCatId1, Long cseCatId2, Long cseTypId, Date cseDate,
            Long crtId, Long cseCaseStatusId, List<Long> cseId) {

        StringBuilder searchQuery = new StringBuilder(
                " SELECT ce FROM CaseEntry ce WHERE (ce.orgid = :orgId OR ce.concernedUlb  = :orgId )");

		
		 if (StringUtils.isNotEmpty(cseSuitNo)) {
		 searchQuery.append(" AND (ce.cseSuitNo = :cseSuitNo ");
		 searchQuery.append(" OR ce.caseNo = :cseSuitNo "); searchQuery.append(")");
		 }

        if (cseDeptid != null) {
            searchQuery.append(" AND ce.cseDeptid = :cseDeptid ");
        }
        if (cseCatId1 != null) {
            searchQuery.append(" AND ce.cseCatId1 = :cseCatId1 ");
        }
        if (cseCatId2 != null) {
            searchQuery.append(" AND ce.cseCatId2 = :cseCatId2 ");
        }
        if (cseTypId != null) {
            searchQuery.append(" AND ce.cseTypId = :cseTypId ");
        }
        if (cseDate != null) {
            searchQuery.append(" AND ce.cseDate = :cseDate ");
        }
        if (crtId != null) {
            searchQuery.append(" AND ce.crtId = :crtId ");
        }
        // get all non closed case
        if (cseCaseStatusId != null) {
            searchQuery.append(" AND ce.cseCaseStatusId != :cseCaseStatusId ");
        }

        // in query based on cseId
        if (cseId != null) {
            searchQuery.append(" AND ce.cseId IN (:cseId) ");
        }
        searchQuery.append(" ORDER BY ce.cseId DESC ");

        return searchQuery.toString();

    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<CaseEntry> searchCaseEntrys(String cseSuitNo, Long cseDeptid, Long cseCatId1, Long cseCatId2, Long cseTypId,
            Date cseDate,
            Long crtId, Long cseCaseStatusId, Long orgId, List<Long> cseId,String caseNo) {

        Query query = this.createQuery(
                buildQuery(cseSuitNo, cseDeptid, cseCatId1, cseCatId2, cseTypId, cseDate, crtId, cseCaseStatusId, cseId,caseNo));
        query.setParameter("orgId", orgId);

        if (StringUtils.isNotEmpty(caseNo)) {
            query.setParameter("caseNo", caseNo);
        }
        if (StringUtils.isNotEmpty(cseSuitNo)) {
            query.setParameter("cseSuitNo", cseSuitNo);
        }
        if (cseDeptid != null) {
            query.setParameter("cseDeptid", cseDeptid);
        }
        if (cseCatId1 != null) {
            query.setParameter("cseCatId1", cseCatId1);
        }
        if (cseCatId2 != null) {
            query.setParameter("cseCatId2", cseCatId2);
        }
        if (cseTypId != null) {
            query.setParameter("cseTypId", cseTypId);
        }
        if (cseDate != null) {
            query.setParameter("cseDate", cseDate);
        }
        if (crtId != null) {
            query.setParameter("crtId", crtId);
        }
        if (cseCaseStatusId != null) {
            query.setParameter("cseCaseStatusId", cseCaseStatusId);
        }

        if (cseId != null) {
            query.setParameter("cseId", cseId);
        }

        return query.getResultList();
    }

    private String buildQuery(String cseSuitNo, Long cseDeptid, Long cseCatId1, Long cseCatId2, Long cseTypId, Date cseDate,
            Long crtId, Long cseCaseStatusId, List<Long> cseId,String caseNo) {

        StringBuilder searchQuery = new StringBuilder(
                " SELECT ce FROM CaseEntry ce WHERE (ce.orgid = :orgId OR ce.concernedUlb  = :orgId )");

        if (StringUtils.isNotEmpty(cseSuitNo)) {
            searchQuery.append(" AND ce.cseSuitNo = :cseSuitNo ");
        }
        if (StringUtils.isNotEmpty(caseNo)) {
            searchQuery.append(" AND ce.caseNo = :caseNo ");
        }
        if (cseDeptid != null) {
            searchQuery.append(" AND ce.cseDeptid = :cseDeptid ");
        }
        if (cseCatId1 != null) {
            searchQuery.append(" AND ce.cseCatId1 = :cseCatId1 ");
        }
        if (cseCatId2 != null) {
            searchQuery.append(" AND ce.cseCatId2 = :cseCatId2 ");
        }
        if (cseTypId != null) {
            searchQuery.append(" AND ce.cseTypId = :cseTypId ");
        }
        if (cseDate != null) {
            searchQuery.append(" AND ce.cseDate = :cseDate ");
        }
        if (crtId != null) {
            searchQuery.append(" AND ce.crtId = :crtId ");
        }
        // get all non closed case
        if (cseCaseStatusId != null) {
            searchQuery.append(" AND ce.cseCaseStatusId != :cseCaseStatusId ");
        }

        // in query based on cseId
        if (cseId != null) {
            searchQuery.append(" AND ce.cseId IN (:cseId) ");
        }
        searchQuery.append(" ORDER BY ce.cseId DESC ");

        return searchQuery.toString();

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CaseEntry> searchCaseEntryUAD(String cseSuitNo, Long cseDeptid, Long cseCatId1, Long cseCatId2, Long cseTypId,
            Date cseDate, Long crtId, Long cseCaseStatusId, Long orgId, List<Long> cseId,String flag) {
        Query query = this.createNativeQuery(
                buildQueryUAD(cseSuitNo, cseDeptid, cseCatId1, cseCatId2, cseTypId, cseDate, crtId, cseCaseStatusId, cseId, orgId, flag ));
        // query.setParameter("orgId", orgId);

        if (StringUtils.isNotEmpty(cseSuitNo)) {
            query.setParameter("cseSuitNo", cseSuitNo);
        }
        if (cseDeptid != null) {
            query.setParameter("cseDeptid", cseDeptid);
        }
        if (orgId != null) {
            query.setParameter("orgId", orgId);
        }
        if (cseCatId1 != null) {
            query.setParameter("cseCatId1", cseCatId1);
        }
        if (cseCatId2 != null) {
            query.setParameter("cseCatId2", cseCatId2);
        }
        if (cseTypId != null) {
            query.setParameter("cseTypId", cseTypId);
        }
        if (cseDate != null) {
            query.setParameter("cseDate", cseDate);
        }
        if (crtId != null) {
            query.setParameter("crtId", crtId);
        }
        if (cseCaseStatusId != null) {
            query.setParameter("cseCaseStatusId", cseCaseStatusId);
        }
        

        if (cseId != null) {
            query.setParameter("cseId", cseId);
        }

        List<Object[]> entity = query.getResultList();
        List<CaseEntry> caseEntryList = new ArrayList<>();
        if (entity != null && !entity.isEmpty()) {
            for (Object[] caseEntryInfo : entity) {
                CaseEntry caseEntry = new CaseEntry();
                if (caseEntryInfo != null) {
                    if (caseEntryInfo[0] != null)
                        caseEntry.setCseId(Long.valueOf(caseEntryInfo[0].toString()));
                    if (caseEntryInfo[11] != null)
                        caseEntry.setCrtId(Long.valueOf(caseEntryInfo[11].toString()));
                    if (caseEntryInfo[12] != null)
                        caseEntry.setAdvId(Long.valueOf(caseEntryInfo[12].toString()));
                    if (caseEntryInfo[17] != null)
                        caseEntry.setCseCaseStatusId(Long.valueOf(caseEntryInfo[17].toString()));
                    if (caseEntryInfo[5] != null)
                        caseEntry.setCseCatId1(Long.valueOf(caseEntryInfo[5].toString()));
                    if (caseEntryInfo[6] != null)
                        caseEntry.setCseCatId2(Long.valueOf(caseEntryInfo[6].toString()));
                    if (caseEntryInfo[7] != null)
                        caseEntry.setCseDate((Date) caseEntryInfo[7]);
                    if (caseEntryInfo[4] != null)
                        caseEntry.setCseDeptid(Long.valueOf(caseEntryInfo[4].toString()));
                    if (caseEntryInfo[8] != null)
                        caseEntry.setCseEntryDt((Date) caseEntryInfo[8]);
                    if (caseEntryInfo[14] != null)
                        caseEntry.setCseMatdet1((String) caseEntryInfo[14]);
                    if (caseEntryInfo[1] != null)
                        caseEntry.setCseName((String) caseEntryInfo[1]);
                    if (caseEntryInfo[10] != null)
                        caseEntry.setCsePeicDroa(Long.valueOf(caseEntryInfo[10].toString()));
                    if (caseEntryInfo[18] != null)
                        caseEntry.setCseReferenceno((String) caseEntryInfo[18]);
                    if (caseEntryInfo[3] != null)
                        caseEntry.setCseRefsuitNo((String) caseEntryInfo[3]);
                    if (caseEntryInfo[15] != null)
                        caseEntry.setCseRemarks((String) caseEntryInfo[15]);
                    if (caseEntryInfo[16] != null)
                        caseEntry.setCseSectAppl((String) caseEntryInfo[16]);
                    if (caseEntryInfo[2] != null)
                        caseEntry.setCseSuitNo((String) caseEntryInfo[2]);
                    if (caseEntryInfo[9] != null)
                        caseEntry.setCseTypId(Long.valueOf(caseEntryInfo[9].toString()));

                }
                caseEntryList.add(caseEntry);
            }
        }
        return caseEntryList;
    }

    private String buildQueryUAD(String cseSuitNo, Long cseDeptid, Long cseCatId1, Long cseCatId2, Long cseTypId, Date cseDate,
            Long crtId, Long cseCaseStatusId, List<Long> cseId, Long orgId, String flag) {

        StringBuilder searchQuery = new StringBuilder(
                " SELECT * FROM TB_LGL_CASE_MAS ce, (select distinct b.CSE_ID,b.ORGID from TB_LGL_CASE_PDDETAILS b WHERE  b.CSED_PARTY_TYPE in (select p.CPD_ID from TB_COMPARAM_DET p, TB_COMPARAM_MAS r where p.CPM_ID = r.CPM_ID "
                        + " AND p.CPD_VALUE <>'U' AND r.CPM_PREFIX='PAT')) b where exists (select cse_id from tb_lgl_caseparawise_remark x where x.ORGID = ce.orgid OR x.ORGID = 1 and x.cse_id = ce.cse_id) and ce.CSE_ID = b.CSE_ID ");
        if (StringUtils.isNotEmpty(cseSuitNo)) {
            searchQuery.append(" AND ce.CSE_SUIT_NO = :cseSuitNo ");
        }
        if (StringUtils.isNotEmpty(cseSuitNo)) {
            searchQuery.append(" OR ce.CSE_NO = :cseSuitNo ");
        }
        if (cseDeptid != null) {
            searchQuery.append(" AND ce.CSE_DEPTID = :cseDeptid ");
        }
        if (cseCatId1 != null) {
            searchQuery.append(" AND ce.CSE_CAT_ID = :cseCatId1 ");
        }
        if (orgId != null) {
            searchQuery.append(" AND ce.ORGID = :orgId ");
        }

        if (cseCatId2 != null) {
            searchQuery.append(" AND ce.CSE_SUBCAT_ID = :cseCatId2 ");
        }
        if (cseTypId != null) {
            searchQuery.append(" AND ce.CSE_TYP_ID = :cseTypId ");
        }
        if (cseDate != null) {
            searchQuery.append(" AND ce.CSE_DATE = :cseDate ");
        }
        if (crtId != null) {
            searchQuery.append(" AND ce.CRT_ID = :crtId ");
        }
        // get all non closed case
        if (cseCaseStatusId != null) {
            searchQuery.append(" AND ce.CSE_CASE_STATUS_ID != :cseCaseStatusId ");
        }
        if(flag !=null) {
        if(flag.equalsIgnoreCase(MainetConstants.FlagH) ) {
        	 searchQuery.append(" AND ce.concerned_ulb is not null ");
        }
        }

        // in query based on cseId
        if (cseId != null) {
            searchQuery.append(" AND ce.CSE_ID IN (:cseId) ");
        }
        searchQuery.append(" ORDER BY ce.CSE_ID DESC ");

        return searchQuery.toString();

    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<CaseEntry> searchCaseEntryUADs(String cseSuitNo, Long cseDeptid, Long cseCatId1, Long cseCatId2, Long cseTypId,
            Date cseDate, Long crtId, Long cseCaseStatusId, Long orgId, List<Long> cseId,String flag,String caseNo) {
        Query query = this.createNativeQuery(
                buildQueryUADs(cseSuitNo, cseDeptid, cseCatId1, cseCatId2, cseTypId, cseDate, crtId, cseCaseStatusId, cseId, orgId, flag,caseNo ));
        // query.setParameter("orgId", orgId);

        if (StringUtils.isNotEmpty(caseNo)) {
            query.setParameter("caseNo", caseNo);
        }
        if (StringUtils.isNotEmpty(cseSuitNo)) {
            query.setParameter("cseSuitNo", cseSuitNo);
        }
        if (cseDeptid != null) {
            query.setParameter("cseDeptid", cseDeptid);
        }
        if (orgId != null) {
            query.setParameter("orgId", orgId);
        }
        if (cseCatId1 != null) {
            query.setParameter("cseCatId1", cseCatId1);
        }
        if (cseCatId2 != null) {
            query.setParameter("cseCatId2", cseCatId2);
        }
        if (cseTypId != null) {
            query.setParameter("cseTypId", cseTypId);
        }
        if (cseDate != null) {
            query.setParameter("cseDate", cseDate);
        }
        if (crtId != null) {
            query.setParameter("crtId", crtId);
        }
        if (cseCaseStatusId != null) {
            query.setParameter("cseCaseStatusId", cseCaseStatusId);
        }
        

        if (cseId != null) {
            query.setParameter("cseId", cseId);
        }

        List<Object[]> entity = query.getResultList();
        List<CaseEntry> caseEntryList = new ArrayList<>();
        if (entity != null && !entity.isEmpty()) {
            for (Object[] caseEntryInfo : entity) {
                CaseEntry caseEntry = new CaseEntry();
                if (caseEntryInfo != null) {
                    if (caseEntryInfo[0] != null)
                        caseEntry.setCseId(Long.valueOf(caseEntryInfo[0].toString()));
                    if (caseEntryInfo[11] != null)
                        caseEntry.setCrtId(Long.valueOf(caseEntryInfo[11].toString()));
                    if (caseEntryInfo[12] != null)
                        caseEntry.setAdvId(Long.valueOf(caseEntryInfo[12].toString()));
                    if (caseEntryInfo[17] != null)
                        caseEntry.setCseCaseStatusId(Long.valueOf(caseEntryInfo[17].toString()));
                    if (caseEntryInfo[5] != null)
                        caseEntry.setCseCatId1(Long.valueOf(caseEntryInfo[5].toString()));
                    if (caseEntryInfo[6] != null)
                        caseEntry.setCseCatId2(Long.valueOf(caseEntryInfo[6].toString()));
                    if (caseEntryInfo[7] != null)
                        caseEntry.setCseDate((Date) caseEntryInfo[7]);
                    if (caseEntryInfo[4] != null)
                        caseEntry.setCseDeptid(Long.valueOf(caseEntryInfo[4].toString()));
                    if (caseEntryInfo[8] != null)
                        caseEntry.setCseEntryDt((Date) caseEntryInfo[8]);
                    if (caseEntryInfo[14] != null)
                        caseEntry.setCseMatdet1((String) caseEntryInfo[14]);
                    if (caseEntryInfo[1] != null)
                        caseEntry.setCseName((String) caseEntryInfo[1]);
                    if (caseEntryInfo[10] != null)
                        caseEntry.setCsePeicDroa(Long.valueOf(caseEntryInfo[10].toString()));
                    if (caseEntryInfo[18] != null)
                        caseEntry.setCseReferenceno((String) caseEntryInfo[18]);
                    if (caseEntryInfo[3] != null)
                        caseEntry.setCseRefsuitNo((String) caseEntryInfo[3]);
                    if (caseEntryInfo[15] != null)
                        caseEntry.setCseRemarks((String) caseEntryInfo[15]);
                    if (caseEntryInfo[16] != null)
                        caseEntry.setCseSectAppl((String) caseEntryInfo[16]);
                    if (caseEntryInfo[2] != null)
                        caseEntry.setCseSuitNo((String) caseEntryInfo[2]);
                    if (caseEntryInfo[9] != null)
                        caseEntry.setCseTypId(Long.valueOf(caseEntryInfo[9].toString()));

                }
                caseEntryList.add(caseEntry);
            }
        }
        return caseEntryList;
    }

    private String buildQueryUADs(String cseSuitNo, Long cseDeptid, Long cseCatId1, Long cseCatId2, Long cseTypId, Date cseDate,
            Long crtId, Long cseCaseStatusId, List<Long> cseId, Long orgId, String flag,String caseNo) {

        StringBuilder searchQuery = new StringBuilder(
                " SELECT * FROM TB_LGL_CASE_MAS ce, (select distinct b.CSE_ID,b.ORGID from TB_LGL_CASE_PDDETAILS b WHERE  b.CSED_PARTY_TYPE in (select p.CPD_ID from TB_COMPARAM_DET p, TB_COMPARAM_MAS r where p.CPM_ID = r.CPM_ID "
                        + " AND p.CPD_VALUE <>'U' AND r.CPM_PREFIX='PAT')) b where exists (select cse_id from tb_lgl_caseparawise_remark x where x.ORGID = ce.orgid OR x.ORGID = 1 and x.cse_id = ce.cse_id) and ce.CSE_ID = b.CSE_ID ");
        if (StringUtils.isNotEmpty(cseSuitNo)) {
            searchQuery.append(" AND ce.CSE_SUIT_NO = :cseSuitNo ");
        }
        if (StringUtils.isNotEmpty(caseNo)) {
            searchQuery.append(" AND ce.CSE_NO = :caseNo ");
        }
        if (cseDeptid != null) {
            searchQuery.append(" AND ce.CSE_DEPTID = :cseDeptid ");
        }
        if (cseCatId1 != null) {
            searchQuery.append(" AND ce.CSE_CAT_ID = :cseCatId1 ");
        }
        if (orgId != null) {
            searchQuery.append(" AND ce.ORGID = :orgId ");
        }

        if (cseCatId2 != null) {
            searchQuery.append(" AND ce.CSE_SUBCAT_ID = :cseCatId2 ");
        }
        if (cseTypId != null) {
            searchQuery.append(" AND ce.CSE_TYP_ID = :cseTypId ");
        }
        if (cseDate != null) {
            searchQuery.append(" AND ce.CSE_DATE = :cseDate ");
        }
        if (crtId != null) {
            searchQuery.append(" AND ce.CRT_ID = :crtId ");
        }
        // get all non closed case
        if (cseCaseStatusId != null) {
            searchQuery.append(" AND ce.CSE_CASE_STATUS_ID != :cseCaseStatusId ");
        }
        if(flag !=null) {
        if(flag.equalsIgnoreCase(MainetConstants.FlagH) ) {
        	 searchQuery.append(" AND ce.concerned_ulb is not null ");
        }
        }

        // in query based on cseId
        if (cseId != null) {
            searchQuery.append(" AND ce.CSE_ID IN (:cseId) ");
        }
        searchQuery.append(" ORDER BY ce.CSE_ID DESC ");

        return searchQuery.toString();

    }


    //127193
	@Override
	public List<JudgeMaster> findJudgeDetails(Long crtId, String judgeStatus, String judgeBenchName, Long orgid) {
		final StringBuilder queryString = new StringBuilder();
        queryString.append("SELECT jm FROM JudgeMaster  jm , JudgeDetailMaster jd WHERE jm.orgId=:orgid and jd.judge.id=jm.id ");

        if (crtId != null && crtId != 0 ) {
			queryString.append(" and jd.court.id=:crtId");
		}
		
		if (judgeStatus != null && StringUtils.isNotBlank(judgeStatus)) {
			queryString.append(" and jd.judgeStatus=:judgeStatus");
		} 
		
		if (judgeBenchName != null && StringUtils.isNotBlank(judgeBenchName) ) {
			queryString.append(" and jm.judgeBenchName=:judgeBenchName");
		}
		
		Query query = this.createQuery(queryString.toString());
		query.setParameter("orgid", orgid);

		if (crtId != null && crtId != 0) {
			query.setParameter("crtId", crtId);
			}
		
		if (judgeStatus != null && StringUtils.isNotBlank(judgeStatus)) {
			query.setParameter("judgeStatus", judgeStatus);
			}
		
		
		if (judgeBenchName != null &&StringUtils.isNotBlank(judgeBenchName)) {
			query.setParameter("judgeBenchName", judgeBenchName);
		}
		List<JudgeMaster> entityList = query.getResultList();
		return entityList;
		
	}


	@Override
	public List<CourtMaster> findCourtDetailsByIds(Long crtId, Long crtType, Long orgid) {
		final StringBuilder queryString = new StringBuilder();
        queryString.append("SELECT cm FROM CourtMaster cm  WHERE cm.orgId=:orgid ");

        if (crtId != null && crtId != 0 ) {
			queryString.append(" and cm.id=:crtId");
		}
		
		if (crtType != null && crtType != 0) {
			queryString.append(" and cm.crtType=:crtType");
		} 	
		Query query = this.createQuery(queryString.toString());
		query.setParameter("orgid", orgid);

		if (crtId != null && crtId != 0) {
			query.setParameter("crtId", crtId);
			}
		
		if (crtType != null && crtType != 0) {
			query.setParameter("crtType", crtType);
			}
		
		List<CourtMaster> entityList = query.getResultList();
		return entityList;
	
	}
  
}
