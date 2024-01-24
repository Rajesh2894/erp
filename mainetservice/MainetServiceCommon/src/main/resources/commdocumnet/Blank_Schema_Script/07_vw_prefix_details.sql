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
