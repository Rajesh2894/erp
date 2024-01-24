package com.abm.mainet.dashboard.citizen.dao;

import java.util.List;

import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;

public interface ICitizenDashBoardDao {

    List<Object[]> getApplicationList(String flag, Employee employee, Organisation orgId);

    int countForApplicationStatus(String flag, Employee employee, Organisation orgId);

    List<Object[]> getApplicationListForAdmin(String flag, Organisation organisation);

    int countForApplicationStatusForAdmin(String flag, Organisation organisation);
}
