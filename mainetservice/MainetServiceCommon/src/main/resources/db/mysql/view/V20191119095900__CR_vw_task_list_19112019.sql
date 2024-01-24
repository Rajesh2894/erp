--liquibase formatted sql
--changeset Anil:V20191119095900__CR_vw_task_list_19112019.sql
drop view if exists vw_task_list;
--liquibase formatted sql
--changeset Anil:V20191119095900__CR_vw_task_list_191120191.sql
CREATE VIEW vw_task_list AS
    SELECT 
        `a`.`id` AS `TASK_ID`,
        `a`.`name` AS `TASK_NAME`,
        `a`.`status` AS `TASK_STATUS`,
        `b`.`APM_APPLICATION_ID` AS `APM_APPLICATION_ID_OLD`,
        `b`.`REFERENCE_ID` AS `REFERENCE_ID_OLD`,
        `a`.`description` AS `ORGID`,
        `c`.`TASK_ACTOR_ID` AS `TASK_ACTOR_ID`,
        `a`.`subject` AS `TASK_DATA`,
        `a`.`formName` AS `TASK_URL`,
        `a`.`createdBy_id` AS `WORKFLOWID_OLD`,
        (CASE
            WHEN
                (UPPER(SUBSTR(`a`.`description`,
                            1,
                            (LOCATE('|', `a`.`description`, 1) - 1))) = 'NULL')
            THEN
                NULL
            ELSE CAST(SUBSTR(`a`.`description`,
                    1,
                    (LOCATE('|', `a`.`description`, 1) - 1))
                AS UNSIGNED)
        END) AS `APM_APPLICATION_ID`,
        (CASE
            WHEN
                (UPPER(SUBSTR(`a`.`description`,
                            (LOCATE('|', `a`.`description`, 1) + 1),
                            (LENGTH(SUBSTR(`a`.`description`,
                                        1,
                                        (LOCATE('|',
                                                `a`.`description`,
                                                (LOCATE('|', `a`.`description`) + 1)) - 1))) - LOCATE('|', `a`.`description`, 1)))) = 'NULL')
            THEN
                NULL
            ELSE SUBSTR(`a`.`description`,
                (LOCATE('|', `a`.`description`, 1) + 1),
                (LENGTH(SUBSTR(`a`.`description`,
                            1,
                            (LOCATE('|',
                                    `a`.`description`,
                                    (LOCATE('|', `a`.`description`) + 1)) - 1))) - LOCATE('|', `a`.`description`, 1)))
        END) AS `REFERENCE_ID`,
        (CASE
            WHEN
                (UPPER(SUBSTR(`a`.`description`,
                            (LENGTH(SUBSTR(`a`.`description`,
                                        1,
                                        (LOCATE('|',
                                                `a`.`description`,
                                                (LOCATE('|', `a`.`description`) + 1)) - 1))) + 2),
                            LENGTH(`a`.`description`))) = 'NULL')
            THEN
                NULL
            ELSE SUBSTR(`a`.`description`,
                (LENGTH(SUBSTR(`a`.`description`,
                            1,
                            (LOCATE('|',
                                    `a`.`description`,
                                    (LOCATE('|', `a`.`description`) + 1)) - 1))) + 2),
                LENGTH(`a`.`description`))
        END) AS `WORKFLOWID`
    FROM
        (((SELECT 
            `service45`.`peopleassignments_potowners`.`task_id` AS `TASK_ID`,
                GROUP_CONCAT(`service45`.`peopleassignments_potowners`.`entity_id`
                    SEPARATOR ',') AS `TASK_ACTOR_ID`
        FROM
            `service45`.`peopleassignments_potowners`
        GROUP BY `service45`.`peopleassignments_potowners`.`task_id`) `c`
        JOIN `service45`.`task` `a`)
        JOIN (SELECT 
            `b`.`taskId` AS `TASKID`,
                GROUP_CONCAT((CASE
                    WHEN (`b`.`name` = 'APPLICATIONID') THEN `b`.`value`
                END), ''
                    SEPARATOR ',') AS `APM_APPLICATION_ID`,
                GROUP_CONCAT((CASE
                    WHEN (`b`.`name` = 'REFERENCEID') THEN `b`.`value`
                END), ''
                    SEPARATOR ',') AS `REFERENCE_ID`
        FROM
            `service45`.`taskvariableimpl` `b`
        WHERE
            ((`b`.`name` = 'APPLICATIONID')
                OR (`b`.`name` = 'REFERENCEID'))
        GROUP BY `b`.`taskId`) `b`)
    WHERE
        ((`a`.`id` = `c`.`TASK_ID`)
            AND (`b`.`TASKID` = `a`.`id`))
