package com.abm.mainet.workManagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.abm.mainet.workManagement.dto.WorkEstimateMasterDto;

/**
 * @author vishwajeet.kumar
 * @since 1 March 2018
 */
@Service
public interface WorkEstimateReportService {

	/**
     * Used to find find Abstract Sheet Report
     * @param workId
     * @param orgId
     * @return List<WorkEstimateMasterDto>
     */
    public List<WorkEstimateMasterDto> findAbstractSheetReport(Long workId,Long deptId,Long workType,Long orgId);

    /**
     * Used to find Measurement Report
     * @param workId
     * @param orgId
     * @return List<WorkEstimateMasterDto>
     */
    List<WorkEstimateMasterDto> findMeasurementReport(Long workId, Long orgId);
}
