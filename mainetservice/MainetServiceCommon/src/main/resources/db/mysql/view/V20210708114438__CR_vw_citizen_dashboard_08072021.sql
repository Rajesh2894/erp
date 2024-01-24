--liquibase formatted sql
--changeset Kanchan:V20210708114438__CR_vw_citizen_dashboard_08072021.sql
drop view if exists vw_citizen_dashboard;
--liquibase formatted sql
--changeset Kanchan:V20210708114438__CR_vw_citizen_dashboard_080720211.sql
CREATE VIEW vw_citizen_dashboard AS
   select `c`.`APM_APPLICATION_ID`  AS `apm_Application_Id`,
`c`.`REF_NO` AS `Reference_Id`,
`s`.`SM_SERVICE_NAME` AS `sm_Service_Name`,
`s`.`SM_SERVICE_NAME_MAR` AS `sm_Service_Name_Mar`,
`w`.`STATUS` AS `status`,
`s`.`SM_SHORTDESC` AS `sm_Shortdesc`,
`w`.`LAST_DECISION` AS `last_Decision`,`c`.`APM_APPLICATION_DATE` AS `apm_application_date`,`dp`.`DP_DEPTDESC` AS `dp_deptdesc`,
`dp`.`DP_NAME_MAR` AS `dp_name_mar`,`c`.`ORGID` AS `orgid`,`w`.`EMPID` AS `empid`,`t`.`TASK_ID` AS `TASKID`,
`t`.`TASK_SLA_DURATION` AS `TASK_SLA_DURATION`,`t`.`TASK_STATUS` AS `TASK_STATUS`,`t`.`WFTASK_ACTORID` AS `WFTASK_ACTORID`,
`t`.`EVENT_ID` AS `EVENT_ID`,`sy`.`SMFNAME` AS `SMFNAME`,`sy`.`SMFNAME_MAR` AS `SMFNAME_MAR`,`t`.`SMFACTION` AS `SMFACTION`,
`d`.`APA_MOBILNO` AS `APA_MOBILNO`,'W' AS `ServiceType`,`dp`.`DP_DEPTCODE` AS `DP_DEPTCODE`,`s`.`SM_SERDUR` AS `SM_SERDUR`
from ((((((`tb_services_mst` `s` join `tb_department` `dp`) join `tb_cfc_application_mst` `c`)
join `tb_workflow_request` `w`) join `tb_workflow_task` `t`)
join `tb_sysmodfunction` `sy`) join `tb_cfc_application_address` `d`)
where ((`s`.`SM_SERVICE_ID` = `c`.`SM_SERVICE_ID`) and (`dp`.`DP_DEPTID` = `s`.`CDM_DEPT_ID`)
and (`c`.`APM_APPLICATION_ID` = `w`.`APM_APPLICATION_ID`) and (`w`.`WORKFLOW_REQ_ID` = `t`.`WORKFLOW_REQ_ID`)
and (`sy`.`SMFID` = `t`.`EVENT_ID`) and (`d`.`APM_APPLICATION_ID` = `c`.`APM_APPLICATION_ID`)
and `t`.`WFTASK_ID` in (select max(`tb_workflow_task`.`WFTASK_ID`)
from `tb_workflow_task` where (`tb_workflow_task`.`EVENT_ID` <> -(1))
group by `tb_workflow_task`.`APM_APPLICATION_ID`))
union
select `c`.`APM_APPLICATION_ID` AS `apm_Application_Id`,
`c`.`REF_NO` AS `Reference_Id`,`s`.`SM_SERVICE_NAME` AS `sm_Service_Name`,
`s`.`SM_SERVICE_NAME_MAR` AS `sm_Service_Name_Mar`,
(case when (`c`.`APM_APPL_CLOSED_FLAG` = 'Y') then 'CLOSED' else 'PENDING' end) AS `status`,
`s`.`SM_SHORTDESC` AS `sm_Shortdesc`,NULL AS `last_Decision`,`c`.`APM_APPLICATION_DATE` AS `apm_application_date`,
`dp`.`DP_DEPTDESC` AS `dp_deptdesc`,`dp`.`DP_NAME_MAR` AS `dp_name_mar`,`c`.`ORGID` AS `orgid`, `c`.`USER_ID` AS `user_id`,
NULL AS `TASKID`,NULL AS `TASK_SLA_DURATION`,NULL AS `TASK_STATUS`,NULL AS `WFTASK_ACTORID`,NULL AS `EVENT_ID`,NULL AS `SMFNAME`,
NULL AS `SMFNAME_MAR`,NULL AS `SMFACTION`,`d`.`APA_MOBILNO` AS `APA_MOBILNO`,'NW' AS `ServiceType`,`dp`.`DP_DEPTCODE` AS `DP_DEPTCODE`,
`s`.`SM_SERDUR` AS `SM_SERDUR` from (((`tb_services_mst` `s` join `tb_department` `dp`)
join `tb_cfc_application_mst` `c`) join `tb_cfc_application_address` `d`)
where ((`s`.`SM_SERVICE_ID` = `c`.`SM_SERVICE_ID`) and (`dp`.`DP_DEPTID` = `s`.`CDM_DEPT_ID`)
and (`d`.`APM_APPLICATION_ID` = `c`.`APM_APPLICATION_ID`) and (`s`.`SM_SCRU_APPLICABLE_FLAG` = 'N')
and (not(`c`.`APM_APPLICATION_ID` in (select `tb_workflow_request`.`APM_APPLICATION_ID`
from `tb_workflow_request`))))
union
select `c`.`APM_APPLICATION_ID` AS `apm_Application_Id`,
`c`.`REF_NO` AS `Reference_Id`,
`s`.`SM_SERVICE_NAME` AS `sm_Service_Name`,`s`.`SM_SERVICE_NAME_MAR` AS `sm_Service_Name_Mar`,
(case when (`c`.`APM_MODE` = 'D') then 'DRAFT' else 'PENDING' end) AS `status`,`s`.`SM_SHORTDESC` AS `sm_Shortdesc`,
NULL AS `last_Decision`,`c`.`APM_APPLICATION_DATE` AS `apm_application_date`,`dp`.`DP_DEPTDESC` AS `dp_deptdesc`,
`dp`.`DP_NAME_MAR` AS `dp_name_mar`,`c`.`ORGID` AS `orgid`,`c`.`USER_ID` AS `user_id`,NULL AS `TASKID`,
NULL AS `TASK_SLA_DURATION`,NULL AS `TASK_STATUS`,NULL AS `WFTASK_ACTORID`,NULL AS `EVENT_ID`,
NULL AS `SMFNAME`,NULL AS `SMFNAME_MAR`,NULL AS `SMFACTION`,`d`.`APA_MOBILNO` AS `APA_MOBILNO`,
'D' AS `ServiceType`,`dp`.`DP_DEPTCODE` AS `DP_DEPTCODE`,`s`.`SM_SERDUR` AS `SM_SERDUR`
from (((`tb_services_mst` `s` join `tb_department` `dp`)
join `tb_cfc_application_mst` `c`) join `tb_cfc_application_address` `d`)
where ((`s`.`SM_SERVICE_ID` = `c`.`SM_SERVICE_ID`) and (`dp`.`DP_DEPTID` = `s`.`CDM_DEPT_ID`)
and (`d`.`APM_APPLICATION_ID` = `c`.`APM_APPLICATION_ID`) and (`c`.`APM_MODE` = 'D'));
