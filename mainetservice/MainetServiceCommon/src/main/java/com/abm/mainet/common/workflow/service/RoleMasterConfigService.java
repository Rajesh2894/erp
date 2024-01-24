package com.abm.mainet.common.workflow.service;

import java.util.List;

import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.workflow.dto.RoleDecisionDTO;
import com.abm.mainet.common.workflow.dto.RoleDecisionMstDTO;

public interface RoleMasterConfigService {

	public List<RoleDecisionMstDTO> findEventsByDeptOrgService(final Long deptId, final Long orgId,
			final Long serviceId);

	public boolean saveOrUpdateEventMaster(RoleDecisionMstDTO roleDecisionDTO, Employee emp, Organisation org);

	public List<RoleDecisionDTO> findDecisionByData(Long deptId, Long roleId, Long serviceId,String status,
			Organisation organisation);

	public boolean updateDecisionMapping(RoleDecisionMstDTO serviceEventDto, List<String> deletedList, Organisation org,
			Employee emp);
	
	public List<Object[]> findDecisionData(Long deptId, Long roleId, Long serviceId,String status,
			Organisation organisation);
}
