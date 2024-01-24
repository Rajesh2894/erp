create or replace force view vw_cfc_checklist as
select a.apm_application_id,
       a.apm_application_date,
       fn_getcpddesc(a.apm_title, 'E', a.orgid) englis_title,
       fn_getcpddesc(a.apm_title, 'R', a.orgid) Regional_title,
       a.apm_fname || ' ' || a.apm_mname || ' ' || a.apm_lname applicants_name,
       a.sm_service_id,
       c.sm_service_name service_name,
       c.sm_service_name_mar service_name_mar,
       NVL(a.apm_chklst_vrfy_flag,'P')apm_chklst_vrfy_flag,
     -- NVL(b.apm_chklst_vrfy_flag, 'P') apm_chklst_vrfy_flag, ---Commented By Ashish tb_cfc_application_status this table is not used
       a.orgid,
       c.cdm_dept_id
  from tb_cfc_application_mst    a,
       --tb_cfc_application_status b,
       tb_services_mst           c
 where /*a.apm_application_id = b.apm_application_id
   and a.orgid =  b.orgid
   and*/  ---Commented By Ashish tb_cfc_application_status this table is not used
   a.sm_service_id = c.sm_service_id
   and a.orgid = c.orgid
   /*edited By lalit as field not avail in ahemad nagar*/
   /*and fn_getcpddesc(c.cpd_id_apl_chklist_verify,'V',c.orgid) = 'A'*/
   --and nvl(b.apm_pay_stat_flag,'x') in ('F','Y') ---Commented By Ashish tb_cfc_application_status this table is not used
   and nvl(a.apm_pay_stat_flag,'x') in ('F','Y')
   and exists ( select 1
                   from tb_attach_cfc ac
                  where ac.orgid = a.orgid
                    and ac.application_id = a.apm_application_id
                    and ac.service_id = a.sm_service_id);
