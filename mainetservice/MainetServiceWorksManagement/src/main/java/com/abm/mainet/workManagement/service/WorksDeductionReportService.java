package com.abm.mainet.workManagement.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.abm.mainet.workManagement.dto.WorksDeductionReportDto;

@Service
public interface WorksDeductionReportService {

    /**
     * Used to find Works Deduction Report
     * @param orgId
     * @param fromDate
     * @param toDate
     * @return List<WorksDeductionReportDto>
     */

    public List<WorksDeductionReportDto> getWorksDeductionReport(Long orgId, Date fromDate, Date toDate);

}