package com.abm.mainet.rts.dao;

import java.util.List;

import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;

public interface DrainageConnectionServiceDAO {

	
	List<TbCfcApplicationMstEntity> searchData(Long applicationId, Long serviceId, Long orgId);
}
