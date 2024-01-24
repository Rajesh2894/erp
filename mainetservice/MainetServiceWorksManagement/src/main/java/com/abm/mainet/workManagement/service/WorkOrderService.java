package com.abm.mainet.workManagement.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.workManagement.domain.WorkOrder;
import com.abm.mainet.workManagement.dto.WorkOrderContractDetailsDto;
import com.abm.mainet.workManagement.dto.WorkOrderDto;

/**
 * @author vishwajeet.kumar
 * @since 14 May 2018
 */
public interface WorkOrderService {

	/**
	 * used to get work order and terms details by work order number
	 * 
	 * @param orderId
	 * @return
	 */
	WorkOrderDto getWorkOredrByOrderId(Long orderId);

	/**
	 * this service is used to get all work order based on organization id
	 * 
	 * @param orgId
	 * @return List<WorkOrderDto>
	 */
	List<WorkOrderDto> getAllWorkOrder(Long orgId);

	/**
	 * used to get work order details based on work order id in which works are not
	 * assigned
	 * 
	 * @param orderId
	 * @return
	 */
	WorkOrderDto getNotAssigneedWorksByWorkOrder(Long orderId);

	/**
	 * used to create Work Order Generation
	 * 
	 * @param workOrderDto
	 * @param attachments
	 * @param requestDTO
	 * @return
	 */
	WorkOrderDto createWorkOrderGeneration(WorkOrderDto workOrderDto, List<DocumentDetailsVO> attachments,
			RequestDTO requestDTO, Long deptId);

	/**
	 * Used to Update update Work Order Generation
	 * 
	 * @param workOrderDto
	 * @param attachments
	 * @param requestDTO
	 * @param removeFileId
	 * @param removeTermsById
	 */
	void updateWorkOrderGeneration(WorkOrderDto workOrderDto, List<DocumentDetailsVO> attachments,
			RequestDTO requestDTO, List<Long> removeFileId, List<Long> removeTermsById);

	/**
	 * used to get filet work order List by different parameters.
	 * 
	 * @param workOrderNo
	 * @param workOrderDate
	 * @param agreementFormDate
	 * @param agreementToDate
	 * @param vendorName
	 * @param orgId
	 * @return List<WorkOrderDto>
	 */
	List<WorkOrderDto> getFilterWorkOrderGenerationList(String workOrderNo, Date workOrderDate, Date contractFromDate,
			Date contractToDate, String vendorName, Long orgId);

	/**
	 * get all work order id and work code list
	 * 
	 * @param orgid
	 * @return
	 */
	List<Object[]> getAllWorkOrderIdAndNoList(Long orgid);

	/**
	 * this service is used to get all work order based on organization id
	 * 
	 * @param orgId
	 * @return List<WorkOrderDto>
	 */
	List<WorkOrderDto> getAllWorkOrderGroupByAssignee(Long orgId);

	/**
	 * 
	 * @param orderId
	 * @return
	 */
	List<WorkOrderDto> searchWorkOrderGroupByAssignee(Long orderId);

	/**
	 * get tender assigned work for update which will contain those work that
	 * assigned to employee and work which are not assigned to any one.
	 * 
	 * @param orderId
	 * @param empId
	 * @return WorkOrderDto
	 */
	WorkOrderDto getTenderWorkForUpdateByWorkIdAndAssignee(Long orderId, Long empId);

	/**
	 * get tender assigned work which will contain those work that assigned to
	 * employee.
	 * 
	 * @param orderId
	 * @param empId
	 * @return WorkOrderDto
	 */
	WorkOrderDto getAssigneeTenderWork(Long orderId, Long empId);

	/**
	 * used to get Work Order Number list
	 * 
	 * @param workOrderNumber
	 * @param orgId
	 * @param referenceNumber
	 * @return
	 */
	List<WorkOrderDto> workOrderList(Long venderId, Long orgId);

	/**
	 * find All Contract Details By OrgId on the basis of NOT IN condition
	 * 
	 * @param orgId
	 * @return List<WorkOrderContractDetailsDto>
	 */

	List<WorkOrderContractDetailsDto> findAllContractDetailsByOrgId(Long orgId);

	/**
	 * find All Contract Details By OrgId on the basis of IN condition
	 * 
	 * @param orgId
	 * @return List<WorkOrderContractDetailsDto>
	 */
	List<WorkOrderContractDetailsDto> getAllContractDetailsInWorkOrderByOrgId(Long orgId);

	/**
	 * get All Summary Contract Details
	 * 
	 * @param orgId
	 * @return List<WorkOrderContractDetailsDto>
	 */

	List<WorkOrderContractDetailsDto> getAllSummaryContractDetails(Long orgId);

	/**
	 * update Contract Variation Approval Status Pending ,Draft,Approved And Reject
	 * 
	 * @param contId
	 * @param flag
	 */
	void updateContractVariationStatus(Long contId, String flag);

	/**
	 * fetch Work Order By Contract Id
	 * 
	 * @param contId
	 * @param orgId
	 * @return WorkOrderDto
	 */
	WorkOrderDto fetchWorkOrderByContId(Long contId, Long orgId);

	/**
	 * get Vendor Details Against Which Work order Generated
	 * 
	 * @param orgId
	 * @return
	 */
	List<WorkOrderContractDetailsDto> findAllWorkOrderGeneratedVendorDetail(Long orgId);

	/**
	 * this service is used to get all work order for Legacy Works based on organization id and Legacy Id
	 * 
	 * @param orgId
	 * @return List<WorkOrderDto>
	 */
	List<WorkOrderDto> getAllLegacyWorkOrder(String fromLegacy,Long orgId);
	
	//added by sadik.shaikh
	WorkOrder getWorkOrderbyorderNo(String orderNo);

	List<WorkOrderDto> getFilterWorkOrderGeneration(String workOrderNo, Date workStipulatedDate, Long codId1,
			Long codId2, Long codId3, Long orgId, Long dpDeptId);

	WorkOrderDto getWorkOredrByWorkId(Long workId);
 
}
