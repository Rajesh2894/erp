package com.abm.mainet.swm.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.swm.domain.EmployeeSchedule;

/**
 * @author Ajay.Kumar
 *
 */
public interface IEmployeeScheduleDAO {

    /**
     * searchEmployeeScheduleByEmployeeName
     * @param empid
     * @param fromDate
     * @param toDate
     * @param orgId
     * @return
     */
    List<EmployeeSchedule> searchEmployeeScheduleByEmployeeName(Long empid, Date fromDate, Date toDate, Long orgId);

}
