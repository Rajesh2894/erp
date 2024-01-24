---------------------------------------------------------
-- Export file for user SERVICE1                       --
-- Created by kailash.agarwal on 3/17/2017, 5:29:52 PM --
---------------------------------------------------------

set define off
spool view.log

prompt
prompt Creating view VW_APP_REJ_REPORT
prompt ===============================
prompt
create or replace force view vw_app_rej_report as
select distinct /*rm.cfc_rejection_id,*/ am.apm_application_id,
                to_char (am.apm_application_date, 'DD/MM/RRRR') adate,
                 sm.sm_service_name  service,
               fn_getcpddesc (am.apm_title, 'E',am.orgid)
                || ' '
                || am.apm_fname
                || ' '
                || am.apm_mname
                || ' '
                || am.apm_lname aname,
               /*fn_getcpddesc(ad.apa_blockno,'E',ad.orgid)
                || ' '
                || ad.apa_blockno
                || ' '
                || ad.apa_floor
                || ' '
                || ad.apa_wing
                || ' '
                || ad.apa_bldgnm
                || ' '
                || ad.apa_hsg_cmplxnm
                || ' '
                || ad.apa_roadnm
                || ' '
                || ad.apa_areanm
                || ' '
                || ad.apa_pincode aaddress,*/  --commented by ashish mahadik against D-7651 on 29-01-2016
               am.orgid/*,
               am.rejction_no,
               am.rejection_dt*/
           from tb_cfc_application_mst am,
                tb_services_mst sm,
                /*tb_cfc_rejection rm,*/
                tb_cfc_application_address ad
          where am.sm_service_id = sm.sm_service_id
            /*and am.apm_application_id = rm.apm_application_id*/  --commented by ashish mahadik against D-7651 on 29-01-2016
            and am.apm_application_id = ad.apm_application_id
            and am.orgid = sm.orgid
            and sm.orgid = ad.orgid
          order by am.apm_application_id
;

prompt
prompt Creating view VW_CFC_CHECKLIST
prompt ==============================
prompt
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
                    and ac.service_id = a.sm_service_id)
;

