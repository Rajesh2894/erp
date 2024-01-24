/**
 * @author Lalit.Prusti
 * 
 */
package com.abm.mainet.common.service;

import java.util.List;

import com.abm.mainet.common.dto.HelpDeskDTO;

/**
 * The Interface HelpDeskService.
 */
public interface IHelpDeskService {

    /**
     * Gets the all individual and team callLog.
     *
     * @param orgId the organization id
     * @param empId the employee id
     * @return the all individual and team callLog
     */
    List<HelpDeskDTO> getAllIndividualAndTeamCallLog(Long orgId, Long empId);

    /**
     * Save or update callLog.
     *
     * @param callLog the callLog
     * @return the callLog management DTO
     */
    HelpDeskDTO saveOrUpdateCallLog(HelpDeskDTO callLog);

    /**
     * Gets the callLog.
     *
     * @param callLogId the callLog id
     * @return the callLog
     */
    HelpDeskDTO getCallLog(Long callLogId);

    /**
     * Delete callLog.
     *
     * @param callLogId the callLog id
     * @return the callLog management DTO
     */
    boolean deleteCallLog(Long callLogId);

    /**
     * Find all callLog by orgid.
     *
     * @param orgId the org id
     * @return the list of Activities
     */
    List<HelpDeskDTO> findAllCallLogByOrgid(Long orgId);

	List<HelpDeskDTO> getAllCallLog();
}
