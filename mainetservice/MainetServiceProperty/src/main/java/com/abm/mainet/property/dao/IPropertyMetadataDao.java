package com.abm.mainet.property.dao;

import java.util.List;
import java.util.Map;

import com.abm.mainet.property.domain.AssesmentMastEntity;

public interface IPropertyMetadataDao {
	public List<AssesmentMastEntity> getPropertyDetails(String colname, String Colvalue, Long orgId, Long deptId,
			Map<String, Object> argumentsMap);
	
}
