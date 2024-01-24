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
