package com.abm.mainet.legal.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.legal.domain.CaseEntry;
import com.abm.mainet.legal.domain.CourtMaster;
import com.abm.mainet.legal.domain.JudgeMaster;

public interface ICaseEntryDAO {
    List<CaseEntry> searchCaseEntry(String cseSuitNo, Long cseDeptid, Long cseCatId1, Long cseCatId2, Long cseTypId, Date cseDate,
            Long crtId, Long cseCaseStatusId, Long orgId, List<Long> cseId);
    
    List<CaseEntry> searchCaseEntryUAD(String cseSuitNo, Long cseDeptid, Long cseCatId1, Long cseCatId2, Long cseTypId,
            Date cseDate,
            Long crtId, Long cseCaseStatusId, Long orgId, List<Long> cseId, String flag);

	List<JudgeMaster> findJudgeDetails(Long crtId, String judgeStatus, String judgeBenchName, Long orgid);

	List<CourtMaster> findCourtDetailsByIds(Long crtId, Long crtType, Long orgid);

	List<CaseEntry> searchCaseEntryUADs(String cseSuitNo, Long cseDeptid, Long cseCatId1, Long cseCatId2, Long cseTypId,
			Date cseDate, Long crtId, Long cseCaseStatusId, Long orgId, List<Long> cseId, String flag, String caseNo);

	List<CaseEntry> searchCaseEntrys(String cseSuitNo, Long cseDeptid, Long cseCatId1, Long cseCatId2, Long cseTypId,
			Date cseDate, Long crtId, Long cseCaseStatusId, Long orgId, List<Long> cseId, String caseNo);

}
