/**
 * @author Lalit.Prusti
 * 
 */
package com.abm.mainet.common.service;

import java.util.List;

import com.abm.mainet.common.dto.ActivityManagementDTO;

/**
 * The Interface ActivityManagementService.
 */
public interface IActivityManagementService {

    /**
     * Gets the all individual and team activity.
     *
     * @param orgId the organization id
     * @param empId the employee id
     * @return the all individual and team activity
     */
    List<ActivityManagementDTO> getAllIndividualAndTeamActivity(Long orgId, Long empId);

    /**
     * Save or update activity.
     *
     * @param activity the activity
     * @return the activity management DTO
     */
    ActivityManagementDTO saveOrUpdateActivity(ActivityManagementDTO activity);

    /**
     * Gets the activity.
     *
     * @param activityId the activity id
     * @return the activity
     */
    ActivityManagementDTO getActivity(Long activityId);

    /**
     * Delete activity.
     *
     * @param activityId the activity id
     * @return the activity management DTO
     */
    boolean deleteActivity(Long activityId);

    /**
     * Find all activity by orgid.
     *
     * @param orgId the org id
     * @return the list of Activities
     */
    List<ActivityManagementDTO> findAllActivityByOrgid(Long orgId);
}
