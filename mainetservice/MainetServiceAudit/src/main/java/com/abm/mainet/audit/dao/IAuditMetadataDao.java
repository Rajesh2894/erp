package com.abm.mainet.audit.dao;

import java.util.List;
import java.util.Map;

import com.abm.mainet.audit.domain.AuditParaEntryEntity;

public interface IAuditMetadataDao {

	public List<AuditParaEntryEntity> getAuditDetails(String colname, String Colvalue, Long orgId, Long deptId,
			Map<String, Object> argumentsMap);
}
