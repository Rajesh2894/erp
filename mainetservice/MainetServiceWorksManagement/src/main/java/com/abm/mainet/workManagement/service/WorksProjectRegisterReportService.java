package com.abm.mainet.workManagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.abm.mainet.workManagement.dto.WmsProjectStatusReportDetDto;

@Service
public interface WorksProjectRegisterReportService {

    /**
     * Used to find Project Status Report
     * @param schId
     * @param projId
     * @param workType
     * @return List<WmsProjectStatusReportDetDto>
     */

    public List<WmsProjectStatusReportDetDto> findProjectRegisterSheetReport(Long schId, Long projId, Long workType, Long orgId);

}