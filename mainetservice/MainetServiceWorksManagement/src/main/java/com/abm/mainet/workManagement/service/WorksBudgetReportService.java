package com.abm.mainet.workManagement.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.abm.mainet.workManagement.dto.WorksBudgetReportDto;

@Service
public interface WorksBudgetReportService {

    /**
     * Used to find Budget Vs Project Expenses Report
     * @param fromDate
     * @param toDate
     * @param orgId
     * @return List<WorksBudgetReportDto>
     */
    public List<WorksBudgetReportDto> getBudgetVsProjExpensesReport(Date fromDate, Date toDate, Long orgId);

}