package com.abm.mainet.legal.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.legal.domain.CaseHearing;
import com.abm.mainet.legal.dto.CaseEntryDTO;
import com.abm.mainet.legal.dto.CaseHearingAttendeeDetailsDTO;
import com.abm.mainet.legal.dto.CaseHearingDTO;
import com.abm.mainet.legal.dto.TbLglComntRevwDtlDTO;

/**
 * This is mainly for CRUD operation. This is use to store all case hearing proceeding.
 * @author Lalit.Prusti
 *
 */

@WebService
public interface ICaseHearingService {
    /**
     * This method will save form content in the database
     * @param caseHearingDto
     */
    void saveCaseHearing(CaseHearingDTO caseHearingDto);

    /**
     * This method will save or update all content in the database
     * @param caseHearingDto
     */
    List<CaseHearingDTO> saveAllCaseHearing(List<CaseHearingDTO> caseHearingDto);

    /**
     * This method will help us to edit the content of form
     * @param caseHearingDto
     */
    void updateCaseHearing(CaseHearingDTO caseHearingDto);

    /**
     * This will delete The entry of CaseHearing
     * @param caseHearingDto
     */
    boolean deleteCaseHearing(CaseHearingDTO caseHearingDto);

    /**
     * This will help us to get CaseHearing Details by id
     * @param id
     * @return
     */
    CaseHearingDTO getCaseHearingById(Long id);

    /**
     * @param orgId organization Id
     * @param cseId case Id
     * @return all hearing of the case
     */

    List<CaseHearingDTO> getHearingDetailByCaseId(Long orgId, Long cseId);

    List<CaseHearingDTO>  getHearingDetailsByCaseId(Long cseId);
    
    void saveAndUpdateCaseHearingAttendee(List<CaseHearingAttendeeDetailsDTO> caseHearingAttendeeDetailsDTOs,CaseEntryDTO caseEntryDTO,
            List<Long> removeAttendeeIds, Organisation organisation);

    List<CaseHearingAttendeeDetailsDTO> fetchHearingAttendeeDetailsByCaseId(Long cseId, Long orgId);
    
    List<CaseHearingAttendeeDetailsDTO> fetchHearingAttenDetailsByCaseIdUad(Long cseId);
    
    void saveAndUpdtHearingComntAndRevw(List<TbLglComntRevwDtlDTO> tbLglComntRevwDtlDTOList,
            CaseEntryDTO caseEntryDTO, List<Long> removeAttendeeIds, Organisation organisation);
    
    List<TbLglComntRevwDtlDTO> fetchHearingComntsDetailsByCaseId(Long cseId, Long orgId);


	List<CaseHearingDTO> getHearingDetailByOrgId(Long orgid, Date currDate);

	List<TbLglComntRevwDtlDTO> fetchHearingComntsDetailsByHearingId(Long id, Long orgId);

	List<CaseHearingAttendeeDetailsDTO> fetchHearingAttenDetailsByHearingId(Long id);

	List<CaseHearingAttendeeDetailsDTO> fetchHearingAttendeeDetailsByHearingId(Long id, Long orgId);

}
