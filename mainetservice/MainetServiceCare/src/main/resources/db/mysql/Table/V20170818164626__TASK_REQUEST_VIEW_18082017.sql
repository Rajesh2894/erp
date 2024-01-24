--liquibase formatted sql
--changeset nilima:V20170818164626__TASK_REQUEST_VIEW_18082017.sql
CREATE OR REPLACE VIEW TASK_REQUEST_VIEW AS
    SELECT 
        t.id AS task_id,
        d.ORGANIZATION_ID AS org_id,
        d.ID AS document_id,
        p.entity_id AS entity_id,
        d.APM_APPLICATION_ID AS request_no,
        d.DATE_OF_REQUEST AS date_of_request,
        d.PROCESS_STATUS AS process_status,
        d.PROCESS_NAME AS process_name,
        i.shortText AS task_name,
        t.status AS task_status,
        t.createdOn AS task_created_date,
        description.DESCRIPTION_TEXT AS request_reference,
        d.EMPID AS emp_id,
        d.CREATED_DATE AS date_of_action
    FROM
        ((((TB_WORKFLOW_REQUEST d
        JOIN TB_WORKFLOW_DESCRIPTION description)
        JOIN Task t)
        JOIN I18NText i)
        JOIN PeopleAssignments_PotOwners p)
    WHERE
        ((d.PROCESS_SESSIONID = t.processInstanceId)
            AND (i.Task_Names_Id = t.id)
            AND (t.id = p.task_id)
            AND (d.ID = description.DOCUMENT_ID)
            AND (description.DESCRIPTIONS_KEY = 'REQUEST_REFERENCE')
            AND (t.status in ('Ready','Reserved'))
            AND (p.entity_id not in ('REVIEW_REQUEST','APPLICANT_REVIEW','Reviewer','Requester')))