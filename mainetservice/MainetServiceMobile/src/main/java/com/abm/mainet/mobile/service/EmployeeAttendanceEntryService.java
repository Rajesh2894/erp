package com.abm.mainet.mobile.service;

import javax.validation.Valid;

import com.abm.mainet.common.dto.EmployeeAttendanceDTO;

public interface EmployeeAttendanceEntryService {
public EmployeeAttendanceDTO saveEmployeeAttendanceData(EmployeeAttendanceDTO dto);

public EmployeeAttendanceDTO fetchEmployeeAttendance( EmployeeAttendanceDTO dto);
}
