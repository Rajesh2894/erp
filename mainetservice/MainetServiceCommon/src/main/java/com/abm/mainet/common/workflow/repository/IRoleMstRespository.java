package com.abm.mainet.common.workflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.workflow.domain.RoleDecisionMstEntity;

public interface IRoleMstRespository extends CrudRepository<RoleDecisionMstEntity, Long> {

	@Query("select rl.cpdId.cpdId from RoleDecisionMstEntity rl where rl.deptId.dpDeptid=:deptId and rl.gmId=:roleId and rl.smServiceId=:serviceId and rl.isActive=:status and rl.orgId.orgid=:orgId ")
	List<Long> findDecisionByData(@Param("deptId") Long deptId, @Param("roleId") Long roleId,
			@Param("serviceId") Long serviceId, @Param("status") String status, @Param("orgId") Long orgId);

	@Query("select rl from RoleDecisionMstEntity rl where rl.deptId.dpDeptid=:deptId and rl.gmId=:roleId and rl.smServiceId=:serviceId and rl.cpdId.cpdId=:cpdId and rl.orgId.orgid=:orgId ")
	RoleDecisionMstEntity findEntityByData(@Param("deptId") Long deptId, @Param("roleId") Long roleId,
			@Param("serviceId") Long serviceId, @Param("cpdId")Long cpdId,@Param("orgId") Long orgid);

}
