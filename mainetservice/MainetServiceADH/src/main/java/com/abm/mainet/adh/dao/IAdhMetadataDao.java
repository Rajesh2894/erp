package com.abm.mainet.adh.dao;

import java.util.List;
import java.util.Map;

import com.abm.mainet.common.domain.ContractMastEntity;

public interface IAdhMetadataDao {

	public List<ContractMastEntity> getAdhDetails(String colname, String Colvalue, Long orgId, Long deptId,
			Map<String, Object> argumentsMap);

}
