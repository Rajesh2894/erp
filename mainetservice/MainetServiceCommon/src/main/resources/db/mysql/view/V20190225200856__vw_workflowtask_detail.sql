--liquibase formatted sql
--changeset nilima:V20190225200856__vw_workflowtask_detail.sql
drop view if exists VW_WORKFLOWTASK_DETAIL;
--liquibase formatted sql
--changeset nilima:V20190225200856__vw_workflowtask_detail1.sql
CREATE VIEW VW_WORKFLOWTASK_DETAIL AS
    SELECT 
        a.WORKFLOW_ACT_ID AS WORKFLOW_ACT_ID,
        a.APM_APPLICATION_ID AS APM_APPLICATION_ID,
        a.DECISION AS DECISION,
        a.COMMENTS AS COMMENTS,
        a.ORGID AS ORGID,
        a.EMPID AS EMPID,
        a.EMPL_TYPE AS EMPL_TYPE,
        (SELECT DP_DEPTID FROM tb_department WHERE DP_DEPTID = c.DP_DEPTID) AS DP_DEPTID,
        (SELECT DP_DEPTDESC FROM tb_department WHERE DP_DEPTID = c.DP_DEPTID) AS DP_DEPTDESC,
        (SELECT DP_NAME_MAR FROM tb_department WHERE DP_DEPTID = c.DP_DEPTID) AS DP_NAME_MAR,
		(SELECT SM_SERVICE_ID FROM tb_services_mst WHERE SM_SERVICE_ID=c.SM_SERVICE_ID) AS SM_SERVICE_ID,
        (SELECT SM_SERVICE_NAME FROM tb_services_mst WHERE SM_SERVICE_ID=c.SM_SERVICE_ID) AS SM_SERVICE_NAME,
        (SELECT SM_SERVICE_NAME_MAR FROM tb_services_mst WHERE SM_SERVICE_ID = c.SM_SERVICE_ID) AS SM_SERVICE_NAME_MAR,
        c.EVENT_ID,
		(SELECT SMFNAME FROM tb_sysmodfunction WHERE SMFID=c.EVENT_ID) AS serviceEventName,
        (SELECT SMFNAME_MAR FROM tb_sysmodfunction WHERE SMFID =c.EVENT_ID) AS serviceEventNameReg,
        c.WFTASK_ESCALLEVEL AS WFTASK_ESCALLEVEL,
        c.TASK_SLA_DURATION AS TASK_SLA_DURATION,
        c.WFTASK_CCHEKLEVEL AS WFTASK_CCHEKLEVEL,
        c.WFTASK_CCHEKGROUP AS WFTASK_CCHEKGROUP,
		c.WFTASK_ASSIGDATE,
        c.WFTASK_COMPDATE,
        a.TASK_ID AS TASK_ID,
        a.TASK_NAME AS TASK_NAME,
		c.SMFACTION,
        a.DATE_OF_ACTION AS DATE_OF_ACTION,
        a.CREATED_DATE AS CREATED_DATE,
        a.CREATED_BY AS CREATED_BY,
        a.UPDATED_DATE AS UPDATED_DATE,
        a.UPDATED_BY AS UPDATED_BY,
		e.DATE_OF_REQUEST,
		e.WORFLOW_TYPE_ID,
        (CASE
            WHEN
                ((a.EMPL_TYPE IS NOT NULL)
                    AND (a.EMPL_TYPE <> '-1'))
            THEN
                (SELECT APM_FNAME FROM tb_cfc_application_mst
                    WHERE APM_APPLICATION_ID = a.APM_APPLICATION_ID)
            WHEN (a.EMPL_TYPE = '-1') THEN 'SYSTEM_USER'
            ELSE b.EMPNAME
        END) AS EMPNAME,
        (CASE
            WHEN
                ((a.EMPL_TYPE IS NOT NULL)
                    AND (a.EMPL_TYPE <> '-1'))
            THEN
                (SELECT 
                        APM_MNAME
                    FROM
                        tb_cfc_application_mst
                    WHERE
                        (APM_APPLICATION_ID = a.APM_APPLICATION_ID))
            ELSE b.EMPMNAME
        END) AS EMPMNAME,
        (CASE
            WHEN
                ((a.EMPL_TYPE IS NOT NULL)
                    AND (a.EMPL_TYPE <> '-1'))
            THEN
                (SELECT 
                        APM_LNAME
                    FROM
                        tb_cfc_application_mst
                    WHERE
                        (APM_APPLICATION_ID = a.APM_APPLICATION_ID))
            ELSE b.EMPLNAME
        END) AS EMPLNAME,
        (CASE
            WHEN
                ((a.EMPL_TYPE IS NOT NULL)
                    AND (a.EMPL_TYPE <> '-1'))
            THEN
                (SELECT 
                        APA_EMAIL
                    FROM
                        tb_cfc_application_address
                    WHERE
                        (APM_APPLICATION_ID = a.APM_APPLICATION_ID))
            ELSE b.EMPEMAIL
        END) AS EMPEMAIL,
        (CASE
            WHEN
                ((a.EMPL_TYPE IS NOT NULL)
                    AND (a.EMPL_TYPE <> '-1'))
            THEN
                'PROTAL_USER'
            WHEN (a.EMPL_TYPE = '-1') THEN 'SYSTEM_USER'
            ELSE (SELECT DSGNAME FROM designation
                WHERE (DSGID = b.DSGID))
        END) AS GR_DESC_ENG,
        (CASE
            WHEN
                ((a.EMPL_TYPE IS NOT NULL)
                    AND (a.EMPL_TYPE <> '-1'))
            THEN
                'PROTAL_USER'
            WHEN (a.EMPL_TYPE = '-1') THEN 'SYSTEM_USER'
            ELSE (SELECT 
                    DSGNAME_REG
                FROM
                    designation
                WHERE
                    (DSGID = b.DSGID))
        END) AS GR_DESC_REG,
        a.REFERENCE_ID AS REFERENCE_ID,
        (SELECT group_concat(ATT_PATH,':',ATT_FNAME,'|') 
		  FROM tb_attach_cfc 
          WHERE ATT_ID in (a.WORKFLOW_ATT)
		  GROUP BY APPLICATION_Id,REFERENCE_ID) AS att_path,
        b.EMPMOBNO AS EMPMOBNO,
        c.WORKFLOW_REQ_ID AS WORKFLOW_REQ_ID,
        c.TASK_STATUS AS TASK_STATUS
    FROM
        tb_workflow_request e,
		tb_workflow_task c,
        tb_workflow_action a
        LEFT JOIN employee b ON a.EMPID = b.EMPID
    WHERE
        (a.WFTASK_ID = c.WFTASK_ID and
		 e.WORKFLOW_REQ_ID=c.WORKFLOW_REQ_ID);