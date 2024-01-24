package com.abm.mainet.legal.service;

import java.util.List;

import javax.jws.WebService;

import com.abm.mainet.legal.dto.JudgementDetailDTO;

/**
 * This is mainly for CRUD operation. This service used to given Remark on Legal Document paragraph/section wise from
 * @author Lalit.Prusti
 *
 */

@WebService
public interface IJudgementDetailService {
    /**
     * This method will save form content in the database
     * @param judgementDetailDto
     */
    void saveJudgementDetail(JudgementDetailDTO judgementDetailDto);

    /**
     * This method will save or update all content in the database
     * @param judgementDetailDto
     */
    void saveAllJudgementDetail(List<JudgementDetailDTO> judgementDetailDto);

    /**
     * This method will help us to edit the content of form
     * @param judgementDetailDto
     */
    void updateJudgementDetail(JudgementDetailDTO judgementDetailDto, List<Long> removeAttendeeIds);

    /**
     * This will delete The entry of JudgementDetail
     * @param id
     */
    boolean deleteJudgementDetail(JudgementDetailDTO judgementDetailDto);


    /**
     * This will help us to get JudgementDetail Details by id
     * @param id
     * @return
     */
    JudgementDetailDTO getJudgementDetailById(Long id);

    /**
     * This method will return a current case judgment details
     * @param orgId
     * @param cseId
     * @return
     */
    List<JudgementDetailDTO> getHearingDetailByCaseId(Long orgId, Long cseId);
    
    JudgementDetailDTO getJudgeDetailByCaseId(Long orgId, Long caseId);
    
    JudgementDetailDTO getJudgeDetailsByCaseId(Long caseId);

}
