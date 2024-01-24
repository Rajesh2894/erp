--liquibase formatted sql
--changeset Kanchan:V20210414110506__CR_vw_citizen_dashboard_14042021.sql
CREATE VIEW `vw_citizen_dashboard` AS

    SELECT

        (CASE

            WHEN (`c`.`REF_NO` IS NOT NULL) THEN `c`.`REF_NO`

            ELSE `c`.`APM_APPLICATION_ID`

        END) AS `apm_Application_Id`,

        `s`.`SM_SERVICE_NAME` AS `sm_Service_Name`,

        `s`.`SM_SERVICE_NAME_MAR` AS `sm_Service_Name_Mar`,

        `w`.`STATUS` AS `status`,

        `s`.`SM_SHORTDESC` AS `sm_Shortdesc`,

        `w`.`LAST_DECISION` AS `last_Decision`,

        `c`.`APM_APPLICATION_DATE` AS `apm_application_date`,

        `dp`.`DP_DEPTDESC` AS `dp_deptdesc`,
       
         `dp`.`DP_NAME_MAR` AS `dp_name_mar`,

        `c`.`orgid` AS `orgid`,

        `w`.`EMPID` AS `empid`,

        `t`.`TASK_ID` AS `TASKID`,

        `t`.`TASK_SLA_DURATION` AS `TASK_SLA_DURATION`,

        `t`.`TASK_STATUS` AS `TASK_STATUS`,

        `t`.`WFTASK_ACTORID` AS `WFTASK_ACTORID`,

        `t`.`EVENT_ID` AS `EVENT_ID`,

        `sy`.`SMFNAME` AS `SMFNAME`,

        `sy`.`SMFNAME_MAR` AS `SMFNAME_MAR`,

        `t`.`SMFACTION` AS `SMFACTION`,

        `d`.`APA_MOBILNO` AS `APA_MOBILNO`,

        'W' AS `ServiceType`,

        `dp`.`DP_DEPTCODE` AS `DP_DEPTCODE`,

        `s`.`SM_SERDUR` AS `SM_SERDUR`

    FROM

        ((((((`tb_services_mst` `s`

        JOIN `tb_department` `dp`)

        JOIN `tb_cfc_application_mst` `c`)

        JOIN `tb_workflow_request` `w`)

        JOIN `tb_workflow_task` `t`)

        JOIN `tb_sysmodfunction` `sy`)

        JOIN `tb_cfc_application_address` `d`)

    WHERE

        ((`s`.`SM_SERVICE_ID` = `c`.`SM_SERVICE_ID`)

            AND (`dp`.`DP_DEPTID` = `s`.`CDM_DEPT_ID`)

            AND (`c`.`APM_APPLICATION_ID` = `w`.`APM_APPLICATION_ID`)

            AND (`w`.`WORKFLOW_REQ_ID` = `t`.`WORKFLOW_REQ_ID`)

            AND (`sy`.`SMFID` = `t`.`EVENT_ID`)

            AND (`d`.`APM_APPLICATION_ID` = `c`.`APM_APPLICATION_ID`)

            AND `t`.`WFTASK_ID` IN (SELECT

                MAX(`tb_workflow_task`.`WFTASK_ID`)

            FROM

                `tb_workflow_task`

            WHERE

                (`tb_workflow_task`.`EVENT_ID` <> -(1))

            GROUP BY `tb_workflow_task`.`APM_APPLICATION_ID`))

    UNION SELECT

        `c`.`APM_APPLICATION_ID` AS `apm_Application_Id`,

        `s`.`SM_SERVICE_NAME` AS `sm_Service_Name`,

        `s`.`SM_SERVICE_NAME_MAR` AS `sm_Service_Name_Mar`,

        (CASE

            WHEN (`c`.`APM_APPL_CLOSED_FLAG` = 'Y') THEN 'CLOSED'

            ELSE 'PENDING'

        END) AS `status`,

        `s`.`SM_SHORTDESC` AS `sm_Shortdesc`,

        NULL AS `last_Decision`,

        `c`.`APM_APPLICATION_DATE` AS `apm_application_date`,

        `dp`.`DP_DEPTDESC` AS `dp_deptdesc`,
       
         `dp`.`DP_NAME_MAR` AS `dp_name_mar`,

        `c`.`orgid` AS `orgid`,

        `c`.`USER_ID` AS `user_id`,

        NULL AS `TASKID`,

        NULL AS `TASK_SLA_DURATION`,

        NULL AS `TASK_STATUS`,

        NULL AS `WFTASK_ACTORID`,

        NULL AS `EVENT_ID`,

        NULL AS `SMFNAME`,

        NULL AS `SMFNAME_MAR`,

        NULL AS `SMFACTION`,

        `d`.`APA_MOBILNO` AS `APA_MOBILNO`,

        'NW' AS `ServiceType`,

        `dp`.`DP_DEPTCODE` AS `DP_DEPTCODE`,

        `s`.`SM_SERDUR` AS `SM_SERDUR`

    FROM

        (((`tb_services_mst` `s`

        JOIN `tb_department` `dp`)

        JOIN `tb_cfc_application_mst` `c`)

        JOIN `tb_cfc_application_address` `d`)

    WHERE

        ((`s`.`SM_SERVICE_ID` = `c`.`SM_SERVICE_ID`)

            AND (`dp`.`DP_DEPTID` = `s`.`CDM_DEPT_ID`)

            AND (`d`.`APM_APPLICATION_ID` = `c`.`APM_APPLICATION_ID`)

            AND (`s`.`SM_SCRU_APPLICABLE_FLAG` = 'N')

            AND (NOT (`c`.`APM_APPLICATION_ID` IN (SELECT

                `tb_workflow_request`.`APM_APPLICATION_ID`

            FROM

                `tb_workflow_request`))))

    UNION SELECT

        `c`.`APM_APPLICATION_ID` AS `apm_Application_Id`,

        `s`.`SM_SERVICE_NAME` AS `sm_Service_Name`,

        `s`.`SM_SERVICE_NAME_MAR` AS `sm_Service_Name_Mar`,

        (CASE

            WHEN (`c`.`APM_MODE` = 'D') THEN 'DRAFT'

            ELSE 'PENDING'

        END) AS `status`,

        `s`.`SM_SHORTDESC` AS `sm_Shortdesc`,

        NULL AS `last_Decision`,

        `c`.`APM_APPLICATION_DATE` AS `apm_application_date`,

        `dp`.`DP_DEPTDESC` AS `dp_deptdesc`,
       
         `dp`.`DP_NAME_MAR` AS `dp_name_mar`,

        `c`.`orgid` AS `orgid`,

        `c`.`USER_ID` AS `user_id`,

        NULL AS `TASKID`,

        NULL AS `TASK_SLA_DURATION`,

        NULL AS `TASK_STATUS`,

        NULL AS `WFTASK_ACTORID`,

        NULL AS `EVENT_ID`,

        NULL AS `SMFNAME`,

        NULL AS `SMFNAME_MAR`,

        NULL AS `SMFACTION`,

        `d`.`APA_MOBILNO` AS `APA_MOBILNO`,

        'D' AS `ServiceType`,

        `dp`.`DP_DEPTCODE` AS `DP_DEPTCODE`,

        `s`.`SM_SERDUR` AS `SM_SERDUR`

    FROM

        (((`tb_services_mst` `s`

        JOIN `tb_department` `dp`)

        JOIN `tb_cfc_application_mst` `c`)

        JOIN `tb_cfc_application_address` `d`)

    WHERE

        ((`s`.`SM_SERVICE_ID` = `c`.`SM_SERVICE_ID`)

            AND (`dp`.`DP_DEPTID` = `s`.`CDM_DEPT_ID`)

            AND (`d`.`APM_APPLICATION_ID` = `c`.`APM_APPLICATION_ID`)

            AND (`c`.`APM_MODE` = 'D'));
