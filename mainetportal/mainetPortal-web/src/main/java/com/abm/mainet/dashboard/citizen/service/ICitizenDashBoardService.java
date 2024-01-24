
package com.abm.mainet.dashboard.citizen.service;

import java.util.List;

import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.ApplicationPortalMasterDTO;

public interface ICitizenDashBoardService {

    List<ApplicationPortalMasterDTO> findApplicationStatusList(String flag, Employee employees, Organisation orgId, int langId);

    int countForApplicationStatus(String flag, Employee employees, Organisation orgId);

    List<ApplicationPortalMasterDTO> getApplicationListForAdmin(String flag, Organisation organisation, int langId);

    int countForApplicationStatusForAdmin(String flag, Organisation organisation);
}
