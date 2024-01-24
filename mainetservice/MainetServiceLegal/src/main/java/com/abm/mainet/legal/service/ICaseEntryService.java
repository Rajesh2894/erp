package com.abm.mainet.legal.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import com.abm.mainet.legal.dto.CaseEntryDTO;
import com.abm.mainet.legal.dto.JudgeMasterDTO;

/**
 * This is mainly for CRUD operation of a Case Entry.Here all the case information store of an Organization.
 * @author Lalit.Prusti
 *
 */

@WebService
public interface ICaseEntryService {
    /**
     * This method will save form content in the database
     * @param CaseEntryDto
     */
    CaseEntryDTO saveCaseEntry(CaseEntryDTO CaseEntryDto);

    /**
     * This method will help us to edit the content of form
     * @param CaseEntryDto
     */
    void updateCaseEntry(CaseEntryDTO CaseEntryDto);

    /**
     * This will delete The entry of CaseEntry
     * @param id
     */
    boolean deleteCaseEntry(CaseEntryDTO CaseEntryDto);

    /**
     * This will return active CaseEntry list
     * @param orgId
     * @return
     */
    List<CaseEntryDTO> getAllCaseEntry(Long orgId);

    List<CaseEntryDTO> getAllCaseEntryBasedOnHearing(Long hrStatus, Long orgId);
    
    List<CaseEntryDTO> getAllCaseEntryBasedOnHearingStatus(Long hrStatus);
    /**
     * This will help us to get CaseEntry Details by id
     * @param id
     * @return
     */
    CaseEntryDTO getCaseEntryById(Long id);

    List<CaseEntryDTO> searchCaseEntry(String cseSuitNo, Long cseDeptid, Long cseCatId1, Long cseCatId2, Long cseTypId,
            Date cseDate, Long crtId, Long cseCaseStatusId, Long orgId, Long hrStatus,String flag);
    

    void deleteContractDocFileById(List<Long> enclosureRemoveById, Long empId);

    List<JudgeMasterDTO> getAllJudgebyCaseId(long caseId, long orgId);

    List<CaseEntryDTO> getCaseEntryByStatus(Long id,Long orgid);
    
    

	CaseEntryDTO getCaseEntryByCaseNumber(CaseEntryDTO cseEntryDto);

	boolean checkCaseNoAlreadyPresent(String cseSuitNo, Long orgid);

	List<CaseEntryDTO> getcaseEntryByAdvId(Long advId, Long orgId);

	boolean isTSCLEnvPresent();

	List<CaseEntryDTO> searchCaseEntrys(String cseSuitNo, Long cseDeptid, Long cseCatId1, Long cseCatId2, Long cseTypId,
			Date cseDate, Long crtId, Long cseCaseStatusId, Long orgId, Long hrStatus, String flag, String caseNo);

	List<CaseEntryDTO> getCaseByRefCaseNumber(String cseRefsuitNo, Long orgId);

	List<CaseEntryDTO> getAllCaseEntrySummary(Long orgId);

	List<String> findSuitNoByOrgid(Long orgId);
    
}
