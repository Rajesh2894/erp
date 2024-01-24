/*
 * 
 */
package com.abm.mainet.swm.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import com.abm.mainet.swm.dto.EmployeeScheduleDTO;

/**
 * The Interface EmployeeScheduleService.
 *
 * @author Lalit.Prusti
 *
 * Created Date : 09-Jun-2018
 */
@WebService
public interface IEmployeeScheduleService {

    /**
     * Gets the employeeSchedule by employeeSchedule id.
     *
     * @param employeeScheduleId the employeeSchedule id
     * @return the employeeSchedule by employeeSchedule id
     */
    EmployeeScheduleDTO getByEmployeeScheduleId(Long employeeScheduleId);

    /**
     * Save employeeSchedule.
     *
     * @param employeeScheduleDetails the employee schedule schedule details
     * @return the employeeSchedule master DTO
     */
    EmployeeScheduleDTO save(EmployeeScheduleDTO employeeScheduleDetails);

    /**
     * Update employeeSchedule.
     *
     * @param employeeScheduleDetails the employee schedule schedule details
     * @return the employeeSchedule master DTO
     */
    EmployeeScheduleDTO update(EmployeeScheduleDTO employeeScheduleDetails);

    /**
     * Delete employeeSchedule.
     *
     * @param employeeScheduleId the employeeSchedule id
     */
    void delete(Long employeeScheduleId, Long empId, String ipMacAdd);

    /**
     * Search employeeSchedule schedule by employeeSchedule type and employeeSchedule reg no.
     *
     * @param employeeScheduleType the employeeSchedule type
     * @param employeeScheduleNo the employeeSchedule no
     * @param orgId the org id
     * @return the list
     */
    List<EmployeeScheduleDTO> search(Long employeeId, Date fromDate, Date toDate, Long orgId);

    /**
     * get Employee Shedule Details For Reports
     * @param OrgId
     * @param empId
     * @param scheduleType
     * @param fromDate
     * @param toDate
     * @return
     */
    EmployeeScheduleDTO getEmployeeSheduleDetailsForReports(Long OrgId, Long empId, String scheduleType, Date fromDate,
            Date toDate);

    /**
     * validate EmpScheduling
     * @param employeeScheduleDto
     * @return
     */
    List<String> validateEmpScheduling(EmployeeScheduleDTO employeeScheduleDto);

    /**
     * all Sheduled Employee
     * @param employeeId
     * @param orgId
     * @return
     */
    List<EmployeeScheduleDTO> allSheduledEmployee(Long employeeId, Long orgId);

}
