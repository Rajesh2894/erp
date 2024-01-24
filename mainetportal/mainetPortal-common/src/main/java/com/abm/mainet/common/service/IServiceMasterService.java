package com.abm.mainet.common.service;

import java.util.List;

import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.PortalServiceDTO;

public interface IServiceMasterService {

    Long getServiceId(String shortCode, Long orgId);

    PortalService getPortalServiceMaster(String shortCode, Long orgId);

    public void create(ServiceMaster serviceMaster);

    public void createPortalService(PortalServiceDTO portalserviceDto);

    public PortalService createRestPortalService(PortalService portalService);

    public PortalService findShortCodeByOrgId(final String shortName, Long orgid);

	 public List<Object[]> findAllActiveServicesByDepartment(Long orgid, Long deptId, String activeStatus);
	
	 

}
