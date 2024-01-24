package com.abm.mainet.legal.service;

import javax.jws.WebService;

import com.abm.mainet.legal.dto.CaseReferenceAndLegalOpinionDTO;

/**
 * This is mainly for CRUD operation. This service used to given Remark on Legal Document paragraph/section wise from
 * @author Lalit.Prusti
 *
 */

@WebService
public interface ICaseReferenceAndLegalOpinionService {

    /**
     * This method will save or update all content in the database
     * @param caseReferenceAndLegalOpinionDto
     */
    void saveAllCaseReferenceAndLegalOpinion(CaseReferenceAndLegalOpinionDTO caseReferenceAndLegalOpinionDto);

    /**
     * This will help us to get CaseReferenceAndLegalOpinion Details
     * @param orgId
     * @param caseId
     * @return
     */
    CaseReferenceAndLegalOpinionDTO getCaseReferenceAndLegalOpinionById(Long orgId, Long caseId);

}
