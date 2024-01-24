package com.abm.mainet.common.workflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.workflow.domain.WorkflowDet;

@Repository
public interface WorkflowMappingRepository extends JpaRepository<WorkflowDet, Long> {

	@Query("SELECT m FROM WorkflowDet m where m.servicesEventEntity.systemModuleFunction.smfname =:eventName and m.servicesEventEntity.systemModuleFunction.smfaction =:url")
	public WorkflowDet getAllWorkFlowMappingByEventNameURL(@Param("url") String url,
			@Param("eventName") String eventName);

	@Query("SELECT m FROM WorkflowDet m left join m.wfId w where m.servicesEventEntity.systemModuleFunction.smfdescription =:smfdescription and w.wfId=:wfId")
	public List<WorkflowDet> getAllWorkFlowMappingByEventDescription(@Param("smfdescription") String smfdescription,
			@Param("wfId") Long wfId);

	@Query(value = "SELECT wt.WFTASK_ID, wt.WORKFLOW_REQ_ID,wt.EVENT_ID from tb_workflow_task wt where wt.WF_ID =:wfId AND wt.EVENT_ID =:eventId AND wt.TASK_STATUS !='COMPLETED' AND wt.ORGID =:orgId", nativeQuery = true)
	Object[] getPendingTaskPresent(@Param("wfId") Long wfId, @Param("eventId") Long eventId,
			@Param("orgId") Long orgId);

	@Query(value = "SELECT wt.TASK_ID from tb_workflow_task wt where   wt.SMFACTION='LoiPayment.html' AND wt.APM_APPLICATION_ID=:appId  AND wt.ORGID =:orgId", nativeQuery = true)
	public Long getTaskIdByAppIdAndOrgId(@Param("appId") Long appId, @Param("orgId") Long orgId);
	// US#34043
	@Query(value = "SELECT m.WFD_EMPROLE,m.SERVICE_EVENT_ID  FROM TB_WORKFLOW_DET  m   where m.WF_ID=:wfId and m.ORGID=:orgid and m.WFD_STATUS=:status ", nativeQuery = true)
	public List<Object[]> getAllWorkFlowMappingByWfId(@Param("wfId") Long wfId, @Param("orgid") long orgid,
			@Param("status") String status);
	

	@Query(value = "SELECT wt.TASK_ID,wt.SM_SERVICE_ID  from tb_workflow_task wt where wt.APM_APPLICATION_ID=:apmApplicationId", nativeQuery = true)
	List<Object[]> getTaskIdByAppId(@Param("apmApplicationId") Long apmApplicationId);
	
	@Query(value = "select t.TASK_ID,a.COMMENTS,t.WFTASK_COMPDATE, concat(ifnull(e.EMPNAME,''),' ',ifnull(e.EMPMNAME,''),' ',ifnull(e.EMPLNAME,'')) as Employee_name,\r\n" + 
			"gm.GR_DESC_ENG\r\n" + 
			"from tb_workflow_task t,tb_workflow_action a , employee e,tb_group_mast gm\r\n" + 
			"where t.TASK_ID=a.TASK_ID and a.EMPID=e.EMPID and e.GM_ID=gm.GM_ID and t.apm_application_id =:apmApplicationId and a.DECISION not in ('PENDING','SUBMITTED')", nativeQuery = true)
	List<Object[]> getTaskIdAndCommentByAppId(@Param("apmApplicationId") Long apmApplicationId);
}
