package com.abm.mainet.common.workflow.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.TbComparamDetEntity;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.workflow.domain.RoleDecisionMstEntity;
import com.abm.mainet.common.workflow.domain.ServicesEventEntity;
import com.abm.mainet.common.workflow.dto.RoleDecisionDTO;
import com.abm.mainet.common.workflow.dto.RoleDecisionMstDTO;
import com.abm.mainet.common.workflow.repository.IRoleMstRespository;

@Service
public class RoleMasterConfigServiceImpl implements RoleMasterConfigService {

	@Autowired
	private IRoleMstRespository roleMstRespository;

	@Override
	public List<RoleDecisionMstDTO> findEventsByDeptOrgService(Long deptId, Long orgId, Long serviceId) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	@Transactional
	public boolean saveOrUpdateEventMaster(RoleDecisionMstDTO roleDecisionDTO, Employee emp, Organisation org) {
		RoleDecisionMstEntity roleDecisionMstEntity = null;

		boolean status = false;
		final List<String> roleList = roleDecisionDTO.getDecisionMapId();
		for (final String string : roleList) {
			roleDecisionMstEntity = new RoleDecisionMstEntity();
			TbComparamDetEntity tbComparamDetEntity = new TbComparamDetEntity();
			tbComparamDetEntity.setCpdId(Long.valueOf(string));
			roleDecisionMstEntity.setDeptId(roleDecisionDTO.getDeptId());
			roleDecisionMstEntity.setSmServiceId(roleDecisionDTO.getSmServiceId());
			roleDecisionMstEntity.setGmId(roleDecisionDTO.getRoleId());
			roleDecisionMstEntity.setCpdId(tbComparamDetEntity);
			roleDecisionMstEntity.setDeptId(roleDecisionDTO.getDeptId());
			roleDecisionMstEntity.setCreatedBy(emp);
			roleDecisionMstEntity.setCreatedDate(new Date());
			roleDecisionMstEntity.setOrgId(org);
			roleDecisionMstEntity.setIsActive("Y");
			roleMstRespository.save(roleDecisionMstEntity);
			status = true;
		}
		return status;
	}

	@Override
	public List<RoleDecisionDTO> findDecisionByData(Long deptId, Long roleId, Long serviceId, String status,
			Organisation organisation) {
		List<RoleDecisionDTO> roleDecisionList = new ArrayList<RoleDecisionDTO>();
		List<Long> decisionIdList = roleMstRespository.findDecisionByData(deptId, roleId, serviceId, status,
				organisation.getOrgid());
		for (Long decisionId : decisionIdList) {
			RoleDecisionDTO roleDecision = new RoleDecisionDTO();
			LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(decisionId, organisation);
			roleDecision.setDecisionId(decisionId);
			roleDecision.setDecisionDescFirst(lookUp.getDescLangFirst());
			roleDecision.setDecisionDescSecond(lookUp.getDescLangSecond());
			roleDecision.setDecisionValue(lookUp.getLookUpCode());
			roleDecisionList.add(roleDecision);
		}

		return roleDecisionList;
	}
	
	@Override
	public List<Object[]> findDecisionData(Long deptId, Long roleId, Long serviceId, String status,
			Organisation organisation) {
		List<Object[]> decisionList = new ArrayList<>();
		List<Long> decisionIdList = roleMstRespository.findDecisionByData(deptId, roleId, serviceId, status,
				organisation.getOrgid());
		for (Long decisionId : decisionIdList) {
			Object[] decision = new Object[10];
			LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(decisionId, organisation);
			decision[0] = decisionId;
			decision[1] = lookUp.getLookUpCode();
			decision[2] = lookUp.getDescLangFirst();
			decision[3] = lookUp.getDescLangSecond();
			decisionList.add(decision);
		}

		return decisionList;
	}

	@SuppressWarnings("deprecation")
	@Override
	@Transactional
	public boolean updateDecisionMapping(RoleDecisionMstDTO roleDecisionDTO, List<String> deletedList, Organisation org,
			Employee emp) {
		RoleDecisionMstEntity roleDecisionMstEntity = null;

		if (null != roleDecisionDTO.getDecisionMapId()) {
			for (final String string : roleDecisionDTO.getDecisionMapId()) {
				roleDecisionMstEntity = new RoleDecisionMstEntity();
				RoleDecisionMstEntity entity = roleMstRespository.findEntityByData(
						roleDecisionDTO.getDeptId().getDpDeptid(), roleDecisionDTO.getRoleId(),
						roleDecisionDTO.getSmServiceId(), Long.valueOf(string), org.getOrgid());
				if (entity != null) {
					entity.setUpdatedBy(emp);
					entity.setUpdatedDate(new Date());
					entity.setIsActive("Y");
					roleMstRespository.save(entity);
				} else {
					TbComparamDetEntity tbComparamDetEntity = new TbComparamDetEntity();
					tbComparamDetEntity.setCpdId(Long.valueOf(string));
					roleDecisionMstEntity.setDeptId(roleDecisionDTO.getDeptId());
					roleDecisionMstEntity.setSmServiceId(roleDecisionDTO.getSmServiceId());
					roleDecisionMstEntity.setGmId(roleDecisionDTO.getRoleId());
					roleDecisionMstEntity.setCpdId(tbComparamDetEntity);
					roleDecisionMstEntity.setDeptId(roleDecisionDTO.getDeptId());
					roleDecisionMstEntity.setOrgId(org);
					roleDecisionMstEntity.setCreatedBy(emp);
					roleDecisionMstEntity.setCreatedDate(new Date());
					roleDecisionMstEntity.setIsActive("Y");
					roleMstRespository.save(roleDecisionMstEntity);
				}
			}
		}
		for (final String string : deletedList) {
			RoleDecisionMstEntity entity = roleMstRespository.findEntityByData(
					roleDecisionDTO.getDeptId().getDpDeptid(), roleDecisionDTO.getRoleId(),
					roleDecisionDTO.getSmServiceId(), Long.valueOf(string), org.getOrgid());
			if (entity != null) {
				entity.setIsActive("N");
				entity.setUpdatedBy(emp);
				entity.setUpdatedDate(new Date());
				roleMstRespository.save(entity);
			}
		}
		return true;
	}

}
