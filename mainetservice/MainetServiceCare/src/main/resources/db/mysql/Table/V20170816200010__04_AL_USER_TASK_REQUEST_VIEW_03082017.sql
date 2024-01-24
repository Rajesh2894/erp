--liquibase formatted sql
--changeset nilima:V20170816200010__04_AL_USER_TASK_REQUEST_VIEW_03082017.sql
CREATE OR REPLACE
VIEW USER_TASK_REQUEST_VIEW AS
    SELECT 
        FUNC_INC_VAR_SESSION() AS row_id,
        wr.SERVICE_ID AS service_id,
        wr.APM_APPLICATION_ID AS application_id,
        wr.ORGANIZATION_ID AS organization_id,
        wr.DEPT_ID AS dept_id,
        wr.PROCESS_STATUS AS request_status,
        t.name AS service_name,
        t.name AS event_name,
        t.formName AS service_url,
        t.status AS task_status,
        t.id AS task_id,
        t.processInstanceId AS process_instance_id,
        t.name AS task_name,
        t.createdOn AS task_created_date,
        p.entity_id AS entity_id
    FROM
        (((Task t
        JOIN I18NText i)
        JOIN TB_WORKFLOW_REQUEST wr)
        JOIN PeopleAssignments_PotOwners p)
    WHERE
        ((i.Task_Names_Id = t.id)
            AND (t.id = p.task_id)
            AND (t.status IN ('Ready' , 'Completed'))
            AND (wr.PROCESS_SESSIONID = t.processInstanceId)
            AND (p.entity_id NOT IN ('CHALLAN_VERIFICATION' , 'CHECKLIST_VERIFICATION',
            'LOI_PAYMENT_GENRATION',
            'SCRUTINY_1_VERIFICATION',
            'SCRUTINY_2_VERIFICATION',
            'SCRUTINY_3_VERIFICATION',
            'SCRUTINY_4_VERIFICATION',
            'SCRUTINY_5_VERIFICATION',
            'WORK_ORDER_GENERATION',
            'LOI_PAYMENT_GENRATION'))
            AND (t.processId = 'BPMProcess.WorkFlow'))