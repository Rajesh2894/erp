package com.abm.mainet.bpm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.bpm.domain.WorkflowUserTask;

@Repository
public interface WorkflowTaskRepository
        extends JpaRepository<WorkflowUserTask, Long>, JpaSpecificationExecutor<WorkflowUserTask> {

    @Query("select t from WorkflowUserTask t where t.applicationId =:applicationId or t.referenceId =:referenceId")
    List<WorkflowUserTask> findByApplicationIdOrReferenceId(@Param("applicationId") Long applicationId,
            @Param("referenceId") String referenceId);

    WorkflowUserTask findByTaskId(Long taskId);
    /*LAST_DECISION field added as per told by Rajesh Sir*/
    @Query( value = "  SELECT\r\n" + 
    		"        a.APM_APPLICATION_ID AS APM_APPLICATION_ID,\r\n" + 
    		"        a.ORGID AS ORGID,\r\n" + 
    		"        c.DP_DEPTID AS DP_DEPTID,\r\n" + 
    		"        (SELECT  DP_DEPTDESC FROM tb_department WHERE DP_DEPTID = c.DP_DEPTID) AS DP_DEPTDESC,\r\n" + 
    		"        (SELECT  DP_NAME_MAR FROM tb_department WHERE DP_DEPTID = c.DP_DEPTID) AS DP_NAME_MAR,\r\n" + 
    		"        c.SM_SERVICE_ID AS SM_SERVICE_ID,\r\n" + 
    		"        (SELECT SM_SERVICE_NAME FROM tb_services_mst WHERE SM_SERVICE_ID = c.SM_SERVICE_ID) AS SM_SERVICE_NAME,\r\n" + 
    		"        (SELECT SM_SERVICE_NAME_MAR FROM tb_services_mst WHERE SM_SERVICE_ID = c.SM_SERVICE_ID) AS SM_SERVICE_NAME_MAR,\r\n" + 
    		"        c.EVENT_ID AS EVENT_ID,\r\n" + 
    		"        (SELECT SMFNAME FROM tb_sysmodfunction WHERE SMFID = c.EVENT_ID) AS serviceEventName,\r\n" + 
    		"        (SELECT SMFNAME_MAR FROM tb_sysmodfunction WHERE SMFID = c.EVENT_ID) AS serviceEventNameReg,\r\n" + 
    		"        a.TASK_ID AS TASK_ID,\r\n" + 
    		"        a.TASK_NAME AS TASK_NAME,\r\n" + 
    		"        c.SMFACTION AS SMFACTION,\r\n" + 
    		"        e.DATE_OF_REQUEST AS DATE_OF_REQUEST,\r\n" + 
    		"        c.WORKFLOW_REQ_ID AS WORKFLOW_REQ_ID,\r\n" + 
    		"        c.TASK_STATUS AS TASK_STATUS,\r\n" + 
    		"        e.LAST_DECISION AS DECISION,\r\n" + 
    		"        a.EMPID AS EMPID,\r\n" + 
    		"        a.REFERENCE_ID AS REFERENCE_ID,\r\n" +
    		"        e.WORFLOW_TYPE_ID AS WORFLOW_TYPE_ID,\r\n" + 
    		"        c.TASK_SLA_DURATION AS TASK_SLA_DURATION,\r\n" +
    		"	     c.WFTASK_ASSIGDATE as WFTASK_ASSIGDATE,\r\n" +
    		"	     c.WFTASK_ASSIGDATE as WFTASK_COMPDATE,\r\n" +
    		"        (SELECT SM_SHORTDESC FROM tb_services_mst WHERE SM_SERVICE_ID = c.SM_SERVICE_ID) AS SM_SHORT_CODE\r\n" +
    		"     FROM tb_workflow_request e,\r\n" + 
    		"         tb_workflow_task c,\r\n" + 
    		"         tb_workflow_action a\r\n" +
    		"    WHERE a.WFTASK_ID = c.WFTASK_ID\r\n" + 
    		"            AND e.WORKFLOW_REQ_ID = c.WORKFLOW_REQ_ID \r\n"
    		+ "			AND a.empId = :empId and c.task_Status in (:taskStatus)\r\n"
    		+ "			AND a.orgId = :orgId  \r\n ",  nativeQuery = true)
    List<Object[]> findTasksForEmployee(@Param("empId") Long empId, @Param("taskStatus") List<String>  taskStatus,@Param("orgId") Long orgId );
    
    @Query( value = "  SELECT\r\n" + 
    		"        a.APM_APPLICATION_ID AS APM_APPLICATION_ID,\r\n" +
    		"        a.WORKFLOW_ACT_ID AS WORKFLOW_ACT_ID,\r\n" + 
    		"        a.ORGID AS ORGID,\r\n" + 
    		"        c.DP_DEPTID AS DP_DEPTID,\r\n" + 
    		"        (SELECT  DP_DEPTDESC FROM tb_department WHERE DP_DEPTID = c.DP_DEPTID) AS DP_DEPTDESC,\r\n" + 
    		"        (SELECT  DP_NAME_MAR FROM tb_department WHERE DP_DEPTID = c.DP_DEPTID) AS DP_NAME_MAR,\r\n" + 
    		"        c.SM_SERVICE_ID AS SM_SERVICE_ID,\r\n" + 
    		"        (SELECT SM_SERVICE_NAME FROM tb_services_mst WHERE SM_SERVICE_ID = c.SM_SERVICE_ID) AS SM_SERVICE_NAME,\r\n" + 
    		"        (SELECT SM_SERVICE_NAME_MAR FROM tb_services_mst WHERE SM_SERVICE_ID = c.SM_SERVICE_ID) AS SM_SERVICE_NAME_MAR,\r\n" + 
    		"        c.EVENT_ID AS EVENT_ID,\r\n" + 
    		"        (SELECT SMFNAME FROM tb_sysmodfunction WHERE SMFID = c.EVENT_ID) AS serviceEventName,\r\n" + 
    		"        (SELECT SMFNAME_MAR FROM tb_sysmodfunction WHERE SMFID = c.EVENT_ID) AS serviceEventNameReg,\r\n" + 
    		"        a.TASK_ID AS TASK_ID,\r\n" + 
    		"       (CASE WHEN  a.TASK_NAME IS null THEN c.TASK_NAME ELSE a.TASK_NAME END) AS TASK_NAME,\r\n" + 
    		"        c.SMFACTION AS SMFACTION,\r\n" + 
    		"        e.DATE_OF_REQUEST AS DATE_OF_REQUEST,\r\n" + 
    		"        c.WORKFLOW_REQ_ID AS WORKFLOW_REQ_ID,\r\n" + 
    		"        c.TASK_STATUS AS TASK_STATUS,\r\n" + 
    		"        (Case When c.TASK_STATUS='EXITED' Then 'Escalated' Else a.DECISION end) AS DECISION,\r\n" + 
    		"        a.EMPID AS EMPID,\r\n" + 
    		"        a.COMMENTS AS COMMENTS,\r\n" + 
    		"        a.REFERENCE_ID AS REFERENCE_ID,\r\n" +
    		"        e.EMPL_TYPE AS EMPL_TYPE,\r\n" +
    		"(case when c.WFTASK_COMPDATE is null then a.DATE_OF_ACTION\r\n"+
    		"           else c.WFTASK_COMPDATE end) AS DATE_OF_ACTION, "+
    	    "    		a.CREATED_DATE AS CREATED_DATE,\r\n" +
    	    "    		a.CREATED_BY AS CREATED_BY,\r\n" +
    	    "    		a.UPDATED_DATE AS UPDATED_DATE,\r\n" +
    	    "		    a.UPDATED_BY AS UPDATED_BY,\r\n" +
    	    "			(SELECT case when group_concat(distinct DMS_DOC_ID) is not null then group_concat(ATT_PATH,':',ATT_FNAME,':',DMS_DOC_ID separator '*') \r\n" + 
    	    "                        else group_concat(ATT_PATH,':',ATT_FNAME,':' separator '*') end \r\n" + 
    	    "		  FROM tb_attach_cfc \r\n" + 
    	    "          WHERE FIND_IN_SET(ATT_ID,a.WORKFLOW_ATT)\r\n" + 
    	    "		  GROUP BY APPLICATION_Id,REFERENCE_ID) AS att_path,\r\n"+
    	    "        (select EMPLOGINNAME from employee where EMPID=a.EMPID and EMPLOGINNAME is not null) as EMPLOGINNAME,\r\n" +
    	    "        (select concat(coalesce(b.APM_FNAME,' '),' ',coalesce(b.APM_MNAME,' '),' ',coalesce(b.APM_LNAME,' ')) from tb_cfc_application_mst b where REF_NO=:referenceId) as ApplicantName" +
    		"     FROM tb_workflow_request e,\r\n" + 
    		"         tb_workflow_task c,\r\n" + 
    		"         tb_workflow_action a\r\n" + 
    		"    WHERE a.WFTASK_ID = c.WFTASK_ID\r\n" + 
    		"            AND e.WORKFLOW_REQ_ID = c.WORKFLOW_REQ_ID AND (a.REFERENCE_ID = :referenceId)\r\n" + 
    		"            order by DATE_OF_ACTION asc\r\n" + 
    		"            \r\n" + 
    		"          ",  nativeQuery = true)
    List<Object[]> findByReferenceIdWithDetails(@Param("referenceId") String referenceId);
    /*Separate method created for application Id Or reference Id - Told By Rajesh Sir*/
    @Query( value = "  SELECT\r\n" + 
    		"        a.APM_APPLICATION_ID AS APM_APPLICATION_ID,\r\n" +
    		"        a.WORKFLOW_ACT_ID AS WORKFLOW_ACT_ID,\r\n" + 
    		"        a.ORGID AS ORGID,\r\n" + 
    		"        c.DP_DEPTID AS DP_DEPTID,\r\n" + 
    		"        (SELECT  DP_DEPTDESC FROM tb_department WHERE DP_DEPTID = c.DP_DEPTID) AS DP_DEPTDESC,\r\n" + 
    		"        (SELECT  DP_NAME_MAR FROM tb_department WHERE DP_DEPTID = c.DP_DEPTID) AS DP_NAME_MAR,\r\n" + 
    		"        c.SM_SERVICE_ID AS SM_SERVICE_ID,\r\n" + 
    		"        (SELECT SM_SERVICE_NAME FROM tb_services_mst WHERE SM_SERVICE_ID = c.SM_SERVICE_ID) AS SM_SERVICE_NAME,\r\n" + 
    		"        (SELECT SM_SERVICE_NAME_MAR FROM tb_services_mst WHERE SM_SERVICE_ID = c.SM_SERVICE_ID) AS SM_SERVICE_NAME_MAR,\r\n" + 
    		"        c.EVENT_ID AS EVENT_ID,\r\n" + 
    		"        (SELECT SMFNAME FROM tb_sysmodfunction WHERE SMFID = c.EVENT_ID) AS serviceEventName,\r\n" + 
    		"        (SELECT SMFNAME_MAR FROM tb_sysmodfunction WHERE SMFID = c.EVENT_ID) AS serviceEventNameReg,\r\n" + 
    		"        a.TASK_ID AS TASK_ID,\r\n" + 
    		"       (CASE WHEN  a.TASK_NAME IS null THEN c.TASK_NAME ELSE a.TASK_NAME END) AS TASK_NAME,\r\n" + 
    		"        c.SMFACTION AS SMFACTION,\r\n" + 
    		"        e.DATE_OF_REQUEST AS DATE_OF_REQUEST,\r\n" + 
    		"        c.WORKFLOW_REQ_ID AS WORKFLOW_REQ_ID,\r\n" + 
    		"        c.TASK_STATUS AS TASK_STATUS,\r\n" + 
    		"        (Case When c.TASK_STATUS='EXITED' Then 'Escalated' Else a.DECISION end) AS DECISION,\r\n" + 
    		"        a.EMPID AS EMPID,\r\n" + 
    		"        a.COMMENTS AS COMMENTS,\r\n" + 
    		"        a.REFERENCE_ID AS REFERENCE_ID,\r\n" +
    		"        e.EMPL_TYPE AS EMPL_TYPE,\r\n" +
    		"(case when c.WFTASK_COMPDATE is null then a.DATE_OF_ACTION\r\n"+
    		"           else c.WFTASK_COMPDATE end) AS DATE_OF_ACTION, "+
    	    "    		a.CREATED_DATE AS CREATED_DATE,\r\n" +
    	    "    		a.CREATED_BY AS CREATED_BY,\r\n" +
    	    "    		a.UPDATED_DATE AS UPDATED_DATE,\r\n" +
    	    "		    a.UPDATED_BY AS UPDATED_BY,\r\n" +
    	    "			(SELECT case when group_concat(distinct DMS_DOC_ID) is not null then group_concat(ATT_PATH,':',ATT_FNAME,':',DMS_DOC_ID separator '*') \r\n" + 
    	    "                        else group_concat(ATT_PATH,':',ATT_FNAME,':' separator '*') end \r\n" + 
    	    "		  FROM tb_attach_cfc \r\n" + 
    	    "          WHERE FIND_IN_SET(ATT_ID,a.WORKFLOW_ATT)\r\n" + 
    	    "		  GROUP BY APPLICATION_Id,REFERENCE_ID) AS att_path,\r\n"+
    	    "        (select EMPLOGINNAME from employee where EMPID=a.EMPID and EMPLOGINNAME is not null) as EMPLOGINNAME,\r\n" +
    	    "        (select concat(coalesce(b.APM_FNAME,' '),' ',coalesce(b.APM_MNAME,' '),' ',coalesce(b.APM_LNAME,' ')) from tb_cfc_application_mst b where APM_APPLICATION_ID=:applicationId) as ApplicantName" +
    		"     FROM tb_workflow_request e,\r\n" + 
    		"         tb_workflow_task c,\r\n" + 
    		"         tb_workflow_action a\r\n" + 
    		"    WHERE a.WFTASK_ID = c.WFTASK_ID\r\n" + 
    		"            AND e.WORKFLOW_REQ_ID = c.WORKFLOW_REQ_ID AND (c.APM_APPLICATION_ID= :applicationId)\r\n" + 
    		"            order by DATE_OF_ACTION asc\r\n" + 
    		"            \r\n" + 
    		"          ",  nativeQuery = true)
    List<Object[]> findByApplicationIdWithDetails(@Param("applicationId") Long applicationId);
    
    /*Separate method created for application Id Or reference Id - Told By Rajesh Sir*/
    @Query( value = "  SELECT\r\n" + 
    		"        a.APM_APPLICATION_ID AS APM_APPLICATION_ID,\r\n" +
    		"        a.WORKFLOW_ACT_ID AS WORKFLOW_ACT_ID,\r\n" + 
    		"        a.ORGID AS ORGID,\r\n" + 
    		"        c.DP_DEPTID AS DP_DEPTID,\r\n" + 
    		"        (SELECT  DP_DEPTDESC FROM tb_department WHERE DP_DEPTID = c.DP_DEPTID) AS DP_DEPTDESC,\r\n" + 
    		"        (SELECT  DP_NAME_MAR FROM tb_department WHERE DP_DEPTID = c.DP_DEPTID) AS DP_NAME_MAR,\r\n" + 
    		"        c.SM_SERVICE_ID AS SM_SERVICE_ID,\r\n" + 
    		"        (SELECT SM_SERVICE_NAME FROM tb_services_mst WHERE SM_SERVICE_ID = c.SM_SERVICE_ID) AS SM_SERVICE_NAME,\r\n" + 
    		"        (SELECT SM_SERVICE_NAME_MAR FROM tb_services_mst WHERE SM_SERVICE_ID = c.SM_SERVICE_ID) AS SM_SERVICE_NAME_MAR,\r\n" + 
    		"        c.EVENT_ID AS EVENT_ID,\r\n" + 
    		"        (SELECT SMFNAME FROM tb_sysmodfunction WHERE SMFID = c.EVENT_ID) AS serviceEventName,\r\n" + 
    		"        (SELECT SMFNAME_MAR FROM tb_sysmodfunction WHERE SMFID = c.EVENT_ID) AS serviceEventNameReg,\r\n" + 
    		"        a.TASK_ID AS TASK_ID,\r\n" + 
    		"       (CASE WHEN  a.TASK_NAME IS null THEN c.TASK_NAME ELSE a.TASK_NAME END) AS TASK_NAME,\r\n" + 
    		"        c.SMFACTION AS SMFACTION,\r\n" + 
    		"        e.DATE_OF_REQUEST AS DATE_OF_REQUEST,\r\n" + 
    		"        c.WORKFLOW_REQ_ID AS WORKFLOW_REQ_ID,\r\n" + 
    		"        c.TASK_STATUS AS TASK_STATUS,\r\n" + 
    		"        (Case When c.TASK_STATUS='EXITED' Then 'Escalated' Else a.DECISION end) AS DECISION,\r\n" + 
    		"        a.EMPID AS EMPID,\r\n" + 
    		"        a.COMMENTS AS COMMENTS,\r\n" + 
    		"        a.REFERENCE_ID AS REFERENCE_ID,\r\n" +
    		"        e.EMPL_TYPE AS EMPL_TYPE,\r\n" +
    		"		   (case when c.WFTASK_COMPDATE is null then a.DATE_OF_ACTION\r\n"+
    		"    		        else c.WFTASK_COMPDATE end) AS DATE_OF_ACTION,\r\n" +
    	    "    		a.CREATED_DATE AS CREATED_DATE,\r\n" +
    	    "    		a.CREATED_BY AS CREATED_BY,\r\n" +
    	    "    		a.UPDATED_DATE AS UPDATED_DATE,\r\n" +
    	    "		    a.UPDATED_BY AS UPDATED_BY,\r\n" +
    	    "			(SELECT case when group_concat(distinct DMS_DOC_ID) is not null then group_concat(ATT_PATH,':',ATT_FNAME,':',DMS_DOC_ID separator '*') \r\n" + 
    	    "                        else group_concat(ATT_PATH,':',ATT_FNAME,':' separator '*') end \r\n" + 
    	    "		  FROM tb_attach_cfc \r\n" + 
    	    "          WHERE FIND_IN_SET(ATT_ID,a.WORKFLOW_ATT)\r\n" + 
    	    "		  GROUP BY APPLICATION_Id,REFERENCE_ID) AS att_path,\r\n"+
    	    "        (select EMPLOGINNAME from employee where EMPID=a.EMPID and EMPLOGINNAME is not null) as EMPLOGINNAME,\r\n" +
    	    "        (select concat(coalesce(b.APM_FNAME,' '),' ',coalesce(b.APM_MNAME,' '),' ',coalesce(b.APM_LNAME,' ')) from tb_cfc_application_mst b where REF_NO=:referenceId) as ApplicantName" +
    		"     FROM tb_workflow_request e,\r\n" + 
    		"         tb_workflow_task c,\r\n" + 
    		"         tb_workflow_action a\r\n" + 
    		"    WHERE a.WFTASK_ID = c.WFTASK_ID\r\n" + 
    		"            AND e.WORKFLOW_REQ_ID = c.WORKFLOW_REQ_ID AND e.WORKFLOW_REQ_ID = :workflowReqId AND (a.REFERENCE_ID = :referenceId)\r\n" + 
    		"            \r\n" + 
    		"            \r\n" + 
    		"          ",  nativeQuery = true)
    List<Object[]> findByReferenceIdWithDetails(@Param("referenceId") String referenceId, @Param("workflowReqId") Long workflowReqId);
    /*Separate method created for application Id Or reference Id - Told By Rajesh Sir*/
    @Query( value = "  SELECT\r\n" + 
    		"        a.APM_APPLICATION_ID AS APM_APPLICATION_ID,\r\n" +
    		"        a.WORKFLOW_ACT_ID AS WORKFLOW_ACT_ID,\r\n" + 
    		"        a.ORGID AS ORGID,\r\n" + 
    		"        c.DP_DEPTID AS DP_DEPTID,\r\n" + 
    		"        (SELECT  DP_DEPTDESC FROM tb_department WHERE DP_DEPTID = c.DP_DEPTID) AS DP_DEPTDESC,\r\n" + 
    		"        (SELECT  DP_NAME_MAR FROM tb_department WHERE DP_DEPTID = c.DP_DEPTID) AS DP_NAME_MAR,\r\n" + 
    		"        c.SM_SERVICE_ID AS SM_SERVICE_ID,\r\n" + 
    		"        (SELECT SM_SERVICE_NAME FROM tb_services_mst WHERE SM_SERVICE_ID = c.SM_SERVICE_ID) AS SM_SERVICE_NAME,\r\n" + 
    		"        (SELECT SM_SERVICE_NAME_MAR FROM tb_services_mst WHERE SM_SERVICE_ID = c.SM_SERVICE_ID) AS SM_SERVICE_NAME_MAR,\r\n" + 
    		"        c.EVENT_ID AS EVENT_ID,\r\n" + 
    		"        (SELECT SMFNAME FROM tb_sysmodfunction WHERE SMFID = c.EVENT_ID) AS serviceEventName,\r\n" + 
    		"        (SELECT SMFNAME_MAR FROM tb_sysmodfunction WHERE SMFID = c.EVENT_ID) AS serviceEventNameReg,\r\n" + 
    		"        a.TASK_ID AS TASK_ID,\r\n" + 
    		"       (CASE WHEN  a.TASK_NAME IS null THEN c.TASK_NAME ELSE a.TASK_NAME END) AS TASK_NAME,\r\n" + 
    		"        c.SMFACTION AS SMFACTION,\r\n" + 
    		"        e.DATE_OF_REQUEST AS DATE_OF_REQUEST,\r\n" + 
    		"        c.WORKFLOW_REQ_ID AS WORKFLOW_REQ_ID,\r\n" +
    		"        c.TASK_STATUS AS TASK_STATUS,\r\n" + 
    		"        (Case When c.TASK_STATUS='EXITED' Then 'Escalated' Else a.DECISION end) AS DECISION,\r\n" + 
    		"        a.EMPID AS EMPID,\r\n" +
    		"        a.COMMENTS AS COMMENTS,\r\n" + 
    		"        a.REFERENCE_ID AS REFERENCE_ID,\r\n" +
    		"        e.EMPL_TYPE AS EMPL_TYPE,\r\n" +
    		"		   (case when c.WFTASK_COMPDATE is null then a.DATE_OF_ACTION\r\n"+
    		"    		        else c.WFTASK_COMPDATE end) AS DATE_OF_ACTION,\r\n" +
    	    "    		a.CREATED_DATE AS CREATED_DATE,\r\n" +
    	    "    		a.CREATED_BY AS CREATED_BY,\r\n" +
    	    "    		a.UPDATED_DATE AS UPDATED_DATE,\r\n" +
    	    "		    a.UPDATED_BY AS UPDATED_BY,\r\n" +
    	    "			(SELECT case when group_concat(distinct DMS_DOC_ID) is not null then group_concat(ATT_PATH,':',ATT_FNAME,':',DMS_DOC_ID separator '*') \r\n" + 
    	    "                        else group_concat(ATT_PATH,':',ATT_FNAME,':' separator '*') end \r\n" + 
    	    "		  FROM tb_attach_cfc \r\n" + 
    	    "          WHERE FIND_IN_SET(ATT_ID,a.WORKFLOW_ATT)\r\n" + 
    	    "		  GROUP BY APPLICATION_Id,REFERENCE_ID) AS att_path,\r\n"+
    	    "        (select EMPLOGINNAME from employee where EMPID=a.EMPID and EMPLOGINNAME is not null) as EMPLOGINNAME,\r\n" +
    	    "        (select concat(coalesce(b.APM_FNAME,' '),' ',coalesce(b.APM_MNAME,' '),' ',coalesce(b.APM_LNAME,' ')) from tb_cfc_application_mst b where APM_APPLICATION_ID=:applicationId) as ApplicantName" +
    		"     FROM tb_workflow_request e,\r\n" + 
    		"         tb_workflow_task c,\r\n" + 
    		"         tb_workflow_action a\r\n" + 
    		"    WHERE a.WFTASK_ID = c.WFTASK_ID\r\n" + 
    		"            AND e.WORKFLOW_REQ_ID = c.WORKFLOW_REQ_ID AND e.WORKFLOW_REQ_ID = :workflowReqId AND (c.APM_APPLICATION_ID= :applicationId)\r\n" + 
    		"            \r\n" + 
    		"            \r\n" + 
    		"          ",  nativeQuery = true)
    List<Object[]> findByApplicationIdWithDetails(@Param("applicationId") Long applicationId,@Param("workflowReqId") Long workflowReqId);
    
    @Query("select t from WorkflowUserTask t where t.referenceId =:referenceId and t.taskId =:taskId")
  WorkflowUserTask findByReferenceIdAndTaskId(
            @Param("referenceId") String referenceId,@Param("taskId") Long taskId);
    
    @Query("select t from WorkflowUserTask t where t.applicationId =:applicationId and t.taskId =:taskId")
    WorkflowUserTask findByApplicationIdAndTaskId(
              @Param("applicationId") Long applicationId,@Param("taskId") Long taskId);
    
    @Query("select t from WorkflowUserTask t where t.taskId =:taskId and t.referenceId =:referenceId and t.applicationId =:applicationId")
    WorkflowUserTask findByReferenceIdAndTaskIdAndApplicationId(@Param("referenceId") String referenceId,
              @Param("applicationId") Long applicationId,@Param("taskId") Long taskId);

}
