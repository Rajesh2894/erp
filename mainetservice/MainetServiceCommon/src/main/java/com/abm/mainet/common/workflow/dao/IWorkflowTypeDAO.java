package com.abm.mainet.common.workflow.dao;

import java.math.BigDecimal;
import java.util.List;

import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.TaskSearchRequest;
import com.abm.mainet.common.workflow.dto.UserTaskDTO;
import com.abm.mainet.common.workflow.dto.WorkflowAction;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;

public interface IWorkflowTypeDAO {

    /**
     * @param orgId
     * @param serviceId
     * @param compId
     * @return
     */
    public List<WorkflowMas> getDefinedActiveWorkFlows(Long orgId, Long deptId, Long serviceId, Long compId,Long schId,Long extId);

    public List<WorkflowMas> getAllWorkFlows(Long orgId, Long deptId, Long serviceId);

    public WorkflowMas getWorkFlowTypeByOrgDepartmentAndComplaintTypeForWardZone(Long orgId, Long compId,
            Long codIdOperLevel1,
            Long codIdOperLevel2,
            Long codIdOperLevel3,
            Long codIdOperLevel4,
            Long codIdOperLevel5);

    public WorkflowMas getServiceWorkFlowForAllWardZone(Long orgId, Long deptId, Long serviceId, BigDecimal amount,
            Long sourceOfFund);
    
    public WorkflowMas getServiceWorkFlowForAllWardZone(Long orgId, Long deptId, Long serviceId, BigDecimal amount,
            Long sourceOfFund,Long getwmschcodeid2);
    

    public WorkflowMas getServiceWorkFlowForWardZone(Long orgId, Long deptId, Long serviceId, BigDecimal amount,
            Long codIdOperLevel1,
            Long codIdOperLevel2,
            Long codIdOperLevel3,
            Long codIdOperLevel4,
            Long codIdOperLevel5);

	public Long getTaskIdByAppIdAndOrgId(Long appId, Long orgId);
	
	List<Object[]> fetchClosedTaskList(TaskSearchRequest taskSearchRequest);
	
	List<Object[]> findComplaintTaskForEmployee(TaskSearchRequest taskSearchRequest);

	List<Object[]> fetchIssuedTaskList(TaskSearchRequest taskSearchRequest);
	
	public WorkflowMas getWorkFlowTypeByOrgDepartmentAndExtForWardZone(Long orgId, Long extIdentifier,
            Long codIdOperLevel1,
            Long codIdOperLevel2,
            Long codIdOperLevel3,
            Long codIdOperLevel4,
            Long codIdOperLevel5, Long serviceId);

	WorkflowMas getWorkFlowTypeByOrgDepartmentAndWmsForWardZone(Long orgId, Long getwmschcodeid2, Long codIdOperLevel1,
			Long codIdOperLevel2, Long codIdOperLevel3, Long codIdOperLevel4, Long codIdOperLevel5, Long serviceId,BigDecimal amount);

	void insertIntoTbWorkflowAction(UserTaskDTO userTaskDto, WorkflowTaskAction actionDto, Long taskId, Long wfTaskId);

	void updateTbWorkflowTask(UserTaskDTO userTaskDTO);

	void insertIntoTbWorkflowTask(UserTaskDTO userTaskDto, WorkflowTaskAction actionDto, Long taskId, Long wfTaskId);

	String getWftaskid();

	void updateTbWorkflowAction(UserTaskDTO userTaskDTO, WorkflowTaskAction actionDto);

	void updateTbWorkflowActionForHistory(UserTaskDTO userTaskDTO, WorkflowTaskAction actionDto, Long taskId);

	void updateTbWorkflowTaskForHistory(UserTaskDTO userTaskDTO, Long taskId);

	public WorkflowMas getWorkFlowByDivWardZoneAndConnSizeSpecification(Long orgId, Long deptId, Long serviceId,
			BigDecimal amount, Long codIdOperLevel1);

}	