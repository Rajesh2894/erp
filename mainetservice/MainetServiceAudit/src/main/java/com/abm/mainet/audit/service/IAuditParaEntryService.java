package com.abm.mainet.audit.service;



import java.util.Date;
import java.util.List;
import java.util.Map;

import com.abm.mainet.audit.dto.AuditParaEntryDto;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;


public interface IAuditParaEntryService {
	
	
    void saveAuditParaEntryService(AuditParaEntryDto auditParaEntryDto, String saveMode);

	//List<AuditParaEntryDto> searchAuditParaService(Long auditType, Long auditDeptId,Long orgId, Long auditWard1, Long auditParaStatus);

	AuditParaEntryDto getAuditParaEntryByAuditParaId(Long auditParaId);

	String updateWorkFlowAuditService(WorkflowTaskAction workflowTaskAction);
	
	AuditParaEntryDto getAuditParaEntryByAuditParaCodeandOrgId(String auditParaCode, long orgId);

	void updateAuditWfStatus(String auditParaCode, String flag);

	void updateAuditParaStatus(String auditParaCode, Long flag);
	
	//Long loadStatus(String code,String prefix);

	// int getWorkFlowStatus(String auditParaCode, String task, Long orgId);

//	Map<String, String> getEmpByAuditDeptId(String auditParaCode, String deptId);	
	
	 //WorkflowTaskAction prepareWorkFlowTaskAction(AuditParaEntryDto auditParaEntryDto) ;

	Long initiateWorkFlow(AuditParaEntryDto auditParaEntryDto);

	List<AuditParaEntryDto> searchAuditParaService(Long auditType, Long auditDeptId, Long orgId, Long auditWard1,
			Long auditParaStatus, String auditParaCode, Date fromDate, Date toDate);

	Long loadStatus(String code, String prefix);

	List<AuditParaEntryDto> getAuditParaEntryByOrgId(long orgId);

	List<LookUp> fetchChiefAuditorDept();

	Map<String, String> getEmpList(String deptId);

	void updateHistoryforWorkflow(AuditParaEntryDto dto, String saveMode);
	
	List<Object[]> findWorkFlowTaskByRefId(String refId, Long orgId);

	void updateRemarks(AuditParaEntryDto approvalAuditParaDto);
	
	void updateAuditParaStatusAndDate(String auditParaCode, Long flag,Date date);

	void updateAuditWfStatusWithParaID(Long auditParaId, String flag);

	void updateAuditParaStatusAndDatewithID(Long auditParaCode, Long flag, Date date);

	void updateAuditParaStatusById(Long auditParaId, Long flag);
	
	void updateAuditParaSubUnitWithID(Long auditParaId, Integer subUnitClosed);

	List<Object[]> getAuditReportData(Long deptId, Long auditParaYr, String formDate, String toDate, Long orgid);

	void updateAuditParaSubDoneAndPendingWithID(Long auditParaCode, String subUnitCompDone, String subUnitCompPending);
	
}
