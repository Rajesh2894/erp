package com.abm.mainet.legal.service;

import java.util.List;

import javax.jws.WebService;

import com.abm.mainet.legal.domain.JudgeDetailMaster;
import com.abm.mainet.legal.dto.JudgeMasterDTO;

/**
 * This is mainly for CRUD operation
 * @author rahul.maurya
 *
 */
@WebService
public interface IJudgeMasterService {
    /**
     * This method will save form content in the database
     * @param judgeMasterDto
     */
    void saveJudgeMaster(JudgeMasterDTO judgeMasterDto);

    /**
     * This method will help us to edit the content of form
     * @param judgeMasterDto
     */
    void updateJudgeMaster(JudgeMasterDTO judgeMasterDto);

    /**
     * This will delete The entry of judge
     * @param id
     */
    boolean deleteJudgeMaster(JudgeMasterDTO judgeMasterDto);

    /**
     * This will return active judge list
     * @return
     */
    List<JudgeMasterDTO> getAllJudgeMaster(Long orgId);

    /**
     * This will help us to get Judge Details by id
     * @param id
     * @return
     */
    JudgeMasterDTO getJudgeMasterById(Long id);

    // only use for find benchName at JudgementMasterController
    List<JudgeDetailMaster> fetchJudgeDetailsByCrtId(Long crtId, Long cseId,Long orgId );

	List<JudgeMasterDTO> getjudgeDetails(Long crtId, String judgeStatus, String judgeBenchName, Long orgid);
}
