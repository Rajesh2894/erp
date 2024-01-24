package com.abm.mainet.audit.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.audit.domain.AuditParaEntryEntity;

public interface IAuditParaEntryDao {
	
	 List<AuditParaEntryEntity> searchAuditParaEntry(Long auditType, Long auditDeptId, Long orgId, Long auditParaZone, Long auditParaStatus, String auditParaCode, Date fromDate, Date toDate);
	
}
