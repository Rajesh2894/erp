--liquibase formatted sql
--changeset nilima:V20171031184554__DR_TABLES_CARE_WORKFLOW.SQL
CREATE or REPLACE VIEW task_request_view AS
select tr.id AS task_id,
tr.processInstanceId,
tr.status AS task_status,
tr.createdOn AS task_created_date,
tr.formName AS task_url,
p.entity_id AS entity_id,
i.shortText AS task_name,
d.ORGID AS org_id,
d.WORKFLOW_REQ_ID AS WORKFLOW_REQ_ID,
d.APM_APPLICATION_ID AS APM_APPLICATION_ID,
d.DATE_OF_REQUEST AS date_of_request,
d.STATUS AS process_status,
d.EMPID AS emp_id,
d.CREATED_DATE AS date_of_action,
sm.SM_SERVICE_ID AS serviceid,
sm.SM_SERVICE_NAME AS service_nameeng,
sm.SM_SERVICE_NAME_MAR AS service_namereg
from
(select t.processInstanceId,
0 parentid
from task t,
tb_workflow_request d
where d.PROCESS_SESSIONID = t.processInstanceId
AND t.status IN ('Ready' , 'Reserved', 'InProgress')
union
select t.processInstanceId,
pl.parentProcessInstanceid parentid
from task t,
processinstancelog pl
where pl.id = t.processInstanceId
AND t.status IN ('Ready' , 'Reserved', 'InProgress')) a,
task tr,
tb_workflow_request d,
tb_workflow_mas wm,
tb_services_mst sm,
i18ntext i,
peopleassignments_potowners p
where a.processInstanceId=tr.processInstanceId 
AND (case when a.parentid=0  then d.PROCESS_SESSIONID =a.processInstanceId
     when a.parentid<>0 and a.parentid<>-1 then d.PROCESS_SESSIONID =a.parentid end)
AND (d.WORFLOW_TYPE_ID = wm.WF_ID)
AND (sm.SM_SERVICE_ID = wm.SM_SERVICE_ID)
AND i.Task_Names_Id = tr.id
and tr.id = p.task_id
AND tr.status IN ('Ready' , 'Reserved', 'InProgress')