package com.abm.mainet.common.dao;

import java.util.List;

import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.domain.ServiceMaster;

public interface IServiceMasterDao {

	Long getSeviceId(String shortCode, Long orgId);

	PortalService getPortalServiceMaster(String shortCode, Long orgId);

	ServiceMaster create(ServiceMaster serviceMaster);

	void createPortalService(PortalService portalService);

	PortalService createRestPortalService(PortalService portalService);

	PortalService getRestPortalService(String shortName, Long orgid);

	List<Object[]> findAllActiveServicesByDepartment(Long orgid, Long deptId, String activeStatus);

}
