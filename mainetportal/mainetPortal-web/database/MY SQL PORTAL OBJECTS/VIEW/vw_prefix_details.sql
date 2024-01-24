/*---2---*/
create or replace view vw_prefix_details as
select m.cpm_id,
       m.cpm_prefix,
       m.cpm_desc,
       COALESCE(m.cpm_type, 'N') cpm_type,
       pm.com_id,
       pm.com_level,
       pm.com_value,
       pm.com_desc,
       pm.com_desc_mar,
       pd.cod_id COD_CPD_ID,
       pd.cod_value COD_CPD_VALUE,
       null cpd_others,
       pd.cod_desc COD_CPD_DESC,
       pd.cod_desc_mar COD_CPD_DESC_MAR,
       pd.cpd_default cod_cpd_default,
       pd.parent_id COD_CPD_PARENT_ID,
       o.orgid,
       m.cpm_replicate_flag
  from tb_comparam_mas  m,
       tb_comparent_mas pm,
       tb_comparent_det pd,
       tb_organisation  o
      
 where m.cpm_id = pm.cpm_id
   and COALESCE(m.cpm_type, 'N') = 'H'
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
  
union all
select m.cpm_id,
       m.cpm_prefix,
       m.cpm_desc,
       COALESCE(m.cpm_type, 'N') cpm_type,
       pm.com_id,
       pm.com_level,
       pm.com_value,
       pm.com_desc,
       pm.com_desc_mar,
       pd.cod_id COD_CPD_ID,
       pd.cod_value COD_CPD_VALUE,
       null cpd_others,
       pd.cod_desc COD_CPD_DESC,
       pd.cod_desc_mar COD_CPD_DESC_MAR,
       pd.cpd_default cod_cpd_default,
       pd.parent_id COD_CPD_PARENT_ID,
       o.orgid,
       m.cpm_replicate_flag
  from tb_comparam_mas  m,
       tb_comparent_mas pm,
       tb_comparent_det pd,
       tb_organisation  o
       
 where m.cpm_id = pm.cpm_id
   and COALESCE(m.cpm_type, 'N') = 'H'
   and m.cpm_replicate_flag = 'Y'
--   and m.load_at_startup = 'Y'
   and m.cpm_status = 'A'
   and pm.com_id = pd.com_id
   and pm.orgid = pd.orgid
   and pm.com_status = 'Y'
   and pd.cod_status = 'Y'
   and pd.orgid = o.orgid
   and o.org_status = 'A'
   
union all
select m.cpm_id,
       m.cpm_prefix,
       m.cpm_desc,
       COALESCE(m.cpm_type, 'N') cpm_type,
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
   and COALESCE(m.cpm_type, 'N') <> 'H'
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
       COALESCE(m.cpm_type, 'N') cpm_type,
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
   and COALESCE(m.cpm_type, 'N') = 'N'
   and m.cpm_status = 'A'
   and m.cpm_replicate_flag = 'Y'
--   and m.load_at_startup = 'Y'
   and cd.cpd_status = 'A'
   and cd.orgid = o.orgid
   and o.org_status = 'A'
 
;
