package com.abm.mainet.rnl.dao;

import java.util.List;
import java.util.Map;

import com.abm.mainet.rnl.domain.EstatePropertyEntity;

public interface IRnlMetadataDao {

	public List<EstatePropertyEntity> getRnlDetails(String colname, String Colvalue, Long orgId, Long deptId,
			Map<String, Object> argumentsMap);
}
