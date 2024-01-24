
package com.abm.mainet.common.workflow.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.WorkFlowTypeGrid;
import com.abm.mainet.common.workflow.dto.WorkflowMasDTO;

/**
 * WorkFlowTypeService is service provided to manage workflow master data, like defining workflow managing task in it.
 * 
 * 
 * @author ritesh.patil
 *
 */
public interface IWorkFlowTypeService {

    List<Object[]> findAllEventsByDeptAndServiceId(Long orgId, Long deptId, Long serviceId);

    /**
     * This method save workflow master details
     * 
     * @param dto
     * @return
     */
    boolean saveForm(WorkflowMasDTO dto);

    /**
     * This method retrieve workflowMaster workflow Id and return WorkflowMasDTO
     * @param id
     * @return
     */
    WorkflowMasDTO findById(Long id);

    /**
     * This method retrieve workflowMaster workflow Id and return WorkflowMas
     * 
     * @param id
     * @return
     */
    WorkflowMas getWorkFlowById(Long id);

    /**
     * This method update workflow master details
     * 
     * @param dto
     * @return
     */
    boolean saveEdit(WorkflowMasDTO dto, List<Long> removeChildIds);

    /**
     * This method retrieve list of WorkflowMas by using orgId, deptId, and complaintId
     * 
     * @param orgId
     * @param deptId
     * @param compId
     * @return
     */
    List<WorkflowMas> getWorkFlowTypeByOrgDepartmentAndComplaintType(Long orgId, Long deptId, Long compId);

    /**
     * This method soft delete workflow master details
     * 
     * @param dto
     * @return
     */
    boolean deleteWorkFLow(String flag, Long empId, Long id);

    /**
     * List workflow by workflow status and workflow type
     * 
     * @param status
     * @param type
     * @return
     */
    List<WorkFlowTypeGrid> findAllRecords(String status, String type);

    /**
     * List workflow by workflow status depetId, orgId, serviceId
     * 
     * @param orgId
     * @param deptId
     * @param serviceId
     * @param status
     * @return
     */
    List<WorkFlowTypeGrid> findRecords(Long orgId, Long deptId, Long serviceId, String status);

    /**
     * check if workflow id present in active workflow list or not
     * 
     * @param deletedList
     * @param orgId
     * @param serviceId
     * @param deptId
     * @return
     */
    boolean isEventMapped(List<Long> deletedList, Long orgId, Long deptId, Long serviceId);

    /**
     * This method will return list completed work-flow checker events associated with workflowTypeId and taskId. By default this
     * method will make use of task event list exist in BPM process by making REST call to BPM service with taskId.
     * 
     * 
     * @param workflowTypeId
     * @param taskId
     * @return
     */
    Set<LookUp> getCheckerSendBackEventList(Long workflowTypeId, Long taskId);

    /**
     * This method will returns true if given task id is last task in checker task list. This method will make use of task event
     * list exist in BPM process by making REST call to BPM service with taskId.
     * 
     * @param taskId
     * @return
     */
    boolean isLastTaskInCheckerTaskList(Long taskId);

    /**
     * This method will returns true if given task id is last task in current checker group. This method will make use of task
     * event list exist in BPM process by making REST call to BPM service with taskId.
     * 
     * @param taskId
     * @return
     */
    boolean isLastTaskInCheckerTaskListGroup(Long taskId);

    /**
     * This method find and returns department Id by taskName.
     * 
     * @param taskName
     * @param orgId
     * @param workFlowId
     * @return deptId
     */
    Long findDepartmentIdBytaskName(String taskName, Long orgId, Long workFlowId);

    /**
     * This method will check if workflow already exist or not. This method will accept all parameter which are required to
     * identify unique workflow from master.
     * 
     * @param orgId
     * @param deptId
     * @param serviceId
     * @param compId
     * @param wardZoneType
     * @param codIdOperLevel1
     * @param codIdOperLevel2
     * @param codIdOperLevel3
     * @param codIdOperLevel4
     * @param codIdOperLevel5
     * @param fromAmount
     * @param toAmount
     * @param souceOfFund
     * @param extIdentifier 
     * @return
     */
    boolean isWorkFlowExist(Long orgId, Long deptId, Long serviceId, Long compId, String wardZoneType, Long codIdOperLevel1,
            Long codIdOperLevel2, Long codIdOperLevel3, Long codIdOperLevel4, Long codIdOperLevel5, BigDecimal fromAmount,
            BigDecimal toAmount, Long souceOfFund, Long schemeId, Long extIdentifier);

    /**
     * To get All Workflow defined departmentIds By orgId
     * 
     * @param orgId
     * @return List<TbDepartment>
     */
    List<TbDepartment> getAllWorkflowDefinedDepartmentsByOrgId(Long orgId, String serviceShortCode);

    boolean checkPendingTask(List<Long> wfdId, Long orgId);

    boolean curentCheckerLevel(Long taskId);
}
