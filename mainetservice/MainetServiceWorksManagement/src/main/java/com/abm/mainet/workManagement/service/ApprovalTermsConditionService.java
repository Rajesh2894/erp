package com.abm.mainet.workManagement.service;

import java.util.List;

import com.abm.mainet.workManagement.dto.ApprovalTermsConditionDto;

public interface ApprovalTermsConditionService {
    /**
     * used to save ,update and delete approval term and condition
     * 
     * @param approvalTermsConditionDto
     * @param deletedIds
     */

    void saveTermsCondition(List<ApprovalTermsConditionDto> approvalTermsConditionDto, List<Long> deletedIds);

    /**
     * used to fetch all terms by reference number
     * 
     * @param referenceId
     * @param orgId
     * @return
     */
    List<ApprovalTermsConditionDto> getTermsList(String referenceId, Long orgId);

    /**
     * update SancNo In Terms And Codition
     * 
     * @param sanctionNumber
     * @param workId
     * @param workEstimateNo
     */
    void updateSancNoInTermsAndCodition(String sanctionNumber, Long workId, String workEstimateNo);

    /**
     * find All Terms And Codition with the help of workId and Sanction Number
     * 
     * @param workId
     * @param sanctionNumber
     * @return
     */
    List<ApprovalTermsConditionDto> findAllTermsAndCodition(Long workId, String sanctionNumber);

}
