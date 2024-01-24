--liquibase formatted sql
--changeset nilima:V20190225200834__vw_task_list.sql
drop view if exists vw_task_list;
--liquibase formatted sql
--changeset nilima:V20190225200834__vw_task_list1.sql
CREATE VIEW vw_task_list AS
    SELECT 
        a.id AS TASK_ID,
        a.name AS TASK_NAME,
        a.status AS TASK_STATUS,
        b.APM_APPLICATION_ID AS APM_APPLICATION_ID,
        b.REFERENCE_ID AS REFERENCE_ID,
        a.description AS ORGID,
        c.TASK_ACTOR_ID AS TASK_ACTOR_ID,
        a.subject AS TASK_DATA,
        a.formName AS TASK_URL,
        b.workflowId AS workflowId
    FROM
        (((SELECT 
            peopleassignments_potowners.task_id AS task_id,
                GROUP_CONCAT(peopleassignments_potowners.entity_id
                    SEPARATOR ',') AS TASK_ACTOR_ID
        FROM
            peopleassignments_potowners
        GROUP BY peopleassignments_potowners.task_id)) c
        JOIN (task a
        JOIN (SELECT 
            b.taskId AS taskId,
                GROUP_CONCAT((CASE
                    WHEN (b.name = 'applicationId') THEN b.value
                END), ''
                    SEPARATOR ',') AS APM_APPLICATION_ID,
                GROUP_CONCAT((CASE
                    WHEN (b.name = 'referenceId') THEN b.value
                END), ''
                    SEPARATOR ',') AS REFERENCE_ID,
                GROUP_CONCAT((CASE
                    WHEN (b.name = 'workflowId') THEN b.value
                END), ''
                    SEPARATOR ',') AS workflowId
        FROM
            taskvariableimpl b
        WHERE
            ((b.name = 'applicationId')
                OR (b.name = 'referenceId')
                OR (b.name = 'workflowId'))
        GROUP BY b.taskId) b))
    WHERE
        ((a.id = c.task_id)
            AND (b.taskId = a.id))