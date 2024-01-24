--liquibase formatted sql
--changeset priya:V20180123181126__vw_citizen_dashboard.sql
create or replace view vw_citizen_dashboard as
select c.apm_Application_Id,
s.sm_Service_Name,
s.sm_Service_Name_Mar,
w.status, 
s.sm_Shortdesc,
w.last_Decision,
c.apm_application_date, 
dp.dp_deptdesc,
c.orgid,
w.empid
from 
tb_serviceS_Mst s, 
tb_department dp, 
tb_cfc_Application_Mst c, 
TB_Workflow_Request w
where  s.sm_service_Id = c.sm_Service_Id and
dp.dp_deptid=cdm_dept_id and
c.apm_Application_Id=w.apm_Application_Id 
union
select c.apm_Application_Id,
s.sm_Service_Name,
s.sm_Service_Name_Mar,
(case when c.apm_appl_closed_flag='Y' then
    'CLOSED'
   else
     'PENDING'
   end)
status, 
s.sm_Shortdesc,
null last_Decision,
c.apm_application_date, 
dp.dp_deptdesc,
c.orgid,
c.user_id
from 
tb_serviceS_Mst s, 
tb_department dp, 
tb_cfc_Application_Mst c 
where  s.sm_service_Id = c.sm_Service_Id and
dp.dp_deptid=cdm_dept_id and
s.SM_SCRU_APPLICABLE_FLAG='N' and
c.apm_Application_Id not in
(select apm_Application_Id from TB_Workflow_Request)










