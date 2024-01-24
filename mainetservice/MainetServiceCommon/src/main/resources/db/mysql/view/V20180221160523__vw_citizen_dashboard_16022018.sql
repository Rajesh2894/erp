--liquibase formatted sql
--changeset priya:V20180221160523__CR_vw_citizen_dashboard_16022018.sql
CREATE or replace VIEW vw_citizen_dashboard AS
    SELECT 
        c.APM_APPLICATION_ID AS apm_Application_Id,
        s.SM_SERVICE_NAME AS sm_Service_Name,
        s.SM_SERVICE_NAME_MAR AS sm_Service_Name_Mar,
        w.STATUS AS status,
        s.SM_SHORTDESC AS sm_Shortdesc,
        w.LAST_DECISION AS last_Decision,
        c.APM_APPLICATION_DATE AS apm_application_date,
        dp.DP_DEPTDESC AS dp_deptdesc,
        c.ORGID AS orgid,
        w.EMPID AS empid,
        t.task_url AS url
    FROM
        ((((tb_services_mst s
        JOIN tb_department dp)
        JOIN tb_cfc_application_mst c)
        JOIN tb_workflow_request w)
        JOIN task_request_view t)
    WHERE
        ((s.SM_SERVICE_ID = c.SM_SERVICE_ID)
            AND (dp.DP_DEPTID = s.CDM_DEPT_ID)
            AND (c.APM_APPLICATION_ID = w.APM_APPLICATION_ID)
            AND (t.APM_APPLICATION_ID = c.APM_APPLICATION_ID)) 
    UNION SELECT 
        c.APM_APPLICATION_ID AS apm_Application_Id,
        s.SM_SERVICE_NAME AS sm_Service_Name,
        s.SM_SERVICE_NAME_MAR AS sm_Service_Name_Mar,
        (CASE
            WHEN (c.APM_APPL_CLOSED_FLAG = 'Y') THEN 'CLOSED'
            ELSE 'PENDING'
        END) AS status,
        s.SM_SHORTDESC AS sm_Shortdesc,
        NULL AS last_Decision,
        c.APM_APPLICATION_DATE AS apm_application_date,
        dp.DP_DEPTDESC AS dp_deptdesc,
        c.ORGID AS orgid,
        c.USER_ID AS empid,
        NULL AS URL
    FROM
        ((tb_services_mst s
        JOIN tb_department dp)
        JOIN tb_cfc_application_mst c)
    WHERE
        ((s.SM_SERVICE_ID = c.SM_SERVICE_ID)
            AND (dp.DP_DEPTID = s.CDM_DEPT_ID)
            AND (s.SM_SCRU_APPLICABLE_FLAG = 'N')
            AND (NOT (c.APM_APPLICATION_ID IN (SELECT 
                tb_workflow_request.APM_APPLICATION_ID
            FROM
                tb_workflow_request))))