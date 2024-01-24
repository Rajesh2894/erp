select 
cr.OrganisationName,
(select dep.DP_DEPTDESC from tb_department dep where dep.DP_DEPTID=cr.DEPT_COMP_ID) department_desc,
(select COD_DESC from tb_comparent_det where COD_ID=cr.CARE_WARD_NO) CARE_WARD_NO_ENG_desc,
(select COD_DESC from tb_comparent_det where COD_ID=cr.CARE_WARD_NO1)CARE_WARD_NO_ENG_desc_1,
(select comp.comp_type_desc from tb_dep_complaint_subtype comp where comp.COMP_ID=cr.COMP_SUBTYPE_ID) complaintSubType,
COMPLAINT_DESC,
(case when (cr.STATUS = 'PENDING') then timestampdiff(DAY,cr.dateofrequest,now()) else
timestampdiff(DAY,cr.dateofrequest,cr.LAST_DATE_OF_ACTION) end) AS NOOFDAY,
COMPLAINT_NO,
cr.dateofrequest,
applicantName,
APA_AREANM As Address,
APA_MOBILNO As MobileNo,
APA_EMAIL Emaild,
cr.STATUS status,
CASE WHEN cr.STATUS='CLOSED' and cr.SLA='B' THEN 'Yes' END AS BeyoundCLOSED,
CASE WHEN cr.STATUS='CLOSED' and cr.SLA='W'THEN 'Yes' END AS WithinCLOSED,
cr.EmployeeName
from
(select STRAIGHT_JOIN
(select org.O_NLS_ORGNAME  from tb_organisation org where org.ORGID =tb1.ORGID) OrganisationName,
tb1.DEPT_COMP_ID,
tb1.COMP_SUBTYPE_ID,
tb1.CARE_WARD_NO,
tb1.CARE_WARD_NO1,
tb1.COMPLAINT_NO,
tb1.DATE_OF_REQUEST dateofrequest,
tb1.COMPLAINT_DESC,
concat(COALESCE(tb2.APM_FNAME,''),' ',COALESCE(tb2.APM_MNAME,''),' ',COALESCE(tb2.APM_LNAME,'')) as applicantName,
tb3.APA_AREANM,
tb3.APA_MOBILNO,
tb3.APA_EMAIL,
tb1.LAST_DATE_OF_ACTION,
(CASE WHEN ((REPLACE(tb4.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (tb4.LAST_DECISION = 'REJECTED'))
 THEN 'REJECTED' WHEN ((REPLACE(tb4.STATUS,'EXPIRED','CLOSED') = 'CLOSED') AND (tb4.LAST_DECISION <> 'REJECTED'))
 THEN 'CLOSED' WHEN ((REPLACE(tb4.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (tb4.LAST_DECISION = 'HOLD'))
 THEN 'HOLD'   WHEN ((REPLACE(tb4.STATUS,'EXPIRED','CLOSED') = 'PENDING') AND (tb4.LAST_DECISION <> 'HOLD'))
 THEN 'PENDING'  END)  AS STATUS,
(CASE WHEN 
 ((tb4.STATUS = 'PENDING') AND (TIMESTAMPDIFF(SECOND,tb4.DATE_OF_REQUEST,NOW()) >= 
(CASE WHEN (((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det WHERE (WF_ID = tb5.WF_ID)) / 1000) <> 0) THEN ((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det 
WHERE (WF_ID = tb5.WF_ID)) / 1000) 
ELSE (SELECT (SM_SERDUR / 1000) FROM tb_services_mst WHERE (SM_SERVICE_ID = tb5.SM_SERVICE_ID)) END))) THEN 'B'
WHEN ((tb4.STATUS = 'PENDING') AND (TIMESTAMPDIFF(SECOND,tb4.DATE_OF_REQUEST,NOW()) < 
(CASE WHEN (((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det WHERE (WF_ID = tb5.WF_ID)) / 1000) <> 0)
THEN ((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det WHERE (WF_ID = tb5.WF_ID)) / 1000)
ELSE (SELECT (SM_SERDUR / 1000) FROM tb_services_mst WHERE (SM_SERVICE_ID = tb5.SM_SERVICE_ID)) END)))
THEN 'W'      
WHEN ((REPLACE(tb4.STATUS,'EXPIRED','CLOSED') IN ('CLOSED' , 'EXPIRED')) AND (TIMESTAMPDIFF(SECOND,
tb4.DATE_OF_REQUEST,tb4.LAST_DATE_OF_ACTION) > 
(CASE WHEN (((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det WHERE (WF_ID = tb5.WF_ID)) / 1000) <> 0)
THEN ((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det WHERE (WF_ID = tb5.WF_ID)) / 1000)
ELSE (SELECT (SM_SERDUR / 1000) FROM tb_services_mst WHERE (SM_SERVICE_ID = tb5.SM_SERVICE_ID))               END))) THEN 'B' WHEN ((REPLACE(tb4.STATUS,'EXPIRED','CLOSED') IN ('CLOSED' , 'EXPIRED')) AND 
(TIMESTAMPDIFF(SECOND,tb4.DATE_OF_REQUEST,tb4.LAST_DATE_OF_ACTION) <= (CASE WHEN (((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det WHERE (WF_ID = tb5.wf_id)) / 1000) <> 0) THEN 
((SELECT SUM(COALESCE(SLA_CAL, 0)) FROM tb_workflow_det WHERE  (WF_ID = tb5.WF_ID)) / 1000)
ELSE (SELECT (SM_SERDUR / 1000) FROM tb_services_mst WHERE (SM_SERVICE_ID = tb5.SM_SERVICE_ID)) END)))
THEN 'W' END) AS SLA,
wr.EmployeeName As EmployeeName
from
tb_care_request tb1,
tb_cfc_application_mst tb2,
tb_cfc_application_address tb3,
TB_WORKFLOW_REQUEST tb4,
tb_workflow_mas tb5,
(select STRAIGHT_JOIN concat(b.EMPNAME,' ',b.EMPMNAME,' ',b.EMPLNAME) EmployeeName,APM_APPLICATION_ID
from tb_workflow_action a,employee b where a.empid=b.empid and WORKFLOW_ACT_ID
in (select max(WORKFLOW_ACT_ID) from tb_workflow_action
group by APM_APPLICATION_ID)) wr
where
tb1.APM_APPLICATION_ID =tb2.APM_APPLICATION_ID and
tb2.APM_APPLICATION_ID =tb3.APM_APPLICATION_ID and
tb3.APM_APPLICATION_ID= tb4.APM_APPLICATION_ID and
tb4.APM_APPLICATION_ID=wr.APM_APPLICATION_ID and
tb5.WF_ID = tb4.WORFLOW_TYPE_ID and
tb1.DATE_OF_REQUEST between '2019-04-01' and '2020-03-31' and
/*tb1.CARE_WARD_NO is not null and
tb1.CARE_WARD_NO1 is not null and*/
tb1.SM_SERVICE_ID is null /* for complaint request */ and
COALESCE(tb1.ORGID,0)=(case when COALESCE(0,0)=0 then COALESCE(tb1.ORGID,0) else COALESCE(0,0) end) and
COALESCE(tb1.DEPT_COMP_ID ,0)=(case when COALESCE(0,0)=0 then COALESCE(tb1.DEPT_COMP_ID ,0) else COALESCE(0,0) end) and
COALESCE(tb1.COMP_SUBTYPE_ID,0)=(case when COALESCE(0,0)=0 then COALESCE(tb1.COMP_SUBTYPE_ID,0) else COALESCE(0,0) end) and
COALESCE(tb1.CARE_WARD_NO,0)=(case when COALESCE(0,0)=0 then COALESCE(tb1.CARE_WARD_NO,0) else COALESCE(0,0) end) and
COALESCE(tb1.CARE_WARD_NO1,0)=(case when COALESCE(0,0)=0 then COALESCE(tb1.CARE_WARD_NO1,0) else COALESCE(0,0) end) ) cr
order by cr.OrganisationName