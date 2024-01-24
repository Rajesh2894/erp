package com.abm.mainet.cfc.objection.dao;

import java.util.List;

import com.abm.mainet.cfc.objection.domain.TbObjectionEntity;
import com.abm.mainet.cfc.objection.dto.ObjectionDetailsDto;

public interface IObjectionServiceDAO {

    void saveObjectionDetails(TbObjectionEntity tbObjectionEntity);

    List<TbObjectionEntity> getObjectionDetails(ObjectionDetailsDto objectionDetailsDto);

    String getdepartmentNameById(Long deptId);

    List<TbObjectionEntity> getObjectionDetails(Long orgId, Long objectionDeptId, Long serviceId, String refNo, Long objectionOn);

}
