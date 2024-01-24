package com.abm.mainet.materialmgmt.service;

import java.util.List;

import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.materialmgmt.dto.BinLocMasDto;
import com.abm.mainet.materialmgmt.dto.DeptReturnDTO;
import com.abm.mainet.materialmgmt.dto.IndentIssueDto;
import com.abm.mainet.materialmgmt.dto.IndentProcessDTO;


public interface IndentProcessService {

	IndentProcessDTO saveIndentProcess(IndentProcessDTO IndentProcessDTO, long levelCheck,WorkflowTaskAction workflowTaskAction);

	List<IndentProcessDTO> searchIndentByStoreName(Long storeid, String indentno, Long deptId, Long indenter, String status, Long orgid);

	IndentProcessDTO getIndentDataByIndentNo(String indentNo, Long orgId);

	void updateIndentStatus(Long orgId, String indentNo, String status, String wfFlag);

	List<IndentProcessDTO> findIndentByorgId(Long orgId);

	void updateWorkFlowService(WorkflowTaskAction workflowTaskAction, ServiceMaster serviceMas);

	List<String> getItemNumberListByBinLoc(Long binLocation, Long itemid, Long storeId, Long orgId);

	Double getSumOfAvailbleQuantities(Long binLocation, Long itemid, String itemNo, Long storeId, Long orgId);

	Double getSumOfNotInBatchAvailbleQuantities(Long binLocation, Long itemid, Long storeId, Long orgId);

	List<BinLocMasDto> findAllBinLocationByItemID(Long orgId, Long storeId, Long itemId);

	IndentProcessDTO getIndentDataByIndentId(Long indentId, Long orgId);

	Double fetchBalanceQuantityForIndent(Long orgId, Long storeId, Long itemid, Long binLocation, String itemNo);

}
