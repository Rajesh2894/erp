package com.abm.mainet.workManagement.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.abm.mainet.workManagement.dto.WorksDepositRegisterReportDto;

@Service
public interface WorksDepositRegisterReportService {

    /**
     * Used to find Works Deposit Register Report
     * @param orgId
     * @param fromDate
     * @param toDate
     * @return List<WorksDepositRegisterReportDto>
     */

    public List<WorksDepositRegisterReportDto> getWorksDepositRegisterReport(Long orgId, Date fromDate, Date toDate);

}