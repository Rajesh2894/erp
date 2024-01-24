package com.abm.mainet.common.master.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.dto.AdvanceRequisitionDto;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;

/**
 * 
 * @author Jeetendra.Pal
 *
 */
public interface IAdvanceRequisitionService {

	Map<Long, String> getBudgetHeadAllData(Long acountSubType, Organisation organisation, int langId);

	/**
	 * used to Search Requisition
	 * 
	 * @param advanceEntryDate
	 * @param vendorId
	 * @param deptId
	 * @param orgId
	 * @return
	 */
	List<AdvanceRequisitionDto> getFilterRequisition(Date advanceEntryDate, Long vendorId, Long deptId, Long orgId);

	/**
	 * used to save Update Advance Requisition
	 * 
	 * @param advanceRequisitionDto
	 * @param removeFileById
	 * @return
	 */
	AdvanceRequisitionDto saveUpdateAdvanceRequisition(AdvanceRequisitionDto advanceRequisitionDto,
			String removeFileById);

	/**
	 * used to get Advance Requisition details(Web Service)
	 * 
	 * @param deptCode
	 * @param vendorId
	 * @param referenceNumber
	 * @param orgId
	 * @return
	 */
	List<AdvanceRequisitionDto> getWorkOrderDetails(String deptCode, Long vendorId, Long orgId);

	/**
	 * used to get Advance Requisition By Id
	 * 
	 * @param advId
	 * @return
	 */
	AdvanceRequisitionDto getAdvanceRequisitionById(Long advId);

	/**
	 * used to get employee type or vendor type List based on Advance Type(if
	 * advType='C'- then vendor else employee)
	 * 
	 * @param advType
	 * @param referenceId
	 * @param orgId
	 * @return
	 */
	Map<Long, String> getEmployeeVendorType(String advType, Long referenceId, Long orgId);

	/**
	 * used to get Used Contract Amount By Reference Number
	 * 
	 * @param referenceNumber
	 * @param orgId
	 * @return
	 */
	BigDecimal getUsedContractAmountByReferenceNumber(String referenceNumber, Long orgId);

	/**
	 * used to update Advance Requisition status
	 * 
	 * @param advId
	 * @param flagp
	 */
	void updateAdvanceRequisitionMode(Long advId, String flagp);

	/**
	 * used to get Advance Requisition By Arn Number
	 * 
	 * @param arnNumber
	 * @return
	 */
	AdvanceRequisitionDto getAdvanceRequisitionByArn(String arnNumber,Long orgId);

	/**
	 * used to initiate and update work flow process
	 * 
	 * @param workflowActionDto
	 * @param workFlowId
	 * @param url
	 * @param workFlowFlag
	 */
	void initiateWorkFlow(WorkflowTaskAction workflowActionDto, Long workFlowId, String url, String workFlowFlag);

	/**
	 * update bill no and date after bill posting in acount
	 * 
	 * @param billNumber
	 * @param advId
	 */
	void updateBillNumberByAdvId(String billNumber, Long advId);

}