prompt
prompt Creating view VW_PREFIX_DETAILS
prompt ===============================
prompt
create or replace force view vw_prefix_details as
select  cpm_id,cpm_prefix,cpm_desc,cpm_type,com_id,com_level,com_value,com_desc,com_desc_mar,cod_id cod_cpd_id,cod_value cod_cpd_value,cpd_others,cod_desc cod_cpd_desc,cod_desc_mar cod_cpd_desc_mar,cod_cpd_default,parent_id cod_cpd_parent_id,orgid,cpm_replicate_flag
from  /* Created by Ajit B Gaikwad dated on 2 jan 2015*/
(
with tb_hir_details as
(
   select  x.cod_id , x.orgid
     from tb_comparent_mas m,tb_comparent_det x
     where m.com_id = x.com_id
       and m.orgid = x.orgid
       and x.cod_status = 'Y'
    start with x.parent_id is null  and x.orgid = m.orgid and x.cod_status = 'Y'
    connect by prior x.cod_id = x.parent_id  and  x.orgid = m.orgid and x.cod_status = 'Y'
)
select m.cpm_id,
       m.cpm_prefix,
       m.cpm_desc,
       nvl(m.cpm_type, 'N') cpm_type,
       pm.com_id,
       pm.com_level,
       pm.com_value,
       pm.com_desc,
       pm.com_desc_mar,
       pd.cod_id,
       pd.cod_value,
       null cpd_others,
       pd.cod_desc,
       pd.cod_desc_mar,
       pd.cpd_default cod_cpd_default,
       pd.parent_id,
       o.orgid,
       m.cpm_replicate_flag
  from tb_comparam_mas  m,
       tb_comparent_mas pm,
       tb_comparent_det pd,
       tb_organisation  o
 where m.cpm_id = pm.cpm_id
   and nvl(m.cpm_type, 'N') = 'H'
   and m.cpm_replicate_flag = 'N'
--   and m.load_at_startup = 'Y'
   and m.cpm_status = 'A'
   and pm.com_id = pd.com_id
   and pm.orgid = pd.orgid
   and pm.com_status = 'Y'
   and pd.cod_status = 'Y'
   and pd.orgid = o.orgid
   and o.default_status = 'Y'
   and o.org_status = 'A'
   and exists (select 1
          from tb_hir_details h
         where h.orgid = pd.orgid
           and h.cod_id = pd.cod_id)
union all
select m.cpm_id,
       m.cpm_prefix,
       m.cpm_desc,
       nvl(m.cpm_type, 'N') cpm_type,
       pm.com_id,
       pm.com_level,
       pm.com_value,
       pm.com_desc,
       pm.com_desc_mar,
       pd.cod_id,
       pd.cod_value,
       null cpd_others,
       pd.cod_desc,
       pd.cod_desc_mar,
       pd.cpd_default cod_cpd_default,
       pd.parent_id,
       o.orgid,
       m.cpm_replicate_flag
  from tb_comparam_mas  m,
       tb_comparent_mas pm,
       tb_comparent_det pd,
       tb_organisation  o
 where m.cpm_id = pm.cpm_id
   and nvl(m.cpm_type, 'N') = 'H'
   and m.cpm_replicate_flag = 'Y'
--   and m.load_at_startup = 'Y'
   and m.cpm_status = 'A'
   and pm.com_id = pd.com_id
   and pm.orgid = pd.orgid
   and pm.com_status = 'Y'
   and pd.cod_status = 'Y'
   and pd.orgid = o.orgid
   and o.org_status = 'A'
   and exists (select 1
          from tb_hir_details h
         where h.orgid = pd.orgid
           and h.cod_id = pd.cod_id)
union all
select m.cpm_id,
       m.cpm_prefix,
       m.cpm_desc,
       nvl(m.cpm_type, 'N') cpm_type,
       null com_id,
       null com_level,
       null com_value,
       null com_desc,
       null com_desc_mar,
       cd.cpd_id,
       cd.cpd_value,
       cd.cpd_others,
       cd.cpd_desc,
       cd.cpd_desc_mar,
       cd.cpd_default cod_cpd_default,
       null parent_id,
       o.orgid,
       m.cpm_replicate_flag
  from tb_comparam_mas m, tb_comparam_det cd, tb_organisation o
 where m.cpm_id = cd.cpm_id
   and nvl(m.cpm_type, 'N') <> 'H'
   and m.cpm_status = 'A'
   and m.cpm_replicate_flag = 'N'
--   and m.load_at_startup = 'Y'
   and cd.orgid = o.orgid
   and cd.cpd_status = 'A'
   and o.default_status = 'Y'
   and o.org_status = 'A'
union all
select m.cpm_id,
       m.cpm_prefix,
       m.cpm_desc,
       nvl(m.cpm_type, 'N') cpm_type,
       null com_id,
       null com_level,
       null com_value,
       null com_desc,
       null com_desc_mar,
       cd.cpd_id,
       cd.cpd_value,
       cd.cpd_others,
       cd.cpd_desc,
       cd.cpd_desc_mar,
       cd.cpd_default cod_cpd_default,
       null parent_id,
       o.orgid,
       m.cpm_replicate_flag
  from tb_comparam_mas m, tb_comparam_det cd, tb_organisation o
 where m.cpm_id = cd.cpm_id
   and nvl(m.cpm_type, 'N') = 'N'
   and m.cpm_status = 'A'
   and m.cpm_replicate_flag = 'Y'
--   and m.load_at_startup = 'Y'
   and cd.cpd_status = 'A'
   and cd.orgid = o.orgid
   and o.org_status = 'A'
 )
;

prompt
prompt Creating view VW_REJ_CHECKLIST
prompt ==============================
prompt
create or replace force view vw_rej_checklist as
select distinct cm.clm_id,/*cr.cfc_rejection_id,*/cm.clm_desc,cm.clm_desc_engl/*,cr.apm_application_id*/,a.application_id,a.clm_remark
  from /*tb_cfc_rejection cr,*/  -- tb_cfc_rejection related code commented by ashish mahadik against D-7651 on 29-01-2016
       tb_cfc_checklist_mst cm,
       tb_attach_cfc a
 where /*cr.clm_id = cm.clm_id
   and a.clm_id = cr.clm_id
   and cr.orgid = cm.orgid*/
   /*and*/ cm.orgid = a.orgid
   /*and a.application_id = cr.apm_application_id*/
   and a.clm_id = cm.clm_id
   and a.application_id = a.application_id
   and a.att_id in (select max(x.att_id)
                      from tb_attach_cfc x/*, tb_cfc_rejection cj
                     where x.clm_id = cj.clm_id
                       and x.orgid = cj.orgid
                       and x.application_id = cj.apm_application_id*/ --commented by nk for defect D-7651
                     group by x.application_id,x.clm_id)
   and a.clm_apr_status = 'N'
   --and cr.cfc_rej_type in ('R','H')
;


spool off
