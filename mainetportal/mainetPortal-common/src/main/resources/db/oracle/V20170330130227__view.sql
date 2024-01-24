---------------------------------------------------------
-- Export file for user PORTAL1                        --
-- Created by kailash.agarwal on 3/30/2017, 1:02:06 PM --
---------------------------------------------------------

set define off
spool view.log

prompt
prompt Creating view VW_EIP_QUICK_LINK_MENU
prompt ====================================
prompt
create or replace force view vw_eip_quick_link_menu as
(select rownum row_num, x."ORGID",x."LINKID",x."LINK_ID",x."MENU_NM_EN",x."MENU_NM_REG",x."PARENTID",
   x."LINK_TYPE",x."HAS_SUB_LINK",x.PAGE_URL,x."SORT_ORDER",x."LINK_ORDER",X."IS_LINK_MODIFY"
from
(select s.orgid,
       link_id linkid,
       'M' || link_id link_id,
       link_title_en MENU_NM_EN,
       link_title_reg MENU_NM_REG,
       'M' parentid,
       'M' link_type,
       null has_sub_link,
--       null page_url,--commened by yv dated 130514
       s.link_path page_url, --added on 160514
       to_number(link_id) sort_order,
       link_order,
       null  Section_type, --added by yv dated 130514
       null  img_link_type, --added by yv dated 140514
       S.IS_LINK_MODIFY  IS_LINK_MODIFY ---ADDED A
  from tb_eip_links_master s where s.isdeleted='N' and s.cheker_flag ='Y'
  union
select s.orgid,
       d.sub_link_mas_id linkid,
       'F' || d.sub_link_mas_id link_id,
       d.sub_link_name_en MENU_NM_EN,
       d.sub_link_name_rg MENU_NM_REG,
       case
         when d.sub_link_par_id is null then
          'M' || d.link_id
         else
          'F' || d.sub_link_par_id
       end parentid,
       'F' link_type,
       d.has_sub_link,
       d.page_url,
--       to_number(s.link_id || '.' || d.sub_link_mas_id) sort_order, --commented by yv dated 200514
      -- to_number(s.link_id || '.' || d.sub_link_order) sort_order, --added by yv dated 200514
         to_number(s.link_id || d.sub_link_order) sort_order, --added by Rajendra dated 110614
       link_order,
       fn_getcpddesc_eip( d.cpd_secion_type,'V',d.orgid) Section_type, --added by yv dated 130514
       fn_getcpddesc_eip( d.cpd_img_link_type,'V',d.orgid) IMG_link_type, --added by yv dated 130514
       D.IS_LINK_MODIFY IS_LINK_MODIFY
  from tb_eip_links_master s, TB_EIP_SUB_LINKS_MASTER d
 where s.link_id = d.link_id
 and s.isdeleted='N' and d.isdeleted = 'N' and d.cheker_flag ='Y' and s.cheker_flag ='Y'
   and s.orgid = d.orgid
   order by LINK_ORDER,SORT_ORDER) x
   )
;

prompt
prompt Creating view VW_PG_BANK_MASTER_PORTAL
prompt ======================================
prompt
create or replace force view vw_pg_bank_master_portal as
select a.bm_bankid, b.cb_bankname bm_bankname,
a.pb_merchantid,c.pb_bankurl,a.orgid
from tb_pg_bank  a,
tb_custbanks b,
tb_pg_bank_detail c
where a.bm_bankid= b.cb_bankcode
and a.orgid        = b.orgid
and a.orgid        = c.orgid
and a.bm_bankid=c.bm_bankid
and a.bm_active = 'A';

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


spool off
