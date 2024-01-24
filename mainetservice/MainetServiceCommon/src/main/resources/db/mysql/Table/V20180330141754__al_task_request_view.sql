--liquibase formatted sql
--changeset priya:V20180330141754__al_task_request_view.sql
CREATE OR REPLACE  
VIEW task_request_view AS
    SELECT 
        tr.id AS task_id,
        tr.processInstanceId AS processInstanceId,
        tr.status AS task_status,
        tr.createdOn AS task_created_date,
        tr.formName AS task_url,
        p.entity_id AS entity_id,
        i.shortText AS task_name,
        d.ORGID AS org_id,
        tr.description AS event_orgid,
        d.WORKFLOW_REQ_ID AS WORKFLOW_REQ_ID,
        d.APM_APPLICATION_ID AS APM_APPLICATION_ID,
		d.REFERENCE_ID AS REFERENCE_ID,
        d.DATE_OF_REQUEST AS date_of_request,
        d.STATUS AS process_status,
        d.EMPID AS emp_id,
        d.CREATED_DATE AS date_of_action,
        sm.SM_SERVICE_ID AS serviceid,
        sm.SM_SERVICE_NAME AS service_nameeng,
        sm.SM_SERVICE_NAME_MAR AS service_namereg,
        sm.CDM_DEPT_ID AS deptid,
        dp.DP_DEPTDESC AS DP_DEPTDESC
    FROM
        ((((((((SELECT 
            t.processInstanceId AS processInstanceId,
                0 AS parentid
        FROM
            (task t
        JOIN tb_workflow_request d)
        WHERE
            ((d.PROCESS_SESSIONID = t.processInstanceId)
                AND (t.status IN ('Ready' , 'Reserved', 'InProgress'))) UNION SELECT 
            t.processInstanceId AS processInstanceId,
                pl.parentProcessInstanceId AS parentid
        FROM
            (task t
        JOIN processinstancelog pl)
        WHERE
            ((pl.id = t.processInstanceId)
                AND (t.status IN ('Ready' , 'Reserved', 'InProgress')))) a
        JOIN task tr)
        JOIN tb_workflow_request d)
        JOIN tb_workflow_mas wm)
        JOIN tb_services_mst sm)
        JOIN i18ntext i)
        JOIN peopleassignments_potowners p)
        JOIN tb_department dp)
    WHERE
        ((a.processInstanceId = tr.processInstanceId)
            AND (CASE
            WHEN (a.parentid = 0) THEN (d.PROCESS_SESSIONID = a.processInstanceId)
            WHEN
                ((a.parentid <> 0)
                    AND (a.parentid <> -(1)))
            THEN
                (d.PROCESS_SESSIONID = a.parentid)
        END)
            AND (d.WORFLOW_TYPE_ID = wm.WF_ID)
            AND (sm.SM_SERVICE_ID = wm.SM_SERVICE_ID)
            AND (i.Task_Names_Id = tr.id)
            AND (tr.id = p.task_id)
            AND (p.entity_id REGEXP '^[0-9]+$')
            AND (sm.CDM_DEPT_ID = dp.DP_DEPTID)
            AND (tr.status IN ('Ready' , 'Reserved', 'InProgress')))