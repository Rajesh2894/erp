/**
 * 
 */
package com.abm.mainet.workManagement.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.workManagement.dto.ProjectProgressDto;

/**
 * @author Saiprasad.Vengurekar
 *
 */
public interface ProjectProgressService {

    /**
     * get All Project Progress By Date
     * @param fromDate
     * @param toDate
     * @param orgId
     * @return
     */
    List<ProjectProgressDto> getAllProjectProgressByDate(Date fromDate, Date toDate, Long orgId);
}
