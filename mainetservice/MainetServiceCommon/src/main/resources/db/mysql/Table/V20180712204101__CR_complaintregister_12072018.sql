--liquibase formatted sql
--changeset nilima:V20180712204101__CR_complaintregister_12072018.sql
CREATE or replace  
VIEW `complaintregister` AS
    SELECT 
        `a`.`ORGID` AS `ORGID`,
        `h`.`O_NLS_ORGNAME` AS `ULB_NAME_ENG`,
        `h`.`O_NLS_ORGNAME_MAR` AS `ULB_NAME_REG`,
        `h`.`ORG_CPD_ID_DIV` AS `DIVISION`,
        (SELECT 
                `tb_comparam_det`.`CPD_DESC`
            FROM
                `tb_comparam_det`
            WHERE
                (`tb_comparam_det`.`CPD_ID` = `h`.`ORG_CPD_ID_DIV`)) AS `DIVISION_ENG`,
        (SELECT 
                `tb_comparam_det`.`CPD_DESC_MAR`
            FROM
                `tb_comparam_det`
            WHERE
                (`tb_comparam_det`.`CPD_ID` = `h`.`ORG_CPD_ID_DIV`)) AS `DIVISION_REG`,
        `h`.`ORG_CPD_ID_DIS` AS `ORG_CPD_ID_DIS`,
        (SELECT 
                `tb_comparam_det`.`CPD_DESC`
            FROM
                `tb_comparam_det`
            WHERE
                (`tb_comparam_det`.`CPD_ID` = `h`.`ORG_CPD_ID_DIS`)) AS `DISTRICT_ENG`,
        (SELECT 
                `tb_comparam_det`.`CPD_DESC_MAR`
            FROM
                `tb_comparam_det`
            WHERE
                (`tb_comparam_det`.`CPD_ID` = `h`.`ORG_CPD_ID_DIS`)) AS `DISTRICT_REG`,
        (SELECT 
                `tb_comparam_det`.`CPD_DESC`
            FROM
                `tb_comparam_det`
            WHERE
                (`tb_comparam_det`.`CPD_ID` = `h`.`ORG_CPD_ID`)) AS `ULB_TYPE`,
        `a`.`APM_APPLICATION_ID` AS `COMPLAINTNO`,
        (SELECT 
                CONCAT(CONCAT(CONCAT(`tb_cfc_application_mst`.`APM_FNAME`,
                                            ' '),
                                    `tb_cfc_application_mst`.`APM_MNAME`,
                                    ' '),
                            `tb_cfc_application_mst`.`APM_LNAME`)
            FROM
                `tb_cfc_application_mst`
            WHERE
                (`tb_cfc_application_mst`.`APM_APPLICATION_ID` = `a`.`APM_APPLICATION_ID`)) AS `APPLICANT_NAME`,
        `a`.`DEPT_COMP_ID` AS `DEPT_COMP_ID`,
        `d`.`COMP_TYPE_DESC` AS `COMPLAINT_SUB_TYPE`,
        `d`.`COMP_TYPE_REG` AS `COMPLAINT_SUB_TYPE_REG`,
        `f`.`DEPT_ID` AS `DEPARTMENT_ID`,
        `c`.`DP_DEPTDESC` AS `DEPARTMENT_ENG`,
        `c`.`DP_NAME_MAR` AS `DEPARTMENT_REG`,
        `a`.`LOC_ID` AS `LOC_ID`,
        (SELECT 
                `tb_location_mas`.`LOC_NAME_ENG`
            FROM
                `tb_location_mas`
            WHERE
                (`tb_location_mas`.`LOC_ID` = `a`.`LOC_ID`)) AS `LOCATIONNAME_ENG`,
        (SELECT 
                `tb_location_mas`.`LOC_NAME_REG`
            FROM
                `tb_location_mas`
            WHERE
                (`tb_location_mas`.`LOC_ID` = `a`.`LOC_ID`)) AS `LOCATIONNAME_REG`,
        (SELECT 
                `tb_location_mas`.`LATTIUDE`
            FROM
                `tb_location_mas`
            WHERE
                (`tb_location_mas`.`LOC_ID` = `a`.`LOC_ID`)) AS `Latitude`,
        (SELECT 
                `tb_location_mas`.`LONGITUDE`
            FROM
                `tb_location_mas`
            WHERE
                (`tb_location_mas`.`LOC_ID` = `a`.`LOC_ID`)) AS `longitude`,
        `g`.`COD_ID_OPER_LEVEL1` AS `COD_ID_OPER_LEVEL1`,
        (SELECT 
                `tb_comparent_det`.`COD_DESC`
            FROM
                `tb_comparent_det`
            WHERE
                (`tb_comparent_det`.`COD_ID` = `g`.`COD_ID_OPER_LEVEL1`)) AS `L1`,
        `g`.`COD_ID_OPER_LEVEL2` AS `COD_ID_OPER_LEVEL2`,
        (SELECT 
                `tb_comparent_det`.`COD_DESC`
            FROM
                `tb_comparent_det`
            WHERE
                (`tb_comparent_det`.`COD_ID` = `g`.`COD_ID_OPER_LEVEL2`)) AS `L2`,
        `g`.`COD_ID_OPER_LEVEL3` AS `COD_ID_OPER_LEVEL3`,
        (SELECT 
                `tb_comparent_det`.`COD_DESC`
            FROM
                `tb_comparent_det`
            WHERE
                (`tb_comparent_det`.`COD_ID` = `g`.`COD_ID_OPER_LEVEL3`)) AS `L3`,
        `g`.`COD_ID_OPER_LEVEL4` AS `COD_ID_OPER_LEVEL4`,
        (SELECT 
                `tb_comparent_det`.`COD_DESC`
            FROM
                `tb_comparent_det`
            WHERE
                (`tb_comparent_det`.`COD_ID` = `g`.`COD_ID_OPER_LEVEL3`)) AS `L4`,
        `g`.`COD_ID_OPER_LEVEL5` AS `COD_ID_OPER_LEVEL5`,
        (SELECT 
                `tb_comparent_det`.`COD_DESC`
            FROM
                `tb_comparent_det`
            WHERE
                (`tb_comparent_det`.`COD_ID` = `g`.`COD_ID_OPER_LEVEL5`)) AS `L5`,
        `e`.`SM_SERVICE_ID` AS `SM_SERVICE_ID`,
        (SELECT 
                `tb_services_mst`.`SM_SERVICE_NAME`
            FROM
                `tb_services_mst`
            WHERE
                (`tb_services_mst`.`SM_SERVICE_ID` = `e`.`SM_SERVICE_ID`)) AS `SERVICE_TYPE`,
        (SELECT 
                `b`.`COD_DESC`
            FROM
                (`tb_location_elect_wardzone` `i`
                JOIN `tb_comparent_det` `b`)
            WHERE
                ((`i`.`LOC_ID` = `a`.`LOC_ID`)
                    AND (`i`.`COD_ID_ELEC_LEVEL1` = `b`.`COD_ID`))) AS `ELECTIONWARD_L1`,
        (SELECT 
                `b`.`COD_DESC`
            FROM
                (`tb_location_elect_wardzone` `i`
                JOIN `tb_comparent_det` `b`)
            WHERE
                ((`i`.`LOC_ID` = `a`.`LOC_ID`)
                    AND (`i`.`COD_ID_ELEC_LEVEL2` = `b`.`COD_ID`))) AS `ELECTIONWARD_L2`,
        (SELECT 
                `b`.`COD_DESC`
            FROM
                (`tb_location_elect_wardzone` `i`
                JOIN `tb_comparent_det` `b`)
            WHERE
                ((`i`.`LOC_ID` = `a`.`LOC_ID`)
                    AND (`i`.`COD_ID_ELEC_LEVEL3` = `b`.`COD_ID`))) AS `ELECTIONWARD_L3`,
        (SELECT 
                `b`.`COD_DESC`
            FROM
                (`tb_location_elect_wardzone` `i`
                JOIN `tb_comparent_det` `b`)
            WHERE
                ((`i`.`LOC_ID` = `i`.`LOC_ID`)
                    AND (`i`.`COD_ID_ELEC_LEVEL4` = `b`.`COD_ID`))) AS `ELECTIONWARD_L4`,
        (SELECT 
                `b`.`COD_DESC`
            FROM
                (`tb_location_elect_wardzone` `i`
                JOIN `tb_comparent_det` `b`)
            WHERE
                ((`i`.`LOC_ID` = `a`.`LOC_ID`)
                    AND (`i`.`COD_ID_ELEC_LEVEL4` = `b`.`COD_ID`))) AS `ELECTIONWARD_L5`,
        (SELECT 
                `tb_care_feedback`.`RATINGS`
            FROM
                `tb_care_feedback`
            WHERE
                (`tb_care_feedback`.`TOKEN_NUMBER` = `a`.`APM_APPLICATION_ID`)) AS `FEEDBACKRATINGS`,
        (SELECT 
                `tb_care_feedback`.`RATINGS_STAR_COUNT`
            FROM
                `tb_care_feedback`
            WHERE
                (`tb_care_feedback`.`TOKEN_NUMBER` = `a`.`APM_APPLICATION_ID`)) AS `FEEDBACKSTARCOUNT`,
        IFNULL((SELECT 
                        `tb_comparam_det`.`CPD_DESC`
                    FROM
                        `tb_comparam_det`
                    WHERE
                        (`tb_comparam_det`.`CPD_ID` = `a`.`REFERENCE_MODE`)),
                'ONLINE') AS `CARE_MODE`,
        `b`.`LAST_DATE_OF_ACTION` AS `LASTDAYACTION`,
        `b`.`DATE_OF_REQUEST` AS `DATEOFREQUEST`,
        (CASE
            WHEN
                ((REPLACE(`b`.`STATUS`,
                    'EXPIRED',
                    'CLOSED') = 'CLOSED')
                    AND (`b`.`LAST_DECISION` = 'REJECTED'))
            THEN
                'REJECTED'
            WHEN
                ((REPLACE(`b`.`STATUS`,
                    'EXPIRED',
                    'CLOSED') = 'CLOSED')
                    AND (`b`.`LAST_DECISION` <> 'REJECTED'))
            THEN
                'CLOSED'
            WHEN
                ((REPLACE(`b`.`STATUS`,
                    'EXPIRED',
                    'CLOSED') = 'PENDING')
                    AND (`b`.`LAST_DECISION` = 'HOLD'))
            THEN
                'HOLD'
            WHEN
                ((REPLACE(`b`.`STATUS`,
                    'EXPIRED',
                    'CLOSED') = 'PENDING')
                    AND (`b`.`LAST_DECISION` <> 'HOLD'))
            THEN
                'PENDING'
        END) AS `STATUS`,
        (CASE
            WHEN
                (`b`.`STATUS` = 'PENDING')
            THEN
                ROUND(((NOW() - `b`.`DATE_OF_REQUEST`) / 1000000),
                        0)
            ELSE TIMESTAMPDIFF(DAY,
                `b`.`DATE_OF_REQUEST`,
                `b`.`LAST_DATE_OF_ACTION`)
        END) AS `NOOFDAY`,
        (CASE
            WHEN
                ((`b`.`STATUS` = 'PENDING')
                    AND (TIMESTAMPDIFF(SECOND,
                    `b`.`DATE_OF_REQUEST`,
                    NOW()) >= (CASE
                    WHEN
                        (((SELECT 
                                SUM(COALESCE(`tb_workflow_det`.`SLA_CAL`, 0))
                            FROM
                                `tb_workflow_det`
                            WHERE
                                (`tb_workflow_det`.`WF_ID` = `e`.`WF_ID`)) / 1000) <> 0)
                    THEN
                        ((SELECT 
                                SUM(COALESCE(`tb_workflow_det`.`SLA_CAL`, 0))
                            FROM
                                `tb_workflow_det`
                            WHERE
                                (`tb_workflow_det`.`WF_ID` = `e`.`WF_ID`)) / 1000)
                    ELSE (SELECT 
                            (`tb_services_mst`.`SM_SERDUR` / 1000)
                        FROM
                            `tb_services_mst`
                        WHERE
                            (`tb_services_mst`.`SM_SERVICE_ID` = `e`.`SM_SERVICE_ID`))
                END)))
            THEN
                'B'
            WHEN
                ((`b`.`STATUS` = 'PENDING')
                    AND (TIMESTAMPDIFF(SECOND,
                    `b`.`DATE_OF_REQUEST`,
                    NOW()) < (CASE
                    WHEN
                        (((SELECT 
                                SUM(COALESCE(`tb_workflow_det`.`SLA_CAL`, 0))
                            FROM
                                `tb_workflow_det`
                            WHERE
                                (`tb_workflow_det`.`WF_ID` = `e`.`WF_ID`)) / 1000) <> 0)
                    THEN
                        ((SELECT 
                                SUM(COALESCE(`tb_workflow_det`.`SLA_CAL`, 0))
                            FROM
                                `tb_workflow_det`
                            WHERE
                                (`tb_workflow_det`.`WF_ID` = `e`.`WF_ID`)) / 1000)
                    ELSE (SELECT 
                            (`tb_services_mst`.`SM_SERDUR` / 1000)
                        FROM
                            `tb_services_mst`
                        WHERE
                            (`tb_services_mst`.`SM_SERVICE_ID` = `e`.`SM_SERVICE_ID`))
                END)))
            THEN
                'W'
            WHEN
                ((REPLACE(`b`.`STATUS`,
                    'EXPIRED',
                    'CLOSED') IN ('CLOSED' , 'EXPIRED'))
                    AND (TIMESTAMPDIFF(SECOND,
                    `b`.`DATE_OF_REQUEST`,
                    `b`.`LAST_DATE_OF_ACTION`) > (CASE
                    WHEN
                        (((SELECT 
                                SUM(COALESCE(`tb_workflow_det`.`SLA_CAL`, 0))
                            FROM
                                `tb_workflow_det`
                            WHERE
                                (`tb_workflow_det`.`WF_ID` = `e`.`WF_ID`)) / 1000) <> 0)
                    THEN
                        ((SELECT 
                                SUM(COALESCE(`tb_workflow_det`.`SLA_CAL`, 0))
                            FROM
                                `tb_workflow_det`
                            WHERE
                                (`tb_workflow_det`.`WF_ID` = `e`.`WF_ID`)) / 1000)
                    ELSE (SELECT 
                            (`tb_services_mst`.`SM_SERDUR` / 1000)
                        FROM
                            `tb_services_mst`
                        WHERE
                            (`tb_services_mst`.`SM_SERVICE_ID` = `e`.`SM_SERVICE_ID`))
                END)))
            THEN
                'B'
            WHEN
                ((REPLACE(`b`.`STATUS`,
                    'EXPIRED',
                    'CLOSED') IN ('CLOSED' , 'EXPIRED'))
                    AND (TIMESTAMPDIFF(SECOND,
                    `b`.`DATE_OF_REQUEST`,
                    `b`.`LAST_DATE_OF_ACTION`) <= (CASE
                    WHEN
                        (((SELECT 
                                SUM(COALESCE(`tb_workflow_det`.`SLA_CAL`, 0))
                            FROM
                                `tb_workflow_det`
                            WHERE
                                (`tb_workflow_det`.`WF_ID` = `e`.`WF_ID`)) / 1000) <> 0)
                    THEN
                        ((SELECT 
                                SUM(COALESCE(`tb_workflow_det`.`SLA_CAL`, 0))
                            FROM
                                `tb_workflow_det`
                            WHERE
                                (`tb_workflow_det`.`WF_ID` = `e`.`WF_ID`)) / 1000)
                    ELSE (SELECT 
                            (`tb_services_mst`.`SM_SERDUR` / 1000)
                        FROM
                            `tb_services_mst`
                        WHERE
                            (`tb_services_mst`.`SM_SERVICE_ID` = `e`.`SM_SERVICE_ID`))
                END)))
            THEN
                'W'
        END) AS `SLA`
    FROM
        (((((((`tb_care_request` `a`
        JOIN `tb_workflow_request` `b`)
        JOIN `tb_dept_complaint_type` `f`)
        JOIN `tb_department` `c`)
        JOIN `tb_dep_complaint_subtype` `d`)
        JOIN `tb_workflow_mas` `e`)
        JOIN `tb_location_oper_wardzone` `g`)
        JOIN `tb_organisation` `h`)
    WHERE
        ((`a`.`APM_APPLICATION_ID` = `b`.`APM_APPLICATION_ID`)
            AND (`a`.`DEPT_COMP_ID` = `f`.`DEPT_COMP_ID`)
            AND (`c`.`DP_DEPTID` = `f`.`DEPT_ID`)
            AND (`d`.`DEPT_COMP_ID` = `a`.`DEPT_COMP_ID`)
            AND (`d`.`COMP_ID` = `e`.`COMP_ID`)
            AND (`e`.`WF_ID` = `b`.`WORFLOW_TYPE_ID`)
            AND (`a`.`LOC_ID` = `g`.`LOC_ID`)
            AND (`f`.`DEPT_ID` = `g`.`DP_DEPTID`)
            AND (`h`.`ORGID` = `a`.`ORGID`))