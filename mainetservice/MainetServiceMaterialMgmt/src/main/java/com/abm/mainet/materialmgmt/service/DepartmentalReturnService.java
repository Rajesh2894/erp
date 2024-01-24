package com.abm.mainet.materialmgmt.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.materialmgmt.dto.BinLocMasDto;
import com.abm.mainet.materialmgmt.dto.DeptReturnDTO;

import com.abm.mainet.materialmgmt.dto.IndentProcessDTO;

public interface DepartmentalReturnService {


	void updateIndentReturnStatus(Long orgId, String dreturnno, Character status, String wfFlag);

	void updateIndentItemReturnStatus(Long orgId, Long dreturnno, Character status);
	
	void updateWorkFlowService(WorkflowTaskAction workflowTaskAction, ServiceMaster serviceMas);
	
	DeptReturnDTO saveDepartmentalReturn(DeptReturnDTO deptReturnDTO, long levelCheck,
			WorkflowTaskAction workflowTaskAction);
	
	List<IndentProcessDTO> findIndentByEmpId(Long indenter, Long orgId, String status);
	
	DeptReturnDTO getIndentReturnDataById(String indentNo, Long orgId);

	List<IndentProcessDTO> findindentBystatus(String status, Long orgId);

	DeptReturnDTO findItemInfoByIndentIdORG(DeptReturnDTO deptReturnDTO, Long ORGID);

	List<DeptReturnDTO> fetchindentReturnSummaryData(Long orgId);

	List<DeptReturnDTO> searchStoresReturnData(String dreturnno, Long indentid, Date fromDate, Date toDate,
			Long storeid, Character status, Long orgId);

	List<BinLocMasDto> getBinlocationList(Long orgid);

	
}
